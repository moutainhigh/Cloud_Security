var myChartPie = null;
var myChartBar = null;
var myChartLine = null;

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
    //add by tangxr 2016-5-6
    var index = $('#index').val();
    
	// 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        function (ec) {//回调函数
            //--- 仪表盘 ---
            var myChart = ec.init(document.getElementById('aqfx'+ index));
            //后台获取数据
            $.ajax({
            	url:"getGaugeData.html?orderId="+$('#orderId').val()+"&type="+$('#type').val()+"&group_flag="+$('#group_flag').val()+"&websoc="+$('#websoc').val()+"&orderAssetId="+$('#orderAssetId'+ index).val(),
                dataType:"json",
                success:function(data){
                    $.each(data,function(i,p){
                    	level=p['level'];
//                    	valueGauge[i]=[p['ratio'], temp];
                    });
                    var option = {//图形
                    	tooltip : {
                            formatter: "{a} <br/>{b} : {c}%"
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                mark : true,
                                restore : true,
                                saveAsImage : true
                            }
                        },
                        series : [
                            {
                                name:'安全风险',
                                type:'gauge',
                                center : ['50%', '50%'],    // 默认全局居中
                                radius : [0, '75%'],
                                startAngle: 140,
                                endAngle : -140,
                                min: 0,                     // 最小值
                                max: 100,                   // 最大值
                                precision: 0,               // 小数精度，默认为0，无小数点
                                splitNumber: 10,             // 分割段数，默认为5
                                axisLine: {            // 坐标轴线
                                    show: true,        // 默认显示，属性show控制显示与否
                                    lineStyle: {       // 属性lineStyle控制线条样式
                                        color: [[0.4, 'lightgreen'],[0.8, 'orange'],[1, 'red']], 
//                                    	color: testGauge(),
                                        width: 30
                                    }
                                },
                                axisTick: {            // 坐标轴小标记
                                    show: true,        // 属性show控制显示与否，默认不显示
                                    splitNumber: 5,    // 每份split细分多少段
                                    length :8,         // 属性length控制线长
                                    lineStyle: {       // 属性lineStyle控制线条样式
                                        color: '#eee',
                                        width: 1,
                                        type: 'solid'
                                    }
                                },
                                axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                                    show: true,
                                    formatter: function(v){
                                        switch (v+''){
                                            case '20': return '低';
                                            case '60': return '中';
                                            case '90': return '高';
//                                        	testGauge();
                                            default: return '';
                                        }
                                    },
                                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                        color: '#333'
                                    }
                                },
                                splitLine: {           // 分隔线
                                    show: true,        // 默认显示，属性show控制显示与否
                                    length :30,         // 属性length控制线长
                                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                        color: '#eee',
                                        width: 2,
                                        type: 'solid'
                                    }
                                },
                                pointer : {
                                    length : '80%',
                                    width : 8,
                                    color : 'auto'
                                },
                                title : {
                                    show : true,
                                    offsetCenter: ['-65%', -10],       // x, y，单位px
                                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                        color: '#333',
                                        fontSize : 15
                                    }
                                },
                                detail : {
                                    show : false,
                                    backgroundColor: 'rgba(0,0,0,0)',
                                    borderWidth: 0,
                                    borderColor: '#ccc',
                                    width: 100,
                                    height: 40,
                                    offsetCenter: ['-60%', 10],       // x, y，单位px
                                    formatter:'{value}%',
                                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                        color: 'auto',
                                        fontSize : 30
                                    }
                                },
                                data:[{value: level, name: '仪表盘'}]
                            }
                        ]
                    };
                    //图形展示
                    //myChart.setOption(option);
//                    clearInterval(timeTicket);
//                    var timeTicket = setInterval(function (){
//                        option.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
                        myChart.setOption(option, true);
