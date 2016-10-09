<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>服务维护列表页面</title>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/jquery-ui-1.8.10.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.form.js"></script>
<!-- <script type="text/javascript">
  $axure.utils.getTransparentGifPath = function() { return 'resources/images/transparent.gif'; };
  $axure.utils.getOtherPath = function() { return 'resources/Other.html'; };
  $axure.utils.getReloadPath = function() { return 'resources/reload.html'; };
</script> -->
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
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
</head>
<body>
	<!--头部代码-->
	<div class="head_bj b_head">
	  <div class="head">
	    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
	    <div class="list b_list">
	      <ul>
	      	<li><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
			<li><a href="${ctx}/adminchinas.html" target="_blank" class="white">安全态势</a></li>
	        <li class="b_current"><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
	        <li><a href="${ctx}/adminDataAssetUI.html" class="white">资产分析</a></li>
	        <li><a href="${ctx}/adminUserAnalysisUI.html" class="white">用户分析</a></li>
	        <!-- <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">订单分析</a></li>-->
	        <li><a href="${ctx}/orderformanalyse.html" class="white">订单分析</a></li>
	        <li><a href="${ctx}/adminWarnAnalysisUI.html" class="white">告警分析</a></li>
	        <li><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
	        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
	        <li><a href="${ctx}/adminAPIAnalysisUI.html" class="white">API分析</a></li>
	        <li style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
	      </ul>
	    </div>
	    <div class="lagst">
	      <div class="lagst-left b_lagst_left"> <a href="#"><img src="${ctx}/source/adminImages/b_photo.jpg" width="43" height="42"></a> </div>
	      <div class="lagst-right">
	        <p ><a href="###" class="white">${sessionScope.admin_user.name }</a></p>
	        <p> <a href="${ctx}/adminExit.html" class="white">退出</a></p>
	      </div>
	    </div>
	  </div>
	</div>
	<!--头部代码结束-->
    <div id="base" class="main_wrap">
    <div class="main_center">
		<a href="${ctx}/addServUI.html" class="serviceBtn new">添加服务</a>
		<a href="javascript:;" class="serviceBtn new" id="add_ser">添加服务</a>
      	<form  class="clearfix analysecent" style="padding-top:16px;">
	      	
	        <div class="analyse_lable fl" style="float: right;">
	            <label>服务类型</label>
	            <select class="text" id="u43_input">
	                <option value="">请选择</option>
	               	<option value="1">网站安全监测及预警服务</option>
	          		<option value="2">网站安全防护及加固服务</option>
	          	</select>
	        </div>
	        <div class="analyse_lable fl" style="float: right;">
	            <label>一级分类</label>
	            <select class="text" id="u5_input" onchange="changeParentForSearch();">
	                <option value="">请选择</option>
	               	<option value="1">网站安全帮</option>
	          		<option value="2">数据库安全帮</option>
	          		<option value="3">系统安全帮</option>
	          		<option value="4">网络安全帮</option>
	         		<option value="5">移动安全帮</option>
	          		<option value="6">API</option>
	          	</select>
	        </div>
	        <div class="analyse_lable fl" style="float: right;">
	            <label>服务名称</label>
	            <input type="text" class="text" id="u2_input">
	        </div>
        	<input type="button" class="sub" value="" style="right:-130px;" onclick="searchServ();">
      	</form>
      	
            <!-- Unnamed (Table) -->
        <div class="system_table" style="display:block;margin-top: 20px;">
        	<table class="user_table" cellpadding="0" cellspacing="0">
            	<thead>
                	<tr>
                    	<th class="t_username" style="text-align:center">一级分类</th>
                        <th class="t_date" style="text-align:center">服务类型</th>
                        <th class="t_role" style="text-align:center">服务名称</th>
                        <th class="t_assets" style="text-align:center;width:320px">服务描述</th>
                        <th class="t_operation" style="text-align:center;width:300px">操作</th>
                    </tr>
                </thead>
                <tbody id="servList">
	                <c:forEach items="${servList}" var="serv">
	                    <tr>
	                    	<td class="t_username">${serv.parentCName}</td>
	                        <td class="t_date">${serv.typeName}</td>
	                        <td class="t_assets"><a href="${ctx}/serviceDetailsUI.html?servId=${serv.id}&parent=${serv.parentC}">${serv.name}</a></td>
	                        <td class="t_service" style="text-align:center;width:320px">${serv.remarks}</td>
	                        <td class="t_operation">
	                        	<a href="javascript:void(0);" class="ope_a add_change edit_service" servId="${serv.id}" remarks="${serv.remarks}" parent="${serv.parentC}" icon="${serv.icon}" servName="${serv.name}" type="${serv.servType}">编辑</a>
	                        	<!-- 
	                        	<a href="${ctx }/updateServUI.html?servId=${serv.id}&parent=${serv.parentC}&servName=${serv.name}&icon=${serv.icon}&remarks=${serv.remarks}&type=${serv.servType}" class="ope_a add_change">编辑</a>
	                        	 -->
	                        	<a href="javascript:void(0)" servid="${serv.id}" parentC="${serv.parentCName}" onclick="delServ(this)" class="ope_a ml20">删除</a>
	                        	<!-- <a href="${ctx }/addServicePriceUI.html?servId=${serv.id}&parent=${serv.parentC}" class="ope_a add_change">设置价格</a> -->
	                        	<c:if test="${serv.parentCName=='API'}">
		                        	<a href="javascript:void(0)" class="ope_a add_change" onclick="editApiPrice(${serv.id })">设置价格</a>
	                        	</c:if>
	                        	<c:if test="${serv.parentCName!='API'}">
		                        	<a href="javascript:void(0)" class="ope_a add_change" onclick="editPrice(${serv.id })">设置价格</a>
	                        	</c:if>
	                        	<a href="${ctx}/serviceDetailsUI.html?servId=${serv.id}&parent=${serv.parentCName}" class="ope_a add_change">详情维护</a>
	                        </td>
                   		</tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
     </div>
