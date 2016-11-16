// JavaScript Document
//添加
$(function(){
	//图片选择时,图片名称变换
	$(".homeIconPath").change(function(){
		$(this).parents(".uploader").find(".filename").val($(this).val());
		//$("#homeIconPathHidden").val($(this).val());
		//$("#edit_homeIconPathHidden").val($(this).val());
	});
	//未选择图片时,		
	$(".homeIconPath").each(function(){
		if($(this).val()==""){
			$(this).parents(".uploader").find(".filename").val("No file selected...");
		}
	});
	
	//图片选择时,图片名称变换
	$(".categoryIconPath").change(function(){
		$(this).parents(".uploader").find(".filename").val($(this).val());
		//$("#categoryIconPathHidden").val($(this).val());
		//$("#edit_categoryIconPathHidden").val($(this).val());
	});
	
	//未选择图片时,		
	$(".categoryIconPath").each(function(){
		if($(this).val()==""){
			$(this).parents(".uploader").find(".filename").val("No file selected...");
		}
	});
	
	//图片选择时,图片名称变换
	$(".detailIconPath").change(function(){
		$(this).parents(".uploader").find(".filename").val($(this).val());
		//$("#detailIconPathHidden").val($(this).val());
		//$("#edit_detailIconPathHidden").val($(this).val());
	});
	//未选择图片时,		
	$(".detailIconPath").each(function(){
		if($(this).val()==""){
			$(this).parents(".uploader").find(".filename").val("No file selected...");
		}
	});
	
	var oMark =document.getElementById('modelbox');
	var addUI =document.getElementById('box_addService');
	var editUI =document.getElementById('box_editService');
	
	$("#add_ser").click(function(){
		//var _index =$(".add_ser").index(this);  //获取当前点击按钮
		
		$("#serv_parent").val("-1");
		$("#serv_type").val("-1");
		$("#serv_name").val("");
		$("#serv_remarks").val("");
		$("#homeIconPathHidden").val("");
		$("#categoryIconPathHidden").val("");
		$("#detailIconPathHidden").val("");
		$(".file").val("");
		$(".filename").val("");
		
		//$("#regist_name_msg").html("");
		//$("#regist_image_msg").html("");
		//$("#regist_date_msg").html("");
		
		oMark.style.display ="block";
		addUI.style.display ="block";
		oMark.style.width = viewWidth() + 'px';
		oMark.style.height = documentHeight() + 'px';
		addUI.style.left = (viewWidth() - addUI.offsetWidth)/2 + 'px';
		
		var topDis = (viewHeight() - addUI.offsetHeight)/2-25;
		if (topDis <= 0) {
			topDis = 10;
		}
		addUI.style.top = topDis + 'px';	
		
		//image.clone(true).appendTo(".box_logoIn");
	
	})
	
	//重置按钮
	$("#addUI_reset").click(function(){
		$("#serv_parent").val("-1");
		$("#serv_type").val("-1");
		$("#serv_name").val("");
		$("#serv_remarks").val("");
		$("#homeIconPathHidden").val("");
		$("#categoryIconPathHidden").val("");
		$("#detailIconPathHidden").val("");
		$(".file").val("");
		$(".filename").val("");
	});
	
	$('.edit_service').delegate(this,'click',function(){
		var servId = $(this).attr("servId");
		var remarks = $(this).attr("remarks");
		var parent = $(this).attr("parent");
		var homeIcon = $(this).attr("homeIcon");
		var categoryIcon = $(this).attr("categoryIcon");
		var detailIcon = $(this).attr("detailIcon");
		var servName = $(this).attr("servName");
		var type = $(this).attr("type");
		
		$("#edit_serv_id").val(servId);
		$("#edit_serv_parent").val(parent);
		if(parent=="6"){
			$("#edit_serv_type").empty();
		}else{
			$("#edit_serv_type").val(type);
		
		}
		$("#edit_serv_name").val(servName);
		$("#edit_serv_remarks").val(remarks);
		$("#editHomeIconName").val(homeIcon);
		$("#editCategoryIconName").val(categoryIcon);
		$("#editDetailIconName").val(detailIcon);
		
		$("#edit_homeIconPathHidden").val(homeIcon);
		$("#edit_categoryIconPathHidden").val(categoryIcon);
		$("#edit_detailIconPathHidden").val(detailIcon);
		
		oMark.style.display ="block";
		editUI.style.display ="block";
		oMark.style.width = viewWidth() + 'px';
		oMark.style.height = documentHeight() + 'px';
		editUI.style.left = (viewWidth() - editUI.offsetWidth)/2 + 'px';
		editUI.style.top = (viewHeight() - editUI.offsetHeight)/2-25 + 'px';	
	
	});
	
	//重置按钮
	$("#editUI_reset").click(function(){
		$("#edit_serv_parent").val("-1");
		$("#edit_serv_type").val("-1");
		$("#edit_serv_name").val("");
		$("#edit_serv_remarks").val("");
		$("#edit_homeIconPathHidden").val("");
		$("#edit_categoryIconPathHidden").val("");
		$("#edit_detailIconPathHidden").val("");
		$(".file").val("");
		$(".filename").val("");
	});
	
     
		//关闭按钮
		function toClose(){
			var oClose = document.getElementById('close');
			oClose.onclick = function(){
				oMark.style.display ="none";
				addUI.style.display ="none";
				//$(".box_logoIn").empty()
				
			};
		}
		toClose();
		
		//关闭按钮
		function toClose2(){
			var oClose = document.getElementById('close2');
			oClose.onclick = function(){
				oMark.style.display ="none";
				editUI.style.display ="none";
				//$(".box_logoIn").empty()
				
			};
		}
		toClose2();
		
		window.onscroll = window.onresize = function(){
		
		var oDiv = document.getElementById('box_logoIn');
		if(oDiv){
			oDiv.style.left = (viewWidth() - oDiv.offsetWidth)/2 + 'px';
			oDiv.style.top = (viewHeight() - oDiv.offsetHeight)/2-25 + 'px';
		}
	
	}
})

//获取浏览器可视区的宽度和高度
function viewWidth(){
	return document.documentElement.clientWidth;
}
function viewHeight(){
	return document.documentElement.clientHeight;
}
function documentHeight(){
	return Math.max(document.documentElement.scrollHeight || document.body.scrollHeight,document.documentElement.clientHeight);
}
function scrollY(){
	return document.documentElement.scrollTop || document.body.scrollTop;
}

