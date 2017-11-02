<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>商品详情配置</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
<style>
.user_table thead tr th:nth-child(1) {padding-right: 66px;padding-left: 25px;}
.user_table thead tr th:nth-child(2) {padding-right: 132px;}
.user_table thead tr th:nth-child(3) {padding-right: 173px;}
.user_table thead tr th:nth-child(4) {padding-right: 236px;}
.user_table tbody tr td:nth-child(1) {width: 115px;padding-left: 25px;}
.user_table tbody tr td:nth-child(2) {width: 180px;}
.user_table tbody tr td:nth-child(3) {}
.user_table tbody tr td:nth-child(4) {width: 250px;}
.user_table tbody tr td:last-child a {display: inline-block;width: auto;padding-left: 6px;}
</style>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/jquery-ui-1.8.10.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.form.js"></script>
<!-- <script type="text/javascript">
  $axure.utils.getTransparentGifPath = function() { return 'resources/images/transparent.gif'; };
  $axure.utils.getOtherPath = function() { return 'resources/Other.html'; };
  $axure.utils.getReloadPath = function() { return 'resources/reload.html'; };
</script> -->
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/price/price.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/servManage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/servicebox.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/pricebox.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/apiPriceBox.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<style type="text/css">
.uploader {
position: relative;
}
.filename {
float: left;
width: 180px;
/*overflow: hidden;
padding: 8px 10px;*/
/*border: 1px solid;*/
/*border-color: #ccc;*/
color: #777;

}

.filebutton {
float: left;
height: 38px;
padding: 8px 12px;
border: 1px solid;
border-color: #ccc;
color: #555;
}

input[type=file] {
position: absolute;
border: 0;
left: 0;
width:260px;
height: 38px;
filter: alpha(opacity = 0);
-moz-opacity: 0;
-khtml-opacity: 0;
opacity: 0;
}

#select_type{
	background: rgba(0, 0, 0, 0) url("${ctx}/source/images/b_safes_icon.jpg") no-repeat scroll 0 0;
    float: right;
    height: 36px;
    line-height: 36px;
    margin-left: 15px;
    padding-left: 10px;
    width: 178px;
    border:none; 
}

