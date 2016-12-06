// JavaScript Document

$(function(){
	var oMark2 =document.getElementById('modelbox2');
	var oLogin2 =document.getElementById('box_logoIn2');
	
	$(".add_change").click(function(){
		var _index =$(".add_change").index(this);  //获取当前点击按钮
		var id= $(".add_change").eq(_index).attr("id");
		var name= $(".add_change").eq(_index).attr("name");
		var realName= $(".add_change").eq(_index).attr("realName");
		var type= $(".add_change").eq(_index).attr("type");
		$("#hiddenEditUserid").val(id);
		$("#editUseName").val(name);
		$("#editRealName").val(realName);
		$("#editType").val(type);
		//var image=$(this).parent().find("a img");
		//$(".box_logoIn").empty()
		oMark2.style.display ="block";
		oLogin2.style.display ="block";
		oMark2.style.width = viewWidth() + 'px';
		oMark2.style.height = documentHeight() + 'px';
		oLogin2.style.left = (viewWidth() - oLogin2.offsetWidth)/2 + 'px';
		oLogin2.style.top = (viewHeight() - oLogin2.offsetHeight)/2-25 + 'px';	
		
		//image.clone(true).appendTo(".box_logoIn");
	
	})
     
		//关闭按钮
		function toClose(){
			var oClose = document.getElementById('close2');
			oClose.onclick = function(){
				oMark2.style.display ="none";
				oLogin2.style.display ="none";
				//$(".box_logoIn").empty()
				
			};
		}
		toClose();
		
		window.onscroll = window.onresize = function(){
		
		var oDiv2 = document.getElementById('box_logoIn2');
		if(oDiv2){
			oDiv2.style.left = (viewWidth() - oDiv.offsetWidth)/2 + 'px';
			oDiv2.style.top = (viewHeight() - oDiv.offsetHeight)/2-25 + 'px';
		}
	
	}
});
//修改
$(function(){
	var oMark =document.getElementById('modelbox');
	var oLogin =document.getElementById('box_logoIn');
	
	$("#add_ser").click(function(){
		var _index =$(".add_ser").index(this);  //获取当前点击按钮
		//var image=$(this).parent().find("a img");
		//$(".box_logoIn").empty()
		oMark.style.display ="block";
		oLogin.style.display ="block";
		oMark.style.width = viewWidth() + 'px';
		oMark.style.height = documentHeight() + 'px';
		oLogin.style.left = (viewWidth() - oLogin.offsetWidth)/2 + 'px';
		oLogin.style.top = (viewHeight() - oLogin.offsetHeight)/2-25 + 'px';	
		
		//image.clone(true).appendTo(".box_logoIn");
	
	})
     
		//关闭按钮
		function toClose(){
			var oClose = document.getElementById('close');
			oClose.onclick = function(){
				oMark.style.display ="none";
				oLogin.style.display ="none";
				//$(".box_logoIn").empty()
				
			};
		}
		toClose();
		
		window.onscroll = window.onresize = function(){
		
		var oDiv = document.getElementById('box_logoIn');
		if(oDiv){
			oDiv.style.left = (viewWidth() - oDiv.offsetWidth)/2 + 'px';
			oDiv.style.top = (viewHeight() - oDiv.offsetHeight)/2-25 + 'px';
		}
	
	}
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
