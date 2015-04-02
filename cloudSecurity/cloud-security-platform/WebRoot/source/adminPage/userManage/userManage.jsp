<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
<script type="text/javascript" src="${ctx}/source/scripts/common/modelbox.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/query.JPlaceholder.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/userManage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/editUserManage.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#searchName").val("${name}");
});
</script>
</head>

<body>
<!--头部代码-->
<div class="head_bj b_head">
  <div class="head">
    <div class="logo"><img src="${ctx}/source/adminImages/b_logo2.jpg"/></div>
    <div class="list b_list">
      <ul>
        <li class="b_current"><a href="${ctx}/adminUserManageUI.html" class="white">用户管理</a></li>
        <li><a href="${ctx}/adminServUI.html" class="white">服务管理</a></li>
        <li><a href="${ctx}/adminDataAnalysisUI.html" class="white">数据分析</a></li>
        <li><a href="${ctx}/adminSystemManageUI.html" class="white">系统管理</a></li>
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
<form action="${ctx}/adminUserManageUI.html" method="post" id="searchForm">
	<div class="user_search_box">
	    <div class="user_sea_box" style="position:relative">
	      <input class="user_search" type="text" name="name" id="searchName" placeholder="请输入用户名">
	      <span style="width:49px;height:36px;z-index:10;position:absolute;display:block;cursor:pointer;top:4px;right:6px;"onclick="adminSearch()" ></span>
	    </div>
	</div>
</form>
<div class="main_wrap">
	<div class="main_center">
    	<div class="add_service">
        	<a href="#" class="add_ser fl" id="add_ser">添加用户</a>
            <a href="${ctx}/adminAuthorityUI.html" class="add_ser fl ml20 b_juris_btn">用户权限</a>
        </div>
        <div class="b_user_table">
        	<div class="b_user_table_box userbox_cur" id="supper">
            	<div class="b_user_table_c">
                	<span class="user_title">超级管理员</span><span class="user_num">${supSum}</span>
                </div>
            </div>
            <div class="b_user_table_box" id="system">
            	<div class="b_user_table_c">
                    <span class="user_title">系统管理员 </span><span class="user_num">${sysSum}</span>
                </div>
            </div>
            <div class="b_user_table_box" id="users">
            	<div class="b_user_table_c" style="border-right:1px solid #e0e0e0;">
                	<span class="user_title">注册用户　</span><span class="user_num">${regSum}</span>
                </div>
            </div>
        </div>
        <div class="system_table">
        	<table class="user_table" cellpadding="0" cellspacing="0">
            	<thead>
                	<tr>
                    	<th class="t_username">用户名</th>
                        <th class="t_date">创建日期</th>
                        <th class="t_role">用户角色</th>
                        <th class="t_assets">资产数</th>
                        <th class="t_service">服务数</th>
                        <th class="t_operation">操作</th>
                    </tr>
                </thead>
                <tbody>
	                <c:forEach items="${sysList}" var="user">
	                    <tr>
	                    	<td class="t_username">${user.name}</td>
	                        <td class="t_date"><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/></td>
	                        <td class="t_role">
	                        	<c:if test="${user.type==1}">系统管理员</c:if>
	                        </td>
	                        <td class="t_assets">${user.assetSum}</td>
	                        <td class="t_service">${user.servSum}</td>
	                        <td class="t_operation">
	                        	<a href="#" class="ope_a add_change" id="${user.id}" name="${user.name}" realName="${user.realName}" type="${user.type}">修改</a>
	                        	<a href="javascript:void(0)" onclick="deleteUser('${user.id}')" class="ope_a ml20">删除</a>
	                        </td>
                   		</tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="users_table">
        	<table class="user_table" cellpadding="0" cellspacing="0">
            	<thead>
                	<tr>
                    	<th class="t_username">用户名</th>
                        <th class="t_date">创建日期</th>
                        <th class="t_role">用户角色</th>
                        <th class="t_assets">资产数</th>
                        <th class="t_service">服务数</th>
                        <th class="t_operation">操作</th>
                    </tr>
                </thead>
                <tbody>
               		<c:forEach items="${regList}" var="user">
	                    <tr>
                    	<td class="t_username">${user.name}</td>
                        <td class="t_date"><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="t_role">
                        	<c:if test="${user.type==2}">注册用户</c:if>
                        </td>
                        <td class="t_assets">${user.assetSum}</td>
                        <td class="t_service">${user.servSum}</td>
                        <td class="t_operation">
                        <a href="#" class="ope_a add_change" id="${user.id}" name="${user.name}" realName="${user.realName}" type="${user.type}">修改</a>
                        <a href="javascript:void(0)" class="ope_a ml20" onclick="deleteUser('${user.id}')">删除</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="supper_table">
        	<table class="user_table" cellpadding="0" cellspacing="0">
            	<thead>
                	<tr>
                    	<th class="t_username">用户名</th>
                        <th class="t_date">创建日期</th>
                        <th class="t_role">用户角色</th>
                        <th class="t_assets">资产数</th>
                        <th class="t_service">服务数</th>
                        <th class="t_operation">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="user">
                    <tr>
                    	<td class="t_username">${user.name}</td>
                        <td class="t_date"><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="t_role">
                        	<c:if test="${user.type==0}">超级管理员	</c:if>
                        </td>
                        <td class="t_assets">${user.assetSum}</td>
                        <td class="t_service">${user.servSum}</td>
                        <td class="t_operation"><a href="#" class="ope_a add_change" id="${user.id}" name="${user.name}" realName="${user.realName}" type="${user.type}">修改</a>
                        <a href="javascript:void(0)" class="ope_a ml20" onclick="deleteUser('${user.id}')">删除</a></td>
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
    <li><a href="###">新用户注册</a></li>
    <li><a href="###">用户登录</a></li>
    <li><a href="###">找回密码</a></li>
  </ul>
