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
 * for WAF
 * task\del\sendMessage\的调度类
 * @author tangxr
 * 
 */
public class SchedulerWaf {

	static Logger logger = Logger.getLogger(SchedulerWaf.class.getName());
	
	private static String wafIp;
	private static String resourceId;
	private static String deadline1;
	private static String deadline7;
	static{
		try {
			Properties p = new Properties();
			p.load(SchedulerWaf.class.getClassLoader().getResourceAsStream("northAPI.properties"));
			wafIp = p.getProperty("wafIp");
			resourceId = p.getProperty("resourceId");
			deadline1 = p.getProperty("deadline1");
			deadline7 = p.getProperty("deadline7");
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
	
	public void executeDel() throws Exception {
		logger.info("[获取结果调度]:任务表扫描开始....");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		/* 
		 * isAPI：2是waf
		 * mark ：标识删除waf
		 */
		map.put("isAPI", 2);
		map.put("mark", "del");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();//获得系统时间.
        String nowTime = sdf.format(date);
		// 获取订单表前n条未开始执行的记录
		List<Order> orderList = orderService.findDelOrderByMap(map);
		for (Order order : orderList) {
			try {
				List<OrderAsset> oal = orderAssetService.findOrderAssetByOrderId(order.getId());
				for (OrderAsset orderAsset : oal) {
					if(orderAsset.getTargetKey()!=null){
						String delStr = WafAPIWorker.deleteVirtualSiteInResource(resourceId, orderAsset.getTargetKey());
						boolean status = this.getStatusByDelStr(delStr);
						if(status==true){
							order.setStatus(1); //完成
							orderService.update(order);
						}
					}else{
						order.setStatus(1); //完成
						orderService.update(order);
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}
	
	
	public void executeSendMessage() throws Exception {
		logger.info("[获取结果调度]:waf发送通知....");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		/* 
		 * isAPI：2是waf
		 * mark ：标识发送信息
		 * deadline：waf到期期限
		 */
		map.put("isAPI", 2);
		map.put("mark", "sendMessage");
		map.put("deadline", deadline7);
		// 获取最后7天即将到期订单的记录
		List<Order> orderSList = orderService.findDelOrderByMap(map);
		getInfoToSend(orderSList,deadline7);
		
		map.put("deadline", deadline1);
		// 获取最后1天即将到期订单的记录
		List<Order> orderOList = orderService.findDelOrderByMap(map);
		getInfoToSend(orderOList,deadline1);
		logger.info("[获取结果调度]:waf发送通知结束....");
	}
	
	
	
	//删除waf之解析
	private boolean getStatusByDelStr(String delStr) {
		boolean state = false;
		try {
			JSONArray delArray = JSONArray.fromObject(delStr);
			for (Object obj : delArray) {
				JSONObject delObj = (JSONObject) obj;
				String InfoList = delObj.getString("InfoList");
				JSONObject infoList = JSONObject.fromObject(InfoList);
				String result = infoList.getString("result");
				if("Success".equals(result)){
					state = true;
				}else if("not found".equals(result)){
					state = true;
				}
			}
			return state;
		} catch (Exception e) {
			return false;
		}
	}
	
	//发送短信
	private void getInfoToSend(List<Order> orderList,String deadline) {
		for (Order order : orderList) {
			try{
				List<Linkman> mlist = orderService.findLinkmanById(order.getContactId());
				if (mlist.size() > 0) {
					Linkman linkman = mlist.get(0);
					String phoneNumber = linkman.getMobile();// 联系方式
					// int sendFlag=order.getMessage();//是否下发短信
					if (!phoneNumber.equals("") && phoneNumber != null) {
						// 发短信
						SMSUtils smsUtils = new SMSUtils();
						smsUtils.sendMessage_warn(phoneNumber, order, "",deadline);
						order.setMessage(1);
						orderService.update(order);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
}
