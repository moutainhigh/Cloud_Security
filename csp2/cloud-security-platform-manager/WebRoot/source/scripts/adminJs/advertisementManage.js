//提交
function add(){
	var name=$("#regist_name").val();
	var image=$("#regist_image").val();
	var startDate=$('#regist_startDate').val();
    var endDate=$('#regist_endDate').val();
    var submitFlg = true;
    //验证广告名称
    if(name=="") {
    	$("#regist_name_msg").html("广告名称不能为空!");
    	submitFlg = false;
    }else{
    	$("#regist_name_msg").html("");
    }
    //验证广告图片
    var filename = $("#regist_images").val();
    var fileFormat = filename.substring(filename.lastIndexOf("."));

    if(filename=="") {
    	$("#regist_image_msg").html("图片不能为空!");
    	submitFlg = false;
    }else if(/.jpg|.JPG|.bmp|.BMP|.PNG|.png|.PDF|.pdf/.test(fileFormat)){
    	$("#regist_image_msg").html("");
    }else{
    	$("#regist_image_msg").html("格式不正确!");
    	submitFlg = false;
    }
    //if(image=="") {
    	//$("#regist_image_msg").html("广告图片不能为空!");
    	//submitFlg = false;
    //}else {
    //	$("#regist_image_msg").html("");
    //}
    
    if(startDate=="" || endDate=="") {
    	$("#regist_date_msg").html("有效期时间不能为空!");
    	submitFlg = false;
    }else if(startDate>=endDate){
        $("#regist_date_msg").html("开始时间不能大于结束时间!");
        submitFlg = false;
    }else{
    	 $("#regist_date_msg").html("");
    }
    
	
	if(!submitFlg) {
		return;
	}
	
	$("#form_advertisement").ajaxSubmit(function (responseResult) {
	alert(responseResult.success);
		if(responseResult.success == false) {
			alert("添加广告失败!");
		}
		window.location.href = "adminAdvertisementManageUI.html";
	});
	
	
	//$("#form_advertisement").submit();
	
	
}

//删除
function deleteAdvertisement(id){
	if (window.confirm("确实要删除吗?")==false) {
		return;
	}
	
	$.ajax({
		type:"POST",
		dataType:"json",
		data:{"adId":id},
		url:"adminAdvertisementDelete.html",
		success: function(data){
			if(data.success == false) {
				alert("添加广告失败!");
			}
			window.location.href = "adminAdvertisementManageUI.html";
		},
		error:function(data){
			alert("系统异常!请稍后再试");
		}
	});
	
    //var advertisementId = id;
    //if (window.confirm("确实要删除吗?")==true) {
    //    window.location.href="/cloud-security-platform-manager/adminAdvertisementDelete.html?id="+advertisementId;
    //} else {
    //    return;
    //}
}

//检查有限期的开始时间和结束时间
function checkDate() {
	var startDate=$('#regist_startDate').val();
    var endDate=$('#regist_endDate').val();
    //都不为空时，判断时间大小
    if(startDate!=""&&startDate!=null&&endDate!=""&&endDate!=null){
    	if(startDate>=endDate){
         	alert("开始时间不能大于结束时间!");
         	return;
         }
	}
}