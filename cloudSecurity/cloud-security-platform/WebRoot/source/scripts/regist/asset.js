function saveAsset() {
	var assetName = $("#assetName").val();
alert(assetName);
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
//资产验证
function verificationAsset(){
	
}
