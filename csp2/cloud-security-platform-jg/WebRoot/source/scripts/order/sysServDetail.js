$(function(){
  //初始化价格
  changePrice();

});

//计算价格
function changePrice(){
  //var serviceId = $('#serviceId').val();
  var serviceId = 7;
  var scanType = $("#num").val();
            
  $.ajax({ type: "POST",
    async: false, 
    url: "syscalPrice.html", 
    //serviceId及scan_type
    data: {"serviceId":serviceId,"scanType":scanType},
    dataType: "json",
    success: function(data) {
      var price = data.price;
      $("#price").html(price);
    }, 
    
    error: function(data){ 
      if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
        window.location.href = "loginUI.html"; } 
      else { window.location.href = "loginUI.html"; } 
      } 
  });
}