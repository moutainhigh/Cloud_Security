package com.cn.ctbri.jms;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.common.ReInternalWorker;
import com.cn.ctbri.common.SouthAPIWorker;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.logger.CSPLoggerAdapter;
import com.cn.ctbri.logger.CSPLoggerConstant;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.PreDateUtil;

public class ResultConsumerListener  implements MessageListener,Runnable{
	
	@Autowired
	private IAlarmService alarmService;

	@Autowired
    ITaskWarnService taskWarnService;

	@Autowired
	private ITaskService taskService;
	
	@Autowired
    private IEngineService engineService;
	
	@Autowired
    private IServService servService;
	
	private Task task;
    
	public ResultConsumerListener() {
	}

	public ResultConsumerListener(Task task, IAlarmService alarmService, ITaskService taskService, ITaskWarnService taskWarnService,IEngineService engineService) {
		this.task = task;
		this.alarmService = alarmService;
		this.taskService = taskService;
		this.taskWarnService = taskWarnService;
		this.engineService = engineService;
	}

	public void onMessage(Message message) {  
		JSONObject json = new JSONObject();
    	//接收到object消息
		try {  
        	if(message instanceof ObjectMessage){
        		ObjectMessage om = (ObjectMessage) message;
        	    Task taskRe = (Task) om.getObject();
        	    //任务进度获取
        	    if(taskRe != null){
        	    	ResultConsumerListener resultConsumer = new ResultConsumerListener(taskRe,alarmService,taskService,taskWarnService,engineService);
        	    	Thread thread = new Thread(resultConsumer);
        	    	thread.start();
        	    }
        	}  
        } catch (Exception e) {   
            e.printStackTrace();
	        try {
				json.put("result", "fail");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
        } 
	}


	public void run() {
		JSONObject json = new JSONObject();
        try {  
    	    if(task!=null){
				getscanResult(task);
				//根据taskId查询task
//					Task task = taskService.findTaskById(taskId);
				//如果是创宇需查数据库获取结果
				task.setOrder_id(task.getOrder_id());
				task.setOrderTaskId(task.getOrderTaskId());
				task.setStatus(3);
				task = taskService.findTaskById(task.getTaskId());
				
				//获取未结束的任务
				List<Task> taskList = taskService.findTaskByOrderTaskId(task);
				if(taskList.size()>0){
					json.put("taskStatus", "running");
				}else{
					json.put("taskStatus", "finish");
				}
				
				//时间vo
				task.setExecuteTime(DateUtils.dateToString(task.getExecute_time()));
				task.setBeginTime(DateUtils.dateToString(task.getBegin_time()));
				task.setEndTime(DateUtils.dateToString(task.getEnd_time()));
				task.setGroupFlag(DateUtils.dateToString(task.getGroup_flag()));
				if(task.getWebsoc()==1){
					if(task.getServiceId()==5){//可用性检测
						//根据groupId查询alarm表及taskwarn表
						List<TaskWarn> taskwarnList = taskWarnService.findTaskWarnByGroupId(task.getGroup_id());
//						if(taskwarnList!=null && taskwarnList.size()>0){
						if(task.getStatus()==3){
							//返回结果
							JSONArray taskwarnObject = new JSONArray().fromObject(taskwarnList);
							json.put("taskwarnObj", taskwarnObject);
							json.put("alarmObj", "");
							json.put("status", "success");
							ReInternalWorker.vulnScanGetOrderTaskResult(json.toString());
						}else{
							JSONObject taskObject = new JSONObject().fromObject(task);
							json.put("taskObj", taskObject);
							json.put("status", "success");
							ReInternalWorker.vulnScanGetOrderTaskStatus(json.toString());
						}
					}else{//其他
						Map<String,Object> paramMap = new HashMap<String,Object>();
						paramMap.put("group_id", task.getGroup_id());
						List<Alarm> alarmList = alarmService.findAlarmBygroupId(paramMap);
//						if(alarmList!=null && alarmList.size()>0){
						if(task.getStatus()==3){
							//返回结果
							JSONArray alarmObject = new JSONArray().fromObject(alarmList);
							json.put("alarmObj", alarmObject);
							json.put("taskwarnObj", "");
							json.put("status", "success");
							ReInternalWorker.vulnScanGetOrderTaskResult(json.toString());
						}else{
							JSONObject taskObject = new JSONObject().fromObject(task);
							json.put("taskObj", taskObject);
							json.put("status", "success");
							ReInternalWorker.vulnScanGetOrderTaskStatus(json.toString());
						}
					}
				}else{//安恒
					if(task.getServiceId()==5){//可用性检测
						//根据groupId查询alarm表及taskwarn表
						List<TaskWarn> taskwarnList = taskWarnService.findTaskWarnByTaskId(task.getTaskId());
						JSONObject taskObject = new JSONObject().fromObject(task);
						json.put("taskObj", taskObject);
						json.put("status", "success");
						//返回结果
						JSONArray taskwarnObject = new JSONArray().fromObject(taskwarnList);
						json.put("taskwarnObj", taskwarnObject);
						ReInternalWorker.vulnScanGetOrderTaskResult(json.toString());
					}else{//其他
						Map<String,Object> paramMap = new HashMap<String,Object>();
						paramMap.put("taskId", task.getTaskId());
						List<Alarm> alarmList = alarmService.findAlarmByTaskId(paramMap);
//						if(alarmList!=null && alarmList.size()>0){
						if(task.getStatus()==3){
							//返回结果
							JSONArray alarmObject = new JSONArray().fromObject(alarmList);
							json.put("status", "success");
							json.put("taskObj", task);
							json.put("alarmObj", alarmObject);
							ReInternalWorker.vulnScanGetOrderTaskResult(json.toString());
						}else{
							JSONObject taskObject = new JSONObject().fromObject(task);
							json.put("taskObj", taskObject);
							json.put("status", "success");
							ReInternalWorker.vulnScanGetOrderTaskStatus(json.toString());
						}
					}
				}
    	    }
        } catch (Exception e) {   
            e.printStackTrace();
	        try {
//	        	JSONObject taskObject = new JSONObject().fromObject(task);
//				json.put("status", "fail");
//				json.put("code", "404");
//				json.put("message", "获取结果失败");
//				json.put("taskObj", taskObject);
//				ReInternalWorker.vulnScanGetOrderTaskStatus(json.toString());
	        	CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务-[" + String.valueOf(task.getTaskId()) + "]获取结果发生异常!;User="+null);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
        } 
	}
	
	public void getscanResult(Task task) throws Exception {
		CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务表扫描开始....;User="+null);
        
		//根据taskId查询task
//		Task task = taskService.findTaskById(taskId);
		
        //获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        long executeTime = 0;
        long endTime = 0;
        long diff = 0;
        if(task.getExecute_time()!=null){
        	executeTime = task.getExecute_time().getTime();
            endTime = setDateFormat.parse(temp).getTime();
            diff = endTime-executeTime;
        }
        
        
//		int engine = 0;
//		EngineCfg en = engineService.getEngineById(task.getEngine());
//		if(task.getEngine()!=-1){
//			engine = en.getEngine();
//		}else{
//			logger.info("[获取结果调度]:任务-[" + task.getTaskId() + "]由于引擎调度失败无法获取结果!");
//			return;
//		}
		
		String engine = "";
		EngineCfg en = engineService.getEngineById(task.getEngine());
//		if(task.getEngine()!=0){
//			engine = en.getEngine_number();
//		}else{
//			engine = "10001";
//		}
		//大于0,任务已经下发的引擎 modify by tangxr 2016-5-3
		if(task.getEngine()>0){
			engine = en.getEngine_number();
		    if(task.getWebsoc()==1){//创宇
//		        String sessionid = WebSocWorker.getSessionId();
//		        boolean flag = WebSocWorker.getProgressByTaskId(sessionid,task.getGroup_id());
		    	boolean flag = false;
		    	//检测完成
		        if(flag){
		        	CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务-[" + task.getTaskId() + "]扫描已完成，准备解析结果......;User="+null);
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
	                    CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务-[" + task.getTaskId() + "]扫描未完成，扫描进度[" + task.getTaskProgress()+"];User="+null);
	                    taskService.updateTask(task);
	                }
		        }
		    }else{//安恒
		        try {
	    			// 根据任务id获取任务状态
	    			String resultStr = SouthAPIWorker.getStatusByTaskId(engine,String.valueOf(task.getTaskId())+"_"+task.getOrder_id());
	    			String status = this.getStatusByResult(resultStr);
	                List<Alarm> aList = new ArrayList<Alarm>();
	
	    			if ("finish".equals(status) && task.getStatus()!=Integer.parseInt(Constants.TASK_FINISH)) {// 任务执行完毕
	    				//2016-7-26 完成状态，先获取任务进度
	    				// 获取任务进度引擎
	                    String progressStr = SouthAPIWorker.getProgressByTaskId(engine, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),String.valueOf(task.getServiceId()));
	    				getProgressByRes(task.getTaskId(),progressStr);
	    				CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务-[" + task.getTaskId() + "]扫描未完成，扫描进度["+task.getTaskProgress()+"];User="+null);

	    				//再去获取扫描结果
	    				CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务-[" + task.getTaskId() + "]扫描已完成，准备解析结果......;User="+null);
	    				/**
	    				 * 获取任务结果信息并入库
	    				 */
	    				//根据taskId查询地区
	//    				int districtId = taskService.findDistrictIdByTaskId(String.valueOf(task.getTaskId()));
	    				// 获取弱点总数
	    				String resultCount = SouthAPIWorker.getResultCountByTaskID(engine, String.valueOf(task.getTaskId())+"_"+task.getOrder_id());
	    				int count = this.getCountByResult(resultCount);
	    				if(count != 0){
	    					for (int i = 0; i <= count/30; i++) {
	        					int j = i*30;
	        					// 获取任务引擎
	//            				String reportByTaskID = ArnhemWorker.getReportByTaskID(sessionId, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),
	//            						getProductByTask(task), j, 30, engine);   //获取全部告警
	            				String reportByTaskID = SouthAPIWorker.getReportByTaskID(engine, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),
	            						String.valueOf(task.getServiceId()), j, 30);   //获取全部告警
	            				
	            				try {
	            				    aList = this.getAlarmByRerult(String.valueOf(task.getTaskId()), reportByTaskID,task.getServiceId());
	                            } catch (Exception e) {
	                                aList = new ArrayList<Alarm>();
	                                continue;
	                            }
	            				
	            				// 插入报警表
	            				for (Alarm a : aList) {
	            					alarmService.saveAlarm(a);
	            				}
	            				CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务-[" + task.getTaskId() + "]入库告警数为" + aList.size() + "个!;User="+null);
	    					}
	    				}
	    				    				
			    		task.setStatus(Integer.parseInt(Constants.TASK_FINISH));		    		
			    		task.setEnd_time(setDateFormat.parse(temp));
			    		task.setScanTime(String.valueOf(diff));
			    		task.setTaskProgress("101");
						taskService.update(task);
	
	    				//任务完成后,引擎活跃数减1
	                    en.setId(task.getEngine());
	                    engineService.updatedown(en);
	                    
	                    
	                    //add by tang 2017-5-10
	                    //篡改处理开始（本次结束，创建下次任务）
	                    if(task.getServiceId()==3){
	                    	// 获取此任务的服务模版名称
		    	    		Serv service = servService.findById(task.getServiceId());
		    	    		int scantime = PreDateUtil.preTaskData(task, en, service);//预处理
	                        Date endTaskTime = task.getEnd_time();
	                        Map<String, Object> paramMap = new HashMap<String, Object>();
	                        paramMap.put("executeTime", DateUtils.dateToString(new Date()));
	                        paramMap.put("scantime", scantime);
	                        Date nextTime = taskService.getNextScanTime(paramMap);
	                        if(nextTime.compareTo(endTaskTime)<=0){
	                            //创建任务
	                            Task newtask = new Task(); 
	                            newtask.setExecute_time(nextTime);
	                            newtask.setStatus(Integer.parseInt(Constants.TASK_START));
	                            newtask.setGroup_flag(nextTime);
	                            newtask.setBegin_time(task.getBegin_time());
	                            newtask.setEnd_time(task.getEnd_time());
	                            newtask.setOrderTaskId(task.getOrderTaskId());
	                            newtask.setServiceId(task.getServiceId());
	                            newtask.setScanMode(task.getScanMode());
	                            newtask.setScanType(task.getScanType());
	                            newtask.setAssetAddr(task.getAssetAddr());
	                            newtask.setOrder_id(task.getOrder_id());
	                            //插入一条任务数据  获取任务id
	                            Task ifnew = taskService.findTaskByTaskObj(newtask);
	                            if(ifnew == null){
	                            	int taskId = taskService.insert(newtask);
	                            }
	                        }
	                    }
                        //篡改处理结束（本次结束，创建下次任务）
	    			}else if("running".equals(status)){
	                    
	                    // 获取任务进度引擎
	                    String progressStr = SouthAPIWorker.getProgressByTaskId(engine, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),String.valueOf(task.getServiceId()));
	                    getProgressByRes(task.getTaskId(),progressStr);
	    				CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务-[" + task.getTaskId() + "]扫描未完成，扫描进度["+task.getTaskProgress()+"];User="+null);
	
	//    			}else if(status.contains("not found")){
	    			}else if(status.equals("")&&task.getServiceId()!=3){
	    				//modify by 2017-5-8 任务找不到的情况,异常标识设置-5（不会再次下发任务）
	    				task.setTaskProgress("0");
	    				task.setStatus(Integer.parseInt(Constants.TASK_FINISH));
	    				task.setExceptMark(Integer.parseInt(Constants.TASK_EXCEPTION_FIVE));
	    				taskService.update(task);
	    			} 
		        }catch (Exception e) {
		        	e.printStackTrace();
		        	CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务-[" + task.getTaskId() + "]异常!;User="+null);
			    }
	    	}
		}

	    CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务表扫描结束....;User="+null);
	}
	
	

	/**
	 * 根据任务状态结果解析当前任务状态
	 * 
	 * @param resultStr
	 * @return
	 */
	private String getStatusByResult(String resultStr) {
		String state = "";
		try {
			String status = JSONObject.fromObject(resultStr).getString("status");
			if("Success".equals(status)){
				state = JSONObject.fromObject(resultStr).getString("State");
			}else if("Fail".equals(status)){
				String errorMsg = JSONObject.fromObject(resultStr).getString("ErrorMsg");
				if(errorMsg.contains("not found")){
					state = "";
				}
				CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]:远程没有此任务，可以下发!;User="+null);
			}
			return state;
		} catch (Exception e) {
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:解析任务状态发生异常,远程没有此任务!;User="+null);
			throw new RuntimeException("[获取结果调度]:解析任务状态发生异常,远程没有此任务!");
		}
	}

	/**
	 * 根据任务结果解析漏洞告警信息
	 * 
	 * @param taskStr
	 * @return
	 */
	private List<Alarm> getAlarmByRerult(String taskId, String taskStr, int serviceId) {
		List<Alarm> aList = new ArrayList<Alarm>();
		try {
			JSONObject obj = new JSONObject().fromObject(taskStr);
	        String report = obj.getString("Report");
	        JSONObject reportObj = new JSONObject().fromObject(report);
	        String funcs = reportObj.getString("Funcs");
	        JSONObject funcsObj = new JSONObject().fromObject(funcs);
	        String func = funcsObj.getString("Func");
	        JSONObject funcObj = new JSONObject().fromObject(func);
	        String alarm_type = URLDecoder.decode(funcObj.getString("name"), "UTF-8");
	        String site = funcObj.getString("site");
	        JSONObject siteObj = new JSONObject().fromObject(site);
	        String urlAb = URLDecoder.decode(siteObj.getString("@name"), "UTF-8");
	        String time = URLDecoder.decode(siteObj.getString("@time"), "UTF-8");
	        String score = URLDecoder.decode(siteObj.getString("@score"), "UTF-8");
	        String issuetype = siteObj.getString("issuetype");
	        if(getJSONType(issuetype) == JSON_TYPE.JSON_TYPE_OBJECT){
	        	JSONObject issuetypeObj = new JSONObject().fromObject(issuetype);
	        	String name = URLDecoder.decode(issuetypeObj.getString("@name"), "UTF-8");
	        	String level = URLDecoder.decode(issuetypeObj.getString("@level"), "UTF-8");
	        	String advice = URLDecoder.decode(issuetypeObj.getString("@advice"), "UTF-8");
	        	if(advice.equals("Web Service SQL盲注")){
				    advice = "SQL";
				}
	        	String issuedata = issuetypeObj.getString("issuedata");
	        	if(getJSONType(issuedata) == JSON_TYPE.JSON_TYPE_OBJECT){
	        		JSONObject issuedataObj = new JSONObject().fromObject(issuedata);
	        		String url = URLDecoder.decode(issuedataObj.getString("@url"), "UTF-8");
	            	String value = URLDecoder.decode(issuedataObj.getString("@value"), "UTF-8");
	            	//放入alarm中
	            	aList.add(addAlarm(taskId, time, urlAb, alarm_type,
							name, score, advice, level, url, value,
							serviceId));
	        	}else if(getJSONType(issuedata) == JSON_TYPE.JSON_TYPE_ARRAY){
	        		JSONArray issuesArray = issuetypeObj.getJSONArray("issuedata");
	            	for (int j=0;j<issuesArray.size();j++) {
	            		JSONObject issueObj = (JSONObject) issuesArray.get(j);
	                	String url = URLDecoder.decode(issueObj.getString("@url"), "UTF-8");
	                	String value = URLDecoder.decode(issueObj.getString("@value"), "UTF-8");
	                	aList.add(addAlarm(taskId, time, urlAb, alarm_type,
								name, score, advice, level, url, value,
								serviceId));
	            	}
	        	}
	        }else if(getJSONType(issuetype) == JSON_TYPE.JSON_TYPE_ARRAY){
	        	JSONArray issuetypesArray = siteObj.getJSONArray("issuetype");
	        	for (int i=0;i<issuetypesArray.size();i++) {
	            	JSONObject issuetypeObj = (JSONObject) issuetypesArray.get(i);
	            	String name = URLDecoder.decode(issuetypeObj.getString("@name"), "UTF-8");
	            	String level = URLDecoder.decode(issuetypeObj.getString("@level"), "UTF-8");
	            	String advice = URLDecoder.decode(issuetypeObj.getString("@advice"), "UTF-8");
	            	if(advice.equals("Web Service SQL盲注")){
					    advice = "SQL";
					}
	            	String issuedata = issuetypeObj.getString("issuedata");
	            	if(getJSONType(issuedata) == JSON_TYPE.JSON_TYPE_OBJECT){
	            		JSONObject issuedataObj = new JSONObject().fromObject(issuedata);
	            		String url = URLDecoder.decode(issuedataObj.getString("@url"), "UTF-8");
	                	String value = URLDecoder.decode(issuedataObj.getString("@value"), "UTF-8");
	                	aList.add(addAlarm(taskId, time, urlAb, alarm_type,
								name, score, advice, level, url, value,
								serviceId));
	            	}else if(getJSONType(issuedata) == JSON_TYPE.JSON_TYPE_ARRAY){
	            		JSONArray issuesArray = issuetypeObj.getJSONArray("issuedata");
	                	for (int j=0;j<issuesArray.size();j++) {
	                		JSONObject issueObj = (JSONObject) issuesArray.get(j);
	                    	String url = URLDecoder.decode(issueObj.getString("@url"), "UTF-8");
	                    	String value = URLDecoder.decode(issueObj.getString("@value"), "UTF-8");
							aList.add(addAlarm(taskId, time, urlAb, alarm_type,
									name, score, advice, level, url, value,
									serviceId));
	                	}
	            	}
	    		}
	        }
		} catch (Exception e) {
		    e.printStackTrace();
		    CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务-[" + taskId + "]解析任务结果发生异常!;User="+null);
			throw new RuntimeException("[获取结果调度]:任务-[" + taskId + "]解析任务结果发生异常!");
		}
		return aList;
	}
	
	public Alarm addAlarm(String taskId, String time, String urlAb, 
			String alarm_type, String name, String score, String advice, 
			String level, String url, String value, int serviceId) {
		Alarm alarm = new Alarm();
		alarm.setTaskId(Long.parseLong(taskId));
//		alarm.setAlarm_time(DateUtils.stringToDateNYRSFM(time));
		alarm.setAlarm_time(time);
		alarm.setUrl(urlAb);
		alarm.setAlarm_type(alarm_type);
		alarm.setName(name);
		alarm.setScore(score);
		alarm.setAdvice(advice);
		if ("信息".equals(level)) {
			alarm.setLevel(Integer.parseInt(Constants.ALARMLEVEL_MESSAGE));
		} else if ("低危".equals(level)) {
			alarm.setLevel(Integer.parseInt(Constants.ALARMLEVEL_LOW));
		} else if ("中危".equals(level)) {
			alarm.setLevel(Integer.parseInt(Constants.ALARMLEVEL_MIDDLE));
		} else if ("高危".equals(level)) {
			alarm.setLevel(Integer.parseInt(Constants.ALARMLEVEL_HIGH));
		} else if ("紧急".equals(level)) {
			alarm.setLevel(Integer.parseInt(Constants.ALARMLEVEL_HIGHER));
		}
		alarm.setAlarm_content(url);
		if(value.length()>9000){
			value = value.substring(0, 8000);
		}
		alarm.setKeyword(value);
		alarm.setServiceId(serviceId);
		return alarm;
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
		int count = 0;
		try {
			String status = JSONObject.fromObject(resultCount).getString("status");
			if("Success".equals(status)){
				count = Integer.parseInt(JSONObject.fromObject(resultCount).getString("allNumber"));
			}
			return count;
		} catch (Exception e) {
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:解析任务状态发生异常,远程没有此任务!;User="+null);
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
        try {
        	JSONObject obj = JSONObject.fromObject(progressStr);
        	String status = obj.getString("status");
            if("Success".equals(status)){
            	String taskState = obj.getString("TaskState");
                if(!"other".equals(taskState) && !"wait".equals(taskState)){
                    String engineIP = obj.getString("EngineIP");
                    String taskProgress = obj.getString("TaskProgress");
                    String currentUrl = URLDecoder.decode(obj.getString("CurrentUrl"), "UTF-8");
                    String issueCount = obj.getString("IssueCount");
                    String requestCount = obj.getString("RequestCount");
                    String urlCount = obj.getString("UrlCount");
                    String averResponse = obj.getString("AverResponse");
                    String averSendCount = obj.getString("AverSendCount");
                    String sendBytes = obj.getString("SendBytes");
                    String receiveBytes = obj.getString("ReceiveBytes");
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
        	CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[获取结果调度]:任务-[" + String.valueOf(taskId) + "]解析任务进度发生异常!;User="+null);
            e.printStackTrace();
            throw new RuntimeException("[获取结果调度]:任务-[" + String.valueOf(taskId) + "]解析任务进度发生异常!");
        }
        return t;
    }
    
    
	public enum JSON_TYPE{
        /**JSONObject*/    
        JSON_TYPE_OBJECT,
        /**JSONArray*/
        JSON_TYPE_ARRAY,
        /**不是JSON格式的字符串*/
        JSON_TYPE_ERROR
    }
	/***
     * 
     * 获取JSON类型
     * 判断规则
     * 判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文本
     * @param str
     * @return
     */
    public static JSON_TYPE getJSONType(String str){
        if(TextUtils.isEmpty(str)){
            return JSON_TYPE.JSON_TYPE_ERROR;
        }
        
        final char[] strChar = str.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];
                
        if(firstChar == '{'){
            return JSON_TYPE.JSON_TYPE_OBJECT;
        }else if(firstChar == '['){
            return JSON_TYPE.JSON_TYPE_ARRAY;
        }else{
            return JSON_TYPE.JSON_TYPE_ERROR;
        }
    }

}
