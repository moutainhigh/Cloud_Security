//提交
function add(){
	var name=$("#regist_name").val();
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

    //验证广告分类
    var adType = $("#regist_type").val();
    if(adType=="" ||adType==-1){
    	$("#regist_type_msg").html("广告分类不能为空!");
    	submitFlg = false;
    }else {
    	$("#regist_type_msg").html("");
    }
    
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

		if(responseResult.success == false) {
			alert("添加广告失败!");
		}
		
		toAdManagerUI(adType);
		//window.location.href = "adminAdvertisementManageUI.html";
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
				alert("删除广告失败!");
			}
			//window.location.href = "adminAdvertisementManageUI.html";
			var adType = $("#adType").val();
			toAdManagerUI(adType);
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

function toAdManagerUI(adType){
	if( adType == null) {
		adType = $("#select_type").val();
	}
		var tempForm = document.createElement("form");
  		tempForm.action = "adminAdvertisementManageUI.html";
  		tempForm.method = "post";
  		tempForm.style.display = "none";
  							
  		var adTypeInput = document.createElement("input");
  		adTypeInput.type="hidden"; 
		adTypeInput.name= "type"; 
		adTypeInput.value= adType; 
		tempForm.appendChild(adTypeInput);
							
		document.body.appendChild(tempForm);
		tempForm.submit();
		document.body.removeChild(tempForm);
}

//广告上移
function upSort(tableIndex){
	var upIndex = Number(tableIndex)-1;
	
	var adId1 = $("#ad_id_"+tableIndex).val();
	var adOrder1 = $("#ad_order_"+ upIndex).val();
	
	var adId2 = $("#ad_id_"+upIndex).val();
	var adOrder2 = $("#ad_order_"+ tableIndex).val();
	$.ajax({
		type:"POST",
		dataType:"json",
		data:{"adId1":adId1,"adOrder1":adOrder1,"adId2":adId2,"adOrder2":adOrder2},
		url:"adminAdvertisementSort.html",
		success: function(data){
			if(data.success == false) {
				alert("广告排序失败!");
			}
			//window.location.href = "adminAdvertisementManageUI.html";
			var adType = $("#adType").val();
			toAdManagerUI(adType);
		},
		error:function(data){
			alert("系统异常!请稍后再试");
		}
	});

}
//广告下移
function downSort(tableIndex){
	var downIndex = Number(tableIndex)+1;
	
	var adId1 = $("#ad_id_"+tableIndex).val();
	var adOrder1 = $("#ad_order_"+ downIndex).val();
	
	var adId2 = $("#ad_id_"+downIndex).val();
	var adOrder2 = $("#ad_order_"+ tableIndex).val();
	
	$.ajax({
		type:"POST",
		dataType:"json",
		data:{"adId1":adId1,"adOrder1":adOrder1,"adId2":adId2,"adOrder2":adOrder2},
		url:"adminAdvertisementSort.html",
		success: function(data){
			if(data.success == false) {
				alert("广告排序失败!");
			}
			//window.location.href = "adminAdvertisementManageUI.html";
			var adType = $("#adType").val();
			toAdManagerUI(adType);
		},
		error:function(data){
			alert("系统异常!请稍后再试");
		}
	});
}