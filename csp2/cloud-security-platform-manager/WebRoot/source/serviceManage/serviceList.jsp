<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>服务维护列表页面</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<link href="${ctx}/source/serviceManage/serList/styles.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/serviceManage/serList/data.js"></script>
<link href="${ctx}/source/serviceManage/resources/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/source/serviceManage/resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/source/serviceManage/data/styles.css" type="text/css" rel="stylesheet"/>
<script src="${ctx}/source/serviceManage/resources/scripts/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/jquery-ui-1.8.10.custom.min.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/axQuery.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/globals.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axutils.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/annotation.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/axQuery.std.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/doc.js"></script>
<script src="${ctx}/source/serviceManage/data/document.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/messagecenter.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/events.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/action.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/expr.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/geometry.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/flyout.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/ie.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/model.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/repeater.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/sto.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/utils.temp.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/variables.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/drag.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/move.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/visibility.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/style.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/adaptive.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/tree.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/init.temp.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/legacy.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/viewer.js"></script>
<script type="text/javascript">
  $axure.utils.getTransparentGifPath = function() { return 'resources/images/transparent.gif'; };
  $axure.utils.getOtherPath = function() { return 'resources/Other.html'; };
  $axure.utils.getReloadPath = function() { return 'resources/reload.html'; };
</script>
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/serviceManage/servManage.js"></script>
  </head>
  <body>
    <div id="base" class="">

      <!-- Unnamed (Shape) -->
      <div id="u0" class="ax_paragraph">
        <img id="u0_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u1" class="text">
          <p><span>服务名称：</span></p>
        </div>
      </div>

      <!-- Unnamed (Text Field) -->
      <div id="u2" class="ax_text_field">
        <input id="u2_input" type="text" value=""/>
      </div>

      <!-- Unnamed (Shape) -->
      <div id="u3" class="ax_paragraph">
        <img id="u3_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u4" class="text">
          <p><span>一级分类</span><span>：</span></p>
        </div>
      </div>

      <!-- Unnamed (Droplist) -->
      <div id="u5" class="ax_droplist">
        <select id="u5_input" onchange="changeParentForSearch();">
          <option value="1">网站安全帮</option>
          <option value="2">数据库安全帮</option>
          <option value="3">系统安全帮</option>
          <option value="4">网络安全帮</option>
          <option value="5">移动安全帮</option>
          <option value="6">API</option>
        </select>
      </div>
            <!-- Unnamed (Shape) -->
      <div id="u41" class="ax_paragraph">
        <img id="u41_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u42" class="text">
          <p><span>服务类型：</span></p>
        </div>
      </div>
		<!-- Unnamed (Droplist) -->
      <div id="u43" class="ax_droplist">
        <select id="u43_input">
          <option value="1">网站安全监测及预警服务</option>
          <option value="2">网站安全防护及加固服务</option>
        </select>
      </div>
      <!-- Unnamed (HTML Button) -->
      <div id="u6" class="ax_html_button">
        <input id="u6_input" type="button" value="查询" onclick="searchServ();"/>
      </div>

      <!-- Unnamed (HTML Button) -->
      <div id="u38" class="ax_html_button"><!--
        <input id="u38_input" type="submit" value="添加"/>
        --><a href="${ctx}/addServUI.html" class="add_ser" id="add_ser">添加服务</a>
      </div>
            <!-- Unnamed (Table) -->
        <div class="system_table" style="display:block;margin-top: 110px;">
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
	                    	<td class="t_username">${serv.parentC}</td>
	                        <td class="t_date">${serv.typeName}</td>
	                        <td class="t_assets"><a href="${ctx}/serviceDetailsUI.html?servId=${serv.id}&parent=${serv.parentC}">${serv.name}</a></td>
	                        <td class="t_service" style="text-align:center;width:320px">${serv.remarks}</td>
	                        <td class="t_operation">
	                        	<a href="${ctx }/updateServUI.html?servId=${serv.id}&parent=${serv.parentC}&servName=${serv.name}&icon=${serv.icon}&remarks=${serv.remarks}&type=${serv.servType}" class="ope_a add_change">编辑</a>
	                        	<a href="javascript:void(0)" servid="${serv.id}" parentC="${serv.parentC}" onclick="delServ(this)" class="ope_a ml20">删除</a>
	                        	<a href="${ctx }/addServicePriceUI.html?servId=${serv.id}&parent=${serv.parentC}" class="ope_a add_change">设置价格</a>
	                        </td>
                   		</tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

  </body>
</html>
