package com.cn.ctbri.southapi.adapter.waf;

import java.util.Iterator;

import javax.ws.rs.core.MediaType;

import se.akerfeldt.com.google.gson.JsonArray;
import se.akerfeldt.com.google.gson.JsonElement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.org.apache.bcel.internal.generic.RETURN;


public class NsfocusWAFOperation extends CommonDeviceOperation {
	private String nsfocusWafUrl ="";
	private static final String REST_URI_V1 = "/rest/v1";
	
	private String nsfocusWafUsername = "";
	private String nsfocusWafPassword = "";
	private String nsfocusAPIKey = "";
	private String nsfocusAPIValue = "";
	
	public NsfocusWAFOperation(String apiAddr, String apiKey, String apiValue, String apiUsername, String apiPassword) {
		initNsfocusWafDevice(apiAddr, apiKey, apiValue, apiUsername, apiPassword);
	}
	
	public boolean initNsfocusWafDevice(String apiAddr, String apiKey, String apiValue, String apiUsername, String apiPassword ) {
		if(null==apiAddr||"".equalsIgnoreCase(apiAddr)) return false;
		nsfocusWafUrl = apiAddr;
		if(null==apiKey||"".equalsIgnoreCase(apiKey)) return false; 
		nsfocusAPIKey = apiKey;
		if(null==apiValue||"".equalsIgnoreCase(apiValue)) return false;
		nsfocusAPIValue = apiValue;
		if(null==apiUsername||"".equalsIgnoreCase(apiUsername)) return false;
		nsfocusWafUsername = apiUsername;
		if(null==apiPassword||"".equalsIgnoreCase(apiPassword)) return false;
		nsfocusWafPassword = apiPassword;
		return true;
		
	}
	
	private WebResource createBasicWebResource(String url) {
    	ClientConfig config = new DefaultClientConfig();
    	//通信层配置设定
		buildConfig(url,config);
		//创建客户端
		Client client = Client.create(config);
		client.addFilter(new HTTPBasicAuthFilter(nsfocusWafUsername,nsfocusWafPassword));
		//连接服务器
		WebResource service = client.resource(url);

		return service;
	}
	/**
	 * 功能描述：post方法请求(填充xml)
	 * @time 
	 * 2015-10-21
	 * @param url
	 * @param sessionId
	 * @return String响应结果
	 */
	private  String postMethod(String url, String jsonString) {
		//创建客户端配置对象
		WebResource service = createBasicWebResource(url);
		Builder builder = service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		//获取响应结果
		ClientResponse response = builder.post(ClientResponse.class, jsonString);
		//String cookie = response.getCookies().toString();
		String body = response.getEntity(String.class);
		//For 2
		return body;
	}
	
	/**
	 * 功能描述：post方法请求(不填充xml)
	 * @time 
	 * 2015-10-21
	 * @param url
	 * @param sessionId
	 * @return String响应结果
	 */
	private String postMethod(String url) {
        WebResource service = createBasicWebResource(url);
        Builder builder = service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        String response = builder.post(String.class);
        return response;
	}
	private String getMethod(String url){
        WebResource service = createBasicWebResource(url);   
        Builder builder =  service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        String response = builder.get(String.class);
        return response;
    }
	private String delMethod(String url){
        WebResource service = createBasicWebResource(url);   
        Builder builder =  service.type("application/json");
        String response = builder.delete(String.class);
        return response;
	}
	private String putmethod(String url, String jsonString) {
		WebResource service = createBasicWebResource(url);
		Builder builder = service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		String response = builder.put(String.class,jsonString);
		return response;
	}
	
	public static String getWAFAuth(String apiKey, String apiValue, String method) {
		long timestamp = System.currentTimeMillis();
		String apivalue1 = apiValue;
		String signature = "apikey="+apiKey+
							"method="+method+
							"timestamp="+System.currentTimeMillis()+apivalue1;
		String md5String = getMd5(signature);
		String authString = "?timestamp="+timestamp+
							"&apikey="+apiKey+
							"&method=get&sign="+md5String;
		return authString;

	}
	
	//基础方法负责生成各自标签字符串，拼接各自的登录参数字符串以及调用登录方法
	/**
	 * get基础方法
	 * @param url
	 * @return
	 */
	private String getOperation(String url) {
		String authString = getWAFAuth(nsfocusAPIKey, nsfocusAPIValue, "get");
		String returnString = getMethod(url+authString);
		return returnString;
	}
	/**
	 * post基础方法
	 * @param url
	 * @return
	 */
	private String postOperation(String url) {
		String authString = getWAFAuth(nsfocusAPIKey, nsfocusAPIValue, "post");
		String returnString = delMethod(url+authString);
		return returnString;
	}
	
