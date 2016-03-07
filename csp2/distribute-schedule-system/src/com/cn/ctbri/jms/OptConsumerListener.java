package com.cn.ctbri.jms;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.common.ArnhemWorker;
import com.cn.ctbri.common.Constants;
import com.cn.ctbri.common.ReInternalWorker;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.logger.CSPLoggerAdapter;
import com.cn.ctbri.logger.CSPLoggerConstant;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;

public class OptConsumerListener  implements MessageListener,Runnable{
	//日志对象
	private Logger logger = Logger.getLogger(OptConsumerListener.class.getName());
	@Autowired
    ITaskWarnService taskWarnService;

	@Autowired
	private ITaskService taskService;
	
	@Autowired
    private IEngineService engineService;
	
	private Task task;
    
	private TextMessage tm;
	
	public OptConsumerListener() {
	}
	
	public OptConsumerListener(TextMessage tm, ITaskService taskService, ITaskWarnService taskWarnService,IEngineService engineService) {
		this.tm = tm;
		this.taskService = taskService;
		this.taskWarnService = taskWarnService;
		this.engineService = engineService;
	}

	public void onMessage(Message message) {
		JSONObject json = new JSONObject();
        try {  
        	if(message instanceof TextMessage){
        		TextMessage tmessage = (TextMessage) message;
        	    //任务进度获取
        	    if(tmessage != null){
        	    	OptConsumerListener optConsumer = new OptConsumerListener(tmessage,taskService,taskWarnService,engineService);
        	    	Thread thread = new Thread(optConsumer);
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
        //推送结果
	}
	

	public void run() {
		try {
			setOpt(tm);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * 任务获取
	 * @param task 任务
	 */
	public void setOpt(TextMessage tm) throws Exception {
		logger.info("[订单操作]:订单操作开始....");
        
		JSONObject json = new JSONObject();
		String orderId = tm.toString();
		List<Task> tlist = taskService.findTaskByOrderId(orderId);
		//循环订单下的任务，操作任务
		for (Task task : tlist) {
			int engine = 0;
			EngineCfg en = engineService.getEngineById(task.getEngine());
			if(task.getEngine()!=0){
				engine = en.getEngine();
			}else{
				engine = 2;
			}
			
		    if(task.getWebsoc()==1){//创宇暂时不能操作
	            
		    }else{
		        try {
	    			logger.info("[订单操作]:任务-[" + task.getTaskId() + "]开始操作状态!");
	    			
	    			// 根据任务id获取任务状态
	    			String sessionId = ArnhemWorker.getSessionId(engine);
	    			String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),engine);
	    			String status = this.getStatusByResult(resultStr);
	    			if ("running".equals(status)) {// 任务执行完毕
	    				// 订单操作
	    				try {
		    				String optStr = ArnhemWorker.stopTask(sessionId, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),engine);
		    				this.getOptByRes(task.getTaskId(),optStr);
		    				json.put("result", "success");
	    				} catch (Exception e) {
//	    					continue;
	    					logger.info("[进度获取]: 失败!");
	    					json.put("result", "fail");
	    				}
	    				logger.info("[获取任务进度调度]:任务-[" + task.getTaskId() + "]进度信息结果已完成入库!");
	    			} else if("finish".equals(status)){
	    			    task.setTaskProgress("101");
	    			    taskService.updateTask(task);
	    			    json.put("result", "success");
	    			} else if(status.contains("not found")){
	    				task.setTaskProgress("101");
	    				task.setStatus(Integer.parseInt(Constants.TASK_FINISH));
	    				taskService.update(task);
	    				json.put("result", "success");
	    				//更新order
//	    				order.setStatus(Integer.parseInt(Constants.ORDERALARM_NO));
//	    				orderService.update(order);
	    			} else {
	    				logger.info("[获取任务进度调度]:任务-[" + task.getTaskId() + "]进度信息结果未完成，等待下次拉取结果~");
	    			}
		        } catch (Exception e) {
//	                  continue;
		        	logger.info("[进度获取]: 失败!");
					json.put("result", "fail");
	            }
			}
		}
		

	    //推送结果
  		try {
  			task.setOrder_id(task.getOrder_id());
			task.setOrderTaskId(task.getOrderTaskId());
//			task = taskService.findTaskByOrderTaskId(task);
  			net.sf.json.JSONObject taskObject = new net.sf.json.JSONObject().fromObject(task);
  			json.put("taskObj", taskObject);
  			ReInternalWorker.vulnScanGetOrderTaskStatus(json.toString());
  		} catch (JSONException e) {
  			e.printStackTrace();
  		}
		logger.info("[获取任务进度调度]:任务进度扫描结束....");
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
     * 根据任务解析任务操作数据
     * 
     * @param task
     * @param progressStr
     * @return
     */
    private Task getOptByRes(int taskId, String optStr) { 
    	Task t = new Task();
    	String decode;
		String state = "";
		try {
			decode = URLDecoder.decode(optStr, "UTF-8");
			Document document = DocumentHelper.parseText(decode);
			Element result = document.getRootElement();
			Attribute attribute  = result.attribute("value");
			String resultValue = attribute.getStringValue();
			if("Success".equals(resultValue)){
				Element StateNode = result.element("State");
				state = StateNode.getTextTrim();
			}
			return t;
		} catch (Exception e) {
			logger.info("[获取结果调度]:解析任务状态发生异常,远程没有此任务!");
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "解析任务状态发生异常,远程没有此任务!");
			CSPLoggerAdapter.log(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "");
			throw new RuntimeException("[获取结果调度]:解析任务状态发生异常,远程没有此任务!");
		}
    }

}
