
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>全国恶意url分布图</title>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/echarts3/echarts.min.js"></script>
<script src="${ctx}/source/scripts/echarts3/china.js"></script>
<style >
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
	<div id="backImg" style="width:100%;height:100%;position:relative">
		<div id="logo" onclick="getContextPath()"
			style="width:116px;height:43px;left:3%;top:3%;position:absolute;z-index:10;background: url('${ctx}/source/images/anquanbang_white_logo.png');"></div>
		<div id="china-map"></div>
	</div>

	<script>
		function getContextPath() {
			parent.location.href = "index.html";
		}

		/**设置中国地图高度宽度
		 */

		var worldMapContainer = document.getElementById('china-map');
		//用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
		var resizeWorldMapContainer = function() {
			worldMapContainer.style.width = window.innerWidth + 'px';
			worldMapContainer.style.height = window.innerHeight + 'px';
		};
		//设置容器高宽
		resizeWorldMapContainer();
		var myChart = echarts.init(document.getElementById('china-map'));

		$(document)
				.ready(
						function() {
							jQuery
									.ajax({
										type : 'GET',
										url : "chinaURL.html",
										dataType : 'json',

										success : function(mapData) {

											var data = mapData.mapdata;
											var count = mapData.count;
											var validcount=mapData.validcount;
											data.sort(function(a, b) {
												return b.value - a.value;
											})
											var categoryData = [];
											var barData = [];
											var xData = [];
											for (var i = 0; i < 5; i++) {
												categoryData.push(data[i].name);
												barData.push(data[i].value);
												var x = {};
												x.name = data[i].name;
												x.value = data[i].value;
												x.visualMap = false;
												xData.push(x);
												//alert(x.visualMap);

											}
											barData.reverse();
											categoryData.reverse();
											xData = xData.reverse();

											var option = {
												// backgroundColor: '#404a59',//背景yanse
												title : [
														{
															text : '全国恶意URL数量分布图',
															subtext : '钓鱼网站总数：'
																	+ count
																	+ '个     '+'活跃数：'+validcount+'个',
															// subtext: '纯属虚构',
															left : 'center',
															top : '1%',
															textStyle : {
																color : '#fff'
															},
															subtextStyle : {
																color : '#f3e925',
																fontWeight:'bolder',
																fontSize:15
																
															},
														},
														{
															id : 'statistic',
															text : '活跃恶意URL数量Top5',
															left : '10%',
															top : '65%',
															width : 100,
															textStyle : {
																color : '#fff',
																fontSize : 16
															}
														} ],
												tooltip : {
													trigger : 'item',
													
												},
												legend : {
													orient : 'vertical',
													left : '5%',
													data : [ '恶意URL数量' ],
													top : '10%',
													textStyle : {
														color : '#fff'
													}
												},
												visualMap : [ {
													min : 0,
													max : 250,
													right : '5%',
													top : '60%',
													dimension:0,
													text : [ '高', '低' ],
													realtime : false,
													calculable : false,
													textStyle : {
														color : '#ddd'//为字体颜色
													},
													inRange : {
														color : [ '#ffffbf',
																'#fee090',
																'#fdae61',
																'#f46d43',
																'#d73027',
																'#a50026' ]
													}
												} ],
												toolbox : {
													show : false,
													orient : 'vertical',
													left : 'right',
													top : 'center',
													feature : {
														dataView : {
															readOnly : false
														},
														restore : {},
														saveAsImage : {}
													}
												},
												grid : {
													left : '7%',
													top : '70%',
													bottom : 2,
													//height: '20%'
													height : '25%',
													width : '20%'
												},
												xAxis : {
													show : false,
													type : 'value',
													scale : true,
													min : 0,
													position : 'top',
													boundaryGap : false,
													splitLine : {
														show : false
													},
													axisLine : {
														show : false
													},
													axisTick : {
														show : false
													},
													axisLabel : {
														margin : 2,
														textStyle : {
															color : '#aaa'
														}
													},
												},
												yAxis : {
													//show :false,
													type : 'category',
													//  name: 'TOP 20',
													nameGap : 16,
													axisLine : {
														show : true,
														lineStyle : {
															color : '#ddd'
														}
													},
													axisTick : {
														show : false,
														lineStyle : {
															color : '#ddd'
														}
													},
													axisLabel : {
														interval : 0,
														textStyle : {
															color : '#ddd'
														}
													},
													data : categoryData
												},
												series : [
														// 
														{
															name : '恶意URL数',
															type : 'map',
															mapType : 'china',
															roam : false,
															label : {
																normal : {
																	show : true
																},
																emphasis : {
																	show : true
																}
															},
															data : data
														},
														{
															id : 'bar',
															zlevel : 2,
															type : 'bar',
															symbol : 'none',
															// visualMap: false,
															itemStyle : {
																normal : {
																	formatter : '{b} : {c}',
																	color : function(
																			params) {
																		// build a color map as your need.
																		var colorList = [
																				'#00FFFF',
																				'#00ff00',
																				'#FCCE10',
																				'#FF6100',
																				'#C1232B' ];
																		return colorList[params.dataIndex]
																	},
																	label : {
																		show : true,
																		position : 'right',
																		formatter : ' {c}'
																	}

																}

															},

															data : xData
														//[{name: '北京', value: 382, visualMap: false},{name: '香港', value: 277, visualMap: false},{name: '河南', value: 188, visualMap: false},{name: '福建', value: 100, visualMap: false},{name: '陕西', value: 76, visualMap: false}   ].reverse(),
														}

												]
											};
											myChart.setOption(option);
											window.onresize = function () { 
												//resizemainMapContainer();
												resizeWorldMapContainer();
												myChart.resize({width:window.innerWidth, height:window.innerHeight}); };
										}
									});
						});
	</script>

</body>
</html>