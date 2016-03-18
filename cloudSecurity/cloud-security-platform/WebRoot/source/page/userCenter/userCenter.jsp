<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>用户中心</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
</head>

<body>
<!--头部-->
<div class="head new-head">
	<div class="headBox">
		<div class="safeL fl">
			<div class="logo">
			<img src="${ctx}/source/images/portal/newlogo-footer.png" alt="">
			</div>
		</div>
		
		<div class="safem fl">
			<span class="fl"><a href="${ctx}/index.html">首页</a></span>
			<div class="Divlist listJs fl">
				<a href="#">我的安全帮<i></i></a>
				<ul class="list listl">
					<li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
					<li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
					<li style="border: none;"><a href="${ctx}/userDataUI.html">个人信息</a></li>
				</ul>
			</div>
			<span class="fl"><a href="#">手机APP</a></span>
			<span class="fl"><a href="${ctx}/aider.html">帮助</a></span>
			
		</div>
		<div class="safer fr">
			<!-- 如果已经登录则显示用户名，否则需要登录 -->
	         <c:if test="${sessionScope.globle_user!=null }">
		        <a href="${ctx}/userDataUI.html">${sessionScope.globle_user.name }</a>
		        <em>|</em>
		        <a href="${ctx}/exit.html">退出</a>
	         </c:if>
	         <c:if test="${sessionScope.globle_user==null }">
	            <a href="${ctx}/loginUI.html">登录</a>
				<em>|</em>
				<a href="${ctx}/registUI.html">注册</a>
	         </c:if>
		</div>
	</div>
</div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <div class="user_left">
    <ul class="user_1">
      <li style="font-size:16px; font-weight:500; line-height:28px; text-align:center;">
      	<a  style="color:#4593fd;" href="${ctx}/userCenterUI.html">用户中心</a>
      </li>
      <li><a href="${ctx}/userDataUI.html">基本资料</a></li>
      <li><a href="${ctx}/userBillUI.html">我的账单</a></li>
      <li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
      <h2>订购中心</h2>
      <!-- <li><a href="${ctx}/selfHelpOrderInit1.html">自助下单</a></li>-->
      <li><a href="${ctx}/orderTrackInit.html">订单跟踪</a></li>
    </ul>
  </div>
  
  <!--用户中心-->
  <div class="user_right" style=" position:relative;">
    <div class="user_ren" style="left:340px;"><a href="###"><img src="${ctx}/source/images/admin_rw.png" /></a></div>
    <div class="user_name"><span>用户名</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="###">${sessionScope.globle_user.name }</a></div>
    <div class="user_ico_1"><img src="${ctx}/source/images/user_ico_9.jpg" /> </div>
    <div class="user_ico_2"> <img src="${ctx}/source/images/user_ico_10.jpg" /></div>
    <div class="center_bottom">
      <div class="center_bottom_div">
        <div class="center_bottom_number">${orderNum}</div>
        <div class="center_bottom_txt"><a href="${ctx}/orderTrackInit.html">订单总数</a></div>
      </div>
      <div class="center_bottom_div">
        <div class="center_bottom_number">${servNum}</div>
        <div class="center_bottom_txt"><a href="${ctx}/orderTrackInit.html?state=1">服务中订单总数 </a></div>
      </div>
      <div class="center_bottom_div" style="border-right:0px;">
        <div class="center_bottom_number">${alarmSum}</div>
        <div class="center_bottom_txt"><a href="${ctx}/orderTrackInit.html?state=2">告警订单总数</a></div>
      </div>
    </div>
  </div>
</div>

<!-- 尾部代码开始-->
<div class="safeBox">
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="#"><img src="${ctx}/source/images/portal/footlogo.png" alt=""></a>
				</div>
				<ol class="footr clearfix fr">
					<li>
                    	<h2>帮助中心</h2>
                        <dl>
                        	<dd><a href="#">购买指南</a></dd>
                            <dd><a href="#">在线帮助</a></dd>
                            <dd><a href="#">常见问题</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关于安全帮</h2>
                        <dl>
                        	<dd><a href="${ctx}/knowUs.html">了解安全帮</a></dd>
                            <dd><a href="${ctx}/joinUs.html">加入安全帮</a></dd>
                            <dd><a href="#">联系我们</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd><a href="#">QQ交流群</br>470899318</a></dd>
                            <dd class="weixin"><a href="#">官方微信</a></dd>
                       </dl>
                    </li>
                     <li>
                    	<h2>特色服务</h2>
                        <dl>
                        	<dd><a href="#">优惠劵通道</a></dd>
                            <dd><a href="#">专家服务通道</a></dd>
                       </dl>
                    </li>
					
				</ol>
				
			</div>
		</div>
		<div class="foot">
			<p>版权所有Copyright © 2015 中国电信股份有限公司北京研究院京ICP备12019458号-10</p>
		</div>
		<!---执行效果-->
<div class="weixinshow popBoxhide" id="weixin">
	<i class="close chide"></i>
    <div class="Pophead">
    	<h1 class="heaf">安全帮微信二维码</h1>
    </div>
	<div class="popBox">
    	 <p>打开微信，点击右上角的“+”，选择“扫一扫”功能，<br>
对准下方二维码即可。
		</p>
           <div class="weinImg" style="text-align:center;">
           	<img src="${ctx}/source/images/portal/weixin.png" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</div>
</body>
</html>
