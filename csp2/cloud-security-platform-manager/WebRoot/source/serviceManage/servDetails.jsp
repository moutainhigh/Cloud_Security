<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>网站安全帮-服务详情维护</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.form.js"></script>
<!-- <script type="text/javascript" src="${ctx}/source/scripts/common/jquery-1.7.1.min.js"></script> -->
<link href="${ctx}/source/serviceManage/servDetails/styles.css" type="text/css" rel="stylesheet" /><!--
<script type="text/javascript" src="${ctx}/source/serviceManage/servDetails/data.js"></script>
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
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/serviceManage/servDetails/fileupload.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/source/serviceManage/zxxFile.js"></script>
<script type="text/javascript" src="${ctx}/source/serviceManage/servManage.js"></script>
<script type="text/javascript">
$(function(){
//保存上传图片的名字
$("#servDetailHidden").val('');
//编辑详情时，编辑前存的图片
$("#resServDetailHidden").val('');
$("#preview").empty();
var iconList = new Array();
var parent = '${parent}';
if(parent == 'API'){
	$("#u12").hide();
	$("#u16").hide();
	$("#u27").hide();
	$("#u30").hide();
	$("#u26").hide();
} else {
	$("#u12").show();
	$("#u16").show();
	$("#u27").show();
	$("#u30").show();
	$("#u26").show();
}
//添加行按钮
$("#u27_input").click(function(){
	var html = '<div id="u31" class="ax_text_field positionr">'
	        +'<input id="u31_input" name="scanType" type="text" value=""/>'
	        +'</div>';
	$("#u16").append(html);
});
//删除行按钮
$("#u30_input").click(function(){
	$("input[name='scanType']:last").parent().remove();
});

$(".u22_input").click(function(){
	$("#u2_input").val('');
	$("#u5_input").val('');
	$("input[name='servType']").each(function(){
	if($(this).attr('checked')){
		$(this).removeAttr('checked');
	}
	});
	$("#u14_input").val('');
	$("input[name='scanType']").each(function(){
		$(this).val('');
	});
	$("#u39_input").val('');
});

$("#u34_input").bind('change',function(){
	if($(this).val() == '0'){
		$("#u39").hide();
		$("#u19").show();
		$("#u28").show();
	} else {
		$("#u39").show();
		$("#u19").hide();
		$("#u28").hide();
	}
});

var serviceDetail = '${serviceDetail}';
if(serviceDetail != null && serviceDetail != ''){
	var detailFlag = '${serviceDetail.detailFlag}';
	if(detailFlag != null && detailFlag != '' && detailFlag == '0'){
			var detailIcon = '${detailIcon}';
		if(detailIcon != null && detailIcon != ''){
			var detailIconArray = detailIcon.split(";");
			for(var i=0; i<detailIconArray.length; i++){
				var imgsrc = 'http://219.141.189.186:60080/cloud-security-platform/source/images/portal/' + detailIconArray[i];
				var html = '';
				html = html + '<div id="resUploadList_'+ i +'" class="upload_append_list"><p><strong>' + detailIconArray[i] + '</strong>'+ 
					'<a href="javascript:" class="upload_delete" title="删除" data-index="'+ i +'">删除</a><br />' +
					'<img id="resUploadImage_' + i + '" src="' + imgsrc + '" class="upload_image" /></p>'+ 
					'<span id="resUploadProgress_' + i + '" class="upload_progress"></span>' +
				'</div>';
				$("#preview").append(html);
				iconList.push(detailIconArray[i]);
				$("#resServDetailHidden").val(iconList.join(";"));
			}
			$("#uploadHtml").val($('#preview').html());
		}
	}
}

//点击图片上的删除
$(".upload_delete").click(function(){
	var currentIcon = $(this).siblings().eq(0).text();
	iconList.splice($.inArray(currentIcon,iconList),1);
	$("#resServDetailHidden").val(iconList.join(";"));
	$(this).parent().remove();
	$("#uploadHtml").val($('#preview').html());
});

});
</script>
</head>
  <body>
    <div id="base" class="">
	  <input type="hidden" id="uploadHtml" value=""/>
	  <input type="hidden" id="servDetailHidden" value=""/>
	  <input type="hidden" id="resServDetailHidden" value=""/>
      <!-- Unnamed (Shape) -->
      <div id="u0" class="ax_paragraph">
        <img id="u0_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u1" class="text positiont1">
          <p><span>价格标题：</span></p>
        </div>
	      <div id="u2" class="ax_text_field positionr1">
	        <input id="u2_input" type="text" value="${serviceDetail.priceTitle}"/>
	      </div>
      </div>


      <!-- Unnamed (Shape) -->
      <div id="u3" class="ax_paragraph">
        <img id="u3_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u4" class="text positiont">
          <p><span>选类型标题：</span></p>
        </div>
        <div id="u5" class="ax_text_field positionr">
        	<input id="u5_input" type="text" value="${serviceDetail.typeTitle }"/>
      	</div>
      </div>

      <!-- Unnamed (Shape) -->
      <div id="u6" class="ax_paragraph">
        <img id="u6_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u7" class="text positiont">
          <p><span>选类型：</span></p>
        </div>
              <!-- Unnamed (Checkbox) -->
      <div id="u8" class="ax_checkbox positionr">
        <label for="u8_input">
          <!-- Unnamed () -->
          	<c:choose>
	          	<c:when test="${serviceDetail.servType == 0 or serviceDetail.servType ==1}">
	          		<input id="u8_input" type="checkbox" name="servType" value="1" checked/>  
	          	</c:when>
	          	<c:otherwise>
	          		<input id="u8_input" type="checkbox" name="servType" value="1"/> 
	          	</c:otherwise>
          	</c:choose>         
            <span>单次</span>      
        </label>
      </div>

      <!-- Unnamed (Checkbox) -->
      <div id="u10" class="ax_checkbox positionr">
        <label for="u10_input">
            <c:choose>
	          	<c:when test="${serviceDetail.servType == 0 or serviceDetail.servType ==2}">
	          		<input id="u10_input" type="checkbox" name="servType" value="2" checked/>  
	          	</c:when>
	          	<c:otherwise>
	          		<input id="u10_input" type="checkbox" name="servType" value="2"/>
	          	</c:otherwise>
          	</c:choose>    
            
            <span>长期</span>           
        </label>
      </div>
      <div id="u23" class="ax_paragraph positionr">
        <img id="u23_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
         <span>备注：</span><span>如果当前</span><span>服务只有单次或者长期，详情页面不显示</span><span>这一行</span>
      </div>
      </div>

      <!-- Unnamed (Shape) -->
      <div id="u12" class="ax_paragraph">
        <img id="u12_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u13" class="text positiont">
          <p><span>服务频率标题：</span></p>
        </div>
        <div id="u14" class="ax_text_field positionr">
        	<input id="u14_input" type="text" value="${serviceDetail.ratesTitle }"/>
      	</div>
