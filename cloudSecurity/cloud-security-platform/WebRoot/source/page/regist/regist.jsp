<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>登录-注册</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/loginregist.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/login.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/regist.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/registzezao.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script type="text/javascript">
$(document).ready(function(){
 var flag = '<%= request.getAttribute("flag") %>';
	if(flag=="zc"){
		$('.user_regist').siblings().removeClass('cur');
		$('.user_regist').addClass('cur');
		$('.login_form').hide();
		$('.regist_form').show();
	}else{
		$('.user_login').siblings().removeClass('cur');
		$('.user_login').addClass('cur');
		$('.login_form').show();
		$('.regist_form').hide();
		//$("#login_name").attr("value",'');
		//$("#login_password").attr("value",'');
		//$("#remeberMe").attr("checked",false);
	}
});

$(document).ready(function(){
	$(".footer").load("footer.html");
});
function loginSubmit(){
	var name = $("#login_name").val();
	var password = $("#login_password").val();
	var checkNumber = $("#checkNumber").val();
	if(name==null||name==""||password==null||password==""||checkNumber==null||checkNumber==""){
		if(name==null||name==""){
			$("#login_name_msg").html("请输入用户名");
		}else{
			$("#login_name_msg").html("");
		}
		if(password==null||password==""){
			$("#login_password_msg").html("请输入密码");
		}else{
			$("#login_password_msg").html("");
		}
		if(checkNumber==null||checkNumber==""){
			$("#login_checkNumber_msg").html("请输入验证码");
		}else{
			$("#login_checkNumber_msg").html("");
		}
	}else{
			$("#form_login").submit();
	}
}


function checkNumberImage(){
	var imageNumber = document.getElementById("imageNumber");
	imageNumber.src = "${pageContext.request.contextPath}/image.jsp?timestamp="+new Date().getTime();
}

function notagree(){
    window.location.href = "${pageContext.request.contextPath}/loginUI.html";
}
</script>

<script language="javascript"> 
	window.load = function(){ 
	document.getElementById('login_password').value=''; 
	}; 
</script>
<script type="text/javascript">
        function on_return(){
          if(window.event.keyCode == 13){
           if ($("#login_btn")!=null){
        	   $("#login_btn").click();
              }
          }
         }
</script>
</head>
<body onkeydown="on_return();">
<!--头部-->
<div class="safe01 detalis-head">
	<!--头部-->
	<div class="head" style="width:100%">
		<div class="headBox">
			<div class="safeL fl" style="width:275px; margin-right:18%">
				<img src="${ctx}/source/images/portal/newlogo-footer.png" alt="" style="position:relative; top:4px;"/>
                      <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px;">网站安全帮</strong>
			</div>
			<div class="safem fl">
				<span class="fl"><a href="${ctx}/index.html">首页</a></span>
				<div class="Divlist listJs fl">
					<a href="${ctx}/orderTrackInit.html">我的安全帮<!--<i></i>--></a>
					<!--<ul class="list listl">
						<li><a href="#">我的订单</a></li>
						<li><a href="#">我的资产</a></li>
						<li style="border: none;"><a href="#">个人信息</a></li>
					</ul>-->
				</div>
				<span class="fl ask">
					<a href="#" class="hbule">手机APP</a>
					<b style="display:none"><img src="${ctx}/source/images/portal/apk.png" alt=""></b>
				</span>
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
	

