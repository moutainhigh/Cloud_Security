var numMsg = "漏洞总数";
var typeMsg = "漏洞类型";
$(function(){
//	showHighSiteMap();
	//initHackerMap();
	//initUserMap();
	
	//选项卡切换
	$('.navlist li:last').css('background-image','none');
	$('.navlist li').click(function(){
			var zindex=$(this).index();
			
			$('.navlist li').removeClass('none');
			$(this).addClass('this');
			$(this).prev('li').addClass('none');
			$(this).addClass('active').siblings('li').removeClass('active');
			$(".tabBox .not-used").eq($(this).index()).show().siblings().hide();
			
			//地域分布
			if (zindex == 2) {
				showSecurityStateMap();
			}
			
					
	});
		
	//地图切换
	$('#content_ul li').click(function(){
		  var index = $(this).index();
		  $(this).addClass('active').siblings('li').removeClass('active');
	}); 
	
}); 

function showHighSiteMap() {
	var myChart = echarts.init(document.getElementById('safe-map'));
	//地图初始化
	initHighSiteMap(myChart);
	//loading动画显示
	//myChart.showLoading();
	myChart.showLoading({
	 text: '正在加载中',
	 color: '#42a7ff',
	 //textColor: '#073763',
	  textColor: '#42a7ff',
	 maskColor: 'rgba(255, 255, 255, 0)',
	 zlevel: 0
	});
	
	
	//后台获取数据
    $.ajax({
           type : "post",
           url:"highSiteMap.html",
           dataType:"json",
           contentType: "application/x-www-form-urlencoded; charset=utf-8",
           success:function(data){
          	   var dataValue = [];
	           var dataList= data.list;
	           if(null!=dataList && dataList.length>0){
					for (var i = 0; i < dataList.length; i++) {

						var name = getDistrictName(dataList[i].name);
	  					var val ={'name':name,'value':dataList[i].siteCount};
	  					dataValue.push(val);
					}
				}
				//loading动画隐藏 
				myChart.hideLoading();
				//map中数据显示
				myChart.setOption({
				    series: [
				        {
				        	name: '告警总和超过100的网站数',
				            data: dataValue
				        }
				    ]
				});
           },
           error:function(){
           		myChart.hideLoading();
           		alert("系统异常，数据加载失败！");
           		return;
           }
     });
 }
 
function initHighSiteMap(myChart) {
	myChart.setOption({
		//backgroundColor: '#404a59',
	    tooltip: {
	    	backgroundColor : 'rgba(20, 94, 181,0.8)',
	        trigger: 'item',
	        formatter: function (params) {
	        	var count = params.value;
	        	if(count== null || isNaN(count)) {
	        		count = 0;
	        	}
            	return params.name + ' : ' + count;
        	}
	    },
	    visualMap: {
	        min: 0,
	        max: 1000,
	        left: '10%',
	        bottom: '2%',
	        textStyle: {
	           color: '#fff'
	        },
	        inRange: {
	            //color : [ '#c4fffd', '#9ae9e6', '#6aceca', '#42aeaa', '#1e8682']
	            //color: ['#ffe074', '#fd2204']
	            color: ['#b8e2b0', '#93b06a','#b09664','#cc7c5f','#e5665a']
	       },
	        calculable: true
	    },
	    series: [
	        {
	            name: '告警总和超过100的网站数',
	            type: 'map',
	            mapType: 'china',
	            layoutCenter: ['50%', '50%'],  //地图中心到两侧的距离
				layoutSize: 780,  //地图大小
	            roam: false,
	            label: {
	                normal: {
	                    show: true,
	                    textStyle: {
				           color: '#fff'
				        }
	                },
	                emphasis: {
	                    show: true
	                }
	            },
	            itemStyle: {
		            normal: {
		            	borderWidth : 2,
		                areaColor : 'rgba(0,0,0,0.1)',
		                borderColor : 'rgb(110,117,145)'
		            },
		            emphasis: {
		                //areaColor: '#04144d',
		                areaColor : '#42a7ff'
		            }
		        },
	            data: []
	        }
	    ]
	});
}

function getDistrictName(key){
	var name;
	if(key == null || key == "") {
		name =  "其他";
	}else if (key.indexOf("内蒙古") != -1) {
		name =  "内蒙古";
	}else if (key.indexOf("黑龙江") != -1) {
		name =  "黑龙江";
	}else {
		name =  key.slice(0, 2);
	}
	return name;
}

