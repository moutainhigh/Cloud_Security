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
.join{
			width: 1000px;
			height: 930px;
			border: 1px solid #ededed;
		 	box-shadow: 0px 0px 10px #ededed;
			margin: 0 auto;			
			margin-top: 50px;
		}
		.headtext{
			width: 170px;
			height: 65px;
			margin-top: -15px;
			margin-left: 115px;
			position: relative;			
		}
		.txt{
			width: 170px;
			height: 60px;
			background:#2499FB;
			opacity: 0.9;
			border-radius: 5px 0px 5px 5px;
		}
		.txt p{
			font-size: 25px;
			line-height: 60px;
			text-align: center;
			color: white;
		}
		.triangle{			
			position: absolute;
			top: 0px;
			left: 170px;
			border-top:solid 15px rgba(0,0,0,0);
			border-left:solid 10px #2499FB;
		}		
		.text{
			width: 760px;
			height: 837px;
			margin: 0 auto;
		}		
		.join-us{
			text-align: center;
			line-height: 40px;
			font-size: 16px;
			color: #2499FB;
		}
		.position{
			width: 100%;
			height: 35px;
			position: relative;
			border: 1px solid #2499FB;
			box-shadow: 0px 0px 10px #2499FB;
			border-radius: 5px 5px 5px 5px;	
			margin-top: 10px;	
		}
		.position p{
			color: #2499FB;
			margin-left: 60px;
			font-size: 21px;
			line-height: 35px;
		}
		.btn{
			width: 26px;
			height: 29px;
			position: absolute;
			top: 2px;
			right: 30px;
			background-size: 100% 100%;
			background-image:url(${ctx}/source/img/加.png);
		}
		.demand{
			width: 90%;
			height: 330px;
			margin: 0 auto;
			display: none;
			margin-top: 20px;
		}
		.require{
			font-size:20px;
			color: #2499FB;
			line-height: 35px;
			
		}
		.demand li{
			list-style: none;
			font-size: 14px;
			margin: 5px 0px;
		}
		.common{
			width: 90%;
			height: 220px;
			margin: 0 auto;
			margin-top: 20px;
			border-top: 2px #2499FB solid;			
		}
		.common li{
			list-style: none;
			font-size: 14px;
		}
		.foot-text{
			text-align: center;
			line-height: 40px;
			font-size: 20px;
			color: #2499FB;
			font-weight: 600;
			border-top: 2px #2499FB solid;
			box-shadow: 0px 0px 10px #2499FB;
			margin-top: 20px;
		}
		.safe04{
		margin-top:40px;
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
<div class="join">
			<!--主题标签-->
			<div class="headtext">
				<div class="txt"><p>加入我们</p></div>
				<div class="triangle"></div>
			</div>
			<!--主要内容-->
			<div class="text">
				<p class="join-us">加入安全帮，一起改变世界，一起实现梦想！</p>
                <p class="join-us">安全帮，是全球首创的全自助式安全服务云商城。</p>
                <p class="join-us">安全帮，是中国电信北京研究院安全产品线的一款产品，一个平台。</p>
				<p class="join-us">在这里，你可以掌握互联网安全的前沿科技，可以与各位大咖交流，
				</br> 可以充分发挥自己的聪明才智。</p>
				<p class="join-us">期待你的加入，与我们一起去改变世界，一起去实现梦想。</p>
				
				<p class="require" style="font-weight: 600;text-align: center;margin-top: 20px;">招聘单位:中国电信股份有限公司北京研究院</p>
				<!--招聘岗位-->
				<div class="position"><p>Java开发实习生</p><div class="btn"></div></div>				
				<div class="demand" style="height: 290px;">
					<p class="require">岗位要求：</p>
					<ul>
						<li>
							1.通信、电子、计算机、软件等相关专业在校研究生；
						</li>
						<li>
							2.熟悉Java及Java Web相关内容，使用过SpringMVC+MyBatis/SSH等框架开发，熟练掌握Spring/Spring MVC/Mybatis相关知识；
						</li>
						<li>
							3.了解Linux相关知识，掌握常见服务器配置、部署方式，例：tomcat/niginx等；
						</li>
						<li>
							4.熟悉MySQL等常见数据库的应用，熟练掌握常见SQL命令及至少一种数据库管理工具；
						</li>
						<li>
							5.了解并掌握基本的HTML、CSS页面设计；
						</li>
						<li>
							6.有较强的工作责任心。
						</li>
					</ul>
					<span class="require">工作职责：</span>
					<p>主要负责部门项目的研发工作</p>									
				</div>
		
				<div class="position"><p>软件测试实习生</p><div class="btn"></div></div>
				<div class="demand" style="height: 350px;">
					<span class="require">岗位要求：</span>
					<ul>
						<li>
							1.通信、电子、计算机、软件等相关专业在校研究生；
						</li>
						<li>
							2.有测试经验者优先；						
						</li>
						<li>
							3.了解TCPIP、UDP等基本网络通信协议；
						</li>
						<li>
							4.熟悉java或任何一种编程语言； 
						</li>
						<li>
							5.具有较强的逻辑思维能力以及良好的学习能力，热爱技术能独立解决问题；
						</li>
						<li>
							6.有良好的团队合作意识、沟通及表达能力，积极融入团队。
						</li>
					</ul>
					<span class="require">工作职责：</span>
					<ul>
						<li>
							1.制定项目项目测试方案和测试计划，保证项目质量和进度； 
						</li>
						<li>
							2.设计与执行测试用例，跟踪定位产品软件中的缺陷或问题，推动项目进度与项目问题的解决；
						</li>
						<li>
							3.编写自动化测试脚本。
						</li>
					</ul>
				</div>
				
				<div class="position"><p>视觉设计实习生</p><div class="btn"></div></div>
				<div class="demand">
					<span class="require">岗位要求：</span>
					<ul>
						<li>
							1.美术、设计或相关专业在校生；
						</li>
						<li>
							2.有丰富的设计理论知识和对流行趋势敏锐的洞察力；							
						</li>
						<li>
							3.能够熟练使用Photoshop、InDesign、Coreldraw或Illustrator等软件；
						</li>
						<li>
							4.有手绘功底优先；
						</li>
						<li>
							5.精通 C/C++、MatLab等编程工具
						</li>
						<li>
							6.有三维重建、立体视觉项目经验优先
						</li>
					</ul>						
					<span class="require">工作职责：</span>
					<ul>
						<li>
							1.负责产品设计和推广设计
						</li>
						<li>
							2.辅助运营设计组的其他设计工作
						</li>
						<li>
							3.设计PC、移动端产品Icon、插画等，统一多端下的视觉感受，并配合技术实现
						</li>
					</ul>
				</div>
				
				<div class="position"><p>前端开发实习生</p><div class="btn"></div></div>
				<div class="demand">
					<span class="require">岗位要求：</span>
					<ul>
						<li>
							1.熟悉HTML/XHTML、CSS等网页制作技术，熟悉页面架构和布局；
						</li>
						<li>
							2.熟悉php、myeclipes优先；
						</li>
						<li>
							3.熟悉JavaScript、Ajax等Web开发技术；
						</li>
						<li>
							4.熟悉jQuery、Bootstrap等常用类库；
						</li>
						<li>
							5.熟悉各种前端开发工具，例如：Firebug，Web Developer Tools等；
						</li>
						<li>
							6.熟悉ECharts、D3等可视化工具者优先。
						</li>
					</ul>			
					<span class="require">工作职责：</span>
					<ul>
						<li>
							1.负责网站的前端开发；
						</li>
						<li>
							2.负责网站的可视化开发；
						</li>
						<li>
						    3.学会运用各种工具进行辅助开发。
						</li>
					</ul>
				</div>
				
				<!--时间地点及福利-->
				<div class="common">
					<span class="require">实习时间：</span>
					<p>随时入职，一周3天以上，能长期实习者优先。</p>
					<span class="require">实习地点：</span>
					<p>北京市昌平区未来科技城蓬莱苑南街（有班车接送）。</p>
					<span class="require">薪酬福利：</span>
					<ul>
						<li>
							1.工资面议
						</li>
						<li>
							2.公司内部食堂提供三餐，价格实惠。并不定期举行美食节活动
						</li>
						<li>
							3.配备健身房以一些体育场地设施，均可免费使用。
						</li>
					</ul>					
				</div> 
				<div class="foot-text">
					<p>有兴趣的同学请将个人简历投递至：hr@anquanbang.net</p>
					<p>标题“应聘实习职位+学校+专业+年级+姓名”，期待你的到来！</p>
				</div>
				
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
	var Btn=document.getElementsByClassName("btn");
	var safe=document.getElementsByClassName("safe04");
	var Foot=document.getElementsByClassName("foot");
	var Join=document.getElementsByClassName("join");
		var Bemand=document.getElementsByClassName("demand");	
		for (var n=0;n<Btn.length;n++) {
			Btn[n].a=n;
			Btn[n].onclick=function(){
				
				/*单击隐藏显示*/
				if (Bemand[this.a].style.display=="block") {
					Bemand[this.a].style.display="none";
					Btn[this.a].style.backgroundImage="url(${ctx}/source/img/加.png)";
					safe[0].style.marginTop="40px";
					Foot[0].style.marginTop="0px";
					Join[0].style.height="930px";
					
				} else{
					for (var m=0;m<Bemand.length;m++) {
						Bemand[m].style.display="none";
						Btn[this.a].style.backgroundImage="url(${ctx}/source/img/加.png)";
						
					}
					Bemand[this.a].style.display="block";
					Btn[this.a].style.backgroundImage="url(${ctx}/source/img/减.png)";
					safe[0].style.marginTop="50px";
					Foot[0].style.marginTop="0px";
					Join[0].style.height="1300px";
				}
				
			}
		}
			
</script>

</html>
