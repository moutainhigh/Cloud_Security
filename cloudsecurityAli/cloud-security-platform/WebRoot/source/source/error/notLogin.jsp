<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<title>toLoginUI</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
</head>
 <BODY onload = "showTimer()"> 
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
                   <li><a href="${ctx}/aider.html">在线帮助</a></li>
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
				<td width="49" height="45"></td>
				<td style="word-break:break-all" align="center">
					<font face="宋体" size="4" color="gray">
						<b>您尚未登录或登录超时，系统将在5秒内跳转到登录界面...</b>
                	</font>
                </td>
			</tr>
			<tr>
				<td width="39" height="34"></td>
				<td style="word-break:break-all" align="center">
					<font face="黑体" size="3" color="red">
						<div id ="timer" style="color:#999;font-size:20pt;text-align:center"></div>
                	</font>
                </td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</BODY>
<script>
var i=6;
var t;
function showTimer(){
 if(i==0){//如果秒数为0的话,清除t,防止一直调用函数,对于反应慢的机器可能实现不了跳转到的效果，所以要清除掉 setInterval()
  parent.location.href="${ctx}/loginUI.html";
  window.clearInterval(t);

  }else{
  i = i - 1 ;
  // 秒数减少并插入 timer 层中
  document.getElementById("timer").innerHTML= i+"秒";
  }
}
// 每隔一秒钟调用一次函数 showTimer()
t = window.setInterval(showTimer,1000);
</script>
</body>
</html>