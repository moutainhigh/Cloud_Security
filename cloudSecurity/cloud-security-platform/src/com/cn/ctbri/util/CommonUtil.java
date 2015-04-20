package com.cn.ctbri.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import net.sf.json.JSONObject;

import com.cn.ctbri.entity.User;

public class CommonUtil {
	/**
	 * 功能描述： 把数据返回到页面
	 * 参数描述： HttpServletResponse response, JSONObject JSON
	 * @throws Exception 
	 *		 @time 2014-12-31
	 */
	public static void writeToJsp(HttpServletResponse response, JSONObject JSON)
			throws IOException {
		response.getWriter().write(JSON.toString());
		response.getWriter().flush();
	}
	
	/**
	 * 功能描述：  object转化为Json格式
	 * 参数描述： HttpServletResponse response,Map<String, Object> m
	 * @throws Exception 
	 *		 @time 2014-12-31
	 */
	public static JSONObject objectToJson(HttpServletResponse response,
			Map<String, Object> m) {
		JSONObject JSON = JSONObject.fromObject(m);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		return JSON;
	}
	/**
	 * 获取当前登录用户
	 * @param request
	 * @return
	 */
	public static User getGloble_user(HttpServletRequest request){
		return (User) request.getSession().getAttribute("globle_user");
	}
	/**
	 * 获取后台登录用户
	 * @param request
	 * @return
	 */
	public static User getAdmin_user(HttpServletRequest request){
		return (User) request.getSession().getAttribute("admin_user");
	}
	/**
	 * 创建一个bean，把数据封装到bean中返回
	 * @param datas
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(Map datas, Class<T> clazz) {
		/*
		 * 依赖commons-beanutils
		 */
		try {
			/*
			 * 1. 通过clazz创建bean实例
			 */
			T bean = clazz.newInstance();
			/*
			 * 注册类型转换器，用来把字符串转换成Date类型
			 */
			ConvertUtils.register(new DateConverter(), java.util.Date.class);
			/*
			 * 2. 把datas封装到bean中
			 */
			BeanUtils.populate(bean, datas);
			/*
			 * 3. 返回bean
			 */
			return bean;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
