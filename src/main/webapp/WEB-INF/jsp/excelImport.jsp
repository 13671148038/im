<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cloud-admin.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dropzone.min.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/dropzone.min.js"></script>

<body>
	<div class="box-body" style="height: 100%">
		<form action="${pageContext.request.contextPath}/excel/uploadFile.do" method="post" enctype="multipart/form-data" class="dropzone" style="height: 100%" id="my-awesome-dropzone">
			<div class="fallback">
				<input name="file" type="file" />
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	var result = '${result}'
	console.log(result)
	if (result == "SUCCESS") {
		alert("上传成功")
	} else if (result == "ERROR") {
		alert("上传失败")
	}
</script>
</html>