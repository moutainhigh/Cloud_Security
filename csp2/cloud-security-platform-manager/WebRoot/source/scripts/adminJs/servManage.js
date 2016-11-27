
//添加时改变一级分类
function changeParent(){
	var parent = $("#serv_parent").val();
	if(parent=="6"){
		$("#serv_type").empty();
	}else{
		$("#serv_type").empty();
		//var temp = "<option selected='selected' value='-1'>请选择</option><option value='1'>网站安全监测及预警服务</option><option value='2'>网站安全防护及加固服务</option>";
		var temp = "<option selected='selected' value='1'>网站安全监测及预警服务</option>";
		$("#serv_type").append(temp);
	}
}
//修改时改变一级分类
function changeEditParent(){
	var parent = $("#edit_serv_parent").val();
	if(parent=="6"){
		$("#edit_serv_type").empty();
	}else{
		$("#edit_serv_type").empty();
		//var temp = "<option selected='selected' value='-1'>请选择</option><option value='1'>网站安全监测及预警服务</option><option value='2'>网站安全防护及加固服务</option>";
		var temp = "<option selected='selected' value='1'>网站安全监测及预警服务</option>";
		$("#edit_serv_type").append(temp);
	}
}

//查询时改变一级分类
function changeParentForSearch(){
	var parent = $("#u5_input").val();
	if(parent=="6"){
		$("#u43_input").empty();
	}else{
		$("#u43_input").empty();
		var temp = "<option selected='selected' value='-1'>请选择</option><option value='1'>网站安全监测及预警服务</option><option value='2'>网站安全防护及加固服务</option>";
		$("#u43_input").append(temp);
	}
}

//上传首页服务图标
function saveHomeIcon(){
	var filename = $("#homeIconName").val();
    var fileFormat = filename.substring(filename.lastIndexOf("."));

    if(filename=="") {
    	alert("未选择图片!");
    	return;
    }else if(!/.jpg|.JPG|.bmp|.BMP|.PNG|.png|.PDF|.pdf/.test(fileFormat)){
    	alert("格式不正确!");
    	return;
    }
	var options = {
		url:'uploadIcon.html',
		//beforeSubmit:showRequest,
		success: function(data) {
				
			if(data.success){
	    		alert("上传首页服务图标成功!");
	    		$("#homeIconPathHidden").val(data.filePath);
	    	}else{
	    		alert("上传首页服务图标失败!");
	    	}
		  },
	      error:function(data){
	      	alert("上传首页服务图标失败!");
	      }
	}
	// 将options传给ajaxForm
	$('#homeIconForm').ajaxSubmit(options);

}

//上传二级服务图标
function saveCategoryIcon(){
	var filename = $("#categoryIconName").val();
    var fileFormat = filename.substring(filename.lastIndexOf("."));

    if(filename=="") {
    	alert("未选择图片!");
    	return;
    }else if(!/.jpg|.JPG|.bmp|.BMP|.PNG|.png|.PDF|.pdf/.test(fileFormat)){
    	alert("格式不正确!");
    	return;
    }
	var options = {
		url:'uploadIcon.html',
		//beforeSubmit:showRequest,
		success: function(data) {
				
			if(data.success){
	    		alert("上传二级服务图标成功!");
	    		$("#categoryIconPathHidden").val(data.filePath);
	    	}else{
	    		alert("上传二级服务图标失败!");
	    	}
		  },
	      error:function(data){
	      	alert("上传二级服务图标失败!");
	      }
	}
	// 将options传给ajaxForm
	$('#categoryIconForm').ajaxSubmit(options);
}

//上传详情服务图标
function saveDetailIcon(){
	var filename = $("#detailIconName").val();
    var fileFormat = filename.substring(filename.lastIndexOf("."));

    if(filename=="") {
    	alert("未选择图片!");
    	return;
    }else if(!/.jpg|.JPG|.bmp|.BMP|.PNG|.png|.PDF|.pdf/.test(fileFormat)){
    	alert("格式不正确!");
    	return;
    }
    
	var options = {
		url:'uploadIcon.html',
		//beforeSubmit:showRequest,
		success: function(data) {
				
			if(data.success){
	    		alert("上传详情服务图标成功!");
	    		$("#detailIconPathHidden").val(data.filePath);
	    	}else{
	    		alert("上传详情服务图标失败!");
	    	}
		  },
	      error:function(data){
	      	alert("上传详情服务图标失败!");
	      }
	}
	// 将options传给ajaxForm
	$('#detailIconForm').ajaxSubmit(options);
}