</div>
	<!--尾部部分代码-->
	<div class="bottom_bj">
	<div class="bottom">
	<div class="bottom_main">
	  <h3><a href="###">新手入门</a></h3>
	  <ul>
	    <li><a href="${ctx}/registUI.html">新用户注册</a></li>
	    <li><a href="${ctx}/loginUI.html">用户登录</a></li>
	    <li><a href="###">找回密码</a></li>
	  </ul>
	</div>
	<div  class="bottom_main">
	   <h3><a href="###"> 帮助</a></h3>
	  <ul>
	    <li><a href="${ctx}/aider.html">常见问题</a></li>
	  </ul>
	</div>
	<div  class="bottom_main">
	  <h3><a href="###">厂商合作</a></h3>
	  <ul>
	    <li><a href="###">华为</a></li>
	    <li><a href="###">安恒</a></li>
	    <li><a href="###">知道创宇</a></li>
	  </ul>
	</div>
	<div  class="bottom_main">
	<h3><a href="###">联系我们</a></h3>
	<ul>
	<li><a href="###">客户电话</a></li>
	</div>
	<div  class="bottom_main" style="width:380px;">
	<h3><a href="###">版权信息</a></h3>
	 <ul>
	 <li>Copyright&nbsp;©&nbsp;2015 中国电信股份有限公司北京研究院<br />
	京ICP备12019458号－10</li>
	</div>
	</div>
	</div>
	<!--尾部部分代码结束-->
