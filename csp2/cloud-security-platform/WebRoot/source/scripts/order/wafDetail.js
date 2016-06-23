$(function(){
	//价格
	var month = $('#month').val();
	if(month=='-1'){
		$("#price").html("¥100");
	}else{
		var priceVals = 100*month;
		$("#price").html("¥"+priceVals);
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
      var price = $('#price').html().substr(1);
      //网站域名
      var domainName =$('.ym span').text();
      if(beginDate==""||beginDate==null){
        		alert("开始时间不能为空");
        		return;
			}
      //选中的网站地址	
      
      var ip="";
		var acval=$('#senone .ac .rcent div').text();
		$('#wafBox input:text').each(function(index, element) {
			var tval= $(this).val();
			//存入val值
		   ip = ip+tval+",";
		});
      
     var ipVal = ip.substring(0,ip.length-1);

        	$.ajax({ type: "POST",
		     async: false, 
		     url: "VerificationIP.html",
		     data: {
        		   "serviceId":serviceId,
        		    "price":price,
	        		"orderType":orderType,
	        		"beginDate":beginDate,
	        		"month":month,
	        		"ipVal":ipVal,
	        		 "domainName":domainName,
        		    }, 
		     dataType: "json", 
		     success: function(data) {
    			   		 if(!data.flag){
    			   			 alert("输入域名对应IP地址与绑定的域名IP地址不一致!输入的错误ip地址是："+data.errorIp);
    			   		 }else{
    			   			 addCart(data.serviceId,data.price,data.orderType,data.beginDate,data.endDate,data.ipStr,data.month,data.domainName);
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
		$('#wafBox input:text').each(function(index, element) {
			var ip= $(this).val();
			if(ip!=null && ip!=''){
				Ipval = Ipval + ip +",";
			}		
		});
		
		
      var ipVal = Ipval.substring(0,Ipval.length-1);
      if(ipVal==''||ipVal==null){
    	  alert("请选择网站!");
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
		   		    	 window.location.href="buyNowWafUI.html?type="+2+"&beginDate="+beginDate+"&timeswaf="+times+"&scanType="+scanType+"&serviceId="+serviceId+
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
        var serviceName = $('#serviceName').val();
        var userId = $("#userIdHidden").val();
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
			    			"timeswaf":times,
			    			"serviceName":serviceName,
			    			"userId":userId},  
	    		     dataType: "json", 
	    		     success: function(data) {
			    				if(data.userStatus == false){
				    				alert("该订单不属当前用户,请重新下单!");
				    				window.location.href = "index.html";
			    		     	    return;
				    			}else if(data.assetsStatus == true){
			    					alert("订单资产未验证,请重新购买!");
			    		     		return;
			    				}else if(data.orderStatus == true){
			    					 //alert("完成下单，去订单跟踪查看吧~~"); 
		    		    			 var orderListId = data.orderListId;
	    		    			 	 window.location.href = "cashierUI.html?orderListId="+orderListId;
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
   
 function chanageDiv(value){
  //类型
      if(value=='long'){
    	  $("#yearDiv").show();
    	   $("#monthDiv").hide();
    	   $("#price").html("¥1000");
      }else{
    	  $("#yearDiv").hide();
    	   $("#monthDiv").show();
    	  
    		var month = $('#month').val();
    		if(month=='-1'){
    			$("#price").html("¥100");
    		}else{
    			var priceVals = 100*month;
        		$("#price").html("¥"+priceVals);
    		}
    		
      }
 }
 
 //添加到购物车
 function addCart(serviceId,price,orderType,beginDate,endDate,ipStr,month,domainName){
	 $.ajax({ type: "POST",
		     async: false, 
		     url: "shoppingWaf.html",
		     data: {
		            "serviceId":serviceId,
		            "orderType":orderType,
    			   	"beginDate": beginDate,
    			   	"endDate":endDate,
    			   	"price":price,
    			   	"ipStr":ipStr,
    			   	"month":month,
    			   	"domainName":domainName
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

function changePrice(){
	//获得选择的月份
	var month = $('#month').val();
	if(month=='-1'){
		$("#price").html("¥100");
	}else{
		var priceVals = 100*month;
		$("#price").html("¥"+priceVals);
	}
}

function saveWafAsset() {
	var assetName =$("#assetName").val();
	var assetAddr = $("#InertAddr").val();
     var addrType = $('input:radio[name="addrType"]:checked').val();
     var purpose = $("#purpose").val();
     var prov = $("#districtId").val();
     var city = $("#city").val();
     var patrn=/[`~@#$%^&*()+<>"{},\\;'[\]]/im;  
	//获取选中的radio的值
    if(assetName == null || assetName == ""){
		$("#assetName_msg").html("请输入网站名称");
	}else if(patrn.test(assetName)){
		$("#assetName_msg").html("您输入的网站名称含有非法字符");
	}else if(assetName.length>25){
		$("#assetName_msg").html("网站名称长度不能超过25个字符！");
	}else if(patrn.test(assetAddr)){
		$("#assetAddr_msg").html("您输入的网站地址含有非法字符");
	}else if(assetAddr==null || assetAddr == ""){
			$("#assetName_msg").html("");
			$("#assetAddr_msg").html("请输入网站地址");
	}else if(assetAddr.length>50){
			 $("#assetName_msg").html("");
			 $("#assetAddr_msg").html("网站地址长度不能超过50个字符！");
	}else if(assetAddr.indexOf("gov.cn")!=-1){
		   $("#assetName_msg").html("");
		   $("#assetAddr_msg").html("输入网站地址不能包含'gov.cn'！");
	}else if((addrType.length==4 && assetAddr.substring(0,5)=='https') || (addrType.length==5 && assetAddr.substring(0,5)=='http:')){
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("网站类型与网站地址填写不一致!");
	}else if(prov == -1){
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("");
		$("#location_msg").html("请选择网站所在物理地址！");
	}else if(purpose==-1){
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("");
		$("#location_msg").html("");
		$("#assetUsage_msg").html("请选择网站用途！");
	}else{
		$("#assetName_msg").html("");
		$("#assetAddr_msg").html("");
		$("#location_msg").html("");
		$("#assetUsage_msg").html("");
			//验证资产是否重复
			$.ajax({
		        type: "POST",
		        url: "asset_addrIsExist.html",
		        data: {"addr":assetAddr,"name": encodeURI(assetName),"addrType":addrType},
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
		    			       		 var options = {
		    				 					url:'addWebSite.html',
		    				 					data:{
		    				  	               'assetName':assetName,
		    				  	               'assetAddr':assetAddr,
		    				  	               'addrType':addrType,
		    				  	               'purpose':purpose,
		    				  	               'prov':prov,
		    				  	               'city':city,
		    				  	               'wafFlag':1
		    				 					},
		    				 					//beforeSubmit:showRequest,
		    				 					success: function(data) {
		    				 						if(data.success){
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
		    				 						}else{
		    				 							if(!data.wafFlag){
		    				 								alert("网站地址不是域名,请填写域名!");
		    				 							}else{
			    				 							alert("添加失败!");
		    				 							}
		    				 						}
		    				 								
		    				 					},
		    				 					error: function(data){
		    				 						 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    				 				    		 window.location.href = "loginUI.html"; } 
		    				 				    	 else { window.location.href = "loginUI.html"; } 
		    				 					}
		    				 				};
		    						 		 // 将options传给ajaxForm
		    						 		 $('#saveWafAsset').ajaxSubmit(options);
		    		            }
		    		        },
		    		     }); 
		            }
		        },
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