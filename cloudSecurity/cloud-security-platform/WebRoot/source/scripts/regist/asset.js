function saveAsset() {
	var assetName = $("#assetName").val();
	var assetAddr = $("#assetAddr").val();
	if(assetName == null || assetName == "" || assetAddr==null || assetAddr == ""){
		if(assetName == null || assetName == ""){
			$("#assetName_msg").html("请输入资产名称");
		}
		if(assetAddr==null || assetAddr == ""){
			$("#assetAddr_msg").html("请输入资产地址");
		}
	}else{
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("");
		$("#saveAsset").submit();
	}
}