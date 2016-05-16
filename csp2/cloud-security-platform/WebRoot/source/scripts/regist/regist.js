//校验用户名是否出现重复
var checkName1 = 0;
var checkPassword1 = 0;
var checkConfirmPassword1 = 0;
var checkMobile1 = 0;
var checkEmail1 = 0;
var checkSendEmail1 = 0;
var checkSendMobile1 = 0;
var checkEmailActivationCode1 = 0;
var wait=120;
function checkName(){
	var name = $("#regist_name").val();
	var	pattern	= /^[a-zA-Z0-9_]{4,12}$/;
	var flag = pattern.test(name);
	if(name==""||name==null){
		$("#regist_name_flag").attr("class","error");
		$("#regist_name_flag").show();
		checkName1=0;
	}else{
		if(flag){
			$.ajax({
               type: "POST",
               url: "regist_checkName.html",
               data: {"name":name},
               dataType:"json",
               success: function(data){
                   if(data.count>0){
               			$("#regist_name_flag").attr("class","error");
               			$("#regist_name_flag").show();
                   		checkName1=0;
                   }else{
           				$("#regist_name_flag").attr("class","right");
           				$("#regist_name_flag").show();
                   		checkName1=1;
                   }
               },
            }); 
		}else{
			$("#regist_name_flag").attr("class","error");
			$("#regist_name_flag").show();
			checkName1=0;
		}
	}
}
//校验密码	
function checkPassword(){
	var p1 = $("#regist_password").val();

	if(p1==""){
		$("#regist_password_flag").attr("class","error");
		$("#regist_password_flag").show();
		checkPassword1 = 0;
	}else if(p1.length<6||p1.length>20){
			$("#regist_password_flag").attr("class","error");
			$("#regist_password_flag").show();
			checkPassword1 = 0;
		}else{
			var number = passwordLevel(p1);
			//请至少使用两种字符组合
			if(number<2){
				$("#regist_password_flag").attr("class","error");
				$("#regist_password_flag").show();
				checkPassword1 = 0;
			}else{
				$("#regist_password_flag").attr("class","right");
				$("#regist_password_flag").show();
				checkPassword1 = 1;
			}

		}
	}

//密码高级设置验证
function passwordLevel(password) {
	 var Modes = 0;
	 for (i = 0; i < password.length; i++) {
	  Modes |= CharMode(password.charCodeAt(i));
	 }
	 return bitTotal(Modes);
}

//CharMode函数
function CharMode(iN) {
 if (iN >= 48 && iN <= 57)//数字
  return 1;
 if (iN >= 65 && iN <= 90) //大写字母
  return 2;
 if ((iN >= 97 && iN <= 122) || (iN >= 65 && iN <= 90))
//大小写
  return 4;
 else
  return 8; //特殊字符
}

//bitTotal函数
function bitTotal(num) {
 modes = 0;
 for (i = 0; i < 4; i++) {
  if (num & 1) modes++;
  num >>>= 1;
 }
 return modes;
}

