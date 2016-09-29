 <%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>        	
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<ul class="not-used" style="display:block">
                    		<li class="head">
                            	<span style="padding-left:120px;">资产名称</span>
                                <span style="padding-left:228px;">资产地址</span>
                                <span style="padding-left:178px;">资产状态</span>
                                <span style="padding-left:82px;">操作</span>
                                <!-- <span style="padding-left:178px;">操作</span>-->
                            </li>
                     <c:forEach items="${list}" var="asset"> 
                     <li class="trlist">
                         <table>
                             <tbody>
                                 <tr height="100" valign="middle">
                                 <td><span style=" margin-left:80px; width:144px;">${asset.name}</span></td>
                                 <td> <span style="padding-left:80px; width:260px;">${asset.addr}</span></td>
                                 <!-- modify by tangxr 2016-7-23 放开资产验证 -->
                                 <td>
                                 <c:if test="${asset.status==0}">
				            		<span style="padding-left:84px;width:60px;">
				            		<b>未验证</b>
				            		<a href="#" class="zican_top" style="color:#2499fb;" id="${asset.id}" name="${asset.name}" addr="${asset.addr}" onclick="verificationAssetUI('${asset.id},${asset.name},${asset.addr}')">立即验证</a>
				            		</span>
				             	 </c:if>
				             	 <c:if test="${asset.status==1}">
				            		<span style="padding-left:84px;width:60px;">
                                                	<b>已验证</b>
                                                    
                                     </span>
				                 </c:if>
                                 </td> 
                                 <!-- end -->
                                 <td> 
                                     <span style="padding-left:80px; width:32px;">
                                         <a href="#" class="zc_edit" style="color:#2499fb;" id="${asset.id}" name="${asset.name}" addr="${asset.addr}" districtId="${asset.districtId}" city="${asset.city}" purpose="${asset.purpose}" onclick="editAssetUI('${asset.id},${asset.name},${asset.addr},${asset.districtId},${asset.city},${asset.purpose},${asset.type}')">修改</a>
                                         <a href="#" style="color:#2499fb;" onclick="deleteAsset('${asset.id}')">删除</a>
                                     </span>
                                 </td>
                                 
               					 </tr>
                             </tbody>
                         </table>  
                     </li> 
                     </c:forEach>                 
                    	</ul>
                        <ul class="not-used">
                    		<li class="head">
                            	<span style="padding-left:120px;">资产名称</span>
                                <span style="padding-left:228px;">资产地址</span>
                                <span style="padding-left:178px;">资产状态</span>
                                <span style="padding-left:82px;">操作</span>
                                <!-- <span style="padding-left:178px;">操作</span>-->
                            </li>
 						<c:forEach items="${list}" var="asset"> 
 						<c:if test="${asset.status==1}">
                            <li class="trlist">
                         <table>
                             <tbody>
                                 <tr height="100" valign="middle">
                                 <td><span style=" margin-left:80px; width:144px;">${asset.name}</span></td>
                                 <td> <span style="padding-left:80px; width:260px;">${asset.addr}</span></td>
                                 <!-- modify by tangxr 2016-7-23 放开资产验证 -->
                                 <td>
				            		<span style="padding-left:84px;width:60px;">
                                                	<b>已验证</b>
                                                    
                                     </span>
                                 </td>
                                 <!-- end -->
                                 <td> 
                                     <span style="padding-left:80px; width:32px;">
                                         <a href="#" class="zc_edit" style="color:#2499fb;" id="${asset.id}" name="${asset.name}" addr="${asset.addr}" districtId="${asset.districtId}" city="${asset.city}" purpose="${asset.purpose}" onclick="editAssetUI('${asset.id},${asset.name},${asset.addr},${asset.districtId},${asset.city},${asset.purpose},${asset.type}')">修改</a>
                                         <a href="#" style="color:#2499fb;" onclick="deleteAsset('${asset.id}')">删除</a>
                                     </span>
                                 </td>
                                 
               					 </tr>
                             </tbody>
                         </table>  
                     </li>
                      </c:if> 
                     </c:forEach> 
                        
                    
                    	</ul>
                        <ul class="not-used">
                    		<li class="head">
                            	<span style="padding-left:120px;">资产名称</span>
                                <span style="padding-left:228px;">资产地址</span>
                                <span style="padding-left:178px;">资产状态</span>
                                <span style="padding-left:82px;">操作</span>
                                <!-- <span style="padding-left:178px;">操作</span> -->
                            </li>
                           <c:forEach items="${list}" var="asset"> 
                           <c:if test="${asset.status==0}">
                           <li class="trlist">
                         <table>
                             <tbody>
                                 <tr height="100" valign="middle">
                                 <td><span style=" margin-left:80px; width:144px;">${asset.name}</span></td>
                                 <td> <span style="padding-left:80px; width:260px;">${asset.addr}</span></td>
                                 <!-- modify by tangxr 2016-7-23 放开资产验证 -->
                                 <td>

				            		<span style="padding-left:84px;width:60px;">
				            		<b>未验证</b>
				            		<a href="#" class="zican_top" style="color:#2499fb;" id="${asset.id}" name="${asset.name}" addr="${asset.addr}" onclick="verificationAssetUI('${asset.id},${asset.name},${asset.addr}')">立即验证</a>
				            		</span>
				             	
				             	 
                                 </td>
                                 <!-- end -->
                                 <td> 
                                     <span style="padding-left:80px; width:32px;">
                                         <a href="#" class="zc_edit" style="color:#2499fb;" id="${asset.id}" name="${asset.name}" addr="${asset.addr}" districtId="${asset.districtId}" city="${asset.city}" purpose="${asset.purpose}" onclick="editAssetUI('${asset.id},${asset.name},${asset.addr},${asset.districtId},${asset.city},${asset.purpose},${asset.type}')">修改</a>
                                         <a href="#" style="color:#2499fb;" onclick="deleteAsset('${asset.id}')">删除</a>
                                     </span>
                                 </td>
                                 
               					 </tr>
                             </tbody>
                         </table>  
                     </li> 
                      </c:if>
                     </c:forEach> 
                    
                    	</ul>