	private String postOperation(String url,String jsonString){
		String authString = getWAFAuth(nsfocusAPIKey, nsfocusAPIValue, "post");
		String returnString = postMethod(url+authString, jsonString);
		return returnString;
	}
	/**
	 * delete基础方法
	 * @param url
	 * @return
	 */
	private String delOperation(String url) {
		String authString = getWAFAuth(nsfocusAPIKey, nsfocusAPIValue, "delete");
		String returnString = postMethod(url+authString);
		return returnString;
	}
	
	private String putOperation(String url,String jsonString) {
		String authString = getWAFAuth(nsfocusAPIKey, nsfocusAPIValue, "put");
		String returnString = putmethod(url+authString, jsonString);
		return returnString;
	}
	public String getSSLCert(){
		String getSSLCertString = getOperation(nsfocusWafUrl+REST_URI_V1+"/sslcert");
		return getSSLCertString;
	}
	
	public String postSSLCert(){
		String jsonString = null;
		String postSSLCertString = postOperation(nsfocusWafUrl+REST_URI_V1+"/sslcert", jsonString);
		return postSSLCertString;
	}
	/**
	 * 资源池信息获取（获取所有站点组、站点、虚拟站点）
	 * @return
	 */
	public String getSites(){
		String getSiteString = getOperation(nsfocusWafUrl+REST_URI_V1+"/sites");
		return getSiteString;
	}
	/**
	 * 删除所有站点组、站点、虚拟站点
	 * @return
	 */
	public String deleteAllSites() {
		String deleteString = delOperation(nsfocusWafUrl+REST_URI_V1+"/sites?siteid=all");
		return deleteString;
	}
	/**
	 * 资产信息查询（站点查询）
	 * @param siteId
	 * @return
	 */
	public String getSite(String siteId) {
		String getSiteString = getOperation(nsfocusWafUrl+REST_URI_V1+"/sites/{"+siteId+"}");
		return getSiteString;
	}
	
	public String delSite(String siteId) {
		String delSiteString = delOperation(nsfocusWafUrl+REST_URI_V1+"/sites?siteid="+siteId);
		return delSiteString;
	}
	
