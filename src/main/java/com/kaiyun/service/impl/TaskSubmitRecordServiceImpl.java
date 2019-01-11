package com.kaiyun.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kaiyun.dao.TaskDao;
import com.kaiyun.dao.TaskSubmitRecordDao;
import com.kaiyun.pojo.Task;
import com.kaiyun.pojo.TaskSubmitRecord;
import com.kaiyun.pojo.User;
import com.kaiyun.service.TaskSubmitRecordService;
import com.kaiyun.until.PageUntil;

@Service
public class TaskSubmitRecordServiceImpl implements TaskSubmitRecordService {
	
	@Autowired
	private TaskSubmitRecordDao taskSubmitRecordDao;
	@Autowired
	private TaskDao taskDao;

	/**
	 * 分页查询提交记录
	 */
	@Override
	public PageUntil<TaskSubmitRecord> getPageDate(Map<String, Object> condition) {
		Integer currentPage = (Integer)condition.get("currentPage");
		Integer pageSize = (Integer)condition.get("pageSize");
		Page<TaskSubmitRecord> page = PageHelper.startPage(currentPage,pageSize);
		List<TaskSubmitRecord> list = taskSubmitRecordDao.getPageDate(condition);
		for (TaskSubmitRecord taskSubmitRecord : list) {
			String taskId = taskSubmitRecord.getTaskId();
			//根据taskId查询任务名称
			Task task = taskDao.getTaskById(taskId);
			StringBuffer sb = new StringBuffer();
			String columnTwo = task.getColumnTwo();
			if(StringUtils.isNotBlank(columnTwo)){
				sb.append(columnTwo+":th:");
			}
			String columnThree = task.getColumnThree();
			if(StringUtils.isNotBlank(columnThree)){
				sb.append(columnThree+":th:");
			}
			String columnFour = task.getColumnFour();
			if(StringUtils.isNotBlank(columnFour)){
				sb.append(columnFour+":th:");
			}
			String taskName = "";
			if (sb.length()>0) {
				sb.delete(sb.length()-4, sb.length());
				taskName = sb.toString();
				if(taskName.contains(":th:")){
					String[] split = taskName.split(":th:");
					taskName="";
					for (int i = 0; i < split.length; i++) {
						if(i==split.length-1){
							break;
						}
						taskName+=split[i]+"  ";
					}
					taskName.substring(0, taskName.length()-1);
				}
			}
			taskSubmitRecord.setTaskId(taskName);
		}
		PageUntil<TaskSubmitRecord> pu = new PageUntil<>();
		pu.setCurrentPage(currentPage);
		pu.setPageCount(pageSize);
		pu.setRows(list);
		pu.setTotal(page.getTotal());
		return pu;
	}

	/**
	 * 根据id删除记录
	 */
	@Override
	public void deleteRecord(Integer id) {
		taskSubmitRecordDao.deleteRecord(id);
	}

}
