<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="modal-box add-data-source">
    <div class="mod-title">
        <span>修改密码</span>
        <span onclick="javascript: shutDialog();">关闭</span>
    </div>
    <div class="mod-body add_user">
        <table>
            <tr>
                <td>旧密码<em>* </em>:</td>
                <td>
                    <span class="input-sp"><input autocomplete="off" id="oldPass" type="text"></span>
                </td>
            </tr>
            <tr>
                <td>新密码<em>* </em>:</td>
                <td>
                    <span class="input-sp">
                        <input autocomplete="off" id="pass" type="password">
                    </span>
                </td>
            </tr>
            <tr>
                <td>确认密码<em>* </em>:</td>
                <td>
                    <span class="input-sp">
                        <input autocomplete="off" id="repass" type="password">
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
        <button type="button" class="buleBgBtn" id="editPass">确认</button>
        <button type="button" class="buleBgBtn" onclick="javascript:shutDialog()">取消</button>
    </div>
</div>
<script type="text/javascript">
    $("#editPass").click(function () {
        var oldPass=$.trim($("#oldPass").val());
        if(oldPass==""){
            $("#errorInfo").text("旧密码不能为空")
            return
        }
        var pass=$.trim($("#pass").val());
        var repass=$.trim($("#repass").val());
        if(pass==""){
            $("#errorInfo").text("密码不能为空")
            return
        }
        if(pass!=repass){
            $("#errorInfo").text("密码不一致")
            return
        }
        $.ajax({
        	type:"post",
        	dataType:"text",
        	data:{
        		oldPass:oldPass,
        		passWord:pass,
        	},
        	url:"${pageContext.request.contextPath}/user/ajax/editPass.do",
        	success:function(data){
        		if(data=="SUCCESS"){
        		location.href="${pageContext.request.contextPath}/user/toLogin.do"
        		}else{
        			 $("#errorInfo").text("旧密码不正确")
        		}
        	},
        	error:function(){
        		location.href="${pageContext.request.contextPath}/user/toLogin.do"
        	}
        })
    });
</script>