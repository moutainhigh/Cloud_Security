<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>云安全服务平台</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/index.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/index.js"></script>
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript">
    //首页广告页数据定时刷新,数据不去重
    function showUnreadNews()
    {
        $(document).ready(function() {
            $.ajax({
                type: "GET",
                url: "${ctx}/getNum.html",
                dataType: "json",
                success: function(data) {
                	$("#num1").html(data.leakNum);
                	$("#num").html(data.webPageNum);
                }
            });
        });
    }
    //定时30s刷新一次
    setInterval('showUnreadNews()',20000);
</script>
</head>

<body>
<div>
    <div class="head_bj">
        <div class="head">
           <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
           <div class="lagst">
               <div class="lagst-left">
                <c:if test="${sessionScope.globle_user!=null }">
                   <a href="${ctx}/userDataUI.html"><img src="${ctx}/source/images/ren.png" /></a>
                </c:if>
                 <c:if test="${sessionScope.globle_user==null }">
                    <a href="${ctx}/toLoginUI.html"><img src="${ctx}/source/images/ren.png" /></a>
                 </c:if>
               </div>
               <div class="lagst-right">
               <!-- 如果已经登录则显示用户名，否则需要登录 -->
               <c:if test="${sessionScope.globle_user!=null }">
                <p><a href="${ctx}/userDataUI.html" style="color: #fff">${sessionScope.globle_user.name }</a></p>
                <p><a href="${ctx}/exit.html">退出</a></p>
               </c:if>
               <c:if test="${sessionScope.globle_user==null }">
                     <p><a href="${pageContext.request.contextPath}/loginUI.html">登录</a></p>
                     <p><a href="${pageContext.request.contextPath}/registUI.html">注册</a></p>
               </c:if>
               </div>
           </div>
            <div class="list">
               <ul>
                   <li class="list_active"><a href="${ctx}/index.html">首页</a></li>
                   <li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
                   <li><a href="aider.html">在线帮助</a></li>
                   <li style="border-right:1px solid #1369C0;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
               </ul>
           </div>
        </div>
    </div>
    <div class="nav">
        <div class="subnav_box">
        <div class="fl left_nav">
            <div class="left_nav_box">
                <ul>
                    <li class="brbottom">
                        <div class="lncenter">
                            <p class="lntitle">WEB安全</p>
                            <span class="ln_more">></span>
                            <p class="lndetail"><a href="#">扫描类</a> / <a href="#">监控类</a> / <a href="#">防护类</a></p>
                            <div class="lndetails">
                                <div class="lndetails_line">
                                    <div class="fl lndetailtitle">热门厂商：</div>
                                    <div class="fl lndetaillist"><a href="#">中国电信</a><a href="#">奇虎360</a>     <a href="#">安恒</a></div>
                                </div>
                                <div class="lndetails_line">
                                    <div class="fl lndetailtitle">热门服务：</div>
                                    <div class="fl lndetaillist">
                                        <c:forEach var="list" items="${servList}" varStatus="status">
                                        <c:if test="${status.index<=4}">
                                            <a href="${ctx}/filterPage.html?type=${list.orderType }&serviceId=${list.id }">${list.name }</a>
                                        </c:if>
                                    </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="brbottom brtop">
                        <div class="lncenter">
                            <p class="lntitle">DDOS防护</p>
                            <span class="ln_more">></span>
                            <p class="lndetail"><a href="#">监控类 </a> / <a href="#">防护类</a></p>
                            <div class="lndetails">
                                <div class="lndetails_line">
                                    <div class="fl lndetailtitle">热门厂商：</div>
                                    <div class="fl lndetaillist"><a href="#">中国电信</a>     <a href="#">华为</a></div>
                                </div>
                                <div class="lndetails_line">
                                    <div class="fl lndetailtitle">热门服务：</div>
                                    <div class="fl lndetaillist"><a href="#">日常流量监控</a><a href="#">日常攻击防护服务</a><a href="#">突发异常流量清洗服务</a></div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="brbottom brtop">
                        <div class="lncenter">
                            <p class="lntitle">安全评估</p>
                            <span class="ln_more">></span>
                            <p class="lndetail"></p>
                            <div class="lndetails">
                               <div style="color:#666; font-size:28px; line-height:48px; margin-left:20px;"><font color="#4593fd">建设中....</font></div>   
                               <!-- <div class="lndetails_line">
                                    <div class="fl lndetailtitle">热门厂商：</div>
                                    <div class="fl lndetaillist"><a href="#">中国电信</a><a href="#">奇虎360</a>     <a href="#">安恒</a></div>
                                </div>
                                <div class="lndetails_line">
                                    <div class="fl lndetailtitle">热门服务：</div>
                                    <div class="fl lndetaillist"><a href="#">漏洞扫描服务</a><a href="#">恶意代码监测服务</a><a href="#">网页篡改检测服务</a><a href="#">关键字检测服务</a><a href="#">可用性监测服务</a></div>
                                </div> -->
                            </div>
                        </div>
                    </li>
                    <li class="brtop">
                        <div class="lncenter">
                            <p class="lntitle">移动安全</p>
                            <span class="ln_more">></span>
                            <p class="lndetail"></p>
                            <div class="lndetails" style="bottom:0;top:auto;">
                                <div style="color:#666; font-size:28px; line-height:48px; margin-left:20px;"><font color="#4593fd">建设中....</font></div>
                                <!--<div class="lndetails_line">
                                    <div class="fl lndetailtitle">热门厂商：</div>
                                    <div class="fl lndetaillist"><a href="#">中国电信</a><a href="#">奇虎360</a>     <a href="#">安恒</a></div>
                                </div>
                                <div class="lndetails_line">
                                    <div class="fl lndetailtitle">热门服务：</div>
                                    <div class="fl lndetaillist"><a href="#">漏洞扫描服务</a><a href="#">恶意代码监测服务</a><a href="#">网页篡改检测服务</a><a href="#">关键字检测服务</a><a href="#">可用性监测服务</a></div>
                                </div> -->
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
         <div class="subnav fl" id="play">
          <div class="">
            
          </div>
           <!--<p class="prev">&laquo;</p>
          <p class="next">&raquo;</p>-->
             <ul>
                  <li style="position:relative"><a href="###">
                  <img src="${ctx}/source/images/indexphoto.jpg" />
                  <span class="lb_font lb_first">累计检测<span class="lb_font_w" id="num">${webPageNum }</span>个网页</span>
                  <span class="lb_font lb_second">发现<span class="lb_font_w" id="num1">${leakNum }</span>个漏洞</span>
                  <span class="lb_font lb_third">累计拦截<span class="lb_font_w" id="num2"></span>次DDOS攻击</span>
                  <span class="lb_font lb_forth">完成<span class="lb_font_w" id="num3"></span>G防护</span>
                  </a></li>
                  <li><a href="###"><img src="${ctx}/source/images/indexphoto2.jpg" /></a></li>
                  <li><a href="###"><img src="${ctx}/source/images/indexphoto3.jpg" /></a></li>
             </ul>
             <ol>
                 <li  class="active"></li>
                 <li></li>
                 <li></li>
             </ol>
         </div>
         <div class="fl right_nav">
            <div class="right_title">系统公告</div>
            <div class="right_list">
                <ul>
                <c:forEach var="list" items="${noticeList}" varStatus="status">
                    <li><a href="${ctx}/noticeDescUI.html?id=${list.id}" title="${list.noticeName }">
                        <c:if test="${fn:length(list.noticeName)<=9}">
                                    ${list.noticeName }
                        </c:if>
                        <c:if test="${fn:length(list.noticeName)>9}">
                                ${fn:substring(list.noticeName, 0, 9)}...
                        </c:if>
                    </a></li>
                </c:forEach>
                </ul>
            </div>
            <div class="wechatebox">
		   	  	<img src="${ctx}/source/images/wechateer.jpg"/>
		      	<p class="wechatenam">anquanbang</p>
		        <p class="wechatep">扫一扫关注微信</p>
		      </div>
		    	<img src="${ctx}/source/images/wechatsmall.png" width="27" height="27" class="wechateicon" />
            <!-- <div><a href="#"><img src="${ctx}/source/images/indexsmall.jpg" width="152" height="202" /></a></div> -->
         </div>
         <script>
    	$('.wechateicon').hover(function(){$('.wechatebox').show();},function(){$('.wechatebox').hide();});
    	</script>
         </div>
         <!-- <div class="web_input">
                    <input type="text"  value="http://"/>
                    <div class="web_input_r"><img src="${ctx}/source/images/user_ico_16.jpg" /></div>
         </div>  -->
         <div class="center clear">
             <div class="web_fuwu clear">
                 <h1>WEB云安全服务</h1>
                 <c:forEach var="list" items="${servList}" varStatus="status">
                    <c:choose>
                       <c:when test="${status.index<4}">
                           <div class="web_main bor_right">
                            <a href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1">
                            <div class="web_main_pic"><img src="${ctx}/source/images/center_${status.index+1 }.png" /></div>
                            <h2>${list.name }</h2>
                            <p>${list.remarks }</p></a>
                           </div>
                       </c:when>
                       <c:when test="${status.index==4}">
                           <div class="web_main">
                            <a href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1">
                            <div class="web_main_pic"><img src="${ctx}/source/images/center_${status.index+1 }.png" /></div>
                            <h2>${list.name }</h2>
                            <p>${list.remarks }</p></a>
                           </div>
                       </c:when>
                    </c:choose>
                 </c:forEach>
             </div>
             
             <div class="anti_fuwu clear">
                  <h1>Anti-DDOS云安全服务</h1>
                  <c:forEach var="list" items="${servList}" varStatus="status">
                    <c:choose>
                       <c:when test="${status.index==5||status.index==6}">
                           <div class="anti_main bor_right">
                             <a href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1">
                             <div class="anti_main_pic"><img src="${ctx}/source/images/center_${status.index+1 }.png" /></div>
                             <div class="anti_main_right">
                                <h2>${list.name }</h2>
                                <p>${list.remarks }</p>
                            </div></a>
                          </div>
                       </c:when>
                       <c:when test="${status.last}">
                           <div class="anti_main">
                             <a href="${ctx}/selfHelpOrderInit.html?type=${list.orderType }&serviceId=${list.id }&indexPage=1">
                             <div class="anti_main_pic"><img src="${ctx}/source/images/center_${status.index+1 }.png" /></div>
                             <div class="anti_main_right">
                                <h2>${list.name }</h2>
                                <p>${list.remarks }</p>
                            </div></a>
                          </div>
                       </c:when>
                    </c:choose>
                 </c:forEach>
             </div>
         </div>
    </div>
    <div class="bottom_bj">
        <div class="bottom">
             <div class="bottom_main">
                 <h3><a href="###">新手入门</a></h3>
                 <ul>
                      <li><a href="###">新用户注册</a></li>
                      <li><a href="###">用户登录</a></li>
                      <li><a href="###">找回密码</a></li>
                 </ul>
             </div>
              <div  class="bottom_main">
              <h3><a href="###">    帮助</a></h3>
                 <ul>
                      <li><a href="###">常见问题</a></li>
                    
                 </ul>
             </div>
              <div  class="bottom_main">
               <h3><a href="###">厂商合作</a></h3>
                 <ul>
                      <li><a href="###">华为</a></li>
                      <li><a href="###">安恒</a></li>
                      <li><a href="###">360</a></li>
                 </ul>
             </div>
              <div  class="bottom_main">
              <h3><a href="###">联系我们</a></h3>
                 <ul>
                      <li><a href="###">客户电话</a></li>
             </div>
              <div  class="bottom_main" style="width:380px;">
              <h3><a href="###">版权信息</a></h3>
             
             </div>
        </div>
    </div>
</div>
</body>
</html>

