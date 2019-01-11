<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <style type="text/css">
 .dataDiv table tr td{
  border:1px solid #ccc
 }
 .dataDiv td{
  text-align: center;
 }
 .dataDiv tr{
  height: 30px
 }
 .remarkdiv table tr td{
  border:1px solid #ccc
 }
 .remarkdiv td{
  text-align: center;
 }
 .remarkdiv tr{
  height: 200px
 }
 </style>
<div style="text-align: center;height: 50px;line-height: 50px;font-size: 20px;">任务管理</div>
<div style="margin-top: 10px;padding:5px 0;">
	<a id="import" href="javascript:void(0)">任务导入</a>
	<select style="width: 100px" id="mm" onchange="getTaskByType(this.value)">
	</select>
	<!-- <button onclick="importResult()" type="button">结果导出</button> -->
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<span><a id="shareToMobile" href="javascript:void(0)">同步至终端</a>&nbsp;&nbsp;(上次同步时间:<span id="tbTime"></span>)</span>
</div>
<div id="showDiv" style="height: 800px;overflow: auto;">
	<div class="dataDiv">
		<div style="class="lefttable">
			<table>
				<tbody class="lefttitle">
					<tr>
						<td rowspan="3" style="width: 40px">序号</td>
						<td rowspan="2" id="jcnr" colspan="2" style="width: 300px"></td>
						<td rowspan="2" style="width: 150px;">技术要求</td>
						<td colspan="7" style="width: 630px">检查结果</td>
					</tr>
					<tr>
						<td colspan="7">
						    <a style="cursor:pointer" id="uppage" onclick="upPage()" href="javascript:void(0)">上一页</a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a style="cursor:pointer" style="cursor:pointer" id="nextpage" onclick="nextPage()" href="javascript:void(0)">下一页</a>
						</td>
					</tr>
					<tr id="checkResultDate">
						<td colspan="3">日期</td>
						<td style="width: 89px"></td>
						<td style="width: 89px"></td>
						<td style="width: 89px"></td>
						<td style="width: 89px"></td>
						<td style="width: 89px"></td>
						<td style="width: 89px"></td>
						<td style="width: 89px"></td>
					</tr>
				</tbody>
				<tbody class="dataTask" id="dataTask">
				</tbody>
			</table>
		</div>
	</div>
	<div class="remarkdiv">
		<table id="remarktable">
		</table>
	</div>
</div>
<script type="text/javascript">
var ph = $(".bky-left").height()
$("#showDiv").height(ph-150)
var num = 1;
//查询最新的同步时间
 			$.ajax({
            	 dataType:'text',
            	 type:'get',
            	 url:'${pageContext.request.contextPath}/task/ajax/getMaxShareTime.do',
            	 success:function(data){
            		 $("#tbTime").text(data)
            	 },
            	 error:function(){
            		 toLogin()
            	 }
             })
