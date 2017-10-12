<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>服务管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/manageCss/index.css"/>

<link href="${ctx}/source/adminCss/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/head_bottom.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/adminCss/backstage.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/backstage.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/modelbox.js"></script>
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
</head>

<body>
<div id="container">
		<!--=============top==============-->
		
		<!-- menu start -->
		<c:import url="/menu.html"></c:import>
		<!-- menu end -->
<div class="main_wrap">
	<div class="main_center">
    	<div class="add_service">
        	<!--<a href="#" class="add_ser" id="add_ser">添加服务</a>
        	--><a href="${ctx}/getServiceList.html" class="sel_ser" id="sel_ser">服务列表</a>
        </div>
        <div class="b_choose">
        	<ul>
            	<li>类型：</li>
                <li class="choose_a twoword">全部</li>
                <li class="choose_a twoword">最新</li>
            </ul>
            <ul>
            	<li>厂商：</li>
                <li class="choose_a twoword">电信</li>
                <li class="choose_a twoword">360</li>
                <li class="choose_a twoword">华为</li>
                <li class="choose_a twoword">绿盟</li>
                <li class="choose_a twoword">其他</li>
            </ul>
            <ul>
            	<li>服务：</li>
                <li class="choose_a">扫描类</li>
                <li class="choose_a">监控类</li>
                <li class="choose_a">防护类</li>
                <li class="choose_a twoword">其他</li>
            </ul>
        </div>
        <div class="ser_list">
        	<ul>          
                
              <c:forEach var="list" items="${servList}" varStatus="status">
                <li class="nomr">
                   	<dl>
                       <dt></dt>
                         <dd>
                            <p class="ser_type">${list.name }</p>
                            <p>厂商：
                            <c:if test="${list.factory==1}">安恒</c:if>
                            <c:if test="${list.factory==2}">华为</c:if>
                            </p>
                            <p>
							<c:if test="${list.type==1}">扫描类</c:if>
							<c:if test="${list.type==2}">监控类</c:if>
							<c:if test="${list.type==3}">防护类</c:if>
							<c:if test="${list.type==4}">其他</c:if>
							</p>
                         </dd>
                     </dl>
                    <div class="ser_change"><a href="#">修改</a><a href="#">删除</a></div>
                </li>
              </c:forEach>  
            </ul>
        </div>
    </div>
</div>
<!--============bottom============-->
	<!-- footer start -->
	<c:import url="/footer.html"></c:import>
	<!-- footer end -->
</div>
<!--模态框-->
<div class="modelbox" id="modelbox"></div>
<div id="box_logoIn" class="box_logoIn">
  <div class="add_ser_top">
	<p>添加服务</p><p id="close"><img src="${ctx}/source/adminImages/b_exit.jpg" width="25" height="26"></p>
   </div> 
   <form method="post">
        <div class="ser_div">
        <p>服务名称</p>
          <input type="text" class="ser_input"/>
        </div>
        <div class="ser_div">
        <p>服务类型</p>
          <select class="ser_input">
          	<option>扫描类</option>
          </select>
        </div>
        <div class="ser_div">
        <p>厂商类型</p>
          <select class="ser_input">
          	<option>电信</option>
            <option>360</option>
          </select>
        </div>
        <button class="ser_btn" id="seradd_btn">立即添加</button>
      </form>
</div>
</body>
</html>
