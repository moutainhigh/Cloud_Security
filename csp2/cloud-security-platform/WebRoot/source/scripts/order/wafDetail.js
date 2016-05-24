$(function(){
	//回显修改信息
	getWafInfo();
	
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
    $("#addCarWaf").click(function(){
    //类型
      var orderType = $('.click').val();
      
      //开始时间
      var beginDate=null;
      var month=null;
      if(orderType=='8'){
    	  beginDate=$('#beginDateForMonth').val();
    	  month = $('#month').val();
      }else{
    	  beginDate=$('#beginDateForYear').val();
      }
     var serviceId = $('#serviceIdHidden').val();
     //价格
      var price = $('#price').html().substr(1);
      //服务期限
    
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
    $("#buyNowWaf").click(function(){
      //服务id
      var serviceId = $('#serviceIdHidden').val();
      //类型
      var scanType = $('.click').val();//8:包月；9：包年
      var beginDate = '';      //开始时间
      var times = 1;//月份数
      if(scanType=='8'){
    	  beginDate=$('#beginDateForMonth').val();
    	  times = $('#month').val();
      }else{
    	  beginDate=$('#beginDateForYear').val();
      }
 
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
	    			   		    	 window.location.href="buyNowWafUI.html?type="+2+"&beginDate="+beginDate+"&times="+times+"&scanType="+scanType+"&serviceId="+serviceId+
	    			   		    	 "&domainName="+domainName+"&domainId="+domainId+"&price="+price+"&ipArray="+ipVal;
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
   
    $('#settlementWaf').click(function(){

    	var createDate = getCreateDate();
    	var scanType = $('#scanType').val();//(8:包月 9：包年)
    	var beginDate=$('#beginDate').val();
    	var endDate=$('#endDate').val();
    	var serviceId = $("#serviceId").val();
        var domainId = $("#assetIds").val();
        var price = $('#priceHidden').val();
        var ipArray = $('#ipArrayHidden').val();
        var times = $('#timesHidden').val();
		var result = window.confirm("确定要提交订单吗？");
    	if(result){
    		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "saveWafOrder.html", 
	    		     data: {"scanType":scanType,
		    			   	"beginDate": beginDate,
		    			   	"endDate":endDate,
		    			   	"createDate":createDate,
		    			   	"serviceId":serviceId,
		    			   	"domainId":domainId,
			    			"price":price,
			    			"ipArray":ipArray,
			    			"times":times},  
	    		     dataType: "json", 
	    		     success: function(data) {
	    		    	 if(data.timeCompare == true){
	    		    		 if(data.assetsStatus == false && data.orderStatus == true){
	    		    			 alert("完成下单，去订单跟踪查看吧~~"); 
	    		    			 window.location.href = "orderTrackInit.html";
		    		    	 }else{
		    		    		 alert("订单资产未验证,请重新购买!");
		    		     		 return;
		    		    	 }
	    		    	 }else{
	    		    		 alert("订单超时,请重新购买!");
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
      if(orderType=='8'){
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
 
 function getCreateDate(){
	   var now = new Date();
	   var createDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
	   return createDate;
}

//返回修改订单信息
function wafOrderBack(){	
	var domainId=$("#assetIds").val();
	var beginDate=$('#beginDate').val();
	var endDate=$('#endDate').val();
	var serviceId = $("#serviceId").val();
	var scanType = $("#scanType").val();
	var domainName = $("#assetAddr").val();
	var ipArray = $("#ipArrayHidden").val();
	var times = $("#timesHidden").val();

	window.location.href="wafOrderBack.html?serviceId="+serviceId+"&beginDate="+beginDate+"&endDate="+endDate+
	"&scanType="+scanType+"&serviceId="+serviceId+"&domainId="+domainId+"&domainName="+domainName+"&ipArray="+ipArray+"&times="+times;

}

//提取修改信息
function getWafInfo(){
	 var scanType = $('#scanTypeHidden').val();
	 var beginDate = $('#beginDateHidden').val();
	 var times = $('#timesHidden').val();
	 var domainName = $('#domainNameHidden').val();
	 var ipArray = $('#ipArrayHidden').val();
	 if(scanType!=null && scanType!=''){
		 if(scanType=='8'){
			 $('.Single').addClass("click");
			 $('.long').removeClass("click");
			 $('#beginDateForMonth').val(beginDate.substring(0,10));
			 $('#month').val(times);
			 $("#price").html("¥100");
			 $("#yearDiv").hide();
	    	 $("#monthDiv").show();

			 
		 }else if(scanType=='9'){
			 $('.long').addClass("click");
			 $('.Single').removeClass("click");
			 $('#beginDateForYear').val(beginDate.substring(0,10));
			 $("#price").html("¥1000");
			 $("#yearDiv").show();
	    	 $("#monthDiv").hide();
			 
		 }
		 $('#domainName').val(domainName);
		 
		 var ips = ipArray.split(',');
		 for(var n in ips){
			 if(n==0){
	    		  $('#wafbox').children('li:first').children('input').val(ips[n]);

	    	  }else{
	    		     var imgSrc = getRootPath() + '/source/images/portal/del.png';
	    			 var html = '';
	    			 html+= '<li class="add-list"> ';
	    			 html+='<input type="text" class="text"  style="width:236px;" name="IPValue" value="'+ips[n]+'">';
	    			 html+='<i class="del_list"><img src='+ imgSrc+'></i>';
	    			 html+='</li>';
	    		  $('#wafbox').children('li:first').after(html);	
	    	  }
		 }
	 }


}

function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}