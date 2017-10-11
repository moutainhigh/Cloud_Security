<%@ page language="java" import="java.util.*,java.lang.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/source/css/base.css" type="text/css" rel="stylesheet">
<link href="${ctx}/source/css/popBox.css" type="text/css" rel="stylesheet">	
<link href="${ctx}/source/css/portalindex.css" type="text/css" rel="stylesheet">
<script src="${ctx}/source/scripts/common/jquery-1.7.1.min.js"></script>
<script src="${ctx}/source/scripts/common/portalindex.js"></script>
<script src="${ctx}/source/scripts/common/popBox.js"></script>
<script src="${ctx}/source/scripts/common/slidelf.js"></script>
<script src="${ctx}/source/scripts/common/main.js"></script>
<link rel="stylesheet" href="${ctx}/source/css/bootstrap.css"/>
<link rel="stylesheet" href="${ctx}/source/css/bootstrapValidator.css"/>
<link href="${ctx}/source/css/bootstrap-table.css" rel="stylesheet"/> 
<script type="text/javascript" src="${ctx}/source/scripts/common/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/source/scripts/common/bootstrapValidator.js"></script>
<script src="${ctx}/source/scripts/common/bootstrap-table.min.js"></script>
<script src="${ctx}/source/scripts/common/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/source/scripts/common/bootstrap-table-export.min.js"></script>
<script src="${ctx}/source/scripts/common/tableExport.min.js"></script>
<style>
.frameInside{
	width: 100%;
	min-width:1024px;
	height: 100%;
	min-height: 680px;
	font-family: "Microsoft YaHei",verdana, "Hiragino Sans GB";
	background: url("${ctx}/source/images/portal/xbackground.png") no-repeat;
	margin-top: 6px;
}
.body{
	width: 1114px;
	height: 100%;
	margin:0 auto;
	padding:0;
	border:0;
	background-size: cover;
	 z-index: -10;
}
.bannerbox {
	width:73%;
	height:14%;
	min-height:130px;
	margin-left:auto;
	margin-right:auto;
	background: url('../img/banner.png') center center no-repeat;
	position: relative;
}
.contentbox{
	width: 100%;
	height: 77%;
	min-height: 450px;
	margin: 1% auto 0;
	position: relative;
}

.timelinebox{
	height:100%;
	float: left;
	position: relative;
	top:14px;
}


ul,li{
	list-style: none;
}

.timeline_ul{
	max-width:298px;
	min-height: 450px;
	height:100%;
	padding: 0; 
	margin: 0;
	overflow: hidden;
}
.timeline_ul:hover{
	overflow: auto;
}

.timeline_ul li{
	width: 100%;
	min-width:243.750px;
	height:10%;
	margin: 0;
	text-align:left;
	float:left;
	background:url("${ctx}/source/images/portal/point.png") 103px 0 repeat-y;
	display:inline;
	margin-bottom: 2px;
}
.timeline_span_left{
	width:70px;
	height:100%;
	font-size: 12px;
	float: left;
	color:#8f8d8d;
	margin-top:16px;
	word-break:break-all;
}
.timeline_span_right{
	/*width:108.5px;*/
	width:126px;
	height:100%;
	float: left;
	font-size: 18px;
	padding-top:14px;
	padding-left:5px;
	word-break:break-all ;
	overflow:hidden;
	color:#E5F3FF;
}
.timeline_div_icon{
	width:66px;
	height:100%;
	float: left;
	background: url("${ctx}/source/images/portal/xlittleCircle.png") 26px 18px no-repeat;
}

.timeline_divicon_side{
	display: none;
	width:100%;
	height:100%;
	line-height:100%;
	text-align:center;
	padding-top:20px;
	background: url("${ctx}/source/images/portal/xcircle.png") 7px 1px no-repeat;
}

.timeline_div_icon label{
	line-height:14px;
	color:#FCD833;
	font-size:15px;
	cursor: pointer;
	position: relative;
}
.timeline_div_icon label:hover{
	color: #FCD833;
	}
.searchbox{
	margin-right: 7%;
	width:63%;
	height:100%;
	padding-top:37px;
	float: left;
	position: relative;
	margin-left: 100px;
    margin-top: -46px;
}


.search_titlespan{
	color: #ffffff;
	font-size: 25px;
}