//黑客分布地图
function showHackerMap() {
	var myChart = echarts.init(document.getElementById('safe-map'));
	//地图初始化
	initHackerMap(myChart);
	//loading动画显示
	//myChart.showLoading();
	myChart.showLoading({
	   text: '正在加载中',
	   color: '#42a7ff',
	   //textColor: '#073763',
	   textColor: '#42a7ff',
	   maskColor: 'rgba(255, 255, 255, 0)',
	   zlevel: 0
	});
	
	//后台获取数据
    $.ajax({
           type : "post",
           url:"HackerMap.html",
           dataType:"json",
           contentType: "application/x-www-form-urlencoded; charset=utf-8",
           success:function(data){
               var dataValue = [];
	           var dataList = data.list;
			  // 取得json的值重新转化为我们需要的json对象。 
			   if(null!=dataList && dataList.length>0){
					for (var i = 0; i < dataList.length; i++) {
						var value = [];
						value.push(dataList[i].longitude);
						value.push(dataList[i].latitude);
						value.push(dataList[i].val*100);
						
						var name = getDistrictName(dataList[i].name);
						dataValue[i]={'name':name,'value':value};
					}
				}
				myChart.hideLoading();
				//map中数据显示
				myChart.setOption({
				    series: [
				        {
				        	name: '黑客数量',
				            data: dataValue
				        }
				    ]
				});
				
           },
           error:function(){
           		myChart.hideLoading();
           		alert("系统异常，数据加载失败！");
           }
     });
 }
 
 //黑客分布地图初始化
 function initHackerMap(myChart) {
 	// 为ECharts准备一个具备大小（宽高）的Dom
	// 基于准备好的dom，初始化echarts图表
	//var myChart = echarts.init(document.getElementById('safe-map'));
	// 定义图表选项，包含图表实例可配置选项： 公共选项 ， 组件选项 ， 数据选项
	myChart.setOption({
		//backgroundColor: '#404a59',
	    tooltip: {
	        trigger: 'item'
	    },
	    visualMap: {
	    	type: 'piecewise', // 定义为分段型 visualMap
	        left: '10%',
	        bottom: '2%',
	        textStyle: {
	           color: '#fff'
	        },
	        pieces: [
			    {gt: 0,   lte: 10},   // (0, 10]
			    {gt: 10,  lte: 50},  // (10, 50]
			    {gt: 50,  lte: 100},  // (50, 100]
			    {gt: 100, lte: 300},  // (100, 300]
	        	{gt: 300}          // (300, Infinity]
			],
			inRange: {
            	color: ['#6bc770','#ddde73','#eabf6c','#ea9068', '#fa7e7d']
        	}
	    },
	    geo: {
	        map: 'china',
	        label: {
	            emphasis: {
	                show: false
	            }
	        },
	        roam: false,
	        layoutCenter: ['50%', '50%'],  //地图中心到两侧的距离
			layoutSize: 780,  //地图大小
	        itemStyle: {
		            normal: {
		            	borderWidth : 2,
		                areaColor : 'rgba(0,0,0,0.1)',
		                borderColor : 'rgb(110,117,145)'
		            },
		            emphasis: {
		                areaColor : 'rgba(0,0,0,0.1)'
		            }
		    }
	    },
	    series: [
	        {
	            name: '黑客数量',
	            type: 'heatmap',
        		coordinateSystem: 'geo',
	            mapType: 'china',
	            blurSize:20,
	            //minOpacity:0.2,
	            //maxOpacity:1,
	            //roam: false,
	            label: {
	                normal: {
	                    show: true
	                },
	                emphasis: {
	                    show: true
	                }
	            },
	            data: []
	        }
	    ]
	});
         
 }
 
