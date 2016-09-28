var serviceList = ['日志分析'];
$(function() {
	// 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
	require.config({
		paths : {
			echarts : '../echarts/echarts',
			'echarts/chart/bar' : '../echarts/echarts-map',
			'echarts/chart/line' : '../echarts/echarts-map',
			'echarts/chart/pie' : '../echarts/echarts-map'
		}
	});
	
	// 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {//回调函数
            //--- 折线图 ---
			// 最近30日使用趋势图
			var myChart = ec.init(document.getElementById('system1'));
			// 后台获取数据
			$.ajax({
				type : "post",
				url : "getlogslinedate.html",
				dataType : "json",
				success : function(data) {
					var timeList = data.dateList;
					timeList.reverse();
					var countList = [];
					var list = data.countList;
					for ( var j = 0; j < serviceList.length; j++) {
						var map = {};
						map['name'] = serviceList[j];
						map['type'] = 'line';
						var valueList = [];
						for ( var i = 0; i < timeList.length; i++) {
							var hasFlag = false;// 是否该日期下有值
							for ( var k = 0; k < list.length; k++) {
								if (list[k].days == timeList[i]) {
									hasFlag = true;
									valueList.push(list[k].count1);
								}
		
							}
							if (!hasFlag) {
								valueList.push(0);
							}
						}
						map['data'] = valueList;
						countList.push(map);
					}
		
					option = {
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							data : serviceList
						},
		
						calculable : false,
						xAxis : [ {
							name : '日期',
							type : 'category',
							boundaryGap : false,
							axisLabel : {
								// X轴刻度配置
								interval : 0
							// 0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
							},
		
							data : timeList
						} ],
						yAxis : [ {
							name : '扫描个数',
							type : 'value'
						} ],
						grid : { // 控制图的大小，调整下面这些值就可以，
							y : 30,
							x : 50,
							x2 : 30,
							y2 : 20,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
							// left:'10%',
							// right:'10%',
							borderColor : '#fff'
						},
						series : countList
					};
		
					myChart.setOption(option);
				},
			});
	
        }
    );
    
});