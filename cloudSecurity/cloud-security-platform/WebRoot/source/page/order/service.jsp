<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心-自助下单</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/order.js"></script>
</head>
      <!-- 漏洞扫描服务-->
      <div class="peiz_cont" style="display:block;">
        <div style="position: absolute; top:-10px;"><img src="${ctx}/source/images/user_ico.jpg" /></div>
        <div class="clear">
          <h3>服务对象</h3>
          <div class="peiz_table">
            <table id="addTr">
              <tr style="background:#e0e0e0;">
                <td style="width:30%;">资产名称 </td>
                <td style="width:25%;">资产类型 </td>
                <td style="width:30%;">资产地址 </td>
                <td style="width:15%;"><input type="checkbox" id="checkItems"/></td>
              </tr>
              <c:forEach var="list" items="${serviceAssetList}" varStatus="status">
	              <tr>
	                <input type="hidden" value="${list.id }" id="assetId" name="assetId"/>
	                <td>${list.name }</td>
	                <c:if test="${list.type == 1 }">
	                   <td>URL</td>
	                </c:if>
	                <c:if test="${list.type == 2 }">
                       <td>IP</td>
                    </c:if>
	                <td>${list.addr }</td>
	                <td><input type="checkbox" name="serviceAssetId"/></td>
	              </tr>
              </c:forEach>
                            
            </table>
          </div>
          <div class="peiz_ico"><img src="${ctx}/source/images/peiz_ico.jpg" id="to_right"/></div>
          <div class="peiz_table">
            <table id="addTr1">
              <tr style="background:#e0e0e0;">
                <td style="width:30%;">资产名称 </td>
                <td style="width:25%;">资产类型 </td>
                <td style="width:30%;">资产地址 </td>
                <td style="width:15%;"></td>
              </tr>
            </table>
          </div>
        </div>
        <div class="pinv">
          <h3>扫描频率</h3>
          <div class="pinv_subnav">
            <ul class="pinv_sub_nav">
              <li class="pinv_active">
                <input type="radio" name="scanType" value="1"/>
                &nbsp;&nbsp;每天</li>
              <li>
                <input type="radio" name="scanType" value="2"/>
                &nbsp;&nbsp;每周</li>
              <li>
                <input type="radio" name="scanType" value="3"/>
                &nbsp;&nbsp;每月</li>
            </ul>
            <!-- <div class="pinv_subcenter" style="display:block;"></div>
            <div class="pinv_subcenter">
              <ul>
                <li><a href="###">周一</a></li>
                <li><a href="###">周二</a></li>
                <li><a href="###">周三</a></li>
                <li><a href="###">周四</a></li>
                <li><a href="###">周五</a></li>
                <li><a href="###">周六</a></li>
                <li style="border-right:0px"><a href="###">周日</a></li>
              </ul>
            </div>
            <div class="pinv_subcenter"></div> -->
          </div>
        </div>
      </div>

