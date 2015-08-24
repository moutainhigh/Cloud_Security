// 总体风险趋势表绘制。
function redrawTotalLevel(months,counts) {
	
	// 基于准备好的dom，初始化echarts图表
	var myChart = echarts.init(document.getElementById('total-level-list'));
	var option = {
		    title : {
		        text: '漏洞个数',
		        subtext: '',
				textStyle : {
					color : "blue",
					align : 'right',
					fontFamily : 'Arial',
					fontSize: 14,
					fontWeight : 'normal'
				} 
		    },
			
		    tooltip : {
		        trigger: 'axis'
		    },
		   
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		            data : months,
		            axisLabel : {
						show : true,
						margin: 2,
						textStyle : {
							color : 'white',
							fontFamily: 'Arial',
							//fontSize: 11,
							fontStyle: 'normal',
						}
					},
		            splitLine: {
						show: false,
						lineStyle: {
						    color: ['rgba(255,255,255,0.2)'],
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
							fontFamily : 'Arial',
							fontWeight : 'normal'
						} 
		            },
					splitLine: {
						show: false,
						lineStyle: {
						    color: ['rgba(255,255,255,0.2)'],
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
}