package com.kaiyun.service;

import java.util.Map;

import com.kaiyun.pojo.TaskSubmitRecord;
import com.kaiyun.until.PageUntil;

public interface TaskSubmitRecordService {

	PageUntil<TaskSubmitRecord> getPageDate(Map<String, Object> condition);

	void deleteRecord(Integer id);

}
