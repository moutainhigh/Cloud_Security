package com.cn.ctbri.southapi.adapter.systemserv;

import java.io.IOException;
import java.util.Properties;

import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;
import com.cn.ctbri.southapi.adapter.utils.EncryptUtility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
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
			properties.load(NsfocusSysServOperation.class.getClassLoader().getResourceAsStream("nsfocusSysServ.properties"));
			apiKey = properties.getProperty("apiKey");
			apiSecret = properties.getProperty("apiSecret");
			baseURL = properties.getProperty("baseURL");
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
	private ApiSign createSign(int apiId,String apiKey,String apiSecret) {
		ApiSign apiSign = new ApiSign();
		Long timestrap = System.currentTimeMillis();


		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_id", apiId);
		jsonObject.put("api_key", apiKey);
		jsonObject.put("api_secret", apiSecret);
		jsonObject.put("timestrap", timestrap);
		
		String signString = jsonObject.toString();
		System.out.println(signString);
		String signHashString = EncryptUtility.HMACSHA256(signString, apiKey);
		apiSign.setTimestrap(1484032931632L);
		apiSign.setSign(signHashString);

		return apiSign;
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
		//For 2
		return response;
	}
	/**
	 * 创建绿盟极光自助扫描订单
	 * @param orderJsonObject
	 * @return responseJsonString
	 */
	public String createNsfocusSysOrder(JSONObject orderJsonObject){
		if (orderJsonObject.get("apiId")==null||orderJsonObject.getString("apiId").length()<=0) {
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "apiId is null");
		}
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("api_key", "");
		params.add("timestrap", "");
		params.add("api_id", "");
		params.add("sign", "");
		params.add("params", "");
		String responeJsonString = postMethodWithParams("url", params);
		return null;
	}
	//TODO 等待厂家接口
	public String renewNsfocusSysOrder(JSONObject orderJsonObject) {
		if (orderJsonObject.get("apiId")==null||orderJsonObject.getString("apiId").length()<=0) {
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "apiId is null");
		}
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("", "");
		String responseJsonString = postMethodWithParams("", params);
		return null;
	}
	
	public String name() {
		return null;
	}
	
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
