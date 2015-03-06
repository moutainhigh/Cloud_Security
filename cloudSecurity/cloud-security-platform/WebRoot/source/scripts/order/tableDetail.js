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
        loadQueryData();
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
	var type = $("#type").val();
    var servName = $("#servName").val();
    var state = $("#state").val();
    var begin_date = $("#begin_date").val();
    var end_date = $("#end_date").val();
    var data = "&pageIndex=" + pageIndex;
    var url = "";
    if(type!=""||servName!=""||state!=""||begin_date!=""||end_date!=""){
    	url = "/cloud-security-platform/searchCombineByPage.html?type="+type+"&servName="+servName+"&state="+state+"&begin_date="+begin_date+"&end_date="+end_date;
    }else{
    	url = "/cloud-security-platform/getOrderList.html";
    }
    $.get(url, data, function(result){
        pageIndex++;
        $("#orderTab tbody").append(result);
        if (result.length > 0) {
            flag = true;
        }
    });
}
