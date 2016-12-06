window.onload =function(){
 $(':input','#passForm').not(':button, :submit, :reset, :hidden').val('');
 $('#imageNumber').click(function(){checkNumberImage()});
}
//跟换验证码
function checkNumberImage(){
	var imageNumber = document.getElementById("imageNumber");
	imageNumber.src = getRootPath()+"/image.jsp?timestamp="+new Date().getTime();
}



//提交
function confimPass(){
	 
	  	  var p1=$("#regist_password").val();//获取密码框的值
		  var p2=$("#regist_confirm_password").val();//获取重新输入的密码值
		  var checkNumber = $("#checkNumber1").val();
		  if(p2!=null&&p2!=""&&p1!=null&&p1!=""){
			  //验证新密码
			  checkPassword();
			  if(checkPassword1==1){
				  //验证确认密码
				  checkConfirmPassword();
				  if (checkConfirmPassword1==1) {
						
					  if(checkNumber==""||checkNumber==null){
						  $("#checkNumber1_flag").attr("class","error");
						  $("#checkNumber1_flag").show();
					   	  $("#checkNumber1_prompt").html("<b></b>验证码不能为空");
					   	  $("#checkNumber1_prompt").fadeIn();
					  }else{
						  
						  $.ajax({
					           type: "POST",
					           url: "regist_checkNumber.html",
					           data: {"checkNumber":checkNumber},
					           dataType:"json",
					           success: function(data){
					           		if(!data.flag){
					           			$("#checkNumber1_flag").attr("class","error");
					           			$("#checkNumber1_flag").show();
					               		$("#checkNumber1_prompt").html("<b></b>验证码填写错误");
					               		$("#checkNumber_prompt").fadeIn();
					               		//刷新验证码
					               		checkNumberImage();
					           		}else{
					           			$("#checkNumber1_flag").attr("class","right");
					           			$("#checkNumber1_flag").show();
					               		$("#checkNumber1_prompt").html("<b></b>");
					               		$("#checkNumber1_prompt").fadeOut();
										$("#passForm").submit();
					           		}
					           }
					        }); 
	
	
					  }
				  }
			  }
		  }else if(p1==null||p1==""){
				$("#regist_password_flag").attr("class","error");
				$("#regist_password_flag").show();
				$("#regist_password_prompt").fadeIn();
		  }else{
			   	$("#regist_confirm_password_flag").attr("class","error");
			   	$("#regist_confirm_password_flag").show();
			   	$("#regist_confirm_password_prompt").fadeIn();
		  }
	  }


//校验密码	
 function checkPassword(){
 	var p1 = $("#regist_password").val();
 	if(p1==""){
 		$("#regist_password_flag").attr("class","error");
 		$("#regist_password_flag").show();
 		$("#regist_password_prompt").fadeIn();
 		checkPassword1 = 0;
 	}else if(p1.length<6||p1.length>20){
 			$("#regist_password_flag").attr("class","error");
 			$("#regist_password_flag").show();
 			$("#regist_password_prompt").fadeIn();
 			checkPassword1 = 0;
 	}else{
 		var number = passwordLevel(p1);
 		//请至少使用两种字符组合
 		if(number<2){
 			$("#regist_password_flag").attr("class","error");
 			$("#regist_password_flag").show();
 			$("#regist_password_prompt").fadeIn();
 			checkPassword1 = 0;
 		}else{
 			$("#regist_password_flag").attr("class","right");
 			$("#regist_password_flag").show();
 			$("#regist_password_prompt").fadeOut();
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
 			$("#regist_confirm_password_prompt").fadeOut();
 			$("#regist_confirm_password_prompt").html("<b></b>支持6-20位,且至少两种字符组合(大小写字母/数字/字符)");
 		    checkConfirmPassword1 = 1;
 		  }
 	  }else{
 		   	$("#regist_confirm_password_flag").attr("class","error");
 		   	$("#regist_confirm_password_flag").show();
 		   	$("#regist_confirm_password_prompt").html("<b></b>支持6-20位,且至少两种字符组合(大小写字母/数字/字符)");
 		   	$("#regist_confirm_password_prompt").fadeOut();
 	  }

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

