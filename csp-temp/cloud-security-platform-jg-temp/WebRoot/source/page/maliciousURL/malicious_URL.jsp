<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

<title>安全帮-中国电信云安全服务在线商城</title>
<link type="text/css" href="${ctx}/source/css/jquery.fullPage.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/source/css/style.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.fullPage.min.js"></script>

</head>

<body>

<div id="fullpage">

    <div class="section">
    	 <iframe src="${ctx}/worldMaliciousUrl.html " width="100%" height="100%" frameborder="1"  marginwidth="0" marginheight="0" scrolling="no"></iframe> 
    	
        	
       
    </div> 
    <div class="section">
    	<iframe src="${ctx}/chinaMaliciousUrl.html" width="100%" height="100%" frameborder="1"  marginwidth="0" marginheight="0" scrolling="no"></iframe> 
    	
    </div>
    <div class="section">
    	<iframe src="${ctx}/maliciousUrlAnalysis.html " width="100%" height="100%" frameborder="1"  marginwidth="0" marginheight="0" scrolling="no"></iframe> 
    	
    </div>
    
    
</div>
<script>
$(function(){
    $('#fullpage').fullpage({
        'verticalCentered': false,
        'css3': true,
      //  'sectionsColor': ['#825b56', '#343b4e', '#4b6558', '#822c2b'],
        anchors: ['page1', 'page2', 'page3'],
        'navigation': true,
        'navigationPosition': 'right',
        'navigationTooltips': ['全球恶意URL分布', '全国恶意URL分布', '钓鱼网站分析'],
    });
	
	

})


</script>
</body>
</html>
