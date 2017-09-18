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

    <script type="text/javascript" src="${ctx}/source/scripts/common/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/source/scripts/common/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/source/scripts/common/bootstrapValidator.js"></script>
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
				   placeholder="例如：127.0.0.1或者https://www.baidu.com"/>
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
	
			<button type="submit" class="btn btn-default" onclick="submitForm()">提交</button>
		</div>
		
	</form>
   </div>
   <div class="tab-pane fade" id="task">
   <br/>
     	<table class="table table-hover" align="center" valign="center"   style="width:80%" id="tableList" name="tableList">
		  
		  <thead>
		    <tr>
		      <th>序号</th>
		      <th>端口</th>
		      <th>协议</th>
		      <th>状态</th>
		      <th>添加时间</th>
		    </tr>
		  </thead>
		  <tbody>
		    <tr>
		      <td>1</td>
		      <td>25</td>
		      <td>SMTP</td>
		      <td>UP</td>
		      <td>2017-03-30 10:18:20</td>
		    </tr>
		    <tr>
		      <td>2</td>
		      <td>110</td>
		      <td>POP3</td>
		      <td>UP</td>
		      <td>2017-03-30 10:18:20</td>
		    </tr>
		    <tr>
		      <td>3</td>
		      <td>130</td>
		      <td>POP3</td>
		      <td>UP</td>
		      <td>2017-03-30 10:18:20</td>
		    </tr>
		  </tbody>
		</table>
		<table id="tb_departments"></table>
   </div>
  
</div>
<script>

$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_departments').bootstrapTable({
            url: '/Home/GetDepartment',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'Name',
                title: '部门名称'
            }, {
                field: 'ParentName',
                title: '上级部门'
            }, {
                field: 'Level',
                title: '部门级别'
            }, {
                field: 'Desc',
                title: '描述'
            }, ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            departmentname: $("#txt_search_departmentname").val(),
            statu: $("#txt_search_statu").val()
        };
        return temp;
    };
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