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

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-05
 * 描        述：  安恒接口本地Worker
 * 版        本：  1.0
 */
public class ArnhemWorker {
	/**
	 * 安恒服务器根路径
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
	
	static{
		try {
			Properties p = new Properties();
			p.load(ArnhemWorker.class.getClassLoader().getResourceAsStream("arnhem.properties"));
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			USERNAME = p.getProperty("USERNAME");
			PASSWORD = p.getProperty("PASSWORD");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArnhemWorker() {
	}
	/**
	 * 功能描述： 获取SessionId
	 *		 @time 2015-01-05
	 */
	public static String getSessionId(){
		try {
			 // 登陆信息
	    	 String xmlContent = "<Login><Name>" + USERNAME + "</Name><Password>" + PASSWORD + "</Password></Login>";
	    	 // 登陆服务器地址
	         String url = SERVER_WEB_ROOT + "/rest/login";         
	         // 创建客户端配置对象
	         ClientConfig config = new DefaultClientConfig(); 
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
	         // 建立客户端
	         Client client = Client.create(config);
	         // 连接服务器
	         WebResource service = client.resource(url); 
	         // 发送请求，接收返回数据
	         String response = service.type(MediaType.APPLICATION_XML).post(String.class, xmlContent);
	         // 创建XML解析对象
	         SAXReader reader = new SAXReader();
	         // 加载XML
	         Document doc = reader.read(IOUtils.toInputStream(response));
	         //解析且获取回话ID
	         Element ele = doc.getRootElement();
	         Element s = ele.element("SessionId");
	         return s.getText();
	   	}catch(Exception e) {
	   		e.printStackTrace();
	   	}
		return "";
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
    	    SSLContext sc = SSLContext.getInstance("TLS");
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
	 * 功能描述：下发任务
	 * 参数描述：sessionId 回话Id,taskId 任务ID,destURL 监测目标URL,destIP 监测目标IP
	 *        destPort  监测目标PORT,taskSLA   任务模板名称
	 *		 @time 2015-01-05
	 */
	public static String lssuedTask(String sessionId, String taskId, String destURL, 
    		String destIP, String destPort, String taskSLA){
		//组织发送内容XML
		String xml = "<Task><TaskID>" + taskId + "</TaskID><CustomID>123123</CustomID><TaskInfo><DestURL>" +
    			destURL + "</DestURL><DestIP>" + nullFilter(destIP) + "</DestIP><DestPort>" +
    			nullFilter(destPort) + "</DestPort></TaskInfo><TaskSLA>" + taskSLA + "</TaskSLA></Task>";
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + "/rest/task";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).post(String.class, xml);
        return response;
	}
	/**
	 * 功能描述：取消下发的任务
	 * 参数描述:String sessionId, String taskId
	 *		 @time 2015-01-08
	 */
	public static String removeTask(String sessionId, String taskId){
		//创建路径
		String url = SERVER_WEB_ROOT + "/rest/task/" + taskId;
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //连接服务器，返回结果
        String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).delete(String.class);
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
	 * 功能描述：根据任务ID获取任务执行结果数
	 * 参数描述:String sessionId 回话ID, String taskId任务ID
	 *		 @time 2015-01-05
	 */
	public static String getResultCountByTaskID(String sessionId, String taskId) {
		//创建Url
    	String url = SERVER_WEB_ROOT + "/rest/report/ResultCount/TaskID/" + taskId;
    	return getMethod(url, sessionId);
    }
	/**
	 * 功能描述：根据任务ID分页获取扫描结果
	 * 参数描述:String sessionId 回话ID, String taskId任务ID, String productId 引擎功能,
     *		int startNum 起始数, int size 每页大小
	 *		 @time 2015-01-05
	 */
    public static String getReportByTaskID(String sessionId, String taskId, String productId,
    		int startNum, int size) {
    	//创建请求路径
    	String url = SERVER_WEB_ROOT + "/rest/report/TaskID";
    	//组织请求内容XML
    	String xml = "<ResultParam><TaskID>" + taskId + "</TaskID>" +
    			"<ProductID>" + productId + "</ProductID><StartNum>" + 
    			startNum + "</StartNum><Size>" + size + "</Size></ResultParam>";
    	//发送POST方法
    	return postMethod(url, xml, sessionId);
    }
    /**
	 * 功能描述：post方法请求
	 * 参数描述:String sessionId 回话ID, String taskId任务ID
	 *		 @time 2015-01-05
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
	 *		 @time 2015-01-05
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
		String sessionId = ArnhemWorker.getSessionId();
		String lssuedTask = ArnhemWorker.lssuedTask(sessionId, "test005", "", "65.61.137.117", "80", "可用性监测服务-周期10分钟");
		System.err.println(lssuedTask);
//		String  resultCountByTaskID= ArnhemWorker.getResultCountByTaskID(sessionId, "test");
//		System.err.println(resultCountByTaskID);
//		String reportByTaskID = ArnhemWorker.getReportByTaskID(sessionId, "test004", "1", 0, 2);
//		System.err.println(URLDecoder.decode(reportByTaskID, "UTF-8"));
	}
}