//读取任务种类
$.ajax({
	dataType:"json",
	type:"get",
	async:false,
	url:"${pageContext.request.contextPath}/task/ajax/getTaskType.do",
	success:function(data){
		$("#mm").empty()
		$.each(data,function(index,value){
			$("#mm").append('<option>'+value+'</option>')
		})
	},
	error:function(){
   	 toLogin()
    }
})
var t = $("#mm").val()
getTaskByType(t)
var pageNumber = null;
var totalPage = null;
var taskIds=null;
//根据任务类型查询任务
 function getTaskByType(type){
	t=type
	//获取任务
	$("#jcnr").text("("+type+")检查内容")
		$.ajax({
			dataType:"json",
			type:"get",
			url:"${pageContext.request.contextPath}/task/ajax/getTaskByType.do",
			data:{
				type:type
			},
			success:function(data){
				var taskIdArr =[];
				$("#dataTask").empty()
				$("#remarktable").empty()
				$("#checkresult").empty()
				var dataLen = data.length
				
				var firstColumnOne = "";
				var firstColumnOneNum = 0;
				var fbs=1;
				$.each(data,function(index,value){
					
					var taskId = value.pop()
					//备注信息
					if(dataLen==index+1){
						var remark = "";
						$.ajax({
							type:"get",
							async:false,
							dataType:"text",
							data:{
								id:taskId
							},
							url:"${pageContext.request.contextPath}/task/ajax/getTaskRemarkById.do",
							success:function(data){
								remark=data
							}
						})
						$("#remarktable").append(
								'<tr>'+
									'<td style="width: 50px">'+value[0]+'</td>'+
									'<td style="width: 1070px">'+remark+'</td>'+
								'</tr>'		
						)
					}else{
						
						taskIdArr.push(taskId)
						var len = value.length
						
						var sdc = value[0]
						if(sdc!=firstColumnOne){
							firstColumnOne=sdc
							firstColumnOneNum = 0;
							if(firstColumnOneNum==0){
								$.each(data,function(index2,value2){
									if(firstColumnOne==value2[0]){
									firstColumnOneNum++
									}
								})
								fbs=1
							}
						}
						
						
						if(len==4){
							if(firstColumnOneNum>1){
								if(firstColumnOne==value[0]&&fbs==1){
									$("#dataTask").append(
									'<tr id="'+taskId+'">'+
										'<td rowspan="'+firstColumnOneNum+'">'+value[0]+'</td>'+
										'<td style="width: 148px" rowspan="'+firstColumnOneNum+'">'+value[1]+'</td>'+
										'<td>'+value[2]+'</td>'+
										'<td>'+value[3]+'</td>'+
										'<td></td>'+
										'<td></td>'+
										'<td></td>'+
										'<td></td>'+
										'<td></td>'+
										'<td></td>'+
										'<td></td>'+
									'</tr>'		
									)
									fbs=2
								}else{
									$("#dataTask").append(
									'<tr id="'+taskId+'">'+
										'<td>'+value[2]+'</td>'+
										'<td>'+value[3]+'</td>'+
										'<td></td>'+
										'<td></td>'+
										'<td></td>'+
										'<td></td>'+
										'<td></td>'+
										'<td></td>'+
										'<td></td>'+
									'</tr>'	
									)
								}
							}else{
								$("#dataTask").append(
								'<tr id="'+taskId+'">'+
									'<td>'+value[0]+'</td>'+
									'<td>'+value[1]+'</td>'+
									'<td>'+value[2]+'</td>'+
									'<td>'+value[3]+'</td>'+
									'<td></td>'+
									'<td></td>'+
									'<td></td>'+
									'<td></td>'+
									'<td></td>'+
									'<td></td>'+
									'<td></td>'+
								'</tr>'		
								)
							}
						}else if(len==3){
							if(firstColumnOneNum>1){
								if(firstColumnOne==value[0]&&fbs==1){
									$("#dataTask").append(
										'<tr id="'+taskId+'">'+
											'<td rowspan="'+firstColumnOneNum+'">'+value[0]+'</td>'+
											'<td colspan="2">'+value[1]+'</td>'+
											'<td>'+value[2]+'</td>'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
										'</tr>'		
									)
									fbs=2
								}else{
									$("#dataTask").append(
											'<tr id="'+taskId+'">'+
												'<td colspan="2">'+value[1]+'</td>'+
												'<td>'+value[2]+'</td>'+
												'<td></td>'+
												'<td></td>'+
												'<td></td>'+
												'<td></td>'+
												'<td></td>'+
												'<td></td>'+
												'<td></td>'+
											'</tr>'		
									)
								}
							}else{
								$("#dataTask").append(
										'<tr id="'+taskId+'">'+
											'<td>'+value[0]+'</td>'+
											'<td colspan="2">'+value[1]+'</td>'+
											'<td>'+value[2]+'</td>'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
										'</tr>'		
								)
							}
						}else if(len==1){
							$("#dataTask").append(
							'<tr id="'+taskId+'">'+
								'<td colspan="4">'+value[0]+'</td>'+
								'<td></td>'+
								'<td></td>'+
								'<td></td>'+
								'<td></td>'+
								'<td></td>'+
								'<td></td>'+
								'<td></td>'+
							'</tr>'		
							)
						}
					}
				})
				 pageNumber = null;
                 taskIds=null;
				//查询检查结果
				selectCheckResult(taskIdArr,type)
			},error:function(){
	        	 toLogin()
	         }
		})
 }
 
