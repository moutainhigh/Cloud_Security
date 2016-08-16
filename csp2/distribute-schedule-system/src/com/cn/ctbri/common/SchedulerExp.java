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
import com.cn.ctbri.util.DateUtils;

/**
 * 扫描订单任务表的调度类
 * 
 * @author tangxr 
 * 
 */

@SuppressWarnings("deprecation")
public class SchedulerExp {

	static Logger logger = Logger.getLogger(SchedulerExp.class.getName());

	@Autowired
    ITaskService taskService;
	@Autowired  
    private IProducerService producerService;
	@Autowired  
    @Qualifier("taskQueueDestination")   
    private Destination taskDestination; 

	public void execute() throws Exception {
		logger.info("[下发任务调度]:任务表扫描开始......");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Integer.parseInt(Constants.TASK_START));   //设置为 已开始？
		// 获取订单任务表前n条未下发的记录
		List<Task> taskList = taskService.findExpTask(map);
		logger.info("[下发任务调度]:当前等待下发的任务有 " + taskList.size() + " 个!");
		// 调用接口下发任务
		for (Task task : taskList) {
			//将任务放到消息队列里	
			producerService.sendMessage(taskDestination, task);
		}
		logger.info("[下发任务调度]:任务表扫描结束......");
	}
	
}
