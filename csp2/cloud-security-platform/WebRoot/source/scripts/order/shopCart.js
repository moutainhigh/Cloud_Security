$(function(){

    //购物车点击“去结算”
    $("#shopBuy").click(function(){
    	var userId = $("#userIdHidden").val();
    	
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
			     url: "checkShoppOrder.html",
			     data:{"str":str,
			            "userId":userId
			           },
			    dataType: "json", 
			     success: function(data) {
			   		 if(!data.userStatus){
			   			 alert("该订单不属当前用户,请重新下单!");
			   			 window.location.href="index.html";
			   		 }else{
			   			 if(data.flag){
					    	 $("input:checkbox[name=check_name]").attr("checked",false);
						    	 //window.location.href="shopBuy.html?str="+str;
						    	 //虚拟表单post提交
						    	var tempForm = document.createElement("form");
	  							tempForm.action = "shopBuy.html";
	  							tempForm.method = "post";
	  							tempForm.style.display = "none";
  							
	  							var orderIdInput = document.createElement("input");
	  							orderIdInput.type="hidden"; 
								orderIdInput.name= "str"; 
								orderIdInput.value= str; 
								tempForm.appendChild(orderIdInput);
								
								document.body.appendChild(tempForm);
								tempForm.submit();
								document.body.removeChild(tempForm);
  
						   
					     }			   		
			   			 else{
			   				if(data.status==-2){
				   				alert("此商品只能购买一次，去我的订单里看看吧!");
				   			 }
			   				else if(data.status==-3){
				   				alert("此商品一次只能购买一个哦");
				   			 }
			   				else{
			   					alert("部分订单已失效，请重新下单!");
			   				}
					    	 
					    	 window.location.href="showShopCar.html";
					     } 
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
      var userName =  $(".test_name").text();
        var userAdd = $(".test_add").text();
        var mobile =  $(".test_iphone").text();
       $("input:hidden[name='orderId']").each(function(obj){
    	    orderIds+=$(this).val()+",";
       });
   // var result = window.confirm("确定要提交订单吗？");
    //if(result){
	  $.ajax({ type: "POST",
		     async: false, 
		     url: "shopSettlement.html",
		      data:{"orderIds":orderIds,
		            "linkName":userName,
		            "linkEmail":userAdd,
		            "linkMobile":mobile
		           },
		     dataType: "json", 
		     success: function(data) {
		    		  if(data.errorStatus){
		    		      if(data.errorMsg!=""){
		    		          alert(data.errorMsg);
		    		      }else{
						      alert("参数值错误!");
		        		      window.location.href = "index.html";
		    		      }
	    		     	    return;
					  }else if(!data.flag){
						  alert("当前时间已经超过下单结束时间，订单已作废请到购物车修改订单!");
						  return;
					  }else{
			    	 		if(data.sucess==true&&data.orderStatus == true){
	    		    			 //alert("完成下单，去订单跟踪查看吧~~"); 
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
		})
		//};
    })
});


 
  