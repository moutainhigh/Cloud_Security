
$(function(){
	var oMark=document.getElementById('box_mark');
	var oLogin =document.getElementById('box_logoIn1');
	$(".user_ctive").click(function(){
		var _index =$(".user_ctive").index(this);  //获取当前点击按钮
		oMark.style.display ="block";
		oLogin.style.display ="block";
		oMark.style.width = viewWidth() + 'px';
		oMark.style.height = documentHeight() + 'px';
		oLogin.style.left = (viewWidth() - oLogin.offsetWidth)/2 + 'px';
		oLogin.style.top = (viewHeight() - oLogin.offsetHeight)/2-25 + 'px';	
		
		//image.clone(true).appendTo(".box_logoIn");
	
	});
	//修该
	
	var oMark2=document.getElementById('box_mark');
	var oLogin2 =document.getElementById('box_logoIn_edit');
	
	$(".zc_edit").click(function(){
		var _index =$(".zc_edit").index(this);  //获取当前点击按钮
		var id= $(".zc_edit").eq(_index).attr("id");
		var name= $(".zc_edit").eq(_index).attr("name");
		var addr = $(".zc_edit").eq(_index).attr("addr");
		var arr = new Array();
		arr=addr.split(":"); //字符分割
	    $('[name="addrType"]:radio').each(function() {
	    	if (this.value ==arr[0] ) {
	    		this.checked = true;
	    		} 
	    	}); 
	    var address = new Array();
	    address =addr.split("//"); 
		$("#hiddenEditAssetid").val(id);
		$("#hiddenEditAssetName").val(name);
		$("#hiddenEditAssetAddr").val(address[1]);//设置不带   http://  的地址
		$("#editAssetName").val(name);
		$("#editAssetAddr").val(address[1]);
		oMark2.style.display ="block";
		oLogin2.style.display ="block";
		oMark2.style.width = viewWidth() + 'px';
		oMark2.style.height = documentHeight() + 'px';
		oLogin2.style.left = (viewWidth() - oLogin2.offsetWidth)/2 + 'px';
		oLogin2.style.top = (viewHeight() - oLogin2.offsetHeight)/2-25 + 'px';	
		
		//image.clone(true).appendTo(".box_logoIn");
	
	});
     
		//关闭按钮
		function toClose(){
			var oClose= document.getElementById('close1');
			oClose.onclick = function(){
				oMark.style.display ="none";
				oLogin.style.display ="none";
				//$(".box_logoIn").empty()
				$("#assetName").val("");
				$("#assetAddr").val("");
				$("#assetName_msg").html("");
				$("#assetAddr_msg").html("");
			};
		}
		function toCloseedit(){
			var oClose= document.getElementById('close_edit');
			oClose.onclick = function(){
				oMark2.style.display ="none";
				oLogin2.style.display ="none";
				//$(".box_logoIn").empty()
				
			};
		}
		
		toClose();
		toCloseedit();
		
		window.onscroll = window.onresize = function(){
		
		var oDiv = document.getElementById('box_logoIn1');
		var oDiv2 = document.getElementById('box_logoIn1');
		if(oDiv){
			oDiv.style.left = (viewWidth() - oDiv.offsetWidth)/2 + 'px';
			oDiv.style.top = (viewHeight() - oDiv.offsetHeight)/2-25 + 'px';
		};
		if(oDiv2){
			oDiv2.style.left = (viewWidth() - oDiv.offsetWidth)/2 + 'px';
			oDiv2.style.top = (viewHeight() - oDiv.offsetHeight)/2-25 + 'px';
		}
	
	}
})


$(function(){
	var oMark1=document.getElementById('box_mark');
	var oLogin1 =document.getElementById('box_logoIn2');
	
	$(".zican_top").click(function(){
		var _index =$(".zican_top").index(this);  //获取当前点击按钮
		//将资产的值带到资产验证div中
		var id= $(".zican_top").eq(_index).attr("id");
		var name= $(".zican_top").eq(_index).attr("name");
		var addr = $(".zican_top").eq(_index).attr("addr");
		$("#verificationName").html("请验证资产"+name+"的权限");
		$("#hiddenId").val(id);
		$("#hiddenAddr").val(addr);
		var ss=document.getElementById("c");
		ss.href=addr;
		oMark1.style.display ="block";
		oLogin1.style.display ="block";
		oMark1.style.width = viewWidth() + 'px';
		oMark1.style.height = documentHeight() + 'px';
		oLogin1.style.left = (viewWidth() - oLogin1.offsetWidth)/2 + 'px';
		oLogin1.style.top = (viewHeight() - oLogin1.offsetHeight)/2-25 + 'px';	
		//image.clone(true).appendTo(".box_logoIn");
	
	});
     
		//关闭按钮
		function toClose(){
			var oClose1= document.getElementById('close2');
			oClose1.onclick = function(){
				oMark1.style.display ="none";
				oLogin1.style.display ="none";
				//$(".box_logoIn").empty()
				
			};
		}
		toClose();
		
		window.onscroll = window.onresize = function(){
		
		var oDiv1 = document.getElementById('box_logoIn2');
		if(oDiv1){
			oDiv1.style.left = (viewWidth() - oDiv1.offsetWidth)/2 + 'px';
			oDiv1.style.top = (viewHeight() - oDiv1.offsetHeight)/2-25 + 'px';
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