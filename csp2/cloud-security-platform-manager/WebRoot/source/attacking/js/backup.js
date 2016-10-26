/*
 * 动态攻击效果
 */

var refreshSeconds = 60 * 60; // 6 hours
setTimeout("location.reload()", refreshSeconds * 1000);

//var typejson = [
//	["主控端探测分布端", 111],
//	["DNS服务器攻击", 222],
//	["远程拒绝服务攻击", 333],
//	["DHCP服务器遭受攻击", 444],
//	["HTTP协议类型攻击", 555],
//	["IMAP协议类型攻击", 666],
//	["扫描探测攻击", 777],
//	["SNMP扫描探测", 888],
//	["恶意数据编码指令执行", 999],
//	["Web服务远程跨站脚本执行攻击", 500]
//];
var typejson = [
	{0: "主控端探测分布端", 1: 111},
	{0: "DNS服务器攻击", 1: 222},
	{0: "远程拒绝服务攻击", 1: 333},
	{0: "DHCP服务器遭受攻击", 1: 444},
	{0: "HTTP协议类型攻击", 1: 555},
	{0: "IMAP协议类型攻击", 1: 666},
	{0: "扫描探测攻击", 1: 777},
	{0: "SNMP扫描探测", 1: 888},
	{0: "恶意数据编码指令执行", 1: 999},
	{0: "Web服务远程跨站脚本执行攻击", 1: 500}
];
// 攻击类型。
var typestate = d3.map();
for(var i=0;i<typejson.length; i++){
	typestate.set(typejson[i][0], typejson[i][1]);
}
console.log(typestate);

var settings = {
    // The data properties to parse into numbers
    numberProps: ["dport", "latitude", "longitude", "latitude2", "longitude2"],

    // Layout settings
    linkAnchor: false,
    linkSiblings: false,

    tableRows: 8,
    wsHost: "ws://127.0.0.1:8888",
    psk: "",
    wsTimeout: 30000,
    pruneInterval: 3600,
    dataPruneInterval: 60
};

//typestate:类型初始数据
var typestate = d3.map();
// 时间标记数据
var timestampedData = [];

// 删除函数
function prune () {
    var now = new Date().getTime() / 1000;
    for (var i in timestampedData) {
        if (timestampedData[i].pruneTS > now) {
            break;
        }
    }

    // 失效数据
    var expiredData = [];

    if (i > 0) {
        var expiredData = timestampedData.splice(0, i);
    }

}


var width = $(".content").width(),
    height = $(".content").height();

var sscale = (width >= height) ? height*1.1 : width*0.9;
   
var projection = d3.geo.mercator()
    	        .scale(height/4)
    			.center([0, 10])
    	        .translate([width / 2, height / 2]);

var path = d3.geo.path()
				.projection(projection);

var graticule = d3.geo.graticule();


//设置画布
var svg = d3.select(".content").append("svg")
    .attr("class", "overlay")
    .attr("width", width)
    .attr("height", height);

svg.append("defs")
    .append("filter")
    .attr("id", "blur")
  .append("feGaussianBlur")
  	//设置标准偏差
    .attr("stdDeviation", 2);

//设置colorizer为自定义的20种颜色
var colorizer = d3.scale.ordinal().range(["#A7734B","#4BA74D","#C95399","#5494BB","#B1A135",
		"#BA4A52", "#9655A6","#465BD3","#83A74B","#4879CA","#469337", "#C2964B", 
		"#BD4ED1", "#55A1A6","#C0473E", "#CBC364","#4646BD","#A67424","#7D63B0","#9EBE75"]);

//返回添加过title的字符串
function spanWrap(content, classes, title) {
    return '<span title="'+ title +'" class="' + (classes ? classes.join(" ") : "") + '">' +
        content + '</span>'
}
//返回添加过title和type颜色的字符串
function spanWrapType(content, classes, title, dtype) {
	var color =  colorizer(dtype);
    return '<span title="'+ title +'" class="' + (classes ? classes.join(" ") : "") + '" style="color: '+color+'">' +
        content + '</span>'
}

//返回两点之间的距离
function dist(x1, y1, x2, y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
}

//返回颜色的rgba字符串
function rgbaString(c, a) {
    return "rgba(" + c.r + "," + c.g + "," + c.b + "," + a + ")";
}

function parsePorts(rawPorts) {
    var ports = [];
    for (var i = 0; i < rawPorts.length; ++i) {
        var port = parseInt(rawPorts[i].port);
        if (port in ports) {
            ports[port] = ports[port] + ", " + rawPorts[i].service;
        } else {
            ports[port] = rawPorts[i].service;
        }
    }

    // Fix certain port strings
    ports[80] = "http";
    return ports;
}

