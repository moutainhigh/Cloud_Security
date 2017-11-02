package com.cn.ctbri.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-12-11
 * 描        述： 日志实体
 * 版        本：  1.0
 */
public class LogInfo implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	//主键Id
	private int log_id;
	//登陆id
	private String loginId;
	//用户名
	private String userName;
	//日志时间
    private Date logTime;
	//日志类
	private String optClass;
	//方法
	private String optMethod;
	//级别
	private String logLevel;
	//日志信息
	private String logMessage;
	public int getLog_id() {
		return log_id;
	}
	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getOptClass() {
		return optClass;
	}
	public void setOptClass(String optClass) {
		this.optClass = optClass;
	}
	public String getOptMethod() {
		return optMethod;
	}
	public void setOptMethod(String optMethod) {
		this.optMethod = optMethod;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getLogMessage() {
		return logMessage;
	}
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	
	
}
