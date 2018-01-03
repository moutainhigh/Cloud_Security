$(function(){
  changeDur();
  //初始化价格
  changePrice();
  //选择服务频率时，改变价格
//  $("#time").click(function(){
//	  alert(0);
//  })
  //监控目标发生改变时清空已选端口
  $("#ip").change(function(){
	  var ip = $("#ip").val();	  
	  if(!isIpOrDomain(ip)){
		return false;
	  }
	  
  });
  //重置按钮
  $("#reset").click(function(){
	  $("#servAdded").val("");
	  $("#servAddedHidden").val("");
  })
  //端口号改变时校验端口号
  $("#portNum").change(function(){
	  var port = $("#portNum").val();	  
	  if(!isPort(port)){
		return false;
	  }
  });
  //已添加端口号改变时清空错误提示
  $("#servAdded").change(function(){
	  $("#addedPortCheck").val("");
  });
  //点击添加触发事件
  $("#addService").click(function(){
	var ip = $("#ip").val();
	var port = $("#portNum").val();
	if(!(isIpOrDomain(ip)&&isPort(port))){
		return false;
	}
  	if($("#portNum").val()==""){
  		return false;
  	}
  	var servAddedHidden = $("#servAddedHidden").val();
  	var newAddedHidden = $("#servName").find("option:selected").attr("data-type") + ":" + $("#portNum").val() + ";";
    var servAdded = $("#servAdded").val();
    var addedNum = $("#servAdded").val().split(";").length;
    if(addedNum > 5){
    	alert("最多添加5个端口！");
    	return false;
    }
    var newAdded =  $("#portNum").val() + ";";
  	if(servAdded.indexOf(newAdded) != -1){
  		alert("此端口已添加过！");
  		return false;
  	}
  	$("#servAdded").val(servAdded + newAdded);
  	$("#servAddedHidden").val(servAddedHidden + newAddedHidden);
  })
  
  //立即购买
  $("#buyNowsys").click(function(){
    var createDate = getCreateDate();    
    var serviceId = $('#serviceIdHidden').val();
    var duration = $("#duration").val();
    var scanType = null;
    if (serviceId == 7) {
       scanType = $("#ipNum").val();
    } else if (serviceId == 8) {
       scanType=$("#nodeNum").val();
    } else if (serviceId == 10) {
    	var ip = $("#ip").val();
    	var port = $("#portNum").val();
    	if(!isIpOrDomain(ip)){
    		return false;
    	}
      	if($.trim($("#servAdded").val())==""){
      		$("#addedPortCheck").val("请添加端口号!");
      		return false;
      	}
       scanType=$("#time").find(".clickTime").val();
    } else {
       scanType="1";
    }
    var indexPage = $("#indexPage").val();
    var type = "1";//orderType订单类型：长期单次
    var port
    $.ajax({
      type:"POST",
      async: false,
      url:"getSession.html",
      dataType:"json",
      success:function(data){
    	  $.ajax({
    		  type:"POST",
    		  url:"checkifcanbuy.html",
    		  async: false,
    		  data: {
    			  "serviceId":serviceId}, 
    		  dataType:"json",
    		  success:function(da){    			  
    			  if(da.status==1){
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
      		         
      		        if(serviceId == 10){
	      		    	var ip = $("#ip").val();
	      		    	var port = $("#portNum").val();
		      	    	if(!(isIpOrDomain(ip)&&isPort(port))){
		      	    		return false;
		      	    	}
		      	      	if($.trim($("#servAdded").val())==""){
		      	      		$("#addedPortCheck").val("请添加端口号!")
		      	      		return false;
		      	      	}
      		        	var portMessageInput = document.createElement("input");
      		        	portMessageInput.type="hidden"; 
      		        	portMessageInput.name= "portMessage";                 
      		        	portMessageInput.value= $("#ip").val() + ";" + $("#servAddedHidden").val().substring(0,$("#servAddedHidden").val().length-1); 
        		        tempForm.appendChild(portMessageInput);
      		        }
      		        
      		        document.body.appendChild(tempForm);
      		        tempForm.submit();
      		        document.body.removeChild(tempForm);
    			  }
    			  else if(da.status==0){
    				  alert("此商品只能购买一次，去我的订单里看看吧~");
    			  }
    		  },
    		  error:function(da){
    			  window.location.href = "loginUI.html";
    		  }
    	  });                                                    
      },
      error:function(data){    	
        if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
          window.location.href = "loginUI.html"; } 
        else { window.location.href = "loginUI.html"; }         
      }
    });
  });
  
  
//点击“添加购物车”
  $("#addSysCar").click(function(){
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
    else if(serviceId==10){
    	var ip = $("#ip").val();
    	if(!isIpOrDomain(ip)){
    		return false;
    	}
        scanType=$("#time").find(".clickTime").val();
    }
    else
      scanType="1";
    var parameterObj = {
    		"orderType":orderType,
		   	"duration": duration,
		   	"scanType":scanType,
		   	"serviceId":serviceId
		   	};
    if(serviceId == 10){
    	parameterObj = {
        		"orderType":orderType,
    		   	"duration": duration,
    		   	"scanType":scanType,
    		   	"serviceId":serviceId,
    		   	"portMessage":$("#ip").val() + ";" + $("#servAddedHidden").val().substring(0,$("#servAddedHidden").val().length-1)
    		   	};
    }
	$.ajax({ type: "POST",
	     async: false, 
	     url: "shoppingCarSys.html",
	     data: parameterObj,
	     dataType: "json", 
	     success: function(data) {
			   		if(data.error){
			   			alert("参数值数据异常!");
			   			//window.location.href="index.html";
			   		}
			   		if(data.status==3){
			   			alert("此商品只能购买一次，去我的订单里看看吧!");
			   			//window.location.href="index.html";
			   		}
			   		if(data.status==4){
			   			alert("此商品已经加入到购物车了，去我的购物车里看看吧!");
			   			//window.location.href="index.html";
			   		}
			   		 if(data.sucess){
			   			 alert("添加购物车成功!");
			   			 buySystemService(serviceId);
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
    var scanType = $("#scanType").val();
    var pay = $(".click").val();
  	$.ajax({
  		type: "POST",
  	    async: false, 
  	    url: "saveOrderSys.html", 
  	    data: {"orderDetailId":orderDetailId,    
  			   "createDate":createDate,
  			   "scanType":scanType,
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
  					
  					var payInput = document.createElement("input");
					payInput.type="hidden"; 
					payInput.name= "payId"; 
					payInput.value= pay; 
					tempForm.appendChild(payInput);
  				
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
  if(typeof(serviceId) == "undefined"){
		return;
	}
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
//系统服务监测切换
function changePort(){
	var curPort = $("#changePort").find("option:selected").val();
	$("#changePort").find("input").val(curPort);
}
//验证是否为ip或者是域名
function isIpOrDomain(ip){
	var ip = $("#ip").val();
	var regString = /^(http|https|ftp)\:\/\/([a-zA-Z0-9\.\-]+(\:[a-zA-Z0-9\.&%\$\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\-]+\.)*[a-zA-Z0-9\-]+\.[a-zA-Z]{2,4})(\:[0-9]+)?([^/][a-zA-Z0-9\.\,\?\'\\/\+&%\$#\=~_\-@]*)*$/;
	var reg =  new RegExp(regString);
	if(reg.test(ip)){
		$("#ipCheck").text(""); 
		return true;
	} else {
		$("#ipCheck").text("IP或域名输入有误！");
		return false;
	}
}
//function isIpOrDomain(ip){
//	var ipArray,j; 
//	var ip = $("#ip").val();	  
//    if(/[A-Za-z_-]/.test(ip)){ 
//        if(!/^([\w-]+\.)+((com)|(net)|(org)|(gov\.cn)|(info)|(cc)|(com\.cn)|(net\.cn)|(org\.cn)|(name)|(biz)|(tv)|(cn)|(mobi)|(name)|(sh)|(ac)|(io)|(tw)|(com\.tw)|(hk)|(com\.hk)|(ws)|(travel)|(us)|(tm)|(la)|(me\.uk)|(org\.uk)|(ltd\.uk)|(plc\.uk)|(in)|(eu)|(it)|(jp))$/.test(ip)){ 
//        	$("#ipCheck").text("IP或域名输入有误！");
//            return false; 
//        }
//        $("#ipCheck").text(""); 
//        return true;
//    } 
//    else{ 
//        ipArray = ip.split("."); 
//        j = ipArray.length 
//        if(j!=4) 
//        { 
//        	$("#ipCheck").text("IP或域名输入有误！");
//            return false; 
//        } 
//
//        for(var i=0;i<4;i++) 
//        { 
//            if(ipArray[i].length==0 || ipArray[i]>255) 
//            { 
//            	$("#ipCheck").text("IP或域名输入有误！");
//                return false; 
//            } 
//        }
//        $("#ipCheck").text(""); 
//        return true;
//    } 
//}
//验证端口号是否正确
function isPort(port){
    var port = $("#portNum").val();	  
	var reg = /^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5])$/;
	if(!reg.test(port)){
		$("#portCheck").text("端口号输入有误！");
		return false;
		
	} else {
		$("#portCheck").text("");  
		return true;
	}

}