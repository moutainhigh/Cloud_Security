package com.cn.ctbri.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.util.DateUtils;

/**
 * 根据任务同步进度和结果的调度类
 * 
 * @author tangxr
 * 
 */
public class Scheduler4Result {

	static Logger logger = Logger.getLogger(Scheduler4Result.class.getName());
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITaskService taskService;
	@Autowired
	private IOrderAssetService orderAssetService;

	public void execute() throws Exception {
		logger.info("[获取结果调度]:任务表扫描开始....");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		/*订单状态
		 * 1：完成无告警
		 * 2：完成有告警
		 * 3：扫描中有告警
		 * 4：开始扫描
		 * 5：暂停
		 */
//		map.put("status", 0);
		// 获取订单表前n条未开始执行的记录
		List<Order> orderList = orderService.findOrderByMap(map);
		for (Order order : orderList) {
			//订单服务类型
			String serviceId = String.valueOf(order.getServiceId());
			//订单扫描类型
			String scanMode = String.valueOf(order.getType());
			String begin_date = DateUtils.dateToString(order.getBegin_date());
			String end_date = DateUtils.dateToString(order.getEnd_date());
			List<OrderAsset> oaList = orderAssetService.findOrderAssetByOrderId(order.getId());
			//获取订单状态
			if(order.getStatus()==0||order.getStatus()==4){
				String result = NorthWorker.vulnScanGetStatus(order.getId(),"");
				JSONObject obj = new JSONObject().fromObject(result);
				int status = obj.getInt("status");
				int websoc = obj.getInt("websoc");
				String taskStr = obj.getString("taskObj");
				if(taskStr!=null&&!taskStr.equals("null")){
					JSONObject taskObj = obj.getJSONObject("taskObj");
					int sta = taskObj.getInt("status");
					int taskId = taskObj.getInt("taskId");
					if(sta!=3){
						String engineIP = taskObj.getString("engineIP");
				        String taskProgress = taskObj.getString("taskProgress");
				        String currentUrl = taskObj.getString("currentUrl");
				        int issueCount = taskObj.getInt("issueCount");
				        int requestCount = taskObj.getInt("requestCount");
				        int urlCount = taskObj.getInt("urlCount");
				        int averResponse = taskObj.getInt("averResponse");
				        int averSendCount = taskObj.getInt("averSendCount");
				        String sendBytes = taskObj.getString("sendBytes");
				        String receiveBytes = taskObj.getString("receiveBytes");
				        Task t = new Task();
				        t.setTaskId(taskId);
				        t.setOrder_asset_Id(String.valueOf(oaList.get(0).getId()));
				        t.setEngineIP(engineIP);
				        t.setTaskProgress(taskProgress);
				        t.setCurrentUrl(currentUrl);
				        t.setIssueCount(issueCount);
				        t.setRequestCount(requestCount);
				        t.setUrlCount(urlCount);
				        t.setAverResponse(averResponse);
				        t.setAverSendCount(averSendCount);
				        t.setStatus(sta);
				        taskService.insert(t);
				        
						
//						if(status==4){
//							//根据orderid 获取要扫描的订单详情集合
//			                List<OrderAsset> oaList = orderAssetService.findOrderAssetByOrderId(order.getId());
//			                //遍历订单详情  创建任务
//			                for(OrderAsset oa : oaList){
//						        Task task = new Task();
//						        task.setOrder_asset_Id(String.valueOf(oa.getId()));
//								task.setStatus(0);
//								task.setWebsoc(order.getWebsoc());
//								taskService.insert(task);
//					        }
//						}
					}
					order.setStatus(status);
					order.setWebsoc(websoc);
					orderService.update(order);
//					else if(order.getStatus()==4){//获取任务状态
//			        	List<Task> taskList = taskService.findTaskByOrderId(order.getId());
//			        	for (Task t : taskList) {
//			        		String taskId = String.valueOf(t.getTaskId());
//			        		String result = NorthWorker.vulnScanGetStatus(order.getId(),taskId);
//							net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(result);
//							net.sf.json.JSONObject taskObj = obj.getJSONObject("taskObj");
//							String engineIP = taskObj.getString("engineIP");
//					        String taskProgress = taskObj.getString("taskProgress");
//					        String currentUrl = taskObj.getString("currentUrl");
//					        int issueCount = taskObj.getInt("issueCount");
//					        int requestCount = taskObj.getInt("requestCount");
//					        int urlCount = taskObj.getInt("urlCount");
//					        int averResponse = taskObj.getInt("averResponse");
//					        int averSendCount = taskObj.getInt("averSendCount");
//					        String sendBytes = taskObj.getString("sendBytes");
//					        String receiveBytes = taskObj.getString("receiveBytes");
//					        t.setEngineIP(engineIP);
//					        t.setProgress(Integer.parseInt(taskProgress));
//					        t.setCurrentUrl(currentUrl);
//					        t.setIssueCount(issueCount);
//					        t.setRequestCount(requestCount);
//					        t.setUrlCount(urlCount);
//					        t.setAverResponse(averResponse);
//					        t.setAverSendCount(averSendCount);
//					        taskService.updateTask(t);
//						}
//					}
				}
				
			}
				
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}
}
