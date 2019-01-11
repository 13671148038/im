<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div style="text-align: center;height: 50px;line-height: 50px;font-size: 20px;">终端用户管理</div>
    <div style="margin-top: 10px;padding:5px 0;">
    <table id="userListTable" style="width:1160px;max-height: 600px"></table>
    <div id="kkpager"></div>
    </div>
<script type="text/javascript">
$('#userListTable').datagrid({
	title:"终端用户列表",
	rownumbers:true,
	singleSelect:true,
	/* pagination:true,
	pagePosition:"top",
	loader:function(param,success){
		 $.ajax({
		        type : "get",
		        url : "${pageContext.request.contextPath}/phoneuser/ajax/getPageDate.do",
		        data : param,
		        dataType  : "json",
		        success : success,
		        error : function(){
		        	toLogin()
		        }
		});
	}, */
	toolbar: [{
		text:'添加',
		iconCls: 'icon-add',
		handler: function(){
			add()
		}
	},'-',{
		text:'刪除',
		iconCls: 'icon-remove',
		handler: function(){
			remove()
		},
	},'-',{
		text:'修改',
		iconCls: 'icon-edit',
		handler: function(){
			edit()
		}
			  }],
    columns:[[
        {field:'userName',title:'用户名',width:285,align:'center'},
        {field:'name',title:'姓名',width:280,align:'center'},
        {field:'contact',title:'电话号码',width:280,align:'center'},
        {field:'createTime',title:'创建时间',width:282,align:'center'}
    ]]
});
var currentPage=1
getPageDate(1)
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
		url:"${pageContext.request.contextPath}/phoneuser/ajax/getPageDate.do",
		success:function(data){
			$('#userListTable').datagrid({
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
        var row = $('#userListTable').datagrid('getSelected');
        if (row){
            return row.userName
        }
        return "";
    }
	function edit(){
		var userName = getSelected();
		if(!userName){
			$.message.alert("提示","请选择要修改的用户","error")
			return
		}
		openDialogFold("${pageContext.request.contextPath}/phoneuser/toEditUser.do?userName="+userName,450,350,100)
	}
	
	
	function remove(){
		var userName = getSelected()
		if(!userName){
			$.messager.alert("提示","请选择要删除的列",'error')
			return
		}
		$.messager.confirm('确认', '确定删除吗?', function(r){
            if (r){
            	deleteUser(userName)
            }
        });
	}

				//location.href="${pageContext.request.contextPath}/user/toLogin.do"

	function deleteUser(userName){
		$.ajax({
			type:"get",
			dataType:"text",
			data:{
				userName:userName
			},
			url:"${pageContext.request.contextPath}/phoneuser/ajax/deleteUser.do",
			success:function(data){
				if(data=="SUCCESS"){
					getPageDate(currentPage);
					//$('#userListTable').datagrid('reload')
				}else{
					$.messager.alert("错误","删除失败","error")
				}
			},
			error:function(){
				location.href="${pageContext.request.contextPath}/user/toLogin.do"
			}
		})
	}
	function add(){
		openDialogFold("${pageContext.request.contextPath}/phoneuser/toAddUser.do",450,350,100)
	}
	function reload(){
		getPageDate(currentPage);
	}
</script>