var getID = (function() {
	//生成唯一的ID
    var i = 0;
    return function() {
        return i++;
    }
})();

//如果参数是一个数字，返回true
function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

//时间格式化
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

//绘图函数
var nodeModel = {
	// 设置
    linkSiblings: true,
    linkAnchor: false,
    target: true,
    interval: 50,
    pathLength: 15,
    targetMaxAge: 200,
    scaleTargetVel: d3.scale.log().domain([1, 40]).range([40, 100]),

    // Constants
    // 常量
    ATTACKS: "attacks",
    TARGETS: "targets",
    ANCHORS: "anchors",

    lastPrune: new Date().getTime() / 1000,

    // The list of nodes, shared with the force layout
    nodes: undefined,
    // The list of links, shared with the force layout
    links: undefined,

    // Force layout to make the elements move correctly
    // 力学模拟
    force: d3.layout.force()
        .size([width, height])
        .friction(0.25)
        .gravity(0)
        .charge(-10)
        .chargeDistance(50)
        .linkDistance(15)
        .linkStrength(function(d) { return d.linkStrength || 0.5; }),

    prune: function () {
        var now = new Date().getTime() / 1000;

        if (now - this.lastPrune > 10) {
            this.lastPrune = now;

            for (var i in this.nodes) {
                if (this.nodes[i].pruneTS > now) {
                    if (i > 0) {
                        this.nodes.splice(0, i);
                    }
                    break;
                }
            }
            
            for (var i in this.links) {
                if (this.links[i].pruneTS > now) {
                    if (i > 0) {
                        this.links.splice(0, i);
                    }
                    break;
                }
            }
        }
    },

    // 获得类型（攻击）
    get: function(type) {
        return this.nodes.filter(function(n) { return n.type === type; });
    },

    // 返回城市+纬度+经度
    _mapKey: function(d) {
        return d.city + d.latitude + d.longitude;
    },

    
    _remove: function(n, j) {
        for (var i = 0; i < this.links.length; i++) {
            if (this.links[i].source.id === n.id ||
                this.links[i].target.id === n.id) {
                this.links.splice(i--, 1);
            }
        }

        if (typeof j !== 'undefined') this.nodes.splice(j, 1);

        // Remove a node, and its associated links
        for (var i = 0; i < this.nodes.length; i++) {
            if (n.id === this.nodes[i].id) {
                this.nodes.splice(i--, 1);
            }
        }

    },

    _shift: function(type) {
        for (var i = 0; i < this.nodes.length; i++) {
            if (this.nodes[i].type === type) {
                this._remove(this.nodes[i], i);
                break;
            }
        }
    },

    _getsertAnchorFor: function(attack) {
        // Get the anchor for the given node, inserting it if its not present
        var key = this._mapKey(attack),
        anchor = this.nodes.filter(function(n) { return n.key === key; })[0];

        if ( anchor ) {
            return anchor;
        } else {
            var newAnchor = {
                id: getID(),
                key: key,
                type: this.ANCHORS,
                x: attack.cx,
                y: attack.cy,
                cx: attack.cx,
                cy: attack.cy,
                country: attack.country,
                city: attack.city,
                fixed: true,
                pruneTS: (new Date()).getTime() / 1000 + settings.dataPruneInterval
            }
            this.nodes.push(newAnchor);
            return newAnchor;
        }
    },

    pushAttack: function(attack) {
        while (this.get(this.ATTACKS).length > 50) {
            this._shift(this.ATTACKS);
        }
        if (this.linkSiblings) {
            var key = this._mapKey(attack),
                that = this;
            this.nodes.forEach(function(n) {
                if ( that._mapKey(n) === key ) {//&& n.dport === d.dport ) {
                    that.links.push({
                        source: n,
                        target: attack,
                        pruneTS: (new Date()).getTime() / 1000 + settings.dataPruneInterval,
                        linkStrength: n.dport === attack.dport ? 0.5 : 2.25});
                }
            });
        }

        // Anchor
        var anchor = this._getsertAnchorFor(attack);
        if (this.linkAnchor) {
            this.links.push({source: anchor, target: attack, linkStrength: 1.0});
        }

        // Target
        if (this.target) {
            var initialVelocity = -0.0001;
            var target = {
                type: this.TARGETS,
                age: 0,
                path: [],
                h: dist(attack.x, attack.y, attack.targetX, attack.targetY),
                id: getID(),
                x: attack.x,
                y: attack.y,
                cx: attack.targetX,
                cy: attack.targetY,
                startX: attack.x,
                startY: attack.y,
                city: attack.city2,
                country: attack.country2,
                theta: Math.atan((attack.targetY - attack.y) /
                                 (attack.targetX - attack.x)),

                dport: attack.dport,
                dtype: attack.dtype,
                pruneTS: (new Date()).getTime() / 1000 + settings.dataPruneInterval
            }
            this.links.push({source: this._getsertAnchorFor(target),
                     target: target, linkStrength: 1.0});
            this.nodes.push(target);
        }

        // Decorate and add the attack node
        attack.type = this.ATTACKS;
        attack.age = 0;
        /*this.nodes.push(attack);*/

        this.force.start();
    },

    step: function() {
    	// 圆形半径增加的幅度
        this.nodes.forEach(function (n) {
            n.age++;
        });

        // 如果圆的半径大于设定的半径则移除
        this.get(this.TARGETS)
            .filter(function(t) {
                return t.age > this.targetMaxAge ||
                    "arrivalAge" in t && t["arrivalAge"] + 40 < t.age; }, this)
            .forEach(function (t) {
                this._remove(t);
            }, this);
    },

    start: function() {
        // Start the layout
    	// 开始布局
        var that = this;

        // Initialize the array references
        // 初始化数组的引用
        this.nodes = [];
        this.links = [];
        this.force
            .nodes(this.nodes)
            .links(this.links)
            .on("tick", (function() {
                return function(e) {
                    that.step();
                    that.get(that.ATTACKS).forEach(function(d) {
                        var scale = 0.1;
                        d.x += scale * (d.cx - d.x) * e.alpha;
                        d.y += scale * (d.cy - d.y) * e.alpha; });

                    that.get(that.TARGETS).forEach(function(d) {
                        d.path.unshift({x: d.x, y: d.y});
                        if (d.path.length > that.pathLength)
                            d.path.pop();

                        if (d.arrivalAge) {
                            d.fixed = true;
                        } else {
                            var travelled = dist(d.x, d.y, d.startX, d.startY),
                                //v = (Math.sqrt(travelled * 50) + 180) * e.alpha,
                                v = that.scaleTargetVel(d.age) * e.alpha,
                                toTarget = dist(d.cx, d.cy, d.x, d.y);

                            if (v <= toTarget) {
                                var theta = Math.atan2(d.cy - d.y, d.cx - d.x);
                                    //r = v / d.h;
                                d.x += v * Math.cos(theta);
                                d.y += v * Math.sin(theta);
                            } else {
                                //debugger;
                                d.x = d.cx;
                                d.y = d.cy;
                                d.arrivalAge = d.age;
                            }
                        }
                    });
                    that.force.resume();
                }
            })())
            .start();

        // 设置一个定时器，定时重新调用start函数
        d3.timer(this.force.resume);
    }
}

