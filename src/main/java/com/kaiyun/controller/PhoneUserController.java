package com.kaiyun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kaiyun.pojo.PhoneUser;
import com.kaiyun.service.PhoneUserService;
import com.kaiyun.until.PageUntil;

@Controller
@RequestMapping(value={"phoneuser","app/phoneuser"})
public class PhoneUserController {
	
	@Autowired
	private PhoneUserService phoneUserService;
	
	
	@RequestMapping("toPhoneUserManage")
	public ModelAndView toPhoneUserManage(ModelAndView mv){
		mv.setViewName("phoneuser/userList");
		return mv;
	}
	
	@RequestMapping("toAddUser")
	public ModelAndView addUser(ModelAndView mv){
		mv.setViewName("phoneuser/user_add");
		return mv;
	}
	
	@RequestMapping("toEditUser")
	public ModelAndView toEditUser(ModelAndView mv,String userName){
		mv.setViewName("phoneuser/user_edit");
		mv.addObject("userName", userName);
		return mv;
	}
	/**
	 * 根据用户id查询用户信息
	 * @param userName
	 * @return
	 */
	@RequestMapping("ajax/getByUserName")
	@ResponseBody
	public PhoneUser getByUserName(String userName){
		PhoneUser phoneUser = null;
		try {
			phoneUser = phoneUserService.getByUserName(userName);
		} catch (Exception e) {
			e.printStackTrace();
			phoneUser = new PhoneUser();
		}
		return phoneUser;
	}
	
	/**
	 * 登录
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public String login(PhoneUser phoneUser,HttpServletRequest request){
		String result = null;
		try {
			 result = phoneUserService.login(phoneUser,request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 用户添加
	 * @param user
	 * @return
	 */
	@RequestMapping("ajax/addUser")
	@ResponseBody
	public String addUser(PhoneUser phoneUser){
		String result = "SUCCESS";
		try {
			result = phoneUserService.addUser(phoneUser);
		} catch (Exception e) {
			result="ERROR";
		}
		return result;
	}
	/**
	 * 用户添加
	 * @param user
	 * @return
	 */
	@RequestMapping("ajax/updateUser")
	@ResponseBody
	public String updateUser(PhoneUser phoneUser){
		String result = "SUCCESS";
		try {
			phoneUserService.updateUser(phoneUser);
		} catch (Exception e) {
			result="ERROR";
		}
		return result;
	}
	
	/**
	 * 手机用户分页查询
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("ajax/getPageDate")
	@ResponseBody
	public PageUntil<PhoneUser> getPageData(Integer page,Integer rows){
		PageUntil<PhoneUser> dataPage = new PageUntil<>();
		try {
		Map<String, Object> condition = new HashMap<>();
		condition.put("currentPage", page);
		condition.put("pageSize", rows);
		dataPage = phoneUserService.getPageDate(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataPage;
	}
	/**
	 * 手机获取全部用户信息
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("phoneGetPageData")
	@ResponseBody
	public List<PhoneUser> phoneGetPageData(){
		List<PhoneUser> dataPage = phoneUserService.phoneGetPageData();
		return dataPage;
	}

	/**
	 * 删除用户信息
	 * @param userName
	 * @return
	 */
	@RequestMapping("ajax/deleteUser")
	@ResponseBody
	public String deleteUser(String userName){
		String result = "SUCCESS";
		try {
			phoneUserService.deleteUser(userName);
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR";
		}
		return result;
	}
	
	/**
	 * 手机端获取版本信息
	 * @return
	 */
	@RequestMapping("getVersion")
	@ResponseBody
	public Map<String,Integer> getVersion(){
		Map<String,Integer> map = phoneUserService.getVersion();
		return map;
	}
}