//校验确认密码	
function checkConfirmPassword(){
	  var p1=$("#regist_password").val();//获取密码框的值
	  var p2=$("#regist_confirm_password").val();//获取重新输入的密码值
	  if(p2!=null && p2!=""){
		  if (p1!=p2) {
		   	$("#regist_confirm_password_flag").attr("class","error");
		   	$("#regist_confirm_password_flag").show();
		   	$("#regist_confirm_password_prompt").html("<b></b>两次输入密码不一致");
		   	$("#regist_confirm_password_prompt").fadeIn();
		    checkConfirmPassword1 = 0;
		  }else{
			$("#regist_confirm_password_flag").attr("class","right");
			$("#regist_confirm_password_flag").show();
		   	$("#regist_confirm_password_prompt").html("<b></b>支持6-20位,且至少两种字符组合(大小写字母/数字/字符)");
		   	$("#regist_confirm_password_prompt").fadeIn();
		    checkConfirmPassword1 = 1;
		  }
	  }else{
		   	$("#regist_confirm_password_flag").attr("class","error");
		   	$("#regist_confirm_password_flag").show();
	  }

}
//校验手机号码是否出现重复
function checkMobile(){
	var mobile = $("#regist_phone").val();
	var pattern = /^1[3|5|8|7][0-9]{9}$/;
	var flag = pattern.test(mobile);
	if(mobile==""||mobile==null){
		$("#regist_phone_flag").attr("class","error");
		$("#regist_phone_flag").show();
        $("#regist_phone_prompt").html("<b></b>手机号码不能为空");
        $("#regist_phone_prompt").fadeIn();
		 checkMobile1 = 0;
	}else{
		if(flag){
			$.ajax({
               type: "POST",
               url: "regist_checkMobile.html",
               data: {"mobile":mobile},
               dataType:"json",
               success: function(data){
                    if(data.count>0){
                		$("#regist_phone_flag").attr("class","error");
                		$("#regist_phone_flag").show();
                        $("#regist_phone_prompt").html("<b></b>该手机号码已使用");
                        $("#regist_phone_prompt").fadeIn();
                        checkMobile1 = 0;
                    }else{
                		$("#regist_phone_flag").attr("class","right");
                		$("#regist_phone_flag").show();
                		 $("#regist_phone_prompt").html("<b></b>");
                		$("#regist_phone_prompt").fadeOut();
                   		checkMobile1=1;
                    }
               }
            });
		}else{
    		$("#regist_phone_flag").attr("class","error");
    		$("#regist_phone_flag").show();
    		$("#regist_phone_prompt").fadeOut();
   		 	$("#regist_phone_prompt").html("<b></b>");
			checkMobile1 = 0;
		}
	}
}
/*
//校验邮箱是否出现重复
function checkEmail(){
	var email = $("#regist_email").val();
//	var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
	var pattern = /^([a-z0-9A-Z]+[-|_|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/;
	var flag = pattern.test(email);
	if(email==""||email==null){
		$("#regist_mobile_email_msg").html("邮箱不能为空");
		 checkEmail1 = 0;
	}else{
		if(flag){
			$.ajax({
               type: "POST",
               url: "regist_checkEmail.html",
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
 	var checkNumber1 = $("#checkNumber1").val();
 	if(checkNumber1==""){
 		$("#verification_Image_msg").html("图片验证码不能为空!"); 
 	 }else{
 	 	if(checkEmail1==1){
 	 		$("#verification_Image_msg").html(""); 
 	 		$.ajax({
 	           type: "POST",
 	           url: "regist_checkSendEmail.html",
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

}
*/
//检测手机验证码是否发送成功
function checkSendMobile(){
 	var phone = $("#regist_phone").val();


	 if(checkMobile1==1){
 		$.ajax({
           type: "POST",
           url: "regist_checkSendMobile.html",
           data: {"mobile":phone},
           dataType:"json",
           success: function(data){
           		if(data.msg=="0"){
               		$("#verification_code_prompt").html("<b></b>短信发送失败");
               		$("#verification_code_prompt").fadeIn();
               		checkSendMobile1 = 0;
           		}else if(data.msg=="1"){
           			timeMobile();
               		$("#verification_code_prompt").html("<b></b>验证码发送成功，请查收短信");
               		$("#verification_code_prompt").fadeIn();
           			checkSendMobile1 = 1;
           		}else{
               		$("#verification_code_prompt").html("<b></b>一个手机号码只能发送3次短信,且两次间隔大于2分钟");
               		$("#verification_code_prompt").fadeIn();
               		checkSendMobile1 = 0;
           		}
           }
        });
  
    }
 	
}
/*//发邮件按钮显示倒计时的效果	
function time() {
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
}*/ 	
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
	 var verification_code = $("#verification_code").val();
	 if(verification_code!=null&&verification_code!=""){
		 $.ajax({
           type: "POST",
           url: "regist_checkEmailActivationCode.html",
           data: {"verification_code":verification_code},
           dataType:"json",
           success: function(data){
           		if(data.msg=="0"){
           			$("#verification_code_flag").attr("class","error");
           			$("#verification_code_flag").show();
               		$("#verification_code_prompt").html("<b></b>验证码填写错误");
               		$("#verification_code_prompt").fadeIn();
	               	checkEmailActivationCode1 = 0;
           		}else{
           			$("#verification_code_flag").attr("class","right");
           			$("#verification_code_flag").show();
               		$("#verification_code_prompt").html("<b></b>");
               		$("#verification_code_prompt").fadeOut();
           			checkEmailActivationCode1 = 1;
           		}
           },
        });  
	 }else{
			$("#verification_code_flag").attr("class","error");
   			$("#verification_code_flag").show();
       		$("#verification_code_prompt").html("<b></b>请填写验证码");
       		$("#verification_code_prompt").fadeIn();
		checkEmailActivationCode1 = 0;
	 }
}
//提交表单及校验
function submitForm(){
	var name = $("#regist_name").val();
	var p1 = $("#regist_password").val();
	var p2=$("#regist_confirm_password").val();
	var mobile = $("#regist_phone").val();
	var verification_code = $("#verification_code").val();
	var checkNumber = $("#checkNumber1").val();
	var agreeId = $("#agreeId").val();//同意协议
	if(null==name||""==name||p1==""||p1==null||p2==""||p2==null||mobile==""||mobile==null||verification_code==""||verification_code==null||checkNumber==""||checkNumber==null){
		if(null==name||""==name){
			$("#regist_name_flag").attr("class","error");
			$("#regist_name_flag").show();
		}else{
			$("#regist_name_flag").attr("class","right");
			$("#regist_name_flag").show();
		}
		if(null==p1||""==p1){
			$("#regist_password_flag").attr("class","error");
			$("#regist_password_flag").show();
		}else{
			$("#regist_password_flag").attr("class","right");
			$("#regist_password_flag").show();
		}
		if(name!=null&&name!=''&&p1!=null&&p1!='')
		{
			if(name==p1){
				$("#regist_password_flag").attr("class","error");
				$("#regist_password_flag").show();
				$("#regist_password_prompt").html("<b></b>用户名和密码一致，请重新修改密码!");
				$("#regist_password_prompt").fadeIn();
			}else{
				$("#regist_password_flag").attr("class","right");
				$("#regist_password_flag").show();
				$("#regist_password_prompt").html("<b></b>");
				$("#regist_password_prompt").fadeOut();
			}
		}
		if(null==p2||""==p2){
		   	$("#regist_confirm_password_flag").attr("class","error");
		   	$("#regist_confirm_password_flag").show();
		}else{
		   	$("#regist_confirm_password_flag").attr("class","right");
		   	$("#regist_confirm_password_flag").show();
		}
		
		if(mobile==""||mobile==null){
    		$("#regist_phone_flag").attr("class","error");
    		$("#regist_phone_flag").show();
		}else{
    		$("#regist_phone_flag").attr("class","right");
    		$("#regist_phone_flag").show();
		}
		if(verification_code==""||verification_code==null){
			$("#verification_code_flag").attr("class","error");
			$("#verification_code_flag").show();
		}else{
			$("#verification_code_flag").attr("class","right");
			$("#verification_code_flag").show();
		}
		if(checkNumber==""||checkNumber==null){
			$("#checkNumber1_flag").attr("class","error");
			$("#checkNumber1_flag").show();
		}else{
			$("#checkNumber1_flag").attr("class","right");
			$("#checkNumber1_flag").show();
		}
		
	}else{
	    if(checkMobile1==0){
	    }else{
	    	if(document.getElementById("ck").checked && agreeId==1){
	    		$("#ck_flag").html("<b></b>");
				$("#ck_flag").fadeOut();
			    if(checkName1==1&&checkPassword1==1&&checkConfirmPassword1==1&&checkSendMobile1==1&&checkEmailActivationCode1==1){
			    	if(name==p1){
			    		$("#regist_password_flag").attr("class","error");
						$("#regist_password_flag").show();
						$("#regist_password_prompt").html("<b></b>用户名和密码一致，请重新修改密码!");
						$("#regist_password_prompt").fadeIn();
		          }else{
						$("#regist_password_flag").attr("class","right");
						$("#regist_password_flag").show();
						$("#regist_password_prompt").html("<b></b>");
						$("#regist_password_prompt").fadeOut();
						$.ajax({
							   type: "POST",
							   url:'regist_checkNumber.html',
							   data:{"checkNumber":checkNumber},
							   dataType:"json",
							   success: function(data) {
								   if(data.flag){
										$("#checkNumber1_flag").attr("class","right");
										$("#checkNumber1_flag").show();
										$("#checkNumber1_prompt").html("<b></b>");
										$("#checkNumber1_prompt").fadeOut();
										$("#form_regist").submit();
										alert("注册成功！");
								   }else{
										$("#checkNumber1_flag").attr("class","error");
										$("#checkNumber1_flag").show();
										$("#checkNumber1_prompt").html("<b></b>验证码输入有误");
										$("#checkNumber1_prompt").fadeIn();
								   }
							   }
						});

				  }
				}    	
		    }else{
	    		$("#ck_prompt").html("<b></b>请阅读《云平台用户注册协议》");
				$("#ck_prompt").fadeIn();
		    }
	    }
	}
}



//跟换验证码
function checkRegisterImage(){
	var imageNumber = document.getElementById("imageRegisterNumber");
	imageNumber.src = getRootPath()+"image.jsp?timestamp="+new Date().getTime();
};
 $('#imageRegisterNumber').click(function(){checkRegisterImage()});
 
 function getRootPath(){
	    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	    var curWwwPath=window.document.location.href;
	    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	    var pathName=window.document.location.pathname;
	    var pos=curWwwPath.indexOf(pathName);
	    //获取主机地址，如： http://localhost:8083
	    var localhostPaht=curWwwPath.substring(0,pos);
	    //获取带"/"的项目名，如：/uimcardprj
	    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	    return(localhostPaht+projectName);
	}
	