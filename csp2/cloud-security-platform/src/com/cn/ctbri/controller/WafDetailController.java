package com.cn.ctbri.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderListService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.util.CommonUtil;


/**
 * 创 建 人  ：  tang
 * 创建日期：  2016-6-2
 * 描        述：  wafDetailController
 * 版        本：  1.0
 */
@Controller
public class WafDetailController {
	@Autowired
    IOrderService orderService;
	@Autowired
	IAssetService assetService;
    @Autowired
    IServService servService;
    @Autowired
	IOrderAssetService orderAssetService;
    @Autowired
	ISelfHelpOrderService selfHelpOrderService;
    @Autowired
    IOrderListService orderListService;
    
    
    @RequestMapping(value="warningWaf.html")
    public String warningWaf(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        User user = (User)request.getSession().getAttribute("globle_user");
        //获取订单信息
        List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
        request.setAttribute("order", orderList.get(0));
        //获取服务ID
        int serviceId=0;
        HashMap<String, Object> order=new HashMap<String, Object>();
	    order=(HashMap) orderList.get(0);
	    serviceId=(Integer) order.get("serviceId");

        List assets = orderAssetService.findAssetsByOrderId(orderId);
        List websecList = null;
        if(assets != null && assets.size() > 0){
        	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	String ipArray=(String) assetOrder.get("ipArray");
        	String[] ips = null;   
            ips = ipArray.split(",");
//            String websecStr = WafAPIWorker.getWaflogWebsecByIp(ips);
            String websecStr = WafAPIWorker.getWaflogWebsecInTime(ips, "1");
        	websecList = this.getWaflogWebsecByIp(websecStr);
        	request.setAttribute("websecList", websecList);
        }
        request.setAttribute("websecNum", websecList.size());
        //end 
        return "/source/page/personalCenter/wafDetail";
    }
    
    @RequestMapping(value="warningWafDetail.html")
    public void warningWafDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String logId = request.getParameter("logId");
        String websecStr = WafAPIWorker.getWaflogWebsecById(logId);
    	Map map = this.analysisGetWaflogWebsecById(websecStr);
    	
