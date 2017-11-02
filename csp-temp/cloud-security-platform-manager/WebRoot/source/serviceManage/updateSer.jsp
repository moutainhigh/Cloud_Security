<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
    <title>服务维护修改</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>

<script src="${ctx}/source/scripts/common/jquery.js" type="text/javascript"></script><!--
<script src="${ctx}/source/serviceManage/resources/scripts/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/jquery-ui-1.8.10.custom.min.js"></script>
--><script src="${ctx}/source/scripts/common/jquery.form.js" type="text/javascript"></script>
<link href="${ctx}/source/serviceManage/addSer/styles.css" type="text/css" rel="stylesheet" /><!--
<script type="text/javascript" src="${ctx}/source/serviceManage/addSer/data.js"></script>
--><link href="${ctx}/source/serviceManage/resources/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/source/serviceManage/resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/source/serviceManage/data/styles.css" type="text/css" rel="stylesheet"/>

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
<script src="${ctx}/source/serviceManage/resources/scripts/axure/tree.js"></script><!--
<script src="${ctx}/source/serviceManage/resources/scripts/axure/init.temp.js"></script>
--><script src="${ctx}/source/serviceManage/resources/scripts/axure/legacy.js"></script>
<script src="${ctx}/source/serviceManage/resources/scripts/axure/viewer.js"></script>
<script type="text/javascript">
  $axure.utils.getTransparentGifPath = function() { return 'resources/images/transparent.gif'; };
  $axure.utils.getOtherPath = function() { return 'resources/Other.html'; };
  $axure.utils.getReloadPath = function() { return 'resources/reload.html'; };
</script>
<script type="text/javascript" src="${ctx}/source/serviceManage/servManage.js"></script>
<script type="text/javascript">
$(function(){
//一级分类设定
var parent = "${parent}";
var length = $("#u14_input option").length;
for(var i=0; i<length; i++){
	if($("#u14_input option").eq(i).text() == parent){
		$("#u14_input option").eq(i).siblings().attr("selected",false);
		$("#u14_input option").eq(i).attr("selected",true);
	}
}
//服务类型设定
var type = ${type}-1;
$("#u17_input option").eq(type).siblings().attr("selected",false);
$("#u17_input option").eq(type).attr("selected",true);

//重置按钮
$("#u11_input").click(function(){
	$("#u14_input").val('1');
	$("#u17_input").val('1');
	$("#u2_input").val('');
	$("#u5_input").val('');
});
});

</script>
  </head>
  <body>
    <div id="base" class="">
	<input type="hidden" id="filePathHidden" value="${icon}"/>
	<input type="hidden" id="servIdHidden" value="${servId}"/>
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
        <input id="u2_input" type="text" value="${servName}"/>
      </div>

      <!-- Unnamed (Shape) -->
      <div id="u3" class="ax_paragraph">
        <img id="u3_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u4" class="text">
          <p><span>服务描述：</span></p>
        </div>
      </div>

      <!-- Unnamed (Text Area) -->
      <div id="u5" class="ax_text_area">
        <textarea id="u5_input">${remarks}</textarea>
      </div>

      <!-- Unnamed (Shape) -->
      <div id="u6" class="ax_paragraph">
        <img id="u6_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u7" class="text">
          <p><span>服务图标</span><span>：</span></p>
        </div>
      </div>
	 <form enctype='multipart/form-data' method="post" name="form1" id="form1" > 
      <!-- Unnamed (Text Field) -->
      <div id="u8" class="ax_text_field">
        <!--<input id="u8_input" type="text" value=""/>
        --><input  id="u8_input" type="file" name="file" value="${icon}"/>
      </div>

      <!-- Unnamed (HTML Button) -->
      <div id="u9" class="ax_html_button">
        <input id="u9_input" type="button" value="上传" onclick="uploadIcon();"/>
      </div>
      </form>
      <!-- Unnamed (HTML Button) -->
      <div id="u10" class="ax_html_button">
        <input id="u10_input" type="button" value="保存" onclick="updateServ();"/>
      </div>

      <!-- Unnamed (HTML Button) -->
      <div id="u11" class="ax_html_button">
        <input id="u11_input" type="submit" value="重置"/>
      </div>

      <!-- Unnamed (Shape) -->
      <div id="u12" class="ax_paragraph">
        <img id="u12_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u13" class="text">
          <p><span>一级分类</span><span>：</span></p>
        </div>
      </div>

      <!-- Unnamed (Droplist) -->
      <div id="u14" class="ax_droplist">
        <select id="u14_input" onchange="changeParent();">
          <option value="1">网站安全帮</option>
          <option value="2">数据库安全帮</option>
          <option value="3">系统安全帮</option>
          <option value="4">网络安全帮</option>
          <option value="5">移动安全帮</option>
          <option value="6">API</option>
        </select>
      </div>

      <!-- Unnamed (Shape) -->
      <div id="u15" class="ax_paragraph">
        <img id="u15_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u16" class="text">
          <p><span>服务类型：</span></p>
        </div>
      </div>

      <!-- Unnamed (Droplist) -->
      <div id="u17" class="ax_droplist">
        <select id="u17_input">
          <option value="1">网站安全监测及预警服务</option>
          <option value="2">网站安全防护及加固服务</option>
        </select>
      </div>

    </div>
  </body>
</html>
