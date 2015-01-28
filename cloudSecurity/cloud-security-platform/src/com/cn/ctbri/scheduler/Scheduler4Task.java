package com.cn.ctbri.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import com.cn.ctbri.common.ArnhemWorker;
import com.cn.ctbri.common.Constants;
import com.cn.ctbri.dao.AssetDao;
import com.cn.ctbri.dao.OrderAssetDao;
import com.cn.ctbri.dao.OrderDao;
import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Service;
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
	
	private OrderAssetDao orderAssetDao;
	
	//private OrderDao serviceDao;
	
	private Order order;
	
	//private String destURL = "";
	
	//private String destIP = "";
	
	static{
		try {
			Properties p = new Properties();
			p.load(Scheduler4Task.class.getClassLoader().getResourceAsStream("arnhem.properties"));
			//获取预制时间点 
			tasktime= p.getProperty("TASK_TIME");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		/**
		 * 定时要job任务执行的逻辑
		 */
		//根据orderid 获取要扫描的订单详情集合
		List<OrderAsset> oaList = orderAssetDao.findOrderAssetByOrderId(order.getId());
		//获取订单定制的服务信息
		//Service s = orderDao.getTPLByServiceId();
		//遍历订单详情  创建任务
		for(OrderAsset oa : oaList){
			Task task = new Task(); 
			task.setExecute_time(new Date());
			task.setStatus(Integer.parseInt(Constants.TASK_START));
			//设置订单详情id
			task.setOrder_asset_Id(oa.getId());
			//插入一条任务数据  获取任务id
			int taskId = taskDao.insert(task);
			
			///*******************************
			//调用接口下发任务   由接口应用扫描任务表 下发任务
			//ArnhemWorker.lssuedTask(ArnhemWorker.getSessionId(), String.valueOf(taskId), destURL, destIP, "80", "");
			//调用轮训获取任务状态和结果的调度
		}
		
	}
	
	public void setTaskByOrder(Order o) throws SchedulerException{
		this.setOrder(o);
		//获取订单详情信息 
		Date beginDate = o.getBegin_date();
		Date endDate = o.getEnd_date();
		int type = o.getType();
		// 为订单创建一个调度
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler scheduler = schedFact.getScheduler();
		// 创建一个JobDetail，指明name，groupname，以及具体的Job类名，
		//该Job负责定义需要执行任务
		JobDetail jobDetail = new JobDetail("job"+order.getId(), Scheduler.DEFAULT_GROUP,Scheduler4Task.class);
		jobDetail.getJobDataMap().put("type", "FULL");
		//根据订单信息创建触发器  设置调度策略
		if(Integer.parseInt(Constants.ORDERTYPE_LONG) == type){
			CronTrigger trigger = new CronTrigger("CronTrigger","CronGroup");
			trigger.setStartTime(beginDate);
			trigger.setEndTime(endDate);
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
			 * 单次  只有起始时间
			 */
			if(beginDate.getTime() > System.currentTimeMillis()){
				// 从开始时间开始执行
				SimpleTrigger trigger = new SimpleTrigger("SimpleTrigger" , "SimpleGroup");
				trigger.setStartTime(beginDate);
				trigger.setEndTime(null);
				trigger.setRepeatCount(1);
				scheduler.scheduleJob(jobDetail, trigger);
			}
		}else{
			logger.error("调度任务日志：订单类型有误!");
			throw new RuntimeException("调度任务日志：订单类型有误!");
		}
		//开始调度任务
		scheduler.start();
	}
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
