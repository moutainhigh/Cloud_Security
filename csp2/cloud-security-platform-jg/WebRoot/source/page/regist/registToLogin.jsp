<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>注册成功</title>
</head>
 <BODY onload = "showTimer()"> 
<form name="form1" method="post">
<table border="0" width="100%" id="table1" height="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center">
		<table border="0" width="60%" id="table2" cellspacing="0" cellpadding="0">
			<tr>
				<td width="49" height="45"></td>
				<td style="word-break:break-all" align="center">
					<font face="宋体" size="4" color="gray">
						<b>恭喜，注册成功！系统将在5秒内跳转到登录界面...</b>
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
  parent.location.href="${pageContext.request.contextPath }/loginUI.html";
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