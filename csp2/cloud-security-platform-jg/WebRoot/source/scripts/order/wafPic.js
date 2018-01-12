var isLoaded = false;
var secId = 0;
var myChartPieLevel = null;
var myChartPieEvent = null;
var myChartBar = null;
var num = 8;

$(function(){
	pielevel();  	 
	event();
	websec();
	
	setInterval(function(){
		if(isLoaded) websec();
	},10000);
	

});
	
	
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
function pielevel(){
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
            	data:{
            		"orderId":$('#orderId').val()
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
                            text: '最近事件风险分布图'
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
    
function event(){
    // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line',
            'echarts/chart/pie'
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
        	//---饼图---
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
            	type: "post",
            	data:{
            		"orderId":$('#orderId').val()
            	},
            	url:"getEventBarData.html",
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                	myChartBar.hideLoading();  
                    myChartBar.setOption({//图形
                    	title: {
                            text: '最近事件类型统计图'
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
                    
               	
                    	myChartPieEvent.hideLoading();  
                        myChartPieEvent.setOption({//图形
                        	title: {
                                text: '最近事件类型分布图'
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
//                            color:colorData,
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
    
//实时攻击数据
function websec(){
	$.ajax({		
		type:"post",		
		data:{
			"orderId":$('#orderId').val(),
			"secId":secId
		},
		url:"getWebsec.html",
		dataType:"json",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		beforeSend:function(){
			//if()
			isLoaded = false;
		},
		success:function(data){
			//$(".nodata_time").hide();
    		//$(".timeTable").show();
        	var htmlStr = "";
        	var level;
        	for(var i=data.websecList.length-1;i>=0;i--){
        		if(data.websecList[i].logId>secId){
        			secId=data.websecList[i].logId;
        			//console.log(secId);
        		}
        		switch(data.websecList[i].alertlevel){
        		case "HIGH":
        			level="高风险";
        			break;
        		case "MEDIUM":
        			level="中风险";
        			break;
        		case "LOW":
        			level="低风险";
        			break;
        		default:
        			level="低风险";
        		}
        		//htmlStr = '<tr><td width="30%">'+data.websecList[i].statTime+'</td>'+'<td width="30%">'+data.websecList[i].eventType+'</td>'+
        		htmlStr = '<tr><td width="30%">'+data.websecList[i].statTime+'</td>'+'<td width="30%" class="data_table_cont"><a href="javascript:void(0)" onclick="websecDetail('+data.websecList[i].logId+')">'+data.websecList[i].eventType+'</a></td>'+
        		'<td width="25%">'+data.websecList[i].dstIp+'</td>'+'<td width="15%">'+level+'</td></tr>';
        		if(!num){
            		$("#websec tr:last").remove();
            		num++;
            	}   
        		$("#websec").prepend(htmlStr);
        		num--;
            	        	
        	}     	
        	//websec();
        	console.log("true");

		},
		complete:function(){
			isLoaded = true;
		},
		error:function(){
			//websec();
			console.log("error");
		}
		
	});
}

function websecDetail(logId){
    $.ajax({
      type: "POST",
      url: "warningWafDetail.html",
      data: {"logId":logId},
      dataType:"json",
      success: function(data){
     		$("#dstIp").html(data.dstIp);
     		$("#srcIp").html(data.srcIp);
     		$("#srcPort").html(data.srcPort);
     		$("#alertlevel").html(data.alertlevel);
     		$("#eventType").html(data.eventType);
     		$("#statTime").html(data.statTime);
     		$("#alertinfo").html(data.alertinfo);
     		$("#protocolType").html(data.protocolType);
     		
     		$(".mark,.data_tanc").show();
     	}
    });
}

