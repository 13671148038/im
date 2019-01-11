<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/animate.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cloud-admin.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/uniform.default.min.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<div style="width: 1580px;margin: 0 auto;">
			<section id="login" class="visible">
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div class="login-box-plain">
								<h6 class="bigintro" style="font-size: 30px">日常维护保障系统</h6>
								<div class="divide-40"></div>
								<form role="form">
								  <div class="form-group">
									<label for="exampleInputEmail1">用户名</label>
									<i class="fa fa-user"></i>
									<input type="text" class="form-control" id="userName" >
								  </div>
								  <div class="form-group"> 
									<label for="exampleInputPassword1">密码</label>
									<i class="fa fa-lock"></i>
									<input type="password" class="form-control" id="passWord" >
								  </div>
								  <div id="reminder" style="text-align: center; color: red;"></div>
								  <div class="form-actions">
									<button id="loginButton" type="button" class="btn btn-danger">登录</button>
								  </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</section>
</div>
<script type="text/javascript">
		$("#loginButton").click(function(){
			var userName = $.trim($("#userName").val())
			var passWord = $.trim($("#passWord").val())
			if(!userName){
				$("#reminder").text("用户名不能为空")
				return
			}
			if(!passWord){
				$("#reminder").text("密码不能为空")
				return
			}
			$.ajax({
				type:"post",
				data:{
					userName:userName,
					passWord:passWord
				},
				dataType:"text",
				url:"${pageContext.request.contextPath}/user/login.do",
				success:function(data){
					if(data=="ERROR"){
						$("#reminder").text("用户名或密码错误")
					}else{
						location.href="${pageContext.request.contextPath}/user/toIndex.do"
					}
				}
			})
		})
</script>
</body>
</html>