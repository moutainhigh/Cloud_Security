package com.cn.ctbri.cfg;

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

import net.sf.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-10-12
 * 描        述：  分布式接口本地Worker
 * 版        本：  1.0
 */
public class dssWorker {
	
	private static String SERVER_WEB_ROOT;
	
	//获取资源信息
	private static String getResource;
	
	static {
		try {
			Properties p = new Properties();
			p.load(dssWorker.class.getClassLoader().getResourceAsStream("InternalAPI.properties"));
			SERVER_WEB_ROOT = p.getProperty("SERVER_DSS_ROOT");
			getResource = p.getProperty("getResource");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public dssWorker() {}
	
	
	
	/**
	 * 功能描述：资源获取
	 *		 @time 2016-09-5
	 */
	public static String getResource(String resourcename,String resourceaddr,int isapi){
		JSONObject json = new JSONObject();
		json.put("resourcename", resourcename);
		json.put("resourceaddr", resourceaddr);
		json.put("isapi", isapi);
		
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + getResource;
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, json.toString()); 
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

}
