package com.kaiyun.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kaiyun.pojo.User;
import com.kaiyun.until.PageUntil;

public interface UserService {

	String login(User user,HttpServletRequest request);

	String addUser(User user);

	PageUntil<User> getPageDate(Map<String, Object> condition);

	void deleteUser(String userName);

	void editPass(User u);

}
