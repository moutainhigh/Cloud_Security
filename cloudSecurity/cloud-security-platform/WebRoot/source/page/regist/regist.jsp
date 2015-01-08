<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册页面</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/loginregist.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/login.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/regist.js"></script>
</head>
<body>
<!--头部代码-->
<div class="head_bj">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
    <div class="list">
      <ul>
        <li><a href="###">首页</a></li>
        <li><a href="###">我的资产</a></li>
        <li><a href="###">服务下单</a></li>
        <li><a href="###">订单追踪</a></li>
        <li style="border-right:1px solid #11871d;"><a href="###">我的账单</a></li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left"> <a href="###"><img src="${ctx}/source/images/ren.png" /></a> </div>
      <div class="lagst-right">
        <p ><a href="###">登陆</a></p>
        <p> <a href="${pageContext.request.contextPath}/registUI.html">注册</a></p>
      </div>
    </div>
  </div>
</div>
<!--头部代码结束-->
<div class="login_wrap">
  <div class="login_box">
    <div class="login_title">
      <ul>
        <li class="user_login cur">用户登录</li>
        <li class="user_regist">用户注册</li>
      </ul>
    </div>
    <div class="login_form">
      <form  id="form_login" name="form_login" method="post">
        <div class="login_list">
          <input type="text" class="login_txt required" id="name" name="name" />
        </div>
        <div class="login_list">
          <input type="password" class="login_txt login_pass" id="password" name="password"/>
        </div>
        <div class="login_list">
          <input type="text" class="login_txt lgoin_yzm required" name="yzm"/>
          <div class="yam_box"><img src="${ctx}/source/images/login_yzm.png" width="65" height="38" id="imageNumber"/></div>
        </div>
        <div class="login_list">
          <input type="checkbox" class="login_checkbox"/>
          <span class="auto_login">自动登录</span><a href="#" class="forget_pass">忘记密码</a> </div>
        <button class="login_btn" id="login_btn">登　　录</button>
      </form>
    </div>
    
    
    
    
    <div class="regist_form">
      <form  id="form_regist" action="${pageContext.request.contextPath}/regist.html" name="form_regist" method="post">
        <table>
          <tr class="register_tr">
            <td class="regist_title">用户名</td>
            <td class="regist_input"><input type="text" class="regist_txt required" name="name" id="regist_name" onblur="checkName()" /><span id="regist_name_msg" style="color:red;float:left"></span></td>
            <td class="regist_prompt">4-20位字符，支持中英文，数字，字符组合</td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">设置密码</td>
            <td class="regist_input"><input type="password" class="regist_txt required" name="password" id="regist_password" onblur="checkPassword()"/><span id="regist_password_msg" style="color:red;float:left"></td>
            <td class="regist_prompt">6-20位字符，可使用字母、数字和符号的组合，不建议纯字母、纯数字、纯符号</td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">确认密码</td>
            <td class="regist_input"><input type="password" class="regist_txt required" name="confirm_password" id="regist_confirm_password" onblur="checkConfirmPassword()" /><span id="regist_confirm_password_msg" style="color:red;float:left;"></td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title"><span class="yzfs">验证手机</span></td>
            <td class="regist_input" id="yzbox">
            <input type="text" class="regist_txt required" name="mobile" id="regist_phone" onblur="checkMobile()"/>
            <span id="regist_mobile_email_msg" style="color:red;float:left;display:block;"></td>
            <td class="regist_prompt">或<a class="forget_pass" id="get_email_yzm">验证邮箱</a><a class="forget_pass" id="get_phone_yzm">验证手机</a></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">验证码</td>
            <td class="regist_input"><input type="text" class="regist_txt regist_yzm required" name="verification_code" id="verification_code" onblur="checkEmailActivationCode()"/>
              <input class="regist_yzm_btn" type="button" id="phone_yzm" value="获取短信验证码" onclick="checkSendMobile()"/>
              <input class="regist_yzm_btn" type="button" id="email_yzm" value="获取邮箱验证码" onclick="checkSendEmail()"/>
           	  <span id="verification_code_msg" style="color:red;float:left">
            </td>
            
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title"></td>
            <td><input type="checkbox"  class="login_checkbox" id="ck"/>
              <span class="regist_yes">我已阅读并同意</span><a href="#" class="forget_pass">《云平台用户注册协议》</a>
              <span id="ck_msg" style="color:red">
             </td>
          </tr>
          <tr>
            <td class="regist_title"></td>
            <td>
            	<input type="button" class="login_btn" onclick="submitForm()" value="立即注册"/>
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
</div>
<!--尾部部分代码-->
<div class="bottom_bj">
<div class="bottom">
<div class="bottom_main">
  <h3><a href="###">新手入门</a></h3>
  <ul>
    <li><a href="###">新用户注册</a></li>
    <li><a href="###">用户登录</a></li>
    <li><a href="###">找回密码</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###"> 帮助</a></h3>
  <ul>
    <li><a href="###">常见问题</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###">厂商合作</a></h3>
  <ul>
    <li><a href="###">华为</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">360</a></li>
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
<li>如未经授权用作他处，将保留追究侵权者法律责任的权利。<br />
  京ICP备11111111号-4 / 京ICP证1111111号<br />
  北京市公安局朝阳分局备案编号:110105000501</li>
</div>
</div>
</div>
<!--尾部部分代码结束-->
</body>
</html>