/*
 * painter handles rendering to the canvas
 */

// Prepare canvas and buffer
var canvas = d3.select(".content").append("canvas")
    .text("This browser doesn't support Canvas elements")
    .attr("id", "visible-canvas")
    .attr("class", "overlay")
    .attr("width", width)
    .attr("height", height);

var bufCanvas = d3.select(".content").append("canvas")
    .attr("class", "buffer overlay")
    .attr("width", width)
    .attr("height", height);

// Allows for consistent scaling of drawn elements
var logScale = d3.scale.log()
     .domain([1, 200])
     .range([1, 10]);


var lineScale = function(x) { return logScale(x) };
var circleScale = function(x) { return Math.ceil(1.4 * logScale(x)) };
var colorScale = (function() {
    var log = d3.scale.log()
        .domain([1, 600])
        .range([1, 100]);
    return function(v) {
        return log(v);
    }
})();

var painter = {
    drawings: {
        nodes: {
            active: true,
            nodeModel: nodeModel,
            // Canvas composition: "lighter", "darker", ...
            compositeOperation: undefined,
            impactOpacityScale: d3.scale.linear().domain([1, 40]).range([1, 0]),

            // 设置攻击源处圆环的半径。
            getRadius: function(d) {
                var growthEnd = 60, growthMax = 80,
                    growthStep = growthMax / growthEnd,
                    shrinkEnd = 120, shrinkMin = 20,
                    shrinkStep = (growthMax - shrinkMin) / (shrinkEnd - growthEnd);
                if (d.age >= 0 && d.age < growthEnd) {
                    return growthStep * d.age;
                } else if (d.age < shrinkEnd) {
                    return growthMax - shrinkStep * (d.age - growthEnd);
                } else {
                    return shrinkMin;
                }
            },
        	
       
            draw: function(context) {
            	//alert(nodeModel.ATTACKS);
                nodeModel.prune();

                if (this.compositeOperation) {
                    context.globalCompositeOperation = this.compositeOperation;
                }

                var that = this;
                // console.log(nodeModel.get(nodeModel.ATTACKS));
                // 此方法返回值为空，更改为TARGETS可获取到值。  -By Lee.
                //nodeModel.get(nodeModel.ATTACKS)
                nodeModel.get(nodeModel.TARGETS)
                    .forEach(function(n) {
                        //console.log(n.age);
                       var c = d3.rgb(colorizer(n.dtype)),
                            r = that.getRadius(n),
                            afterArrival = n.age - n["arrivalAge"];
                       
                       if(r <= 0){
                    	   return false;
                       }
                       
                    context.lineWidth = 3;
                    context.globalAlpha = that.impactOpacityScale(afterArrival);
                    context.strokeStyle = c.toString();
                    context.beginPath();
                    context.arc(n.startX, n.startY, r/2, 0, 2 * Math.PI);
                    context.stroke();
                });
            }
        },

        // 画攻击目标处的碰撞。
        targetImpact: {
            active: true,
            // 设置攻击目标圆形缩放的大小
            impactRadiusScale: d3.scale.linear().domain([1, 40]).range([1, 30]),
            impactOpacityScale: d3.scale.linear().domain([1, 40]).range([1, 0]),
            // 设置碰撞攻击目标圆环的宽度。 -By Lee.
            impactWidth: 3,

            draw: function(ctx) {
                var pi = Math.PI;

                ctx.fillStyle = "#f00";
                nodeModel.get(nodeModel.TARGETS)
                    .forEach(function(n) {

                        var c = d3.rgb(colorizer(n.dtype)),
                            afterArrival = n.age - n["arrivalAge"];

                        // 绘制攻击目标处的光圈。
                        if ("arrivalAge" in n) {
                            var r = this.impactRadiusScale(afterArrival);
                            //console.log(n);
                            ctx.save();
                            ctx.globalAlpha = this.impactOpacityScale(afterArrival);
                            ctx.strokeStyle = c.toString();
                            ctx.lineWidth = this.impactWidth;
                            ctx.beginPath();
                            ctx.arc(n.x, n.y, r, 0, 2 * pi);
                            ctx.closePath();
                            ctx.stroke();
                            ctx.restore();
                        }

                        // 绘制实时攻击线条。 -By Lee.
                        if (n.path.length > 0) {
                            var point = n.path[n.path.length - 1];
                            if (n.x != point.x && n.y != point.y) {
                                ctx.save();
                                var grd = ctx.createLinearGradient(n.x, n.y, point.x, point.y);
                                grd.addColorStop(0, rgbaString(c, 1));
                                grd.addColorStop(1, rgbaString(c, 0));
                                ctx.lineCap = 'round';
                                // 设置实时攻击线条的宽度。 -By Lee.
                                ctx.lineWidth = 3;
                                ctx.beginPath();
                                ctx.moveTo(n.x, n.y);
                                ctx.strokeStyle = grd;
                                ctx.lineTo(point.x, point.y);
                                ctx.closePath();
                                ctx.stroke();
                                ctx.restore();
                            }
                        }

                    }, this);
            }
        },

        // Draw the provided links.   ----画提供的线条。
        // A link: {source: _, target: _, color: _, width: _}
        // source/target: {x: _, y: _, strokeStyle: _, fillStyle: _}
        links: {
            active: true,
            order: -1,
            data: [],

            draw: function(context) {
                var pi = Math.PI;

                for (var i = 0; i < this.data.length; i++) {
                    context.beginPath();
                    context.strokeStyle = this.data[i].color;
                    context.moveTo(this.data[i].source.x, this.data[i].source.y);
                    context.lineTo(this.data[i].target.x, this.data[i].target.y);
                    context.lineWidth = this.data[i].width;
                    context.lineCap = "round";
                    context.stroke();

                    context.lineWidth = 2;
                    context.beginPath();
                    context.arc(this.data[i].source.x, this.data[i].source.y,
                                this.data[i].source.r || 5, 0, pi * 2);
                    if (this.data[i].source.fillStyle) {
                        context.fillStyle = this.data[i].source.fillStyle;
                        context.fill();
                    }
                    if (this.data[i].source.strokeStyle) {
                        context.strokeStyle = this.data[i].source.strokeStyle;
                        context.stroke();
                    }

                    context.beginPath();
                    context.fillStyle = this.data[i].target.fillStyle || "#fff";
                    context.arc(this.data[i].target.x, this.data[i].target.y,
                                this.data[i].target.r || 5, 0, pi * 2);
                    if (this.data[i].target.fillStyle) {
                        context.fillStyle = this.data[i].target.fillStyle;
                        context.fill();
                    }
                    if (this.data[i].target.strokeStyle) {
                        context.strokeStyle = this.data[i].target.strokeStyle;
                        context.stroke();
                    }
                }
            }
        }

    },

    // State
    _activeCanvas: {
        canvas: canvas,
        context: canvas.node().getContext("2d")
    },

    _clearContext: function(context) {
        context.save();
        context.clearRect(0, 0, width, height)
        context.restore();
    },

    _drawSort: function (d1, d2) { return d1.order || 0 - d2.order || 0; },

    redraw: function() {
        this.drawStart = new Date().getTime();

        // Draw the active drawings
        this._clearContext(this._activeCanvas.context);

        for (var drawing in this.drawings) {
            if (this.drawings[drawing].active) {
                this.drawings[drawing].draw(this._activeCanvas.context);
            }
        }

        var that = this;
        
        var nextFrame = 1000 / 30 - (new Date().getTime() - this.drawStart);

        if (nextFrame < 0) nextFrame = 0;
        
        this._timeout = setTimeout(function() { that.redraw() }, nextFrame);
    },

    start: function(interval) {
        this.redraw();
    },

    stop: function() {
        clearTimeout(this._timeout);
    }
}

