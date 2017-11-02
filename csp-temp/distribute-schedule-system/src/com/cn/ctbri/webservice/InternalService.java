package com.cn.ctbri.webservice;

import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cn.ctbri.common.SouthAPIWorker;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.logger.CSPLoggerAdapter;
import com.cn.ctbri.logger.CSPLoggerConstant;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IProducerService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Respones;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-10-19
 * 描        述：  接收内部WebService请求
 * 版        本：  1.0
 */
@Component
@Path("internalapi/vulnscan")
public class InternalService {
	@Autowired
    private ITaskService taskService;
	@Autowired
    ITaskWarnService taskWarnService;
	@Autowired
    IAlarmService alarmService;	
	@Autowired
    private IEngineService engineService;
	@Autowired  
    private IProducerService producerService;  
    @Autowired  
    @Qualifier("taskQueueDestination")   
    private Destination taskDestination; 
    @Autowired  
    @Qualifier("resultQueueDestination")   
    private Destination resultDestination;
	
	//创建订单任务
	@POST
    @Path("/orderTask")
	@Produces(MediaType.APPLICATION_JSON) 
	public String VulnScan_Create_orderTask(String dataJson) {
		System.out.print("================================================="+dataJson.toString());  
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObj = JSONObject.fromObject(dataJson);
			//单次，长期
			int scanMode = Integer.parseInt(jsonObj.getString("scanMode"));
			//目标地址，只有一个
			String assetAddr = jsonObj.getString("targetURL");
			//扫描方式（正常、快速、全量）
			String scanType = jsonObj.getString("scanType");
			//开始时间
			String startTime = jsonObj.getString("startTime");
			//结束时间
			String endTime = jsonObj.getString("endTime");
		    //周期
			String scanPeriod = jsonObj.getString("scanPeriod");
			//检测深度
			String scanDepth = jsonObj.getString("scanDepth");
			//最大页面数
			String maxPages = jsonObj.getString("maxPages");
			//策略
			String stategy = jsonObj.getString("stategy");
			//指定厂家设备，可以多个
			JSONArray customArray = jsonObj.getJSONArray("customManus");
			//订单任务ID
			String orderTaskId = jsonObj.getString("orderTaskId");
			//serviceId
			String serviceId = jsonObj.getString("serviceId");
			//合作方
			String partner = jsonObj.getString("partner");
			
			String[] strs = orderTaskId.split("_");  	
			String orderId = strs[1];
			
			int period = 0;
			if(scanPeriod!=null && !scanPeriod.equals("")){
				period = Integer.parseInt(scanPeriod);
			}
			Date begin_date = DateUtils.stringToDateNYRSFM(startTime);
			Date end_date = DateUtils.stringToDateNYRSFM(endTime);
			
			for (int i = 0;  i< customArray.size(); i++) {
				Task task = new Task();
//				task.setExecute_time(task_date);
				task.setGroup_flag(begin_date);
				task.setBegin_time(begin_date);
				task.setEnd_time(end_date);
				task.setWebsoc(Integer.parseInt(customArray.get(i).toString()));
				task.setServiceId(Integer.parseInt(serviceId));
				task.setOrder_id(orderId);
				task.setOrderTaskId(orderTaskId);
				task.setAssetAddr(assetAddr);
				task.setScanMode(scanMode);
				task.setScanType(period);
				task.setPartner(partner);
				task.setOrder_end_time(end_date);
				//插入数据库
				taskService.insert(task);
				
				task = taskService.findTaskByTaskObj(task);
				//将任务放到消息队列里
				producerService.sendMessage(taskDestination, task);
			}
			json.put("code", 201);//返回201表示成功
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
			json.put("message", "创建订单任务失败");
		}
    	System.out.print("================================================="+json.toString());    	
        return json.toString();
    }
	
	
	//订单操作,暂停、启动
	@PUT
    @Path("/orderTask_opt/{orderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_Opt_orderTask(@PathParam("orderId") String orderId, String dataJson) {
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			String opt = jsonObj.getString("opt");
			List<Task> tlist = taskService.findTaskByOrderId(orderId);
			//循环订单下的任务，操作任务
			if(tlist.size()>0){
				if(tlist.get(0).getScanMode()!=1){
					for (Task task : tlist) {
						if(task.getStatus()!=3){
							String engine = "";
							EngineCfg en = engineService.getEngineById(task.getEngine());
							if(task.getEngine()!=0){
								engine = en.getEngine_number();
							}else{
								engine = "10001";
							}
							if(task.getWebsoc()==1){//创宇暂时不能操作
								json.put("result", "websoc");
						    }else{
					        	CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "[订单操作]:任务-[" + task.getTaskId() + "]开始操作状态!");
					        	// 根据任务id获取任务状态
				    			String sessionId = SouthAPIWorker.getSessionId(engine);
				    			String resultStr = SouthAPIWorker.getStatusByTaskId(engine, String.valueOf

(task.getTaskId())+"_"+task.getOrder_id());
				    			String status = this.getStatusByResult(resultStr);
//				    			if ("stop".equals(opt)) {
				    			if ("running".equals(status)) {// 任务执行中
				    				// 订单操作
				    				String optStr = SouthAPIWorker.stopTask(engine, String.valueOf(task.getTaskId

())+"_"+task.getOrder_id());
				    				boolean res = this.getOptByRes(task.getTaskId(),optStr);
				    				if(res){
				    					json.put("code", "200");//成功
					    				//更新task的状态
					    				task.setStatus(0);//暂停
					    				taskService.update(task);
				    				}else{
				    					json.put("code", "404");//失败
				    					json.put("message", "获取结果失败");
				    				}
				    			}else if("stop".equals(status)){//任务暂停中
//				    			}else if("resume".equals(opt)){
				    				String optStr = SouthAPIWorker.startTask(engine, String.valueOf(task.getTaskId

())+"_"+task.getOrder_id());
				    				boolean res = this.getOptByRes(task.getTaskId(),optStr);
				    				if(res){
				    					json.put("code", "200");//成功
					    				//更新task的状态
					    				task.setStatus(2);
					    				taskService.update(task);
				    				}else{
				    					json.put("code", "404");//失败
				    					json.put("message", "获取结果失败");
				    				}
				    			}else if("start".equals(status)){
				    				json.put("code", 424);//返回404表示失败
				    				json.put("message", "任务等待中");
				    			}else {
				    				CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "[进度获取]: 失败!");
			    					json.put("code", 404);//返回404表示失败
				    				json.put("message", "获取结果失败");
				    			}
							}
						}else{
							json.put("code", 423);//返回404表示失败
		    				json.put("message", "任务已完成或目前没有可以操作的任务");
						}
					}
				}else{
					json.put("code", 423);//返回404表示失败
    				json.put("message", "任务已完成或目前没有可以操作的任务");
				}
			}else{
				json.put("code", 421);//返回404表示失败
				json.put("message", "任务未下发到设备");
			}
			//将orderId放到消息队列里	
