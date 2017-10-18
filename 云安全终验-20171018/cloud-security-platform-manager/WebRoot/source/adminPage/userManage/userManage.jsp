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
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
<style>
.user_table thead tr th:nth-child(1) {padding-right: 152px;padding-left: 60px;}
.user_table thead tr th:nth-child(2) {padding-right: 100px;}
.user_table thead tr th:nth-child(3) {padding-right: 80px;}
.user_table thead tr th:nth-child(4) {padding-right: 120px;}
.user_table thead tr th:nth-child(5) {padding-right: 120px;}
.user_table tbody tr td:nth-child(1) {width: 210px;padding-left: 40px;}
.user_table tbody tr td:nth-child(2) {width: 170px;}
.user_table tbody tr td:nth-child(3) {width: 170px;}
.user_table tbody tr td:nth-child(4) {width: 150px;}
.user_table tbody tr td:nth-child(5) {width: 150px;}
.user_search:-webkit-autofill {
				-webkit-box-shadow : 0 0 0px 1000px white inset;
				 border-radius : 50%;
				color:#999
			}
</style>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.JPlaceholder.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/modelbox.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.JPlaceholder.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/userManage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/editUserManage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/price.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript">
$(document).ready(function(){
	$("#searchName").val("${name}");
});
</script>
</head>

<body>
	<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
<!--头部代码结束-->
<form action="${ctx}/adminUserManageUI.html" method="post" id="searchForm">
	<div class="user_search_box">
	    <div class="user_sea_box" style="position:relative">
	      <input class="user_search" type="text" name="name" id="searchName" placeholder="请输入用户名">
	      <span style="width:49px;height:36px;z-index:10;position:absolute;display:block;cursor:pointer;top:4px;right:6px;"onclick="adminSearch()" ></span>
	    </div>
	</div>
