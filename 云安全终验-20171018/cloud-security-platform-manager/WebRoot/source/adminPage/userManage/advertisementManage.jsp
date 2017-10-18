<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%   
          java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
          java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
          String str_date = formatter.format(currentTime); //将日期时间格式化
 %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>广告区配置</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
<style>
.user_table thead tr th:nth-child(1) {padding-right: 170px;padding-left: 60px;}
.user_table thead tr th:nth-child(2) {padding-right: 160px;}
.user_table thead tr th:nth-child(3) {padding-right: 151px;}
.user_table thead tr th:nth-child(4) {padding-right: 140px;}
.user_table tbody tr td:nth-child(1) {width: 229px;padding-left: 60px;}
.user_table tbody tr td:nth-child(2) {width: 220px;}
.user_table tbody tr td:nth-child(3) {width: 230px;}
.user_table tbody tr td:nth-child(4) {width: 225px;}
</style>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/advertisementbox.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/query.JPlaceholder.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/advertisementManage.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<style type="text/css">
.uploader {
position: relative;
}
.filename {
float: left;
width: 180px;
overflow: hidden;
/*padding: 8px 10px;*/
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
right: 0;
height: 30px;
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
$(function() {
	var type = ${adType};
	 document.getElementById("select_type")[type].selected=true;
});
</script>
</head>

<body>
<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
<div class="main_wrap">
	<div class="main_center">
		<input type="hidden" value="${adType}" id="adType"/>
		<a href="javascript:;" class="adBtn new"  id="add_ser">添加广告</a>
	    <form class="clearfix equipment analysecent" >
	            <select id="select_type" class="text" name="type">
	            	<option value="0">首页</option>
	            	<option value="1">网站安全帮</option>
	            	<option value="2">安全能力API</option>
	            </select>                            
	            <input type="button" onclick="toAdManagerUI(null);" class="sub" value="" style="right:-130px; top:16px;">
	    </form>

        <div class="supper_table">
        	<table class="user_table" cellpadding="0" cellspacing="0">
            	<thead>
                	<tr>
                    	<th>广告名称</th>
                        <th>广告图片</th>
                        <th>广告有效时间</th>
                        <th>广告排序</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${adList}" var="ad" varStatus="status">
                    <tr>
                    	<td>${ad.name}</td>
                        <td>
	                        <c:if test="${fn:length(ad.image)<=18}">
	                                    ${ad.image}
	                        </c:if>
	                        <c:if test="${fn:length(ad.image)>18}">
	                                ${fn:substring(ad.image, 0, 18)}...
	                        </c:if>
                        </td>
                        <td><fmt:formatDate value="${ad.startDate}" pattern="yyyy/MM/dd"/>&nbsp;-&nbsp;<fmt:formatDate value="${ad.endDate}" pattern="yyyy/MM/dd"/></td>
                        <td>
                        	<input type="hidden" id="ad_id_${status.index }" value="${ad.id}"/>
                        	<input type="hidden" id="ad_order_${status.index }" value="${ad.orderIndex}"/>
	                        <c:if test="${status.index > 0}">
	                        	<a href="javascript:void(0)" class="ope_a" onclick="upSort('${status.index}')">上移</a>
	                        </c:if>
	                        <c:if test="${status.index < adList.size()-1}">
                        		<a href="javascript:void(0)" class="ope_a" onclick="downSort('${status.index}')">下移</a>
	                        </c:if>
                        </td>
                        <td>
                        	<a href="javascript:void(0)" class="delet" onclick="deleteAdvertisement('${ad.id}')">删除</a>
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
<div id="box_logoIn" class="box_logoIn user_model" style="height:400px">
  <div class="add_ser_top w678">
	<p class="w634">添加广告</p><p id="close" class="modelclose"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
   </div> 
       <div class="regist_form">
      <form  id="form_advertisement" name="form_regist" method="post" action="${ctx}/adminAdvertisementAdd.html" enctype="multipart/form-data">
        <table>
          <tr class="register_tr">
            <td class="regist_title">广告名称</td>
            <td class="regist_input"><input type="text" class="regist_txt required" name="name" id="regist_name"/><span id="regist_name_msg" style="color:red;float:left"></span></td>
            <td class="regist_prompt"></td>
          </tr>
           <tr class="register_tr">
            <td class="regist_title">广告图片</td>
            <td class="regist_input">
            <div  class="uploader">
	            <!-- <input type="text" class="regist_txt required" name="image" id="regist_image"/> -->
	            <input type="text" class="regist_txt filename" readonly="readonly">
	            <input type="button" class="filebutton" value="浏览.."/>
	            <input type="file" id="regist_images" name="file" />
	            </div>
	            <span id="regist_image_msg" style="color:red;float:left"></span>
	        </td>
	        <td class="regist_prompt" style="text-align:left;">请上传.jpg,.bmp或.png格式的文件</td>
          </tr>
          <tr class="register_tr">
          	<td class="regist_title">广告分类</td>
            <td class="regist_input">
                <select class="regist_sel" id="regist_type" name="type">
                	<option selected="selected" value="-1">请选择分类</option>
                	<option value="0" >首页</option>
         			<option value="1" >网站安全帮</option>
         			<option value="2" >安全能力API</option>
                </select>
                <span id="regist_type_msg" style="color:red;float:left;">
            </td>
            <td class="regist_prompt"></td>
          </tr>

          <tr class="register_tr">
            <td class="regist_title">广告有效期</td>
            <td class="regist_input">
            	<input type="text" class="regist_date_start" value="" id="regist_startDate" name="startDate" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'<%=str_date%>',startDate:'<%=str_date%>',dateFmt:'yyyy-MM-dd',onpicked:function(dp){checkDate(); }})"/>
	            <!--  <input type="text" class="regist_date_start" id="startDate" value="" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" /> -->
	            &nbsp;-&nbsp;
	            <input type="text" class="regist_date_end" value="" id="regist_endDate" name="endDate" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,minDate:'<%=str_date%>',startDate:'<%=str_date%>',dateFmt:'yyyy-MM-dd',onpicked:function(dp){checkDate(); }})"/>
	            <!--<input type="text" class="regist_date_end" id="endDate" value="" onclick="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" />  -->
            	<span id="regist_date_msg" style="color:red;float:left"></span>
            </td>
            <td class="regist_prompt"></td>
          </tr>
          
          <tr>
            <td colspan="3"><input type="button" class="ser_btn" onclick="add()" value="立即添加"/></td>
          </tr>
        </table>
      </form>
    </div>
</div>
</body>
</html>
