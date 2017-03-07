$(function(){
  changeDur();
  //初始化价格
  changePrice();

  //立即购买
  $("#buyNowsys").click(function(){
    var createDate = getCreateDate();    
    var serviceId = $('#serviceIdHidden').val();
    var duration = $("#duration").val();
    var scanType = null;
    if(serviceId==7)
  	  scanType=$("#ipNum").val();
    else if(serviceId==8)
  	  scanType=$("#nodeNum").val();
    else
    	scanType="1";
    var indexPage = $("#indexPage").val();
    var type = "1";//orderType订单类型：长期单次

    $.ajax({
      type:"POST",
      async: false,
      url:"getSession.html",
      dataType:"json",
      success:function(data){
        var tempForm = document.createElement("form");
        tempForm.action = "jiGuangselfHelpOrderOpera.html";
        tempForm.method = "post";
        tempForm.style.display = "none";

        var typeInput = document.createElement("input");
        typeInput.type="hidden"; 
        typeInput.name= "type"; 
        typeInput.value= type; 
        tempForm.appendChild(typeInput);
        
        var durationInput = document.createElement("input");
        durationInput.type="hidden"; 
        durationInput.name= "duration"; 
        durationInput.value= duration; 
        tempForm.appendChild(durationInput);
              
        var scanTypeInput = document.createElement("input");
        scanTypeInput.type="hidden"; 
        scanTypeInput.name= "scanType"; 
        scanTypeInput.value= scanType; 
        tempForm.appendChild(scanTypeInput);
              
        var serviceIdInput = document.createElement("input");
        serviceIdInput.type="hidden"; 
        serviceIdInput.name= "serviceId"; 
        serviceIdInput.value= serviceId; 
        tempForm.appendChild(serviceIdInput);
                           
              
        document.body.appendChild(tempForm);
        tempForm.submit();
        document.body.removeChild(tempForm);

      },
      error:function(data){
    	  /*
        if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
          window.location.href = "loginUI.html"; } 
        else { window.location.href = "loginUI.html"; } 
        */
    	  alert("baocuo");
      }
    });
  });

//点击“添加购物车”
  $("#addSysCar").click(function(){
  	alert("abc");
  	var shopCarNum = Number($("#shopCarNum").html());
	if (shopCarNum >= 99) {
		alert("购物车中商品数量已达上限，请先清理购物车！");
		return;
	}
	var createDate = getCreateDate();
	var orderType = "1";
	var duration = $("#duration").val();
	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
	var serviceId = $("#serviceIdHidden").val();
	var scanType = null;
    if(serviceId==7)
  	  scanType=$("#ipNum").val();
    else if(serviceId==8)
  	  scanType=$("#nodeNum").val();
    else
    	scanType="1";
    
	$.ajax({ type: "POST",
	     async: false, 
	     url: "shoppingCar.html",
	     data: {"orderType":orderType,
			   	"duration": duration,
			   	"scanType":scanType,
			   	"serviceId":serviceId}, 
	     dataType: "json", 
	     success: function(data) {
			   		if(data.error){
			   			alert("参数值数据异常!");
			   			//window.location.href="index.html";
			   		}
			   		 if(data.sucess){
			   			 alert("添加购物车成功!");
			   			 buyService(serviceId);
			   			// window.location.href="selfHelpOrderInit.html?serviceId="+serviceId+"&indexPage="+indexPage;
			   		 }
			   			
			   	
	    	 }, 
	     error: function(data){ 
	    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		 //window.location.href = "loginUI.html"; 
	    		 alert("请求失败");
	    	 } 
	    	 else { 
	    		 //window.location.href = "loginUI.html"; 
	    		 alert("请求失败");
	    		 } } 
	});
  });
  
  //结算页提交订单
  $("#settlementSys").click(function(){
  	var createDate = getCreateDate();
  	var orderType =  $('#orderType').val();
  	var orderDetailId = $("#orderDetailId").val();
  	var userName =  $(".test_name").text();
    var userAdd = $(".test_add").text();
    var mobile =  $(".test_iphone").text();
    var userId = $("#userIdHidden").val();
  	$.ajax({
  		type: "POST",
  	    async: false, 
  	    url: "saveOrderSys.html", 
  	    data: {"orderDetailId":orderDetailId,    
  			   "createDate":createDate,
  			   "linkname":userName,
  			   "phone":mobile,
  			   "email":userAdd
     			},  
  	     dataType: "json",
  	     success: function(data) {
   			if(data.error){
   				alert("参数值数据异常!!");
   				window.location.href = "index.html";
  		     	    return;
   			}else if(data.timeCompare == false){
  					alert("订单无效,请重新下单!");
  		     		return;
  				}else if(data.orderStatus == false){
  					alert("订单有异常,请重新下单!");
  		     	    return;
  				}else{
   		    	var orderListId = data.orderListId;
  	    			//window.location.href = "cashierUI.html?orderListId="+orderListId;
  	    			//虚拟表单post提交
  			    	var tempForm = document.createElement("form");
  					tempForm.action = "cashierUI.html";
  					tempForm.method = "post";
  					tempForm.style.display = "none";
  				
  					var orderListIdInput = document.createElement("input");
  					orderListIdInput.type="hidden"; 
  					orderListIdInput.name= "orderListId"; 
  					orderListIdInput.value= orderListId; 
  					tempForm.appendChild(orderListIdInput);
  				
  					document.body.appendChild(tempForm);
  					tempForm.submit();
  					document.body.removeChild(tempForm);
  				}
  	    }, 
  	    error: function(data){ 
  	    	if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		// window.location.href = "loginUI.html"; 
	    		 } 
	    	 else { 
	    		 //window.location.href = "loginUI.html"; 
	    		 }
  	    } 
  	});
  });
  
});

