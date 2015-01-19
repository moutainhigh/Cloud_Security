$(document).ready(function(){
	$("#status").val("${status}");
	$("#searchAssetName").val("${name1}");
});
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
	alert(11);
	$("#searchAssetForm").submit();
}