<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>API分析</title>
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
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/apiAnalysis.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/DateSlider.js"></script>
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
        <li class="b_current"><a href="${ctx}/adminAPIAnalysisUI.html" class="white">API分析</a></li>
        <li><a href="${ctx}/adminAPIAnalysisUI.html" class="white">日志分析</a></li>
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
		<div class="text">
			<span>API累计调用数<i>123782</i></span>
			<span>扫描网站数<i>782</i></span>
			<span>接入APIKEY数<i>1282</i></span>
			<span>接入任务数<i>1782</i></span>
		</div>
	
		<div class="dateSlider" id="dateSlider"></div>
	
	    	<div class="data_title">
	        	<button class="dataana_btn datab_cur fl" id="dd_btn">服务类型统计</button>
	            <div class="bor_t_small fl"></div>
	            <button class="dataana_btn fl" id="gj_btn">用户信息统计</button>
	            <div class="bor_t_big fl"></div>
	        </div>
	        <div class="dd_data_box">
	        <div class="infos clearfix">
			<dl class="info-33">
				<dt>web漏洞扫描API</dt>
				<dd class="cont" id="api1"></dd>
			</dl>
			<dl class="info-33">
				<dt>木马检测API</dt>
				<dd class="cont" id="api2"></dd>
			</dl>
			<dl class="info-33">
				<dt>网页篡改监测API</dt>
				<dd class="cont" id="api3"></dd>
			</dl>
			<dl class="info-33">
				<dt>网页敏感内容监测API</dt>
				<dd class="cont" id="api4"></dd>
			</dl>
			<dl class="info-33">
				<dt>网站可用性监测API</dt>
				<dd class="cont" id="api5"></dd>
			</dl>
			<dl class="info-33">
				<dt>会话管理API</dt>
				<dd class="cont" id="api6"></dd>
			</dl>
			<dl class="info-50">
				<dt>API服务使用时段统计</dt>
				<dd class="cont" id="apiUseBar"  style="height:234px"></dd>
			</dl>
			<dl class="info-50">
				<dt>最近7日使用趋势图</dt>
				<dd class="cont" id="apiUseLine"  style="height:234px"></dd>
			</dl>
		</div>
        </div>
        <div class="gj_data_box">
        	<div class="system_pz" style="margin:0px;width:1200px;height:674px">
			    <div>
			      <input type="text" name="name" id="searchName" style="border:1px solid red;" placeholder="请输入用户名">
			      <a id="search" href="javascript:;" onclick="analysisAPIUser();">搜索</a>
			    </div>   
			<div class="infos clearfix">
				<dl class="info-50">
					<dt>API服务使用时段统计</dt>
					<dd class="cont" style="height:234px">
						<div style="height:214px;width:580px"  id="userBar"></div>	
					</dd>
				</dl>
				<dl class="info-50">
					<dt>开发者调用次数TOP5</dt>
					<dd class="cont" style="height:234px">
						<div style="height:214px;width:500px" id="userBarTop5"></div>	
					</dd>
				</dl>
			</div> 
			<div class="infos clearfix">
				<dl class="info-50">
					<dt>开发者使用服务统计</dt>
					<dd class="cont">
						<div style="height:214px;width:500px" id="apiCountForUser"></div>	
					</dd>
				</dl>
				<dl class="info-50">
					<a id="allAPIUsers" href="javascript:;">查看完整列表</a>
				</dl>
			</div>       	
            </div>
        </div>
    </div>
</div>

    <!---完整列表-->
<div class="assetsaAdd hide popBoxhide" id="revise">
	  <div class="add_ser_top w678" style="margin-left:0px">
	<p class="w634">用户列表</p><p id="close" class="modelclose"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
   </div> 
        <div class="system_table" style="display:block;">
        	<table class="user_table" cellpadding="0" cellspacing="0">
            	<thead>
                	<tr>
                		<th class="t_username" style="text-align:center">序号</th>
                    	<th class="t_username" style="text-align:center">用户名</th>
                        <th class="t_date" style="text-align:center">使用API次数</th>
                    </tr>
                </thead>
                <tbody id="apiUserCountList">
	              

                </tbody>
            </table>
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