.searchbox_row_padding{
	padding-top: 12px;
}
.searchbox_input_col{
	width: 100%;
	padding: 0;
}
.searchbox_row_padding button{
	color: #000000;
}
.searchbox_row_msgbox{
	padding-top: 8px;
	padding-left:1em;
	height: 22px;
}
.searchbox_row_msg_span{
	color: red;
	font-size: 22px;	
}
.searchbox_img{
	margin: 0;
	padding: 0;
	height: 15px;
	margin-top:14px;
	background:url("../img/j.png") 30px no-repeat;
	
}
.searchbox_col{
	text-align:justify;
	padding: 18px;
	background-color: rgba(255,255,255,0.15);
}
.searchbox_col p{
	color: #FFFFFF;
	font-size:13px;
	width: 100%;
}
.searchbox_mz_col{
	text-align:justify;
	color: red;
	font-size:12px;
	padding: 18px 18 5px 18px;
}
.footer{
	left:0;
	/*bottom:0;*/
	padding-top:2%;
	width: 100%;
	height:9%;
	min-height:46px;
	/*margin-top:2%;*/
	border-top:#565555 solid 1px;
	color:#FFFFFF;
	float:left;
	position: absolute;
	word-break:keep-all;
	white-space:nowrap; 
	font-size: 12px;
	background-color: #2e2f30;
}
.company{
	text-align:center;
	width: 70%;
	float: left;
}
.icp{
	text-align: center;
	width: 30%;
	float: left;
	height: 100%;
}
#verifyimg{
	height: 34px;
	cursor: pointer;
}
#verifymsg{
	color: red;
	line-height: 34px;
}
.ah_div{
	color: #FFFFFF;
	text-align: right;
}
.container2 {
display:none;
}
#tb_departments{
background-color:#FFF;
}
thead {
background-color:RGB(237,237,237);
}
table {
text-align: center;
}
th {
text-align: center;
}
.help-block {
color:#CC0033;
}
.form-group label {
color:#f0f0f0;
font-size:20px;
}
.timelinebox {
margin-left: -74px;
}
#target, #xport {
width: 526px;
height: 43px;
}
#form {
margin-left: 31px;
margin-top:28px;
}
.pagination-info {
	color:#fff;
}
.fixed-table-body {
	height:auto;
}
/* X专区下导航栏 */
.top {
color:#fff;
height: 68px;
line-height: 70px;
margin-left: 5px;
}
.link a {
color:#fff;
}
.link i {
margin:0px 8px;
}
label {
font-weight:lighter;
}
.xport {
margin-top:27px;
}
.xbutton {
margin-top:61px;
margin-left:40px;
}
.xbutton .btn {
height:45px;
width:135px;
margin-left:59px;
color:#6E8699;
font-size:20px;
font-weight:lighter;
background-color:#FBFCFF;
}
.xbutton .btn:hover {
color:#fff;
background-color:#C8C8C8;
}
/* 解决导航栏bug */
.safe01 .Divlist .list {
top:50px;
}
a:link, a:visited, a:hover, a:active {
text-decoration:none;
}
#form i {
line-height: 57px;
right: 58px;
}
.li1 {
cursor:pointer;
}
.li2 {
cursor:auto;
}
</style>
<script>

	
</script>
</head>

