var myChartPieLevel = null;
var myChartPieEvent = null;
var myChartBar = null;
var ontimeLine = null;
var sourceIp = null;
var sourceArea = null;
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
	            'echarts/chart/pie'
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
    function barEvent(startDate,timeUnit){ 
    	require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line'
	        ],
	        function (ec) {//回调函数
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
	            	type: "post",
	            	async:false,
	            	url:"getEventBarData.html",
	            	data : {
	            		"orderId":$('#orderId').val(),
	            		"isHis":$('#isHis').val(),
	            		"startDate":startDate,
	            		"timeUnit":timeUnit
	            	},
	            	dataType:"json",
	//                contentType: "application/x-www-form-urlencoded; charset=utf-8",
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
    }
    
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
	            	async:false,
	            	url:"getOntimeLineData.html",
	            	data : {
	            		"orderId":$('#orderId').val(),
	            		"isHis":$('#isHis').val(),
	            		"startDate":startDate,
	            		"timeUnit":timeUnit
	            	},
	            	dataType:"json",
	//                contentType: "application/x-www-form-urlencoded; charset=utf-8",
	                success:function(data){
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
	                }//ajax执行后台
	            }); 
	        }
	    );
    }
    
    //攻击源IP统计图
    function sourceIpEvent(startDate,timeUnit){ 
	    require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line'
	        ],
	        function (ec) {//回调函数
	        	sourceIp = ec.init(document.getElementById('sourceIP'));
	        	sourceIp.showLoading({
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
	            	//async:false,
	            	url:"getSourceIpData.html",
	            	data : {
	            		"orderId":$('#orderId').val(),
	            		"isHis":$('#isHis').val(),
	            		"startDate":startDate,
	            		"timeUnit":timeUnit
	            	},
	            	dataType:"json",
	//                contentType: "application/x-www-form-urlencoded; charset=utf-8",
	                success:function(data){
	                	sourceIp.hideLoading();  
	                	sourceIp.setOption({
	                		title: {
	                            text: '攻击源IP统计图'
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
			      		        //trigger: 'axis',
			      		        formatter: "{b} : {c}次"
			      		    },
			      		    yAxis : [
			      		        {
			      		            type : 'value',
			      		            name : '攻击次数',
			      		            axisLabel : {
			      		                formatter: '{value}'
			      		            }
			      		        }
			      		    ],
			      		    xAxis : [
			      		        {
			      		            type : 'category',
			      		            
			      		            axisLabel : {
			      		            	interval:'auto',
			      		                formatter: '{value} '
			      		            },
			      		            boundaryGap : true,
			      		            data : data.name
			      		        }
			      		    ],
			      		    series : [
			      		        {
			      		            name:'IP攻击次数',
			      		            type:'bar',
			      		            itemStyle: {
			      		                normal: {
			      		                	label: {
			      	                            show: true,
			      	                            position: 'top',
			      	                             textStyle: {
			      	                                color: '#800080'
			      	                            }
			      	                        }
			      		                }
			      		            },
			      		            barWidth:70,
			      		            data:data.count
			      		        }
			      		    ]
			        	},true);//图形展示
	                    window.onresize = sourceIp.resize;
	                }//ajax执行后台
	            }); 
	        }
	    );
    }
    
    //攻击源区域分布图
    function sourceAreaEvent(startDate,timeUnit){ 
	    require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line'
	        ],
	        function (ec) {//回调函数
	        	sourceArea = ec.init(document.getElementById('sourceArea'));
	        	sourceArea.showLoading({
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
	            	//async:false,
	            	url:"getSourceAreaData.html",
	            	data : {
	            		"orderId":$('#orderId').val(),
	            		"isHis":$('#isHis').val(),
	            		"startDate":startDate,
	            		"timeUnit":timeUnit
	            	},
	            	dataType:"json",
	//                contentType: "application/x-www-form-urlencoded; charset=utf-8",
	                success:function(data){
	                	sourceArea.hideLoading();  
	                	sourceArea.setOption({
	                		title: {
	                            text: '攻击源区域分布图'
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
				      		  		restore : true,
				      		  		saveAsImage : true
				      		  	}	
			      		    },
			      		    calculable : false,
			      		    tooltip : {
			      		    	axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		                                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		                        },
			      		        //trigger: 'axis',
			      		        formatter: "{a} : {c}次"
			      		    },
			      		    yAxis : [
			      		        {
			      		            type : 'value',			      		            
			      		        }
			      		    ],
			      		    xAxis : [
			      		        {
			      		            type : 'category',
			      		            
			      		            axisLabel : {
			      		            	interval:'auto',
			      		                //formatter: '{value} '
			      		            },
			      		            //boundaryGap : true,
			      		            data : ['']
			      		        }
			      		    ],
			      		   
		                    series :
		                       function(){
			                       var serie=[];
			                       	 
			                       for( var i=0;i < data.name.length;i++){
			                    	   var num=[];
			                    	   num[0]=data.count[i];
			                    	   var item={
			                    			 name:data.name[i],
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
	                    window.onresize = sourceArea.resize;
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
    
/*
function exportImgWAF(){
    var dataPieLevel = myChartPieLevel.getDataURL("png");  
    var dataPieEvent = myChartPieEvent.getDataURL("png");
	var dataBar = myChartBar.getDataURL("png"); 
    var ontime = ontimeLine.getDataURL("png");  
    var mySourceIp = sourceIp.getDataURL("png");
    alert(mySourceIp);
    //var mySourceArea = sourceArea.getDataURL("png");
	
	$("#imgPieLevel").val(dataPieLevel);
    $("#imgPieEvent").val(dataPieEvent);
    $("#imgBar").val(dataBar);
	$("#imgOntimeLine").val(ontime);
	$("#imgSourceIp").val(mySourceIp);
	//$("#imgSourceArea").val(mySourceArea);
	$("#exportWafForm").submit();
}
*/
    
$(function(){
	$("#exportWAF").click(function(){
		//var dataPieLevel = myChartPieLevel.getDataURL("png");  
		var dataPieLevel = myChartPieLevel.getDataURL("png");
	    var dataPieEvent = myChartPieEvent.getDataURL("png");
		var dataBar = myChartBar.getDataURL("png"); 
	    var ontime = ontimeLine.getDataURL("png");  
	    var mySourceIp = sourceIp.getDataURL("png");
	  //var mySourceArea = sourceArea.getDataURL("png");
	    
	    var imgPieLevel = dataPieLevel.substring(22);
	    var imgPieEvent = dataPieEvent;
	    var imgBar = dataBar; 
	    var imgOntime = ontime;  
	    var imgSourceIp = mySourceIp;
	    
	    var list={}; //声明为一个对象,这是一个数组对象  
	    
	    var img1= new Object();  
	    img1.name = dataPieLevel;    
	    list[0] = img1;  	      
	    var img2= new Object();  
	    img2.name = dataPieEvent;  
	    list[1] = img2; 
	    var img3= new Object();  
	    img3.name = dataBar;  
	    list[2] = img3;  
	    var img4= new Object();  
	    img4.name = ontime;  
	    list[3] = img4;  
	    var img5= new Object();  
	    img5.name = mySourceIp;  
	    list[4] = img5;  
	    
	    var orderId = $("#orderId").val();
	    $.ajax({
	    	type: "POST",
	    	//type: "GET",
	    	contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		    async: false, 
		    url: "exportWAF.html",
		    /*
		    String orderId = request.getParameter("orderId");
            String group_flag = request.getParameter("group_flag");
            String orderAssetId = request.getParameter("orderAssetId");
            String imgPieLevel = request.getParameter("imgPieLevel");
            String imgBar = request.getParameter("imgBar");
            String imgPieEvent = request.getParameter("imgPieEvent");
            String imgSourceIp = request.getParameter("imgSourceIp");
            
            String levelTotal = request.getParameter("level");
            String levelhigh = request.getParameter("levelhigh");
            String levelmid = request.getParameter("levelmid");
            String levellow = request.getParameter("levellow");
            String listtimeString = request.getParameter("resultList");
            
            String timeCountTotal = request.getParameter("timeCountTotal"); // time
            String timeStrBase64 = request.getParameter("resultListTime");
		     */
		    data:{
		    	//"orderId":orderId,
		    	//"imgPieLevel":dataPieLevel,
		        //"imgPieEvent":dataPieEvent
		    	//"imgPieLevel":imgPieLevel,
		    	//"imgPieEvent":dataPieEvent,
		    	//"imgBar":imgBar,
		    	//"imgOntimeLine":imgOntime, 
		    	//"imgSourceIp":imgSourceIp
		    	'img':JSON.stringify(list)
		        },
		    dataType: "json",		   
		    success: function(data) {		    	
		    	alert("chenggong");
		     },
		    error: function(data){ 
		    	alert("shibai");
		    	//window.location.href = "loginUI.html"; 
		    	 
		     } 
	    });
		//alert("abc");
	});
});



