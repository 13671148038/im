package com.kaiyun.dao;

import java.util.List;
import java.util.Map;

import com.kaiyun.pojo.Task;

public interface TaskDao {

	void add(Map<String, Object> task);

	List<String> getTaskType();

	String getTaskRemarkById(String id);

	List<Task> getAllTask();

	Task getTaskById(String taskId);

	List<Task> getTaskByType(String type, String wordId);

	void deleteByWordId(String wordId);

	List<String> getIdByWordId(String wordId);

	List<String> getTaskTypeByWordId(String wordId);

}
