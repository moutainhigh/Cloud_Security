window.onload =function(){
 $(':input','#mobileForm').not(':button, :submit, :reset, :hidden').val('');
}

var checkMobile = 0; //0:手机号校验失败
var checkEmailActivationCode = 0; //0:验证码验证失败
var wait=120;

//校验手机号码是否出现重复
function checkMobileNum(){
	var oldMobile = $("#originalMobile").val();
	var mobile = $("#phone_code").val();
	var pattern = /^1[3|5|8|7][0-9]{9}$/;
	var flag = pattern.test(mobile);
	if(mobile==""||mobile==null){
		$("#phone_code_flag").attr("class","error");
		$("#phone_code_prompt").html("手机号码不能为空!");
		$("#phone_code_prompt").fadeIn();
		checkMobile = 0;
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
		                    	$("#phone_code_flag").attr("class","error");
		                    	$("#phone_code_prompt").html("该手机号码已使用!");
		                    	$("#phone_code_prompt").fadeIn();
		                    	checkMobile = 0;
		                    }else{
		                    	$("#phone_code_flag").attr("class","right");
								$("#phone_code_prompt").html("<b></b>");
								$("#phone_code_prompt").fadeOut();
		                    	checkMobile = 1;
		                    }
		               }
		        });
			}else{
				$("#phone_code_flag").attr("class","right");
				$("#phone_code_prompt").html("<b></b>");
				$("#phone_code_prompt").fadeOut();
				checkMobile = 1;
			}

		}else{
			$("#phone_code_flag").attr("class","error");
			$("#phone_code_prompt").html("输入的手机号码格式不正确!");
			$("#phone_code_prompt").fadeIn();
			checkMobile = 0;
		}
	}
	return checkMobile;
}

//检测手机验证码是否发送成功
function checkSendMobile(){
 	var mobile = $("#phone_code").val();
 	if(mobile==""||mobile==null){
		$("#phone_code_flag").attr("class","error");
		$("#phone_code_prompt").html("手机号码不能为空!");
		$("#phone_code_prompt").fadeIn();
		checkMobile = 0;
		return;
	}
 	if(checkMobile==1){
		$.ajax({
			type: "POST",
			url: "checkSendMobile.html",
			data: {"mobile":mobile,"useFlag":"3"},
			dataType:"json",
			success: function(data){
				if(data.msg=="0"){
					$("#phone_code_prompt").html("<b></b>短信发送失败!");
					$("#phone_code_prompt").fadeIn();
				}else if(data.msg=="1"){
					timeMobile();
					$("#phone_code_prompt").html("<b></b>验证码发送成功，请查收短信!");
					$("#phone_code_prompt").fadeIn();
				}else{
					$("#phone_code_prompt").html("<b></b>一个手机号码一小时内只能发送3次短信,且两次间隔大于2分钟!");
					$("#phone_code_prompt").fadeIn();
									               		
				}
			}
		});
 	}

 }
 
 //发短信按钮显示倒计时的效果	
function timeMobile() {
	if (wait == 0) { 
		$("#phone_yzm").html("获取验证码");
		$("#phone_yzm").attr("onclick","checkSendMobile()");
		wait = 120;
	} else { 
		$("#phone_yzm").html("<font size='2px'>" + wait + "秒后重新获取" + "</font>");
		$('#phone_yzm').removeAttr('onclick');
		wait--; 
		setTimeout(function() { 
			timeMobile();
		}, 1000); 
	} 
}

