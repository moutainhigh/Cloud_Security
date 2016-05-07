$(function(){

    //购物车点击“去结算”
    $("#shopBuy").click(function(){
       var str="";
		$("input:checkbox[name=check_name]:checked").each(function(obj){
		   str+=$(this).val()+",";
    	});
		alert(str);
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
    });
    
});


 
  