//修改首页服务图标
function editHomeIcon(){
	var filename = $("#editHomeIconName").val();
    var fileFormat = filename.substring(filename.lastIndexOf("."));

    if(filename=="") {
    	alert("未选择图片!");
    	return;
    }else if(!/.jpg|.JPG|.bmp|.BMP|.PNG|.png|.PDF|.pdf/.test(fileFormat)){
    	alert("格式不正确!");
    	return;
    }
    
	var options = {
		url:'uploadIcon.html',
		//beforeSubmit:showRequest,
		success: function(data) {
				
			if(data.success){
	    		alert("上传首页服务图标成功!");
	    		$("#edit_homeIconPathHidden").val(data.filePath);
	    	}else{
	    		alert("上传首页服务图标失败!");
	    	}
		  },
	      error:function(data){
	      	alert("上传服首页务图标失败!");
	      }
	}
	// 将options传给ajaxForm
	$('#homeIconForm1').ajaxSubmit(options);

}

//修改二级页服务图标
function editCategoryIcon(){
	var filename = $("#editCategoryIconName").val();
    var fileFormat = filename.substring(filename.lastIndexOf("."));

    if(filename=="") {
    	alert("未选择图片!");
    	return;
    }else if(!/.jpg|.JPG|.bmp|.BMP|.PNG|.png|.PDF|.pdf/.test(fileFormat)){
    	alert("格式不正确!");
    	return;
    }
    
	var options = {
		url:'uploadIcon.html',
		//beforeSubmit:showRequest,
		success: function(data) {
				
			if(data.success){
	    		alert("上传二级服务图标成功!");
	    		$("#edit_categoryIconPathHidden").val(data.filePath);
	    	}else{
	    		alert("上传二级服务图标失败!");
	    	}
		  },
	      error:function(data){
	      	alert("上传二级服务图标失败!");
	      }
	}
	// 将options传给ajaxForm
	$('#categoryIconForm1').ajaxSubmit(options);

}

//修改详情页服务图标
function editDetailIcon(){
	var filename = $("#editDetailIconName").val();
    var fileFormat = filename.substring(filename.lastIndexOf("."));
    if(filename=="") {
    	alert("未选择图片!");
    	return;
    }else if(!/.jpg|.JPG|.bmp|.BMP|.PNG|.png|.PDF|.pdf/.test(fileFormat)){
    	alert("格式不正确!");
    	return;
    }
    
	var options = {
		url:'uploadIcon.html',
		//beforeSubmit:showRequest,
		success: function(data) {
				
			if(data.success){
	    		alert("上传详情服务图标成功!");
	    		$("#edit_detailIconPathHidden").val(data.filePath);
	    	}else{
	    		alert("上传服务图标失败!");
	    	}
		  },
	      error:function(data){
	      	alert("上传详情服务图标失败!");
	      }
	}
	// 将options传给ajaxForm
	$('#detailIconForm1').ajaxSubmit(options);

}

//保存服务
function saveServ(){
	var parent = $("#serv_parent").val();
	var type = $("#serv_type").val();
	var name = $("#serv_name").val();
    var remarks = $("#serv_remarks").val();
    var homeIcon = $("#homeIconPathHidden").val();
    var categoryIcon = $("#categoryIconPathHidden").val();
    var detailIcon = $("#detailIconPathHidden").val();

    if(parent == -1){
	    alert("请选择一级分类!");
	    return;
    }else if(name==null||name==""){
    	alert("请填写服务名称!");
    	return;
    }else if (remarks==null || remarks=="") {
    	alert("请填写服务描述!");
    	return;
    }else if(homeIcon==null||homeIcon==""){
    	alert("请上传首页服务图标!");
    	return;
    }else if(categoryIcon==null||categoryIcon==""){
    	alert("请上传二级服务图标!");
    	return;
    }else if(detailIcon==null||detailIcon==""){
    	alert("请上传详情服务图标!");
    	return;
    }else{
    	 $.ajax({
    	        type: "POST",
    	        url: "addServ.html",
    	        data: {"parent":parent,"type":type,"name":name,"remarks":remarks,"homeIcon":homeIcon,"categoryIcon":categoryIcon,"detailIcon":detailIcon},
    	        dataType:"json",
    	        success: function(data){
    	        	if(data.success){
    	        		alert("服务添加成功!");
    	        		init();
    	        	}else{
    	        		alert("服务添加失败!");
    	        	}
    	        },
    	        error:function(data){
    	        	alert("服务添加失败!");
    	        }
    	     });
    }
   
}

