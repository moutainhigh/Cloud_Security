// JavaScript Document
window.onload =function(){

 $('#imageNumber').click(function(){checkNumberImage()});
}

$(function(){
	$('#login_btn').removeAttr('disabled');
	$('#login_btn').css('background-color','#2499fb');
	$("#login_btn").css("opacity",""); 
	$('#login_btn').click(function(){
		/*function loginSubmit(){*/
			
			var name = $("#login_name").val();
			var password = $("#login_password").val();
			var checkNumber = $("#checkNumber").val();
			//var remeberMe = $("#remeberMe").prop("checked");
			if(name==null||name==""){
				$("#errMsg").html("用户名不能为空!");
				$("#errMsgDiv").show();
				//刷新验证码
				checkNumberImage();
				return;
			}
			if(password==null||password==""){
				$("#errMsg").html("密码不能为空!");
				$("#errMsgDiv").show();
				//刷新验证码
				checkNumberImage();
				return;
			}
			if(checkNumber==null||checkNumber==""){
		    	$("#errMsg").html("验证码不能为空!");
				$("#errMsgDiv").show();
				//刷新验证码
				checkNumberImage();
				return;
			}

			$.ajax({
		        type: "POST",
		        url: "login_checkName.html",
		        data: {"name":name},
		        dataType:"json",
		        success: function(data){
		        	$('#login_btn').attr('disabled',"disabled");
					$("#login_btn").css("opacity","0.4"); 
		            if(data.count<=0){
		        		$("#errMsg").html("用户不存在!");
		        		$("#errMsgDiv").show();
		        		//刷新验证码
						checkNumberImage();
						$('#login_btn').removeAttr('disabled');
						$("#login_btn").css("opacity",""); 
		        		return;
		            }else{
		            	$.ajax({
				            type: "POST",
							url:'login.html',
							data:{
		 	               'name':name,
		 	               'password':password,
		 	               'checkNumber':checkNumber
		 	               //'remeberMe':remeberMe
							},
							dataType:"json",
							//beforeSubmit:showRequest,
							success: function(data) {
								switch(data.result){
								case 1:
							    	$("#errMsg").html("对不起，您的帐号已停用!");
									$("#errMsgDiv").show();
									//刷新验证码
									checkNumberImage();
									$('#login_btn').removeAttr('disabled');
									$("#login_btn").css("opacity",""); 
									break;
								case 2:
									$("#errMsg").html("用户名或密码错误!");
									$("#errMsgDiv").show();
									//刷新验证码
									checkNumberImage();
									$('#login_btn').removeAttr('disabled');
									$("#login_btn").css("opacity",""); 
									break;
								case 3:
									$("#errMsg").html("验证码输入有误!");
									$("#errMsgDiv").show();
									//刷新验证码
									checkNumberImage();
									$('#login_btn').removeAttr('disabled');
									$("#login_btn").css("opacity","");
									break;
								case 4:
//									$("#errMsg").html("对不起，企业用户不允许登录!");
									$("#errMsg").html(data.msg);
									$("#errMsgDiv").show();
									//刷新验证码
									checkNumberImage();
									$('#login_btn').removeAttr('disabled');
									$("#login_btn").css("opacity","");
									break;
								case 5:
									$("#errMsg").html("");
									$("#errMsgDiv").hide();
									var serviceId = data.serviceId;
									var indexPage = data.indexPage;
									if(serviceId==6){
										//window.location.href="wafDetails.html?serviceId="+serviceId+"&indexPage="+indexPage;
										$("#serviceIdWafHidden").val(serviceId);
										$("#indexPageWafHidden").val(indexPage);
										$("#wafDetailsForm").submit();
									}else if (indexPage ==1){
										//window.location.href="selfHelpOrderInit.html?serviceId="+serviceId+"&indexPage="+indexPage;
										$("#serviceIdHidden").val(serviceId);
										$("#indexPageHidden").val(indexPage);
										$("#serviceLoginForm").submit();
									} else if (indexPage ==3) {
										var tempForm = document.createElement("form");
			  							tempForm.action = "systemAnquanbangDetailUI.html";
			  							tempForm.method = "post";
			  							tempForm.style.display = "none";
			  							
			  						   	var indexPageInput = document.createElement("input");
			  							indexPageInput.type="hidden"; 
										indexPageInput.name= "indexPage"; 
										indexPageInput.value= indexPage; 
										tempForm.appendChild(indexPageInput); 
										
										var serviceIdInput = document.createElement("input");
			  							serviceIdInput.type="hidden"; 
										serviceIdInput.name= "serviceId"; 
										serviceIdInput.value= serviceId; 
										tempForm.appendChild(serviceIdInput);
							
										document.body.appendChild(tempForm);
										tempForm.submit();
										document.body.removeChild(tempForm);
									}
									break;
								case 6:
									$("#errMsg").html("");
									$("#errMsgDiv").hide();
									var  apiId = data.apiId;
									var indexPage = data.indexPage;
									//window.location.href="selfHelpOrderAPIInit.html?apiId="+apiId+"&indexPage="+indexPage;
									$("#apiIdHidden").val(apiId);
									$("#indexPageAPIHidden").val(indexPage);
									$("#APILoginForm").submit();
									break;
								case 7:
									$("#errMsg").html("");
									$("#errMsgDiv").hide();
									if (data.afterLoginUrl != null && data.afterLoginUrl!='') {
										window.location.href = data.afterLoginUrl;
									
									}else{
										window.location.href="userCenterUI.html";
									}
									break;
								case 8:
									$("#errMsg").html("");
									$("#errMsgDiv").hide();
									window.location.href="loginUI.html";
									$('#login_btn').removeAttr('disabled');
									$("#login_btn").css("opacity","");
									break;
								default:
									break;
								}
							},
							error: function(data){
								 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
						    		 window.location.href = "loginUI.html"; } 
						    	 else { window.location.href = "loginUI.html"; }
								 } 
							});
		            }
		        },
		     }); 

		})

	/*$('#login_name').keyup(function() {
		//用户名发生改变时判断是否为cookie保存
		var userName = $("#login_name").val();
		$.ajax({
            type: "POST",
            url: "login_checkCookie.html",
            data: {"name":userName},
            dataType:"json",
            success: function(data){
                if(data.cookie){
                	$("#login_password").prop("value",data.password);
                }else{
                	$("#login_password").prop("value","");
                }
            },
         }); 
		});*/
	/*用户名输入框的回车事件：提交登录表单*/
	$("#login_name").keydown(function(e){ 
		var curKey = e.which; 
		if(curKey == 13){ 
		$("#login_btn").click(); 
		return false; 
		} 
	}); 
	
	/*密码输入框的回车事件：提交登录表单*/
	$("#login_password").keydown(function(e){ 
		var curKey = e.which; 
		if(curKey == 13){ 
		$("#login_btn").click(); 
		return false; 
		} 
	});
	
	/*校验码输入框的回车事件：提交登录表单*/
	$("#checkNumber").keydown(function(e){ 
		var curKey = e.which; 
		if(curKey == 13){ 
		$("#login_btn").click(); 
		return false; 
		} 
	});
});

