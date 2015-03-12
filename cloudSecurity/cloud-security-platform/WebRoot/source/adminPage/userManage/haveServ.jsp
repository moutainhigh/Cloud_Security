<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
    
    <title>活跃用户</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <table class="user_table" cellpadding="5" cellspacing="5" >
 	<tr>
    	<th class="t_username">用户名</th>
        <th class="t_service">用户服务数</th>
    </tr>
  	<c:forEach items="${listHaveServ}" var="dataAnalysis">
       	<tr>
       	   <td>${dataAnalysis.name}</td>
           <td>${dataAnalysis.count}</td>
     	</tr>
      </c:forEach>
      </table>
  </body>
</html>
