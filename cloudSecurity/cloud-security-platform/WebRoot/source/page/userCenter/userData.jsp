<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心-基本资料</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript">
var checkName1=0;
var checkMobile1=0;
var checkEmail1=0;
//验证用户名
function checkName(){
	var name = $("#regist_name").val();
	var	pattern	= /^[a-zA-Z0-9]{4,20}$/;
	var originalName = $("#originalName").val();
	if(name!=originalName){
		var flag = pattern.test(name);
		if(name==""||name==null){
			$("#regist_name_msg").html("请输入用户名！");
			checkName1=0;
		}else{
			if(flag){
				$.ajax({
	               type: "POST",
	               url: "/cloud-security-platform/regist_checkName.html",
	               data: {"name":name},
	               dataType:"json",
	               success: function(data){
	                   if(data.count>0){
	                   		$("#regist_name_msg").html("用户名已经使用!");
	                   		checkName1=0;
	                   }else{
	                   		$("#regist_name_msg").html("");
	                   		checkName1=1;
	                   }
	               },
	            }); 
			}else{
				$("#regist_name_msg").html("请输入4-20位字符！");
				checkName1=0;
			}
		}
	}else{
		$("#regist_name_msg").html("");
		checkName1=1;
	}
}
//验证邮箱和手机号码
function checkMobileAndEmail(){
	var mobile = $("#regist_phone").val();
	var email = $("#regist_email").val();
	if((mobile!=""&&mobile!=null)||(email!=""&&email!=null)){
		if(mobile!=""&&mobile!=null){
		var originalMobile = $("#originalMobile").val();
		if(mobile!=originalMobile){
				var pattern = /^1[3|5|8|7][0-9]{9}$/;
				var flag = pattern.test(mobile);
				if(flag){
					$.ajax({
			               type: "POST",
			               url: "/cloud-security-platform/regist_checkMobile.html",
			               data: {"mobile":mobile},
			               dataType:"json",
			               success: function(data){
			                    if(data.count>0){
			                        $("#regist_mobile_msg").html("您填写的手机号码已使用!");
			                        checkMobile1 = 0;
			                    }else{
			                   		$("#regist_mobile_msg").html("");
			                   		checkMobile1=1;
			                    }
			               }
			            });
				}else{
					$("#regist_mobile_msg").html("手机号码格式不正确！");
					checkMobile1=0;
					}
			}else{
				$("#regist_mobile_msg").html("");
				checkMobile1=1;
			}
		}
		if(email!=""&&email!=null){
				var originalEmail = $("#originalEmail").val();
				if(email!=originalEmail){
					var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
					var flag = pattern.test(email);
					if(flag){
						$.ajax({
				               type: "POST",
				               url: "/cloud-security-platform/regist_checkEmail.html",
				               data: {"email":email},
				               dataType:"json",
				               success: function(data){
				                    if(data.count>0){
				                        $("#regist_mobile_email_msg").html("您填写的邮箱已使用!");
				                        checkEmail1 = 0;
				                    }else{
				                   		$("#regist_mobile_email_msg").html("");
				                   		checkEmail1=1;
				                    }
				               }
				            }); 
					}else{
						$("#regist_mobile_email_msg").html("邮箱格式不正确！");
						 checkEmail1=0;
					}
				}else{
					$("#regist_mobile_email_msg").html("");
					checkEmail1=1;
				}
				
			}
	}else{
		$("#regist_mobile_email_msg").html("请输入手机或邮箱号码！");
		checkEmail1=0;
		checkMobile1=0;
		}
}

function checkUserData(){
	var mobile = $("#regist_phone").val();
	var email = $("#regist_email").val();
	checkName();
	checkMobileAndEmail();
	if(mobile!=null&&mobile!=""&&email!=null&&email!=""){
		if(checkName1==1&&checkMobile1==1&&checkEmail1==1){
			alert("保存成功！");
			$("#userdata").submit();
		}
	}else {
		if(checkName1==1&&(checkMobile1==1||checkEmail1==1)){
			alert("保存成功！");
			$("#userdata").submit();
		}
	}
}
</script> 
</head>

<body>
<div class="head_bj">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
    <div class="lagst">
      <div class="lagst-left"> <a href="${ctx}/userDataUI.html"><img src="${ctx}/source/images/ren.png" /></a> </div>
      <div class="lagst-right">
        <p><a href="${ctx}/userDataUI.html" style="color: #fff">${sessionScope.globle_user.name }</a></p>
        <p><a href="${ctx}/exit.html">退出</a></p>
      </div>
    </div>
    <div class="list">
      <ul>
        <ul>
          <li><a href="${ctx}/index.html">首页</a></li>
          <li><a href="###">我的订单</a></li>
          <li><a href="aider.html">在线帮助</a></li>
          <li class="list_active" style="border-right:1px solid #1369C0;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
        </ul>
      </ul>
    </div>
  </div>
</div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <div class="user_left">
    <ul class="user_1">
      <li style="font-size:16px; font-weight:500; line-height:28px; text-align:center;"><a  style="color:#4593fd; " href="${ctx}/userCenterUI.html">用户中心</a></li>
      <li class="active"><a href="${ctx}/userDataUI.html">基本资料</a></li>
      <li><a href="${ctx}/userBillUI.html">我的账单</a></li>
      <li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
      <h2>订购中心</h2>
      <li><a href="${ctx}/selfHelpOrderInit.html">自助下单</a></li>
      <li><a href="${ctx}/orderTrackInit.html">订单跟踪</a></li>
    </ul>
  </div>
  
  <!-- <jsp:include page="left.jsp"/>  -->
  
  <!--基本资料-->
  <div class="user_right" style="position:relative;">
    <div class="user_ren"><a href="###"><img src="${ctx}/source/images/admin_rw.png" /></a></div>
    <div class="user_table">
      <form id="userdata" action="${ctx}/saveUserData.html">
        <table>
          <tr class="register_tr">
            <td class="regist_title">用户名</td>
            <td class="regist_input">
             	<input type="hidden" id="originalName" value="${user.name}">
            	<input type="text" name="name" value="${user.name}" id="regist_name" class="regist_txt required"/>
            	<span id="regist_name_msg" style="color:red;float:left"></span>
            </td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">手&nbsp;&nbsp;&nbsp;机</td>
            <td class="regist_input">
            	<input type="hidden" id="originalMobile"  value="${user.mobile}"/>
            	<input type="text" name="mobile" value="${user.mobile}" id="regist_phone" class="regist_txt required" />
           		<span id="regist_mobile_msg" style="color:red;float:left"></span>
            </td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">邮&nbsp;&nbsp;&nbsp;箱</td>
            <td class="regist_input">
            	<input type="hidden" id="originalEmail" value="${user.email}"/>
            	<input type="text" name="email" value="${user.email}" id="regist_email" class="regist_txt required"/>
            	<span id="regist_mobile_email_msg" style="color:red;float:left"></span>
            </td>
          </tr>
        </table>
      <div class="user_sub"><a href="javascript:void(0)" onclick="checkUserData()"><img src="${ctx}/source/images/user_sub.png" /></a></div>
      </form>
    </div>
  </div>
</div>
<!--基本资料--> 
<!-- 尾部代码开始-->
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
</body>
</html>
