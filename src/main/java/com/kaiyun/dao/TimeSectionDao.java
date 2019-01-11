package com.kaiyun.dao;

import java.util.List;
import java.util.Map;

public interface TimeSectionDao {

	void deleteAll();

	Map<String, String> getNowDa(String day, String type);

	void update(Map<String, String> map);

	void add(Map<String, String> m);

	String getMaxTimeAndXiaoYuNow(String day, String taskType,String wordId);

	List<String> getPageDate(String type, String nowTime, String wordId);

	List<String> getAll(String type, String wordId);

	void resetTime(Map<String, String> content);

	List<String> selectResetTimeAfterNum(Map<String, String> content);

	void resetTimeById(Map<String, String> content);

	void deleteByWordId(String wordId);

	List<String> getAlltimeXiaoYuNowByWordIdAndType(Map<String, Object> condition);

}
