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
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/forgetPass.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<!-- add by 2016-02 -->
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/main.js"></script>
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
<!--头部-->
<div class="safe01 detalis-head">
	<!--头部-->
	<div class="head" style="width:100%">
		<div class="headBox">
			<div class="safeL fl" style="width:260px; margin-right:13%">
				<img src="${ctx}/source/images/portal/newlogo-footer.png" alt="" style="position:relative; top:4px;"/>
			</div>
			<div class="safem fl">
				<span class="fl"><a href="${ctx}/index.html">首页</a></span>
				<div class="Divlist listJs fl">
				<a href="#" class="hbule">我的安全帮<i></i></a>
							<ul class="list listl">
								<li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
								<li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
								<li style="border: none;"><a href="${ctx}/userDataUI.html">个人信息</a></li>
							</ul>
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
  <div class="pass_box">
    
    <div class="forget_form">
        <div class="pass_list">
         
          <span class="pass_login"><img src="${ctx}/source/images/forgetEmail.png" width="143" height="40" id="imageNumber" title="邮箱找回密码"  onclick="forgetPass('1');"/></img></span>
         <span class="pass_login"><img src="${ctx}/source/images/forgetPhone.png" width="143" height="40" id="imageNumber" title="手机找回密码" onclick="forgetPass('2');"/></img></span>
        </div>
      <div  id="phoneDiv" style="display:none">
      <form id="phoneForm" action="${ctx}/updatePass.html">
       <table align="center">
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
        <tr class="register_tr">
         <td class="regist_title"><span class="yzfs">图片验证码</span></td>
          <td class="regist_input"> <input type="text" class="login_txt lgoin_yzm required" name="checkNumber" id="checkNumber1"/>
          <span><img src="${ctx}/image.jsp" width="65" height="38" id="imageRegisterNumber" title="点击换一张" onclick="checkRegisterImage()"/></img></span>
          <span id="verification_Image_msg" style="color:red"></span>
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
        <table align="center">
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
</body>
</html>

