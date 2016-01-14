package com.cn.ctbri.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IOrderTaskService;
import com.cn.ctbri.service.ITaskService;

/**
 * 根据任务获取进度的调度类
 * 
 * @author txr
 * 
 */
public class Scheduler4Progress {

	static Logger logger = Logger.getLogger(Scheduler4Progress.class.getName());

	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
    private IEngineService engineService;
	
	@Autowired
    IOrderTaskService orderTaskService;
	


	public void execute() throws Exception {
		logger.info("[获取任务进度调度]:任务表扫描开始....");
		/**
		 * 定时要job任务进度执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("task_status", Integer.parseInt(Constants.TASK_RUNNING));
		// 获取任务表前n条未完成的记录
		List<OrderTask> oTaskList = orderTaskService.findOrderTask(map);
		logger.info("[获取任务进度调度]:当前等待获取进度的任务有 " + oTaskList.size() + " 个!");
		for (OrderTask o : oTaskList) {
			String result = InternalWorker.vulnScanGetOrderTaskStatus(o.getOrderId(), o.getOrderTaskId());
        	System.out.println(result);
		}
		logger.info("[获取任务进度调度]:任务进度扫描结束....");
	}

}
