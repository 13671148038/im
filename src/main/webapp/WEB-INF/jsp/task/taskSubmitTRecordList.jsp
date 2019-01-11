<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div style="text-align: center;height: 50px;line-height: 50px;font-size: 20px;">文档管理</div>
    <div style="margin-top: 10px;padding:5px 0;">
    <table id="taskSubmitRecordListTable" style="width:1160px;max-height: 600px">
    </table>
    <div id="kkpager"></div>
    </div>
<script type="text/javascript">
$('#taskSubmitRecordListTable').datagrid({
	title:"任务提交记录列表",
	rownumbers:true,
	singleSelect:true,
	autoRowHeight:true,
/* 	pagination:true,
	pagePosition:"top", */
	/* loader:function(param,success){
		 $.ajax({
		        type : "get",
		        url : "${pageContext.request.contextPath}/taskSubmitRecord/ajax/getPageDate.do",
		        data : param,
		        dataType  : "json",
		        success : success,
		        error : function(){
		        	toLogin()
		        }
		});
	}, */
	toolbar: [{
		text:'刪除',
		iconCls: 'icon-remove',
		handler: function(){
			remove()
	}
	}],
    columns:[[
        {field:'id',title:'用户名',hidden:true,width:285,align:'center'},
        {field:'submitUser',title:'提交用户',width:100,align:'center'},
        {field:'taskId',title:'任务名称',width:480,align:'center',
       	 formatter:function(val, rec,index){
          	if(val){
                  return '<div style="width=100%;word-break:break-all; word-wrap:break-word;white-space:pre-wrap;">'+rec.taskId+'</div>';
          	}
          	return '';
          }
        },
        {field:'checkResult',title:'检查结果',width:200,align:'center'},
        {field:'taskType',title:'任务类型',width:100,align:'center'},
        {field:'checkTime',title:'任务日期',width:100,align:'center'},
        {field:'submitTime',title:'提交时间',width:145,align:'center'}
    ]]
});
getPageDate(1)
var currentPage = 1;
//查询数据
function getPageDate(currentPage1){
	currentPage=currentPage1
	$.ajax({
		type:"get",
		dataType:"json",
		data:{
			page:currentPage,
			rows:10,
		},
		url:"${pageContext.request.contextPath}/taskSubmitRecord/ajax/getPageDate.do",
		success:function(data){
			$('#taskSubmitRecordListTable').datagrid({
				data: data.rows
			});
			loadPage(data.currentPage,data.totalPage,data.total);
		},
		error:function(){
			toLogin()
		}
	})
}
//加载分页插件
function loadPage(currentPage,totalPage,totalCount){
	kkpager.generPageHtml({
		pno : currentPage,
		//总页码
		total : totalPage,
		//总数据条数
		totalRecords : totalCount,
		mode : 'click',//默认值是link，可选link或者click
		click : function(n){
			console.log(this)
			// do something
			//手动选中按钮
			getPageDate(n)
			this.selectPage(n);
		}
	},true);
}


	//获取选中的列的用户id
	function getSelected(){
        var row = $('#taskSubmitRecordListTable').datagrid('getSelected');
        if (row){
            return row.id
        }
        return "";
    }
	function remove(){
		var id = getSelected()
		if(!id){
			$.messager.alert("提示","请选择要删除的列",'error')
			return
		}
		$.messager.confirm('确认', '确定删除吗?', function(r){
            if (r){
            	deleteRecord(id)
            }
        });
	}
	
	function deleteRecord(id){
		$.ajax({
			type:"get",
			dataType:"text",
			data:{
				id:id
			},
			url:"${pageContext.request.contextPath}/taskSubmitRecord/ajax/deleteRecord.do",
			success:function(data){
				if(data=="SUCCESS"){
					getTaskSubmitRecordList()
				}else{
					$.messager.alert("错误","删除失败","error")
				}
			},
			error:function(){
				toLogin()
			}
		})
	}
	function add(){
		openDialogFold("${pageContext.request.contextPath}/word/toAddUser.do",450,350,100)
	}
	function getTaskSubmitRecordList(){
		getPageDate(currentPage)
	}
</script>