//                    },2000)
                    window.onresize = myChart.resize;
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
            myChartPie = ec.init(document.getElementById('ldgs'+ index));
            //后台获取数据
            $.ajax({
            	type : "post",
            	url:"getPieData.html?orderId="+$('#orderId').val()+"&type="+$('#type').val()+"&group_flag="+$('#group_flag').val()+"&websoc="+$('#websoc').val()+"&orderAssetId="+$('#orderAssetId'+ index).val(),
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
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
//	                   	label[i]=p['label'];
//	                   	value[i]={'name':p['label'],'value':p['value']};
                    });
                    myChartPie.setOption({//图形
                        tooltip : {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
//                        legend: {
//                            orient : 'vertical',
//                            x : 'right',
////                            data:['低','中','高']
//                            data:testX()
//                        },
//                        color:['lightgreen', 'orange','red'],
                        color:colorData,
                        toolbox: {
                            show : true,
                            feature : {
                                mark : true,
//                                magicType : {
//                                    show: true, 
//                                    type: ['pie', 'funnel'],
//                                    option: {
//                                        funnel: {
//                                            x: '25%',
//                                            width: '50%',
//                                            funnelAlign: 'left',
//                                            max: 1548
//                                        }
//                                    }
//                                },
                                restore : true,
                                saveAsImage : true
                            }
                        },
                        calculable : false,
                        series : [
                            {
                                name:'漏洞个数',
                                type:'pie',
                                radius : '55%',
                                center: ['50%', '60%'],
//                                data:[
//                                    {value:234, name:'低'},
//                                    {value:135, name:'中'},
//                                    {value:1548, name:'高'}
//                                ]
                                data:testY()
                            }
                        ]
                    },true);//图形展示
                    window.onresize = myChartPie.resize;
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
    
    
    // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        function (ec) {//回调函数
            //--- 趋势图 ---
        	if($('#type').val()==1){
        		myChartLine = ec.init(document.getElementById('aqpf'+index));
        	}
          //后台获取数据
            $.ajax({
            	type : "post",
            	url:"getLineData.html?orderId="+$('#orderId').val()+"&type="+$('#type').val()+"&group_flag="+$('#group_flag').val()+"&websoc="+$('#websoc').val()+"&orderAssetId="+$('#orderAssetId'+index).val(),
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
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
//	                   	label[i]=temp;
	                   	lineData[i]=p['value'];
	                   	lineData2[i]=p['value2'];
	                   	lineData3[i]=p['value3'];
	                   	time[i]=p['time'];
//	                   	label[i]=p['label'];
//	                   	value[i]={'name':p['label'],'value':p['value']};
                    });
                    
                    myChartLine.setOption({//图形
                    	tooltip : {
                            trigger: 'axis'
                        },
                        legend: {
                            data:['低危漏洞个数','中危漏洞个数','高危漏洞个数']
//                        	data:testX()
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                mark : true,
                                //magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                                restore : true,
                                saveAsImage : true
                            }
                        },
                        calculable : true,
                        xAxis : [
                            {
                                type : 'category',
                                boundaryGap : false,
//                                data : ['周一','周二','周三','周四','周五','周六','周日']
                                data : testLineX()
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value'
                            }
                        ],
                        series : [
                            {
                                name:'低危漏洞个数',
                                type:'line',
//                                stack: '总量',
                                smooth: true,
                                itemStyle: {normal: {areaStyle: {type: 'default'}}},
//                                data:[120, 132, 101, 134, 90, 230, 210]
                                data: testLineData()
                            },
                            {
                                name:'中危漏洞个数',
                                type:'line',
//                                stack: '总量',
                                smooth: true,
                                itemStyle: {normal: {areaStyle: {type: 'default'}}},
//                                data:[220, 182, 191, 234, 290, 330, 310]
                                data: testLineData2()
                            },
                            {
                                name:'高危漏洞个数',
                                type:'line',
//                                stack: '总量',
                                smooth: true,
                                itemStyle: {normal: {areaStyle: {type: 'default'}}},
//                                data:[150, 232, 201, 154, 190, 330, 410]
                                data: testLineData3()
                            }
                        ]
                    },true);//图形展示
//                    window.onresize = myChartLine.resize;
                }//ajax执行后台
            }); 
        }
    );
    
    
    // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/bar'
        ],
        function (ec) {//回调函数
            //--- 柱形图 ---
        	myChartBar = ec.init(document.getElementById('aqpf1'+index));
          //后台获取数据
            $.ajax({
            	type: "post",
            	url:"getBarData.html?orderId="+$('#orderId').val()+"&type="+$('#type').val()+"&group_flag="+$('#group_flag').val()+"&websoc="+$('#websoc').val()+"&orderAssetId="+$('#orderAssetId'+index).val(),
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                    $.each(data,function(i,p){
	                   	lineData[i]=p['name'];
                    });
                    
                    myChartBar.setOption({//图形
                    	title: {
                            text: '弱点类型统计图'
                        },
                    	tooltip : {
//                            trigger: 'axis',
                            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            }
                        },
                        legend: {
                            y : 'bottom',
                            data:testLineData()
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
                                data : ['']
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
	                       	 
	                    	 for( var i=0;i < data.length;i++){
	                    		 var num=[];
	                    		 num[0]=data[i].num;
	                        	 var item={
		                        	 name:data[i].name,
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
//                        	[
//                            {
//                                name:'直接访问',
//                                type:'bar',
//                                itemStyle: {
//                                     normal: {
//                                         label: {
//                                             show: true,
//                                             textStyle: {
//                                                 color: '#800080'
//                                             }
//                                         }
//                                     }
//                                },
//                                data:[320]
//                            },
//                            {
//                                name:'邮件营销',
//                                type:'bar',
//                                data:[120]
//                            },
//                            {
//                                name:'联盟广告',
//                                type:'bar',
//                                data:[220]
//                            },
//                            {
//                                name:'视频广告',
//                                type:'bar',
//                                data:[150]
//                            },
//                            {
//                                name:'百度',
//                                type:'bar',
//                                data:[620]
//                            },
//                            {
//                                name:'谷歌',
//                                type:'bar',
//                                data:[120]
//                            },
//                            {
//                                name:'必应',
//                                type:'bar',
//                                data:[60]
//                            },
//                            {
//                                name:'其他',
//                                type:'bar',
//                                data:[62]
//                            }
//                        ]
                    },true);//图形展示
//                    window.onresize = myChartBar.resize;
                }//ajax执行后台
            }); 
        }
    );
    
    
    
});

function exportImg(){
    var dataPie = myChartPie.getDataURL("png");   
    var dataBar = myChartBar.getDataURL("png");
    if(myChartLine!=null){
    	var dataLine = myChartLine.getDataURL("png"); 
    	$("#imgLine").val(dataLine);
    }
    $("#imgPie").val(dataPie);
    $("#imgBar").val(dataBar);
	$("#exportForm").submit();
}
