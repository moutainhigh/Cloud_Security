package com.cn.ctbri.test;
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
public class NorthAPIWorker {
	/**
	 * 服务能力管理服务器根路径
	 */
	private static String SERVER_WEB_ROOT = "http://localhost:8080/cspi/";
	/**
	 * getSession
	 */
	private static String Session = "rest/openapi/getSession";
	/**
	 * 创建漏洞扫描订单（任务）
	 */
	private static String VulnScan_Create_Order = "rest/openapi/order";
	/**
	 * 对漏洞扫描订单进行操作，暂停，重启，停止
	 */
	private static String VulnScan_Opt_Order = "rest/openapi/order/";
	/**
	 * 获得订单检测结果报告
	 */
	private static String VulnScan_Get_OrderReport = "rest/openapi/orderReport/";
	/**
	 * 获得订单/任务检测结果
	 */
	private static String VulnScan_Get_OrderResult = "rest/openapi/orderResult/";
	/**
	 * 获得订单/任务当前执行状态
	 */
	private static String VulnScan_Get_OrderStatus = "rest/openapi/orderStatus/";
	/**
	 * 创建API订单（任务）
	 */
	private static String VulnScan_Create_Order_API = "rest/openapi/orderAPI";
	/**
	 * 获取会话令牌
	 */
	private static String Login = "rest/openapi/useraction/login";
	/**
	 * 设置回调地址
	 */
	private static String CallbackAddr = "rest/openapi/external/setcallbackaddr/";
	
	public NorthAPIWorker() {
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
	
	
	public static boolean getSession() {
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + Session;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON).get(String.class);
        JSONObject obj = JSONObject.fromObject(response);
		String stateCode = obj.getString("code");
		if(stateCode.equals("200")){
			return true;
		}else{
			return false;
		}
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
	 *		 @time 2015-10-16
	 */
	public static String vulnScanCreate(String scanMode, String[] targetURL, String scanType, 
    		String startTime, String endTime, String scanPeriod, String scanDepth, 
    		String maxPages, String stategy, String customManu[], String serviceId) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("scanMode", scanMode);
		JSONArray targetURLs = JSONArray.fromObject(targetURL);
		json.put("targetURLs", targetURLs);
		json.put("scanType", scanType);
		json.put("startTime", startTime);
		json.put("endTime", endTime);
		json.put("scanPeriod", scanPeriod);
		json.put("scanDepth", scanDepth);
		json.put("maxPages", maxPages);
		json.put("stategy", stategy);
		JSONArray customManus = JSONArray.fromObject(customManu);
		json.put("customManus", customManus);
		json.put("serviceId", serviceId);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Create_Order;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json.toString());
        return response;
	}
	
	/**
	 * 功能描述：对漏洞扫描订单进行操作，暂停，重启，停止
	 * 参数描述： OrderId 订单编号
	 *		 @time 2015-10-16
	 */
	public static String vulnScanOptOrder(String orderId ,String opt) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("opt", opt);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Opt_Order + orderId;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON).put(String.class,json.toString());
        return response;
	}
	
	
	/**
	 * 功能描述：获得订单/任务当前执行状态
	 * 参数描述： orderId 订单编号
	 * 		   taskId  任务编号
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String vulnScanGetStatus(String orderId) {
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Get_OrderStatus + orderId;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);   
        String textEntity = response.getEntity(String.class);
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
//	    config.getClasses().add(JacksonJsonProvider.class);
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);   
        String textEntity = response.getEntity(String.class);
        return textEntity;
	}
	
	
	/**
	 * 功能描述：获得订单检测结果报告
	 * 参数描述： OrderId 订单编号
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String vulnScanGetReport(String orderId) {
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Get_OrderReport + orderId;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);   
        String textEntity = response.getEntity(String.class);
        return textEntity;
	}
	
	/**
	 * 功能描述：创建API订单（任务）
	 * 参数描述： 
	 *		 @time 2015-10-16
	 */
	public static String vulnScanCreateAPI(int time, int num, 
    		int apiId, String apiKey) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("time", time);
		json.put("num", num);
		json.put("apiId", apiId);
		json.put("apiKey", apiKey);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + VulnScan_Create_Order_API;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json.toString());
        return response;
	}
	
	/**
	 * 功能描述：获取会话令牌
	 * 参数描述： 
	 *		 @time 2016-4-9
	 */
	public static String login(String userID, String apiKey, String randomChar) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("userID", userID);
		json.put("apiKey", apiKey);
		json.put("randomChar", randomChar);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + Login;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json.toString());
        JSONObject obj = JSONObject.fromObject(response);
        String stateCode = obj.getString("code");
		if(stateCode.equals("201")){
			String token = obj.getString("token");
			return token;
		}else{
			return "";
		}
//        return response;
	}
	
	
	/**
	 * 功能描述：设置回调地址
	 * 参数描述： 
	 *		 @time 2016-4-9
	 */
	public static String setCallbackAddr(String callbackAddr, String token) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("callbackAddr", callbackAddr);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + CallbackAddr + token;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json.toString());
//        JSONObject obj = JSONObject.fromObject(response);
//        String stateCode = obj.getString("code");
//		if(stateCode.equals("201")){
//			String token = obj.getString("token");
//			return token;
//		}else{
//			return "";
//		}
        return response;
	}
	
	public static String taskNotice(String callbackAddr, String orderId) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("orderId", orderId);
		//创建任务发送路径
    	String url = callbackAddr;
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json.toString());
        return response;
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
//        String create = vulnScanCreate("2", new String[]{"http://www.testfire.net","http://www.sinosoft.com.cn/"}, "", "2016-03-21 15:12:35", "", "", "", "", "", new String[]{"2"} ,"1");
//    	String status = vulnScanGetStatus("16032115111572197");
//    	String opt = vulnScanOptOrder("16021615033414544","stop");//resume/stop
//    	String result = vulnScanGetResult("16032114234741129","31");
//    	String report = vulnScanGetReport("16032114234741129");
//    	String createapi = vulnScanCreateAPI(1000, 1, 1, "fkshfksjfksjdfuruiyruy78");
//    	boolean report = false;
//        String token = login("191","d8bc3f404c65402f94ab417bfe8b427e", "79898uu8980iu8sjdfuruiyruy78");
//        String callbackAddr = setCallbackAddr("http://localhost:8080",token);
    	try {
//    		report = getSession();
		} catch (Exception e) {
//			e.printStackTrace();
		}
    	
    	String ok = taskNotice("http://localhost:8080/cspi/rest/open/taskNotice","16041015204427745");
        System.out.println(ok);
    }
}
