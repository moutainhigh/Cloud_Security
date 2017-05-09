package com.cn.ctbri.common;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.apache.http.util.TextUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.jms.ResultConsumerListener.JSON_TYPE;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-12-31
 * 描        述：  south接口本地Worker
 * 版        本：  1.0
 */
public class SouthAPIWorker {
	/**
	 * southAPI服务器根路径
	 */
	
	private static String SERVER_WEB_ROOT;
	
	static{
		try {
			Properties p = new Properties();
			p.load(SouthAPIWorker.class.getClassLoader().getResourceAsStream("southAPI.properties"));
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public SouthAPIWorker() {
	}
	/**
	 * 功能描述： 获取SessionId
	 * @param deviceId
	 * @time 2015-12-31
	 */
	public static String getSessionId(String deviceId){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		String url = SERVER_WEB_ROOT + "/rest/adapter/loadDeviceAdapter";       
		//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
        String status = JSONObject.fromObject(textEntity).getString("status");
       
        System.out.println(textEntity);
        return status;
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
	/**
	 * 功能描述：下发任务
	 * 参数描述：deviceId 设备编号,taskId 任务ID,destURL 监测目标URL,destIP 监测目标IP
	 *        destPort  监测目标PORT,taskSLA   任务模板名称
	 * @param  
	 * @time 2015-12-31
	 */
	public static String disposeScanTask(String deviceId, String taskId, String destURL, 
    		String destIP, String destPort, String taskSLA){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("taskId", taskId);
		json.put("destURL", destURL);
		json.put("destIP", destIP);
		json.put("destPort", destPort);
		json.put("taskSLA", taskSLA);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/disposeScanTask";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
      
        if(deviceId.equals("11001")){
        	String jsStr = JSONObject.fromObject(textEntity).getString("result");
            String virtual_group_id = JSONObject.fromObject(jsStr).getString("virtual_group_id");
            return virtual_group_id;
        }else{
        	return textEntity;
        }
	}
	/**
	 * 功能描述：取消下发的任务
	 * 参数描述:String deviceId, String taskId
	 *		 @time 2015-12-31
	 */
	public static String removeTask(String deviceId, String taskId){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("taskId", taskId);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/removeTask";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        
        System.out.println(textEntity);
        return textEntity;
	}
	/**
	 * 功能描述：空字符串转化方法
	 * 参数描述:String str 要转化的字符
	 *		 @time 2015-12-31
	 */
	private static String nullFilter(String str) {
    	return str==null ? "" : str;
    }
	/**
	 * 功能描述：根据任务ID获取任务执行结果数
	 * 参数描述:String sessionId 回话ID, String taskId任务ID
	 *		 @time 2015-12-31
	 */
	public static String getResultCountByTaskID(String deviceId, String taskId) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("taskId", taskId);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/getResultCountByTaskID";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        
        System.out.println(textEntity);
        return textEntity;
    }
	/**
	 * 功能描述：根据任务ID分页获取扫描结果
	 * 参数描述:String sessionId 回话ID, String taskId任务ID, String productId 引擎功能,
     *		int startNum 起始数, int size 每页大小
	 *		 @time 2015-12-31
	 */
    public static String getReportByTaskID(String deviceId, String taskId, String productId,
    		int startNum, int size) {
    	//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("taskId", taskId);
		json.put("productId", productId);
		json.put("startNum", startNum);
		json.put("size", size);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/getReportByTaskID";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, json.toString());
        
        
        return response;
    }
    /**
	 * 功能描述：post方法请求
	 * 参数描述:String sessionId 回话ID, String taskId任务ID
	 *		 @time 2015-12-31
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
	 *		 @time 2015-12-31
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
	 * 根据任务id获取任务当前状态
	 * @param sessionId 会话id
	 * @param taskId 任务id
	 * @param engine 
	 * @return 任务状态代码
	 */
	public static String getStatusByTaskId(String deviceId, String taskId) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("taskId", taskId);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/getStatusByTaskId";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        String textEntity = response.getEntity(String.class);
      
        System.out.println(textEntity);
       
        return textEntity;
	}
	
	/**
     * 根据任务id获取任务当前进度
     * @param sessionId 会话id
     * @param taskId 任务id
     * @return 任务状态代码
     */
    public static String getProgressByTaskId(String deviceId, String taskId, String productId) {
    	//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("taskId", taskId);
		json.put("productId", productId);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/getProgressById";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        
        System.out.println(textEntity);
        return textEntity;
    }
    
    
    /**
     * 获取服务资源设备id
     */
    public static String getDeviceId() {
    	//组织发送内容JSON
    	String url = SERVER_WEB_ROOT + "/rest/adapter/getDeviceId";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        
       
        return response;
    }
    
    /**
     * 获取引擎的存活状态
     * @param sessionId 会话id
     * @param taskId 任务id
     * @return 任务状态代码
     */
    public static String getEngineState(String deviceId) {
    	//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/getEngineStat";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
       
        return textEntity;
    }
    /**
     * 获取性能数据参数
     * @param sessionId 会话id
     * @param taskId 任务id
     * @return 任务状态代码
     */
    public static String getEngineStatRate(String deviceId) {
    	//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/getEngineStatRate";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
//        System.out.println(textEntity);
        
        return textEntity;
    }
    
    /**
     * 获取性能数据参数
     * @param sessionId 会话id
     * @param taskId 任务id
     * @return 任务状态代码
     */
    public static String getTaskLoadInfo(String deviceId) {
    	//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/getTaskLoadInfo";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
       
        return textEntity;
    }
    
    /**
	 * 根据任务ID获取弱点记录总数
	 * @param sessionId 会话id
	 * @param taskId 任务id
	 * @param engine 
	 * @return 任务状态代码
	 */
	public static String getResultCount(String deviceId, String taskId) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("taskId", taskId);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/getResultCountByTaskID";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
       
        System.out.println(textEntity);
        return textEntity;
	}
	
