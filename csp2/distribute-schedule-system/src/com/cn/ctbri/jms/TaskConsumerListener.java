package com.cn.ctbri.jms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.common.ReInternalWorker;
import com.cn.ctbri.common.SouthAPIWorker;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.LogInfo;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.logger.CSPLoggerAdapter;
import com.cn.ctbri.logger.CSPLoggerConstant;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.ILoggerService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.LoggerUtil;
import com.cn.ctbri.util.PreDateUtil;

public class TaskConsumerListener implements MessageListener,Runnable{
	static Logger logger = Logger.getLogger(TaskConsumerListener.class.getName());

	@Autowired
	ITaskService taskService;
	
	@Autowired
	IServService servService;
	
	@Autowired
    ITaskWarnService taskWarnService;
	
	@Autowired
    IEngineService engineService;
	
	@Autowired
    ILoggerService loggerService;

	private String destURL = "";

	private String destIP = "";

	private String tplName ="";
	
	private int scantime = 0;
	
	private Task task;

	/**
	 * 引擎编号r 
	 */
	
	private static String cpu_usageWeight;
	private static String memory_usageWeight;
	private static String disk_usageWeight;
	private static String load_usageWeight;
	
	static {
		try {
			Properties p = new Properties();
			p.load(TaskConsumerListener.class.getClassLoader().getResourceAsStream("engineConfig.properties"));
			cpu_usageWeight = p.getProperty("cpu_usageWeight");
			memory_usageWeight = p.getProperty("memory_usageWeight");
			disk_usageWeight = p.getProperty("disk_usageWeight");
			load_usageWeight = p.getProperty("load_usageWeight");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public TaskConsumerListener() {
	}
	
	
	public TaskConsumerListener(Task task, ITaskService taskService, IServService servService, ITaskWarnService taskWarnService,IEngineService engineService,ILoggerService loggerService) {
		this.task = task;
		this.taskService = taskService;
		this.servService = servService;
		this.taskWarnService = taskWarnService;
		this.engineService = engineService;
		this.loggerService = loggerService;
	}

	public void onMessage(Message message) {  
        try {  
        	//接收到object消息
        	if(message instanceof ObjectMessage){
        		ObjectMessage om = (ObjectMessage) message;
        	    Task taskRe = (Task) om.getObject();
        	    //引擎调度,任务分发
        	    if(taskRe != null){
            	    CSPLoggerAdapter.error(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Date="+DateUtils.nowDate()+"0505:" + taskRe.getTaskId());
        	    	TaskConsumerListener taskConsumer = new TaskConsumerListener(taskRe,taskService,servService,taskWarnService,engineService,loggerService);
        	    	Thread thread = new Thread(taskConsumer);
    				thread.start();
        	    }

        	}  
        } catch (JMSException e) {   
            e.printStackTrace();   
        }   
		
	}
	

	public void run() {
		//开始调度
    	schedule(task);		
	} 
	
	/**
	 * 引擎调度
	 * @param t 任务
	 */
	public void schedule(Task t){
		CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[引擎调度]: 引擎调度开始!;User="+null);
		
		JSONObject json = new JSONObject();

		try {
			CSPLoggerAdapter.error(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "**********************************"+t.getOrderTaskId()+"&"+t.getScanMode());
			//查询最优的引擎top3
			Map<String, Object> engineMap = new HashMap<String, Object>();
            engineMap.put("serviceId", t.getServiceId());
            
            //查询引擎编号
            List<EngineCfg> engineList = engineService.findEngineByParam(engineMap);
            
            List<EngineCfg> engineTop3 = getArnhemUsableEngine(engineList, t.getServiceId());
            
            EngineCfg engineSel = new EngineCfg();
            boolean engineStatus = false;
            String sessionid = "";
            String status = "";
            for (EngineCfg engineCfg : engineTop3) {
            	engineSel = engineCfg;
				for(int i=0;i<3;i++){
					status = SouthAPIWorker.getSessionId(engineSel.getEngine_number());
	                if(status.equals("success")){
	                    engineStatus = true;
	                    break;
	                }
	                if(!engineStatus){
	                	continue;
	                }
	            }
				if(!engineStatus){
					continue;
                }else{
                	break;
                }
			}
            
		   
        	if(engineStatus){
        		if(t.getWebsoc()==1){//创宇
    		        t = getWebsoc(t, sessionid, engineSel);
    		    }else if(t.getWebsoc()==2){//引擎调度
                    if(engineSel.getEngine()==3){//创宇
                    	t = getWebsoc(t, sessionid, engineSel);
                    }else{//安恒
                    	t = getArnhem(t, sessionid, engineSel);
                    }
                }else{
                	t = getArnhem(t, sessionid, engineSel);
    		    }
        		json.put("result", "success");
        	}else {
				t.setEngine(-1);
				
				//add by 2017-5-5 异常设置
				int status_code = t.getExceptMark()-1;
				String result = "fail";
				if ( status_code <= 0 && status_code> Integer.parseInt(Constants.TASK_EXCEPTION_FIVE)){
					t.setExceptMark(status_code);
					result = "exception "+status_code+" times";
				}else if(status_code == -5 ){
					t.setStatus(Integer.parseInt(Constants.TASK_FINISH));
					t.setExceptMark(Integer.parseInt(Constants.TASK_EXCEPTION_FIVE));
					result = "exception "+ -5 +" times";
				}
					
				taskService.update(t);
				CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[引擎调度]: 引擎状态不可用!;User="+null);
				json.put("result", result);
				//add by 2017-5-9 返回异常
//				JSONObject taskObject = new JSONObject().fromObject(t);
//				json.put("taskObj", taskObject);
			}
        	
		} catch (Exception e) {
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!;User="+null);
			e.printStackTrace();
			try {
				json.put("result", "fail");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		//推送结果
		try {
			json.put("orderTaskId", t.getOrderTaskId());
			json.put("websoc", t.getWebsoc());
			String result = ReInternalWorker.vulnScanCreate(json.toString());
//			JSONObject jsonObj = new JSONObject().fromObject(result);
//			String state = jsonObj.getString("state");
			if(result.equals("200")){
				t.setWarn(1);
				taskService.update(t);
			}else{
				t.setWarn(0);
				taskService.update(t);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[引擎调度]: 引擎调度结束!;User="+null);
	}

	/**
	 * 安恒任务下发
	 * @param t 任务
	 * @param engine 引擎
	 * @return 
	 */
	private Task getArnhem(Task t, String sessionId, EngineCfg engine) {
	    CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[taskConsumer...]:task-[" + t.getTaskId() + "];User="+null);
	    
	    if(engine!=null){
	    	String resultStr = SouthAPIWorker.getStatusByTaskId(engine.getEngine_number(), String.valueOf(t.getTaskId())+"_"+t.getOrder_id());
	        String status = this.getStatusByResult(resultStr);
	        if("".equals(status)){
	            CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]:任务-[" + t.getTaskId() + "]开始下发!;User="+null);
	            preTaskData(t,engine);
	            try {
	            	String lssued = SouthAPIWorker.disposeScanTask(engine.getEngine_number(), String.valueOf(t.getTaskId())+"_"+t.getOrder_id(), this.destURL, this.destIP, "80", this.tplName);
	                //System.out.println("任务logo"+lssued);
	            	boolean state = this.getStatusBylssued(lssued);
	            	if(state){
                	//if(true){
	                    //任务下发后,引擎活跃数加1
	                    engine.setId(engine.getId());
	                    engineService.update(engine);
	                    
	                    //更新任务状态为running
	                    t.setStatus(Integer.parseInt(Constants.TASK_RUNNING));
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                    TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
	                    TimeZone.setDefault(tz);
	                    Date date = new Date();//获得系统时间.
	                    String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
	                    t.setExecute_time(sdf.parse(nowTime));
	                    t.setEngine(engine.getId());
	                    t.setWebsoc(0);
	                    taskService.update(t);
	                    CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!;User="+null);
	                    
	                    LogInfo logInf = LoggerUtil.addLoginfo("", "", new Date(), "","getArnhem","",LoggerUtil.TASK_SUCCESS);
	                    loggerService.insert(logInf);
	                    
	                    //modify by 2016-8-1
	                    int serviceId = t.getServiceId();
		                if(serviceId==4){//关键字
		                    if(t.getScanMode() == 1){
		                        //下一次扫描时间
		                        Date endTime = t.getEnd_time();
		                        Map<String, Object> paramMap = new HashMap<String, Object>();
		                        if(t.getGroup_flag().compareTo(new Date())<0){
		                        	paramMap.put("executeTime", DateUtils.dateToString(new Date()));
		                        }else{
		                        	paramMap.put("executeTime", t.getGroup_flag());
		                        }
		                        paramMap.put("scantime", this.scantime);
		                        Date nextTime = taskService.getNextScanTime(paramMap);
		                        if(nextTime.compareTo(endTime)<=0){
		                            //创建任务
		                            Task task = new Task(); 
		                            task.setExecute_time(nextTime);
		                            task.setStatus(Integer.parseInt(Constants.TASK_START));
		                            //设置订单详情id
//		                            task.setOrder_asset_Id(t.getOrder_asset_Id());
		                            task.setGroup_flag(nextTime);
		                            task.setBegin_time(t.getBegin_time());
		                            task.setEnd_time(t.getEnd_time());
		                            task.setOrderTaskId(t.getOrderTaskId());
		                            task.setServiceId(t.getServiceId());
		                            task.setScanMode(t.getScanMode());
		                            task.setScanType(t.getScanType());
		                            task.setAssetAddr(t.getAssetAddr());
		                            task.setOrder_id(t.getOrder_id());
		                            if(t.getWebsoc()==0){
		                            	task.setWebsoc(0);
		                            }else{
		                            	task.setWebsoc(2);
		                            }
		                            //插入一条任务数据  获取任务id
		                            //add by 2017-5-5
		                            Task ifnew = taskService.findTaskByTaskObj(task);
		                            if(ifnew == null){
		                            	int taskId = taskService.insert(task);
		                            }
		                        }
		                    }
		                }
		                if(serviceId==1||serviceId==2){//漏扫和木马
		                    if(t.getScanMode() == 1){//长期
		                        //下一次扫描时间
		                        Date endTime = t.getEnd_time();
		                        Map<String, Object> paramMap = new HashMap<String, Object>();
		                        Date nextTime = null;
		                        //add by 2017-5-5
		                        if(t.getGroup_flag().compareTo(new Date())<0){
		                        	t.setGroup_flag(new Date());
		                        }
		                        if(t.getScanType()==1){
		                        	//按天
		                        	nextTime = DateUtils.getAfterDate(t.getGroup_flag(),1);
		                        }else if(t.getScanType()==5){
		                        	//按周
		                        	nextTime = DateUtils.getAfterDate(t.getGroup_flag(),7);
		                        }else if(t.getScanType()==6){
		                        	//按月
		                        	nextTime = DateUtils.getAfterMonth(t.getGroup_flag());
		                        }
		                        if(nextTime.compareTo(endTime)<=0){
		                            //创建任务
		                            Task task = new Task(); 
		                            task.setExecute_time(nextTime);
		                            task.setStatus(Integer.parseInt(Constants.TASK_START));
		                            //设置订单详情id
//		                            task.setOrder_asset_Id(t.getOrder_asset_Id());
		                            task.setGroup_flag(nextTime);
		                            task.setBegin_time(t.getBegin_time());
		                            task.setEnd_time(t.getEnd_time());
		                            task.setOrderTaskId(t.getOrderTaskId());
		                            task.setServiceId(t.getServiceId());
		                            task.setScanMode(t.getScanMode());
		                            task.setScanType(t.getScanType());
		                            task.setAssetAddr(t.getAssetAddr());
		                            task.setOrder_id(t.getOrder_id());
		                            if(t.getWebsoc()==0){
		                            	task.setWebsoc(0);
		                            }else{
		                            	task.setWebsoc(2);
		                            }
		                            //add by 2017-5-5
		                            Task ifnew = taskService.findTaskByTaskObj(task);
		                            if(ifnew == null){
		                            	int taskId = taskService.insert(task);
		                            }
		                        }
		                    }
		                }
	                }else{
	                	
	                }
	                
	            } catch (Exception e) {
	            	e.printStackTrace();
	            	CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!;User="+null);
	            }
	        }else if(!"".equals(status)&&t.getEngine()==-1){
	        	SouthAPIWorker.removeTask(engine.getEngine_number(), String.valueOf(t.getTaskId())+"_"+t.getOrder_id());
	        }else{
	        	CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!;User="+null);
	        }
	    }else{
            t.setEngine(-1);
            //add by 2017-5-5 异常设置
			int status_code = t.getExceptMark()-1;
			String result = "fail";
			if ( status_code <= 0 && status_code> Integer.parseInt(Constants.TASK_EXCEPTION_FIVE)){
				t.setExceptMark(status_code);
				result = "exception "+status_code+" times";
			}else if(status_code == -5 ){
				task.setStatus(Integer.parseInt(Constants.TASK_FINISH));
				t.setExceptMark(Integer.parseInt(Constants.TASK_EXCEPTION_FIVE));
				result = "exception "+ -5 +" times";
			}
				
            taskService.update(t);
        }
        return t;
    }
	
	
	private Task getWebsoc(Task t, String sessionId, EngineCfg engine) {
		String[] assets = new String[1];
    	assets[0] = t.getAssetAddr();
        //websoc任务信息设置接口参数
        preTaskDatasoc(t);
        try {
            //下发任务,返回任务组id
            String virtual_group_id = "";
            if(engine!=null){
                //下发任务
            	virtual_group_id = SouthAPIWorker.disposeScanTask(engine.getEngine_number(), String.valueOf(t.getTaskId())+"_"+t.getOrder_id(), t.getAssetAddr(), "", "", this.tplName);
                //任务下发后,引擎活跃数加1
                engine.setId(engine.getId());
                engineService.update(engine);
                //更新任务状态为running
                t.setStatus(Integer.parseInt(Constants.TASK_RUNNING));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
                TimeZone.setDefault(tz);
                Date date = new Date();//获得系统时间.
                String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
                t.setExecute_time(sdf.parse(nowTime));
                t.setGroup_id(virtual_group_id);
                //下发到创宇引擎，7
                t.setEngine(engine.getId());
                t.setWebsoc(1);
                taskService.update(t);
                CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!;User="+null);
            
                int serviceId = t.getServiceId();
                if(serviceId==2||serviceId==3||serviceId==4||serviceId==5){
                    if(t.getScanMode()==1){
                        //下一次扫描时间
                        Date endTime = t.getEnd_time();
                        Map<String, Object> paramMap = new HashMap<String, Object>();
                        paramMap.put("executeTime", t.getGroup_flag());
                        paramMap.put("scantime", this.scantime);
                        Date nextTime = taskService.getNextScanTime(paramMap);
                        if(nextTime.compareTo(endTime)<=0){
                            //创建任务
                            Task task = new Task(); 
                            task.setExecute_time(nextTime);
                            task.setStatus(Integer.parseInt(Constants.TASK_START));
                            //设置订单详情id
                            task.setOrder_asset_Id(t.getOrder_asset_Id());
                            task.setGroup_flag(nextTime);
                            if(t.getWebsoc()==1){
                            	task.setWebsoc(1);
                            }else{
                            	task.setWebsoc(2);
                            }
//                            task.setWebsoc(1);
                            //插入一条任务数据  获取任务id
                            int taskId = taskService.insert(task);
                        }
                    }
                }
                if(serviceId==1){
                    if(t.getScanMode()==1){
                        //下一次扫描时间
                        Date endTime = t.getEnd_time();
                        Map<String, Object> paramMap = new HashMap<String, Object>();
                        Date nextTime = null;
                        if(t.getScanType()==1){
                        	nextTime = DateUtils.getAfterDate(t.getGroup_flag(),1);
                        }else if(t.getScanType()==2){
                        	nextTime = DateUtils.getAfterDate(t.getGroup_flag(),7);
                        }else if(t.getScanType()==3){
                        	nextTime = DateUtils.getAfterMonth(t.getGroup_flag());
                        }
                        if(nextTime.compareTo(endTime)<=0){
                            //创建任务
                            Task task = new Task(); 
                            task.setExecute_time(nextTime);
                            task.setStatus(Integer.parseInt(Constants.TASK_START));
                            //设置订单详情id
                            task.setOrder_asset_Id(t.getOrder_asset_Id());
                            task.setGroup_flag(nextTime);
                            if(t.getWebsoc()==1){
                            	task.setWebsoc(1);
                            }else{
                            	task.setWebsoc(2);
                            }
                            //插入一条任务数据  获取任务id
                            int taskId = taskService.insert(task);
                        }
                    }
                }
            }else{
                t.setEngine(-1);
                taskService.update(t);
            }
        } catch (Exception e) {
            CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!;User="+null);
        }
        return t;
    }
	
	
	/**
	 * 根据任务信息设置接口参数
	 * 
	 * @param task
     * @param engine 
	 */
	private void preTaskData(Task t, EngineCfg engine) {
		// 获取此任务的资产信息
		if (t.getAssetAddr() != null && !t.getAssetAddr().equals("")
				&& !isboolIp(t.getAssetAddr())) { // 判断地址是否是ip
			this.destURL = t.getAssetAddr();
		} else {
			this.destIP = t.getAssetAddr();
		}
		// 获取此任务的服务模版名称
		Serv service = servService.findById(t.getServiceId());
		if (service.getModule_name() != null
				&& !service.getModule_name().equals("")) {
			this.tplName = service.getModule_name();
		}
		// 扫描频率
		int rate = t.getScanType();
		if (Constants.SERVICE_KYXJCFW.equals(this.tplName)) {

			switch (rate) {
			case 1:
				this.tplName = Constants.TPL_KYXJCFU_10M
						+ engine.getEngine_name();
				this.scantime = 10;
				break;
			case 2:
				this.tplName = Constants.TPL_KYXJCFU_30M
						+ engine.getEngine_name();
				this.scantime = 30;
				break;
			case 3:
				this.tplName = Constants.TPL_KYXJCFU_1H
						+ engine.getEngine_name();
				this.scantime = 60;
				break;
//			case 4:
//				this.tplName = Constants.TPL_KYXJCFU_2H
//						+ engine.getEngine_name();
//				this.scantime = 120;
//				break;
			}

		} else if (Constants.SERVICE_WYCGJCFW.equals(this.tplName)) {
			switch (rate) {
			case 2:
				this.tplName = Constants.TPL_WYCGJCFW_30M2
						+ engine.getEngine_name();
				this.scantime = 30;
				break;
			case 3:
				this.tplName = Constants.TPL_WYCGJCFW_1H2
						+ engine.getEngine_name();
				this.scantime = 60;
				break;
			case 4:
				this.tplName = Constants.TPL_WYCGJCFW_1D2
						+ engine.getEngine_name();
				this.scantime = 1440;
				break;
			}
		} else if (Constants.SERVICE_GJZJCFW.equals(this.tplName)) {
			switch (rate) {
			case 2:
				this.tplName = Constants.SERVICE_GJZJCFW
						+ engine.getEngine_name();
				this.scantime = 30;
				break;
			case 3:
				this.tplName = Constants.SERVICE_GJZJCFW
						+ engine.getEngine_name();
				this.scantime = 60;
				break;
			case 4:
				this.tplName = Constants.SERVICE_GJZJCFW
						+ engine.getEngine_name();
				this.scantime = 1440;
				break;
			default:
				this.tplName = Constants.SERVICE_GJZJCFW
						+ engine.getEngine_name();
				break;
			}
		} else if (Constants.SERVICE_EYDMJCFW.equals(this.tplName)) {//木马改成每周每月，执行
			switch (rate) {
			case 1:
				this.tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				this.scantime = 30;
				break;
			case 2:
				this.tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				this.scantime = 60;
				break;
			case 3:
				this.tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				this.scantime = 120;
				break;
			case 4:
				this.tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				this.scantime = 1440;
				break;
			default:
				this.tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				break;
			}
		} else if (Constants.SERVICE_LDSMFW.equals(this.tplName)) {
			this.tplName = Constants.SERVICE_LDSMFW + engine.getEngine_name();
		}

	}
	
	
	/**
     * websoc任务信息设置接口参数
     * 
     * @param task
     */
    private void preTaskDatasoc(Task t) {
        //获取扫描频率
    	int rate = t.getScanType();
    	int serviceId = t.getScanType();
        if(serviceId == 5){
        	this.tplName = "可用性监测模板";
            switch (rate) {
            case 1:
                this.scantime = 10;
                break;
            case 2:
                this.scantime = 30;
                break;
            case 3:
                this.scantime = 60;
                break;
            case 4:
                this.scantime = 120;
                break;
            }
        }else if(serviceId == 3){
        	this.tplName = "网页篡改监测模板";
            switch (rate) {
            case 1:
                this.scantime = 30;
                break;
            case 2:
                this.scantime = 60;
                break;
            case 3:
                this.scantime = 120;
                break;
            case 4:
                this.scantime = 1440;
                break;
            }
        }else if(serviceId == 4){
        	this.tplName = "关键字监测模板";
            switch (rate) {
            case 4:
                this.scantime = 1440;
                break;
            }
        }else if(serviceId == 2){
        	this.tplName = "恶意代码监测模板";
            switch (rate) {
            case 1:
                this.scantime = 30;
                break;
            }
        }else{
        	this.tplName = "漏洞扫描模板";
        }
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
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]:解析任务状态失败,远程没有此任务，可以下发!;User="+null);
			return "";
		}
		
	}
	
	private boolean getStatusBylssued(String lssued) {
        boolean state = false;
        try {
        	String status = JSONObject.fromObject(lssued).getString("status");
            if("Success".equals(status)){
                state = true;
            }
            return state;
        } catch (Exception e) {
        	CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]:解析任务状态失败!;User="+null);
            return state;
        }
        
    }
	
	Map<String, Double> arnhemEngineMap = null;
	
	//获取Arnhem支持的引擎top3
	public List<EngineCfg> getArnhemUsableEngine(List<EngineCfg> engineList,int sessionId) {
		//最优引擎top3
		List<EngineCfg> engineTop3 = new ArrayList<EngineCfg>();
		
		arnhemEngineMap = new HashMap<String,Double>();
	    
		for (int i = 0; i < engineList.size(); i++) {		    
    		//获取引擎状态json串
            String resultStr = SouthAPIWorker.getEngineStatRate(engineList.get(i).getEngine_number());

            String enNum = engineList.get(i).getEngine_number();
            //解析引擎设备参数，返回负载值
            getLoadForEngine(resultStr, enNum);
          
    	}

		
		//排序
		List<Map.Entry<String, Double>> loads =
		    new ArrayList<Map.Entry<String, Double>>(arnhemEngineMap.entrySet());
		
		Collections.sort(loads, new Comparator<Map.Entry<String, Double>>() {   
		    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
		    	if ((o1.getValue() - o2.getValue())>0)  
		            return 1;  
		          else if((o1.getValue() - o2.getValue())==0)  
		            return 0;  
		          else   
		            return -1;
		    }
		}); 
		
		
		//获取任务下发的引擎
		for(int i = 0; i < loads.size(); i++){
			String engineIP = loads.get(i).getKey();
			//根据ip获取引擎
			EngineCfg engine = engineService.findEngineIdbyIP(engineIP);
			if(engine.getEngine_capacity().contains(String.valueOf(sessionId)) && engineTop3.size()<3){
				engineTop3.add(engine);
			}
		}
		return engineTop3;
	}
	

	/**
	 * 解析引擎，返回负载值
	 * @param resultStr 返回的json串
	 * @param activity 运行中的任务数
	 * @param maxTask 引擎最大承载任务数
	 * @return
	 */
	private Map<String,Double> getLoadForEngine(String resultStr, String enNum){
		Map<String,Double> loadMap = new HashMap<String,Double>();
		double load = 0;
        try {
        	Map<String, Object> engineMap = new HashMap<String, Object>();
            engineMap.put("engine_number", enNum);
            String status = JSONObject.fromObject(resultStr).getString("status");
            if("success".equals(status)){
            	//status=1 ,平台引擎可用
                engineMap.put("status", 1);
                engineService.updateStatus(engineMap);
            	//解析引擎list
				JSONArray jsonList= JSONObject.fromObject(resultStr).getJSONArray("StatRateList"); 
				for (int i = 0; i < jsonList.size(); i++) {
					JSONObject jsonObject = jsonList.getJSONObject(i); 
					String ip = jsonObject.getString("ip");
					//根据ip查询当前任务数和最大任务数
					EngineCfg engine = engineService.findEngineIdbyIP(ip);
					if(engine!=null){
						if(!jsonObject.get("memoryUsage").equals("null") && jsonObject.get("cpuUsage") != null && jsonObject.get("diskUsage") != null){
							double memory_usage = jsonObject.getDouble("memoryUsage")/100;
							double cpu_usage = jsonObject.getDouble("cpuUsage")/100;
							double disk_usage = jsonObject.getDouble("diskUsage")/100;
							
							int activity = engine.getActivity();
							int maxTask = engine.getMaxTask();
							double load_usage = (double)activity/(double)maxTask;
							
							double cpu_usageWeightD = Double.parseDouble(cpu_usageWeight);
							double memory_usageWeightD = Double.parseDouble(memory_usageWeight);
							double disk_usageWeightD = Double.parseDouble(disk_usageWeight);
		                    double load_usageWeightD = Double.parseDouble(load_usageWeight);
		                    
		                    load = cpu_usageWeightD*cpu_usage + memory_usageWeightD*memory_usage + disk_usageWeightD*disk_usage + load_usage*load_usageWeightD;
		                    if(load!=0){
		                    	arnhemEngineMap.put(ip, load);
		                    }
						}else{
							logger.info(ip+"引擎处于停止或者异常的状态!");
						}
					}
				}
			}else{
				//status=0 ,平台引擎不可用
				engineMap.put("status", 0);
				engineService.updateStatus(engineMap);
				logger.info("当前设备处于停止或者异常的状态!");
			}
        } catch (Exception e) {
            logger.info("解析引擎状态失败!");
            
        }
		return loadMap;
	}
	

}
