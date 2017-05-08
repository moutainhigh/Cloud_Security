package com.cn.ctbri.common;
import java.io.UnsupportedEncodingException;
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
import javax.ws.rs.core.NewCookie;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-05
 * 描        述：  安恒接口本地Worker
 * 版        本：  1.0
 */
public class ArnhemWorker {
	/**
	 * 安恒服务器根路径
	 */
	
	private static String SERVER_WEB_ROOT1;
	
	private static String SERVER_WEB_ROOT2;
	/**
	 * 用户名
	 */
	private static String USERNAME1;
	private static String USERNAME2;
	/**
	 * 密码
	 */
	private static String PASSWORD1;
	private static String PASSWORD2;
	
	static{
		try {
			Properties p = new Properties();
			p.load(ArnhemWorker.class.getClassLoader().getResourceAsStream("engineConfig.properties"));
//			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
//			USERNAME = p.getProperty("USERNAME");
//			PASSWORD = p.getProperty("PASSWORD");
			SERVER_WEB_ROOT1 = p.getProperty("engine_addr");
			SERVER_WEB_ROOT2 = p.getProperty("engine_addr1");
			USERNAME1 = p.getProperty("username");
			PASSWORD1 = p.getProperty("password");
			USERNAME2 = p.getProperty("username1");
			PASSWORD2 = p.getProperty("password1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArnhemWorker() {
	}
	/**
	 * 功能描述： 获取SessionId
	 * @param i 
	 *		 @time 2015-01-05
	 */
	public static String getSessionId(int engine){
		try {
			 String xmlContent = "";
			 String url = "";
	    	 if(engine==1){
	    		// 登陆信息
	    		 xmlContent = "<Login><Name>" + USERNAME1 + "</Name><Password>" + PASSWORD1 + "</Password></Login>";
	    		// 登陆服务器地址
	    		 url = SERVER_WEB_ROOT1 + "/rest/login";    
	    	 }else{
//	    		 xmlContent = "<Login><Name>" + USERNAME2 + "</Name><Password>" + PASSWORD2 + "</Password></Login>";
//	    		 url = SERVER_WEB_ROOT2 + "/rest/login";  
	    		 xmlContent = "<Login><Name>" + "admin" + "</Name><Password>" + "admin123456" + "</Password></Login>";
	    		 url = "https://219.141.189.187:61443" + "/rest/login"; 
	    	 }        
	         // 创建客户端配置对象
	         ClientConfig config = new DefaultClientConfig(); 
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
	         // 建立客户端
	         Client client = Client.create(config);
	         // 连接服务器
	         WebResource service = client.resource(url); 
	         // 发送请求，接收返回数据
	         String response = service.type(MediaType.APPLICATION_XML).post(String.class, xmlContent);
	         // 创建XML解析对象
	         SAXReader reader = new SAXReader();
	         // 加载XML
	         Document doc = reader.read(IOUtils.toInputStream(response));
	         //解析且获取回话ID
	         Element ele = doc.getRootElement();
	         Element s = ele.element("SessionId");
	         return s.getText();
	   	}catch(Exception e) {
	   		e.printStackTrace();
	   	}
		return "";
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
	/**
	 * 功能描述：下发任务
	 * 参数描述：sessionId 回话Id,taskId 任务ID,destURL 监测目标URL,destIP 监测目标IP
	 *        destPort  监测目标PORT,taskSLA   任务模板名称
	 * @param  
	 *		 @time 2015-01-05
	 */
	public static String lssuedTask(String sessionId, String taskId, String destURL, 
    		String destIP, String destPort, String taskSLA, int engine){
		//组织发送内容XML
		String xml = "<Task><TaskID>" + taskId + "</TaskID><CustomID>123123</CustomID><TaskInfo><DestURL>" +
    			destURL + "</DestURL><DestIP>" + nullFilter(destIP) + "</DestIP><DestPort>" +
    			nullFilter(destPort) + "</DestPort></TaskInfo><TaskSLA>" + taskSLA + "</TaskSLA></Task>";
		//创建任务发送路径
//    	String url = SERVER_WEB_ROOT + "/rest/task";
    	String url = "";
	   	if(engine==1){
	   		// 创建路径
	   		url = SERVER_WEB_ROOT1 + "/rest/task"; 
	   	}else{
	   		url = SERVER_WEB_ROOT2 + "/rest/task";
	   	} 
    	
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).post(String.class, xml);
        return response;
	}
	/**
	 * 功能描述：取消下发的任务
	 * 参数描述:String sessionId, String taskId
	 *		 @time 2015-01-08
	 */
	public static String removeTask(String sessionId, String taskId, int engine){
		//创建路径
//		String url = SERVER_WEB_ROOT + "/rest/task/Remove/" + taskId;
		String url = "";
	   	if(engine==1){
	   		// 创建路径
	   		url = SERVER_WEB_ROOT1 + "/rest/task/Remove/" + taskId;
	   	}else{
	   		url = "https://219.141.189.187:61443" + "/rest/task/Remove/" + taskId;
	   	} 
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //连接服务器，返回结果
        //String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).delete(String.class);
        String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class);
        return response;
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
	 * 功能描述：根据任务ID获取任务执行结果数
	 * 参数描述:String sessionId 回话ID, String taskId任务ID
	 *		 @time 2015-01-05
	 */
	public static String getResultCountByTaskID(String sessionId, String taskId, int engine) {
		//创建Url
//    	String url = SERVER_WEB_ROOT + "/rest/report/ResultCount/TaskID/" + taskId;
		String url = "";
	   	if(engine==1){
	   		// 创建路径
	   		url = SERVER_WEB_ROOT1 + "/rest/report/ResultCount/TaskID/" + taskId;
	   	}else{
	   		url = SERVER_WEB_ROOT2 + "/rest/report/ResultCount/TaskID/" + taskId;
	   	}
    	return getMethod(url, sessionId);
    }
	/**
	 * 功能描述：根据任务ID分页获取扫描结果
	 * 参数描述:String sessionId 回话ID, String taskId任务ID, String productId 引擎功能,
     *		int startNum 起始数, int size 每页大小
	 *		 @time 2015-01-05
	 */
    public static String getReportByTaskID(String sessionId, String taskId, String productId,
    		int startNum, int size, int engine) {
    	//创建请求路径
//    	String url = SERVER_WEB_ROOT + "/rest/report/TaskID";
    	String url = "";
	   	if(engine==1){
	   		// 创建路径
	   		url = SERVER_WEB_ROOT1 + "/rest/report/TaskID";
	   	}else{
	   		url = SERVER_WEB_ROOT2 + "/rest/report/TaskID";
	   	}
    	//组织请求内容XML
    	String xml = "<ResultParam><TaskID>" + taskId + "</TaskID>" +
    			"<ProductID>" + productId + "</ProductID><StartNum>" + 
    			startNum + "</StartNum><Size>" + size + "</Size></ResultParam>";
    	//发送POST方法
    	return postMethod(url, xml, sessionId);
    }
    /**
	 * 功能描述：post方法请求
	 * 参数描述:String sessionId 回话ID, String taskId任务ID
	 *		 @time 2015-01-05
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
	 *		 @time 2015-01-05
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
	 *		 @time 2015-01-05
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
	public static String getStatusByTaskId(String sessionId, String taskId, int engine) {
		String url = "";
	   	if(engine==1){
	   		// 创建路径
	   		url = SERVER_WEB_ROOT1 + "/rest/task/Test/" + taskId;   
	   	}else{
	   		url = SERVER_WEB_ROOT2 + "/rest/task/Test/" + taskId;
	   	}  
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
     * 根据任务id获取任务当前进度
     * @param sessionId 会话id
     * @param taskId 任务id
     * @return 任务状态代码
     */
    public static String getProgressByTaskId(String sessionId, String taskId, String ServiceId, int engine) {
        //组织发送内容XML
        String xml = "<Task><TaskID>" + taskId + "</TaskID><ProductID>" + ServiceId +"</ProductID ></Task>";
        //创建路径
//        String url = SERVER_WEB_ROOT + "/rest/task/getTaskProgress";
        String url = "";
	   	if(engine==1){
	   		// 创建路径
	   		url = SERVER_WEB_ROOT1 + "/rest/task/getTaskProgress"; 
	   	}else{
	   		url = SERVER_WEB_ROOT2 + "/rest/task/getTaskProgress";
	   	}  
        //创建配置
        ClientConfig config = new DefaultClientConfig();
        //绑定配置
        buildConfig(url,config);
        //创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //连接服务器，返回结果
        String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class,xml);
        return response;
    }
    
    
    /**
     * 获取引擎的存活状态
     * @param sessionId 会话id
     * @param taskId 任务id
     * @return 任务状态代码
     */
    public static String getEngineState(String sessionId,String engine_api) {
        //创建路径
        String url = engine_api + "/rest/control/GetEngineState";
        //创建配置
        ClientConfig config = new DefaultClientConfig();
        //绑定配置
        buildConfig(url,config);
        //创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //连接服务器，返回结果
        String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).get(String.class);
        return response;
    }
    
