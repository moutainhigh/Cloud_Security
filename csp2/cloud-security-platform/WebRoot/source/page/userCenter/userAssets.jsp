<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>用户中心-我的资产</title>
<link href="${ctx}/source/css/mian.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/user.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/css/head_bottom.css" type="text/css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#status").val("${status}");
	$("#searchAssetName").val("${name}");
});
</script>
<script type="text/javascript" src="${ctx}/source/scripts/common/user.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/zezao.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/asset.js"></script>
<link href="${ctx}/source/css/blue.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<!-- add by 2016-02 -->
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<!-- end -->
<script type="text/javascript" src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/order/order.js"></script>

</head>

<body>

<!--头部-->
<div class="safe01 detalis-head">
	<!--头部-->
	<div class="head" style="width:100%">
		<div class="headBox">
			<div class="safeL fl" style="width:260px; margin-right:13%">
				<a href="${ctx}/index.html"><img src="${ctx}/source/images/portal/newlogo-footer.png" alt="" style="position:relative; top:4px;"/></a>
			</div>
			<div class="safem fl">
				<span class="fl"><a href="${ctx}/index.html">首页</a></span>
				<div class="Divlist listJs fl">
					<a href="#" class="this">我的安全帮<i></i></a>
							<ul class="list listl">
								<li><a href="${ctx}/orderTrackInit.html">我的订单</a></li>
								<li><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
								<li style="border: none;"><a href="${ctx}/userDataUI.html">个人信息</a></li>
							</ul>
				</div>
				<span class="fl ask">
					<a href="#" class="hbule">手机APP</a>
					<b style="display:none"><img src="${ctx}/source/images/portal/apk.png" alt=""></b>
				</span>
				<span class="fl"><a href="${ctx}/aider.html">帮助</a></span>
				
			</div>
			<div class="safer fr">
				<!-- 如果已经登录则显示用户名，否则需要登录 -->
		         <c:if test="${sessionScope.globle_user!=null }">
			        <a href="${ctx}/userDataUI.html">${sessionScope.globle_user.name }</a>
			        <em>|</em>
			        <a href="${ctx}/exit.html">退出</a>
		         </c:if>
		         <c:if test="${sessionScope.globle_user==null }">
		            <a href="${ctx}/loginUI.html">登录</a>
					<em>|</em>
					<a href="${ctx}/registUI.html">注册</a>
		         </c:if>
			</div>
		</div>
	</div>
	

</div>
<!-- 头部代码结束-->
<div class="user_center clear">
  <div class="user_left">
    <ul class="user_1">
      <li style="font-size:16px; font-weight:500; line-height:28px; text-align:center;"><a  style="color:#4593fd; " href="${ctx}/userCenterUI.html">用户中心</a></li>
      <li><a href="${ctx}/userDataUI.html">基本资料</a></li>
      <li><a href="${ctx}/userBillUI.html">我的账单</a></li>
      <li class="active"><a href="${ctx}/userAssetsUI.html">我的资产</a></li>
      <h2>订购中心</h2>
      <!-- <li><a onclick="tasknum_verification()" href="javascript:void(0)">自助下单</a></li>-->
      <li><a href="${ctx}/orderTrackInit.html">订单跟踪</a></li>
    </ul>
  </div>
  
  <!-- <jsp:include page="left.jsp"/> -->
  
  <!--我的资产-->
  <div class="user_right" >
    <div class="user_top">
      <div class="user_ctive" style="left:200px;"><a href="###"><img src="${ctx}/source/images/user_ico_3.jpg" /></a></div>
  <form action="${ctx}/searchAssetCombine.html" method="post" id="searchAssetForm">
      <div class="user_sec_cont" style=" left:200px; ">
      </div>
      <div class="user_sec_cont" style="left:350px;">
        <select id="status" name="status" class="user_secta spiclesel">
       		<option select="selected" value="">请选择状态</option>
       		<option value="0" >未验证</option>
       		<option value="1" >已验证</option>
  		</select>
      </div>
      <div class="user_sec_txt">
        <input type="text" name="name" id="searchAssetName" value="请输入关键字"></input>
      </div>
      <div class="user_soucuo" style="left:754px;"><img src="${ctx}/source/images/user_submit_2.jpg" onclick="searchAssetCombine()"/></div>
  </form>
    </div>
    
    <div class="zhangd_table">
      <table>
        <tbody>
          <tr style="background:#e0e0e0; height:30px; line-height:30px;">
            <td style="width:20%;">资产名称</td>
            <td  style="width:20%;">资产地址</td>
            <td  style="width:20%;">资产状态</td>
            <td  style="width:20%;">操作</td>
          </tr>
          <c:forEach items="${list}" var="asset"> 
          <tr>
            <td>${asset.name}</td>
            <td>${asset.addr}</td>
            <c:if test="${asset.status==0}">
            <td><div class="zican_wei">未验证</div>
              <div class="zican_top" id="${asset.id}" name="${asset.name}" addr="${asset.addr}">立即验证</div></td>
              </c:if>
              <c:if test="${asset.status==1}">
            <td><div class="zican_wei">已验证</div></td>
              </c:if>
            <td><div class="zc_edit" id="${asset.id}" name="${asset.name}" addr="${asset.addr}" districtId="${asset.districtId}" city="${asset.city}" purpose="${asset.purpose}">修改</div>
              <div class="zican_bottom"><a href="javascript:void(0)" onclick="deleteAsset('${asset.id}')" >删除</a></div></td>
          </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
