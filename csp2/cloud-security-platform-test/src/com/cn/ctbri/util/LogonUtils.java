package com.cn.ctbri.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
		//如果name存在中文
		try {
			name = URLEncoder.encode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//设置cookie
		int cookieMaxAge = 7*24*60*60;
		long valueTime = System.currentTimeMillis() + cookieMaxAge*1000;
		String cookieValue = valueTime + ":" + name + ":" + password;
		Cookie newCookie = new Cookie("anquanbang_login",cookieValue);
		
		//设置Cookie的有效路径
		newCookie.setPath(request.getContextPath()+"/loginUI.html");
		newCookie.setMaxAge(cookieMaxAge);
		newCookie.setPath("/");
		//将Cookie放置到response对象中
		response.addCookie(newCookie);
	}

	public static Map<String,Object> readCookie(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		Cookie cookies[] = request.getCookies();
		String cookieValue = null;
		if(cookies!=null){
			for(int i=0; i<cookies.length; i++){
				if("anquanbang_login".equals(cookies[i].getName())){
					cookieValue = cookies[i].getValue();
				}
			}
		}else{
			return null;
		}
		if(cookieValue==null){
			return null;
		}
		
		String[] cookieValues = cookieValue.split(":");
		if(cookieValues!=null && cookieValues.length==3){
			//判断是否在有效期内,过期就删除Cookie
			long validTimeInCookie = new Long(cookieValues[0]);
			if(validTimeInCookie < System.currentTimeMillis()){
				//删除cookie
				Cookie cookie = new Cookie("anquanbang_login", null);
				cookie.setMaxAge(0);
				cookie.setPath(request.getContextPath()+"/loginUI.html");
				response.addCookie(cookie);
				return map;
			}
			map.put("result", cookieValues);
			return map;
		}
		return null;
	}
}
