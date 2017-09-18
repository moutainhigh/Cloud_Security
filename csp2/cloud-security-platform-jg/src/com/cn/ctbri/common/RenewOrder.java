package com.cn.ctbri.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.SMSUtils;

/**
 * 短信提醒
 * 的调度类
 * @authorgxy
 * 
 */
public class RenewOrder {

	static Logger logger = Logger.getLogger(RenewOrder.class.getName());
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITaskService taskService;

	
	public void execute() throws Exception {
	
		/**
		 * 定时执行短信提醒
		 */
		Date date = new Date();
	
	    List<Order> orderList = orderService.getWafOrderById();
	    if(orderList!=null&&orderList.size()>0){
	    	for(int i=0;i<orderList.size();i++){
	    		Order order = (Order)orderList.get(i);
	    		List taskList = taskService.getExecuteTimeById(order.getId());
	    		if(taskList!=null&&taskList.size()>0){
	    			for(int m=0;m<taskList.size();m++){
	    				Task task=(Task)taskList.get(m);
	    				//获得任务执行日期7天后的日期
	    				Date dayTime = DateUtils.getDateAfterDay(task.getExecute_time());
	    				//获得任务执行日期24小时后的日期
	    				Date hourTime = DateUtils.getDateAfterHour(task.getExecute_time());
	    				Map map = new HashMap();
	    				  SMSUtils smsUtils = new SMSUtils();
	    				if(dayTime.getTime()==date.getTime()){
	    					Linkman linkman = orderService.findLinkmanById(order.getContactId()).get(0);
	    					if(linkman.getSendDayStatus()==0){
	    					  map.put("sendDayStatus", 1);
	    					  smsUtils.sendMessage(linkman.getMobile(), "订单七日后即将到期，请及时续费");
	    					  
	    						orderService.updateLinkRenew(map);
	    					}
	    				}if(hourTime.getTime()==date.getTime()){
	    					Linkman linkman = orderService.findLinkmanById(order.getContactId()).get(0);
	    					if(linkman.getSendHourStatus()==0){
	    						  map.put("sendHourStatus", 1);
	    						  smsUtils.sendMessage(linkman.getMobile(), "订单24小时后即将到期，请及时续费");
	    						  orderService.updateLinkRenew(map);
	    					}
	    				}
	    			}
	    		}
	    	}
	    }
	}
	
}
