package com.kaiyun.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kaiyun.pojo.PhoneUser;
import com.kaiyun.until.PageUntil;

public interface PhoneUserService {

	PageUntil<PhoneUser> getPageDate(Map<String, Object> condition);

	String addUser(PhoneUser phoneUser);

	void deleteUser(String userName);

	String login(PhoneUser phoneUser, HttpServletRequest request);

	List<PhoneUser> phoneGetPageData();

	Map<String, Integer> getVersion();

	void updateVersion(String versionType);

	void updateUser(PhoneUser phoneUser);

	PhoneUser getByUserName(String userName);

}