<%--       	<div id="u25" class="ax_paragraph positionr">
        <img id="u25_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
      </div> --%>
      </div>

      <!-- Unnamed (Shape) -->
      <div id="u15" class="ax_paragraph">
        <img id="u15_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u16" class="text positiont">
          <p><span>服务频率：</span></p>
          <!--服务频率-->
          <c:choose>
          <c:when test="${scanTypeList==null or scanTypeList.size() == 0}">
          		<div id="u31" class="ax_text_field positionr">
			        <input id="u31_input" name="scanType" type="text" value=""/>
			    </div>
          </c:when>
          <c:otherwise>
	          <c:forEach var="scanType" items="${scanTypeList }">
			      <div id="u31" class="ax_text_field positionr">
			        <input id="u31_input" name="scanType" type="text" value="${scanType.scan_name }"/>
			      </div>
		      </c:forEach>
          </c:otherwise>
          </c:choose>
        </div>
        <div id="u27" class="ax_html_button positionr">
        	<input id="u27_input" type="submit" value="添加行"/>
        </div>
        <div id="u30" class="ax_html_button positionr">
        	<input id="u30_input" type="submit" value="删除行"/>
      	</div>
      	<div id="u26" class="text positionr">
          <p><span>备注：</span><span>根据</span><span>配置</span><span>服务频率功能显示</span></p>
        </div>
      </div>
      
      <div id="u32" class="ax_paragraph">
        <img id="u32_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u33" class="text positiont">
          <p><span>详细信息选项：</span></p>
        </div>
        <div id="u34" class="ax_text_field positionr">
        	<c:choose>
        	<c:when test="${serviceDetail.detailFlag == '1' }">
        		<select id="u34_input" >
	        		<option value="0">上传详情图片</option>
	        		<option value="1" selected>填写API信息</option>
	        	</select>
        	</c:when>
        	<c:otherwise>
	        	<select id="u34_input" >
	        		<option value="0" selected>上传详情图片</option>
	        		<option value="1">填写API信息</option>
	        	</select>
        	</c:otherwise>
        	</c:choose>
      	</div>
      </div>
      <!-- Unnamed (Shape) -->
      <div id="u17" class="ax_paragraph">
        <img id="u17_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u18" class="text positiont">
          <p><span>服务详细信息：</span></p>
        </div>
      <c:choose>
      <c:when test="${serviceDetail.detailFlag == '1' }">
     	<div id="u19" class="ax_text_field positionr" style="display:none;">
        <form id="uploadForm" action="uploadDetail.html" method="post" enctype="multipart/form-data">
              <div class="upload_box">
                  <div class="upload_main">
                      <div class="upload_choose">
                          <input id="fileImage" type="file" size="30" name="fileselect" multiple />
                          <span id="fileDragArea" class="upload_drag_area">或者将图片拖到此处</span>
                      </div>
                      <div id="preview" class="upload_preview">
                      </div>
                  </div>
                  <div class="upload_submit">
                      <button type="button" id="fileSubmit" class="upload_submit_btn">确认上传图片</button>
                  </div>
                  <div id="uploadInf" class="upload_inf">
                  	<c:choose>
                  	<c:when test="${serviceDetail == null or serviceDetail == ''}">
                  		<input id="u21_input" type="button" value="保存" onclick="saveDetails('${serviceId}','${parent}',true);"/>
                  	</c:when>
                  	<c:otherwise>
                  		<input id="u21_input" type="button" value="保存" onclick="saveDetails('${serviceId}','${parent}',false);"/>
                  	</c:otherwise>
                  	</c:choose>     	
                  	<input class="u22_input" type="reset" value="重置"/>
                  </div>
              </div>
         </form>
      </div>
      <div id="u28" class="ax_paragraph positionr" style="display:none;">
        <img id="u28_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u29" class="text">
          <p><span>上传多张图片</span></p>
        </div>
      </div>
      		<div id="u39" class="ax_text_field positionr">
      		   <textarea id="u39_input">${serviceDetail.detailIcon }</textarea>
        	   <div id="uploadInf" class="upload_inf">
		           	<c:choose>
		           	<c:when test="${serviceDetail == null or serviceDetail == ''}">
		           		<input id="u21_input" type="button" value="保存" onclick="saveDetails('${serviceId}','${parent}',true);"/>
		           	</c:when>
		           	<c:otherwise>
		           		<input id="u21_input" type="button" value="保存" onclick="saveDetails('${serviceId}','${parent}',false);"/>
		           	</c:otherwise>
		           	</c:choose>     	
		           	<input class="u22_input" type="reset" value="重置"/>
           		</div>
      		</div>
      </c:when>
      <c:otherwise>
      <div id="u19" class="ax_text_field positionr">
        <form id="uploadForm" action="uploadDetail.html" method="post" enctype="multipart/form-data">
              <div class="upload_box">
                  <div class="upload_main">
                      <div class="upload_choose">
                          <input id="fileImage" type="file" size="30" name="fileselect" multiple />
                          <span id="fileDragArea" class="upload_drag_area">或者将图片拖到此处</span>
                      </div>
                      <div id="preview" class="upload_preview">
                      </div>
                  </div>
                  <div class="upload_submit">
                      <button type="button" id="fileSubmit" class="upload_submit_btn">确认上传图片</button>
                  </div>
                  <div id="uploadInf" class="upload_inf">
                  	<c:choose>
                  	<c:when test="${serviceDetail == null or serviceDetail == ''}">
                  		<input id="u21_input" type="button" value="保存" onclick="saveDetails('${serviceId}','${parent}',true);"/>
                  	</c:when>
                  	<c:otherwise>
                  		<input id="u21_input" type="button" value="保存" onclick="saveDetails('${serviceId}','${parent}',false);"/>
                  	</c:otherwise>
                  	</c:choose>     	
                  	<input class="u22_input" type="reset" value="重置"/>
                  </div>
              </div>
         </form>
      </div>
      <div id="u28" class="ax_paragraph positionr">
        <img id="u28_img" class="img " src="${ctx}/source/serviceManage/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u29" class="text">
          <p><span>上传多张图片</span></p>
        </div>
      </div>
      	<div id="u39" class="ax_text_field positionr" style="display:none;">
      	    <textarea id="u39_input">${serviceDetail.detailIcon }</textarea>
        	<div id="uploadInf" class="upload_inf">
	           	<c:choose>
	           	<c:when test="${serviceDetail == null or serviceDetail == ''}">
	           		<input id="u21_input" type="button" value="保存" onclick="saveDetails('${serviceId}','${parent}',true);"/>
	           	</c:when>
	           	<c:otherwise>
	           		<input id="u21_input" type="button" value="保存" onclick="saveDetails('${serviceId}','${parent}',false);"/>
	           	</c:otherwise>
	           	</c:choose>     	
	           	<input class="u22_input" type="reset" value="重置"/>
           </div>
      </div>
      </c:otherwise>
      </c:choose>
      </div>
    </div>
    <script>
