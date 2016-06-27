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
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<style>
.rich_media_meta_list{
	height:40px;
	line-height:40px;
}
.rich_media_meta_nickname{
	color: #607fa6;
    text-decoration: none;
}
.rich_media_content p{
    line-height: 24px;
    font-size: 14px;
    color: #343434;
	
}
.rich_media_inner{
	padding:0px;
}
.understand img{
    padding: 20px 0;
}
</style>
<script>
$(function(){
	$('.understand p').css('text-align','left');	
})

</script>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
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
				         <div class="login clearfix">
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
        <!--主题内容-->
		<div class="understand" style="width:740px; margin:0 auto">
        	<div class="rich_media_inner">
            <div id="page-content">
                <div id="img-content" class="rich_media_area_primary">
                    <!-- <h2 class="rich_media_title" id="activity-name">
                        	安全帮，帮一下就好了！ 
                    </h2>
                    <div class="rich_media_meta_list">
					   <em id="post-date" class="rich_media_meta rich_media_meta_text">2016-04-12</em>

                       <em class="rich_media_meta rich_media_meta_text">安全帮团队</em>
                       <a class="rich_media_meta rich_media_meta_link rich_media_meta_nickname" href="javascript:void(0);" id="post-user">安全帮</a>
                       <span class="rich_media_meta rich_media_meta_text rich_media_meta_nickname">安全帮</span> -->

                        <div id="js_profile_qrcode" class="profile_container" style="display:none;">
                            <div class="profile_inner">
                                <strong class="profile_nickname">安全帮</strong>
                                <img class="profile_avatar" id="js_profile_qrcode_img" src="" alt="">

                                <p class="profile_meta">
                                <label class="profile_meta_label">微信号</label>
                                <span class="profile_meta_value">anquanbang_123456</span>
                                </p>

                                <p class="profile_meta">
                                <label class="profile_meta_label">功能介绍</label>
                                <span class="profile_meta_value">安全帮，全自助式安全服务在线商城，是中国电信基于专业安全能力自主研发的云安全服务平台，旨在解决企业面临的安全厂商多、安全产品繁、安全投资大、安全人才缺等四大问题，为用户提供一站式安全服务。</span>
                                </p>
                                
                            </div>
                            <span class="profile_arrow_wrp" id="js_profile_arrow_wrp">
                                <i class="profile_arrow arrow_out"></i>
                                <i class="profile_arrow arrow_in"></i>
                            </span>
                        </div>
                    </div>
                    
                    
                    
                    
                                                            
                                        <div class="rich_media_thumb_wrp" id="media">
                        
                                               <img class="rich_media_thumb" id="js_cover" onerror="this.parentNode.removeChild(this)" data-backsrc="http://mmbiz.qpic.cn/mmbiz/fPY475147RiaQaS0G2DOHr7kZN9FBcfqa3ia3Nnx8ib38rRELAsic6CserTCqkzoS2QGfhPXxwSR5SJ8a8OmqlHmIA/0?wx_fmt=jpeg" data-s="300,640" src="${ctx}/source/images/knowUs/1.jpg" alt="">
                                            </div>
                                        
                    
                    <div class="rich_media_content " id="js_content">
                        
                        <p>安全帮，</p><p>www.anquanbang.net</p><p>是一个全自助式安全服务云商城，</p><p>诞生于中国电信北京研究院安全技术与应用产品线。<img data-s="300,640" data-type="png"  src="${ctx}/source/images/knowUs/2.jpg" style="width: auto !important; visibility: visible !important; height: auto !important;"><br></p><p>在安全帮，</p><p>你可以像在线购买普通商品那样，</p><p>购买和使用及时生效的安全服务。</p><p>这样，</p><p>你不用再购买昂贵的安全设备，</p><p>不用再部署高大上的安全设备，</p><p>不用再熬夜看使用手册，</p><p>你要做的，就是：</p><p>轻点鼠标，立享服务，</p><p>晴空万里，一切都好！</p><p style="text-align: center;"><img data-s="300,640" data-type="png"  src="${ctx}/source/images/knowUs/7.jpg" style="width: auto !important; visibility: visible !important; height: auto !important;"><br></p><p style="text-align: center;"><img data-s="300,640" src="${ctx}/source/images/knowUs/3.png" style="width: auto !important; visibility: visible !important; height: auto !important;"><br></p><p>这是一个美好的理想，</p><p>也是我们的梦想。</p><p>梦想是远大的，如何实现呢？</p><p>我们决定先上线“网站安全帮”：</p><p>为370万个网站提供7*24小时的安全监测及预警服务。</p><p>我们的梦想，</p><p>为了让那些苦逼的网管不再那么疲倦，</p><p>让他们可以睡一次安稳觉。</p><p style="text-align: center;"><img data-s="300,640"  src="${ctx}/source/images/knowUs/6.jpg" style="width: auto !important; visibility: visible !important; height: auto !important;"><br></p><p>也许，我们的梦想就是：</p><p>我们希望可以让网管不再那么苦逼，</p><p>我们希望可以让企业不再那么花钱，</p><p>我们希望可以让安全不再那么遥远…….</p><p style="text-align: center;"><img data-s="300,640"  src="${ctx}/source/images/knowUs/5.jpg" style="width: auto !important; visibility: visible !important; height: auto !important;"><br></p><p>我们刚开始，</p><p>做的还很不好，</p><p>我们在路上，</p><p>为了梦想努力的向前走，</p><p>我们相信：我们会做得更好，</p><p>因为我们的梦想，</p><p>因为我们的坚持，</p><p>因为我们的专业。</p><p style="text-align: left;"><img data-s="300,640" data-type="png"  src="${ctx}/source/images/knowUs/4.png" style="width: auto !important; visibility: visible !important; height: auto !important;"><br><span style="color: rgb(61, 170, 214);"><strong>关于安全帮</strong></span></p><section class="pEditor" data-id="1660"><section style="padding-right: 5px; padding-left: 5px; line-height: 10px; color: inherit; border: 1px solid rgb(30, 154, 225);margin-bottom:10px;"><section style="color: inherit; height: 8px; margin-top: -8px; margin-left: 140px; width: 50%; background-color: rgb(254, 254, 254);">
                        <section style="width: 8px; height: 8px; border-radius: 100%; line-height: 0; box-sizing: border-box; font-size: 18px; text-decoration: inherit; border-color: rgb(135, 190, 222); display: inline-block; color: rgb(255, 255, 238); background-color: rgb(30, 154, 225);"></section>
                        </section>
                        <section class="pBrush" style="line-height: 2em; color: rgb(62, 62, 62); font-size: 14px; margin: 15px;">
                        <p>www.anquanbang.net</p><p>安全帮，全自助式安全服务云商城。</p><p>
                        <span style="line-height: 2em;">
                        是中国电信基于专业安全能力打造的云安全服务平台，是安全服务与产品集结的一个云平台。用户通过在线自助注册购买，即可享受及时、在线、智能、便捷的安全服务。
                        </span><br></p><p>旨在解决企业面临的安全厂商多、安全产品繁、安全投资大、安全人才缺等四大问题，省时省力省钱。</p><p>
                        <span style="line-height: 2em;"><br></span></p><p>
                        <span style="line-height: 2em;">“安全帮”v1.0之“网站安全帮”已上线公测，首推5大网站安全监测及预警服务：WEB漏洞扫描、网页挂马监测、网页篡改监测、敏感内容监测、可用性监测。安全帮进行7*24小时监测，一旦发现问题，及时短信和邮件通知用户。</span><br></p><p>网络安全帮、数据库安全帮、系统安全帮、移动安全帮的相关服务会陆续上线提供。</p></section><section style="color: inherit; text-align: right; height: 10px; margin-bottom: -4px; margin-left: 25px; width: 65%; background-color: rgb(254, 254, 254);"><section style="margin-right: auto; margin-bottom: 1px; margin-left: auto; border-radius: 100%; line-height: 1; box-sizing: border-box; text-decoration: inherit; border-color: rgb(135, 190, 222); display: inline-block; height: 8px; width: 8px; color: rgb(255, 255, 238); background-color: rgb(30, 154, 225);"></section></section></section></section>
                    </div>
                    
                    <link rel="stylesheet" type="text/css" href="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/page/appmsg/page_mp_article_improve_combo2d1390.css">
                    
                    
                                        
                                        
                                        <div class="rich_media_tool" id="js_toobar3">
                                                                    <div id="js_read_area3" class="media_tool_meta tips_global meta_primary" style="display:none;">阅读 <span id="readNum3"></span></div>

                        <span style="display:none;" class="media_tool_meta meta_primary tips_global meta_praise" id="like3">
                            <i class="icon_praise_gray"></i><span class="praise_num" id="likeNum3"></span>
                        </span>

                        <a id="js_report_article3" style="display:none;" class="media_tool_meta tips_global meta_extra" href="javascript:void(0);">投诉</a>

                    </div>



                                    </div>

                <div class="rich_media_area_primary sougou" id="sg_tj" style="display:none">

                </div>

                <div class="rich_media_area_extra">

                    
                                        <div class="mpda_bottom_container" id="js_bottom_ad_area">
                        
                    </div>
                                        
                    <div id="js_iframetest" style="display:none;"></div>
                                        
                                    </div>
               
            </div>
           
        </div>
        </div>
        
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="${ctx}/index.html">
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
                        	<dd>QQ交流群<br>470899318</dd>
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
           	<img src="${ctx}/source/images/portal/weixin.jpg" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</body> 

</html>
