<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>钓鱼网站分析</title>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/echarts3/echarts.min.js"></script>
<script src="${ctx}/source/scripts/echarts3/china.js"></script>
<style>
#china-map {
	width: 100%;
	margin: auto;
}
#backImg {
	background: url("${ctx}/source/images/background4.png");
	background-size: cover;
}
</style>
</head>

<body>
	<div id="backImg" style="width:100%;height:100%;">
		<div id="logo" onclick="getContextPath()"
			style="width:116px;height:43px;left:3%;top:3%;position:absolute;z-index:10;background: url('${ctx}/source/images/anquanbang_white_logo.png');"></div>
		<div id="china-map" style="width:50%;height:100% ;float:left "></div>
		
		<div id="industry-map" style="width:50%;height:50% ;float:right "></div>
		<div id="object-map"
			style="width:50%;height:50% ;right:0px;bottom:0px;float:right "></div>
		
	</div>
	<script>

        
        	function getContextPath() {
			parent.location.href = "index.html";
		}
			/**
			*设置窗体总高度、宽度
			*/
        	var mainContainer=document.getElementById('backImg');
        	var resizemainMapContainer = function () {
	   	   		mainContainer.style.width = window.innerWidth+'px';
	   	 		mainContainer.style.height = window.innerHeight*1+'px';
			};
			resizemainMapContainer();
			
			/**
			*设置左边窗体总高度、宽度
			*/
        	var worldMapContainer = document.getElementById('china-map');
			//用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
			var resizeWorldMapContainer = function () {
	   	   		worldMapContainer.style.width = window.innerWidth*0.5+'px';
	   	 		worldMapContainer.style.height = window.innerHeight+'px';
			};
			resizeWorldMapContainer();
			var myChart = echarts.init(document.getElementById('china-map'));
			/**
			*设置右边窗体1高度、宽度
			*/
			var mapContainer = document.getElementById('industry-map');
			//用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
			var resizemapContainer = function () {
	   	   		mapContainer.style.width = window.innerWidth*0.5+'px';
	   	 		mapContainer.style.height = window.innerHeight*0.4+'px';
			};
			resizemapContainer();
            var myChart2 = echarts.init(document.getElementById('industry-map'));
      		/**
			*设置右边窗体2高度、宽度
			*/
			var objectContainer = document.getElementById('object-map');
			//用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
			var resizeObjectContainer = function () {
	   	   		objectContainer.style.width = window.innerWidth*0.5+'px';
	   	 		objectContainer.style.height = window.innerHeight*0.6+'px';
			};
			resizeObjectContainer();
            var myChart3 = echarts.init(document.getElementById('object-map'));
            $(document).ready(function(){
			//
				jQuery.ajax({  
				type: 'GET',  
				        //contentType: 'application/json',  
				url: "monthUrl.html",  
				dataType: 'json',
				        
				success: function(mapdata){
					option = {
					title :[{
					        text: '钓鱼网站按月发展趋势',
					        //subtext: '分布图',
					        left: 'center',
					        top: '5%',
					        textStyle: {
					            color: '#fff'
					        }
					    }],
   // backgroundColor: '#0f375f',
    tooltip: {
        trigger: 'axis',
        
        axisPointer: {
            type: 'shadow'
        }
    },
    legend: {
    	top:'10%',
        data: ['全部', '有效','新增'],
        textStyle: {
            color: '#ccc'
        }
    },
    xAxis: {
        data: mapdata.month.slice(0, 12).reverse(),
        axisLine: {
            lineStyle: {
                color: '#ccc'
            }
        }
    },
    yAxis: {
        splitLine: {show: false},
        axisLine: {
            lineStyle: {
                color: '#ccc'
            }
        }
    },
    series: [ {
        name: '全部',
        type: 'bar',
        barGap: '-100%',
        barWidth: 10,
        itemStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(
                    0, 0, 0, 1,
                    [
                        {offset: 0, color: 'rgba(20,200,212,0.5)'},
                        {offset: 0.2, color: 'rgba(20,200,212,0.2)'},
                        {offset: 1, color: 'rgba(20,200,212,0)'}
                    ]
                )
            }
        },
        z: -12,
        data: mapdata.unvalid.slice(0, 12).reverse()
    }, {
        name: '有效',
        type: 'bar',
        barWidth: 10,
        itemStyle: {
            normal: {
                barBorderRadius: 5,
                color: new echarts.graphic.LinearGradient(
                    0, 0, 0, 1,
                    [
                        {offset: 0, color: '#14c8d4'},
                        {offset: 1, color: '#43eec6'}
                    ]
                )
            }
        },
        data: mapdata.valid.slice(0, 12).reverse()
    },{
        name: '新增',
        type: 'line',
        smooth: true,
        showAllSymbol: true,
        symbol: 'emptyCircle',
        symbolSize: 7,
        data: mapdata.increase.reverse()
    },]
};
					myChart.setOption(option);
				} 
	    });
	    
	    jQuery.ajax({  
				type: 'GET',  
				        //contentType: 'application/json',  
				url: "getcountbyfieldtop5.html",  
				dataType: 'json',
				        
				success: function(mapdata){
					option2 = {
					title :[{
					        text: '恶意URL仿冒行业分布Top5',
					        //subtext: '分布图',
					        left: 'center',
					        top: '5%',
					        textStyle: {
					            color: '#fff'
					        }
					    }],
			    //color: ['#3398DB'],
			    tooltip : {
			        trigger: 'axis',
			        
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    yAxis : [
			        {
			            type : 'category',
			            data : mapdata.filed.slice(0, 5).reverse(),
			            axisTick: {
					            show: true,
					            lineStyle: {
					                color: '#ddd'
					            }
					        },
					        axisLabel: {
					            interval: 0,
					            textStyle: {
					                color: '#ddd'
					            }
					        }, axisLine: {
					            lineStyle: {
					                color: '#ccc'
					            }
					        }
			           
			        }
			    ],
			    xAxis : [
			        {
			        	splitLine: {show: false},
			            min:0,
			            //max:2500,
			            axisLine: {
					            lineStyle: {
					                color: '#ccc'
					            }
					        },
			            axisTick: {
					            show: true,
					            lineStyle: {
					                color: '#ddd'
					            }
					        },
					        axisLabel: {
					            interval: 0,
					            textStyle: {
					                color: '#ddd'
					            }
					        },
					        
			           // interval:500,
			            type : 'value'
			        }
			    ],
			    series : [
			        {    
			        itemStyle: {
			                    normal: {
			                        color: function(params) {
			                            // build a color map as your need.
			                            var colorList = [
			                              '#ff383f','#ff7474','#a3ffa0','#7cc2fd',
			                               '#4249ff'
			                               
			                            ].reverse();
			                            return colorList[params.dataIndex]
			                        },
			                        label: {
			                            show: false,
			                            position: 'top',
			//                             formatter: '{c}'
			                            formatter: '{b}\n{c}'
			                        }
			                    }
			                },
			                
			            name:'恶意URL个数',
			            type:'bar',
			            barWidth: '30%',
			            data:mapdata.count.slice(0, 5).reverse()
			        }
			    ]
			};
			myChart2.setOption(option2);
			} 
	    });
	     jQuery.ajax({  
				type: 'GET',  
				        //contentType: 'application/json',  
				url: "getcountbytargettop10.html",  
				dataType: 'json',
				        
				success: function(mapdata){
					var ydata=mapdata.target;
					var ydata2=[];
					for (var i = 0; i < 10; i++){
						if(ydata[i].length>6){
						ydata2[i]=ydata[i];
						ydata[i]=ydata[i].substring(0,6);
						//alert(ydata[i]);
						}else{
							ydata2[i]=ydata[i];
						}
					}
					ydata2.reverse();
					option3 = {
							title :[{
							        text: '恶意URL仿冒对象分布Top10',
							       // subtext: '分布图',
							        left: 'center',
							        top: '5%',
							        textStyle: {
							            color: '#fff'
							        }
							    }],
					    //color: ['#3398DB'],
					    tooltip : {
					        trigger: 'axis',
					        formatter : function(params) {
					        	//alert(params.seriesType);
					        //	return params;
					        	//alert(ydata2[params[0].dataIndex]);
					        	return ydata2[params[0].dataIndex]+"<br />"+"恶意URL个数"+":"+params[0].value;
					        	//alert(JSON.stringify(params));
					        },
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        }
					    },
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
					    yAxis : [
					        {
					            type : 'category',
					            data : mapdata.target.slice(0, 10).reverse(),
					            axisTick: {
							            show: true,
							            lineStyle: {
							                color: '#ddd'
							            }
							        },
							        axisLabel: {
							            interval: 0,
							            textStyle: {
							                color: '#ddd'
							            }
							        },axisLine: {
							            lineStyle: {
							                color: '#ccc'
							            }
							        }
					           
					        }
					    ],
					    xAxis : [
					        {
					            splitLine: {show: false},
					           
					            axisTick: {
							            show: true,
							            lineStyle: {
							                color: '#ddd'
							            }
							        },
							        axisLabel: {
							            interval: 0,
							            textStyle: {
							                color: '#ddd'
							            }
							        },axisLine: {
							            lineStyle: {
							                color: '#ccc'
							            }
							        },
					           // interval:300,
					            type : 'value'
					        }
					    ],
					    series : [
					        {    
					        itemStyle: {
					                    normal: {
					                        color: function(params) {
					                            // build a color map as your need.
					                            var colorList = [
					                             '#26C0C0', '#ff383f','#FE8463','#fffd79','#7cc3fd','#97735b',
					                               '#61459b','#D7504B','#C6E579','#F4E001','#F0805A'
					                            ].reverse();
					                            return colorList[params.dataIndex]
					                        },
					                        label: {
					                            show: false,
					                            position: 'top',
					//                             formatter: '{c}'
					                            formatter: '{b}\n{c}'
					                        }  
					                    }
					                },
					                
					            name:'恶意URL个数',
					            type:'bar',
					            barWidth: '30%',
					            data:mapdata.count.slice(0, 10).reverse()
					        }
					    ]
					};
					myChart3.setOption(option3);
				} 
	    	});
			window.onresize =  function () { 
				resizemainMapContainer();
				resizeWorldMapContainer();
				resizemapContainer();
				resizeObjectContainer();
				myChart.resize({width:window.innerWidth*0.5, height:window.innerHeight});
				myChart2.resize({width:window.innerWidth*0.5, height:window.innerHeight*0.4});
				myChart3.resize({width:window.innerWidth*0.5, height:window.innerHeight*0.6});
			
			}; 
		//
 		});		
        </script>

</body>
</html>