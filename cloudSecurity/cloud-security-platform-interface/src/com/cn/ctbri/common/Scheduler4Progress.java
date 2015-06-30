package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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

import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;

/**
 * 根据任务获取进度的调度类
 * 
 * @author txr
 * 
 */
public class Scheduler4Progress {

	static Logger logger = Logger.getLogger(Scheduler4Progress.class.getName());

	private static String taskpage;

	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
    private IEngineService engineService;
	

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
		logger.info("[获取任务进度调度]:任务表扫描开始....");
		/**
		 * 定时要job任务进度执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", Integer.parseInt(taskpage));
		map.put("status", Integer.parseInt(Constants.TASK_RUNNING));
		// 获取任务表前n条未完成的记录
		List<Task> taskList = taskService.findTask(map);
		logger.info("[获取任务进度调度]:当前等待获取进度的任务有 " + taskList.size() + " 个!");
		for (Task task : taskList) {
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
        			String sessionId = ArnhemWorker.getSessionId();
        			String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, String.valueOf(task.getTaskId()));
        			String status = this.getStatusByResult(resultStr);
        			List<Order> orderList = orderService.findOrderByTask(task);
        			if(orderList!=null&&!orderList.equals("")){
            			Order order = orderList.get(0);
            			if ("running".equals(status)) {// 任务执行完毕
            				logger.info("[获取任务进度调度]:任务-[" + task.getTaskId() + "]扫描已完成，准备解析任务进度......");
            				/**
            				 * 获取任务进度信息并入库
            				 */
            				// 获取任务进度引擎
            				try {
        	    				String progressStr = ArnhemWorker.getProgressByTaskId(sessionId, String.valueOf(task.getTaskId()),String.valueOf(order.getServiceId()));
        	    				this.getProgressByRes(task.getTaskId(),progressStr);
            				} catch (Exception e) {
            					continue;
            				}
            				logger.info("[获取任务进度调度]:任务-[" + task.getTaskId() + "]进度信息结果已完成入库!");
            			} else if("finish".equals(status)){
            			    task.setTaskProgress("101");
            			    taskService.updateTask(task);
            			} else {
            				logger.info("[获取任务进度调度]:任务-[" + task.getTaskId() + "]进度信息结果未完成，等待下次拉取结果~");
            			}
        			}
		        } catch (Exception e) {
	                  continue;
	            }
    		}
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