<!-- 尾部代码开始-->
<div class="safeBox">
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="${ctx}/index.html">
	               <img src="${ctx}/source/images/portal/logo footer.png" alt="">
                   <i class="" style="height:35px; color:#b3b4b5; width:1px; display:inline-block;">|</i>
	               <img src="${ctx}/source/images/portal/newlogo-footer.png" alt="">
                   </a>
				</div>
				<ol class="footr clearfix fr">
					<li>
                    	<h2>帮助中心</h2>
                        <dl>
                        	<dd><a href="#">购物指南</a></dd>
                            <dd><a href="#">在线帮助</a></dd>
                            <dd><a href="#">常见问题</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关于安全帮</h2>
                        <dl>
                        	<dd><a href="${ctx}/knowUs.html">了解安全帮</a></dd>
                            <dd><a href="${ctx}/joinUs.html">加入安全帮</a></dd>
                            <dd><a href="#">联系我们</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd><a href="#">QQ交流群</br>470899318</a></dd>
                            <dd class="weixin"><a href="#">官方微信</a></dd>
                       </dl>
                    </li>
                     <li>
                    	<h2>特色服务</h2>
                        <dl>
                        	<dd><a href="#">优惠劵通道</a></dd>
                            <dd><a href="#">专家服务通道</a></dd>
                       </dl>
                    </li>
					
				</ol>
				
			</div>
		</div>
		<div class="foot">
			<p>版权所有Copyright © 2015 中国电信股份有限公司北京研究院京ICP备12019458号-10</p>
		</div>
		<!---执行效果-->
<div class="weixinshow popBoxhide" id="weixin">
	<i class="close chide"></i>
    <div class="Pophead">
    	<h1 class="heaf">安全帮微信二维码</h1>
    </div>
	<div class="popBox">
    	 <p>打开微信，点击右上角的“+”，选择“扫一扫”功能，<br>
对准下方二维码即可。
		</p>
           <div class="weinImg" style="text-align:center;">
           	<img src="${ctx}/source/images/portal/weixin.png" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</div>
<!-- 尾部代码结束 -->

