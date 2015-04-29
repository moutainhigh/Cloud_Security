/*var chart; 
var time = [];
$(function() { 
	var orderId = $("#orderId").val();
    chart = new Highcharts.Chart({ 
        chart: { 
            renderTo: 'pic', //图表放置的容器，DIV 
            defaultSeriesType: 'line', //图表类型line(折线图), 
            zoomType: 'x'   //x轴方向可以缩放 
        }, 
        credits: { 
            enabled: false   //右下角不显示LOGO 
        }, 
        title: { 
            text: '告警统计' //图表标题 
        }, 
        xAxis: {
        	categories: time
        }, 
        yAxis: {  //y轴 
            title: {text: '告警个数'}, //标题 
            lineWidth: 2 //基线宽度 
        }, 
        plotOptions:{ //设置数据点 
            line:{ 
                dataLabels:{ 
                    enabled:true  //在数据点上显示对应的数据值 
                }, 
                enableMouseTracking: false //取消鼠标滑向触发提示框 
            } 
        }, 
        exporting: { 
            enabled: false  //设置导出按钮不可用 
        }, 
        series: [{  //数据列 
            name: '关键字告警统计', 
        }] 
    }); 
  //异步请求数据
    $.ajax({
    	url:"getData.html",
    	data: {"orderId":orderId},
    	dataType:"json",
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	type: "post",
        success:function(data){
            //定义一个数组
        	datas = [],
            //迭代，把异步获取的数据放到数组中
            $.each(data,function(i,d){
            	datas.push([d.alarm_time,d.count]);
            	time.push(d.alarm_time);
            });
        	//设置数据
        	if(datas.length==0){
        		alert("没有相关结果");
        	}else{
        		 chart.series[0].setData(datas); 
        	}
        },
    });
    //getData();
	//window.setInterval(getData,30000);
}); 
function getData(){
	var orderId = $("#orderId").val();
 		$.ajax({
           type: "POST",
           url: "/cloud-security-platform/scaning.html",
           data: {"orderId":orderId},
           dataType:"json",
           success: function(data){
          		var progress = data.progress;
           		$("#bar1").html(progress+"%");
           		$("#bar2").css("width", progress+"%");
           		$("#bar2").html(progress+"%");
           		$("#url").html("当前URL:"+data.currentUrl);
           }
        });
}
//加载模板下拉框选项 
$(document).ready(function() {
	var orderId = $("#orderId").val();
	$.ajax({ 
		type: "POST",
		url: "/cloud-security-platform/getExecuteTime.html",
        data: {"orderId":orderId},
        dataType:"text",
		success : function(result){
			$("#execute_Time").append(result); 
		} 
	});
}); 
function historicalDetails(){
	var orderId = $("#orderId").val();
	var execute_Time = $("#execute_Time").val();
	var type = $("#type").val();
//	window.location.href = "${ctx}/historyInit.html?execute_Time="
	//							+ execute_Time+"&orderId="+orderId;
	window.open("${ctx}/historyInit.html?execute_Time="
								+ execute_Time+"&orderId="+orderId+"&type="+type); 
	

}*/















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
            //--- 趋势图 ---
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
	                   	alert(time[i]);
                    });
                    myChart.setOption({//图形
                    	tooltip : {
                            trigger: 'axis'
                        },
                        toolbox: {
                            show : true,
                            feature : {
                               /* mark : {show: true},
                                dataView : {show: true, readOnly: false},
                               // magicType : {show: true, type: ['line', 'bar']},
                                restore : {show: true},
                                saveAsImage : {show: true}*/
                            }
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
		                        	 data:data[i].count
	                        	 };
	                        	 serie.push(item);
                        	 };
                        	 return serie;
                        	 }()
                        /*series : [
                            {
                                name:'中危漏洞个数',
                                type:'line',
                                data: testLineData2()
                            }
                        ]*/
                    },true);//图形展示
                }//ajax执行后台
            }); 
        }
    );
    
});
// 事件的参数中包括：数据在序列中的下标dataIndex，数据的值value，x轴上的名称name
function eConsole(param) {
	if (typeof param.seriesIndex == 'undefined') {
		return;
	}
	if (param.type == 'click') {
		var mes = param.name + ':' + param.value;
		document.getElementById('info').innerHTML = mes;
	}
}
