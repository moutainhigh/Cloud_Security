<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
   <title>Try Bootstrap Online</title>
    <link rel="stylesheet" href="${ctx}/source/css/bootstrap.css"/>
    <link rel="stylesheet" href="${ctx}/source/css/bootstrapValidator.css"/>
	<link href="${ctx}/source/css/bootstrap-table.css" rel="stylesheet"/> 
    <script type="text/javascript" src="${ctx}/source/scripts/common/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/source/scripts/common/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/source/scripts/common/bootstrapValidator.js"></script>
    <script src="${ctx}/source/scripts/common/bootstrap-table.min.js"></script>
    <script src="${ctx}/source/scripts/common/bootstrap-table-zh-CN.js"></script>
    <script src="${ctx}/source/scripts/common/bootstrap-table-export.min.js"></script>
    <script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
	<style >
@charset "utf-8";
/**
 * 	@name:style for 0day
 *	@author: rayzhang
 *  @email:ray.zhang@dbappsecurity.com.cn
 */
html{
	width: 100%;
    overflow-y: auto;
    overflow-x: hidden;
	min-width:1024px;
	height: 100%;
	min-height: 680px;
	/*background-color:#FFFFFF;*/
	font-family: "Microsoft YaHei",verdana, "Hiragino Sans GB";
}
  
.body{
	width: 100%;
	min-width:1024px;
	height: 100%;
	min-height: 680px;
	margin:0;
	padding:0 15px;
	border:0;
	background: url('http://0day.websaas.com.cn/img/1112.jpg') no-repeat;
	/*background-color: #269ABC;*/
	background-size: cover;
	position: absolute;
	/*overflow:-Scroll;*/
	overflow-x:hidden;
	/*overflow:-moz-scrollbars-vertical;*/
	 /*scroll="no";*/
}

/**
 * 	滚动条
 */
	::-webkit-scrollbar {
		  width: 6px;
	} 
	
	::-webkit-scrollbar-track {
		  background-color: rgba(0, 0, 0, 0);
	} /* 滚动条的滑轨背景颜色 */

	::-webkit-scrollbar-thumb {
		  background-color: rgba(0, 0, 0, 0.2); 
	} /* 滑块颜色 */

	::-webkit-scrollbar-button {
		  background-color:rgba(0, 0, 0, 0);
	} /* 滑轨两头的监听按钮颜色 */

	::-webkit-scrollbar-corner {
		  background-color: rgba(0,0,0,0);
	} /* 横向滚动条和纵向滚动条相交处尖角的颜色 */
	
	
	
.bannerbox {
	width:73%;
	height:14%;
	min-height:130px;
	/*margin-top:1%;*/
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
	background:url("http://0day.websaas.com.cn/img/point_blue.png") 103px 0 repeat-y;
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
	font-size: 12px;
	padding-top: 10px;
	color:#8f8d8d;
	word-break:break-all ;
	overflow:hidden; 
}
.timeline_div_icon{
	width:66px;
	height:100%;
	float: left;
	background: url("http://0day.websaas.com.cn/img/yq.png") 26px 18px no-repeat;
	cursor: pointer;
}

.timeline_divicon_side{
	display: none;
	width:100%;
	height:100%;
	line-height:100%;
	text-align:center;
	padding-top:20px;
	background: url("http://0day.websaas.com.cn/img/yq_1.png") 2px -5px no-repeat;
}

.timeline_div_icon label{
	line-height:14px;
	color:rgb(253,149,38);
	font-size:14px;
	cursor: pointer;
	position: relative;
}
.timeline_div_icon label:hover{
	color: rgb(253,149,38);
}



