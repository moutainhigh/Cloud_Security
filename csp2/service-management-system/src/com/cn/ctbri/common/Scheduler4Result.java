package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IOrderTaskService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.Random;
import com.cn.ctbri.util.SMSUtils;

/**
 * 根据任务获取结果的调度类
 * 
 * @author googe
 * 
 */
public class Scheduler4Result {

	static Logger logger = Logger.getLogger(Scheduler4Result.class.getName());

	private static String taskpage;
	
	@Autowired
    IOrderTaskService orderTaskService;
    
	static {
		try {
			Properties p = new Properties();
			p.load(Scheduler4Task.class.getClassLoader().getResourceAsStream("arnhem.properties"));
			taskpage = p.getProperty("TASKPAGE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute() throws Exception {
		logger.info("[获取结果调度]:任务表扫描开始....");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", Integer.parseInt(taskpage));
		map.put("status", Integer.parseInt(Constants.TASK_RUNNING));
		// 获取任务表前n条未完成的记录
		List<OrderTask> oTaskList = orderTaskService.findOrderTask(map);
		logger.info("[获取结果调度]:当前等待获取结果的任务有 " + oTaskList.size() + " 个!");
		for (OrderTask o : oTaskList) {
			String result = InternalWorker.vulnScanGetOrderTaskResult(o.getOrderId(), o.getOrderTaskId());
        	System.out.println(result);
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}
}
