package com.cn.ctbri.util;

import java.util.Date;

import com.cn.ctbri.entity.LogInfo;


/**
 * 日志的操作的公共类
 * @author txr
 *
 */
public class LoggerUtil {
	
	/**
	 * 任务下发成功.
	 */
	public  static final String TASK_SUCCESS = "任务下发成功";
	
	/**
	 * 添加日志信息.
	 * @param loginId 
	 * @param userName 用户姓名
	 * @param logTime 用户操作的时间
	 * @param optClass 操作类
	 * @param optMethod 操作方法
	 * @param logLevel 级别
	 * @param logMessage 消息
	 */
	public static LogInfo addLoginfo(String loginId, String userName, Date logTime, String optClass, String optMethod, String logLevel, String logMessage) {
		LogInfo logInf=new LogInfo();
		logInf.setLoginId(loginId);
		logInf.setUserName(userName);
		logInf.setLogTime(logTime);
		logInf.setOptClass(optClass);
		logInf.setOptMethod(optMethod);
		logInf.setLogLevel(logLevel);
		logInf.setLogMessage(logMessage);
		return logInf;
	}
	
}
