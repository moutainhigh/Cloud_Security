$(function(){

    //购物车点击“去结算”
    $("#shopBuy").click(function(){
       var str="";
		$("input:checkbox[name=check_name]:checked").each(function(obj){
		   str+=$(this).val()+",";
    	});
		if(str==""){
			alert("请至少选择一件商品!");
		}else{
		   $.ajax({ type: "POST",
			     async: false, 
			     url: "getSession.html",
			     dataType: "json", 
			     success: function(data) {
				    	 window.location.href="shopBuy.html?str="+str;
				    }, 
			     error: function(data){ 
			    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
			    		 window.location.href = "loginUI.html"; } 
			    	 else { window.location.href = "loginUI.html"; } } 
			});
	   }
    });
  
       //购物车点击“结算”
    $("#shopSettlement").click(function(){
       var orderIds="";
       $("input:hidden[name='orderId']").each(function(obj){
    	    orderIds+=$(this).val()+",";
       });
    var result = window.confirm("确定要提交订单吗？");
    if(result){
	  $.ajax({ type: "POST",
		     async: false, 
		     url: "shopSettlement.html?orderIds="+orderIds,
		     dataType: "json", 
		     success: function(data) {
			    	 if(data.sucess==true&&data.orderStatus == true){
	    		    			 alert("完成下单，去订单跟踪查看吧~~"); 
	    		    			 window.location.href = "orderTrackInit.html";
		    		    	 }else{
		    		    		alert("系统异常，暂时不能购买，请稍后购买~~!");
		    		     		 return;
		    		    	 }
			    }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		})};
    })
});


 
  