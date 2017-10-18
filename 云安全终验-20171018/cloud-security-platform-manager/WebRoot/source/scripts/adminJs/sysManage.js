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
            	url:"adminSysDiskUsage.html",
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
                        x:'center',
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
                    color:['blue','red'],
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
                    calculable : false,//是否允许拖拽图
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
                        data:testY(),
                        //显示百分比
                        itemStyle:{ 
                            normal:{ 
                                  label:{ 
                                    show: true, 
                                    formatter: '{b}:{c}GB' 
                                  }, 
                                  labelLine :{show:true} 
                                } 
                            } 
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
            	url:"adminSysMemoryUsage.html",
                dataType:"json",
                success:function(data){
                	$.each(data,function(i,p){
                    	use=p['use'];
                    	total=p['total'];
//                    	valueGauge[i]=[p['ratio'], temp];
                    });
                	option = {
                		    tooltip : {
                		        formatter: "{a} <br/>{b} : {c}"
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
                		            name:'内存已使用',
                		            type:'gauge',
                		            splitNumber: 4,       // 分割段数，默认为5
                		            max: total,				//最大值
                		            axisLine: {            // 坐标轴线
                		                lineStyle: {       // 属性lineStyle控制线条样式
                		                    color: [[0.8, '#48b'],[1, '#ff4500']], 
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
                		                    fontWeight: 'bolder '

                		                },
                		           
        		                    offsetCenter: [0, '90%']
                		            },
                		            data:[{value: use, name: ''}]
                		        }
                		    ]
                		};

                	//图形展示
                    myChart.setOption(option);
                    clearInterval(timeTicket);
                    var timeTicket = setInterval(function (){
                    	$.ajax({
                        	url:"adminSysMemoryUsage.html",
                            dataType:"json",
                            success:function(data){
                            	$.each(data,function(i,p){
                            		useTemp=p['use'];
//                                	valueGauge[i]=[p['ratio'], temp];
                                });

                            }//ajax执行后台
                        });
                    	
                        option.series[0].data[0].value = useTemp.toFixed(2) - 0;
                       myChart.setOption(option, true);
                    },1000);
                    window.onresize = myChart.resize;
                }//ajax执行后台
            }); 
        }
    );
    
 //内存动态图                                                                   
$(document).ready(function() {                                                  
    Highcharts.setOptions({                                                     
        global: {                                                               
            useUTC: false                                                       
        }                                                                       
    });         
    //http://www.blogjava.net/iamhuzl/archive/2012/08/03/384652.html
    var chart; 
    var use = null;
    var total = null;
    $('#system4').highcharts({                                                
        chart: {                                                                
            type: 'spline',                                                     
            animation: Highcharts.svg, // don't animate in old IE               
            marginRight: 10,                                                    
            events: {                                                           
                load: function() {                                              
                	                                                          
                    // set up the updating of the chart each second             
                    var series = this.series[0];                                
                    setInterval(function() {  
                    	$.ajax({
                        	url:"adminSysMemoryUsage.html",
                            dataType:"json",
                            success:function(data){
                            	
                            	$.each(data,function(i,p){
                            		use=p['use'];
                                	total=p['total'];
                                });
                            	
                            	
                            	var x = (new Date()).getTime(), // current time         
                            	y = use;                                  
                            	series.addPoint([x, y], true, true);                    
                            }
                    	}); 
                    }, 1000);                                                   
                }                                                               
            }                                                                   
        },                                                                      
        title: {                                                                
            text: ''                                            
        },                                                                      
        xAxis: {                                                                
            type: 'datetime',                                                   
            tickPixelInterval: 150                                              
        },                                                                      
        yAxis: {                                                                
            title: {                                                            
//                text: 'GB' 
            	text: ''
            },                                                                  
            plotLines: [{                                                       
                value: 0,                                                       
                width: total,                                                       
                color: '#808080'                                                
            }]                                                                  
        },                                                                      
        tooltip: {                                                              
            formatter: function() {                                             
                    return '<b>'+ this.series.name +'</b><br>'+                
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br>'+
                    Highcharts.numberFormat(this.y, 2)+'MB';                         
            }                                                                   
        },                                                                      
        legend: {                                                               
            enabled: false                                                      
        },                                                                      
        exporting: {                                                            
            enabled: false                                                      
        },   
        credits: {
            enabled: false
       },
        series: [{                                                              
            name: '内存使用情况',                                                
            data: (function() {                                                 
                // generate an array of random data                             
                var data = [],                                                  
                    time = (new Date()).getTime(),                              
                    i;                                                          
                                                                                
                for (i = -19; i <= 0; i++) {                                    
                    data.push({                                                 
                        x: time + i * 1000,                                     
                        y: use                                        
                    });                                                         
                }                                                               
                return data;                                                    
            })()                                                                
        }]                                                                      
    });                                                                         
});                                                                             
                                                                                