/*
 * Stats the state machine
 */
function Stats(params) {
    this.state = params.state || d3.map();
    this.elt = params.elt || d3.select("body");
    this.tag = params.tag || "div";

    this.insert = function(incoming) {
        // Insert a new item, updating the state. params.insert should mutate
        params.insert(incoming, this.state);
    };

    this.data = function() {
        // Get the data as a list
        if (params.data) return params.data(this.state); else this.state;
    }

    this.redraw = function() {
        if (params.redraw) {
            params.redraw()
        } else {
            this.elt.selectAll(this.tag)
                .data(this.data())
                .enter().append(this.tag)
                .text(function(d) { return d});
        }
    }
}


var statsManager = {
    insert: function(incoming) {
        for (var i = 0; i < this.stats.length; ++i) {
            this.stats[i].insert(incoming);
        }
    },

    redraw: function() {
        for (var i = 0; i < this.stats.length; i++) {
            this.stats[i].redraw();
        }
    },

    stats: [
        new Stats(
            {
             state: typestate,

             insert: function(incoming) {
                 this.updated = incoming.service;
                 if (this.state.has(incoming.service)) {
                     this.state.set(incoming.service,
                                    this.state.get(incoming.service) + 1);
                 } else {
                     this.state.set(incoming.service, 1);
                 }
             },

             //攻击类型
             redraw: function() {
            	 // 将state进行排序裁剪。
                 var data = this.state.entries()
                     .sort(function(d1, d2) { return d2.value - d1.value; })
                     .slice(0, settings.tableRows),
                     updated = this.updated;

                 // 给表格的行绑定数据。
                 var rows = d3.select("#attack-type tbody").selectAll("tr.row")
                     .data(data, function(d, i) { return d ? d.key : i; });
                 rows.enter()
                     .append("tr")
                     .attr("class", "row")
                     .style("color", function(d) { return colorizer(d.key); });
                 rows.sort(function(d1, d2) { return d2.value - d1.value; });
                 rows.exit().remove();

                 // 过滤器：如果新的攻击在state中，则该行的颜色变为预定的颜色并动画到原来的颜色。
                 rows.filter(function(d) {return d.key == updated})
                 	.style("color", settings.typetriggerColor)
                     .transition()
                     .duration(1000)
                     .style("color", function(d) { return colorizer(d.key); });

                 // 返回表格要显示的数据。
                 var cols = rows.selectAll("td")
                     .data(function(d) {
                         return [
                            '<span title="' + d.key + '">' + d.key+ '</span>',
                            '<span class="type-number">' + d.value + '</span>'
                            
                     ]; });
                 // 添加单元格。
                 cols.enter().append("td");
                 // 添加数据。
                 cols.html(function(d) { return d; });
                 cols.exit().remove();
             }
            }),

        //实时攻击
        new Stats({
            state: [],

            // 如果state的长度超出预设的长度，则删除第一条内容。
            insert: function(incoming, state) {
                state.push(incoming);
                while (state.length > settings.tableRows) {
                    state.shift();
                }
                return state;
            },

            redraw: function() {
                var that = this;
                var rows = d3.select("#events tbody").selectAll("tr.row")
                    .data(this.state, function(d) { 
                    	return d.id;
                    });
                
                rows.enter().append("tr")
                	// 根据端口号设置单元行的文字颜色。
//                 	.style("color", function(d) { return colorizer(d.dtype); })
                    .attr("class", "row");
                rows.exit().remove();

                // 设置单元格内容。
                var cols = rows.selectAll("td")
                    .data(function(d) {
                        return [
                            d.datetime,
                            "",
                            spanWrap(d.srcCountry,
                                    ["service", "overflow"], d.srcCountry),
                            "",
                            d.srcIp,
                            "",
                            spanWrap(d.desCountry,
                                    ["service", "overflow"], d.desCountry),
                            "",
                            d.desIp,
                            "",
                            spanWrapType(d.service || "未知类型",
                                          ["service", "overflow"], d.dtype || "未知类型", d.dtype),
                            "",
                            spanWrap(d.dport, ["numeric"], d.dport)
                            ]; });
                cols.enter().append("td")
                    .html(function(d) { return d; });
                cols.exit().remove();
            }

        })
    ]};

