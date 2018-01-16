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
<link href="${ctx}/source/css/problem_frame.css" type="text/css" rel="stylesheet">
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
<div class="join_left" id="nav">
			<ul>
				<li class="cebianlan"style="background-color: dodgerblue;"><p class="cebianlan_p"style="color: white;">会员问题</p></li>
				<li class="cebianlan"><p class="cebianlan_p">金融问题</p></li>
				<li class="cebianlan"><p class="cebianlan_p">操作问题</p></li>
				<li class="cebianlan"><p class="cebianlan_p">网站监测服务问题</p></li>
				<li class="cebianlan"><p class="cebianlan_p">云WAF网站安全防护问题</p></li>
				<li class="cebianlan"><p class="cebianlan_p">系统安全帮问题</p></li>
				<li class="cebianlan"><p class="cebianlan_p">安全服务能力API问题</p></li>
			</ul>
		</div>
		<div class="bigbox">
			<!--主题标签-->
			<div class="headtext">
				<div class="txt1"><p>常见问题</p></div>
				<div class="triangle"></div>
			</div>
			<!--会员问题-->
			<div class="content" style="display: block;height: 1980px;">
				<p class="title">1. 如何注册与登录</p>
				<div class="content_left">
					<p>1）进入网站安全云防护服务系统（www.anquanbang.vip），点击 “注册”。</p>
					<img width="430px" src="${ctx}/source/img/wt1.png">
					<div class="arrow1"></div>
				</div>				
				<div class="content_right">
					<p>2）进入用户注册界面，提交详细的用户信息及联系方式，点击“立即注册”，完成注册账号。</p>
					<img width="430px" align="right" src="${ctx}/source/img/wt2.png">
				</div>
	
				<p class="title" style="margin-top: 10px;">2. 忘记账户和昵称怎么办？忘记密码怎么办？如何修改密码？</p>
				<div class="content_left">
					<p>1）登录安全帮网站首页，点击登录，选择忘记密码；</p>
					<img width="430px" src="${ctx}/source/img/wt3.png">
					<div class="arrow1"></div>
				</div>				
				<div class="content_right">
					<p>2）输入已验证的手机号码，并获取验证码，点击“提交”；</p>
					<img width="430px" align="right" src="${ctx}/source/img/wt4.png">
					<div class="arrow2"></div>
				</div>
				
				<div class="content_left" style="margin-top: 70px;">
					<p>4）密码修改完成。</p>
					<img width="430px" src="${ctx}/source/img/wt6.png">
					<div class="arrow3"></div>
				</div>
				<div class="content_right" style="margin-top: 70px;">
					<p>3）输入新密码，确认新密码，并输入验证码，点击“提交”；</p>
					<img width="430px" align="right" src="${ctx}/source/img/wt5.png">
				</div>
				
				<p class="title">3. 如何修改个人资料？</p>
				<p>登录进入个人中心，点击右方“个人信息管理”->“个人资料”，可进行修改，修改后点击保存，修改完成。</p>
				<img width="600px" src="${ctx}/source/img/wt7.png"style="position: relative;left: 80px;">
				<p class="title">4. 如何添加、删除、更改资产？</p>
				<p>登录进入个人中心，点击右方“个人信息管理”->“我的资产”，可添加资产并对资产进行验证，也可删除资产。</p>
				<img width="800px" src="${ctx}/source/img/wt8.png"style="position: relative;left: 80px;">			
			</div>
			
			<!--金融问题-->
			<div class="content">
				<img src="${ctx}/source/img/wt9.png" align="right" style="margin-right: 100px;">
				<p class="title">1. 都支持哪些支付方式？</p> 
				<p class="txt">仅支持使用安全币购买的形式。</p>
				
				<p class="title">2. 安全币是什么？</p>
				<p class="txt">1安全币=1人民币，用于购买安全帮网站各类服务。</p>
				
				<p class="title">3. 如何充值安全币？</p>
				<p class="txt">联系在线客服。</p>
				
				<p class="title">4. 可以开发票吗？</p>
				<p class="txt">可以，联系在线客服。</p>
				
				<p class="title">5. 取消订单后如何退款？</p>
				<p class="txt">取消订单后，退款金额直接退回到账户余额。</p>
				
				<p class="title">6. 安全币可以提现吗？</p>
				<p class="txt">不可以。</p>
				
				<p class="title">7. 我的余额、消费记录怎么查看？</p>
				<p class="txt">进入“个人中心”->安全币，可以查看安全币余额和消费记录。</p>				
			</div>
			
			<!--操作问题-->
			<div class="content" style="height: 1130px;">
				<p class="title">1. 如何查询订单情况？</p>
				<p class="txt">登录成功后，点击用户名，可以进入个人订单页，查看所有订单情况</p>
				<img width="800px" src="${ctx}/source/img/wt10.png"style="position: relative;left: 80px;">
				<img width="800px" src="${ctx}/source/img/wt11.png"style="position: relative;left: 80px;">
				<p class="title">2. 下单后可以修改或取消订单吗？</p>
				<p class="txt">下单后不可以在网站上直接修改或取消订单，可以联系在线客服进行更改。</p>			
				<p class="title">3. 如何联系安全帮人工客服？</p>
				<p class="txt">安全帮人工电话：</p>
				<p class="title">4. 订单状态标志什么意思？</p>
				<p class="txt">告警订单是红色感叹号；正在服务中订单是转动进度条；已下单还未开始服务的订单显示时钟。</p>
			</div>
			
			<!--网站监测服务问题-->
			<div class="content">
				<p class="title">1. 网站监测服务包括哪些？</p>
				<p class="txt">网站漏洞监测：基于自有特征库匹配，主流SQL注入、跨站、0day漏洞一网打尽。</p>
				<p class="txt">网站挂马监测：全面解决网页后门、木马、挂马、黑链、上传webshell等问题，让网页木马无所遁形 。</p>
				<p class="txt">网页篡改监测：针对页面框架篡改、内容篡改、图片篡改进行全天候监测，网站不再面目全非。</p>
				<p class="txt">网页敏感内容监测：海量丰富的敏感字库，精准识别暴恐、涉黄及违法违规内容。</p>
				<p class="txt">网站可用性监测：链路异常、访问延迟、解析错误等故障智能定位，保证网站时刻在线。</p>
				<p class="title">2. 安全帮网站监测服务有什么特点？</p>
				<p class="txt">安全帮提供专业的网站安全服务产品，为企业提供7*24小时实时在线监测与防护，实时告警与图文报表结合，实现企业侧零部署、低成本、高效率。</p>
			</div>
			
			<!--云WAF网站安全防护问题-->
			<div class="content" style="height: 1430px;">
				<p class="title">1. 云WAF网站安全防护是什么</p>
				<p style="text-indent: 2em;">网站安全云防护服务基于运营商量级安全资源池，构建强大的软件定义安全（SDS）多异构安全引擎，使用先进的网站替身技术，极大提升安全威胁的检出率，有效降低误报率，隔离网站业务系统，全面防护各种WEB应用攻击、应用层DDoS、恶意访问、客户端脚本攻击、网页篡改等网站安全威胁，保障网站的数据安全和业务的正常运行。</p>
				<p style="text-indent: 2em;">您只需要登陆网站安全云防护服务系统（www.anquanbang.vip），按需选择安全防护服务的时间，输入要保护的网站域名及其真实IP，修改域名DNS指向给定的网站安全云防护服务的IP地址，即刻启动云安全防护服务。您可以在线查看攻击态势和智能防护分析报告，防护系统还提供短信和邮件的实时告警，以及7╳24小时1对1资深安全专家服务，使您全面掌握网站安全状态，从容应对安全威胁事件。</p>
				<p class="title">2. 云WAF网站安全防护能防护哪些？</p>
				<img width="680px" src="${ctx}/source/img/from_1.png"style="position: relative;left: 100px;">
				<p class="title">3. 购买云WAF网站安全防护流程？</p>
				<p>注册、登录网站安全云防护服务系统</p>
				<p>添加要防护的网站资产</p>
				<p>在线购买网站安全云防护服务</p>
				<p>修改域名DNS指向给定的IP地址</p>
				<p>防护服务启动，在线查看防护状态报告</p>
				<p class="title">4. 云WAF网站安全防护应用场景？</p>
				<p>日常运维、应急处理、重点保障、合法合规</p>
				<p class="title">5. 云WAF网站安全防护产品优势？</p>
				<p style="text-indent: 2em;">全面准确的检测：底层能力与业界领先厂家合作，实现异构设备间的数据融合，基于极致专业能力构建丰富全面的特征库，让检测结果更准确。</p>
				<p style="text-indent: 2em;">便捷高效的交付：按需选购服务，自助式在线交互，支付即生效，全程服务自动指引，免部署低投入零延时。</p>
				<p style="text-indent: 2em;">灵活可靠的防护：分布式部署的云端WAF让网站接入更灵活，云端集群与负载均衡确保防护服务的高可用性。大数据分析+威胁情报全方位覆盖网站攻击手段，全天候为网站安全保驾护航。</p>
				<p style="text-indent: 2em;">清晰直观的展现：服务订单列表化呈现，进度可预期并全程可视化展示。告警实时短信通知，并进行数据的数字化处理与分析转换，结合丰富多样的图表让报表更直观立体。</p>
				<p style="text-indent: 2em;">智能化高性能的质量保证：分布式资源调度管理实现SDS架构中的核心控制功能，基于BP神经网络算法并结合资源建模自动进行最优资源选择，确保服务的效率与质量。</p>
			</div>
			
			<!--系统安全帮问题-->
			<div class="content">
				<p class="title">1. 系统安全帮是什么？</p>
				<p style="text-indent: 2em;">安全帮为提升系统可用性与完整性而打造的专业安全服务产品，企业可远程进行系统安全管理，及早发现危险因素，防止恶意行为扩散。</p>	
				<p class="title">2. 上网行为管理服务是什么？</p>
				<p style="text-indent: 2em;">精细全面的深度用户行为控制和管理，通过云端管理平台进行远程策略定义与下发即可实现PC终端上应用与流量的管理与记录，帮助企业净化网络环境，优化网络资源。同时基于全局行为数据分析为企业完善安全管理制度提供有效参考建议。</p>
				<p class="title">3. 上网行为管理服务包括什么？</p>
				<p>网络应用控制：视频、游戏、下载、音乐等常见应用软件轻松管理；</p>
				<p>网站访问控制：自定义添加需禁用的电商、求职、网盘、社交网站；</p>
				<p>带宽流量控制：下载与上传根据需求自主设定，合理分配宽带资源；</p>
				<p>USB使用管理：针对USB接口连接的U盘、移动硬盘及手机等存储设备进行管理，保证资料更安全；</p>
				<p>黑白名单管理：管理方式由禁用向许可转变，黑白名单搭配，更加灵活；</p>
				<p>全局数据分析：数据归纳汇总，可进行精准到人的记录查询，支持多维度分析，提供丰富直观的报表。</p>
				
				<p class="title">4.上网行为管理服务相比其他产品优势？</p>
				<p style="text-indent: 2em;">轻量级产品加载：被管理终端通过安装的轻量级代理实现策略接收与行为上报，互联网接入云端集中管理平台进行在线管理与数据查看，轻松实现管理范围扩展；</p>
				<p style="text-indent: 2em;">多重稳定性保障：采用系统冗余检测机制，还通过线路冗余备份、HA双机热备、Bypass等技术来保证系统的稳定运行；</p>
				<p style="text-indent: 2em;">更全面的内容审计：更多关注于用户的网络应用，管理者可深入了解员工的上网行为和网络应用状态。同时具备强大的防泄密功能，多种途径防范企业机密信息泄露；</p>
				<p style="text-indent: 2em;">更有价值的报表：可自定义的更贴近需求的数据报表，随时通过图表了解网络资源使用情况，实现更好的企业安全管理。</p>
				
				<p class="title">5. 上网行为管理服务购买流程？</p>
				<p>详见购物流程</p>
			</div>
			
			<!--安全服务能力API问题-->
			<div class="content">
				<p class="title">1. 安全服务能力API是什么？</p>
				<p style="text-indent: 2em;">安全帮通过安全能力开放平台将安全服务能力进行标准化封装和开放，供开发者进行结合以实现更多业务需求。</p>
				<p class="title">3. 安全服务能力API如何购买和使用？</p>
				<p class="txt">购买详见购物流程；</p>
				<p class="txt">使用联系在线客服。</p>
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
</script>

</html>
