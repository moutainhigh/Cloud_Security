
$(function(){
	var oMark =document.getElementById('modelbox');
	var oLogin =document.getElementById('box_logoIn');
	
	//修改资产
	$('.zc_edit').delegate(this,'click',function(){
		var id = $(this).attr("id");
		var name = $(this).attr("name");
		var addr = $(this).attr("addr");
		var districtId = $(this).attr("districtId");
		var type = $(this).attr("assetType");
		var temp = $(this).attr("city");
		var purpose = $(this).attr("purpose");

				$(".editMsg").html("");
				$(".w634").html("修改项目");
				$("#hiddenEditName").val(name);
				$("#hiddenEditAddr").val(addr);
				$("#editAssetName").val(name);
				$("#editAssetAddr").val(addr);
				$("#editDistrictId").val(districtId);
				
				if (type==0) {
					$("input[name='editAssetType'][value='http']").attr("checked",true); 
				}else if(type==1){
					$("input[name='editAssetType'][value='https']").attr("checked",true);
				}
				


				oMark.style.display ="block";
				oLogin.style.display ="block";
				oMark.style.width = viewWidth() + 'px';
				oMark.style.height = documentHeight() + 'px';
				oLogin.style.left = (viewWidth() - oLogin.offsetWidth)/2 + 'px';
				oLogin.style.top = (viewHeight() - oLogin.offsetHeight)/2-25 + 'px';	
				

	})
	
	
	
	//添加 点击创建项目
	
	$("#add_ser").click(function(){
		var _index =$(".add_ser").index(this);  //获取当前点击按钮
		
		$("#regist_name").val("");
		$("#regist_images").val("");
		$(".filename").val("");
		$("#regist_type").val("");
		$("#regist_startDate").val("");
		$("#regist_endDate").val("");
		
		$("#regist_name_msg").html("");
		$("#regist_image_msg").html("");
		$("#regist_date_msg").html("");
		$("#regist_type_msg").html("");
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


//关闭按钮
function toClose(){
	var oClose = document.getElementById('close');
	oClose.onclick = function(){
		oMark.style.display ="none";
		oLogin.style.display ="none";
		//$(".box_logoIn").empty()
		
	};
}



//删除
function deleteMon(id){}

//修改
function AlterMon(id){}

//立即创建
function add(){}