<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>安全帮-中国电信云安全服务在线商城</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/index.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/index.js"></script>
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script type="text/javascript">
    //首页广告页数据定时刷新,数据不去重
$(function () {
    //showUnreadNews();
    window.setInterval(showUnreadNews,30000);
});
function showUnreadNews()
{
    $.ajax({
        type: "GET",
        url: "${ctx}/getNum.html",
        dataType: "json",
        success: function(data) {
        	$("#num3").html(data.brokenNetwork);
        	$("#num2").html(data.leakNum);
            $("#num1").html(data.webPageNum);
            $("#num").html(data.webSite);
        }
    });
}
    //定时30s刷新一次
    //setInterval('showUnreadNews()',20000);
</script>
</head>
<body>
<div>

<!--头部-->
<div class="head">
	<div class="headBox">
		<div class="safeL fl">
			<div class="logo">
			<img src="${ctx}/source/images/portal/logo.png" alt="" style="position:relative; top:5px;"/><b></b><span>网站安全帮</span>
			</div>
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
<div class="nav">
  <div class="subnav_box">
      <div class="subnav fl" id="play" >
      <div class=""> </div>
      <!--<p class="prev">&laquo;</p>
          <p class="next">&raquo;</p>-->
      <ul>
        <li style="position:relative">
            <a href="###">
                <img src="${ctx}/source/images/banner1.jpg"/>
                <!-- <span class="lb_font lb_first">发现篡改<span class="lb_font_w" id="num3">${pageTamperNum }</span>次</span>
                <span class="lb_font lb_second">发现<span class="lb_font_w" id="num2">${whorseNum }</span>个木马</span>
                <span class="lb_font lb_third">累计检测<span class="lb_font_w" id="num">${webPageNum }</span>个网页</span>
                <span class="lb_font lb_forth">发现<span class="lb_font_w" id="num1">${leakNum }</span>个漏洞</span>-->
                <span class="lb_font lb_first">发现断网<span class="lb_font_w" id="num3">${brokenNetwork }</span>次</span>
                <span class="lb_font lb_second">发现漏洞<span class="lb_font_w" id="num2">${leakNum }</span>个</span>
                <span class="lb_font lb_third">已监测网站<span class="lb_font_w" id="num">${webSite }</span>个</span>
                <span class="lb_font lb_forth">检测页面<span class="lb_font_w" id="num1">${webPageNum }</span>个</span>
            </a>
        </li>
       
        <li><a href="###"><img src="${ctx}/source/images/cloudsecurity4.png" /></a></li>
        <li><a href="###"><img src="${ctx}/source/images/cloudsecurity5.png" /></a></li>
        <li><a href="###"><img src="${ctx}/source/images/cloudsecurity6.png" /></a></li>
        <li><a href="###"><img src="${ctx}/source/images/cloudsecurity1.jpg" /></a></li>
      </ul>
      <ol>
        <li  class="active"></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
      </ol>
    </div>
    <div class="fl right_nav">
      <div class="right_title">系统公告</div>
      <div class="right_list">
        <ul>
           <c:forEach var="list" items="${noticeList}" varStatus="status">
             <c:if test="${status.index<4 }">
               <li><a href="${ctx}/noticeDescUI.html?id=${list.id}" title="${list.noticeName }">
                   <c:if test="${fn:length(list.noticeName)<=9}">
                               ${list.noticeName }
                   </c:if>
                   <c:if test="${fn:length(list.noticeName)>9}">
                           ${fn:substring(list.noticeName, 0, 9)}...
                   </c:if>
               </a></li>
              </c:if>
           </c:forEach>
        </ul>
      </div>
      <div class="right_wechate">
        <p>最新的WEB安全资讯</p>
        <P>尽在网站安全帮</P>
        <img src="${ctx}/source/images/wedetil.png"/>
      </div>
    </div>
  </div>
  <div class="center clear">
    <div class="web_fuwu clear">
      <h1>网站安全监测及预警服务</h1>
      <c:forEach var="list" items="${servList}" varStatus="status">
        <c:choose>
            <c:when test="${status.index<4}">
		      <div class="web_main bor_right">
		      <div class="web_main_pic"><img src="${ctx}/source/images/iconin${status.index+1 }.jpg" /></div>
		        <h2><a href="###">${list.name }</a></h2>
		        <p class="web_txt">${list.remarks }</p>
		        <div class="in_web_box new_in_web_box">
		            <div class="web_left">
						<p class="fl new_web_p">0<span class="web_money" style="font-size:17px;">￥</span></p>
				        <p class="fl new_web_pt" style="">
				        	<span class="web_money" style="font-size:14px;text-decoration:line-through;">99￥</span>
				         </p>
		            </div>
		            <div class="web_right fr">
				    <a class="index_buy new_index_buy" href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1">购买</a>
				    </div>
		        </div>
		      </div>
		    </c:when>
		    <c:when test="${status.index==4}">
              <div class="web_main" style="margin-right:0">
                <div class="web_main_pic"><img src="${ctx}/source/images/iconin${status.index+1 }.jpg" /></div>
                <h2><a href="###">${list.name }</a></h2>
                <p class="web_txt">${list.remarks }</p>
                <div class="in_web_box new_in_web_box">
                    <div class="web_left">
                    	<p class="fl new_web_p">0<span class="web_money" style="font-size:17px;">￥</span></p>
				        <p class="fl new_web_pt" style="">
				        	<span class="web_money" style="font-size:14px;text-decoration:line-through;">99￥</span>
				         </p>
                    </div>
                    <div class="web_right fr">
				    <a class="index_buy new_index_buy" href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1">购买</a>
				    </div>
                </div>
              </div>
            </c:when>
	    </c:choose>
      </c:forEach>


    </div>
    <div class="anti_fuwu clear">
      <h1>网站安全防护及加固服务</h1>
      <c:forEach var="list" items="${servList}" varStatus="status">
         <c:choose>
            <c:when test="${status.index==5||status.index==6}">
		      <div class="anti_main bor_right">
		        <div class="web_model"></div>
                <div class="web_modelp">即将上线...</div>
		        <div class="anti_main_pic fl"><img src="${ctx}/source/images/iconin${status.index+1 }.jpg" /></div>
		        <div class="anti_main_right fl" style="width:193px">
		          <h2><a href="###">${list.name }</a></h2>
		          <p class="web_txt">${list.remarks }</p>
		            
		           <DIV class="in_web_box new_in_web_box" style="margin:0">
				    <div class="web_left">
				        <p class="fl new_web_p">0<span class="web_money" style="font-size:17px;">￥</span></p>
				        <p class="fl new_web_pt" style="">
				        	<span class="web_money" style="font-size:14px;text-decoration:line-through;">99￥</span>
				         </p>
				    </div>
					<div class="web_right fr">
				    <a class="index_buy new_index_buy" href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1">购买</a>
				    </div>
				   </div>
		        </div>
		      </div>
            </c:when>
            <c:when test="${status.last}">
		      <div class="anti_main">
		        <div class="web_model"></div>
                <div class="web_modelp">即将上线...</div>
		        <div class="anti_main_pic fl"><img src="${ctx}/source/images/iconin${status.index+1 }.jpg" /></div>
		        <div class="anti_main_right fl" style="width:193px">
		          <h2><a href="###">${list.name }</a></h2>
		          <p class="web_txt">${list.remarks }</p>
		            
		           <DIV class="in_web_box new_in_web_box" style="margin:0">
				    <div class="web_left">
				        <p class="fl new_web_p">0<span class="web_money" style="font-size:17px;">￥</span></p>
				        <p class="fl new_web_pt" style="">
				        	<span class="web_money" style="font-size:14px;text-decoration:line-through;">99￥</span>
				         </p>
				    </div>
					<div class="web_right fr">
				    <a class="index_buy new_index_buy" href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1">购买</a>
				    </div>
				   </div>
		        </div>
		      </div>
		    </c:when>
           </c:choose>
        </c:forEach>
    </div>
  </div>
</div>
<%-- <div class="bottom_bj">
<div class="bottom">
<div class="bottom_main">
  <h3><a href="###">新手入门</a></h3>
  <ul>
    <li><a href="${ctx}/registUI.html">新用户注册</a></li>
    <li><a href="${ctx}/loginUI.html">用户登录</a></li>
    <li><a href="${ctx}/forgetPass.html">找回密码</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###"> 帮助</a></h3>
  <ul>
    <li><a href="${ctx}/aider.html">常见问题</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###">厂商合作</a></h3>
  <ul>
    <li><a href="###">绿盟</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">华为</a></li>
  </ul>
</div>
<div  class="bottom_main">
<h3><a href="###">联系我们</a></h3>
<ul>
<li><a href="###">QQ交流群470899318</a></li>
</div>
<div  class="bottom_main" style="width:380px;">
<h3><a href="###">版权信息</a></h3>
 <ul>
 <li>Copyright&nbsp;©&nbsp;2015 中国电信股份有限公司北京研究院<br />
京ICP备12019458号－10</li>
</div>
</div>
</div> --%>
<div class="safeBox">
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
		
</div>
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
</body>
</html>
