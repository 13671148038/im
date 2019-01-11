<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="modal-box add-data-source">
    <div class="mod-title">
        <span>用户修改</span>
        <span onclick="javascript: shutDialog();">关闭</span>
    </div>
    <div class="mod-body add_user">
        <table>
            <tr>
                <td>用户名<em>* </em>:</td>
                <td>
                    <span class="input-sp"><input autocomplete="off" readonly="readonly" id="userName" type="text"></span>
                </td>
            </tr>
            <tr>
                <td>密码<em>* </em>:</td>
                <td>
                    <span class="input-sp">
                        <input autocomplete="off" id="pass" type="password">
                    </span>
                </td>
            </tr>
            <!-- <tr>
                <td>确认密码<em>* </em>:</td>
                <td>
                    <span class="input-sp">
                        <input autocomplete="off" id="repass" type="password">
                    </span>
                </td>
            </tr> -->
            <tr>
                <td>姓名<em>* </em>:</td>
                <td>
                    <span class="input-sp">
                        <input autocomplete="off" id="name" type="text">
                    </span>
                </td>
            </tr>
            <tr>
                <td>联系方式:</td>
                <td>
                    <span class="input-sp">
                        <input autocomplete="off" id="contact" type="text">
                    </span>
                </td>
            </tr>
             <tr>
                <td></td>
                <td><span id="errorInfo" class="input-sp" style="float: left;color: red;border: none" ></span></td>
            </tr>
        </table>
    </div>
    <div class="mod-footer" style="position: static;">
        <button type="button" class="buleBgBtn" id="editUser">保存</button>
        <button type="button" class="buleBgBtn" onclick="javascript:shutDialog()">取消</button>
    </div>
</div>
<script type="text/javascript">
   var userName = "${userName}"
   $.ajax({
	   type:"get",
	   dataType:"json",
	   data:{
		   userName:userName   
	   },
	   url:"${pageContext.request.contextPath}/phoneuser/ajax/getByUserName.do",
	   success:function(data){
		   $("#userName").val(data.userName)
		   $("#name").val(data.name)
		   $("#contact").val(data.contact)
		   }
   })
   
   
    $("#editUser").click(function () {
        var userName=$.trim($("#userName").val());
        if(userName==""){
            $("#errorInfo").text("用户名你能为空")
            return
        }
        var pass=$.trim($("#pass").val());
        //var repass=$.trim($("#repass").val());
       /*  if(pass==""){
        	pass=null;
        } */
      /*   if(pass!=repass){
            $("#errorInfo").text("密码不一致")
            return
        } */
        var name=$.trim($("#name").val());
        if(name==""){
            $("#errorInfo").text("姓名不能为空")
            return
        }
        var contact=$.trim($("#contact").val());
        $.ajax({
        	type:"post",
        	dataType:"text",
        	data:{
        		userName:userName,
        		passWord:pass,
        		name:name,
        		contact:contact
        	},
        	url:"${pageContext.request.contextPath}/phoneuser/ajax/updateUser.do",
        	success:function(data){
        		if(data=="SUCCESS"){
        		shutDialog()
        		reload()
        		$.messager.alert("提示","用户修改成功",'info')
        		}else{
        		$.messager.alert("提示","用户修改失败",'error')
        		}
        	},error:function(){
        		location.href="${pageContext.request.contextPath}/user/toLogin.do"
        	}
        })
    });
</script>