package com.cn.ctbri.common;
import java.io.IOException;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;

import com.cn.ctbri.entity.Order;
import com.cn.ctbri.listener.ContextClient;

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
	private static String VulnScan_Get_orderTaskResult;
	/**
	 * 获得订单/任务当前执行状态
	 */
	private static String VulnScan_Get_orderTaskStatus;
	
	static{
		try {
			Properties p = new Properties();
			p.load(InternalWorker.class.getClassLoader().getResourceAsStream("internal.properties"));
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			VulnScan_Create_orderTask = p.getProperty("VulnScan_Create_orderTask");
			VulnScan_Opt_Order = p.getProperty("VulnScan_Opt_Order");
			VulnScan_Get_OrderReport = p.getProperty("VulnScan_Get_OrderReport");
			VulnScan_Get_orderTaskResult = p.getProperty("VulnScan_Get_orderTaskResult");
			VulnScan_Get_orderTaskStatus = p.getProperty("VulnScan_Get_orderTaskStatus");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public InternalWorker() {
	}

	final static WebTarget mainTarget = ContextClient.mainTarget;	
	
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
	 * 		   TargetURL 目标地址，只有一个,
	 * 		   ScanType 扫描方式（正常、快速、全量）,
	 * 		   StartTime 计划开始时间,
	 * 		   EndTime 单次扫描此项为空,
	 *         ScanPeriod  周期,
	 *         ScanDepth   检测深度,
	 *         MaxPages    最大页面数,
	 *         Stategy     策略,
	 *         CustomManu  指定厂家，可以多个，以逗号区分,
	 *         Reserve     保留字段,
	 *         Partner     合作方
	 *		 @time 2015-10-16
	 */
	public static String vulnScanCreate(String scanMode, String targetURL, String scanType, 
    		String startTime, String endTime, String scanPeriod, String scanDepth, 
    		String maxPages, String stategy, String customManu[], String orderTaskId,
    		String serviceId, String partner) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("scanMode", scanMode);
		json.put("targetURL", targetURL);
		json.put("scanType", scanType);
		json.put("startTime", startTime);
		json.put("endTime", endTime);
		json.put("scanPeriod", scanPeriod);
		json.put("scanDepth", scanDepth);
		json.put("maxPages", maxPages);
		json.put("stategy", stategy);
		JSONArray customManus = JSONArray.fromObject(customManu);
		json.put("customManus", customManus);
		json.put("orderTaskId", orderTaskId);
		json.put("serviceId", serviceId);
		json.put("partner", partner);
		
		System.out.println("--------------------------------------------------------json"+json);
		System.out.println("scanMode"+scanMode);
		System.out.println("targetURL"+targetURL);
		System.out.println("scanType"+scanType);
		System.out.println("startTime"+startTime);
		System.out.println("endTime"+endTime);
		System.out.println("scanPeriod"+scanPeriod);
		System.out.println("scanDepth"+scanDepth);
		System.out.println("maxPages"+maxPages);
		System.out.println("stategy"+stategy);
		System.out.println("customManus"+customManus);
		System.out.println("orderTaskId"+orderTaskId);
		System.out.println("serviceId"+serviceId);
		System.out.println("partner"+partner);
		
		
		System.out.println("****创建漏洞扫描订单（任务）****");  
		WebTarget target = mainTarget.path(VulnScan_Create_orderTask);
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        
        System.out.println("AAAAAAAAAAAAAA str"+str);
        JSONObject obj = JSONObject.fromObject(str);
        System.out.println("BBBBBBBBBBBBBB obj"+obj);
        
        response.close();
        int stateCode = obj.getInt("code");
		if(stateCode == 201){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 功能描述：对漏洞扫描订单进行操作，暂停，重启，停止
	 * 参数描述： OrderId 订单编号
	 *		 @time 2015-10-16
	 */
	public static String vulnScanOptOrderTask(String orderId, String opt) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("opt", opt);

		System.out.println("****对漏洞扫描订单进行操作，暂停，重启，停止****");  
		WebTarget target = mainTarget.path(VulnScan_Opt_Order + "/" + orderId);
        Response response = target.request().put(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        JSONObject obj = JSONObject.fromObject(str);
        response.close();
        String stateCode = obj.getString("code");//?code从哪里来的
		if(stateCode.equals("200")){
			return "success";
		}else if(stateCode.equals("424")){
			return "wait";
		}else if(stateCode.equals("423")){
			return "other";
		}else if(stateCode.equals("421")){
			return "wrong";
		}else{
			return "error";
		}
	}
	
	/**
	 * 功能描述：获得订单检测结果报告
	 * 参数描述： OrderId 订单编号
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String lssuedTaskGetReport(String OrderId) throws JSONException{
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("OrderId", OrderId);

        System.out.println("****获得订单检测结果报告****");  
		WebTarget target = mainTarget.path(VulnScan_Get_OrderReport + OrderId);
        Response response = target.request().get();
        int status = response.getStatus();
        response.close();
        return status+"";
	}
	
	/**
	 * 功能描述：获得订单/任务检测结果
	 * 参数描述： orderId 订单编号
	 * 	       orderTaskId 订单任务编号
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String vulnScanGetOrderTaskResult(String orderId, String orderTaskId){

        System.out.println("****获得订单/任务检测结果****");  
		WebTarget target = mainTarget.path(VulnScan_Get_orderTaskResult + "/" + orderTaskId + "/" + orderId);
        Response response = target.request().get();
        int status = response.getStatus();
        response.close();
        return status+"";
	}
	
	/**
	 * 功能描述：获得订单/任务当前执行状态
	 * 参数描述： orderId 订单编号
	 * 		   orderTaskId 订单任务编号
	 * @throws JSONException 
	 *		 @time 2015-10-16
	 */
	public static String vulnScanGetOrderTaskStatus(String orderId, String orderTaskId){

        System.out.println("****获得订单/任务当前执行状态****");  
		WebTarget target = mainTarget.path(VulnScan_Get_orderTaskStatus + "/" + orderTaskId + "/" + orderId);
        Response response = target.request().get();
        int status = response.getStatus();
        response.close();
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
/*	private static ClientResponse postMethod(String url, JSONObject json) {
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
		client.destroy();
		return response;
	}*/
	/**
	 * 功能描述：get方法请求
	 * 参数描述:String url 请求路径, String sessionId 回话ID
	 *		 @time 2015-01-05
	 */
/*	private static String getMethod(String url,String sessionId){
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
		client.destroy();
		return response;
	}*/
	/**
	 * 功能描述：安全通信配置设置
	 * 参数描述:String url 路径,ClientConfig config 配置对象
	 *		 @time 2015-10-16
	 */
/*	private static void buildConfig(String url,ClientConfig config) {
		if(url.startsWith("http")) {//？？？
        	SSLContext ctx = getSSLContext();
        	config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
        		     new HostnameVerifier() {
        		         public boolean verify( String s, SSLSession sslSession ) {
        		             return true;
        		         }
        		     }, ctx
        		 ));
        }
	}*/


    public static void main(String[] args) {
    	String create = vulnScanCreate("2", "http://www.testfire.net", "", "2016-02-04 17:55:35", "", "", "", "", "", new String[]{"2"},"35_16020515441179849", "1", "");
//    	String o = vulnScanGetOrderTaskStatus("1","4");
    	System.out.println(create);
    }
}