function nextPage(){
     if(pageNumber<totalPage){
         pageNumber++;
     }
	selectCheckResult(taskIds,t);
}
function upPage(){
	if(pageNumber>1){
		pageNumber--;
		selectCheckResult(taskIds,t);
	}
	
}
 //查询检查结果
 function selectCheckResult(taskIdArr,type){
	 if(!taskIdArr){
		 taskIdArr=taskIds;
	 }
	 if(!type){
		 type=t;
	 }
	 taskIds=taskIdArr;
	 var jsonIds = JSON.stringify(taskIds)
	 $.ajax({
		 type:"post",
		 dataType:"json",
		 data:{
			 taskIds:jsonIds,
			 type:type,
			 pageNumber:pageNumber,
			 pageSize:7
		 },
		 url:"${pageContext.request.contextPath}/task/ajax/getCheckResult.do",
		 success:function(data){
			 pageNumber=data.pageNumber;
             totalPage = data.pages;
			 setCheckResult("checkResultDate",data.timeList)//设置检查结果
			 $.each(data.checkResult,function(index,value){
					 setCheckResult(index,value)//设置检查结果
			 })
			 
		 }, error:function(error){
			 toLogin();
		 }
	 })
 }
 
 //设置检查结果
 function setCheckResult(trId,arr){
	 var tds = $("#"+trId+" td");
	 var result = 0;
	 $.each(tds,function(index,value){
		 result=index;
	 })
	 var dateStart = result-6;
	 var jl = 0;
	 var t = 1;
	  $.each(tds,function(index,value){
		 if(index >= dateStart){
			 if(jl< arr.length){
				 if(trId=='checkResultDate'){
					 value.innerHTML='<span style="cursor:pointer" ondblclick="editTime(this)">'+arr[jl]+'</span>'
					
				 }else{
					value.innerHTML=arr[jl]
				 }
			 }else{
				 if(trId=='checkResultDate'&&t==1){
			 			value.innerHTML='<button onclick="selectTime()">选择时间</button>'
				 }else{
			 	value.innerHTML=""
				 }
			 	t=2;
			 }
			 jl++;
		 }
	 })
 }
 
 //修改时间
 function editTime(data){
	 var oldTime = data.innerText;
	 oldTime = oldTime.replace(/-/g,"");
	 oldTime = parseInt(oldTime);
	 var date = new Date();
	 var year = date.getFullYear()+"";
	 var month = date.getMonth()+1;
	 month=(month<10?"0"+month:month)+"";
	 var day = date.getDate();
	 day=(day<10?"0"+day:day);
	 var currentTime = year+month+day;
	 currentTime = parseInt(currentTime);
	 if(oldTime<=currentTime){
		 $.messager.alert("提示","不能修改当天及之前日期");
		 return
	 }
	 //添加修改或者重置时间功能
	 $('#resetTime').datebox('setValue', data.innerText);
	 $('#resetBeforeTime').val(data.innerText);
	 $('#resetTimeDiv').dialog('open');
 }
 
$('#startTime').datebox({
	panelWidth:250,
	formatter: function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	},
	parser: function(s){
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
            return new Date(y,m-1,d);
        } else {
            return new Date();
        }
    },
});
//只能选择当前时间之后的时间
$('#startTime').datebox().datebox('calendar').calendar({
    validator: function(date){
        var now = new Date();
        var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
        //var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate()+10);
        //return d1<=date && date<=d2;
        return d1<=date;
    }
});
$('#resetTime').datebox({
	panelWidth:250,
	formatter: function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	},
	parser: function(s){
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
            return new Date(y,m-1,d);
        } else {
            return new Date();
        }
    },
});
//只能选择当前时间之后的时间
$('#resetTime').datebox().datebox('calendar').calendar({
    validator: function(date){
        var now = new Date();
        var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
        //var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate()+10);
        //return d1<=date && date<=d2;
        return d1<=date;
    }
});

$('#import').linkbutton({
    iconCls: 'icon-add'
});
$("#import").click(function(data){
	$('#dd').dialog('open')
})
$('#shareToMobile').linkbutton({
});
$('#uppage').linkbutton({
});
$('#nextpage').linkbutton({
});
$("#shareToMobile").click(function(data){
	 $.messager.confirm('同步', '确认同步吗?', function(r){
         if (r){
             $.ajax({
            	 dataType:'text',
            	 type:'post',
            	 url:'${pageContext.request.contextPath}/task/ajax/updateShareTime.do',
            	 success:function(data){
            		 $("#tbTime").text(data)
            	 },
            	 error:function(){
            		 toLogin()
            	 }
             })
         }
     });
})
function selectTime(data){
	//获取时间段表中最后一个时间   暂时没有做
	
	$('#xuanzeshijian').dialog('open')
}
$('#file').filebox({
	buttonText: '选择文件',
    buttonAlign: 'left',
})
$('#dd').dialog({
    title: '数据导入',
    width: 400,
    height: 200,
    closed: true,
    modal: true,
    buttons: [{
        text:'Ok',
        iconCls:'icon-ok',
        handler:function(){
        	dataImport(2)
        	num=1;
        }
    },{
        text:'Cancel',
        handler:function(){
        	$('#dd').dialog('close');
        }
    }]
});
$('#updateProgress').dialog({
    title: '数据导入进度',
    width: 400,
    height: 200,
    closed: true,
    modal: true,
    buttons: [{
        text:'Ok',
        iconCls:'icon-ok',
        handler:function(){
        	$('#updateProgress').dialog('close');
        }
    },{
        text:'Cancel',
        handler:function(){
        	$('#updateProgress').dialog('close');
        }
    }]
});
$('#xuanzeshijian').dialog({
    title: '选择时间',
    width: 400,
    height: 200,
    closed: true,
    modal: true,
    buttons: [{
        text:'Ok',
        iconCls:'icon-ok',
        handler:function(){
        	var xuanzeshijian = $("#startTime").val()
        	$('#xuanzeshijian').dialog('close');
        	updateStartTime(xuanzeshijian)
        }
    },{
        text:'Cancel',
        handler:function(){
        	$('#xuanzeshijian').dialog('close');
        }
    }]
});
$('#resetTimeDiv').dialog({
    title: '重置时间',
    width: 400,
    height: 200,
    closed: true,
    modal: true,
});

