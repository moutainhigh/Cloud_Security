package com.cn.ctbri.jms;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

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
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;

public class ProgressConsumerListener  implements MessageListener,Runnable{
	//日志对象
	private Logger logger = Logger.getLogger(ProgressConsumerListener.class.getName());
	@Autowired
    ITaskWarnService taskWarnService;

	@Autowired
	private ITaskService taskService;
	
	@Autowired
    private IEngineService engineService;
	
	private Task task;
    
	public ProgressConsumerListener() {
	}
	
	public ProgressConsumerListener(Task task, ITaskService taskService, ITaskWarnService taskWarnService,IEngineService engineService) {
		this.task = task;
		this.taskService = taskService;
		this.taskWarnService = taskWarnService;
		this.engineService = engineService;
	}

	public void onMessage(Message message) {
		JSONObject json = new JSONObject();

        try {  
        	if(message instanceof ObjectMessage){
        		ObjectMessage om = (ObjectMessage) message;
        	    Task taskRe = (Task) om.getObject();
        	    //任务进度获取
        	    if(taskRe != null){
        	    	ProgressConsumerListener progressConsumer = new ProgressConsumerListener(taskRe,taskService,taskWarnService,engineService);
        	    	Thread thread = new Thread(progressConsumer);
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
			getscanProgress(task);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * 任务获取
	 * @param task 任务
	 */
	public void getscanProgress(Task task) throws Exception {
		logger.info("[获取任务进度调度]:任务表扫描开始....");
        
		JSONObject json = new JSONObject();
		
		int engine = 0;
		EngineCfg en = engineService.getEngineById(task.getEngine());
		if(task.getEngine()!=0){
			engine = en.getEngine();
		}else{
			engine = 2;
		}
	    if(task.getWebsoc()==1){
	        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String temp = setDateFormat.format(Calendar.getInstance().getTime());
            long executeTime = task.getExecute_time().getTime();
            long endTime = setDateFormat.parse(temp).getTime();
            long diff = endTime-executeTime;
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
                taskService.updateTask(task);
            }
            
	    }else{
	        try {
    			logger.info("[获取任务进度调度]:任务-[" + task.getTaskId() + "]开始获取进度状态!");
    			
    			// 根据任务id获取任务状态
    			String sessionId = ArnhemWorker.getSessionId(engine);
    			String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),engine);
    			String status = this.getStatusByResult(resultStr);
    			if ("running".equals(status)) {// 任务执行完毕
    				logger.info("[获取任务进度调度]:任务-[" + task.getTaskId() + "]扫描已完成，准备解析任务进度......");
    				/**
    				 * 获取任务进度信息并入库
    				 */
    				// 获取任务进度引擎
    				try {
	    				String progressStr = ArnhemWorker.getProgressByTaskId(sessionId, String.valueOf(task.getTaskId())+"_"+task.getOrder_id(),String.valueOf(task.getServiceId()),engine);
	    				this.getProgressByRes(task.getTaskId(),progressStr);
	    				json.put("result", "success");
    				} catch (Exception e) {
//    					continue;
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
//    				order.setStatus(Integer.parseInt(Constants.ORDERALARM_NO));
//    				orderService.update(order);
    			} else {
    				logger.info("[获取任务进度调度]:任务-[" + task.getTaskId() + "]进度信息结果未完成，等待下次拉取结果~");
    			}
	        } catch (Exception e) {
//                  continue;
	        	logger.info("[进度获取]: 失败!");
				json.put("result", "fail");
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
