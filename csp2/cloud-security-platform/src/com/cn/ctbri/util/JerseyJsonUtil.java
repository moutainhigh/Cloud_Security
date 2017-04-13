package com.cn.ctbri.util;

import java.security.SecureRandom;
import java.security.cert.CertificateException;


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
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

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
	/**
	 * 功能描述：安全通信配置设置
	 * 参数描述:String url 路径,ClientConfig config 配置对象
	 *		 @time 2015-01-05
	 */
	protected void buildConfig(String url,ClientConfig config) {
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
	
	public WebResource createBasicWebResource(String url) {
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//连接服务器
		WebResource service = client.resource(url);
		return service;
	}
	//post方法，带body参数
	/**
	 * 功能描述：post方法
	 * @param url：url路径
	 * @param jsonString body传递参数
	 * @return json结果
	 */
	public  String postMethod(String url, String jsonString) {
		//创建客户端配置对象
		WebResource service = createBasicWebResource(url);
		Builder builder = service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		//获取响应结果
		ClientResponse response = builder.post(ClientResponse.class, jsonString);
		//String cookie = response.getCookies().toString();
		String body = response.getEntity(String.class);
		//For 2
		return body;
	}
	//get方法
	/**
	 * 功能描述：get方法
	 * @param url：url路径
	 * @return json结果
	 */
	public String getMethod(String url){
        WebResource service = createBasicWebResource(url);   
        Builder builder =  service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        String response = builder.get(String.class);
        return response;
    }
	//post方法，不带参数
	/**
	 * 功能描述：post方法
	 * @param url：url路径
	 * @return json结果
	 */
	public String postMethod(String url) {
        WebResource service = createBasicWebResource(url);
        Builder builder = service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        String response = builder.post(String.class);
        return response;
	}
}
