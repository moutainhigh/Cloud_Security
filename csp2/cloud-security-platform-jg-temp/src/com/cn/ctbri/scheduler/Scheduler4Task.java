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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.dao.OrderAssetDao;
import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
/**
 * 根据订单定制任务的调度类
 * @author googe
 *
 */
public class Scheduler4Task implements Job{
	
	static Logger logger = Logger.getLogger(Scheduler4Task.class.getName());
	
	WebApplicationContext ac;
	
	public WebApplicationContext getAc() {
        return ac;
    }

    public void setAc(WebApplicationContext ac) {
        this.ac = ac;
    }

    static{
		try {
			Properties p = new Properties();
			p.load(Scheduler4Task.class.getClassLoader().getResourceAsStream("arnhem.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		String orderId = arg0.getJobDetail().getJobDataMap().getString("orderId");
		TaskDao taskDao = (TaskDao) arg0.getJobDetail().getJobDataMap().get("taskDao");
		OrderAssetDao orderAssetDao = (OrderAssetDao) arg0.getJobDetail().getJobDataMap().get("orderAssetDao");
		
		/**
		 * 定时要job任务执行的逻辑
		 */
		//根据orderid 获取要扫描的订单详情集合
		List<OrderAsset> oaList = orderAssetDao.findOrderAssetByOrderId(orderId);
		//获取订单定制的服务信息
		//Service s = orderDao.getTPLByServiceId();
		//遍历订单详情  创建任务
		for(OrderAsset oa : oaList){
			Task task = new Task(); 
			task.setExecute_time(new Date());
			task.setStatus(Integer.parseInt(Constants.TASK_START));
			//设置订单详情id
			task.setOrder_asset_Id(String.valueOf(oa.getId()));
			//插入一条任务数据  获取任务id
			int taskId = taskDao.insert(task);
			///*******************************
			//调用接口下发任务   由接口应用扫描任务表 下发任务
			//ArnhemWorker.lssuedTask(ArnhemWorker.getSessionId(), String.valueOf(taskId), destURL, destIP, "80", "");
			//调用轮训获取任务状态和结果的调度
		}
		
	}
	
	public void setTaskByOrder(Order o) throws SchedulerException{
	    TaskDao taskDao = (TaskDao) ac.getBean("taskDao");
		OrderAssetDao orderAssetDao = (OrderAssetDao) ac.getBean("orderAssetDao");
		//获取订单详情信息 
		Date beginDate = o.getBegin_date();
		Date endDate = o.getEnd_date();
		int type = o.getType();
		// 为订单创建一个调度
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler scheduler = schedFact.getScheduler();
		// 创建一个JobDetail，指明name，groupname，以及具体的Job类名，
		//该Job负责定义需要执行任务
		JobDetail jobDetail = new JobDetail("job"+o.getId(), Scheduler.DEFAULT_GROUP,Scheduler4Task.class);
		jobDetail.getJobDataMap().put("type", "FULL");
		jobDetail.getJobDataMap().put("orderId", o.getId());
		jobDetail.getJobDataMap().put("orderAssetDao", orderAssetDao);
		jobDetail.getJobDataMap().put("taskDao", taskDao);
		
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
			try{
				if(Integer.parseInt(Constants.SCANTYPE_DAY) == scanType){  //每天 00:10:00
					//秒 分 时 日 月 周 年(可选)
					trigger.setCronExpression("0 10 00 * * ?");
				}else if(Integer.parseInt(Constants.SCANTYPE_WEEK) == scanType){ //每周一。。。
					trigger.setCronExpression("0 10 * ? * MON");
				}else if(Integer.parseInt(Constants.SCANTYPE_MONTH) == scanType){  //每月一号。。。
					trigger.setCronExpression("0 10 00 1 * ?");
				}else{
					logger.error("调度任务日志：订单扫描类型有误!");
					throw new RuntimeException("调度任务日志：订单扫描类型有误!");
				}
				//YUYONGBO 必须把任务和触发事件设置到调度中
				scheduler.scheduleJob(jobDetail, trigger);
			}catch (Exception e) {
			    e.printStackTrace();
				logger.error("调度任务日志：设置调度策略有误!");
				throw new RuntimeException("调度任务日志：设置调度策略有误!");
			}
			
			
		}else if(Integer.parseInt(Constants.ORDERTYPE_SINGLE) == type){
			/**
			 * 单次  只有起始时间
			 */
			if(beginDate.getTime() > System.currentTimeMillis()){
				// 从开始时间开始执行
				SimpleTrigger trigger = new SimpleTrigger("SimpleTrigger" , Scheduler.DEFAULT_GROUP);
				trigger.setStartTime(beginDate);
				trigger.setEndTime(null);
				trigger.setRepeatCount(0);
				trigger.setRepeatInterval(0);
				scheduler.scheduleJob(jobDetail, trigger);
			}
		}else{
			logger.error("调度任务日志：订单类型有误!");
			throw new RuntimeException("调度任务日志：订单类型有误!");
		}
		//开始调度任务
		scheduler.start();
	}

}
