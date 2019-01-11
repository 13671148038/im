package com.kaiyun.controller;

import com.kaiyun.service.TaskService;
import com.kaiyun.until.FileUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("task")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("toTaskManage")
	public ModelAndView toTaskManage(ModelAndView mv){
		mv.setViewName("task/taskmanage");
		return mv;
	}
	
	/**
	 * 文件上传
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("ajax/importTask")
	@ResponseBody
	public String importTask(MultipartFile file,HttpServletRequest request,String biaoshi){
		String result = "SUCCESS";
		try {
			String originalFilename = file.getOriginalFilename();
			String realPath = request.getSession().getServletContext().getRealPath("taskdata");
			String filePath = realPath+"/"+originalFilename;
			File f = new File(filePath);
			InputStream inputStream = file.getInputStream();
			file.transferTo(f);
			taskService.analysis(inputStream,biaoshi);
		} catch (Exception e) {
			e.printStackTrace();
			 result = "ERROR";
		}
		return result;
	}
	/**
	 * 获取任务的全部类型
	 * @return
	 */
	@RequestMapping("ajax/getTaskType")
	@ResponseBody
	public List<String> getTaskType(){
		List<String> list = null;
		try {
			list =taskService.getTaskType();
		} catch (Exception e) {
			e.printStackTrace();
			list=new ArrayList<>();
		}
		return list;
	}
	/**
	 * 根据任务类型查询任务
	 * @param type
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("ajax/getTaskByType")
	@ResponseBody
	public List<List<String>> getTaskByType(String type,String wordId) throws UnsupportedEncodingException{
		List<List<String>> result = taskService.getTaskByType(type,wordId);
		return result;
	}
	/**
	 * 根据id查询任务的备注信息
	 * @param id
	 * @return
	 */
	@RequestMapping("ajax/getTaskRemarkById")
	public void getTaskRemarkById(String id,HttpServletResponse response){
		String remark = taskService.getTaskRemarkById(id);
		 response.setContentType("text/html;charset=UTF-8");
		 try {
			response.getWriter().write(remark);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加或者更新选择的任务时间
	 * @param startTime
	 * @param taskType
	 * @return
	 */
	@RequestMapping("ajax/updateStartTime")
	@ResponseBody
	public String updateStartTime(String startTime,String taskType){
		String result = "SUCCESS";
		try {
			taskService.updateStartTime(startTime,taskType);
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR";
		}
		return result;
	}
	
	/**
	 * 检查结果分页查询
	 * @param taskIds
	 * @param type
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("ajax/getCheckResult")
	@ResponseBody
	public Map<String,Object> getCheckResult(String taskIds,String type,Integer pageNumber,Integer pageSize,String wordId){
		Map<String,Object> condition = new HashMap<>();
		condition.put("taskIds",taskIds);
		condition.put("type",type);
		condition.put("pageNumber",pageNumber);
		condition.put("pageSize",pageSize);
		condition.put("wordId",wordId);
		Map<String,Object> result = null;
		try {
			result = taskService.getCheckResult(condition);
		} catch (Exception e) {
			e.printStackTrace();
			result=new HashMap<>();
		}
		return result;
	}
	/**
	 * 更新同步日期
	 * @return
	 */
	@RequestMapping("ajax/updateShareTime")
	@ResponseBody
	public String updateShareTime(){
		String time = taskService.updateShareTime();
		return time;
	}
	/**
	 * 获取最新的更新时间
	 * @return
	 */
	@RequestMapping("ajax/getMaxShareTime")
	@ResponseBody
	public String getMaxShareTime(){
		String time = taskService.getMaxShareTime();
		if(time==null){
			time="";
		}
		return time;
	}
	
	/**
	 * 更改时间
	 * @param type
	 * @param resetBeforeTime
	 * @param resetTime
	 * @param taskType
	 * @return
	 */
	@RequestMapping("ajax/resetTime")
	@ResponseBody
	public String resetTime(String type,String resetBeforeTime,String resetTime,String taskType){
		String result = "SUCCESS";
		Map<String,String> content = new HashMap<>();
		content.put("type",type);
		content.put("resetBeforeTime",resetBeforeTime);
		content.put("resetTime",resetTime);
		content.put("taskType",taskType);
		try {
			taskService.resetTime(content);
		} catch (Exception e) {
			e.printStackTrace();
			result="ERROR";
		}
		return result;
	}
	
	/**
	 * 任务结果导出
	 * @param wordId
	 */
	@RequestMapping("importResult")
	public void importResult(String wordId,HttpServletResponse response,HttpServletRequest request){
		try {
			String filename = "taskResult.doc";
			String agent = request.getHeader("User-Agent");
			String filenameEncoder = FileUtil.encodeDownloadFilename(filename, agent);
			//要下载的这个文件的类型-----客户端通过文件的MIME类型去区分类型
			response.setContentType(request.getSession().getServletContext().getMimeType(filename));
			//告诉客户端该文件不是直接解析 而是以附件形式打开(下载)----filename="+filename 客户端默认对名字进行解码
			response.setHeader("Content-Disposition", "attachment;filename="+filenameEncoder);
			//获取模板存放的路径
//			String templatePath = request.getSession().getServletContext().getRealPath("template");
//			freemarkerConfiguration.setDirectoryForTemplateLoading(new File(templatePath));
//			Template template = freemarkerConfiguration.getTemplate("template.ftl", "UTF-8");
//			template.process(null, response.getWriter());
			 
			XWPFDocument document = taskService.getImportResultData(wordId);
//			FileOutputStream out = new FileOutputStream(new File("C:/Users/MyPC/Desktop/taskResult.doc"));
//			document.write(out);
			document.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