function updateStartTime(xuanzeshijian){
	var taskType = $("#mm").val()
	if(!xuanzeshijian){
		return
	}
	$.ajax({
		type:"post",
		dataType:"text",
		data:{
			startTime:xuanzeshijian,
			taskType:taskType
		},
		url:"${pageContext.request.contextPath}/task/ajax/updateStartTime.do",
		success:function(data){
			selectCheckResult(taskIds,t)
		},
		error:function(){
       	 toLogin()
        }
	})
}
function dataImport(biaoshi){
	 var from = document.getElementById('updateTaskForm');
     var formFile = new FormData(from);
     var file = formFile.get("file")
     if (!$.trim(file)){
         $.messager.alert("提示","请选择文件")
         return;
     } 
     formFile.append("biaoshi", biaoshi); //加入文件对象
     
     $('#updateProgress').dialog('open'); //开启进度条窗口
     $('#dd').dialog('close');            //关闭文件窗口
     $('#progress').progressbar({
    		value:0
     });
     startProgress()                      //开启进度条
     $.ajax({
    	 type:"post",
    	 dataType:"text",
    	 data:formFile,
    	 url:"${pageContext.request.contextPath}/task/ajax/importTask.do",
    	 cache: false,//上传文件无需缓存
         processData: false,//用于对data参数进行序列化处理 这里必须false
         contentType: false, //必须
         success: function (result) {
        	 if(result=='ERROR'&&num<2){
        		 dataImport(1);
        		 num++;
        	 }else if(result=='ERROR'&&num>=2){
        	 	$.messager.alert("提示","上传失败","error")
        	 }else{
        		 //上万完毕设置进度为100%
        		 $('#progress').progressbar({
        				value:100
        		 });
        	 	$('#updateProgress').dialog('close');
        	 	$.messager.alert("提示","上传成功")
        	 	$('#pagecontainer').load("${pageContext.request.contextPath}/user/toIndex.do");
        	 }
         },
         error:function(){
        	 toLogin()
         }
    	 
     })
}
function startProgress(){
	var value = $('#progress').progressbar('getValue');
	if (value < 89){
	    value += Math.floor(Math.random() * 10);
	    $('#progress').progressbar('setValue', value);
	    setTimeout(arguments.callee, 200);
	}
}
</script>
<div id="updateProgress">
	 <div id="progress" style="margin-left: 38px; margin-top: 40px; width: 300px">
	 </div>
</div>
<div id="dd">
	 <div style="margin-left: 38px; margin-top: 40px;">
		 <form id="updateTaskForm">
		            <input id="file" name="file" type="text" style="width:300px">
		 </form>
	 </div>
</div>
<div id="xuanzeshijian" style="display: none;padding-left:80px;padding-top: 50px">
	<input id="startTime" type="text" style="width: 250px">
</div>
<div id="resetTimeDiv" style="display: none;padding-left:70px;padding-top: 50px">
	<input id="resetTime" type="text" style="width: 250px">
	<input id="resetBeforeTime" type="text" style="width: 250px;display: none;">
	<div style="height: 25px"></div>
	<a id="singleReset">单次修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a id="bathReset">批量修改</a>
</div>
<script>
	$('#singleReset').linkbutton({
	});
	$('#singleReset').click(function(){
		setReSetTime(1);
	});
	$('#bathReset').linkbutton({
	});
	$('#bathReset').click(function(){
		setReSetTime(2);
	});
	
	function setReSetTime(type){
		var resetBeforeTime = $("#resetBeforeTime").val()
		var resetTime = $("#resetTime").val()
		if(!resetTime){
			$.messager.alert("提示","请选择时间",'error')
			return
		}
		$.ajax({
			type:"get",
			dataType:"text",
			data:{
				type:type,
				resetBeforeTime:resetBeforeTime,
				resetTime:resetTime,
				taskType:t
			},
			url:"${pageContext.request.contextPath}/task/ajax/resetTime.do",
			success:function(data){
				if(data=="SUCCESS"){
					 $('#resetTimeDiv').dialog('close');
					//$.messager.alert("提示","修改成功")
					selectCheckResult(taskIds,t);
				}else{
					$.messager.alert("提示","修改失败","error")
				}
			},
			error:function(){
				toLogin()
			}
		})
	}
	//定时刷新检查结果
	setInterval('selectCheckResult()',20000); 
	function importResult(){
		location.href="${pageContext.request.contextPath}/task/importResult.do";
	}
</script>