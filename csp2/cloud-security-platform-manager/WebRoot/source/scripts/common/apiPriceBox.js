var maxApiPriceIndex = 0;
function editApiPrice(servId){
	maxApiPriceIndex = 0;
	var oMark =document.getElementById('modelbox');
	var apiPriceBox =document.getElementById('box_apiPrice');
	$.ajax({
		type:"POST",
		url:"serviceAPIPriceMaintainUI.html",
		data:{"servId":servId},
		success:function(data) {
			$("#add_serviceApiId").val(servId);
			var priceList = data.priceList;
			var priceTrHtml = $("#apiPrice_table_template").html();
			if(priceList!= null && priceList.length > 0){
				maxApiPriceIndex = priceList.length;
				for(var i = 1;i <= priceList.length;i++) {
					var price = priceList[i-1];
					//元素id,name替换
					var htmlStr = priceTrHtml.replace(new RegExp("apiPrice_index_0",'gm'), "apiPrice_index_"+i);
					htmlStr = htmlStr.replace(new RegExp("apiPrice_type_0",'gm'), "apiPrice_type_"+i);
					htmlStr = htmlStr.replace(new RegExp("apiPrice_price_0",'gm'), "apiPrice_price_"+i);
					htmlStr = htmlStr.replace(new RegExp("apiPrice_timesG_0",'gm'), "apiPrice_timesG_"+i);
					htmlStr = htmlStr.replace(new RegExp("apiPrice_timesLE_0",'gm'), "apiPrice_timesLE_"+i);
					htmlStr = htmlStr.replace(new RegExp("apiPrice_title_timesLE_0",'gm'), "apiPrice_title_timesLE_"+i);
					htmlStr = htmlStr.replace(new RegExp("deleteOneApiPrice(0)",'gm'),"deleteOneApiPrice("+i+")");
					htmlStr = htmlStr.replace(new RegExp("getApiPriceType(this.value,0)",'gm'),"getApiPriceType(this.value,"+i+")");
					$("#apiPrice_table").append(htmlStr);
					//替换 大于
					$("#apiPrice_timesG_"+i).val(price.timesG);
					//替换 小于等于
					$("#apiPrice_timesLE_"+i).val(price.timesLE);
					//替换 价格
					$("#apiPrice_price_"+i).val(price.price);
					
					if (price.timesLE == 0) {   //大于
						//替换 类型
						$("#apiPrice_type_"+i).val(2);
						//小于等于 移除
						$("#apiPrice_title_timesLE_"+i).hide();
						$("#apiPrice_timesLE_"+i).hide();
					}
				}
			}else {
				maxApiPriceIndex = 1;
				//元素id,name替换
				var htmlStr = priceTrHtml.replace(new RegExp("apiPrice_index_0",'gm'), "apiPrice_index_1");
				htmlStr = htmlStr.replace(new RegExp("apiPrice_type_0",'gm'), "apiPrice_type_1");
				htmlStr = htmlStr.replace(new RegExp("apiPrice_price_0",'gm'), "apiPrice_price_1");
				htmlStr = htmlStr.replace(new RegExp("apiPrice_timesG_0",'gm'), "apiPrice_timesG_1");
				htmlStr = htmlStr.replace(new RegExp("apiPrice_timesLE_0",'gm'), "apiPrice_timesLE_1");
				htmlStr = htmlStr.replace(new RegExp("apiPrice_title_timesLE_0",'gm'), "apiPrice_title_timesLE_1");
				htmlStr = htmlStr.replace(new RegExp("deleteOnePrice(0)",'gm'),"deleteOnePrice(1)");
				htmlStr = htmlStr.replace(new RegExp("getPriceType(this.value,0)",'gm'),"getPriceType(this.value,1)");
				$("#apiPrice_table").append(htmlStr);
			}
			
			
			oMark.style.display ="block";
			apiPriceBox.style.display ="block";
			oMark.style.width = viewWidth() + 'px';
			oMark.style.height = documentHeight() + 'px';
			apiPriceBox.style.left = (viewWidth() - apiPriceBox.offsetWidth)/2 + 'px';
			apiPriceBox.style.top = (viewHeight() - apiPriceBox.offsetHeight)/2-25 + 'px';
		},
		error:function(data) {
		
		}
	});
}

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

//添加行
function addOneApiPrice(){
	maxApiPriceIndex = maxApiPriceIndex+1;
	var priceTrHtml = $("#apiPrice_table_template").html();
	//元素id,name替换
	var htmlStr = priceTrHtml.replace(new RegExp("apiPrice_index_0",'gm'), "apiPrice_index_"+maxApiPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("apiPrice_type_0",'gm'), "apiPrice_type_"+maxApiPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("apiPrice_price_0",'gm'), "apiPrice_price_"+maxApiPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("apiPrice_timesG_0",'gm'), "apiPrice_timesG_"+maxApiPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("apiPrice_timesLE_0",'gm'), "apiPrice_timesLE_"+maxApiPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("apiPrice_title_timesLE_0",'gm'), "apiPrice_title_timesLE_"+maxApiPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("deleteOneApiPrice(0)",'gm'),"deleteOneApiPrice("+maxApiPriceIndex+")");
	htmlStr = htmlStr.replace(new RegExp("getApiPriceType(this.value,0)",'gm'),"getApiPriceType(this.value,"+maxApiPriceIndex+")");
	$("#apiPrice_table").append(htmlStr);
}

//删除行
function deleteOneApiPrice(delete_index){
	$("#apiPrice_index_"+delete_index).remove();
	
}
//区间或大于的下拉框选择
function getApiPriceType(value, priceIndex){
	if (value == 2) {   //1：区间；2：大于
		//小于等于 移除
		$("#apiPrice_title_timesLE_"+priceIndex).hide();
		$("#apiPrice_timesLE_"+priceIndex).hide();
	}else {
		$("#apiPrice_title_timesLE_"+priceIndex).show();
		$("#apiPrice_timesLE_"+priceIndex).show();
	}
}

function saveServiceApiPrice(){
	//验证 TODO
	$("#maxApiPriceIndex").val(maxApiPriceIndex);
	
	//表单提交
	$("#form_api_price").ajaxSubmit(function (responseResult) {

		if(responseResult.success == false) {
			alert("API价格维护失败!");
		}
		
		window.location.href = "getServiceList.html";
	});
}
