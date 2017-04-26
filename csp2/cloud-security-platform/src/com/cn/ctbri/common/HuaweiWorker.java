//package com.cn.ctbri.common;
//import java.security.SecureRandom;
//import java.security.cert.CertificateException;
//import java.util.Properties;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import javax.ws.rs.core.MediaType;
//
//import net.sf.json.JSONObject;
//
//import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;
//import com.sun.jersey.client.urlconnection.HTTPSProperties;
///**
// * 创 建 人  ：  于永波
// * 创建日期：  2015-03-16
// * 描        述：  华为接口本地Worker
// * 版        本：  1.0
// */
//public class HuaweiWorker {
//	/**
//	 * 认证
//	 */
//	private static String AUTH;
//	/**
//	 * 创建zone
//	 */
//	private static String CREATE_ZONE;
//	/**
//	 * 修改zone
//	 */
//	private static String UPDATE_ZONE;
//	/**
//	 * 查询zone
//	 */
//	private static String QUERY_ZONE;
//	/**
//	 * 查询zone_ip
//	 */
//	private static String ZONE_IP;
//	/**
//	 * 删除zone
//	 */
//	private static String DELETE_ZONE;
//	/**
//	 * 创建引流
//	 */
//	private static String CREATE_DIVERT;
//	/**
//	 * 删除引流
//	 */
//	private static String DELETE_DIVERT;
//	/**
//	 * 创建黑洞
//	 */
//	private static String CREATE_BLACKHOLE;
//	/**
//	 * 删除黑洞
//	 */
//	private static String DELETE_BLACKHOLE;
//	
//	/**
//	 * 华为服务器根路径
//	 */
//	private static String SERVER_WEB_ROOT;
//	/**
//	 * 用户名
//	 */
//	private static String USERNAME;
//	/**
//	 * 密码
//	 */
//	private static String PASSWORD;
//	
//	static{
//		try {
//			Properties p = new Properties();
//			p.load(HuaweiWorker.class.getClassLoader().getResourceAsStream("huawei.properties"));
//			
//			AUTH = p.getProperty("auth");
//			CREATE_ZONE = p.getProperty("create_zone");
//			UPDATE_ZONE = p.getProperty("update_zone");
//			QUERY_ZONE = p.getProperty("query_zone");
//			ZONE_IP = p.getProperty("zone_ip");
//			DELETE_ZONE = p.getProperty("delete_zone");
//			CREATE_DIVERT = p.getProperty("create_divert");
//			DELETE_DIVERT = p.getProperty("delete_divert");
//			CREATE_BLACKHOLE = p.getProperty("create_blackhole");
//			DELETE_BLACKHOLE = p.getProperty("delete_blackhole");
//			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
//			USERNAME = p.getProperty("USERNAME");
//			PASSWORD = p.getProperty("PASSWORD");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public HuaweiWorker() {
//	}
//	/**
//	 * 功能描述： 获取安全套接层上下文对象
//	 *		 @time 2015-01-05
//	 */
//	private static SSLContext getSSLContext() {
//		//创建认证管理器
//    	TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
//    	    public java.security.cert.X509Certificate[] getAcceptedIssuers(){return null;}
//			public void checkClientTrusted(
//					java.security.cert.X509Certificate[] arg0, String arg1)
//					throws CertificateException {
//			}
//			public void checkServerTrusted(
//					java.security.cert.X509Certificate[] arg0, String arg1)
//					throws CertificateException {
//			}
//    	}};
//    	try {
//    		//创建安全传输层对象
//    	    SSLContext sc = SSLContext.getInstance("TLS");
//    	    //初始化参数
//    	    sc.init(null, trustAllCerts, new SecureRandom());
//    	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//    	    return sc;
//    	} catch (Exception e) {
//    	    e.printStackTrace();
//    	}
//    	return null;
//    }
//	
//	
//	/**
//	 * 描    述：认证
//	 * 创建人：txr
//	 * 日    期：2015-5-13
//	 */
//	public static String auth(){
//		//组织发送内容JSON
//		JSONObject json = new JSONObject();
//		json.accumulate("username", USERNAME);
//		json.accumulate("password", PASSWORD);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + AUTH;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //获取响应结果
////        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json);
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
//        int status = response.getStatus();
//        
//        //获取token
//        String token = null;
//        if(status==200){
//        	String textEntity = response.getEntity(String.class);
//            JSONObject jsStr = JSONObject.fromObject(textEntity);
//            token = jsStr.getString("token");//获取id的值
//        }
//        return token;
//	}
//	
//	
//	/**
//	 * 描    述：下发创建Zone任务
//	 * 创建人：于永波
//	 * 日    期：2015-3-17
//	 */
//	public static String lssuedCreateZoneTask(String token,String zone_name,String[] zone_ips){
//		//组织发送内容JSON
//		JSONObject json = new JSONObject();
//		json.accumulate("zone_name", zone_name);
//		json.accumulate("zone_ip", zone_ips);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + CREATE_ZONE;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
////        service.getRequestBuilder().header("X-Auth-Token", token);
////        service.header("X-Auth-Token", token);
//        //获取响应结果
////        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json);
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token", token).post(ClientResponse.class, json);
//        int status = response.getStatus();
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	
//	
//	/**
//	 * 描    述：下发更新Zone任务
//	 * 创建人：于永波
//	 * 日    期：2015-3-17
//	 */
//	public static String lssuedUpdateZoneTask(String token,String[] zone_ips,String zone_name){
//		//组织发送内容XML
//		JSONObject json = new JSONObject();
//		json.accumulate("zone_ip", zone_ips);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + UPDATE_ZONE + zone_name;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//        
//        //获取响应结果
////        String response = service.type(MediaType.APPLICATION_JSON).put(String.class, json);
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token",token).put(ClientResponse.class, json);
//        int status = response.getStatus();
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	
//	/**
//	 * 描    述：查询Zone任务
//	 * 创建人：txr
//	 * 日    期：2015-5-13
//	 */
//	public static String lssuedQueryZoneTask(String token){
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + QUERY_ZONE;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//        
//        //获取响应结果
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token", token).get(ClientResponse.class);
//        int status = response.getStatus();
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	
//	/**
//	 * 描    述：查询Zone任务BY zone_name
//	 * 创建人：txr
//	 * 日    期：2015-5-13
//	 */
//	public static String lssuedQueryZoneTask(String token,String zone_name){
//		//组织发送内容XML
//		JSONObject json = new JSONObject();
//		json.accumulate("zone_name", zone_name);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + UPDATE_ZONE + zone_name;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//        
//        //获取响应结果
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token", token).get(ClientResponse.class);
//        int status = response.getStatus();
//
//        //获取token
////        String zone_id = null;
////        if(status==200){
////            String textEntity = response.getEntity(String.class);
////            JSONObject jsStr = JSONObject.fromObject(textEntity);
////            token = jsStr.getString("zone_id");//获取id的值
////        }
////        return zone_id;
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	
//	/**
//	 * 描    述：添加ZoneIP
//	 * 创建人：txr
//	 * 日    期：2015-5-13
//	 */
//	public static String lssuedAddZoneIP(String token,String zoneName,String[] zone_ips){
//		//组织发送内容JSON
//		JSONObject json = new JSONObject();
//		json.accumulate("zone_name", zoneName);
//		json.accumulate("zone_ip", zone_ips);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + ZONE_IP;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//        
//        //获取响应结果
////        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json);
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token",token).post(ClientResponse.class, json);
//        int status = response.getStatus();
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	
//	/**
//	 * 描    述： 删除ZoneIP
//	 * 创建人：txr
//	 * 日    期：2015-5-13
//	 */
//	public static String lssuedDeleteZoneIP(String token,String zone_ip){
//		//组织发送内容JSON
//		JSONObject json = new JSONObject();
//		json.accumulate("zone_ip", zone_ip);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + ZONE_IP + "/" + zone_ip;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//        
//        //获取响应结果
////        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json);
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token",token).delete(ClientResponse.class, json);
//        int status = response.getStatus();
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	/**
//	 * 描    述：下发删除Zone任务
//	 * 创建人：于永波
//	 * 日    期：2015-3-17
//	 */
//	public static String lssuedDeleteZoneTask(String token,String zone_name){
//		//组织发送内容JSON
//		JSONObject json = new JSONObject();
//		json.accumulate("zone_name", zone_name);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + UPDATE_ZONE + zone_name;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//        
//        //获取响应结果
////        service.type(MediaType.APPLICATION_JSON).delete();
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token",token).delete(ClientResponse.class);
//        int status = response.getStatus();
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	/**
//	 * 描    述：下发创建引流任务
//	 * 创建人：于永波
//	 * 日    期：2015-3-17
//	 */
//	public static String lssuedCreateDivertTask(String token,String[] zone_ips){
//		//组织发送内容JSON
//		JSONObject json = new JSONObject();
//		json.accumulate("zone_ip", zone_ips);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + CREATE_DIVERT;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//        
//        //获取响应结果
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token",token).post(ClientResponse.class, json);
//        int status = response.getStatus();
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	/**
//	 * 描    述：下发删除引流任务
//	 * 创建人：于永波
//	 * 日    期：2015-3-17
//	 */
//	public static String lssuedDeleteDivertTask(String token,String zone_ip){
//		//组织发送内容JSON
//		JSONObject json = new JSONObject();
//		json.accumulate("zone_ip", zone_ip);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + DELETE_DIVERT + zone_ip;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//        
//        //获取响应结果
////        service.type(MediaType.APPLICATION_JSON).delete();
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token",token).delete(ClientResponse.class,json);
//        int status = response.getStatus();
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	/**
//	 * 描    述：下发创建黑洞任务
//	 * 创建人：于永波
//	 * 日    期：2015-3-17
//	 */
//	public static String lssuedCreateBlackHoleTask(String token,String[] zone_ips){
//		JSONObject json = new JSONObject();
//		json.accumulate("zone_ip", zone_ips);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + CREATE_BLACKHOLE;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//        
//        //获取响应结果
////        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json);
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token",token).post(ClientResponse.class, json);
//        int status = response.getStatus();
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	/**
//	 * 描    述：下发删除黑洞任务
//	 * 创建人：于永波
//	 * 日    期：2015-3-17
//	 */
//	public static String lssuedDeleteBlackHoleTask(String token,String zone_ip){
//		//组织发送内容JSON
//		JSONObject json = new JSONObject();
//		json.accumulate("zone_ip", zone_ip);
//		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + DELETE_BLACKHOLE + zone_ip;
//    	//创建jersery客户端配置对象
//	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
//	    //检查安全传输协议设置
//	    buildConfig(url,config);
//	    //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource(url);
//        //设置报文头
////        service.getRequestBuilder().header("User-Agent", "Mozilla/4.0 (X-Auth-Token "+ token+";compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//        
//        //获取响应结果
////        service.type(MediaType.APPLICATION_JSON).delete();
//        ClientResponse response = service.type(MediaType.APPLICATION_JSON).header("X-Auth-Token",token).delete(ClientResponse.class,json);
//        int status = response.getStatus();
//        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
//        return status+"";
//	}
//	
//	/**
//	 * 功能描述：空字符串转化方法
//	 * 参数描述:String str 要转化的字符
//	 *		 @time 2015-01-05
//	 */
//	private static String nullFilter(String str) {
//    	return str==null ? "" : str;
//    }
//	/**
//	 * 功能描述：安全通信配置设置
//	 * 参数描述:String url 路径,ClientConfig config 配置对象
//	 *		 @time 2015-01-05
//	 */
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
//	public static void main(String[] args) {
//		String token = "e44777805d06f5d664438b9c097d8096087b7c572f00b174904869ea1870b0af";
////		System.err.println(auth());
//		//创建
////		System.out.println(lssuedCreateZoneTask(token, "test999", new String[]{"117.12.12.14/32","139.33.30.0/24"}));
//		
//		//查询
////		System.err.println(lssuedQueryZoneTask(token));;
////		//添加IP
////		System.out.println(lssuedAddZoneIP(token,"test0001", new String[]{"114.12.12.13/32","135.33.34.0/24"}));
////		//查询
////		System.err.println(lssuedQueryZoneTask(token, "test999"));;
//		//删除
//		//更新
////		System.err.println(lssuedUpdateZoneTask(token, new String[]{"1.1.1.12/32","1.3.3.6/24"}, "test999"));
////		System.err.println(lssuedQueryZoneTask(token, "test0002"));;
//		//删除IP
////		System.err.println(lssuedDeleteZoneIP(token, "1.3.3.0"));
////		System.err.println(lssuedQueryZoneTask(token, "test0002"));;
//		//删除ZONE
////		System.err.println(lssuedDeleteZoneTask(token, "test0002"));
////		System.err.println(lssuedQueryZoneTask(token, "test0002"));;
//		
//		//创建引流
////		System.out.println(lssuedCreateDivertTask(token, new String[]{"135.33.34.200/32"}));//133.33.34.0/32
//		//删除引流
//		System.out.println(lssuedDeleteDivertTask(token, "135.33.34.200"));
//		
//		//创建黑洞
////		System.out.println(lssuedCreateBlackHoleTask(token, new String[]{"133.33.34.0"}));//133.33.34.0/32
////		//删除黑洞
////		System.out.println(lssuedDeleteBlackHoleTask(token, "133.33.34.0"));
//		
//		
//		
//	}
//	
//	
//}
