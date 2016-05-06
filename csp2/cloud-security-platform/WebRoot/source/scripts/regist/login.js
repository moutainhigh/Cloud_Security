// JavaScript Document
window.onload =function(){

 $('#imageNumber').click(function(){checkNumberImage()});
}

//跟换验证码
function checkNumberImage(){
	var imageNumber = document.getElementById("imageNumber");
	imageNumber.src = getRootPath()+"/image.jsp?timestamp="+new Date().getTime();
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
                }else{
            		$("#login_name_flag").attr("class","right");
            		$("#login_name_flag").show();
            		$("#login_name_prompt").html("<b></b>");
            		$("#login_name_prompt").fadeOut();
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

function loginSubmit(){
	var name = $("#login_name").val();
	var password = $("#login_password").val();
	var checkNumber = $("#checkNumber").val();

	if(name==null||name==""){
		$("#login_name_flag").attr("class","error");
		$("#login_name_flag").show();
		$("#login_name_prompt").html("<b></b>用户名不能为空!");
		$("#login_name_prompt").fadeIn();
		return;
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
            		return;
                }else{
            		$("#login_name_flag").attr("class","right");
            		$("#login_name_flag").show();
            		$("#login_name_prompt").html("<b></b>");
            		$("#login_name_prompt").fadeOut();
                }
            },
         }); 
	}
	if(password==null||password==""){
		$("#login_password_flag").attr("class","error");
		$("#login_password_flag").show();
		$("#login_password_prompt").html("<b></b>密码不能为空!");
		$("#login_password_prompt").fadeIn();
		return;
	}else{
		$("#login_password_flag").attr("class","right");
		$("#login_password_flag").show();
		$("#login_password_prompt").html("<b></b>");
		$("#login_password_prompt").fadeOut();
	}
	if(checkNumber==null||checkNumber==""){
		$("#checkNumber_flag").attr("class","error");
		$("#checkNumber_flag").show();
		$("#checkNumber_prompt").html("<b></b>验证码不能为空");
		$("#checkNumber_prompt").fadeIn();
		return;
	}else{
				$.ajax({
		            type: "POST",
					url:'login.html',
					data:{
 	               'name':name,
 	               'password':password,
 	               'checkNumber':checkNumber
					},
					dataType:"json",
					//beforeSubmit:showRequest,
					success: function(data) {
						switch(data.result){
						case 1:
							$("#login_name_flag").attr("class","error");
							$("#login_name_flag").show();
							$("#login_name_prompt").html("<b></b>对不起，您的帐号已停用!");
							$("#login_name_prompt").fadeIn();
							break;
						case 2:
		            		$("#login_name_flag").attr("class","right");
		            		$("#login_name_flag").show();
		            		$("#login_name_prompt").html("<b></b>");
		            		$("#login_name_prompt").fadeOut();
							$("#login_password_flag").attr("class","error");
							$("#login_password_flag").show();
							$("#login_password_prompt").html("<b></b>密码输入有误!");
							$("#login_password_prompt").fadeIn();
							break;
						case 3:
		            		$("#login_name_flag").attr("class","right");
		            		$("#login_name_flag").show();
		            		$("#login_name_prompt").html("<b></b>");
		            		$("#login_name_prompt").fadeOut();
		            		$("#login_password_flag").attr("class","right");
		            		$("#login_password_flag").show();
		            		$("#login_password_prompt").html("<b></b>");
		            		$("#login_password_prompt").fadeOut();
							$("#checkNumber_flag").attr("class","error");
							$("#checkNumber_flag").show();
							$("#checkNumber_prompt").html("<b></b>验证码输入有误!");
							$("#checkNumber_prompt").fadeIn();
							break;
						case 4:
							$("#login_name_flag").attr("class","error");
							$("#login_name_flag").show();
							$("#login_name_prompt").html("<b></b>对不起，企业用户不允许登录!");
							$("#login_name_prompt").fadeIn();
							break;
						case 5:
		            		$("#login_name_flag").attr("class","right");
		            		$("#login_name_flag").show();
		            		$("#login_name_prompt").html("<b></b>");
		            		$("#login_name_prompt").fadeOut();
		            		$("#login_name_flag").attr("class","right");
		            		$("#login_name_flag").show();
		            		$("#login_name_prompt").html("<b></b>");
		            		$("#login_name_prompt").fadeOut();
		            		$("#login_password_flag").attr("class","right");
		            		$("#login_password_flag").show();
		            		$("#login_password_prompt").html("<b></b>");
		            		$("#login_password_prompt").fadeOut();
							window.location.href="selfHelpOrderInit.html";
							break;
						case 6:
		            		$("#login_name_flag").attr("class","right");
		            		$("#login_name_flag").show();
		            		$("#login_name_prompt").html("<b></b>");
		            		$("#login_name_prompt").fadeOut();
		            		$("#login_name_flag").attr("class","right");
		            		$("#login_name_flag").show();
		            		$("#login_name_prompt").html("<b></b>");
		            		$("#login_name_prompt").fadeOut();
		            		$("#login_password_flag").attr("class","right");
		            		$("#login_password_flag").show();
		            		$("#login_password_prompt").html("<b></b>");
		            		$("#login_password_prompt").fadeOut();
							map.put("apiId", apiId);
							map.put("indexPage", indexPage);
							var  apiId = data.apiId;
							var indexPage = data.indexPage;
							window.location.href="selfHelpOrderAPIInit.html?apiId="+apiId+"&indexPage="+indexPage;
							break;
						case 7:
		            		$("#login_name_flag").attr("class","right");
		            		$("#login_name_flag").show();
		            		$("#login_name_prompt").html("<b></b>");
		            		$("#login_name_prompt").fadeOut();
		            		$("#login_name_flag").attr("class","right");
		            		$("#login_name_flag").show();
		            		$("#login_name_prompt").html("<b></b>");
		            		$("#login_name_prompt").fadeOut();
		            		$("#login_password_flag").attr("class","right");
		            		$("#login_password_flag").show();
		            		$("#login_password_prompt").html("<b></b>");
		            		$("#login_password_prompt").fadeOut();
							window.location.href="userCenterUI.html";
							break;
						case 8:
							window.location.href="loginUI.html";
							break;
						default:
							break;
						}
					},
					error: function(data){
						alert("err:"+data);
					}
					});

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