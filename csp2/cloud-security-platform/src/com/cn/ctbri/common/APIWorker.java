package com.cn.ctbri.common;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.cn.ctbri.controller.WafController;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-05-25
 * 描        述：  api接口本地Worker(用户获取api相关)
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
		System.out.println("****获取用户购买服务次数****");  
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);  
        WebTarget target = client.target(url+orderId);  
        Response response = target.request(MediaType.APPLICATION_JSON).buildGet().invoke();
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	/**
	 * 功能描述： 获取某订单扫描所有api的次数
	 * @param orderId 订单编号
	 * @time 2016-5-25
	 */
	public static String getUserAllCount(String orderId){
        String url = SERVER_WEB_ROOT + VulnScan_analysisAPICount;
		System.out.println("****获取某订单扫描所有api的次数****");  
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);  
        WebTarget target = client.target(url+orderId);  
        Response response = target.request().get();
        String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	
	/**
	 * 功能描述： 根据订单号获取调用接口历史记录
	 * @param orderId 订单编号
	 * @time 2016-5-25
	 */
	public static String getAPIHistory(String scanUrl, String beginDate, String endDate, String orderId){
        String url = SERVER_WEB_ROOT + VulnScan_getAPIHistory;
		System.out.println("****根据订单号获取调用接口历史记录****");  
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        WebTarget target = client.target(url+orderId).queryParam("scanUrl", scanUrl)
        		.queryParam("beginDate", beginDate)
        		.queryParam("endDate", endDate);
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
		//创建客户端配置对象
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
		//创建客户端配置对象
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
        
    }
}