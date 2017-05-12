package com.cn.ctbri.southapi.adapter.scanner;


import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;
import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.*;


import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



public class ArnhemDeviceOperation extends CommonDeviceOperation{
	
	static Logger logger = Logger.getLogger(ArnhemDeviceOperation.class.getName());
	
	
	private String arnhemServerWebrootUrl = "";
	private String username = "";
	private String password = "";
	private String connectSessionId = "";
	public ArnhemDeviceOperation() {
	}
	


	/**
	 * 功能描述：空字符串转化方法
	 * 参数描述:String str 要转化的字符
	 *		 @time 2015-01-05
	 */
	private  String nullFilter(String str) {
    	return str==null ? "" : str;
    }
	
	
	
	
	private WebTarget createBasicWebTarget(String targetUrl) {
        SSLContext ctx = getSSLContext();
		ClientBuilder clientBuilder = ClientBuilder.newBuilder().sslContext(ctx);
		HostnameVerifier allowAll = new HostnameVerifier() 
 	    {
 	        @Override
 	        public boolean verify(String hostname, SSLSession session) {
 	            return true;
 	        }
 	    };
		//buildConfig(targetUrl, clientBuilder);
		Client c = clientBuilder.hostnameVerifier(allowAll).build();
		//连接服务器
		WebTarget webTarget = c.target(targetUrl);
		return webTarget;
	}
    
	/**
	 * 序号：4.1
	 * 功能描述： 获取SessionId
	 *		 @time 2015-01-05
	 */	
	public boolean createSessionId(String username, String password, String serverWebRoot){
		 // 登陆信息
		this.username=username;
		this.password=password;
		arnhemServerWebrootUrl=serverWebRoot;
		String xmlContent = "<Login><Name>" + username+ "</Name><Password>" + password + "</Password></Login>";
   	 	// 登陆服务器地址
        String url = "/rest/login";

        WebTarget webTarget = createBasicWebTarget(serverWebRoot);
        // 建立客户端

		try {

			String response = webTarget.path(url).request().post(Entity.entity(xmlContent,MediaType.APPLICATION_XML_TYPE),String.class);
	        System.out.println(response); 
			if(response==null){
	        	 return false;
	         }
	         // 创建XML解析对象
	         SAXReader reader = new SAXReader();
	            // 加载XML
	         Document doc = reader.read(IOUtils.toInputStream(response));
	            //解析且获取回话ID
	         Element ele = doc.getRootElement();
	         Element s = ele.element("SessionId");
	         connectSessionId = s.getText();

	         arnhemServerWebrootUrl = serverWebRoot;

	         return true;
	   	}catch(Exception e) {
	   		e.printStackTrace();
			return false;
	   	}finally {
			//client.destroy();
		}
	}
	
	
	/**
	 * 功能描述：post方法请求(填充xml)
	 * 参数描述:String sessionId 回话ID, String taskId任务ID
	 * @time 2015-01-05
	 * @param url
	 * @param xml
	 * @param sessionId
	 * @return String 响应结果
	 */
	
    public static void main(String[] args) {
		ArnhemDeviceOperation operation = new ArnhemDeviceOperation();
		System.out.println(operation.createSessionId("admin", "admin123456", "https://219.141.189.187:61443"));
	}
}