var wsDiscTime = 0;

//Websocket操作：连接、接收消息、断开
function start(loc, psk) {
   webSocket = new WebSocket(loc || settings.wsHost);
    var pauser = {
        _buffer: [],

        //unbuffer属性：如果_buffer数组长度大于0，
        unbuffer: function(d) {
            while (this._buffer.length > 0) {
            	//返回_buffer数组的第一个元素并插入到unbuffer的前面
                this.insert(this._buffer.shift());
            }
            statsManager.redraw();
        },

        insert: function(d) {
        	var props = "";
        	//遍历d对象中的元素
        	for(var p in d){  
        	      if(typeof(d[p])=="function"){  
        	            d[p]();  
        	      }else{  
        	            props += p + "=" + d[p] + " ";  
        	         }  
        	  }  
        	nodeModel.pushAttack(d);
            statsManager.insert(d);
            
            d.pruneTS = new Date().getTime() / 1000 + settings.pruneInterval;
            timestampedData.push(d);
        },

        push: function(d) {
            this.insert(d);
            statsManager.redraw();
        }
    }

    webSocket.onopen = function() {
        wsDiscTime = 0;
        d3.select("#events").selectAll("tr.row").remove(); 
        webSocket.send(psk || settings.psk);            
    };
    
    // websocket连接后得到数据。
    webSocket.onmessage = function(evt) {
        if (!evt) {
            return;
        }
        // 把得到的json数据转换成对象
	  	var datum = eval("(" + data + ")");
	    
	    // 如果经度和纬度都为0，则设置经度为-5，纬度为-10.
	    if (datum.sLongitude == 0 && datum.sLatitude == 0) {
	        datum.sLongitude = -5;
	        datum.sLatitude = -50;
	    }
	
	    // 攻击源坐标
	    var startLoc = projection([datum.srcLongitude, datum.srcLatitude]);
	    // 攻击目的坐标
	    var endLoc = projection([datum.desLongitude, datum.desLatitude]);
	
	    // 如果数据解析出现错误，弹出错误窗口
	    if (datum.error) {
	        console.log("ERROR: " + datum.error.msg);
	    }
	
	    for (var prop in datum) {
	        if (settings.numberProps.indexOf(prop) !== -1) {
	            datum[prop] = Number(datum[prop]);
	        }
	    }
	
//		datum.service = datum.dtype in ports ? ports[datum.dtype] : "未知类型";
	  	datum.cx = startLoc[0];
	  	datum.cy = startLoc[1];
		datum.x = startLoc[0];
		datum.y = startLoc[1];
		datum.targetX = endLoc[0];
		datum.targetY = endLoc[1];
		datum.id = getID();
		datum.datetime = (new Date()).Format("yyyy-MM-dd hh:mm:ss");
		datum.dport = datum.port;
	
	    pauser.push(datum);
    };

    webSocket.onclose = function() {
        //try to reconnect in 5 seconds
        var interval = 5000;
        wsDiscTime += 500;
        
        webSocket.send('stop');
        
        d3.select("#events").selectAll("tr.row").remove(); 
        d3.select("#events").append("tr").attr('class', 'row').html("<td colspan='7'><img src='/D3/media/img/loading.gif' style='margin-top: 6px;'/>&nbsp;<span style='display: inline-block; height: 25px; vertical-align: middle;'>与服务器断开连接，5秒后尝试重新连接...</span></td>");

        if (wsDiscTime > settings.wsTimeout) {
        	console.log("与服务器连接困难，不过我们仍在尝试...");
            wsDiscTime = 0;
        }

        setTimeout(function(){
            console.log((new Date()).toLocaleTimeString() + "websocket closed, reconnecting in " + interval + "ms");
            start(loc, psk);
        }, interval);
    };

    return webSocket;
}


