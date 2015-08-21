/*
 * 中国安全漏洞图页面外部js文件。
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

	// 取得分院的信息。 -By Lee.
	// 取得json的值重新转化为我们需要的json对象。 -By Lee.
	for (var i = 0; i < list.length; i++) {
		// 得到分院的名称和安全等级。 -By Lee.
		// console.log(list[i].orgName +":"+list[i].level);
		var str = "{name:'" + list[i].id + "',value:" + list[i].count + "}"
		var obj = eval('(' + str + ')');
		// console.log(obj);
		branchData.push(obj);
		// console.log(branchData);

		// 得到分院的坐标。 -By Lee.
		// 将所有分院的坐标组成一个字符串。 -By Lee.
		coorStr += ("'" + list[i].id + "'" + ":[" + list[i].longitude + ","
				+ list[i].latitude + "],");
		console.log(coorStr);
	}

	// 给坐标字符串套上大括号。 -By Lee.
	var coorEnd = "{" + coorStr + "}";
	// 将坐标数据转换成json格式。
	coorData = eval('(' + coorEnd + ')');
	//console.log(coorData);

	// 为ECharts准备一个具备大小（宽高）的Dom
	// 基于准备好的dom，初始化echarts图表
	var myChart = echarts.init(document.getElementById('safe-map'));
	var option = {
//		    title : {
//		        text: 'iphone销量',
//		        subtext: '纯属虚构',
//		        x:'center'
//		    },
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
		    legend : {
				show : false,
				orient : 'vertical',
				x : 'left',
				data : [ '中科院各分院', '分院下属各研究所' ]
			},
		    dataRange: {
		    	show : false,
				min : 0,
				max : 100,
				calculable : false,
				color : [ '#980000', '#F34C00', '#F49300', '#2E9204']
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
				itemStyle : {
					normal : {
						// color: 各异,
						borderColor : '#fff',
						borderWidth : 20,
						areaStyle : {
							color : '#ccc'// rgba(135,206,250,0.8)
						},
						label : {
							show : false,
							textStyle : {
								color : 'rgba(139,69,19,1)'
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
				}
			},
		    series : [
		        {
		            name: '',
		            type: 'map',
		            mapType: 'china',
		         // 是否支持滚轮缩放功能
		            roam: true,
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
		            markPoint : {
		            	 symbol : 'diamond',
		                 symbolSize: 2,
		                 //large: true,
						effect : {
							show : true,
							//shadowBlur : 0
						},
						// 图形样式
						itemStyle : {
							normal : {
								// 此颜色值为亮蓝灰色
								// 设置闪烁边框的颜色
								//borderColor : 'white',
								//borderWidth : 4, // 标注边线线宽，单位px，默认为1
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

//得到分院信息，用于在地图上显示。 -By Lee.
function getBranchInfo(){
	 $.ajax({
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "initDistrictList.html", 
	        success: function(obj){
	        	alert("ssssssssssss");
	        	// 从后台得到返回的值，是一个json对象。 -By Lee.
	        	console.log(obj);
	        	//console.log("研究院信息："+url);
	        	redrawBranch(obj);
	     	}
		});
}



