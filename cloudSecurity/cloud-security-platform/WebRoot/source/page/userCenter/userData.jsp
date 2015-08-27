<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>用户中心-基本资料</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/regist.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/userzezao.js"></script>
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

function checkName(){
	var name = $("#regist_name").val();
	var	pattern	= /^[a-zA-Z0-9_]{4,20}$/;
	var flag = pattern.test(name);
	if(name==""||name==null){
		$("#regist_name_msg").html("用户名不能为空");
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
                   		$("#regist_name_msg").html("用户名已经存在");
                   		checkName1=0;
                   }else{
                   		$("#regist_name_msg").html("");
                   		checkName1=1;
                   }
               },
            }); 
		}else{
			$("#regist_name_msg").html("请输入4-20位字符");
			checkName1=0;
		}
	}
}

function checkPwd(){
	var opassword =$("#opassword").val();
	var name = $("#originalName").val();
	$.ajax({
             type: "POST",
             url: "/cloud-security-platform/regist_checkPwd.html",
             data: {"name":name,"opassword":opassword},
             dataType:"json",
             success: function(data){
                 if(data.count==true){
                 		$("#editPassword_msg").html("原密码不正确");
                 }else{
                 		$("#editPassword_msg").html("");
                 }
             },
          }); 
}


function editPassword(){
	checkPwd();
	var opassword =$("#opassword").val();
	var p1 = $("#regist_password").val();
	var p2 = $("#regist_confirm_password").val();
	if(opassword==""||opassword==null||p1==""||p1==null||p2==""||p2==null){
		if(null==opassword||""==opassword){
			$("#editPassword_msg").html("<font color='red'>原密码不能为空</font>");
		}else{
			$("#editPassword_msg").html("");
		}
	    if(null==p1||""==p1){
			$("#regist_password_msg").html("密码不能为空");
		}else{
			$("#regist_password_msg").html("");
		}
		if(null==p2||""==p2){
			$("#regist_confirm_password_msg").html("<font color='red'>确认密码不能为空</font>");
		}else{
			$("#regist_confirm_password_msg").html("");
		}
	}else{
		if(p1.length<6||p1.length>20){
	        $("#regist_password_msg").html("请输入6-20位，支持中英文，数字，字符组合");
	    }else if(p1!=p2) {
	   		$("#regist_confirm_password_msg").html("<font color='red'>两次输入密码不一致，请重新输入</font>");
		}else{
		    $("#regist_confirm_password_msg").html("");
		    $("#editPassword").submit();
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
          <li><a href="${ctx}/chinas.html">安全态势</a></li>
          <li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
          <li><a href="${ctx}/aider.html">在线帮助</a></li>
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
            <td class="regist_title">密&nbsp;&nbsp;&nbsp;码</td>
            <td class="regist_input">
                <input type="hidden" id="originalPassword" value="${user.password}"/>
                <input type="password" value="******" disabled="true" id="regist_pwd" class="regist_txt required"/>
                <span id="regist_mobile_password_msg" style="color:red;float:left"></span>
            </td>
            <td class="regist_title"><div class="zc_edit" id="${user.id}" name="${user.name}" pwd="${user.password}" >修改</div></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">邮&nbsp;&nbsp;&nbsp;箱</td>
            <td class="regist_input">
            	<input type="hidden" id="originalEmail" value="${user.email}"/>
            	<input type="text" name="email" value="${user.email}" id="regist_email" class="regist_txt required"/>
            	<span id="regist_mobile_email_msg" style="color:red;float:left"></span>
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

<div id="box_mark"></div>
<div id="box_logoIn_edit">
  <div id="close_edit"></div>  <div class="text_1">
    <form id="editPassword" action="${ctx}/saveUserPassword.html" method="post">
    <div class="text_top">修改密码</div>
    <div class="text_bottm">
    <input type="hidden" name="id" id="hiddenEditUserid"/>
    <input type="hidden" name="hiddenEditUserName" id="hiddenEditUserName"/>
    <input type="hidden" name="hiddenEditUserPwd" id="hiddenEditUserPwd"/>
      <table style="margin-top:56px;width:630px">
        <tr>
          <td style="width:25%;">当前密码</td>
          <td style="width:37%;"><input class="boz_inout_1" style="height:30px;line-height:30px" type="password" name="opassword" id="opassword" onblur="checkPwd()"/></td>
          <td style="width:30%; text-align:left; color:#e32929;font-size:12px"><div id="editPassword_msg"></div></td>
        </tr>
        <tr>
          <td>新密码</td>
          <td style="width:37%;"><input class="boz_inout_1" style="height:30px;line-height:30px" type="password" name="password" id="regist_password" onblur="checkPassword()"/></td>
          <td style="width:30%; text-align:left; color:#e32929;font-size:12px"><div id="regist_password_msg"></div></td>
        </tr>
        <tr>
          <td>确认密码</td>
          <td><input class="boz_inout_1" style="height:30px;line-height:30px" type="password" name="confirm_password" id="regist_confirm_password" onblur="checkConfirmPassword()"/></td>
          <td style="color:#e32929;text-align:left"><div id="regist_confirm_password_msg"></div></td>
        </tr>
      </table>
    </div>
    <div style="margin-top:35px;"><a href="javascript:void(0)"><img src="${ctx}/source/images/user_submit_3.jpg" onclick="editPassword()"/></a></div>
  </div>
  </form>
</div>

</body>
</html>
