function getcheckbox(authorityId,i){
//	alert(i);
//	alert(authorityId);
    var test = document.getElementById(""+authorityId+i).checked;
    if(test){
    	$.ajax({
            type: "POST",
            url: "/cloud-security-platform-manager/adminAddAuthority_userType.html",
            data: {"authorityId":authorityId,"userType":i},
            dataType:"json",
            success: function(data){},
         }); 
    }else{
    	//将记录从用户类型权限表里面删除
    	$.ajax({
            type: "POST",
            url: "/cloud-security-platform-manager/adminDeleteAuthority_userType.html",
            data: {"authorityId":authorityId,"userType":i},
            dataType:"json",
            success: function(data){},
         });
    }
    
}