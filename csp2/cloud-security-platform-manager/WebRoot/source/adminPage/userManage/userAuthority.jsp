<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>权限管理</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.JPlaceholder.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/authority.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript">
$(document).ready(function(){
	var list = ${list};
		for(var i=0;i<list.length;i++){
			document.getElementById(""+list[i]).checked = true;
		};
});
</script>
</head>

<body>
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul>
       <li class="b_current"><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
        <li><a href="${ctx}/adminchinas.html" target="_blank" class="white">安全态势</a></li>
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">数据分析</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
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
	<div class="main_center">
    	<div class="add_service">
        	<span class="addr">当前位置 : </span><a href="${ctx}/adminUserManageUI.html" class="addr">用户管理</a><span class="addr"> > </span><a href="${ctx}/adminAuthorityUI.html" class="addr">用户权限</a>
        </div>
        <div class="juris_wrap">
        	<table class="juris_table fl" cellpadding="1" cellspacing="1">
            	<thead>
                	<tr>
                    	<th>功能</th>
                        <th>超级管理员</th>
                        <th>系统管理员</th>
                        <th>注册用户</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${authorityList}" var="authority" begin="0" end="7">
	                	<tr>
	                    	<td>${authority.authorityName}</td>
	                        <td><input type="checkbox" name="subcheck" id="${authority.id}0" onclick="getcheckbox(${authority.id},0)"/></td>
	                        <td><input type="checkbox" name="subcheck" id="${authority.id}1" onclick="getcheckbox(${authority.id},1)"/></td>
	                        <td><input type="checkbox" name="subcheck" id="${authority.id}2" onclick="getcheckbox(${authority.id},2)"/></td>
	                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <table class="juris_table fl ml12" cellpadding="1" cellspacing="1">
            	<thead>
                	<tr>
                    	<th>功能</th>
                        <th>超级管理员</th>
                        <th>系统管理员</th>
                        <th>注册用户</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${authorityList}" var="authority" begin="8">
	                	<tr>
	                    	<td>${authority.authorityName}</td>
	                        <td><input type="checkbox" name="subcheck" id="${authority.id}0" onclick="getcheckbox(${authority.id},0)"/></td>
	                        <td><input type="checkbox" name="subcheck" id="${authority.id}1" onclick="getcheckbox(${authority.id},1)"/></td>
	                        <td><input type="checkbox" name="subcheck" id="${authority.id}2" onclick="getcheckbox(${authority.id},2)"/></td>
	                    </tr>
                    </c:forEach>
                    <tr>
                    	<td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
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
