<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>忘记密码</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/loginregist.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.metadata.js"></script>
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script type="text/javaScript">
 function confimPass(){
 
  var p1=$("#regist_password").val();//获取密码框的值
	  var p2=$("#regist_confirm_password").val();//获取重新输入的密码值
	  if(p2!=null&&p2!=""){
		  if (p1!=p2) {
		   	$("#regist_confirm_password_msg").html("<font color='red'>两次输入密码不一致，请重新输入</font>");
		   
		  }else if(p1.length<6||p1.length>20){
			$("#regist_password_msg").html("请输入6-20位，支持中英文，数字，字符组合");
		}else{
		    $("#regist_confirm_password_msg").html("");
		   $("#passForm").submit();
		  }
	  }else{
		  $("#regist_confirm_password_msg").html("<font color='red'>确认密码不能为空</font>");
		 
	  }
 }
</script>
</head>
<body>
<!--头部代码-->
<div class="head">
	<div class="headBox">
		<div class="safeL fl">
			<div class="logo">
			<img src="${ctx}/source/images/portal/logo.png" alt=""/><b></b><span>网站安全帮</span>
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
<!--头部代码结束-->
 <div class="login_wrap">
  <div class="pass_box">
 
        <form id="passForm" action="${ctx}/confirmPass.html">
        <table style="margin: 0 auto">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr class="register_tr">
          <input type="hidden" name="email" value="${user.email}"/>
           <input type="hidden" name="mobile" value="${user.mobile}"/>
          
            <td class="regist_title">设置密码</td>
            <td class="regist_input"><input type="password" class="regist_txt required" name="password" id="regist_password" onblur="checkPassword()"/><span id="regist_password_msg" style="color:red;float:left"></td>
            <td class="regist_prompt">请输入6-20位，支持中英文，数字，字符组合</td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">确认密码</td>
            <td class="regist_input"><input type="password" class="regist_txt required" name="confirm_password" id="regist_confirm_password"  autocomplete="false"/><span id="regist_confirm_password_msg" style="color:red;float:left;"></td>
            <td class="regist_prompt"></td>
          </tr>
         <tr>
            <td class="regist_title"></td>
            <td>
            	<input type="button" class="login_btn" onclick="confimPass()" value="确认"/>
            </td>
          </tr>
          </table>
          </form>
      
  </div>
  </div>
  
<!--尾部部分代码-->
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
<!--尾部部分代码结束-->
</body>
</html>

