package com.cn.ctbri.southapi.adapter.systemserv;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.alibaba.fastjson.JSONObject;
import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;
import com.cn.ctbri.southapi.adapter.utils.EncryptUtility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;



public class NsfocusSysServOperation extends CommonDeviceOperation {
	private static String apiKey;
	private static String apiSecret;
	private static String baseURL;
	private static String createOrderURI;
	private static String renewOrderURI;
	//读取配置文件并初始化参数
	static{
		
		try {
			Properties properties = new Properties();
			properties.load(NsfocusSysServOperation.class.getClassLoader().getResourceAsStream("SystemServiceConfig.properties"));
			apiKey = properties.getProperty("AuroraScanApiKey");
			apiSecret = properties.getProperty("AuroraScanApiSecret");
			baseURL = properties.getProperty("AuroraScanBaseURL");
			createOrderURI = properties.getProperty("AuroraScanCreateOrderURI");
			renewOrderURI = properties.getProperty("AuroraScanRenewOrderURI");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String createHashMac(JSONObject jsonObject) {
		String signString = jsonObject.toString();
		String signHashString = EncryptUtility.HMACSHA256(signString, apiKey);
		return signHashString;
		
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
				
		JSONObject jsonObject = new JSONObject(false);
		jsonObject.put("api_id", apiId);
		jsonObject.put("api_key", apiKey);
		jsonObject.put("timestrap", timestrap);
		jsonObject.put("api_secret", apiSecret);

		String signHashString = createHashMac(jsonObject);
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
	 * 创建绿盟极光自助扫描订单apiid=501
	 * 
	 * @param orderJsonObject
	 * @return responseJsonString
	 * JSONObject orderJsonObject
	 */
	public String createNsfocusSysOrder(JSONObject jsonObject){
		//判断输入是否正确
		if (jsonObject.get("userId")==null||jsonObject.getString("userId").length()<=0||
			jsonObject.get("orderId")==null||jsonObject.getString("orderId").length()<=0||
			jsonObject.get("mobile")==null||jsonObject.getString("mobile").length()<=0||
			jsonObject.get("customerName")==null||jsonObject.getString("customerName").length()<=0
		){
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("message", "parameter error");
			return errJsonObject.toJSONString();
		}
		
		//建立参数对象，获取时间戳
		JSONObject paramJsonObject = new JSONObject(false);
		Long timestrap = System.currentTimeMillis()/1000;

		
		
		/**
		 * 用户下单接口，apiid=501
		 */
		paramJsonObject.put("sku_id", "anqb000001");
		paramJsonObject.put("AnqbUid", jsonObject.getString("userId"));
		paramJsonObject.put("AnqbOrderID", jsonObject.getString("orderId"));
		paramJsonObject.put("mobile", jsonObject.getString("mobile"));
		paramJsonObject.put("customer_name", jsonObject.getString("customerName"));


		Form form = new Form();
		form.add("api_key", apiKey);
		form.add("timestrap", timestrap);
		form.add("api_id", 501);
		form.add("sign", createSign(501, apiKey, apiSecret, timestrap).getSign());
		form.add("params", createParamsString(paramJsonObject));
		String responseJsonString = postMethod(baseURL+createOrderURI, form);
		System.out.println(responseJsonString);

		
		JSONObject responseJsonObject = JSONObject.parseObject(responseJsonString);
		
		//TODO 判断返回是否正确，是否能得到免登地址
		if (false) {
			
		}
		/**
		 * 	生成免登接口地址，apiid=503	
		 */
		String instanceIdString = responseJsonObject.getString("instanceId");
		StringBuffer authBaseUrlBuffer = new StringBuffer();
		authBaseUrlBuffer.append(responseJsonObject.getJSONObject("appInfo").getString("authUrl"));
		

		JSONObject authSignJsonObject = new JSONObject(false);
		authSignJsonObject.put("instanceId", instanceIdString);
		authSignJsonObject.put("timestrap", timestrap);
		authSignJsonObject.put("api_key", apiKey);
		authSignJsonObject.put("api_secret", apiSecret);
		authSignJsonObject.put("api_id", 503);
		System.out.println(authSignJsonObject);
		String authSignString = createHashMac(authSignJsonObject);
		
		
		authBaseUrlBuffer.append("?instanceId="+instanceIdString);
		authBaseUrlBuffer.append("&timestrap="+timestrap);
		authBaseUrlBuffer.append("&api_key="+apiKey);
		authBaseUrlBuffer.append("&api_id=503");
		authBaseUrlBuffer.append("&sign="+authSignString);
		
		JSONObject returnJsonObject = new JSONObject();
		returnJsonObject.put("status", "success");
		returnJsonObject.put("instanceId", instanceIdString);
		returnJsonObject.put("url", authBaseUrlBuffer.toString());
		
		return returnJsonObject.toJSONString();
	}

	public String renewNsfocusSysOrder(JSONObject jsonObject) {

		String instanceId;
		int cycle;
		Date expiredTime;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			instanceId = jsonObject.getString("instanceId");
			cycle = jsonObject.getIntValue("cycle");
			String expiredTimeString = jsonObject.getString("expiredTime");
			expiredTime = sdf.parse(expiredTimeString);
		} catch (NullPointerException e) {
			// 判断输入参数是否为空
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("message", "parameter error");
			return errJsonObject.toJSONString();
		} catch (NumberFormatException e) {
			// 判断输入的周期数是否为数字
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("message", "value of cycle error");
			return errJsonObject.toJSONString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("message", "expiredtime error");
			return errJsonObject.toJSONString();
			
		}
		

		JSONObject paramJsonObject = new JSONObject();
		paramJsonObject.put("instanceId", instanceId);
		//使用时长
		paramJsonObject.put("cycle", cycle);
		//过期时间 例："2017-4-20 00:00:00"
		paramJsonObject.put("expired_time", sdf.format(expiredTime));
		
		System.out.println(paramJsonObject);
				
		Long timestrap = System.currentTimeMillis()/1000;

		
		JSONObject signJsonObject = new JSONObject(false);
		//signJsonObject.put("instanceId", instanceId);
		signJsonObject.put("timestrap", timestrap);
		signJsonObject.put("api_key", apiKey);
		signJsonObject.put("api_secret", apiSecret);
		signJsonObject.put("api_id", 502);
		
		String signString = createHashMac(signJsonObject);
		
		Form form = new Form();
		form.add("api_key", apiKey);
		form.add("timestrap", timestrap);
		form.add("params", createParamsString(paramJsonObject));
		form.add("api_id", 502);
		form.add("sign", signString);
		
		String responseJsonString = postMethod(baseURL+renewOrderURI, form);
		
		return responseJsonString;
	}
	
	public static void main(String[] args) {
		NsfocusSysServOperation operation = new NsfocusSysServOperation();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("orderId", "17041916363267699");
		jsonObject.put("userId", "594");
		jsonObject.put("mobile", "15810000000");
		jsonObject.put("customerName", "test");
		jsonObject.put("instanceId", "17041916363267699_anqb000001");
		jsonObject.put("expiredTime", "2017-4-20 00:00:00");
		jsonObject.put("cycle", "24");
		System.out.println(operation.renewNsfocusSysOrder(jsonObject));
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
