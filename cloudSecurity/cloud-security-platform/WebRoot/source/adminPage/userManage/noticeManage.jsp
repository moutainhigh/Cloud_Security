<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>用户管理</title>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
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
        window.location.href="/cloud-security-platform/adminNoticeDelete.html?id="+noticeId;
    } else {
        return;
    }
}
</script>
</head>

<body>
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul>
        <li><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">数据分析</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
        <li style="border-right:1px solid #1f8db4;" class="b_current"><a href="${ctx}/adminNoticeManageUI.html" class="white">公告管理</a></li>
      </ul>
    </div>
    <div class="lagst">
      <div class="lagst-left b_lagst_left"> <a href="#"><img src="${ctx}/source/adminImages/b_photo.jpg" width="43" height="42"></a> </div>
      <div class="lagst-right">
        <p ><a href="###" class="white">${sessionScope.globle_user.name }</a></p>
        <p> <a href="${ctx}/adminExit.html" class="white">退出</a></p>
      </div>
    </div>
  </div>
</div>
<div class="main_wrap">
	<div class="main_center">
    	<div class="add_service">
        	<a href="#" class="add_ser fl" id="add_ser">添加公告</a>
        </div>
        <div class="supper_table">
        	<table class="user_table" cellpadding="0" cellspacing="0">
            	<thead>
                	<tr>
                    	<th class="t_username">标题</th>
                        <th class="t_date">公告内容</th>
                        <th class="t_role">创建时间</th>
                        <th class="t_operation">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${noticeList}" var="notice">
                    <tr>
                    	<td class="t_username">${notice.noticeName}</td>
                        <td class="t_date" style="width:280px">
	                        <c:if test="${fn:length(notice.noticeDesc)<=18}">
	                                    ${notice.noticeDesc}
	                        </c:if>
	                        <c:if test="${fn:length(notice.noticeDesc)>18}">
	                                ${fn:substring(notice.noticeDesc, 0, 18)}...
	                        </c:if>
                        </td>
                        <td class="t_role"><fmt:formatDate value="${notice.createDate}" pattern="yyyy-MM-dd"/></td>
                        <td class="t_operation"><a href="#" class="ope_a add_change" id="${notice.id}" noticeName="${notice.noticeName}" noticeDesc="${notice.noticeDesc}">修改</a>
                        <a href="javascript:void(0)" class="ope_a ml20" onclick="deleteNotice('${notice.id}')">删除</a></td>
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
    <li><a href="###">360</a></li>
  </ul>
</div>
<div  class="bottom_main">
<h3><a href="###">联系我们</a></h3>
<ul>
<li><a href="###">客户电话</a></li>
</div>
<div  class="bottom_main" style="width:380px;">
<h3><a href="###">版权信息</a></h3>

</div>
</div>
</div>
<!--尾部部分代码结束-->
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
            <td class="regist_input"><input type="text" class="regist_txt required" name="noticeName" id="noticeName"/></td>
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
            <td class="regist_input"><input type="text" class="regist_txt" name="noticeName" id="editNoticeName"/><span id="regist_name_msg" style="color:red;float:left"></span></td>
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
