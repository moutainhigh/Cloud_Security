package com.cn.ctbri.southapi.adapter.systemserv;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;

import com.alibaba.fastjson.JSONObject;
import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;



public class CloudInsightOperation extends CommonDeviceOperation{
	private static String baseURL;
	private static String stewardURI;
	private static String stewardURL;
	static{
		
		try {
			Properties properties = new Properties();
			properties.load(CloudInsightOperation.class.getClassLoader().getResourceAsStream("SystemServiceConfig.properties"));
			baseURL = properties.getProperty("cloudInsightBaseURL");
			stewardURI = properties.getProperty("cloudInsightStewardURI");
			stewardURL = baseURL + stewardURI;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Client createBasicWebClient(String url) {
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//连接服务器
		
		return client;
	}
	
	
	private String getMethod(String url){
		Client client = createBasicWebClient(url);
		WebResource service = client.resource(url);  
        Builder builder =  service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        String response = builder.get(String.class);
        client.destroy();
        return response;
    }
	
	public String getToken(JSONObject jsonObject) {
		if (null==jsonObject.get("userId")||jsonObject.getString("userId").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			jsonObject.put("status", "failed");
			jsonObject.put("message", "userid is null");
			return errorJsonObject.toString();
		}
		String randomChar=RandomStringUtils.randomAlphanumeric(32);
		String url = stewardURL + "/getToken.do" + "?username=" + jsonObject.getString("userId") + "&"+randomChar;
		String tokenString = getMethod(url);
		JSONObject returnJsonObject = new JSONObject(true);
		returnJsonObject.put("status", "success");
		returnJsonObject.put("token", tokenString);
		returnJsonObject.put("randomChar", randomChar);
		return returnJsonObject.toJSONString();
	}
	
	public String destroyToken(JSONObject jsonObject) {
		if (null==jsonObject.get("token")||jsonObject.getString("token").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			jsonObject.put("status", "failed");
			jsonObject.put("message", "token is null");
			return errorJsonObject.toString();
		}
		String url = stewardURL + "/destroyToken.do" + "?token="+jsonObject.getString("token");
		String returnString = getMethod(url);
		JSONObject returnJsonObject = new JSONObject();
		if (returnString.equals("201")) {
			returnJsonObject.put("status", "success");
		}else {
			returnJsonObject.put("status", "failed");
		}
		return returnJsonObject.toJSONString();
		
	}
	
	public String getLoginURL(JSONObject jsonObject) {
		if (null==jsonObject.get("token")||jsonObject.getString("token").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			jsonObject.put("status", "failed");
			jsonObject.put("message", "token is null");
			return errorJsonObject.toString();
		}
		String url = stewardURL + "/securityLogin.do" + "?token="+jsonObject.getString("token");
		JSONObject returnJsonObject = new JSONObject();
		returnJsonObject.put("status", "success");
		returnJsonObject.put("url", url);
		return returnJsonObject.toJSONString();
	}

	public static void main(String[] args) {
		CloudInsightOperation operation = new CloudInsightOperation();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", "test1234567");
		String token = operation.getToken(jsonObject);
		
		System.out.println(token);
		jsonObject.clear();
		jsonObject = (JSONObject) JSONObject.parse(token);
		System.out.println(jsonObject.toJSONString());
		System.out.println(operation.getLoginURL(jsonObject));
		//System.out.println(operation.destroyToken(jsonObject));
	}
	

	
	
}
