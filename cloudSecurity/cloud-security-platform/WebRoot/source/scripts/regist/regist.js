//校验用户名是否出现重复
var checkName1 = 0;
var checkPassword1 = 0;
var checkConfirmPassword1 = 0;
var checkMobile1 = 0;
var checkEmail1 = 0;
var checkSendEmail1 = 0;
var checkSendMobile1 = 0;
var checkEmailActivationCode1 = 0;
var wait=30;
function checkName(){
	var name = $("#regist_name").val();
	var	pattern	= /^[a-zA-Z0-9]{4,20}$/;
	var flag = pattern.test(name);
	if(name==""||name==null){
		$("#regist_name_msg").html("用户名不能为空");
		checkName1=0;
	}else{
		if(flag){
			$.ajax({
               type: "POST",
               url: "/cloud-security-platform/regist_checkName.html",
               data: {"name":name},
               dataType:"json",
               success: function(data){
                   if(data.count>0){
                   		$("#regist_name_msg").html("用户名已经存在");
                   		checkName1=0;
                   }else{
                   		$("#regist_name_msg").html("");
                   		checkName1=1;
                   }
               },
            }); 
		}else{
			$("#regist_name_msg").html("请输入4-20位字符");
			checkName1=0;
		}
	}
}
//校验密码	
function checkPassword(){
	var p1 = $("#regist_password").val();
	var	pattern	= /^[a-zA-Z0-9]{6,16}$/;
	var flag = pattern.test(p1);
	if(p1==""||p1==null){
		$("#regist_password_msg").html("密码不能为空");
		checkPassword1 = 0;
	}else{
		if(flag){
			$("#regist_password_msg").html("");
			checkPassword1 = 1;
		}else{
			$("#regist_password_msg").html("请输入6-16位仅包含字母和数字的密码");
			checkPassword1 = 0;
		}
	}
}
//校验确认密码	
function checkConfirmPassword(){
	  var p1=$("#regist_password").val();//获取密码框的值
	  var p2=$("#regist_confirm_password").val();//获取重新输入的密码值
	  if(p2!=null&&p2!=""){
		  if (p1!=p2) {
		   	$("#regist_confirm_password_msg").html("<font color='red'>两次输入密码不一致，请重新输入</font>");
		    checkConfirmPassword1 = 0;
		  }else{
		    $("#regist_confirm_password_msg").html("");
		    checkConfirmPassword1 = 1;
		  }
	  }else{
		  $("#regist_confirm_password_msg").html("<font color='red'>确认密码不能为空</font>");
		  checkConfirmPassword1 = 0;
	  }
}
//校验手机号码是否出现重复
function checkMobile(){
	var mobile = $("#regist_phone").val();
	var pattern = /^1[3|5|8|7][0-9]{9}$/;
	var flag = pattern.test(mobile);
	if(mobile==""||mobile==null){
		$("#regist_mobile_email_msg").html("手机号码不能为空");
		 checkMobile1 = 0;
	}else{
		if(flag){
			$.ajax({
               type: "POST",
               url: "/cloud-security-platform/regist_checkMobile.html",
               data: {"name":name},
               dataType:"json",
               success: function(data){
                    if(data.count>0){
                        $("#regist_mobile_email_msg").html("您填写的手机号码已使用");//将发送验证码的button锁住，此时不能点击发送验证码
                        checkMobile1 = 0;
                    }else{
                   		$("#regist_mobile_email_msg").html("");
                   		checkMobile1=1;
                    }
               }
            });
		}else{
			$("#regist_mobile_email_msg").html("手机号码格式不对");
			checkMobile1 = 0;
		}
	}
}	
//校验邮箱是否出现重复
function checkEmail(){
	var email = $("#regist_email").val();
	var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
	var flag = pattern.test(email);
	if(email==""||email==null){
		$("#regist_mobile_email_msg").html("邮箱不能为空");
		 checkEmail1 = 0;
	}else{
		if(flag){
			$.ajax({
               type: "POST",
               url: "/cloud-security-platform/regist_checkEmail.html",
               data: {"email":email},
               dataType:"json",
               success: function(data){
                    if(data.count>0){
                        $("#regist_mobile_email_msg").html("您填写的邮箱已使用");//将发送验证码的button锁住，此时不能点击发送验证码
                        checkEmail1 = 0;
                    }else{
                   		$("#regist_mobile_email_msg").html("");
                   		checkEmail1=1;
                    }
               }
            }); 
		}else{
			$("#regist_mobile_email_msg").html("邮箱格式不对");
			 checkEmail1 = 0;
		}
	}
}	
	
