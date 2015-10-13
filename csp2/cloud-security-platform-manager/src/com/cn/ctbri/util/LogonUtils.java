package com.cn.ctbri.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

public class LogonUtils {

	/**校验验证码*/
	/**
	 * 返回值 boolean：true：验证码输入正确
	 * 	   boolean：false：验证码输入有误
	 */
	public static boolean checkNumber(HttpServletRequest request) {
		//获取页面输入的验证码
		String checkNumber = request.getParameter("checkNumber");
		if(StringUtils.isBlank(checkNumber)){
			return false;
		}
		//获取image.jsp生成的验证
		String CHECK_NUMBER_KEY = (String) request.getSession().getAttribute("CHECK_NUMBER_KEY");
		if(StringUtils.isBlank(CHECK_NUMBER_KEY)){
			return false;
		}
		return checkNumber.equalsIgnoreCase(CHECK_NUMBER_KEY);
	}
	
	public static boolean checkNumberAdmin(HttpServletRequest request) {
		//获取页面输入的验证码
		String checkNumber = request.getParameter("checkNumber");
		if(StringUtils.isBlank(checkNumber)){
			return false;
		}
		//获取image.jsp生成的验证
		String CHECK_NUMBER_KEY = (String) request.getSession().getAttribute("CHECK_NUMBER_KEY_admin");
		if(StringUtils.isBlank(CHECK_NUMBER_KEY)){
			return false;
		}
		return checkNumber.equalsIgnoreCase(CHECK_NUMBER_KEY);
	}

	/**记住密码*/
	public static void remeberMe(HttpServletRequest request,
			HttpServletResponse response, String name, String password) {
		//创建2个Cookie
		//如果name存在中文
		try {
			name = URLEncoder.encode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie nameCookie = new Cookie("name",name);
		Cookie passwordCookie = new Cookie("password",password);
		
		//设置Cookie的有效路径
		nameCookie.setPath(request.getContextPath()+"/loginUI.html");
		passwordCookie.setPath(request.getContextPath()+"/loginUI.html");
		
		//设置Cookie的有效时长（一周），当页面复选框选中的时候，设置
		//获取页面复选框的值
		String remeberMe = request.getParameter("remeberMe");
		//选中页面复选框
		if(remeberMe!=null && remeberMe.equals("yes")){
			nameCookie.setMaxAge(7*24*60*60);
			passwordCookie.setMaxAge(7*24*60*60);
		}
		//如果没有被选中，清空有效时长
		else{
			nameCookie.setMaxAge(0);
			passwordCookie.setMaxAge(0);
		}
		//将2个Cookie放置到response对象中
		response.addCookie(nameCookie);
		response.addCookie(passwordCookie);

	}
	//后台记住密码
	public static void remeberAdmin(HttpServletRequest request,
			HttpServletResponse response, String name, String password) {
		//创建2个Cookie
		//如果name存在中文
		try {
			name = URLEncoder.encode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie nameCookie = new Cookie("name",name);
		Cookie passwordCookie = new Cookie("password",password);
		
		//设置Cookie的有效路径
		nameCookie.setPath(request.getContextPath()+"/admin.html");
		passwordCookie.setPath(request.getContextPath()+"/admin.html");
		
		//设置Cookie的有效时长（一周），当页面复选框选中的时候，设置
		//获取页面复选框的值
		String remeberMe = request.getParameter("remeberMe");
		//选中页面复选框
		if(remeberMe!=null && remeberMe.equals("yes")){
			nameCookie.setMaxAge(7*24*60*60);
			passwordCookie.setMaxAge(7*24*60*60);
		}
		//如果没有被选中，清空有效时长
		else{
			nameCookie.setMaxAge(0);
			passwordCookie.setMaxAge(0);
		}
		//将2个Cookie放置到response对象中
		response.addCookie(nameCookie);
		response.addCookie(passwordCookie);

	}
}