</style>
<script type="text/javascript">
$(document).ready(function(){
    //回显
	$("#u43_input").val("${type}");
    $("#u2_input").val("${name}");
    $("#u5_input").val("${parent}");
});
</script>
</head>
<body>
	<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
    <div id="base" class="main_wrap">
    <div class="main_center">
		<a href="${ctx}/addServUI.html" class="serviceBtn new">添加服务</a>
		<a href="javascript:;" class="serviceBtn new" id="add_ser">添加服务</a>
      	<form  class="clearfix analysecent" style="padding-top:16px;" action="${ctx}/searchService.html" method="post" id="searchForm">
	      	
	        <div class="analyse_lable fl" style="float: right;">
	            <label>服务类型</label>
	            <select class="text" id="u43_input" name="servType">
	                <option value="">请选择</option>
	               	<option value="1">网站安全监测及预警服务</option>
	          		<option value="2">网站安全防护及加固服务</option>
	          	</select>
	        </div>
	        <div class="analyse_lable fl" style="float: right;">
	            <label>一级分类</label>
	            <select class="text" id="u5_input" onchange="changeParentForSearch();" name="parentC">
	                <option value="">请选择</option>
	               	<option value="1">网站安全帮</option>
	          		<!--  <option value="2">数据库安全帮</option>
	          		<option value="3">系统安全帮</option>
	          		<option value="4">网络安全帮</option>
	         		<option value="5">移动安全帮</option>-->
	          		<option value="6">安全能力API</option>
	          	</select>
	        </div>
	        <div class="analyse_lable fl" style="float: right;">
	            <label>服务名称</label>
	            <input type="text" class="text" id="u2_input" name="servName">
	        </div>
        	<input type="button" class="sub" value="" style="right:-130px;" onclick="searchService();">
      	</form>
      	
            <!-- Unnamed (Table) -->
        <div class="system_table" style="display:block;margin-top: 20px;">
        	<table class="user_table" cellpadding="0" cellspacing="0">
            	<thead>
                	<tr>
                    	<th>一级分类</th>
                        <th>服务类型</th>
                        <th>服务名称</th>
                        <th>服务描述</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="servList">
	                <c:forEach items="${servList}" var="serv">
	                    <tr>
	                    	<td>${serv.parentCName}</td>
	                        <td>${serv.typeName}</td>
	                        <td>
	                        <a href="${ctx}/serviceDetailsUI.html?servId=${serv.id}&parent=${serv.parentC}" style="width:240px;padding-left: 26px;">
	                        	<c:if test="${fn:length(serv.name)<=13}">
		                                    ${serv.name}
		                        </c:if>
		                        <c:if test="${fn:length(serv.name)>13}">
		                                ${fn:substring(serv.name, 0, 13)}...
		                        </c:if>
	                        </a>
	                        </td>
	                        <td>
		                        <c:if test="${fn:length(serv.remarks)<=13}">
		                                    ${serv.remarks}
		                        </c:if>
		                        <c:if test="${fn:length(serv.remarks)>13}">
		                                ${fn:substring(serv.remarks, 0, 13)}...
		                        </c:if>
	                        </td>
	                        <td>
	                        	<a href="javascript:void(0);" class="ope_a edit_service" servId="${serv.id}" remarks="${serv.remarks}" parent="${serv.parentC}" homeIcon="${serv.homeIcon}" categoryIcon="${serv.categoryIcon }" detailIcon="${serv.detailIcon }" servName="${serv.name}" type="${serv.servType}">编辑</a>
	                        	<!-- 
	                        	<a href="${ctx }/updateServUI.html?servId=${serv.id}&parent=${serv.parentC}&servName=${serv.name}&icon=${serv.icon}&remarks=${serv.remarks}&type=${serv.servType}" class="ope_a add_change">编辑</a>
	                        	 -->
	                        	<a href="javascript:void(0)" servid="${serv.id}" parentC="${serv.parentC}" onclick="delServ(this)" class="ope_a">删除</a>
	                        	<!-- <a href="${ctx }/addServicePriceUI.html?servId=${serv.id}&parent=${serv.parentC}" class="ope_a">设置价格</a> -->
	                        	<c:if test="${serv.parentC==6}">
		                        	<a href="javascript:void(0)" class="ope_a" onclick="editApiPrice(${serv.id })">设置价格</a>
	                        	</c:if>
	                        	<c:if test="${serv.parentC!=6 && serv.id != 6}">
	                        		<a href="${ctx}/serviceDetailsUI.html?servId=${serv.id}&parent=${serv.parentC}" class="ope_a">详情维护</a>
		                        	<a href="javascript:void(0)" class="ope_a" onclick="editPrice(${serv.id },${serv.parentC})">设置价格</a>
	                        	</c:if>
	                        </td>
                   		</tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
     </div>
</div>
<!--============bottom============-->
	<!-- footer start -->
	<c:import url="/footer.html"></c:import>
	<!-- footer end -->
