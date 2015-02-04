package com.cn.ctbri.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.impl.TaskServiceImpl;

/**
 * 扫描任务表的调度类
 * 
 * @author googe
 * 
 */

@SuppressWarnings("deprecation")
public class Scheduler4Task {

	static Logger logger = Logger.getLogger(Scheduler4Task.class.getName());

	private static String taskpage;

	@Autowired
	ITaskService taskService;
	
	@Autowired
	IAssetService assetService;
	
	@Autowired
	IServService servService;

	private String destURL = "";

	private String destIP = "";

	private String tplName ="";

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
		//System.out.println(new Date().toLocaleString() + " : 任务表扫描开始...");
		logger.info(new Date().toLocaleString() + " : 任务表扫描开始");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", Integer.parseInt(taskpage));
		map.put("status", Integer.parseInt(Constants.TASK_START));   //设置为 已开始？
		// 获取任务表前n条未下发的记录
		List<Task> taskList = taskService.findTask(map);
		logger.info(new Date().toLocaleString() + ": 当前等待下发的任务有 " + taskList.size() + " 个!");
		// 调用接口下发任务
		for (Task t : taskList) {
			logger.info(new Date().toLocaleString() + "任务：[" + t.getTaskId() + "]开始下发!");
			preTaskData(t);
			ArnhemWorker.lssuedTask(ArnhemWorker.getSessionId(), String.valueOf(t.getTaskId()), this.destURL, this.destIP, "80",
					this.tplName);
			Thread.sleep(3000);   //判断任务是否running？？
			//更新任务状态为running
			t.setStatus(Integer.parseInt(Constants.TASK_RUNNING));
			taskService.update(t);
			//为此任务创建调度，定时获取任务结果
//			/Scheduler4Result scheduler = new Scheduler4Result();
			getResultByTask(t);
			logger.info(new Date().toLocaleString() + "任务：[" + t.getTaskId() + "]完成下发!");
		}

		//System.out.println(new Date().toLocaleString() + " : 任务表扫描结束!");
		logger.info(new Date().toLocaleString() + " : 任务表扫描结束");

	}

	/**
	 * 根据任务信息设置接口参数
	 * 
	 * @param task
	 */
	private void preTaskData(Task task) {
		// 获取此任务的资产信息
		List<Asset> taskList = assetService.findByTask(task);
		if(false){
			this.destURL = taskList.get(0).getAddr();
		}else{
			this.destIP = taskList.get(0).getAddr();
		}
		// 获取此任务的服务模版名称
		List<Serv> servList = servService.findByTask(task);
		this.tplName = servList.get(0).getModule_name();
	}
	
	/**
	 * 
	 * @param task
	 * @throws SchedulerException 
	 */
	public void getResultByTask(Task task) throws SchedulerException{
		//获取引擎名称
//		servService = new ServServiceImpl();
//		List<Serv> sList = servService.findByTask(task);
//		String SName = sList.get(0).getName();
//		if("漏洞扫描服务".equals(SName)){
//			this.productId = Constants.PRODUCT_LD;
//		}else if("恶意代码监测服务".equals(SName)){
//			this.productId = Constants.PRODUCT_MM;
//		}else if("网页篡改监测服务".equals(SName)){
//			this.productId = Constants.PRODUCT_CG;
//		}else if("关键字监测服务".equals(SName)){
//			this.productId = Constants.PRODUCT_GJZ;
//		}else if("可用性监测服务".equals(SName)){
//			this.productId = Constants.PRODUCT_KYX;
//		}
		//获取任务id
		//this.taskId = String.valueOf(task.getTaskId());
		/**
		 * 为任务创建一个调度
		 */
		SchedulerFactory schedFact = new StdSchedulerFactory();
		Scheduler scheduler = schedFact.getScheduler();
		// 创建一个JobDetail，指明name，groupname，以及具体的Job类名，
		//该Job负责定义需要执行任务
		JobDetail jobDetail = new JobDetail("job"+task.getTaskId(), Scheduler.DEFAULT_GROUP,Scheduler4Result.class);
		jobDetail.getJobDataMap().put("task", task);
		//jobDetail.getJobDataMap().put("task", task);
		//根据任务信息创建触发器  设置调度策略
		SimpleTrigger trigger = new SimpleTrigger("SimpleTrigger" , Scheduler.DEFAULT_GROUP);
		trigger.setStartTime(new Date());  //立即执行
		trigger.setEndTime(null);
		trigger.setRepeatCount(0);
		trigger.setRepeatInterval(60*1000);   //   每隔1分钟执行一次
		scheduler.scheduleJob(jobDetail, trigger);
		//启动调度
		scheduler.start();
	}
}
