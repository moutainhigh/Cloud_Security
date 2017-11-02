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
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.form.js"></script>
<!--  <link href="${ctx}/source/serviceManage/servDetails/styles.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/serviceManage/resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/source/serviceManage/data/styles.css" type="text/css" rel="stylesheet"/>
-->
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>
<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/fileupload.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/zxxFile.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/adminJs/servManage.js"></script>
<style type="text/css">
.regist_input {
    width: 300px;
}
.regist_prompt{
	width: 450px;
	color:red;
}
.checkbox_lable{
	padding-right: 50px;
	padding-left: 20px;
}
</style>
<script type="text/javascript">
$(function(){
//保存上传图片的名字
$("#servDetailHidden").val('');
$("#uploadSuccessHidden").val('');
//编辑详情时，编辑前存的图片
$("#resServDetailHidden").val('');
$("#preview").empty();
var iconList = new Array();

var parent = '${parent}';
var type = '${serviceDetail.servType}';	//type: 0:单次和长期,1:长期 2:单次
if(parent != '6' && (type== '0' || type =='1') ){	
	$("#u12").show();	//服务频率标题 显示
	$("#u15").show();	//服务频率     显示
}

//长期复选框
$("#u10_input").click(function(){
	var chk = document.getElementById('u10_input');
	if(chk.checked){ 
		$("#u12").show();
		$("#u15").show();
	}else{ 
		$("#u12").hide();	
		$("#u15").hide();
	}
});
//添加行按钮
$("#u27_input").click(function(){
	       // +'<input class="regist_txt"  id="u31_input" name="scanType" type="text" value=""/>'
	var html = '<div id="u31" class="ax_text_field positionr">'
	        +'<select class="regist_sel" id="u31_input" name="scanType" onchange="checkScanType(this)">'
				+'<option value="-1" selected="selected">请选择</option>'
				+'<option value="1">10分钟</option>'
				+'<option value="2">30分钟</option>'
				+'<option value="3">1小时</option>'
				+'<option value="4">1天</option>'
				+'<option value="5">每周</option>'
				+'<option value="6">每月</option>'
			+'</select>'
	        +'</div>';
	$("#u17").append(html);
});
//删除行按钮
$("#u30_input").click(function(){
	$("select[name='scanType']:last").parent().remove();
});

//重置按钮
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

//上传详情图片/填写API信息
$("#u34_input").bind('change',function(){
	if($(this).val() == '0'){
		$("#u39").hide();
		$("#u19").show();
		$("#u28").show();
		$("#u29").show();
	} else {
		$("#u39").show();
		$("#u19").hide();
		$("#u28").hide();
		$("#u29").hide();
	}
});

var serviceDetail = '${serviceDetail}';
var webPath = '${webRootPath}';
if(serviceDetail != null && serviceDetail != ''){
	var detailFlag = '${serviceDetail.detailFlag}';
	if(detailFlag != null && detailFlag != '' && detailFlag == '0'){
			var detailIcon = '${detailIcon}';
		if(detailIcon != null && detailIcon != ''){
			var detailIconArray = detailIcon.split(";");
			for(var i=0; i<detailIconArray.length; i++){
				if (detailIconArray[i]==null || detailIconArray[i]=="" ) {
					continue;
				}
				var imgsrc = webPath + 'source/images/serviceDetail/'+ detailIconArray[i];
				//var imgsrc = 'http://127.0.0.1:8080/csp/source/images/serviceDetail/' + detailIconArray[i];
				var html = '';
				html = html + '<div id="resUploadList_'+ i +'" class="upload_append_list"><p><strong>' + detailIconArray[i] + '</strong>'+ 
					'<a href="javascript:" class="upload_delete" title="删除" data-index="'+ i +'">删除</a><br />' +
					'<img id="resUploadImage_' + i + '" src="' + imgsrc + '" class="upload_image" />'+ 
					'<input type="hidden" class="previewUploadName" value="'+detailIconArray[i]+'"/></p>'+
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
  <div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
	<div class="main_wrap">
	<div style="padding-top: 20px;padding-left: 30px;">
	<a href="javascript:void(0)" class="ope_a" onclick="goBack()">&lt;&lt;返回服务列表</a>
	</div>
    <div id="base" class="main_center" style="padding-top: 30px;padding-bottom: 20px;">
	  <input type="hidden" id="uploadHtml" value=""/>
	  <input type="hidden" id="uploadSuccessHidden" value=""/>
	  <input type="hidden" id="servDetailHidden" value=""/>
	  <input type="hidden" id="resServDetailHidden" value=""/>
	  <table>
	  		<tr class="register_tr">
	  			<td class="regist_title" >服务名称：</td>
	  			<td class="regist_input" style="color:#888">
	  				${servName }
	  			</td>
	  		</tr>
	  		<tr class="register_tr">
	  			<td class="regist_title" id="u1">价格标题：</td>
	  			<td class="regist_input" id="u2">
	  				<input class="regist_txt"  id="u2_input" type="text" value="${serviceDetail.priceTitle}"/>
	  			</td>
	  		</tr>
	  		<tr class="register_tr">
	  			<td class="regist_title" id="u4">选类型标题：</td>
	  			<td class="regist_input" id="u5">
	  				<input class="regist_txt" id="u5_input" type="text" value="${serviceDetail.typeTitle }"/>
	  			</td>
	  		</tr>
	  		<tr class="register_tr">
	  			<td class="regist_title" id="u7">选类型：</td>
	  			<td class="regist_input" id="u8">
		  			<label for="u8_input" class="checkbox_lable" >
			          	<c:choose>
				          	<c:when test="${serviceDetail.servType == 0 or serviceDetail.servType ==2}">
				          		<input id="u8_input" type="checkbox" name="servType" value="2" checked/>  
				          	</c:when>
				          	<c:otherwise>
				          		<input id="u8_input" type="checkbox" name="servType" value="2"/> 
				          	</c:otherwise>
			          	</c:choose>         
			            <span>单次</span>      
			        </label>
			        
			        <label for="u10_input" class="checkbox_lable" >
			            <c:choose>
				          	<c:when test="${serviceDetail.servType == 0 or serviceDetail.servType ==1}">
				          		<input id="u10_input" type="checkbox" name="servType" value="1" checked/>
				          	</c:when>
				          	<c:otherwise>
				          		<input id="u10_input" type="checkbox" name="servType" value="1"/>
				          	</c:otherwise>
			          	</c:choose>    
			            
			            <span>长期</span>           
        			</label>
	  			</td>
	  			<td class="regist_prompt">
	  				<span>备注：如果当前服务只有单次或者长期，详情页面不显示这一行</span>
	  			</td>
	  		</tr>
	  		<tr class="register_tr" id="u12" style="display:none;">
	  			<td class="regist_title" id="u13">服务频率标题：</td>
	  			<td id="u14">
	  				<input class="regist_txt" id="u14_input" type="text" value="${serviceDetail.ratesTitle }"/>
	  			</td>
	  			<td></td>
	  		</tr>
	  		<tr class="register_tr" id="u15" style="display:none;">
	  			<td class="regist_title" id="u16" style="vertical-align:top;">服务频率：</td>
	  			<td class="regist_input" id="u17">
	  				<c:choose>
			          	<c:when test="${scanTypeList==null or scanTypeList.size() == 0}">
			          		<div id="u31" class="ax_text_field positionr">
						        <!-- <input class="regist_txt" id="u31_input" name="scanType" type="text" value=""/> -->
						        <select class="regist_sel" id="u31_input" name="scanType" onchange="checkScanType(this)">
			                		<option value="-1" selected="selected">请选择</option>
				                	<option value="1">10分钟</option>
				                	<option value="2">30分钟</option>
				                	<option value="3">1小时</option>
				                	<option value="4">1天</option>
				                	<option value="5">每周</option>
				                	<option value="6">每月</option>
				                </select>
						    </div>
			          	</c:when>
			          	<c:otherwise>
				          <c:forEach var="scanType" items="${scanTypeList }" varStatus="status">
						      <div id="u31" class="ax_text_field positionr">
						        <!-- <input class="regist_txt" id="u31_input" name="scanType" type="text" value="${scanType.scan_name }"/>
						      	  -->
						      	  <select class="regist_sel" id="u31_input" name="scanType" onchange="checkScanType(this)">
			                		<option value="-1">请选择</option>
			                		<c:if test="${scanType.scan_type==1}">
				                		<option value="1" selected="selected">10分钟</option>
				                		<option value="2">30分钟</option>
				                		<option value="3">1小时</option>
				                		<option value="4">1天</option>
				                		<option value="5">每周</option>
				                		<option value="6">每月</option>
			                		</c:if>
			                		<c:if test="${scanType.scan_type==2}">
				                		<option value="1">10分钟</option>
				                		<option value="2" selected="selected">30分钟</option>
				                		<option value="3">1小时</option>
				                		<option value="4">1天</option>
				                		<option value="5">每周</option>
				                		<option value="6">每月</option>
			                		</c:if>
			                		<c:if test="${scanType.scan_type==3}">
				                		<option value="1">10分钟</option>
				                		<option value="2">30分钟</option>
				                		<option value="3" selected="selected">1小时</option>
				                		<option value="4">1天</option>
				                		<option value="5">每周</option>
				                		<option value="6">每月</option>
			                		</c:if>
			                		<c:if test="${scanType.scan_type==4}">
				                		<option value="1">10分钟</option>
				                		<option value="2">30分钟</option>
				                		<option value="3">1小时</option>
				                		<option value="4" selected="selected">1天</option>
				                		<option value="5">每周</option>
				                		<option value="6">每月</option>
			                		</c:if>
			                		<c:if test="${scanType.scan_type==5}">
				                		<option value="1">10分钟</option>
				                		<option value="2">30分钟</option>
				                		<option value="3">1小时</option>
				                		<option value="4">1天</option>
				                		<option value="5" selected="selected">每周</option>
				                		<option value="6">每月</option>
			                		</c:if>
			                		<c:if test="${scanType.scan_type==6}">
				                		<option value="1">10分钟</option>
				                		<option value="2">30分钟</option>
				                		<option value="3">1小时</option>
				                		<option value="4">1天</option>
				                		<option value="5">每周</option>
				                		<option value="6" selected="selected">每月</option>
			                		</c:if>
			                	</select>
						      </div>
					      </c:forEach>
			          	</c:otherwise>
			        </c:choose>
	  			</td>
	  			<td class="regist_prompt" style="vertical-align:top;">
		  			<div id="u27" class="ax_html_button positionr" style="float:left;">
			        	<input id="u27_input" type="submit" value="添加行"/>
			        </div>
			        <div id="u30" class="ax_html_button positionr" style="float:left;padding-left:10px;">
			        	<input id="u30_input" type="submit" value="删除行"/>
			      	</div>
			      	<div id="u26" class="text positionr" style="float:left;padding-left:10px;">
			          <p><span>备注：根据配置服务频率功能显示</span></p>
			        </div>
	  			</td>
	  		</tr>
	  		<tr class="register_tr" style="display:none">
	  			<td class="regist_title" id="u33">详细信息选项：</td>
	  			<td class="regist_input" id="u34">
	  				<c:choose>
			        	<c:when test="${serviceDetail.detailFlag == '1' }">
			        		<select class="regist_sel" id="u34_input" >
				        		<option value="0">上传详情图片</option>
				        		<option value="1" selected>填写API信息</option>
				        	</select>
			        	</c:when>
			        	<c:otherwise>
				        	<select class="regist_sel" id="u34_input" >
				        		<option value="0" selected>上传详情图片</option>
				        		<option value="1">填写API信息</option>
				        	</select>
			        	</c:otherwise>
        			</c:choose>
	  			</td>
	  			<td></td>
	  		</tr>
	  		<tr class="register_tr">
	  			<td class="regist_title" id="u18" style="vertical-align:top;">服务详细信息：</td>
	  			<td class="regist_input" colspan="2">
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
						              </div>
						         </form>
					      	</div>
					      	<div id="u39" class="ax_text_field positionr">
	      	    			<textarea class="regist_txt" id="u39_input">${serviceDetail.detailIcon }</textarea>
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
					              </div>
					         </form>
					      </div>
						  <div id="u39" class="ax_text_field positionr" style="display:none;">
	      	    			<textarea class="regist_txt" id="u39_input">${serviceDetail.detailIcon }</textarea>
	      	    		  </div>
					      </c:otherwise>
					      </c:choose>
	  			</td>
	  			<c:if test="${serviceDetail.detailFlag == '1' }">
	  				<td class="regist_prompt" id="u29" style="width:120px;display:none;">上传多张图片</td>
	  			</c:if>
	  			<c:if test="${serviceDetail.detailFlag != '1' }">
	  				<td class="regist_prompt" id="u29" style="width:120px;" >上传多张图片</td>
	  			</c:if>
	  		</tr>
	  </table>
	  <div id="uploadInf" class="upload_inf" style="text-align: center;">
		<c:choose>
			<c:when test="${serviceDetail == null or serviceDetail == ''}">
				<input class="ser_btn" id="u21_input" type="button" value="保存" onclick="saveDetails('${serviceId}','${parent}',true);"/>
			</c:when>
			<c:otherwise>
				<input class="ser_btn" id="u21_input" type="button" value="保存" onclick="saveDetails('${serviceId}','${parent}',false);"/>
			</c:otherwise>
		</c:choose>     	
		<input class="ser_btn u22_input" type="reset" value="重置"/>
	</div>
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
				//if (file.size >= 512000) {
					//alert('您这张"'+ file.name +'"图片大小过大，应小于500k');	
				//} else {
					arrFiles.push(file);	
				//}			
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
						'<input type="hidden" class="previewUploadName" id="uploadName_'+ i +'" value=""/>'+
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
					$("#fileSubmit").attr("disabled", false);	
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
		
		var fileName=$("#uploadName_" + file.index).val();
		var iconNames = $("#servDetailHidden").val();
		iconNames = iconNames.replace(fileName+";","");
		iconNames = iconNames.replace(fileName, "");
		$("#servDetailHidden").val(iconNames);
		
		fileName = file.name;
		var names = $("#uploadSuccessHidden").val();
		names = names.replace(fileName+";", "");
		names = names.replace(fileName, "");
		$("#uploadSuccessHidden").val(names);
	},
	onDragOver: function() {
		$(this).addClass("upload_drag_hover");
	},
	onDragLeave: function() {
		$(this).removeClass("upload_drag_hover");
	},
	onProgress: function(file) {
		$("#uploadProgress_" + file.index).show();
		$("#fileSubmit").attr("disabled", true);
	},
	onSuccess: function(file, response) {
		alert("图片" + file.name +"上传成功!");
		$("#uploadProgress_" + file.index).hide();
		$("#uploadName_" + file.index).val(response.filePath);
		
	},
	onFailure: function(file) {
		alert("图片" + file.name + "上传失败！");	
		$("#uploadImage_" + file.index).css("opacity", 0.2);
	},
	onComplete: function() {
		//提交按钮隐藏
		$("#fileSubmit").hide();
		//file控件value置空
		$("#fileImage").val("");
		//$("#uploadInf").append("<p>当前图片全部上传完毕，可继续添加上传。</p>");
	}
};
ZXXFILE = $.extend(ZXXFILE, params);
ZXXFILE.init();
</script>
<!--============bottom============-->
	<!-- footer start -->
	<c:import url="/footer.html"></c:import>
	<!-- footer end -->
</div>
</body>
</html>
