package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;

/**
 * 引擎调度类
 * 
 * @author tang
 * 
 */

public class EngineWorker {

	static Logger logger = Logger.getLogger(EngineWorker.class.getName());

	/**
	 * 引擎编号
	 */
	private static String engine_capacity;
	
	private static String engine_capacity1;
	
	@Autowired
	ITaskService taskService;


	static {
		try {
			Properties p = new Properties();
			p.load(EngineWorker.class.getClassLoader().getResourceAsStream("engineConfig.properties"));
			engine_capacity = p.getProperty("engine_capacity");
			engine_capacity1 = p.getProperty("engine_capacity1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getUsableEngine(int serviceId) {
		
	}
	
	public static void main(String[] args) {
		System.out.println(engine_capacity);
		System.out.println(engine_capacity1);
	}

	
	
    
}
