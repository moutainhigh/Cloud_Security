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
<script type="text/javascript" src="${ctx}/source/scripts/regist/forgetPass.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<script type="text/javaScript">
function forgetPass(type){
  if(type=='1'){
   $("#eamilDiv").show();
      $("#phoneDiv").hide();
     $('#email_yzm').show();
  }else{
    $("#phoneDiv").show();
    $("#eamilDiv").hide();
  }
}

</script>
</head>
<body>
<!--头部代码-->
<div class="head_bj">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
    <div class="list">
      <ul>
        <li><a href="${ctx}/index.html">首页</a></li>
        <li><a href="###">我的订单</a></li>
        <li><a href="${ctx}/aider.html">在线帮助</a></li>
        <li style="border-right:1px solid #11871d;">
        	<c:if test="${sessionScope.globle_user==null }">
        		<a href="${ctx}/toLoginUI.html">用户中心</a>
        	</c:if>
        	<c:if test="${sessionScope.globle_user!=null }">
        		<a href="${ctx}/userCenterUI.html">用户中心</a>
        	</c:if>
        </li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left"> 
	      <c:if test="${sessionScope.globle_user!=null }">
	          <a href="${ctx}/userDataUI.html"><img src="${ctx}/source/images/ren.png" /></a>
	      </c:if>
	      <c:if test="${sessionScope.globle_user==null }">
	      	<a href="${ctx}/loginUI.html"><img src="${ctx}/source/images/ren.png" /></a>
	      </c:if>
      </div>
     
    </div>
  </div>
</div>
<!--头部代码结束-->
<div class="login_wrap">
  <div class="pass_box">
    
    <div class="forget_form">
        <div class="login_list">
         
          <span class="auto_login"><img src="${ctx}/source/images/forgetEmail.png" width="143" height="40" id="imageNumber" title="邮箱找回密码"  onclick="forgetPass('1');"/></img></span>
         <span class="auto_login"><img src="${ctx}/source/images/forgetPhone.png" width="143" height="40" id="imageNumber" title="手机找回密码" onclick="forgetPass('2');"/></img></span>
        </div>
      <div  id="phoneDiv" style="display:none">
      <form id="phoneForm" action="${ctx}/updatePass.html">
       <table>
        <tr class="register_tr">
            <td class="regist_title"><span class="yzfs">验证手机</span></td>
            <td class="regist_input" id="yzbox">
            <input type="text" class="regist_txt required" name="phone_code" id="phone_code"  style="margin-right:20px"/>
            <span id="forget_phone_msg" style="color:red;float:left;display:block;"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">验证码</td>
            <td class="regist_input"><input type="text" class="regist_txt regist_yzm required" name="verification_phone" id="verification_phone" />
            <input class="regist_yzm_btn" type="button" id="phone_yzm" value="获取短信验证码"  onclick="checkSendMobile()"/>
           	  <span id="verification_phone_msg" style="color:red;float:left">
            </td>
          </tr>
           <tr>
            <td class="regist_title"></td>
            <td>
            	<input type="button" class="login_btn" onclick="checkPhoneActivationCode()" value="下一步"/>
            </td>
          </tr>
          </table>
          </form>
       </div>
       
        <div id="eamilDiv" style="display:none">
        <form id="passForm" action="${ctx}/updatePass.html">
        <table>
        <tr class="register_tr">
            <td class="regist_title"><span class="yzfs">验证邮箱</span></td>
            <td class="regist_input" id="yzbox">
            <input type="text" class="regist_txt required" name="eamil_ecode" id="eamil_ecode"  style="margin-right:20px"/>
            <span id="forget_email_msg" style="color:red;float:left;display:block;"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">验证码</td>
            <td class="regist_input"><input type="text" class="regist_txt regist_yzm required" name="verification_email" id="verification_email" />
              <input class="regist_yzm_btn" type="button" id="email_yzm" value="获取邮箱验证码" onclick="checkSendEmail()"/>
           	  <span id="verification_email_msg" style="color:red;float:left">
            </td>
         </tr>
         <tr>
            <td class="regist_title"></td>
            <td>
            	<input type="button" class="login_btn" onclick="checkEmailActivationCode()" value="下一步"/>
            </td>
          </tr>
          </table>
          </form>
          </div>
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

</div>
</div>
</div>
<!--尾部部分代码结束-->
</body>
</html>