// Attacks are added to the .attacks svg group and based on data
var node = svg.selectAll(".node"),
    link = svg.selectAll(".link");

queue()
    .defer(d3.json, "data/world-110m.json")
    .defer(d3.tsv, "data/port-names.tsv")
    .await(function (error, world, rawPorts) {
        
        var root = topojson.feature(world,world.objects.countries);
      	//console.log(rawPorts);
    	var grid = graticule();

    	//地图区域路径。
    	svg.selectAll(".map_path")
    		.data( root.features )
    		.enter()
    		.append("path")
    		.attr("class","map_path")
    		.attr("d", path );
        
//      var webSocket = start();
		var ff = function(){
			var pauser = {
			        _buffer: [],
			
			        //unbuffer属性：如果_buffer数组长度大于0，
			        unbuffer: function(d) {
			            while (this._buffer.length > 0) {
			            	//返回_buffer数组的第一个元素并插入到unbuffer的前面
			                this.insert(this._buffer.shift());
			            }
			            statsManager.redraw();
			        },
			
			        insert: function(d) {
			        	var props = "";
			        	//遍历d对象中的元素
			        	for(var p in d){  
			        	      if(typeof(d[p])=="function"){  
			        	            d[p]();  
			        	      }else{  
			        	            props += p + "=" + d[p] + " ";  
			        	         }  
			        	  }  
			        	nodeModel.pushAttack(d);
			            statsManager.insert(d);
			            
			            d.pruneTS = new Date().getTime() / 1000 + settings.pruneInterval;
			            timestampedData.push(d);
			        },
			
			        push: function(d) {
		                this.insert(d);
		                statsManager.redraw();
			        }
			    }
			  	var url='ws://127.0.0.1:8888';
			  	socket=new WebSocket(url);
			  	socket.onopen=function(){console.log('连接成功')}
			  	socket.onmessage=function(evt){
			  		console.log(evt.data);
			  		if (!evt) {
			            return;
			        }
			        // 把得到的json数据转换成对象
				  	var datum = eval("(" + evt.data + ")");
				    
				    // 如果经度和纬度都为0，则设置经度为-5，纬度为-10.
				    if (datum.sLongitude == 0 && datum.sLatitude == 0) {
				        datum.sLongitude = -5;
				        datum.sLatitude = -50;
				    }
				
				    // 攻击源坐标
				    var startLoc = projection([datum.srcLongitude, datum.srcLatitude]);
				    // 攻击目的坐标
				    var endLoc = projection([datum.desLongitude, datum.desLatitude]);
				
				    // 如果数据解析出现错误，弹出错误窗口
				    if (datum.error) {
				        console.log("ERROR: " + datum.error.msg);
				    }
				
				    for (var prop in datum) {
				        if (settings.numberProps.indexOf(prop) !== -1) {
				            datum[prop] = Number(datum[prop]);
				        }
				    }
				
			//		datum.service = datum.dtype in ports ? ports[datum.dtype] : "未知类型";
				  	datum.cx = startLoc[0];
				  	datum.cy = startLoc[1];
					datum.x = startLoc[0];
					datum.y = startLoc[1];
					datum.targetX = endLoc[0];
					datum.targetY = endLoc[1];
					datum.id = getID();
					datum.datetime = (new Date()).Format("yyyy-MM-dd hh:mm:ss");
					datum.dport = datum.port;
				
				    pauser.push(datum);
			  	}
			  	socket.onclose=function(){console.log('断开连接')}
		};
		ff();
        nodeModel.start();
        // 画攻击源与攻击目标之间线条。 
        painter.start();
    });
    
