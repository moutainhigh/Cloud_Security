package com.cn.ctbri.southapi.adpater.manager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;


public class NsfocusWAFOperation extends CommonDeviceOperation {
	private  String postMethod(String url, String xml) {
		//创建客户端配置对象
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//连接服务器
		WebResource service = client.resource(url);
		//获取响应结果
		ClientResponse response = service.type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(ClientResponse.class, xml);
		String cookie = response.getCookies().toString();
		String body = response.getEntity(String.class);
		//For 2
		return cookie+"/r/n"+body;
	}
	
	/**
	 * 功能描述：post方法请求(不填充xml)
	 * @time 
	 * 2015-10-21
	 * @param url
	 * @param sessionId
	 * @return String响应结果
	 */
	private   String postMethod(String url) {
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //service.type("application/x-download");
        //连接服务器，返回结果
        //String response = service.cookie(new NewCookie("sessionid",sessionId)).type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).delete(String.class);
        String response = service.type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class);
        return response;
	}
	private String getMethod(String url){
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter("admin", "nsfocus"));
        WebResource service = client.resource(url);
        System.out.println(service.toString());
        String response = service.type(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_JSON).get(String.class);
        return response;
    }
	public void getWafConfig() throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read("");
	}
	
	
	public static String getWAFAuth(String apiKey, String apiValue, String method) {
		long timestamp = System.currentTimeMillis();
		String apivalue1 = apiValue;
		String signature = "apikey="+apiKey+
							"method="+method+
							"timestamp="+System.currentTimeMillis()+apivalue1;
			//System.out.println(signature);
		String md5String = getMd5(signature);
			//System.out.println(md5String);
		String authString = "?timestamp="+timestamp+
							"&apikey="+apiKey+
							"&method=get&sign="+md5String;
		return authString;

	}
	public static String getMd5(String plainText) {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(plainText.getBytes());  
            byte b[] = md.digest();  
            int i;  
            StringBuffer buf = new StringBuffer("");  
            for (int offset = 0; offset < b.length; offset++) {  
                i = b[offset];  
                if (i < 0)  
                    i += 256;  
                if (i < 16)  
                    buf.append("0");  
                buf.append(Integer.toHexString(i));  
            }  
            //32位加密  
            return buf.toString();  
            // 16位的加密  
            //return buf.toString().substring(8, 24);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return null;  
        }  
  
    }
	
	public String getText(String url) {
		String authString = getWAFAuth("vmwaf", "E34A-44A6-E12B-E1C9", "get");
		String returnString = getMethod(url+authString);
		return returnString;
	}
	
	public static void main(String[] args) {
		NsfocusWAFOperation operation = new NsfocusWAFOperation();
		System.out.println(operation.getText("https://219.141.189.189:58442/rest/v1/sites"));
	}
	
}









