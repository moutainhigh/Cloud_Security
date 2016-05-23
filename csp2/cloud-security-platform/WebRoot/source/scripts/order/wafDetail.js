$(function(){
//跳转到waf详情页
    $("#wafDetail").click(function(){
    	$.ajax({ type: "POST",
		     async: false, 
		     url: "getSession.html", 
		     dataType: "json", 
		     success: function(data) {
		    	 window.location.href="wafDetails.html?serviceId=6";
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});

    });
    //点击“添加购物车”按钮
    $("#addCar").click(function(){
    //类型
      var orderType = $('.click').val();
      //开始时间
      var beginDate=$('#beginDate').val();
     var serviceId = $('#serviceIdHidden').val();
     //价格
      var price = $('.price').children('strong:first').text();
      //服务期限
      var month = $('#month').val();
      //网站域名
      var domainName = $('#domainName').val();
      var assetName = $("#domainName").find("option:selected").text(); 
      var ipAddr ="/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/";
      if(beginDate==""||beginDate==null){
        		alert("开始时间不能为空");
        		return;
			}
        
      //ip地址
      var Ipval= $("input[name='IPValue']").val();
      var ip="";
      if(Ipval==""||Ipval==null){
    	   alert("请输入IP地址"); 
      }else{
        $("input[name='IPValue']").each(function(obj){
         ip = ip+$(this).val()+",";
         
      });
      var ipVal = ip.substring(0,ip.length-1);
      //循环判断ip地址
      var isTrue = ipVal.split(',').every(function(ip){
    	  return isIp(ip);
		});
        if(!isTrue){
        	alert("IP地址输入无效，请输入正确的IP地址!");
        	return false;
        }
        if(isTrue){
        	$.ajax({ type: "POST",
		     async: false, 
		     url: "VerificationIP.html",
		     data: {
        		   "serviceId":serviceId,
        		    "price":price,
	        		"orderType":orderType,
	        		"assetName":assetName,
	        		"beginDate":beginDate,
	        		"month":month,
	        		"ipVal":ipVal,
	        		 "domainName":domainName,
        		    }, 
		     dataType: "json", 
		     success: function(data) {
    			   		 if(!data.flag){
    			   			 alert("输入域名对应IP地址与绑定的域名IP地址不一致!");
    			   		 }else{
    			   			 addCart(data.serviceId,data.price,data.orderType,data.beginDate,data.endDate,data.assetName,data.ipStr,data.month);
    			   		 }
    			 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});

        }
        }
         
     });
    
    //立即购买
    $("#buyNow").click(function(){
      //服务id
      var serviceId = $('#serviceIdHidden').val();
      //类型
      var scanType = $('.click').val();
      //开始时间
      var beginDate=$('#beginDate').val();
      //结束时间
      //var endDate=$('#endDate').val();
      //网站域名
      var domainName = $('#domainName').val();
      var domainId = $("#domainName option:selected").attr("assId");
      var price = $('#price').html().substr(1);
    
      //var addurl = $('#addurl').val();
      var ipAddr ="/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/";
      if(beginDate==""||beginDate==null){
        		alert("开始时间不能为空");
        		return;
			}
         
      //ip地址
      var Ipval="";
        $("input[name='IPValue']").each(function(obj){
         Ipval = Ipval+$(this).val()+",";
         
      });

      var ipVal = Ipval.substring(0,Ipval.length-1);
      //循环判断ip地址
      var isTrue = ipVal.split(',').every(function(ip){
    	 
		  return isIp(ip);//先将字符串按照逗号分成数组，在校验就可以了
		});
        if(!isTrue){
        	alert("IP地址输入无效，请输入正确的IP地址!");
        	return false;
        }
        if(isTrue){
        	$.ajax({ type: "POST",
		     async: false, 
		     url: "VerificationIP.html",
		     data: {"ipVal":ipVal,
        		    "domainName":domainName}, 
		     dataType: "json", 
		     success: function(data) {
    			   		 if(!data.flag){
    			   			 alert("输入域名对应IP地址与绑定的域名IP地址不一致!");
    			   		 }else{
    			   			$.ajax({ type: "POST",
	    			   		     async: false, 
	    			   		     url: "getSession.html", 
	    			   		     dataType: "json", 
	    			   		     success: function(data) {
	    			   		    	 window.location.href="buyNowWafUI.html?type="+2+"&beginDate="+beginDate+"&scanType="+scanType+"&serviceId="+serviceId+"&domainName="+domainName+"&domainId="+domainId+"&price="+price;
	    			   		    	 }, 
	    			   		     error: function(data){ 
	    			   		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    			   		    		 window.location.href = "loginUI.html"; } 
	    			   		    	 else { window.location.href = "loginUI.html"; } } 
    			   			});
    			   		 }
    			   			
    			   	
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});

        }
   
     });
    
   });
   

  //判断ip地址是否无效
	var isIp = function(value){
		var regexp = /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/;
		 var valid = regexp.test(value);
		      if(!valid){
	            return false;
	           }else{
	        	   return true;
	           }
	}
   
 function chanageDiv(){
  //类型
      var orderType = $('.click').val();
      if(orderType=='9'){
    	  $("#yearDiv").show();
    	   $("#monthDiv").hide();
    	   $("#price").html("¥1000");
      }else{
    	  $("#yearDiv").hide();
    	   $("#monthDiv").show();
    	   $("#price").html("¥100");
      }
 }
 
 //添加到购物车
 function addCart(serviceId,price,orderType,beginDate,endDate,assetName,ipStr,month){
	 $.ajax({ type: "POST",
		     async: false, 
		     url: "shoppingWaf.html",
		     data: {
		            "serviceId":serviceId,
		            "orderType":orderType,
    			   	"beginDate": beginDate,
    			   	"endDate":endDate,
    			   	"assetName":assetName,
    			   	"price":price,
    			   	"ipStr":ipStr,
    			   	"month":month
    			   	}, 
		     dataType: "json", 
		     success: function(data) {
    			   		 if(data.sucess){
    			   			 alert("添加购物车成功!");
    			   			 window.location.href="wafDetails.html?serviceId="+data.serviceId;
    			   		 }
    			   			
    			   	
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});
	 
 }