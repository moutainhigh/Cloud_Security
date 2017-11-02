$(function(){
	//生成默认价格
	calApiPrice();
	
	//增加数量
	$(".add").click(function(){ 
		var t=$('input[class*=text_box]'); 
		if(t.val()==''){
			alert("请输入购买数量!");
			return;
		}
		//数量不能超过10000
		if(parseInt(t.val())==10000) {
			return;
		}
		t.val(parseInt(t.val())+1);
		calApiPrice();
	}) 
	
	//减少数量
	$(".min").click(function(){ 
	
		var t=$('input[class*=text_box]'); 
		if(t.val()==''){
			alert("请输入购买数量!");
			return;
		}

		t.val(parseInt(t.val())-1) 
		if(parseInt(t.val())<0){ 
		t.val(0); 
		} 
		calApiPrice();
	})
	
    
    //确认订单界面点击"确认订单"进入完成
    $("#buyAPI").click(function(){
    	var createDate = getCreateDate();
    	var num = $('#num').val();//数量
    	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
    	var apiId = $("#apiId").val();
    	
    	if(num<=0){
    		alert("数量不能小于0");
    		return;
    	}
    	if(num>10000){
    		alert("数量不能大于10000");
    		return;
    	}
    	$.ajax({ type: "POST",
		     async: false, 
		     url: "checkAPI.html", 
		     success: function(data) {
			    	 if(data.message == true){
			    	 var tempForm = document.createElement("form");
  							tempForm.action = "settlementAPI.html";
  							tempForm.method = "post";
  							tempForm.style.display = "none";
  							
  							var apiIdInput = document.createElement("input");
  							apiIdInput.type="hidden"; 
							apiIdInput.name= "apiId"; 
							apiIdInput.value= apiId; 
							tempForm.appendChild(apiIdInput);
							
							var numInput = document.createElement("input");
  							numInput.type="hidden"; 
							numInput.name= "num"; 
							numInput.value= num; 
							tempForm.appendChild(numInput);
							
							document.body.appendChild(tempForm);
							tempForm.submit();
							document.body.removeChild(tempForm);
			    		 //window.location.href="settlementAPI.html?apiId="+apiId+"&time="+time+"&num="+num+"&type="+type+"&price="+priceVal;
			    		 
			    	 
			    	 }else{
			    		 alert(data.message);
			     		 return;
			    	 }
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});
    	

    });
   //添加到购物车
    $("#shopCarAPI").click(function(){
    	var createDate = getCreateDate();

    	var num = $('#num').val();//数量
    	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
    	var apiId = $("#apiId").val();
    	
    	if(num<=0){
    		alert("数量不能小于0");
    		return;
    	}
    	if(num>10000){
    		alert("数量不能大于10000");
    		return;
    	}
    	$.ajax({ type: "POST",
		     async: false, 
		     url: "checkAPI.html", 
		     success: function(data) {
 			   	    if(data.message == true){
			    		shopCarAPIVal(apiId,num);
			    		 
			    	 }else{
			    		 alert(data.message);
			     		 return;
			    	 }
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});
		

    });
    
   
    
    $("#settlement").click(function(){
    	var createDate = getCreateDate();
    	var orderType = $('#orderType').val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var scanType = $('#scanType').val();
//    	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
    	var serviceId = $("#serviceId").val();
    	if(orderType==2){
    		scanType="";
    	}
    	//获得服务资产
    	var assetIds = $("#assetIds").val();;
    	var ip="";
		var bandwidth="";
		
		//var result = window.confirm("确定要提交订单吗？");
    	//if(result){
    		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "saveOrder.html", 
	    		     data: {"orderType":orderType,
		    			   	"beginDate": beginDate,
		    			   	"endDate":endDate,
		    			   	"createDate":createDate,
		    			   	"scanType":scanType,
		    			   	"serviceId":serviceId,
		    			   	"linkname":"",
		    			   	"phone":"",
		    			   	"email":"",
		    			   	"company":"",
		    			   	"address":"",
		    			   	"assetIds":assetIds,
		    			   	"ip":ip,
		    			   	"bandwidth":bandwidth,
			    			"websoc":"",
			    			"tasknum":""},  
	    		     dataType: "json", 
//		    		     contentType: "application/json; charset=utf-8", 
	    		     success: function(data) {
	    		    	 if(data.assetsStatus == false){
	    		    		 if(data.timeCompare == true){
	    		    			 alert("完成下单，去订单跟踪查看吧~~");  
	    		    			 window.location.href = "orderTrackInit.html";
		    		    	 }else{
		    		    		 alert("订单开始时间不能早于当前订单提交时间!");
		    		     		 return;
		    		    	 }
	    		    	 }else{
	    		    		 alert("订单有异常,请重新下单!");
	    		     		 return;
	    		    	 }
	    		    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "loginUI.html"; } 
	    		    	 else { window.location.href = "loginUI.html"; } } 
		    	});

    	//}
    });
   

   

   
   function getCreateDate(){
	   var now = new Date();
   	   var createDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
   	   return createDate;
   }
   

});

function tasknum_verification(){
		$.ajax({
	        type: "POST",
	        url: "verificateTaskNum.html",
	        data: {},
	        dataType:"json",
	        success: function(data){
	            if(data.msg){
	            	alert("个人用户累计下单不能超过20次，如欲继续下单，需升级为企业用户！");           	
	            }else{
	            	 window.location.href = "selfHelpOrderInit.html";
	            }
	        },
  		    error: function(data){ 
  		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
  		    		 window.location.href = "loginUI.html"; } 
  		    	 else { window.location.href = "loginUI.html"; } } 
	     });
}
function shopCarAPIVal(apiId,num){
	$.ajax({ type: "POST",
		     async: false, 
		     url: "shoppingCarAPI.html", 
		     data: {"apiId":apiId,
 			   	    "num":num},  
		     dataType: "json", 
		     success: function(data) {
			    	 if(data.sucess){
			    		 alert("添加购物车成功!");
			    		 window.location.href="selfHelpOrderAPIInit.html?apiId="+apiId+"&indexPage=2";
			    	 }
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
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
		

    //计算API价格
    function calApiPrice(){//num:数量
    	var apiId = $("#apiId").val();
    		num = $("#num").val();
    	
    	$.ajax({ type: "POST",
   		     async: false, 
   		     url: "calApiPrice.html", 
   		     data:{"serviceId":apiId,"num":num},
   		     dataType: "json",
   		     success: function(data) {
       			if(data.success){
     		    	  var price = data.price;
     		    	  $("#price").html(price);
     		    	  //$("#timesHidden").val(data.times);
       			}

	    	 }, 
	    	 error: function(data){ 
	    		 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
	    	
    	});
	}
	
	function checkNum() {
		var num = $("#num").val().replace(/\D/g,'');
		if(num>10000){
			alert("数量不能大于10000");
			$("#num").val(10000);
		}else{
			$("#num").val(num);
		}
		//计算价格
		calApiPrice();
		
	}
