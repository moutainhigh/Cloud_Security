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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;


import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-10-16
 * 描        述：  北向接口本地Worker
 * 版        本：  1.0
 */
public class NorthWorker {
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
	 * 创建订单（任务）
	 */
	private static String Create_Order;
	/**
	 * 创建漏洞扫描订单（任务）
	 */
	private static String VulnScan_Create_Order;
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
			p.load(NorthWorker.class.getClassLoader().getResourceAsStream("north.properties"));
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			USERNAME = p.getProperty("USERNAME");
			PASSWORD = p.getProperty("PASSWORD");
			Create_Order = p.getProperty("Create_Order");
			VulnScan_Create_Order = p.getProperty("VulnScan_Create_Order");
			VulnScan_Opt_Order = p.getProperty("VulnScan_Opt_Order");
			VulnScan_Get_OrderReport = p.getProperty("VulnScan_Get_OrderReport");
			VulnScan_Get_OrderResult = p.getProperty("VulnScan_Get_OrderResult");
			VulnScan_Get_OrderStatus = p.getProperty("VulnScan_Get_OrderStatus");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public NorthWorker() {
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
	 * 参数描述： ScanMode 单次、长期,
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
	 * @param targetURL 
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String createOrder(Order order, String[] targetURL) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		net.sf.json.JSONObject orderObj = net.sf.json.JSONObject.fromObject(order);
		json.put("orderObj", orderObj);
		net.sf.json.JSONArray targetURLs = net.sf.json.JSONArray.fromObject(targetURL);
		json.put("targetURLs", targetURLs);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + Create_Order;
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
	 * 功能描述：创建漏洞扫描订单（任务）
	 * 参数描述： ScanMode 单次、长期,
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
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String vulnScanCreate(String ScanMode, String[] targetURL, String ScanType, 
    		String StartTime, String EndTime, String ScanPeriod, String ScanDepth, 
    		String MaxPages, String Stategy, String CustomManu[], String orderId, String serviceId, String websoc) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("ScanMode", ScanMode);
		JSONArray targetURLs = JSONArray.fromObject(targetURL);
		json.put("targetURLs", targetURLs);
		json.put("ScanType", ScanType);
		json.put("StartTime", StartTime);
		json.put("EndTime", EndTime);
		json.put("ScanPeriod", ScanPeriod);
		json.put("ScanDepth", ScanDepth);
		json.put("MaxPages", MaxPages);
		json.put("Stategy", Stategy);
		json.put("CustomManu", CustomManu);
		json.put("orderId", orderId);
		json.put("serviceId", serviceId);
		json.put("websoc", websoc);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Create_Order;
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
        ClientResponse response = service.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json.toString());
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(textEntity);
		String state = obj.getString("state");
        return state;
	}
	
	/**
	 * 功能描述：对漏洞扫描订单进行操作，暂停，重启，停止
	 * 参数描述： OrderId 订单编号
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String vulnScanOptOrder(String orderId) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("orderId", orderId);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Opt_Order + orderId;
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
        ClientResponse response = service.type(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        System.out.println(textEntity);
        return status+"";
	}
	
	/**
	 * 功能描述：获得订单检测结果报告
	 * 参数描述： OrderId 订单编号
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String lssuedTaskGetReport(String OrderId) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("OrderId", OrderId);
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
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String lssuedTaskGetResult(String OrderId, String Taskid) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("OrderId", OrderId);
		json.put("Taskid", Taskid);
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
	 * 参数描述： orderId 订单编号
	 * 		   taskId  任务编号
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String vulnScanGetStatus(String orderId,String taskId) {
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Get_OrderStatus + orderId + "/" + taskId;
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
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);   
        String textEntity = response.getEntity(String.class);
        System.out.println(textEntity);
        return textEntity;
	}
	
	
	/**
	 * 功能描述：获得订单/任务当前执行结果
	 * 参数描述： orderId 订单编号
	 * 		   taskId  任务编号
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String vulnScanGetResult(String orderId,String taskId) {
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Get_OrderResult + orderId + "/" + taskId;
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
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);   
        String textEntity = response.getEntity(String.class);
        System.out.println(textEntity);
        return textEntity;
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


    public static void main(String[] args) {
//        String create = vulnScanCreate("2", new String[]{"27","31"}, "", "2015-10-20 16:10:01", "", "0", "", "", "", new String[0], "","", "");
        String t = vulnScanGetStatus("15120117145433875","");
        System.out.println(t);
    }
}
