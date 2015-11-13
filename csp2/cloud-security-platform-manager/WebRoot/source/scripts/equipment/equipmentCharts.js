var getDisk = function(id, engine, ip) {
	var label = [];
	var value = [];
	require.config({
		paths : {
			echarts : '../echarts/echarts',
			'echarts/chart/bar' : '../echarts/echarts-map',
			'echarts/chart/line' : '../echarts/echarts-map',
			'echarts/chart/pie' : '../echarts/echarts-map'
		}
	});
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {//回调函数
		var myChart = ec.init(document.getElementById('system5'));
		$.ajax({
			type : "POST",
			url : "getDiskUsage.html",
			dataType : "json",
			data : {
				id : id,
				engine : engine,
				ip : ip
			},
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				$.each(data, function(i, p) {
					var temp = null;
					if (p['label'] == 0) {
						temp = "未使用";
					}
					if (p['label'] == 1) {
						temp = "已使用";
					}
					label[i] = temp;
					value[i] = {
						'name' : temp,
						'value' : p['value']
					};
				});
				$("#diskNum").text(data[1].total);
				myChart.setOption({
					title : {
						text : '',
						subtext : '',
						x : 'center',
					},
					tooltip : {
						trigger : 'item',
						formatter : "{a} <br/>{b} : {c} ({d}%)"
					},
					legend : {
						orient : 'vertical',
						x : 'left',
						data : testX()
					},
					color : [ 'blue', 'red' ],
					toolbox : {
						show : true,
						feature : {
							restore : {
								show : true
							},
						}
					},
					calculable : false,//是否允许拖拽图
					series : [ {
						name : '磁盘使用情况',
						type : 'pie',
						radius : '55%',
						center : [ '50%', '60%' ],
						data : testY(),
						//显示百分比
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter : '{b}:{c}GB'
								},
								labelLine : {
									show : true
								}
							}
						}
					} ]
				}, true);//图形展示
				window.onresize = myChart.resize;
			}
		});
	});
	function testX() {
		return label;
	}

	function testY() {
		return value;
	}
};

