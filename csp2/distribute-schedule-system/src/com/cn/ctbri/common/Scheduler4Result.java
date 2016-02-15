package com.cn.ctbri.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.Destination;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IOrderTaskService;
import com.cn.ctbri.service.IProducerService;
import com.cn.ctbri.service.ITaskService;

/**
 * 根据任务获取结果的调度类
 * 
 * @author googe
 * 
 */
public class Scheduler4Result {

	static Logger logger = Logger.getLogger(Scheduler4Result.class.getName());
	
	@Autowired
    ITaskService taskService;
	@Autowired  
    private IProducerService producerService;
	@Autowired  
    @Qualifier("taskQueueDestination")   
    private Destination taskDestination; 

	public void execute() throws Exception {
		logger.info("[获取结果调度]:任务表扫描开始....");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Integer.parseInt(Constants.TASK_RUNNING));
		// 获取任务表前n条未完成的记录
		List<Task> taskList = taskService.findTask(map);
		logger.info("[获取结果调度]:当前等待获取结果的任务有 " + taskList.size() + " 个!");
		for (Task task : taskList) {
			//将任务放到消息队列里	
			producerService.sendMessage(taskDestination, task);
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}
}
