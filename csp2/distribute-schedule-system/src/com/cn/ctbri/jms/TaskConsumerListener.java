package com.cn.ctbri.jms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.common.ReInternalWorker;
import com.cn.ctbri.common.SouthAPIWorker;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Logger;
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

public class TaskConsumerListener implements MessageListener,Runnable{
//	static Logger logger = Logger.getLogger(TaskConsumerListener.class.getName());

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
			//查询最优的引擎top3
			Map<String, Object> engineMap = new HashMap<String, Object>();
            engineMap.put("serviceId", t.getServiceId());
            engineMap.put("websoc", t.getWebsoc());
            List<EngineCfg> engineList = engineService.findEngineByParam(engineMap);
            EngineCfg engineSel = new EngineCfg();
            boolean engineStatus = false;
            String sessionid = "";
            String status = "";
            for (EngineCfg engineCfg : engineList) {
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
            
		    //创宇任务下发
        	if(engineStatus){
        		if(t.getWebsoc()==1){
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
				taskService.update(t);
				CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[引擎调度]: 引擎状态不可用!;User="+null);
				json.put("result", "fail");
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
			json.put("orderId", t.getOrder_id());
			json.put("orderTaskId", t.getOrderTaskId());
			json.put("websoc", t.getWebsoc());
			ReInternalWorker.vulnScanCreate(json.toString());
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
	    CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]:任务-[" + t.getTaskId() + "]获取状态;User="+null);
        
	    if(engine!=null){
	    	String resultStr = SouthAPIWorker.getStatusByTaskId(engine.getEngine_number(), String.valueOf(t.getTaskId())+"_"+t.getOrder_id());
	        String status = this.getStatusByResult(resultStr);
	        if("".equals(status)){
	            CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]:任务-[" + t.getTaskId() + "]开始下发!;User="+null);
	            preTaskData(t,engine);
	            try {
	            	String lssued = SouthAPIWorker.disposeScanTask(engine.getEngine_number(), String.valueOf(t.getTaskId())+"_"+t.getOrder_id(), this.destURL, this.destIP, "80", this.tplName);
	                boolean state = this.getStatusBylssued(lssued);
	                if(state){
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
	                    
	                    Logger logInf = LoggerUtil.addLoginfo("", "", new Date(), "","getArnhem","",LoggerUtil.TASK_SUCCESS);
	                    loggerService.insert(logInf);
	                }
	                int serviceId = t.getServiceId();
	                if(serviceId==2||serviceId==3||serviceId==4){
	                    if(t.getScanMode() == 1){
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
	                            if(t.getWebsoc()==0){
	                            	task.setWebsoc(0);
	                            }else{
	                            	task.setWebsoc(2);
	                            }
	                            //插入一条任务数据  获取任务id
	                            int taskId = taskService.insert(task);
	                        }
	                    }
	                }
	                if(serviceId==1){
	                    if(t.getScanMode() == 1){
	                        //下一次扫描时间
	                        Date endTime = t.getEnd_time();
	                        Map<String, Object> paramMap = new HashMap<String, Object>();
	                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
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
	                            if(t.getWebsoc()==0){
	                            	task.setWebsoc(0);
	                            }else{
	                            	task.setWebsoc(2);
	                            }
	                            //插入一条任务数据  获取任务id
	                            int taskId = taskService.insert(task);
	                        }
	                    }
	                }
	            } catch (Exception e) {
	            	e.printStackTrace();
	            	CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!;User="+null);
	            }
	        }else{
	        	CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!;User="+null);
	        }
	    }else{
            t.setEngine(-1);
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
			case 4:
				this.tplName = Constants.TPL_KYXJCFU_2H
						+ engine.getEngine_name();
				this.scantime = 120;
				break;
			}

		} else if (Constants.SERVICE_WYCGJCFW.equals(this.tplName)) {
			switch (rate) {
			case 1:
				this.tplName = Constants.TPL_WYCGJCFW_30M2
						+ engine.getEngine_name();
				this.scantime = 30;
				break;
			case 2:
				this.tplName = Constants.TPL_WYCGJCFW_1H2
						+ engine.getEngine_name();
				this.scantime = 60;
				break;
			case 3:
				this.tplName = Constants.TPL_WYCGJCFW_2H2
						+ engine.getEngine_name();
				this.scantime = 120;
				break;
			case 4:
				this.tplName = Constants.TPL_WYCGJCFW + engine.getEngine_name();
				this.scantime = 1440;
				break;
			}
		} else if (Constants.SERVICE_GJZJCFW.equals(this.tplName)) {
			switch (rate) {
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
		} else if (Constants.SERVICE_EYDMJCFW.equals(this.tplName)) {
			switch (rate) {
			case 1:
				this.tplName = Constants.SERVICE_EYDMJCFW
						+ engine.getEngine_name();
				this.scantime = 30;
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

}
