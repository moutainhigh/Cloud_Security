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
<title>广告管理</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
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
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAssetUI.html" class="white">资产分析</a></li>
        <li><a href="${ctx}/adminUserAnalysisUI.html" class="white">用户分析</a></li>
        <!-- <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">订单分析</a></li>-->
        <li><a href="${ctx}/orderformanalyse.html" class="white">订单分析</a></li>
        <li><a href="${ctx}/adminWarnAnalysisUI.html" class="white">告警分析</a></li>
        <li><a href="${ctx}/equResourceUI.html" class="white">设备资源管理</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
        <li class="b_current" style="border-right:1px solid #1f8db4;"><a href="${ctx}/adminAdvertisementManageUI.html" class="white">广告管理</a></li>
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
                    	<th class="t_username">广告名称</th>
                        <th class="t_date">广告图片</th>
                        <th class="t_role">广告有效时间</th>
                        <th class="t_order">广告排序</th>
                        <th class="t_operation">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${adList}" var="ad" varStatus="status">
                    <tr>
                    	<td class="t_username">${ad.name}</td>
                        <td class="t_date" style="width:280px">
	                        <c:if test="${fn:length(ad.image)<=18}">
	                                    ${ad.image}
	                        </c:if>
	                        <c:if test="${fn:length(ad.image)>18}">
	                                ${fn:substring(ad.image, 0, 18)}...
	                        </c:if>
                        </td>
                        <td class="t_role"><fmt:formatDate value="${ad.startDate}" pattern="yyyy/MM/dd"/>&nbsp;-&nbsp;<fmt:formatDate value="${ad.endDate}" pattern="yyyy/MM/dd"/></td>
                        <td class="t_order">
                        	<input type="hidden" id="ad_id_${status.index }" value="${ad.id}"/>
                        	<input type="hidden" id="ad_order_${status.index }" value="${ad.orderIndex}"/>
	                        <c:if test="${status.index > 0}">
	                        	<a href="javascript:void(0)" class="ope_a" onclick="upSort('${status.index}')">上移</a>
	                        </c:if>
	                        <c:if test="${status.index < adList.size()-1}">
                        		<a href="javascript:void(0)" class="ope_a" onclick="downSort'${status.index}')">下移</a>
	                        </c:if>
                        </td>
                        <td class="t_operation">
                        	<a href="javascript:void(0)" class="ope_a" onclick="deleteAdvertisement('${ad.id}')">删除</a>
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
<div id="box_logoIn" class="box_logoIn user_model">
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
