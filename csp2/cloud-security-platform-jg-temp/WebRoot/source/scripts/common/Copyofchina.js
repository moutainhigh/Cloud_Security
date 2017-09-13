/*
 * 云安全漏洞图页面外部js文件。
 */
"use strict";
var fox = 0;
var orgnames="";
function redrawBranch(obj) {
	//var list = obj.data.flawList;
	var list = obj;
	//console.log(list[0]);
	var branchData = [];
	var coorStr = "";
	var coorData;

	// 取得分院的信息。 
	// 取得json的值重新转化为我们需要的json对象。 
	for (var i = 0; i < list.length; i++) {
		// 得到分院的名称和安全等级。 
		// console.log(list[i].orgName +":"+list[i].level);
		var str = "{name:'" + list[i].id + "',value:" + list[i].count + "}"
		var obj = eval('(' + str + ')');
		// console.log(obj);
		branchData.push(obj);

		// 得到分院的坐标。 
		// 将所有分院的坐标组成一个字符串。 
		coorStr += ("'" + list[i].id + "'" + ":[" + list[i].longitude + ","
				+ list[i].latitude + "],");
		console.log(coorStr);
	}

	// 给坐标字符串套上大括号。 
	var coorEnd = "{" + coorStr + "}";
	// 将坐标数据转换成json格式。
	coorData = eval('(' + coorEnd + ')');
	//console.log(coorData);

	// 为ECharts准备一个具备大小（宽高）的Dom
	// 基于准备好的dom，初始化echarts图表
	var myChart = echarts.init(document.getElementById('safe-map'));
	var option = {

		    tooltip : {
		        //trigger: 'item',
		        backgroundColor : 'rgba(255,255,255,0.1)',
		        textStyle : {
					color : 'white',
					// fontFamily : 'verdana',
					fontSize : 12,
					//fontStyle : 'normal',
				// fontWeight : 'bold'
				},
				//borderWidth : 1,
				enterable : false,
				formatter : function(param){
					//getOrgData(param);
					return "ddddddd";
			 }
		    },
		    /*legend : {
				show : false,
				orient : 'vertical',
				x : 'left',
				data : [ '中科院各分院', '分院下属各研究所' ]
			},*/
		    dataRange: {
		    	show : false,
				min : 0,
				max : 100,
				calculable : false,
				color : [ '#D64529', 'orange', 'yellow', '#81BA77', '#5688C1' ]
		    },
		    map : {
				// mapType : 'china', // 各省的mapType暂时都用中文
				mapLocation : {
					x : 'center',
					y : 'center'
				// width // 自适应
				// height // 自适应
				},
				showLegendSymbol : true, // 显示图例颜色标识（系列标识的小圆点），存在legend时生效
				// 图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）
				// 设定地图的颜色
				itemStyle : {
					normal : {
						borderWidth : 1,
						//borderWidth : "",
						// 设置地图边框的颜色
						borderColor : 'rgb(85,85,85)',
						// 设置地图内的填充色
						areaStyle : {
							color : 'rgba(0,0,0,0.5)'
							//color : 'rgba(255,255,255,0.1)'
						}
					}
				},
				emphasis : { // 也是选中样式
					// color: 各异,
					borderColor : 'rgba(0,0,0,0)',
					borderWidth : 1,
					areaStyle : {
						color : 'rgba(255,215,0,0.8)'
					},
					label : {
						show : false,
						textStyle : {
							color : 'rgba(139,69,19,1)'
						}
					}
				}
			},
		    series : [
		        {
		            name: '',
		            type: 'map',
		            mapType: 'china',
		         // 是否支持滚轮缩放功能
		            roam: false,
		            // 鼠标悬浮区域是否高亮
					hoverable : false,
					//滚轮缩放的极限控制
					scaleLimit:{min:0.8,max:10.0},
					// 设定地图的颜色
					itemStyle : {
						normal : {
							borderWidth : 1,
							// 设置地图边框的颜色
							borderColor : 'rgb(85,85,85)',
							// 设置地图内的填充色
							areaStyle : {
								color : 'rgba(0,0,0,0.5)'
							}
						}
					},
		            data : [],
		         // 系列中的数据标注内容
					markPoint : {
						// 闪烁的点的样式为圆形的点
						symbol : 'circle',
						//symbolSize : 5, // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
						symbolSize : function(v){
							return v/10+3;
						},
						// 图形样式
						itemStyle : {
							normal : {
								// 此颜色值为亮蓝灰色
								// 设置闪烁边框的颜色
								borderColor : 'white',
								borderWidth : 0, // 标注边线线宽，单位px，默认为1
								// 是否显示点的数值
								label : {
									show : false
								}
							},
							emphasis : {
								borderColor : '#1e90ff',
								borderWidth : 1,
								label : {
									show : false
								}
							}
						},
						// 标注的数据内容

						// 标注的数据内容
						data : branchData
					},
					// 坐标数据
					geoCoord : coorData
		        }
		    ]
		};
	myChart.setOption(option);    
 }

$(window).ready(function(){
	getBranchInfo();
}); 

//得到分院信息，用于在地图上显示。 
function getBranchInfo(){
	 $.ajax({
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "initDistrictList.html", 
	        success: function(obj){
	        	// 从后台得到返回的值，是一个json对象。 
	        	console.log(obj);
	        	//console.log("研究院信息："+url);
	        	redrawBranch(obj);
	     	}
		});
}



