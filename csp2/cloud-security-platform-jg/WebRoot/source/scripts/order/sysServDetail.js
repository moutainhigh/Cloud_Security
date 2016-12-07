var saveAssetFlag = 0;
$(function(){
  
    //点击“添加购物车”按钮
    $("#addCar").click(function(){
      alert("此服务尚未配置完成  请关注其他服务1");  

     });
    
    //立即购买
    $("#buyNow").click(function(){
      alert("此服务尚未配置完成  请关注其他服务2");
      var createDate = getCreateDate();
      //var type = $('.click').val();
      var type = 1;
      //scan_type
      var scanType = $('#num').val();
      var indexPage = $("#indexPage").val();//标记从首页进入自助下单流程
      var serviceId = 7;
      var times = $("#timesHidden").val();
      if(type==2){
        scanType="";
      }

    $.ajax({ type: "POST",
      async: false, 
      url: "getSession.html", 
      dataType: "json", 
      success: function(data) {
        //虚拟表单post提交
        var tempForm = document.createElement("form");
        tempForm.action = "****.html";
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
              
        var timesInput = document.createElement("input");
        timesInput.type="hidden"; 
        timesInput.name= "buy_times"; 
        timesInput.value= times; 
        tempForm.appendChild(timesInput);
              
              
              
        document.body.appendChild(tempForm);
        tempForm.submit();
        document.body.removeChild(tempForm);
           //window.location.href="settlement.html?type="+type+"&beginDate="+beginDate+"&endDate="+endDate+"&scanType="+scanType+"&serviceId="+serviceId+"&assetIds="+assetIds+"&buy_times="+times+"&price="+priceVal;
        }, 
      error: function(data){ 
        if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
          window.location.href = "loginUI.html"; } 
        else { window.location.href = "loginUI.html"; } } 
    }); 

    });

   function getCreateDate(){
     var now = new Date();
       var createDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
       return createDate;
   }
   
    
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

/*
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
*/
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
  var ipNum = $("#num").val();
  //var serviceId = null;      
  $.ajax({ type: "POST",
    async: false, 
    //url: "sysPrice.html", 
    url:"/csp/source/page/details/lilingxiao.json",
    //data:{"serviceId":serviceId,"ipNum":ipNum},
    //serviceId及scan_type
    data: {"serviceId":serviceId,"type":servType,"beginDate":beginDate,"endDate":endDate,"orderType":serviceType},
    dataType: "json",
    success: function(data) {
      //var price=0;
      //var a=JSON.stringify(data);
      //var b=eval(a);
      /*
      var i=0;
      for(i;i<2;i++){
        if(b.ipprice[i].ipNum==ipNum){
          price=b.ipprice[i].price;
          break;
        }
      }*/
      //var result = $.parseJSON(data);
      //alert(result);
      //$("#price").html(price);
      //$("#price").html("180");
      var price = data.price;
      $("#price").html(price);

    }, 
    error: function(data){
      alert('Error loading document'); 
    }}
    /*
    error: function(data){ 
      if (data.responseText.indexOf("<!DOCTYPE html>") >= 0) { 
        window.location.href = "loginUI.html"; } 
      else { window.location.href = "loginUI.html"; } 
      } 
  }
  */);
}