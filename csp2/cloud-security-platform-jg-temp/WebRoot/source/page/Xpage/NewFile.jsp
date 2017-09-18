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
		.table th, .table td { 
	
		text-align: center; 
		
		height:38px;
		
		}
	</style>
</head>
<body>

<ul id="myTab" class="nav nav-tabs" >
   <li class="active"><a href="#home" data-toggle="tab">
      +创建</a>
   </li>
   <li><a href="#task" data-toggle="tab">任务详情</a></li>
</ul>
<div id="myTabContent" class="tab-content">
   <div class="tab-pane fade in active" id="home"   style="width:70%;align:center;left:10%;margin:0 auto;">
      <form role="form" method="post"  id="form" name="form"  >
      <br/><br/>
		<div class="form-group">
			<label for="name">扫描目标</label>
			<input type="text" class="form-control" id="target" name="target" 
				   placeholder="例如：127.0.0.1或者baidu.com"/>
		</div>
		<br/>
		<div class="form-group">
			<label for="name">扫描端口</label>
			<input type="text" class="form-control" id="port"  name="port"
				   placeholder="例如：0-65535 or 21，23，80"/>
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
		<br/>
		<br/>
		<div class="form-group">
			<button type="reset" class="btn btn-default">重置</button>
	
			<button type="button" class="btn btn-default" onclick="submitForm()" id="btn_sub">提交</button>
		</div>
		
	</form>
   </div>
   <div class="tab-pane fade" id="task" style="margin-top:0px;width:80%;margin:0 auto">
   <br/>
     	
		<table id="tb_departments" align="center" valign="center"   ></table>
   </div>
  
</div>
<script>


function submitForm(){
	
	
	//alert();
	$(form).data('bootstrapValidator').validate();
	if($(form).data('bootstrapValidator').isValid()){
		var target=$("#target").val();
		var port=$("#port").val();
		var scan=$("#scan").val();
		//alert(target);
		$('#myTab li:eq(1) a').tab('show');
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
		//var datatype=[];
	    //datatype=[{"id":23,"port":34,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"},{"id":1,"port":45,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"},{"id":2,"port":78,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"},{"id":3,"port":89,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"},{"id":4,"port":90,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"},{"id":5,"port":91,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"}];
	   
	    //初始化Table
	    //1.初始化Table
	   // var oTable = new TableInit();
	    //oTable.Init(datatype);
	    //alert("1");
	}
	
	
	
	
	//alert("a");
}
$(document).ready(function() {
	/*
	alert(liId);
 	if(liId==0){
 		
 		$('#myTab li:eq(0) a').tab('show');
 	}
 	else{
 		$('#myTab li:eq(1) a').tab('show');
 	}	
 	//else('#myTab li:eq(0) a').tab('show');
   	 //alert(x);
  
  */
 

 $('#form')
        .bootstrapValidator({
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
            var $form              = $(e.target),
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
  //  datatype=[{"id":23,"port":34,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"},{"id":1,"port":45,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"},{"id":2,"port":78,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"},{"id":3,"port":89,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"},{"id":4,"port":90,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"},{"id":5,"port":91,"agreement":"co","state":"up","time":"2017-06-07 19:41:23"}];
    //初始化Table
    //1.初始化Table
    //alert("a");
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