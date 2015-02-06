function getcheckbox(authorityId,i){
//	alert(i);
//	alert(authorityId);
    var test = document.getElementById(""+authorityId+i).checked;
    alert(test);
    if(test){
    	$.ajax({
            type: "POST",
            url: "/cloud-security-platform/addAuthority_userType.html",
            data: {"authorityId":authorityId,"userType":i},
            dataType:"json",
            success: function(data){},
         }); 
    }else{
    	//将记录从用户类型权限表里面删除
    	return;
    }
    
}