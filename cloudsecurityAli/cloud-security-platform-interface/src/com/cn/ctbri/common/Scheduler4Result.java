package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.Random;
import com.cn.ctbri.util.SMSUtils;

/**
 * 根据任务获取结果的调度类
 * 
 * @author googe
 * 
 */
public class Scheduler4Result {

	static Logger logger = Logger.getLogger(Scheduler4Result.class.getName());

	private static String taskpage;
	
	@Autowired
	private IAlarmService alarmService;

	@Autowired
	private IServService servService;

	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
    ITaskWarnService taskWarnService;
	
	@Autowired
    private IAssetService assetService;
    

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
		    if(task.getWebsoc()==1){
		        String sessionid = WebSocWorker.getSessionId();
		        boolean flag = WebSocWorker.getProgressByTaskId(sessionid,task.getGroup_id());
		        if(flag){
		            //获取当前时间
	                SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                String temp = setDateFormat.format(Calendar.getInstance().getTime());
		            task.setStatus(Integer.parseInt(Constants.TASK_FINISH));
                    task.setEnd_time(setDateFormat.parse(temp));
                    long executeTime = task.getExecute_time().getTime();
                    long endTime = setDateFormat.parse(temp).getTime();
                    long diff = endTime-executeTime;
                    task.setScanTime(String.valueOf(diff));
                    task.setTaskProgress("101");
                    taskService.update(task);
                    
                    //更新订单告警状态
                    List<Order> oList = orderService.findOrderByTask(task);
                    Order o = oList.get(0);
                    //获取订单还在执行的任务
                    List<Task> tList = taskService.getTaskStatus(oList.get(0));
                    //告警
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("websoc", task.getWebsoc());
                    paramMap.put("orderId", oList.get(0).getId());
                    List<Alarm> allAlarm = alarmService.findAlarmByOrderId(paramMap);
                    if(tList.size()==0 && oList.size() > 0){
                        if(allAlarm.size() > 0){
                            o.setStatus(Integer.parseInt(Constants.ORDERALARM_YES));
                        }else{
                            o.setStatus(Integer.parseInt(Constants.ORDERALARM_NO));
                        }
                        orderService.update(o);
                    }else{
                        if(allAlarm.size() > 0){
                            o.setStatus(Integer.parseInt(Constants.ORDERALARM_YES_RUNNING));
                        }
                        orderService.update(o);
                    }
		        }
		    }else{
		        try {
        			logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]开始获取状态!");
        			// 根据任务id获取任务状态
        			String sessionId = ArnhemWorker.getSessionId();
        			String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, String.valueOf(task.getTaskId()));
        			String status = this.getStatusByResult(resultStr);
                    List<Alarm> aList;
                    //更新订单告警状态
                    List<Order> oList = orderService.findOrderByTask(task);
                    Order o = oList.get(0);
                    //获取当前时间
                    SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String temp = setDateFormat.format(Calendar.getInstance().getTime());
        			if ("finish".equals(status)) {// 任务执行完毕
        				logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]扫描已完成，准备解析结果......");
        				/**
        				 * 获取任务结果信息并入库
        				 */
        				// 获取任务引擎
        				String reportByTaskID = ArnhemWorker.getReportByTaskID(sessionId, String.valueOf(task.getTaskId()),
        						getProductByTask(task), 0, 500);   //获取全部告警
        				try {
        				    aList = this.getAlarmByRerult(String.valueOf(task.getTaskId()), reportByTaskID);
                        } catch (Exception e) {
                            aList = new ArrayList<Alarm>();
                            continue;
                        }
        				
