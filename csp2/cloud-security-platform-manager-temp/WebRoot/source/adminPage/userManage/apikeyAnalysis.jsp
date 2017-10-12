<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>APIKey分析</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/raphael.2.1.0.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/justgage.1.0.1.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/apikeyAnalysis.js"></script>
</head>

<body>
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul>
      	<li><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
		<li><a href="${ctx}/adminchinas.html" target="_blank" class="white">安全态势</a></li>
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAssetUI.html" class="white">资产分析</a></li>
        <li><a href="${ctx}/adminUserAnalysisUI.html" class="white">用户分析</a></li>
        <!-- <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">订单分析</a></li>-->
        <li><a href="${ctx}/orderformanalyse.html" class="white">订单分析</a></li>
        <li><a href="${ctx}/adminWarnAnalysisUI.html" class="white">告警分析</a></li>
        <li><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
        <li><a href="${ctx}/adminAdvertisementManageUI.html" class="white">广告管理</a></li>
        <li class="b_current" style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminApikeyUI.html" class="white">APIKEY分析</a></li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left b_lagst_left"> <a href="#"><img src="${ctx}/source/adminImages/b_photo.jpg" width="43" height="42"></a> </div>
      <div class="lagst-right">
        <p ><a href="###" class="white">${sessionScope.admin_user.name }</a></p>
        <p> <a href="${ctx}/adminExit.html" class="white">退出</a></p>
      </div>
    </div>
  </div>
</div>
<!--头部代码结束-->
<div class="main_wrap">
	<div class="main_center user">
        <div class="tabBox tabCont">
            <div class="item clearfix tabItem" style="display:block">
            		<form class="clearfix analysecent user_form" action="${ctx}/adminApikeyUI.html" style="margin-top:40px">
                               
                         <div class="analyse_lable fl">
                            <label>用户名</label>
                            <input type="text" class="text" id="userName" name="userName">
                        </div>
                        <div class="analyse_lable fl">
                            <label>APIKEY</label>
                            <input type="text" class="text" id="apikey" name="apikey">
                        </div>
                        <input type="button" class="sub" value="" style="right:-130px;" onclick="findApikey()">
                    </form>	
                    <div class="tableBox">
                    				
                                	<table cellpadding="0" cellspacing="0" border="1" width="954" bordercolor="#e0e0e0">
                                    <thead>
                                        <tr style="width:100%">
                                            <th style="width:100px">序号</th>
                                            <th colspan="2" style="width:565px">APIKEY</th>
                                            <th colspan="2" >用户名</th>
                                        </tr>
                                    </thead>
                                    <tbody id="body1">
                                        <tr>
                                            <td></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                    </tbody>
                                </table>
                                
                                </div>
            </div>
        
        </div>
    </div>
</div>
<!--尾部部分代码-->
<div class="bottom_bj">
<div class="bottom">
<div class="bottom_main">
  <h3><a href="###">新手入门</a></h3>
  <ul>
      <li><a href="${ctx}/registUI.html">新用户注册</a></li>
    <li><a href="${ctx}/loginUI.html">用户登录</a></li>
    <li><a href="###">找回密码</a></li>
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
    <li><a href="###">华为</a></li>
    <li><a href="###">安恒</a></li>
    <li><a href="###">知道创宇</a></li>
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
 <li>Copyright&nbsp;©&nbsp;2015 中国电信股份有限公司北京研究院<br />
京ICP备12019458号－10</li>
</div>
</div>
</div>
<!--尾部部分代码结束-->
</body>
</html>