var getCpu = function(id, engine, ip) {
	$.ajax({
		type : "POST",
		url : "getSysCpuUsage.html",
		dataType : "json",
		data : {
			id : id,
			engine : engine,
			ip : ip
		},
		success : function(data) {
			var printCpuPerc;
			$.each(data, function(i, p) {
				printCpuPerc = p['printCpuPerc'];
			});
			$("#system1").html("");
			var g1 = new JustGage({
				id : "system1",
				value : printCpuPerc,
				min : 0,
				max : 100,
				title : "Speedometer",
				label : "percent",
				levelColors : [ "#FF0000", "#FFF000", "#FF0000" ]
			});
		},
	});
	var chart;
	var printCpuPerc = null;
	$('#system2').highcharts(
			{
				chart : {
					type : 'spline',
					animation : Highcharts.svg, // don't animate in old IE               
					marginRight : 10,
					events : {
						load : function() {
							// set up the updating of the chart each second             
							var series = this.series[0];
							$.ajax({
								type : "POST",
								url : "getSysCpuUsage.html",
								dataType : "json",
								data : {
									id : id,
									engine : engine,
									ip : ip
								},
								success : function(data) {
									$.each(data, function(i, p) {
										printCpuPerc = p['printCpuPerc'];
									});
									var x = (new Date()).getTime(), // current time         
									y = printCpuPerc;
									series.addPoint([ x, y ], true, true);
								}
							});
						}
					}
				},
				title : {
					text : ''
				},
				xAxis : {
					type : 'datetime',
					tickPixelInterval : 150
				},
				yAxis : {
					title : {
						text : 'percent'
					},
					plotLines : [ {
						value : 0,
						width : 1,
						color : '#808080'
					} ]
				},
				tooltip : {
					formatter : function() {
						return '<b>'
								+ this.series.name
								+ '</b><br>'
								+ Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',
										this.x) + '<br>'
								+ Highcharts.numberFormat(this.y, 2) + '%';
					}
				},
				legend : {
					enabled : false
				},
				exporting : {
					enabled : false
				},
				credits : {
					enabled : false
				},
				series : [ {
					name : 'CPU使用情况',
					data : (function() {
						// generate an array of random data                             
						var data = [], time = (new Date()).getTime(), i;

						for (i = -19; i <= 0; i++) {
							data.push({
								x : time + i * 1000,
								y : printCpuPerc
							});
						}
						return data;
					})()
				} ]
			});
};
var getRam = function(id, engine, ip) {
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line' ], 
	function(ec) {//回调函数
		//--- 仪表盘 ---
		var myChart = ec.init(document.getElementById('system3'));
		//后台获取数据
		$.ajax({
			url : "getSysMemoryUsage.html",
			dataType : "json",
			data : {
				id : id,
				engine : engine,
				ip : ip
			},
			success : function(data) {
				$.each(data, function(i, p) {
					use = p['use'];
					total = p['total'];
					//                    	valueGauge[i]=[p['ratio'], temp];
				});
				option = {
					tooltip : {
						formatter : "{a} <br/>{b} : {c}"
					},
					series : [ {
						name : '内存已使用',
						type : 'gauge',
						splitNumber : 4, // 分割段数，默认为5
						max : total, //最大值
						axisLine : { // 坐标轴线
							lineStyle : { // 属性lineStyle控制线条样式
								color : [ [ 0.8, '#48b' ], [ 1, '#ff4500' ] ],
								width : 8
							}
						},
						axisTick : { // 坐标轴小标记
							splitNumber : 0, // 每份split细分多少段
							length : 0, // 属性length控制线长
							lineStyle : { // 属性lineStyle控制线条样式
								color : 'auto'
							}
						},
						axisLabel : { // 坐标轴文本标签，详见axis.axisLabel
							textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
								color : 'auto'
							}
						},
						splitLine : { // 分隔线
							show : true, // 默认显示，属性show控制显示与否
							length : 0, // 属性length控制线长
							lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
								color : 'auto'
							}
						},
						pointer : {
							width : 5
						},
						title : {
							show : true,
							offsetCenter : [ 0, '-40%' ], // x, y，单位px
							textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
								fontWeight : 'bolder'
							}
						},
						detail : {
							formatter : '{value}',
							textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
								color : 'auto',
								fontWeight : 'bolder'
							}
						},
						data : [ {
							value : use,
							name : ''
						} ]
					} ]
				};

				//图形展示
				myChart.setOption(option);
				window.onresize = myChart.resize;
				$("#rawNum").text(data[0].totals+" GB");
			}//ajax执行后台
		});
	});
	//内存动态图                                                                   
	var chart;
	var use = null;
	var total = null;
	$('#system4').highcharts({
		chart : {
					type : 'spline',
					animation : Highcharts.svg, // don't animate in old IE
					marginRight : 10,
					events : {
						load : function() {
							var series = this.series[0];
							$.ajax({
								url : "getSysMemoryUsage.html",
								dataType : "json",
								data : {
									id : id,
									engine : engine,
									ip : ip
								},
								success : function(data) {
									$.each(data, function(i, p) {
										use = p['use'];
										total = p['total'];
									});

									var x = (new Date()).getTime(), // current
									// time
									y = use;
									series.addPoint([ x, y ], true, true);
								}
							});
						}
					}
				},
				title : {
					text : ''
				},
				xAxis : {
					type : 'datetime',
					tickPixelInterval : 150
				},
				yAxis : {
					title : {
						text : 'GB'
					},
					plotLines : [ {
						value : 0,
						width : total,
						color : '#808080'
					} ]
				},
				tooltip : {
					formatter : function() {
						return '<b>'
								+ this.series.name
								+ '</b><br>'
								+ Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',
										this.x) + '<br>'
								+ Highcharts.numberFormat(this.y, 2) + 'MB';
					}
				},
				legend : {
					enabled : false
				},
				exporting : {
					enabled : false
				},
				credits : {
					enabled : false
				},
				series : [ {
					name : '内存使用情况',
					data : (function() {
						// generate an array of random data                             
						var data = [], time = (new Date()).getTime(), i;

						for (i = -19; i <= 0; i++) {
							data.push({
								x : time + i * 1000,
								y : use
							});
						}
						return data;
					})()
				}]
			});
};
var getCount=function(id){
	$.ajax({
		url : "getCountRuning.html",
		dataType : "json",
		data : {
			engine : id
		},
		success : function(data) {
			$("#system6").text(data.count);
		}
	});
}
