// 总体风险趋势表绘制。
/*function redrawTotalLevel(months,counts) {
	
	// 基于准备好的dom，初始化echarts图表
	var myChart = echarts.init(document.getElementById('total-level-list'));
	var option = {
		    title : {
		        text: '漏洞个数',
		        subtext: '',
				textStyle : {
					color : "#00b7ee",
					align : 'right',
					fontFamily : '微软雅黑',
					fontSize: 14,
					fontWeight : 'normal'
				} 
		    },
			
		    tooltip : {
		        trigger: 'item'
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		            data : months,
		            
		            axisLabel : {
						show : true,
						margin: 10,
						textStyle : {
							color : 'white',
							fontFamily: '微软雅黑',
							//fontSize: 11,
							fontStyle: 'normal',
							baseline : 'top'
						}
					},
					
		            splitLine: {
						show: true,
						lineStyle: {
						    color: ['rgba(255,255,255,0.1)'],
						    width: 1,
						    type: 'dashed'
						}
					} 
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            
		            axisLabel : {
		                formatter: '{value}',
		                
						textStyle : {
							color : "white",
							align : 'right',
							fontFamily : '微软雅黑',
							fontWeight : 'normal'
						}
		            },
					splitLine: {
						show: true,
						lineStyle: {
						    color: ['rgba(255,255,255,0.1)'],
						    width: 1,
						    type: 'dashed'
						} 
					} 
		        }
		    ],
		    series : [
		        {
		            name:'漏洞个数',
		            type:'line',
		            data:counts,
		            itemStyle: {
		                normal: {
		                	lineStyle: {
							    color: ['rgba(131,135,22,1)'],//131 135 22
							    width: 3,
							    type: 'solid',
							    shadowOffsetY:20,
							    shadowOffsetX:20
							}
		                }
		            },
		            markPoint : {
		                data : []
		            },
		            markLine : {
		                data : []
		            }
		        }
		    ] 
		};

	// 为echarts对象加载数据
	myChart.setOption(option);
}*/
var lineMsg = "";
function redrawTotalLevel(months,counts) {
	lineMsg = $("#lineMsg").val();
	$('#total-level-list').highcharts({ 
		chart: {
            backgroundColor: 'rgba(0,0,0,0)',
            type: 'line',
            height: 240,
			width: 430
        },
        credits: {
            enabled: false
        },
		title: { 
			text: lineMsg, 
			style : {
				color : "#00b7ee",
//				align : 'left',
				fontFamily : '微软雅黑',
				fontSize: 14,
				fontWeight : 'normal'
			},
			x: -20 //center 
		}, 
		subtitle: { 
			text: '', 
			x: -20 
		}, 
		xAxis: { 
//			type: 'category',
			categories: months,
			gridLineColor: 'rgba(255,255,255,0.1)',//纵向网格线颜色
			gridLineDashStyle: 'longdash',//横向网格线样式
			gridLineWidth: 1 //纵向网格线宽度
			
		}, 
		yAxis: { 
			min: 0,
			gridLineColor: 'rgba(255,255,255,0.1)',//横向网格线颜色
			gridLineDashStyle: 'longdash',//横向网格线样式
			gridLineWidth: 1,//横向网格线宽度
			title: { 
				text: '' 
			}, 
			plotLines: [{ 
				value: 0, 
				width: 1, 
				color: '#808080' 
			}] }, 
			tooltip: { 
				
			}, 
			legend: { 
				layout: 'vertical', 
				align: 'right', 
				verticalAlign: 'middle', 
				borderWidth: 0,
				enabled: false
			}, 
			series: [{ 
				name: '漏洞个数', 
				data: counts
			}] 
	});
}