//检测验证码填写是否正确
function checkActivationCode(){
	 var mobile = $("#phone_code").val();
	 var verification_code = $("#verification_phone").val();
	 if (checkMobile ==0) {
		  checkEmailActivationCode = 0;
		  return;
	 }
	 if(verification_code!=null&&verification_code!=""){
		 $.ajax({
           type: "POST",
           url: "regist_checkActivationCode.html",
           data: {"verification_code":verification_code,"useFlag":"3","mobile":mobile},
           dataType:"json",
           success: function(data){
           		if(data.msg=="0"){
           			$("#verification_phone_flag").attr("class","error");
           			$("#verification_phone_prompt").html("<b></b>验证码填写错误!");
           			$("#verification_phone_prompt").fadeIn();
           			checkEmailActivationCode = 0;
           		}else if(data.msg=="2"){
           			$("#verification_phone_flag").attr("class","error");
           			$("#verification_phone_prompt").html("<b></b>未获取验证码或验证码失效!");
           			$("#verification_phone_prompt").fadeIn();
           			checkEmailActivationCode = 0;
           		}else{
           			$("#verification_phone_flag").attr("class","right");
           			$("#verification_phone_prompt").html("<b></b>");
           			$("#verification_phone_prompt").fadeOut();
           			checkEmailActivationCode = 1;
           		}
           }
        });  
	 }else{
		$("#verification_phone_flag").attr("class","error");
		$("#verification_phone_prompt").html("<b></b>请填写验证码!");
		$("#verification_phone_prompt").fadeIn();
		checkEmailActivationCode = 0;
	 }
	 return checkEmailActivationCode;
}
	

function updateMobile(){
     var checkPhone = false;
     var checkCode = false;
    //------------------验证手机号码 start-----------
    var oldMobile = $("#originalMobile").val();
	var mobile = $("#phone_code").val();
	var pattern = /^1[3|5|8|7][0-9]{9}$/;
	var flag = pattern.test(mobile);
	if(mobile==""||mobile==null){
		$("#phone_code_flag").attr("class","error");
		$("#phone_code_prompt").html("手机号码不能为空!");
		$("#phone_code_prompt").fadeIn();
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
		                    	$("#phone_code_flag").attr("class","error");
		                    	$("#phone_code_prompt").html("该手机号码已使用!");
		                    	$("#phone_code_prompt").fadeIn();
		                    }else{
		                    	$("#phone_code_flag").attr("class","right");
								$("#phone_code_prompt").html("<b></b>");
								$("#phone_code_prompt").fadeOut();
								checkPhone = true;
		                    }
		               }
		        });
			}else{
				$("#phone_code_flag").attr("class","right");
				$("#phone_code_prompt").html("<b></b>");
				$("#phone_code_prompt").fadeOut();
				checkPhone = true;
			}

		}else{
			$("#phone_code_flag").attr("class","error");
			$("#phone_code_prompt").html("输入的手机号码格式不正确!");
			$("#phone_code_prompt").fadeIn();
		}
	}
	//------------------验证手机号码 end-----------
	if (!checkPhone) {
		$("#verification_phone_flag").removeClass("error");
		$("#verification_phone_flag").removeClass("right");
		$("#verification_phone_prompt").html("<b></b>");
		$("#verification_phone_prompt").hide();
		return;
		
	}
	//------------------验证短信验证码 start-----------
	var verification_code = $("#verification_phone").val();
	 if(verification_code!=null&&verification_code!=""){
		 $.ajax({
           type: "POST",
           url: "regist_checkActivationCode.html",
           data: {"verification_code":verification_code,"useFlag":"3","mobile":mobile},
           dataType:"json",
           success: function(data){
           		if(data.msg=="0"){
           			$("#verification_phone_flag").attr("class","error");
           			$("#verification_phone_prompt").html("<b></b>验证码填写错误!");
           			$("#verification_phone_prompt").fadeIn();
           		}else if(data.msg=="2"){
           			$("#verification_phone_flag").attr("class","error");
           			$("#verification_phone_prompt").html("<b></b>未获取验证码或验证码失效!");
           			$("#verification_phone_prompt").fadeIn();
           		}else{
           			//checkCode = true;
           			$("#verification_phone_flag").attr("class","right");
           			$("#verification_phone_prompt").html("<b></b>");
           			$("#verification_phone_prompt").fadeOut();
           			$("#mobileForm").submit();
           		}
           }
        });  
	 }else{
		$("#verification_phone_flag").attr("class","error");
		$("#verification_phone_prompt").html("<b></b>请填写验证码!");
		$("#verification_phone_prompt").fadeIn();
	 }
    //------------------验证短信验证码 end-----------

}