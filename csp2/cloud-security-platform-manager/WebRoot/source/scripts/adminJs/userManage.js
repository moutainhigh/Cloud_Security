var checkName1 = 0;
var checkPassword1 = 0;
var checkConfirmPassword1 = 0;

//校验用户名
function checkName(){
	var name = $("#regist_name").val();
	var	pattern	= /^[a-zA-Z0-9_]{4,20}$/;
	var flag = pattern.test(name);
	if(name==""||name==null){
		$("#regist_name_msg").html("用户名不能为空");
		checkName1=0;
	}else{
		if(flag){
			$.ajax({
               type: "POST",
               url: "/cloud-security-platform-manager/regist_checkName.html",
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
	
	if(p1==""||p1==null){
		$("#regist_password_msg").html("密码不能为空");
		checkPassword1 = 0;
	}else{
		if(p1.length<6||p1.length>20){
			$("#regist_password_msg").html("请输入6-20位，支持中英文，数字，字符组合");
			checkPassword1 = 0;
		}else{
			$("#regist_password_msg").html("");
			checkPassword1 = 1;
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
//提交
function add(){
	$("#form_regist").submit();
}
//删除
function deleteUser(id){
	var userId = id;
	//检查订单  资产表是否有此用户的 资产和订单，若有：则提示不能删除，若无：则可以删除
	$.post("/cloud-security-platform-manager/adminDeleteCheck.html", {"id" : userId}, function(data, textStatus) {
		if (data.count>0){
			alert("您要删除的用户下有订单或资产，不能删除！");
			return false;
		}else{
			if (window.confirm("确实要删除吗?")==true) {
				window.location.href="/cloud-security-platform-manager/adminDeleteUser.html?id="+userId;
			} else {
				return;
			}
		}
	});
}
//搜索用户
function adminSearch(){
	$("#searchForm").submit();
}