package com.kaiyun.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kaiyun.pojo.User;
import com.kaiyun.service.UserService;
import com.kaiyun.until.PageUntil;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 前往登录界面
	 * @param mv
	 * @return
	 */
	@RequestMapping("/toLogin")
	public ModelAndView toLogin(ModelAndView mv){
		mv.setViewName("login");
		return mv;
	}
	
	/**
	 * 登录
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public String login(User user,HttpServletRequest request){
		String result = null;
		try {
			 result = userService.login(user,request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 前往主界面
	 * @param mv
	 * @return
	 */
	@RequestMapping("toIndex")
	public ModelAndView toIndex(HttpServletRequest request,ModelAndView mv){
		User user =(User)request.getSession().getAttribute("user");
		mv.addObject("userName", user.getUserName());
		mv.setViewName("index");
		return mv;
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		 return "redirect:/user/toLogin.do";
	}
	/**
	 * 前往用户管理页面
	 * @param mv
	 * @return
	 */
	@RequestMapping("toUserManage")
	public ModelAndView toUserManage(ModelAndView mv){
		mv.setViewName("user/userList");
		return mv;
	}
	
	/**
	 * 用户添加
	 * @param user
	 * @return
	 */
	@RequestMapping("ajax/addUser")
	@ResponseBody
	public String addUser(User user){
		String result = "SUCCESS";
		try {
			result = userService.addUser(user);
		} catch (Exception e) {
			result="ERROR";
		}
		return result;
	}
	@RequestMapping("toAddUser")
	public ModelAndView addUser(ModelAndView mv){
		mv.setViewName("user/user_add");
		return mv;
	}
	
	/**
	 * 分页查询用户列表
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("ajax/getPageDate")
	@ResponseBody
	public PageUntil<User> getPageDate(Integer page,Integer rows){
		PageUntil<User> dataPage = new PageUntil<>();
		try {
			Map<String, Object> condition = new HashMap<>();
			condition.put("currentPage", page);
			condition.put("pageSize", rows);
			dataPage = userService.getPageDate(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataPage;
	}
	
	@RequestMapping("ajax/deleteUser")
	@ResponseBody
	public String deleteUser(String userName){
		String result = "SUCCESS";
		try {
			userService.deleteUser(userName);
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR";
		}
		return result;
	}
	@RequestMapping("toEditPass")
	public ModelAndView toEditPass(HttpServletRequest request,ModelAndView mv){
		mv.setViewName("user/edit_password");
		return mv;
	}
	@RequestMapping("/ajax/editPass")
	@ResponseBody
	public String editPass(String oldPass,String passWord,HttpServletRequest request){
		User u = (User)request.getSession().getAttribute("user");
		u.setPassWord(oldPass);
		String result = userService.login(u, request);
		//如果旧密码正确
		if("SUCCESS".equals(result)){
			u.setPassWord(passWord);
			userService.editPass(u);
		}
		return result;
	}
	
	@RequestMapping("loginouttime")
	public ModelAndView test(ModelAndView mv){
		
		mv.setViewName("loginouttime");
		
		return mv;
	}
}
