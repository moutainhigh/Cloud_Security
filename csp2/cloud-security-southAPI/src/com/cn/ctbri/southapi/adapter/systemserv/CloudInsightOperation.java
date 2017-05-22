package com.cn.ctbri.southapi.adapter.systemserv;

import java.io.IOException;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.alibaba.fastjson.JSONObject;
import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;




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
	
	private Client createBasicClient() {
		SSLContext ctx = getSSLContext();
		ClientBuilder clientBuilder = ClientBuilder.newBuilder().sslContext(ctx);
		HostnameVerifier allowAll = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// buildConfig(targetUrl, clientBuilder);
		Client c = clientBuilder.hostnameVerifier(allowAll).build().register(JacksonJsonProvider.class);
		// 连接服务器
		return c;
	}
	

	
	
	private String getMethod(String url){
		Client client = createBasicClient();
		
		WebTarget webTarget = client.target(stewardURL+url);
		Response response = webTarget.request().get();
		String responseString = response.readEntity(String.class);
		System.out.println(responseString);
		response.close();
		client.close();
        return responseString;
    }
	
	public String getToken(JSONObject jsonObject) {
		if (null==jsonObject.get("userId")||jsonObject.getString("userId").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			jsonObject.put("status", "failed");
			jsonObject.put("message", "userid is null");
			return errorJsonObject.toString();
		}
		String randomChar=RandomStringUtils.randomAlphanumeric(32);
		String url =  "/getToken.do?username=" + jsonObject.getString("userId") + "&"+randomChar;
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
		String url = "/destroyToken.do" + "?token="+jsonObject.getString("token");
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
