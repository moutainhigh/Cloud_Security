//获取资产列表
/*function getUserAssets(){
	$.post("userAssets.html",{},function(data){
		$("#assetsTable").html("");
		$("#assetsTable").append(data);
		//设置tab的显示
		setTabShow();
	});     
}*/

function saveAsset() {
	var assetName =$("#assetName").val();
	var assetAddr = $("#assetAddr").val();
     var addrType = $('input:radio[name="addrType"]:checked').val();
     var purpose = $("#purpose").val();
     var prov = $("#districtId").val();
     var patrn=/[`~@#$%^&*()+<>"{},\\;'[\]]/im;  
	//获取选中的radio的值
	if(assetName == null || assetName == ""){
		$("#assetName_msg").html("请输入资产名称");
	}else if(patrn.test(assetName)){
		$("#assetName_msg").html("您输入的资产名称含有非法字符");
	}else if(assetName.length>25){
		$("#assetName_msg").html("资产名称长度不能超过25个字符！");
	}else if(patrn.test(assetAddr)){
		$("#assetAddr_msg").html("您输入的资产地址含有非法字符");
	}else if(assetAddr==null || assetAddr == ""){
			$("#assetName_msg").html("");
			$("#assetAddr_msg").html("请输入资产地址");
	}else if(assetAddr.length>50){
			 $("#assetName_msg").html("");
			 $("#assetAddr_msg").html("资产地址长度不能超过50个字符！");
	}else if(assetAddr.indexOf("gov.cn")!=-1){
		   $("#assetName_msg").html("");
		   $("#assetAddr_msg").html("输入资产地址不能包含'gov.cn'！");
	}else if((addrType.length==4 && assetAddr.substring(0,5)=='https') || (addrType.length==5 && assetAddr.substring(0,5)=='http:')){
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("资产类型与资产地址填写不一致!");
	}else if(prov == -1){
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("");
		$("#location_msg").html("请选择资产所在物理地址！");
	}else if(purpose==-1){
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("");
		$("#location_msg").html("");
		$("#assetUsage_msg").html("请选择资产用途！");
	}else{
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("");
		$("#location_msg").html("");
		$("#assetUsage_msg").html("");
			//验证资产是否重复
			$.ajax({
		        type: "POST",
		        url: "asset_addrIsExist.html",
		        data: {"addr":assetAddr,"name": encodeURI(assetName),"addrType":addrType},
		        dataType:"json",
		        success: function(data){
		            if(data.msg=='1'){
		            	$("#assetName_msg").html("资产名称重复!");
		            }else if(data.msg=='2'){
		            	$("#assetName_msg").html("");
		            	$("#assetAddr_msg").html("资产地址重复!");
		            }else{
		            	$("#assetName_msg").html("");
		            	$("#assetAddr_msg").html("");
		            	//资产数验证
		            	$.ajax({
		    		        type: "POST",
		    		        url: "asset_CountOver.html",
		    		        data: {},
		    		        dataType:"json",
		    		        success: function(data){
		    		            if(data.msg){
		    		            	alert("免费用户管理资产数不能大于" + data.allowCount);
		    		            }else{
		    		            	$("#saveAsset").submit();
		    		            }
		    		        },
		    		     }); 
		            }
		        },
		     }); 
		}
	
}
function searchAssetCombine(){
	//$("#searchAssetForm").submit();
	var searchAssetName = $("#searchAssetName").val();
	$.post("searchAssetCombine.html",{"searchAssetName":searchAssetName},function(data){
		$("#assetsTable").html("");
		$("#assetsTable").append(data);
		//设置tab的显示
		setTabShow();
	}); 

	
	
}
//提取修改信息
function editAssetUI(str){
	$(".editMsg").html("");
	var arr = str.split(',');
	$("#hiddenEditName").val(arr[1]);
	$("#hiddenEditAddr").val(arr[2]);
	$("#editAssetName").val(arr[1]);
	$("#editAssetAddr").val(arr[2]);
	$("#editDistrictId").val(arr[3]);
	getEditCitys(arr[3]);
	var temp = arr[4];

	$("#editPurpose").val(arr[5]);
	$("#editAssetid").val(arr[0]);
	setTimeout(function(){
		$("#editCity").val(temp);
	},100);

	$('.shade').show();
	$('#updateAssest').show();
	$('html').css({overflow:"hidden"});
}
//修改资产
function editAsset(){
	var oldAssetName = $("#hiddenEditName").val();
	var oldAssetAddr = $("#hiddenEditAddr").val();
	var assetName =$("#editAssetName").val();
	var assetAddr = $("#editAssetAddr").val();
    var addrType = $('input:radio[name="addrType"]:checked').val();
    var purpose = $("#editPurpose").val();
    var prov = $("#editDistrictId").val();
    var city = $("#editCity").val();
    var id = $("#editAssetid").val();
//     var patrn=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im; 
     var patrn=/[`~@#$%^&*()+<>"{},\\;'[\]]/im;  
//     if(patrn.test(assetAddr)){  
//         alert("提示信息：您输入的资产地址含有非法字符！");  
//         return false;     
//     }     
	//获取选中的radio的值
	if(assetName == null || assetName == ""){
		$("#editAssetName_msg").html("请输入资产名称");
	}else if(patrn.test(assetName)){
		$("#editAssetName_msg").html("您输入的资产名称含有非法字符");
	}else if(assetName.length>25){
		$("#editAssetName_msg").html("资产名称长度不能超过25个字符！");
	}else if(patrn.test(assetAddr)){
		$("#editAssetAddr_msg").html("您输入的资产地址含有非法字符");
	}else if(assetAddr==null || assetAddr == ""){
		$("#editAssetAddr_msg").html("请输入资产地址");
	}else if(assetAddr.length>50){
		 $("#editAssetAddr_msg").html("资产地址长度不能超过50个字符！");
	}else if(assetAddr.indexOf("gov.cn")!=-1){
	   $("#editAssetAddr_msg").html("输入资产地址不能包含'gov.cn'！");
	}else if((addrType.length==4 && assetAddr.substring(0,5)=='https') || (addrType.length==5 && assetAddr.substring(0,5)=='http:')){
		$("#editAssetAddr_msg").html("资产类型与资产地址填写不一致!");
	}else if(prov == -1){
		$("#editAssetName_msg").html("");
		$("#editAssetAddr_msg").html("");
		$("#editLocation_msg").html("请选择资产所在物理地址！");
	}else if(purpose==-1){
		$("#editAssetName_msg").html("");
		$("#editAssetAddr_msg").html("");
		$("#editLocation_msg").html("");
		$("#editAssetUsage_msg").html("请选择资产用途！");
	}else{
		$("#editAssetName_msg").html("");
		$("#editAssetAddr_msg").html("");
		$("#editLocation_msg").html("");
		$("#editAssetUsage_msg").html("");
		
		//验证资产是否重复
		$.ajax({
	        type: "POST",
	        url: "asset_addrIsExist.html",
	        data: {"addr":assetAddr,"name": encodeURI(assetName),"addrType":addrType,"oldName":oldAssetName,"oldAddr":oldAssetAddr},
	        dataType:"json",
	        success: function(data){
	            if(data.msg=='1'){
	            	$("#editAssetName_msg").html("资产名称重复!");
	            }else if(data.msg=='2'){
	            	$("#editAssetName_msg").html("");
	            	$("#editAssetAddr_msg").html("资产地址重复!");
	            }else{
	            	$("#editAssetName_msg").html("");
	            	$("#editAssetAddr_msg").html("");

	       		 var options = {
	 					url:'editAsset.html',
	 					data:{
	 			 		'id':id,
	  	               'assetName':assetName,
	  	               'assetAddr':assetAddr,
	  	               'addrType':addrType,
	  	               'purpose':purpose,
	  	               'prov':prov,
	  	               'city':city
	 					},
	 					//beforeSubmit:showRequest,
	 					success: function(data) {
	 						if(data.successFlag){
	 							alert("修改成功!");
	 							$('.popBoxhide').hide();
	 							$('.shade').hide();
	 							$('html').css({overflow:'auto'})

	 							//刷新页面
	 							$.post("userAssets.html",{},function(data){
	 								$("#assetsTable").html("");
	 								$("#assetsTable").append(data);
	 								//设置tab的显示
	 								setTabShow();
	 							}); 
	 						}else{
	 							alert("修改失败!");
	 						}
	 								
	 					},
	 					error: function(data){
	 						alert("err:"+data);
	 					}
	 				};
			 		 // 将options传给ajaxForm
			 		 $('#editAsset').ajaxSubmit(options);
	            }
	        },
	     }); 
		

	}
	
}
//删除资产
function deleteAsset(id){
	var assetId = id;
	//检查订单资产表里是否有此项记录，若有：则提示不能删除，若无：则可以删除
	$.post("checkdelete.html", {"id" : assetId}, function(data, textStatus) {
		if (data.count>0){
			alert("您的订单中包含此资产，不能删除！");
			return false;
		}else{
			if (window.confirm("确实要删除吗?")==true) {
				//window.location.href="deleteAsset.html?id="+assetId;
				
			$.ajax({
		        type: "POST",
		        url: "deleteAsset.html",
		        data: {"id":assetId},
		        dataType:"json",
		        success: function(data){
		        	if(data.successFlag){
		        		//刷新页面
		        		$.post("userAssets.html",{},function(data){
							$("#assetsTable").html("");
							$("#assetsTable").append(data);
							//设置tab的显示
							setTabShow();
						});
		        	}else{
		        		alert("删除失败!");
		        	}
		        		
		        },
		     }); 
				
			} else {
				return;
			}
		}
	});
}
//隐藏显示div
function showAndHiddenRadio(){
	var codeStyle = "";
	var zt = document.getElementsByName("verification_msg");
	for(var i=0;i<zt.length;i++){ 
			if(zt[i].checked) { 
				codeStyle = zt[i].value;
			} 
		}
	//隐藏div
	if(codeStyle==="codeVerification"){
		 document.getElementById("codeVerificationID").style.display="block";
		 document.getElementById("fileVerificationID").style.display="none";
		 document.getElementById("manualVerificationID").style.display="none";
		 document.getElementById("radioDiv").style.display="block";
		 $("#submitId").show();
	}
	if(codeStyle==="fileVerification"){
		 document.getElementById("radioDiv").style.display="block";
		document.getElementById("codeVerificationID").style.display="none";
		document.getElementById("manualVerificationID").style.display="none";
		document.getElementById("fileVerificationID").style.display="block";
		$("#submitId").show();
	}
	if(codeStyle==="manualVerification"){
		 document.getElementById("radioDiv").style.display="block";
		document.getElementById("codeVerificationID").style.display="none";
		document.getElementById("fileVerificationID").style.display="none";
		document.getElementById("manualVerificationID").style.display="block";
		$("#submitId").hide();
	}
}

//资产验证界面
function verificationAssetUI(str){
	var arr = str.split(',');
	var id= arr[0];
	var name= arr[1];
	var addr = arr[2];
	$("#verificationName").html("请验证资产"+name+"的权限");
	$("#hiddenId").val(id);
	$("#hiddenAddr").val(addr);
	var ss=document.getElementById("c");
	ss.href=addr;
	
	$('.shade').show();
	$('#verification').show();
	$('html').css({overflow:"hidden"});
}
//资产验证
function verificationAsset(){
	var id = $("#hiddenId").val();
	var codeStyle = "";
	var zt = document.getElementsByName("verification_msg");
	for(var i=0;i<zt.length;i++){ 
			if(zt[i].checked) { 
				codeStyle = zt[i].value;
			} 
		}
//	var code1 = document.getElementById('code').innerHTML;
	var code1 = $("#code").val();
	$.ajax({
        type: "POST",
        url: "asset_verification.html",
        data: {"code1":code1,"id":id,"codeStyle":codeStyle},
        dataType:"json",
        success: function(data){
            if(data.msg=="1"&&data.status=="0"){
            	alert("验证成功");
            	$("#verificationAssetForm").submit();
            }else{
            	alert("验证失败");
            	return;
            }
        },
     }); 
}
//复制文本
function copyToClipBoard(){ 
//	var clipBoardContent=''; 
//	clipBoardContent+=document.getElementById('code').innerHTML; 
//	alert(clipBoardContent);
//	window.clipboardData.setData("Text",clipBoardContent); 
//	$("#copyToClipBoard").html("已复制");
    var clipBoardContent="";
    clipBoardContent+=document.getElementById('code').innerHTML;
    if(window.clipboardData){ 
           window.clipboardData.clearData(); 
           window.clipboardData.setData("Text", clipBoardContent);
    }else if(navigator.userAgent.indexOf("Opera") != -1){ 
           window.location = clipBoardContent; 
    }else if (window.netscape){ 
           try{ 
                  netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
           }catch (e){ 
                  alert("您的当前浏览器设置已关闭此功能！请按以下步骤开启此功能！\n新开一个浏览器，在浏览器地址栏输入'about:config'并回车。\n然后找到'signed.applets.codebase_principal_support'项，双击后设置为'true'。\n声明：本功能不会危极您计算机或数据的安全！"); 
           } 
           var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard); 
           if (!clip) return; 
           var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable); 
           if (!trans) return; 
           trans.addDataFlavor('text/unicode'); 
           var str = new Object(); 
           var len = new Object(); 
           var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString); 
           var copytext = clipBoardContent; 
           str.data = copytext; 
           trans.setTransferData("text/unicode",str,copytext.length*2); 
           var clipid = Components.interfaces.nsIClipboard; 
           if (!clip) return false; 
           clip.setData(trans,null,clipid.kGlobalClipboard); 
    }
    alert("已成功复制！");
    return true;
	} 

//手动设置tab表格的显示
function setTabShow(){
	 $('.navlist li').each(function() {
	    	var zindex=$(this).index();
	    	var temp = $(this).attr("class");

	    	if(temp=='this active'){
	    		$(".tabBox .not-used").eq(zindex).show().siblings().hide();	
	    	}
	    });
}