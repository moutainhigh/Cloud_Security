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
	<div id="main" style="width:50%;height:100%;"></div>
	
	<script>
	var myChart = echarts.init(document.getElementById('main'));
	var option;
		$.ajax({
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "orderServiceCount.html", 
	        success: function(obj){
	        	var titleData=obj.title;
	        	var typeData=obj.type;
	        	var innerRingData=obj.innerRing;
	        	var detailServiceData=obj.detailService;
	        	
	        	option = {
				    tooltip: {
				        trigger: 'item',
				        formatter: "{a} <br/>{b}:  ({d}%)"
				    },
				    legend: {
				        orient: 'horizontal',
				        x: 'left',
				        data:typeData
				    },
				    series: [
				        {
				            name:'',
				            type:'pie',
				            selectedMode: 'single',
				            radius: [0, '30%'],
				
				            label: {
				                normal: {
				                    position: 'inner'
				                }
				            },
				            labelLine: {
				                normal: {
				                    show: false
				                }
				            },
				            data:innerRingData,
				            itemStyle:{ 
	                            normal:{ 
	                                label:{ 
	                                   show: true, 
	                                   formatter: '{b} :  ({d}%)' 
	                                }, 
	                                labelLine :{show:true}
	                            } 
	                        } 
				        },
				        {
				            name:'',
				            type:'pie',
				            radius: ['40%', '55%'],
				            data:detailServiceData,
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
				};	        	
			myChart.setOption(option);
        	}
		});
		
	</script>
</body> 

</html>
