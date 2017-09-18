<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <meta charset="utf-8">
        <title>图片上传</title>
        <!-- jq -->
        <script type="text/javascript" src="<%=basePath%>js/jquery-3.1.1.min.js"></script>
        
        <!-- bootstrap -->
        <link rel="stylesheet" href="<%=basePath%>/plugs/bootstrap/css/bootstrap.min.css">
        <script type="text/javascript" src="<%=basePath%>/plugs/bootstrap/js/bootstrap.min.js"></script>
        
        <!-- 分页插件 -->
        <link rel="stylesheet" href="<%=basePath%>plugs/bootstrap-table/bootstrap-table.min.css">
        <script type="text/javascript" src="<%=basePath%>plugs/bootstrap-table/bootstrap-table.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>plugs/bootstrap-table/bootstrap-table-locale-all.min.js"></script>
    </head>
    <body>
        <div class="container" style="margin-top:100px">
            <div class="row">
                <!-- 表格 -->
                <div class="col-xs-12">
                    <table class="table table-striped table-bordered table-hover" ></table>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            class BstpTable{
                constructor(obj) {
                    this.obj=obj;
                }
                inint(){
                     //---先销毁表格 ---
                     this.obj.bootstrapTable('destroy');  
                     //---初始化表格,动态从服务器加载数据--- 
                     this.obj.bootstrapTable({
                        //【发出请求的基础信息】
                        url: '<%=basePath%>student/selectAll',
                        method: 'post',
                        contentType: "application/x-www-form-urlencoded",
                        
                        //【其它设置】
                        locale:'zh-CN',//中文支持
                        pagination: true,//是否开启分页（*）
                        pageNumber:1,//初始化加载第一页，默认第一页
                        pageSize: 3,//每页的记录行数（*）
                        pageList: [2,3,4],//可供选择的每页的行数（*）
                        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
                        showRefresh:true,//刷新按钮
                        search: true,//是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                        
                        //【样式设置】
                        height: 300,//table的高度
                        //按需求设置不同的样式：5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                        rowStyle: function (row, index) {
                            var style = "";
                            if (row.name=="毛主席") {style ='danger';}
                            return { classes: style }
                        },
                        
                        //【设置列】
                        columns: [
                         {field: 'id',title: 'id'}, 
                         {field: 'name',title: '姓名'},
                         {field: 'age',title: '年龄'},
                         {field: 'tool',title: '操作', align: 'center',
                            formatter:function(value,row,index){
                                var element = 
                                "<a class='edit' data-id='"+row.id +"'>编辑</a> "+ 
                                "<a class='delet' data-id='"+row.id +"'>删除</a> ";
                                return element;  
                            } 
                          }
                        ]
                    })
                 }
            };
            
            var bstpTable=new BstpTable($("table"));
            bstpTable.inint()
        </script>
    </body>
</html>