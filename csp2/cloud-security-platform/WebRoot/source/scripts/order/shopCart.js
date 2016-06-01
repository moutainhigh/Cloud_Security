$(function(){

    //购物车点击“去结算”
    $("#shopBuy").click(function(){
       var str="";
		$("input:checkbox[name=check_name]:checked").each(function(obj){
		   str+=$(this).val()+",";
    	});
		//alert(str);
		if(str==""){
			alert("请至少选择一件商品!");
		}else{
		   $.ajax({ type: "POST",
			     async: false, 
			     url: "checkShoppOrder.html?str="+str,
			    dataType: "json", 
			     success: function(data) {
			     if(data.flag){
			    	 $("input:checkbox[name=check_name]").attr("checked",false);
				    	 window.location.href="shopBuy.html?str="+str;
				   
			     }else{
			    	 alert("当前时间已经超过下单开始时间，订单已作废请删除订单!");
			    	 window.location.href="showShopCar.html";
			       } 
			     },
			     error: function(data){ 
			    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
			    		 window.location.href = "loginUI.html"; } 
			    	 else { window.location.href = "loginUI.html"; }
			    	 
			     } 
			});
	   }
    });
  
       //购物车点击“提交订单”
    $("#shopSettlement").click(function(){
       var orderIds="";
       var countPrice = $("#countPrice").val();
      var userName =  $(".test_name").text();
        var userAdd = $(".test_add").text();
        var mobile =  $(".test_iphone").text();
       $("input:hidden[name='orderId']").each(function(obj){
    	    orderIds+=$(this).val()+",";
       });
    var result = window.confirm("确定要提交订单吗？");
    if(result){
	  $.ajax({ type: "POST",
		     async: false, 
		     url: "shopSettlement.html",
		      data:{"orderIds":orderIds,
		            "countPrice":countPrice,
		            "linkName":userName,
		            "linkEmail":userAdd,
		            "linkMobile":mobile
		           },
		     dataType: "json", 
		     success: function(data) {
					  if(!data.flag){
						  alert("当前时间已经超过下单开始时间，订单已作废请到购物车修改订单!");
						  return;
					  }else{
			    	 if(data.sucess==true&&data.orderStatus == true){
	    		    			 //alert("完成下单，去订单跟踪查看吧~~"); 
	    		    			 var orderListId = data.orderListId;
	    		    			 window.location.href = "cashierUI.html?orderListId="+orderListId;
		    		    	 }else{
		    		    		alert("系统异常，暂时不能购买，请稍后购买~~!");
		    		     		 return;
		    		    	 }
			          }
				}, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		})};
    })
});


 
  