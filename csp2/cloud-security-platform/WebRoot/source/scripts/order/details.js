
//默认服务频率
var servType = 0;
$(function(){
	//默认选中资产数
	var assetCount = 0;

	//获取默认选中的资产数
	//获取资产数
/*	$('.dropdown-menu li').each(function(){
		var ck=$(this).find('input');
		if($(ck).is(':checked')){
			assetCount++;	
		}
	})*/
	
   	$('.btnNew i').each(function(){
   		assetCount++;	
	});
   		
	//生成默认价格
	calDefaultPrice();
	
	//选择资产
/*	$('.dropdown-menu li').each(function(){
		$(this).click(function(){

			var ck=$(this).find('input');
			var id=$(this).find('input').attr('id');
			alert("clickid:"+id);
			if($(ck).is(':checked')){
				assetCount=assetCount+1;
				//alert("check:"+assetCount);
			}else
			{
				if(assetCount>0){
					assetCount=assetCount-1;
				}
				//alert("uncheck:"+assetCount);
			}
			var type = $(".click").val();
			if(type="1"){//长期
				calPriceLong(null,servType,assetCount);
			}else{
				calPrice(assetCount);
			}
			
		})
		
	})*/
	
		var assetIds = $("#assetIds").val();
		var assetAddr = $("#assetAddr").val();
		$('.dropdown-menu li').each(function(){
			$(this).delegate(this,'click',function(){
				var ck=$(this).find('input');
				var id=$(this).find('input').attr('id');
				if($(ck).is(':checked')){
					var flag = false;
					$('.btnNew i').each(function(index, element) {
                        var iId =$(this).attr('id');
						if(id==iId){
							flag = true;
						}
                    });
					if(!flag){
						var v= $(this).children('label').text();
						$('.btnNew em').before('<i id='+ assetIds + id +'>'+ assetAddr + v +',</i>');
						assetCount=assetCount+1;
					}
					
				}else
				{
					$('.btnNew i').each(function(index, element) {
                        var iId =$(this).attr('id');
						if(id==iId){
							$(this).remove();
							if(assetCount>0){
								assetCount=assetCount-1;
							}
						}
                    });
				}
				
				var type = $(".click").val();
				if(type="1"){//长期
					calPriceLong(null,servType,assetCount);
				}else{
					calPrice(assetCount);
				}
			})
			
		})
	
	
    //确认订单界面点击"确认订单"进入完成
    $("#buyNow").click(function(){
    	var createDate = getCreateDate();
    	var type = $('.click').val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var scanType = $('.clickTime').val();
    	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
    	var serviceId = $("#serviceId").val();
    	var times = $("#timesHidden").val();
    	var price = $('.price').children('strong:first').text();
    	if(type==2){
    		scanType="";
    	}
    	//获得服务资产
//    	var assetIds = $("#assetIds").val();
    	
    	var assetIds = "";
   		$('.btnNew i').each(function(){
   			assetIds = assetIds + $(this).attr("id") + ",";
		});
    	var ip="";
		var bandwidth="";
		if(type==2){
			if(beginDate==""||beginDate==null){
        		alert("开始时间不能为空");
        		return;
			}
		}else{
			if(beginDate==""||beginDate==null||endDate==""||endDate==null){
        		if(beginDate==""||beginDate==null){
            		alert("开始时间不能为空");
            		return;
            	}
            	if(endDate==""||endDate==null){
            		alert("结束时间不能为空");
            		return;
            	}
        	}else{
        		if(beginDate>=endDate){
            		alert("开始时间不能大于结束时间!");
            		return;
            	}
        	}
		}
		if(assetIds==""||assetIds==null){
			alert("请选择资产!");
    		return;
		}

		$.ajax({ type: "POST",
		     async: false, 
		     url: "getSession.html", 
		     dataType: "json", 
		     success: function(data) {
		    	 window.location.href="settlement.html?type="+type+"&beginDate="+beginDate+"&endDate="+endDate+"&scanType="+scanType+"&serviceId="+serviceId+"&assetIds="+assetIds+"&buy_times="+times+"&price="+price;
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
    	  var price = $('#priceHidden').val();
    	if(orderType==2){
    		scanType="";
    	}
    	//获得服务资产
    	var assetIds = $("#assetIds").val();;
    	var ip="";
		var bandwidth="";
		
		var result = window.confirm("确定要提交订单吗？");
    	if(result){
    		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "saveOrder.html", 
	    		     data: {"orderType":orderType,
		    			   	"beginDate": beginDate,
		    			   	"endDate":endDate,
		    			   	"createDate":createDate,
		    			   	"scanType":scanType,
		    			   	"serviceId":serviceId,
		    			   	"price":price,
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
	    		    	 if(data.timeCompare == true){
	    		    		 if(data.assetsStatus == false && data.orderStatus == true){
	    		    			 alert("完成下单，去订单跟踪查看吧~~"); 
	    		    			 window.location.href = "orderTrackInit.html";
		    		    	 }else{
		    		    		alert("订单有异常,请重新下单!");
		    		     		 return;
		    		    	 }
	    		    	 }else{
	    		    		 alert("订单开始时间不能早于当前订单提交时间!");
	    		     		 return;
	    		    	 }
	    		    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "loginUI.html"; } 
	    		    	 else { window.location.href = "loginUI.html"; } } 
		    	});

    	}
    });
    
    
    $("#settlementAPI").click(function(){
    	var apiId = $("#apiId").val();
    	var time = $('#time').val();//次数
    	var num = $('#num').val();//数量
    	var type = $('#type').val();//套餐类型
    	 var price = $('#priceHidden').val();
		var result = window.confirm("确定要提交订单吗？");
    	if(result){
    		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "saveOrderAPI.html", 
	    		     data: {"apiId":apiId,
		    			   	"time":time,
		    			   	"price":price,
		    			   	"num":num,
		    			   	"type":type},  
	    		     dataType: "json",
	    		     success: function(data) {
	    		    	 if(data.message == true){
	    		    		 alert("完成下单，去订单跟踪查看订单吧~~");  
    		    			 window.location.href = "orderTrackInit.html";
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
    	}
    });
   
   
//点击“添加购物车”
    $("#addCar").click(function(){
    	var createDate = getCreateDate();
    	var orderType = $('.click').val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var scanType = $('.clickTime').val();
    	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
    	var serviceId = $("#serviceId").val();
    	var price = $('.price').children('strong:first').text();
    	var times = $("#timesHidden").val();
    	if(orderType==2){
    		scanType="";
    	}
    	//获得服务资产
    	var assetIds = "";
   		$('.btnNew i').each(function(){
   			assetIds = assetIds + $(this).attr("id") + ",";
		});
    	var ip="";
		var bandwidth="";
		if(orderType==2){
			if(beginDate==""||beginDate==null){
        		alert("开始时间不能为空");
        		return;
			}
		}else{
			if(beginDate==""||beginDate==null||endDate==""||endDate==null){
        		if(beginDate==""||beginDate==null){
            		alert("开始时间不能为空");
            		return;
            	}
            	if(endDate==""||endDate==null){
            		alert("结束时间不能为空");
            		return;
            	}
        	}else{
        		if(beginDate>=endDate){
            		alert("开始时间不能大于结束时间!");
            		return;
            	}
        	}
		}
		if(assetIds==""||assetIds==null){
			alert("请选择资产!");
    		return;
		}

		$.ajax({ type: "POST",
		     async: false, 
		     url: "shoppingCar.html",
		     data: {"orderType":orderType,
    			   	"beginDate": beginDate,
    			   	"endDate":endDate,
    			   	"scanType":scanType,
    			   	"serviceId":serviceId,
    			   	"assetIds":assetIds,
    			   	"price":price,
    			   	"buy_times":times}, 
		     dataType: "json", 
		     success: function(data) {
    			   		 if(data.sucess){
    			   			 alert("添加购物车成功!");
    			   			 window.location.href="selfHelpOrderInit.html?serviceId="+serviceId+"&indexPage="+indexPage;
    			   		 }
    			   			
    			   	
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});

    });
    
   

   
   function getCreateDate(){
	   var now = new Date();
   	   var createDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
   	   return createDate;
   }
   

});
//设置默认价格
function calDefaultPrice(){
	var serviceId = $("#serviceIdHidden").val();
	if(typeof(serviceId) == "undefined"){
		return;
	}
	
	switch(parseInt(serviceId)){
	case 1://默认单次
		servType = 2;
		calPrice(null);
		break;
	case 2://默认单次
		servType = 1;
		calPrice(null);
		break;
	case 3://默认长期
		servType = 4;
		calPriceLong(null,servType,null);
		break;
	case 4://默认单次
		servType = 4;
		calPrice(null);
		break;
	case 5://默认长期
		calPriceLong(null,servType,null);
		break;
	}
	$("#timesHidden").val(1);
	
}
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
   //删除购物车
    function delShopCar(orderId){
    var result = window.confirm("确定要删除这个服务吗？");
    	if(result){
    		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "delShopCar.html?orderId="+orderId, 
	    		     dataType: "json",
	    		     success: function(data) {
	    		    	  window.location.href = "showShopCar.html";
				    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "loginUI.html"; } 
	    		    	 else { window.location.href = "loginUI.html"; } } 
		    	});
    	}
    }
    //返回修改订单信息
    function orderBack(){
    	
    	var assetIds=$("#assetIds").val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var scanType = $('#scanType').val();
    	var serviceId = $("#serviceId").val();
    	var orderType = $("#orderType").val();
    	var apiId =$("#apiId").val();
    	var type =$("#type").val();
    	var num =$("#num").val();
    	var price = $('#priceHidden').val();
     window.location.href="orderBack.html?serviceId="+serviceId+"&indexPage=1&orderType="+orderType+"&beginDate="+beginDate
		    		                       +"&endDate="+endDate+"&scanType="+scanType+"&serviceId="+serviceId+"&assetIds="+assetIds+"&apiId="+apiId+"&type="+type+"&num="+num+"&price="+price;	
    }
    
    
    //计算价格
    function calPrice(assetCount){//assetCount:资产数
    	var serviceId = $("#serviceIdHidden").val();
    	var assetCountNew = 0;
    	if(assetCount==null){
    		//获取资产数
/*    		$('.dropdown-menu li').each(function(){
				var ck=$(this).find('input');
				if($(ck).is(':checked')){
					assetCountNew++;
					
				}
    		})*/
    	   	$('.btnNew i').each(function(){
    	   		assetCountNew++;
    		});
    	}else{
        	assetCountNew = assetCount;
    	}
    	
    	if(assetCountNew==0){//如果资产不选，按单个资产算价格
    		assetCountNew = 1;
    	}
    	
    	$.ajax({ type: "POST",
   		     async: false, 
   		     url: "calPrice.html", 
   		     data:{"serviceId":serviceId,"assetCount":assetCountNew},
   		     dataType: "json",
   		     success: function(data) {
       			if(data.success){
     		    	  var price = data.price;
     		    	  $("#price").html("¥"+price);
     		    	  $("#timesHidden").val(data.times);
       			}

	    	 }, 
	    	 error: function(data){ 
	    		 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
	    	
    	});
	}
   
    //计算长期价格
    function calPriceLong(obj,typeDefault,assetCount){
    	var serviceId = $("#serviceIdHidden").val();
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var assetCountNew = 0;
    	if(assetCount==null){
    		//获取资产数
/*    		$('.dropdown-menu li').each(function(){
				var ck=$(this).find('input');
				if($(ck).is(':checked')){
					assetCountNew++;
					
				}
    		})*/
    		
    	   	$('.btnNew i').each(function(){
    	   		assetCountNew++;
    		});
    		
    	}else{
        	assetCountNew = assetCount;
    	}
    	
    	if(assetCountNew==0){//如果资产不选，按单个资产算价格
    		assetCountNew = 1;
    	}

    	if(typeDefault!=null){
    		servType = typeDefault;
    	}else if(obj!=null){
    		servType = obj.value;
    	}else{
    		servType = $(".clickTime").val();
    	}

    	//都不为空时，判断时间大小
    	if(beginDate!=""&&beginDate!=null&&endDate!=""&&endDate!=null){
    		if(beginDate>=endDate){
         		alert("开始时间不能大于结束时间!");
         		return;
         	}
		}
   	
		$.ajax({ type: "POST",
	     async: false, 
	     url: "calPrice.html", 
	     data:{"serviceId":serviceId,"type":servType,"beginDate":beginDate,"endDate":endDate,"assetCount":assetCountNew},
	     dataType: "json",
	     success: function(data) {
   			if(data.success){
 		    	  var price = data.price;
 		    	  $("#price").html("¥"+price);
 		    	  $("#timesHidden").val(data.times);
   			}

	    	 }, 
	     error: function(data){ 
	    		 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } 
	    	} 
   	});

}