function send(){
	socket.send($('#text').attr('value'));
}


//模拟数据开始
var defaultData = [
	{srcCountry: "北京", srcLongitude: 23.4, srcLatitude: 38.2, srcIp: "123.0.0.1", desCountry: "上海", desLongitude: 131.2, desLatitude: 34.1, desIp: "123.0.0.2", dtype: 1001, service: "主控端探测分布端", port: 2001},
	{srcCountry: "无锡", srcLongitude: 25.4, srcLatitude: 36.2, srcIp: "123.0.0.1", desCountry: "上海", desLongitude: 132.6, desLatitude: 35.2, desIp: "123.0.0.2", dtype: 1002, service: "DNS服务器攻击", port: 2002},
	{srcCountry: "广州", srcLongitude: 26.4, srcLatitude: 41.2, srcIp: "123.0.0.1", desCountry: "北京", desLongitude: 133.2, desLatitude: 37.3, desIp: "123.0.0.2", dtype: 1003, service: "远程拒绝服务攻击", port: 2003},
	{srcCountry: "南京", srcLongitude: 36.4, srcLatitude: 37.2, srcIp: "123.0.0.1", desCountry: "吉林", desLongitude: 140.3, desLatitude: 39.4, desIp: "123.0.0.2", dtype: 1004, service: "DHCP服务器遭受攻击", port: 2004},
	{srcCountry: "北京", srcLongitude: 41.4, srcLatitude: 34.2, srcIp: "123.0.0.1", desCountry: "西安", desLongitude: 135.3, desLatitude: 42.5, desIp: "123.0.0.2", dtype: 1005, service: "HTTP协议类型攻击", port: 2005},
	{srcCountry: "北京", srcLongitude: 23.4, srcLatitude: 36.2, srcIp: "123.0.0.1", desCountry: "南京", desLongitude: 131.7, desLatitude: 45.6, desIp: "123.0.0.2", dtype: 1006, service: "IMAP协议类型攻击", port: 2006},
	{srcCountry: "北京", srcLongitude: 26.4, srcLatitude: 35.2, srcIp: "123.0.0.1", desCountry: "深圳", desLongitude: 138.5, desLatitude: 39.7, desIp: "123.0.0.2", dtype: 1007, service: "扫描探测攻击", port: 2007},
	{srcCountry: "深圳", srcLongitude: 13.4, srcLatitude: 40.2, srcIp: "123.0.0.1", desCountry: "上海", desLongitude: 139.3, desLatitude: 37.8, desIp: "123.0.0.2", dtype: 1008, service: "SNMP扫描探测", port: 2008},
	{srcCountry: "深圳", srcLongitude: 28.4, srcLatitude: 43.2, srcIp: "123.0.0.1", desCountry: "北京", desLongitude: 140.1, desLatitude: 32.9, desIp: "123.0.0.2", dtype: 1009, service: "恶意数据编码指令执行", port: 2009},
	{srcCountry: "广州", srcLongitude: 43.4, srcLatitude: 39.2, srcIp: "123.0.0.1", desCountry: "西安", desLongitude: 139.7, desLatitude: 40.3, desIp: "123.0.0.2", dtype: 1010, service: "Web服务远程跨站脚本执行攻击", port: 2010},
];

