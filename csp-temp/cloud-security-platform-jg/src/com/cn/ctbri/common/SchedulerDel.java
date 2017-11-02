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

import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;

/**
 * waf开始结束的调度类
 * 
 * @author tangxr
 * 
 */
public class SchedulerDel {

	static Logger logger = Logger.getLogger(SchedulerDel.class.getName());
	
	private static String wafIp;
	private static String resourceId;
	static{
		try {
			Properties p = new Properties();
			p.load(SchedulerDel.class.getClassLoader().getResourceAsStream("northAPI.properties"));
			wafIp = p.getProperty("wafIp");
			resourceId = p.getProperty("resourceId");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
		List<Order> orderList = orderService.findOrderByMap(map);
		for (Order order : orderList) {
			//创建waf虚拟站点,add by tangxr 2016-6-22
			List assets = orderAssetService.findAssetsByOrderId(order.getId());
			HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	int id = 0;
        	String addr = "";
        	String addrName = "";
        	String wafPort = "";
			JSONArray ser = new JSONArray();
			
			if(assets != null && assets.size() > 0){
				String ipArray=(String) assetOrder.get("ipArray");
	        	id = (Integer) assetOrder.get("orderAssetId");
	        	addr=(String) assetOrder.get("addr");
	        	addrName=(String) assetOrder.get("name");
	        	String[] addrs = addr.split(":");
	        	if(addrs[0].length()==5){
	        		addr = addr.substring(8);
	        		wafPort = "443";
	        	}else if(addrs[0].length()==4){
	        		addr = addr.substring(7);
	        		wafPort = "80";
	        	}
	        	String[] ips = null;   
	            ips = ipArray.split(",");
	            for (int n = 0; n < ips.length; n++) {
	            	JSONObject jo = new JSONObject();
	            	String[] ip = ips[n].split(":");
					jo.put("ip", ip[0]);
					jo.put("port", ip[1]);
					ser.add(jo);
	            }
	        }
			//时间戳
			String timestamp = String.valueOf(new Date().getTime());
			addrName = addrName + timestamp;
			if(addrName.length()>20){
				addrName = addrName.substring(0, 20);
			}
			String wafcreate = WafAPIWorker.createVirtualSiteInResource(resourceId, addrName, wafIp, wafPort, "nsfocus.cer", "0", addr, "*", "", ser);
			String targetKey = "";
	    	try {
	    		JSONObject obj = JSONObject.fromObject(wafcreate);
	    		targetKey = obj.getString("targetKey"); 
	    		String sta = obj.getString("status");
//	    		String sta = "success";
	    		if(sta.equals("success")){
	    			OrderAsset oa = new OrderAsset();
		    		oa.setId(id);
		    		oa.setTargetKey(targetKey);
		    		orderAssetService.update(oa);
		    		
		    		//5 下发到waf，但未解析域名
	    			order.setId(order.getId());
	    			order.setStatus(5);
	    			orderService.update(order);
	    		}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			//end
				
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}
	
}
