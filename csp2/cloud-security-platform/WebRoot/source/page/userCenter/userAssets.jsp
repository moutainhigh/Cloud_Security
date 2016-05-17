<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>个人中心-我的资产</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="${ctx}/source/scripts/common/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript"  src="${ctx}/source/scripts/common/jquery.form.js"></script>
<script src="${ctx}/source/scripts/common/index.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/regist/asset.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/zezao.js"></script>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/>
                        <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px; font-weight:normal;">个人中心</strong>
					</div>
					<div class="safem fl">
						<span class="fl"><a href="${ctx}/index.html">首页</a></span>
						
						<!-- 商品分类 start -->
						<c:import url="/category.html"></c:import>
						<!-- 商品分类 end -->
						
						<span class="fl"><a href="${ctx}/knowUs.html">关于我们</a></span>
						<span class="fl shopping" style="margin-right:0">
							<a href="${ctx}/showShopCar.html"><i></i>购物车</a>
						</span>
					</div>
						<div class="safer fr" style="margin-left:0px;">
						<!-- 如果已经登录则显示用户名，否则需要登录 -->
				         <c:if test="${sessionScope.globle_user!=null }">
				         <div class="login clearfix">
					        <a href="${ctx}/userCenterUI.html"  class="fl loginname">${sessionScope.globle_user.name }</a>
					        <em class="fl">|</em>
					        <a href="${ctx}/exit.html" class="fl" >退出</a>
					      </div>
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
		<div class="core clearfix">
        	<div class="coreLeft fl">
            	<a href="${ctx}/userCenterUI.html"><h3><i></i>个人中心</h3></a>
                <dl>
                	<dt>交易管理</dt>
                    <dd><a href="${ctx}/orderTrackInit.html" >我的订单</a></dd>
                    <!-- <dd><a href="#">我的优惠劵</a></dd> -->
                    <dt>个人信息管理</dt>
                    <dd><a href="${ctx}/userDataUI.html">个人资料</a></dd>
                    <dd style="border-bottom:none;"><a class="active" href="${ctx }/userAssetsUI.html">我的资产</a></dd>
                </dl>
            </div>
        	<div class="coreRight assetsece coupon fl" style="margin-bottom:400px;">
            	<div class="assetstitle clearfix">
            	<form action="${ctx}/searchAssetCombine.html" method="post" id="searchAssetForm">
                	<span class="fl addass assetstitlel" style="cursor: pointer;"><em></em>新增资产</span>
                	<div class="fr assetstitlr">
                    	<input type="text" class="text promptext" name="name" id="searchAssetName" value="输入资产名称或地址" onkeydown="if(event.keyCode==13){return false;}">
                        <input type="button" id="searchBtn" value="搜索" class="btn" onclick="searchAssetCombine()">
                    </div>
                    </form>
                </div>
            	<div class="coupontab">
                	<ol class="navlist centlist assets clearfix">
                    	<li class="active">全部</li>
                        <li>已验证</li>
                        <li>未验证</li>
                    </ol>
                    <div class="tabBox" id="assetsTable">
                    	
                       <ul class="not-used" style="display:block">
                    		<li class="head">
                            	<span style="padding-left:120px;">资产名称</span>
                                <span style="padding-left:228px;">资产地址</span>
                                <span style="padding-left:178px;">资产状态</span>
                                <span style="padding-left:82px;">操作</span>
                            </li>
                     <c:forEach items="${list}" var="asset"> 
                     <li class="trlist">
                         <table>
                             <tbody>
                                 <tr height="100" valign="middle">
                                 <td><span style=" margin-left:80px; width:144px;">${asset.name}</span></td>
                                 <td> <span style="padding-left:80px; width:260px;">${asset.addr}</span></td>
                                 <td>
                                 <c:if test="${asset.status==0}">
				            		<span style="padding-left:84px;width:60px;">
				            		<b>未验证</b>
				            		<a href="#" class="zican_top" style="color:#2499fb;" id="${asset.id}" name="${asset.name}" addr="${asset.addr}">立即验证</a>
				            		</span>
				             	 </c:if>
				             	 <c:if test="${asset.status==1}">
				            		<span style="padding-left:84px;width:60px;">
                                                	<b>已验证</b>
                                                    
                                     </span>
				                 </c:if>
                                 </td>
                                 <td> 
                                     <span style="padding-left:80px; width:32px;">
                                         <a href="#" class="zc_edit" style="color:#2499fb;" id="${asset.id}" name="${asset.name}" addr="${asset.addr}" districtId="${asset.districtId}" city="${asset.city}" purpose="${asset.purpose}">修改</a>
                                         <a href="#" style="color:#2499fb;" onclick="deleteAsset('${asset.id}')">删除</a>
                                     </span>
                                 </td>
                                 
               					 </tr>
                             </tbody>
                         </table>  
                     </li> 
                     </c:forEach>                 
                    	</ul>
                        <ul class="not-used">
                    		<li class="head">
                            	<span style="padding-left:120px;">资产名称</span>
                                <span style="padding-left:228px;">资产地址</span>
                                <span style="padding-left:178px;">资产状态</span>
                                <span style="padding-left:82px;">操作</span>
                            </li>
 						<c:forEach items="${list}" var="asset"> 
 						<c:if test="${asset.status==1}">
                            <li class="trlist">
                         <table>
                             <tbody>
                                 <tr height="100" valign="middle">
                                 <td><span style=" margin-left:80px; width:144px;">${asset.name}</span></td>
                                 <td> <span style="padding-left:80px; width:260px;">${asset.addr}</span></td>
                                 <td>
				            		<span style="padding-left:84px;width:60px;">
                                                	<b>已验证</b>
                                                    
                                     </span>
                                 </td>
                                 <td> 
                                     <span style="padding-left:80px; width:32px;">
                                         <a href="#" class="zc_edit" style="color:#2499fb;" id="${asset.id}" name="${asset.name}" addr="${asset.addr}" districtId="${asset.districtId}" city="${asset.city}" purpose="${asset.purpose}">修改</a>
                                         <a href="#" style="color:#2499fb;" onclick="deleteAsset('${asset.id}')">删除</a>
                                     </span>
                                 </td>
                                 
               					 </tr>
                             </tbody>
                         </table>  
                     </li>
                      </c:if> 
                     </c:forEach> 
                        
                    
                    	</ul>
                        <ul class="not-used">
                    		<li class="head">
                            	<span style="padding-left:120px;">资产名称</span>
                                <span style="padding-left:228px;">资产地址</span>
                                <span style="padding-left:178px;">资产状态</span>
                                <span style="padding-left:82px;">操作</span>
                            </li>
                           <c:forEach items="${list}" var="asset"> 
                           <c:if test="${asset.status==0}">
                           <li class="trlist">
                         <table>
                             <tbody>
                                 <tr height="100" valign="middle">
                                 <td><span style=" margin-left:80px; width:144px;">${asset.name}</span></td>
                                 <td> <span style="padding-left:80px; width:260px;">${asset.addr}</span></td>
                                 <td>

				            		<span style="padding-left:84px;width:60px;">
				            		<b>未验证</b>
				            		<a href="#" class="zican_top" style="color:#2499fb;" id="${asset.id}" name="${asset.name}" addr="${asset.addr}">立即验证</a>
				            		</span>
				             	
				             	 
                                 </td>
                                 <td> 
                                     <span style="padding-left:80px; width:32px;">
                                         <a href="#" class="zc_edit" style="color:#2499fb;" id="${asset.id}" name="${asset.name}" addr="${asset.addr}" districtId="${asset.districtId}" city="${asset.city}" purpose="${asset.purpose}">修改</a>
                                         <a href="#" style="color:#2499fb;" onclick="deleteAsset('${asset.id}')">删除</a>
                                     </span>
                                 </td>
                                 
               					 </tr>
                             </tbody>
                         </table>  
                     </li> 
                      </c:if>
                     </c:forEach> 
                    
                    	</ul> 
                    
                    </div>
                </div>
            </div>
        </div>
        
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<a href="#">
                    	<img src="${ctx}/source/images/portal/new-footer-logo.png" alt="" />
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
                        	<dd><a href="#">了解安全帮</a></dd>
                            <dd><a href="#">加入安全帮</a></dd>
                            <dd><a href="#">联系我们</a></dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd><a href="#">QQ交流群<br>470899318</a></dd>
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
			<p>Label@1234567890北京电信研究院<br>
				www.anquanbang.net
			</p>
		</div>
	</div>
 
    <style>
    	.assetsaAdd{
			    display: none;
				padding: 20px;
				position: fixed;
				top: 50%;
				left: 50%;
				z-index: 99;
				text-align: center;
				background: #fff;
				border-radius: 10px;
				width: 670px;
				height: 400px;
				margin-left:-355px;
				margin-top:-220px;
					
		}
		
		
    
    </style>
    <!---新增增产-->
