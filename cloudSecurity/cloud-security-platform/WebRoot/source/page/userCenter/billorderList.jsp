<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", -10); 
%>
        <c:forEach items="${list}" var="order"> 
              <tr>
                <td>${order.id}</td>
                <td>
                    <c:if test="${order.type==1}">长期</c:if>
                    <c:if test="${order.type==2}">单次</c:if> 
               </td>
                <td>
                ${order.name}
                </td>
                <td><fmt:formatDate value="${order.begin_date}" pattern="yyyy-MM-dd HH:mm:ss"/>~<fmt:formatDate value="${order.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><fmt:formatDate value="${order.create_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td class="seedetail" value="0" name="${order.id}" onclick="seedetail(this)"><span>查看详情</span></td>
              </tr>
              <tr  class="detailbox">
                <td colspan="6"><div  class="zhangd_div">
                    <div class="zhangd_ding"></div>
                                    服务对象资产个数&nbsp;&nbsp; &nbsp; <span id="${order.id}"></span> &nbsp;&nbsp; &nbsp; 
                          <c:if test="${order.type!=2}">         
                                    扫描频率 &nbsp;&nbsp; &nbsp; 
                                    <span id="scan_type">
                                        <c:if test="${order.scan_type==1}">每天</c:if>
                                        <c:if test="${order.scan_type==2}">每周</c:if>
                                        <c:if test="${order.scan_type==3}">每月</c:if>
                                    </span> &nbsp;&nbsp; &nbsp;  
                         </c:if> 
                                    扫描次数 &nbsp;&nbsp; &nbsp; <span id="${order.id}scan"></span> </div></td>
              </tr>
          
       </c:forEach> 