function searchService(){
	//提交表单
    $("#searchForm").submit();
}

function editServ(Obj){
	var oMark =document.getElementById('modelbox');
	var addUI =document.getElementById('box_addService');
	var editUI =document.getElementById('box_editService');
	
	var servId = $(Obj).attr("servId");
	var remarks = $(Obj).attr("remarks");
	var parent = $(Obj).attr("parent");
	var homeIcon = $(Obj).attr("homeIcon");
	var categoryIcon = $(Obj).attr("categoryIcon");
	var detailIcon = $(Obj).attr("detailIcon");
	var servName = $(Obj).attr("servName");
	var type = $(Obj).attr("type");
	
	$("#edit_serv_id").val(servId);
	$("#edit_serv_parent").val(parent);
	$("#edit_serv_type").val(type);
	$("#edit_serv_name").val(servName);
	$("#edit_serv_remarks").val(remarks);
	$("#homeIconName").val(homeIcon);
	$("#categoryIconName").val(categoryIcon);
	$("#detailIconName").val(detailIcon);
	
	$("#edit_homeIconPathHidden").val(homeIcon);
	$("#edit_categoryIconPathHidden").val(categoryIcon);
	$("#edit_detailIconPathHidden").val(detailIcon);
	
	oMark.style.display ="block";
	editUI.style.display ="block";
	oMark.style.width = viewWidth() + 'px';
	oMark.style.height = documentHeight() + 'px';
	editUI.style.left = (viewWidth() - editUI.offsetWidth)/2 + 'px';
	editUI.style.top = (viewHeight() - editUI.offsetHeight)/2-25 + 'px';	

}
//删除服务
function delServ(Obj){
	if (window.confirm("确实要删除该服务吗?")==false) {
		return;
	}
	var servid = $(Obj).attr("servid");
	var parentC = $(Obj).attr("parentC");
	 $.ajax({
	        type: "POST",
	        url: "delServ.html",
	        data: {"parent":parentC,"servId":servid},
	        dataType:"json",
	        success: function(data){
	        	if(data.success){
	        		alert("删除服务成功!");
	        		init();
	        	}else{
	        		alert("删除服务失败!");
	        	}
	        },
	        error:function(){
	        	alert("err");
	        }
	 });
}

//修改服务
/*function updateServ(Obj){
	var servid = $(Obj).attr("servid");
	var parentC = $(Obj).attr("parentC");
	 $.ajax({
	        type: "POST",
	        url: "updateServ.html",
	        data: {"parent":parentC,"servId":servid},
	        dataType:"json",
	        success: function(data){
	        	if(data.success){
	        		alert("修改服务成功!");
	        	}else{
	        		alert("修改服务失败!");
	        	}
	        },
	        error:function(){
	        	alert("err");
	        }
	 });
}*/

//修改服务
function updateServ(){
	var servId = $("#edit_serv_id").val();
	var parent = $("#edit_serv_parent").val();
	var type = $("#edit_serv_type").val();
	var name = $("#edit_serv_name").val();
    var remarks = $("#edit_serv_remarks").val();
    var homeIcon = $("#edit_homeIconPathHidden").val();
	var categoryIcon= $("#edit_categoryIconPathHidden").val();
	var detailIcon = $("#edit_detailIconPathHidden").val();
	
    if(parent == -1){
	    alert("请选择一级分类!");
	    return;
    }else if(name==null||name==""){
    	alert("请填写服务名称!");
    	return;
    }else if (remarks==null || remarks=="") {
    	alert("请填写服务描述!");
    	return;
    }else if(homeIcon==null||homeIcon==""){
    	alert("请上传首页服务图标!");
    	return;
    }else if(categoryIcon==null||categoryIcon==""){
    	alert("请上传二级服务图标!");
    	return;
    }else if(detailIcon==null||detailIcon==""){
    	alert("请上传详情服务图标!");
    	return;
    }else{
    	 $.ajax({
    	        type: "POST",
    	        url: "updateServ.html",
    	        data: {"servId":servId,"parent":parent,"type":type,"name":name,"remarks":remarks,"homeIcon":homeIcon,"categoryIcon":categoryIcon,"detailIcon":detailIcon},
    	        dataType:"json",
    	        success: function(data){
    	        	if(data.success){
    	        		alert("服务修改成功!");
    	        		init();
    	        	}else{
    	        		alert("服务修改失败!");
    	        	}
    	        },
    	        error:function(data){
    	        	alert("服务修改失败!");
    	        }
    	     });
    }
}

