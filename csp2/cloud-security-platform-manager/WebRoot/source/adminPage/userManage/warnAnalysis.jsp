<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>告警分析</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>

<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/warnAnalysis.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/d3.min.js"></script>
</head>

<body>
<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
<div class="main_wrap">
	<div class="main_center">
    	<ul class="main_nav tabList clearfix">
        	<li class="statistics active">告警分析视图<i></i></li>
        </ul>
        <div class="tabBox tabCont">
        	<div class="item tabItem" style="display:block">
            	<div class="analysis clearfix">
                	<div class="analysisL fl" id="warn">
                    	<!--<img src="${ctx}/source/images/chartsmall.jpg" alt="">
                    --></div>
                    <div class="fl analysisM">
                    	
                    </div>
                    <div class="fl analysisR">
                    	<h3>告警类型TOP5实时排行榜</h3>
                        <ol class="ananly_list anlist clearfix">
                        	<li class="active">漏洞扫描</li>
                            <li>木马检测</li>
                            <li>页面篡改</li>
                            <li>关键字检测</li>
                            <li>可用性</li>
                        </ol>
                        <div class="thecharts analyse_tabCont">
                        	<div class="charts_list analyse_tabItem" style="display:block" id="data1">
                        	</div>
                            <div class="charts_list analyse_tabItem" id="data2">
                            </div>
                            <div class="charts_list analyse_tabItem" id="data3">

                            </div>
                            <div class="charts_list analyse_tabItem" id="data4">
  
                            </div>
                            <div class="charts_list analyse_tabItem" id="data5">
                            </div>
                        
                        </div>
                    </div>
                </div>
            </div>
            
        
        </div>
    </div>
</div>
<!--============bottom============-->
	<!-- footer start -->
	<c:import url="/footer.html"></c:import>
	<!-- footer end -->
</div>
</body>
</html>
