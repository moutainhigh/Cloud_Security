//添加省下拉框
function putOption(obj){
	var list = obj;
	for (var i = 0; i < list.length; i++){
		$("#districtId").append( "<option value=\""+ (i+1) +"\">"+list[i].name+"</option>" );
		$("#editDistrictId").append( "<option value=\""+ (i+1) +"\">"+list[i].name+"</option>" );
	}
}

function getCitys(districtId){
	if(districtId=="-1"){
		$("#city").empty();
		var i = -1;
		$("#city").append( "<option value=\""+i+"\">"+"请选择城市"+"</option>" );
		$("#city").attr('disabled','disabled');
	}else{
		
	//查询省对应的市  
	 $.ajax({
		 	data: {"districtId":districtId},
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "getCityList.html", 
	        success: function(obj){
	        	putCityOption(obj);
	        	
	     	}
		});
	}
}

function getEditCitys(districtId){
	if(districtId=="-1"){
		$("#editCity").empty();
		var i = -1;
		$("#editCity").append( "<option value=\""+i+"\">"+"请选择城市"+"</option>" );
		$("#editCity").attr('disabled','disabled');
	}else{
		
	//查询省对应的市  
	 $.ajax({
		 	data: {"districtId":districtId},
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "getCityList.html", 
	        success: function(obj){
	        	putEditCityOption(obj);
	        	
	     	}
		});
	}
}

//添加市下拉框
function putCityOption(obj){
	//清空城市下拉框
	$("#city").empty();
	var list = obj;
	if(list != null && list.length > 0){
			
		for (var i = 0; i < list.length; i++){
			$("#city").append( "<option value=\""+list[i].id+"\">"+list[i].name+"</option>" );
		}
		$("#city").removeAttr("disabled");
	}
}

//添加市下拉框
function putEditCityOption(obj){
	//清空城市下拉框
	$("#editCity").empty();
	var list = obj;
	if(list != null && list.length > 0){
			
		for (var i = 0; i < list.length; i++){
			$("#editCity").append( "<option value=\""+list[i].id+"\">"+list[i].name+"</option>" );
		}
		$("#editCity").removeAttr("disabled");
	}
}

$(function(){
	var oMark=document.getElementById('box_mark');
	var oLogin =document.getElementById('box_logoIn1');
	//查询省
	 $.ajax({
	        type: "POST",
	        cache: false,
	        dataType: "json",
	        url: "getDistrictListAll.html", 
	        success: function(obj){
	        	putOption(obj);
	     	}
		});
	 
/*	$(".user_ctive").click(function(){
		var _index =$(".user_ctive").index(this);  //获取当前点击按钮
		oMark.style.display ="block";
		oLogin.style.display ="block";
		oMark.style.width = viewWidth() + 'px';
		oMark.style.height = documentHeight() + 'px';
		oLogin.style.left = (viewWidth() - oLogin.offsetWidth)/2 + 'px';
		oLogin.style.top = (viewHeight() - oLogin.offsetHeight)/2-25 + 'px';	
		
		//image.clone(true).appendTo(".box_logoIn");
	
	});*/
	//修改
/*	
	var oMark2=document.getElementById('box_mark');
	var oLogin2 =document.getElementById('box_logoIn_edit');
	
	$(".zc_edit").click(function(){
		var _index =$(".zc_edit").index(this);  //获取当前点击按钮
		var id= $(".zc_edit").eq(_index).attr("id");
		var name= $(".zc_edit").eq(_index).attr("name");
		var addr = $(".zc_edit").eq(_index).attr("addr");
		//add by tangxr 2016-3-3
		var districtId = $(".zc_edit").eq(_index).attr("districtId");
		var city = $(".zc_edit").eq(_index).attr("city");
		var purpose = $(".zc_edit").eq(_index).attr("purpose");
		//end
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
		//add by tangxr 2016-3-3
		$("#hiddenEditDistrictId").val(districtId);
		$("#hiddenEditCity").val(city);
		$("#hiddenEditPurpose").val(purpose);
		//end
		$("#editAssetName").val(name);
		$("#editAssetAddr").val(address[1]);
		//add by tangxr 2016-3-3
//		 $("#editPurpose option[value='"+purpose+"']").attr("selected", true);
		$("#editDistrictId").val(districtId);
		getEditCitys(districtId)
		$("#editPurpose").val(purpose);
		//end
		oMark2.style.display ="block";
		oLogin2.style.display ="block";
		oMark2.style.width = viewWidth() + 'px';
		oMark2.style.height = documentHeight() + 'px';
		oLogin2.style.left = (viewWidth() - oLogin2.offsetWidth)/2 + 'px';
		oLogin2.style.top = (viewHeight() - oLogin2.offsetHeight)/2-25 + 'px';	
		
		//image.clone(true).appendTo(".box_logoIn");
	
	});*/
     
/*		//关闭按钮
		function toClose(){
			var oClose= document.getElementById('close1');
			oClose.onclick = function(){
				oMark.style.display ="none";
				oLogin.style.display ="none";
				//$(".box_logoIn").empty()
				$("#assetName").val("");
				$("#assetAddr").val("");
				$("#districtId").val(-1);
				getCitys(-1);
				$("#purpose").val("");
				$("#assetName_msg").html("");
				$("#assetAddr_msg").html("");
				$("#location_msg").html("");
				$("#assetUsage_msg").html("");
			};
		}*/
/*		function toCloseedit(){
			var oClose= document.getElementById('close_edit');
			oClose.onclick = function(){
				oMark2.style.display ="none";
				oLogin2.style.display ="none";
				//$(".box_logoIn").empty()
				$("#editAssetName_msg").html("");
				$("#editAssetAddr_msg").html("");
				$("#editLocation_msg").html("");
				$("#editAssetUsage_msg").html("");
			};
		}
		
		toClose();
		toCloseedit();*/
		
		/*window.onscroll = window.onresize = function(){
		
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
	
	}*/
})


$(function(){
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
		
		$('.shade').show();
		$('#verification').show();
		$('html').css({overflow:"hidden"});
	
	});
     
/*		//关闭按钮
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
	
	}*/
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