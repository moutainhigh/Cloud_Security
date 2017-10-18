var myChartPieLevel = null;
var myChartPieEvent = null;
var myChartBar = null;
var ontimeLine = null;
//$(function(){	
	
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
    function pielevel(startDate,timeUnit){
		require(
	        [
	            'echarts',
	            'echarts/chart/pie'
	        ],
	        function (ec) {//回调函数
	            //--- 饼图 ---
	            myChartPieLevel = ec.init(document.getElementById('levelPie'));
	            myChartPieLevel.showLoading({
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
	            	data : {
	            		"orderId":$('#orderId').val(),
	            		"isHis":$('#isHis').val(),
	            		"startDate":startDate,
	            		"timeUnit":timeUnit
	            	},
	            	url:"getLevelPieData.html",
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
		                   	label[i]=temp;
		                   	value[i]={'name':temp,'value':p['value']};
		                   	colorData[i]=p['color'];
	                    });
	                	myChartPieLevel.hideLoading();  
	                    myChartPieLevel.setOption({//图形
	                    	title: {
	                            text: '事件风险分布图'
	                        },
	                        tooltip : {
	                            trigger: 'item',
	                            formatter: "{a} <br/>{b} : {c} ({d}%)"
	                        },
	                        legend: {
	                            show:false,
	//					    	orient: 'vertical',
						        x: 'left',
	//					        y:'top',
						        y: '35',
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
	                                data:testY(),
	                                itemStyle:{ 
			                            normal:{ 
			                                label:{ 
			                                   show: true, 
			                                   formatter: '{b} : {d}%' 
			                                }, 
			                                labelLine :{show:true}
			                            } 
			                        }
	                            }
	                        ]
	                    },true);//图形展示
	                    window.onresize = myChartPieLevel.resize;
	                }//ajax执行后台
	            }); 
	        }
	    );
    }
    
    
 // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    function pieEvent(startDate,timeUnit){
	    require(
	        [
	            'echarts',
	            'echarts/chart/pie',
	            'echarts/chart/bar',
	        ],
	        function (ec) {//回调函数
	            //--- 饼图 ---
	            myChartPieEvent = ec.init(document.getElementById('eventPie'));
	            myChartPieEvent.showLoading({
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
	          //--- 柱形图 ---
	        	myChartBar = ec.init(document.getElementById('eventBar'));
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
	            	type : "post",
	            	url:"getEventPieData.html",
	            	data : {
	            		"orderId":$('#orderId').val(),
	            		"isHis":$('#isHis').val(),
	            		"startDate":startDate,
	            		"timeUnit":timeUnit
	            	},
	            	dataType:"json",
	                contentType: "application/x-www-form-urlencoded; charset=utf-8",
	                success:function(data){
	                	
	                	myChartBar.hideLoading();  
	                    myChartBar.setOption({//图形
	                    	title: {
	                            text: '事件类型统计图'
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
		                       	 if(data.json==null||data.json==''||data.json.length==0){
		                       		var item={
				                        	 name:'',
				                        	 type:'bar',
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
				                        	 data:['']
			                        	 };
			                        	 serie.push(item);
		                       		 return serie;
		                       	 }
		                       	 
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
	                    
	                	myChartPieEvent.hideLoading();  
	                    myChartPieEvent.setOption({//图形
	                    	title: {
	                            text: '事件类型分布图'
	                        },
	                        tooltip : {
	                            trigger: 'item',
	                            formatter: "{a} <br/>{b} : {c} ({d}%)"
	                        },
	                        legend: {
	                            show:false,
						        y : 'bottom',
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
	                                center: ['50%', '50%'],
	                                data:data.json,
	                                itemStyle:{ 
			                            normal:{ 
			                                label:{ 
			                                   show: true, 
			                                   formatter: '{d}%' 
			                                }, 
			                                labelLine :{show:true}
			                            } 
			                        }
	                            }
	                        ]
	                    },true);//图形展示
	                    window.onresize = myChartPieEvent.resize;
	                    
	                    
	                    
	                }//ajax执行后台
	            }); 
	        }
	    );
    }
    
    
    // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
//    function barEvent(startDate,timeUnit){ 
//    	require(
//	        [
//	            'echarts',
//	            'echarts/chart/bar',
//	            'echarts/chart/line'
//	        ],
//	        function (ec) {//回调函数
//	            //--- 柱形图 ---
//	        	myChartBar = ec.init(document.getElementById('eventBar'));
//	        	myChartBar.showLoading({
//	          	  text: 'loading...',
//	          	  effect : 'spin',
//	          	  textStyle : {
//	          	        fontSize : 20,
//	          	        color:'#000'
//	          	    },
//	          	    effectOption :{
//	          	    	  backgroundColor:'#fff'
//	          	   }
//	
//		        });
//	          //后台获取数据
//	            $.ajax({
//	            	type: "post",
//	            	url:"getEventBarData.html",
//	            	data : {
//	            		"orderId":$('#orderId').val(),
//	            		"isHis":$('#isHis').val(),
//	            		"startDate":startDate,
//	            		"timeUnit":timeUnit
//	            	},
//	            	dataType:"json",
//	//                contentType: "application/x-www-form-urlencoded; charset=utf-8",
//	                success:function(data){
//	                	myChartBar.hideLoading();  
//	                    myChartBar.setOption({//图形
//	                    	title: {
//	                            text: '事件发生分布图'
//	                        },
//	                    	tooltip : {
//	//                            trigger: 'axis',
//	                            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
//	                                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
//	                            }
//	                        },
//	                        legend: {
//	                            y : 'bottom',
//	                            data:data.name
//	                        },
//	                        toolbox: {
//	                            show : true,
//	                            orient: 'vertical',
//	                            x: 'right',
//	                            y: 'center',
//		                        feature : {
//		                            mark : true,
//		                            //magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
//		                            restore : true,
//		                            saveAsImage : true
//		                        }
//	                        },
//	                        calculable : false,
//	                        xAxis : [
//	                            {
//	                                type : 'category',
//	                                data : [''],
//	                                axisLabel:{
//	           		                 //X轴刻度配置
//	           		                 interval:'auto' //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
//	           		                }
//	
//	                            }
//	                        ],
//	                        yAxis : [
//	                            {
//	                                type : 'value'
//	                            }
//	                        ],
//	                        series :
//	                        	function(){
//		                       	 var serie=[];
//		                       	 
//		                    	 for( var i=0;i < data.json.length;i++){
//		                    		 var num=[];
//		                    		 num[0]=data.json[i].value;
//		                        	 var item={
//			                        	 name:data.json[i].name,
//			                        	 type:'bar',
//	//		                        	 barWidth : 25,
//			                        	 itemStyle: {
//		                                     normal: {
//		                                         label: {
//		                                             show: true,
//		                                             textStyle: {
//		                                                 color: '#800080'
//		                                             }
//		                                         }
//		                                     }
//			                        	 },
//			                        	 data:num
//		                        	 };
//		                        	 serie.push(item);
//		                    	 };
//		                    	 return serie;
//		                    	 }()
//	
//	                    },true);//图形展示
//	                    window.onresize = myChartBar.resize;
//	                }//ajax执行后台
//	            }); 
//	        }
//	    );
//    }
    
    // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    function lineEvent(startDate,timeUnit){ 
	    require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line'
	        ],
	        function (ec) {//回调函数
	            //--- 折线图 ---
	        	ontimeLine = ec.init(document.getElementById('ontimeLine'));
	        	ontimeLine.showLoading({
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
	            	url:"getOntimeLineData.html",
	            	data : {
	            		"orderId":$('#orderId').val(),
	            		"isHis":$('#isHis').val(),
	            		"startDate":startDate,
	            		"timeUnit":timeUnit
	            	},
	            	dataType:"json",
	            	//async:false,
	//                contentType: "application/x-www-form-urlencoded; charset=utf-8",
	                success:function(data){
	                	
	                	//更新折线图
	                	ontimeLine.hideLoading();  
	                	ontimeLine.setOption({
			      		  legend: {
			      		        data:['告警时段统计图']
			      		    },
			      		    toolbox: {
			      		        show : true,
				      		  	feature : {
				      		  		mark : true,
				      		  		restore : true,
				      		  		saveAsImage : true
				      		  	}	
			      		    },
			      		    calculable : true,
			      		    tooltip : {
			      		        trigger: 'axis',
			      		        formatter: "{b} : {c}个"
			      		    },
			      		    yAxis : [
			      		        {
			      		            type : 'value',
			      		            axisLabel : {
			      		                formatter: '{value}'
			      		            }
			      		        }
			      		    ],
			      		    xAxis : [
			      		        {
			      		            type : 'category',
			      		            axisLine : {onZero: false},
			      		            axisLabel : {
			      		                formatter: '{value} '
			      		            },
			      		            boundaryGap : false,
			      		            data : data.name
			      		        }
			      		    ],
			      		    series : [
			      		        {
			      		            name:'告警时段统计图',
			      		            type:'line',
			      		            smooth:true,
			      		            itemStyle: {
			      		                normal: {
			      		                    lineStyle: {
			      		                        shadowColor : 'rgba(0,0,0,0.4)'
			      		                    }
			      		                }
			      		            },
			      		            data:data.count
			      		        }
			      		    ]
			        	},true);//图形展示
	                    window.onresize = ontimeLine.resize;
	                    
	                    //更新表格	                	
	                	var resultList = data.resultList;
	                	var htmlStr = "";
	                	for(var i=0;i<resultList.length;i++){
	                		htmlStr += '<tr><td width="30%">'+resultList[i].time+'</td>'+
	                		'<td width="30%">'+resultList[i].count+'</td></tr>';
	                	}
	                	$("#timeList").append(htmlStr);
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
    
    function testLineData(){
    	return lineData;
    }
    
    function testLineData2(){
    	return lineData2;
    }
    
    function testLineData3(){
    	return lineData3;
    }
    
    
//});
    

function exportWaf(){
 if(myChartPieLevel!=null){
    	 var dataPieLevel = myChartPieLevel.getDataURL("png"); 
    		$("#imgPieLevel").val(dataPieLevel);
    }
	 if(myChartPieEvent!=null){
		 var dataPieEvent = myChartPieEvent.getDataURL("png");
		 $("#imgPieEvent").val(dataPieEvent);
	 }
   
   if(myChartBar!=null){
		var dataBar = myChartBar.getDataURL("png");  
		  $("#imgBar").val(dataBar);
 }
   if(ontimeLine!=null){
		 var ontime = ontimeLine.getDataURL("png");  
		  $("#imgOntimeLine").val(ontime);
	}
  $("#exportWafForm").submit();
}

//攻击源
function updateAttackSourceTable(startDate,timeUnit){
	$.ajax({
    	type : "post",
    	data : {
    		"orderId":$('#orderId').val(),
    		"isHis":$('#isHis').val(),
    		"startDate":startDate,
    		"timeUnit":timeUnit
    	},
    	url:"getAttackSourceData.html",
        dataType:"json",
        success:function(data){
        	$("#websecNum").val(data.websecNum);
        	
        	var websecList = data.websecList;
        	htmlStr = "";
        	for(var i=0;i<websecList.length;i++){
        		htmlStr += '<tr><td width="30%">'+websecList[i].dstIp+'</td>'+
        		'<td width="30%">'+websecList[i].count+'</td></tr>';
        	}
        	$("#websecItem").append(htmlStr);
        	
        	/*if(data.websecNum==0){
        		$(".nodata").show();
        		$(".websecTable").hide();
        	}else{
        		$(".nodata").hide();
        		$(".websecTable").show();
        	}*/
        }
     });
}