</div>
<div  class="bottom_main">
  <h3><a href="###"> 帮助</a></h3>
  <ul>
    <li><a href="###">常见问题</a></li>
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
<ul>
<li>如未经授权用作他处，将保留追究侵权者法律责任的权利。<br />
  京ICP备11111111号-4 / 京ICP证1111111号<br />
  北京市公安局朝阳分局备案编号:110105000501</li>
</div>
</div>
</div>
<!--尾部部分代码结束-->
<!--模态框-->
<div class="modelbox" id="modelbox"></div>
<div id="box_logoIn" class="box_logoIn user_model">
  <div class="add_ser_top w678">
	<p class="w634">添加用户</p><p id="close" class="modelclose"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
   </div> 
       <div class="regist_form">
      <form  id="form_regist" name="form_regist" method="post" action="${ctx}/adminAddUser.html">
        <table>
          <tr class="register_tr">
            <td class="regist_title">用户账号</td>
            <td class="regist_input"><input type="text" class="regist_txt" name="name" id="regist_name" onblur="checkName()" /><span id="regist_name_msg" style="color:red;float:left"></span></td>
            <td class="regist_prompt" style="text-align:left;">4-20位字符，支持中英文，数字，字符组合</td>
          </tr>
           <tr class="register_tr">
            <td class="regist_title">设置密码</td>
            <td class="regist_input"><input type="password" class="regist_txt" name="password" id="regist_password" onblur="checkPassword()"/><span id="regist_password_msg" style="color:red;float:left"></td>
            <td class="regist_prompt" style="text-align:left;">6-20位，可使用字母、数字和下划线的组合，不建议纯字母、纯数字</td>
          </tr>
           <tr class="register_tr">
            <td class="regist_title">确认密码</td>
            <td class="regist_input"><input type="password" class="regist_txt required" name="confirm_password" id="regist_confirm_password" onblur="checkConfirmPassword()"/><span id="regist_confirm_password_msg" style="color:red;float:left;"></td>
            <td class="regist_prompt red_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">真实姓名</td>
            <td class="regist_input"><input type="text" name="realName" id="realName" class="regist_txt"/></td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">用户分组</td>
            <td class="regist_input">
                <select class="regist_sel" id="type" name="type" >
                	<option selected="selected" value="">请选择分组</option>
                	<option value="0" >超级管理员</option>
         			<option value="1" >系统管理员</option>
         			<option value="2" >自注册用户</option>
                </select>
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
<div class="modelbox" id="modelbox2"></div>
<div id="box_logoIn2" class="box_logoIn user_model">
  <div class="add_ser_top w678">
	<p class="w634">修改用户</p><p id="close2" class="modelclose"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
   </div> 
       <div class="regist_form">
      <form  id="editUserForm" name="form_regist" method="post" action="${ctx}/adminEditUser.html">
       <input type="hidden" name="id" id="hiddenEditUserid"/>
        <table>
          <tr class="register_tr">
            <td class="regist_title">用户账号</td>
            <td class="regist_input"><input type="text" class="regist_txt" name="name" id="editUseName"/><span id="regist_name_msg" style="color:red;float:left"></span></td>
            <td class="regist_prompt" style="text-align:left;">4-20位字符，支持中英文，数字，字符组合</td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">真实姓名</td>
            <td class="regist_input"><input type="text" name="realName" id="editRealName" class="regist_txt"/></td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">用户分组</td>
            <td class="regist_input">
                <select class="regist_sel" id="editType" name="type" >
                	<option selected="selected" value="">请选择分组</option>
                	<option value="0" >超级管理员</option>
         			<option value="1" >系统管理员</option>
         			<option value="2" >自注册用户</option>
                </select>
            </td>
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
