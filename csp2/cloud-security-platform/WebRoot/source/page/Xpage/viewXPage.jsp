<%@ page language="java" import="java.util.*,java.lang.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/source/scripts/common/js/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript">
function detectionUrl(){
	var urlInfo = $("#urlInfo").val();
		$.ajax({
            type: "POST",
            url: "detectionUrl.html",
            data: {"urlInfo":urlInfo},
            dataType:"json",
            success: function(data){
                if(data){
            	    $("#url_prompt").html("<b>没有漏洞</b>");
            	    return;
                }else{
            	    $("#url_prompt").html("<b>有漏洞</b>");
            	   
                }
            },
         }); 
	}
	
</script>
 <body>
 
 
    Struts2 漏洞监测  <br/>
     <input type="text" name="urlInfo" id="urlInfo" style="width: 500px"/> <input type="button" value="检测" onclick="detectionUrl();"/><br/>
    
     检测结果<br/>
   <div  id="url_prompt"><b></b></div>
  </body>