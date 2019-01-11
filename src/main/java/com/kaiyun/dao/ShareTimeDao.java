package com.kaiyun.dao;

import java.util.Map;

public interface ShareTimeDao {

	void addShareTime(Map<String, Object> map);

	String getMaxShareTime();

}
