package com.cn.ctbri.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Task;

public class PreDateUtil {
	
	/**
	 * 根据任务信息设置接口参数
	 * 
	 * @param task
     * @param engine 
	 */
	public static int preTaskData(Task t, EngineCfg engine, Serv service) {
		String destURL = "";

		String destIP = "";
		//模板
		String tplName ="";
		//扫描时间
		int scantime = 0;
		// 获取此任务的资产信息
		if (t.getAssetAddr() != null && !t.getAssetAddr().equals("")
				&& !isboolIp(t.getAssetAddr())) { // 判断地址是否是ip
			destURL = t.getAssetAddr();
		} else {
			destIP = t.getAssetAddr();
		}
		// 获取此任务的服务模版名称
		if (service.getModule_name() != null
				&& !service.getModule_name().equals("")) {
			tplName = service.getModule_name();
		}
		// 扫描频率
		int rate = t.getScanType();
		if (Constants.SERVICE_KYXJCFW.equals(tplName)) {

			switch (rate) {
			case 1:
				tplName = Constants.TPL_KYXJCFU_10M
						+ engine.getEngine_name();
				scantime = 10;
				break;
			case 2:
				tplName = Constants.TPL_KYXJCFU_30M
						+ engine.getEngine_name();
				scantime = 30;
				break;
			case 3:
				tplName = Constants.TPL_KYXJCFU_1H
						+ engine.getEngine_name();
				scantime = 60;
				break;
			}

		} else if (Constants.SERVICE_WYCGJCFW.equals(tplName)) {
			switch (rate) {
			case 2:
				tplName = Constants.TPL_WYCGJCFW_30M2
						+ engine.getEngine_name();
				scantime = 30;
				break;
			case 3:
				tplName = Constants.TPL_WYCGJCFW_1H2
						+ engine.getEngine_name();
				scantime = 60;
				break;
			case 4:
				tplName = Constants.TPL_WYCGJCFW_1D2
						+ engine.getEngine_name();
				scantime = 1440;
				break;
			}
		} else if (Constants.SERVICE_GJZJCFW.equals(tplName)) {
			switch (rate) {
			case 2:
				tplName = Constants.SERVICE_GJZJCFW
						+ engine.getEngine_name();
				scantime = 30;
				break;
			case 3:
				tplName = Constants.SERVICE_GJZJCFW
						+ engine.getEngine_name();
				scantime = 60;
				break;
			case 4:
				tplName = Constants.SERVICE_GJZJCFW
						+ engine.getEngine_name();
				scantime = 1440;
				break;
			default:
				tplName = Constants.SERVICE_GJZJCFW
						+ engine.getEngine_name();
				break;
			}
		} else if (Constants.SERVICE_EYDMJCFW.equals(tplName)) {//木马改成每周每月，执行
			switch (rate) {
			case 1:
				tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				scantime = 30;
				break;
			case 2:
				tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				scantime = 60;
				break;
			case 3:
				tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				scantime = 120;
				break;
			case 4:
				tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				scantime = 1440;
				break;
			default:
				tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				break;
			}
		} else if (Constants.SERVICE_LDSMFW.equals(tplName)) {
			tplName = Constants.SERVICE_LDSMFW + engine.getEngine_name();
		}
		return scantime;
	}
	
	/** 
	 * 判断是否为合法IP 
	 * @return the ip 
	 */  
	public static boolean isboolIp(String ipAddress)  
	{  
	       String ip = "([1-9]|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])(//.(//d|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])){3}";   
	       Pattern pattern = Pattern.compile(ip);   
	       Matcher matcher = pattern.matcher(ipAddress);   
	       return matcher.matches();   
	}  
	
}
