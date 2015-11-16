package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;

/**
 * 根据任务获取结果的调度类
 * 
 * @author googe
 * 
 */
public class SchedulerResult {

	static Logger logger = Logger.getLogger(SchedulerResult.class.getName());
	
	@Autowired
	private IAlarmService alarmService;

	@Autowired
	private IServService servService;

	@Autowired
	private ITaskService taskService;
	
	@Autowired
    private IEngineService engineService;

	public void getscanResult(int taskId) throws Exception {
		logger.info("[获取结果调度]:任务表扫描开始....");
        
		//根据taskId查询task
		Task task = taskService.findTaskById(taskId);
		
        //获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        long executeTime = task.getExecute_time().getTime();
        long endTime = setDateFormat.parse(temp).getTime();
        long diff = endTime-executeTime;
        
		int engine = 0;
		EngineCfg en = engineService.getEngineById(task.getEngine());
		if(task.getEngine()!=-1){
			engine = en.getEngine();
		}else{
			logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]由于引擎调度失败无法获取结果!");
			return;
		}
                        
	    if(task.getWebsoc()==1){//创宇
            
	        String sessionid = WebSocWorker.getSessionId();
	        boolean flag = WebSocWorker.getProgressByTaskId(sessionid,task.getGroup_id());
	        //检测完成
	        if(flag){
	        	logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]扫描已完成，准备解析结果......");

	            task.setStatus(Integer.parseInt(Constants.TASK_FINISH));
	            task.setEnd_time(setDateFormat.parse(temp));
                task.setScanTime(String.valueOf(diff));
                task.setTaskProgress("101");
                taskService.update(task);
                
				//任务完成后,引擎活跃数减1
                en.setId(task.getEngine());
                engineService.updatedown(en);
	        }else{
                if(task.getStatus()==Integer.parseInt(Constants.TASK_RUNNING)){
                    if(diff/1000<=32){
                        task.setTaskProgress("20");
                    }else if(diff/1000<=63){
                        task.setTaskProgress("40");
                    }else if(diff/1000<=93){
                        task.setTaskProgress("60");
                    }else if(diff/1000<=123){
                        task.setTaskProgress("80");
                    }else if(diff/1000<=153){
                        task.setTaskProgress("90");
                    }else if(diff/1000<=183){
                        task.setTaskProgress("100");
                    }
                    logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]扫描未完成，扫描进度[" + task.getTaskProgress()+"]");
                    taskService.updateTask(task);
                }
	        }
	    }else{//安恒
	        try {
    			// 根据任务id获取任务状态
    			String sessionId = ArnhemWorker.getSessionId(engine);
    			String resultStr = ArnhemWorker.getStatusByTaskId(sessionId,String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),engine);
    			String status = this.getStatusByResult(resultStr);
                List<Alarm> aList = new ArrayList<Alarm>();

    			if ("finish".equals(status)) {// 任务执行完毕
    				logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]扫描已完成，准备解析结果......");
    				/**
    				 * 获取任务结果信息并入库
    				 */
    				//根据taskId查询地区
    				int districtId = taskService.findDistrictIdByTaskId(String.valueOf(task.getTaskId()));
    				// 获取弱点总数
    				String resultCount = ArnhemWorker.getResultCount(sessionId, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),engine);
    				int count = this.getCountByResult(resultCount);
    				for (int i = 0; i <= count/30; i++) {
    					int j = i*30;
    					// 获取任务引擎
        				String reportByTaskID = ArnhemWorker.getReportByTaskID(sessionId, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),
        						getProductByTask(task), j, 30, engine);   //获取全部告警
        				
        				try {
        				    aList = this.getAlarmByRerult(String.valueOf(task.getTaskId()), reportByTaskID,task.getService_id(),districtId);
                        } catch (Exception e) {
                            aList = new ArrayList<Alarm>();
                            continue;
                        }
        				
        				// 插入报警表
        				for (Alarm a : aList) {
        					alarmService.saveAlarm(a);
        				}
        				logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]入库告警数为" + aList.size() + "个!");
					}
    				    				
		    		task.setStatus(Integer.parseInt(Constants.TASK_FINISH));		    		
		    		task.setEnd_time(setDateFormat.parse(temp));
		    		task.setScanTime(String.valueOf(diff));
		    		task.setTaskProgress("101");
					taskService.update(task);

    				//任务完成后,引擎活跃数减1
                    en.setId(task.getEngine());
                    engineService.updatedown(en);

    			}else if("running".equals(status)){
                    
                    // 获取任务进度引擎
                    String progressStr = ArnhemWorker.getProgressByTaskId(sessionId, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),String.valueOf(task.getService_id()),engine);
    				getProgressByRes(task.getTaskId(),progressStr);
	                logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]扫描未完成，扫描进度["+task.getTaskProgress()+"]");

    			}
	        }catch (Exception e) {
		    	logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]异常!");
		    }
    	}	    

		logger.info("[获取结果调度]:任务表扫描结束....");
	}

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
	 * 根据任务结果解析漏洞告警信息
	 * 
	 * @param taskStr
	 * @return
	 */
	private List<Alarm> getAlarmByRerult(String taskId, String taskStr, int serviceId,int districtId) {
		List<Alarm> aList = new ArrayList<Alarm>();
		try {
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
					advice = URLDecoder.decode(advice, "UTF-8");
					if(advice.equals("Web Service SQL盲注")){
					    advice = "SQL";
					}
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
						alarm.setAdvice(advice);
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
						alarm.setServiceId(serviceId);
						alarm.setDistrictId(districtId);
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
	
	/**
	 * 解析弱点数
	 * 
	 * @param resultStr
	 * @return
	 */
	private int getCountByResult(String resultCount) {
		String decode;
		int count = 0;
		try {
			decode = URLDecoder.decode(resultCount, "UTF-8");
			Document document = DocumentHelper.parseText(decode);
			Element result = document.getRootElement();
			Attribute attribute  = result.attribute("value");
			String resultValue = attribute.getStringValue();
			if("Success".equals(resultValue)){
				Attribute allNumber  = result.attribute("allNumber");
				count = Integer.parseInt(allNumber.getStringValue());
			}
			return count;
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
    private Task getProgressByRes(int taskId, String progressStr) {
        Task t = new Task();
        String decode;
        try {
            decode = URLDecoder.decode(progressStr, "UTF-8");
            Document document = DocumentHelper.parseText(decode);
            Element task = document.getRootElement();
            Attribute attribute  = task.attribute("value");
            String resultValue = attribute.getStringValue();
            if("Success".equals(resultValue)){
                String taskState = task.element("TaskState").getTextTrim();
                if(!"other".equals(taskState)||!"wait".equals(taskState)){
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
                    t.setTaskId(taskId);
                    t.setEngineIP(engineIP);
                    t.setTaskProgress(taskProgress);
                    t.setCurrentUrl(currentUrl);
                    t.setIssueCount(issueCount);
                    t.setRequestCount(requestCount);
                    t.setUrlCount(urlCount);
                    t.setAverResponse(averResponse);
                    t.setAverSendCount(averSendCount);
                    if(sendBytes!=null&&!sendBytes.equals("")){
                        String bytes = sendBytes.substring(sendBytes.length()-2,sendBytes.length());
                        String send = sendBytes.substring(0,sendBytes.length()-2);
                        if(bytes.equals("KB")){
                            t.setSendBytes(send);
                        }else if(bytes.equals("MB")){
                            t.setSendBytes(String.valueOf(Long.parseLong(send)*1024));
                        }
                    }
                    
                    if(receiveBytes!=null&&!receiveBytes.equals("")){
                        String re = receiveBytes.substring(receiveBytes.length()-2,receiveBytes.length());
                        String receive = receiveBytes.substring(0,receiveBytes.length()-2);
                        if(re.equals("KB")){
                            t.setReceiveBytes(receive);
                        }else if(re.equals("MB")){
                            t.setReceiveBytes(String.valueOf(Long.parseLong(receive)*1024));
                        }
                    }
                    
//                    t.setSendBytes(sendBytes);
//                    t.setReceiveBytes(receiveBytes);
                    // 插入任务进度信息表
                    taskService.updateTask(t);
                }
            }
        } catch (Exception e) {
            logger.info("[获取结果调度]:任务-[" + String.valueOf(taskId) + "]解析任务进度发生异常!");
            e.printStackTrace();
            throw new RuntimeException("[获取结果调度]:任务-[" + String.valueOf(taskId) + "]解析任务进度发生异常!");
        }
        return t;
    }

}

