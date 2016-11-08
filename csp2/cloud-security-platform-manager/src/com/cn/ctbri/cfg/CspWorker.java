package com.cn.ctbri.cfg;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cn.ctbri.entity.ApiPrice;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.service.IServService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

/**
 * 创 建 人  ：  zhang_shaohua
 * 创建日期：  2016-06-20
 * 描        述：  前端Portal接口本地Worker
 * 版        本：  1.0
 */
public class CspWorker {
	
	private static String SERVER_WEB_ROOT;
	
	
	private static String Advertisement_Add;		//添加广告
	private static String Advertisement_Delete;		//删除广告
	private static String Advertisement_Sort;		//广告排序
	
	private static String Service_Add;				//添加服务
	private static String Service_Update;			//修改服务
	private static String Service_Delete;			//删除服务
	private static String ServiceDetail_vindicate;	//服务详情维护
	
	private static String Service_Price_Vindicate;
	private static String serviceAPI_Price_Vindicate;
	
	
	static {
		try {
			Properties p = new Properties();
			p.load(CspWorker.class.getClassLoader().getResourceAsStream("InternalAPI.properties"));
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			Advertisement_Add = p.getProperty("Advertisement_Add");
			Advertisement_Delete = p.getProperty("Advertisement_Delete");
			Advertisement_Sort = p.getProperty("Advertisement_Sort");
			
			Service_Add = p.getProperty("Service_Add");
			Service_Update = p.getProperty("Service_Update");
			Service_Delete = p.getProperty("Service_Delete");
			ServiceDetail_vindicate = p.getProperty("ServiceDetail_vindicate");
			
			Service_Price_Vindicate = p.getProperty("Service_Price_Vindicate");
			serviceAPI_Price_Vindicate = p.getProperty("serviceAPI_Price_Vindicate");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public CspWorker() {}
	
	/**
	 * 功能描述：添加广告
	 * 参数描述： adName 广告名称
	 * 			adImage 广告图片
	 * 			adStartDate 广告有效期起始时间
	 * 			adEndDate 广告有效期结束时间
	 * 			adCreateDate 广告创建时间
	 *		 @time 2016-06-20
	 */
	public static String addAdvertisement(String adName,
			String adImage, int adType, int orderIndex, 
			String adStartDate, String adEndDate,
			String adCreateDate) {
		JSONObject json = new JSONObject();
//		json.put("AdId", adId);
		json.put("AdName", adName);
		json.put("AdImage", adImage);
		json.put("AdType", adType);
		json.put("AdOrder", orderIndex);
		json.put("AdStartDate", adStartDate);
		json.put("AdEndDate", adEndDate);
		json.put("AdCreateDate", adCreateDate);
		//创建任务发送路径
		String url = SERVER_WEB_ROOT + Advertisement_Add;
		//创建jersery客户端配置对象
		ClientConfig config = new DefaultClientConfig();
		//检查安全传输协议设置
	    buildConfig(url,config);
	  //创建Jersery客户端对象
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource(url);
      //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON).post(String.class, json.toString());
        JSONObject obj = JSONObject.fromObject(response);
        String stateCode = obj.getString("code");
		if(stateCode.equals("200")){
			String adId = obj.getString("adId");
			return adId;
		}else{
			return "";
		}
	}
	
	/**
	 * 功能描述：删除广告
	 * 参数描述： adId 广告Id
	 *		 @time 2016-06-20
	 */
	public static String deleteAdvertisement(String adId){
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + Advertisement_Delete + adId;
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class); 
        JSONObject obj = JSONObject.fromObject(response);
        String stateCode = obj.getString("code");
        return stateCode;
	}
	
	/**
	 * 功能描述：广告排序(广告上移或下移)
	 *		 @time 2016-09-5
	 */
	public static String sortAdvertisement(String adId1,String adOrder1,String adId2,String adOrder2){
		JSONObject json = new JSONObject();
		json.put("AdId1", adId1);
		json.put("AdOrder1", adOrder1);
		json.put("AdId2", adId2);
		json.put("AdOrder2", adOrder2);
		
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + Advertisement_Sort;
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, json.toString()); 
        JSONObject obj = JSONObject.fromObject(response);
        String stateCode = obj.getString("code");
        return stateCode;
	}
	