//				producerService.sendMessageOrderId(optDestination, orderId);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
			json.put("message", "获取结果失败");
		}
		return json.toString();
    }

	//获取进度
	@GET
    @Path("/orderTask_status/{orderTaskId}/{orderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_Get_orderTaskStatus(@PathParam("orderTaskId") String orderTaskId,@PathParam("orderId") String orderId) {
		try {
			Task task = new Task();
			task.setOrder_id(orderId);
			task.setOrderTaskId(orderTaskId);
			task.setStatus(1);
			List<Task> taskList = taskService.findTaskByOrderTaskId(task);
			//将任务放到消息队列里	
//			producerService.sendMessage(progressDestination, task);
			producerService.sendMessage(resultDestination, taskList);
//				producerService.sendMessageTaskId(progressDestination, orderTaskid);
		} catch (Exception e) {
			
			e.printStackTrace();
			Respones r = new Respones();
			r.setCode("404");
			JSONArray json = new JSONArray().fromObject(r);
	        return json.toString();
		}

		Respones r = new Respones();
		r.setCode("201");
		JSONArray json = new JSONArray().fromObject(r);
        return json.toString();
    }
	
	
	//获取结果
	@GET
    @Path("/orderTask_result/{orderTaskId}/{orderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_Get_orderTaskResult(@PathParam("orderTaskId") String orderTaskId,@PathParam("orderId") String orderId) {
		JSONObject json = new JSONObject();
		try {
			//modify by 2016-8-18
			//查询tlist，status=2
			List<Task> tlist = taskService.findTaskByOrderId(orderId);
			int serviceId = 0;
			if(tlist!=null&&tlist.size()>0){
				serviceId = tlist.get(0).getServiceId();
				//end
				Task task = new Task();
				task.setOrder_id(orderId);
				task.setOrderTaskId(orderTaskId);
				//获取在执行的任务
				if(serviceId!=5){
					task.setStatus(2);
				}else{
					task.setStatus(1);//sql status不等于1
				}
				List<Task> taskList = taskService.findTaskByOrderTaskId(task);
				//将任务放到消息队列里	
				producerService.sendMessage(resultDestination, taskList);
				
				json.put("code", 200);//返回200表示成功
			}else{
				json.put("code", 404);//返回404表示失败
				json.put("message", "暂无结果");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
			json.put("message", "获取结果失败");
		}
        return json.toString();
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
			}
			return state;
		} catch (Exception e) {
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "[获取结果调度]:解析任务状态发生异常,远程没有此任务!");
			throw new RuntimeException("[获取结果调度]:解析任务状态发生异常,远程没有此任务!");
		}
	}
	
	/**
     * 根据任务解析任务操作数据
     * @param task
     * @param progressStr
     * @return
     */
    private boolean getOptByRes(int taskId, String optStr) { 
    	Task t = new Task();
    	boolean state = false;
		try {
			String status = JSONObject.fromObject(optStr).getString("status");
			if("Success".equals(status)){
				state = true;
				return state;
			}else{
				return state;
			}
		} catch (Exception e) {
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "解析任务状态发生异常,远程没有此任务!");
			CSPLoggerAdapter.log(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "");
			throw new RuntimeException("[获取结果调度]:解析任务状态发生异常,远程没有此任务!");
		}
    }

	 
/*	public static void main(String[] args) {
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
        WebResource service = client.resource("http://localhost:8080/dss/rest/internalapi/vulnscan/orderTask");
//        WebResource service = client.resource("http://localhost:8080/dss/rest/internalapi/vulnscan/orderTask/orderTaskid/6/9");
        
//        WebResource service = client.resource("http://localhost:8080/cspi/rest/openapi/createOrder");
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,json.toString());        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses); 

	}*/
}
