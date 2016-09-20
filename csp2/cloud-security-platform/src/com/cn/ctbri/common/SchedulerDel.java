package com.cn.ctbri.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Random;
import com.cn.ctbri.util.SMSUtils;

/**
 * del的调度类
 * 
 * @author tangxr
 * 
 */
public class SchedulerDel {

	static Logger logger = Logger.getLogger(SchedulerDel.class.getName());
	
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
		map.put("isAPI", 2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();//获得系统时间.
        String nowTime = sdf.format(date);
		// 获取订单表前n条未开始执行的记录
		List<Order> orderList = orderService.findDelOrderByMap(map);
		for (Order order : orderList) {
			Date end_date = DateUtils.getAfterDate(order.getEnd_date(), -1);
				
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}
	
}