	/**
	 * 功能描述：添加服务
	 * 参数描述： 
	 */
	public static String addService(String name,String parent, 
			int type, String remarks, String homeIcon, 
			String categoryIcon, String detailIcon) {
		JSONObject json = new JSONObject();
		json.put("parent", parent);      //一级分类
		json.put("type", type);	//服务类型
		json.put("name", name);		//服务名称
		json.put("remarks", remarks);	//服务描述
		json.put("homeIcon", homeIcon);	//首页服务图标
		json.put("categoryIcon", categoryIcon);	//二级服务图标
		json.put("detailIcon", detailIcon);	//详情页服务图标
		
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + Service_Add;
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, json.toString()); 
        JSONObject obj = JSONObject.fromObject(response);
        String stateCode = obj.getString("code");
        if(stateCode.equals("200")){
			String adId = obj.getString("serviceId");
			return adId;
		}else{
			return "";
		}
	}
	
	/**
	 * 功能描述：编辑服务
	 * 参数描述： 
	 */
	public static String updateService(String serviceId, String name, 
			String parent, int type, String remarks, String homeIcon, 
			String categoryIcon, String detailIcon) {
		JSONObject json = new JSONObject();
		json.put("serviceId", serviceId);
		json.put("parent", parent);      //一级分类
		json.put("type", type);	//服务类型
		json.put("name", name);		//服务名称
		json.put("remarks", remarks);	//服务描述
		json.put("homeIcon", homeIcon);	//首页服务图标
		json.put("categoryIcon", categoryIcon);	//二级服务图标
		json.put("detailIcon", detailIcon);	//详情页服务图标
		
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + Service_Update;
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, json.toString()); 
        JSONObject obj = JSONObject.fromObject(response);
        String stateCode = obj.getString("code");
        return stateCode;
	}
	
	/**
	 * 功能描述：删除服务
	 * 参数描述： serviceId 服务Id
	 *		 @time 2016-06-20
	 */
	public static String deleteService(String serviceId, String parent){
		JSONObject json = new JSONObject();
		json.put("serviceId", serviceId);
		json.put("parent", parent);      //一级分类
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + Service_Delete;
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, json.toString()); 
        JSONObject obj = JSONObject.fromObject(response);
        String stateCode = obj.getString("code");
        return stateCode;
	}
	
	/**
	 * 功能描述：服务详情维护
	 * 参数描述： 
	 */
	public static String saveServDetails(int serviceId, int parent, String priceTitle, String typeTitle, 
			int servType, String servRatesTitle, String scanType, String servIcon) {
		JSONObject json = new JSONObject();
		json.put("serviceId", serviceId);
		json.put("parent", parent);//一级分类
		json.put("priceTitle", priceTitle);			//价格标题
		json.put("typeTitle", typeTitle);			//选类型标题
		json.put("servType", servType);				//选类型(0:单次和长期,1:长期,2:单次)
		json.put("servRatesTitle", servRatesTitle);	//服务频率标题
		json.put("scanType", scanType);				//服务频率
		json.put("servIcon", servIcon);				//服务详情图片
		
		//创建任务发送路径
    	String url = SERVER_WEB_ROOT + ServiceDetail_vindicate;
		//创建配置
		ClientConfig config = new DefaultClientConfig();
		//绑定配置
    	buildConfig(url,config);
    	//创建客户端
        Client client = Client.create(config);
        WebResource service = client.resource(url);
        //获取响应结果
        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, json.toString()); 
        JSONObject obj = JSONObject.fromObject(response);
        String stateCode = obj.getString("code");
        return stateCode;
	}
	
	//服务价格
	public static String updateServicePrice(int serverid, List<Price> priceList) {
		JSONObject json = new JSONObject();
		//JSONArray jsonArray = new JSONArray();
		try {
			//根据serviceid查询价格列表
			//List<Price> priceList = servService.findPriceByServiceId(serverid);
			JSONArray jsonArray = null;
			if (priceList != null && priceList.size()!=0){
				jsonArray = new JSONArray().fromObject(priceList);
				
			}
			json.put("PriceStr", jsonArray);
			json.put("serviceId", serverid);
			
			//创建任务发送路径
	    	String url = SERVER_WEB_ROOT + Service_Price_Vindicate;
			//创建配置
			ClientConfig config = new DefaultClientConfig();
			//绑定配置
	    	buildConfig(url,config);
	    	//创建客户端
	        Client client = Client.create(config);
	        WebResource service = client.resource(url);
	        //获取响应结果
	        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, json.toString()); 
	        JSONObject obj = JSONObject.fromObject(response);
	        String stateCode = obj.getString("code");
	        return stateCode;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
			json.put("message", "更新服务价格失败");
		}
    	    	
        return json.toString();
    }
	
	//服务价格
	public static String updateServiceAPIPrice(int serverid, List<ApiPrice> priceList) {
		JSONObject json = new JSONObject();
		//JSONArray jsonArray = new JSONArray();
		try {
			//根据serviceid查询价格列表
//			List<ApiPrice> priceList = servService.findApiPriceByServiceId(serverid);
			JSONArray jsonArray = null;
			if (priceList != null && priceList.size()!=0){
				jsonArray = new JSONArray().fromObject(priceList);
				
			}
			json.put("PriceStr", jsonArray);
			json.put("serviceId", serverid);
			
			//创建任务发送路径
	    	String url = SERVER_WEB_ROOT + serviceAPI_Price_Vindicate;
			//创建配置
			ClientConfig config = new DefaultClientConfig();
			//绑定配置
	    	buildConfig(url,config);
	    	//创建客户端
	        Client client = Client.create(config);
	        WebResource service = client.resource(url);
	        //获取响应结果
	        String response = service.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, json.toString()); 
	        JSONObject obj = JSONObject.fromObject(response);
	        String stateCode = obj.getString("code");
	        return stateCode;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
			json.put("message", "更新服务价格失败");
		}
    	    	
        return json.toString();
    }
	
	/**
	 * 功能描述：安全通信配置设置
	 * 参数描述:String url 路径,ClientConfig config 配置对象
	 *		 @time 2015-10-16
	 */
	private static void buildConfig(String url,ClientConfig config) {
		if(url.startsWith("http")) {
        	SSLContext ctx = getSSLContext();
        	config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
        		     new HostnameVerifier() {
        		         public boolean verify( String s, SSLSession sslSession ) {
        		             return true;
        		         }
        		     }, ctx
        		 ));
        }
	}
	
	/**
	 * 功能描述： 获取安全套接层上下文对象
	 *		 @time 2015-01-05
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

	public static String getServerWebRoot() {
		return SERVER_WEB_ROOT;
	}

}
