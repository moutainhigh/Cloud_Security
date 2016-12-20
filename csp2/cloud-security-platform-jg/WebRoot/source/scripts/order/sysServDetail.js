$(function(){
  //初始化价格
  changePrice();

  //立即购买
  $("#buyNowsys").click(function(){
    var createDate = getCreateDate();
    //orderType
    var type = 1;
    var scanType = $("#num").val();
    var indexPage = $("#indexPage").val();
    var serviceId = 7;

    $.ajax({
      type:"POST",
      async: false,
      url:"getSession.html",
      dataType:"json",
      success:function(data){
        var tempForm = document.createElement("form");
        tempForm.action = "jiGuangselfHelpOrderOpera.html";
        tempForm.method = "post";
        tempForm.style.display = "none";

        var typeInput = document.createElement("input");
        typeInput.type="hidden"; 
        typeInput.name= "type"; 
        typeInput.value= type; 
        tempForm.appendChild(typeInput);
              
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
                           
              
        document.body.appendChild(tempForm);
        tempForm.submit();
        document.body.removeChild(tempForm);

      },
      error:function(data){
        alert("cuowu!");
      }
    });
  });

});
function getCreateDate(){
  var now = new Date();
  var createDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
  return createDate;
}
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