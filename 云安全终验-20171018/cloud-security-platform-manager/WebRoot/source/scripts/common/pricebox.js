// JavaScript Document
var maxPriceIndex=0;
var scanTypeList;
//价格维护
$(function(){
	var oMark =document.getElementById('modelbox');
	var oLogin =document.getElementById('box_servicePrice');
	

     
		/*//关闭按钮
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
	
		}*/
})
function editPrice(servId,parentC){
	var oMark =document.getElementById('modelbox');
	var oLogin =document.getElementById('box_servicePrice');
	maxPriceIndex = 0;
	scanTypeList= null;
	$.ajax({
		type: "POST",
		url:"servicePriceMaintainUI.html",
		data:{"servId":servId,"parentC":parentC},
		success:function(data) {
			if (!data.detailFlag) {
				alert("请先完成详情维护！");
				return;
			}
			$("#add_serviceId").val(servId);
			if (data.orderType!=1) {   //1：长期
				//单次价格组件 追加
				var singlePriceDiv = '<div id="singlePrice_div">'+
	        							'<table>'+
	          								'<tr class="price_tr" id="singlePrice_tr">'+
	            								'<td class="regist_title">单次价格</td>'+
	            								'<td class="price_input"><input type="text" class="price_txt required" name="singlePrice" id="singlePrice" onblur="checkPrice(this)"/><span id="regist_name_msg" style="color:red;float:left" ></span></td>'+
	            								'<td class="regist_prompt"></td>'+
	          								'</tr>'+
	        						 	'</table>'+
        							'</div>';
				$("#allPriceDiv").append(singlePriceDiv);
			}
			if(data.orderType!=2) { //2：单次
				//长期价格组件 追加
				var longPriceDiv = '<div id="longPrice_div">'+
	        						'<table >'+
	          							'<tr class="price_tr">'+
	            							'<td class="regist_title">长期价格</td>'+
	            							'<td class="price_input_radio">'+
	            								'<input type="radio" name="scanTypeRadio" value="0" checked="true" onclick="getScanTypeSettingFlag()"/>不根据频率设置价格'+
	            								'<input type="radio" name="scanTypeRadio" value="1" onclick="getScanTypeSettingFlag()"/>根据频率设置价格'+
	            							'</td>'+
	          							'</tr>'+
	        						'</table>'+
	        						'<div id="allLongPriceDiv"></div>'+
	        					'</div>';
				$("#allPriceDiv").append(longPriceDiv);
			}
			
			//单次价格回填
			if (data.singlePrice != null) {
				$("#singlePrice").val(data.singlePrice);
			}
			
			var scanTypeHtml = $("#scanType_id_template").html();
			var priceTrHtml = $("#scanTypeTable_template").html();
			var longPriceList = data.longPriceList;
			var scanTypeSettingFlag = 0;
			if (longPriceList.length >0){
				scanTypeSettingFlag = longPriceList[0].scanType;
			}
			scanTypeList = data.scanTypeList;
			//服务频率回填
			if (scanTypeList!= null) {
				//$("#scanType_id_0").remove();
				//根据服务频率设置
				if (scanTypeSettingFlag != 0) {
					maxPriceIndex = scanTypeList.length;
					for(var s=0; s<scanTypeList.length;s++) {
						var scanTypeDivHtml = scanTypeHtml.replace("scanType_id_template","scanType_id_"+scanTypeList[s].scan_type);
						scanTypeDivHtml = scanTypeDivHtml.replace("scanType_div_template","scanType_div");
						scanTypeDivHtml = scanTypeDivHtml.replace("scanTypeTable_template","scanTypeTable_"+scanTypeList[s].scan_type);
						scanTypeDivHtml = scanTypeDivHtml.replace("price_index_template","price_index_"+scanTypeList[s].scan_type);
						scanTypeDivHtml = scanTypeDivHtml.replace("addOnePrice(0)","addOnePrice("+scanTypeList[s].scan_type+")");
						scanTypeDivHtml = scanTypeDivHtml.replace("price_scanType_0","price_scanType_"+s);  //为每一行设置服务频率
						//替换服务频率    scanType_index_
						scanTypeDivHtml = scanTypeDivHtml.replace("XXXX",scanTypeList[s].scan_name);
						$("#allLongPriceDiv").append(scanTypeDivHtml);
						$("#price_scanType_"+s).val(scanTypeList[s].scan_type);   //该组件的服务频率设为
						
					}
				}else{
					//不根据服务频率设置
						maxPriceIndex = 1;
						var scanTypeDivHtml = scanTypeHtml.replace("scanType_id_template","scanType_id_0");
						scanTypeDivHtml = scanTypeDivHtml.replace("scanType_div_template","scanType_div");
						scanTypeDivHtml = scanTypeDivHtml.replace("scanTypeTable_template","scanTypeTable_0");
						scanTypeDivHtml = scanTypeDivHtml.replace("price_index_template","price_index_0");
						scanTypeDivHtml = scanTypeDivHtml.replace("addOnePrice(0)","addOnePrice(0)");
						//替换服务频率
						scanTypeDivHtml = scanTypeDivHtml.replace("服务频率: XXXX","");
						$("#allLongPriceDiv").append(scanTypeDivHtml);
						$("#price_scanType_0").val(0);   //该组件的服务频率设为0
				}
			}
			
			//长期价格回填
			if(longPriceList!= null && longPriceList.length > 0){
				maxPriceIndex = longPriceList.length;
				var lastScanType=-1;
				for (var i = 1;i <= longPriceList.length;i++) {
					var price = longPriceList[i-1];
					if (price.scanType != lastScanType) {
						lastScanType = price.scanType;
						$("#scanTypeTable_"+lastScanType).find("tr").first().remove();
					}
					//元素id,name替换
					htmlStr = priceTrHtml.replace(new RegExp("price_index_template",'gm'), "price_index_"+i);
					htmlStr = htmlStr.replace(new RegExp("type_0",'gm'), "type_"+i);
					htmlStr = htmlStr.replace(new RegExp("scanType_0",'gm'), "scanType_"+i);
					htmlStr = htmlStr.replace(new RegExp("price_0",'gm'), "price_"+i);
					htmlStr = htmlStr.replace(new RegExp("timesG_0",'gm'), "timesG_"+i);
					htmlStr = htmlStr.replace(new RegExp("timesLE_0",'gm'), "timesLE_"+i);
					htmlStr = htmlStr.replace(new RegExp("title_timesLE_0",'gm'), "title_timesLE_"+i);
					//htmlStr = htmlStr.replace(new RegExp("deleteOnePrice(0)",'gm'),"deleteOnePrice("+i+")");
					htmlStr = htmlStr.replace("deleteOnePrice(0)","deleteOnePrice("+i+")");
					htmlStr = htmlStr.replace("getPriceType(this.value,0)","getPriceType(this.value,"+i+")");
					htmlStr = htmlStr.replace("checkTimesG(0)","checkTimesG("+i+")");
					htmlStr = htmlStr.replace("checkTimesLE(0)","checkTimesLE("+i+")");
					$("#scanTypeTable_"+price.scanType).append(htmlStr);
					//替换 类型
					$("#type_"+i).val(price.type);
					//替换 大于
					$("#timesG_"+i).val(price.timesG);
					//替换 小于等于
					$("#timesLE_"+i).val(price.timesLE);
					//替换 价格
					$("#price_"+i).val(price.price);
					//替换 服务频率
					$("#price_scanType_"+i).val(price.scanType);
					
					if (price.type == 2) {   //0:单次；1：长期；2：大于
						//小于等于 移除
						$("#title_timesLE_"+i).hide();
						$("#timesLE_"+i).val("0");
						$("#timesLE_"+i).hide();
					}
				}
				if(lastScanType == -1 || lastScanType == 0) {
					//$(".scanType_div").remove();
					$(":radio[name=scanTypeRadio][value=0]").attr("checked","true");
				}else {
					$(":radio[name=scanTypeRadio][value=1]").attr("checked","true");
				}
				
			}
			oMark.style.display ="block";
			oLogin.style.display ="block";
			oMark.style.width = viewWidth() + 'px';
			oMark.style.height = documentHeight() + 'px';
			oLogin.style.left = (viewWidth() - oLogin.offsetWidth)/2 + 'px';
			var topDis = (viewHeight() - oLogin.offsetHeight)/2-25;
			if (topDis <= 0) {
				topDis = 10;
			}
			oLogin.style.top = topDis + 'px';	
			
		},
		error:function(data) {
		
		}
		
	});
	//oMark.style.display ="block";
	//oLogin.style.display ="block";
	//oMark.style.width = viewWidth() + 'px';
	//oMark.style.height = documentHeight() + 'px';
	//oLogin.style.left = (viewWidth() - oLogin.offsetWidth)/2 + 'px';
	//oLogin.style.top = (viewHeight() - oLogin.offsetHeight)/2-25 + 'px';	
	
}

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