    /**
	 * 根据任务ID获取弱点记录总数
	 * @param sessionId 会话id
	 * @param taskId 任务id
	 * @param engine 
	 * @return 任务状态代码
	 */
	public static String getResultCount(String sessionId, String taskId, int engine) {
		String url = "";
	   	if(engine==1){
	   		// 创建路径
	   		url = SERVER_WEB_ROOT1 + "/rest/report/ResultCount/TaskID/" + taskId;   
	   	}else{
	   		url = SERVER_WEB_ROOT2 + "/rest/report/ResultCount/TaskID/" + taskId;
	   	}  
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //连接服务器，返回结果
        String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).get(String.class);
        return response;
	}
	
	
	public static String CreateReport(String sessionId, String taskId) {
        //组织发送内容XML
        String xml = "<ReportParam><ReportType>B3</ReportType></ReportParam>";
        //创建路径
//        String url = SERVER_WEB_ROOT1 + "/rest/report/CreateReport/" + taskId;
        String url = SERVER_WEB_ROOT1 + "/rest/report/DownloadReportFile/integrateWord_3920";
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
    
    public static void main(String[] args) throws UnsupportedEncodingException, JSONException {
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
        String sessionId = getSessionId(1);
//        String s = getEngineState(sessionId,"https://219.141.189.187:61443");
        
//        String sessionId1 = getSessionId("https://219.141.189.187:60443","developer","developer111111");
//        String s1 = getEngineState(sessionId,"https://219.141.189.187:61443");
        
        
//    	String s = lssuedTask(sessionId, "test88", "http://www.testfire.net", 
//        		"", "80", "漏洞扫描模板");
    	
//    	String p = getProgressByTaskId(sessionId, "922_15101511393080584", "1",2);
//    	String p1 = getStatusByTaskId(sessionId, "922_15101511393080584",2);
//    	
//    	System.out.println(s1);
//    	System.out.println("ok");
    	
//    	String g = getStatusByTaskId(sessionId, "test88");
        
//        String result = getReportByTaskID(sessionId, "3981_15102316092858295", "1",340, 30,2);
//        String result = getReportByTaskID(sessionId, "922_15101511393080584", "1",0, 500,2);
//    	String count = getResultCount(sessionId, "3981_15102316092858295",2);
        String result = CreateReport(sessionId,"2_16021916432586375");
    	System.out.println(result);
//    	System.out.println(count);
    }
}