//跟换验证码
function checkNumberImage(){
	var imageNumber = document.getElementById("imageNumber");
	imageNumber.src = getRootPath()+"/image.jsp?timestamp="+new Date().getTime();
}

//用户名发生改变时判断是否为cookie保存
function logiName(){
	var userName = $("#login_name").val();
	$.ajax({
        type: "POST",
        url: "login_checkCookie.html",
        data: {"name":userName},
        dataType:"json",
        success: function(data){
            if(data.cookie){
            	$("#login_password").prop("value",data.password);
            }else{
            	$("#login_password").prop("value","");
            }
        },
     }); 
}
//校验用户名
function checkName(){
	var name = $("#login_name").val();
	if(name==null||name==""){
		$("#login_name_flag").attr("class","error");
		$("#login_name_flag").show();
		$("#login_name_prompt").html("<b></b>用户名不能为空!");
		$("#login_name_prompt").fadeIn();
	}else{		
		$.ajax({
            type: "POST",
            url: "login_checkName.html",
            data: {"name":name},
            dataType:"json",
            success: function(data){
                if(data.count<=0){
            		$("#login_name_flag").attr("class","error");
            		$("#login_name_flag").show();
            		$("#login_name_prompt").html("<b></b>用户名不存在");
            		$("#login_name_prompt").fadeIn();
            		$('#login_btn').removeAttr('disabled');
					$("#login_btn").css("opacity","");
                }else{
            		$("#login_name_flag").attr("class","right");
            		$("#login_name_flag").show();
            		$("#login_name_prompt").html("<b></b>");
            		$("#login_name_prompt").fadeOut();
            		$('#login_btn').removeAttr('disabled');
					$("#login_btn").css("opacity","");
                }
            },
         }); 
	}
}
//校验密码
function checkPassword(){
	var password = $("#login_password").val();
	if(password==null||password==""){
		$("#login_password_flag").attr("class","error");
		$("#login_password_flag").show();
		$("#login_password_prompt").html("<b></b>密码不能为空!");
		$("#login_password_prompt").fadeIn();
	}else{
		$("#login_password_flag").attr("class","right");
		$("#login_password_flag").show();
		$("#login_password_prompt").html("<b></b>");
		$("#login_password_prompt").fadeOut();
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