</form>
<!--===========content============-->
<div id="content" class="main_wrap">
	<div class="main_center">
    	<div class="add_service">
        	<a href="#" class="add_ser fl" id="add_ser">添加用户</a>
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
            <div class="b_user_table_box" id="online">
            	<div class="b_user_table_c" style="border-right:1px solid #e0e0e0;">
                	<span class="user_title">在线用户　</span><span class="user_num">${onLineSum}</span>
                </div>
            </div>
        </div>
		<div class="system_table">
			<table class="user_table" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th>用户名</th>
                        <th>创建日期</th>
                        <th>用户角色</th>
                        <th>资产数</th>
                        <th>服务数</th>
                        <th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${sysList}" var="user">
	                    <tr>
	                    	<td>${user.name}</td>
	                        <td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/></td>
	                        <td>
	                        	<c:if test="${user.type==1}">系统管理员</c:if>
	                        </td>
	                        <td>${user.assetSum}</td>
	                        <td>${user.servSum}</td>
	                        <td>
	                        	<a href="#" class="add_change" id="${user.id}" name="${user.name}" realName="${user.realName}" mobile="${user.mobile}" industry="${user.industry}" 
	                        	job="${user.job}" company="${user.company}" type="${user.type}">修改</a>
	                        	<a href="javascript:void(0)" onclick="deleteUser('${user.id}')" class="delet">删除</a>
	                        </td>
                   		</tr>
                    </c:forEach>
				</tbody>
			</table>
		</div>
        
        
        <div class="users_table">
        	<table class="user_table" border="0" cellspacing="0" cellpadding="0">
            	<thead>
                	<tr>
                    	<th>用户名</th>
                        <th>创建日期</th>
                        <th>用户角色</th>
                        <th>资产数</th>
                        <th>服务数</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
               		<c:forEach items="${regList}" var="user">
	                    <tr>
                    	<td>${user.name}</td>
                        <td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td>
                        	<c:if test="${user.type==2}">个人用户</c:if>
                        	<c:if test="${user.type==3}">企业用户</c:if>
                        </td>
                        <td>${user.assetSum}</td>
                        <td>${user.servSum}</td>
                        <td>
                        <a href="#" class="add_change" id="${user.id}" name="${user.name}" realName="${user.realName}" mobile="${user.mobile}" industry="${user.industry}" 
	                        	job="${user.job}" company="${user.company}" startIP="${user.startIP}" endIP="${user.endIP}" type="${user.type}">修改</a>
                        <a href="javascript:void(0)" class="delet" onclick="deleteUser('${user.id}')">删除</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="supper_table">
        	<table class="user_table" border="0" cellspacing="0" cellpadding="0">
            	<thead>
                	<tr>
                    	<th>用户名</th>
                        <th>创建日期</th>
                        <th>用户角色</th>
                        <th>资产数</th>
                        <th>服务数</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="user">
                    <tr>
                    	<td>${user.name}</td>
                        <td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td>
                        	<c:if test="${user.type==0}">超级管理员	</c:if>
                        </td>
                        <td>${user.assetSum}</td>
                        <td>${user.servSum}</td>
                        <td><a href="#" class="add_change" id="${user.id}" name="${user.name}" realName="${user.realName}" type="${user.type}" mobile="${user.mobile}" industry="${user.industry}" 
	                        	job="${user.job}" company="${user.company}" >修改</a>
                        <a href="javascript:void(0)" class="delet" onclick="deleteUser('${user.id}')">删除</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="online_table">
        	<table class="user_table" border="0" cellspacing="0" cellpadding="0">
            	<thead>
                	<tr>
                    	<th>用户名</th>
                        <th>创建日期</th>
                        <th>用户角色</th>
                        <th>资产数</th>
                        <th>服务数</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
	                <c:forEach items="${onLineList}" var="user">
	                    <tr>
	                    	<td>${user.name}</td>
	                        <td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/></td>
	                        <td>
	                        	<c:if test="${user.type==1}">系统管理员</c:if>
	                        	<c:if test="${user.type==0}">超级管理员</c:if>
	                        	<c:if test="${user.type==2}">个人用户</c:if>
	                        	<c:if test="${user.type==3}">企业用户</c:if>
	                        </td>
	                        <td>${user.assetSum}</td>
	                        <td>${user.servSum}</td>
	                        <td>
	                        	<a href="#" class="add_change" id="${user.id}" name="${user.name}" realName="${user.realName}" mobile="${user.mobile}" industry="${user.industry}" 
	                        	job="${user.job}" company="${user.company}" startIP="${user.startIP}" endIP="${user.endIP}" type="${user.type}">修改</a>
	                        	<a href="javascript:void(0)" onclick="deleteUser('${user.id}')" class="delet">删除</a>
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
            <td class="regist_prompt" style="text-align:left;">4-20位，支持英文，数字，下划线组合</td>
          </tr>
           <tr class="register_tr">
            <td class="regist_title">设置密码</td>
            <td class="regist_input"><input type="password" class="regist_txt" name="password" id="regist_password" onblur="checkPassword()"/><span id="regist_password_msg" style="color:red;float:left"></td>
            <td class="regist_prompt" style="text-align:left;">6-20位，支持英文，数字，字符组合</td>
          </tr>
           <tr class="register_tr">
            <td class="regist_title">确认密码</td>
            <td class="regist_input"><input type="password" class="regist_txt required" name="confirm_password" id="regist_confirm_password" onblur="checkConfirmPassword()" autocomplete="off"/><span id="regist_confirm_password_msg" style="color:red;float:left;"></td>
            <td class="regist_prompt red_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">真实姓名</td>
            <td class="regist_input"><input type="text" name="realName" id="realName" class="regist_txt"/><span id="regist_realname_msg" style="color:red;float:left;"></td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">手机号码</td>
            <td class="regist_input"><input type="text" class="regist_txt required" name="mobile" id="addPhone" onblur="checkMobile()" style="margin-right:20px"/>
            <span id="add_mobile_msg" style="color:red;float:left;display:block;"></td>
            <td class="regist_prompt"></td>                       
          </tr>
          <tr class="register_tr">
            <td class="regist_title">用户分组</td>
            <td class="regist_input">
                <select class="regist_sel" id="addType" name="type"  onchange="addShow(this.value)">
                	<option selected="selected" value="-1">请选择分组</option>
                	<option value="0" >超级管理员</option>
         			<option value="1" >系统管理员</option>
         			<option value="2" >个人用户</option>
         			<option value="3" >企业用户</option>
                </select>
                <span id="regist_type_msg" style="color:red;float:left;">
            </td>
            <td class="regist_prompt"></td>
          </tr>

          <tr class="register_tr" id="ipAddRange" style="display:none">
            <td class="regist_title">IP地址段</td>
            <td class="regist_input"><input type="text" name="startIP" id="addStartIP" class="regist_txt" style="width:100px;"/><i class="fl">-</i><input type="text" name="endIP" id="addEndIP" class="regist_txt" style="width:100px;"></span></td>
            <td class="regist_prompt"><span id="add_ip_msg" style="color:red;float:left"></td>    
          </tr>
          <tr class="register_tr">
            <td class="regist_title">所在行业</td>
            <td class="regist_input">
                <select class="regist_sel" id="addIndustry" name="industry">
                	<option selected="selected" value="-1">请选择行业</option>
                	<option value="农、林、牧、渔业" > 农、林、牧、渔业</option>
         			<option value="采矿业" > 采矿业</option>
         			<option value="电力、热力、燃气及水生产和供应业" >电力、热力、燃气及水生产和供应业</option>
         			<option value="建筑业" >建筑业</option>
         			<option value="批发和零售业" >批发和零售业</option>
         			<option value="交通运输、仓储和邮政业" >交通运输、仓储和邮政业</option>
         			<option value="住宿和餐饮业" >住宿和餐饮业</option>
         			<option value="信息传输、软件和信息技术服务业" >信息传输、软件和信息技术服务业</option>
         			<option value="金融业" >金融业</option>
         			<option value="房地产业" >房地产业</option>
         			<option value="租赁和商务服务业" >租赁和商务服务业</option>
         			<option value="科学研究和技术服务业" >科学研究和技术服务业</option> 
                </select>
            </td>
            
          </tr>
          <tr class="register_tr">
            <td class="regist_title">职业</td>
            <td class="regist_input">
            <select class="regist_sel" id="addJob" name="job" >
            	<option selected="selected" value="-1">请选择职业</option>
               	<option value="党的机关、国家机关、群众团体和社会组织、企事业单位负责人" >党的机关、国家机关、群众团体和社会组织、企事业单位负责人</option>
         			<option value="专业技术人员" >专业技术人员</option>
         			<option value="办事人员和有关人员" >办事人员和有关人员</option>
         			<option value="社会生产服务和生活服务人员" >社会生产服务和生活服务人员</option>
         			<option value="农、林、牧、渔业生产及辅助人员" >农、林、牧、渔业生产及辅助人员</option>
         			<option value="生产制造及有关人员" >生产制造及有关人员</option>
         			<option value="军人" >军人</option>
         			<option value="不便分类的其他从业人员" >不便分类的其他从业人员</option>
            </select>
            </td>
            
          </tr>
          <tr class="register_tr">
            <td class="regist_title">所在公司名称</td>
			<td class="regist_input"><input type="text" name="company" id="addCompany" class="regist_txt"/></td>           
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
            <td class="regist_input"><input type="text" class="regist_txt" name="name" id="editUseName"/><span id="update_name_msg" style="color:red;float:left"></span></td>
            <td class="regist_prompt" style="text-align:left;">4-20位字符，支持英文，数字，字符组合</td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">设置密码</td>
            <td class="regist_input"><input type="password" class="regist_txt" name="password" id="update_password" /><span id="update_password_msg" style="color:red;float:left"></td>
            <td class="regist_prompt" style="text-align:left;">6-20位，支持英文，数字，字符组合</td>
          </tr>
           <tr class="register_tr">
            <td class="regist_title">确认密码</td>
            <td class="regist_input"><input type="password" class="regist_txt required" name="confirm_Updatepassword" id="update_confirm_password" /><span id="update_confirm_password_msg" style="color:red;float:left;"></td>
            <td class="regist_prompt red_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">真实姓名</td>
            <td class="regist_input"><input type="text" name="realName" id="editRealName" class="regist_txt"/><span id="update_realname_msg" style="color:red;float:left;"></td>
            <td class="regist_prompt"></td>
          </tr>
          <tr class="register_tr">
            <td class="regist_title">手机号码</td>
            <td class="regist_input"><input type="text" class="regist_txt required" name="mobile" id="editPhone" onblur="editCheckMobile()" style="margin-right:20px"/>
            <span id="edit_mobile_msg" style="color:red;float:left;display:block;"></td>
            <td class="regist_prompt"></td>                       
          </tr>
          
          <tr class="register_tr">
            <td class="regist_title">用户分组</td>
            <td class="regist_input">
                <select class="regist_sel" id="editType" name="type"  onchange="editShow(this.value)">
                	<option selected="selected" value="-1">请选择分组</option>
                	<option value="0" >超级管理员</option>
         			<option value="1" >系统管理员</option>
         			<option value="2" >个人用户</option>
         			<option value="3" >企业用户</option>
                </select>
                <span id="edit_type_msg" style="color:red;float:left;">
            </td>
            <td class="regist_prompt"></td>
          </tr>

          <tr class="register_tr" id="ipRange" style="display:none">
            <td class="regist_title">IP地址段</td>
            <td class="regist_input"><input type="text" name="startIP" id="editStartIP" class="regist_txt" style="width:100px;"/><i class="fl">-</i><input type="text" name="endIP" id="editEndIP" class="regist_txt"  style="width:100px;"></td>
             <td class="regist_prompt"><span id="edit_ip_msg" style="color:red;float:left"></span></td>    
          </tr>
          <tr class="register_tr">
            <td class="regist_title">所在行业</td>
            <td class="regist_input">
                <select class="regist_sel" id="editIndustry" name="industry">
                	<option selected="selected" value="-1">请选择行业</option>
                	<option value="农、林、牧、渔业" > 农、林、牧、渔业</option>
         			<option value="采矿业" > 采矿业</option>
         			<option value="电力、热力、燃气及水生产和供应业" >电力、热力、燃气及水生产和供应业</option>
         			<option value="建筑业" >建筑业</option>
         			<option value="批发和零售业" >批发和零售业</option>
         			<option value="交通运输、仓储和邮政业" >交通运输、仓储和邮政业</option>
         			<option value="住宿和餐饮业" >住宿和餐饮业</option>
         			<option value="信息传输、软件和信息技术服务业" >信息传输、软件和信息技术服务业</option>
         			<option value="金融业" >金融业</option>
         			<option value="房地产业" >房地产业</option>
         			<option value="租赁和商务服务业" >租赁和商务服务业</option>
         			<option value="科学研究和技术服务业" >科学研究和技术服务业</option>         
                </select>
            </td>
            </tr>
          <tr class="register_tr">
            <td class="regist_title">职业</td>
            <td class="regist_input">
            <select class="regist_sel" id="editJob" name="job" >
            	<option selected="selected" value="-1">请选择职业</option>
               	<option value="党的机关、国家机关、群众团体和社会组织、企事业单位负责人" >党的机关、国家机关、群众团体和社会组织、企事业单位负责人</option>
         			<option value="专业技术人员" >专业技术人员</option>
         			<option value="办事人员和有关人员" >办事人员和有关人员</option>
         			<option value="社会生产服务和生活服务人员" >社会生产服务和生活服务人员</option>
         			<option value="农、林、牧、渔业生产及辅助人员" >农、林、牧、渔业生产及辅助人员</option>
         			<option value="生产制造及有关人员" >生产制造及有关人员</option>
         			<option value="军人" >军人</option>
         			<option value="不便分类的其他从业人员" >不便分类的其他从业人员</option>
            </select>
            </td>
            
          </tr>
          <tr class="register_tr">
            <td class="regist_title">所在公司名称</td>
			<td class="regist_input"><input type="text" name="company" id="editCompany" class="regist_txt"/></td>           
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
