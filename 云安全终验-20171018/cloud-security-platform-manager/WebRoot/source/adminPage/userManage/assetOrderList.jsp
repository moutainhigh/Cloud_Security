<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
    
    <title>资产订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
.table-c table{border-right:1px solid #000;border-bottom:1px solid #000}
.table-c table td{border-left:1px solid #000;border-top:1px solid #000}
</style>
  </head>
  
  <body style="text-align: center;">
  <h3>订单列表(${asset.addr})</h3>
  <div class="table-c">
	  <table  border="0" cellspacing="0" cellpadding="0" align="center" width="80%">
	  <!-- <table  border="1" align="center" width="80%"> -->
	 	<tr> 
	 		<td>序号</td>
	 		<td>订单编号</td>
	 		<td>订单类型</td>
	 		<td>订单服务</td>
	 		<td>创建时间</td>
	    </tr>
	  	<c:forEach items="${list }" var="list" varStatus="status">
	       	<tr>
	       	   <td>${status.index+1 }</td>
	       	   <td>
	       	   <a href="${ctx}/adminwarningInit.html?orderId=${list.id}&type=${list.type}&websoc=${list.websoc}" target="_blank">
	       	   ${list.id}</a>
	       	   </td>
	       	   <td>
	       	   	 <c:if test="${list.type==1}">长期</c:if>
                 <c:if test="${list.type==2}">单次</c:if>
	       	   </td>
		 	   <td>${list.name}</td>
		 	   <td><fmt:formatDate value="${list.create_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		 	   
	     	</tr>
	      </c:forEach>
	      </table>
      </div>
      </br>



  </body>
</html>