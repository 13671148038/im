package com.kaiyun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaiyun.pojo.Task;
import com.kaiyun.pojo.TaskResult;
import com.kaiyun.service.TaskService;

@Controller
@RequestMapping("app/task")
public class AppTaskController {
	
	@Autowired
	private TaskService taskService;
	
	/**
	 * 获取所有任务
	 * @return
	 */
	@RequestMapping("getAllTaskByType")
	@ResponseBody
	public Map<String,Object> getAllTaskByType(){
		Map<String,Object> result = null;
		try {
		    result = taskService.getAllTaskByType();
		} catch (Exception e) {
			e.printStackTrace();
			result=new HashMap<>();
		}
		return result;
	}
	
	/**
	 * 获取所有任务
	 * @return
	 */
	@RequestMapping("getAllTask")
	@ResponseBody
	public List<Task> getAllTask(){
		List<Task> result = null;
		try {
			result = taskService.getAllTask();
		} catch (Exception e) {
			result = new ArrayList<>();
		}
		return result;
	}
	
	/**
	 * app  提交任务一条一条
	 * @param taskId   任务id
	 * @param rexult   提交的结果
	 * @param userName 提交人
	 * @return
	 */
	@RequestMapping("submitTask")
	@ResponseBody
	public String submitTask(String taskId,String checkResult,String userName){
		String result = "SUCCESS";
		Map<String,String> contentMap = new HashMap<String, String>();
				contentMap.put("taskId", taskId);
				contentMap.put("checkResult", checkResult);
				contentMap.put("userName", userName);
				try {
					taskService.submitTask(contentMap);
				} catch (Exception e) {
					e.printStackTrace();
					 result = "ERROR";
				}
		return result;
	}
	
	/**
	 * 提交任务  批量提交
	 * @param tasks
	 * @return
	 */
	@RequestMapping("submitTasks")
	@ResponseBody
	public String submitTasks(String tasks){
		String result = "SUCCESS";
				try {
						taskService.submitTasks(tasks);
				} catch (Exception e) {
					e.printStackTrace();
					 result = "ERROR";
				}
		return result;
	}
	
	/**
	 * 获取检结果
	 * @return
	 */
	@RequestMapping("getNewResult")
	@ResponseBody
	public List<TaskResult> getNewResult(){
		List<TaskResult> list = null;
		try {
			list = taskService.getNewResult();
		} catch (Exception e) {
			e.printStackTrace();
			list=new ArrayList<>();
		}
		
		return list;
	}

}