        				// 插入报警表
        				for (Alarm a : aList) {
        					alarmService.saveAlarm(a);
        				}
        				logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]入库告警数为" + aList.size() + "个!");
        				
        				//获取订单类型
        //				OrderAsset orderAsset = taskService.getTypeByAssetId(task.getOrder_asset_Id());
        //				List<Order> orderList = orderService.findByOrderId(orderAsset.getOrderId());
        //				if(orderList.get(0).getServiceId()!=1){
        //				    if(orderList.get(0).getType()==1){
        //				    	Date endTime = orderList.get(0).getEnd_date();
        //				    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //				    	Date date = new Date();//获得系统时间.
        //					    String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
        //				    	if(sdf.parse(nowTime).compareTo(endTime)>=0){
        				    		task.setStatus(Integer.parseInt(Constants.TASK_FINISH));
        				    		
        				    		task.setEnd_time(setDateFormat.parse(temp));
        				    		long executeTime = task.getExecute_time().getTime();
        				    		long endTime = setDateFormat.parse(temp).getTime();
        				    		long diff = endTime-executeTime;
        				    		task.setScanTime(String.valueOf(diff));
        							taskService.update(task);
        //							ArnhemWorker.removeTask(sessionId, String.valueOf(task.getTaskId()));
        //				    	}
        //				    }
        //				}
        				
                        //获取订单还在执行的任务
                        List<Task> tList = taskService.getTaskStatus(oList.get(0));
                        //告警
                        Map<String, Object> paramMap = new HashMap<String, Object>();
                        paramMap.put("websoc", task.getWebsoc());
                        paramMap.put("orderId", oList.get(0).getId());
                        List<Alarm> allAlarm = alarmService.findAlarmByOrderId(paramMap);
                        if(tList.size()==0 && oList.size() > 0){
                            if(allAlarm.size() > 0){
                                o.setStatus(Integer.parseInt(Constants.ORDERALARM_YES));
                            }else{
                                o.setStatus(Integer.parseInt(Constants.ORDERALARM_NO));
                            }
                            orderService.update(o);
                        }else{
                            if(allAlarm.size() > 0){
                                o.setStatus(Integer.parseInt(Constants.ORDERALARM_YES_RUNNING));
                            }
                            orderService.update(o);
                        }
                        
                        
                        
                        //任务是否有告警信息
                        if(aList!=null && aList.size()>0){//如果有告警发短信通知
                            Order order=null;
                            if(oList!=null && oList.size()>0){
                                order=oList.get(0);
                                List<Linkman> mlist= orderService.findLinkmanById(order.getContactId());
                                Linkman linkman=mlist.get(0);
                                String phoneNumber = linkman.getMobile();//联系方式
                                List<Asset> asset = assetService.findByTask(task);
                                String assetName = asset.get(0).getName();
        //                        int sendFlag=order.getMessage();//是否下发短信
        //                        if(sendFlag!=1){
                                   if(!phoneNumber.equals("") && phoneNumber!=null){
                                    //发短信
                                      SMSUtils smsUtils = new SMSUtils();
                                      smsUtils.sendMessage_warn(phoneNumber,order,assetName,String.valueOf(aList.size()));
                                      order.setMessage(1);
                                      orderService.update(order);
                                   }
        //                        }
                            }
                        }
                        
        				//删除任务   add by txr 2015-03-27
        				//ArnhemWorker.removeTask(sessionId, String.valueOf(task.getTaskId()));
        				logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]告警结果已完成入库，已修改此任务为完成状态!");
        			//可用性
        //			}else if("running".equals(status) && o.getServiceId()==5 && o.getEnd_date().compareTo(setDateFormat.parse(temp))>0){
        //			    //获取告警信息
        //                List<TaskWarn> taskWarnList=taskWarnService.findTaskWarnByOrderId(oList.get(0).getId());
        //                if(taskWarnList.size() > 0){
        //                    o.setStatus(Integer.parseInt(Constants.ORDERALARM_YES_RUNNING));
        //                }else{
        //                    o.setStatus(Integer.parseInt(Constants.ORDERALARM_NO));
        //                }
        //                orderService.update(o);
        //                
        //                //是否有告警信息
        //                if(taskWarnList!=null && taskWarnList.size()>0){//如果有告警发邮件和短信通知
        //                    Order order=null;
        //                    if(oList!=null && oList.size()>0){
        //                        order=oList.get(0);
        //                        List<Linkman> mlist= orderService.findLinkmanById(order.getContactId());
        //                        Linkman linkman=mlist.get(0);
        //                        String phoneNumber = linkman.getMobile();//联系方式
        //                        List<Asset> asset = assetService.findByTask(task);
        //                        String assetName = asset.get(0).getName();
        ////                        int sendFlag=order.getMessage();//是否下发短信或邮件
        ////                        if(sendFlag!=1){
        //                           if(!phoneNumber.equals("") && phoneNumber!=null){
        //                            //发短信
        //                              SMSUtils smsUtils = new SMSUtils();
        //                              smsUtils.sendMessage_warn(phoneNumber,order,assetName,null);
        //                              order.setMessage(1);
        //                              orderService.update(order);
        //                           }
        ////                        }
        //                    }
        //                }
        			}else {
        				logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]扫描未完成，等待下次拉取结果~");
        			}
    		    } catch (Exception e) {
                  continue;
    		    }
        	}	    
		        
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}

	// public void execute(JobExecutionContext context) throws JobExecutionException {
	// JobDetail jobDetail = context.getJobDetail();
	// Task task = (Task) jobDetail.getJobDataMap().get("task");
	// String taskId = String.valueOf(task.getTaskId());
	// logger.info("任务：[" + taskId + "]扫描结果获取中........请等待....");
	// // 根据任务id获取任务状态
	// String sessionId = ArnhemWorker.getSessionId();
	// String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, taskId);
	// String status = this.getStatusByResult(resultStr);
	// List<Alarm> aList;
	// if (Constants.TASK_FINISH.equals(status)) {
	// // 更新任务状态已完成
	// task.setStatus(Integer.parseInt(Constants.TASK_FINISH));
	// taskService.update(task);
	// logger.info("任务：[" + taskId + "]扫描已完成，准备解析结果......");
	// // 任务执行完毕 获取任务结果信息
	// String reportByTaskID = ArnhemWorker.getReportByTaskID(sessionId, taskId, "1", 0, 500);
	// aList = this.getAlarmByRerult(taskId, reportByTaskID);
	// // 插入报警表
	// for (Alarm a : aList) {
	// // alarmService.saveAlarm(a);
	// }
	// logger.info("任务：[" + taskId + "]入库告警数为" + aList + "个!");
	// try {
	// logger.info("任务：[" + taskId + "]告警结果已完成入库，结束此任务的调度!");
	// // 关闭此调度
	// this.scheduler.shutdown();
	// } catch (SchedulerException e) {
	// e.printStackTrace();
	// }
	// } else {
	// logger.info("任务：[" + taskId + "]扫描未完成，等待下次拉取~");
	// }
	// }

	/**
	 * 根据任务状态结果解析当前任务状态
	 * 
	 * @param resultStr
	 * @return
	 */
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
			}
			return state;
		} catch (Exception e) {
			logger.info("[获取结果调度]:解析任务状态发生异常,远程没有此任务!");
			throw new RuntimeException("[获取结果调度]:解析任务状态发生异常,远程没有此任务!");
		}
	}
	
	
	/**
     * 根据任务解析任务进度
     * 
     * @param task
     * @param progressStr
     * @return
     */
    private Task getProgressByRes(Task t, String progressStr) {
        Task aList = new Task();
        String decode;
        try {
            decode = URLDecoder.decode(progressStr, "UTF-8");
            Document document = DocumentHelper.parseText(decode);
            Element task = document.getRootElement();
            Attribute attribute  = task.attribute("value");
            String resultValue = attribute.getStringValue();
            if("Success".equals(resultValue)){
                String taskState = task.element("TaskState").getTextTrim();
                String engineIP = task.element("EngineIP").getTextTrim();
                String taskProgress = task.element("TaskProgress").getTextTrim();
                String currentUrl = task.element("CurrentUrl").getTextTrim();
                String issueCount = task.element("IssueCount").getTextTrim();
                String requestCount = task.element("RequestCount").getTextTrim();
                String urlCount = task.element("UrlCount").getTextTrim();
                String averResponse = task.element("AverResponse").getTextTrim();
                String averSendCount = task.element("AverSendCount").getTextTrim();
                String sendBytes = task.element("SendBytes").getTextTrim();
                String receiveBytes = task.element("ReceiveBytes").getTextTrim();
                Task taskInfo = new Task();
                taskInfo.setTaskId(t.getTaskId());
                taskInfo.setBegin_time(null);
                taskInfo.setEnd_time(null);
                taskInfo.setScanTime(null);
                taskInfo.setIssueCount(issueCount);
                taskInfo.setRequestCount(requestCount);
                taskInfo.setUrlCount(urlCount);
                taskInfo.setAverResponse(averResponse);
                taskInfo.setAverSendCount(averSendCount);
                taskInfo.setSendBytes(sendBytes);
                taskInfo.setReceiveBytes(receiveBytes);
            }
        } catch (Exception e) {
            logger.info("[获取结果调度]:任务-[" + String.valueOf(t.getTaskId()) + "]解析任务进度发生异常!");
            throw new RuntimeException("[获取结果调度]:任务-[" + String.valueOf(t.getTaskId()) + "]解析任务进度发生异常!");
        }
        return aList;
    }

	/**
	 * 根据任务结果解析漏洞告警信息
	 * 
	 * @param taskStr
	 * @return
	 */
	private List<Alarm> getAlarmByRerult(String taskId, String taskStr) {
		List<Alarm> aList = new ArrayList<Alarm>();
		String decode;
		try {
//		    decode = URLDecoder.decode(taskStr, "UTF-8");
//            Document document = DocumentHelper.parseText(decode);
			Document document = DocumentHelper.parseText(taskStr);
			Element task = document.getRootElement();
			// 任务ID节点
			Element taskIDNode = task.element("TaskID");
			// 任务ID值
			// String taskId = taskIDNode.getTextTrim();
			// 获取报表节点
			Element reportNode = task.element("Report");
			// 获取所有的Funcs
			Element funcs = reportNode.element("Funcs");
			// 获取所有的Func集合
			List<Element> funcList = funcs.elements("Func");
			for (Element func : funcList) {
				// 报警服务类型
				String alarm_type = func.element("name").getTextTrim();
				// 站点
				Element siteNode = func.element("site");
				// 资源地址
				Attribute urlAb = siteNode.attribute("name");
				String url = urlAb.getStringValue();
				// 时间
				String time = siteNode.attribute("time").getStringValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 得分
                String score = siteNode.attribute("score").getStringValue();
				// 一个漏洞对应一个issuetype
				List<Element> issuetypes = siteNode.elements("issuetype");
				for (Element issuetype : issuetypes) {
					// 名称
					String name = issuetype.attribute("name").getStringValue();
					// 等级
					String level = issuetype.attribute("level").getStringValue();
					level = URLDecoder.decode(level, "UTF-8");
					// 建议
					String advice = issuetype.attribute("advice").getStringValue();
					// issuedata
					List<Element> issuedatas = issuetype.elements("issuedata");
					for (Element issuedata : issuedatas) {
						Alarm alarm = new Alarm();
						alarm.setTaskId(Long.parseLong(taskId));
						alarm.setAlarm_time(sdf.parse(URLDecoder.decode(time, "UTF-8")));
						alarm.setUrl(URLDecoder.decode(url, "UTF-8"));
						alarm.setAlarm_type(URLDecoder.decode(alarm_type, "UTF-8"));
						alarm.setName(URLDecoder.decode(name, "UTF-8"));
						alarm.setScore(score);
						alarm.setAdvice(URLDecoder.decode(advice, "UTF-8"));
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
						String content = issuedata.attribute("url").getStringValue();
						String keyword = issuedata.attribute("value").getStringValue();
						alarm.setAlarm_content(URLDecoder.decode(content, "UTF-8"));
						alarm.setKeyword(URLDecoder.decode(keyword, "UTF-8"));
						aList.add(alarm);
					}

				}

			}
		} catch (Exception e) {
		    e.printStackTrace();
			logger.info("[获取结果调度]:任务-[" + taskId + "]解析任务结果发生异常!");
			throw new RuntimeException("[获取结果调度]:任务-[" + taskId + "]解析任务结果发生异常!");
		}
		return aList;
	}

	/**
	 * 根据任务获取引擎id
	 * @param task
	 * @return
	 */
	private String getProductByTask(Task task) {
		String productId = "";
		List<HashMap<String, Object>> sList = servService.findByTask(task);
		if(sList != null && sList.size() > 0){
			String SName = (String) sList.get(0).get("module_name");
			if (Constants.SERVICE_LDSMFW.equals(SName)) {
				productId = Constants.PRODUCT_LD;
			} else if (Constants.SERVICE_EYDMJCFW.equals(SName)) {
				productId = Constants.PRODUCT_MM;
			} else if (Constants.SERVICE_WYCGJCFW.equals(SName)) {
				productId = Constants.PRODUCT_CG;
			} else if (Constants.SERVICE_GJZJCFW.equals(SName)) {
				productId = Constants.PRODUCT_GJZ;
			} else if (Constants.SERVICE_KYXJCFW.equals(SName)) {
				productId = Constants.PRODUCT_KYX;
			}
		}else{
			//首页快扫用默认服务引擎：漏洞扫描
			productId = Constants.PRODUCT_LD;
		}
		return productId;
	}

}
