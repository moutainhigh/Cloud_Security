package com.cn.ctbri.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量表
 * @author googe
 *
 */
		
public class Constants {
	/**
	 * 任务状态<br>
	 * start:已开始
	 */
	public static final String TASK_START = "1"; 
	
	/**
	 * 任务状态<br>
	 * finish:已结束
	 */
	public static final String TASK_FINISH = "3"; 
	
	/**
	 * 任务状态<br>
	 * running :执行中
	 */
	public static final String TASK_RUNNING = "2"; 
	
	/**
	 * 引擎功能<br>
	 * 1 :漏洞
	 */
	public static final String PRODUCT_LD = "1"; 
	
	/**
	 * 引擎功能<br>
	 * 2 :木马
	 */
	public static final String PRODUCT_MM = "2"; 
	
	/**
	 * 引擎功能<br>
	 * 3 :篡改
	 */
	public static final String PRODUCT_CK = "3"; 
	
	/**
	 * 引擎功能<br>
	 * 4 :关键字
	 */
	public static final String PRODUCT_GJZ = "4"; 
	
	/**
	 * 引擎功能<br>
	 * 5 :可用性
	 */
	public static final String PRODUCT_KYX = "5"; 
	
	/**
	 * 订单类型<br>
	 * 1 :长期
	 */
	public static final String ORDERTYPE_LONG = "1"; 
	
	/**
	 * 订单类型<br>
	 * 2 :单次
	 */
	public static final String ORDERTYPE_SINGLE = "2"; 
	
	
	/**
	 * 扫描类型<br>
	 * 1 :每天
	 */
	public static final String SCANTYPE_DAY = "1"; 
	
	/**
	 * 扫描类型<br>
	 * 2 :每周
	 */
	public static final String SCANTYPE_WEEK = "2"; 
	
	/**
	 * 扫描类型<br>
	 * 3 :每月
	 */
	public static final String SCANTYPE_MONTH = "3"; 
	
	/**
	 * 任务模版<br>
	 * 漏洞扫描服务
	 */
	public static final String TPL_LDSMFU = "漏洞扫描服务"; 
	
	/**
	 * 任务模版<br>
	 * 恶意代码检测服务
	 */
	public static final String TPL_EYDMJCFU = "恶意代码监测服务"; 
	
	/**
	 * 任务模版<br>
	 * 关键字监测服务
	 */
	public static final String TPL_GJZJCFU = "关键字监测服务"; 
	
	/**
	 * 任务模版<br>
	 * 可用性监测服务-周期1小时
	 */
	public static final String TPL_KYXJCFU_1H = "可用性监测服务-周期1小时"; 
	
	/**
	 * 任务模版<br>
	 * 网页篡改监测服务-周期2小时深度2
	 */
	public static final String TPL_WYCGJCFW_2H2 = "网页篡改监测服务-周期2小时深度2"; 
	
	public static Map<String,String> SCAN_TYPE_MAP = new HashMap<String, String>();
	static{
		
		SCAN_TYPE_MAP.put("1","10分钟");
		SCAN_TYPE_MAP.put("2","30分钟");
		SCAN_TYPE_MAP.put("3","1小时");
		SCAN_TYPE_MAP.put("4","1天");
		SCAN_TYPE_MAP.put("5","每周");
		SCAN_TYPE_MAP.put("6","每月");
	}
}
