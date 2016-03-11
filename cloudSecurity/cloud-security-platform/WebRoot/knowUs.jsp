<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>安全帮-中国电信云安全服务在线商城</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<style>
body,html{
	height: 100%;
	width: 100%;
}
.helph{height:30px;font-size:18x;line-height:30px;padding-left:20px;color:#666;padding-top:20px;
	width: 70%;
margin: 10px 26%;
}
.helphn{
	height:30px;font-size:20px !important;line-height:30px;padding-left:20px;color:#666;padding:20px 0 10px 0;
	text-align: center;
	margin-bottom:0 !important;
}
.help_detail{margin: 10px 0 0 20px;color: #888;font-size: 16px; text-indent:2em;line-height:30px;
	width: 70%;
margin: 0 26%;
}
.hr{width:70%;height:1px;background:#666;margin:0 auto;margin-bottom:20px;overflow: hidden; }

</style>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl">
						<img src="${ctx}/source/images/portal/logo.png" alt=""/>
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

		</div>
		
		
		<div class="safe02">
			
			<div class="imgBox">
				  <div class="user_right" style="width: 942px;border:1px solid #e0e0e0; margin:0 auto;">
			  	  <a name="1F">
				  <div id="f1" class="dd">
				    <p class="help_detail">安全帮，</p>
				    <p class="help_detail">是一个全自助式安全服务电子商城，</p>
				    <p class="help_detail">2015年诞生于中国电信北京研究院安全技术与应用产品线。</p><br/>
				    
				    <p class="help_detail">在这里，</p>
				    <p class="help_detail">你可以像在线购买普通商品那样，</p>
				    <p class="help_detail">购买和使用及时生效的安全服务。</p>
				    <p class="help_detail">这样，</p>
				    <p class="help_detail">你不用再购买昂贵的安全设备，</p>
				    <p class="help_detail">不用再部署高大上的安全设备，</p>
				    <p class="help_detail">不用再熬夜看使用手册，</p>
				    <p class="help_detail">你要做的，就是：</p>
				    <p class="help_detail">轻点鼠标，立享服务，</p>
				    <p class="help_detail">晴空万里，一切都好！</p><br/>
				    
				    <p class="help_detail">这是一个美好的理想，</p>
				    <p class="help_detail">也是我们的梦想。</p>
				    <p class="help_detail">梦想是远大的，如何实现呢？</p>
				    <p class="help_detail">我们决定先上线“网站安全帮”：</p>
				    <p class="help_detail">为370万个网站提供7*24小时的安全监测及预警服务。</p>
				    <p class="help_detail">我们的梦想，</p>
				    <p class="help_detail">为了让那些苦逼的网管不再那么疲倦，</p>
				    <p class="help_detail">让他们可以睡一次安稳觉。</p><br/>
				    
				    <p class="help_detail">也许，我们的梦想就是：</p>
				    <p class="help_detail">我们希望可以让网管不再那么苦逼，</p>
				    <p class="help_detail">我们希望可以让企业不再那么花钱，</p>
				    <p class="help_detail">我们希望可以让安全不再那么遥远…….</p><br/>
				    
				    <p class="help_detail">我们刚开始，</p>
				    <p class="help_detail">做的还很不好，</p>
				    <p class="help_detail">我们在路上，</p>
				    <p class="help_detail">为了梦想努力的向前走，</p>
				    <p class="help_detail">我们相信：我们会做得更好，</p>
				    <p class="help_detail">因为我们的梦想，</p>
				    <p class="help_detail">因为我们的坚持，</p>
				    <p class="help_detail">因为我们的专业。</p><br/>
				    <div class="hr"></div>
				    <p class="help_detail">“安全帮”——全自助式安全服务在线商城，是中国电信基于<br/>
				                                               专业安全能力自主研发的云安全服务平台，旨在解决企业面临的安<br/>
				                                               全厂商多、安全产品繁、安全投资大、安全人才缺等四大问题，为<br/>
				                                               用户提供一站式安全服务。 用户通过在线注册购买，即可享受及时、<br/>
				                                               在线、智能、便捷的安全服务，全面保障业务安全。</p><br/>
				                                               
				  </div>
				  </a>
			      
			  
			  </div>
			</div>
		</div>
		
		<div class="safe03">
			<div class="imgBox" id="carrousel" style="width: 1190px;">
				<h2><i></i>合作伙伴<i></i></h2>
                
                <div class="friend-Link ">
                    <div class="left-arrow"><a href="javascript:;"><img src="${ctx}/source/images/portal/left.png"></a></div>
                    <div class="fl-pic">
                      <ul class="imgist clearfix">
                        <li><a href="#"><img src="${ctx}/source/images/portal/ico11.png" alt=""></a></li>
                        <li><a href="#"><img src="${ctx}/source/images/portal/ico12.png" alt=""></a></li>
                        <li><a href="#"><img src="${ctx}/source/images/portal/ico13.png" alt=""></a></li>
                        <li><a href="#"><img src="${ctx}/source/images/portal/ico14.png" alt=""></a></li>
                        <li><a href="#"><img src="${ctx}/source/images/portal/ico15.png" alt=""></a></li>
                        <!--<li><a href="#"><img src="imges/ico13.png" alt=""></a></li>
                        <li><a href="#"><img src="imges/ico14.png" alt=""></a></li>
                        <li><a href="#"><img src="imges/ico15.png" alt=""></a></li>-->
                      </ul>
                    </div>
                    <div class="right-arrow"><a href="javascript:;"><img src="${ctx}/source/images/portal/right.png"></a></div>
                  </div>
        
			</div>
		</div>
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
	

</body>
<script>
$(function(){	
	$('#carrousel').hover(function(){
			$('.left-arrow').fadeIn();
			$('.right-arrow').fadeIn();
		},function(){
			$('.right-arrow').fadeOut();
			$('.left-arrow').fadeOut();
		})
	
	$(".fl-pic").slidelf({
			"prev":"left-arrow",
			"next":"right-arrow",
			"speed":300 //时间可以任意调动  以毫秒为单位
		});
		
	})
	
</script>

</html>
