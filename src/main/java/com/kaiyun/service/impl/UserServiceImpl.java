package com.kaiyun.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kaiyun.dao.UserDao;
import com.kaiyun.pojo.User;
import com.kaiyun.service.UserService;
import com.kaiyun.until.DateUtil;
import com.kaiyun.until.MD5Util;
import com.kaiyun.until.PageUntil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	/**
	 * 登录校验
	 */
	@Override
	public String login(User user,HttpServletRequest request) {
		String result ="ERROR";
		user.setPassWord(MD5Util.encode(user.getPassWord()));
		User u = userDao.getUserByUserNameAndPassWord(user);
		if(u!=null){
			result="SUCCESS";
			request.getSession().setAttribute("user", u);
		}
		return result;
	}

	/**
	 * 添加用户
	 */
	@Override
	public String addUser(User user) {
		String result = "SUCCESS";
		String userName = user.getUserName();
		User u = userDao.getUserByUserName(userName);
		if(u==null){
			user.setPassWord(MD5Util.encode(user.getPassWord()));
			user.setCreateTime(DateUtil.getTime());
			userDao.insert(user);
		}else{
			result="ERROR";
		}
		return result;
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageUntil<User> getPageDate(Map<String, Object> condition) {
		Integer currentPage = (Integer)condition.get("currentPage");
		Integer pageSize = (Integer)condition.get("pageSize");
		Page<User> page = PageHelper.startPage(currentPage,pageSize);
		List<User> list = userDao.getPageDate(condition);
		PageUntil<User> pu = new PageUntil<>();
		pu.setCurrentPage(currentPage);
		pu.setPageCount(pageSize);
		pu.setRows(list);
		pu.setTotal(page.getTotal());
		return pu;
	}

	/**
	 * 删除用户
	 */
	@Override
	public void deleteUser(String userName) {
		userDao.deleteUser(userName);
	}

	/**
	 * 修改密码
	 */
	@Override
	public void editPass(User u) {
		u.setPassWord(MD5Util.encode(u.getPassWord()));
		userDao.update(u);
	}

}
