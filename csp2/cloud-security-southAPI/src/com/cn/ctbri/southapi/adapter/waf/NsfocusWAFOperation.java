package com.cn.ctbri.southapi.adapter.waf;

import java.util.Iterator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cn.ctbri.southapi.adapter.manager.CommonDeviceOperation;



/**
 * 
 * This class is used for waf Operation   
 * @author shaozhenya  
 * @version   
 * 	1.0, 2016年6月21日 下午2:59:25
 */

public class NsfocusWAFOperation extends CommonDeviceOperation {
	//REST版本uri
	private static final String REST_URI_V1 = "/rest/v1";
	//WAF地址
	private String nsfocusWafUrl ="";	
	//WAF用户名
	private String nsfocusWafUsername = "";
	//WAF密码
	private String nsfocusWafPassword = "";
	//WAF API Key=用户名
	private String nsfocusAPIKey = "";
	//WAF API Value=密码
	private String nsfocusAPIValue = "";
	//WAF代理ip列表
	private String[] nsfocusWafPublicIPList = null;
		
	public NsfocusWAFOperation(String apiAddr, String apiKey, String apiValue, String apiUsername, String apiPassword ) {
		initNsfocusWafDevice(apiAddr, apiKey, apiValue, apiUsername, apiPassword);
	}
	
	public NsfocusWAFOperation(String apiAddr, String apiKey, String apiValue, String apiUsername, String apiPassword, String[] apiPublicIPList) {
		initNsfocusWafDevcie(apiAddr, apiKey, apiValue, apiUsername, apiPassword, apiPublicIPList);
	}
	//初始化WAF设备参数
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
	//初始化WAF设备参数，带ip列表
	public boolean initNsfocusWafDevcie(String apiAddr, String apiKey, String apiValue, String apiUsername, String apiPassword, String[] apiPublicIPList) {
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
		if (null==apiPublicIPList) return false;
		nsfocusWafPublicIPList = apiPublicIPList;
		return true;
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
		Client c = clientBuilder.hostnameVerifier(allowAll).build();
		// 连接服务器
		
		return c;
	}
	

	/**
	 * 功能描述：post方法请求(填充xml)
	 * @time 2015-10-21
	 * @param url
	 * @param sessionId
	 * @return String响应结果
	 */
	private  String postMethod(String url, String jsonString) {
		//创建客户端配置对象
		Client client = createBasicClient(url);
		WebResource service = client.resource(url);
		Builder builder = service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		//获取响应结果
		ClientResponse response = builder.post(ClientResponse.class, jsonString);
		//String cookie = response.getCookies().toString();
		String body = response.getEntity(String.class);
		//For 2
		return body;
	}
	
	private String postMethod(String url, String jsonString){
		Client client = createBasicClient();
		WebTarget webTarget = client.target(url);
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(nsfocusWafUsername, nsfocusWafPassword).build();
		Response response = webTarget.register(feature).request().post(Entity.entity(jsonString,MediaType.APPLICATION_JSON_TYPE),Response.class);
		String responsesString = response.readEntity(String.class);
		response.close();
		client.close();
		return responsesString;
	}
	
	/**
	 * 功能描述：post方法请求(不填充xml)
	 * @time 2015-10-21
	 * @param url
	 * @param sessionId
	 * @return String响应结果
	 */
	private String postMethod(String url) {
		Client client = createBasicClient(url);
		WebResource service = client.resource(url);
        Builder builder = service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        String response = builder.post(String.class);
        client.destroy();
        return response;
	}
	
	
	private String getMethod(String url) {
		Client client = createBasicClient();
		WebTarget webTarget = client.target(url);
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(nsfocusWafUsername, nsfocusWafPassword).build();
		Response response = webTarget.register(feature).request().get();
		String responsesString = response.readEntity(String.class);
		response.close();
		client.close();
		return responsesString;
	}

	private String delMethod(String url) {
		Client client = createBasicClient();
		WebTarget webTarget = client.target(url);
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(nsfocusWafUsername, nsfocusWafPassword).build();
		Response response = webTarget.register(feature).request().delete();
		String responsesString = response.readEntity(String.class);
		response.close();
		client.close();
		return responsesString;
	}
	
	private String delMethod(String url,String jsonString) {
		Client client = createBasicClient();
		WebTarget webTarget = client.target(url);
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(nsfocusWafUsername, nsfocusWafPassword).build();
		Response response = webTarget.register(feature).request().delete();
		String responsesString = response.readEntity(String.class);
		response.close();
		client.close();
		return responsesString;
	}
	
