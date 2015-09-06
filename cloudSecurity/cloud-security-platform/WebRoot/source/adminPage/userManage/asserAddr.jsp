<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
    
    <title>监测网站</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
    	function  _go(select) {
    		var index = select.selectedIndex;//选中的option的下标
    		var option = select.options[index];//通过下标得到option元素对象
    		var value = option.value;//通过option元素对象得到value值
    		location="<c:url value='/adminAssetAddr.html?query&pageCode='/>" + value;
    	}
</script>
<style>
.table-c table{border-right:1px solid #000;border-bottom:1px solid #000}
.table-c table td{border-left:1px solid #000;border-top:1px solid #000}
</style>
  </head>
  
  <body style="text-align: center;">
  <h3>监测网站列表</h3>
  <div class="table-c">
	  <table  border="0" cellspacing="0" cellpadding="0" align="center" width="80%">
	  <!-- <table  border="1" align="center" width="80%"> -->
	 	<tr> 
	 	    <td>序号</td>
	 		<td>资产名称</td>
	    	<td>网址</td>
	    	<td>地域</td>
	    </tr>
	  	<c:forEach items="${pb.datas }" var="asset" varStatus="status">
	       	<tr>
	       	   <td>${status.index+1 }</td>
	       	   <td>${asset.name}</td>
	       	   <td><a href="${ctx}/admingetOrdersByAsset.html?assetId=${asset.id }" target="_blank" >${asset.addr}</a></td>
	       	   <td>${asset.disName}</td>
	     	</tr>
	      </c:forEach>
	      </table>
      </div>
      </br>
      <!-- pagerBegin -->
 
第${pb.pageCode }页/共${pb.totalPage }页　　　
<a href="<c:url value='/adminAssetAddr.html'/>">首页</a>
<c:if test="${pb.pageCode > 1 }">
<a href="<c:url value='/adminAssetAddr.html?pageCode=${pb.pageCode-1 }'/>">上一页</a>
</c:if>


<%--页码列表--%>
<%--
  14 15 16 17 18 19 20 21 22 23
  10 11 12 13 14 15 16 17 18 19
 --%>
<c:set var="begin" value="1"/>
<c:set var="end" value="10"/>
<%-- 定位begin和end --%>
<c:choose>
	<c:when test="${pb.totalPage <= 10 }">
		<c:set var="begin" value="1"/>
		<c:set var="end" value="${pb.totalPage }"/>		
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${pb.pageCode - 4 < 1 }">
				<c:set var="begin" value="1"/>
				<c:set var="end" value="10"/>					
			</c:when>
			<c:when test="${pb.pageCode + 5 > pb.totalPage }">
				<c:set var="begin" value="${pb.totalPage - 9 }"/>
				<c:set var="end" value="${pb.totalPage }"/>					
			</c:when>
			<c:otherwise>
				<c:set var="begin" value="${pb.pageCode - 4 }"/>
				<c:set var="end" value="${pb.pageCode + 5 }"/>					
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
<c:forEach begin="${begin }" end="${end }" var="i">
  <c:choose>
  	<c:when test="${pb.pageCode eq i }">${i }</c:when>
  	<c:otherwise>
  		<a href="<c:url value='/adminAssetAddr.html?pageCode=${i}'/>">[${i}]</a>
  	</c:otherwise>
  </c:choose>
</c:forEach>


<c:if test="${pb.pageCode < pb.totalPage }">
<a href="<c:url value='/adminAssetAddr.html?pageCode=${pb.pageCode+1 }'/>">下一页</a>
</c:if>
<a href="<c:url value='/adminAssetAddr.html?pageCode=${pb.totalPage }'/>">尾页</a>

<%-- 下拉列表 --%>
跳到<select name="pageCode" onchange="_go(this)">
	<c:forEach begin="1" end="${pb.totalPage }" var="i">
		<option value="${i }" <c:if test="${pb.pageCode eq i }">selected='selected'</c:if>>${i}</option>
	</c:forEach>
</select>页

<!-- pagerEnd -->
  </body>
</html>