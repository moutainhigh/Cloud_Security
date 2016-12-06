var saveAssetFlag = 0;
$(function(){
	
    //点击“添加购物车”按钮
    $("#addCar").click(function(){
    	alert("此服务尚未配置完成  请关注其他服务");  

     });
    
    //立即购买
    $("#buyNow").click(function(){
    	alert("此服务尚未配置完成  请关注其他服务"); 

    });
    
   });
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

 function changeDiv(value){
  //类型
      if(value=='long'){
    	  $("#yearDiv").show();
    	  $("#monthDiv").hide();
      }else if(value=='Single'){
    	  $("#yearDiv").hide();
    	  $("#monthDiv").show();
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