//金山商品详情页中服务期限与管理终端数的联动对应
function changeDur(){
	var duration = $("#duration").val();
	$("#nodeNum").empty();
	var text = null;
	if(duration =="12"){
		text+="<option value='30' selected='selected'>30</option>";
		text+="<option value='50'>50</option>";
	}
	if(duration ==24){
		text+="<option value='10' selected='selected'>10</option>";
		text+="<option value='20'>20</option>";
		text+="<option value='30'>30</option>";
		text+="<option value='50'>50</option>";
	}
	if(!text){
		text+="<option value='100' selected='selected'>100</option>";
	}
	else{
		text+="<option value='100'>100</option>";
	}
	text+="<option value='200'>200</option>";
	text+="<option value='500'>500</option>";
	$("#nodeNum").append(text);
	changePrice();
}

function getCreateDate(){
  var now = new Date();
  var createDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
  return createDate;
}
//计算价格
function changePrice(){	
  var serviceId = $('#serviceIdHidden').val();
  var duration = $("#duration").val();
  var scanType = null;
  if(serviceId==7)
	  scanType=$("#ipNum").val();
  else if(serviceId==8)
	  scanType=$("#nodeNum").val();
  else
  	scanType="1";
  
  $.ajax({ type: "POST",
    async: false, 
    url: "syscalPrice.html", 
    //服务id，服务时长，scantype
    data: {"serviceId":serviceId,"duration":duration,"scanType":scanType},
    dataType: "json",
    success: function(data) {
      var price = data.price;
      $("#price").html(price);
    }, 
    
    error: function(data){ 
      if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
        window.location.href = "loginUI.html"; } 
      else { window.location.href = "loginUI.html"; } 
      } 
  });
}

//跳转到购物车页面
function showShopCar(){
		$.ajax({ type: "POST",
		     async: false, 
		     url: "getSession.html", 
		     dataType: "json", 
		     success: function(data) {
		    	 window.location.href="showShopCar.html";
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});

		}