package com.kaiyun.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kaiyun.pojo.TaskSubmitRecord;
import com.kaiyun.pojo.User;
import com.kaiyun.service.TaskSubmitRecordService;
import com.kaiyun.until.PageUntil;

@Controller
@RequestMapping("taskSubmitRecord")
public class TaskSubmitRecordController {
	
	@Autowired
	private TaskSubmitRecordService taskSubmitRecordService;
	
	/**
	 * 任务提交记录跳转
	 * @param mv
	 * @return
	 */
	@RequestMapping("toTaskSubmitRecordList")
	public ModelAndView toTaskSubmitRecordList(ModelAndView mv){
		mv.setViewName("task/taskSubmitTRecordList");
		return mv;
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("ajax/getPageDate")
	@ResponseBody
	public PageUntil<TaskSubmitRecord> getPageDate(Integer page,Integer rows){
		PageUntil<TaskSubmitRecord> dataPage = new PageUntil<>();
		try {
			Map<String, Object> condition = new HashMap<>();
			condition.put("currentPage", page);
			condition.put("pageSize", rows);
			dataPage = taskSubmitRecordService.getPageDate(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataPage;
	}
	/**
	 * 删除
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("ajax/deleteRecord")
	@ResponseBody
	public String deleteRecord(Integer id){
		String result ="SUCCESS";
		try {
			taskSubmitRecordService.deleteRecord(id);
		} catch (Exception e) {
			e.printStackTrace();
			result ="ERROR";
		}
		return result;
	}

}
