$(function(){
    
    //确认订单界面点击"确认订单"进入完成
    $("#buyAPI").click(function(){
    	var createDate = getCreateDate();
    	var time = $('.click').val();//次数
    	var num = $('#num').val();//数量
    	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
    	var apiId = $("#apiId").val();
    	
    	var type = $('.click').attr("name");//获取套餐类型
    	
    	//判断选择免费数量不能大于1
    	var f = $('.click').attr("id");
    	if(f=='free'){
    		if(num>1){
    			alert("免费使用数量不能大于1");
        		return;
    		}
    	}
    	if(num<=0){
    		alert("数量不能小于0");
    		return;
    	}
		$.ajax({ type: "POST",
		     async: false, 
		     url: "getSession.html",
		     dataType: "json", 
		     success: function(data) {
			    	 window.location.href="settlementAPI.html?apiId="+apiId+"&time="+time+"&num="+num+"&type="+type;
			    		 
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
    	var time = $('.click').val();//次数
    	var num = $('#num').val();//数量
    	var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
    	var apiId = $("#apiId").val();
    	
    	var type = $('.click').attr("name");//获取套餐类型
    	
    	//判断选择免费数量不能大于1
    	var f = $('.click').attr("id");
    	if(f=='free'){
    		if(num>1){
    			alert("免费使用数量不能大于1");
        		return;
    		}
    	}
    	if(num<=0){
    		alert("数量不能小于0");
    		return;
    	}
		$.ajax({ type: "POST",
		     async: false, 
		     url: "shoppingCarAPI.html", 
		     data: {"apiId":apiId,
 			   	    "time":time,
 			   	    "num":num,
 			   	    "type":type},  
		     dataType: "json", 
		     success: function(data) {
			    	 if(data.sucess){
			    		 alert("添加购物车成功!");
			    		 window.location.href="selfHelpOrderAPIInit.html?apiId="+apiId+"&indexPage="+indexPage;
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

    	}
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