</div>
<!-- 头部代码结束-->
<div class="login_wrap">
  <div class="login_box">
    <div class="login_title">
      <ul>
        <li class="user_login cur">用户登录</li>
        <li class="user_regist">用户注册</li>
      </ul>
    </div>
    <div class="login_form">
      <form  id="form_login" name="form_login" action="${ctx}/login.html" method="post">
        <div class="login_list">
          <input type="text" class="login_txt required" id="login_name" name="name" value="${requestScope.name }" autocomplete="off"/>
       	  <span id="login_name_msg" style="color:red;float:left"></span>
        </div>
        <div class="login_list">
          <!-- <input type="hidden" name="val"/> -->
          <input type="password" class="login_txt login_pass" id="login_password" name="password" value="${requestScope.password }" onfocus="this.type='password'" autocomplete="off"/>
          <span id="login_password_msg" style="color:red;float:left"></span>
        </div>
        <div class="login_list">
          <input type="text" class="login_txt lgoin_yzm required" name="checkNumber" id="checkNumber"/>
          <span><img src="${ctx}/image.jsp" width="65" height="38" id="imageNumber" title="点击换一张" onclick="checkNumberImage()"/></img></span>
          <span id="login_checkNumber_msg" style="color:red"></span>
        </div>
        <span style="color:red" >${msg}</span>
        <div class="login_list">
          <input type="checkbox" class="login_checkbox" name="remeberMe" id="remeberMe" value="yes" ${requestScope.checked }/>
          <span class="auto_login">记住密码</span>
          <a href="${ctx}/forgetPass.html">忘记密码</a> 
        </div>
        <input type="button" class="login_btn" id="login_btn" onclick="loginSubmit()" value="登　　录"/>
      </form>
    </div>
    
    
    
    
    <div class="regist_form">
      <form  id="form_regist" action="${ctx}/registToLogin.html" name="form_regist" method="post">
        <table>
          <tr class="register_tr">
            <td class="regist_title">用户名</td>
            <td class="regist_input"><input type="text" class="regist_txt required" name="name" id="regist_name" onblur="checkName()" /><span id="regist_name_msg" style="color:red;float:left"></span></td>
            <td class="regist_prompt">支持4-20位字母、数字、下划线及其组合</td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">设置密码</td>
            <td class="regist_input"><input type="password" class="regist_txt required" name="password" id="regist_password" onblur="checkPassword()"/><span id="regist_password_msg" style="color:red;float:left"></td>
            <td class="regist_prompt">请输入6-20位，支持中英文，数字，字符组合</td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">确认密码</td>
            <td class="regist_input"><input type="password" class="regist_txt required" name="confirm_password" id="regist_confirm_password" onblur="checkConfirmPassword()" autocomplete="false"/><span id="regist_confirm_password_msg" style="color:red;float:left;"></td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title"><span class="yzfs">手机号码</span></td>
            <td class="regist_input" id="yzbox">
            <input type="text" class="regist_txt required" name="mobile" id="regist_phone" onblur="checkMobile()" style="margin-right:20px"/>
            <span id="regist_mobile_email_msg" style="color:red;float:left;display:block;"></td>
            <td class="regist_prompt">或<a class="forget_pass" id="get_email_yzm">验证邮箱</a><a class="forget_pass" id="get_phone_yzm">验证手机</a></td>
          </tr>
         <tr class="register_tr">
         <td class="regist_title"><span>验证码</span></td>
          <td class="regist_input"> <input type="text" class="login_txt lgoin_yzm required" name="checkNumber" id="checkNumber1"/>
          <span><img src="${ctx}/image.jsp" width="65" height="38" id="imageRegisterNumber" title="点击换一张" onclick="checkRegisterImage()"/></img></span>
          <span id="verification_Image_msg" style="color:red"></span>
          </td>
         </tr>
         <tr class="register_tr">
            <td class="regist_title"><span class="yzfsadd">手机验证码</span></td>
            <td class="regist_input"><input type="text" class="regist_txt regist_yzm required" name="verification_code" id="verification_code" onblur="checkEmailActivationCode()"/>
              
           	  <span id="verification_code_msg" style="color:red;float:left">
            </td>
            <td>
              <input class="regist_yzm_btn" type="button" id="phone_yzm" value="获取短信验证码" onclick="checkSendMobile()"/>
              <input class="regist_yzm_btn" type="button" id="email_yzm" value="获取邮箱验证码" onclick="checkSendEmail()"/>
            </td>
            
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title"></td>
            <td><input type="checkbox"  class="login_checkbox" id="ck"/>
              <span class="regist_yes">我已阅读并同意</span><a href="javascript:void(0)" class="forget_pass" id="zc_regist">《云平台用户注册协议》</a>
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
<%-- <div class="bottom_bj">
<div class="bottom">
<div class="bottom_main">
  <h3><a href="###">新手入门</a></h3>
  <ul>
   <li><a href="${ctx}/registUI.html">新用户注册</a></li>
    <li><a href="${ctx}/loginUI.html">用户登录</a></li>
    <li><a href="${ctx}/forgetPass.html">找回密码</a></li>
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
    <li><a href="###">绿盟</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">华为</a></li>
  </ul>