//检测邮箱验证码是否发送成功
function checkSendEmail(){
 	var email = $("#regist_email").val();
 	if(checkEmail1==1){
 		$.ajax({
           type: "POST",
           url: "/cloud-security-platform/regist_checkSendEmail.html",
           data: {"email":email},
           dataType:"json",
           success: function(data){
           		if(data.msg=="0"){
               		$("#verification_code_msg").html("邮件发送失败");
               		checkSendEmail1 = 0;
           		}else{
           			time();
           			$("#verification_code_msg").html("<font color='green'>验证码发送成功，请到邮箱查收</font>");
           			checkSendEmail1 = 1;
           		}
           }
        });
      
 	}
}
//检测手机验证码是否发送成功
function checkSendMobile(){
 	var phone = $("#regist_phone").val();
 	if(checkMobile1==1){
 		$.ajax({
           type: "POST",
           url: "/cloud-security-platform/regist_checkSendMobile.html",
           data: {"mobile":phone},
           dataType:"json",
           success: function(data){
           		if(data.msg=="0"){
               		$("#verification_code_msg").html("短信发送失败");
               		checkSendMobile1 = 0;
           		}else{
           			timeMobile();
           			$("#verification_code_msg").html("<font color='green'>验证码发送成功，请查收短信</font>");
           			checkSendMobile1 = 1;
           		}
           }
        });
      
 	}
}
//发邮件按钮显示倒计时的效果	
function time() {
	if (wait == 0) { 
		document.getElementById("email_yzm").disabled=false;
		document.getElementById("email_yzm").value="点击发送验证码";
		wait = 30;
	} else { 
		document.getElementById("email_yzm").value=wait + "秒后重新获取验证码";
		document.getElementById("email_yzm").disabled=true;
		wait--; 
		setTimeout(function() { 
			time();
		}, 1000); 
	} 
} 	
//发短信按钮显示倒计时的效果	
function timeMobile() {
	if (wait == 0) { 
		document.getElementById("phone_yzm").disabled=false;
		document.getElementById("phone_yzm").value="点击发送验证码";
		wait = 30;
	} else { 
		document.getElementById("phone_yzm").value=wait + "秒后重新获取验证码";
		document.getElementById("phone_yzm").disabled=true;
		wait--; 
		setTimeout(function() { 
			timeMobile();
		}, 1000); 
	} 
} 	
//检测验证码填写是否正确
function checkEmailActivationCode(){
	 var verification_code = $("#verification_code").val();
	 if(verification_code!=null&&verification_code!=""){
		 $.ajax({
           type: "POST",
           url: "/cloud-security-platform/regist_checkEmailActivationCode.html",
           data: {"verification_code":verification_code},
           dataType:"json",
           success: function(data){
           		if(data.msg=="0"){
	               	$("#verification_code_msg").html("验证码填写错误");
	               	checkEmailActivationCode1 = 0;
           		}else{
           			$("#verification_code_msg").html("");
           			checkEmailActivationCode1 = 1;
           		}
           },
        });  
	 }else{
	 	$("#verification_code_msg").html("请填写验证码");
		checkEmailActivationCode1 = 0;
	 }
}
//提交表单及校验
function submitForm(){
	var name = $("#regist_name").val();
	var p1 = $("#regist_password").val();
	var p2=$("#regist_confirm_password").val();
	var email = $("#regist_email").val();
	var mobile = $("#regist_phone").val();
	var verification_code = $("#verification_code").val();
	if(null==name||""==name||p1==""||p1==null||p2==""||p2==null||((email==""||email==null)&&(mobile==""||mobile==null))||verification_code==""||verification_code==null){
		if(null==name||""==name){
			$("#regist_name_msg").html("用户名不能为空");
		}else{
			$("#regist_name_msg").html("");
		}
		if(null==p1||""==p1){
			$("#regist_password_msg").html("密码不能为空");
		}else{
			$("#regist_password_msg").html("");
		}
		if(null==p2||""==p2){
			$("#regist_confirm_password_msg").html("<font color='red'>确认密码不能为空</font>");
		}else{
			$("#regist_confirm_password_msg").html("");
		}
		if((email==""||email==null)&&(mobile==""||mobile==null)){
			$("#regist_mobile_email_msg").html("手机号码或邮箱不能为空");
		}else{
			$("#regist_mobile_email_msg").html("");
		}
		if((email==""||email==null)&&(mobile==""||mobile==null)){
			$("#verification_code_msg").html("验证码不能为空");
		}else{
			$("#regist_mobile_email_msg").html("");
		}
	}else{
	    if(checkMobile1==0&&checkEmail1==0){
	    }else{
	    	if(document.getElementById("ck").checked){
	    		$("#ck_msg").html("");
			    if(checkName1==1&&checkPassword1==1&&checkConfirmPassword1==1&&(checkSendEmail1==1||checkSendMobile1==1)&&checkEmailActivationCode1==1){
					$("#form_regist").submit();
			    }
		    }else{
			    $("#ck_msg").html("请阅读《云平台用户注册协议》");
		    }
	    }
	}
}
	