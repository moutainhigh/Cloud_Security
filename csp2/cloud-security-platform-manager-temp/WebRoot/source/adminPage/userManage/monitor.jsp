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
<title>可用性监控</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/query.JPlaceholder.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/monitor.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />

<style>
.user_table{padding-top: 20px; }
.user_table thead tr th{text-align:center;}
.user_table thead tr th:nth-child(1) {padding-right: 40px;padding-left: 20px;}
.user_table thead tr th:nth-child(2) {padding-right: 100px;padding-left: 50px;}
.user_table thead tr th:nth-child(3) {padding-right: 30px;}
.user_table thead tr th:nth-child(4) {padding-right: 30px;}
.user_table thead tr th:nth-child(5) {padding-right: 30px;}
.user_table thead tr th:nth-child(6) {padding-right: 30px;}
.user_table thead tr th:nth-child(7) {padding-right: 30px;}
.user_table thead tr th:nth-child(8) {padding-right: 70px;padding-left: 20px;}
.user_table thead tr th:nth-child(9) {padding-right: 100px;padding-left: 10px;}
.user_table tbody tr td{font-size:14px;}
.user_table tbody tr td a{padding-left: 2px; width: 40px;}
.user_table tbody tr td:nth-child(1) {width: 114px;padding-left: 10px;}
.user_table tbody tr td:nth-child(2) {width: 182px;}
.user_table tbody tr td:nth-child(3) {width: 94px;}
.user_table tbody tr td:nth-child(4) {width: 94px;}
.user_table tbody tr td:nth-child(5) {width: 78px;}
.user_table tbody tr td:nth-child(6) {width: 94px;}
.user_table tbody tr td:nth-child(7) {width: 94px;}
.user_table tbody tr td:nth-child(8) {width: 186px;}
.user_table tbody tr td:nth-child(9) {width: 100px;}
.user_table tbody tr td span{display:inline-block; line-height:1rem;}

</style>
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
<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
<div class="main_wrap">
	<div class="main_center">
		<input type="hidden" value="${adType}" id="adType"/>
		<div class="clearfix equipment">
			<a href="javascript:;" class="adBtn new"  id="add_ser">创建项目</a>
		</div>
        <div class="supper_table">
        	<table class="user_table" cellpadding="0" cellspacing="0">
            	<thead>
                	<tr>
                    	<th>监控项目</th>
                        <th>地址</th>
                        <th>监控类型</th>
                        <th>监控频率</th>
                        <th>可用率</th>
                        <th>响应时间</th>
                        <th>当前状态</th>
                        <th>最后检查时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                <!--
                <c:forEach items="${MonList}" var="mon" varStatus="status">
                    <tr>
                    	<td><span>${mon.name}</span></td>
                        <td>${mon.addr}</td>                       
                        <td>${mon.type}</td>
                        <td>${mon.time}</td>
                        <td>${mon.rate}</td>
                        <td>${mon.response}</td>
                        <td>${mon.state}</td>
                        <td>${mon.date}</td>                                           
                        <td>
                        	<a href="javascript:void(0)" class="delet" style="color:#2499fb;" onclick="deleteMon('${mon.id}')">删除</a>/
                        	<a href="javascript:void(0)" onclick="AlterMon('${mon.id}')" style="color:#2499fb;" id="${mon.id}" name="${mon.name}" addr="${mon.addr }" type="${mon.type}" time="${mon.time}" rate="${mon.rate}" response="${mon.response}" state="${mon.state}" date="${mon.date}">修改</a>
                        </td>
                    </tr>
                </c:forEach>
                 -->
                   <tr>
                        <td><span>安全帮</span></td>
                        <td><span>www.anquanbang.net</span></td>                       
                        <td>http服务</td>
                        <td>15分钟</td>
                        <td>98%</td>                       
                        <td>32ms</td>
                        <td>200ok</td>
                        <td>2017.12.12 12:22:22</td>   
                        <td>
                        	<a href="javascript:void(0)" style="color:#2499fb;" onclick="deleteMon('${mon.id}')">删除</a>/
                        	<a href="javascript:void(0)" class="zc_edit" style="color:#2499fb;" id="1" name="安全帮" addr="www.anquanbang.net" type="http服务" time="15分钟" rate="98%" response="32ms" state="200ok" date="2017.12.12 12:22:22">修改</a>
                        </td>
                    </tr>
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
	<p class="w634">创建项目</p><p id="close" class="modelclose"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
   </div> 
       <div class="regist_form">
       <form  id="form_advertisement" name="form_regist" method="post" action="${ctx}/monitorAdd.html" enctype="multipart/form-data">
       <table>
          <tr class="register_tr">
            <td class="regist_title">监控名称</td>
            <td class="regist_input"><input type="text" class="regist_txt required" name="name" id="regist_name"/></td>
            <td class="regist_prompt" style="text-align:left;">给监控项目起一个名字，比如：测试环境。</td>
          </tr>
           <tr class="register_tr">
            <td class="regist_title">URL</td>
            <td class="regist_input"><input type="text" class="regist_txt required" name="addr" id="regist_addr"/></td>            
	        <td class="regist_prompt" style="text-align:left;">监控的网址，可以是网站首页或其他页面，也可以是ip地址</td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">监控频率</td>
            <td class="regist_input">
            	<label><input type="radio" name="time" checked="checked" id="time15" value="15">15分钟</input></label>
                <label style="margin-left:10px;"><input type="radio" name="time" id="time20" value="20">20分钟</label>
                <label style="margin-left:10px;"><input type="radio" name="time" id="time30" value="30">30分钟</label>
                <label style="margin-left:10px;"><input type="radio" name="time" id="time60" value="60">60分钟</label>
            </td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
          	<td class="regist_title">监控类型</td>
            <td class="regist_input">
            	<label><input type="radio" name="type" checked="checked" id="type1" value="15">主机监控</label>
                <label style="margin-left:10px;"><input type="radio" name="type" id="type2" value="20">服务监控</label>

                <select style="margin-left:10px;border:1px solid #cbc9c9;" id="regist_type" name="type2">
                	<option selected="selected" value="-1">http</option>
                	<option value="0" >https</option>
                	<option value="1" >其他服务</option>
                </select>
            </td>
            <td class="regist_prompt"></td>
          </tr>        
          <tr class="register_tr">
          	<td class="regist_title">告警设置</td>
            <td class="regist_input">
            	<div>
            	<label><input type="checkbox" name="alarm" id="alarm1" style="vertical-align:middle;" value="1"><span >邮件</span></label> 
            	<input calss="input_alarm" style="margin-left:10px;width:200px;height:22px;border:1px solid #cbc9c9;" type="text" name="addr" id="regist_addr"/> 
            	</div>
				<br/> 
				<div>
            	<label ><input type="checkbox" name="alarm" id="alarm2" style="vertical-align:middle;" value="2"><span >短信</span></label>
                <input calss="input_alarm" style="margin-left:10px;width:200px;height:22px;border:1px solid #cbc9c9;" type="text" name="addr" id="regist_addr"/>                     
				</div>
            	                       
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