</div>
<div  class="bottom_main">
<h3><a href="###">联系我们</a></h3>
<ul>
<li><a href="###">QQ交流群470899318</a></li>
</div>
<div  class="bottom_main" style="width:380px;">
<h3><a href="###">版权信息</a></h3>
 <ul>
 <li>Copyright&nbsp;©&nbsp;2015 中国电信股份有限公司北京研究院<br />
京ICP备12019458号－10</li>
</div>
</div>
</div> --%>
<div class="safeBox">
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<!--修改-->
				   <a href="#">
		               <img src="${ctx}/source/images/portal/logo footer.png" alt="">
	                   <i class="" style="height:35px; color:#b3b4b5; width:1px; display:inline-block;">|</i>
		               <img src="${ctx}/source/images/portal/newlogo-footer.png" alt="">
                   </a>
                	<!--修改-->
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


<div id="box_mark"></div>
<div id="box_logoIn_regist">
  <div id="close_edit"></div>  <div class="text_1">
    <form id="" action="${ctx}/saveUserPassword.html" method="post">
    <div class="text_top" style="text-align:center;">云安全服务平台用户协议</div>
      <div id="f1" class="dd" style="overflow:auto;height:430px;width:870px;margin-top:15px;">
        <p class="help_detail">中国电信股份有限公司北京研究院（以下简称"电信北研院"）同意按照本服务条款的规定基于云安全服务平台（以下简称“平台”）提供“WEB网站云安全服务”。为获得“WEB网站云安全服务”，服务使用人（以下称"用户"）应当同意本协议，才能完成申请程序并通过平台的审核。用户在向平台提交审核申请时，首先签订了该用户协议后才可提交申请。</p>
        <h3 class="helph">1. 服务内容</h3>
        <p class="help_detail">1.1 云安全服务平台是电信北研院为网站提供主动保护的服务平台，所提供的服务包括但不限于：漏洞扫描、恶意代码监测、网页篡改监测、关键字监测、可用性监测。</p>
        <p class="help_detail">1.2 云安全服务平台所提供的服务不得对用户的网站造成任何破坏。</p>
        <h3 class="helph">2. 服务规则</h4>
        <p class="help_detail">2.1 网站管理员权限验证</p>
        <p class="help_detail">2.1.1 用户在申请“WEB网站云安全服务”时，应提供网站管理员真实、完整和准确的信息及证明材料。如因故意或过失未向电信北研院提供真实、完整和准确的信息，导致电信北研院验证错误，造成相关各方损失的，由用户承担一切责任。</p>
        <p class="help_detail">2.1.2 用户通过了管理员权限验证，意味着默认确认了此条款。</p>
        <p class="help_detail">2.2 WEB网站云安全服务</p>
        <p class="help_detail">2.2.1 WEB网站云安全服务标准以不损害用户网站正常使用为准则，由电信北研院拟定</p>
        <p class="help_detail">2.2.2用户在使用" WEB网站云安全服务"过程中，用户会收到云安全服务平台的检测结果邮件或短信通知。</p>
        <p class="help_detail">2.2.3 由于涉及到对网站的安全情况的监控，因此电信北研院有权在用户提交验证申请后，自动将用户申请的网站进行挂马，篡改和漏洞扫描的监控。用户通过了管理员权限验证，就表明确认了此条款。</p>
        <h3 class="helph">3. 用户信息保护</h4>
        <p class="help_detail">3.1平台不得泄露用户的保密信息（不包括查询页面需要公布的信息），更不得以任何缘由对用户的隐私信息进行公布。</p>
        <p class="help_detail">3.2 电信北研院制定了以下四项隐私权保护原则：</p>
        <p class="help_detail">3.2.1 利用我们收集的信息为用户提供有价值的产品和服务。</p>
        <p class="help_detail">3.2.2 开发符合隐私权标准和隐私权惯例的产品。</p>
        <p class="help_detail">3.2.3 将个人信息的收集透明化，并由权威第三方监督。</p>
        <p class="help_detail">3.2.4 尽最大的努力保护我们掌握的信息。</p>
        <h3 class="helph">4. 版权声明</h4>
        <p class="help_detail">4.1 电信北研院对其所提供的验证服务内容，包括但不限于文字、软件、数据库、声音、图片、录像、图表等，拥有所有权和使用权。所有这些内容受版权、商标、专利和其它财产所有权法律的保护。</p>
        <p class="help_detail">4.2 任何人不得擅自以非法的方式传播、修改和使用本网站所提供的内容。</p>
        <h3 class="helph">5. 免责声明</h4>
        <p class="help_detail">5.1 用户知悉并理解，“WEB网站云安全服务”经过详细的测试，但不能保证与所有的软硬件系统完全兼容，不能保证本服务完全没有错误，如果出现不兼容及服务错误的情况，用户可寻求技术支持以解决问题。电信北研院就此情形不承担任何责任。</p>
        <p class="help_detail">5.2 用户明确同意其免费使用"“WEB网站云安全服务"所存在的风险将完全由其自己承担；在适用法律允许的最大范围内，对因使用或不能使用"“WEB网站云安全服务"所产生的损害及风险，包括但不限于直接或间接的个人损害、商业赢利的丧失、贸易中断、商业信息的丢失或任何其它经济损失，电信北研院不承担任何责任。</p>
        <p class="help_detail">5.3电信北研院不担保提供的功能及服务一定能满足用户的要求，也不担保服务不会中断，对服务的及时性、安全性、真实性、准确性都不作担保。</p>
        <p class="help_detail">5.4对于因运营商系统或互联网网络故障、计算机故障或病毒、信息损坏或丢失、计算机系统问题或其它任何不可抗力原因而产生损失，电信北研院不承担任何责任。</p>
        <p class="help_detail">5.5用户违反本协议规定，对电信北研院造成损害的，电信北研院有权采取包括但不限于中断使用许可、停止提供服务、限制使用、法律追究等措施。</p>
        <h3 class="helph">6. 条款修改</h4>
        <p class="help_detail">6.1 电信北研院将有权随时修改本条款的有关内容，一旦本条款的内容发生变动，平台将通过适当方式作解释说明。</p>
        <p class="help_detail">6.2 如果用户不同意电信北研院对本条款相关内容所做的修改，有权停止使用"“WEB网站云安全服务"。如果用户继续使用"“WEB网站云安全服务"，则视为接受电信北研院对本协议相关内容所做的修改。</p>
        <p class="help_detail" style="text-align:center;">本条款的更新修改及最终解释权归中国电信股份有限公司北京研究院所有。</p>
      </div>
      <input type="hidden" name="agreeId" id="agreeId"/>
    <div style="margin-top:35px;text-align:center;"><a href="javascript:void(0)"><img src="${ctx}/source/images/agree.png" id="agree"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="${ctx}/source/images/notagree.png" onclick="notagree()"/></a></div>
  </div>
  </form>
</div>

</body>
</html>

