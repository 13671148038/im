package com.kaiyun.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kaiyun.until.Parameters;

public class MyIntercept extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Boolean flag = true;
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute("user");
		String path = request.getServletPath();
		String contextPath = request.getContextPath();
		if(path.contains("login")||path.contains("Login")||path.contains("app")){
			return true;
		}
		if(attribute!=null){
			return true;
		}
		if(attribute==null){
			if(path.contains("ajax")){
				throw new Exception(Parameters.ajaxExceptionContent);
			}else{
				response.sendRedirect(contextPath+"/user/loginouttime.do");
				flag = false;
			}
		}
		return flag;
	}

}