function saveServicePrice(){
	//验证
	var numberPatrn=new RegExp("^[0-9]+$");
	var pricePattern = new RegExp("^[0-9]+(.[0-9]+)?$");
	var checkFlag = true;
	if ($("#singlePrice").length > 0&& !numberPatrn.test($("#singlePrice").val())) {
		$("#singlePrice").css("border","solid 1px red");
		checkFlag = false;
	}
	
	for (var i = 0;i<=maxPriceIndex;i++) {
		if ($("#type_"+i).length > 0) {
			$("#price_"+i).css("border","solid 1px #cbc9c9");
			$("#timesG_"+i).css("border","solid 1px #cbc9c9");
			$("#timesLE_"+i).css("border","solid 1px #cbc9c9");
			
			var type = $("#type_"+i).val();
			var timesG = $("#timesG_"+i).val();
			var timesLE = $("#timesLE_"+i).val();
			var price =  $("#price_"+i).val();
			if (price=='' && timesG=='' && timesLE==''){
				continue;
			}
			
			if (!numberPatrn.test(timesG)){
				$("#timesG_"+i).css("border","solid 1px red");
				checkFlag = false;
			}
			if (!numberPatrn.test(timesLE)){
				$("#timesLE_"+i).css("border","solid 1px red");
				checkFlag = false;
			}
			
			//检查价格
			if(!pricePattern.test(price) ) {
				$("#price_"+i).css("border","solid 1px red");
				checkFlag = false;
			}
			
			if (type == 1 && numberPatrn.test(timesG) && numberPatrn.test(timesLE) && Number(timesG) >= Number(timesLE)) {
				$("#timesG_"+i).css("border","solid 1px red");
				$("#timesLE_"+i).css("border","solid 1px red");
				checkFlag = false;
				continue;
			}
			
			
			
		}
	}
	if(!checkFlag) {
		alert("输入有误！");
		return;
	}
	
	$("#maxPriceIndex").val(maxPriceIndex);
	
	//表单提交
$("#form_price").ajaxSubmit({  
       success: function(data){  
    	if(data.success){
    		   alert( "价格添加成功!");  
    	          window.location.href = "getServiceList.html";
    	}else{
    		alert("价格维护失败!");
    	}
        }
 });  
}

