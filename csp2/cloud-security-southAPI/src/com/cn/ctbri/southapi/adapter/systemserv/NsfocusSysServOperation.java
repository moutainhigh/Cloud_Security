package com.cn.ctbri.southapi.adapter.systemserv;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;
import com.cn.ctbri.southapi.adapter.utils.EncryptUtility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import net.sf.json.JSONObject;

public class NsfocusSysServOperation extends CommonDeviceOperation {
	private static String apiKey;
	private static String apiSecret;
	private static String baseURL;
	//读取配置文件并初始化参数
	static{
		
		try {
			Properties properties = new Properties();
			properties.load(NsfocusSysServOperation.class.getClassLoader().getResourceAsStream("SystemServiceConfig.properties"));
			apiKey = properties.getProperty("AuroraScanApiKey");
			apiSecret = properties.getProperty("AuroraScanApiSecret");
			baseURL = properties.getProperty("AuroraScanBaseURL");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 创建标签
	 * @param apiId
	 * @param apiKey
	 * @param apiSecret
	 * @return timeStrap,signHashString
	 */
	private ApiSign createSign(int apiId,String apiKey,String apiSecret,Long timestrap) {
		ApiSign apiSign = new ApiSign();
		


		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_id", apiId);
		jsonObject.put("api_key", apiKey);
		jsonObject.put("api_secret", apiSecret);
		jsonObject.put("timestrap", timestrap);
		
		String signString = jsonObject.toString();
		System.out.println(signString);
		String signHashString = EncryptUtility.HMACSHA256(signString, apiKey);
		apiSign.setTimestrap(timestrap);
		apiSign.setSign(signHashString);

		return apiSign;
	}
	
	private String createParamsString(com.alibaba.fastjson.JSONObject jsonObject){
		String jsonString = jsonObject.toJSONString();
		System.out.println(jsonString);
		String paramsStringBase64 = EncryptUtility.encodeBase64Str(jsonString);
		return paramsStringBase64;
	}
	/**
	 * 带key-value参数的post方法
	 * @param url
	 * @param params
	 * @return response
	 */
	private  String postMethodWithParams(String url, MultivaluedMapImpl params) {
		//创建客户端配置对象
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//
		//连接服务器
		WebResource service = client.resource(url);
		//获取响应结果
		String response = service.queryParams(params).post(String.class);
		client.destroy();
		//For 2
		return response;
	}
	
	private  String postMethod(String url, Form form) {
		//创建客户端配置对象
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//
		//连接服务器
		WebResource service = client.resource(url);
		ClientResponse response = service.post(ClientResponse.class,form);
		//String cookie = response.getCookies().toString();
		String body = response.getEntity(String.class);
		client.destroy();
		//For 2
		return body;
	}
	/**
	 * 创建绿盟极光自助扫描订单
	 * @param orderJsonObject
	 * @return responseJsonString
	 * JSONObject orderJsonObject
	 */
	public String createNsfocusSysOrder(){
		/*if (orderJsonObject.get("apiId")==null||orderJsonObject.getString("apiId").length()<=0) {
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "apiId is null");
		}*/
		com.alibaba.fastjson.JSONObject postJsonObject = new com.alibaba.fastjson.JSONObject(false);
		com.alibaba.fastjson.JSONObject paramJsonObject = new com.alibaba.fastjson.JSONObject(false);
		Long timestrap = System.currentTimeMillis()/1000;
		System.out.println(timestrap);
		
		paramJsonObject.put("sku_id", "anqb000001");
		paramJsonObject.put("AnqbUid", "testshao");
		paramJsonObject.put("AnqbOrderID", "test20170331");
		/*
		postJsonObject.put("api_key", apiKey);
		postJsonObject.put("timestrap", timestrap);
		postJsonObject.put("api_id", 501);
		postJsonObject.put("sign", createSign(501, apiKey, apiSecret, timestrap).getSign());
		postJsonObject.put("params", createParamsString(paramJsonObject));
		System.out.println(paramJsonObject);
		System.out.println(postJsonObject.toJSONString());
		*/
		Form form = new Form();
		form.add("api_key", apiKey);
		form.add("timestrap", timestrap);
		form.add("api_id", 501);
		form.add("sign", createSign(501, apiKey, apiSecret, timestrap).getSign());
		form.add("params", createParamsString(paramJsonObject));
		String responeJsonString = postMethod("https://www.nscloud.com/api/krosa/meari/anqb/create_order", form);
		return responeJsonString;
	}
	//TODO 等待厂家接口
	/*
	public String renewNsfocusSysOrder(JSONObject orderJsonObject) {
		if (orderJsonObject.get("apiId")==null||orderJsonObject.getString("apiId").length()<=0) {
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "apiId is null");
		}
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("", "");
		String responseJsonString = postMethod("","");
		return null;
	}
	*/
	public String name() {
		return null;
	}
	/*
	public static void main(String[] args) {
		com.alibaba.fastjson.JSONObject testJsonObject = new com.alibaba.fastjson.JSONObject(false);
		testJsonObject.put("a", "123");
		testJsonObject.put("z", "sas");
		testJsonObject.put("b", "456");
		testJsonObject.put("c", "789");
		testJsonObject.put("aa", "000");
		NsfocusSysServOperation operation = new NsfocusSysServOperation();
		System.out.println(operation.createNsfocusSysOrder());
	}
	*/
}
class ApiSign{
	//标志位
	private String sign;
	//时间戳
	private Long timestrap;
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public Long getTimestrap() {
		return timestrap;
	}
	public void setTimestrap(Long timestrap) {
		this.timestrap = timestrap;
	}

}
