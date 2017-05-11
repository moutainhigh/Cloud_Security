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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.cn.ctbri.listener.ContextClient;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-7-14
 * 描        述：  运营接口本地Worker
 * 版        本：  1.0
 */
public class ManagerWorker {
	
	/**
	 * 运营管理服务器根路径
	 */
	private static String SERVER_MANAGER_ROOT;
	/**
	 * 创建api统计
	 */
	private static String Create_APINum;     

	
	static{
		try {
			Properties p = new Properties();
			p.load(ManagerWorker.class.getClassLoader().getResourceAsStream("internal.properties"));
			SERVER_MANAGER_ROOT = p.getProperty("SERVER_MANAGER_ROOT");
			Create_APINum = p.getProperty("Create_APINum");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ManagerWorker() {
	}
	
	final static WebTarget mainTarget = ContextClient.mainManagerTarget;
	
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
	 * 功能描述：创建api统计
	 *		 @time 2015-10-16
	 */
	public static String createAPINum(String apiKey, int service_type, int api_type, int status) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("apiKey", apiKey);
		json.put("service_type", service_type);
		json.put("api_type", api_type);
		json.put("status", status);

		System.out.println("****创建api统计****");  
		WebTarget target = mainTarget.path(Create_APINum);
        Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String str = (String)response.readEntity(String.class);
        JSONObject obj = JSONObject.fromObject(str);
        response.close();
		String stateCode = obj.getString("code");
		if(stateCode.equals("201")){
			return "success";
		}else{
			return "error";
		}
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
	}*/


    public static void main(String[] args) {
//    	String create = vulnScanCreate("2", "http://www.testfire.net", "", "2016-02-04 17:55:35", "", "", "", "", "", new String[]{"2"},"35_16020515441179849", "1");
//    	String o = vulnScanGetOrderTaskStatus("1","4");
//    	System.out.println(create);
    }
}
