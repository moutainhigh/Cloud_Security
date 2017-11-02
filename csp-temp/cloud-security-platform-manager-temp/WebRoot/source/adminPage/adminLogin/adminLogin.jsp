<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>管理员登录页面</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/loginregist.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<!-- add new css -->
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/adminLogin.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript">
function checkNumberImage(){
	var imageNumber = document.getElementById("imageNumber");
	imageNumber.src = "${ctx}/imageAdmin.jsp?timestamp="+new Date().getTime();
}
</script>
</head>
<body>
<!--头部代码-->
<div id="container">
<div class="head_bj b_head_bj">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/backstage_logo.jpg" width="342" height="54" /></div>
    </div>
</div>

<!--头部代码结束-->
<div class="b_login_wrap">
  <div class="b_login_box">
    <div class="b_login_form">
      <form  id="b_login" name="b_login" method="post" action="${ctx}/adminLogin.html">
      	<div class="b_login_list">用户登录</div>
        <div class="login_list">
          <input type="text" class="b_login_txt required" id="login_name" name="name" value="${requestScope.name }"/>
          <span id="login_name_msg" style="color:red;float:left"></span>
        </div>
        <div class="login_list">
          <input type="password" class="b_login_txt b_login_pass" id="login_password" name="password" value="${requestScope.password }"/>
          <span id="login_password_msg" style="color:red;float:left"></span>
        </div>
        <div class="login_list">
          <input type="text" class="b_login_txt b_lgoin_yzm required" name="checkNumber" id="checkNumber"/>
          <span><img src="${ctx}/imageAdmin.jsp" width="65" height="38" id="imageNumber" title="点击换一张" onclick="checkNumberImage()"/></img></span>
          <span id="login_checkNumber_msg" style="color:red"></span>
        </div>
        <span style="color:red" >${msg}</span>
       <div class="login_list pb0">
          <input type="checkbox" class="login_checkbox" name="remeberMe" id="remeberMe" value="yes" ${requestScope.checked }/>
          <span class="b_auto_login">自动登录</span>
          <!-- <a href="#" class="b_forget_pass">忘记密码</a>  -->
        </div>
        <button class="b_login_btn" id="b_login_btn">登　　录</button>
      </form>
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