//关闭按钮
function toClose(){
	var oMark =document.getElementById('modelbox');
	oMark.style.display ="none";
	
	var priceBox =document.getElementById('box_servicePrice');
	priceBox.style.display ="none";
	$("#allPriceDiv").empty();
	$("#maxPriceIndex").val("");
	
	var apiPriceBox =document.getElementById('box_apiPrice');
	apiPriceBox.style.display ="none";
	$("#apiPrice_table").empty();
	$("#maxApiPriceIndex").val("");
	
}

//删除行
function deleteOnePrice(delete_index){
	$("#price_index_"+delete_index).remove();
	
}

//添加行
function addOnePrice(scantype_index){
	maxPriceIndex = maxPriceIndex+1;
	var priceTrHtml = $("#scanTypeTable_template").html();
	//元素id,name替换
	var htmlStr = priceTrHtml.replace(new RegExp("price_index_template",'gm'), "price_index_"+maxPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("type_0",'gm'), "type_"+maxPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("scanType_0",'gm'), "scanType_"+maxPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("price_0",'gm'), "price_"+maxPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("timesG_0",'gm'), "timesG_"+maxPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("timesLE_0",'gm'), "timesLE_"+maxPriceIndex);
	htmlStr = htmlStr.replace(new RegExp("title_timesLE_0",'gm'), "title_timesLE_"+maxPriceIndex);
	htmlStr = htmlStr.replace("deleteOnePrice(0)","deleteOnePrice("+maxPriceIndex+")");
	htmlStr = htmlStr.replace("getPriceType(this.value,0)","getPriceType(this.value,"+maxPriceIndex+")");
	htmlStr = htmlStr.replace("checkTimesG(0)","checkTimesG("+maxPriceIndex+")");
	htmlStr = htmlStr.replace("checkTimesLE(0)","checkTimesLE("+maxPriceIndex+")");
	$("#scanTypeTable_"+scantype_index).append(htmlStr);
	$("#price_scanType_"+maxPriceIndex).val(scantype_index);
	
}

