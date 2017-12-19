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
<script>
$(function(){
	$('.understand p').css('text-align','left');
	$('.understand p').css('width','640px');		
})

</script>
</head>
<style>
.about{
			width: 1000px;
			height: 2100px;
			margin: 0 auto;			
			margin-top: 30px;
		}
		.banner img{
			width: 1000px;
			height: 400px;
			margin: 0 auto;			
			margin-top:0px;
			box-shadow: 0px 0px 10px #2499FB;			
		}
		/*标题*/
		.Title{
			font-size:20px;
			color: #2499FB;
			line-height: 55px;
			margin-top: 5px;
			margin-left: 30px;
		}
		/*我们的产品与服务*/
		.product{
			width: 100%;
			height: 1040px;
			position: relative;
			font-size: 16px;
			box-shadow: 0px 0px 10px #2499FB;
			border-radius: 5px 5px 5px 5px;	
		}
		/*黄色强调字样式*/
		.txt{
			color:#FFCC00;
			font-weight: 600;
		}
		
		/*图文交叉*/
		.product_1{
			margin-top: 20px;
			width: 100%;
			height: 278px;
		}
		.product_1 img{
			margin-left: 30px;
		}
		.product_1 p{
			width: 45%;
			height: 200px;
			float: right;			
			text-indent: 2em;
			line-height: 40px;
			margin-right: 30px;
			margin-top: 25px;
		}
		.product_2{
			width: 100%;
			height: 250px;
		}
		.product_2 img{
			margin-top: 10px;
			margin-right: 30px;
		}
		.product_2 p{
			width: 44%;
			float: left;
			margin-top: 10px;
			margin-left: 30px;
			text-indent: 2em;
			line-height: 45px;
		}
		.product_3{
			width: 100%;
			height: 285px;
		}
		.product_3 img{
			margin-top: 30px;
			margin-left: 30px;
		}
		.product_3 ul{
			width: 44%;
			float: right;
			margin-right: 30px;
			/*margin-top:-10px;*/
		}
		.product_3 li{
			list-style: none;
			line-height: 28px;
			margin: 10px 0px;
			text-indent: 2em;
		}		
		
		/*安全新生态*/
		.security{
			width: 100%;
			height: 270px;
			font-size: 16px;
			box-shadow: 0px 0px 10px #2499FB;
			border-radius: 5px 5px 5px 5px;	
		}
		/*安全新生态左侧*/
		.security_left{
			width: 44%;
			height: 250px;
			float: left;
			margin-top: 10px;
			margin-left: 30px;
			text-indent: 2em;
			line-height: 35px;
		}	
		/*安全新生态右侧*/
		.security_right{
			width: 50%;
			height: 270px;
			float: right;
			line-height: 35px;
			box-shadow: 0px 0px 8px #2499FB;
			border-radius: 0px 5px 5px 0px;	
		}
		.security_right li{
			list-style: none;
			margin-left: 135px;
			color: #2499FB;
			font-weight: 600;
			line-height: 55px;
		}
		/*联系我们*/
		.contact{
			width: 100%;
			box-shadow: 0px 0px 10px #2499FB;
			border-radius: 5px 5px 5px 5px;	
			position: relative;
		}
		.contact li{
			list-style: none;
			margin-left: 30px;
			font-size: 16px;
			line-height: 50px;
		}
		/*二维码*/
		.pic1{
			width: 150px;
			text-align: center;
			position: absolute;
			top: 8px;
			left: 535px;
		}
		/*文字*/
		.pic1 p{
			font-size: 14px;
			margin-top: -10px;
		}
		.pic2{
			width: 150px;
			text-align: center;
			position: absolute;
			top: 15px;
			left: 740px;
		}
		.pic2 p{
			font-size: 14px;
			margin-top: -5px;
		}
