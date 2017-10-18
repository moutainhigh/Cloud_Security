<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>公告管理</title>
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
<style>
.user_table thead tr th:nth-child(1) {padding-right: 234px;padding-left: 60px;}
.user_table thead tr th:nth-child(2) {padding-right: 298px;}
.user_table thead tr th:nth-child(3) {padding-right: 187px;}
.user_table tbody tr td:nth-child(1) {width: 260px;padding-left: 60px;}
.user_table tbody tr td:nth-child(2) {width: 360px;}
.user_table tbody tr td:nth-child(3) {width: 200px;}
</style>

<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/noticebox.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/query.JPlaceholder.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript">
//提交
function add(){
    $("#form_notice").submit();
}
//修改
function edit(){
    $("#editNoticeForm").submit();
}
//删除
function deleteNotice(id){
    var noticeId = id;
    if (window.confirm("确实要删除吗?")==true) {
        window.location.href="/cloud-security-platform-manager/adminNoticeDelete.html?id="+noticeId;
    } else {
        return;
    }
}
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
		    	<div class="add_service">
		        	<a href="#" class="add_ser fl" id="add_ser">添加公告</a>
		        </div>
		        <div class="supper_table">
		        	<table class="user_table" cellpadding="0" cellspacing="0">
		            	<thead>
		                	<tr>
		                    	<th>标题</th>
		                        <th>公告内容</th>
		                        <th>创建时间</th>
		                        <th>操作</th>
		                    </tr>
		                </thead>
		                <tbody>
		                <c:forEach items="${noticeList}" var="notice">
		                    <tr>
		                    	<td>${notice.noticeName}</td>
		                        <td>
			                        <c:if test="${fn:length(notice.noticeDesc)<=18}">
			                                    ${notice.noticeDesc}
			                        </c:if>
			                        <c:if test="${fn:length(notice.noticeDesc)>18}">
			                                ${fn:substring(notice.noticeDesc, 0, 18)}...
			                        </c:if>
		                        </td>
		                        <td><fmt:formatDate value="${notice.createDate}" pattern="yyyy-MM-dd"/></td>
		                        <td><a href="#" class="add_change" id="${notice.id}" noticeName="${notice.noticeName}" noticeDesc="${notice.noticeDesc}">修改</a>
		                        <a href="javascript:void(0)" class="delet" onclick="deleteNotice('${notice.id}')">删除</a></td>
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
<div id="box_logoIn" class="box_logoIn user_model">
  <div class="add_ser_top w678">
	<p class="w634">添加公告</p><p id="close" class="modelclose"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
   </div> 
       <div class="regist_form">
      <form  id="form_notice" name="form_regist" method="post" action="${ctx}/adminNoticeAdd.html">
        <table>
           <tr class="register_tr">
            <td class="regist_title">标题</td>
            <td class="regist_input"><input type="text" class="regist_txt required" name="noticeName" id="noticeName" maxlength="15"/></td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title" style="width:410px">公告内容</td>
            <td class="regist_input"><textarea name="noticeDesc" id="noticeDesc" class="regist_txt" style="height:180px;width:500px"></textarea></td>
            <td class="regist_prompt"></td>
          </tr>
          
          <tr>
            <td colspan="3"><input type="button" class="ser_btn" onclick="add()" value="立即添加"/></td>
          </tr>
        </table>
      </form>
    </div>
</div>
<div class="modelbox" id="modelbox2"></div>
<div id="box_logoIn2" class="box_logoIn user_model">
  <div class="add_ser_top w678">
	<p class="w634">修改公告</p><p id="close2" class="modelclose"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
   </div> 
       <div class="regist_form">
      <form  id="editNoticeForm" name="form_regist" method="post" action="${ctx}/adminNoticeEdit.html">
       <input type="hidden" name="id" id="hiddenEditNoticeid"/>
        <table>
          <tr class="register_tr">
            <td class="regist_title">标题</td>
            <td class="regist_input"><input type="text" class="regist_txt" name="noticeName" id="editNoticeName" maxlength="15"/><span id="regist_name_msg" style="color:red;float:left"></span></td>
            <td class="regist_prompt" style="text-align:left;"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title" style="width:410px">公告内容</td>
            <td class="regist_input"><textarea name="noticeDesc" id="editNoticeDesc" class="regist_txt" style="height:180px;width:500px"></textarea></td>
            <td class="regist_prompt"></td>
          </tr>
          <tr>
            <td colspan="3"><input type="button" class="ser_btn" onclick="edit()" value="立即修改"/></td>
          </tr>
        </table>
      </form>
    </div>
</div>
</body>
</html>
