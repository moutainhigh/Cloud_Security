window.onload =function(){
 $(':input','#phoneForm').not(':button, :submit, :reset, :hidden').val('');
}
var wait=120;


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
 	var useFlag = 0;
 	if(phone==""||phone==null){
		$("#phone_code_flag").attr("class","error");
		$("#phone_code_prompt").html("<b></b>手机号码不能为空");
		$("#phone_code_prompt").fadeIn();
	}else{
		//原始密码:为空是忘记密码；不为空修改密码
		var originalMobile = $("#originalMobile").val();
		if(originalMobile!='' && originalMobile!=null){
			useFlag = 2;//修改密码
			if(originalMobile!=phone){//不是原始密码
				$("#phone_code_flag").attr("class","error");
				$("#phone_code_prompt").html("<b></b>手机号码输入有误");
				$("#phone_code_prompt").fadeIn();
				return;
			}
		}else{
			useFlag = 1;//忘记密码
		}

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
					           url: "checkSendMobile.html",
					           data: {"mobile":phone,"useFlag":useFlag},
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
           		}else {
           			$("#phone_code_flag").attr("class","error");
           			$("#phone_code_prompt").html("<b></b>手机号码不存在!");
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
function checkPhoneActivationCode(){
	 var useFlag = 0;
	 //原始密码:为空是忘记密码；不为空修改密码
	 var originalMobile = $("#originalMobile").val();
	 if(originalMobile!=null && originalMobile!=''){
		 useFlag = 2;//修改密码
	 }else{
		 useFlag = 1;//忘记密码
	 }
	 
	 
	 var verification_code = $("#verification_phone").val();
	 	if(verification_code!=null&&verification_code!=""){
	 	 var phone = $("#phone_code").val();
	 	 
		 $.ajax({
           type: "POST",
           url: "regist_checkActivationCode.html",
           data: {"verification_code":verification_code,"useFlag":useFlag,"mobile":phone},
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