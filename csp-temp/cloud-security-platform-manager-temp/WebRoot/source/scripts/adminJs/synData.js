$(function(){
    
    $("#synData").click(function(){
		var result = window.confirm("确定同步数据吗？");
    	if(result){
    		$.ajax({ type: "POST",
	    		     async: false, 
	    		     url: "synData.html", 
	    		     data: {},  
	    		     dataType: "json", 
	    		     success: function(data) {
		    		    	 if(data.message == false){
		    		    		 alert("数据同步失败");
		    		     		 return;
		    		    	 }else{
		    		    		 alert("数据同步成功");
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


