<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<title>统计分析</title>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts3/echarts.js"></script>
</head>

<body style="height: 100%; margin: 0">
	<div id="main" style="width:50%;height:100%;float:left;"></div>
<!-- 	<div id="main2" style="width:50%;height:100%;float:left;"></div> -->
	<script>
	var myChart = echarts.init(document.getElementById('main'));
// 	var myChart2 = echarts.init(document.getElementById('main2'));
	var option;
		$.ajax({
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "serviceUser.html", 
	        success: function(obj){
	        	var titleData=obj.title;
	        	var legendData=obj.legend;
	        	var serviceUserList=obj.serviceUserList;
	        	
	        	option = {
					    title : {
					        text: titleData,
					        x:'left',
					        y:'top'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} :({d}%)"
					    },
					    legend: {
					        orient: 'vertical',
// 					        left: 'left',
					        x:'200',
					        y:'bottom',
					        data: legendData
					    },
					    series : [
					        {
					            name: '',
					            type: 'pie',
					            radius : '25%',
					            center: ['60%', '30%'],
					            data:serviceUserList,
					            itemStyle: {
					                emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					                },
					                normal:{ 
		                                label:{ 
		                                   show: true, 
		                                   formatter: '{b} : ({d}%)' ,
		                                   position: 'outer'
		                                }, 
		                                labelLine :{show:true}
		                            } 
					            }
					        }
					    ]
					};
					myChart.setOption(option);
// 				    myChart2.setOption(option);	
				        }
				    });
	</script>
</body> 

</html>
