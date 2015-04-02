<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", -10); 
%>
        <c:forEach var="list" items="${orderList}" varStatus="status">
                  <tr>
                    <td><a href="${ctx}/orderDetails.html?orderId=${list.id }" target="_blank">${list.id }</a></td>
                    <td>
                        <c:if test="${list.type==1}">长期</c:if>
                        <c:if test="${list.type==2}">单次</c:if> 
                    </td>
                    <td>
                        <c:set var="temp" value="${nowDate }"/>
                        <c:if test="${list.type==1}">
                            <!-- <c:if test="${list.status==0||list.end_date>=temp}">服务中</c:if>
                            <c:if test="${list.end_date<temp}">已结束</c:if> -->
                            <c:choose>
                                <c:when test="${list.status==0||list.end_date>=temp}">服务中</c:when>
                                <c:otherwise>已结束</c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:if test="${list.type==2}">
                            <!-- <c:if test="${list.begin_date>=temp}">服务中</c:if> -->
                            <!-- <c:if test="${list.begin_date<temp}">服务中</c:if> -->
                            <!--<c:if test="${list.status!=0}">已结束</c:if>-->
                            <!--<c:if test="${list.status==0}">扫描中</c:if>-->
                            <c:choose>
	                            <c:when test="${list.status==0||list.begin_date>temp}">扫描中</c:when>
	                            <c:otherwise>已结束</c:otherwise>
                            </c:choose>
                        </c:if>
                    </td>
                    <td>
                       ${list.name}                         
                    </td>
                    <td><fmt:formatDate value="${list.begin_date}" pattern="yyyy-MM-dd HH:mm:ss"/>~<fmt:formatDate value="${list.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${list.create_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
                       <c:if test="${list.status!=2}"><img src="${ctx}/source/images/user_ico_2.jpg" /></c:if>
                       <c:if test="${list.status==2}"><a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}" target="_blank"><img src="${ctx}/source/images/user_ico_1.jpg" /></a></c:if>
                    </td>
                  </tr>
              </c:forEach>