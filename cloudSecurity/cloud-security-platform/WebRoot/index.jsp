<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>云安全服务平台</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/index.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="${ctx}/source/scripts/common/index.js"></script>

</head>

<body>
<div>
    <div class="head_bj">
        <div class="head">
           <div class="logo"><img src="${ctx}/source/images/logo.png" /></div>
           <div class="lagst">
               <div class="lagst-left">
                   <a href="###"><img src="${ctx}/source/images/ren.png" /></a>
               </div>
               <div class="lagst-right">
               <!-- 如果已经登录则显示用户名，否则需要登录 -->
               <c:if test="${sessionScope.globle_user!=null }">
                <p><a href="###" style="color: #fff">${sessionScope.globle_user.name }</a></p>
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
                   <li><a href="###">我的订单</a></li>
                   <li><a href="aider.html">在线帮助</a></li>
                   <li style="border-right:1px solid #11871d;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
               </ul>
           </div>
        </div>
    </div>
    <div class="nav">
         <div class="subnav" id="play">
           <!--<p class="prev">&laquo;</p>
          <p class="next">&raquo;</p>-->
             <ul>
                  <li><a href="###"><img src="${ctx}/source/images/1.jpg" /></a></li>
                  <li><a href="###"><img src="${ctx}/source/images/1.jpg" /></a></li>
                  <li><a href="###"><img src="${ctx}/source/images/1.jpg" /></a></li>
             </ul>
             <ol>
                 <li  class="active"></li>
                 <li></li>
                 <li></li>
             </ol>
         </div>
         <div class="center clear">
             <div class="web_fuwu clear">
                 <h1>WEB云安全服务</h1>
                 <div class="web_main bor_right"> 
                    <div class="web_main_pic"><img src="${ctx}/source/images/center_1.png" /></div>
                    <h2><a href="${ctx}/selfHelpOrderInit.html?type=0">漏洞扫描服务</a></h2>
                    <p>漏洞扫描是对你的电脑进行全方位的扫描，检查你当前的系统是否有漏洞，如果有漏洞则需要马上进行修复。</p>
                 </div>
                 <div class="web_main bor_right">
                     <div class="web_main_pic"><img src="${ctx}/source/images/center_2.png" /></div>
                    <h2><a href="${ctx}/selfHelpOrderInit.html?type=0">恶意代码监测服务</a></h2>
                    <p>漏洞扫描是对你的电脑进行全方位的扫描，检查你当前的系统是否有漏洞，如果有漏洞则需要马上进行修复。</p>
                 </div>
                 <div class="web_main bor_right">
                     <div class="web_main_pic"><img src="${ctx}/source/images/center_3.png" /></div>
                    <h2><a href="${ctx}/selfHelpOrderInit.html?type=1">网页篡改监测服务</a></h2>
                    <p>漏洞扫描是对你的电脑进行全方位的扫描，检查你当前的系统是否有漏洞，如果有漏洞则需要马上进行修复。</p>
                 </div>
                <div class="web_main bor_right">
                    <div class="web_main_pic"><img src="${ctx}/source/images/center_4.png" /></div>
                    <h2><a href="${ctx}/selfHelpOrderInit.html?type=0">关键字监测服务</a></h2>
                    <p>漏洞扫描是对你的电脑进行全方位的扫描，检查你当前的系统是否有漏洞，如果有漏洞则需要马上进行修复。</p>
                 </div>
                 <div class="web_main">
                     <div class="web_main_pic"><img src="${ctx}/source/images/center_5.png" /></div>
                    <h2><a href="${ctx}/selfHelpOrderInit.html?type=1">可用性监测服务</a></h2>
                    <p>漏洞扫描是对你的电脑进行全方位的扫描，检查你当前的系统是否有漏洞，如果有漏洞则需要马上进行修复。</p>
                 </div>
             </div>
            	<div class="web_input">
                    <input type="text"  value="http://"/>
                    <div class="web_input_r"><img src="${ctx}/source/images/user_ico_16.jpg" /></div>
              </div>
             <div class="anti_fuwu clear">
                  <h1>Anti-DDOS云安全服务</h1>
                  <div class="anti_main bor_right">
                     <div class="anti_main_pic"><img src="${ctx}/source/images/center_6.png" /></div>
                     <div class="anti_main_right">
                        <h2><a href="${ctx}/selfHelpOrderInit.html?type=1">日常流量监控服务</a></h2>
                        <p>漏洞扫描是对你的电脑进行全方位的扫描，检查你当前的系统是否有漏洞，如果有漏洞则需要马上进行修复。</p>
                    </div>
                  </div>
                   <div class="anti_main bor_right">
                     <div class="anti_main_pic"><img src="${ctx}/source/images/center_7.png" /></div>
                     <div class="anti_main_right">
                        <h2><a href="${ctx}/selfHelpOrderInit.html?type=1">日常攻击防护服务</a></h2>
                        <p>漏洞扫描是对你的电脑进行全方位的扫描，检查你当前的系统是否有漏洞，如果有漏洞则需要马上进行修复。</p>
                    </div>
                  </div>
                  <div class="anti_main">
                     <div class="anti_main_pic"><img src="${ctx}/source/images/center_8.png" /></div>
                     <div class="anti_main_right">
                        <h2><a href="${ctx}/selfHelpOrderInit.html?type=2">突发异常流量清洗服务</a></h2>
                        <p>漏洞扫描是对你的电脑进行全方位的扫描，检查你当前的系统是否有漏洞，如果有漏洞则需要马上进行修复。</p>
                    </div>
                  </div>
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
                 <ul>
                      <li>如未经授权用作他处，将保留追究侵权者法律责任的权利。<br />
京ICP备11111111号-4 / 京ICP证1111111号<br />
北京市公安局朝阳分局备案编号:110105000501</li>
             </div>
        </div>
    </div>
</div>
</body>
</html>

