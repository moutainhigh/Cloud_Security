/*
 * 动态攻击效果
 */


(function(window){
// 攻击类型。
var typestate = d3.map();

var typearray = eval("("+ typejson +")");

for(var i=0;i<typearray.length; i++){
	typestate.set(typearray[i][0], typearray[i][1]);
}

var settings = {
    // The data properties to parse into numbers
    numberProps: ["port", "srcLatitude", "srcLongitude", "desLatitude", "desLongitude"],

    // Layout settings
    linkAnchor: false,
    linkSiblings: false,

    tableRows: 8,
    wsHost: "ws://"+wsurl+"/websocket",
    psk: "135de9e2fb6ae653e45f06ed18fbe9a7"+"--"+window.currentId,
    wsTimeout: 30000,
    pruneInterval: 3600,
    dataPruneInterval: 60
};

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


//设置colorizer为自定义的20种颜色
var colorizer = d3.scale.ordinal().range([
		"#806bff","#fffb74", "#ff383e","#a3ffa1","#4248ff","#52a3e5","#7aff74", 
		"#f2c304", "#7b008c","#dee700", "#ff6300","#673ab7","#e51c23","#009688",
		"#cddc39","#ddde73","#fa7e7d","#6bc770", "#0665bf","#ebbf6c"]);

//返回添加过title的字符串
function spanWrap(content, classes, title) {
    return '<span title="'+ title +'" class="' + (classes ? classes.join(" ") : "") + '">' +
        content + '</span>'
}
//返回添加过title和type颜色的字符串
function spanWrapType(content, classes, title, dtype) {
	var color =  colorizer(content);
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
        return d.srcCountry + d.srcLatitude + d.srcLongitude;
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
                srcName: attack.srcName,
                service: attack.service,
                fixed: true,
                pruneTS: (new Date()).getTime() / 1000 + settings.dataPruneInterval
            }
            this.nodes.push(newAnchor);
            return newAnchor;
        }
    },

    pushAttack: function(attack) {
        while (this.get(this.ATTACKS).length > 19) {
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
                        linkStrength: n.dport === attack.dport ? 0.5 : 0.25});
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
                desName: attack.desName,
                theta: Math.atan((attack.targetY - attack.y) /
                                 (attack.targetX - attack.x)),

                dport: attack.dport,
                dtype: attack.dtype,
                service: attack.service,
                pruneTS: (new Date()).getTime() / 1000 + settings.dataPruneInterval
            }
            this.links.push({source: this._getsertAnchorFor(target),
                     target: target, linkStrength: 1.0});
            this.nodes.push(target);
        }

        attack.type = this.ATTACKS;
        attack.age = 0;

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
    	// 开始布局
        var that = this;

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
                                v = that.scaleTargetVel(d.age) * e.alpha,
                                toTarget = dist(d.cx, d.cy, d.x, d.y);

                            if (v <= toTarget) {
                                var theta = Math.atan2(d.cy - d.y, d.cx - d.x);
                                d.x += v * Math.cos(theta);
                                d.y += v * Math.sin(theta);
                            } else {
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
                
                nodeModel.get(nodeModel.TARGETS)
                    .forEach(function(n) {
                       var c = d3.rgb(colorizer(n.service)),
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
            // 设置碰撞攻击目标圆环的宽度。
            impactWidth: 3,

            draw: function(ctx) {
                var pi = Math.PI;

                ctx.fillStyle = "#f00";
                nodeModel.get(nodeModel.TARGETS)
                    .forEach(function(n) {

                        var c = d3.rgb(colorizer(n.service)),
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
        params.insert(incoming, this.state);
    };

    this.data = function() {
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
                this.updated = incoming.attackCount;
                if(this.updated.length != 0){
                	for(var i=0; i<this.updated.length; i++){
                		if (this.state.has(this.updated[i].type)) {
                            this.state.set(this.updated[i].type,
                                    	this.state.get(this.updated[i].type) + this.updated[i].currentNum);
                        } else {
                            this.state.set(this.updated[i].type, this.updated[i].currentNum);
                        }
                	}
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
                 for(var i=0; i<updated.length; i++){
	                 rows.filter(function(d) {return d.key == updated[i].type})
	                 	.style("color", settings.typetriggerColor)
	                     .transition()
	                     .duration(1000)
	                     .style("color", function(d) { return colorizer(d.key); });
                 }

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
                    .attr("class", "row");
                rows.exit().remove();

                // 设置单元格内容。
                var cols = rows.selectAll("td")
                    .data(function(d) {
                        return [
                            d.datetime,
                            "",
                            spanWrap(d.srcName,
                                    ["service2", "overflow"], d.srcName),
                            "",
                            d.srcIP,
                            "",
                            spanWrap(d.desName,
                                    ["service2", "overflow"], d.desName),
                            "",
                            d.desIP,
                            "",
                            spanWrapType(d.service || "未知类型",
                                          ["service2", "overflow"], d.service || "未知类型", d.dtype),
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
	  	var data = eval("(" + evt.data + ")");
	  	var datum = data.attack;
	    
	    // 如果经度和纬度都为0，则设置经度为-5，纬度为-10.
	    if (datum.srcLongitude == 0 && datum.srcLatitude == 0) {
	        datum.srcLongitude = -5;
	        datum.srcLatitude = -50;
	    }
	    var smallNum=wafTotalNumber;
	    wafTotalNumber++;
	    todayFontVal++;
	    //alert(fontVal);
		document.getElementById("totalFon").firstChild.nodeValue=wafTotalNumber;
		document.getElementById("dayTotalFon").firstChild.nodeValue=todayFontVal;
		//$("#testNum").runNum(wafTotalNumber);
		$('.dataStatistics').dataStatistics({min:smallNum,max:wafTotalNumber,time:30,len:7,init:false});
	    // 攻击源坐标
	    var startLoc = projection([datum.srcLongitude, datum.srcLatitude]);
	    // 攻击目的坐标
	    var endLoc = projection([datum.desLongitude, datum.desLatitude]);
	
	    // 如果数据解析出现错误
	    if (datum.error) {
	        console.log("ERROR: " + datum.error.msg);
	    }
	
	    for (var prop in datum) {
	        if (settings.numberProps.indexOf(prop) !== -1) {
	            datum[prop] = Number(datum[prop]);
	        }
	    }
	
		datum.service = datum.type ? datum.type : "未知类型";
	  	datum.cx = startLoc[0];
	  	datum.cy = startLoc[1];
		datum.x = startLoc[0];
		datum.y = startLoc[1];
		datum.targetX = endLoc[0];
		datum.targetY = endLoc[1];
		datum.id = getID();
		datum.datetime = datum.startTime;
		datum.dport = datum.port;
	
	    pauser.push(datum);
    };

    webSocket.onclose = function() {
        //try to reconnect in 5 seconds
        var interval = 5000;
        wsDiscTime += 500;
        
        webSocket.send('stop');
        
        d3.select("#events").selectAll("tr.row").remove(); 
        d3.select("#events").append("tr").attr('class', 'row').html("<td colspan='7'><span style='display: inline-block; height: 25px; vertical-align: middle;'>与服务器断开连接，5秒后尝试重新连接...</span></td>");

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

queue()
    .defer(d3.json, "source/attacking/data/world-110m.json")
    .await(function (error, world) {
        
        var root = topojson.feature(world,world.objects.countries);
    	var grid = graticule();

    	//地图区域路径。
    	svg.selectAll(".map_path")
    		.data( root.features )
    		.enter()
    		.append("path")
    		.attr("class","map_path")
    		.attr("d", path );
        
    	var webSocket = start();
        nodeModel.start();
        // 画攻击源与攻击目标之间线条。 
        painter.start();
    });
window.monitoring = {
            settings: settings
        };
    
	setInterval(function () { prune(); }, 30000);
})(window);
    

