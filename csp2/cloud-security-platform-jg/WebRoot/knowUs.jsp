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
	$('.understand p').css('width','640px');		
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
                    <div class="rich_media_content " id="js_content">
                        <p style="padding-left:205px;font-family:FZShuTi;font-size:22px;margin-top:28px;margin-bottom:21px;">安全帮，帮一下就好了！</p>
                        <p style="padding-left:220px;">安全帮，www.anquanbang.net</p>
                        <p style="padding-left:170px;">中国电信基于专业安全能力打造的云安全服务平台</p>
                        <p style="padding-left:170px;">诞生于中国电信北京研究院安全技术与应用产品线</p>
                        <img data-s="300,640" data-type="png"  src="${ctx}/source/images/knowUs/14.jpg" style="width: 640px; visibility: visible !important; height: 240px;"/><br></p>
                        <p style="font-weight:bold;font-family:SimHei">安全帮<span style="font-size:20px;color:#FFCC00;line-height:30px;">“1+4”</span>产品体系：</p>
                        <p style="font-size:15px;color:#FFCC00;line-height:40px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“1”：SaaS云安全服务商城</p>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;全自助式安全服务云商城，为企业用户提供专业的云化安全服务和安全能力API。</p>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户通过在线注册购买，即可享受及时、在线、智能、便捷的安全服务。安全帮的创立，旨在解决企业面临的安全厂商多、安全产品繁、安全投资大、安全人才缺等四大问题，为企业省时省力省钱。</p>
                        <p style="text-align: center;"><img data-s="300,640" data-type="png"  src="${ctx}/source/images/knowUs/2.jpg" style="width: auto !important; visibility: visible !important; height: auto !important;"><br></p>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在安全帮，你可以像在线购买普通商品那样，购买和使用及时生效的安全服务。这样，你不用再购买昂贵的安全设备，不用再部署高大上的安全设备，不用再熬夜看使用手册，你要做的，就是：轻点鼠标，立享服务，晴空万里，一切都好！ </p>
                        <img data-s="300,640" data-type="png"  src="${ctx}/source/images/knowUs/16.jpg" style="width: 640px; visibility: visible !important; height: 340px;margin-left:-26px;"><br></p>
                        <img data-s="300,640" data-type="png"  src="${ctx}/source/images/knowUs/17.jpg" style="width: 640px; visibility: visible !important; height: 340px;"><br></p>
                        <p style="font-size:15px;color:#FFCC00;line-height:40px;">“4”：SDS分布式调度管理系统、安全能力开放平台、安全大数据平台、安全态势感知平台</p>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#10022;&nbsp;&nbsp;SDS分布式调度系统基于SDS架构设计，支持安全策略的解析与下发、安全资源的智能随选调度与管理、多厂家设备的统一能力适配和异构设备间的协同工作</p>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#10022;&nbsp;&nbsp;安全能力开放平台支持智能化的业务编排和管理、快速的自动化服务配置部署、已定义的业务能力输出</p>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#10022;&nbsp;&nbsp;安全大数据平台基于海量的安全数据源，通过行为建模、经验转化、算法应用进行多维度数据分析和展现</p>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#10022;&nbsp;&nbsp;安全态势感知平台定义安全态势指标体系，基于安全大数据分析能力进行安全态势指标的获取，并构建立体的安全态势展示模型进行直观的安全态势展示与预测</p>
                        <p style="text-align: center;"><img data-s="300,640" data-type="png"  src="${ctx}/source/images/knowUs/15.jpg" style="padding-top:15px; padding-bottom:0px; width: auto !important; visibility: visible !important; height: auto !important;"><br></p>
                        <br />
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国电信安全帮携绿盟科技、安恒信息、金山安全等合作伙伴，共同打造云安全服务平台，为客户的网络信息安全保驾护航！</p>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;安全帮三步战略：</p>
                        <p style="color:#6699FF;margin-top:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1 中国电信自有品牌安全服务</p>
                        <p style="color:#6699FF;margin-top:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2 第三方品牌安全服务入驻安全帮</p>
                        <p style="color:#6699FF;margin-top:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3 智能协同SaaS云安全服务</p>
                        <p style="text-align: center;"><img data-s="300,640" data-type="png"  src="${ctx}/source/images/knowUs/18.jpg" style="width: auto !important; visibility: visible !important; height: auto !important;"><br></p>
                        <br />
                        <br />
                    </div>

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
