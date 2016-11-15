<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>平台日志分析</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>

<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.ui.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<!--<script type="text/javascript" src="${ctx}/source/scripts/common/modelbox.js"></script>
--><script type="text/javascript" src="${ctx}/source/scripts/common/raphael.2.1.0.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/justgage.1.0.1.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/esl.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/logsAnalysis.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<style type="text/css">
.clearfix:after {content: " "; display: block; clear: both; height: 0; visibility: hidden; line-height: 0;font-size:0; }
.clearfix{ zoom:1;}
.text{margin-bottom: 30px;}
.text span{display: inline-block; width: 23%; }
.text span, .text span i{font-size: 18px; color: #009393; font-weight: 700;}
.text span i{ font-style: normal; padding: 0 10px; }
#dateSlider{width:970px; height:40px; padding-top: 20px;}
.info-33{ float: left; width:33%; }
.info-50{ float: left; width:50%; }
.infos{ margin-top: 30px; }
.infos dt{padding: 10px 0; text-align: center; font-size: 24px}
.infos .cont{ padding: 5px 10px; min-height: 200px; /*overflow: scroll;  border: 1px solid #ccc;*/} 
.myCustomClass > span{ display: none; }
.ui-rangeSlider-label-value, .ui-ruler-tick-label{ font-size: 16px; }
  	.assetsaAdd{
	    display: none;
		padding: 20px;
		position: fixed;
		top: 50%;
		left: 50%;
		z-index: 99;
		text-align: center;
		background: #fff;
		border-radius: 10px;
		width: 670px;
		height: 400px;
		margin-left:-355px;
		margin-top:-220px;
			
}
popBoxhide .close{ width:26px; height:26px; display:block; position:absolute; right:12px; top:13px; background:url(../images/user_ico_4.jpg) no-repeat; cursor:pointer;}
</style>
 <script type="text/javascript">

	

	
   </script> 
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
        	<li class="statistics active">日志分析视图<i></i></li>
        </ul>
        <div class="tabBox orderform tabCont">
        	<div class="item clearfix tabItem analyseBox" style="display:block">
            		<div class="fl order" id="system1" style="width:1030px;height:300px"></div>
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