<!--新增资产遮罩层样式-->
<div id="box_mark"></div>
<div id="box_logoIn1">
  <div id="close1"></div>
  <div class="text_1">
    <form id="saveAsset" action="${ctx}/addAsset.html" method="post">
    <div class="text_top">新增资产</div>
    <div class="text_bottm">
  
      <table style="margin-top:26px;width:630px">
        <tr>
          <td style="width:25%;">资产名称</td>
          <td style="width:37%;"><input class="boz_inout_1" type="text" name="name" id="assetName"/></td>
          <td style="width:30%; text-align:left; color:#e32929;font-size:12px"><div id="assetName_msg"></div></td>
        </tr>
        <tr>
       	  <td>资产地址类型</td>
          <td style="text-align:left">
		        &nbsp;&nbsp;<input type="radio" name="addrType" checked="checked" id="assetAddrType1" value="http"/>
		        &nbsp;http&nbsp;&nbsp;
		        <input type="radio" name="addrType" id="assetAddrType2" value="https"/>
		        &nbsp;https
		  </td>
        </tr>
        
        <tr>
          <td>资产地址</td>
          <td><input class="boz_inout_1" type="text" name="addr" id="assetAddr"/></td>
          <td style="color:#e32929;text-align:left"><div id="assetAddr_msg"></div></td>
        </tr>
        <tr>
	        <td>示例</td>
	        <td style="color:#999;text-align: left; line-height: 18px; padding-top: 4px; padding-left: 10px;">http://xxx.xxx.xxx.xxx<br>https://xxx.xxx.xxx.xxx:1234<br>http://xxx.xxx.xxx.xxx:8080/home
            </td>
        </tr> 
        <tr>
          <td>物理位置</td>
          <td>
  				<select class="user_secta_count spiclesel" id="districtId" name="districtId"  onchange="getCitys(this.value)" style="width:119px;height: 35px;">
  					<option selected="selected" value="-1">请选择省份</option>
  				</select> 
    			<select class="user_secta_count spiclesel" id="city" name="city" disabled="disabled" style="width:119px;height: 35px;">
    			<option selected="selected" value="-1">请选择城市</option>
    			</select>
		  </td>
		  <td style="color:#e32929;text-align:left;"><div id="location_msg"></div></td>
        </tr>
        <tr>
        <td>资产用途</td>
         <td>
   			<select class="user_secta_count spiclesel" id="purpose" name="purpose" style="width:242px;height: 35px;">
   				<option selected="selected" value="-1">请选择资产用途</option>
   				<option value="公共服务">公共服务</option>
   				<option value="信息发布">信息发布</option>
   				<option value="其他">其他</option>
   			</select>
	   </td>
		  <td style="color:#e32929;text-align:left"><div id="assetUsage_msg"></div></td>
        </tr>
      </table>
                  
    </div>
    <div style="margin-top:26px;"><a href="javascript:void(0)"><img src="${ctx}/source/images/user_submit_3.jpg" onclick="saveAsset()"/></a></div>
  </div>
  </form>
</div>

<div id="box_logoIn_edit">
  <div id="close_edit"></div>  <div class="text_1">
    <form id="editAsset" action="${ctx}/editAsset.html" method="post">
    <div class="text_top">修改资产</div>
    <div class="text_bottm">
  	<input type="hidden" name="id" id="hiddenEditAssetid"/>
  	<input type="hidden" name="hiddenEditAssetName" id="hiddenEditAssetName"/>
  	<input type="hidden" name="hiddenEditAssetAddr" id="hiddenEditAssetAddr"/>
  	<input type="hidden" name="hiddenEditDistrictId" id="hiddenEditDistrictId"/>
  	<input type="hidden" name="hiddenEditCity" id="hiddenEditCity"/>
  	<input type="hidden" name="hiddenEditPurpose" id="hiddenEditPurpose"/>
      <table style="margin-top:56px;width:630px">
        <tr>
          <td style="width:25%;">资产名称</td>
          <td style="width:37%;"><input class="boz_inout_1" type="text" name="name" id="editAssetName"/></td>
          <td style="width:30%; text-align:left; color:#e32929;font-size:12px"><div id="editAssetName_msg"></div></td>
        </tr>
        <tr>
       	  <td>资产地址类型</td>
          <td style="text-align:left">
		        &nbsp;&nbsp;<input type="radio" name="addrType" id="editAddrType" value="http" checked="checked"/>
		        &nbsp;http
		        &nbsp;&nbsp;<input type="radio" name="addrType" id="editAddrType" value="https"/>
		        &nbsp;https
		  </td>
        </tr>
        <tr>
          <td>资产地址</td>
          <td><input class="boz_inout_1" type="text" name="addr" id="editAssetAddr"/></td>
          <td style="color:#e32929;text-align:left"><div id="editAssetAddr_msg"></div></td>
        </tr>
        <tr>
          <td>物理位置</td>
          <td>
  				<select class="user_secta_count spiclesel" id="editDistrictId" name="districtId"  onchange="getEditCitys(this.value)" style="width:119px;height: 35px;">
  					<option selected="selected" value="-1">请选择省份</option>
  				</select> 
    			<select class="user_secta_count spiclesel" id="editCity" name="city" disabled="disabled" style="width:119px;height: 35px;">
    			<option selected="selected" value="-1">请选择城市</option>
    			</select>
		  </td>
		  <td style="color:#e32929;text-align:left;"><div id="editLocation_msg"></div></td>
        </tr>
        <tr>
        <td>资产用途</td>
         <td>
   			<select class="user_secta_count spiclesel" id="editPurpose" name="purpose" style="width:242px;height: 35px;">
   				<option selected="selected" value="-1">请选择资产用途</option>
   				<option value="公共服务">公共服务</option>
   				<option value="信息发布">信息发布</option>
   				<option value="其他">其他</option>
   			</select>
	   </td>
		  <td style="color:#e32929;text-align:left"><div id="editAssetUsage_msg"></div></td>
        </tr>
      </table>
    </div>
    <div style="margin-top:35px;"><a href="javascript:void(0)"><img src="${ctx}/source/images/user_submit_3.jpg" onclick="editAsset()"/></a></div>
  </div>
  </form>
