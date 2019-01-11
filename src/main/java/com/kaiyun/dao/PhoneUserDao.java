package com.kaiyun.dao;

import java.util.List;
import java.util.Map;

import com.kaiyun.pojo.PhoneUser;
import com.kaiyun.pojo.User;

public interface PhoneUserDao {

	List<PhoneUser> getPageDate(Map<String, Object> condition);

	PhoneUser getUserByUserName(String userName);

	void insert(PhoneUser phoneUser);

	void deleteUser(String userName);

	PhoneUser getUserByUserNameAndPassWord(PhoneUser phoneUser);

	List<PhoneUser> phoneGetPageData();

	void updateUser(PhoneUser phoneUser);

}
