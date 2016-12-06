<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<title>攻击统计分析</title>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts3/echarts.js"></script>
</head>

<body style="height: 100%; margin: 0">
	<div id="main" style="height:100%;"></div>
   <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        window.dateMaxIndex=0;
        var myChart = echarts.init(document.getElementById('main'));
		var option;
//         var option = {
// 		    title: {
// 		        text: '折线图堆叠'
// 		    },
// 		    tooltip: {
// 		        trigger: 'axis'
// 		    },
// 		    legend: {
// 		        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
// 		    },
// 		    grid: {
// 		        left: '3%',
// 		        right: '4%',
// 		        bottom: '3%',
// 		        containLabel: true
// 		    },
// 		    toolbox: {
// 		        feature: {
// 		            saveAsImage: {}
// 		        }
// 		    },
// 		    xAxis: {
// 		        type: 'category',
// 		        boundaryGap: false,
// 		        data: ['周一','周二','周三','周四','周五','周六','周日']
// 		    },
// 		    yAxis: {
// 		        type: 'value'
// 		    },
// 		    series: [
// 		        {
// 		            name:'邮件营销',
// 		            type:'line',
// 		            stack: '总量',
// 		            data:[120, 132, 101, 134, 90, 230, 210]
// 		        },
// 		        {
// 		            name:'联盟广告',
// 		            type:'line',
// 		            stack: '总量',
// 		            data:[220, 182, 191, 234, 290, 330, 310]
// 		        },
// 		        {
// 		            name:'视频广告',
// 		            type:'line',
// 		            stack: '总量',
// 		            data:[150, 232, 201, 154, 190, 330, 410]
// 		        },
// 		        {
// 		            name:'直接访问',
// 		            type:'line',
// 		            stack: '总量',
// 		            data:[320, 332, 301, 334, 390, 330, 320]
// 		        },
// 		        {
// 		            name:'搜索引擎',
// 		            type:'line',
// 		            stack: '总量',
// 		            data:[820, 932, 901, 934, 1290, 1330, 1320]
// 		        }
// 		    ]
// 		};
//         使用刚指定的配置项和数据显示图表。
//         myChart.setOption(option);
        
 var legendData;
 var xAxisData=[];
 var seriesData=[];
 $.ajax({
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "attackCount.html", 
	        success: function(obj){
	        var data= obj.listResult;
// 	        alert(data);
	        var legendData= obj.names;
// 	        	从后台得到返回的值，是一个json对象。 
	        	var　startDate=obj.startTime;
	        	var endDate=obj.endTime;
// 	        	生成开始日期~今天的日期列表
	        	var startTime=getDate(startDate);
	        	var endTime=getDate(endDate);
	        	var currentTime=getDate(startDate);
	        	for(var i=0;i<31;i++){
	        		if(currentTime<=endTime){
	        			currentTime.setDate(currentTime.getDate()+1);
	        			xAxisData.push(currentTime.Format("yyyy-MM-dd"));
	        		}
	        	}
	        	for(var j=0;j<data.length;j++){
	        		var dataUnit=data[j];
	        		var textData=dataUnit.splice(0,1);
	        		seriesData.push({
		            name:textData,
		            type:'line',
		            stack: '总量',
		            data:dataUnit
		        });
	        		
	        	}
	        	
	        	option = {
		    title: {
		        text: '漏洞类型分布及发展趋势'.split("").join("\n"),
		         x:'left',
		         y:'center'
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:legendData
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    toolbox: {
		        feature: {
		            saveAsImage: {}
		        }
		    },
		    xAxis: {
		    	type : 'category',
				position: 'bottom',
				boundaryGap: true,
				axisLabel : {
				show:true,
				interval: 0, 
				rotate: 30,
				margin: 8,
				formatter: '{value}',
				textStyle: {color: '#000000',fontFamily: 'sans-serif',fontSize: 10,fontStyle: '幼圆',fontWeight: 'bold'}
				},
				data : xAxisData
// 		        type: 'category',
// 		        boundaryGap: false,
// 		        data: xAxisData,
// 		        show : true,  
//                 axisLabel:{  
//                     interval:0 ,  
//                     formatter:function(val){  
//                       return val.split("").join("\n");  
//                     }  
//                 }  
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series: seriesData
		};
	        	
	        	
	        	
	        	
	        	
	        	
	        	
    			myChart.setOption(option);
	     	}
		});

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function getDate(strDate) {



            var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,



             function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');



            return date;



        }


    </script>
</body> 

</html>
