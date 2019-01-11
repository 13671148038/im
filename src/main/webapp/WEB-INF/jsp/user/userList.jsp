<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div style="text-align: center;height: 50px;line-height: 50px;font-size: 20px;">用户管理</div>
    <div style="margin-top: 10px;padding:5px 0;">
    <table id="userListTable" style="width:1160px"></table>
    </div>
<script type="text/javascript">
$('#userListTable').datagrid({
	title:"用户列表",
	rownumbers:true,
	singleSelect:true,
	pagination:true,
	loader:function(param,success){
		 $.ajax({
		        type : "get",
		        url : "${pageContext.request.contextPath}/user/ajax/getPageDate.do",
		        data : param,
		        dataType  : "json",
		        success : success,
		        error : function(){
		        	toLogin()
		        }
		});
	},
	toolbar: [{
		text:'添加',
		iconCls: 'icon-check',
		handler: function(){
			add()
		}
	},'-',{
		text:'刪除',
		iconCls: 'icon-remove',
		handler: function(){
			remove()
	}
	}],
    columns:[[
        {field:'userName',title:'用户名',width:285,align:'center'},
        {field:'name',title:'姓名',width:280,align:'center'},
        {field:'contact',title:'电话号码',width:280,align:'center'},
        {field:'createTime',title:'创建时间',width:280,align:'center'}
    ]]
});
	//获取选中的列的用户id
	function getSelected(){
        var row = $('#userListTable').datagrid('getSelected');
        if (row){
            return row.userName
        }
        return "";
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
	
	function deleteUser(userName){
		$.ajax({
			type:"get",
			dataType:"text",
			data:{
				userName:userName
			},
			url:"${pageContext.request.contextPath}/user/ajax/deleteUser.do",
			success:function(data){
				if(data=="SUCCESS"){
					getUserList()
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
		openDialogFold("${pageContext.request.contextPath}/user/toAddUser.do",450,350,100)
	}
	function getUserList(){
		$('#userListTable').datagrid('reload')
	}
</script>
