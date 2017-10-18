// 第几页面数
var pageIndex = 0;
// 控制加载，不加载完时不会进行下一次的加载
var flag = true;
// 判断是否是初始化
var isInit = true;
// 添加滚动加载数据事件
$(document).ready(function(){
    loadData();
    $(window).scroll(function(){
    	$.ajax({ type: "POST",
		     async: false, 
		     url: "getSession.html", 
		     dataType: "json", 
//		     contentType: "application/json; charset=utf-8", 
		     success: function(data) {
		    	 loadQueryData();
		    	 }, 
		     error: function(data){ 
		    	 if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
		    		 window.location.href = "loginUI.html"; } 
		    	 else { window.location.href = "loginUI.html"; } } 
		});
    });
});
// 滚动条到底加载
function loadQueryData(){
    var scrollHeight = document.getElementById("content_data_div").scrollHeight;
    var scrollTop = document.getElementById("content_data_div").scrollTop;
    var divHeight = $("#content_data_div").height();
    if ((scrollTop + divHeight) >= (scrollHeight - 10) && flag) {
        flag = false;
        loadData();
        scrollHeight = document.getElementById("content_data_div").scrollHeight;
    }
}

// 数据加载
function loadData(){
	var mark = $("#mark").val();
	var type = $("#typepage").val();
    var servName = $("#servNamepage").val();
    var begin_date = $("#begin_datepage").val();
    var end_date = $("#end_datepage").val();
    var data = "&pageIndex=" + pageIndex;
    var url = "";
    if(mark!=1){
    	url = "searchCombByPage.html?type="+type+"&servName="+servName+"&begin_date="+begin_date+"&end_date="+end_date;
    }else{
    	url = "getBillList.html";
    }
    $.get(url, data, function(result){
        pageIndex++;
        $("#billTab tbody").append(result);
        if (result.length > 0) {
            flag = true;
        }
    });
}
