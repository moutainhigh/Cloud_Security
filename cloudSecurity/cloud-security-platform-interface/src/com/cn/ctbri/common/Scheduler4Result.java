package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.impl.ServServiceImpl;

/**
 * 根据任务获取结果的调度类
 * 
 * @author googe
 * 
 */
public class Scheduler4Result {

	static Logger logger = Logger.getLogger(Scheduler4Result.class.getName());

	@Autowired
	private IAlarmService alarmService;

	private static String taskpage;

	@Autowired
	private IServService servService;

	@Autowired
	private ITaskService taskService;

	private String productId;

	private Scheduler scheduler;

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
		List<Task> taskList = taskService.findTask(map);
		logger.info("[获取结果调度]:当前等待获取结果的任务有 " + taskList.size() + " 个!");
		for (Task task : taskList) {
			logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]开始获取状态!");
			// 根据任务id获取任务状态
			String sessionId = ArnhemWorker.getSessionId();
			String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, String.valueOf(task.getTaskId()));
			String status = this.getStatusByResult(resultStr);
			List<Alarm> aList;
			if ("finish".equals(status)) {
				// 任务执行完毕
				logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]扫描已完成，准备解析结果......");
				// 任务执行完毕 获取任务结果信息
				String reportByTaskID = ArnhemWorker.getReportByTaskID(sessionId, String.valueOf(task.getTaskId()), "1", 0, 500);
				aList = this.getAlarmByRerult(String.valueOf(task.getTaskId()), reportByTaskID);
				// 插入报警表
				for (Alarm a : aList) {
					alarmService.saveAlarm(a);
				}
				logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]入库告警数为" + aList.size() + "个!");
				task.setStatus(Integer.parseInt(Constants.TASK_FINISH));
				taskService.update(task);
				logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]告警结果已完成入库，已修改此任务为完成状态!");
			}else{
				logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]扫描未完成，等待下次拉取结果~");
			}
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}

//	public void execute(JobExecutionContext context) throws JobExecutionException {
//		JobDetail jobDetail = context.getJobDetail();
//		Task task = (Task) jobDetail.getJobDataMap().get("task");
//		String taskId = String.valueOf(task.getTaskId());
//		logger.info("任务：[" + taskId + "]扫描结果获取中........请等待....");
//		// 根据任务id获取任务状态
//		String sessionId = ArnhemWorker.getSessionId();
//		String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, taskId);
//		String status = this.getStatusByResult(resultStr);
//		List<Alarm> aList;
//		if (Constants.TASK_FINISH.equals(status)) {
//			// 更新任务状态已完成
//			task.setStatus(Integer.parseInt(Constants.TASK_FINISH));
//			taskService.update(task);
//			logger.info("任务：[" + taskId + "]扫描已完成，准备解析结果......");
//			// 任务执行完毕 获取任务结果信息
//			String reportByTaskID = ArnhemWorker.getReportByTaskID(sessionId, taskId, "1", 0, 500);
//			aList = this.getAlarmByRerult(taskId, reportByTaskID);
//			// 插入报警表
//			for (Alarm a : aList) {
//				// alarmService.saveAlarm(a);
//			}
//			logger.info("任务：[" + taskId + "]入库告警数为" + aList + "个!");
//			try {
//				logger.info("任务：[" + taskId + "]告警结果已完成入库，结束此任务的调度!");
//				// 关闭此调度
//				this.scheduler.shutdown();
//			} catch (SchedulerException e) {
//				e.printStackTrace();
//			}
//		} else {
//			logger.info("任务：[" + taskId + "]扫描未完成，等待下次拉取~");
//		}
//	}

	/**
	 * 根据任务状态结果解析当前任务状态
	 * 
	 * @param resultStr
	 * @return
	 */
	private String getStatusByResult(String resultStr) {
		String decode;
		try {
			decode = URLDecoder.decode(resultStr, "UTF-8");
			Document document = DocumentHelper.parseText(decode);
			Element result = document.getRootElement();
			Element StateNode = result.element("State");
			String state = StateNode.getTextTrim();
			return state;
		} catch (Exception e) {
			logger.info("[获取结果调度]:解析任务状态发生异常,远程没有此任务!");
			throw new RuntimeException("[获取结果调度]:解析任务状态发生异常,远程没有此任务!");
		}
	}

	/**
	 * 根据任务结果解析漏洞告警信息
	 * 
	 * @param taskStr
	 * @return
	 */
	private List<Alarm> getAlarmByRerult(String taskId, String taskStr) {
		List<Alarm> aList = new ArrayList<Alarm>();
		Alarm alarm = new Alarm();
		try {
			// String decode = URLDecoder.decode(taskStr, "UTF-8");
			Document document = DocumentHelper.parseText(taskStr);
			Element task = document.getRootElement();
			// 任务ID节点
			Element taskIDNode = task.element("TaskID");
			// 任务ID值
			// String taskId = taskIDNode.getTextTrim();
			alarm.setTaskId(Long.parseLong(taskId));
			// 获取报表节点
			Element reportNode = task.element("Report");
			// 获取所有的Funcs
			Element funcs = reportNode.element("Funcs");
			// 获取所有的Func集合
			List<Element> funcList = funcs.elements("Func");
			for (Element func : funcList) {
				// 报警服务类型
				String alarm_type = func.element("name").getTextTrim();
				alarm.setAlarm_type(URLDecoder.decode(alarm_type, "UTF-8"));
				// 站点
				Element siteNode = func.element("site");
				// 资源地址
				Attribute urlAb = siteNode.attribute("name");
				String url = urlAb.getStringValue();
				alarm.setUrl(URLDecoder.decode(url, "UTF-8"));
				// 时间
				String time = siteNode.attribute("time").getStringValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				alarm.setAlarm_time(sdf.parse(URLDecoder.decode(time, "UTF-8")));
				// 一个漏洞对应一个issuetype
				List<Element> issuetypes = siteNode.elements("issuetype");
				for (Element issuetype : issuetypes) {
					// 名称
					String name = issuetype.attribute("name").getStringValue();
					alarm.setName(URLDecoder.decode(name, "UTF-8"));
					// 等级
					String level = issuetype.attribute("level").getStringValue();
					level = URLDecoder.decode(level, "UTF-8");
					if ("信息".equals(level)) {
						alarm.setLevel(Integer.parseInt(Constants.ALARMLEVEL_LOW));
					} else if ("低危".equals(level)) {
						alarm.setLevel(Integer.parseInt(Constants.ALARMLEVEL_LOW));
					} else if ("中危".equals(level)) {
						alarm.setLevel(Integer.parseInt(Constants.ALARMLEVEL_MIDDLE));
					} else if ("高危".equals(level)) {
						alarm.setLevel(Integer.parseInt(Constants.ALARMLEVEL_HIGH));
					} else if ("紧急".equals(level)) {
						alarm.setLevel(Integer.parseInt(Constants.ALARMLEVEL_HIGH));
					}
					// 建议
					String advice = issuetype.attribute("advice").getStringValue();
					alarm.setAdvice(URLDecoder.decode(advice, "UTF-8"));
					// issuedata
					Element issuedata = issuetype.element("issuedata");
					String content = issuedata.attribute("url").getStringValue();
					alarm.setAlarm_content(URLDecoder.decode(content, "UTF-8"));
					aList.add(alarm);
				}

			}
		} catch (Exception e) {
			logger.info("[获取结果调度]:任务-[" + taskId + "]解析任务结果发生异常!");
			throw new RuntimeException("[获取结果调度]:任务-[" + taskId + "]解析任务结果发生异常!");
		}
		return aList;
	}

}