    	JSONObject JSON = CommonUtil.objectToJson(response, map);
	    // 把数据返回到页面
        CommonUtil.writeToJsp(response, JSON);
    }
    
    
    /**
     * 功能描述： 获取level饼图数据
     * 参数描述：  无
     */
    @RequestMapping(value="getLevelPieData.html")
    @ResponseBody
    public String getLevelPieData(HttpServletRequest request){
    	String levelStr = WafAPIWorker.getWafAlertLevelCount("1");
    	Map map = this.getWafAlertLevelCount(levelStr);
        
        int high = 0;
        int middle = 0;
        int low = 0;
        int count = 0;
        
        if(map != null && map.size() > 0){
			high = Integer.parseInt(map.get("high").toString());
			middle = Integer.parseInt(map.get("low").toString());
			low = Integer.parseInt(map.get("mid").toString());
        }
        count = high + middle + low;
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        JSONArray json = new JSONArray();
        
        if(low>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "0");
            jo.put("value", low);
            jo.put("color", "#1e91ff");
            jo.put("ratio", df.format((float)low/count*100)+"%");
            json.add(jo);
        }
        if(middle>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "1");
            jo.put("value", middle);
            jo.put("color", "#ffa500");
            jo.put("ratio", df.format((float)middle/count*100)+"%");
            json.add(jo);
        }
        if(high>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "2");
            jo.put("value", high);
            jo.put("color", "#ff7e50");
            jo.put("ratio", df.format((float)high/count*100)+"%");
            json.add(jo);
        }
        
        return json.toString();
    }
    
    /**
     * 功能描述： 获取level饼图数据
     * 参数描述：  无
     */
    @RequestMapping(value="getEventPieData.html", method = RequestMethod.POST)
    @ResponseBody
    public void getEventPieData(HttpServletRequest request, HttpServletResponse response){
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
    	String eventStr = WafAPIWorker.getWafEventTypeCount("1");
    	Map map = this.getWafEventTypeCount(eventStr);
        
        List name = null;
        List value = null;
        JSONObject jsondata = null;
        
        if(map != null && map.size() > 0){
			name = (List) map.get("name");
			value = (List) map.get("value");
			jsondata = (JSONObject) map.get("json");
        }

        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        JSONArray json = new JSONArray();
        

        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("count", value);
        jo.put("json", jsondata);
//        json.add(jo);
        
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(jo.toString()); 
            out.flush(); 
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return jo.toString();
    }
    
    
    /**
     * 功能描述： 获取level柱数据
     * 参数描述：  无
     */
    @RequestMapping(value="getEventBarData.html", method = RequestMethod.POST)
    @ResponseBody
    public void getEventBarData(HttpServletRequest request, HttpServletResponse response){
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
    	String eventStr = WafAPIWorker.getWafEventTypeCount("1");
    	Map map = this.getWafEventTypeCount(eventStr);
        
        List name = null;
        List value = null;
        
        if(map != null && map.size() > 0){
			name = (List) map.get("name");
			value = (List) map.get("value");
        }

        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("count", value);
        
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(jo.toString()); 
            out.flush(); 
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return jo.toString();
    }
    
    
    /**
     * 解析新建站点
     * @param siteStr
     * @return id
     */
    public String getcreateSite(String siteStr) {
    	String id = "";
    	try {
    		JSONObject siteObject = JSONObject.fromObject(siteStr);
    		String status = siteObject.getString("status");
            if("success".equals(status)){
            	JSONObject websiteObject = siteObject.getJSONObject("website");
            	JSONObject groupObject = websiteObject.getJSONObject("group");
            	id = groupObject.getString("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    
    /**
     * 解析添加ip到工作接口
     * @param ethStr
     * @return multi_result
     */
    public String getPostIpToEth(String ethStr){
    	String multiResult = "";
    	try {
    		JSONObject obj = JSONObject.fromObject(ethStr);
    		JSONArray jsonArray = obj.getJSONArray("ip_address");
    		if(jsonArray!=null && jsonArray.size()>0){
    			String object = jsonArray.getString(0);
		        JSONObject jsonObject = JSONObject.fromObject(object);
		        multiResult = jsonObject.getString("multi_result");
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(multiResult);
        return multiResult;
    }
    
    /**
     * 解析根据ip查询websec日志信息
     * @param reStr
     * @return wafLogWebsecList
     */
    public List getWaflogWebsecByIp(String reStr){
    	List reList = new ArrayList();
    	try {
    		JSONObject obj = JSONObject.fromObject(reStr);
    		JSONArray jsonArray = obj.getJSONArray("wafLogWebsecList");
    		if(jsonArray!=null && jsonArray.size()>0){
    			for (int i = 0; i < jsonArray.size(); i++) {
    				Map<String,Object> newMap = new HashMap<String,Object>();
    				String object = jsonArray.getString(i);
			        JSONObject jsonObject = JSONObject.fromObject(object);
			        int logId = jsonObject.getInt("logId");
			        int resourceId = jsonObject.getInt("resourceId");
			        String resourceUri = jsonObject.getString("resourceUri");
			        String resourceIp = jsonObject.getString("resourceIp");
			        long siteId = jsonObject.getLong("siteId");
			        int protectId = jsonObject.getInt("protectId");
			        String dstIp = jsonObject.getString("dstIp");
			        String dstPort = jsonObject.getString("dstPort");
			        String srcIp = jsonObject.getString("srcIp");
			        String srcPort = jsonObject.getString("srcPort");
			        String method = jsonObject.getString("method");
			        String domain = jsonObject.getString("domain");
			        String uri = jsonObject.getString("uri");
			        String alertlevel = jsonObject.getString("alertlevel");
			        String eventType = jsonObject.getString("eventType");
			        String statTime = jsonObject.getString("statTime");
			        String action = jsonObject.getString("action");
			        String block = jsonObject.getString("block");
			        String blockInfo = jsonObject.getString("blockInfo");
			        String alertinfo = jsonObject.getString("alertinfo");
			        String proxyInfo = jsonObject.getString("proxyInfo");
			        String characters = jsonObject.getString("characters");
			        int countNum = jsonObject.getInt("countNum");
			        String protocolType = jsonObject.getString("protocolType");
			        String wci = jsonObject.getString("wci");
			        String wsi = jsonObject.getString("wsi");
			        
			        newMap.put("logId", logId);
			        newMap.put("resourceId", resourceId);
			        newMap.put("resourceUri", resourceUri);
			        newMap.put("resourceIp", resourceIp);
			        newMap.put("siteId", siteId);
			        newMap.put("protectId", protectId);
			        newMap.put("dstIp", dstIp);
			        newMap.put("dstPort", dstPort);
			        newMap.put("srcIp", srcIp);
			        newMap.put("srcPort", srcPort);
			        newMap.put("method", method);
			        newMap.put("domain", domain);
			        newMap.put("uri", uri);
			        newMap.put("alertlevel", alertlevel);
			        newMap.put("eventType", eventType);
			        newMap.put("statTime", statTime);
			        newMap.put("action", action);
			        newMap.put("block", block);
			        newMap.put("blockInfo", blockInfo);
			        newMap.put("alertinfo", alertinfo);
			        newMap.put("proxyInfo", proxyInfo);
			        newMap.put("characters", characters);
			        newMap.put("countNum", countNum);
			        newMap.put("protocolType", protocolType);
			        newMap.put("wci", wci);
			        newMap.put("wsi", wsi);
			        
			        reList.add(newMap);
				}
    			
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reList;
    }
    
    /**
     * 解析'在resource中统一新建虚拟站点'
     * @param siteStr
     * @return
     */
    public String getCreateVirtualSite(String siteStr){
    	String targetKey = "";
    	try {
    		JSONObject obj = JSONObject.fromObject(siteStr);
    		targetKey = obj.getString("targetKey");   		
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return targetKey;
    }
    
    /**
     * 解析根据logId查询websec日志信息
     * @param reStr
     * @return wafLogWebsecList
     */
    public Map analysisGetWaflogWebsecById(String reStr){
		Map<String,Object> newMap = new HashMap<String,Object>();
    	try {
    		JSONObject obj = JSONObject.fromObject(reStr);
    		JSONObject jsonObject = obj.getJSONObject("wafLogWebsec");
    		
	        int logId = jsonObject.getInt("logId");
	        int resourceId = jsonObject.getInt("resourceId");
	        String resourceUri = jsonObject.getString("resourceUri");
	        String resourceIp = jsonObject.getString("resourceIp");
	        long siteId = jsonObject.getLong("siteId");
	        int protectId = jsonObject.getInt("protectId");
	        String dstIp = jsonObject.getString("dstIp");
	        String dstPort = jsonObject.getString("dstPort");
	        String srcIp = jsonObject.getString("srcIp");
	        String srcPort = jsonObject.getString("srcPort");
	        String method = jsonObject.getString("method");
	        String domain = jsonObject.getString("domain");
	        String uri = jsonObject.getString("uri");
	        String alertlevel = jsonObject.getString("alertlevel");
	        String eventType = jsonObject.getString("eventType");
	        String statTime = jsonObject.getString("statTime");
	        String action = jsonObject.getString("action");
	        String block = jsonObject.getString("block");
	        String blockInfo = jsonObject.getString("blockInfo");
	        String alertinfo = jsonObject.getString("alertinfo");
	        String proxyInfo = jsonObject.getString("proxyInfo");
	        String characters = jsonObject.getString("characters");
	        int countNum = jsonObject.getInt("countNum");
	        String protocolType = jsonObject.getString("protocolType");
	        String wci = jsonObject.getString("wci");
	        String wsi = jsonObject.getString("wsi");
	        
	        newMap.put("logId", logId);
	        newMap.put("resourceId", resourceId);
	        newMap.put("resourceUri", resourceUri);
	        newMap.put("resourceIp", resourceIp);
	        newMap.put("siteId", siteId);
	        newMap.put("protectId", protectId);
	        newMap.put("dstIp", dstIp);
	        newMap.put("dstPort", dstPort);
	        newMap.put("srcIp", srcIp);
	        newMap.put("srcPort", srcPort);
	        newMap.put("method", method);
	        newMap.put("domain", domain);
	        newMap.put("uri", uri);
	        newMap.put("alertlevel", alertlevel);
	        newMap.put("eventType", eventType);
	        newMap.put("statTime", statTime);
	        newMap.put("action", action);
	        newMap.put("block", block);
	        newMap.put("blockInfo", blockInfo);
	        newMap.put("alertinfo", alertinfo);
	        newMap.put("proxyInfo", proxyInfo);
	        newMap.put("characters", characters);
	        newMap.put("countNum", countNum);
	        newMap.put("protocolType", protocolType);
	        newMap.put("wci", wci);
	        newMap.put("wsi", wsi);
			 
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return newMap;
    }
    
    
    /**
     * 解析安全事件类型统计信息
     * @param eventStr
     */
    public Map getWafEventTypeCount(String eventStr){
    	Map<String,Object> reMap = new HashMap<String,Object>();
    	List<Object> arr = new ArrayList<Object>();
		List<Object> arra = new ArrayList<Object>();
		JSONObject json = new JSONObject();
    	try {
    		JSONArray obj = new JSONArray().fromObject(eventStr);
    		for (Object aObj : obj) {
    			JSONObject e = (JSONObject) aObj;
    			int count = e.getInt("count");
    			if(count!=0){
    				byte[] base64Bytes = Base64.decodeBase64(e.getString("eventType").toString().getBytes());
    				String eventType = new String(base64Bytes).trim();
    				arr.add(eventType);
    				arra.add(count);
    				json.put("value", count);
    				json.put("name", eventType);
    			}
    			
    		}
//    		List<Object> arr = obj.names();//获取名
//			Collection<Object> arra = obj.values();//获取值
//			
//			List<Object> arrNew = new ArrayList<Object>();
//			List<Object> arraNew = new ArrayList<Object>();
//			for(Object name:arr){
//				byte[] base64Bytes = Base64.decodeBase64(name.toString().trim().getBytes());
//				arrNew.add(new String(base64Bytes).trim());
//			}
//			
//			for(Object value:arra){
//				arraNew.add(value.toString());
//			} 		
//
//			
			reMap.put("name", arr);
			reMap.put("value", arra);
			reMap.put("json", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reMap;
    }
    
    
    /**
     * 解析level统计信息
     * @param levelStr
     */
    public Map getWafAlertLevelCount(String levelStr){
    	Map<String,Object> reMap = new HashMap<String,Object>();
    	try {
    		JSONObject obj = JSONObject.fromObject(levelStr);
    		int high = obj.getInt("HIGH");
    		int low = obj.getInt("LOW");
    		int mid = obj.getInt("MID");
    		reMap.put("high", high);
    		reMap.put("low", low);
    		reMap.put("mid", mid);
    		
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reMap;
    }
}
