package com.cn.ctbri.webservice;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IOrderTaskService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.Respones;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-10-19
 * 描        述：  接收内部WebService请求接口
 * 版        本：  1.0
 */
@Component
@Path("internalapi")
public class ReInternalWebService {
	//日志对象
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
    ITaskWarnService taskWarnService;
	@Autowired
    IAlarmService alarmService;	
	@Autowired
    private IOrderTaskService orderTaskService;
	@Autowired
    private ITaskService taskService;
	@Autowired
    private IOrderService orderService;
	
	//主动告警
	@POST
    @Path("/vulnscan/re_orderTask")
	@Produces(MediaType.APPLICATION_JSON) 
	public String VulnScan_Create_orderTask(String dataJson) {
		try {
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			//订单id
			String orderId = jsonObj.getString("orderId");
			//订单任务id
			String orderTaskId = jsonObj.getString("orderTaskId");
			//是否成功下发
			String result = jsonObj.getString("result");
			//websoc
			int websoc = Integer.parseInt(jsonObj.getString("websoc"));
			Respones r = new Respones();
			if(result.equals("success")){
				OrderTask orderTask = new OrderTask();
		        orderTask.setOrderId(orderId);
		        orderTask.setWebsoc(websoc);
		        orderTask.setTask_status(2);
		        //更新orderTask数据库
		        orderTaskService.update(orderTask);
		        //创建任务信息
		        Task t = new Task();
				t.setOrderTaskId(orderTaskId);
				taskService.insert(t);
				r.setState("201");
				//add by tangxr 2016-01-13
				Order order = new Order();
				order.setId(orderId);
				order.setStatus(4);
				order.setWebsoc(websoc);
				orderService.update(order);
			}else{
				r.setState("404");
			}
			net.sf.json.JSONArray json = new net.sf.json.JSONArray().fromObject(r);
	        return json.toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Respones r = new Respones();
			r.setState("404");
			net.sf.json.JSONArray json = new net.sf.json.JSONArray().fromObject(r);
	        return json.toString();
		}
    	    	
		
    }
	
	
	//主动告警
	@POST
    @Path("/vulnscan/re_orderTask_status")
	@Produces(MediaType.APPLICATION_JSON) 
	public String VulnScan_Get_OrderTaskStatus(String dataJson) {
		Respones r = new Respones();
		try {
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			String status = jsonObj.getString("status");
			//获取进度是否成功
			if(status.equals("success")){
				JSONObject taskObj = jsonObj.getJSONObject("taskObj");
				int taskId = taskObj.getInt("taskId");
				String orderTaskId = taskObj.getString("orderTaskId");
				String engineIP = taskObj.getString("engineIP");
	            String taskProgress = taskObj.getString("taskProgress");
	            String currentUrl = taskObj.getString("currentUrl");
	            String issueCount = taskObj.getString("issueCount");
	            String requestCount = taskObj.getString("requestCount");
	            String urlCount = taskObj.getString("urlCount");
	            String averResponse = taskObj.getString("averResponse");
	            String averSendCount = taskObj.getString("averSendCount");
	            String sendBytes = taskObj.getString("sendBytes");
	            String receiveBytes = taskObj.getString("receiveBytes");
				
				Task t = taskService.findByOrderTaskId(orderTaskId);
				if(t!=null){
					//更新数据库
					t.setOrderTaskId(orderTaskId);
	                t.setEngineIP(engineIP);
	                t.setTaskProgress(taskProgress);
	                t.setCurrentUrl(currentUrl);
	                t.setIssueCount(issueCount);
	                t.setRequestCount(requestCount);
	                t.setUrlCount(urlCount);
	                t.setAverResponse(averResponse);
	                t.setAverSendCount(averSendCount);
			        taskService.updateTask(t);
				}else{
					t = new Task();
					t.setTaskId(taskId);
					t.setOrderTaskId(orderTaskId);
					t.setStatus(2);
	                t.setEngineIP(engineIP);
	                t.setTaskProgress(taskProgress);
	                t.setCurrentUrl(currentUrl);
	                t.setIssueCount(issueCount);
	                t.setRequestCount(requestCount);
	                t.setUrlCount(urlCount);
	                t.setAverResponse(averResponse);
	                t.setAverSendCount(averSendCount);
	                
					taskService.insert(t);
				}	
			}
			r.setState("201");
			JSONObject json = new JSONObject().fromObject(r);
	        return json.toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			r.setState("404");
			JSONObject json = new JSONObject().fromObject(r);
	        return json.toString();
		}
    }
	
	
	@POST
    @Path("/vulnscan/re_orderTask_result")
	@Produces(MediaType.APPLICATION_JSON) 
	public String VulnScan_Get_OrderTaskResult(String dataJson) {
		Respones r = new Respones();
		try {
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			String status = jsonObj.getString("status");
			if(status.equals("success")){
				JSONObject taskObj = jsonObj.getJSONObject("taskObj");
				String alarmStr = jsonObj.getString("alarmObj");
				String taskwarnStr = jsonObj.getString("taskwarnObj");
				//订单id
				String orderId = taskObj.getString("order_id");
				//订单id
				String task_status = taskObj.getString("status");
				//任务id
				int taskId = taskObj.getInt("taskId");
				if(alarmStr!=null && !alarmStr.equals("")){
					JSONArray alarmArray = jsonObj.getJSONArray("alarmObj");
					for (Object aObj : alarmArray) {
						JSONObject alarmObj = (JSONObject) aObj;
//						String taskId = alarmObj.getString("taskId");
			            String alarm_time = alarmObj.getString("alarm_time");
			            String url = alarmObj.getString("url");
			            String alarm_type = alarmObj.getString("alarm_type");
			            String name = alarmObj.getString("name");
			            String score = alarmObj.getString("score");
			            String advice = alarmObj.getString("advice");
			            String alarm_content = alarmObj.getString("alarm_content");
			            String keyword = alarmObj.getString("keyword");
			            String serviceId = alarmObj.getString("serviceId");
			            String level = alarmObj.getString("level");
			            
			            Alarm alarm = new Alarm();
						alarm.setTaskId(taskId);
//						alarm.setAlarm_time(DateUtils.stringToDateNYRSFM(alarm_time));
						alarm.setUrl(url);
						alarm.setAlarm_type(alarm_type);
						alarm.setName(name);
						alarm.setScore(score);
						alarm.setAdvice(advice);
						alarm.setLevel(Integer.parseInt(level));
						alarm.setAlarm_content(alarm_content);
						alarm.setKeyword(keyword);
						alarm.setServiceId(Integer.parseInt(serviceId));
						alarmService.saveAlarm(alarm);
					}
				}
				if(taskwarnStr!=null && !taskwarnStr.equals("")){
					JSONArray taskwarnObj = jsonObj.getJSONArray("taskwarnObj");
				}
				
				OrderTask orderTask = new OrderTask();
		        orderTask.setOrderId(orderId);
		        orderTask.setTask_status(3);
		        //更新orderTask数据库
		        orderTaskService.update(orderTask);
		        
		        Task task = new Task();
		        task.setTaskId(taskId);
		        task.setStatus(3);
		        task.setTaskProgress("101");
		        //更新orderTask数据库
		        taskService.update(task);
			}

			r.setState("201");
			JSONObject json = new JSONObject().fromObject(r);
	        return json.toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			r.setState("404");
			JSONObject json = new JSONObject().fromObject(r);
	        return json.toString();
		}
    }
	 
	public static void main(String[] args) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
//		Order order = new Order();
//		order.setId("66");
//		
//		net.sf.json.JSONObject orderObj = net.sf.json.JSONObject.fromObject(order);
//		json.put("orderObj", orderObj);
		
		json.put("ScanMode", 2);
		json.put("targetURL", "");
		json.put("ScanType", "");
		json.put("StartTime", "2015-11-12 09:45:55");
		json.put("EndTime", "");
		json.put("ScanPeriod", 1);
		json.put("ScanDepth", "");
		json.put("MaxPages", "");
		json.put("Stategy", "");
		json.put("CustomManu", "");
		json.put("orderId", "999999");
		json.put("serviceId", 1);
		json.put("websoc", 1);
		json.put("taskTime", "");

	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
	    //检查安全传输协议设置
        Client client = Client.create(config);
        //连接服务器
//        WebResource service = client.resource("http://localhost:8080/dss/rest/internalapi/vulnscan/orderTask");
        WebResource service = client.resource("http://localhost:8080/cspi/rest/internalapi/vulnscan/re_orderTask/93103991");
        
//        WebResource service = client.resource("http://localhost:8080/cspi/rest/openapi/createOrder");
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json);        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses); 

	}
}
