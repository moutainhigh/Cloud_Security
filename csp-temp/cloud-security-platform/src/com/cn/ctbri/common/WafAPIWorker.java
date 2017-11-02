package com.cn.ctbri.common;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cn.ctbri.entity.User;
import com.cn.ctbri.listener.ContextClient;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-05-25
 * 描        述：  waf接口本地Worker
 * 版        本：  1.0
 */
public class WafAPIWorker {
	/**
	 * wafAPI服务器根路径
	 */
	

	public WafAPIWorker() {
	}
	
    final static WebTarget mainTarget = ContextClient.mainSouthTarget;
	
	/**
	 * 功能描述： 获取全部站点、虚拟站点信息
	 * @param resourceId 设备资源编号，
	 * 		  deviceId   设备编号
	 * @time 2016-5-25
	 */
	public static String getSites(String resourceId, String deviceId){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("resourceId", "10001");
		json.put("deviceId", "30001");
        System.out.println("****获取全部站点、虚拟站点信息****");  
        WebTarget target = mainTarget.path("/rest/adapter/getSites");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	/**
	 * 功能描述： 添加ip到工作接口
	 * @param resourceId 设备资源编号，
	 * 		  deviceId   设备编号，
	 * 		  ip         设备ip，
	 *        mask       设备编号
	 * @time 2016-5-25
	 */
	public static String postIpToEth(String resourceId, String deviceId, String ip, String mask){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("resourceId", "10001");
		json.put("deviceId", "30001");
		json.put("ip", ip);
		json.put("mask", mask);
        //创建任务发送路径
		System.out.println("****添加ip到工作接口****");  
        WebTarget target = mainTarget.path("/rest/adapter/postIpToEth");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：新建站点
	 * @param resourceId 设备资源编号，
	 * 		  deviceId   设备编号，
	 * 		  name       站点名称，
	 * 		  ip         设备ip，
	 * 	      port       站点端口，
	 *        cert       证书名称，
	 *        type       协议类型
	 * @time 2016-5-25
	 */
	public static String createSite(String resourceId, String deviceId, 
			String name, String ip, String port, 
			String cert, String type){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("resourceId", "10001");
		json.put("deviceId", "30001");
		json.put("name", name);
		json.put("ip", ip);
		json.put("port", port);
		json.put("cert", cert);
		json.put("type", type);
        //创建任务发送路径
  		System.out.println("****新建站点****");  
        WebTarget target = mainTarget.path("/rest/adapter/createSite");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：修改站点
	 * @param resourceId 设备资源编号，
	 * 		  deviceId   设备编号，
	 * 		  siteId     站点id，
	 * 		  name       站点名称，
	 * 		  ip         设备ip，
	 * 	      port       站点端口，
	 *        cert       证书名称，
	 *        type       协议类型
	 * @time 2016-5-25
	 */
	public static String alterSite(String resourceId, String deviceId, 
			String siteId, String name, String ip, String port, 
			String cert, String type){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("resourceId", "10001");
		json.put("deviceId", "30001");
		json.put("siteId", siteId);
		json.put("name", name);
		json.put("ip", ip);
		json.put("port", port);
		json.put("cert", cert);
		json.put("type", type);
        //创建任务发送路径
  		System.out.println("****修改站点****");  
        WebTarget target = mainTarget.path("/rest/adapter/alterSite");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	
	/**
	 * 功能描述：在resource中统一新建虚拟站点
	 * @param resourceId 设备资源编号，
	 * 		  deviceId   设备编号，
	 *        parent
	 * 		  name       站点名称，
	 * 		  ip         设备ip，
	 * 	      port       站点端口，
	 *        cert       证书名称，
	 *        type       协议类型
	 * @time 2016-5-25
	 */
	public static String createVirtualSiteInResource(String resourceId, String name, 
			String wafIp, String wafPort, String cert, String type, 
			String domain, String include, String exclude, JSONArray server){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("resourceId", resourceId);
		json.put("name", name);
		json.put("wafIp", wafIp);
		json.put("wafPort", wafPort);
		json.put("cert", cert);
		json.put("type", type);
		json.put("domain", domain);
		json.put("include", include);
		json.put("exclude", exclude);
		json.put("server", server);
        //创建任务发送路径
  		System.out.println("****在resource中统一新建虚拟站点****");  
        WebTarget target = mainTarget.path("/rest/adapter/createVirtualSiteInResource");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：删除waf
	 * 参数描述： targetKey 订单编号
	 */
	public static String deleteVirtualSiteInResource(String resourceId, String targetKey) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("resourceId", resourceId);
		json.put("targetKey", targetKey);
        //创建任务发送路径
  		System.out.println("****删除waf****");  
        WebTarget target = mainTarget.path("/rest/adapter/deleteVirtualSiteInResource");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	
	/**
	 * 功能描述：根据ip查询websec日志信息
	 * @param dstIp
	 * @time 2016-5-25
	 */
	public static String getWaflogWebsecByIp(String[] dstIpList){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("dstIp", dstIpList);
        System.out.println("****根据ip查询websec日志信息****");  
        WebTarget target = mainTarget.path("/rest/adapter/getWaflogWebsecByIp");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：根据logId查询websec日志信息
	 * @param logId
	 * @return
	 */
	public static String getWaflogWebsecById(String logId){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("logId", logId);
        System.out.println("****根据logId查询websec日志信息****");  
        WebTarget target = mainTarget.path("/rest/adapter/getWaflogWebsecById");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：根据ip查询arp日志信息
	 * @param logId
	 * @return
	 */
	public static String getWaflogArpByIp(List<String> dstIpList){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("dstIp", dstIpList);
        System.out.println("****根据ip查询arp日志信息****");  
        WebTarget target = mainTarget.path("/rest/adapter/getWaflogArpByIp");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：根据ip查询ddos日志信息
	 * @param logId
	 * @return
	 */
	public static String getWaflogDdosByIp(List<String> dstIpList){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("dstIp", dstIpList);
        System.out.println("****根据ip查询ddos日志信息****");  
        WebTarget target = mainTarget.path("/rest/adapter/getWaflogDdosByIp");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：根据ip查询deface（防篡改）日志信息
	 * @param logId
	 * @return
	 */
	public static String getWaflogDefaceByIp(List<String> dstIpList){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("dstIp", dstIpList);
        System.out.println("****根据ip查询deface（防篡改）日志信息****");  
        WebTarget target = mainTarget.path("/rest/adapter/getWaflogDefaceByIp");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：根据ip和时间查询websec日志信息
	 * @param logId
	 * @return
	 */
	public static String getWaflogWebsecInTime(List<String> dstIpList,String interval){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("dstIp", dstIpList);
		json.put("interval", interval);
        System.out.println("****根据ip和时间查询websec日志信息****");  
        WebTarget target = mainTarget.path("/rest/adapter/getWaflogWebsecInTime");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	//根据域名查询websec日志信息
	public static String getWafLogWebsecByDomainCurrent(List<String> domainList) {
		JSONObject json = new JSONObject();
		json.put("domain",domainList);
        WebTarget target = mainTarget.path("/rest/adapter/getWafLogWebsecByDomainCurrent");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：根据时间查询websec日志信息
	 * @param logId
	 * @return
	 */
	public static String getAllWafLogWebsecInTime(String interval, String timeUnit){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("interval", interval);
		json.put("timeUnit", timeUnit);
        System.out.println("****根据时间查询websec日志信息****");  
        WebTarget target = mainTarget.path("/rest/adapter/getAllWafLogWebsecInTime");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 根据自增的主键id来查询数据
	 * @param currentId
	 * @return
	 */
	public static String getAllWafLogWebsecInTime(long currentId,List<String> addrList,int type){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("currentId", currentId);
		if(type!=0){
			json.put("domain", addrList);
		}
		//System.out.println("json:"+json);
        WebTarget target = mainTarget.path("/rest/adapter/getAllWafLogWebsecThanCurrentId");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	public static String getLocationFromIp(String ip){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("ip", ip);
        WebTarget target = mainTarget.path("/rest/adapter/secbasedata/iplatlong/getlatlongbyip");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	/**
	 * 功能描述：获取安全事件类型统计信息
	 * @param interval
	 * @return
	 */
	public static String getWafEventTypeCount(String interval, String timeUnit,long topNum,List<String>domainList,int type){
		
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		if(type!=0){
			json.put("domain",domainList);
		}
		if(!"forever".equals(timeUnit)){
			json.put("interval", interval);
		}
		if("forever".equals(timeUnit)){			
			json.put("topNum",topNum);
		}
		json.put("timeUnit", timeUnit);
        WebTarget target = mainTarget.path("/rest/adapter/getWafEventTypeCount");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	
	public static String getEventTypeCountInTimeCurrent(long topNum,User user,List<String>domainList){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		int type=user.getType();
		if(user.getName().equals("anquanbang")){
			type=0;
		}
		if(type!=0){
			json.put("domain", domainList);
		}
		json.put("topNum",topNum);
        WebTarget target = mainTarget.path("/rest/adapter/getEventTypeCountInTimeCurrent");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	/**
	 * 获取最新前N条数据
	 */
	public static String getWafLogWebsecCurrent(int topNum){
		JSONObject json = new JSONObject();
		json.put("topNum", topNum);
        WebTarget target = mainTarget.path("/rest/adapter/getWafLogWebsecCurrent");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	/**
	 * 功能描述：获取安全事件类型统计信息
	 * @param interval
	 * @return
	 */
	public static String getWafEventTypeCount(String interval, String timeUnit, List<String> dstIpList){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("interval", interval);
		json.put("timeUnit", timeUnit);
		json.put("dstIp", dstIpList);
        WebTarget target = mainTarget.path("/rest/adapter/getWafEventTypeCount");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	public static String getWafEventTypeCountByDomain(List<String> domainList) {
		JSONObject json = new JSONObject();
		json.put("domain", JSONArray.fromObject(domainList));
		WebTarget target = mainTarget.path("/rest/adapter/getWafEventTypeCountLimitByDomain");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
		
	}
	
	
	/**
	 * 功能描述：获取Level统计信息
	 * @param interval
	 * @return
	 */
	public static String getWafAlertLevelCount(String interval){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("interval", interval);
        WebTarget target = mainTarget.path("/rest/adapter/getWafAlertLevelCount");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	public static String getWafAlertLevelCount(String interval,List<String> dstIpList){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("interval", interval);
		json.put("dstIp", dstIpList);
        WebTarget target = mainTarget.path("/rest/adapter/getWafAlertLevelCount");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	public static String getAlertLevelCountLimitByDomain(List<String> domainList){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("domain",JSONArray.fromObject(domainList));
		json.put("limit", 1000);
        WebTarget target = mainTarget.path("/rest/adapter/getWafAlertLevelCountLimitByDomain");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
		//获取历史
	public static String getWafAlertLevelCountInTime(String startDate, String endDate, String timeUnit, List<String> dstIpList){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("startDate", startDate);
		json.put("endDate", endDate);
		json.put("timeUnit", timeUnit);
		json.put("dstIp", dstIpList);
    	WebTarget target = mainTarget.path("/rest/adapter/getWafAlertLevelCountInTime");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	public static String getWafAlertLevelCountDomainInTime(String startDate, String endDate, String timeUnit, List<String> domainList){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("startDate", startDate);
		json.put("endDate", endDate);
		json.put("timeUnit", timeUnit);
		json.put("domain", domainList);
    	WebTarget target = mainTarget.path("/rest/adapter/getWafAlertLevelCountInTime");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	public static String getEventTypeCountInTime(String startDate, String endDate, String timeUnit, List<String> dstIpList) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("startDate", startDate);
		json.put("endDate", endDate);
		json.put("timeUnit", timeUnit);
		json.put("dstIp", dstIpList);
		WebTarget target = mainTarget.path("/rest/adapter/getEventTypeCountInTime");
		Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	public static String getEventTypeCountDomainInTime(String startDate, String endDate, String timeUnit, List<String> domainList) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("startDate", startDate);
		json.put("endDate", endDate);
		json.put("timeUnit", timeUnit);
		json.put("domain", domainList);
		System.out.println(json);
		WebTarget target = mainTarget.path("/rest/adapter/getEventTypeCountInTime");
		Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        System.out.println("eventTypeCount="+str);
        response.close();
        return str;
	}
	
//获取历史
	public static String getWafLogWebSecTimeCount(String startDate,String endDate, String timeUnit, List<String> domainList) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("startDate", startDate);
		json.put("endDate", endDate);
		json.put("timeUnit", timeUnit);
		json.put("domain", domainList);
		System.out.println("timecountJson="+json);
    	WebTarget target = mainTarget.path("/rest/adapter/getWafLogWebSecTimeCount");
		Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	
	
		public static String getWafLogWebsecSrcIpCountInTime(String startDate,String endDate, String timeUnit, List<String> domainList, int limit) {
		//组织发送内容JSON
				JSONObject json = new JSONObject();
				json.put("startDate", startDate);
				json.put("endDate", endDate);
				json.put("timeUnit", timeUnit);
				json.put("domain", domainList);
				json.put("limit", limit);
				System.out.println(json);
		    	WebTarget target = mainTarget.path("/rest/adapter/getWafLogWebsecSrcIpCountInTime");
				Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
		        String str = (String)response.readEntity(String.class);
		        System.out.println("srcIpCount"+str);
		        response.close();
		        return str;
	}
	
	/**
	 * 功能描述：按日期获取一段时间内的告警类型统计
	 * @param interval
	 * @return
	 */
	public static String getEventTypeCountByDay(String interval){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("interval", interval);
        WebTarget target = mainTarget.path("/rest/adapter/getEventTypeCountByDay");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：按月份获取一段时间内的告警类型统计
	 * @param interval
	 * @return
	 */
	public static String getEventTypeCountByMonth(String interval,String startDate){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("interval", interval);
		json.put("startDate", startDate);
        WebTarget target = mainTarget.path("/rest/adapter/getEventTypeCountByMonth");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：按月份获取一段时间内的告警等级统计信息
	 * @param interval
	 * @return
	 */
	public static String getWafAlertLevelCountByMonth(String interval,String startDate,List<String> domainList,int type){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		if(type!=0){
			json.put("domain",domainList);
		}
		json.put("interval", interval);
		json.put("startDate", startDate);
        WebTarget target = mainTarget.path("/rest/adapter/getWafAlertLevelCountByMonth");
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述：获取防护目标ip统计信息
	 * @return
	 */
	public static String getWafLogWebSecDstIpList(){
        WebTarget target = mainTarget.path("/rest/adapter/getWafLogWebSecDstIpList");
        Response response = target.request().get();
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	
	/**
	 * 功能描述：获取攻击源攻击统计信息
	 * @return
	 */
	public static String getWafLogWebSecSrcIpList(){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
        WebTarget target = mainTarget.path("/rest/adapter/getWafLogWebSecSrcIpList");
        Response response = target.request().get();
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	
	/**
	 * 功能描述： 获取安全套接层上下文对象
	 *		 @time 2015-12-31
	 */
	private static SSLContext getSSLContext() {
		//创建认证管理器
    	TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
    	    public java.security.cert.X509Certificate[] getAcceptedIssuers(){return null;}
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}
    	}};
    	try {
    		//创建安全传输层对象
    	    SSLContext sc = SSLContext.getInstance("SSL");
    	    //初始化参数
    	    sc.init(null, trustAllCerts, new SecureRandom());
    	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    	    return sc;
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	return null;
    }
	

	/**
	 * 功能描述：空字符串转化方法
	 * 参数描述:String str 要转化的字符
	 *		 @time 2015-12-31
	 */
	private static String nullFilter(String str) {
    	return str==null ? "" : str;
    }


    /**
	 * 功能描述：post方法请求
	 * 参数描述:String sessionId 回话ID, String taskId任务ID
	 *		 @time 2015-12-31
	 */
//	private static String postMethod(String url, String xml, String sessionId) {
//		//创建客户端配置对象
//    	ClientConfig config = new DefaultClientConfig();
//    	//通信层配置设定
//		buildConfig(url,config);
//		//创建客户端
//		Client client = Client.create(config);
//		//连接服务器
//		WebResource service = client.resource(url);
//		//获取响应结果
//		String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class, xml);
//		return response;
//	}
	/**
	 * 功能描述：get方法请求
	 * 参数描述:String url 请求路径, String sessionId 回话ID
	 *		 @time 2015-12-31
	 */
//	private static String getMethod(String url,String sessionId){
//		//创建客户端配置对象
//    	ClientConfig config = new DefaultClientConfig();
//    	//通信层配置设定
//		buildConfig(url,config);
//		//创建客户端
//		Client client = Client.create(config);
//		//连接服务器
//		WebResource service = client.resource(url);
//		//获取响应结果
//		String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).get(String.class);
//		return response;
//	}
	/**
	 * 功能描述：安全通信配置设置
	 * 参数描述:String url 路径,ClientConfig config 配置对象
	 *		 @time 2015-12-31
	 */
//	private static void buildConfig(String url,ClientConfig config) {
//		if(url.startsWith("https")) {
//        	SSLContext ctx = getSSLContext();
//        	config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
//        		     new HostnameVerifier() {
//        		         public boolean verify( String s, SSLSession sslSession ) {
//        		             return true;
//        		         }
//        		     }, ctx
//        		 ));
//        }
//	}
	
    

}