</div>
<!--模态框-->
<div class="modelbox" id="modelbox"></div>
<!-- 添加服务 -->
<div id="box_addService" class="box_logoIn user_model">
	<div class="add_ser_top w678">
		<p class="w634">添加服务</p><p id="close" class="modelclose" onclick="toClose()"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
	</div>
	<div class="regist_form" style="height:70%;overflow-y:auto">
	<!--  <form id="form_addService" name="form_regist" method="post" action="${ctx}/servicePriceMaintain.html">-->
		<table>
            <tr class="register_tr">
	            <td class="regist_title">一级分类</td>
	            <td class="regist_input">
		            <select class="regist_sel" id="serv_parent"  onchange="changeParent();">
                		<!-- <option selected="selected" value="-1">请选择</option> -->
                		<option value="1">网站安全帮</option>
			          	<!-- <option value="2">数据库安全帮</option>
			          	<option value="3">系统安全帮</option>
			          	<option value="4">网络安全帮</option>
			          	<option value="5">移动安全帮</option> -->
			          	<option value="6">安全能力API</option>
                	</select>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务类型</td>
	            <td class="regist_input">
		            <select class="regist_sel" id="serv_type" disabled>
                		<!-- <option selected="selected" value="-1">请选择</option> -->
                		<option value="1">网站安全监测及预警服务</option>
          				<!-- <option value="2">网站安全防护及加固服务</option> -->
                	</select>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务名称</td>
	            <td class="regist_input">
		            <input type="text" class="regist_txt required" id="serv_name" maxlength="20"/>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务描述</td>
	            <td class="regist_input">
		            <textarea id="serv_remarks" class="regist_txt" style="height:130px;"></textarea>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
	         <tr class="register_tr">
	            <td class="regist_title">首页服务图标</td>
	            <td class="regist_input" style="width:400px;">
	            	<form enctype='multipart/form-data' method="post" name="form1" id="homeIconForm" > 
	            	<div  class="uploader">
			            <input type="text" class="regist_txt filename" readonly="readonly" id="homeIconName">
			            <input type="button" class="filebutton" value="浏览.."/>
			            <input type="file" id="u8_input" name="file" class="homeIconPath"/>
		            </div>
		            <input id="u9_input" type="button"  class="filebutton" value="上传" onclick="saveHomeIcon();" style="margin-left:10px;"/>
		            <input type="hidden" id="homeIconPathHidden" value=""/>
		            </form>
		            <span id="regist_image_msg" style="color:red;float:left"></span>
		        </td>
		        <td class="regist_prompt" style="text-align:left;">建议图片大小 130*130</td>
	         </tr>
	         <tr class="register_tr">
	            <td class="regist_title">二级服务图标</td>
	            <td class="regist_input" style="width:400px;">
	            	<form enctype='multipart/form-data' method="post" name="form1" id="categoryIconForm" > 
	            	<div  class="uploader">
			            <input type="text" class="regist_txt filename" readonly="readonly" id="categoryIconName">
			            <input type="button" class="filebutton" value="浏览.."/>
			            <input type="file" id="u8_input" name="file" class="categoryIconPath"/>
		            </div>
		            <input id="u9_input" type="button"  class="filebutton" value="上传" onclick="saveCategoryIcon();" style="margin-left:10px;"/>
		            <input type="hidden" id="categoryIconPathHidden" value=""/>
		            </form>
		            <span id="regist_image_msg" style="color:red;float:left"></span>
		        </td>
		        <td class="regist_prompt" style="text-align:left;">建议图片大小 220*220</td>
	         </tr>
	         <tr class="register_tr">
	            <td class="regist_title">详情服务图标</td>
	            <td class="regist_input" style="width:400px;">
	            	<form enctype='multipart/form-data' method="post" name="form1" id="detailIconForm" > 
	            	<div  class="uploader">
			            <input type="text" class="regist_txt filename" readonly="readonly" id="detailIconName">
			            <input type="button" class="filebutton" value="浏览.."/>
			            <input type="file" id="u8_input" name="file" class="detailIconPath"/>
		            </div>
		            <input id="u9_input" type="button"  class="filebutton" value="上传" onclick="saveDetailIcon();" style="margin-left:10px;"/>
		            <input type="hidden" id="detailIconPathHidden" value=""/>
		            </form>
		            <span id="regist_image_msg" style="color:red;float:left"></span>
		        </td>
		        <td class="regist_prompt" style="text-align:left;">建议图片大小 420*420</td>
	         </tr>
          
         </table>
	<!-- </form> -->
	</div>
	<input type="button" class="ser_btn" onclick="saveServ();" value="保存"/>
	<input type="button" class="ser_btn" id="addUI_reset" type="submit" value="重置"/>