//区间或大于的下拉框选择
function getPriceType(value, priceIndex){
	if (value == 2) {   //1：区间；2：大于
		//小于等于 移除
		$("#title_timesLE_"+priceIndex).hide();
		$("#timesLE_"+priceIndex).val("0");
		$("#timesLE_"+priceIndex).hide();
	}else {
		$("#title_timesLE_"+priceIndex).show();
		$("#timesLE_"+priceIndex).show();
	}
}
//是否根据频率设置长期价格
function getScanTypeSettingFlag() {
    //var servId = $("#add_serviceId").val(servId);
	var scanTypeSettingFlag=$("input[name='scanTypeRadio']:checked").val();
	$("#allLongPriceDiv").empty();
	var scanTypeHtml = $("#scanType_id_template").html();
	//服务频率回填
	if (scanTypeList!= null) {
		//根据服务频率设置
		if (scanTypeSettingFlag != 0) {
			maxPriceIndex = scanTypeList.length;
			for(var s=0; s<scanTypeList.length;s++) {
				var scanTypeDivHtml = scanTypeHtml.replace("scanType_id_template","scanType_id_"+scanTypeList[s].scan_type);
				scanTypeDivHtml = scanTypeDivHtml.replace("scanType_div_template","scanType_div");
				scanTypeDivHtml = scanTypeDivHtml.replace("scanTypeTable_template","scanTypeTable_"+scanTypeList[s].scan_type);
				scanTypeDivHtml = scanTypeDivHtml.replace("price_index_template","price_index_"+scanTypeList[s].scan_type);
				scanTypeDivHtml = scanTypeDivHtml.replace("addOnePrice(0)","addOnePrice("+scanTypeList[s].scan_type+")");
				
				//元素id,name替换
				scanTypeDivHtml = scanTypeDivHtml.replace(new RegExp("price_index_template",'gm'), "price_index_"+s);
				scanTypeDivHtml = scanTypeDivHtml.replace(new RegExp("type_0",'gm'), "type_"+s);
				scanTypeDivHtml = scanTypeDivHtml.replace(new RegExp("scanType_0",'gm'), "scanType_"+s);
				scanTypeDivHtml = scanTypeDivHtml.replace(new RegExp("price_0",'gm'), "price_"+s);
				scanTypeDivHtml = scanTypeDivHtml.replace(new RegExp("timesG_0",'gm'), "timesG_"+s);
				scanTypeDivHtml = scanTypeDivHtml.replace(new RegExp("timesLE_0",'gm'), "timesLE_"+s);
				scanTypeDivHtml = scanTypeDivHtml.replace(new RegExp("title_timesLE_0",'gm'), "title_timesLE_"+s);
				scanTypeDivHtml = scanTypeDivHtml.replace("deleteOnePrice(0)","deleteOnePrice("+s+")");
				scanTypeDivHtml = scanTypeDivHtml.replace("getPriceType(this.value,0)","getPriceType(this.value,"+s+")");
				scanTypeDivHtml = scanTypeDivHtml.replace("price_scanType_0","price_scanType_"+s);  //为每一行设置服务频率
				//替换服务频率    scanType_index_
				scanTypeDivHtml = scanTypeDivHtml.replace("XXXX",scanTypeList[s].scan_name);
				
				$("#allLongPriceDiv").append(scanTypeDivHtml);
				$("#price_scanType_"+s).val(scanTypeList[s].scan_type);   //该组件的服务频率设值
			}
		}else{
			maxPriceIndex = 1;
			//不根据服务频率设置
			var scanTypeDivHtml = scanTypeHtml.replace("scanType_id_template","scanType_id_0");
			scanTypeDivHtml = scanTypeDivHtml.replace("scanType_div_template","scanType_div");
			scanTypeDivHtml = scanTypeDivHtml.replace("scanTypeTable_template","scanTypeTable_0");
			scanTypeDivHtml = scanTypeDivHtml.replace("price_index_template","price_index_0");
			scanTypeDivHtml = scanTypeDivHtml.replace("addOnePrice(0)","addOnePrice(0)");
			//替换服务频率
			scanTypeDivHtml = scanTypeDivHtml.replace("服务频率: XXXX","");
			$("#allLongPriceDiv").append(scanTypeDivHtml);
		}
	}
}

