var orgnames="";
var numMsg = "漏洞总数";
var typeMsg = "漏洞类型";
//$(function(){
//	showHighSiteMap();
	//initHackerMap();
	//initUserMap();
//}); 

function showHighSiteMap() {
	var myChart = echarts.init(document.getElementById('safe-map'));
	//地图初始化
	initHighSiteMap(myChart);
	//loading动画显示
	//myChart.showLoading();
	
	
	//后台获取数据
    $.ajax({
           type : "post",
           url:"highSiteMap.html",
           dataType:"json",
           contentType: "application/x-www-form-urlencoded; charset=utf-8",
           success:function(data){
          	   var dataValue = [];
	           var dataMap= data.map;
	           $.each(dataMap,function(key,value){ 
	           		var name = getDistrictName(key);
	  				var val ={'name':name,'value':value};
	  				dataValue.push(val);
				}); 
				//loading动画隐藏 
				//myChart.hideLoading();
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
           		//myChart.hideLoading();
           		return;
           }
     });
 }
 
function initHighSiteMap(myChart) {
	myChart.setOption({
		//backgroundColor: '#404a59',
	    tooltip: {
	        trigger: 'item'
	    },
	    visualMap: {
	        min: 0,
	        max: 1000,
	        left: '10%',
	        bottom: '25%',
	        textStyle: {
	           color: '#fff'
	        },
	        calculable: true
	    },
	    series: [
	        {
	            name: '告警总和超过100的网站数',
	            type: 'map',
	            mapType: 'china',
	            layoutCenter: ['50%', '40%'],  //地图中心到两侧的距离
				layoutSize: 650,  //地图大小
	            roam: false,
	            label: {
	                normal: {
	                    show: true
	                },
	                emphasis: {
	                    show: true
	                }
	            },
	            itemStyle: {
		            normal: {
		            	borderWidth : 2,
		                areaColor : '#04144d',
		                borderColor : 'rgb(110,117,145)'
		            },
		            emphasis: {
		                //areaColor: '#2a333d',
		                areaColor : '#04144d'
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
						value.push(dataList[i].val);
						
						var name = getDistrictName(dataList[i].name);
						dataValue[i]={'name':name,'value':value};
						
					}
				}
				//myChart.hideLoading();
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
           		return;
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
	        bottom: '25%',
	        calculable: true,
	        textStyle: {
	           color: '#fff'
	        },
	        pieces: [
	        	{gt: 300, color: 'black'},            // (300, Infinity]
			    {gt: 100, lte: 300, color: 'red'},  // (100, 300]
			    {gt: 50,  lte: 100, color: 'green'},  // (50, 100]  orange
			    {gt: 10,  lte: 50,  color: 'yellow'},  // (10, 50]
			    {gt: 0,   lte: 10,  color: 'orange'}   // (0, 10]   green
			]
	    },
	    geo: {
	        map: 'china',
	        label: {
	            emphasis: {
	                show: false
	            }
	        },
	        roam: false,
	        layoutCenter: ['50%', '40%'],  //地图中心到两侧的距离
			layoutSize: 650,  //地图大小
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
				//myChart.hideLoading();
				//map中数据显示
				myChart.setOption({
				    series: [
				        {
				        	name: '用户地理位置分布',
				            data: dataValue,
				            symbolSize: function (val) {
				            // 根据最大的值 来控制气泡半径  （最小 3  最大 13）
			               		return Math.round(3 + val[2] * 10 / maxVal);
				            }
				            
				        },
				        {
				            name: 'Top 5',
				            data: UserTop5Data(dataValue),
				            symbolSize: function (val) {
				                // 根据最大的值 来控制气泡半径  （最小 3  最大 13）
			               		return Math.round(3 + val[2] * 10 / maxVal);
				            }
				        }
				    ]
				});
				
           },
           error:function(){
           		myChart.hideLoading();
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
	    tooltip : {
	        trigger: 'item',
	        formatter: function (params) {
            	return params.name + ' : ' + params.value[2];
        	}
	    },
	    visualMap: {
	        min: 0,
	        max: 5000,
	        left: '10%',
	        bottom: '25%',
	        calculable: true,
	        inRange: {
	            //color: ['#50a3ba', '#eac736', '#d94e5d']
	            color: ['lightskyblue','yellow', 'orangered']
	            //color : [ '#ff81ff', '#99d9eb', '#c9bfe7', '#ffff9b', '#ffffff']
	        },
	        textStyle: {
	           color: '#fff'
	        },
	        show: true
    	},
	    geo: {
	        map: 'china',
	        layoutCenter: ['50%', '40%'],  //地图中心到两侧的距离
			layoutSize: 650,  //地图大小
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
	                    show: true
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#ddb926'
	                }
	            }
	        },
	        {
	            name: 'Top 5',
	            type: 'effectScatter',
	            coordinateSystem: 'geo',
	            data: [],
	            showEffectOn: 'render',
	            rippleEffect: {
	                //brushType: 'stroke',
	                brushType: 'fill',
	                scale:5, //动画中波纹的最大缩放比例
	                period:2 //动画的时间
	            },
	            hoverAnimation: true,
	            label: {
	                normal: {
	                    formatter: '{b}',
	                    position: 'right',
	                    show: false
	                },
	                emphasis: {
	                    show: true
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

function UserTop5Data(dataObj){
	var data = dataObj.sort(function (a, b) {
		                return b.value[2] - a.value[2];
		            });
	return data.slice(0, 5);
}

function showSecurityStateMap(){
	var myChart = echarts.init(document.getElementById('safe-map'));
 	//地图初始化
	initSecurityStateMap(myChart);
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
	        	//myChart.hideLoading();
				//map中数据显示
				myChart.setOption({
				    series: [
				        {
				        	name: '',
				            data: dataValue,
				            symbolSize: function (val) {
				            // 根据最大的值 来控制气泡半径  （最小 3  最大 13）
			               		return val[2]/maxVal*10+3;
			               		
				            }
				            
				        },
				        {
				            name: 'effect',
				            data: dataValue,
				            symbolSize: function (val) {
				                // 根据最大的值 来控制气泡半径  （最小 3  最大 13）
			               		return 10 + val[2] / maxVal *10;
				            }
				        }
				    ]
				});
	        	
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
			formatter : function(param){
				getOrgData(param);
				return orgnames;
		 	}
	    },
	    visualMap: {
	    	show : true,
	    	type: 'piecewise', // 定义为分段型 visualMap
	        min: 0,
	        max: 5000,
	        left: '10%',
	        bottom: '25%',
	        calculable: false,
	        //inRange: {
	        //    color : [ '#ff81ff', '#99d9eb', '#c9bfe7', '#ffff9b', '#ffffff']
	       // }
	        pieces: [
	        	{gt: 4000, color: '#ff81ff'}, 
			    {gt: 3000, lte: 4000, color: '#99d9eb'},
			    {gt: 2000, lte: 3000, color: '#c9bfe7'},
			    {gt: 1000, lte: 2000,  color: '#ffff9b'},
			    {gt: 0,    lte: 1000,  color: '#ffffff'}
			]
    	},
	    geo: {
	        map: 'china',
	        layoutCenter: ['50%', '40%'],  //地图中心到两侧的距离
			layoutSize: 650,  //地图大小
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

/*鼠标悬浮*/
function getOrgData(param){
	var reg = /^\+?[1-9][0-9]*$/;
	if (param.name != null && "" != param.name) {
		//console.log(param);
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
					console.log(obj);
					var sname=obj[0].name;
					var leaks = "";
					
					for (var i=0; i< obj.length;i++) {
						leaks += "<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;.&nbsp;&nbsp;"+obj[i].leakName+"</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'>"+obj[i].count+"</td></tr>"
					}
					orgnames = "<div>"
						+"<table style='font-family: 'LTH';'>"
							+"<tr><th style='height: 24px;line-height: 24px;text-align: left;font-size: 12px; font-weight: bold;margin: 0;'>"+sname+"</th></tr>"
							+"<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;"+numMsg+"</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'>"+count+"</td></tr>"
							+"<tr style='height: 16px;line-height: 16px;font-size: 12px;margin: 0;'><td>&nbsp;&nbsp;"+typeMsg+"TOP5：</td><td style='width: 30px;color: rgb(235,174,96); text-align: right;'></td></tr>"
							+leaks
						+"</table>"
					+"</div>";
					
				}
			});
		}else {
			orgnames="";
		}
	}
}
