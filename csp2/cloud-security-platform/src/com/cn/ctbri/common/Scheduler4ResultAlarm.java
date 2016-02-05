package com.cn.ctbri.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.util.DateUtils;

/**
 * 根据任务同步进度和结果的调度类
 * 
 * @author tangxr
 * 
 */
public class Scheduler4ResultAlarm {

	static Logger logger = Logger.getLogger(Scheduler4ResultAlarm.class.getName());
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITaskService taskService;
	@Autowired
	private IAlarmService alarmService;

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
			//获取订单状态
			if(order.getStatus()==4){
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("orderId", order.getId());
	        	paramMap.put("type", order.getType());
				List<Task> tlist = taskService.findAllByOrderId(paramMap);
				for (Task task : tlist) {
					if(task.getStatus()==3){
						String result = NorthAPIWorker.vulnScanGetResult(order.getId(),String.valueOf(task.getTaskId()));
						JSONObject jsonObj = new JSONObject().fromObject(result);
						String alarmStr = jsonObj.getString("alarmObj");
						if(alarmStr!=null && !alarmStr.equals("")){
							JSONArray alarmArray = jsonObj.getJSONArray("alarmObj");
							for (Object aObj : alarmArray) {
								JSONObject alarmObj = (JSONObject) aObj;
								String taskId = alarmObj.getString("taskId");
					            String alarm_time = alarmObj.getString("alarm_time");
					            String url = alarmObj.getString("url");
					            String alarm_type = alarmObj.getString("alarm_type");
					            String name = alarmObj.getString("name");
					            String score = alarmObj.getString("score");
					            String advice = alarmObj.getString("advice");
					            String alarm_content = alarmObj.getString("alarm_content");
					            String keyword = alarmObj.getString("keyword");
					            String serId = alarmObj.getString("serviceId");
					            String level = alarmObj.getString("level");
					            
					            Alarm alarm = new Alarm();
								alarm.setTaskId(Long.parseLong(taskId));
//								alarm.setAlarm_time(DateUtils.stringToDateNYRSFM(alarm_time));
								alarm.setUrl(url);
								alarm.setAlarm_type(alarm_type);
								alarm.setName(name);
								alarm.setScore(score);
								alarm.setAdvice(advice);
								alarm.setLevel(Integer.parseInt(level));
								alarm.setAlarm_content(alarm_content);
								alarm.setKeyword(keyword);
								alarm.setServiceId(Integer.parseInt(serId));
								alarmService.saveAlarm(alarm);
							}
							order.setStatus(2);
							orderService.update(order);
						}else{
							order.setStatus(1);
							orderService.update(order);
						}
					}
					
				}
                
			}
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}
}