function checkPrice(Obj){
		var price = $(Obj).val();
		var pricePattern = new RegExp("^[0-9]+(.[0-9]+)?$");
		//检查价格
		if(!pricePattern.test(price)) {
			$(Obj).css("border","solid 1px red");
			
		}else {
			$(Obj).css("border","solid 1px #cbc9c9");
		}
}

function checkTimesG(i){
	var numberPatrn=new RegExp("^[0-9]+$");
	
	var type = $("#type_"+i).val();
	var timesG = $("#timesG_"+i).val();
	
	if (!numberPatrn.test(timesG)) {
		$("#timesG_"+i).css("border","solid 1px red");
		if(numberPatrn.test(timesLE)) {
			$("#timesLE_"+i).css("border","solid 1px #cbc9c9");
		}
		return;
	}
	
	//检查区间
	if (type == 1) {
		var timesLE = $("#timesLE_"+i).val();
		if (numberPatrn.test(timesLE) && Number(timesG) >= Number(timesLE)) {
			$("#timesG_"+i).css("border","solid 1px red");
			$("#timesLE_"+i).css("border","solid 1px red");
			return;
		}else if (numberPatrn.test(timesLE) && Number(timesG) <Number(timesLE)){
			$("#timesG_"+i).css("border","solid 1px #cbc9c9");
			$("#timesLE_"+i).css("border","solid 1px #cbc9c9");
			return;
		}
	}
	$("#timesG_"+i).css("border","solid 1px #cbc9c9");
}

function checkTimesLE(index){
	var numberPatrn=new RegExp("^[0-9]+$");
	
	var timesG = $("#timesG_"+i).val();
	var timesLE = $("#timesLE_"+i).val();
	
	if (!numberPatrn.test(timesLE)) {
		$("#timesLE_"+i).css("border","solid 1px red");
		if(numberPatrn.test(timesG)) {
			$("#timesG_"+i).css("border","solid 1px #cbc9c9");
		}
		return;
	}
	
	//检查区间
	if (numberPatrn.test(timesG) && Number(timesG) >= Number(timesLE)) {
		$("#timesG_"+i).css("border","solid 1px red");
		$("#timesLE_"+i).css("border","solid 1px red");
		return;
	}else if (numberPatrn.test(timesG) && Number(timesG) <Number(timesLE)){
		$("#timesG_"+i).css("border","solid 1px #cbc9c9");
		$("#timesLE_"+i).css("border","solid 1px #cbc9c9");
		return;
	}

	$("#timesLE_"+i).css("border","solid 1px #cbc9c9");
}