</style>
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
			<!-- 正文内容 -->
			<div class="about">
			<div class="banner">
				<img src="${ctx}/source/img/banner.jpg"/>
			</div>
			<p class="Title">
				我们的产品与服务
			</p>
			<div class="product">
				<p style="margin-left: 30px;line-height: 40px;">安全帮包括<span class="txt">“1+4”</span>产品体系：</p>
				<p class="txt" style="margin-left: 60px;margin-top:10px;line-height: 20px;">“1”：SaaS云安全服务商城</p>
				<p style="margin-left: 30px;margin-right: 30px;margin-top:10px;line-height: 25px;text-indent: 2em;">
					全自助式安全服务云商城，面向中小微企业用户提供在线云化安全服务和安全能力API，让安全像购物一样简单。
					真正实现即买即用，按次计费，在线支付。
				</p>
				<div class="product_1">
					<img width="470px" align="left" src="${ctx}/source/img/show_one.png">
					<p>
						为用户打造全自助一站式在线安全服务体验，动一动鼠标即可享受及时、灵活、智能、便捷的安全服务，
						并结合丰富多样的图表让企业安全更直观立体，企业所面临的安全厂商多、安全产品繁、安全投资大、
						安全人才缺等问题迎刃而解，为企业省时省力省钱。
					</p>
				</div>
				
				<p class="txt" style="margin-left: 40px;">
					“4”：PaaS安全平台——SDS分布式协同平台、安全能力开放平台、安全大数据平台、安全态势感知平台
				</p>
				
				<div class="product_2">
					<img width="490px" align="right" src="${ctx}/source/img/show_two.png">
					<p>
						安全帮作为SDS软件定义安全先行者，以极致的专业能力与创新的精神打造了Sec-aaS云安全服务平台，
						基于多年技术积累进行提炼与抽象，将领域内领先的基础能力对外开放，帮助企业实现快速的安全应用引入，
						以更低的成本来高效、可靠地创建安全融合业务，推动企业安全建设的全面提升与优化。
					</p>
				</div>
				
				<div class="product_3">
					<img width="480px" src="${ctx}/source/img/show_three.png">
					<ul>
						<li>
							&diams;&nbsp;SDS分布式协同平台基于SDS架构设计，支持安全策略的解析与下发、
							安全资源的智能随选调度与管理、多厂家引擎的统一能力适配和异构引擎间的协同工作。
						</li>
						<li>
							&diams;&nbsp;安全能力开放平台支持智能化的业务编排和管理、快速的自动化服务配置部署、已定义的业务能力输出。
						</li>
						<li>
							&diams;&nbsp;安全大数据平台基于海量的安全数据源，通过行为建模、经验转化、算法应用进行多维度数据分析和展现。
						</li>
						<li>
							&diams;&nbsp;安全态势感知平台定义安全态势指标体系，基于安全大数据分析能力进行安全态势指标的获取，
							并构建立体的安全态势展示模型进行直观的安全态势展示与预测。
						</li>
					</ul>
				</div>
				
			</div>
			
			<p class="Title">
				安全新生态
			</p>
			<div class="security">
				<p class="security_left">
					 2017年11月，安全帮携手绿盟科技、安华金和、启明星辰、安恒信息共同发起了“安全服务创新联盟”，
					以开放、协同、创新为宗旨，开展企业安全技术、产品、解决方案及标准规范的协同研究，
					持续深化安全服务理念创新与安全服务模式创新，不断推出满足用户需要的安全新产品和新服务，
					由自主创新驱动产业深度变革，共同开创网络和信息安全的美好未来。
				</p>
				<ul class="security_right">
					<p style="margin-top: 35px;margin-left: 90px;">安全帮三步战略：</p>
					<li>1.中国电信自有品牌安全服务</li>
					<li>2.第三方品牌安全服务入驻安全帮</li>
					<li>3.智能协同SaaS云安全服务</li>
				</ul>
			</div>
			
			
			<p class="Title">
				联系我们
			</p>
			<div class="contact">
				<ul>
					<ul>					
					<li>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：010－50902872</li>
					<li>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：service@anquanbang.net</li>
					<li>Q&nbsp;Q客服：470899318</li>
					<li>通讯地址：北京市昌平区北七家镇未来科技城南区中国电信北京信息科技创新园北京研究院902室</li>
				</ul>
				</ul>
				<div class="pic1">
					<img width="130px" src="${ctx}/source/img/show_four.png">
					<p>关注安全帮微信公众号</p>
				</div>
				<div class="pic2">
					<img width="120px" src="${ctx}/source/img/show_five.png">
					<p>关注安全帮微博</p>
				</div>				
			</div>
			
		</div>

		</div>
        <!--主题内容-->
		
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
