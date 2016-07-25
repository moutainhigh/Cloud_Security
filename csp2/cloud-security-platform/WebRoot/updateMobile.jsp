<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>个人中心-修改手机号码</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/source/css/login.css">
<script src="${ctx}/source/scripts/common/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/index.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script src="${ctx}/source/scripts/regist/updateMobile.js"></script>

</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        <strong style="font-size:20px; color:#fff; padding-left:10px;position:relative; top:-10px; font-weight:normal;">个人中心</strong>
					</div>
					<div class="safem fl">
						<span class="fl"><a href="${ctx}/index.html">首页</a></span>
						
						<!-- 商品分类 start -->
						<c:import url="/category.html"></c:import>
						<!-- 商品分类 end -->
						
						<span class="fl"><a href="${ctx}/knowUs.html">关于我们</a></span>
						<span class="fl shopping" style="margin-right:0">
							<a href="${ctx}/showShopCar.html"><i></i>购物车</a>
						</span>
					</div>
					<div class="safer fr" style="margin-left:0px;">
						<!-- 如果已经登录则显示用户名，否则需要登录 -->
				         <c:if test="${sessionScope.globle_user!=null }">
				         <div class="clearfix">
					        <a href="${ctx}/userCenterUI.html"  class="fl loginname">${sessionScope.globle_user.name }</a>
					        <em class="fl">|</em>
					        <a href="${ctx}/exit.html" class="fl" >退出</a>
					      </div>
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
		<div class="core clearfix" style="margin-bottom:524px;">
        	<div class="coreLeft fl">
            	<h3><i></i>个人中心</h3>
                <dl>
                	<dt>交易管理</dt>
                    <dd><a href="${ctx}/orderTrackInit.html">我的订单</a></dd>
                    <!-- <dd><a href="#">我的优惠劵</a></dd> -->
                    <dd><a href="${ctx}/balanceUI.html">安全币</a></dd>
                    <dt>个人信息管理</dt>
                    <dd><a href="${ctx}/userDataUI.html" class="active">个人资料</a></dd>
                    <dd style="border-bottom:none;"><a href="${ctx}/userAssetsUI.html">我的资产</a></dd>
                </dl>
            </div>
        	<div class="coreRight fl">
            	<div class="dataBox">
                	<div class="data">
                    	<h2>修改手机号码</h2>
                    </div>
                    <div class="passwordform">
                    	<div class="step">
                        <!--修改-->
                            <p class="step_top">
                                <span class="sl" style="border-top:#95cfff solid 1px;">
                                    <i style=" background-color:#95cfff"></i>
                                </span>
                                <span class="sr" style="border-top:1px solid #929292">
                                    <i class="r0" style="background-color:#2499fb""></i>
                                    <i class="r1" style="left:538px;"></i>
                                </span>
                            </p>
                            <p class="step_bottom">
                                <em class="sone" style="color:#95cfff">1验证身份</em>
                                <em class="sone" style="color:#2499fb;left:238px">2修改手机号码</em>
                                <em class="stwo" style="left:526px">3完成</em>
                            </p>
        
                        </div>
                        <form method="post" id="mobileForm" action="${ctx}/confirmMobile.html">
                         <input type="hidden" id="originalMobile" value="${originalMobile}"/>
                        <ul class="passwordlist" style="padding-bottom:30px; width:458px; margin:0 auto">
                        	<li class="clearfix">
                            	<label class="fl">输入新手机号码</label>
                                <div class="fl passwordr">
                                    <input type="text"  class="text" name="mobile" id="phone_code" onblur="checkMobileNum()"/>
                                    <i id="phone_code_flag" ></i>
                                </div>
                                <div class="prompt fl" id="phone_code_prompt"><b></b></div>
                            </li>
                            <li class="clearfix">
                            	<label class="fl">验&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 码</label>
                                <div class="fl passwordr">
                                	<input type="text" class="text fl" style="width:168px;" name="verification_phone" id="verification_phone" onblur="checkActivationCode()" />
                                	<i id="verification_phone_flag" style="margin-right:130px;"></i>
                                    <a href="#" class="btn fl" id="phone_yzm" onclick="checkSendMobile()">获取验证码</a>
                                </div>

                                <div class="prompt fl" id="verification_phone_prompt"><b></b></div>
                            </li>
                             <li class="clearfix" style="margin-top:30px;">
                            	<label></label>
                                <div class="fl passwordr" style="margin-left:135px;">
                                	<input type="button" class="submit" value="提&nbsp;&nbsp;交" onclick="updateMobile()" />
                                </div>
                            </li>
                            
                        
                        </ul>
                        </form>
                    </div>
                </div>
            
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
                        	<dd>QQ交流群<br/>470899318</dd>
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
		</div>
		<!-- <div class="foot">
			<p>Label@1234567890北京电信研究院<br>
				www.anquanbang.net
			</p>
		</div>-->
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
           	<img src="${ctx }/source/images/portal/weixin.jpg" alt="" />
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</body>

</html>