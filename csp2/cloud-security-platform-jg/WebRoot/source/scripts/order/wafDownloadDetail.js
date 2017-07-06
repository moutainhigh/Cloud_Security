var myChartPie = null;
var myChartBar = null;
var myChartLine = null;

//var dataPie = null;  
//var dataBar = null;
//var dataLine = null;
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
var getCharsData=function(repeat){
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
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        function (ec) {//回调函数
            //--- 仪表盘 ---
            var myChart = ec.init(document.getElementById('aqfx'+repeat));
            myChart.showLoading({
            	  text: 'loading...',
            	  effect : 'spin',
            	  textStyle : {
            	        fontSize : 20,
            	        color:'#000'
            	    },
            	    effectOption :{
            	    	  backgroundColor:'#fff'
            	   }

	        	});
            //后台获取数据
            $.ajax({
            	url:"getGaugeData.html?orderId="+$('#orderId').val()+"&type="+$('#type').val()+"&group_flag="+$('#group_flag').val()+"&websoc="+$('#websoc').val()+"&orderAssetId="+$('#orderAssetId'+repeat).val(),
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
                    myChart.hideLoading();    
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
            myChartPie = ec.init(document.getElementById('ldgs'+ repeat));
            myChartPie.showLoading({
          	  text: 'loading...',
          	  effect : 'spin',
          	  textStyle : {
          	        fontSize : 20,
          	        color:'#000'
          	    },
          	    effectOption :{
          	    	  backgroundColor:'#fff'
          	   }

	        	});
            //后台获取数据
            $.ajax({
            	type : "post",
            	url:"getPieData.html?orderId="+$('#orderId').val()+"&type="+$('#type').val()+"&group_flag="+$('#group_flag').val()+"&websoc="+$('#websoc').val()+"&orderAssetId="+$('#orderAssetId'+repeat).val(),
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                	value = [];
                	label = [];
                   	colorData = [];
                    $.each(data,function(i,p){
	                   	
	                   	var temp = null;
	                   	if(p['label']==-1){
	                   		temp="信息";
	                   	}
	                   	if(p['label']==0){
	                   		temp="低";
	                   	}
	                   	if(p['label']==1){
	                   		temp="中";
	                   	}
	                   	if(p['label']==2){
	                   		temp="高";
	                   	}
	                   	if(p['label']==3){
	                   		temp="紧急";
	                   	}
	                   	label[i]=temp+p['ratio'];
	                   	value[i]={'name':temp,'value':p['value']};
	                   	colorData[i]=p['color'];
//	                   	label[i]=p['label'];
//	                   	value[i]={'name':p['label'],'value':p['value']};
                    });
                    myChartPie.hideLoading();    
                    myChartPie.setOption({//图形
                        tooltip : {
                            trigger: 'item',
//                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                            formatter: "{a} : {c}"
                            
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
                                data:testY(),
                                itemStyle:{ 
    	                            normal:{ 
    	                                label:{ 
    	                                   show: true, 
    	                                   formatter: '{b} : ({d}%)' 
    	                                }, 
    	                                labelLine :{show:true}
    	                            } 
    	                        } 
                                
                            }
                        ]
                    },true);//图形展示
                    window.onresize = myChartPie.resize;
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
            //--- 趋势图 ---
        	if($('#type').val()==1){
        		myChartLine = ec.init(document.getElementById('aqpf' + repeat));
        		myChartLine.showLoading({
              	  text: 'loading...',
              	  effect : 'spin',
              	  textStyle : {
              	        fontSize : 20,
              	        color:'#000'
              	    },
              	    effectOption :{
              	    	  backgroundColor:'#fff'
              	   }

  	        	});
        	}
          //后台获取数据
            $.ajax({
            	type : "post",
            	url:"getLineData.html?orderId="+$('#orderId').val()+"&type="+$('#type').val()+"&group_flag="+$('#group_flag').val()+"&websoc="+$('#websoc').val()+"&orderAssetId="+$('#orderAssetId'+repeat).val(),
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                	lineData0=[];
                	lineData=[];
                   	lineData2=[];
                   	lineData3=[];
                   	lineData4=[];
                   	time=[];
                    $.each(data,function(i,p){
//	                   	var temp = null;
//	                   	if(p['label']==0){
//	                   		temp="低";
//	                   	}
//	                   	if(p['label']==1){
//	                   		temp="中";
//	                   	}
//	                   	if(p['label']==2){
//	                   		temp="高";
//	                   	}
                    	lineData0[i]=p['value0'];
	                   	lineData[i]=p['value'];
	                   	lineData2[i]=p['value2'];
	                   	lineData3[i]=p['value3'];
	                   	lineData4[i]=p['value4'];
	                   	time[i]=p['time'];
                    });
                    
                    myChartLine.hideLoading();    
                    myChartLine.setOption({//图形
                    	tooltip : {
                            trigger: 'axis'
                        },
                        legend: {
                            data:['信息漏洞个数','低危漏洞个数','中危漏洞个数','高危漏洞个数','紧急漏洞个数']
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
                        calculable : false,
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
							    name:'信息漏洞个数',
							    type:'line',
							//    stack: '总量',
							    smooth: true,
							    itemStyle: {normal: {areaStyle: {type: 'default'}}},
							//    data:[120, 132, 101, 134, 90, 230, 210]
							    data: testLineData0()
							},
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
                            },
                            {
                                name:'紧急漏洞个数',
                                type:'line',
//                                stack: '总量',
                                smooth: true,
                                itemStyle: {normal: {areaStyle: {type: 'default'}}},
//                                data:[120, 132, 101, 134, 90, 230, 210]
                                data: testLineData4()
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
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        function (ec) {//回调函数
            //--- 柱形图 ---
        	myChartBar = ec.init(document.getElementById('aqpf1'+ repeat));
        	myChartBar.showLoading({
          	  text: 'loading...',
          	  effect : 'spin',
          	  textStyle : {
          	        fontSize : 20,
          	        color:'#000'
          	    },
          	    effectOption :{
          	    	  backgroundColor:'#fff'
          	   }

	        	});
          //后台获取数据
            $.ajax({
            	type: "post",
            	url:"getBarData.html?orderId="+$('#orderId').val()+"&type="+$('#type').val()+"&group_flag="+$('#group_flag').val()+"&websoc="+$('#websoc').val()+"&orderAssetId="+$('#orderAssetId'+repeat).val(),
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                	lineData = [];
                    $.each(data,function(i,p){
	                   	lineData[i]=p['name'];
                    });
                    
                    myChartBar.hideLoading();    
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

                    },true);//图形展示
                    window.onresize = myChartBar.resize;
                }//ajax执行后台
            }); 
        }
    );
    
    
 
}

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

function testLineData0(){
	return lineData0;
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

function testLineData4(){
	return lineData4;
}

function exportImgWAF(){
	//alert("1234");
    //var dataPie = myChartPie.getDataURL("png");   
    //var dataBar = myChartBar.getDataURL("png");
    //if(myChartLine!=null){
    //	var dataLine = myChartLine.getDataURL("png"); 
    //	$("#imgLine").val(dataLine);
    //}
    //$("#imgPie").val(dataPie);
    //$("#imgBar").val(dataBar);
	
	$("#exportForm").submit();
}