<div class="assetsaAdd hide popBoxhide" id="revise">
	<i class="close closed"></i>
	 <form id="saveAsset" action="${ctx}/addAsset.html" method="post">
    <div class="title">
    	<h2>新增资产</h2>
        <div class="hr" style=" margin:10px 0px 10px 0px;"></div>
    </div>
	<div class="popBox">
    	 
         <div class="text_bottm">
  
      <table style="margin-top:26px;width:630px">
        <tbody><tr>
          <td style="width:25%;">资产名称</td>
          <td style="width:37%;"><input class="boz_inout_1" type="text" name="name" id="assetName"></td>
          <td style="width:30%; text-align:left; color:#e32929;font-size:12px"><div class="addMsg" id="assetName_msg"></div></td>
        </tr>
        <tr>
       	  <td>资产地址类型</td>
          <td style="text-align:left">
		        &nbsp;&nbsp;<input type="radio" name="addrType" checked="checked" id="assetAddrType1" value="http">
		        &nbsp;http&nbsp;&nbsp;
		        <input type="radio" name="addrType" id="assetAddrType2" value="https">
		        &nbsp;https
		  </td>
        </tr>
        
        <tr>
          <td>资产地址</td>
          <td><input class="boz_inout_1" type="text" name="addr" id="assetAddr"></td>
          <td style="color:#e32929;text-align:left"><div class="addMsg" id="assetAddr_msg"></div></td>
        </tr>
        <tr>
	        <td>示例</td>
	        <td style="color:#999;text-align: left; line-height: 18px; padding-top: 4px; padding-left: 10px;">http://xxx.xxx.xxx.xxx<br>https://xxx.xxx.xxx.xxx:1234<br>http://xxx.xxx.xxx.xxx:8080/home
            </td>
        </tr> 
        <tr>
          <td>物理位置</td>
          <td>
  				<select class="user_secta_count spiclesel" id="districtId" name="districtId" onchange="getCitys(this.value)" style="width:119px;height: 35px;">
  					<option selected="selected" value="-1">请选择省份</option>
  				</select> 
    			<select class="user_secta_count spiclesel" id="city" name="city" disabled="disabled" style="width:119px;height: 35px;"><option value="-1">请选择城市</option></select>
		  </td>
		  <td style="color:#e32929;text-align:left;"><div class="addMsg" id="location_msg"></div></td>
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
		  <td style="color:#e32929;text-align:left"><div class="addMsg" id="assetUsage_msg"></div></td>
        </tr>
      </tbody></table>
                  
    </div>
        <div style="margin-top:26px;">
        	<a href="javascript:void(0)"><img src="${ctx }/source/images/portal/user_submit_3.jpg" alt="" onclick="saveAsset()"> </a>
        </div> 
         
         
    </div>
