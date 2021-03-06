package com.cn.ctbri.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.common.WafAPIAnalysis;
import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderListService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.ExportUtils;


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
    
    private static String WAF_IP_STRING = "219.141.189.183";
    private static String INTERVAL_STRING = "1";
    
    @RequestMapping(value="warningWaf.html")
    public String warningWaf(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String orderId = request.getParameter("orderId");
        //是否查询历史
        String isHis = request.getParameter("isHis");
        //查询时间
        String reportStartDate = request.getParameter("beginDate");
        //周期类型
    	String timeUnit = request.getParameter("type");
        User user = (User)request.getSession().getAttribute("globle_user");
        //获取订单信息
        List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
        request.setAttribute("order", orderList.get(0));
        //获取服务ID 、开始结束时间
        int serviceId=0;
        HashMap<String, Object> order=new HashMap<String, Object>();
	    order=(HashMap) orderList.get(0);
	    serviceId=(Integer) order.get("serviceId");
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Date orderbeginDate = (Date) order.get("begin_date");
	    Date endDate = (Date) order.get("end_date");
	    String strBeginDate  = sdf.format(orderbeginDate);
	    String strEndDate = sdf.format(endDate);
	    request.setAttribute("defenselength",strBeginDate+" - "+strEndDate);   // 设置防护时长
	    
	    
	    
	    
        List assets = orderAssetService.findAssetsByOrderId(orderId);
        List websecList = null;
        String reurl = "";
        List<String> dstIpList = new ArrayList();
        List<String> domainList = new ArrayList<String>();
    	if(assets != null && assets.size() > 0){
        	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	String ipArray=(String) assetOrder.get("ipArray");
        	String[] ips = null;   
            ips = ipArray.split(",");
           	String addr =(String) assetOrder.get("addr");
        	String domain = (String) addr.substring(addr.indexOf("://")+3);
        	String orderAssetId = assetOrder.get("orderAssetId").toString();
        	request.setAttribute("orderAssetId", orderAssetId);
        	request.setAttribute("domainName", domain);                            //设置域名
        	if (ipArray!=null&&ipArray.length()!=0) {
        		request.setAttribute("ipurl",ipArray.substring(0, ipArray.length()-1));  // 设置ip
			}
        	else
        	{
        		request.setAttribute("ipurl","");
        	}
            for (int n = 0; n < ips.length; n++) {
            	String[] ip = ips[n].split(":");
				dstIpList.add(ip[0]);
            }
            dstIpList.add(WAF_IP_STRING);
            domainList.add(domain);
            String websecStr = "";
            if(isHis!=null && isHis.equals("1")){
            	//level等级判断
            	String levelStr = WafAPIWorker.getWafAlertLevelCountDomainInTime(reportStartDate,"",timeUnit,domainList);
            	Map map = WafAPIAnalysis.getWafAlertLevelCount(levelStr);
            	Integer totallevel = (Integer) map.get("total");
            	Integer levelhigh = (Integer) map.get("high");
            	Integer levellow = (Integer) map.get("low");
            	Integer levelmid = (Integer)map.get("mid");
        		
            	
            	request.setAttribute("level", totallevel);    //设置告警级别统计数  高中低
            	request.setAttribute("levelhigh", levelhigh);
            	request.setAttribute("levelmid", levelmid);
            	request.setAttribute("levellow", levellow);
            	
            	//告警类型判断
            	String eventStr = WafAPIWorker.getEventTypeCountDomainInTime(reportStartDate,"",timeUnit,domainList);
        		Map map1 = WafAPIAnalysis.getWafEventTypeCountInTimeNoDecode(eventStr);
        		Integer totaltype = (Integer) map1.get("total");
            	/*request.setAttribute("levelType", totaltype);
            	List listEventType = WafAPIAnalysis.analysisEventTypeCountList(eventStr);
            	int totalEventTypeCount = 0;
            	for (int i = 0; i < listEventType.size(); i++) {
					Map typeMap = (Map) listEventType.get(i);
					String count = String.valueOf(typeMap.get("count"));
					totalEventTypeCount = totalEventTypeCount + Integer.parseInt(count);
				}*/
            	request.setAttribute("eventTypeTotal", String.valueOf(totaltype));    //设置告警级别总数
            	//String strEventTypeBase64 = new String(eventStr.getBytes("UTF-8"),"UTF-8");
            	//strEventTypeBase64 = new sun.misc.BASE64Encoder().encode(strEventTypeBase64.toString().getBytes());
            	String strlistEventTypeHex = toHexString(eventStr);
            	request.setAttribute("strlistEventType",strlistEventTypeHex);   // 设置告警类型list
            	
        		//告警时段
        		String unit="";
        		if(timeUnit.equals("year")){
        			unit = "month";
        		}else if(timeUnit.equals("month")){
        			unit = "day";
        		}else if (timeUnit.equals("week")) {
					unit = "week";
				}
        		String eventStr1 = WafAPIWorker.getWafLogWebSecTimeCount(reportStartDate+"-01","",unit,domainList);
        		List listTime = WafAPIAnalysis.analysisWafLogWebSecTimeCountList(eventStr1);
        		int totalTimeCount = 0;
        		for (int i = 0; i < listTime.size(); i++) {
        			Map alarm  = (Map) listTime.get(i);
    	 	        String count = String.valueOf(alarm.get("count"));
    	 	       totalTimeCount = totalTimeCount + Integer.parseInt(count);
    	 	    }
    	    //	Map lastrow = new HashMap();
    		//	lastrow.put("time","总计");
    		//	lastrow.put("count",String.valueOf(total));
    		//	listTime.add(lastrow);
        		request.setAttribute("timeCountTotal", String.valueOf(totalTimeCount));  //设置告警时段总数
     	        request.setAttribute("resultList", listTime);        //设置告警时段list
     	     
     	        //String strtimeBase64 = new sun.misc.BASE64Encoder().encode(eventStr1.toString().getBytes());
     	        String resultListTimeHex = toHexString(eventStr1);
     	        request.setAttribute("resultListTime",resultListTimeHex);  // 设置告警time   list
     	
            	//攻击源
            	websecStr = WafAPIWorker.getWafLogWebsecSrcIpCountInTime(reportStartDate,"",timeUnit,domainList,10);
            	request.setAttribute("beginDate", reportStartDate);
            	request.setAttribute("type", timeUnit);
            	websecList = WafAPIAnalysis.getWafLogWebsecSrcIp(websecStr);
            	//String strattackip = new String(websecList.toString().getBytes("UTF-8"),"UTF-8");
            	//String strattackipBase64 = new sun.misc.BASE64Encoder().encode(websecStr.getBytes());
            	String websecListIpHex = toHexString(websecStr);
            	
            	request.setAttribute("websecList", websecList);       
            	request.setAttribute("websecListIp", websecListIpHex);   // 设置攻击源ip list
            	request.setAttribute("websecNum", websecList.size());    // 设置攻击源ip 总数
            	int totalAttackIP = 0;
        		for (int i = 0; i < listTime.size(); i++) {
        			Map alarm  = (Map) listTime.get(i);
    	 	        String count = String.valueOf(alarm.get("count"));
    	 	       totalAttackIP = totalAttackIP + Integer.parseInt(count);
    	 	    }
            	request.setAttribute("totalAttackIP", String.valueOf(totalAttackIP));
        		
            	reurl = "/source/page/personalCenter/wafHistory";
            }else{
            	websecStr = WafAPIWorker.getWafLogWebsecByDomainCurrent(domainList);
            	reurl = "/source/page/personalCenter/wafDetail";
            	websecList = this.getWaflogWebsecByIp(websecStr);
            	request.setAttribute("websecList", websecList);
            	request.setAttribute("websecNum", websecList.size());
            }


            
        }
        
        //end 
        return reurl;
    }
    /*
    public static String toHexString(String s) 
    { 
    	String str="0x"; 
    	for (int i=0;i<s.length();i++) 
    	{ 
    		int ch = (int)s.charAt(i); 
    		String s4 = Integer.toHexString(ch); 
    		str = str + s4; 
    	} 
    	return str; 
    } */
    /**
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    private static String hexString="0123456789ABCDEF";
    public static String toHexString(String str){
     //根据默认编码获取字节数组
     byte[] bytes=str.getBytes();
     StringBuilder sb=new StringBuilder(bytes.length*2);
     //将字节数组中每个字节拆解成2位16进制整数
     for(int i=0;i<bytes.length;i++){
      sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
      sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
     }
     return sb.toString();
    }
    
    public String listToString(List list)throws JSONException{
    	if (list==null) {
    		return "";
		}
    	JSONArray array = new JSONArray();
		JSONObject jsonObject = null;
		for (int i = 0; i < list.size(); i++) {
			jsonObject = new JSONObject();
			Map alarm  = (Map) list.get(i);
			jsonObject.put("time", String.valueOf(alarm.get("time")));
			jsonObject.put("count", String.valueOf(alarm.get("count")));
			array.add(jsonObject);
		}
		return array.toString();
    	
    	//return null;
    }
    public String listToString5(List list, char separator) {  
        return org.apache.commons.lang.StringUtils.join(list.toArray(),separator);  
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
    	String orderId = request.getParameter("orderId");
    	String isHis = "";
    	isHis = request.getParameter("isHis");
    	String startDate = request.getParameter("startDate");
    	String timeUnit = request.getParameter("timeUnit");
    	List assets = orderAssetService.findAssetsByOrderId(orderId);
    	List<String> dstIpList = new ArrayList();
    	List<String> domainList = new ArrayList<String>();
    	if(assets != null && assets.size() > 0){
        	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	String ipArray=(String) assetOrder.get("ipArray");
        	String addr =(String) assetOrder.get("addr");
        	String domain = (String) addr.substring(addr.indexOf("://")+3);
        	String[] ips = null;   
            ips = ipArray.split(",");
            for (int n = 0; n < ips.length; n++) {
            	String[] ip = ips[n].split(":");
				dstIpList.add(ip[0]);
            }
            dstIpList.add(WAF_IP_STRING);
            domainList.add(domain);
        }
    	
    	String levelStr = "";
    	System.out.println("isHis="+isHis);
    	if(null!=isHis&&isHis.equals("1")){//查询历史
    		levelStr = WafAPIWorker.getWafAlertLevelCountDomainInTime(startDate,"",timeUnit,domainList);
    	}else{
    		levelStr = WafAPIWorker.getAlertLevelCountLimitByDomain(domainList);
    	}
    	Map map = WafAPIAnalysis.getWafAlertLevelCount(levelStr);
        
        int high = 0;
        int middle = 0;
        int low = 0;
        int count = 0;
        
        if(map != null && map.size() > 0){
			high = Integer.parseInt(map.get("high").toString());
			middle = Integer.parseInt(map.get("mid").toString());
			low = Integer.parseInt(map.get("low").toString());
        }
        count = high + middle + low;
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        JSONArray json = new JSONArray();
        
        if(low>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "0");
            jo.put("value", low);
            jo.put("color", "#1e91ff");
//            jo.put("ratio", df.format((float)low/count*100)+"%");
            json.add(jo);
        }
        if(middle>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "1");
            jo.put("value", middle);
            jo.put("color", "#ffa500");
//            jo.put("ratio", df.format((float)middle/count*100)+"%");
            json.add(jo);
        }
        if(high>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "2");
            jo.put("value", high);
            jo.put("color", "#ff7e50");
//            jo.put("ratio", df.format((float)high/count*100)+"%");
            json.add(jo);
        }
        
        return json.toString();
    }
    
    /**
     * 功能描述： 获取event饼图数据
     * 参数描述：  无
     */
    @RequestMapping(value="getEventPieData.html", method = RequestMethod.POST)
    @ResponseBody
    public void getEventPieData(HttpServletRequest request, HttpServletResponse response){
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        
        String orderId = request.getParameter("orderId");
        String isHis = request.getParameter("isHis");
        String startDate = request.getParameter("startDate");
    	String timeUnit = request.getParameter("timeUnit");
    	List assets = orderAssetService.findAssetsByOrderId(orderId);
    	List<String> dstIpList = new ArrayList();
    	List<String> domainList = new ArrayList<String>();
    	if(assets != null && assets.size() > 0){
        	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	String ipArray=(String) assetOrder.get("ipArray");
        	String addr =(String) assetOrder.get("addr");
        	String domain = (String) addr.substring(addr.indexOf("://")+3);
        	String[] ips = null;   
            ips = ipArray.split(",");
            for (int n = 0; n < ips.length; n++) {
            	String[] ip = ips[n].split(":");
				dstIpList.add(ip[0]);
            }
            dstIpList.add(WAF_IP_STRING);
            domainList.add(domain);
        }
    	
    	String eventStr = "";
    	Map map = new HashMap();
    	if(null!=isHis&&isHis.equals("1")){//查询历史
    		eventStr = WafAPIWorker.getEventTypeCountDomainInTime(startDate,"",timeUnit,domainList);
    		map = WafAPIAnalysis.getWafEventTypeCountInTimeNoDecode(eventStr);
    	}else{
			eventStr = WafAPIWorker.getWafEventTypeCountByDomain(domainList);
    		map = WafAPIAnalysis.getWafEventTypeCount(eventStr);
    	}
        System.out.println("eventpiestr="+eventStr);
        List name = null;
        List value = null;
        JSONArray jsondata = null;
        
        if(map != null && map.size() > 0){
			name = (List) map.get("name");
			value = (List) map.get("value");
			jsondata = (JSONArray) map.get("json");
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
     * 功能描述： 获取event柱数据
     * 参数描述：  无
     */
    @RequestMapping(value="getEventBarData.html", method = RequestMethod.POST)
    @ResponseBody
    public void getEventBarData(HttpServletRequest request, HttpServletResponse response){
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        
        String orderId = request.getParameter("orderId");
        String isHis = request.getParameter("isHis");
        String startDate = request.getParameter("startDate");
    	String timeUnit = request.getParameter("timeUnit");
    	List assets = orderAssetService.findAssetsByOrderId(orderId);
    	List<String> dstIpList = new ArrayList();
    	List<String> domainList = new ArrayList<String>();
    	if(assets != null && assets.size() > 0){
        	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	String ipArray=(String) assetOrder.get("ipArray");
        	String addr =(String) assetOrder.get("addr");
        	String domain = (String) addr.substring(addr.indexOf("://")+3);
        	String[] ips = null;   
            ips = ipArray.split(",");
            for (int n = 0; n < ips.length; n++) {
            	String[] ip = ips[n].split(":");
				dstIpList.add(ip[0]);
            }
            dstIpList.add(WAF_IP_STRING);
            domainList.add(domain);
        }
        
    	String eventStr = "";
    	Map map = new HashMap();
    	if(null!=isHis&&isHis.equals("1")){//查询历史
    		eventStr = WafAPIWorker.getEventTypeCountDomainInTime(startDate,"",timeUnit,domainList);
    		map = WafAPIAnalysis.getWafEventTypeCountInTimeNoDecode(eventStr);
    	}else{
    		eventStr = WafAPIWorker.getWafEventTypeCountByDomain(domainList);
    		map = WafAPIAnalysis.getWafEventTypeCount(eventStr);
    	}
        System.out.println("eventbarstr+"+eventStr);
        List name = null;
        List value = null;
        JSONArray jsondata = null;
        
        if(map != null && map.size() > 0){
			name = (List) map.get("name");
			value = (List) map.get("value");
			jsondata = (JSONArray) map.get("json");
        }

        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("count", value);
        jo.put("json", jsondata);
        
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
     * 功能描述： 获取时间段柱数据
     * 参数描述：  无
     */
    @RequestMapping(value="getOntimeLineData.html", method = RequestMethod.POST)
    @ResponseBody
    public void getOntimeLineData(HttpServletRequest request, HttpServletResponse response){
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        
        String orderId = request.getParameter("orderId");
        String isHis = request.getParameter("isHis");
        String startDate = request.getParameter("startDate");
    	String timeUnit = request.getParameter("timeUnit");
    	List assets = orderAssetService.findAssetsByOrderId(orderId);
    	List<String> dstIpList = new ArrayList();
    	List<String> domainList = new ArrayList<String>();
    	if(assets != null && assets.size() > 0){
        	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	String ipArray=(String) assetOrder.get("ipArray");
        	String addr =(String) assetOrder.get("addr");
        	String domain = (String) addr.substring(addr.indexOf("://")+3);
        	String[] ips = null;   
            ips = ipArray.split(",");
            for (int n = 0; n < ips.length; n++) {
            	String[] ip = ips[n].split(":");
				dstIpList.add(ip[0]);
            }
            dstIpList.add("219.141.189.183");  
            domainList.add(domain);
        }
        
    	String eventStr = "";
    	if(isHis.equals("1")){//历史查询
    		String unit = "";
    		if(timeUnit.equals("year")){
    			unit = "month";
    		}else if(timeUnit.equals("month")){
    			unit = "day";
    		}else if (timeUnit.equals("week")) {
				unit = "week";
			}
    		eventStr = WafAPIWorker.getWafLogWebSecTimeCount(startDate+"-01","",unit,domainList);
    		System.out.println("eventStr="+eventStr);
    	}
    	Map map = WafAPIAnalysis.analysisWafLogWebSecTimeCount(eventStr);
        
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
  
    @RequestMapping(value="getSourceIpData.html", method = RequestMethod.POST)
    @ResponseBody
    public void getSourceIpData(HttpServletRequest request, HttpServletResponse response){
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        
        String orderId = request.getParameter("orderId");
        String isHis = request.getParameter("isHis");
        String startDate = request.getParameter("startDate");
    	String timeUnit = request.getParameter("timeUnit");
    	List assets = orderAssetService.findAssetsByOrderId(orderId);
    	//List<String> dstIpList = new ArrayList();
    	List<String> domainList = new ArrayList<String>();
    	if(assets != null && assets.size() > 0){
        	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	String ipArray=(String) assetOrder.get("ipArray");
        	String addr =(String) assetOrder.get("addr");
        	String domain = (String) addr.substring(addr.indexOf("://")+3);
        	/*String[] ips = null;   
            ips = ipArray.split(",");
            
            for (int n = 0; n < ips.length; n++) {
            	String[] ip = ips[n].split(":");
				dstIpList.add(ip[0]);
            }
            dstIpList.add("219.141.189.183");  */
            domainList.add(domain);
        }
        
    	String eventStr = "";
    	if(isHis.equals("1")){//历史查询
    		String unit = "";
    		if(timeUnit.equals("year")){
    			unit = "month";
    		}else if(timeUnit.equals("month")){
    			unit = "day";
    		}else if (timeUnit.equals("week")) {
				unit = "week";
			}
    		//eventStr = WafAPIWorker.getWafLogWebSecTimeCount(startDate+"-01","",unit,domainList);
    		eventStr = WafAPIWorker.getWafLogWebsecSrcIpCountInTime(startDate,"",timeUnit,domainList,10);
    		System.out.println("eventStr="+eventStr);
    	}
    	Map map = WafAPIAnalysis.analysisWafLogWebSecSrcIp(eventStr);
        
        List name = null;
        List value = null;
        
        if(map != null && map.size() > 0){
			name = (List) map.get("srcIp");
			value = (List) map.get("count");
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
    @RequestMapping(value="getSourceAreaData.html", method = RequestMethod.POST)
    @ResponseBody
    public void getSourceAreaData(HttpServletRequest request, HttpServletResponse response){
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        
        String orderId = request.getParameter("orderId");
        String isHis = request.getParameter("isHis");
        String startDate = request.getParameter("startDate");
    	String timeUnit = request.getParameter("timeUnit");
    	List assets = orderAssetService.findAssetsByOrderId(orderId);
    	//List<String> dstIpList = new ArrayList();
    	List<String> domainList = new ArrayList<String>();
    	if(assets != null && assets.size() > 0){
        	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	String ipArray=(String) assetOrder.get("ipArray");
        	String addr =(String) assetOrder.get("addr");
        	String domain = (String) addr.substring(addr.indexOf("://")+3);
        	/*String[] ips = null;   
            ips = ipArray.split(",");
            for (int n = 0; n < ips.length; n++) {
            	String[] ip = ips[n].split(":");
				dstIpList.add(ip[0]);
            }*/
          //  dstIpList.add("219.141.189.183");  
            domainList.add(domain);
        }
        
    	String eventStr = "";
    	if(isHis.equals("1")){//历史查询
    		String unit = "";
    		if(timeUnit.equals("year")){
    			unit = "month";
    		}else if(timeUnit.equals("month")){
    			unit = "day";
    		}
    		else if (timeUnit.equals("week")) {
				unit = "week";
			}
    		eventStr = WafAPIWorker.getWafLogWebsecSrcIpCountInTime(startDate,"",timeUnit,domainList,10);
    		System.out.println("eventStr="+eventStr);
    	}
    	Map map = WafAPIAnalysis.analysisWafLogWebSecSrcIp(eventStr);
        
        List name = null;
        List value = null;
        
        if(map != null && map.size() > 0){
			name = (List) map.get("srcIp");
			value = (List) map.get("count");
			
			for (int i = 0; i < name.size(); i++) {
				String strIP = String.valueOf(name.get(i));
				String strLocation = WafAPIWorker.getLocationFromIp(strIP);
				name.set(i, strLocation);
			}
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
			        String countNum = jsonObject.getString("countNum");
			        String protocolType = jsonObject.getString("protocolType");
			        
			        byte[] base64Bytes = eventType.getBytes();	
    				eventType = new String(base64Bytes,"UTF-8");
    				byte[] base64Bytes1 = Base64.decodeBase64(alertinfo.getBytes());	
    				alertinfo = new String(base64Bytes1,"UTF-8");

//			        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
//			        long millionSeconds = sdf.parse(statTime).getTime()+1000*60*60*8;//毫秒

			        
			        newMap.put("logId", logId);
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
			        newMap.put("countNum", countNum);
			        newMap.put("protocolType", protocolType);

			        
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
	        String countNum = jsonObject.getString("countNum");
	        String protocolType = jsonObject.getString("protocolType");
	        
	        byte[] base64Bytes = eventType.getBytes();	
			eventType = new String(base64Bytes,"UTF-8");
			byte[] base64Bytes1 = Base64.decodeBase64(alertinfo.getBytes());	
			alertinfo = new String(base64Bytes1,"UTF-8");
	        
	        newMap.put("logId", logId);
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
	        newMap.put("countNum", countNum);
	        newMap.put("protocolType", protocolType);

			 
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return newMap;
    }
    
    
    
    /**
     * 最近一小时内WAF跟踪
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getWafOneHour.html")
    @ResponseBody
    public void getWafOneHour(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	User user=(User)request.getSession().getAttribute("globle_user");
    	List <String>domainList=assetService.findDomainByUserId(user.getId());
    	int type=user.getType();
    	type=0;//由于权限已经加上但本版本不上，所以type写死为0，可以获得所有用户数据，下次分权限注释此行即可
    	String eventStr = WafAPIWorker.getWafEventTypeCount(INTERVAL_STRING,"hour",0,domainList,type);
    	Map map = WafAPIAnalysis.getWafEventTypeCount(eventStr);
        
        List name = null;
        List value = null;
        JSONArray jsondata = null;
        if(map != null && map.size() > 0){
			name = (List) map.get("name");
			value = (List) map.get("value");
			jsondata = (JSONArray) map.get("json");
        }

        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("count", value);
        jo.put("json", jsondata);
    	
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("dataArray", jsondata);
		//object转化为Json格式
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
    }
}
