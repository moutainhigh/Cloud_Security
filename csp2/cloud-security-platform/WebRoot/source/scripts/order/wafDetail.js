$(function(){
//跳转到waf详情页
    $("#wafDetail").click(function(){
    	$.ajax({ type: "POST",
		     async: false, 
		     url: "getSession.html", 
		     dataType: "json", 
		     success: function(data) {
		    	 window.location.href="wafDetails.html";
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});

    });
    //点击“添加购物车”
    $("#addCar").click(function(){
    //类型
      var orderType = $('.click').val();
      //开始时间
      var beginDate=$('#beginDate').val();
      //结束时间
      var endDate=$('#endDate').val();
      //网站域名
      var domainName = $('#domainName').val();
      var ipAddr ="/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/";
      if(beginDate==""||beginDate==null){
        		alert("开始时间不能为空");
        		return;
			}
          if(endDate==""||endDate==null){
        		alert("结束时间不能为空");
        		return;
			}
          if(beginDate>endDate){
        	 alert("开始时间不能大于结束时间!");
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
      /*  if(isTrue){
        	$.ajax({ type: "POST",
		     async: false, 
		     url: "shoppingWaf.html",
		     data: {"orderType":orderType,
    			   	"beginDate": beginDate,
    			   	"endDate":endDate,
    			   	"scanType":scanType,
    			   	"serviceId":serviceId,
    			   	"assetIds":assetIds,
    			   	"price":price}, 
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

        }*/
         
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
      if(orderType=='0'){
    	  $("#yearDiv").show();
    	   $("#monthDiv").hide();
      }else{
    	  $("#yearDiv").hide();
    	   $("#monthDiv").show();
      }
 }