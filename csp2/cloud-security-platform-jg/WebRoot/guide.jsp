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
<link href="${ctx}/source/css/guide_frame.css" type="text/css" rel="stylesheet">
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

		<!-- 正文内容 -->
	<div class="join_left">
			<ul>
				<li class="cebianlan" style="background: dodgerblue;color: white;height: 50px;line-height: 50px;font-size: 15px;"><p style="margin-left: 70px;">用户注册</p></li>
				<li id="product" style="height: 50px;line-height: 50px;border: 1px solid #ededed;font-size: 15px;"><p style="margin-left: 70px;">产品购买</p></li>
				<div id="cebianlan1">
					<li class="cebianlan"><p>&bull;&nbsp;登录查看详情</p></li>
					<li class="cebianlan"><p>&bull;&nbsp;检测服务</p></li>
					<li class="cebianlan"><p>&bull;&nbsp;云waf网站安全防护服务</p></li>
					<li class="cebianlan"><p>&bull;&nbsp;系统安全帮</p></li>
					<li class="cebianlan"><p>&bull;&nbsp;安全能力API购买</p></li>
				</div>				
				<li id="order" style="height: 50px;line-height: 50px;border: 1px solid #ededed;font-size: 15px;"><p style="margin-left: 70px;">订单查询</p></li>
				<div id="cebianlan2">
					<li class="cebianlan"><p>&bull;&nbsp;网站安全监测服务订单</p></li>
					<li class="cebianlan"><p>&bull;&nbsp;云WAF网站安全防护服务订单</p></li>
				</div>
				<li class="cebianlan" style="height: 50px;line-height: 50px;font-size: 15px;"><p style="margin-left: 65px;">关于安全币</p></li>
				
			</ul>
		</div>
		<div class="text">
			<!--主题标签-->
			<div class="headtext">
				<div class="txt"><p>购物流程</p></div>
				<div class="triangle"></div>
			</div>
			
			<!--用户注册-->
			<div class="content" style="display: block;">
				<p class="title">1. 用户注册</p>
				<p>（1）登录https://www.anquanbang.net，点击右上角“注册”；</p>
				<img width="800px" src="${ctx}/source/img/guide_img/gw1.png"style="position: relative;left:80px;">
				<p>（2）进入到注册页面，请填写您的用户名、密码、邮箱、手机等信息完成注册；</p>
				<img width="400px" style="margin-left: 200px;" src="${ctx}/source/img/guide_img/gw2.png">
				<p>（3）注册成功后，即可登录。</p>
			</div>
			
			<!--产品购买-->
				<!--登录查看详情-->
				<div class="content">
					<p class="title">2. 产品购买</p>
					<p>（1）注册成功后，即可点击右上角“登录”。</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw3.png"style="position: relative;left:80px;">
					<p class="txttop">（2）输入用户名、密码和验证码，点击“立即登录”；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw4.png"style="position: relative;left:80px;">
					<p class="txttop">（3）在商品分类中，浏览您要购买的商品，点击“商品名称”，查看商品详情；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw5.png"style="position: relative;left:80px;">
					<p class="txttop">（4）点击想购买的商品名称，查看商品详情；</p>
				</div>
				
				<!--检测服务-->
				<div class="content">
					<p class="title">2. 产品购买</p>
					<p style="color: #2499FB;">&bull;&nbsp;检测服务</p>		
					<img width="800px" src="${ctx}/source/img/guide_img/gw6.png"style="position: relative;left:80px;">
					<p class="txttop">1) 选择类型、起止时间，点击“点击此处选择网站”进行资产配置；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw7.png"style="position: relative;left:80px;">
					<p class="txttop">2) 输入网站名称和网站地址，选择物理位置和网站用途，点击提交；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw8.png"style="position: relative;left:80px;">
					<p class="txttop">3) 点击“未验证”，进行网站资产验证；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw9.png"style="position: relative;left:80px;">
					<p class="txttop">3) 点击“未验证”，进入网站资产验证页面；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw10.png"style="position: relative;left:80px;">
					<p class="txttop">4) 点击“立即验证”，进入网站资产验证；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw11.png"style="position: relative;left:80px;">
					<p class="txttop">5) 选择“代码验证”，按照上述要求，进行验证，然后点击“开始验证”，成功后会显示“已验证”，否则是“未验证”；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw12.png"style="position: relative;left:80px;">
					<p class="txttop">6) 返回商品详情页，重新添加网站，勾选通过验证并要服务的网站；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw13.png"style="position: relative;left:80px;">
					<p class="txttop">7) 点击确定，可添加到购物车或选择立即购买；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw14.png"style="position: relative;left:80px;">
					<p class="txttop">8) 查看我的购物车；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw15.png"style="position: relative;left:80px;">
					<p class="txttop">9) 选好商品后点击“去结算”；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw16.png"style="position: relative;left:80px;">
					<p class="txttop">10) 详细填写联系人信息、支付方式选择安全币，核对清单等信息；</p>
					<p>11) 确认无误后点击“提交订单”，进入付款流程，点击“确认付款”；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw17.png"style="position: relative;left:80px;">
					<p class="txttop">12) 生成新的订单，并可点击领取安全币；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw18.png"style="position: relative;left:80px;">
					<p>13) 查看订单详细信息：可点击“我的订单”→“订单中心”查看。</p>
				</div>
				
				<!--云waf网站安全防护服务-->
				<div class="content">
					<p class="title">2. 产品购买</p>
					<p style="color: #2499FB;">&bull;&nbsp;云waf网站安全防护服务</p>
					<p>联系在线客服进行购买咨询</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw19.png"style="position: relative;left:80px;">														
				</div>
				
				<!--系统安全帮-->
				<div class="content">
					<p class="title">2. 产品购买</p>
					<p style="color: #2499FB;">&bull;&nbsp;系统安全帮</p>
					<p style="color: #2499FB;">&nbsp;&nbsp;&nbsp;&nbsp;上网行为管理</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw20.png"style="position: relative;left:53px;">
					<p>1) 点击立即购买，提交订单并支付，进入我的订单；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw21.png"style="position: relative;left:80px;">
					<p>2) 点击“查看详情”，进行上网行为管理配置；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw22.png"style="position: relative;left:80px;">
					<p>3) 点击“立即部署”，进行上网行为管理配置；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw23.png"style="position: relative;left:80px;">
					<p>4) 下载“下载安装包”，进行下一步；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw24.png"style="position: relative;left:80px;">
					<p>5) 分发安装包，进行下一步；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw25.png"style="position: relative;left:80px;">
					<p>6) 安装后，进行设备管理；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw26.png"style="position: relative;left:80px;">
					<p>7) 可对员工上网行为进行管理。</p>
					
					<p style="color: #2499FB;margin-top: 20px;">云眼API服务</p>
					<p>1) 进入商品详情页，查看商品详情；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw27.png"style="position: relative;left:60px;">
					<p>2) 点击添加到购物车或立即购买->提交订单->确认付款；</p>
					<p>3) 进入我的订单，点击“查看详情”，进行应用性能管理。</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw28.png"style="position: relative;left:80px;">	
				</div>
				
				<!--安全能力API购买-->
				<div class="content">
					<p class="title">2. 产品购买</p>
					<p style="color: #2499FB;">&bull;&nbsp;安全能力API购买</p>
					<p>1) 点击首页->商品分类->安全能力API，查看安全能力API分类；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw29.png"style="position: relative;left:80px;">
					<p>2) 点击商品名称，查看安全能力API商品详情；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw30.png"style="position: relative;left:33px;">
					<p>3) 点击添加到购物车或立即购买->提交订单->确认付款； </p>
					<p>4) 进入我的订单，点击“查看详情”，联系在线客服进行API借口配置。</p>
				</div>

			
			<!--查看订单情况-->
				<!--网站安全监测服务订单-->
				<div class="content">
					<p class="title">3. 查看订单情况</p>
					<p style="color: #2499FB;">&bull;&nbsp;网站安全监测服务订单</p>
					<p>1）查看订单：可点击右上角“用户名”→“订单中心”查看所有订单信息；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw31.png"style="position: relative;left:80px;">
					<p class="txttop">2）查看单一订单详细信息：可点击订单右方“查看详情”。</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw32.png"style="position: relative;left:80px;">
					<p class="txttop">3）点击 “查看详情”，可看到告警订单详细情况和修改意见，并自动生成在线报表；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw33.png"style="position: relative;left:80px;">
					<p class="txttop">4）点击 “下载Word报表”，可生成Word版网站详细监测报告；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw34.png"style="position: relative;left:80px;">
				</div>
				<!--云WAF网站安全防护服务订单-->
				<div class="content" >
					<p class="title">3. 查看订单情况</p>
					<p style="color: #2499FB;">&bull;&nbsp;云WAF网站安全防护服务订单</p>
					<p>1）查看订单详细信息：可点击订单右方“查看详情”；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw35.png"style="position: relative;left:80px;">
					<p class="txttop">2）查看订单详细信息：可点击订单右方“查看详情”，查看网站防护实时数据；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw36.png"style="position: relative;left:80px;">
					<p class="txttop">3）点击左上角“历史数据”，可查看网站防护历史数据；</p>
					<img width="800px" src="${ctx}/source/img/guide_img/gw37.png"style="position: relative;left:80px;">
					<p>4）根据需求选择月报、年报或者周报，并可以点击“下载Word报表”，生成Word版电子报告。</p>
				</div>

			
			<!--安全币说明-->
			<div class="content">
				<p class="title">4. 安全币说明</p>				
				<p>（1）1安全币=1人民币</p>
				<p>（2）订单支付方式是安全币，用户需进行安全币充值请联系在线客服</p>
				<p>（3）新用户注册立即送2000个安全币； </p>
				<img width="800px" src="${ctx}/source/img/guide_img/gw38.png"style="position: relative;left:80px;">
				<p class="txttop">（4）用户登录签到送10个安全币；</p>
				<img width="800px" src="${ctx}/source/img/guide_img/gw39.png"style="position: relative;left:80px;">
				<p class="txttop">（5）用户下单后可领取10个安全币。</p>
				<img width="800px" src="${ctx}/source/img/guide_img/gw40.png"style="position: relative;left:80px;">
			</div>
						
		</div>
	
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
	var Cebianlan =document.getElementsByClassName("cebianlan");
		var Content =document.getElementsByClassName("content");
		var Cebianlan_p =document.getElementsByClassName("cebianlan_p");
		for(var i = 0;i<Cebianlan.length;i++){
	   		Cebianlan[i].index=i;
	   		Cebianlan[i].onclick=function (){
	   			for(var j =0; j<Content.length;j++){
	   				Content[j].style.display="none";
	   				Cebianlan_p[j].style.color="black";
	   				Cebianlan[j].style.backgroundColor="white";
	   			}
	   			Content[this.index].style.display="block";
	   			Cebianlan_p[this.index].style.color="white";
	   			Cebianlan[this.index].style.backgroundColor="dodgerblue";
	   			
	   			
	   		}
	  }
	  /*大选项卡*/
		var Cebianlan =document.getElementsByClassName("cebianlan");
		var Cebianlan2 =document.getElementById("cebianlan2");
		var Content =document.getElementsByClassName("content");
		for(var i = 0;i<Cebianlan.length;i++){
	   		Cebianlan[i].index=i;
	   		Cebianlan[i].onclick=function (){
	   			for(var j =0; j<Content.length;j++){
	   				Content[j].style.display="none";
	   				Cebianlan[j].style.color="black";
	   				Cebianlan[j].style.background="white";
		   		}
	   			Content[this.index].style.display="block";
	   			Cebianlan[this.index].style.color="white";
	   			Cebianlan[this.index].style.background="dodgerblue";	   			
	   		}  		
	  	}
		
		/*单击出现二级选项卡*/
		var Product =document.getElementById("product");
		var Cebianlan1 =document.getElementById("cebianlan1");
		Product.onclick=function(){
			if (Cebianlan1.style.display=="block") {
				Cebianlan1.style.display="none";
			} else{
				Cebianlan1.style.display="block";
			}
		}
		
		var Order =document.getElementById("order");
		var Cebianlan2 =document.getElementById("cebianlan2");
		Order.onclick=function(){
			if (Cebianlan2.style.display=="block") {
				Cebianlan2.style.display="none";
			} else{
				Cebianlan2.style.display="block";
			}
		}
</script>

</html>
