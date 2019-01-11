package com.kaiyun.dao;

import java.util.List;
import java.util.Map;

import com.kaiyun.pojo.TaskSubmitRecord;

public interface TaskSubmitRecordDao {

	void add(TaskSubmitRecord taskSubmitRecord);

	List<TaskSubmitRecord> getPageDate(Map<String, Object> condition);

	void deleteRecord(Integer id);

	void deleteByTaskId(String taskId);

}
