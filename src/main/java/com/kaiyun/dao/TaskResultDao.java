package com.kaiyun.dao;

import java.util.List;
import java.util.Map;

import com.kaiyun.pojo.TaskResult;

public interface TaskResultDao {

	/**
	 * 放回检查结果
	 * @param condition
	 * @return
	 */
	String getByTaskIdAndCheckTime(Map<String, Object> condition);

	void add(TaskResult tr);

	/**
	 * 返回全部字段
	 * @param condition
	 * @return
	 */
	TaskResult getByTaskIdAndCheckTime2(Map<String, Object> condition);

	void update(TaskResult result);

	List<TaskResult> getNewResult(String maxTimeAndXiaoYuNow, String type,String workId);

	void deleteByWordId(String wordId);

}
