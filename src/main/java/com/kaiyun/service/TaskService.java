package com.kaiyun.service;

import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.kaiyun.pojo.Task;
import com.kaiyun.pojo.TaskResult;

public interface TaskService {

	void analysis(InputStream inputStream,String biaoshi) throws Exception;

	List<String> getTaskType();

	List<List<String>> getTaskByType(String type,String wordId);

	String getTaskRemarkById(String id);

	void updateStartTime(String startTime, String taskType) throws ParseException ;

	Map<String,Object> getAllTaskByType();

	Map<String, Object> getCheckResult(Map<String, Object> condition);

	void submitTask(Map<String, String> contentMap);

	List<Task> getAllTask();

	void submitTasks(String tasks);

	List<TaskResult> getNewResult();

	String updateShareTime();

	String getMaxShareTime();

	void resetTime(Map<String, String> content) throws ParseException ;

	XWPFDocument getImportResultData(String wordId);

}