.searchbox{
	margin-right: 7%;
	margin-left:1%;
	width:54%;
	height:100%;
	padding-top:15px;
	float: left;
	position: relative;
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
	/*margin-left: 15px;*/
	
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
.form-group label {
color:#FFF;
}
.btn {
background-color: #ed9c28;
border-color: #d58512;
}
.btn:hover {
color: #fff;
background-color: #ed9c28;
border-color: #d58512;
}
</style>
</head>
<body  class="body container-fluid">
        <!-- <div class="bannerbox"></div> -->
        <div class="contentbox">
          <div class="timelinebox">
            <ul class="timeline_ul">
              <li data-onselect="1">
                  <span class="timeline_span_left"></span>
                  <div class="timeline_div_icon">
                       <div class="timeline_divicon_side" data-checkid="0"><label>创建</label></div>
                  </div>
                  <span class="timeline_span_right">创建新任务</span>
              </li>
              <li>
                  <span class="timeline_span_left"></span>
                  <div class="timeline_div_icon">
                          <div class="timeline_divicon_side" data-checkid="1"><label>详情</label></div>
                  </div>
                  <span class="timeline_span_right">查看详情</span>
              </li> 
            </ul>
          </div>
          <div class="container searchbox">
             <div class="tab-pane fade in active" id="home"   style="width:70%;align:center;left:10%;margin:0 auto;">
                 <form role="form" method="post"  id="form" name="form"  >
                     <br/><br/>
		             <div class="form-group">
			             <label for="name">扫描目标</label>
			             <input type="text" class="form-control" id="target" name="target" placeholder="例如：127.0.0.1或者baidu.com"/>
		             </div>
		             <br/>
		             <div class="form-group">
			             <label for="name">扫描端口</label>
			             <input type="text" class="form-control" id="port"  name="port" placeholder="例如：0-65535 or 21，23，80"/>
		             </div>
		             <br/><br/>
		             <div class="form-group">
		                 <label for="name">扫描选项</label> 
						 <select class="form-control" name="scan" id="scan">
							<option selected="selected" value="default">默认选项</option>
							<option value="TCP SYN">TCP SYN扫描</option>
							<option value="TCP connect">TCP connect 扫描</option>
							<option value="ping">ping 扫描</option>
							<option value="UDP">UDP 扫描</option>
						 </select>		
		              </div>
		              <br/><br/>
		             <div class="form-group">
		                 <button type="reset" class="btn">重置</button>
                         <button type="button" class="btn" onclick="submitForm()" id="btn_sub">提交</button>
		             </div>
	             </form>
             </div>
          </div>
          <div class="container searchbox container2">
              <div class="row">
                  <div id="task" style="margin-top:0px;width:80%;margin:0 auto">
                  <br/>
                  <table id="tb_departments" align="center" valign="center"></table>
          </div>
        </div>
    
      <!--<div class="container footer">
          <div class="company">
            <span><img src="img/logo.png"> 杭州安恒信息技术有限公司 ©版权所有 2007-2016</span>
          </div>
          <div class="icp">
                    <a href="http://www.miibeian.gov.cn/" target="_blank"><font color="#fff">浙ICP备09102757号-12</font></a>
          </div>
      </div>-->
    
<!--       <div class="modal fade" id="verifyModal">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">重大漏洞安全监测-验证码</h4>
            </div>
            <div class="modal-body">
              <div class="container-fluid">
                <div class="row">
              <div class="col-xs-3"><img src="labs/verify.php" id="verifyimg"/></div>
              <div class="col-xs-9">
                <form class="form-inline">
                  <div class="form-group col-xs-4">
                    <div class="input-group">
                      <input type="text" class="form-control" id="verifycode" name="code" placeholder="←点击刷新" maxlength="4">
                    </div>
                  </div>
                  <button type="button" class="btn btn-warning" id="verifysub">检测</button>
                  <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                  <span id="verifymsg"></span>
                </form>
              </div>
                </div>
              </div>
            </div>
          </div>/.modal-content
        </div>/.modal-dialog
      </div> -->
<script>


function submitForm(){
	//alert();
	$(form).data('bootstrapValidator').validate();
	if($(form).data('bootstrapValidator').isValid()){
		var target=$("#target").val();
		var port=$("#port").val();
		var scan=$("#scan").val();
		//alert(target);
		$(".timeline_ul li:eq(1)").click();
		//alert("1");
		jQuery.ajax({
			type : 'POST',
			url : "portScanMethod.html",
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
$(".timeline_ul>li").hover(
          function () {
          $(this).find('.timeline_divicon_side').slideDown(400);
          $(this).find('.timeline_span_left,.timeline_span_right').css('color','rgb(253,149,38)');
        },
        function(){
          if($(this).attr('data-onselect')!=1){
            $(this).find('.timeline_divicon_side').slideUp(200);
            $(this).find('.timeline_span_left,.timeline_span_right').css('color','#8f8d8d');  
          }
          
        }
        );
//左侧点击切换
 $(".timeline_ul>li").click(function(){
          var checkid=$(this).find('.timeline_divicon_side').data('checkid')
          $(this).find('.timeline_divicon_side').slideDown(400);
          $(this).find('.timeline_span_left,.timeline_span_right').css('color','rgb(253,149,38)');
          if($(this).attr('data-onselect')!=1){
	    		var lastselect=$("li[data-onselect='1']");
	    		lastselect.attr('data-onselect',0);
	    		lastselect.find('.timeline_divicon_side').slideUp(200);
				lastselect.find('.timeline_span_left,.timeline_span_right').css('color','#8f8d8d');
				$(this).attr('data-onselect',1);
	    	}
	      $('.container').hide();
	      $('.container').eq(checkid).show();
}); 
//默认点击 
$(".timeline_ul li:eq(0)").click();
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
                            regexp:/(^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$)|(((com)|(net)|(org)|(gov\.cn)|(info)|(cc)|(com\.cn)|(net\.cn)|(org\.cn)|(name)|(biz)|(tv)|(cn)|(mobi)|(name)|(sh)|(ac)|(io)|(tw)|(com\.tw)|(hk)|(com\.hk)|(ws)|(travel)|(us)|(tm)|(la)|(me\.uk)|(org\.uk)|(ltd\.uk)|(plc\.uk)|(in)|(eu)|(it)|(jp))$)/,
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

</body>
</html>