</div>
<!-- 修改服务 -->
<div id="box_editService" class="box_logoIn user_model">
	<div class="add_ser_top w678">
		<p class="w634">服务维护</p><p id="close2" class="modelclose"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
	</div>
	<div class="regist_form" style="height:70%;overflow-y:auto">
	<!--  <form id="form_addService" name="form_regist" method="post" action="${ctx}/servicePriceMaintain.html">-->
		<table>
            <tr class="register_tr">
	            <td class="regist_title">一级分类</td>
	            <td class="regist_input">
	            	<input type="hidden" id="edit_serv_id" value=""/>
		            <select class="regist_sel" id="edit_serv_parent"  onchange="changeEditParent();">
                		<!-- <option selected="selected" value="-1">请选择</option> -->
                		<option value="1">网站安全帮</option>
			          	<!-- <option value="2">数据库安全帮</option>
			          	<option value="3">系统安全帮</option>
			          	<option value="4">网络安全帮</option>
			          	<option value="5">移动安全帮</option> -->
			          	<option value="6">安全能力API</option>
                	</select>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务类型</td>
	            <td class="regist_input">
		            <select class="regist_sel" id="edit_serv_type" disabled>
                		<option selected="selected" value="-1">请选择</option>
                		<option value="1">网站安全监测及预警服务</option>
          				<option value="2">网站安全防护及加固服务</option>
                	</select>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务名称</td>
	            <td class="regist_input">
		            <input type="text" class="regist_txt required" id="edit_serv_name" maxlength="20"/>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务描述</td>
	            <td class="regist_input">
		            <textarea id="edit_serv_remarks" class="regist_txt" style="height:130px;"></textarea>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">首页服务图标</td>
	            <td class="regist_input" style="width:400px;">
	            	<form enctype='multipart/form-data' method="post" name="form1" id="homeIconForm1" > 
	            	<div  class="uploader">
			            <input type="text" class="regist_txt filename" readonly="readonly" id="editHomeIconName">
			            <input type="button" class="filebutton" value="浏览.."/>
			            <input type="file" class="file homeIconPath" name="file" />
		            </div>
		            <input id="u9_input" type="button"  class="filebutton" value="上传" onclick="editHomeIcon();" style="margin-left:10px;"/>
		            <input type="hidden" id="edit_homeIconPathHidden" value=""/>
		            </form>
		            <span id="regist_image_msg" style="color:red;float:left"></span>
		        </td>
		        <td class="regist_prompt" style="text-align:left;">建议图片大小 130*130</td>
	         </tr>
	         <tr class="register_tr">
	            <td class="regist_title">二级服务图标</td>
	            <td class="regist_input" style="width:400px;">
	            	<form enctype='multipart/form-data' method="post" name="form1" id="categoryIconForm1" > 
	            	<div  class="uploader">
			            <input type="text" class="regist_txt filename" readonly="readonly" id="editCategoryIconName">
			            <input type="button" class="filebutton" value="浏览.."/>
			            <input type="file" class="file categoryIconPath" name="file" />
		            </div>
		            <input id="u9_input" type="button"  class="filebutton" value="上传" onclick="editCategoryIcon();" style="margin-left:10px;"/>
		            <input type="hidden" id="edit_categoryIconPathHidden" value=""/>
		            </form>
		            <span id="regist_image_msg" style="color:red;float:left"></span>
		        </td>
		        <td class="regist_prompt" style="text-align:left;">建议图片大小 220*220</td>
	         </tr>
	         <tr class="register_tr">
	            <td class="regist_title">详情服务图标</td>
	            <td class="regist_input" style="width:400px;">
	            	<form enctype='multipart/form-data' method="post" name="form1" id="detailIconForm1" > 
	            	<div  class="uploader">
			            <input type="text" class="regist_txt filename" readonly="readonly" id="editDetailIconName">
			            <input type="button" class="filebutton" value="浏览.."/>
			            <input type="file" class="file detailIconPath" name="file" />
		            </div>
		            <input id="u9_input" type="button"  class="filebutton" value="上传" onclick="editDetailIcon();" style="margin-left:10px;"/>
		            <input type="hidden" id="edit_detailIconPathHidden" value=""/>
		            </form>
		            <span id="regist_image_msg" style="color:red;float:left"></span>
		        </td>
		        <td class="regist_prompt" style="text-align:left;">建议图片大小 420*420</td>
	         </tr>
          
         </table>
	<!-- </form> -->
	</div>
	<input type="button" class="ser_btn" onclick="updateServ();" value="保存"/>
	<input type="button" class="ser_btn" id="editUI_reset" type="submit" value="重置"/>
