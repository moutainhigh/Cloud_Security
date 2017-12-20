<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<title>错误提示</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/chinatelecom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
</head>
<body bgcolor="#FFFFFF"> 
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
                   <li><a href="${ctx}/knowUs.html">在线帮助</a></li>
                   <li style="border-right:1px solid #11871d;"><a href="${ctx}/userCenterUI.html">用户中心</a></li>
               </ul>
           </div>
        </div>
    </div>
<form name="form1" method="post">
<table border="0" width="100%" id="table1" height="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center">
		<table border="0" width="60%" id="table2" cellspacing="0" cellpadding="0">
			<tr>
				<td style="word-break:break-all" align="center">
					<font face="黑体" size="4" color="gray">
						<b>
							<c:if test="${requestScope.errorMsg!=null}">   
							   ${requestScope.errorMsg}
							</c:if>
								<c:if test="${errorMsg!=null}">   
							   ${errorMsg}
							</c:if>   
						</b>
                	</font>
                </td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
