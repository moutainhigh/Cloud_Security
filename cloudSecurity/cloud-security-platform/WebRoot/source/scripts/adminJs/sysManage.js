$(function(){
    // 定义数组
    var label = [];
    var value = [];
	//为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
    require.config({
        paths:{ 
            echarts:'../echarts/echarts',
            'echarts/chart/bar' : '../echarts/echarts-map',
            'echarts/chart/line': '../echarts/echarts-map',
            'echarts/chart/pie' : '../echarts/echarts-map'
        }
    });
	
    // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/pie'
        ],
        function (ec) {//回调函数
            //--- 饼图 ---
            var myChart = ec.init(document.getElementById('system5'));
            //后台获取数据
            $.ajax({
            	type : "post",
            	url:"sysDiskUsage.html",
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                	$.each(data,function(i,p){
	                   	
	                   	var temp = null;
	                   	if(p['label']==0){
	                   		temp="未使用";
	                   	}
	                   	if(p['label']==1){
	                   		temp="已使用";
	                   	}
//	                   	if(p['label']==2){
//	                   		temp="总大小";
//	                   	}
	                   	label[i]=temp;
	                   	value[i]={'name':temp,'value':p['value']};
//	                   	label[i]=p['label'];
//	                   	value[i]={'name':p['label'],'value':p['value']};
                    });
                	
                	
                    myChart.setOption({ title : {
                        text: '',
                        subtext: '',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient : 'vertical',
                        x : 'left',
                       // data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
                        data:testX()
                    },
                    toolbox: {
                        show : true,
                        feature : {
                           // mark : {show: true},
                            //dataView : {show: true, readOnly: false},
//                            magicType : {
//                                show: true, 
//                                type: ['pie', 'funnel'],
//                                option: {
//                                    funnel: {
//                                        x: '25%',
//                                        width: '50%',
//                                        funnelAlign: 'left',
//                                        max: 1548
//                                    }
//                                }
//                            },
                            restore : {show: true},
                          //  saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    series : [
                        {
                            name:'磁盘使用情况',
                            type:'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
//                            data:[
//                                {value:335, name:'直接访问'},
//                                {value:310, name:'邮件营销'},
//                                {value:234, name:'联盟广告'},
//                                {value:135, name:'视频广告'},
//                                {value:1548, name:'搜索引擎'}
//                            ]
                        data:testY()
                        }
                    ]},true);//图形展示
                    window.onresize = myChart.resize;
                }//ajax执行后台
            }); 
        }
    );
    function testX(){
    	return label;
    }
    
    function testY(){
    	return value;
    }
 // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        function (ec) {//回调函数
            //--- 仪表盘 ---
            var myChart = ec.init(document.getElementById('system3'));
            //后台获取数据
            $.ajax({
            	url:"sysMemoryUsage.html",
                dataType:"json",
                success:function(data){
                	option = {
                		    tooltip : {
                		        formatter: "{a} <br/>{b} : {c}%"
                		    },
//                		    toolbox: {
//                		        show : true,
//                		        feature : {
//                		            mark : {show: true},
//                		            restore : {show: true},
//                		            saveAsImage : {show: true}
//                		        }
//                		    },
                		    series : [
                		        {
                		            name:'业务指标',
                		            type:'gauge',
                		            splitNumber: 5,       // 分割段数，默认为5
                		            axisLine: {            // 坐标轴线
                		                lineStyle: {       // 属性lineStyle控制线条样式
                		                    color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']], 
                		                    width: 8
                		                }
                		            },
                		            axisTick: {            // 坐标轴小标记
                		                splitNumber: 0,   // 每份split细分多少段
                		                length :0,        // 属性length控制线长
                		                lineStyle: {       // 属性lineStyle控制线条样式
                		                    color: 'auto'
                		                }
                		            },
                		            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                		                    color: 'auto'
                		                }
                		            },
                		            splitLine: {           // 分隔线
                		                show: true,        // 默认显示，属性show控制显示与否
                		                length :0,         // 属性length控制线长
                		                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                		                    color: 'auto'
                		                }
                		            },
                		            pointer : {
                		                width : 5
                		            },
                		            title : {
                		                show : true,
                		                offsetCenter: [0, '-40%'],       // x, y，单位px
                		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                		                    fontWeight: 'bolder'
                		                }
                		            },
                		            detail : {
                		                formatter:'{value}',
                		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                		                    color: 'auto',
                		                    fontWeight: 'bolder'
                		                }
                		            },
                		            data:[{value: 50, name: ''}]
                		        }
                		    ]
                		};

                	//图形展示
                    //myChart.setOption(option);
                    clearInterval(timeTicket);
                    var timeTicket = setInterval(function (){
                        option.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
                        myChart.setOption(option, true);
                    },2000)
                    window.onresize = myChart.resize;
                }//ajax执行后台
            }); 
        }
    );
    
    
    
    
    
    
    
    
    
    
    
});
