package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;

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
		logger.info("[下发任务调度]:任务表扫描开始......");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", Integer.parseInt(taskpage));
		map.put("status", Integer.parseInt(Constants.TASK_START));   //设置为 已开始？
		// 获取任务表前n条未下发的记录
		List<Task> taskList = taskService.findTask(map);
		logger.info("[下发任务调度]:当前等待下发的任务有 " + taskList.size() + " 个!");
		// 调用接口下发任务
		for (Task t : taskList) {
//			logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]获取状态!");
//			String sessionId = ArnhemWorker.getSessionId();
//			String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, String.valueOf(t.getTaskId()));
//			String status = this.getStatusByResult(resultStr);
			logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
			preTaskData(t);
			ArnhemWorker.lssuedTask(ArnhemWorker.getSessionId(), String.valueOf(t.getTaskId()), this.destURL, this.destIP, "80",
					this.tplName);
			//为此任务创建调度，定时获取任务结果
			//getResultByTask(t);
			//更新任务状态为running
			t.setStatus(Integer.parseInt(Constants.TASK_RUNNING));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			t.setExecute_time(sdf.parse(new Date().toLocaleString()));
			taskService.update(t);
			logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
		}
		logger.info("[下发任务调度]:任务表扫描结束......");

	}

	/**
	 * 根据任务信息设置接口参数
	 * 
	 * @param task
	 */
	private void preTaskData(Task task) {
		// 获取此任务的资产信息
		List<Asset> taskList = assetService.findByTask(task);
		if(!isboolIp(taskList.get(0).getAddr())){ //判断地址是否是ip
			this.destURL = taskList.get(0).getAddr();
		}else{
			this.destIP = taskList.get(0).getAddr();
		}
		// 获取此任务的服务模版名称
		List<Serv> servList = servService.findByTask(task);
		this.tplName = servList.get(0).getModule_name();
	}
	
	/** 
	 * 判断是否为合法IP 
	 * @return the ip 
	 */  
	public boolean isboolIp(String ipAddress)  
	{  
	       String ip = "([1-9]|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])(//.(//d|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])){3}";   
	       Pattern pattern = Pattern.compile(ip);   
	       Matcher matcher = pattern.matcher(ipAddress);   
	       return matcher.matches();   
	}  
	
	private String getStatusByResult(String resultStr) {
		String decode;
		String state = "";
		try {
			decode = URLDecoder.decode(resultStr, "UTF-8");
			Document document = DocumentHelper.parseText(decode);
			Element result = document.getRootElement();
			Attribute attribute  = result.attribute("value");
			String resultValue = attribute.getStringValue();
			if("Success".equals(resultValue)){
				Element StateNode = result.element("State");
				state = StateNode.getTextTrim();
			}else{
				logger.info("[下发任务调度]:远程没有此任务，可以下发!");
			}
			return state;
		} catch (Exception e) {
			logger.info("[下发任务调度]:解析任务状态失败,远程没有此任务，可以下发!");
			return "";
		}
		
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
//		SchedulerFactory schedFact = new StdSchedulerFactory();
//		Scheduler scheduler = schedFact.getScheduler();
//		// 创建一个JobDetail，指明name，groupname，以及具体的Job类名，
//		//该Job负责定义需要执行任务
//		JobDetail jobDetail = new JobDetail("job"+task.getTaskId(), Scheduler.DEFAULT_GROUP,Scheduler4Result.class);
//		jobDetail.getJobDataMap().put("task", task);
//		//jobDetail.getJobDataMap().put("task", task);
//		//根据任务信息创建触发器  设置调度策略
//		SimpleTrigger trigger = new SimpleTrigger("SimpleTrigger" , Scheduler.DEFAULT_GROUP);
//		trigger.setStartTime(new Date());  //立即执行
//		trigger.setEndTime(null);
//		trigger.setRepeatCount(0);
//		trigger.setRepeatInterval(60*1000);   //   每隔1分钟执行一次
//		scheduler.scheduleJob(jobDetail, trigger);
//		//启动调度
//		//scheduler.start();
	}
}