</form>
</div>

<!-- 修改 -->
<div class="assetsaAdd hide popBoxhide" id="updateAssest">
	<i class="close closed"></i>
    <form id="editAsset" method="post">
    <input type="hidden" name="id" id="editAssetid"/>
    <input type="hidden" name="hiddenEditName" id="hiddenEditName"/>
    <input type="hidden" name="hiddenEditAddr" id="hiddenEditAddr"/>
    <div class="title">
    	<h2>修改资产</h2>
        <div class="hr" style=" margin:10px 0px 10px 0px;"></div>
    </div>
    <div class="text_bottm">
      <table style="margin-top:56px;width:630px">
        <tr>
          <td style="width:25%;">资产名称</td>
          <td style="width:37%;"><input class="boz_inout_1" type="text" name="name" id="editAssetName"/></td>
          <td style="width:30%; text-align:left; color:#e32929;font-size:12px"><div class="editMsg" id="editAssetName_msg"></div></td>
        </tr>
        <tr>
       	  <td>资产地址类型</td>
          <td style="text-align:left">
		        &nbsp;&nbsp;<input type="radio" name="addrType"  value="http" checked="checked"/>
		        &nbsp;http
		        &nbsp;&nbsp;<input type="radio" name="addrType"  value="https"/>
		        &nbsp;https
		  </td>
        </tr>
        <tr>
          <td>资产地址</td>
          <td><input class="boz_inout_1" type="text" name="addr" id="editAssetAddr"/></td>
          <td style="color:#e32929;text-align:left"><div class="editMsg" id="editAssetAddr_msg"></div></td>
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
		  <td style="color:#e32929;text-align:left;"><div class="editMsg" id="editLocation_msg"></div></td>
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
		  <td style="color:#e32929;text-align:left"><div class="editMsg" id="editAssetUsage_msg"></div></td>
        </tr>
      </table>
    </div>
    <div style="margin-top:35px;"><a href="javascript:void(0)"><img src="${ctx}/source/images/portal/user_submit_3.jpg" onclick="editAsset()"/></a></div>
  </div>
  </form>