var params = {
	fileInput: $("#fileImage").get(0),
	dragDrop: $("#fileDragArea").get(0),
	upButton: $("#fileSubmit").get(0),
	url: $("#uploadForm").attr("action"),
	filter: function(files) {
		var arrFiles = [];
		for (var i = 0, file; file = files[i]; i++) {
			if (file.type.indexOf("image") == 0) {
				if (file.size >= 512000) {
					alert('您这张"'+ file.name +'"图片大小过大，应小于500k');	
				} else {
					arrFiles.push(file);	
				}			
			} else {
				alert('文件"' + file.name + '"不是图片。');	
			}
		}
		return arrFiles;
	},
	onSelect: function(files) {
		var html = '', i = 0;
		html = $("#uploadHtml").val();
		$("#preview").html('<div class="upload_loading"></div>');
		var funAppendImage = function() {
			file = files[i];
			if (file) {
				var reader = new FileReader();
				reader.onload = function(e) {
					html = html + '<div id="uploadList_'+ i +'" class="upload_append_list"><p><strong>' + file.name + '</strong>'+ 
						'<a href="javascript:" class="upload_delete" title="删除" data-index="'+ i +'">删除</a><br />' +
						'<img id="uploadImage_' + i + '" src="' + e.target.result + '" class="upload_image" /></p>'+ 
						'<span id="uploadProgress_' + i + '" class="upload_progress"></span>' +
					'</div>';
					
					i++;
					funAppendImage();
				}
				reader.readAsDataURL(file);
			} else {
				$("#preview").html(html);
				if (html) {
					//删除方法
					$(".upload_delete").click(function() {
						ZXXFILE.funDeleteFile(files[parseInt($(this).attr("data-index"))]);
						return false;	
					});
					//提交按钮显示
					$("#fileSubmit").show();	
				} else {
					//提交按钮隐藏
					$("#fileSubmit").hide();	
				}
			}
		};
		funAppendImage();		
	},
	onDelete: function(file) {
		$("#uploadList_" + file.index).fadeOut();
	},
	onDragOver: function() {
		$(this).addClass("upload_drag_hover");
	},
	onDragLeave: function() {
		$(this).removeClass("upload_drag_hover");
	},
	onProgress: function(file) {
	$("#uploadProgress_" + file.index).show();
	},
	onSuccess: function(file, response) {
		alert("图片" + file.name +"上传成功!");
	},
	onFailure: function(file) {
		alert("图片" + file.name + "上传失败！");	
		$("#uploadImage_" + file.index).css("opacity", 0.2);
	},
	onComplete: function() {
		//提交按钮隐藏
		/* $("#fileSubmit").hide(); */
		//file控件value置空
		$("#fileImage").val("");
		$("#uploadInf").append("<p>当前图片全部上传完毕，可继续添加上传。</p>");
	}
};
ZXXFILE = $.extend(ZXXFILE, params);
ZXXFILE.init();
</script>
</body>
</html>
