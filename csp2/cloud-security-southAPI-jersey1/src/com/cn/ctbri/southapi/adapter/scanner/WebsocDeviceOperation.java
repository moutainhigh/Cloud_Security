package com.cn.ctbri.southapi.adapter.scanner;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import net.sf.json.JSONObject;

import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

public class WebsocDeviceOperation {
	private String connectSessionId = null;
	private String serverWebRoot = null;
	private String username = null;
	private String password = null;
	public WebsocDeviceOperation(){
	}
	/**
	 * 功能描述：安全通信配置设置
	 * 参数描述:String url 路径,ClientConfig config 配置对象
	 *		 @time 2015-01-05
	 */
	private void buildConfig(String url,ClientConfig config) {
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
	 */
	private SSLContext getSSLContext() {
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
	
	public String postmethod(String url,String jsonContent) {
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.cookie(new NewCookie("sessionid",connectSessionId)).type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class, jsonContent);      
        String code = JSONObject.fromObject(response).getString("code");
        if(null==code||"".equalsIgnoreCase(code)||"101".equalsIgnoreCase(code)){
        	createSessionId(username, password, serverWebRoot);
        	String redirectResponse = service.cookie(new NewCookie("sessionid",connectSessionId)).type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class, jsonContent); 
        	client.destroy();
        	return redirectResponse;
        }else {
        	client.destroy();
        	return response;			
		}
	}
	
	/**
	 * 
	 * 功能描述： 获取SessionId
	 */
	public boolean createSessionId(String username, String password,String serverWebRoot){
		try {
			this.serverWebRoot = serverWebRoot;
			this.username = username;
			this.password = password;
			System.out.println(serverWebRoot);
			 // 登陆信息
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("username", this.username);
			jsonObj.put("password", this.password);
			String jsonContent = "parameter="+jsonObj.toString();
	         String url = this.serverWebRoot + "/api/v2/login_auth/";         
	         // 创建客户端配置对象
	         ClientConfig config = new DefaultClientConfig(); 
	         if(url.startsWith("http")) {
	         	SSLContext ctx = getSSLContext();
	         	config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
	         		     new HostnameVerifier() {
	         		         public boolean verify(String s, SSLSession sslSession ) {
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
	         JSONObject jsStr = JSONObject.fromObject(textEntity);
	         String jsID = jsStr.getString("code");//获取id的值
	         if(jsID.equals("0")){
	             List<String> headers = response.getHeaders().get("Set-Cookie");
	             for (int i = 0; i < headers.size(); i++) {
	                if(headers.get(i).indexOf("sessionid")!=-1){
	                    connectSessionId = headers.get(i).substring(headers.get(i).indexOf("=")+1,headers.get(i).indexOf(";"));
	                }
                }
	         }
	         return true;
	   	}catch(Exception e) {
	   		e.printStackTrace();
	   	}
		return false;
	}

	/**

	 * 功能描述：下发任务
	 * 参数描述：sessionId 回话Id,taskId 任务ID,destURL 监测目标URL,destIP 监测目标IP
	 *        destPort  监测目标PORT,taskSLA   任务模板名称
	 * @time 2015-01-05
	 * @param sessionid
	 * @return
	 */
	public String disposeScanTask(ScannerTaskUniParam scannerTaskUniParam,HashMap<String, String>mapTemplate){
		//组织发送内容json
	    JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", scannerTaskUniParam.getTaskId());
        jsonObj.put("site_list", new String[] {scannerTaskUniParam.getDestURL()});
        jsonObj.put("plugin", mapTemplate.get(scannerTaskUniParam.getTaskSLA()));
		String jsonContent = "parameter="+jsonObj.toString();
		//创建任务发送路径
    	String url = serverWebRoot+ "/api/v2/vgroup/create_temp/";
    	String response = postmethod(url,jsonContent);
    	System.out.println("response="+response);
		JSONObject responseObject = new JSONObject().fromObject(response);
        if ("0".equalsIgnoreCase(responseObject.getString("code"))) {
			responseObject.put("status", "Success");
		}else {
			responseObject.put("status", "Fail");
		}
		return	responseObject.toString();
	}

	
	
	/**
     * 获取临时组检测进度
     * @param sessionId 会话id
     * @param virtual_group_id
     * @return 任务状态代码
     */
    public String getProgressByVirtualGroupId(ScannerTaskUniParam scannerTaskUniParam) {
    	JSONObject jsonObj = new JSONObject();
    	System.out.println(scannerTaskUniParam.getVirtualGroupId());
		jsonObj.put("virtual_group_id", scannerTaskUniParam.getVirtualGroupId());
		jsonObj.put("detail", true);
		String jsonContent = "parameter="+jsonObj.toString();
		//创建路径
		String url = serverWebRoot + "/api/v2/vgroup/progress_temp/";
        String response = postmethod(url, jsonContent);
		return	response;
    }
    
    public String postDetectResult(String url, String jsonContent) {
    	//创建jersery客户端配置对象
	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
	    buildConfig(url,config);
	    //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, jsonContent);  
    	return response;
	}
    
    public String getTemplate() {
		return "";
	}
}
