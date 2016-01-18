package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 扫描任务表的调度类
 * 
 * @author googe
 * 
 */
@Controller
public class SchedulerTask {

	static Logger logger = Logger.getLogger(SchedulerTask.class.getName());

	@Autowired
	ITaskService taskService;
	
	@Autowired
	IServService servService;
	
	@Autowired
    ITaskWarnService taskWarnService;
	
	@Autowired
    IEngineService engineService;

	private String destURL = "";

	private String destIP = "";

	private String tplName ="";
	
	public SchedulerTask() {
		super();
	}

	/**
	 * 引擎调度
	 * @param t 任务
	 */
	public void schedule(Task t){
		logger.info("[引擎调度]: 引擎调度开始!");
		
		JSONObject json = new JSONObject();
	    ClientConfig config = new DefaultClientConfig();
        //检查安全传输协议设置
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource("http://192.168.42.121:8080/dss/rest/internalapi//vulnscan/re_orderTask");

		try {
			//查询最优的引擎top3
			Map<String, Object> engineMap = new HashMap<String, Object>();
			engineMap.put("serviceId", t.getServiceId());
			
			//获取引擎top3
			List<EngineCfg> engineList = EngineWorker.getArnhemUsableEngine(engineMap);
			
			//引擎状态
			boolean engineStatus = false;
			String sessionId = "";
			
			//被选引擎
			EngineCfg engineSel = new EngineCfg();
			if(engineList != null && engineList.size() > 0){
				for (EngineCfg engineCfg : engineList) {
					sessionId = ArnhemWorker.getSessionId(engineCfg.getEngine());
			        if(sessionId!=null){
			            engineStatus = true;
			            engineSel = engineCfg;
			            break;
			        }
				}
			}
			
			
			if(engineStatus)
			{
				 //创宇任务下发
			    if((t.getWebsoc()==1) || (t.getWebsoc()==2 && engineSel.getEngine()==3)){
			        getWebsoc(t, sessionId, engineSel);
			        t.setWebsoc(1);
		            taskService.update(t); 
			    }else if((t.getWebsoc()==2 && engineSel.getEngine()!=3) || (t.getWebsoc()!=1 && t.getWebsoc()!=2)){//安恒
		        	 getArnhem(t, sessionId, engineSel);
		        	 t.setWebsoc(0);
		             taskService.update(t);
			    }
			    json.put("result", "success");
			}else{
	            t.setEngine(-1);
	            taskService.update(t);
			    logger.info("[引擎调度]: 引擎状态不可用!");
			    json.put("result", "fail");
			}
		} catch (Exception e) {
			logger.info("[引擎调度]: 引擎状态不可用!");
			e.printStackTrace();
			try {
				json.put("result", "fail");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		//推送结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json);        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses); 
		logger.info("[引擎调度]: 引擎调度结束!");
	}

	/**
	 * 安恒任务下发
	 * @param t 任务
	 * @param engine 引擎
	 */
	private void getArnhem(Task t, String sessionId, EngineCfg engine) {
	    logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]获取状态!");

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
                    engine.setActivity(engine.getActivity()+1);
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
                    taskService.update(t);
                    logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
                }

            } catch (Exception e) {
                logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
            }
        }else{
            logger.info("[下发任务调度]: 任务-[" + t.getTaskId() + "]下发失败!，远程存在同名任务请先删除或重新下订单!");
        }

}
    private void getWebsoc(Task t, String sessionId, EngineCfg engine) {
    	String[] assets = new String[1];
    	assets[0] = t.getAssetAddr();
        try {
            //下发任务,返回任务组id
            String virtual_group_id = "";

            //下发任务
            virtual_group_id = WebSocWorker.lssuedTask(sessionId,String.valueOf(t.getTaskId())+"_"+t.getOrder_id(),assets,t.getServiceId());
            
            //任务下发后,引擎活跃数加1
            engine.setActivity(engine.getActivity()+1);
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
            
            //下发到创宇引擎
            t.setEngine(engine.getId());
            taskService.update(t);
            logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
            
        } catch (Exception e) {
            logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
        }
    }

    /**
	 * 根据任务信息设置接口参数
	 * 
	 * @param task
     * @param engine 
	 */
	private void preTaskData(Task t, EngineCfg engine) {
		if(t.getAssetAddr()!= null && !t.getAssetAddr().equals("") &&!isboolIp(t.getAssetAddr())){ //判断地址是否是ip
			this.destURL = t.getAssetAddr();
		}else{
			this.destIP = t.getAssetAddr();
		}
		
		// 获取此任务的服务模版名称
		Serv service = servService.findById(t.getServiceId());
		if(service.getModule_name()!=null && !service.getModule_name().equals("")){
			this.tplName = service.getModule_name();
		}
		//扫描频率
		int rate = t.getScanType();

		if(Constants.SERVICE_KYXJCFW.equals(this.tplName)){
			
			switch (rate) {
			case 1:
				this.tplName = Constants.TPL_KYXJCFU_10M +engine.getEngine_name();
				break;
			case 2:
				this.tplName = Constants.TPL_KYXJCFU_30M +engine.getEngine_name();
				break;
			case 3:
				this.tplName = Constants.TPL_KYXJCFU_1H +engine.getEngine_name();
				break;
			case 4:
				this.tplName = Constants.TPL_KYXJCFU_2H +engine.getEngine_name();
				break;
			}
			
		}else if(Constants.SERVICE_WYCGJCFW.equals(this.tplName)){
			switch (rate) {
			case 1:
				this.tplName = Constants.TPL_WYCGJCFW_30M2+engine.getEngine_name();
				break;
			case 2:
				this.tplName = Constants.TPL_WYCGJCFW_1H2+engine.getEngine_name();
				break;
			case 3:
				this.tplName = Constants.TPL_WYCGJCFW_2H2+engine.getEngine_name();;
				break;
			case 4:
				this.tplName = Constants.TPL_WYCGJCFW+engine.getEngine_name();
				break;
			}
		}else if(Constants.SERVICE_GJZJCFW.equals(this.tplName)){
            switch (rate) {
            case 4:
            	this.tplName = Constants.SERVICE_GJZJCFW+engine.getEngine_name();
                break;
            }
        }else if(Constants.SERVICE_EYDMJCFW.equals(this.tplName)){
            switch (rate) {
            case 1:
            	this.tplName = Constants.SERVICE_EYDMJCFW+engine.getEngine_name();
                break;
            }
        }else if(Constants.SERVICE_LDSMFW.equals(this.tplName)){
        	this.tplName = Constants.SERVICE_LDSMFW+engine.getEngine_name();
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

