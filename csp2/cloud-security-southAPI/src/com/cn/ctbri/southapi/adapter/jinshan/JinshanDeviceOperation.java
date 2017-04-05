package com.cn.ctbri.southapi.adapter.jinshan;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;
import com.cn.ctbri.southapi.adapter.utils.EncryptUtility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class JinshanDeviceOperation extends CommonDeviceOperation{
	private static String apiSecret;
	private static String baseUrl;
	private static int SUCCESS = 0;
	//加载配置文件
	static{
		
		try {
			Properties properties = new Properties();
			properties.load(JinshanDeviceOperation.class.getClassLoader().getResourceAsStream("SystemServiceConfig.properties"));
			apiSecret = properties.getProperty("timeOnApiSecret");
			baseUrl = properties.getProperty("timeOnBaseUrl");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//创建基础webresource通信资源
	private Client createBasicWebClient(String url) {
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		//连接服务器

		return client;
	}
	private String postMethodWithParams(String url,HashMap paramsHashMap) {
        
        MultivaluedMapImpl params = new MultivaluedMapImpl();
        Iterator iterator = paramsHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			params.add(entry.getKey().toString(), entry.getValue().toString());
		}
		Client client = createBasicWebClient(url);
        WebResource service = client.resource(url);
        String response = service.post(String.class, params);
        client.destroy();
        return response;
	}
	
	//加密算法 生成最终签名结果
	private String generateSign(JSONObject paramsObject) {
		StringBuffer sb = new StringBuffer();
		sb.append(apiSecret);
        for (Map.Entry<String, Object> entry : paramsObject.entrySet()) {
            sb.append(entry.getKey()+entry.getValue());
        }
		sb.append(apiSecret);
		System.out.println(sb);
		return EncryptUtility.getMD5(sb.toString()).toUpperCase();
	}
	//订单接口 
	public String getOrderIndex(JSONObject jsonObject){
		if (jsonObject.get("companyId")==null||jsonObject.getString("companyId").length()<=0
				||jsonObject.get("name")==null||jsonObject.getString("name").length()<=0
				||jsonObject.get("tCount")==null||jsonObject.getString("tCount").length()<=0) {
			JSONObject errorJsonObject = new JSONObject(true);
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("reason", "some parameters are empty");
			return errorJsonObject.toJSONString();
		}
		HashMap map = new HashMap();
		map.put("company_id", jsonObject.getString("companyId"));
		map.put("name", jsonObject.getString("name"));
		map.put("t_count", jsonObject.getString("tCount"));
		String urlString = baseUrl + "/openapi/Order/index";
		String returnJsonString = postMethodWithParams(urlString, map);
		//按照自己设定的格式返回相关内容
		JSONObject returnJsonObject = JSON.parseObject(returnJsonString);
		if (returnJsonObject.getInteger("result")==SUCCESS) {
			JSONObject successReturnObject = new JSONObject();
			successReturnObject.put("status", "success");
			return successReturnObject.toJSONString();
		}else {
			JSONObject failedReturnObject = new JSONObject();
			failedReturnObject.put("status", "failed");
			failedReturnObject.put("message", returnJsonObject.getString("msg"));
			return failedReturnObject.toJSONString();
		}
	}
	//获取卸载密码接口
	public String getUninstallInfo(JSONObject jsonObject) {
		if (jsonObject.get("companyId")==null||jsonObject.getString("companyId").length()<=0){
			JSONObject errorJsonObject = new JSONObject(true);
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("reason", "some parameters are empty");
			return errorJsonObject.toJSONString();
		}
		HashMap map = new HashMap();
		map.put("company_id", jsonObject.getString("companyId"));
		String urlString = baseUrl+"/openapi/Order/get_uninstall_info";
		String returnJsonString = postMethodWithParams(urlString, map);
		JSONObject returnJsonObject = JSONObject.parseObject(returnJsonString);
		if (returnJsonObject.getInteger("result")==SUCCESS) {
			JSONObject successReturnObject = new JSONObject(true);
			successReturnObject.put("status", "success");
			String uninstallPassword = returnJsonObject.getJSONObject("data").getString("uninstall_pwd");
			successReturnObject.put("uninstallPassword", uninstallPassword);
			return successReturnObject.toJSONString();
		}else {
			JSONObject failedReturnObject = new JSONObject();
			failedReturnObject.put("status", "failed");
			failedReturnObject.put("message", returnJsonObject.getString("msg"));
			return failedReturnObject.toJSONString();
		}
	}
	//获取安装点数接口
	//TODO 修改返回结构
	public String getHostCount(JSONObject jsonObject) {
		if (jsonObject.get("companyId")==null||jsonObject.getString("companyId").length()<=0){
			JSONObject errorJsonObject = new JSONObject(true);
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("reason", "The companyId is null");
			return errorJsonObject.toJSONString();
		}
		HashMap map = new HashMap();
		map.put("company_id", jsonObject.getString("companyId"));
		String urlString = baseUrl+"/openapi/Order/get_host_count";
		String returnJsonString = postMethodWithParams(urlString, map);
		JSONObject returnJsonObject = JSONObject.parseObject(returnJsonString);
		if (returnJsonObject.getInteger("result")==SUCCESS) {
			JSONObject successReturnObject = new JSONObject(true);
			successReturnObject.put("status", "success");
			JSONObject hostCountObject = returnJsonObject.getJSONObject("data");
			successReturnObject.put("allCount", hostCountObject.getString("t_all_count"));
			successReturnObject.put("installCount", hostCountObject.getString("t_install_count"));
			successReturnObject.put("surplusCount", hostCountObject.getString("t_surplus_count"));
			return successReturnObject.toJSONString();
		}else {
			JSONObject failedReturnObject = new JSONObject();
			failedReturnObject.put("status", "failed");
			failedReturnObject.put("message", returnJsonObject.getString("msg"));
			return failedReturnObject.toJSONString();
		}
	}
	//获取访问地址，返回的内容经过base64加密
	public String getOauthUrl(JSONObject jsonObject) {
		if (jsonObject.get("companyId")==null||jsonObject.getString("companyId").length()<=0){
			JSONObject errorJsonObject = new JSONObject(true);
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("reason", "the companyId is null");
			return errorJsonObject.toJSONString();
		}

		String companyIdString = jsonObject.getString("companyId");
		String timeStampString = String.valueOf(System.currentTimeMillis());
		JSONObject paramsObject = new JSONObject(true);
		paramsObject.put("company_id", companyIdString);
		paramsObject.put("timestamp", timeStampString);
		String sercetString = generateSign(paramsObject);
		
		StringBuffer sb = new StringBuffer();
		sb.append(baseUrl);
		sb.append("/Oauth/index?");
		sb.append("company_id="+companyIdString);
		sb.append("&timestamp="+timeStampString);
		sb.append("&sercet="+sercetString);
		JSONObject returnJsonObject = new JSONObject();
		returnJsonObject.put("status", "success");
		returnJsonObject.put("url", EncryptUtility.encodeBase64Str(sb.toString()));
		return returnJsonObject.toJSONString();
	}
}