	public String createSite(JSONObject jsonObject) {
		if (null==jsonObject.get("ip")||"".equals(jsonObject.getString("ip"))) {
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "Site ip is null!!!");
			return errJsonObject.toString();
		}
		JSONObject tempJsonObject = new JSONObject();
		tempJsonObject.put("ip", jsonObject.getString("ip"));
		if (jsonObject.get("name")!=null) tempJsonObject.put("name", jsonObject.getString("name"));
		if (jsonObject.get("port")!=null) tempJsonObject.put("port", jsonObject.getString("port"));
		if (jsonObject.get("type")!=null){
			tempJsonObject.put("type", jsonObject.getString("type"));
			if ("1".equals(jsonObject.getString("type"))&&jsonObject.get("cert")!=null) {
				tempJsonObject.put("cert", jsonObject.getString("cert"));
			}
		}
		JSONObject createSiteJsonObject = new JSONObject();
		createSiteJsonObject.put("0", tempJsonObject);
		System.out.println(createSiteJsonObject.toString());
		String createSiteString = postOperation(nsfocusWafUrl+REST_URI_V1+"/sites",createSiteJsonObject.toString());
		return createSiteString;
	}
	public String alterVirtSite(JSONObject jsonObject) {
		if(null==jsonObject.get("vSiteId")||"".equals(jsonObject.getString("vSiteId"))
		||null==jsonObject.get("parent")||"".equals(jsonObject.getString("parent"))){
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "Site ip is null!!!");
			return errJsonObject.toString();
		}
		JSONObject tempJsonObject = new JSONObject();
		if(jsonObject.get("domain")!=null&&!jsonObject.getString("domain").equals("")) tempJsonObject.put("domain", jsonObject.getString("domain"));
		if(jsonObject.get("name")!=null&&!jsonObject.getString("name").equals("")) tempJsonObject.put("name", jsonObject.getString("name"));
		tempJsonObject.put("parent", jsonObject.getString("parent"));
		JSONObject alterVirtSiteJsonObject = new JSONObject();
		alterVirtSiteJsonObject.put(jsonObject.getString("vSiteId"), tempJsonObject);
		System.out.println(">>>>>"+alterVirtSiteJsonObject.toString());
		String alterString = putOperation(nsfocusWafUrl+REST_URI_V1+"/sites/protect/virts", alterVirtSiteJsonObject.toString());
		return alterString;
	}
	
	
	public String alterSite(String siteid, String name, String ip, String port, String cert, String type) {
		String jsonString = "{\""+siteid+"\":{"+
							"\"name\":\""+name+"\","+
							"\"ip\":\""+ip+"\","+
							"\"port\":\""+port+"\","+
							"\"cert\":\""+cert+"\","+
							"\"type\":\""+type+"\"}}";
		String putSiteString = putOperation(nsfocusWafUrl+REST_URI_V1+"/sites", jsonString);
		return putSiteString;
	}
	
	public String getVirtSite(String virtSiteId) {
		String getVirtSiteString = getOperation(nsfocusWafUrl+REST_URI_V1+"/sites/protect/virts?virtid="+virtSiteId);
		return getVirtSiteString;
	}
	
	public String createVirtSite(JSONObject jsonObject) {
		if(null==jsonObject.get("parent")||"".equals(jsonObject.getString("parent"))){
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "Parent is null");
			return errJsonObject.toString();
		}
		JSONObject tempJsonObject = new JSONObject();
		tempJsonObject.put("parent", jsonObject.getString("parent"));
		if(jsonObject.get("name")!=null) tempJsonObject.put("name", jsonObject.getString("name"));
		if(jsonObject.get("domain")!=null) tempJsonObject.put("domain", jsonObject.getString("domain"));
		if(jsonObject.get("include")!=null) tempJsonObject.put("include", jsonObject.getString("include"));
		if(jsonObject.get("exclude")!=null) tempJsonObject.put("exclude", jsonObject.getString("exclude"));
		if (jsonObject.get("server")!=null) {
			JSONArray getJsonArray = JSONArray.fromObject(jsonObject.getString("server"));
			JSONArray putJsonArray = new JSONArray();
			for (Iterator<?> iterator = getJsonArray.iterator(); iterator.hasNext();) {
				JSONObject object = (JSONObject) iterator.next();
				if(null==object.get("ip")||"".equals(object.getString("ip"))
				||null==object.get("port")||"".equals(object.getString("port"))){
					continue;
				}
				putJsonArray.add(object);
			}
			tempJsonObject.put("server", putJsonArray);
		}
		JSONObject createVSiteObject = new JSONObject();
		createVSiteObject.put("0", tempJsonObject);
		String createVSiteString = postOperation(nsfocusWafUrl+REST_URI_V1+"/sites/protect/virts",createVSiteObject.toString());
		return createVSiteString;
	}
	
	public String postIpToEth(JSONObject jsonObject) {
		if (null==jsonObject.get("ip")||"".equals(jsonObject.getString("ip"))
		||null==jsonObject.get("mask")||"".equals(jsonObject.getString("mask"))) {
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "Site ip or mask is null!!!");
			return errJsonObject.toString();			
		}
		JSONObject tempJsonObject = new JSONObject();
		tempJsonObject.put("ip", jsonObject.getString("ip"));
		tempJsonObject.put("mask", jsonObject.getString("mask"));
		
		JSONObject postIpJsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(tempJsonObject);
		postIpJsonObject.put("ip_address", jsonArray);
		String postIpString = postOperation(nsfocusWafUrl+REST_URI_V1+"/interfaces/eth2/ip", postIpJsonObject.toString());
		return postIpString;
	}
	
	public static void main(String[] args) {

		System.out.println(MediaType.APPLICATION_JSON.toString());
		NsfocusWAFOperation operation = new NsfocusWAFOperation("https://219.141.189.189:58442/","vmwaf","E34A-44A6-E12B-E1C9","admin","nsfocus");
		String jsonString = "{\"ip_address\":[{"+
							"\"ip\":\"65.61.137.117\","+
							"\"mask\":\"255.255.255.0\"}]}";
		/*
		 *
		 * 
		String jsonString = "{\"0\":{"
				+ "\"name\":\"hello\","
				+ "\";*/
		System.out.println(jsonString);
		String responseString = operation.postOperation("https://219.141.189.189:58442/rest/v1/interfaces/eth2/ip", jsonString);
		System.out.println(">>>>>"+responseString);
		JSONObject jsonObject = JSONObject.fromObject(responseString);
		System.out.println("obj="+jsonObject.toString());
	}
	
}