<body>
	<div class="safeBox">
		<div class="safe01">
			<!--头部-->
			<div class="head">
				<div class="headBox">
					<div class="safeL fl" style="width:260px; margin-right:13%">
						<a href="${ctx}/index.html">
							<img src="${ctx}/source/images/portal/logo.png" alt="" style="position:relative; top:4px;"/>
						</a>
						<a href="${ctx}/Xlist.html"><span style="font-size: 20px;color: #4a4a4a; padding-left:10px;position:relative; top:8px;">X专区</span>
						</a>
                        <!-- <strong style="font-size:20px; color:#fff; padding-left:20px;position:relative; top:-10px; font-weight:normal;">X专区</strong>
						 -->
					</div>
					<div class="safem fl">
						<span class="fl"><a href="${ctx}/index.html" >首页</a></span>
						
						<!-- 商品分类 start -->
						<c:import url="/category.html"></c:import>
						<!-- 商品分类 end -->
						
						<span class="fl"><a href="${ctx}/knowUs.html" class="hbule">关于我们</a></span>
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
		
	   	<div style="width:100%">
	   	    <div class="frameInside">
		   	    <div class="body container-fluid">
			   	    <div class='top'>
			   	        <div class='link'>
			   	            <a href="${ctx}/index.html" style="font-size: 20px;">安全帮</a><i>&gt;</i><a href="${ctx}/Xlist.html">X专区</a><i>&gt;</i><a href="javascript:;">端口扫描</a>
			   	        </div>
			   	    </div> 
		   	    	<div class="contentbox">
			          <div class="timelinebox">
			            <ul class="timeline_ul">
			              <li data-onselect="1" class="li1">
			                  <span class="timeline_span_left"></span>
			                  <div class="timeline_div_icon">
			                       <div class="timeline_divicon_side" data-checkid="0"><label>创建</label></div>
			                  </div>
			                  <label class="timeline_span_right testclass">创建新任务</label>
			              </li>
			              <li class="li2">
			                  <span class="timeline_span_left"></span>
			                  <div class="timeline_div_icon">
			                          <div class="timeline_divicon_side" data-checkid="1"><label>详情</label></div>
			                  </div>
			                  <label class="timeline_span_right">查看详情</label>
			              </li> 
			            </ul>
			          </div>
			          <div class="container searchbox container1">
			             <div class="tab-pane fade in active" id="home"   style="width:90%;align:center;left:10%;margin:0 auto;">
			                 <form role="form" method="post"  id="form" name="form"  >
					             <div class="form-group">
						             <label for="name">扫描目标</label>
						             <input type="text" class="form-control" id="target" name="target" placeholder="例如：127.0.0.1或者baidu.com"/>
					             </div>
					             <div class="form-group xport">
						             <label for="name">扫描端口</label>
						             <input type="text" class="form-control" id="xport"  name="port" placeholder="例如：0-65535 or 21，23，80"/>
					             </div>
					             <div class="form-group xbutton">
					                 <button type="reset" class="btn" onclick="resetForm()">重置</button>
			                         <button type="button" class="btn" onclick="submitForm()" id="btn_sub">提交</button>
					             </div>
				             </form>
			             </div>
			          </div>
			          <div class="container searchbox container2">
			              <div class="row">
			                  <div id="task" style="margin-top:0px;width:100%;margin:0 auto">
			                  <br/>
			                  <table id="tb_departments" align="center" valign="center" class="table"></table>
			          </div>
			        </div>
    
		   	    </div>
	   	    </div>
	    </div>
		<div class="safe04">
			<div class="imgBox clearfix">
				<div class="footL fl">
					<!--修改-->
				   <a href="${ctx}/index.html">
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
<script>
function resetForm(){
$('#form').bootstrapValidator('resetForm', true);
}
function submitForm(){
	//alert();
	$(form).data('bootstrapValidator').validate();
	if($(form).data('bootstrapValidator').isValid()){
		var target=$("#target").val();
		var port=$("#xport").val();
		var scan=$("#scan").val();
		//alert(target);
		$(".li2").find('.timeline_divicon_side').slideDown(400);
        $(".li2").find('.timeline_span_left,.timeline_span_right').css('color','#FCD833');
        if($(".li2").attr('data-onselect')!=1){
	   		var lastselect=$("li[data-onselect='1']");
	   		lastselect.attr('data-onselect',0);
	   		lastselect.find('.timeline_divicon_side').slideUp(200);
			lastselect.find('.timeline_span_left,.timeline_span_right').css('color','#E5F3FF');
			$(".li2").attr('data-onselect',1);
	    }
	    $('.container').hide();
	    $('.container2').show();
		//alert("1");
		jQuery.ajax({
			type : 'POST',
			url : "/csp/source/page/Xpage/portScanMethod.html",
			dataType : 'json',
			data:{'target':target,'port':port,'scan':scan,'agreement':'tcp'},
			success : function(data) {
				var str=JSON.stringify(data);
				//alert(data.status);
				if(data.status=="success"){
					 $('#tb_departments').bootstrapTable('load',data.portLists.rows);
					 //confirm({title:'来自星星的提示',mess:'您确定要给文字设置绿色吗？',ico:'warn'});
				}
				else{
					alert("暂无数据，请稍后");
				}
				//$('#tb_departments').bootstrapTable('load',data);
			}
		});
	}
}
$(document).ready(function() {
//左侧悬浮效果
$(".li1").hover(
          function () {
          $(this).find('.timeline_divicon_side').slideDown(400);
          $(this).find('.timeline_span_left,.timeline_span_right').css('color','#FCD833');
        },
        function(){
          if($(this).attr('data-onselect')!=1){
            $(this).find('.timeline_divicon_side').slideUp(200);
            $(this).find('.timeline_span_left,.timeline_span_right').css('color','#E5F3FF');  
          }
          
        }
        );
//左侧点击切换
 $(".li1").click(function(){
          $(this).find('.timeline_divicon_side').slideDown(400);
          $(this).find('.timeline_span_left,.timeline_span_right').css('color','#FCD833');
          if($(this).attr('data-onselect')!=1){
	    		var lastselect=$("li[data-onselect='1']");
	    		lastselect.attr('data-onselect',0);
	    		lastselect.find('.timeline_divicon_side').slideUp(200);
				lastselect.find('.timeline_span_left,.timeline_span_right').css('color','#E5F3FF');
				$(this).attr('data-onselect',1);
	    	}
	      $('.container').hide();
	      $('.container1').show();
}); 
//默认点击 
$(".li1").click();
 $('#form').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove', 
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                target: {
                    message: '信息提交失败',
                    validators: {
                        notEmpty: {
                            message: '扫描目标不能为空'
                        },
                        regexp: {
                            //regexp:/(^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$)|(^http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?)/,
                            regexp:/(^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$)|(((com)|(net)|(org)|(gov\.cn)|(info)|(cc)|(com\.cn)|(net\.cn)|(org\.cn)|(name)|(biz)|(tv)|(cn)|(mobi)|(name)|(sh)|(ac)|(io)|(tw)|(com\.tw)|(hk)|(com\.hk)|(ws)|(travel)|(us)|(tm)|(la)|(me\.uk)|(org\.uk)|(ltd\.uk)|(plc\.uk)|(in)|(eu)|(it)|(jp))$)/,
                           // |(/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/)
                           //(/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/)
                            message: '请输入正确的ip或域名'
                        }
                    }
                },
                port: {
                    validators: {
                        notEmpty: {
                            message: '端口号不能为空'
                        },
                        regexp: {
                            regexp:/(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|^[1-5][0-9]{4}|^[1-9][0-9]{0,3}|^0){1}((-)(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{0,3}|0)){0,1}((,)(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{0,3}|0)((-)(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{0,3}|0)){0,1})*$/,
                            //  (6553[0-5]$|655[0-2][0-9]$|65[0-4][0-9]{2}$|6[0-4][0-9]{3}$|^[1-5][0-9]{4}$|^[-9][0-9]{0,3}$|^0$) 
                            ///6553[0-5]$|655[0-2][0-9]$|65[0-4][0-9]{2}$|6[0-4][0-9]{3}$|^[1-5][0-9]{4}$|^[1-9][0-9]{0,3}$/
                            message: '请输入正确的端口号，端口号范围为0-65535'
                        }
                    }
                }
            }
        })
        .on('error.form.bv', function(e) {
            var $form = $(e.target),
                bootstrapValidator = $form.data('bootstrapValidator');
            if (!bootstrapValidator.isValidField('captcha')) {
                // The captcha is not valid
                // Regenerate the captcha
                generateCaptcha();
            }
        });

	
});
$(function () {
	var datatype=[];
    var oTable = new TableInit();
    oTable.Init(datatype);
});
var TableInit = function () {
    var oTableInit = new Object();
    oTableInit.Init = function (datatype) {
        $('#tb_departments').bootstrapTable({
          // url: '/csp/source/page/Xpage/portScanMethod.html',         //请求后台的URL（*）
            //method: 'post',                      //请求方式（*）
            data:datatype,
           // toolbar: '#toolbar',                //工具按钮用哪个容器
            locale:'zh-CN',//中文支持
             pagination: true,//是否开启分页（*）
            pageNumber:1,//初始化加载第一页，默认第一页
            pageSize: 8,//每页的记录行数（*）
           pageList: [8],//可供选择的每页的行数（*）
           sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
            //showRefresh:true,//刷新按钮
            showExport: true, 
            buttonsAlign:"right",  //按钮位置  
            exportDataType:'all',
            exportTypes:['excel','json', 'xml', 'csv', 'txt', 'sql'],  //导出文件类型  
          search: true,//是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                        
                        //【样式设置】
            //height: 500,//table的高度
            columns: [{
                field: 'id',
                title: '序号'
            }, {
                field: 'port',
                title: '端口号'
            }, {
                field: 'agreement',
                title: '协议'
            }, {
                field: 'state',
                title: '状态'
            }, {
                field: 'time',
                title: '添加时间'
            },]
        });
    };

    //得到查询的参数
    
    return oTableInit;
};

var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };

    return oInit;
};     
</script>
</html>
