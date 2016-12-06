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
body,html{
	height: 100%;
	width: 100%;
}
.helph{height:30px;font-size:18x;line-height:30px;padding-left:20px;color:#666;padding-top:20px;
	width: 70%;
margin: 10px 10%;
}
.helphn{
	height:30px;font-size:20px !important;line-height:30px;padding-left:20px;color:#4593fd;padding:20px 0 10px 0;
	text-align: center;
	margin-bottom:0 !important;
}
.help_detail{margin: 10px 0 0 20px;color: #888;font-size: 16px; text-indent:2em;line-height:30px;
	width: 70%;
margin: 0 18%;
}

</style>
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
<!-- 头部代码结束-->
		
		<!-- 
		<div class="safe02">
			
			<div class="imgBox">
				  <div class="user_right" style="width: 942px;border:1px solid #e0e0e0; margin:0 auto;">
			  	  <a name="1F">
				  <div id="f1" class="dd">
				    <h2 class="helphn">一、平台开发工程师</h2>
				    <h3 class="helph">（一）工作职责：</h3>
				    <p class="help_detail">从事安全帮平台开发和测试工作。</p>
				    <h3 class="helph">（二）任职条件：</h3>
				    <p class="help_detail">1、1-3年以上Java web开发经验；</p>
				    <p class="help_detail">2、精通三大框架struts、spring、hibernate；</p>
				    <p class="help_detail">3、熟悉Java web开发相关技术，html/css/javascript，有linux下开发布署经验者优先；</p>
				    <p class="help_detail">4、熟悉Web Service并深刻理解RESTFUL Stateless的概念，了解JSON API的设计和具有实现经验；</p>
				    <p class="help_detail">5、掌握关系数据库mysql，了解noSql、mongoDB、redis等技术；</p>
				    <p class="help_detail">6、具备良好的编码风格及文档编写能力；</p>
				    <p class="help_detail">7、具有较强的学习能力、沟通能力和团队合作精神。</p>
				    
				  </div>
				  </a>
				  <a name="2F">
				  <div id="f2" class="dd" >
				    <h2 class="helphn">二、WEB前端开发工程师</h2>
				    <h3 class="helph">（一）工作职责：</h3>
				    <p class="help_detail">从事安全帮web前端开发和测试工作。</p>
				    <h3 class="helph">（二）任职条件：</h3>
				    <p class="help_detail">1、1-3年以上Java web开发经验；</p>
				    <p class="help_detail">2、熟悉Html/CSS/JS/JQuery/Bootstrap/echarts等常见的WEB前端技术；</p>
				    <p class="help_detail">3、熟悉页面架构及布局，对Web标准和标签语义有深入理解；</p>
				    <p class="help_detail">4、有良好的学习能力、沟通能力和合作精神；</p>
				    <p class="help_detail">5、有WEB前端开发经验者优先。</p>
			        <h2 class="helphn"> 简历请发送至 hr@anquanbang.net</h3>
			     
			      </div>
			      </a>
			      
			  
			  </div>
			</div>
		</div> -->
		<!--  start -->
		<c:import url="http://mp.weixin.qq.com/s?__biz=MzIxNTE4ODQ1Mg==&mid=502264160&idx=1&sn=2be459d849c8bd48b1c21070c013cb86#rd"></c:import>
		<!--  end -->
		
	
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<!--修改-->
				   <a href="${ctx}/index.html">
	               		<img src="${ctx}/source/images/portal/new-footer-logo.png" alt="" />
                   </a>
                <!--修改-->  
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
