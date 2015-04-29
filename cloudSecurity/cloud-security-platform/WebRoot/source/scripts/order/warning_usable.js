var chart; 
var time = [];
$(function() { 
	var orderId = $("#orderId").val();
	
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
        		var myChart = ec.init(document.getElementById('pic'));
        	}
          //后台获取数据
            $.ajax({
            	type : "post",
            	url:"getDataUsable.html?orderId="+$('#orderId').val()+"&type="+$('#type').val(),
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
                    
                    myChart.setOption({//图形
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
//                    window.onresize = myChart.resize;
                }//ajax执行后台
            }); 
        }
    );
	
	
	
  //异步请求数据
    $.ajax({
    	url:"getDataUsable.html",
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
	

}
