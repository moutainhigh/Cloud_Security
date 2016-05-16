
var wait=120;

//检测邮箱验证码是否发送成功
/*function checkSendEmail(){
 var email = $("#eamil_ecode").val();
 if(email==""||email==null){
		$("#forget_email_msg").html("邮箱不能为空");
	}else{
		$.ajax({
           type: "POST",
           url: "isExitEmail.html",
           data: {"email":email},
           dataType:"json",
           success: function(data){
           		if(data.isExit=="0"){
               		$("#forget_email_msg").html("邮件地址不存在");
               		
           		}else{
           			
           			$.ajax({
			           type: "POST",
			           url: "regist_checkSendEmail.html",
			           data: {"email":email},
			           dataType:"json",
			           success: function(data){
			           		if(data.msg=="0"){
			               		$("#forget_email_msg").html("邮件发送失败");
			               		
			           		}else{
			           			time();
			           			$("#forget_email_msg").html("<font color='green'>验证码发送成功，请到邮箱查收</font>");
			           		
			           		}
			           }
			        });
           		}
           }
        });
 		
      }
 
}*/
//检测手机号码不能为空
function checkPhone(){
	var phone = $("#phone_code").val();
	var pattern = /^1[3|5|8|7][0-9]{9}$/;
	var flag = pattern.test(phone);
 	if(phone==""||phone==null){
		$("#phone_code_flag").attr("class","error");
		$("#phone_code_prompt").html("<b></b>手机号码不能为空");
		$("#phone_code_prompt").fadeIn();
	}else if(flag){
		$("#phone_code_flag").attr("class","right");
		$("#phone_code_prompt").html("<b></b>");
		$("#phone_code_prompt").fadeOut();
	}else{
		$("#phone_code_flag").attr("class","error");
		$("#phone_code_prompt").html("<b></b>手机号码输入有误");
		$("#phone_code_prompt").fadeIn();
	}
}
//检测手机验证码是否发送成功
function checkSendMobile(){
 	var phone = $("#phone_code").val();
 	if(phone==""||phone==null){
		$("#phone_code_flag").attr("class","error");
		$("#phone_code_prompt").html("<b></b>手机号码不能为空");
		$("#phone_code_prompt").fadeIn();
	}else{
		$("#phone_code_flag").attr("class","right");
		$("#phone_code_prompt").html("<b></b>");
		$("#phone_code_prompt").fadeOut();
		$.ajax({
           type: "POST",
           url: "isExitPhone.html",
           data: {"mobile":phone},
           dataType:"json",
           success: function(data){
           		if(data.isExit=="1"){
           			$("#phone_code_flag").attr("class","right");
           			$("#phone_code_prompt").html("<b></b>");
           			$("#phone_code_prompt").fadeOut();
		               $.ajax({
					           type: "POST",
					           url: "regist_checkSendMobile.html",
					           data: {"mobile":phone},
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
					           			$("#phone_code_prompt").html("<b></b>一个手机号码只能发送3次短信,且两次间隔大于2分钟!");
					           			$("#phone_code_prompt").fadeIn();
					               		
					           		}
					           }
		              });
           		}else {
           			$("#phone_code_flag").attr("class","error");
           			$("#phone_code_prompt").html("<b></b>手机号码不存在!");
           			$("#phone_code_prompt").fadeIn();
           		}
           }
        });
		}
 }
//发邮件按钮显示倒计时的效果	
/*function time() {
	if (wait == 0) { 
		document.getElementById("email_yzm").disabled=false;
		document.getElementById("email_yzm").value="点击发送验证码";
		wait = 120;
	} else { 
		document.getElementById("email_yzm").value=wait + "秒后重新获取验证码";
		document.getElementById("email_yzm").disabled=true;
		wait--; 
		setTimeout(function() { 
			time();
		}, 1000); 
	} 
} */	
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
/*function checkEmailActivationCode(){
	 var verification_code = $("#verification_email").val();
	 if(verification_code!=null&&verification_code!=""){
		 $.ajax({
           type: "POST",
           url: "regist_checkEmailActivationCode.html",
           data: {"verification_code":verification_code},
           dataType:"json",
           success: function(data){
           		if(data.msg=="0"){
	               	$("#verification_email_msg").html("验证码填写错误");
	               
           		}else{
           			$("#forget_email_msg").html("");
           			$("#verification_email_msg").html("");
           			$("#passForm").submit();
           		}
           },
        });  
	 }else{
	 	$("#verification_email_msg").html("请填写验证码");
		
	 }
}*/

//检测验证码填写是否正确
function checkPhoneActivationCode(){
	 var verification_code = $("#verification_phone").val();
	 	if(verification_code!=null&&verification_code!=""){
		 $.ajax({
           type: "POST",
           url: "regist_checkEmailActivationCode.html",
           data: {"verification_code":verification_code},
           dataType:"json",
           success: function(data){
           		if(data.msg=="0"){
           			$("#verification_phone_flag").attr("class","error");
           			$("#verification_phone_prompt").html("<b></b>验证码填写错误!");
           			$("#verification_phone_prompt").fadeIn();
           		}else{
           			$("#verification_phone_flag").attr("class","right");
           			$("#verification_phone_prompt").html("<b></b>");
           			$("#verification_phone_prompt").fadeOut();
           			$("#phoneForm").submit();
           		}
           },
        });  
	 }else{
		$("#verification_phone_flag").attr("class","error");
		$("#verification_phone_prompt").html("<b></b>请填写验证码!");
		$("#verification_phone_prompt").fadeIn();
	 }
}

/*
//跟换验证码
function checkRegisterImage(){
	var imageNumber = document.getElementById("imageRegisterNumber");
	imageNumber.src = "image.jsp?timestamp="+new Date().getTime();
};
 $('#imageRegisterNumber').click(function(){checkRegisterImage()});*/