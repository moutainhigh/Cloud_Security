package com.cn.ctbri.common;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.HashMap;
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

import net.sf.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
/**
 * 描        述：  知道创宇接口本地Worker
 * 版        本：  1.0
 */
public class WebSocWorker {
	/**
	 * 知道创宇服务器根路径
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
			p.load(WebSocWorker.class.getClassLoader().getResourceAsStream("websoc.properties"));
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			USERNAME = p.getProperty("USERNAME");
			PASSWORD = p.getProperty("PASSWORD");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public WebSocWorker() {
	}
	/**
	 * 功能描述： 获取SessionId
	 */
	public static String getSessionId(){
		try {
			 // 登陆信息
			JSONObject jsonObj = new JSONObject();
			String encodeStr = java.net.URLEncoder.encode("{\"username\":\"dianxin\",\"password\":\"Dxyjy@123\"}");
			jsonObj.put("parameter", encodeStr);
	    	 // 登陆服务器地址
	         String url = SERVER_WEB_ROOT + "api/v2/login_auth/";         
	         // 创建客户端配置对象
	         ClientConfig config = new DefaultClientConfig(); 
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
	         // 建立客户端
	         Client client = Client.create(config);
	         // 连接服务器
	         WebResource service = client.resource(url); 
	         // 发送请求，接收返回数据
	         String str = jsonObj.toString();
	         String response = service.type(MediaType.APPLICATION_JSON).post(String.class, jsonObj.toString());
	         System.out.print(response);
	         JSONObject jsStr = JSONObject.fromObject(response); //将字符串{“id”：1}
	         
	         String jsID = jsStr.getString("code");//获取id的值

//	         //解析且获取回话ID
//	         Element ele = dataJson.getRootElement();
//	         Element s = ele.element("SessionId");
	         return jsID;
	   	}catch(Exception e) {
	   		e.printStackTrace();
	   	}
		return "";
	}
	/**
	 * 功能描述： 获取安全套接层上下文对象
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
	 * 功能描述：下发任务
	 * 参数描述：sessionId 回话Id,taskId 任务ID,destURL 监测目标URL,destIP 监测目标IP
	 *        destPort  监测目标PORT,taskSLA   任务模板名称
	 *		 @time 2015-01-05
	 */
	public static String lssuedTask(String sessionId, String taskId, String destURL, 
    		String destIP, String destPort, String taskSLA){
		//组织发送内容json
		Map map = new HashMap();
		map.put("name", "test-vgroup-name");
		map.put("site_list", new String[] { "http://www.a.com/", "http://www.b.com" });
		JSONObject json = JSONObject.fromObject(map);
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + "api/v2/vgroup/create_temp/";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_JSON).post(String.class, json.toString());
        return response;
	}
	/**
	 * 功能描述：安全通信配置设置
	 * 参数描述:String url 路径,ClientConfig config 配置对象
	 *		 @time 2015-01-05
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
	
	/**
	 * 根据任务id获取任务当前状态
	 * @param sessionId 会话id
	 * @param taskId 任务id
	 * @return 任务状态代码
	 */
	public static String getStatusByTaskId(String sessionId, String taskId) {
		//创建路径
		String url = SERVER_WEB_ROOT + "/rest/task/Test/" + taskId;
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //连接服务器，返回结果
        String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class);
		return response;
	}
	
	/**
     * 获取临时组检测进度
     * @param sessionId 会话id
     * @param taskId 任务id
     * @return 任务状态代码
     */
    public static String getProgressByTaskId(String sessionId, String taskId) {
    	JSONObject jsonObj = new JSONObject();
		jsonObj.put("virtual_group_id", taskId);
		//创建路径
		String url = SERVER_WEB_ROOT + "api/v2/vgroup/progress_temp/";
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //连接服务器，返回结果
        String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_TYPE).post(String.class, jsonObj.toString());;
        return response;
    }
    
    public static void main(String[] args) throws Exception {
    	getSessionId();
//    	getProgressByTaskId("e4a715a1ff93fa89e84c6f059a15e4bc", "50a091825de23a5ef0ee99f3");
    }
}
