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

import com.cn.ctbri.entity.Advertisement;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

/**
 * 创 建 人  ：  zhang_shaohua
 * 创建日期：  2016-06-20
 * 描        述：  前端Portal接口本地Worker
 * 版        本：  1.0
 */
public class CspAPIWorker {
	
	private static String SERVER_WEB_ROOT;
	
	//添加广告
	private static String Advertisement_Add;
	
	//删除广告
	private static String Advertisement_Delete;
	
	static {
		try {
			Properties p = new Properties();
			p.load(CspAPIWorker.class.getClassLoader().getResourceAsStream("InternalAPI.properties"));
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			Advertisement_Add = p.getProperty("Advertisement_Add");
			Advertisement_Delete = p.getProperty("Advertisement_Delete");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public CspAPIWorker() {}
	
	/**
	 * 功能描述：添加广告
	 * 参数描述： adName 广告名称
	 * 			adImage 广告图片
	 * 			adStartDate 广告有效期起始时间
	 * 			adEndDate 广告有效期结束时间
	 * 			adCreateDate 广告创建时间
	 *		 @time 2016-06-20
	 */
	public static String addAdvertisement(String adName,
			String adImage, String adStartDate, String adEndDate,
			String adCreateDate) {
		JSONObject json = new JSONObject();
//		json.put("AdId", adId);
		json.put("AdName", adName);
		json.put("AdImage", adImage);
		json.put("AdStartDate", adStartDate);
		json.put("AdEndDate", adEndDate);
		json.put("AdCreateDate", adCreateDate);
		//创建任务发送路径
		String url = SERVER_WEB_ROOT + Advertisement_Add;
		//创建jersery客户端配置对象
		ClientConfig config = new DefaultClientConfig();
		//检查安全传输协议设置
	    buildConfig(url,config);
	  //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
      //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json.toString());
        System.out.println("response:"+response);
        JSONObject obj = JSONObject.fromObject(response);
        String stateCode = obj.getString("code");
		if(stateCode.equals("200")){
			String adId = obj.getString("adId");
			return adId;
		}else{
			return "";
		}
	}
	
	/**
	 * 功能描述：删除广告
	 * 参数描述： adId 广告Id
	 *		 @time 2016-06-20
	 */
	public static String deleteAdvertisement(String adId){
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + Advertisement_Delete + adId;
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class); 
        JSONObject obj = JSONObject.fromObject(response);
        String stateCode = obj.getString("code");
        return stateCode;
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