</div>

<div id="box_logoIn2">
<div  id="close2"></div>
<form action="${ctx}/verificationAsset.html" method="post" id="verificationAssetForm">
<input type="hidden" value="" id="hiddenId" name="id"/>
<input type="hidden" value="" id="hiddenAddr" name="addr"/>
	<div class="text_1">
	  <div class="text_top" id="verificationName"></div>
	  <div class="text_bottm">
	    <div class="txt_yz clear">
	      <div class="txt_yz_left">
	        <input type="radio" name="verification_msg" checked="checked" id="codeVerification" value="codeVerification" onclick="showAndHiddenRadio()"/>
	        &nbsp;&nbsp;代码验证(推荐)</div>
	      <div class="txt_yz_right">
	        <input type="radio" name="verification_msg" id="fileVerification" value="fileVerification" onclick="showAndHiddenRadio()" />
	        &nbsp;&nbsp;上传文件验证</div>
	    </div>
	    <div class="txt_1" id="codeVerificationID" style="display:block;">
	      <p></p>
	      <!-- <div class="txt_2" id="code">
	      <pre><a href="http://www.anquanbang.net/userAssetsUI.html">网站安全帮</a></pre>
	      </div> -->
	      <input type="hidden" id="code" value="网站安全帮">
	      <textarea rows="4" cols="70" ><a href="http://www.anquanbang.net/userAssetsUI.html">网站安全帮</a>
		  </textarea>
	      <p>请在您的<span><a href="" id="c" target="_blank">网站首页</a></span>中任何位置加入如上代码。<!-- （<a href="javascript:void(0)" onclick="copyToClipBoard()">复制代码</a>） --><br />
	       </p>
	      <p>如果添加成功，网站首页能看见文字：“网站安全帮”</p>
	      <p>提示：验证通过后可删除</p>
	    </div>
	    
	    
	    <div class="txt_1" id="fileVerificationID" style="display:none;">
		    <div style="height:30px;" class="txt_2" >
	        	<p>请点击下载：<a href="${ctx}/download.html?fileName=key.html">key.html</a></p>
	        </div>
		    <p>1.请在您的资产根目录下添加 <span> key.html </span>作为验证文件</p>
		    <p>2.浏览器访问<span> http://xxx/key.html </span>确认上传成功</p>
		    <p>提示:上传文件要具有可读权限;验证通过后可删除文件</p>
	    </div>
	    
	    
	  </div>
	  <div style="margin-top:18px;"><a href="javascript:void(0)"><img src="${ctx}/source/images/user_submit_4.jpg" onclick="verificationAsset()"/></a></div>
	</div>
</form>
</body>
</html>
