$(function(){
	//为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
    require.config({
        paths:{ 
            echarts:'../echarts/echarts',
            'echarts/chart/line': '../echarts/echarts-map',
        }
    });
	
    // 定义数组
    var label = [];
    var value = [];
    var valueGauge = [];
    var time = [];
    var lineData = [];
    var lineData2 = [];
    var lineData3 = [];
    var high = null;
    
    // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    function testLineData(){
    	return lineData;
    }
    
    function testLineData2(){
    	return lineData2;
    }
    function testLineX(){
    	return time;
    }
    
    // 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {//回调函数
         var myChart = ec.init(document.getElementById('pic'));
          //后台获取数据
            $.ajax({
            	type : "post",
            	url:"getDataUsable.html?orderId="+$('#orderId').val(),
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                    $.each(data,function(i,p){
	                   	//lineData2[i]=p['count'];
	                   	time[i]=p['time'];
                    });
                    myChart.setOption({//图形
                        calculable : true,
                        xAxis : [
                                 {
                                     type : 'value',
                                     axisLabel : {
                                         formatter: '{value}'
                                     }
//                                     boundaryGap : false,
//                                     data : testLineX()
                                 }
                        ],
                        yAxis : [
                            {
                            	type : 'category',
                                axisLine : {onZero: false},
                                axisLabel : {
                                    formatter: '{value} %'
                                },
                                boundaryGap : false,
                                data : ['0', '10', '20', '30', '40', '50', '60', '70', '80']
                            }
                        ],
                        series:function(){
                        	 var serie=[];
                        	 for( var i=0;i < data.length;i++){
	                        	 var item={
		                        	 name:data[i].url,
		                        	 type:'line',
		                        	 smooth:true,
		                        	 data:data[i].time,
		                        	 clickable :true
	                        	 };
	                        	 serie.push(item);
                        	 };
                        	 return serie;
                        	 }()
                    },true);//图形展示
                }//ajax执行后台
            }); 
            var ecConfig = require('echarts/config');
            var zrEvent = require('zrender/tool/event');
            myChart.on(ecConfig.EVENT.CLICK,function (param) {
            	var temp=myChart.getSeries()[param.seriesIndex].name;;
            	var orderId = $("#orderId").val();
            	//敏感词
         		$.ajax({
                   type: "POST",
                   url: "keyWord.html?orderId="+orderId+"&url="+temp,
                   dataType:"json",
                   success: function(data){
                	   $("#pxbox").empty();//
                	   var str="";
                	   for(var i=0;i<data.length;i++){
                		   str+="<p><span class='pxboxL'>"+data[i].count+"</span>"+data[i].keyword+"</p>";
                	   }
                	   $("#pxbox").append(str);   
                   }
                });
         		
         		//详情
         		$.ajax({
                    type: "POST",
                    url: "details.html?orderId="+orderId+"&url="+temp,
                    dataType:"json",
                    success: function(data){
                 	   $("#web_datal").empty();//
                 	   var str="";
                 	   for(var i=0;i<data.length;i++){
                 		   var str1="";
                 		   if(data[i].scan_type==1){
                 			   str1="10分钟";
                 		   }
                 		   if(data[i].scan_type==2){
                			   str1="30分钟";
                		   }
                 		   if(data[i].scan_type==3){
	               			   str1="1小时";
	               		   }
	                 	   if(data[i].scan_type==4){
	              			   str1="2小时";
	              		   }
                 		   str+="<p>监测URL：<span>"+data[i].url+"</span></p><p>监测频率：<span>"+str1+"</span></p>"+
                          			"<p>得分：<span>"+data[i].score+"分</span></p>";
                 	   }
                 	   $("#web_datal").append(str);   
                    }
                 });
            });
        }
    );
    
    
});
