package com.cn.ctbri.common;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import sun.misc.BASE64Decoder;
import net.sf.json.JSONObject;

import com.cn.ctbri.listener.ContextClient;

public class SysWorker {
	/**
	 * wafAPI服务器根路径
	 */
	
	private static String SERVER_SYS_ROOT;
	//private static String resourceId;
	//金山
	private static String SYS_jinshan_getOrderIndex;
	private static String SYS_jinshan_getUninstallInfo;
	private static String SYS_jinshan_getHostCount;
	private static String SYS_jinshan_getOauthUrl;
	
	
	 //云眼
	private static String SYS_yunyan_gettoken;
	private static String SYS_yunyan_destroyToken;
	private static String SYS_yunyan_getloginurl;
	
	//绿盟
	private static String SYS_jiguang_getauthurl;
	private static String SYS_jiguang_reneworder;
	
	static{
		try {
			Properties p = new Properties();
			p.load(SysWorker.class.getClassLoader().getResourceAsStream("northAPI.properties"));
			SERVER_SYS_ROOT = p.getProperty("SERVER_SYS_ROOT");
			//resourceId = p.getProperty("resourceId");
			SYS_jinshan_getOrderIndex = p.getProperty("SYS_jinshan_getOrderIndex");
			SYS_jinshan_getUninstallInfo = p.getProperty("SYS_jinshan_getUninstallInfo");
			SYS_jinshan_getHostCount = p.getProperty("SYS_jinshan_getHostCount");
			SYS_jinshan_getOauthUrl = p.getProperty("SYS_jinshan_getOauthUrl");
			
			SYS_yunyan_gettoken = p.getProperty("SYS_yunyan_gettoken");
			SYS_yunyan_destroyToken = p.getProperty("SYS_yunyan_destroyToken");
			SYS_yunyan_getloginurl = p.getProperty("SYS_yunyan_getloginurl");	
			
			SYS_jiguang_getauthurl = p.getProperty("SYS_jiguang_getauthurl");
			SYS_jiguang_reneworder = p.getProperty("SYS_jiguang_reneworder");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public SysWorker() {
	}
	
	final static WebTarget southTarget = ContextClient.mainSouthTarget;
	/**
	 * 功能描述：获取订单状态
	 * @param companyId 企业ID（唯一标识）
	 * 		  name   企业名称，
	 *        tCount      订购的终端点数
	 * @time 2017-3-15
	 * 返回成功：{"status":"success"}
		失败：{"message":"Enterprises already have this order","status":"failed"}

	 */
	private static String postMethod(String url, String json) {
		System.out.println(url);
		Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
		WebTarget webTarget = client.target(SERVER_SYS_ROOT);
		WebTarget target = southTarget.path(url);
		Response response = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
		String str = (String)response.readEntity(String.class);
        response.close();
        return str;
	}
	
	public static String getJinshanCreateOrder(String companyId, String companyName, String tCount){
		JSONObject json =new JSONObject();
		json.put("companyId", companyId);
		if (companyName.equals("")) {
			json.put("name", "nocompanyname");
		}
		else {
			json.put("name", companyName);
		}
		json.put("tCount", tCount);
		
		String url = SERVER_SYS_ROOT + SYS_jinshan_getOrderIndex;
		//创建jersery客户端配置对象
		String text = postMethod(SYS_jinshan_getOrderIndex, json.toString());
		//String textEntity = response.getEntity(String.class);
//      String status = JSONObject.fromObject(textEntity).getString("status");
        System.out.println(text);
        String status =JSONObject.fromObject(text).getString("status");
        return status;
	}
	/**
	 * 功能描述：获取卸载密码接口
	 * @param companyId 企业ID（唯一标识）
	 * 		 
	 * @time 2017-3-15
	 * 返回成功：{"status":"success","uninstallPassword":"WGxMRHJ0"}
		失败：{"message":"Company does not exist","status":"failed"}

	 */
	public static String getJinshanUninstallInfo(String companyId){
		JSONObject json =new JSONObject();
		json.put("companyId", companyId);

		
		String url = SERVER_SYS_ROOT + SYS_jinshan_getUninstallInfo;
		//创建jersery客户端配置对象
		String textEntity = postMethod(SYS_jinshan_getUninstallInfo, json.toString());
//      String status = JSONObject.fromObject(textEntity).getString("status");
        
        String status = JSONObject.fromObject(textEntity).getString("status");
        if (status.equals("success")) {
			
        	String struninstallPassword = JSONObject.fromObject(textEntity).getString("uninstallPassword");   	
        	return struninstallPassword;
		}
        else {
			return "failed";
		}

	}
	
	/**
	 * 功能描述：获取安装点数接口
	 * @param companyId 企业ID（唯一标识）
	 * 		 
	 * @time 2017-3-15
	 * 返回成功：{"status":"success","allCount":"64","installCount":"0","surplusCount":"64"}
		失败：{"message":"Company does not exist","status":"failed"}

	 */
	public static String getJinshanhostcount(String companyId){
		JSONObject json =new JSONObject();
		json.put("companyId", companyId);

		
		String url = SYS_jinshan_getHostCount;
		//创建jersery客户端配置对象
		String textEntity = postMethod(url, json.toString());
//      String status = JSONObject.fromObject(textEntity).getString("status");
        System.out.println(textEntity);
        
        
        return textEntity;
	}
	
	/**
	 * 功能描述：获取登陆授权接口 
	 * @param companyId 企业ID（唯一标识）
	 * 		 
	 * @time 2017-3-15
	 * 返回结果：
{"url":"aHR0cDovLzYwLjIwNS4xNjkuMjIzL09hdXRoL2luZGV4P2NvbXBhbnlfaWQ9MTIzNDU2JnRpbWVzdGFtcD0xNDg5NDU3OTQ2MjI2JnNlcmNldD1ERkIwMDM2NDY3M0E3RjVDODZGRTQ0QzVDNkEwRDdFOA==","status":"success"}
url解码后：
	http://60.205.169.223/Oauth/index?company_id=123456&timestamp=1489457946226&sercet=DFB00364673A7F5C86FE44C5C6A0D7E8


	 */
	public static String getJinshanoauthurl(String companyId){
		JSONObject json =new JSONObject();
		json.put("companyId", companyId);

		
		String url = SYS_jinshan_getOauthUrl;
		//创建jersery客户端配置对象
		String textEntity = postMethod(url, json.toString());
        String status = JSONObject.fromObject(textEntity).getString("status");
        if (status.equals("success")) {
			
        	String urlBase64 = JSONObject.fromObject(textEntity).getString("url");
        	String OrderUrl = getFromBase64(urlBase64);    	
        	return OrderUrl;
		}
        else {
			return "failed";
		}
       // System.out.println(textEntity);
       // return textEntity;
	}
	
	/**
	 * 功能描述：获取yunyan  token
	 * @param userId 用户ID（唯一标识）
	 * 		 
	 * @time 2017-3-28
	 * 返回成功："status": "success",
  	"token": "51f7fdf203668e843c895a4241f9a4ec",
  	"randomChar": "gK9XNUGANOnN23e8QjdLPFs8merlsdxf"


	 */
	public static String getYunyanToken(String userId){
		JSONObject json =new JSONObject();
		json.put("userId", userId);

		
		String url = SYS_yunyan_gettoken ;
		//创建jersery客户端配置对象
		String textEntity = postMethod(url, json.toString());
//      String status = JSONObject.fromObject(textEntity).getString("status");
        
        String status = JSONObject.fromObject(textEntity).getString("status");
        if (status.equals("success")) {
			
        	String strtoken = JSONObject.fromObject(textEntity).getString("token");   	
        	return strtoken;
		}
        else {
			return "failed";
		}

	}
	
	
	/**
	 * 功能描述：注销yunyan  token
	 * @param userId 用户ID（唯一标识）
	 * 		 
	 * @time 2017-3-28
	 * 返回成功"status":"success"


	 */
	public static String destroyYunyanToken(String userId){
		JSONObject json =new JSONObject();
		json.put("userId", userId);

		
		String url = SYS_yunyan_destroyToken ;
		//创建jersery客户端配置对象
		String textEntity = postMethod(url, json.toString());
//      String status = JSONObject.fromObject(textEntity).getString("status");
        
        String status = JSONObject.fromObject(textEntity).getString("status");
        if (status.equals("success")) {
			
        	return "success";
		}
        else {
			return "failed";
		}

	}
	
	/**
	 * 功能描述：获取yunyan  登录url
	 * @param token 
	 * 		 
	 * @time 2017-3-28
	 * 返回成功{"url":"http://180.153.47.196:8088/stewardweb/securityLogin.do?token=4e1a3c6fd528d4e9186ee4ef6c1edd13","status":"success"}



	 */
	public static String getYunyanloginURL(String token){
		JSONObject json =new JSONObject();
		json.put("token", token);

		
		String url = SYS_yunyan_getloginurl ;
		//创建jersery客户端配置对象
		String textEntity = postMethod(url, json.toString());
//      String status = JSONObject.fromObject(textEntity).getString("status");
        
        String status = JSONObject.fromObject(textEntity).getString("status");
        if (status.equals("success")) {
        	String urlRes = JSONObject.fromObject(textEntity).getString("url");   	
        	return urlRes;

		}
        else {
			return "failed";
		}

	}
	
	/**
	 * 功能描述：获取jiguang  登录url
	 * @param 
	 * 		 
	 * @time 2017-4-18
	 * 返回成功{
  "status": "success","url":""}



	 */
	public static String getjiguangURL(String userId ,String orderId ,String mobile ,String customerName ){
		JSONObject json =new JSONObject();
		json.put("userId", userId);
		json.put("orderId", orderId);
		json.put("mobile", mobile);
		json.put("customerName",customerName);

		
		String url = SERVER_SYS_ROOT +SYS_jiguang_getauthurl ;
		//创建jersery客户端配置对象
		String textEntity = postMethod(url, json.toString());

//      String status = JSONObject.fromObject(textEntity).getString("status");
        System.out.println("***********************yunyantoken"+textEntity);
        String status = JSONObject.fromObject(textEntity).getString("status");
        if (status.equals("success")) {
        	String urlRes = "failed";
        	urlRes = JSONObject.fromObject(textEntity).getString("url");   	
        	return urlRes;

		}
        else {
			return "failed";
		}

	}
	
	/**
	 * 功能描述：获取jiguang  登录url
	 * @param 
	 * 		 
	 * @time 2017-4-18
	 * 返回成功{
  "status": "success","url":""}



	 */
	public static String getjiguanginstanceID(String userId ,String orderId ,String mobile ,String customerName ){
		JSONObject json =new JSONObject();
		json.put("userId", userId);
		json.put("orderId", orderId);
		json.put("mobile", mobile);
		json.put("customerName",customerName);

		
		String url = SERVER_SYS_ROOT +SYS_jiguang_getauthurl ;
		//创建jersery客户端配置对象
		String textEntity = postMethod(url, json.toString());

        String status = JSONObject.fromObject(textEntity).getString("status");
        if (status.equals("success")) {
        	String resId = "failed";
        	resId = JSONObject.fromObject(textEntity).getString("instanceId");   	
        	return resId;

		}
        else {
			return "failed";
		}

	}
	/**
	 * 功能描述：获取jiguang续费
	 * @param instanceId  实例id（唯一标识，由接口9.1返回）
	 * 		 
	 * @time 2017-4-18
	 * 返回成功


	 */
	public static String getjiguangreneworder(String instanceId){
		JSONObject json =new JSONObject();
		json.put("instanceId", instanceId);

		
		String url = SERVER_SYS_ROOT +SYS_jiguang_reneworder ;
		//创建jersery客户端配置对象
		String textEntity = postMethod(url, json.toString());

//      String status = JSONObject.fromObject(textEntity).getString("status");
        System.out.println("***********************yunyanURL"+textEntity);
        String status = JSONObject.fromObject(textEntity).getString("status");
        if (status.equals("success")) {
        //	String urlRes = JSONObject.fromObject(textEntity).getString("url");   	
        //	return urlRes;
        	return "success";

		}
        else {
			return "failed";
		}

	}
	

	

	
	/**
	 * 功能描述： Base64解码
	 *		 @time 2017-3-15
	 */
	
	private static String getFromBase64(String s){
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	/**
	 * 功能描述： 获取安全套接层上下文对象
	 *		 @time 2015-12-31
	 */
	private static SSLContext getSSLContext() {
		//创建认证管理器
    	TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
    	    public java.security.cert.X509Certificate[] getAcceptedIssuers(){return null;}
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}
    	}};
    	try {
    		//创建安全传输层对象
    	    SSLContext sc = SSLContext.getInstance("SSL");
    	    //初始化参数
    	    sc.init(null, trustAllCerts, new SecureRandom());
    	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    	    return sc;
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	return null;
    }
	

	/**
	 * 功能描述：空字符串转化方法
	 * 参数描述:String str 要转化的字符
	 *		 @time 2015-12-31
	 */
	private static String nullFilter(String str) {
    	return str==null ? "" : str;
    }




}
