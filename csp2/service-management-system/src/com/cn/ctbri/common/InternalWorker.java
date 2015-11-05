package com.cn.ctbri.common;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import net.sf.json.JSONObject;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-10-16
 * 描        述：  内部接口本地Worker
 * 版        本：  1.0
 */
public class InternalWorker {
	/**
	 * 服务能力管理服务器根路径
	 */
	private static String SERVER_WEB_ROOT;
	/**
	 * 用户名
	 */
	private static String USERNAME;
	/**
	 * 密码
	 */
	private static String PASSWORD;
	/**
	 * 创建漏洞扫描订单（任务）
	 */
	private static String VulnScan_Create_orderTask;
	/**
	 * 对漏洞扫描订单进行操作，暂停，重启，停止
	 */
	private static String VulnScan_Opt_Order;
	/**
	 * 获得订单检测结果报告
	 */
	private static String VulnScan_Get_OrderReport;
	/**
	 * 获得订单/任务检测结果
	 */
	private static String VulnScan_Get_OrderResult;
	/**
	 * 获得订单/任务当前执行状态
	 */
	private static String VulnScan_Get_OrderStatus;
	
	static{
		try {
			Properties p = new Properties();
			p.load(InternalWorker.class.getClassLoader().getResourceAsStream("north.properties"));
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			USERNAME = p.getProperty("USERNAME");
			PASSWORD = p.getProperty("PASSWORD");
			VulnScan_Create_orderTask = p.getProperty("VulnScan_Create_orderTask");
			VulnScan_Opt_Order = p.getProperty("VulnScan_Opt_Order");
			VulnScan_Get_OrderReport = p.getProperty("VulnScan_Get_OrderReport");
			VulnScan_Get_OrderResult = p.getProperty("VulnScan_Get_OrderResult");
			VulnScan_Get_OrderStatus = p.getProperty("VulnScan_Get_OrderStatus");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public InternalWorker() {
	}
	
	/**
	 * 功能描述： 获取安全套接层上下文对象
	 *		 @time 2015-01-05
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
	 * 功能描述：创建漏洞扫描订单（任务）
	 * 参数描述： orderTaskId 订单任务编号,
	 * 		   ScanMode 单次、长期,
	 * 		   TargetURL 目标地址，可以多个，以逗号区分,
	 * 		   ScanType 扫描方式（正常、快速、全量）,
	 * 		   StartTime 计划开始时间,
	 * 		   EndTime 单次扫描此项为空,
	 *         ScanPeriod  周期,
	 *         ScanDepth   检测深度,
	 *         MaxPages    最大页面数,
	 *         Stategy     策略,
	 *         CustomManu  指定厂家，可以多个，以逗号区分,
	 *         Reserve     保留字段
	 *		 @time 2015-10-16
	 */
	public static String vulnScanCreate(String orderTaskId, String ScanMode, String[] TargetURL, String ScanType, 
    		String StartTime, String EndTime, String ScanPeriod, String ScanDepth, 
    		String MaxPages, String Stategy, String CustomManu[], String Reserve){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.accumulate("orderTaskId", orderTaskId);
		json.accumulate("ScanMode", ScanMode);
		json.accumulate("TargetURL", TargetURL);
		json.accumulate("ScanType", ScanType);
		json.accumulate("StartTime", StartTime);
		json.accumulate("EndTime", EndTime);
		json.accumulate("ScanPeriod", ScanPeriod);
		json.accumulate("ScanDepth", ScanDepth);
		json.accumulate("MaxPages", MaxPages);
		json.accumulate("Stategy", Stategy);
		json.accumulate("CustomManu", CustomManu);
		json.accumulate("Reserve", Reserve);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Create_orderTask;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJsonProvider.class);
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        System.out.println(textEntity);
        return status+"";
	}
	
	/**
	 * 功能描述：对漏洞扫描订单进行操作，暂停，重启，停止
	 * 参数描述： OrderId 订单编号
	 *		 @time 2015-10-16
	 */
	public static String lssuedTask(String OrderId){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.accumulate("OrderId", OrderId);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Opt_Order + OrderId;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJsonProvider.class);
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, json);
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        System.out.println(textEntity);
        return status+"";
	}
	
	/**
	 * 功能描述：获得订单检测结果报告
	 * 参数描述： OrderId 订单编号
	 *		 @time 2015-10-16
	 */
	public static String lssuedTaskGetReport(String OrderId){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.accumulate("OrderId", OrderId);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Get_OrderReport + OrderId;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJsonProvider.class);
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        System.out.println(textEntity);
        return status+"";
	}
	
	/**
	 * 功能描述：获得订单/任务检测结果
	 * 参数描述： OrderId 订单编号
	 *		 @time 2015-10-16
	 */
	public static String lssuedTaskGetResult(String OrderId, String Taskid){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.accumulate("OrderId", OrderId);
		json.accumulate("Taskid", Taskid);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Get_OrderResult + OrderId + "/" +Taskid;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJsonProvider.class);
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        System.out.println(textEntity);
        return status+"";
	}
	
	/**
	 * 功能描述：获得订单/任务当前执行状态
	 * 参数描述： OrderId 订单编号
	 *		 @time 2015-10-16
	 */
	public static String lssuedTaskGetStatus(String OrderId, String Taskid){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.accumulate("OrderId", OrderId);
		json.accumulate("Taskid", Taskid);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Get_OrderStatus + OrderId + "/" +Taskid;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJsonProvider.class);
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        System.out.println(textEntity);
        return status+"";
	}
	
	/**
	 * 功能描述：空字符串转化方法
	 * 参数描述:String str 要转化的字符
	 *		 @time 2015-01-05
	 */
	private static String nullFilter(String str) {
    	return str==null ? "" : str;
    }

    /**
	 * 功能描述：post方法请求
	 * 参数描述:String sessionId 回话ID, String taskId任务ID
	 *		 @time 2015-01-05
	 */
	private static ClientResponse postMethod(String url, JSONObject json) {
		//创建客户端配置对象
    	ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJsonProvider.class);
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//连接服务器
		WebResource service = client.resource(url);
		//获取响应结果
		ClientResponse response = service.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
		return response;
	}
	/**
	 * 功能描述：get方法请求
	 * 参数描述:String url 请求路径, String sessionId 回话ID
	 *		 @time 2015-01-05
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
	 *		 @time 2015-10-16
	 */
	private static void buildConfig(String url,ClientConfig config) {
		if(url.startsWith("http")) {
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
//        JSONObject json = new JSONObject();
//        org.codehaus.jettison.json.JSONObject jsonObject = new org.codehaus.jettison.json.JSONObject();
//        jsonObject.accumulate("zone_name", "M6");
//        jsonObject.accumulate("zone_ip", "[\"12.12.12.12/32\",\"33.33.33.33/24\"]");
        //创建任务发送路径
        //创建jersery客户端配置对象
//        ClientConfig config = new DefaultClientConfig();
//        //检查安全传输协议设置
//        buildConfig("https://128.18.74.170:443/rest/openapi/ddos/zone",config);
//        //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource("https://128.18.74.170:443/rest/openapi/ddos/zone");
//        //获取响应结果
//        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, "{\"zone_name\":\"M6\"," +
//        		"                                                                         \"zone_ip\":" +
//        		"                                                                              \"[\"12.12.12.12/32\"," +
//        		"                                                                                 \"33.33.33.33/24\"]\"}");
//        String sessionId = getSessionId();
//        String s = getEngineState(sessionId,"https://124.127.117.188:60443");
//        System.out.println(s);
    }
}
