package com.cn.ctbri.common;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;

import net.sf.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
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
			jsonObj.put("username", USERNAME);
			jsonObj.put("password", PASSWORD);
//			String src_data = "parameter=:"+jsonObj.toString();

			
//			String jsonContent = "parameter=%7B%22username%22%3A+%22dianxin%22%2C+%22password%22%3A+%22Dxyjy@123%22%7D";
			String jsonContent = "parameter="+jsonObj.toString();
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
	         ClientResponse response = service.type("application/x-www-form-urlencoded").post(ClientResponse.class, jsonContent);
	         String textEntity = response.getEntity(String.class);
	         System.out.print(response);
	         JSONObject jsStr = JSONObject.fromObject(textEntity);
	         
	         String jsID = jsStr.getString("code");//获取id的值
	         String sessionid = null;
	         if(jsID.equals("0")){
	             List<String> headers = response.getHeaders().get("Set-Cookie");
	             for (int i = 0; i < headers.size(); i++) {
	                if(headers.get(i).indexOf("sessionid")!=-1){
	                    sessionid = headers.get(i).substring(headers.get(i).indexOf("=")+1,headers.get(i).indexOf(";"));
	                    System.out.println(headers.get(i));
	                }
                }
	         }

	         return sessionid;
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
	public static String lssuedTask(String sessionid){
		//组织发送内容json
	    JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "test060102");
        jsonObj.put("site_list", new String[] {"http://www.testfire.net/","http://www.sinosoft.com.cn/"});
		String jsonContent = "parameter="+jsonObj.toString();
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
        String response = service.cookie(new NewCookie("sessionid",sessionid)).type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class, jsonContent);
        System.out.print(response);
        String jsStr = JSONObject.fromObject(response).getString("result");
        String virtual_group_id = JSONObject.fromObject(jsStr).getString("virtual_group_id");
        return virtual_group_id;
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
    public static String getProgressByTaskId(String sessionid,String virtual_group_id) {
    	JSONObject jsonObj = new JSONObject();
		jsonObj.put("virtual_group_id", virtual_group_id);
		String jsonContent = "parameter="+jsonObj.toString();
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
        String response = service.cookie(new NewCookie("sessionid",sessionid)).type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class, jsonContent);
        System.out.print(response);
        return response;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        String sessionid = getSessionId();
        String virtual_group_id = lssuedTask(sessionid);
        getProgressByTaskId(sessionid,virtual_group_id);
//    	getProgressByTaskId("o6ez69b4gzkikyvhm7ce9t16dk8g7y0n", "5566b16c43b9090323057cbf");
    }
}