//随机攻击数据
function randomData(){
	var n = Math.floor(Math.random()*10);
	return defaultData[n]; 
}

/*
//模拟数据
var pauser = {
    _buffer: [],

    unbuffer: function(d) {
        while (this._buffer.length > 0) {
            this.insert(this._buffer.shift());
        }
        statsManager.redraw();
    },
    //insert属性：
    insert: function(d) {
    	var props = "";
    	//遍历d对象中的元素
    	for(var p in d){  
    		//如果元素的类型为函数，则执行
    	      if(typeof(d[p])=="function"){  
    	            d[p]();  
    	      }else{  
    	    //否则，添加到字符串props中
    	            props += p + "=" + d[p] + " ";  
    	         }  
    	  }  
    	// 绘制攻击线条
    	nodeModel.pushAttack(d);
    	// 显示数据
        statsManager.insert(d);
        
        d.pruneTS = new Date().getTime() / 1000 + settings.pruneInterval;
        //console.log("d.pruneTS:"+d.pruneTS);
        timestampedData.push(d);
    },

    push: function(d) {
        this.insert(d);
        statsManager.redraw();
    },
}

nodeModel.start();
painter.start();
var intId = setInterval(function(){
	// 数据绑定：把得到的json数据转换成对象。 -By Lee.
	var datum = randomData();
    
    // 如果经度和纬度都为0，则设置经度为-5，纬度为-10.
    if (datum.sLongitude == 0 && datum.sLatitude == 0) {
        datum.sLongitude = -5;
        datum.sLatitude = -50;
    }

    // 攻击源坐标
    var startLoc = projection([datum.srcLongitude, datum.srcLatitude]);
    // 攻击目的坐标
    var endLoc = projection([datum.desLongitude, datum.desLatitude]);

    // 如果数据解析出现错误，弹出错误窗口
    if (datum.error) {
        console.log("ERROR: " + datum.error.msg);
    }

    for (var prop in datum) {
        if (settings.numberProps.indexOf(prop) !== -1) {
            datum[prop] = Number(datum[prop]);
        }
    }

  	datum.cx = startLoc[0];
  	datum.cy = startLoc[1];
	datum.x = startLoc[0];
	datum.y = startLoc[1];
	datum.targetX = endLoc[0];
	datum.targetY = endLoc[1];
	datum.id = getID();
	datum.datetime = (new Date()).Format("yyyy-MM-dd hh:mm:ss");
	datum.dport = datum.port;

    pauser.push(datum);
}, 300);*/

function stop(){
	clearInterval(intId);
}
