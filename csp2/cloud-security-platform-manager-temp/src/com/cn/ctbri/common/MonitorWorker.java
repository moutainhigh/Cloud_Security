package com.cn.ctbri.common;

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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

import net.sf.json.JSONObject;

public class MonitorWorker {

	
	private static String SERVER_MONITOR_ROOT;
	
	static{
		try {
			Properties p = new Properties();
			p.load(MonitorWorker.class.getClassLoader().getResourceAsStream("InternalAPI.properties"));
			SERVER_MONITOR_ROOT = p.getProperty("SERVER_MONITOR_ROOT");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public MonitorWorker(){
		
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
	
	
	/*功能描述：增加任务
	 * 参数：type ：两种类型  app_detect ， web_detect
	 *		target ：目标URL
	 * 		service：如果类型是 app_detect 这里的service 是具体服务类型如http  如果类型是web_detect service为空
	 * 
	 * 返回值	{"status":"success","id":"1"},{"status":"failed","message":"","id":"-1"}
	 * 返回内容说明：status：状态；id:任务id,-1表示创建任务失败；message：提示信息
	 * */
	public static String createTask(String type,String target,String service , String interval_time)
	{
		//
		JSONObject json = new JSONObject();
		json.put("type", type);
		json.put("target", target);
		json.put("service", service);
		json.put("interval_time", interval_time);
		
		
		String url = SERVER_MONITOR_ROOT + "/createTask";
		//创建jersery客户端配置对象
		ClientConfig config = new DefaultClientConfig();
		//检查安全传输协议
		buildConfig(url, config);
		//创建Jersery客户端对象 
		Client client = Client.create(config);
		//连接服务器
		WebResource serviceResource = client.resource(url);
		ClientResponse response = serviceResource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
		String textEntity = response.getEntity(String.class);
		
		return textEntity;
	}
	
	
	/*功能描述：获取任务状态
	 * 参数：type ：两种类型  app_detect ， web_detect
	 *		id：任务id
	 * 
	 * app_detect返回值{"id":"1","task_type":"app_detect","target":"http://www.baidu.com",
	 * "service":"HTTP","interval_time":"interval|days:0 hours:0 minutes:2",
	 * "state":"waiting","add_time":"2017-03-10 11:31:00","agent_id":"100000","status":"success"}
	 * 
	 * web_detect返回值{"id":"1","task_type":"app_detect","target":"http://www.baidu.com",
	 * "interval_time":"interval|days:0 hours:0 minutes:2",
	 * "state":"waiting","add_time":"2017-03-10 11:31:00","agent_id":"100000","status":"success"}
	 * */
	public static String getTaskStatus(String type,String id){
		
		JSONObject json = new JSONObject();
		json.put("type", type);
		json.put("id", id);

		
		String url = SERVER_MONITOR_ROOT + "/getTask";
		//创建jersery客户端配置对象
		ClientConfig config = new DefaultClientConfig();
		//检查安全传输协议
		buildConfig(url, config);
		//创建Jersery客户端对象 
		Client client = Client.create(config);
		//连接服务器
		WebResource serviceResource = client.resource(url);
		ClientResponse response = serviceResource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
		String textEntity = response.getEntity(String.class);
		
		return textEntity;
		
	}

	
}