<!--模态框-->
<div class="modelbox" id="modelbox"></div>
<!-- 添加服务 -->
<div id="box_addService" class="box_logoIn user_model">
	<div class="add_ser_top w678">
		<p class="w634">服务维护</p><p id="close" class="modelclose" onclick="toClose()"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
	</div>
	<div class="regist_form" style="height:70%;overflow-y:auto">
	<!--  <form id="form_addService" name="form_regist" method="post" action="${ctx}/servicePriceMaintain.html">-->
		<table>
            <tr class="register_tr">
	            <td class="regist_title">一级分类</td>
	            <td class="regist_input">
		            <select class="regist_sel" id="serv_parent"  onchange="changeParent();">
                		<option selected="selected" value="-1">请选择</option>
                		<option value="1">网站安全帮</option>
			          	<option value="2">数据库安全帮</option>
			          	<option value="3">系统安全帮</option>
			          	<option value="4">网络安全帮</option>
			          	<option value="5">移动安全帮</option>
			          	<option value="6">API</option>
                	</select>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务类型</td>
	            <td class="regist_input">
		            <select class="regist_sel" id="serv_type">
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
		            <input type="text" class="regist_txt required" id="serv_name"/>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务描述</td>
	            <td class="regist_input">
		            <textarea id="serv_remarks" class="regist_txt" style="height:160px;"></textarea>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务图标</td>
	            <td class="regist_input" style="width:400px;">
	            	<form enctype='multipart/form-data' method="post" name="form1" id="form1" > 
	            	<div  class="uploader">
			            <input type="text" class="regist_txt filename" readonly="readonly">
			            <input type="button" class="filebutton" value="浏览.."/>
			            <input type="file" id="u8_input" name="file" />
		            </div>
		            <input id="u9_input" type="button"  class="filebutton" value="上传" onclick="uploadIcon();" style="margin-left:10px;"/>
		            <input type="hidden" id="filePathHidden" value=""/>
		            </form>
		            <span id="regist_image_msg" style="color:red;float:left"></span>
		        </td>
		        <td class="regist_prompt" style="text-align:left;">请上传.jpg,.bmp或.png格式的文件</td>
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
                		<option selected="selected" value="-1">请选择</option>
                		<option value="1">网站安全帮</option>
			          	<option value="2">数据库安全帮</option>
			          	<option value="3">系统安全帮</option>
			          	<option value="4">网络安全帮</option>
			          	<option value="5">移动安全帮</option>
			          	<option value="6">API</option>
                	</select>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务类型</td>
	            <td class="regist_input">
		            <select class="regist_sel" id="edit_serv_type">
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
		            <input type="text" class="regist_txt required" id="edit_serv_name"/>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务描述</td>
	            <td class="regist_input">
		            <textarea id="edit_serv_remarks" class="regist_txt" style="height:160px;"></textarea>
		            <span id="regist_name_msg" style="color:red;float:left"></span>
	            </td>
	            <td class="regist_prompt"></td>
          	</tr>
          	<tr class="register_tr">
	            <td class="regist_title">服务图标</td>
	            <td class="regist_input" style="width:400px;">
	            	<form enctype='multipart/form-data' method="post" name="form1" id="form1" > 
	            	<div  class="uploader">
			            <input type="text" class="regist_txt filename" readonly="readonly">
			            <input type="button" class="filebutton" value="浏览.."/>
			            <input type="file" class="file" name="file" />
		            </div>
		            <input id="u9_input" type="button"  class="filebutton" value="上传" onclick="uploadIcon();" style="margin-left:10px;"/>
		            <input type="hidden" id="edit_filePathHidden" value=""/>
		            </form>
		            <span id="regist_image_msg" style="color:red;float:left"></span>
		        </td>
		        <td class="regist_prompt" style="text-align:left;">请上传.jpg,.bmp或.png格式的文件</td>
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
					        	<input type="text"  class="price_txt" name="timesG_0" id="timesG_0"/>
				        	</td>
				        	<td class="price_title"><p id="title_timesLE_0">小于等于</p></td>
				        	<td class="price_input">
					        	<input type="text"  class="price_txt" name="timesLE_0" id="timesLE_0"/>
				        	</td>
				        	<td class="price_title">价格</td>
				        	<td class="price_input">
					        	<input type="text"  class="price_txt" name="price_0" id="price_0"/>
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
					        	<input type="text"  class="price_txt" name="apiPrice_timesG_0" id="apiPrice_timesG_0"/>
				        	</td>
				        	<td class="price_title"><p id="apiPrice_title_timesLE_0">小于等于</p></td>
				        	<td class="price_input">
					        	<input type="text"  class="price_txt" name="apiPrice_timesLE_0" id="apiPrice_timesLE_0"/>
				        	</td>
				        	<td class="price_title">价格</td>
				        	<td class="price_input">
					        	<input type="text"  class="price_txt" name="apiPrice_price_0" id="apiPrice_price_0"/>
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
