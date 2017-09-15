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

        <c:forEach var="olist" items="${orderList}" varStatus="status">
           <div class="mnlist">
        	<div class="tablist-head clearfix">
            	<span class="fl">订单编号：${olist.orderListId }</span>
                <span class="fr" style="padding-right:20px;">下单时间：<fmt:formatDate value="${olist.create_date}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
            </div>
        
	        <style>
	        a{color:#2499fb}
	        a:visited{color: #2499fb;}
	        </style>
	        
            <div class="tabList">
            	<table cellpadding="0" cellspacing="0">
                	<tbody>
                	    <c:forEach var="list" items="${olist.order}" varStatus="status">
                    	<tr valign="middle" align="center">
                        	<td class="order">
                            	<div class="fl htd" style="width:207px; padding-left:90px;">
                                    <span class="tl fl">
                                        <img src="${ctx}/source/images/personalCenter/${list.serviceId }.png" alt="" style="position:relative; top:6px;">
                                    </span>
                                    
                                    <div class="tr fl">
                                        <div class="">
                                         <c:if test="${fn:length(olist.order)>1}">
                                         <h2 style="height: 90px;padding-top: 10px;line-height: 45px;font-size: 14px;color: #343434;text-align: left;*width: 208px;*text-align: left;">
							                 <!-- ${list.name}
							                 	<br>${status.index+1}_${list.id}-->
							                 <c:if test="${list.isAPI==0&&list.serviceId!=6}">
							                 	<a href="${ctx}/selfHelpOrderInit.html?serviceId=${list.serviceId}&indexPage=1" target="_blank">${list.name}<br>${status.index+1}_${list.id}</a>
							                 </c:if>
							                 <c:if test="${list.isAPI==2&&list.serviceId==6}">
							                   <a href="${ctx}/wafDetails.html?serviceId=6" target="_blank">${list.name}<br>${status.index+1}_${list.id}</a>
                                             </c:if>
                                             <c:if test="${list.isAPI==1}">
							                   <a href="${ctx}/selfHelpOrderAPIInit.html?apiId=${list.serviceId}&indexPage=2" target="_blank">${list.name}<br>${status.index+1}_${list.id}</a>
                                             </c:if>
                                         </h2>
                                        </c:if>
                                        <c:if test="${fn:length(olist.order)==1}">
                                          <c:if test="${list.isAPI==0&&list.serviceId!=6}">
                                         <h2>
							                   <a href="${ctx}/selfHelpOrderInit.html?serviceId=${list.serviceId}&indexPage=1" target="_blank">${list.name}</a>
                                         </h2>
                                         </c:if>
                                            <c:if test="${list.isAPI==2&&list.serviceId==6}">
                                         <h2>
							                   <a href="${ctx}/wafDetails.html?serviceId=6" target="_blank">${list.name}</a>
                                         </h2>
                                         </c:if>
                                            <c:if test="${list.isAPI==1}">
                                         <h2>
							                   <a href="${ctx}/selfHelpOrderAPIInit.html?apiId=${list.serviceId}&indexPage=2" target="_blank">${list.name}</a>
                                         </h2>
                                         </c:if>
                                        </c:if>
                                         </div>
                                    	<c:forEach var="asset" items="${list.assetList}" varStatus="status">
                                        	<div class="listDiv"><p style="text-align: left; margin:0; line-height:24px;">资产名称：${asset.name}</p></div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </td>
                            <td class="order" valign="top">
                            	<p class="stylep" style="width:84px;">
                            	<c:if test="${list.isAPI==0}">
									<c:if test="${list.type==1}">长期</c:if>
									<c:if test="${list.type==2}">单次</c:if> 
								</c:if>
								<c:if test="${list.isAPI==1}">
									<!--<c:if test="${list.package_type==1}">套餐1</c:if>
									<c:if test="${list.package_type==2}">套餐2</c:if> 
									<c:if test="${list.package_type==3}">套餐3</c:if>-->
									长期
								</c:if>
								<c:if test="${list.isAPI==2}">
									<c:if test="${list.scan_type==8}">包月</c:if>
									<c:if test="${list.scan_type==9}">包年</c:if> 
								</c:if>
								</p>
                            
                            </td>
                            <td class="order" valign="top">
                            	<c:set var="temp" value="${nowDate }"/>
				                <!-- <c:if test="${list.isAPI==0 && list.status==0}"><p class="stylep" style="width:108px;">已下单<b class="wait"></b></p></c:if>
				                <c:if test="${list.isAPI==0 && (list.status==4||list.status==5)&&list.websoc!=2}"><p class="stylep" style="width:108px;">服务中<b class="ing"></b></p></c:if>
				                <c:if test="${list.isAPI==0 && (list.status==3)&&list.websoc!=2}"><p class="stylep" style="width:108px;">服务中<b class="endend"></b></p></c:if>  -->
				                
				                <c:if test="${list.isAPI==0 && list.begin_date>temp}"><p class="stylep" style="width:108px;">已下单<b class="wait"></b></p></c:if>
				                <c:if test="${list.isAPI==0 && list.begin_date<=temp && list.status!=3 && list.status!=2 && list.status!=1}"><p class="stylep" style="width:108px;">服务中<b class="ing"></b></p></c:if>
				                <c:if test="${list.isAPI==0 && (list.status==3)&&list.websoc!=2}"><p class="stylep" style="width:108px;">服务中<b class="endend"></b></p></c:if>
				                
				                <c:if test="${list.isAPI==0 && (list.status==1)}"><p class="stylep" style="width:108px;">已结束<b class="end"></b></p></c:if>
				                <c:if test="${list.isAPI==0 && (list.status==2)}"><p class="stylep" style="width:108px;">已结束<b class="endend"></b></p></c:if>
				                
				                <!-- <c:if test="${list.isAPI==1 && list.status==0}"><p class="stylep" style="width:108px;">已下单<b class="wait"></b></p></c:if> -->
				                <c:if test="${list.isAPI==1 && list.status==0}"><p class="stylep" style="width:108px;">服务中<b class="ing"></b></p></c:if>
				                <c:if test="${list.isAPI==1 && list.status==1}"><p class="stylep" style="width:108px;">已结束<b class="end"></b></p></c:if>
				                
				                <c:if test="${list.isAPI==2 && (list.status==0)}"><p class="stylep" style="width:108px;">已下单<b class="wait"></b></p></c:if>
				                <c:if test="${list.isAPI==2 && (list.status==5)}"><p class="stylep" style="width:108px;">域名解析未生效</b></p></c:if>
				                <c:if test="${list.isAPI==2 && (list.status==4)}"><p class="stylep" style="width:108px;">服务中<b class="ing"></b></p></c:if>
				                <c:if test="${list.isAPI==2 && (list.status==1)}"><p class="stylep" style="width:108px;">已结束<b class="end"></b></p></c:if>
                            </td>
                            <td class="order" valign="top">
                            	<p style="width:174px; line-height:24px; margin-top:33px;" class="stylep"><fmt:formatDate value="${list.begin_date}" pattern="yyyy-MM-dd HH:mm:ss"/><br><fmt:formatDate value="${list.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                        	
                            </td>
                            <td class="order" valign="top">
                            	 <p style="width:160px; height:auto; line-height:24px; margin-top:33px;" class="stylep">${list.price}</p>
                        		<!-- <p style="width:160px;height:auto;"><em>(使用优惠劵999.00)</em></p> -->
                            </td>
                             <td class="order" valign="top">
                            	<p style="width:98px; line-height:24px;margin-top:33px; height:auto;" class="stylep">
             
						            <c:if test="${list.serviceId==1||list.serviceId==2||list.serviceId==3||list.serviceId==4||list.serviceId==5}">
						            
						            	<c:if test="${list.isAPI==1}"><a href="${ctx}/apiDetails.html?orderId=${list.id }" target="_blank" >查看详情</a></c:if>
						                <c:if test="${list.isAPI!=1 && list.begin_date>temp}"><a href="${ctx}/orderDetails.html?orderId=${list.id }" target="_blank" title="等待">查看详情</a></c:if>
						                
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
						                <c:if test="${list.status==1&&list.isAPI==0}">
						                 <a href="${ctx}/warningInit.html?orderId=${list.id }&type=${list.type}&websoc=${list.websoc}" target="_blank" title="已完成无异常">
						                 	查看详情
						                 </a>
						                </c:if>
						                <c:if test="${list.status==1&&list.isAPI==1}">
						                 <a href="${ctx}/selfHelpOrderAPIInit.html?apiId=${list.serviceId }&indexPage=2">
						                 	查看详情
						                 </a>
						                </c:if>
						                <!-- end -->
						                
						                <!-- 安恒的服务 -->
						                <c:if test="${list.isAPI==0 && list.begin_date<=temp && list.status!=3 && list.status!=2 && list.status!=1}">
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
						            
						            <c:if test="${list.serviceId==6}">
						                 <c:if test="${list.isAPI==2 && (list.status==4)}">
						                 <a href="${ctx}/warningWaf.html?orderId=${list.id }&type=${list.type}" target="_blank" title="">
						                 	查看详情
						                 </a>
						                 </c:if>
						                 <a href="${ctx}/domainNameUI.html?orderId=${list.id }" style="display:block;" title="设置域名解析"> 
						                 <!-- <a href="javascript:void(0)" onclick="domainNameUI(${list.id })" title="设置域名解析">-->
						                 	设置域名解析
						                 </a>
						                 <a href="${ctx}/buyRenewWafUI.html?orderId=${list.id }&orderListId=${olist.orderListId }" style="display:block;" title="续费"> 
						                 续费
						                  </a>
						            </c:if>
						         
                            	</p>
                       			<p style="width:98px; height:auto;">
									<!-- 订单删除操作 -->
             						<a href="javascript:void(0)" onclick="deleteOrder('${list.id}','${list.begin_date}')">删除</a>
								</p>
                            </td>
                        </tr>
                        </c:forEach>
                    
                    </tbody>
                
                </table>
            	
            </div>
        </div>
    </c:forEach>