//cpu利用率
$(document).ready(function() {                                                  
    Highcharts.setOptions({                                                     
        global: {                                                               
            useUTC: false                                                       
        }                                                                       
    });         
    //http://www.blogjava.net/iamhuzl/archive/2012/08/03/384652.html
    var chart; 
    var printCpuPerc = null;
    $('#system2').highcharts({                                                
        chart: {                                                                
            type: 'spline',                                                     
            animation: Highcharts.svg, // don't animate in old IE               
            marginRight: 10,                                                    
            events: {                                                           
                load: function() {                                              
                	                                                          
                    // set up the updating of the chart each second             
                    var series = this.series[0];                                
                    setInterval(function() {  
                    	$.ajax({
                        	url:"adminSysCpuUsage.html",
                            dataType:"json",
                            success:function(data){
                            	
                            	$.each(data,function(i,p){
                            		printCpuPerc=p['printCpuPerc'];
                                });
                            	
                            	
                            	var x = (new Date()).getTime(), // current time         
                            	y = printCpuPerc;                                  
                            	series.addPoint([x, y], true, true);                    
                            }
                    	}); 
                    }, 1000);                                                   
                }                                                               
            }                                                                   
        },                                                                      
        title: {                                                                
            text: ''                                            
        },                                                                      
        xAxis: {                                                                
            type: 'datetime',                                                   
            tickPixelInterval: 150                                              
        },                                                                      
        yAxis: {                                                                
            title: {                                                            
                text: 'percent'                                                   
            },                                                                  
            plotLines: [{                                                       
                value: 0,                                                       
                width: 1,                                                       
                color: '#808080'                                                
            }]                                                                  
        },                                                                      
        tooltip: {  
            formatter: function() {                                             
                    return '<b>'+ this.series.name +'</b><br>'+                
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br>'+
                    Highcharts.numberFormat(this.y, 2)+'%';                         
            }                                                                   
        },                                                                      
        legend: {                                                               
            enabled: false                                                      
        },                                                                      
        exporting: {                                                            
            enabled: false                                                      
        },   
        credits: {
            enabled: false
       },
        series: [{                                                              
            name: 'CPU使用情况',                                                
            data: (function() {                                                 
                // generate an array of random data                             
                var data = [],                                                  
                    time = (new Date()).getTime(),                              
                    i;                                                          
                                                                                
                for (i = -19; i <= 0; i++) {                                    
                    data.push({                                                 
                        x: time + i * 1000,                                     
                        y: printCpuPerc                                        
                    });                                                         
                }                                                               
                return data;                                                    
            })()                                                                
        }]                                                                      
    });                                                                         
});                   

});

function sysForm(){
	var sessionTime = $("#sessionTime").val();
	var numberPatrn=new RegExp("^[0-9]+$");
	if (!numberPatrn.test(sessionTime)) {
		alert("会话时长格式不正确！");
		return;
	}
	if (sessionTime <=0 || sessionTime > 120) {
		alert("会话时长不在1-120范围内");
		return;
	}
	$("#form_sys").submit();
}
