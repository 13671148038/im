package com.kaiyun.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kaiyun.dao.PhoneUserDao;
import com.kaiyun.dao.VersionDao;
import com.kaiyun.pojo.PhoneUser;
import com.kaiyun.pojo.User;
import com.kaiyun.service.PhoneUserService;
import com.kaiyun.until.DateUtil;
import com.kaiyun.until.MD5Util;
import com.kaiyun.until.PageUntil;

@Service
public class PhoneUserServiceImpl implements PhoneUserService {
	
	@Autowired
	private PhoneUserDao phoneUserDao;
	@Autowired
	private VersionDao versionDao;
	
	
	@Value("${userVersion}")
	private String userVersion;

	/**
	 * 分页查询
	 */
	@Override
	public PageUntil<PhoneUser> getPageDate(Map<String, Object> condition) {
		Integer currentPage = (Integer)condition.get("currentPage");
		Integer pageSize = (Integer)condition.get("pageSize");
		Page<PhoneUser> page = PageHelper.startPage(currentPage,pageSize);
		List<PhoneUser> list = phoneUserDao.getPageDate(condition);
		PageUntil<PhoneUser> pu = new PageUntil<>();
		pu.setCurrentPage(currentPage);
		pu.setPageCount(pageSize);
		pu.setRows(list);
		pu.setTotal(page.getTotal());
		return pu;
	}

	@Override
	public String addUser(PhoneUser phoneUser) {
		String result = "SUCCESS";
		String userName = phoneUser.getUserName();
		PhoneUser u = phoneUserDao.getUserByUserName(userName);
		if(u==null){
			phoneUser.setPassWord(MD5Util.encode(phoneUser.getPassWord()));
			phoneUser.setCreateTime(DateUtil.getTime());
			phoneUserDao.insert(phoneUser);
			//更新用户信息版本
			updateVersion(userVersion);
		}else{
			result="ERROR";
		}
		return result;
	}

	/**
	 * 删除用户信息
	 */
	@Override
	public void deleteUser(String userName) {
		phoneUserDao.deleteUser(userName);
		//更新用户信息版本
		updateVersion(userVersion);
	}

	/**
	 * 手机用户登录
	 */
	@Override
	public String login(PhoneUser phoneUser, HttpServletRequest request) {
		String result ="ERROR";
		phoneUser.setPassWord(MD5Util.encode(phoneUser.getPassWord()));
		PhoneUser u = phoneUserDao.getUserByUserNameAndPassWord(phoneUser);
		if(u!=null){
			result="SUCCESS";
			//request.getSession().setAttribute("user", u);
		}
		return result;
	}

	/**
	 * 手机获取用户信息
	 */
	@Override
	public List<PhoneUser> phoneGetPageData() {
		List<PhoneUser> list = phoneUserDao.phoneGetPageData();
		return list;
	}

	
	@Value("${versionId}")
	private String versionId;  //版本id
	/**
	 * 更新版本信息
	 */
	@Override
	public void updateVersion(String versionType) {
		Map<String, String> versionMap = new HashMap<String, String>();
		if(StringUtils.isNotBlank(versionType)){
			versionMap.put(versionType, versionType);
			versionMap.put("id",versionId);
			versionDao.updateVersion(versionMap);
		}
	}
	/**
	 * 手机端获取版本信息
	 */
	@Override
	public Map<String, Integer> getVersion() {
		Map<String, Integer> map = versionDao.getVersion(versionId);
		return map;
	}

	/**
	 * 更新用户信息
	 */
	@Override
	public void updateUser(PhoneUser phoneUser) {
		String passWord = phoneUser.getPassWord();
		if(StringUtils.isNotBlank(passWord)){
			phoneUser.setPassWord(MD5Util.encode(passWord));
		}else{
			phoneUser.setPassWord(null);
		}
		phoneUserDao.updateUser(phoneUser);
		updateVersion(userVersion);
	}

	/**
	 * 根据用户名查询用户
	 */
	@Override
	public PhoneUser getByUserName(String userName) {
		PhoneUser phoneUser = phoneUserDao.getUserByUserName(userName);
		return phoneUser;
	}

}
