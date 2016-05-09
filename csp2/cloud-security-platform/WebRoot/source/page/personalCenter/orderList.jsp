<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", -10); 
%>

        <c:forEach var="list" items="${orderList}" varStatus="status">
           <div class="mnlist">
        	<div class="tablist-head clearfix">
            	<span class="fl">订单编号：${list.id }</span>
                <span class="fr" style="padding-right:20px;">下单时间：<fmt:formatDate value="${list.create_date}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
            </div>
        
            <div class="tabList">
            	<table cellpadding="0" cellspacing="0">
                	<tbody>
                    	<tr valign="middle" align="center">
                        	<td class="order">
                            	<div class="fl htd" style="width:207px; padding-left:90px;">
                                    <span class="tl fl">
                                        <img src="${ctx}/source/images/personalCenter/${list.serviceId }.png" alt="" style="position:relative; top:6px;">
                                    </span>
                                    
                                    <div class="tr fl">
                                        <div class="">
                                         <h2>
                                        	<c:if test="${list.isAPI==1}">
							             	 ${fn:replace(list.name, "服务", "API")}  
							                </c:if> 
							                <c:if test="${list.isAPI!=1}">
							                 ${list.name}   
							                </c:if>   
                                         </h2></div>
                                    	<c:forEach var="asset" items="${list.assetList}" varStatus="status">
                                        	<div class="listDiv"><p style="text-align: left; margin:0;">资产服务：${asset.name}</p></div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </td>
                            <td class="order" valign="top">
                            	<p class="stylep" style="width:84px;">
								<c:if test="${list.type==1}">长期</c:if>
								<c:if test="${list.type==2}">单次</c:if> 
								</p>
                            
                            </td>
                            <td class="order" valign="top">
                            	<c:set var="temp" value="${nowDate }"/>
				                <c:if test="${list.begin_date>temp}"><p class="stylep" style="width:108px;">已下单<b class="wait"></b></p></c:if>
				                <c:if test="${list.begin_date<=temp&&(list.status==4||list.status==5)&&list.websoc!=2}"><p class="stylep" style="width:108px;">服务中<b class="ing"></b></p></c:if>
				                <c:if test="${list.status==1||list.status==2}"><p class="stylep" style="width:108px;">已结束<b class="end"></b></p></c:if>
                            </td>
                            <td class="order" valign="top">
                            	<p style="width:174px; line-height:24px; margin-top:33px;" class="stylep"><fmt:formatDate value="${list.begin_date}" pattern="yyyy-MM-dd HH:mm:ss"/><br><fmt:formatDate value="${list.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                        	
                            </td>
                            <td class="order" valign="top">
                            	 <p style="width:160px; height:auto; line-height:24px; margin-top:33px;" class="stylep">0.00</p>
                        		<p style="width:160px;height:auto;"><em>(使用优惠劵999.00)</em></p>
                            </td>
                             <td class="order" valign="top">
                            	<p style="width:98px; line-height:24px;margin-top:33px; height:auto;" class="stylep">
                            	
                            		<c:if test="${list.begin_date>temp||list.status==0}"><a href="#" title="等待">查看详情</a></c:if>
             
						            <c:if test="${list.serviceId==1||list.serviceId==2||list.serviceId==3||list.serviceId==4||list.serviceId==5}">
						                <c:if test="${list.status==2}">
						                 
						                 <!-- <c:if test="${list.alarmViewedFlag==0}">
						                 <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank" title="已完成有告警">
						                 	查看详情
						                 </a>
						                 </c:if>
						                 <c:if test="${list.alarmViewedFlag==1}">
						                 <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank" title="已完成有告警(已查看)">
						                 	查看详情
						                 </a>
						                 </c:if> -->
						                 <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank" title="已完成有告警">
						                 	查看详情
						                 </a>
						                 
						                </c:if>
						                <!-- modify by 2016-4-13 -->
						                <c:if test="${list.status==1&&list.isAPI!=1}">
						                 <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank" title="已完成无异常">
						                 	查看详情
						                 </a>
						                </c:if>
						                <c:if test="${list.status==1&&list.isAPI==1}">
						                 <a href="" title="已完成">
						                 	查看详情
						                 </a>
						                </c:if>
						                <!-- end -->
						                
						                <!-- 安恒的服务 -->
						                <c:if test="${list.begin_date<=temp&&(list.status==4||list.status==5)&&list.websoc!=2}">
						                <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank" title="服务中">
						                	查看详情
						                </a></c:if>
						                <c:if test="${list.begin_date<=temp&&list.status==3}">
							                <!-- <c:if test="${list.alarmViewedFlag==0}">
							                <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank" title="服务中有告警">
							                	查看详情
							                </a>
							                </c:if>
							                
							                <c:if test="${list.alarmViewedFlag==1}">
							                <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank" title="服务中有告警(已查看)">
							                	查看详情
							                </a>
							                </c:if> -->
							                <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank" title="服务中有告警">
							                	查看详情
							                </a>
						                </c:if>
						            </c:if>
						                
						                <!-- 华为的服务 -->
						            <c:if test="${list.serviceId==6||list.serviceId==7||list.serviceId==8}">
						                <c:if test="${list.begin_date<=temp&&list.status==0}">
						                <a href="" title="服务中">
						                	查看详情
						                </a>
						                </c:if>
						                <c:if test="${list.status==2}">
							                <!-- <c:if test="${list.alarmViewedFlag==0}"> 
							                  <a href="${ctx}/warningTwoAnHeng.html?orderId=${list.id }&type=${list.type}" target="_blank" title="有告警">              
							                 	查看详情
							                  </a>
							                </c:if>
							                <c:if test="${list.alarmViewedFlag==1}">
							                  <a href="${ctx}/warningTwoAnHeng.html?orderId=${list.id }&type=${list.type}" target="_blank" title="有告警(已查看)">
							                   	查看详情
							                  </a>
							                </c:if> -->
							                <a href="${ctx}/warningTwoAnHeng.html?orderId=${list.id }&type=${list.type}" target="_blank" title="有告警">              
							                 	查看详情
							                </a>
						                </c:if>
						                <c:if test="${list.status==1}">
						                 <a href="${ctx}/warningTwoAnHeng.html?orderId=${list.id }&type=${list.type}" target="_blank">
						                 防护
						                 </a>
						                </c:if>
						            </c:if>
                            	</p>
                       			<p style="width:98px; height:auto;">
									<!-- 订单删除操作 -->
             						<a href="javascript:void(0)" onclick="deleteOrder('${list.id}','${list.begin_date}')">删除</a>
								</p>
                            </td>
                        </tr>
                    
                    </tbody>
                
                </table>
            	
            </div>
        </div>
    </c:forEach>