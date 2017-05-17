package com.cn.ctbri.util;

import java.security.SecureRandom;
import java.security.cert.CertificateException;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.cn.ctbri.listener.ContextClient;


public class JerseyJsonUtil {
	/**
	 * 获取安全套接层
	 * @return
	 */
	protected SSLContext getSSLContext() {
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
	
	
	//
	final static WebTarget southTarget = ContextClient.mainSouthTarget;
	
	

	
	
	/**
	 * 功能描述：安全通信配置设置
	 * 参数描述:String url 路径,ClientConfig config 配置对象
	 *		 @time 2015-01-05
	 */

	//post方法，带body参数
	/**
	 * 功能描述：post方法
	 * @param url：url路径
	 * @param jsonString body传递参数
	 * @return json结果
	 */
	public String postMethod(String url, String json) {
		/*Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
		WebTarget webTarget = client.target(url);*/
		
		Response response = southTarget.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
		String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	//get方法
	/**
	 * 功能描述：get方法
	 * @param url：url路径
	 * @return json结果
	 */
	public String getMethod(String url){
		Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
		WebTarget webTarget = client.target(url);
		Response response = webTarget.request().get();
		String str = (String)response.readEntity(String.class);
        response.close();
        return str;
    }
	//post方法，不带参数
	/**
	 * 功能描述：post方法
	 * @param url：url路径
	 * @return json结果
	 */
	public String postMethod(String url) {
		Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
		WebTarget webTarget = client.target(url);
		Response response = webTarget.request().post(Entity.entity(null,MediaType.APPLICATION_JSON));
		String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
}
