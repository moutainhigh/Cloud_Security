$(function(){	
	
	//为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
    require.config({
        paths:{ 
            echarts:'../echarts/echarts',
            'echarts/chart/bar' : '../echarts/echarts-map',
            'echarts/chart/line': '../echarts/echarts-map',
            'echarts/chart/pie' : '../echarts/echarts-map'
        }
    });
	
    // 定义数组
    var label = [];
    var value = [];
    var colorData = [];
    var valueGauge = [];
    var time = [];
    var lineData = [];
    var lineData2 = [];
    var lineData3 = [];
    var high = null;
    
    
    // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/pie'
        ],
        function (ec) {//回调函数
            //--- 饼图 ---
            var myChartPieLevel = ec.init(document.getElementById('levelPie'));
            //后台获取数据
            $.ajax({
            	type : "post",
            	url:"getLevelPieData.html?orderId="+$('#orderId').val(),
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                	value = [];
                	label = [];
                   	colorData = [];
                	$.each(data,function(i,p){
	                   	var temp = null;
	                   	if(p['label']==0){
	                   		temp="低";
	                   	}
	                   	if(p['label']==1){
	                   		temp="中";
	                   	}
	                   	if(p['label']==2){
	                   		temp="高";
	                   	}
	                   	label[i]=temp+p['ratio'];
	                   	value[i]={'name':temp+p['ratio'],'value':p['value']};
	                   	colorData[i]=p['color'];
                    });
                    myChartPieLevel.setOption({//图形
                    	title: {
                            text: '最近一个小时事件风险分布图'
                        },
                        tooltip : {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient : 'vertical',
                            x : 'right',
                            data:testX()
                        },
                        color:colorData,
                        toolbox: {
                            show : true,
                            feature : {
                                mark : true,
                                restore : true,
                                saveAsImage : true
                            }
                        },
                        calculable : false,
                        series : [
                            {
                                name:'事件风险比例',
                                type:'pie',
                                radius : '45%',
                                center: ['50%', '60%'],
                                data:testY()
                            }
                        ]
                    },true);//图形展示
                    window.onresize = myChartPieLevel.resize;
                }//ajax执行后台
            }); 
        }
    );
    
    
 // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/pie'
        ],
        function (ec) {//回调函数
            //--- 饼图 ---
            var myChartPieEvent = ec.init(document.getElementById('eventPie'));
            //后台获取数据
            $.ajax({
            	type : "post",
            	url:"getEventPieData.html?orderId="+$('#orderId').val(),
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                    myChartPieEvent.setOption({//图形
                    	title: {
                            text: '事件类型分布图'
                        },
                        tooltip : {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient : 'vertical',
                            x : 'right',
                            data:data.name
                        },
//                        color:colorData,
                        toolbox: {
                            show : true,
                            feature : {
                                mark : true,
                                restore : true,
                                saveAsImage : true
                            }
                        },
                        calculable : false,
                        series : [
                            {
                                name:'事件类型比例',
                                type:'pie',
                                radius : '45%',
                                center: ['50%', '60%'],
                                data:data.json
                            }
                        ]
                    },true);//图形展示
                    window.onresize = myChartPieEvent.resize;
                }//ajax执行后台
            }); 
        }
    );
    
    
    // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        function (ec) {//回调函数
            //--- 柱形图 ---
        	var myChartBar = ec.init(document.getElementById('eventBar'));
          //后台获取数据
            $.ajax({
            	type: "post",
            	url:"getEventBarData.html?orderId="+$('#orderId').val(),
                dataType:"json",
//                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                    
                    myChartBar.setOption({//图形
                    	title: {
                            text: '最近一个小时事件发生事件'
                        },
                    	tooltip : {
//                            trigger: 'axis',
                            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            }
                        },
                        legend: {
                            y : 'bottom',
                            data:data.name
                        },
                        toolbox: {
                            show : true,
                            orient: 'vertical',
                            x: 'right',
                            y: 'center',
	                        feature : {
	                            mark : true,
	                            //magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
	                            restore : true,
	                            saveAsImage : true
	                        }
                        },
                        calculable : false,
                        xAxis : [
                            {
                                type : 'category',
                                data : [''],
                                axisLabel:{
           		                 //X轴刻度配置
           		                 interval:'auto' //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
           		                }

                            }
                        ],
                        yAxis : [
                            {
                                type : 'value'
                            }
                        ],
                        series :
                        	function(){
	                       	 var serie=[];
	                       	 
	                    	 for( var i=0;i < data.json.length;i++){
	                    		 var num=[];
	                    		 num[0]=data.json[i].value;
	                        	 var item={
		                        	 name:data.json[i].name,
		                        	 type:'bar',
//		                        	 barWidth : 25,
		                        	 itemStyle: {
	                                     normal: {
	                                         label: {
	                                             show: true,
	                                             textStyle: {
	                                                 color: '#800080'
	                                             }
	                                         }
	                                     }
		                        	 },
		                        	 data:num
	                        	 };
	                        	 serie.push(item);
	                    	 };
	                    	 return serie;
	                    	 }()

                    },true);//图形展示
                    window.onresize = myChartBar.resize;
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
    
    function colorData(){
    	return colorData;
    }
    
    function testGauge(){
    	return valueGauge;
    }
    
    function testLineX(){
    	return time;
    }
    
    function testLineData(){
    	return lineData;
    }
    
    function testLineData2(){
    	return lineData2;
    }
    
    function testLineData3(){
    	return lineData3;
    }
    
    
});


