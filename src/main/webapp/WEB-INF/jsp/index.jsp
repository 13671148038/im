<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
      <link rel="stylesheet" href='${pageContext.request.contextPath}/css/bootstrap.min.css'>
      <link rel="stylesheet" href='${pageContext.request.contextPath}/css/easyui.css'>
      <link rel="stylesheet" href='${pageContext.request.contextPath}/css/detail.css'>
      <link rel="stylesheet" href='${pageContext.request.contextPath}/css/loading.css'>
      <link rel="stylesheet" href='${pageContext.request.contextPath}/css/iconfont.css'>
      <link rel="stylesheet" href='${pageContext.request.contextPath}/css/user_user.css'>
      <link rel="stylesheet" href='${pageContext.request.contextPath}/css/icon.css'>
      <link rel="stylesheet" href='${pageContext.request.contextPath}/css/modal.css'>
      <link rel="stylesheet" href='${pageContext.request.contextPath}/css/kkpager_orange.css'>
      <link rel="stylesheet" type="text/css" href='${pageContext.request.contextPath}/css/demo.css'>
      <script src='${pageContext.request.contextPath}/js/jquery.min.js'></script>
      <script src='${pageContext.request.contextPath}/js/detail.js'></script>
      <script src='${pageContext.request.contextPath}/js/kkpager.min.js'></script>
      <script src='${pageContext.request.contextPath}/js/jquery.easyui.min.js'></script>
      <script src='${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js'></script>
  </head>
  <body>
<div style="margin: 0 auto; width: 1400px">
    <div class="bky-left" style="height: 100%;position: absolute;width: 150px;float: left;">
      <div class="admin">
        <span class="user-img"><img src="/assets/images/user.png" alt=""></span>
        <%-- <span class="user-name">${userName}</span> --%>
      </div>
      <div class="bky-panel-list bky-list-default"><!--这个表示第五个整块儿的-->
         <!--    <div class="bky-panel-title bky-panelFive bky-panelMore">第一部分，被点击部分
                系统管理
            </div> -->
            <div data-url="${pageContext.request.contextPath}/task/toTaskManage.do" class="bky-panel-body initMenu">
                    任务管理
                </div>
              <div class="bky-panel-body" data-url="${pageContext.request.contextPath}/phoneuser/toPhoneUserManage.do">
                   终端用户管理
                </div>
                <div data-url="${pageContext.request.contextPath}/taskSubmitRecord/toTaskSubmitRecordList.do" class="bky-panel-body">
                    任务提交记录
                </div>
                <div data-url="${pageContext.request.contextPath}/word/toWordList.do" class="bky-panel-body">
                    文档管理
                </div>
           <%--  <div class="bky-panel-content"><!--第五部分，折叠部分-->
                <div class="bky-panel-body" data-url="${pageContext.request.contextPath}/user/toUserManage.do">
                    用户管理
                     </div>  
                <div data-url="${pageContext.request.contextPath}/task/toTaskManage.do" class="bky-panel-body">
                    任务管理
                </div>
              <div class="bky-panel-body" data-url="${pageContext.request.contextPath}/phoneuser/toPhoneUserManage.do">
                   手机用户管理
                </div>
                <div data-url="${pageContext.request.contextPath}/taskSubmitRecord/toTaskSubmitRecordList.do" class="bky-panel-body">
                    任务提交记录
                </div>
                <div data-url="${pageContext.request.contextPath}/word/toWordList.do" class="bky-panel-body">
                    文档管理
                </div>
            </div> --%>
    </div>
    </div>
    <div class="right" style="width:1250px;margin-left:150px;">
      <div class="header" style="background-color: #e9edf5">
        <div class="logo">
            日常维护保障系统
        </div>
        <div>
         <span>${userName}</span>&nbsp;&nbsp;
          <span style="cursor:pointer" onclick="editPass()" title="退出">修改密码</span>
          &nbsp;&nbsp;
          <span style="cursor:pointer" onclick="(function(){location.href='${pageContext.request.contextPath}/user/logout.do'})()" title="退出" class="exit">退出</span>
        </div>
      </div>
        <div style="width: 1200px;margin: 0 auto;" class="right0-content" id="pagecontainer">

        </div>
    </div>

    <div class="pop" style="display: none;">
      <div class="core">
          <div id="coreDiv" style="height: 100%;width: 100%;padding-left: 10px;padding-right: 10px;"></div>
      </div>
    </div>
    <script>
     function openDialogFold(url,width,height,paddingTop){
         $('#pagecontainer').innerHTML = ''
         $(".pop").css("display","block")
         if(paddingTop){
             $(".pop").css("padding-top",paddingTop+"px")
         }
         $(".core").css("width",width+"px")
         $(".core").css("height",height+"px")
         $('#coreDiv').load(url);
    }
     //关闭弹出框
     function shutDialog() {
      $(".pop").hide();
     }
     function toLogin(){
    	 location.href="${pageContext.request.contextPath}/user/toLogin.do"
     }
     function editPass(){
    	 openDialogFold("${pageContext.request.contextPath}/user/toEditPass.do",450,300)
     }
    $('#pagecontainer').load("${pageContext.request.contextPath}/task/toTaskManage.do");
    </script>
</div>
  </body>
</html>