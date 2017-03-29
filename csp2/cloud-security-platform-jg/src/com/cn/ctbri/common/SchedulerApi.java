package com.cn.ctbri.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.SMSUtils;

/**
 * for API
 * apiStatus的调度类
 * @author tangxr
 * 
 */
public class SchedulerApi {

	static Logger logger = Logger.getLogger(SchedulerApi.class.getName());
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderAssetService orderAssetService;

	
	public void execute() throws Exception {
		logger.info("[获取结果调度]:任务表扫描开始....");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isAPI", 1);//1代表api订单
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();//获得系统时间.
        String nowTime = sdf.format(date);
		// 获取订单表前n条未开始执行的记录
		List<Order> orderList = orderService.findOrderByMap(map);
		for (Order order : orderList) {
			try {
				//远程调用接口获取api使用情况
				String str = APIWorker.getUserCount(order.getId());
				//解析json,进行数据同步
				JSONObject jsonObject = JSONObject.fromObject(str);	
				int createAPICount = 0;//订单调用创建接口的次数
				int code = jsonObject.getInt("code");
				if(code==201){
					createAPICount = jsonObject.getInt("createAPICount");
				}
				
				//获取用户购买服务次数
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("orderId", order.getId());
				int apiCount = orderService.findAPICountByParam(paramMap);
				//api使用数量完毕或者到达期限，设置status 1
				if(createAPICount == apiCount || order.getEnd_date().compareTo(new Date())<0){
	    			order.setId(order.getId());
	    			order.setStatus(1);
	    			orderService.update(order);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}
	
}
