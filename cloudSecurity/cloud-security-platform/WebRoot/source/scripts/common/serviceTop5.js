function redrawServiceList(eventnamelist, eventcountlist) {

	var myChart = echarts.init(document.getElementById('serviceTOP'));

	var option = {
		grid : {
			show : false,
			borderWidth : 0,
			x : 120,
			y : 0,
			x2 : -15,
			y2 : 0,
		},
		textStyle : {
			color : "white",
			align : 'right',
			fontFamily : 'LTH',
			fontWeight : 'bold'
		},

		xAxis : [ {
			// 坐标的竖线是否显示
			show : false,
			type : 'value',
			textStyle : {
				color : 'white',
				fontFamily : 'LTH',
				fontSize : 10,
				//fontStyle : 'normal',
			// fontWeight : 'bold'
			}
		} ],
		yAxis : [
		         
		         {
		        	// min : 300,
			splitLine : {
				show : false
			},
			show : true,
			// 设置坐标轴y轴的字体格式
			axisLabel : {
				show : true,
				textStyle : {
					color : 'white',
					fontFamily : 'LTH',
					fontSize : 12,
					//fontStyle : 'normal',
					//fontWeight : 'bold'
				}
			},
			type : 'category',
			//data : eventnamelist
			data:[
	                {
	                    value:eventnamelist[0],
	                    textStyle: {
	                        color: 'rgb(231, 255, 251)',
	                    }
	                },
	                {
	                    value:eventnamelist[1],
	                    textStyle: {
	                        color: 'rgb(231, 255, 251)',
	                    }
	                },
	                {
	                    value:eventnamelist[2],
	                    textStyle: {
	                        color: 'rgb(231, 255, 251)',
	                    }
	                },
	                {
	                    value:eventnamelist[3],
	                    textStyle: {
	                        color: 'rgb(231, 255, 251)',
	                    }
	                },
	                {
	                    value:eventnamelist[4],
	                    textStyle: {
	                        color: 'rgb(231, 255, 251)',
	                    }
	                }
			      ]
		} ],
		series : [ {
			name : '',
			type : 'bar',
			stack : '总量',
			itemStyle : {
				normal : {
					color : "rgb(214, 69, 41)",
					/*color:function(params){
						var colorList = ['rgb(231, 255, 251)','rgb(231, 255, 251)','rgb(231, 255, 251)','rgb(231, 255, 251)','rgb(231, 255, 251)'];
						return colorList[params.dataIndex];
					},*/
					label : {
						show : true,
						position : 'insideLeft'
					}
				}
			},
			barCategoryGap : '55%',
			data : eventcountlist
		} ]
	};

	// 为echarts对象加载数据
	myChart.setOption(option);

}
