package com.cn.ctbri.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderListService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;


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
            String websecStr = WafAPIWorker.getWaflogWebsecByIp(ips);
        	websecList = this.getWaflogWebsecByIp(websecStr);
        	request.setAttribute("websecList", websecList);
        }
        request.setAttribute("websecNum", websecList.size());
        //end 
        return "/source/page/personalCenter/wafDetail";
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
			        String resourceUri = jsonObject.getString("resourceUri");
			        String resourceIp = jsonObject.getString("resourceIp");
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
			        String countNum = jsonObject.getString("countNum");
			        String protocolType = jsonObject.getString("protocolType");
			        String wci = jsonObject.getString("wci");
			        String wsi = jsonObject.getString("wsi");
			        
			        newMap.put("resourceUri", resourceUri);
			        newMap.put("resourceIp", resourceIp);
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
     * 解析新建虚拟站点
     * @param siteStr
     * @return
     */
    public String getCreateVirtualSite(String siteStr){
    	String status = "";
    	try {
    		JSONObject obj = JSONObject.fromObject(siteStr);
    		status = obj.getString("status");   		
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return status;
    }
    
    /**
     * 解析根据ip查询websec日志信息
     * @param reStr
     * @return wafLogWebsecList
     */
    public List getWaflogWebsecById(String reStr){
		List reList = new ArrayList();
    	try {
    		JSONObject obj = JSONObject.fromObject(reStr);
    		JSONArray jsonArray = obj.getJSONArray("wafLogWebsecList");
    		if(jsonArray!=null && jsonArray.size()>0){
    			for (int i = 0; i < jsonArray.size(); i++) {
    				Map<String,Object> newMap = new HashMap<String,Object>();
    				String object = jsonArray.getString(i);
			        JSONObject jsonObject = JSONObject.fromObject(object);
			        String resourceUri = jsonObject.getString("resourceUri");
			        String resourceIp = jsonObject.getString("resourceIp");
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
			        String countNum = jsonObject.getString("countNum");
			        String protocolType = jsonObject.getString("protocolType");
			        String wci = jsonObject.getString("wci");
			        String wsi = jsonObject.getString("wsi");
			        
			        newMap.put("resourceUri", resourceUri);
			        newMap.put("resourceIp", resourceIp);
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
}
