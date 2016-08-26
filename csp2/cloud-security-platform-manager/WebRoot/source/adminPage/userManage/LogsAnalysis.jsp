<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>日志分析</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/dateSlider.css" type="text/css" rel="stylesheet" />
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
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul>
      	<li><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
		<li><a href="${ctx}/adminchinas.html" target="_blank" class="white">安全态势</a></li>
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAssetUI.html" class="white">资产分析</a></li>
        <li><a href="${ctx}/adminUserAnalysisUI.html" class="white">用户分析</a></li>
        <li><a href="${ctx}/orderformanalyse.html" class="white">订单分析</a></li>
        <li><a href="${ctx}/adminWarnAnalysisUI.html" class="white">告警分析</a></li>
        <li><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li><a href="${ctx}/adminAPIAnalysisUI.html" class="white">API分析</a></li>
        <li class="b_current"><a href="${ctx}/adminAPIAnalysisUI.html" class="white">日志分析</a></li>
        <li style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left b_lagst_left"> <a href="#"><img src="${ctx}/source/adminImages/b_photo.jpg" width="43" height="42"></a> </div>
      <div class="lagst-right">
        <p ><a href="###" class="white">${sessionScope.admin_user.name }</a></p>
        <p> <a href="${ctx}/adminExit.html" class="white">退出</a></p>
      </div>
    </div>
  </div>
</div>
<!--头部代码结束-->
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


<!--尾部部分代码-->
<div class="bottom_bj">
<div class="bottom">
<div class="bottom_main">
  <h3><a href="###">新手入门</a></h3>
  <ul>
      <li><a href="${ctx}/registUI.html">新用户注册</a></li>
    <li><a href="${ctx}/loginUI.html">用户登录</a></li>
    <li><a href="###">找回密码</a></li>
  </ul>
</div>
<div  class="bottom_main">
 <h3><a href="###"> 帮助</a></h3>
  <ul>
  <li><a href="${ctx}/aider.html">常见问题</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###">厂商合作</a></h3>
  <ul>
    <li><a href="###">华为</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">知道创宇</a></li>
  </ul>
</div>
<div  class="bottom_main">
<h3><a href="###">联系我们</a></h3>
<ul>
<li><a href="###">客户电话</a></li>
</div>
<div  class="bottom_main" style="width:380px;">
<h3><a href="###">版权信息</a></h3>
 <ul>
 <li>Copyright&nbsp;©&nbsp;2015 中国电信股份有限公司北京研究院<br />
京ICP备12019458号－10</li>
</div>
</div>
</div>
<!--尾部部分代码结束-->
</body>
</html>