//用户分布地图
function showUserMap() {
 	var myChart = echarts.init(document.getElementById('safe-map'));
 	//地图初始化
	initUserMap(myChart);
	//loading动画显示
 	//myChart.showLoading();
	myChart.showLoading({
	   text: '正在加载中',
	   color: '#42a7ff',
	   //textColor: '#073763',
	   textColor: '#42a7ff',
	   maskColor: 'rgba(255, 255, 255, 0)',
	   zlevel: 0
	});
	
	var maxVal = 0;
	//后台获取数据
    $.ajax({
           type : "post",
           url:"UserMap.html",
           dataType:"json",
           contentType: "application/x-www-form-urlencoded; charset=utf-8",
           success:function(data){
           	   var dataValue = [];
	           var dataList = data.list;
			  // 取得json的值重新转化为我们需要的json对象。 
			   if(null!=dataList && dataList.length>0){
					for (var i = 0; i < dataList.length; i++) {

						var value = [];
						value.push(dataList[i].longitude);
						value.push(dataList[i].latitude);
						value.push(dataList[i].userCount);
						
						dataValue[i]={'name':dataList[i].proviceName,'value':value};
						// 获取到最大的值 来控制气泡半径
						if (parseInt(dataList[i].userCount) >= maxVal) {
		    				maxVal = parseInt(dataList[i].userCount);
		   				}
		   				
					}
				}
				myChart.hideLoading();
				//map中数据显示
				myChart.setOption({
				    series: [
				        {
				        	name: '用户地理位置分布',
				            data: dataValue,
				            symbolSize: function (val) {
				            // 根据最大的值 来控制气泡半径  （最小 10  最大 20）
			               		return 15 + val[2] * 10 / maxVal;
				            }
				            
				        }
				    ]
				});
				
           },
           error:function(data){
           		myChart.hideLoading();
           		alert("系统异常，数据加载失败！");
           		return;
           }
     });
 }
 
 function initUserMap(myChart,dataObj, maxVal) {
 	// 为ECharts准备一个具备大小（宽高）的Dom
	// 基于准备好的dom，初始化echarts图表
	//var myChart = echarts.init(document.getElementById('safe-map'));
	myChart.setOption({
		//backgroundColor: '#404a59',
	    //tooltip : {
	    //	backgroundColor : 'rgba(20, 94, 181,0.8)',
	    //    trigger: 'item',
	    //    formatter: function (params) {
        //    	return params.name + ' : ' + params.value[2];
        //	}
	    //},
	    visualMap: {
	    	show : false,
	    	type: 'piecewise', // 定义为分段型 visualMap
	        left: '10%',
	        bottom: '2%',
	        textStyle: {
	           color: '#fff'
	        },
	        calculable: false,
	        pieces: [
	        	{gt: 2000, color: '#ffffff'}, 
			    {gt: 1500, lte: 2000, color: '#ffffff'},
			    {gt: 1000, lte: 1500, color: '#ffffff'},
			    {gt: 500, lte: 1000,  color: '#ffffff'},
			    {gt: 0,    lte: 500,  color: '#ffffff'}
			]
    	},
	    geo: {
	        map: 'china',
	        layoutCenter: ['50%', '50%'],  //地图中心到两侧的距离
			layoutSize: 780,  //地图大小
	        label: {
	            emphasis: {
	                show: false
	            }
	        },
	        roam: false,
	        itemStyle: {
	            normal: {
	                //areaColor: '#323c48',
	                //borderColor: '#111'
	                borderWidth : 2,
	                areaColor : 'rgba(0,0,0,0.1)',
	                borderColor : 'rgb(110,117,145)'
	            },
	            emphasis: {
	                //areaColor: '#2a333d',
	                areaColor : 'rgba(0,0,0,0.1)'
	            }
	        }
	    },
	    series : [
	        {
	            name: '用户地理位置分布',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            data: [],
	            label: {
	                normal: {
	                    formatter: '{b}',
	                    position: 'right',
	                    show: false
	                },
	                emphasis: {
	                    show: false
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#ddb926'
	                }
	            }
	        }
	    ]
	});
}

function showSecurityStateMap(){
	var myChart = echarts.init(document.getElementById('safe-map'));
 	//地图初始化
	initSecurityStateMap(myChart);
	//loading动画显示
 	//myChart.showLoading();
	myChart.showLoading({
	   text: '正在加载中',
	   color: '#42a7ff',
	   //textColor: '#073763',
	   textColor: '#42a7ff',
	   maskColor: 'rgba(255, 255, 255, 0)',
	   zlevel: 0
	});
	
	$.ajax({
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "initDistrictList.html?serviceId=1", 
	        success: function(data){
	           var dataList = data.districtList;
	           //控制气泡半径
	           var maxVal = data.max;
	           var dataValue = [];
			  // 取得json的值重新转化为我们需要的json对象。 
			   if(null!=dataList && dataList.length>0){
					for (var i = 0; i < dataList.length; i++) {

						var value = [];
						value.push(dataList[i].longitude);
						value.push(dataList[i].latitude);
						value.push(dataList[i].count);
						dataValue[i]={'name':dataList[i].id,'value':value};
					}
				}
	        	myChart.hideLoading();
				//map中数据显示
				myChart.setOption({
				    series: [
				        {
				        	name: '',
				            data: dataValue,
				            symbolSize: function (val) {
				            // 根据最大的值 来控制气泡半径  （最小 3  最大 13）
			               		return val[2]/maxVal*15 +10;
			               		
				            }
				            
				        },
				        {
				            name: 'effect',
				            data: dataValue,
				            symbolSize: function (val) {
				                // 根据最大的值 来控制气泡半径  （最小 3  最大 13）
			               		return val[2] / maxVal *15 +10;
				            }
				        }
				    ]
				});
	        	
	     	},
           error:function(){
           		myChart.hideLoading();
           		alert("系统异常，数据加载失败！");
           		return;
           }
		});
	
}

