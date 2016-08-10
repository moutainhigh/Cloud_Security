package com.cn.ctbri.common;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cn.ctbri.controller.WafController;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
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
	
	private static String SERVER_WAF_ROOT;
	
	static{
		try {
			Properties p = new Properties();
			p.load(WafAPIWorker.class.getClassLoader().getResourceAsStream("northAPI.properties"));
			SERVER_WAF_ROOT = p.getProperty("SERVER_WAF_ROOT");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public WafAPIWorker() {
	}
	
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
		String url = SERVER_WAF_ROOT + "/rest/adapter/getSites";       
		//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
//        String status = JSONObject.fromObject(textEntity).getString("status");
        System.out.println(textEntity);
        return textEntity;
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
		String url = SERVER_WAF_ROOT + "/rest/adapter/postIpToEth";       
		//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
//        String status = JSONObject.fromObject(textEntity).getString("status");
        System.out.println(textEntity);
        return textEntity;
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
    	String url = SERVER_WAF_ROOT + "/rest/adapter/createSite";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        
        return textEntity;
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
    	String url = SERVER_WAF_ROOT + "/rest/adapter/alterSite";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        
        return textEntity;
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
    	String url = SERVER_WAF_ROOT + "/rest/adapter/createVirtualSiteInResource";
    	System.out.println("test06"+url);
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        return textEntity;
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
    	String url = SERVER_WAF_ROOT + "/rest/adapter/getWaflogWebsecByIp";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        return textEntity;
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
    	String url = SERVER_WAF_ROOT + "/rest/adapter/getWaflogWebsecById";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        return textEntity;
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
    	String url = SERVER_WAF_ROOT + "/rest/adapter/getWaflogArpByIp";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        return textEntity;
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
    	String url = SERVER_WAF_ROOT + "/rest/adapter/getWaflogDdosByIp";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        return textEntity;
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
    	String url = SERVER_WAF_ROOT + "/rest/adapter/getWaflogDefaceByIp";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        return textEntity;
	}
	
	/**
	 * 功能描述：根据ip和时间查询websec日志信息
	 * @param logId
	 * @return
	 */
	public static String getWaflogWebsecInTime(String[] dstIpList,String interval){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("dstIp", dstIpList);
		json.put("interval", interval);
    	String url = SERVER_WAF_ROOT + "/rest/adapter/getWaflogWebsecInTime";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        return textEntity;
	}
	
	/**
	 * 功能描述：获取安全事件类型统计信息
	 * @param interval
	 * @return
	 */
	public static String getWafEventTypeCount(String interval){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("interval", interval);
    	String url = SERVER_WAF_ROOT + "/rest/adapter/getWafEventTypeCount";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        return textEntity;
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
    	String url = SERVER_WAF_ROOT + "/rest/adapter/getWafAlertLevelCount";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        return textEntity;
	}
	
	/**
	 * 功能描述：按日期获取一段时间内的告警类型统计
	 * @param interval
	 * @return
	 */
	public static String getEventTypeCountByDay(String interval,String startDate){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("interval", interval);
		json.put("startDate", startDate);
    	String url = SERVER_WAF_ROOT + "/rest/adapter/getEventTypeCountByDay";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        return textEntity;
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
	private static String postMethod(String url, String xml, String sessionId) {
		//创建客户端配置对象
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//连接服务器
		WebResource service = client.resource(url);
		//获取响应结果
		String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class, xml);
		return response;
	}
	/**
	 * 功能描述：get方法请求
	 * 参数描述:String url 请求路径, String sessionId 回话ID
	 *		 @time 2015-12-31
	 */
	private static String getMethod(String url,String sessionId){
		//创建客户端配置对象
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//连接服务器
		WebResource service = client.resource(url);
		//获取响应结果
		String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).get(String.class);
		return response;
	}
	/**
	 * 功能描述：安全通信配置设置
	 * 参数描述:String url 路径,ClientConfig config 配置对象
	 *		 @time 2015-12-31
	 */
	private static void buildConfig(String url,ClientConfig config) {
		if(url.startsWith("https")) {
        	SSLContext ctx = getSSLContext();
        	config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
        		     new HostnameVerifier() {
        		         public boolean verify( String s, SSLSession sslSession ) {
        		             return true;
        		         }
        		     }, ctx
        		 ));
        }
	}
	
    
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
    	
//    	String waf = getWafEventTypeCount("1");
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
    		wafcreate = WafAPIWorker.getEventTypeCountByDay("720","2016-8-10");
    	} catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(wafcreate);
        
    }
}