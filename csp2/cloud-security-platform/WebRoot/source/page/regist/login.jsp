<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>登录</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/source/css/login.css">
<script src="${ctx}/source/scripts/common/js/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/index.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/login.js"></script>
<script src="${ctx}/source/scripts/common/jquery.form.js" type="text/javascript"></script>
<style type="text/css">
@media screen and (min-width: 1201px) {
.login {padding-top: 172px}
}
/* css 注释说明：设置了浏览器宽度不小于1201px时*/

@media screen and (max-width: 1200px) {
.login {padding-top: 150px}
}
/* 设置了浏览器宽度不大于1200px时 */

@media screen and (max-width: 900px) {
.login {padding-top: 100px}
}
/* 设置了浏览器宽度不大于900px时*/

@media screen and (max-width: 500px) {
.login {padding-top: 60px}
}
/* 设置了浏览器宽度不大于500px时 */ 
</style>
<script type="text/javascript">
	window.onload=function(){
		//页面加载时清空所有的val值，如需要请取消注释；
		//$('.prompttext').val('');
}

</script>
</head>

<body>
	<div class="safeBox given login">
		<div class="loginBox" style="margin-bottom:170px;">
            <a href="${ctx}/index.html" class="logo">
                <img src="${ctx}/source/images/portal/logo.png" alt="">
            </a>
            <div class="cent">
            	<input type="hidden" id="userNameHidden" value="${userName}"/>
            	<input type="hidden" id="passwordHidden" value="${password}"/>
            	<input type="hidden" id="remeberHidden" value="${remeber}"/>
            	<form class="form" id="form_login" name="form_login" method="post">
                	<h2>登录安全帮账号</h2>

                	<ul>
                    	<li class="clearfix">
                        	<div class="list fl">
                        	<!-- <input type="text" class="text prompttext" id="login_name" onchange="logiName();" name="userName" value="" placeholder="用户名/手机号"/> -->
                            	<input type="text" class="text prompttext" id="login_name" name="userName" value="" placeholder="用户名/手机号"/>
                                <i id="login_name_flag" class="error" style="display:none"></i>
                            </div>
                            <div class="prompt fl" id="login_name_prompt"><b></b></div>
                        </li>
                        <li class="clearfix">
                        	<div class="list fl">
                            	 <input type="password" class="text prompttext password" id="login_password" name="password" value="" placeholder="密码"/>

                                <i id="login_password_flag" class="right" style="display:none"></i>
                            </div>
                            <div class="prompt fl" id="login_password_prompt"><b></b></div>
                        </li>
                        <li class="clearfix">
                        	<div class="list fl" style="width:218px;">
								<input type="text" class="text prompttext" style="width:186px" placeholder="验证码" name="checkNumber" id="checkNumber"/>
                                <i id="checkNumber_flag" class="right" style="display:none"></i>
                               <span class="test"><img src="${ctx}/image.jsp" alt="" width="108" height="42" id="imageNumber" title="点击换一张" onclick="checkNumberImage()"></img></span>
                               
                            </div>
                             <div class="prompt fl" id="checkNumber_prompt"><b></b></div>
                        </li>
                        <div id="errMsgDiv" style="text-align:left;padding-bottom:10px;display:none">
                    		<span id="errMsg" style="color:red;"></span>
                    	</div>
                    	
                        <li class="clearfix" style=" position:relative; top:-5px;">
                        	<div class="list password fl" style="width:100%;">
                                <label class="fl"><a href="${ctx}/forgetPass.html">忘记密码</a></label> 
                            </div>
                        </li>
                       
                    </ul>
                    
                    <div class="subBox" style=" position:relative; top:0px;">
                    	<input type="button" class="submit" style="background-color: #2499fb;" id="login_btn" value="立即登录">
                    </div>
                    <div class="subBox" style=" position:relative; top:5px;">
                    	<!--<span><a style="padding-right:50px;" href="${ctx}/forgetPass.html">忘记密码</a></span>
                    	--><span>还没有安全帮账号？<a href="${ctx}/registUI.html">免费注册</a><i></i></span>
                    </div>
                    
                </form>
            </div>
        </div>
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="#">
                    	<img src="${ctx}/source/images/portal/new-footer-logo.png" alt="" />
                    </a>
				</div>
				<ol class="footr clearfix fr">
					<li>
                    	<h2>帮助中心</h2>
                        <dl>
                        	<dd>购物指南</dd>
                            <dd>在线帮助</dd>
                            <dd>常见问题</dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关于安全帮</h2>
                        <dl>
                            <dd><a href="${ctx}/knowUs.html">了解安全帮</a></dd>
                            <dd><a href="${ctx}/joinUs.html">加入安全帮</a></dd>
                            <dd>联系我们</dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd>QQ交流群<br>470899318</dd>
                            <dd class="weixin"><a href="#">官方微信</a></dd>
                       </dl>
                    </li>
                     <li>
                    	<h2>特色服务</h2>
                        <dl>
                        	<dd>优惠劵通道</dd>
                            <dd>专家服务通道</dd>
                       </dl>
                    </li>
					
				</ol>
				
			</div>
            <form action="selfHelpOrderAPIInit.html" method="post" id="APILoginForm">
				<input type="hidden" id="apiIdHidden" name="apiId" value=""/>
				<input type="hidden" id="indexPageAPIHidden" name="indexPage" value="2"/>
			</form>
            <form action="selfHelpOrderInit.html" method="post" id="serviceLoginForm">
				<input type="hidden" id="serviceIdHidden" name="serviceId" value=""/>
				<input type="hidden" id="indexPageHidden" name="indexPage" value="1"/>
			</form>
			<form action="wafDetails.html" method="post" id="wafDetailsForm">
				<input type="hidden" id="serviceIdWafHidden" name="serviceId" value=""/>
				<input type="hidden" id="indexPageWafHidden" name="indexPage" value="1"/>
			</form>
		</div>
		<!-- 底部 start -->
		<c:import url="/foot.jsp"></c:import>
		<!-- 底部 end -->
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
           		<img src="${ctx}/source/images/portal/weixin.jpg" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</body>

<script>
$(function(){
	var username = $("#userNameHidden").val();
	var password = $("#passwordHidden").val();
	var remeber = $("#remeberHidden").val();
	if(username!=null && username!=''){
		$("#login_name").prop("value",username);
	}else{
		$('.prompttext').val('');
	}
	if(password!=null && password!=''){
		$("#login_password").prop("value",password);
	}		
})

</script>
</html>