//设置详情
//flag:true 创建  false:编辑
function saveDetails(serviceId,parent,flag){
	//价格标题
	var priceTitle = $.trim($("#u2_input").val());
	//选类型标题
	var typeTitle = $.trim($("#u5_input").val());
	//选类型(0:单次和长期 1:单次2:长期)
	var servType = '';
	var length = $("input[name='servType']:checked").length;
	if(length > 1){
		servType = '0';
	} else {
		servType = $("input[name='servType']:checked").val();
	}
	//服务频率标题
	var servRatesTitle = $.trim($("#u14_input").val());
	//服务频率
	/*var scanType = [];
	$("input[name='scanType']").each(function(){
		if($.trim($(this).val()) != ""){
			scanType.push($.trim($(this).val()));
		}
	});*/

	var scanType = "";
	$("select[name='scanType']").each(function(){
		var scan = $.trim($(this).val());
		if(scan != ""&& scanType.indexOf(scan)== -1){
			scanType= scanType+","+scan;
		}
	});
	if (scanType!="") {
		scanType = scanType.slice(1);
	}
	//详细信息选项
	var servSelected = $("#u34_input").val();
	//服务详细信息
	var servIcon = '';
	if(servSelected == 0){
		//if(flag){
		//	servIcon = $("#servDetailHidden").val();
		//} else {
			$(".previewUploadName").each(function(index){ 
				var fileName = $(this).val();
				if (fileName!= null && fileName != '') {
					servIcon = servIcon + ";" + fileName;
				}
			
			});
		//}
		
	} else {
		servIcon = $.trim($("#u39_input").val());
	}
	
	/*if(flag){*/
		if(priceTitle == null || priceTitle == ""){
			alert("请填写价格标题！");
			return;
		} else if(typeTitle == null || typeTitle == ""){
			alert("请填写选类型标题！");
			return;
		} else if(servType == null || servType == "" || servType == undefined){
			alert("请至少选择一种选类型！");
			return;
		} else if(servIcon == null || servIcon == "" || servIcon == ";"){
			alert("服务详细信息不能为空，请至少上传一张图片或填写api信息！");
			return;
		}
		if(parent != 6 && (servType ==0 || servType == 1)){
			if(servRatesTitle == null || servRatesTitle == ""){
				alert("请填写服务频率标题！");
				return;
			} else if(scanType== ""){
				alert("请至少填写一个服务频率！");
				return;
			}
		}
	/*}*/
	$.ajax({
        type: "POST",
        url: "saveServDetails.html",
        data: {"serviceId":serviceId,"parent":parent,"priceTitle":priceTitle,"typeTitle":typeTitle,"servType":servType,"servRatesTitle":servRatesTitle,"scanType":scanType,"servIcon":servIcon,"servIconFlag":servSelected},
        dataType:"json",
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        traditional: true, 
        success: function(data){
        	if(data.success){
        		alert("服务详情设置成功!");
        		init();
        	}else{
        		alert("服务详情设置失败!");
        	}
        },
        error:function(data){
        	alert("服务详情设置失败!");
        }
     });
}
/**
 * 添加或删除成功后，手动刷新页面
 */
function init(){
	var currentAddr = location.href;
	location.href = currentAddr.substring(0,currentAddr.lastIndexOf("/")) + "/getServiceList.html";
}

function goBack(){
 history.go(-1);
}

function checkScanType(Obj){
	var scanType = "";
	var scan = $(Obj).val();
	$("select[name='scanType']").each(function(){
		if($.trim($(this).val()) != ""){
			scanType= scanType+","+$.trim($(this).val());
		}
	});
	scanType = scanType.replace(scan,"");
	if (scanType.indexOf(scan) != -1){
		$(Obj).val("-1");
		alert("服务频率不能重复！");
	}
	
}