	// 开始停止的任务
	public static String startTask(String deviceId, String taskId) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("taskId", taskId);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/startTask";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        
        System.out.println(textEntity);
        return textEntity;
	}

	// 停止任务
	public static String stopTask(String deviceId, String taskId) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("taskId", taskId);
    	String url = SERVER_WEB_ROOT + "/rest/adapter/stopTask";
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json.toString());
        int status = response.getStatus();
        String textEntity = response.getEntity(String.class);
        
        System.out.println(textEntity);
        return textEntity;
	}

    
    public static void main(String[] args) throws UnsupportedEncodingException {
//        JSONObject json = new JSONObject();
//        org.codehaus.jettison.json.JSONObject jsonObject = new org.codehaus.jettison.json.JSONObject();
//        jsonObject.accumulate("zone_name", "M6");
//        jsonObject.accumulate("zone_ip", "[\"12.12.12.12/32\",\"33.33.33.33/24\"]");
        //创建任务发送路径
        //创建jersery客户端配置对象
//        ClientConfig config = new DefaultClientConfig();
//        //检查安全传输协议设置
//        buildConfig("https://128.18.74.170:443/rest/openapi/ddos/zone",config);
//        //创建Jersery客户端对象
//        Client client = Client.create(config);
//        //连接服务器
//        WebResource service = client.resource("https://128.18.74.170:443/rest/openapi/ddos/zone");
//        //获取响应结果
//        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, "{\"zone_name\":\"M6\"," +
//        		"                                                                         \"zone_ip\":" +
//        		"                                                                              \"[\"12.12.12.12/32\"," +
//        		"                                                                                 \"33.33.33.33/24\"]\"}");
//        String sessionId = getSessionId("10001");
//        String s = getEngineState("10002");
        
//        String sessionId1 = getSessionId("https://219.141.189.187:60443","developer","developer111111");
//        String s1 = getEngineState(sessionId,"https://219.141.189.187:61443");
        
        
//    	String s = lssuedTask(sessionId, "test88", "http://www.testfire.net", 
//        		"", "80", "漏洞扫描模板");
    	
//    	String p = getProgressByTaskId("10001", "3981_15102316092858295", "1");
//    	String p1 = getStatusByTaskId("10002", "9999_16052919335846008");
    	
//    	System.out.println(s1);
//    	System.out.println("ok");
    	
//    	String g = getStatusByTaskId(sessionId, "test88");
        
//      String result = getReportByTaskID(sessionId, "3981_15102316092858295", "1",340, 30,2);
//        String result = getReportByTaskID("10002", "9999_16052919335846008", "1",0, 30);
//    	String count = getResultCountByTaskID("10002", "9999_16052919335846008");
//    	String en = ("10001");
//    	String stop = startTask(sessionId, "45_15120117145433875", 2);
//    	System.out.println(sessionId);
//    	String s =  disposeScanTask("10001", "test20160121111", "http://www.testfire.net", 
//        		"", "", "漏洞扫描模板");


        String en = getDeviceId();
    	System.out.println(en);
    }
}
