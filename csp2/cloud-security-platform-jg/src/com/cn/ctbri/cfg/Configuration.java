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
	private static String register_model;//注册模板
	private static String warn1_model;//告警模板
	private static String warn2_model;//告警模板
	private static String warn3_model;//告警模板
	private static String warn4_model;//告警模板
	private static String warn5_model;//告警模板
	private static String fileContent;//资产验证时的文件内容
	private static String modifyCode_model;//修改密码模板
	private static String forgetCode_model;//忘记密码模板
	private static String modifyMobile_model;//修改手机号模板
	private static String anquanbi_model;//注册赠送安全币模板
	private static String wafMsg_model;//waf短信提醒
	private static String renew_model;//续费短信提醒
	static {
		InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream("default.properties");
		Properties prop = new Properties();
		try {
			prop.load(inputStream);
		} catch (IOException e1) {
			log.error(e1.getMessage());	
		}
		passwordMobile = (String) prop.get("passwordMobile");//向StringBuffer追加密码
		fileContent = (String) prop.get("fileContent");//资产验证时的文件内容
		username = (String) prop.get("username");//username
		apikey = (String) prop.get("apikey");//APIKEY
		stringBuffer = (String) prop.get("stringBuffer");//创建StringBuffer对象用来操作字符串
		emailFrom = (String) prop.get("emailFrom");//发送的邮箱
		server=(String)prop.get("server");//服务器
		name=(String)prop.get("name");//名称
		password=(String)prop.get("password");//密码
		register_model=(String)prop.get("register_model");
		warn1_model=(String)prop.get("warn1_model");
		warn2_model=(String)prop.get("warn2_model");
		warn3_model=(String)prop.get("warn3_model");
		warn4_model=(String)prop.get("warn4_model");
		warn5_model=(String)prop.get("warn5_model");
		modifyCode_model=(String)prop.get("modifyCode_model");
		forgetCode_model=(String)prop.get("forgetCode_model");
		modifyMobile_model=(String)prop.get("modifyMobile_model");
		anquanbi_model=(String)prop.get("anquanbi_model");
		wafMsg_model=(String)prop.get("wafMsg_model");
		renew_model=(String)prop.get("renew_model");
	}
	public static String getFileContent() {
		return fileContent;
	}
	public static void setFileContent(String fileContent) {
		Configuration.fileContent = fileContent;
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
    public static String getRegister_model() {
        return register_model;
    }
    public static void setRegister_model(String register_model) {
        Configuration.register_model = register_model;
    }
    public static String getWarn1_model() {
        return warn1_model;
    }
    public static void setWarn1_model(String warn1_model) {
        Configuration.warn1_model = warn1_model;
    }
    public static String getWarn2_model() {
        return warn2_model;
    }
    public static void setWarn2_model(String warn2_model) {
        Configuration.warn2_model = warn2_model;
    }
    public static String getWarn3_model() {
        return warn3_model;
    }
    public static void setWarn3_model(String warn3_model) {
        Configuration.warn3_model = warn3_model;
    }
    public static String getWarn4_model() {
        return warn4_model;
    }
    public static void setWarn4_model(String warn4_model) {
        Configuration.warn4_model = warn4_model;
    }
    public static String getWarn5_model() {
        return warn5_model;
    }
    public static void setWarn5_model(String warn5_model) {
        Configuration.warn5_model = warn5_model;
    }
	public static String getModifyCode_model() {
		return modifyCode_model;
	}
	public static void setModifyCode_model(String modifyCode_model) {
		Configuration.modifyCode_model = modifyCode_model;
	}
	public static String getForgetCode_model() {
		return forgetCode_model;
	}
	public static void setForgetCode_model(String forgetCode_model) {
		Configuration.forgetCode_model = forgetCode_model;
	}
	public static String getModifyMobile_model() {
		return modifyMobile_model;
	}
	public static void setModifyMobile_model(String modifyMobile_model) {
		Configuration.modifyMobile_model = modifyMobile_model;
	}
	public static String getAnquanbi_model() {
		return anquanbi_model;
	}
	public static void setAnquanbi_model(String anquanbi_model) {
		Configuration.anquanbi_model = anquanbi_model;
	}
	public static String getWafMsg_model() {
		return wafMsg_model;
	}
	public static void setWafMsg_model(String wafMsg_model) {
		Configuration.wafMsg_model = wafMsg_model;
	}
	
	public static String getRenew_model() {
		return renew_model;
	}
	public static void setRenew_model(String renew_model) {
		Configuration.renew_model = renew_model;
	}
    
}
