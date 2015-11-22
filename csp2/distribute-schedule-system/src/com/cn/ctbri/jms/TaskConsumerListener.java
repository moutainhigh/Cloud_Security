package com.cn.ctbri.jms;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.JMSException; 
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.common.ArnhemWorker;
import com.cn.ctbri.common.Constants;
import com.cn.ctbri.common.ReInternalWorker;
import com.cn.ctbri.common.WebSocWorker;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.DateUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class TaskConsumerListener implements MessageListener{
	static Logger logger = Logger.getLogger(TaskConsumerListener.class.getName());

	@Autowired
	ITaskService taskService;
	
	@Autowired
	IAssetService assetService;
	
	@Autowired
	IServService servService;
	
	@Autowired
    ITaskWarnService taskWarnService;
	
	@Autowired
    IEngineService engineService;

	private String destURL = "";

	private String destIP = "";

	private String tplName ="";
	
	private int scantime = 0;

	public void onMessage(Message message) {  
        try {  
        	//接收到object消息
        	if(message instanceof ObjectMessage){
        		ObjectMessage om = (ObjectMessage) message;
        	    Task taskRe = (Task) om.getObject();
        	    //引擎调度,任务分发
        	    if(taskRe != null){
        	    	schedule(taskRe);
        	    }

        	}  
        } catch (JMSException e) {   
            e.printStackTrace();   
        }   
		
	}
	
	/**
	 * 引擎调度
	 * @param t 任务
	 */
	public void schedule(Task t){
		logger.info("[引擎调度]: 引擎调度开始!");
		
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
            for (EngineCfg engineCfg : engineList) {
            	engineSel = engineCfg;
				if(engineSel.getEngine()==3){
					for(int i=0;i<3;i++){
		                sessionid = WebSocWorker.getSessionId();
		                if(sessionid!=null&&sessionid!=""){
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
				}else{
					for(int i=0;i<3;i++){
			            sessionid = ArnhemWorker.getSessionId(engineSel.getEngine());
			            if(sessionid!=null&&sessionid!=""){
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
				logger.info("[引擎调度]: 引擎状态不可用!");
				json.put("result", "fail");
			}
        	
		} catch (Exception e) {
			logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
			e.printStackTrace();
			try {
				json.put("result", "fail");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//推送结果
		try {
			json.put("orderId", t.getOrder_id());
			json.put("orderTaskId", t.getOrderTaskId());
			json.put("websoc", t.getWebsoc());
			ReInternalWorker.vulnScanCreate(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[引擎调度]: 引擎调度结束!");
	}

	/**
	 * 安恒任务下发
	 * @param t 任务
	 * @param engine 引擎
	 * @return 
	 */
	private Task getArnhem(Task t, String sessionId, EngineCfg engine) {
	    logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]获取状态!");
        
	    if(engine!=null){
	    	String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, String.valueOf(t.getTaskId())+"_"+t.getOrder_id(), engine.getEngine());
	        String status = this.getStatusByResult(resultStr);
	        if("".equals(status)){
	            logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
	            preTaskData(t,engine);
	            try {
	                String lssued = ArnhemWorker.lssuedTask(sessionId, String.valueOf(t.getTaskId())+"_"+t.getOrder_id(), this.destURL, this.destIP, "80",
	                        this.tplName, engine.getEngine());
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
	                    logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
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
	                        if(t.getScanType()==2){
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
	                logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
	            }
	        }else{
	            logger.info("[下发任务调度]: 任务-[" + t.getTaskId() + "]下发失败!，远程存在同名任务请先删除或重新下订单!");
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
                virtual_group_id = WebSocWorker.lssuedTask(sessionId,String.valueOf(t.getTaskId())+"_"+t.getOrder_id(),assets,t.getServiceId());
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
                logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
            
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
                        if(t.getScanType()==2){
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
            logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
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
            switch (rate) {
            case 4:
                this.scantime = 1440;
                break;
            }
        }else if(serviceId == 2){
            switch (rate) {
            case 1:
                this.scantime = 30;
                break;
            }
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
	
	private boolean getStatusBylssued(String lssued) {
        String decode;
        boolean state = false;
        try {
            decode = URLDecoder.decode(lssued, "UTF-8");
            Document document = DocumentHelper.parseText(decode);
            Element result = document.getRootElement();
            Attribute attribute  = result.attribute("value");
            String resultValue = attribute.getStringValue();
            if("Success".equals(resultValue)){
                state = true;
            }
            return state;
        } catch (Exception e) {
            logger.info("[下发任务调度]:解析任务状态失败,远程没有此任务，可以下发!");
            return state;
        }
        
    } 

}
