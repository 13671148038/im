<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div style="text-align: center;height: 50px;line-height: 50px;font-size: 20px;">文档管理</div>
    <div style="margin-top: 10px;padding:5px 0;">
    <table id="wordListTable" style="width:1160px;max-height: 600px"></table>
    <div id="kkpager"></div>
    </div>
<script type="text/javascript">
$('#wordListTable').datagrid({
	title:"文档列表",
	rownumbers:true,
	singleSelect:true,
	/* pagination:true,
	pagePosition:"top",
	loader:function(param,success){
		 $.ajax({
		        type : "get",
		        url : "${pageContext.request.contextPath}/word/ajax/getPageDate.do",
		        data : param,
		        dataType  : "json",
		        success : success,
		        error : function(){
		        	toLogin()
		        }
		});
	}, */
	toolbar: [{
		text:'导 出',
		//iconCls: 'icon-',
		handler: function(){
			importCheckResult()
		}
	},'-',{
		text:'刪 除',
		//iconCls: 'icon-remove',
		handler: function(){
			remove()
	}
	}],
    columns:[[
        {field:'importTime',title:'导入时间',width:1120,align:'center'},
        {field:'id',title:'id',hidden:true},
    ]]
});
var currentPage=1
getPageDate(1)
//加载数据
function getPageDate(currentPage1){
	currentPage=currentPage1
	$.ajax({
		type:"get",
		dataType:"json",
		data:{
			page:currentPage,
			rows:10,
		},
		url:"${pageContext.request.contextPath}/word/ajax/getPageDate.do",
		success:function(data){
			$('#wordListTable').datagrid({
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
			// do something
			//手动选中按钮
			getPageDate(n)
			this.selectPage(n);
		}
	},true);
}



	//获取选中的列的用户id
	function getSelected(){
        var row = $('#wordListTable').datagrid('getSelected');
        if (row){
            return row.id
        }
        return "";
    }
	//导出检查结果
	function importCheckResult(){
		var id = getSelected()
		if(!id){
			$.messager.alert("提示","请选择要导出的列",'error')
			return
		}
		$.messager.confirm('确认', '确定导出吗?', function(r){
            if (r){
            	location.href="${pageContext.request.contextPath}/task/importResult.do?wordId="+id;
            }
        });
	}
	//删除
	function remove(){
		var id = getSelected()
		if(!id){
			$.messager.alert("提示","请选择要删除的列",'error')
			return
		}
		$.messager.confirm('确认', '确定删除吗?', function(r){
            if (r){
            	deleteWord(id)
            }
        });
	}
	
	function deleteWord(id){
		$.ajax({
			type:"get",
			dataType:"text",
			data:{
				id:id
			},
			url:"${pageContext.request.contextPath}/word/ajax/deleteWord.do",
			success:function(data){
				if(data=="SUCCESS"){
					getWordList()
				}else{
					$.messager.alert("错误","删除失败","error")
				}
			},
			error:function(){
				toLogin()
			}
		})
	}
	function getWordList(){
		getPageDate(currentPage)
	}
</script>
