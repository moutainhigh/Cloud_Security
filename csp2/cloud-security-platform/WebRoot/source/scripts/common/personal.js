$(function (){
	var industry = $("#hid_industry").val();
	$("#industry").val(industry);
	var job = $("#hid_job").val();
	$("#job").val(job);
});

//校验手机号码是否出现重复
function checkMobile(){
	var mobileChecked = 0;
	var oldMobile = $("#originalMobile").val();
	var mobile = $("#regist_phone").val();
	var pattern = /^1[3|5|8|7][0-9]{9}$/;
	var flag = pattern.test(mobile);
	if(mobile==""||mobile==null){
		$("#regist_mobile_msg").html("手机号码不能为空!");
		mobileChecked = 0;
	}else{
		if(flag){
			if(oldMobile!=mobile){//如果不是原来手机号
				$.ajax({
		               type: "POST",
		               async: false, 
		               url: "regist_checkMobile.html",
		               data: {"mobile":mobile},
		               dataType:"json",
		               success: function(data){
		                    if(data.count>0){
		                    	$("#regist_mobile_msg").html("该手机号码已使用!");
		                    	mobileChecked = 0;
		                    }else{
		                    	$("#regist_mobile_msg").html("");
		                    	mobileChecked = 1;
		                    }
		               }
		        });
			}else{
				$("#regist_mobile_msg").html("");
				mobileChecked = 1;
			}

		}else{
			$("#regist_mobile_msg").html("输入的手机号码格式不正确!");
			mobileChecked = 0;
		}
	}
	return mobileChecked;
}

function checkUserData(){
	var mobile = $("#regist_phone").val();
	var urlAddr = $("#apikey").val();
	var industry = $("#industry").val();
	var job = $("#job").val();
	var company = $("#company").val();
	var patrn=/[`~@#$%^&*()+<>"{},\\;'[\]]/im;  
	if(patrn.test(company)){
		$("#regist_company_msg").html("公司名称含有非法字符");
		return;
	}
	if(company.length>100){
		$("#regist_company_msg").html("公司名称长度不能超过100个字符");
		return;
	}
	//var flag = checkMobile();
	//if(flag==1){
		$.ajax({ type: "POST",
    		     async: false, 
    		     url: "saveUserDataBate.html", 
    		     data: {"mobile":mobile,
	    			   	"urlAddr":urlAddr,
	    			   	"industry":industry,
	    			   	"job":job,
	    			   	"company":company},  
    		     dataType: "json",
    		     success: function(data) {
    		    	 if(data.message == true){
    		    		 alert("设置成功");  
			    	 }else{
			    		 alert(data.message);
			    	 }
			    	 window.location.href = "userDataUI.html";
    		    	 }, 
    		     error: function(data){ 
    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
    		    		 window.location.href = "loginUI.html"; } 
    		    	 else { window.location.href = "loginUI.html"; } } 
    	});
	//}else{
	//	return;
	//}

}