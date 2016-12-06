$(function(){
	
	//修该
	var oMark2=document.getElementById('box_mark');
	var oLogin2 =document.getElementById('box_logoIn_edit');
	
	$(".zc_edit").click(function(){
		var _index =$(".zc_edit").index(this);  //获取当前点击按钮
		var id= $(".zc_edit").eq(_index).attr("id");
		var name= $(".zc_edit").eq(_index).attr("name");
		var pwd = $(".zc_edit").eq(_index).attr("pwd"); 
		$("#hiddenEditUserid").val(id);
		$("#hiddenEditUserName").val(name);
		$("#hiddenEditUserPwd").val(pwd);
		
//		$("#editAssetName").val(name);
//		$("#editAssetAddr").val(pwd);
		oMark2.style.display ="block";
		oLogin2.style.display ="block";
		oMark2.style.width = viewWidth() + 'px';
		oMark2.style.height = documentHeight() + 'px';
		oLogin2.style.left = (viewWidth() - oLogin2.offsetWidth)/2 + 'px';
		oLogin2.style.top = (viewHeight() - oLogin2.offsetHeight)/2-25 + 'px';	
		
	});
     
		//关闭按钮
		function toCloseedit(){
			var oClose= document.getElementById('close_edit');
			oClose.onclick = function(){
				oMark2.style.display ="none";
				oLogin2.style.display ="none";
				$("#opassword").val("");
				$("#regist_password").val("");
				$("#regist_confirm_password").html("");
				$("#editPassword_msg").html("");
				//$(".box_logoIn").empty()
				
			};
		}
		toCloseedit();
		
})

//获取浏览器可视区的宽度和高度
function viewWidth(){
	return document.documentElement.clientWidth;
}
function viewHeight(){
	return document.documentElement.clientHeight;
}
function documentHeight(){
	return Math.max(document.documentElement.scrollHeight || document.body.scrollHeight,document.documentElement.clientHeight);
}
function scrollY(){
	return document.documentElement.scrollTop || document.body.scrollTop;
}