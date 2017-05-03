//校验用户名是否出现重复
var checkName1 = 0;
var checkPassword1 = 0;
var checkConfirmPassword1 = 0;
var checkMobile1 = 0;
var checkSendMobile1 = 0;
var checkCompany = 0;
var checkCheckNumber1 = 0;
var checkCheckPhoneActivationCode = 0;
var checkEmail = 0;
var wait=120;
window.onload =function(){

	 $('#imageNumber').click(function(){checkNumberImage()});
}

function checkName(){
	var name = $("#regist_name").val();
	var	pattern	= /^[a-zA-Z0-9_]{4,20}$/;
	var flag = pattern.test(name);
	if(name==""||name==null){
		$("#regist_name_flag").attr("class","error");
		$("#regist_name_flag").show();
		$("#regist_name_prompt").html("<b></b>用户名不能为空");
   		$("#regist_name_prompt").fadeIn();
   		
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
               		   	$("#regist_name_prompt").html("<b></b>用户名已经存在");
               		   	$("#regist_name_prompt").fadeIn();
                   		checkName1=0;
                   }else{
           				$("#regist_name_flag").attr("class","right");
           				$("#regist_name_flag").show();
           				$("#regist_name_prompt").html("<b></b>支持4-20位字母/数字/下划线及其组合");
           	   		   	$("#regist_name_prompt").fadeOut();
                   		checkName1=1;
                   }
               },
            }); 
		}else{
			$("#regist_name_flag").attr("class","error");
			$("#regist_name_flag").show();
			$("#regist_name_prompt").html("<b></b>支持4-20位字母/数字/下划线及其组合");
   		   	$("#regist_name_prompt").fadeIn();
			checkName1=0;
		}
	}
}
//校验密码	
function checkPassword(){
	var p1 = $("#regist_password").val();
	var p2=$("#regist_confirm_password").val();
	var name = $("#regist_name").val();
	if(p1==""){
		$("#regist_password_flag").attr("class","error");
		$("#regist_password_flag").show();
		$("#regist_password_prompt").html("<b></b>密码不能为空");
		$("#regist_password_prompt").fadeIn();
		checkPassword1 = 0;
	}else if(p1.length<6||p1.length>20){
			$("#regist_password_flag").attr("class","error");
			$("#regist_password_flag").show();
			$("#regist_password_prompt").html("<b></b>支持6-20位,且至少两种字符组合(大小写字母/数字/字符)");
			$("#regist_password_prompt").fadeIn();
			checkPassword1 = 0;
	}else{
			var number = passwordLevel(p1);
			//请至少使用两种字符组合
			if(number<2){
				$("#regist_password_flag").attr("class","error");
				$("#regist_password_flag").show();
				$("#regist_password_prompt").html("<b></b>支持6-20位,且至少两种字符组合(大小写字母/数字/字符)");
				$("#regist_password_prompt").fadeIn();
				checkPassword1 = 0;
			}else if(name==p1){
	    		$("#regist_password_flag").attr("class","error");
				$("#regist_password_flag").show();
				$("#regist_password_prompt").html("<b></b>用户名和密码一致，请重新修改密码!");
				$("#regist_password_prompt").fadeIn();
				checkPassword1 = 0;
	        }else{
				$("#regist_password_flag").attr("class","right");
				$("#regist_password_flag").show();
				$("#regist_password_prompt").html("<b></b>支持6-20位,且至少两种字符组合(大小写字母/数字/字符)");
				$("#regist_password_prompt").fadeOut();
				checkPassword1 = 1;
			}

		}
		if(p2!=""&&p2!=null){
			checkConfirmPassword();
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
		   	$("#regist_confirm_password_prompt").fadeOut();
		    checkConfirmPassword1 = 1;
		  }
	  }else{
		   	$("#regist_confirm_password_flag").attr("class","error");
		   	$("#regist_confirm_password_flag").show();
		   	$("#regist_confirm_password_prompt").html("<b></b>确认密码不能为空");
		   	$("#regist_confirm_password_prompt").fadeIn();
		   	checkConfirmPassword1 = 0;
	  }

}
//校验公司名称
function checkCompanyFun(){
	var company = $("#company").val();
	var patrn=/[`~@#$%^&*()+<>"{},\\;'[\]]/im;
	if(company!='' && company!=null){
		if(patrn.test(company)){
		   	$("#company_flag").attr("class","error");
		   	$("#company_flag").show();
		   	$("#company_prompt").html("<b></b>公司名称含有非法字符");
		   	$("#company_prompt").fadeIn();
		    checkCompany = 0;
		}else if(company.length>20){
		   	$("#company_flag").attr("class","error");
		   	$("#company_flag").show();
		   	$("#company_prompt").html("<b></b>公司名称长度不能超过20个字符");
		   	$("#company_prompt").fadeIn();
		    checkCompany = 0;
		}else{
			$("#company_flag").attr("class","right");
			$("#company_flag").show();
		   	$("#company_prompt").html("<b></b>输入公司名称 ，支持长度1-20");
		   	$("#company_prompt").fadeOut();
		   	checkCompany = 1;
		}
	}else{
		$("#company_flag").attr("class","error");
		$("#company_flag").show();
	   	$("#company_prompt").html("<b></b>公司名称不能为空");
	   	$("#company_prompt").fadeIn();
	   	checkCompany = 0;
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
        $("#regist_phone_prompt").html("<b></b>请输入手机号码");
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
                		$("#regist_phone_prompt").html("<b></b>请输入手机号码");
                		$("#regist_phone_prompt").fadeOut();
                   		checkMobile1=1;
                    }
               }
            });
		}else{
    		$("#regist_phone_flag").attr("class","error");
    		$("#regist_phone_flag").show();
    		$("#regist_phone_prompt").fadeOut();
   		 	$("#regist_phone_prompt").html("<b></b>手机号码格式不正确");
			checkMobile1 = 0;
		}
	}
}

//检测手机验证码是否发送成功
function checkSendMobile(){
 	var phone = $("#regist_phone").val();
 	if(checkCheckNumber1==1){
 		if(checkMobile1==1){
 	 		$.ajax({
 	           type: "POST",
 	           url: "checkSendMobile.html",
 	           data: {"mobile":phone,"useFlag":0},
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
 	               		$("#verification_code_prompt").html("<b></b>一个手机号码一小时内只能发送3次短信,且两次间隔大于2分钟");
 	               		$("#verification_code_prompt").fadeIn();
 	               		checkSendMobile1 = 0;
 	           		}
 	           }
 	        });
 	  
 	    } 
 	}else{
 		$("#verification_code_prompt").html("<b></b>请确认是否已正确输入图形验证码");
    	$("#verification_code_prompt").fadeIn();
    	checkSendMobile1 = 0;
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
	var checkNumber = $("#checkNumber1").val();
	if(checkNumber==""||checkNumber==null){
		$("#checkNumber1_flag").attr("class","error");
		$("#checkNumber1_flag").show();
		$("#checkNumber1_prompt").html("<b></b>输入图片验证码");
		$("#checkNumber1_prompt").fadeIn();
		checkCheckNumber1 = 0;
	}else{
		$.ajax({
			   type: "POST",
			   url:'regist_checkNumber.html',
			   data:{"checkNumber":checkNumber},
			   dataType:"json",
			   success: function(data) {
				   if(data.flag){
						$("#checkNumber1_flag").attr("class","right");
						$("#checkNumber1_flag").show();
						$("#checkNumber1_prompt").html("<b></b>输入图片验证码");
						$("#checkNumber1_prompt").fadeOut();
						checkCheckNumber1 = 1;
				   }else{
						$("#checkNumber1_flag").attr("class","error");
						$("#checkNumber1_flag").show();
						$("#checkNumber1_prompt").html("<b></b>验证码输入有误");
						$("#checkNumber1_prompt").fadeIn();
						checkCheckNumber1 = 0;
						checkNumberImage();
				   }
			   }
		});
	}
}

//检测手机验证码填写是否正确
function checkPhoneActivationCode(){
	 var verification_code = $("#verification_code").val();
	 	if(verification_code!=null&&verification_code!=""){
	 	 var phone = $("#regist_phone").val();
		 $.ajax({
           type: "POST",
           url: "regist_checkActivationCode.html",
           data: {"verification_code":verification_code,"useFlag":0,"mobile":phone},
           dataType:"json",
           success: function(data){
           		if(data.msg=="0"){
           			$("#verification_code_flag").attr("class","error");
           			$("#verification_code_flag").show();
           			$("#verification_code_prompt").html("<b></b>短信验证码输入有误");
           			$("#verification_code_prompt").fadeIn();
           			checkCheckPhoneActivationCode = 0;
           		}else if(data.msg=="2"){
					//未获取验证码或验证码失效!
           			$("#verification_code_flag").attr("class","error");
           			$("#verification_code_flag").show();
           			$("#verification_code_prompt").html("<b></b>未获取短信验证码或短信验证码失效");
           			$("#verification_code_prompt").fadeIn();
           			checkCheckPhoneActivationCode = 0;
           		}else{
           			$("#verification_code_flag").attr("class","right");
           			$("#verification_code_flag").show();
           			$("#verification_code_prompt").html("<b></b>输入短信验证码");
           			$("#verification_code_prompt").fadeOut();
           			checkCheckPhoneActivationCode = 1;
           		}
           },
        });  
	 }else{
		$("#verification_code_flag").attr("class","error");
		$("#verification_code_flag").show();
		$("#verification_code_prompt").html("<b></b>输入短信验证码");
		$("#verification_code_prompt").fadeIn();
		checkCheckPhoneActivationCode = 0;
	 }
}
//验证邮箱
function checkConfirmEmail(){
	var emailVal=$("#regist_confirm_email").val();
//	var reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(emailVal!=null&&emailVal!=''){
		if(!reg.test(emailVal)){
			$("#regist_confirm_Email_flag").attr("class","error");
    		$("#regist_confirm_Email_flag").show();
    		$("#regist_confirm_email_prompt").html("<b></b>请输入正确的邮箱地址!");
    		$("#regist_confirm_email_prompt").fadeIn();
    		checkEmail = 0;
		}else{
			$("#regist_confirm_Email_flag").attr("class","right");
   			$("#regist_confirm_Email_flag").show();
   			$("#regist_confirm_email_prompt").fadeOut();
   			checkEmail = 1;
		}
	}else{
            $("#regist_confirm_Email_flag").attr("class","right");
   			$("#regist_confirm_Email_flag").show();
   			$("#regist_confirm_email_prompt").fadeOut();
   			checkEmail = 1;
	}
}
//提交表单及校验
function submitForm(){
	var name = $("#regist_name").val();
	var p1 = $("#regist_password").val();
	var p2=$("#regist_confirm_password").val();
	var company = $("#company").val();
	var industry = $("#industry").val();
	var job = $("#job").val();
	var mobile = $("#regist_phone").val();
	var verification_code = $("#verification_code").val();
	var checkNumber = $("#checkNumber1").val();
	var agreeId = $("#agreeId").val();//同意协议
    var email = $("#regist_confirm_email").val();
    var partner = $("#partner").val();//合作方
	checkActivationCode();
	checkPhoneActivationCode();
	checkName();
	checkPassword();
	checkConfirmPassword();
	checkCompanyFun();
	checkMobile();
	checkConfirmEmail();
	 setTimeout(function () {
		 if(checkCompany==1&&checkName1==1&&checkPassword1==1&&checkConfirmPassword1==1&&checkMobile1==1&&checkSendMobile1==1&&checkCheckNumber1==1&&checkCheckPhoneActivationCode==1){
		    	if(agreeId=='1'){
		    		$("#ck_prompt").html("<b></b>");
					$("#ck_prompt").fadeOut();
					//$("#form_regist").submit();
					//alert("注册成功！"); 
					$.ajax({
						type: "POST",
						url:'registToLogin.html',
						data:{
		 	               'name':name,
		 	               'password':p1,
		 	               'confirm_password':p2,
		 	               'company':company,
		 	               'industry':industry,
		 	               'job':job,
		 	               'mobile':mobile,
		 	               'checkNumber':checkNumber,
		 	               'verification_code':verification_code,
		 	               'email':email,
		 	               'partner':partner
		 	               //'remeberMe':remeberMe
						},
						dataType:"json",
						success: function(data){
							$('#register_btn').attr('disabled',"disabled");
							$("#register_btn").css("opacity","0.4"); 
							if(data.result != 0) {
								//刷新验证码
								checkNumberImage();
								$("#checkNumber1").val("");
								if (data.result != 8) {
									$("#checkNumber1_flag").hide();
									$("#checkNumber1_prompt").fadeOut();
						    	
						    	}
								$('#register_btn').removeAttr('disabled');
								$("#register_btn").css("opacity",""); 
							}
					    	
							switch(data.result) {
								case 0:
									//注册正确
									alert("注册成功！");
									window.location.href="loginUI.html";
									break;
								case 1:
									//用户名
									$("#regist_name_flag").attr("class","error");
									$("#regist_name_flag").show();
									$("#regist_name_prompt").html("<b></b>" + data.msg);
							   		$("#regist_name_prompt").fadeIn();
							   		$('#register_btn').removeAttr('disabled');
									$("#register_btn").css("opacity",""); 
							   		break;
								case 2:
									//密码
									$("#regist_password_flag").attr("class","error");
									$("#regist_password_flag").show();
									$("#regist_password_prompt").html("<b></b>" + data.msg);
									$("#regist_password_prompt").fadeIn();
									$('#register_btn').removeAttr('disabled');
									$("#register_btn").css("opacity",""); 
									break;
								case 3:
									//确认密码
									$("#regist_confirm_password_flag").attr("class","error");
								   	$("#regist_confirm_password_flag").show();
								   	$("#regist_confirm_password_prompt").html("<b></b>" + data.msg);
								   	$("#regist_confirm_password_prompt").fadeIn();
								   	$('#register_btn').removeAttr('disabled');
									$("#register_btn").css("opacity",""); 
								   	break;
								case 4:
									//公司
									$("#company_flag").attr("class","error");
								   	$("#company_flag").show();
								   	$("#company_prompt").html("<b></b>" + data.msg);
								   	$("#company_prompt").fadeIn();
								   	$('#register_btn').removeAttr('disabled');
									$("#register_btn").css("opacity",""); 
								   	break;
								case 5:
									//行业
									break;
								case 6:
									//职业
									break;
								case 7:
									//手机号
									$("#regist_phone_flag").attr("class","error");
			                		$("#regist_phone_flag").show();
			                        $("#regist_phone_prompt").html("<b></b>" + data.msg);
			                        $("#regist_phone_prompt").fadeIn();
			                        $('#register_btn').removeAttr('disabled');
									$("#register_btn").css("opacity",""); 
			                        break;
								case 8:
									//验证码
									$("#checkNumber1_flag").attr("class","error");
									$("#checkNumber1_flag").show();
									$("#checkNumber1_prompt").html("<b></b>" + data.msg);
									$("#checkNumber1_prompt").fadeIn();
									$('#register_btn').removeAttr('disabled');
									$("#register_btn").css("opacity",""); 
									break;
								case 9:
									//手机验证码
									$("#verification_code_flag").attr("class","error");
				           			$("#verification_code_flag").show();
				           			$("#verification_code_prompt").html("<b></b>" + data.msg);
				           			$("#verification_code_prompt").fadeIn();
				           			$('#register_btn').removeAttr('disabled');
									$("#register_btn").css("opacity",""); 
				           			break;
				           		case 10:
									//邮箱地址
									$("#regist_confirm_Email_flag").attr("class","error");
				           			$("#regist_confirm_Email_flag").show();
				           			$("#regist_confirm_email_prompt").html("<b></b>" + data.msg);
				           			$("#regist_confirm_email_prompt").fadeIn();
				           			break;
								default:
									alert("注册失败！");
									break;
								
							}
						},
						error: function(data){
							window.location.href = "registUI.html";
						}
					});	
			    }else{
		    		$("#ck_prompt").html("<b></b>请阅读《安全帮用户协议》");
					$("#ck_prompt").fadeIn();
			    }
		    }else {
		    	//刷新验证码
		    	checkNumberImage();
		    	$("#checkNumber1").val("");
		    	if (checkCheckNumber1 == 1) {
					$("#checkNumber1_flag").hide();
					$("#checkNumber1_prompt").fadeOut();
		    	
		    	}
		    }
	    }, 100);

}



//跟换验证码
function checkNumberImage(){
	var imageNumber = document.getElementById("imageNumber");
	imageNumber.src = getRootPath()+"/image.jsp?timestamp="+new Date().getTime();
}
 
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
 

	