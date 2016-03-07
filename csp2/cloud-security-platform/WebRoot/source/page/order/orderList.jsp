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
             <td><a class="listnumber" id="${list.id }" href="${ctx}/orderDetails.html?orderId=${list.id }" target="_blank" onmouseover="overfind('${list.id }')" >${list.id }</a></td>
             <td>
                 <c:if test="${list.type==1}">长期</c:if>
                 <c:if test="${list.type==2}">单次</c:if> 
             </td>
             <td>
                 <c:set var="temp" value="${nowDate }"/>
                 <c:if test="${list.begin_date>temp}">已下单</c:if>
                 <c:if test="${list.begin_date<=temp&&(list.status==0||list.status==3||list.status==4||list.status==5)}">服务中</c:if>
                 <c:if test="${list.status==1||list.status==2}">已结束</c:if>
                 
             </td>
             <td>
                  ${list.name }                 
             </td>
             <td><fmt:formatDate value="${list.begin_date}" pattern="yyyy-MM-dd HH:mm:ss"/>~<fmt:formatDate value="${list.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
             <td><fmt:formatDate value="${list.create_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
             <td>
             <c:if test="${list.begin_date>temp||list.status==0}"><img src="${ctx}/source/images/status_3.jpg" title="等待"/></c:if>
             
             <c:if test="${list.serviceId==1||list.serviceId==2||list.serviceId==3||list.serviceId==4||list.serviceId==5}">
                <c:if test="${list.status==2}">
                 <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank">
                 <c:if test="${list.alarmViewedFlag==0}">
                 <img src="${ctx}/source/images/status_1.jpg" title="已完成有告警"/>
                 </c:if>
                 <c:if test="${list.alarmViewedFlag==1}">
                 <img src="${ctx}/source/images/status_5.png" title="已完成有告警(已查看)"/>
                 </c:if>
                 </a>
                </c:if>
                <c:if test="${list.status==1}">
                 <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank">
                 <img src="${ctx}/source/images/status_2.jpg" title="已完成无异常"/>
                 </a>
                </c:if>
                <!-- 安恒的服务 -->
                <c:if test="${list.begin_date<=temp&&(list.status==4||list.status==5)&&list.websoc!=2}"><a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank"><img src="${ctx}/source/images/status_4.jpg" title="服务中"/></a></c:if>
                <c:if test="${list.begin_date<=temp&&list.status==3}"><a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank">
                 <c:if test="${list.alarmViewedFlag==0}">
                <img src="${ctx}/source/images/status_1.jpg" title="服务中有告警"/>
                </c:if>
                 <c:if test="${list.alarmViewedFlag==1}">
                <img src="${ctx}/source/images/status_5.png" title="服务中有告警(已查看)"/>
                </c:if>
                </a></c:if>
             </c:if>
                
                <!-- 华为的服务 -->
             <c:if test="${list.serviceId==6||list.serviceId==7||list.serviceId==8}">
                <c:if test="${list.begin_date<=temp&&list.status==0}"><img src="${ctx}/source/images/status_4.jpg" title="服务中"/></c:if>
                <c:if test="${list.status==2}">
                 <a href="${ctx}/warningTwoAnHeng.html?orderId=${list.id }&type=${list.type}" target="_blank">
                <c:if test="${list.alarmViewedFlag==0}">               
                 <img src="${ctx}/source/images/status_1.jpg" title="有告警"/>
                  </c:if>
                 <c:if test="${list.alarmViewedFlag==1}">
                   <img src="${ctx}/source/images/status_1.jpg" title="有告警(已查看)"/>
                  </c:if>
                 </a>
                </c:if>
                <c:if test="${list.status==1}">
                 <a href="${ctx}/warningTwoAnHeng.html?orderId=${list.id }&type=${list.type}" target="_blank">
                 防护
                 </a>
                </c:if>
             </c:if>
             <!-- 订单删除操作 -->
             <c:if test="${list.status==1||list.status==2||list.status==0}"><a href="javascript:void(0)" onclick="deleteOrder('${list.id}','${list.begin_date}')"><img src="${ctx}/source/images/del.png" title="删除"/></a></c:if>
             <c:if test="${list.status==4}"><a href="javascript:void(0)" onclick="optOrder('${list.id}','${list.status}')"><img src="${ctx}/source/images/stop.png" title="暂停"/></a></c:if>
             <c:if test="${list.status==5}"><a href="javascript:void(0)" onclick="optOrder('${list.id}','${list.status}')"><img src="${ctx}/source/images/resume.png" title="启动"/></a></c:if>
             </td>
           </tr>
       </c:forEach>