</div>
<div class="assetsaAdd hide popBoxhide" id="verification">
<i class="close closed"></i>
<form action="${ctx}/verificationAsset.html" method="post" id="verificationAssetForm">
<input type="hidden" value="" id="hiddenId" name="id"/>
<input type="hidden" value="" id="hiddenAddr" name="addr"/>
	<div class="text_1">
	  <div class="text_top" id="verificationName"></div>
	  <div class="text_bottm">
	    <div>
	      <div class="txt_yz_left">
	        <input type="radio" name="verification_msg" checked="checked" id="codeVerification" value="codeVerification" onclick="showAndHiddenRadio()"/>
	        &nbsp;&nbsp;代码验证(推荐)</div>
	      <div class="txt_yz_left">
	        <input type="radio" name="verification_msg" id="fileVerification" value="fileVerification" onclick="showAndHiddenRadio()" />
	        &nbsp;&nbsp;上传文件验证</div>
	      <div class="txt_yz_left">
	        <input type="radio" name="verification_msg" id="manualVerification" value="manualVerification" onclick="showAndHiddenRadio()" />
	        &nbsp;&nbsp;人工验证</div>
	    </div>
	    <div class="txt_1" id="codeVerificationID" style="display:block;">
	      <p></p>
	      <!-- <div class="txt_2" id="code">
	      <pre><a href="http://www.anquanbang.net/userAssetsUI.html">网站安全帮</a></pre>
	      </div> -->
	      <input type="hidden" id="code" value="网站安全帮">
	      <textarea rows="4" cols="60" ><a href="http://www.anquanbang.net/userAssetsUI.html">网站安全帮</a>
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
	    
	    <div class="txt_1" id="manualVerificationID" style="display:none;">
		    <div style="height:30px;" class="txt_2" >
	        	<p>请联系在线客服，并提交相关证明</p>
	        </div>
	    </div>
	  </div>
	  <div style="margin-top:18px;" id="submitId"><a href="javascript:void(0)"><img src="${ctx }/source/images/portal/user_submit_3.jpg" onclick="verificationAsset()"/></a></div>
	</div>
</form>	
</div>
<div class="shade"></div>
<script><!--
	$(function(){
		/*提示文字效果*/
		$('.promptext').focus(function(){
			if($(this).val()=='输入资产名称或地址'){
				$(this).val('');
				$(this).css('color','#343434');
			}else{
				$(this).css('color','#343434');
			}
		});
		$('.promptext').blur(function(){
			if($.trim($(this).val())==''){
				$(this).val('输入资产名称或地址');
				$(this).css('color','#929292');	
			}
				
		})
		

	})
	
	
</script>
</body>


</html>

