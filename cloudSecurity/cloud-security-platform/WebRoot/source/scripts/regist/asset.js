function saveAsset() {
	var assetName = $("#assetName").val();
	var assetAddr = $("#assetAddr").val();
	if(assetName == null || assetName == "" || assetAddr==null || assetAddr == ""){
		if(assetName == null || assetName == ""){
			$("#assetName_msg").html("请输入资产名称");
		}else{
			$("#assetName_msg").html("");
		}
		if(assetAddr==null || assetAddr == ""){
			$("#assetAddr_msg").html("请输入资产地址");
		}else{
			$("#assetAddr_msg").html("");
		}
	}else{
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("");
		$("#saveAsset").submit();
	}
}
function searchAssetCombine(){
	$("#searchAssetForm").submit();
}
//删除资产
function deleteAsset(id){
	var assetId = id;
	//检查订单资产表里是否有此项记录，若有：则提示不能删除，若无：则可以删除
	$.post("/cloud-security-platform/checkdelete.html", {"id" : assetId}, function(data, textStatus) {
		if (data.count>0){
			alert("您的订单中包含此资产，不能删除！");
			return false;
		}else{
			if (window.confirm("确实要删除吗?")==true) {
				window.location.href="/cloud-security-platform/deleteAsset.html?id="+assetId;
			} else {
				return;
			}
		}
	});
}
//隐藏显示div
function showAndHiddenRadio(){
	var codeStyle = $("input[type='radio']:checked").val();
	//隐藏div
	if(codeStyle==="codeVerification"){
		 document.getElementById("fileVerificationID").style.display="none";
		 document.getElementById("codeVerificationID").style.display="block";
	}
	if(codeStyle==="fileVerification"){
		document.getElementById("codeVerificationID").style.display="none";
		document.getElementById("fileVerificationID").style.display="block";
	}
}
//资产验证
function verificationAsset(){
	var id = $("#hiddenId").val();
	var codeStyle = $("input[type='radio']:checked").val();
	var code1 = document.getElementById('code').innerHTML;
	$.ajax({
        type: "POST",
        url: "/cloud-security-platform/asset_verification.html",
        data: {"code1":code1,"id":id,"codeStyle":codeStyle},
        dataType:"json",
        success: function(data){
            if(data.msg=="1"){
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