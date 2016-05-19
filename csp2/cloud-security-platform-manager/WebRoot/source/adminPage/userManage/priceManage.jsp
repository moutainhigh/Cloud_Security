<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>价格维护</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<link href="${ctx}/source/css/price/styles.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/adminJs/price.js"></script>

</head>
<body>
    <div id="base" class="">
    服务id:<input type="text" id="serviceId"/>
    <div id="td0"><!--

	 
     <c:if test="${priceList!= null}">
     <c:forEach var="price" items="${priceList}" varStatus="status">
         <div id="td">
	      <div class="u10">
	        <img class="u10_img" src="${ctx }/source/images/transparent.gif"/>
	         Unnamed () 
	        <div class="u11" class="text">
	          <p><span>大于</span></p>
	        </div>
	      </div>
	
	       Unnamed (Text Field) 
	      <div class="u12">
	        <input class="u12_input" type="text" id="timesBegin" value='${price.timesBegin }' 
	         onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	         onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
	      </div>
	
	
	
	       Unnamed (Shape) 
	      <div class="u14">
	        <img class="u14_img" src="${ctx }/source/images/transparent.gif"/>
	         Unnamed () 
	        <div class="u15">
	          <p><span>小于等于</span></p>
	        </div>
	      </div>
	
	       Unnamed (Text Field) 
	      <div class="u16">
	        <input class="u16_input" type="text" id="timesEnd" value="${price.timesEnd }"
	        onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	        onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
	      </div>
	
	       Unnamed (Shape) 
	      <div class="u17">
	        <img class="u17_img"  src="${ctx }/source/images/transparent.gif"/>
	         Unnamed () 
	        <div class="u18">
	          <p><span>价格</span></p>
	        </div>
	      </div>
	
	       Unnamed (Text Field) 
	      <div class="u19">
	        <input class="u19_input" type="text" id="price" value="${price.price }"/>
	      </div>
	
	       Unnamed (HTML Button) 
	      <div class="u20">
	        <input type="button" class="addtd" value="添加行"/>
	      </div>
	
	       Unnamed (HTML Button) 
	      <div class="u21">
	        <input  type="button" class="deltd" value="删除行"/>
	      </div>
      </div>
      </c:forEach>
     </c:if>
   
     --><c:if test="${empty priceList}">

         <div id="single" style="padding-bottom:38px">
         <div class="u10" style="left:50px">
	        <img class="u10_img" src="${ctx }/source/images/transparent.gif"/>
	        <!-- Unnamed () -->
	        <div class="u11" class="text">
	          <p><span>单次：</span></p>
	        </div>
	      </div>
	      
	      <div class="u10">
	        <img class="u10_img" src="${ctx }/source/images/transparent.gif"/>
	        <!-- Unnamed () -->
	        <div class="u11" class="text">
	          <p><span>价格</span></p>
	        </div>
	      </div>

	      <!-- Unnamed (Text Field) -->
	      <div class="u12">
	        <input class="u12_input" type="text"  id="singlePrice"/>
	      </div>
	      </div>
	      
	      <div id="max" style="padding-bottom:38px">
         <div class="u10" style="left:50px">
	        <img class="u10_img" src="${ctx }/source/images/transparent.gif"/>
	        <!-- Unnamed () -->
	        <div class="u11" class="text">
	          <p><span>最大限值：</span></p>
	        </div>
	      </div>
	      <div class="u10">
	        <img class="u10_img" src="${ctx }/source/images/transparent.gif"/>
	        <!-- Unnamed () -->
	        <div class="u11" class="text">
	          <p><span>最大值</span></p>
	        </div>
	      </div>
	
	      <!-- Unnamed (Text Field) -->
	      <div class="u12">
	        <input class="u12_input" type="text" id="timesMax"/>
	      </div>
	     	      <!-- Unnamed (Shape) -->
	      <div class="u14">
	        <img class="u14_img" src="${ctx }/source/images/transparent.gif"/>
	        <!-- Unnamed () -->
	        <div class="u15">
	          <p><span>价格</span></p>
	        </div>
	      </div>
	
	      <!-- Unnamed (Text Field) -->
	      <div class="u16">
	        <input class="u16_input" type="text" id="timesMaxPrice"/>
	      </div>
	      </div>
	      <div id="td">
	      <div id="long" style="padding-bottom:38px">
          <div class="u10" style="left:50px">
	        <img class="u10_img" src="${ctx }/source/images/transparent.gif"/>
	        <!-- Unnamed () -->
	        <div class="u11" class="text">
	          <p><span>区间：</span></p>
	        </div>
	      </div>
	      
	      <div class="u10">
	        <img class="u10_img" src="${ctx }/source/images/transparent.gif"/>
	        <!-- Unnamed () -->
	        <div class="u11" class="text">
	          <p><span>大于</span></p>
	        </div>
	      </div>
	
	      <!-- Unnamed (Text Field) -->
	      <div class="u12">
	        <input class="u12_input" type="text" id="timesG"/>
	      </div>
	
	
	
	      <!-- Unnamed (Shape) -->
	      <div class="u14">
	        <img class="u14_img" src="${ctx }/source/images/transparent.gif"/>
	        <!-- Unnamed () -->
	        <div class="u15">
	          <p><span>小于等于</span></p>
	        </div>
	      </div>
	
	      <!-- Unnamed (Text Field) -->
	      <div class="u16">
	        <input class="u16_input" type="text" id="timesLE"/>
	      </div>
	
	      <!-- Unnamed (Shape) -->
	      <div class="u17">
	        <img class="u17_img"  src="${ctx }/source/images/transparent.gif"/>
	        <!-- Unnamed () -->
	        <div class="u18">
	          <p><span>价格</span></p>
	        </div>
	      </div>
	
	      <!-- Unnamed (Text Field) -->
	      <div class="u19">
	        <input class="u19_input" type="text" id="price" />
	      </div>
	
	      <!-- Unnamed (HTML Button) -->
	      <div class="u20">
	        <input type="button" class="addtd" value="添加行"/>
	      </div>
	
	      <!-- Unnamed (HTML Button) -->
	      <div class="u21">
	        <input  type="button" class="deltd" value="删除行"/>
	      </div>
	      </div>
      </div>
      
	 </c:if>

      </div>
      <!-- Unnamed (HTML Button) -->
      <div id="u22" class="ax_html_button">
        <input id="u22_input" type="button" onclick="savePrice();" value="保存"/>
      </div>

      <!-- Unnamed (HTML Button) -->
      <div id="u23" class="ax_html_button">
        <input id="u23_input" type="button" id="resetPrice" value="重置"/>
      </div>

    </div>

  </body>
</html>