	private String delMethod(String url,String jsonString) {
		Client client = createBasicClient(url);
		WebResource service = client.resource(url);
        Builder builder =  service.type("application/json");
        String response = builder.delete(String.class, jsonString);
        client.destroy();
		return response;
	}
	
	
	private String putmethod(String url, String jsonString) {
		Client client = createBasicClient(url);
		WebResource service = client.resource(url);
		Builder builder = service.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		String response = builder.put(String.class,jsonString);
        client.destroy();
		return response;
	}
	
	private String getWAFAuth(String apiKey, String apiValue, String method) {
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
	
	private String getWAFAuthWithQuery(String apiKey, String apiValue, String method) {
		long timestamp = System.currentTimeMillis();
		String apivalue1 = apiValue;
		String signature = "apikey="+apiKey+
							"method="+method+
							"timestamp="+System.currentTimeMillis()+apivalue1;
		String md5String = getMd5(signature);
		String authString = "&timestamp="+timestamp+
							"&apikey="+apiKey+
							"&method=get&sign="+md5String;
		return authString;
	}

//基础方法负责生成各自标签字符串，拼接各自的登录参数字符串以及调用登录方法	
	//get基本操作，不带请求参数
	private String getOperation(String url) {
		String authString = getWAFAuth(nsfocusAPIKey, nsfocusAPIValue, "get");
		String returnString = getMethod(url+authString);
		return returnString;
	}
	//get基本操作，带请求参数
	private String getOperationWithQuery(String url) {
		String authString = getWAFAuthWithQuery(nsfocusAPIKey, nsfocusAPIValue, "get");
		String returnString = getMethod(url+authString);
		return returnString;
	}
	//post基本操作
	private String postOperation(String url,String jsonString){
		String authString = getWAFAuth(nsfocusAPIKey, nsfocusAPIValue, "post");
		String returnString = postMethod2(url+authString, jsonString);
		return returnString;
	}
	//delete基本操作，不带请求参数
	private String delOperation(String url) {
		String authString = getWAFAuth(nsfocusAPIKey, nsfocusAPIValue, "delete");
		String returnString = delMethod(url+authString);
		return returnString;
	}
	//delete基本操作，带请求参数
	private String delOperationWithQuery(String url) {
		String autString = getWAFAuthWithQuery(nsfocusAPIKey, nsfocusAPIValue, "delete");
		String returnString = delMethod(url+autString);
		return returnString;
	}
	//delete基本操作，带传入参数
	private String delOperation(String url,String jsonString){
		String authString = getWAFAuth(nsfocusAPIKey, nsfocusAPIValue, "delete");
		String returnString = delMethod(url+authString, jsonString);
		return returnString;
	}
	//put基本操作
	private String putOperation(String url,String jsonString) {
		String authString = getWAFAuth(nsfocusAPIKey, nsfocusAPIValue, "put");
		String returnString = putmethod(url+authString, jsonString);
		return returnString;
	}
	
	
	//获取可用的公共代理ip
	public JSONArray getPublicIpList() {
		JSONArray publicIpJsonArray = JSONArray.fromObject(nsfocusWafPublicIPList);
		return publicIpJsonArray;
	}
	/*public JSONArray getApiPublicIpList() {
		JSONArray jsonArray = JSONArray.fromObject(nsfocusWafPublicIPList);
		return jsonArray;
	}*/
	
	/**
	 * 获取全部证书信息
	 * @return
	 */
	public String getSSLCert(){
		String getSSLCertString = getOperation(nsfocusWafUrl+REST_URI_V1+"/sslcert");
		return getSSLCertString;
	}
	
	/**
	 * 上传证书
	 * @return
	 */
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
	public String getSite(JSONObject jsonObject) {
		if (jsonObject.get("siteId")==null||jsonObject.getString("siteId").length()<=0) {
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "Site id is null!!");
			return errJsonObject.toString();
		}
		String getSiteString = getSiteOperation(jsonObject.getString("siteId"));
		return getSiteString;
	}
	//站点查询基本操作
	public String getSiteOperation(String siteId) {
		String getSiteOperationString = getOperation(nsfocusWafUrl+REST_URI_V1+"/sites/{"+siteId+"}");
		return getSiteOperationString;
	}
	public String createSite(JSONObject jsonObject) {
		if (jsonObject.get("wafIp")==null||jsonObject.getString("wafIp").length()<=0) {
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "Site ip is null!!!");
			return errJsonObject.toString();
		}
		JSONObject tempJsonObject = new JSONObject();
		//create site
		tempJsonObject.put("ip", jsonObject.getString("wafIp"));
		if (jsonObject.get("name")!=null&&jsonObject.getString("name").length()>0){
			tempJsonObject.put("name", jsonObject.getString("name"));
		}
		if (jsonObject.get("wafPort")!=null&&jsonObject.getString("wafPort").length()>0){
			tempJsonObject.put("port", jsonObject.getString("wafPort"));
		}
		if (jsonObject.get("type")!=null&&(jsonObject.getString("type").length()>0)){
			tempJsonObject.put("type", jsonObject.getString("type"));
			if ("1".equals(jsonObject.getString("type"))&&jsonObject.get("cert")!=null) {
				tempJsonObject.put("cert", jsonObject.getString("cert"));
			}
		}
		JSONObject createSiteJsonObject = new JSONObject();
		createSiteJsonObject.put("0", tempJsonObject);
		System.out.println(">>>createSiteJsonObject="+createSiteJsonObject);
		System.out.println(">>>url="+nsfocusWafUrl+REST_URI_V1+"/sites");
		String createSiteString = postOperation(nsfocusWafUrl+REST_URI_V1+"/sites",createSiteJsonObject.toString());
		System.out.println(createSiteString);
		JSONArray responseArray = JSONArray.fromObject(createSiteString);
		String responseString = responseArray.getString(0);
		return responseString;
	}
	public boolean createSiteOnState(JSONObject jsonObject) {
		JSONObject responseJsonObject = JSONObject.fromObject(createSite(jsonObject));
		if (responseJsonObject.getString("status").equals("success")) {
			return true;
		}else {
			return false;
		}

	}
	public String alterSite(JSONObject jsonObject) {
		if (jsonObject.get("siteId")==null||jsonObject.getString("siteId").length()<=0) {
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "Site id is null!!");
		}
		//根据传入对象组装符合设备需求格式的临时json对象
		JSONObject tempJsonObject = new JSONObject();
		//站点名称
		if(jsonObject.get("name")!=null && jsonObject.getString("name").length()>0)
			tempJsonObject.put("name", jsonObject.getString("name"));
		//站点ip
		if(jsonObject.get("ip")!=null && jsonObject.getString("ip").length()>0)
			tempJsonObject.put("ip", jsonObject.getString("ip"));
		//站点的端口
		if(jsonObject.get("port")!=null && jsonObject.getString("port").length()>0)
			tempJsonObject.put("port", jsonObject.getString("port"));
		//站点的类型，0为http，1为https
		if (jsonObject.get("type")!=null && jsonObject.getString("type").length()>0){
			tempJsonObject.put("type", jsonObject.getString("type"));
			if ("1".equals(jsonObject.getString("type")) && jsonObject.get("cert")!=null) {
				tempJsonObject.put("cert", jsonObject.getString("cert"));
			}
		}
		JSONObject alterSiteJsonObject = new JSONObject();
		alterSiteJsonObject.put(jsonObject.getString("siteId"), tempJsonObject);
		String alterSiteString = putOperation(nsfocusWafUrl+REST_URI_V1+"/sites", alterSiteJsonObject.toString());
		return alterSiteString;
	}
	public String deleteSite(JSONObject jsonObject) {
		if (jsonObject.get("siteId")==null||jsonObject.getString("siteId").length()<=0) {
			JSONObject tempJsonObject = new JSONObject();
			tempJsonObject.put("result", "siteId is Null");
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("null", errJsonObject);
			return errJsonObject.toString();
		}
		String siteId = jsonObject.getString("siteId");
		String delSiteString = delOperationWithQuery(nsfocusWafUrl+REST_URI_V1+"/sites?siteid="+siteId);
		return delSiteString;
	}
	
	
	public String getVirtSite(JSONObject jsonObject) {
		if (jsonObject.get("vSiteId")==null||jsonObject.getString("vSiteId").length()<=0) {
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "VirtualSite is null!!");
		}
		String virtSiteId = jsonObject.getString("vSiteId");
		String getVirtSiteString = getOperationWithQuery(nsfocusWafUrl+REST_URI_V1+"/sites/protect/virts?virtid="+virtSiteId);
		return getVirtSiteString;
	}
	
	public String createVirtSite(JSONObject jsonObject) {
		//判断虚拟站点所在站点组是否为空
		if(jsonObject.get("parent")==null || jsonObject.getString("parent").length()<=0 ){
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "Parent is null!!!");
			return errJsonObject.toString();
		}
		
		//根据传入的json对象组装符合设备需求格式的临时json对象
		JSONObject tempJsonObject = new JSONObject();
		
		//所在站点组
		tempJsonObject.put("parent", jsonObject.getString("parent"));
		
		//虚拟站点名称
		if(jsonObject.get("name")!=null && jsonObject.getString("name").length()>0)
			tempJsonObject.put("name", jsonObject.getString("name"));
		//虚拟站点域名
		if(jsonObject.get("domain")!=null && jsonObject.getString("domain").length()>0)
			tempJsonObject.put("domain", jsonObject.getString("domain"));
		//包含路径，默认为*
		if(jsonObject.get("include")!=null && jsonObject.getString("include").length()>0)
			tempJsonObject.put("include", jsonObject.getString("include"));
		//不包含的路径，默认为空
		if(jsonObject.get("exclude")!=null && jsonObject.getString("exclude").length()>0)
			tempJsonObject.put("exclude", jsonObject.getString("exclude"));
		//真实服务器信息(ip、端口)，一组有多个
		if (jsonObject.get("server")!=null && jsonObject.getString("server").length()>0) {
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
		
		//利用临时json对象创建虚拟站点json对象
		JSONObject createVSiteObject = new JSONObject();
		//加入虚拟站点json对象外层包装，id位置为0
		createVSiteObject.put("0", tempJsonObject);
		//发送创建虚拟站点请求并接收返回的内容
		String createVSiteString = postOperation(nsfocusWafUrl+REST_URI_V1+"/sites/protect/virts",createVSiteObject.toString());
		JSONArray responseArray = JSONArray.fromObject(createVSiteString);
		//取id位为0的返回内容，一般都为0
		String responseString = responseArray.getString(0);
		return responseString;
	}
	public String alterVirtSite(JSONObject jsonObject) {
		if(jsonObject.get("vSiteId")==null||jsonObject.getString("vSiteId").length()<=0
		||null==jsonObject.get("parent")||jsonObject.getString("parent").length()<=0){
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "VirtualSite or parent is null!!!");
			return errJsonObject.toString();
		}
		JSONObject tempJsonObject = new JSONObject();
		if(jsonObject.get("domain")!=null&&jsonObject.getString("domain").length()>0)
			tempJsonObject.put("domain", jsonObject.getString("domain"));
		if(jsonObject.get("name")!=null&&jsonObject.getString("name").length()>0)
			tempJsonObject.put("name", jsonObject.getString("name"));
		tempJsonObject.put("parent", jsonObject.getString("parent"));
		JSONObject alterVirtSiteJsonObject = new JSONObject();
		alterVirtSiteJsonObject.put(jsonObject.getString("vSiteId"), tempJsonObject);
		String alterString = putOperation(nsfocusWafUrl+REST_URI_V1+"/sites/protect/virts", alterVirtSiteJsonObject.toString());
		return alterString;
	}
	/**
	 * 删除指定的虚拟站点信息
	 * @param jsonObject
	 * @return
	 */
	public String deleteVirtSite(JSONObject jsonObject) {
		if(jsonObject.get("vSiteId")==null||jsonObject.getString("vSiteId").length()<=0){
			JSONObject errJsonObject = new JSONObject();
			errJsonObject.put("status", "failed");
			errJsonObject.put("reason", "Virtual site is null!!!");
			return errJsonObject.toString();
		}
		String vSiteId = jsonObject.getString("vSiteId");
		String deleteString = delOperationWithQuery(nsfocusWafUrl+REST_URI_V1+"/sites/protect/virts?virtid="+vSiteId); 
		return deleteString;
	}
	/**
	 * 获取工作组ip
	 * @param jsonObject
	 * @return
	 */
	public String getAllIpFromEth(JSONObject jsonObject) {
		return getOperation(nsfocusWafUrl+REST_URI_V1+"/interfaces/all/ip");
	}	
	
	public static void main(String[] args) {
		NsfocusWAFOperation operation = new NsfocusWAFOperation("https://219.141.189.189:58442", "vmwaf", "E34A-44A6-E12B-E1C9", "admin", "nsfocus");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("wafIp", "101.201.222.199");
		System.out.println(operation.createSite(jsonObject));
	}
}









