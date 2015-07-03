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
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<script type="text/javaScript">
 function confimPass(){
 
  var p1=$("#regist_password").val();//获取密码框的值
	  var p2=$("#regist_confirm_password").val();//获取重新输入的密码值
	  if(p2!=null&&p2!=""){
		  if (p1!=p2) {
		   	$("#regist_confirm_password_msg").html("<font color='red'>两次输入密码不一致，请重新输入</font>");
		   
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
<div class="head_bj">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
    <div class="lagst">
      <div class="lagst-left">
       <c:if test="${sessionScope.globle_user!=null }">
          <a href="${ctx}/userDataUI.html"><img src="${ctx}/source/images/ren.png" /></a>
       </c:if>
        <c:if test="${sessionScope.globle_user==null }">
           <a href="${ctx}/toLoginUI.html"><img src="${ctx}/source/images/ren.png" /></a>
        </c:if>
      </div>
      <div class="lagst-right">
         <!-- 如果已经登录则显示用户名，否则需要登录 -->
         <c:if test="${sessionScope.globle_user!=null }">
          <p><a href="${ctx}/userDataUI.html" style="color: #fff">${sessionScope.globle_user.name }</a></p>
          <p><a href="${ctx}/exit.html">退出</a></p>
         </c:if>
         <c:if test="${sessionScope.globle_user==null }">
               <p><a href="${pageContext.request.contextPath}/loginUI.html">登录</a></p>
               <p><a href="${pageContext.request.contextPath}/registUI.html">注册</a></p>
         </c:if>
      </div>
    </div>
    <div class="list">
        <ul>
            <li><a href="${ctx}/index.html">首页</a></li>
            <li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
            <li><a href="${ctx}/aider.html">在线帮助</a></li>
            <li style="border-right:1px solid #1369C0;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
        </ul>
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

