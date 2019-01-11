package com.kaiyun.dao;

import java.util.Map;

public interface VersionDao {

	Map<String, Integer> getVersion(String versionId);

	void updateVersion(Map<String, String> versionMap);

}
