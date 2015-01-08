package com.cn.ctbri.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-4
 * 描        述：  default properties的常用配置
 * 版        本：  1.0
 */
@Resource
public class Configuration {
	static Logger log = Logger.getLogger(Configuration.class);
	private static String emailFrom;//发送的邮箱
	private static String server;//邮箱服务器
	private static String name;//用户名
	private static String password;//密码
	private static String stringBuffer;//创建StringBuffer对象用来操作字符串
	private static String apikey;//APIKEY
	private static String username;//用户名
	private static String passwordMobile;//向StringBuffer追加密码
	
	static {
		InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream("default.properties");
		Properties prop = new Properties();
		try {
			prop.load(inputStream);
		} catch (IOException e1) {
			log.error(e1.getMessage());	
		}
		passwordMobile = (String) prop.get("passwordMobile");//向StringBuffer追加密码
		username = (String) prop.get("username");//username
		apikey = (String) prop.get("apikey");//APIKEY
		stringBuffer = (String) prop.get("stringBuffer");//创建StringBuffer对象用来操作字符串
		emailFrom = (String) prop.get("emailFrom");//发送的邮箱
		server=(String)prop.get("server");//服务器
		name=(String)prop.get("name");//名称
		password=(String)prop.get("password");//密码
	}
	public static String getPasswordMobile() {
		return passwordMobile;
	}
	public static void setPasswordMobile(String passwordMobile) {
		Configuration.passwordMobile = passwordMobile;
	}
	public static String getStringBuffer() {
		return stringBuffer;
	}
	public static void setStringBuffer(String stringBuffer) {
		Configuration.stringBuffer = stringBuffer;
	}
	public static String getEmailFrom() {
		return emailFrom;
	}
	public static void setEmailFrom(String emailFrom) {
		Configuration.emailFrom = emailFrom;
	}
	public static String getServer() {
		return server;
	}
	public static void setServer(String server) {
		Configuration.server = server;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		Configuration.name = name;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		Configuration.password = password;
	}
	public static String getApikey() {
		return apikey;
	}
	public static void setApikey(String apikey) {
		Configuration.apikey = apikey;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		Configuration.username = username;
	}
}
