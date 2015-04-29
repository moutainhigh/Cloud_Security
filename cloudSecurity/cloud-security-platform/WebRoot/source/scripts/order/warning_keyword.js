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
            	url:"getData.html?orderId="+$('#orderId').val(),
                dataType:"json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                    $.each(data,function(i,p){
	                   	lineData[i]=p['url'];
	                   	lineData2[i]=p['count'];
	                   	time[i]=p['alarm_time'];
                    });
                    myChart.setOption({//图形
                    	tooltip : {
                            trigger: 'axis'
                        },
                        calculable : true,
                        xAxis : [
                                 {
                                     type : 'category',
                                     boundaryGap : false,
                                     data : testLineX()
                                 }
                        ],
                        yAxis : [
                            {
                                type : 'value'
                            }
                        ],
                        series:function(){
                        	 var serie=[];
                        	 for( var i=0;i < data.length;i++){
	                        	 var item={
		                        	 name:data[i].url,
		                        	 type:'line',
		                        	 data:data[i].count,
		                        	 markPoint : {
		                                 clickable :true,
		                                   data : [
		                                       {type : 'min', name: data[i].url}
		                                   ]
		                               }
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
            	var temp=param.data.name;
            	var orderId = $("#orderId").val();
         		$.ajax({
                   type: "POST",
//                   getData.html?orderId="+$('#orderId').val(),
                   url: "keyWord.html?orderId="+orderId+"&url="+temp,
//                   data: {"orderId":orderId,"url":temp},
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
            	alert(temp);
            });
        }
    );
    
    
});
