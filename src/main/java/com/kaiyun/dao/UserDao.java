package com.kaiyun.dao;

import java.util.List;
import java.util.Map;

import com.kaiyun.pojo.User;

public interface UserDao {

	User getUserByUserNameAndPassWord(User user);

	User getUserByUserName(String userName);

	void insert(User user);

	List<User> getPageDate(Map<String, Object> condition);

	void deleteUser(String userName);

	void update(User u);

}
