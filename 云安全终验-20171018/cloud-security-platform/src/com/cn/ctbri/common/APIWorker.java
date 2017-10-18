package com.cn.ctbri.common;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.cn.ctbri.util.DateUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-05-25
 * 描        述：  api接口本地Worker
 * 版        本：  1.0
 */
public class APIWorker {
	/**
	 * API服务器根路径
	 */
	
	private static String SERVER_WEB_ROOT;
    private static String VulnScan_serviceCreateAPICount;
    private static String VulnScan_analysisAPICount;
    private static String VulnScan_getAPIHistory;	
    
    static{
		try {
			Properties p = new Properties();
			p.load(APIWorker.class.getClassLoader().getResourceAsStream("northAPI.properties"));
			
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			VulnScan_serviceCreateAPICount = p.getProperty("VulnScan_serviceCreateAPICount");
			VulnScan_analysisAPICount = p.getProperty("VulnScan_analysisAPICount");
			VulnScan_getAPIHistory = p.getProperty("VulnScan_getAPIHistory");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public APIWorker() {
	}
	
	/**
	 * 功能描述： 获取用户购买服务次数
	 * @param orderId 订单编号
	 * @time 2016-5-25
	 */
	public static String getUserCount(String orderId){
		String url = SERVER_WEB_ROOT + VulnScan_serviceCreateAPICount;
		//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url+orderId);
        //获取响应结果
        ClientResponse clientResponse = service.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);        
		String str = clientResponse.getEntity(String.class);
        System.out.println("getUserCount:"+str);
        return str;
	}
	
	/**
	 * 功能描述： 获取某订单扫描所有api的次数
	 * @param orderId 订单编号
	 * @time 2016-5-25
	 */
	public static String getUserAllCount(String orderId){
		String url = SERVER_WEB_ROOT + VulnScan_analysisAPICount;
		//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url+orderId);
        //获取响应结果
        ClientResponse clientResponse = service.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);        
		String str = clientResponse.getEntity(String.class);
        System.out.println("getUserAllCount:"+str);
        return str;
	}
	
	
	/**
	 * 功能描述： 根据订单号获取调用接口历史记录
	 * @param orderId 订单编号
	 * @time 2016-5-25
	 */
	public static String getAPIHistory(String scanUrl, String beginDate, String endDate, String orderId){
		//组织发送内容JSON
//		JSONObject req = new JSONObject();
//		req.put("scanUrl", scanUrl);
//		req.put("beginDate", beginDate);
//		req.put("endDate", endDate);
//		String param = req.toString();
		String param = "?scanUrl="+scanUrl+"&beginDate="+beginDate+"&endDate="+endDate;
		param= param.replaceAll(" ", "%20");
		String url = SERVER_WEB_ROOT + VulnScan_getAPIHistory;
		//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url+orderId+param);
        
        //获取响应结果
        ClientResponse clientResponse = service.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);  
		String str = clientResponse.getEntity(String.class);
        System.out.println("getAPIHistory:"+str);
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
        
    }
}