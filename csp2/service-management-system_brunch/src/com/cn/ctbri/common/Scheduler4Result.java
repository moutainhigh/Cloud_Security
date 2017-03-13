package com.cn.ctbri.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.service.IOrderTaskService;

/**
 * 根据任务获取结果的调度类
 * 
 * @author googe
 * 
 */
public class Scheduler4Result {

	static Logger logger = Logger.getLogger(Scheduler4Result.class.getName());
	
	@Autowired
    IOrderTaskService orderTaskService;

	public void execute() throws Exception {
		logger.info("[获取结果调度]:任务表扫描开始....");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("task_status", Integer.parseInt(Constants.TASK_RUNNING));
		// 获取任务表前n条未完成的记录
		List<OrderTask> oTaskList = orderTaskService.findOrderTask(map);
		logger.info("[获取结果调度]:当前等待获取结果的任务有 " + oTaskList.size() + " 个!");
		for (OrderTask o : oTaskList) {
			String result = InternalWorker.vulnScanGetOrderTaskResult(o.getOrderId(), o.getId() + "_" + o.getOrderId());
//        	if(result.equals("success")){
//        		
//        	}
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}
}