function initSecurityStateMap(myChart){
	myChart.setOption({
		//backgroundColor: '#404a59',
	    tooltip : {
	    	show : true, // 触发展示浮动窗口的事件类型，此处为数据触发，
			//trigger : 'item', //
			backgroundColor : 'rgba(20, 94, 181,0.8)',
			textStyle : {
				color : 'white',
				fontSize : 12,
			},
			enterable : false,
			hideDelay : 10,
			formatter : function(param, ticket, callback){
				var reg = /^\+?[1-9][0-9]*$/;
				if (param.name != null && "" != param.name) {
					var oname = param.name;
					var count = param.value[2];
					var re = reg.test(oname);
					if (re) {
						$.ajax({
							type : "POST",
							cache : false,
							dataType : "json",
							url : "getDistrictData.html?id=" + oname + "&serviceId=1",
							success : function(obj) {
								var str = getDetailData(obj,count);
					         	callback(ticket, str);
							}
						});
					
				     }
				}
			 	//return 'Loading';
			 	return '';
		 	}
	    },
	    visualMap: {
	    	show : true,
	    	type: 'piecewise', // 定义为分段型 visualMap
	        min: 0,
	        max: 5000,
	        left: '10%',
	        bottom: '2%',
	        textStyle: {
	           color: '#fff'
	        },
	        calculable: false,
	        //inRange: {
	        //    color : [ '#ff81ff', '#99d9eb', '#c9bfe7', '#ffff9b', '#ffffff']
	       // }
	        pieces: [
	        	{gt: 4000, color: '#ed3230'}, 
			    {gt: 3000, lte: 4000, color: '#ef7024'},
			    {gt: 2000, lte: 3000, color: '#f3d94c'},
			    {gt: 1000, lte: 2000,  color: '#62cf3b'},
			    {gt: 0,    lte: 1000,  color: '#ffffff'}
			]
    	},
	    geo: {
	        map: 'china',
	        layoutCenter: ['50%', '50%'],  //地图中心到两侧的距离
			layoutSize: 780,  //地图大小
	        label: {
	            emphasis: {
	                show: false
	            }
	        },
	        // 是否支持滚轮缩放功能
	        roam: false,
	        // 设定地图的颜色
	        itemStyle: {
	            normal: {
	                borderWidth : 2,
	                areaColor : 'rgba(0,0,0,0.1)',
	                borderColor : 'rgb(110,117,145)'
	            },
	            emphasis: {
	                //areaColor: '#2a333d',
	                areaColor : 'rgba(0,0,0,0.1)'
	            }
	        }
	    },
	    series : [
	        {
	            name: '',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            data: [],
	            label: {
	                normal: {
	                    formatter: '{b}',
	                    position: 'right',
	                    show: false
	                },
	                emphasis: {
	                    show: false
	                }
	            },
	            // 图形样式
	            itemStyle: {
	                normal: {
	                    color: '#ddb926'
	                }
	            }
	        },
	        {
	            name: 'effect',
	            type: 'effectScatter',
	            coordinateSystem: 'geo',
	            data: [],
	            symbol:'circle',
	            showEffectOn: 'render',
	            rippleEffect: {
	                //brushType: 'stroke',
	                brushType: 'fill',
	                scale:3, //动画中波纹的最大缩放比例
	                period:3 //动画的时间
	            },
	            label: {
	                normal: {
	                    formatter: '{b}',
	                    position: 'right',
	                    show: false
	                },
	                emphasis: {
	                    show: false
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#f4e925',
	                    shadowBlur: 10,
	                    shadowColor: '#333'
	                }
	            },
	            zlevel: 1
	        }
	    ]
	});
}

function getDetailData(obj,count){
	//console.log(obj);
	var datalist = [];
	datalist = obj;
	var sname=obj[0].name;
	var leaks = "";
					
	for (var i=0; i< obj.length;i++) {
		leaks += "<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;.&nbsp;&nbsp;"+obj[i].leakName+"</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'>"+obj[i].count+"</td></tr>"
	}
	var orgnames = "<div>"
			+"<table style='font-family: 'LTH';'>"
			+"<tr><th style='height: 24px;line-height: 24px;text-align: left;font-size: 12px; font-weight: bold;margin: 0;'>"+sname+"</th></tr>"
			+"<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;"+numMsg+"</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'>"+count+"</td></tr>"
			+"<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;"+typeMsg+"TOP5：</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'></td></tr>"
			+leaks
			+"</table>"
			+"</div>";
	return orgnames;
}
