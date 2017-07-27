package com.cn.ctbri.common;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Properties;

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

import com.cn.ctbri.controller.WafController;
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
	
//	private static String SERVER_WAF_ROOT;
	private static String resourceId;
	private static String deviceId;
	
	static{
		try {
			Properties p = new Properties();
			p.load(WafAPIWorker.class.getClassLoader().getResourceAsStream("northAPI.properties"));
//			SERVER_WAF_ROOT = p.getProperty("SERVER_WAF_ROOT");
			resourceId = p.getProperty("resourceId");
			deviceId = p.getProperty("deviceId");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
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
        System.err.println(">>>>>>>>>json="+json.toString());
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
	public static String getAllWafLogWebsecInTime(long currentId){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("currentId", currentId);
        //System.out.println("****根据自增的主键id来查询数据****");  
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
	public static String getWafEventTypeCount(String interval, String timeUnit,long topNum){
		
		//组织发送内容JSON
		JSONObject json = new JSONObject();
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
	
	
	public static String getEventTypeCountInTimeCurrent(long topNum){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
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
        System.out.println("str2="+str);
        response.close();
        return str;
	}
	
//获取历史
	public static String getWafLogWebSecTimeCount(String startDate,String endDate, String timeUnit, List<String> dstIpList) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("startDate", startDate);
		json.put("endDate", endDate);
		json.put("timeUnit", timeUnit);
		json.put("dstIp", dstIpList);
    	WebTarget target = mainTarget.path("/rest/adapter/getWafLogWebSecTimeCount");
		Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        System.out.println("str="+str);
        response.close();
        return str;
	}
	
		public static String getWafLogWebsecSrcIpCountInTime(String startDate,String endDate, String timeUnit, List<String> dstIpList, int limit) {
		//组织发送内容JSON
				JSONObject json = new JSONObject();
				json.put("startDate", startDate);
				json.put("endDate", endDate);
				json.put("timeUnit", timeUnit);
				json.put("dstIp", dstIpList);
				json.put("limit", limit);
		    	WebTarget target = mainTarget.path("/rest/adapter/getWafLogWebsecSrcIpCountInTime");
				Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
		        String str = (String)response.readEntity(String.class);
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
	public static String getWafAlertLevelCountByMonth(String interval,String startDate){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
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
	
    
    public static void main(String[] args) throws UnsupportedEncodingException {
//        String sites = getSites("10001", "30001");
    	WafController controller = new WafController();
/*    	String eth = postIpToEth("10001", "30001", "187.6.0.2", "255.255.255.0");
    	String postIpToEthRe = controller.analysisGetPostIpToEth(eth);
    	System.out.println(postIpToEthRe);
    	
    	String createSite = createSite("10001", "30001", "new", "187.6.0.2", "80", "", "1");
    	String createSiteRe = controller.analysisGetcreateSite(createSite);
    	System.out.println(createSiteRe);*/
    	
//        System.out.println(eth);
//    	String ethStr = "{\"ip_address\":[{\"ip\":\"187.6.0.1\",\"mask\":\"255.255.255.0\",\"multi_status\":200,\"multi_result\":\"created successfully\"}]}";
/*    	JSONObject json = new JSONObject();
		json.put("ip", "192.168.1.11");
		json.put("port", "62222");
    	JSONArray ser = JSONArray.fromObject(json);
    	String virtualSite = createVirtualSite("10001", "30001", "1464833085", "testfire", "http://www.testfire.net/", "*", "", ser);
    	String virtualSiteRe = controller.analysisGetCreateVirtualSite(virtualSite);
    	System.out.println(virtualSiteRe);*/
    	
//    	List<String> dstIpList = new ArrayList();
//    	dstIpList.add("219.141.189.184");
//    	dstIpList.add("219.141.189.185");
    	
/*    	String wafLogByIp = getWaflogWebsecByIp(dstIpList);
    	List list = controller.analysisGetWaflogWebsecByIp(wafLogByIp);
    	if(list!=null&&list.size()>0){
    		for (int i = 0; i < list.size(); i++) {
				Map map = (Map)list.get(i);
				int logId = (Integer)map.get("logId");
				int resourceId = (Integer)map.get("resourceId");
				String resourceUri = map.get("resourceUri").toString();
				String resourceIp = map.get("resourceIp").toString();
				long siteId = (Long)map.get("siteId");
				int protectId = (Integer)map.get("protectId");
				String dstIp = map.get("dstIp").toString();
				String dstPort = map.get("dstPort").toString();
				String srcIp = map.get("srcIp").toString();
				String srcPort = map.get("srcPort").toString();
				String method = map.get("method").toString();
				String domain = map.get("domain").toString();
				String uri = map.get("uri").toString();
				String alertlevel = map.get("alertlevel").toString();
				String eventType = map.get("eventType").toString();
				String statTime = map.get("statTime").toString();
				String action = map.get("action").toString();
				String block = map.get("block").toString();
				String blockInfo = map.get("blockInfo").toString();
				String alertinfo = map.get("alertinfo").toString();
				String proxyInfo = map.get("proxyInfo").toString();
				String characters = map.get("characters").toString();
		        int countNum = (Integer)map.get("countNum");
		        String protocolType = map.get("protocolType").toString();
		        String wci = map.get("wci").toString();
		        String wsi = map.get("wsi").toString();
		      
			}
    	}*/
    	
//    	String waf = getWafAlertLevelCount("1");
    	
/*    	String wafLogById = getWaflogWebsecById("2104");
    	Map map = controller.analysisGetWaflogWebsecById(wafLogById);
    	
		int logId = (Integer)map.get("logId");
		int resourceId = (Integer)map.get("resourceId");
		String resourceUri = map.get("resourceUri").toString();
		String resourceIp = map.get("resourceIp").toString();
		long siteId = (Long)map.get("siteId");
		int protectId = (Integer)map.get("protectId");
		String dstIp = map.get("dstIp").toString();
		String dstPort = map.get("dstPort").toString();
		String srcIp = map.get("srcIp").toString();
		String srcPort = map.get("srcPort").toString();
		String method = map.get("method").toString();
		String domain = map.get("domain").toString();
		String uri = map.get("uri").toString();
		String alertlevel = map.get("alertlevel").toString();
		String eventType = map.get("eventType").toString();
		String statTime = map.get("statTime").toString();
		String action = map.get("action").toString();
		String block = map.get("block").toString();
		String blockInfo = map.get("blockInfo").toString();
		String alertinfo = map.get("alertinfo").toString();
		String proxyInfo = map.get("proxyInfo").toString();
		String characters = map.get("characters").toString();
        int countNum = (Integer)map.get("countNum");
        String protocolType = map.get("protocolType").toString();
        String wci = map.get("wci").toString();
        String wsi = map.get("wsi").toString();*/


    	
    	
    	/*String wafLogInTime = getWaflogWebsecInTime(dstIpList,"1");
    	List list3 = controller.analysisGetWaflogWebsecInTime(wafLogInTime);
    	if(list3!=null&&list3.size()>0){
    		for (int i = 0; i < list3.size(); i++) {
				Map map = (Map)list3.get(i);
				int logId = (Integer)map.get("logId");
				int resourceId = (Integer)map.get("resourceId");
				String resourceUri = map.get("resourceUri").toString();
				String resourceIp = map.get("resourceIp").toString();
				long siteId = (Long)map.get("siteId");
				int protectId = (Integer)map.get("protectId");
				String dstIp = map.get("dstIp").toString();
				String dstPort = map.get("dstPort").toString();
				String srcIp = map.get("srcIp").toString();
				String srcPort = map.get("srcPort").toString();
				String method = map.get("method").toString();
				String domain = map.get("domain").toString();
				String uri = map.get("uri").toString();
				String alertlevel = map.get("alertlevel").toString();
				String eventType = map.get("eventType").toString();
				String statTime = map.get("statTime").toString();
				String action = map.get("action").toString();
				String block = map.get("block").toString();
				String blockInfo = map.get("blockInfo").toString();
				String alertinfo = map.get("alertinfo").toString();
				String proxyInfo = map.get("proxyInfo").toString();
				String characters = map.get("characters").toString();
		        int countNum = (Integer)map.get("countNum");
		        String protocolType = map.get("protocolType").toString();
		        String wci = map.get("wci").toString();
		        String wsi = map.get("wsi").toString();
		      
			}
    	}*/
    		
        String wafcreate = "";
    	try {
    		/*JSONObject obj = JSONObject.fromObject(ethStr);
        	String ip_address = obj.getString("ip_address");
            if("success".equals(status)){
            	System.out.println(status);
            }*/
/*    		JSONObject siteObject = JSONObject.fromObject(ethStr);
    		JSONObject ip_addressObject = siteObject.getJSONObject("ip_address");
        	JSONObject ipObject = ip_addressObject.getJSONObject("ip");
        	String mask = ipObject.getString("mask");
        	System.out.println(mask);*/
//    		virtualSite  = createVirtualSite("10001", "30001", "1464833085", "testfire", "http://www.testfire.net/", "*", "");
//    		JSONArray ser = new JSONArray();
//    		JSONObject jo = new JSONObject();
//			jo.put("ip", "101.200.234.126");
//			jo.put("port", "80");
//			ser.add(jo);
//    		wafcreate = WafAPIWorker.createVirtualSiteInResource("10001", "test0615", "219.141.189.183", "80", "nsfocus.cer", "0", "www.anquanbang", "*", "", ser);
//    		wafcreate = WafAPIWorker.getEventTypeCountByDay("30");
//    		wafcreate = WafAPIWorker.getAllWafLogWebsecInTime("48","minute");
//    		wafcreate = WafAPIWorker.getEventTypeCountByMonth("1","2016-08");
//    		wafcreate = WafAPIWorker.getWafLogWebSecSrcIpList();
//    		wafcreate = WafAPIWorker.getWafLogWebSecDstIpList();
//    		wafcreate = WafAPIWorker.getWafAlertLevelCountByMonth("1","2016-08");
//    		wafcreate = WafAPIWorker.getWafEventTypeCount("1","hour");
//    		wafcreate = WafAPIWorker.deleteVirtualSiteInResource("10001","fdbe5087-1439-4a09-bfb2-a325489dfb9b");
//    		String data=getLocationFromIp("1.2.2.3");
//    		System.out.println("data:"+data);
    	} catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(wafcreate);
        
    }
}