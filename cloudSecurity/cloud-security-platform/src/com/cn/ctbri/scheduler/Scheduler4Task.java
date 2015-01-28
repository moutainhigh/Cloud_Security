package com.cn.ctbri.scheduler;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;
/**
 * 根据订单定制任务的调度类
 * @author googe
 *
 */
public class Scheduler4Task implements Job{
	
	static Logger logger = Logger.getLogger(Scheduler4Task.class.getName());
	
	/**
	 * 任务执行预制时间点
	 */
	private static String tasktime = "";
	
	private TaskDao taskDao;
	
	private Order order;
	
	static{
		try {
			Properties p = new Properties();
			p.load(Scheduler4Task.class.getClassLoader().getResourceAsStream("arnhem.properties"));
			tasktime= p.getProperty("TASK_TIME");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		/**
		 * 定时要job任务执行的逻辑
		 */
		//根据orderid 获取要扫描的资产集合
		
		//遍历创建任务
//		for(){
//			
//		}
		Task task = new Task(); 
		
		//插入一条任务数据  获取任务id
		int taskId = taskDao.insert(task);
		//调用接口下发任务
//		ArnhemWorker.lssuedTask(ArnhemWorker.getSessionId(), String.valueOf(taskId), "", "", "80", "");
		
		//调用 轮训获取任务状态和结果的调度
		
	}
	
	public void setTaskByOrder(Order o){
		this.setOrder(o);
		//获取订单详情信息 
		Date beginDate = o.getBegin_date();
		Date endDate = o.getEnd_date();
		
		
		
		int type = o.getType();
		if(Integer.parseInt(Constants.ORDERTYPE_LONG) == type){
			/**
			 * 长期
			 */
			//获取扫描类型
			int scanType = o.getScan_type();
			//根据不同类型指定相应的调度任务
			if(Integer.parseInt(Constants.SCANTYPE_DAY) == scanType){  //每天
				
			}else if(Integer.parseInt(Constants.SCANTYPE_WEEK) == scanType){ //每周
				
			}else if(Integer.parseInt(Constants.SCANTYPE_MONTH) == scanType){  //每月
				
			}else{
				logger.error("调度任务日志：订单扫描类型有误!");
				throw new RuntimeException("调度任务日志：订单扫描类型有误!");
			}
			
		}else if(Integer.parseInt(Constants.ORDERTYPE_SINGLE) == type){
			/**
			 * 单次
			 */
		}else{
			logger.error("调度任务日志：订单类型有误!");
			throw new RuntimeException("调度任务日志：订单类型有误!");
		}
		

		//获取预制时间点 
		
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
