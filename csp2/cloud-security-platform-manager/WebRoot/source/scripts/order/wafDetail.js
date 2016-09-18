$(function(){
	//上下键操作下拉框
	$("#month").keydown(function(event){
		var isFocus=$("#month").is(":focus");
		if(isFocus){
			var month = parseInt($('#month').val());
			if((event.keyCode == 38) || (event.keyCode == 37)){//上
				//价格

				if(month==-1||month==1){
					$("#price").html("880");
				}else{
					var priceVals = 880*(month-1);
					$("#price").html(priceVals);
				}
			}
			if((event.keyCode == 39) || (event.keyCode == 40)){//下
				//价格

				if(month==-1){
					$("#price").html("880");
				}else{
					if(month==11){
						$("#price").html("9680");
					}else{
						var priceVals = 880*(month+1);
						$("#price").html(priceVals);
					}
				}
			}
		}
	})
	//价格
	var month = $('#month').val();
	if(month=='-1'){
		$("#price").html("880");
	}else{
		var priceVals = 880*month;
		$("#price").html(priceVals);
	}
	
	//回显修改信息
	getWafInfo();
	
    //跳转到waf详情页
    $("#wafDetail").click(function(){
    	$.ajax({ type: "POST",
		     async: false, 
		     url: "getSession.html", 
		     dataType: "json", 
		     success: function(data) {
		    	 //window.location.href="wafDetails.html?serviceId=6";
    			 $("#serviceIdWafHidden").val(6);
    			 $("#wafDetailsForm").submit();
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
       	var  month=null;
   
      if(orderType=='8'){
    	  beginDate=$('#beginDateForMonth').val();
    	  month =$('#month').val();
       if(month==-1){
    	    alert("请选择服务期限!");  
    	    return;
       }
      }else{
    	  beginDate=$('#beginDateForYear').val();
    	  month=12;
      }
     var serviceId = $('#serviceIdHidden').val();
     //价格
     // var price = $('#price').html().substr(1);
      var domainId = $('.ym span').attr('id');
      //网站域名
      var domainName =$('.ym span').text();
     
      if(beginDate==""||beginDate==null){
        		alert("开始时间不能为空");
        		return;
			}
      //选中的网站地址	
      if(domainId==""||domainId==null){
    	  alert("请选择要服务的网站!");
    	  return;
      }
    	
      	var ip="";		
		$('.fack li').each(function(index, element) {
			var tval= $(this).contents().filter(function() { return this.nodeType == 3; }).text(); 
			if(tval!=null && tval!=''){
				ip = ip+tval+","; 
			}		
		});
      
        var ipVal = ip.substring(0,ip.length-1);

        	$.ajax({ type: "POST",
		     async: false, 
		     url: "VerificationIP.html",
		     data: {
        		   "serviceId":serviceId,
	        		"orderType":orderType,
	        		"beginDate":beginDate,
	        		"month":month,
	        		"ipVal":ipVal,
	        		"domainName":domainName,
	        		 "domainId":domainId,
        		    }, 
		     dataType: "json", 
		     success: function(data) {
    			   		 if(!data.flag){
    			   			 alert("输入域名对应IP地址与绑定的域名IP地址不一致!输入的错误ip地址是："+data.errorIp);
    			   		 }else{
    			   			 addCart(data.serviceId,data.orderType,data.beginDate,data.ipStr,data.month,data.domainName,data.domainId);
    			   		 }
    			 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});

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
          if(beginDate==""||beginDate==null){
      		alert("开始时间不能为空");
      		return;
          }
    	  times = $('#month').val();
    	  if(times==-1){
    		  alert("请选择服务期限!");
    		  return;
    	  }
      }else{
    	  beginDate=$('#beginDateForYear').val();
          if(beginDate==""||beginDate==null){
      		alert("开始时间不能为空");
      		return;
          }
      }
 
      //网站域名
      var domainName = $('.ym span').text();
      var domainId = $('.ym span').attr('id');
      var price = $('#price').html().substr(1);
      var ipAddr ="/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/";

      //ip地址
        var Ipval="";
		$('.fack li').each(function(index, element) {
			var ip= $(this).contents().filter(function() { return this.nodeType == 3; }).text(); 
			if(ip!=null && ip!=''){
				Ipval = Ipval + ip +",";
			}		
		});
		
		
      var ipVal = Ipval.substring(0,Ipval.length-1);
      if(ipVal==''||ipVal==null){
    	  alert("请选择要服务的网站!");
      	  return false;
      }

    	$.ajax({ type: "POST",
	     async: false, 
	     url: "VerificationIP.html",
	     data: {"ipVal":ipVal,
    		    "domainName":domainName}, 
	     dataType: "json", 
	     success: function(data) {
	   		 if(!data.flag){
	   			 alert("输入域名对应IP地址与绑定的域名IP地址不一致!输入的错误ip地址是："+data.errorIp);
	   		 }else{
	   			$.ajax({ type: "POST",
		   		     async: false, 
		   		     url: "getSession.html", 
		   		     dataType: "json", 
		   		     success: function(data) {
		   		     		var tempForm = document.createElement("form");
  							tempForm.action = "buyNowWafUI.html";
  							tempForm.method = "post";
  							tempForm.style.display = "none";
  							
  						   var beginDateInput = document.createElement("input");
  							beginDateInput.type="hidden"; 
							beginDateInput.name= "beginDate"; 
							beginDateInput.value= beginDate; 
							tempForm.appendChild(beginDateInput); 
							
							var timeswafInput = document.createElement("input");
  							timeswafInput.type="hidden"; 
							timeswafInput.name= "timeswaf"; 
							timeswafInput.value= times; 
							tempForm.appendChild(timeswafInput);
							
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
							
							var domainNameInput = document.createElement("input");
  							domainNameInput.type="hidden"; 
							domainNameInput.name= "domainName"; 
							domainNameInput.value= domainName; 
							tempForm.appendChild(domainNameInput);
							
							var domainIdInput = document.createElement("input");
  							domainIdInput.type="hidden"; 
							domainIdInput.name= "domainId"; 
							domainIdInput.value= domainId; 
							tempForm.appendChild(domainIdInput);
							
							
							
							var ipArrayInput = document.createElement("input");
  							ipArrayInput.type="hidden"; 
							ipArrayInput.name= "ipArray"; 
							ipArrayInput.value= ipVal; 
							tempForm.appendChild(ipArrayInput);
							
							document.body.appendChild(tempForm);
							tempForm.submit();
							document.body.removeChild(tempForm);
		   		    	 //window.location.href="buyNowWafUI.html?type="+2+"&beginDate="+beginDate+"&timeswaf="+times+"&scanType="+scanType+"&serviceId="+serviceId+
		   		    	 //"&domainName="+domainName+"&domainId="+domainId+"&price="+price+"&ipArray="+ipVal;
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
     
 });
   
    $('#settlementWaf').click(function(){

    	var createDate = getCreateDate();
    	var orderDetailId = $("#orderDetailId").val();
    	var userName =  $(".test_name").text();
        var userAdd = $(".test_add").text();
        var mobile =  $(".test_iphone").text();
    	
    		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "saveWafOrder.html", 
	    		     data: {"orderDetailId":orderDetailId,
    			            "linkname": userName,
		    			   	"email":userAdd,
		    			   	"createDate":createDate,
		    			   	"phone":mobile},  
	    		     dataType: "json", 
	    		     success: function(data) {
		    		         if(data.error){
		    		        	alert("参数值数据异常!!");
			    				window.location.href = "index.html";
		    		     	    return;
		    		         }else if(data.assetsStatus == true){
			    					alert("订单资产未验证,请重新购买!");
			    		     		return;
			    				}else if(data.orderStatus == true){
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
			    					alert("订单异常,请重新购买!");
			    		     		return;
			    				}
	    		    	 }, 
	    		     error: function(data){ 
	    		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
	    		    		 window.location.href = "loginUI.html"; } 
	    		    	 else { window.location.href = "loginUI.html"; } } 
		    	});

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
	
	//判断port是否有效
	var isPort = function(value){
		var parten=/^(\d)+$/g;  
	    if(parten.test(value)&&parseInt(value)<=65535&&parseInt(value)>=0){  
	        return true;  
	     }else{  
	        return false;  
	     }  
	}
	
	//判断数组是否有重复数据
	var isRepeat = function(arr) {
		var nary = arr.sort();
		for(var i = 0; i < nary.length - 1; i++)
		{
			if (nary[i]!=null && nary[i]!='' && nary[i+1]!=null && nary[i+1]!='' && nary[i] == nary[i+1])
			{
				return true;
			}
		}
		return false;
	}
   
 function chanageDiv(value){
  //类型
      if(value=='long'){
    	  $("#yearDiv").show();
    	   $("#monthDiv").hide();
    	   $("#price").html("9000");
      }else{
    	  $("#yearDiv").hide();
    	   $("#monthDiv").show();
    	  
    		var month = $('#month').val();
    		if(month=='-1'){
    			$("#price").html("880");
    		}else{
    			var priceVals = 880*month;
        		$("#price").html(priceVals);
    		}
    		
      }
 }
 
 //添加到购物车
 function addCart(serviceId,orderType,beginDate,ipStr,month,domainName,domainId){
	 $.ajax({ type: "POST",
		     async: false, 
		     url: "shoppingWaf.html",
		     data: {
		            "serviceId":serviceId,
		            "orderType":orderType,
    			   	"beginDate": beginDate,
    			   	"ipStr":ipStr,
    			   	"month":month,
    			   	"domainName":domainName,
    			   	"domainId":domainId
    			   	}, 
		     dataType: "json", 
		     success: function(data) {
    			   		if(data.error){
    			   			alert("参数值数据异常!");
    			   			window.location.href="index.html";
    			   		 }else  if(data.sucess){
    			   			 alert("添加购物车成功!");
    			   			 //window.location.href="wafDetails.html?serviceId="+data.serviceId;
    			   			 $("#serviceIdWafHidden").val(data.serviceId);
    		    			 $("#wafDetailsForm").submit();
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
	$("#orderBackForm").submit();
	//var domainId=$("#assetIds").val();
	//var beginDate=$('#beginDate').val();
	//var endDate=$('#endDate').val();
	//var serviceId = $("#serviceId").val();
	//var scanType = $("#scanType").val();
	//var domainName = $("#assetAddr").val();
	//var ipArray = $("#ipArrayHidden").val();
	//var times = $("#timesHidden").val();

	//window.location.href="wafOrderBack.html?serviceId="+serviceId+"&beginDate="+beginDate+"&endDate="+endDate+
	//"&scanType="+scanType+"&serviceId="+serviceId+"&domainId="+domainId+"&domainName="+domainName+"&ipArray="+ipArray+"&times="+times;

}

//提取修改信息
function getWafInfo(){
	 var scanType = $('#scanTypeHidden').val();
	 var beginDate = $('#beginDateHidden').val();
	 var times = $('#timesHidden').val();
	 var domainName = $('#domainNameHidden').val();
	 var domainId = $('#domainIdHidden').val();
	 var ipArray = $('#ipArrayHidden').val();
	 if(scanType!=null && scanType!=''){
		 if(scanType=='8'){
			 $('.Single').addClass("click");
			 $('.long').removeClass("click");
			 $('#beginDateForMonth').val(beginDate.substring(0,10));
			 $('#month').val(times);
			 $("#yearDiv").hide();
	    	 $("#monthDiv").show();

    		if(times=='-1'){
    			$("#price").html("880");
    		}else{
    			var priceVals = 880*times;
    			$("#price").html(priceVals);
    		}
			 
		 }else if(scanType=='9'){
			 $('.long').addClass("click");
			 $('.Single').removeClass("click");
			 $('#beginDateForYear').val(beginDate.substring(0,10));
			 $("#price").html("9000");
			 $("#yearDiv").show();
	    	 $("#monthDiv").hide();
			 
		 }
		/* $('#domainName').val(domainName);
		 
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
		 }*/
		 
		 //回显选中的资产
		 var arrtlink = ipArray.split(",");
		 var list='';
         var index=0;
         for(var i=0;i<arrtlink.length;i++){
             index++;
             if(arrtlink[i]!=null&&arrtlink[i]!=''){
            	 list+='<li id='+ index +'><em>ip<b>'+ index +'</b>：</em>'+ arrtlink[i] +'</li>';  
             }            
         }
         $('.ym span').text(domainName);                       
		 $('.ym span').attr('id',domainId);
		 $('.fack').append(list);
         
         //显示页面删除和容器标签
         $('#dele').show();
         $('.not').hide();
         $('.http').show();
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

function changePrice(){
	//获得选择的月份
	var month = $('#month').val();
	if(month=='-1'){
		$("#price").html("880");
	}else{
		var priceVals = 880*month;
		$("#price").html(priceVals);
	}

}




function saveWafAsset() {
	var assetName =$.trim($("#assetName").val());
	var assetAddr = $.trim($("#InertAddr").val());
     //var addrType = $('input:radio[name="addrType"]:checked').val();
     var purpose = $("#purpose").val();
     var prov = $("#districtId").val();
     var city = $("#city").val();
      var patrn=new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
     var pattern = new RegExp("[`~!@#$^&*()=|{}';',<>?~！@#￥……&*（）——|{}【】‘；”“'。，？]"); 
    
     var newRegex = /^((?!([hH][tT][tT][pP][sS]?)\:*\/*)([\w\.\-]+(\:[\w\.\&%\$\-]+)*@)?((([^\s\(\)\<\>\\\"\.\[\]\,@;:]+)(\.[^\s\(\)\<\>\\\"\.\[\]\,@;:]+)*(\.[a-zA-Z]{2,4}))|((([01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}([01]?\d{1,2}|2[0-4]\d|25[0-5])))(\b\:(6553[0-5]|655[0-2]\d|65[0-4]\d{2}|6[0-4]\d{3}|[1-5]\d{4}|[1-9]\d{0,3}|0)\b)?((\/[^\/][\w\.\,\?\'\\\/\+&%\$#\=~_\-@]*)*[^\.\,\?\"\'\(\)\[\]!;<>{}\s\x7F-\xFF])?)$/;
     var strRegex = /^((([hH][tT][tT][pP][sS]?):\/\/)([\w\.\-]+(\:[\w\.\&%\$\-]+)*@)?((([^\s\(\)\<\>\\\"\.\[\]\,@;:]+)(\.[^\s\(\)\<\>\\\"\.\[\]\,@;:]+)*(\.[a-zA-Z]{2,4}))|((([01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}([01]?\d{1,2}|2[0-4]\d|25[0-5])))(\b\:(6553[0-5]|655[0-2]\d|65[0-4]\d{2}|6[0-4]\d{3}|[1-5]\d{4}|[1-9]\d{0,3}|0)\b)?((\/[^\/][\w\.\,\?\'\\\/\+&%\$#\=~_\-@]*)*[^\.\,\?\"\'\(\)\[\]!;<>{}\s\x7F-\xFF])?)$/;
 
	//获取选中的radio的值
	$("#assetName_msg").html("");
	$("#assetAddr_msg").html("");
	$("#location_msg").html("");
	$("#assetUsage_msg").html("");
    if(assetName == null || assetName == ""){
		$("#assetName_msg").html("请输入网站名称!");
	}else if(patrn.test(assetName)){
		$("#assetName_msg").html("请输入正确的网站名称");
	}else if(assetAddr==null || assetAddr == ""){
			$("#assetName_msg").html("");
			$("#assetAddr_msg").html("请输入网站地址!");
	}else if(pattern.test(assetAddr)){
		("#assetName_msg").html("");
		$("#assetAddr_msg").html("请输入正确的网站地址!");
	}else if((!strRegex.test(assetAddr) && !newRegex.test(assetAddr)) || (strRegex.test(assetAddr)&&assetAddr.indexOf('\/\/\/')!=-1)){
        $("#assetName_msg").html("");
        $("#assetAddr_msg").html("请输入正确的网站地址!");
	}else if(prov == -1){
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("");
		$("#location_msg").html("请选择网站所在物理地址!");
	}else if(purpose==-1){
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("");
		$("#location_msg").html("");
		$("#assetUsage_msg").html("请选择网站用途!");
	}else{
			//验证资产是否重复
			$.ajax({
		        type: "POST",
		        url: "asset_addrIsExist.html",
		        data: {"addr":assetAddr,"name": encodeURI(assetName)},
		        dataType:"json",
		        success: function(data){
		            if(data.msg=='1'){
		            	$("#assetName_msg").html("网站名称重复!");
		            }else if(data.msg=='2'){
		            	$("#assetName_msg").html("");
		            	$("#assetAddr_msg").html("网站地址重复!");
		            }else{
		            	$("#assetName_msg").html("");
		            	$("#assetAddr_msg").html("");
		            	//资产数验证
		            	$.ajax({
		    		        type: "POST",
		    		        url: "asset_CountOver.html",
		    		        data: {},
		    		        dataType:"json",
		    		        success: function(data){
		    		            if(data.msg){
		    		            	alert("免费用户管理资产数不能大于" + data.allowCount);
		    		            }else{
		    			       		 $.ajax({
		    			       		 			type:'POST',
		    				 					url:'addWafWebSite.html',
		    				 					data:{
			    				  	               'assetName':assetName,
			    				  	               'assetAddr':assetAddr,
			    				  	               'purpose':purpose,
			    				  	               'prov':prov,
			    				  	               'city':city
		    				 					},
		    				 					//beforeSubmit:showRequest,
		    				 					success: function(data) {
		    				 						    $("#assetName_msg").html("");
														$("#assetAddr_msg").html("");
														$("#location_msg").html("");
														$("#assetUsage_msg").html("");
    		    				 						switch(data.result) {
		    		            							case 0:
		    		            								alert("添加成功!");
		    		            								
		    		            								var list = data.serviceAssetList;
				    				 							if(list!=null&&list.length>0){
				    				 								$("#assBox").empty();
				    				 								$.each(list,function(n,asset) {
				    				 						         var temp = "<li>"+
				    				 					            	 		"<div class='rcent'>"+
				    				 					            	 		"<h3>"+
				    				 			                                "<label onclick='selWafAsset(this)'>"+
				    				 			                                     "<input type='radio' class='radio'  style='display:none' name='anquan' value='"+asset.id+"'><i class=''></i>"+
				    				 			                                "</label>"+
				    				 			                                 "<b>"+asset.name+"</b>"+
				    				 			                            
				    				 			                            "</h3>"+
				    				 			                            "<div class='tBox'>"+asset.addr+"</div>"+
				    				 			                            "</div>"+
				    				 			                            "</li>";  
				    				 						         		$("#assBox").append(temp);
				    				 						           });  
				    				 								} 
				    				 							$('#sentwo').fadeOut(20);
				    				 			                $('#senone').fadeIn(20);
		    		            								break;
		    		            							case 1:
		    		            								alert("免费用户管理资产数不能大于" + data.subResult);
		    		            								break;
		    		            							case 2:
					    		            					if (data.subResult == 1) {
					    		            						$("#assetName_msg").html("请输入网站名称!");
					    		            					}else if(data.subResult == 2) {
					    		            						$("#assetName_msg").html("请输入正确的网站名称!");
					    		            					}else if(data.subResult == 3) {
					    		            						$("#assetName_msg").html("网站名称重复!");
					    		            					}
					    		            					break;
					    		            				case 3:
					    		            					if (data.subResult == 1) {
					    		            						$("#assetAddr_msg").html("请输入网站地址!");
					    		            					}else if(data.subResult == 2) {
					    		            						$("#assetAddr_msg").html("请输入正确的网站地址!");
					    		            					}else if(data.subResult == 3) {
					    		            						$("#assetName_msg").html("网站地址重复!");
					    		            					}
					    		            					break;
					    		            				case 4:
					    		            					$("#location_msg").html("请选择网站所在物理地址!");
					    		            					break;
					    		            				case 5:
					    		            					$("#assetUsage_msg").html("请选择网站用途!");
					    		            					break;
					    		            				case 6:
					    		            					alert("网站地址不是域名,请填写域名!");
													break;
					    		            				default:
					    		            					alert("添加失败!");
					    		            					break;
					    		            			}
		    				 					
		    				 					},
		    				 					error: function(data){
		    				 						 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    				 				    		 window.location.href = "loginUI.html"; } 
		    				 				    	 else { window.location.href = "loginUI.html"; } 
		    				 					}
		    				 				});
		    						 		 // 将options传给ajaxForm
		    						 		 //$('#saveWafAsset').ajaxSubmit(options);
		    		            }
		    		        }
		    		     }); 
		            }
		        }
		     }); 
		}
	
}

function selWafAsset(Obj){
	//显示填写IP的输入框	
	var bas=$('.basic').length;
    if(bas==1){
        //点击单选，显示选中效果         
       $('#wafBox .waflist').remove();
       $('#wafBox input:text').val('');
       
        $('.hide').show();
        $('#acIp').show();
        $('#senone li').removeClass('ac');
        $('#senone i').removeClass('this');
        $(Obj).parents('li').addClass('ac');
        $(Obj).children('i').addClass('this');  
    }else{
       $('#wafBox span').before('<li class="basic"><input type="text" class="text"></li>') 
    }
}