</div>
<div id="box_servicePrice" class="box_logoIn user_model">
	<div class="add_ser_top w678">
		<p class="w634">价格维护</p><p id="close" class="modelclose" onclick="toClose()"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
	</div> 
    <div class="regist_form" style="height:70%;overflow-y:auto">
    	<form id="form_price" name="form_regist" method="post" action="${ctx}/servicePriceMaintain.html">
        	<input type="hidden" name="maxPriceIndex" id="maxPriceIndex"/>
        	<input type="hidden" name="add_serviceId" id="add_serviceId"/>
        	<div id="allPriceDiv">
				<!-- 动态 显示当前价格 -->
        	
        	</div>
      	</form>
      	
      	<div class="price_template" style="display:none">
	      	<div id="singlePrice_template">
		        <table>
		          <tr class="price_tr" id="singlePrice_tr">
		            <td class="regist_title">单次价格</td>
		            <td class="price_input"><input type="text" class="price_txt required" name="singlePrice_template" id="singlePrice_template"/><span id="regist_name_msg" style="color:red;float:left"></span></td>
		            <td class="regist_prompt"></td>
		          </tr>
		         </table>
	        </div>
	        <div id="longPrice_template">
		        <table >
		          <tr class="price_tr">
		            <td class="regist_title">长期价格</td>
		            <td class="price_input_radio">
		            	<input type="radio" name="" value="0" checked="true" />不根据频率设置价格
		            	<input type="radio" name="" value="1" />根据频率设置价格
		            </td>
		          </tr>
		        </table>
	        	<div id="scanType_id_template">
	        		<div class="scanType_div_template">
		        		服务频率: XXXX
		        		<input style="margin-left:20px" type="button" value="添加行" onclick="addOnePrice(0)"/>
		        		<!--  <div style="float: right;"><input type="button" value="添加行" onclick="addOnePrice(0)"/></div>-->
	        		</div>
		        	<table style="margin-left: 70px;" id="scanTypeTable_template">
				        <tr class="price_tr" id="price_index_template">
				        	<td  class="price_input">
				        		<select class="price_sel" id="type_0" name="type_0" onchange="getPriceType(this.value,0)">
				         			<option selected="selected" value="1" >区间</option>
				         			<option value="2" >大于</option>
				                </select>
				        	</td>
				        	<td class="price_title">大于</td>
				        	<td class="price_input">
					        	<input type="text"  class="price_txt" name="timesG_0" id="timesG_0" onblur="checkTimesG(0)"/>
				        	</td>
				        	<td class="price_title"><p id="title_timesLE_0">小于等于</p></td>
				        	<td class="price_input">
					        	<input type="text"  class="price_txt" name="timesLE_0" id="timesLE_0" onblur="checkTimesLE(0)"/>
				        	</td>
				        	<td class="price_title">价格</td>
				        	<td class="price_input">
					        	<input type="text"  class="price_txt" name="price_0" id="price_0" onblur="checkPrice(this)"/>
					        	<input type="hidden" name="price_scanType_0" id="price_scanType_0"/>
				        	</td>
				        	<td>
				        		<input type="button" value="删除行" onclick="deleteOnePrice(0)"/>
				        	</td>
				        </tr>
			        </table>
		        </div>
	        </div>
      	</div>
      	
    </div>
    <input type="button" class="ser_btn" onclick="saveServicePrice()" value="保存"/>
</div>
<div id="box_apiPrice" class="box_logoIn user_model">
	<div class="add_ser_top w678">
		<p class="w634">价格维护</p><p id="close" class="modelclose" onclick="toClose()"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
	</div> 
    <div class="regist_form" style="height:70%;overflow-y:auto">
    	<form id="form_api_price" name="form_regist" method="post" action="${ctx}/serviceApiPriceMaintain.html">
        	<input type="hidden" name="maxApiPriceIndex" id="maxApiPriceIndex"/>
        	<input type="hidden" name="add_serviceApiId" id="add_serviceApiId"/>
        	<div  id="allApiServicePriceDiv">
        		<div style="text-align: left;">
        			<input type="button" value="添加行" onclick="addOneApiPrice()"/>
        		</div>
				<table id="apiPrice_table">
				     <!-- 动态 显示当前价格 -->
			    </table>
        	
        	</div>
      	</form>
      	<div class="apiPrice_template" style="display:none">
      			<table id="apiPrice_table_template">
				        <tr class="price_tr" id="apiPrice_index_0">
				        	<td  class="price_input">
				        		<select class="price_sel" id="apiPrice_type_0" name="apiPrice_type_0" onchange="getApiPriceType(this.value,0)">
				         			<option selected="selected" value="1" >区间</option>
				         			<option value="2" >大于</option>
				                </select>
				        	</td>
				        	<td class="price_title">大于</td>
				        	<td class="price_input">
					        	<input type="text"  class="price_txt" name="apiPrice_timesG_0" id="apiPrice_timesG_0" onblur="checkApiTimesG(0)"/>
				        	</td>
				        	<td class="price_title"><p id="apiPrice_title_timesLE_0">小于等于</p></td>
				        	<td class="price_input">
					        	<input type="text"  class="price_txt" name="apiPrice_timesLE_0" id="apiPrice_timesLE_0" onblur="checkApiTimesLE(0)"/>
				        	</td>
				        	<td class="price_title">价格</td>
				        	<td class="price_input">
					        	<input type="text"  class="price_txt" name="apiPrice_price_0" id="apiPrice_price_0" onblur="checkApiPrice(this)"/>
				        	</td>
				        	<td>
				        		<input type="button" value="删除行" onclick="deleteOneApiPrice(0)"/>
				        	</td>
				        </tr>
			        </table>
      	</div>
    </div>
    <input type="button" class="ser_btn" onclick="saveServiceApiPrice()" value="保存"/>
</div>
  </body>
</html>
