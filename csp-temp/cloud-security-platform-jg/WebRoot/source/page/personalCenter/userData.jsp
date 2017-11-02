<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>个人中心-个人资料</title>
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/core.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/images/chinatelecom.ico" rel="shortcut icon" />
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9682007&sort=0 ></SCRIPT>
<script type="text/javascript"  src="${ctx}/source/scripts/common/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/jquery.js"></script>
<script type="text/javascript"  src="${ctx}/source/scripts/common/jquery.form.js"></script>
<script src="${ctx}/source/scripts/common/index.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/personal.js"></script>
</head>

<body>
	<div class="safeBox">
		
		<div class="safe01 detalis-head">
			<!--头部-->
		<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html"><img src="${ctx}/source/images/anquanbang_white_logo.png" alt="" style="position:relative; top:4px;"/></a>
                        <strong style="font-size:20px; color:#fff; padding-left:10px;position:relative; top:-10px; font-weight:normal;">个人中心</strong>
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
		<div class="core clearfix" style=" margin-bottom:306px;">
        	<div class="coreLeft fl">
            	<a href="${ctx}/userCenterUI.html"><h3><i></i>个人中心</h3></a>
                <dl>
                	<dt>交易管理</dt>
                    <dd><a href="${ctx}/orderTrackInit.html" >我的订单</a></dd>
                    <!-- <dd><a href="#">我的优惠劵</a></dd> -->
                    <dd><a href="${ctx}/balanceUI.html">安全币</a></dd>
                    <dt>个人信息管理</dt>
                    <dd><a class="active" href="${ctx}/userDataUI.html">个人资料</a></dd>
                    <dd style="border-bottom:none;"><a  href="${ctx}/userAssetsUI.html">我的资产</a></dd>
                </dl>
            </div>
        	<div class="coreRight fl">
            	<div class="dataBox">
                	<div class="data">
                    	<h2>个人资料</h2>
                    </div>
                    <form>
                    	<ul class="dataList" style=" margin-bottom:80px;">
                        	<li class="clearfix">
                            	<label class="fl">用&nbsp; 户&nbsp; 名</label>
								<div class="fl dataListR">
                                	<input type="text" class="text" name="name" disabled="true" value="${user.name}" id="regist_name"/>
                                </div>
                             </li>
                             <li class="clearfix">
                            	<label class="fl">密&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;码</label>
								<div class="fl dataListR">
                                	<input type="password" style="color:#929292" class="text" value="******" disabled="true" id="regist_pwd"/>
                                	<a href="${ctx }/modifyPassUI.html">修改</a>
                                </div>
                             </li>
                             
                           <li class="clearfix">
                            	<label class="fl">手&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;机</label>
								<div class="fl dataListR">
									<input type="hidden" id="originalMobile"  value="${user.mobile}"/>
                                	<!-- <input type="text" class="text" name="mobile" value="${user.mobile}" disabled="true" id="regist_phone" onblur="checkMobile()"/>
                                	 -->
                                	<input type="text" class="text" name="mobile" value="${user.mobile}" disabled="true" id="regist_phone"/>
                                	<a href="${ctx }/updateMobile.html">修改</a>
                                	<span id="regist_mobile_msg" style="color:red;"></span>
                                </div>
                             </li>
                             <li class="clearfix">
                            	<label class="fl">邮&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;箱</label>
								<div class="fl dataListR">
                                	<input type="text" class="text" name="email" value="${user.email}" id="regist_email"/>
                                	<span id="regist_email_msg" style="color:red;"></span>
                                </div>
                             </li>
                             <li class="clearfix">
                            	<label class="fl">所在行业</label>
								<div class="fl dataListR">
								<input type="hidden" id="hid_industry" value="${user.industry}"/>
                                <select class="scelt" id="industry" name="industry">
                                    <option selected="selected" value="-1">请选择行业</option>
				                	<option value="农、林、牧、渔业" > 农、林、牧、渔业</option>
				         			<option value="采矿业" > 采矿业</option>
				         			<option value="电力、热力、燃气及水生产和供应业" >电力、热力、燃气及水生产和供应业</option>
				         			<option value="建筑业" >建筑业</option>
				         			<option value="批发和零售业" >批发和零售业</option>
				         			<option value="交通运输、仓储和邮政业" >交通运输、仓储和邮政业</option>
				         			<option value="住宿和餐饮业" >住宿和餐饮业</option>
				         			<option value="信息传输、软件和信息技术服务业" >信息传输、软件和信息技术服务业</option>
				         			<option value="金融业" >金融业</option>
				         			<option value="房地产业" >房地产业</option>
				         			<option value="租赁和商务服务业" >租赁和商务服务业</option>
				         			<option value="科学研究和技术服务业" >科学研究和技术服务业</option> 
                                 </select>
                                	
                                </div>
                             </li><li class="clearfix">
                            	<label class="fl">合&nbsp; 作&nbsp; 方</label>
								<div class="fl dataListR">
									<input type="hidden" id="hid_partner" value="${user.partner}"/>
                                	<select class="scelt" id="partner" name="partner">
                                    	<option selected="selected" value="-1">请选择合作方</option>
					                	<c:forEach items="${partnerList}" var="partner"  varStatus="status">
							            	<option value="${partner.partnerName}">${partner.partnerName}</option>
						            	</c:forEach>
                                    </select>
                                </div>
                             </li>
                             <li class="clearfix">
                            	<label class="fl">职&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;业</label>
								<div class="fl dataListR">
									<input type="hidden" id="hid_job" value="${user.job}"/>
                                	<select class="scelt" id="job" name="job">
                                    	<option selected="selected" value="-1">请选择职业</option>
					                	<option value="党的机关、国家机关、群众团体和社会组织、企事业单位负责人" >党的机关、国家机关、群众团体和社会组织、企事业单位负责人</option>
					         			<option value="专业技术人员" >专业技术人员</option>
					         			<option value="办事人员和有关人员" >办事人员和有关人员</option>
					         			<option value="社会生产服务和生活服务人员" >社会生产服务和生活服务人员</option>
					         			<option value="农、林、牧、渔业生产及辅助人员" >农、林、牧、渔业生产及辅助人员</option>
					         			<option value="生产制造及有关人员" >生产制造及有关人员</option>
					         			<option value="军人" >军人</option>
					         			<option value="不便分类的其他从业人员" >不便分类的其他从业人员</option>	
                                    </select>
                                </div>
                             </li>
                             <li class="clearfix">
                            	<label class="fl">公司名称</label>
								<div class="fl dataListR">
                                	<input type="text" class="text" name="company" value="${user.company}" id="company">
                                	<span id="regist_company_msg" style="color:red;"></span>
                                </div>
                             </li>
                             <li class="clearfix">
                            	<label class="fl">API KEY</label>
								<div class="fl dataListR">
                                	<input type="text" class="text" name="apikey" value="${user.apikey}" id="apikey" disabled="true">
                                    <b>(该apikey可以调用平台所有即用安全能力API服务)</b>
                                </div>
                             </li>
                             <li class="clearfix">
                            	<label class="fl">推送URL</label>
								<div class="fl dataListR">
                                	<input type="text" class="text" name="urlAddr" value="${user.urlAddr}" id="urlAddr">
                                    <b>(用于使用安全能力API服务时，数据返回的推送地址)</b>
                                </div>
                             </li>
                        	<li class="clearfix" style="margin-top:40px;">
                            	<label class="fl"></label>
								<div class="fl dataListR">
                                	
                                	<input type="button" value="保&nbsp;&nbsp;存" class="submit" style="margin-left:80px;" onclick="checkUserData()">
                                    
                                </div>
                             </li>
                        </ul>
                    
                    </form>
                
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
                        	<dd>购物指南</dd>
                            <dd>在线帮助</dd>
                            <dd>常见问题</dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关于安全帮</h2>
                        <dl>
                            <dd><a href="${ctx}/knowUs.html">了解安全帮</a></dd>
                            <dd><a href="${ctx}/joinUs.html">加入安全帮</a></dd>
                            <dd>联系我们</dd>
                       </dl>
                    </li>
                    <li>
                    	<h2>关注我们</h2>
                        <dl>
                        	<dd>QQ交流群<br>470899318</dd>
                            <dd class="weixin"><a href="#">官方微信</a></dd>
                       </dl>
                    </li>
                     <li>
                    	<h2>特色服务</h2>
                        <dl>
                        	<dd>优惠劵通道</dd>
                            <dd>专家服务通道</dd>
                       </dl>
                    </li>
					
				</ol>
				
			</div>
		</div>
		<!-- 底部 start -->
		<c:import url="/foot.jsp"></c:import>
		<!-- 底部 end -->
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
           	<img src="${ctx}/source/images/portal/weixin.jpg" alt="">
           </div> 
    </div>

</div>
	
<div class="shade"></div>
</body>


</html>
