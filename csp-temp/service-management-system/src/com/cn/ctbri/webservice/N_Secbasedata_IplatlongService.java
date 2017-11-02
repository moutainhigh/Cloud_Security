package com.cn.ctbri.webservice;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.common.ManagerWorker;
import com.cn.ctbri.entity.API;
import com.cn.ctbri.entity.APINum;
import com.cn.ctbri.entity.OrderAPI;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.listener.ContextClient;
import com.cn.ctbri.listener.CspContextListener;
import com.cn.ctbri.service.IOrderAPIService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.Common;

/**
 * IP地址与经纬度数据
 * @author LI AO
 *
 */

@Component
@Path("openapi/secbasedata/iplatlong")
public class N_Secbasedata_IplatlongService {

	@Autowired
    private IUserService userService;
	@Autowired
    private IOrderAPIService orderAPIService;
	//获取单IP经纬度信息
	@POST	//插入请求    相当于数据库的插入数据操作
    @Path("/getlatlongbyip/{token}")
	@Produces(MediaType.APPLICATION_JSON) //设置返回值为Json类型
	public String getLatlongByIP(@PathParam("token") String token,String dataJson) {
		JSONObject json = new JSONObject();
		JSONObject jsonObj = new JSONObject().fromObject(dataJson);
		String ip = jsonObj.getString("ip");
		//String ip1 = (String) jsonObj.get("ip");
		System.out.println("aaaaaaaaaaaaaa   +   getLatlongByIP");
		if( ip != null && !ip.trim().isEmpty()){
			try {
				//通过token查询user
				System.out.println("   token   "+token);
				User user = getUserByToken(token);
				System.out.println("user"+user);
				List<OrderAPI> userableList = getUserableList(user);
				List<OrderAPI> oAPIList = this.getOAPIList(user);		
				if(token!=null && token!="" && user!=null){
					
					if(havePermission(user, oAPIList)){
						if(!serviceExpired(user,oAPIList)){
							if(!usedUp(user, userableList)){
								String currentMethodName = getMethodName();
								JSONObject paramJson = new JSONObject();
								paramJson.put("ip", ip);
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,paramJson);
								southAPIWrapper1(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 6, 2);
								}
								
							}else
								Common.usedUpCodeCodeAndMessage(json);
						}else
							Common.expiredCodeAndMessage(json);
					}else
						Common.permissionDeniedCodeAndMessage(json);
				}else{
					Common.tokenInvalidCodeAndMessage(json);
				}
			}catch (Exception e) {
					e.printStackTrace();
					Common.failCodeAndMessage(json);
			}
		}else
			Common.paramErrCodeAndMessage(json);
		return json.toString();
	}
	
	//获取多IP经纬度信息
	@POST
    @Path("/getlatlongbyiplist/{token}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getLatLongByIPList(@PathParam("token") String token,String dataJson) {
		JSONObject json = new JSONObject();
		JSONObject jsonObj = new JSONObject().fromObject(dataJson);
		String ipList = jsonObj.getString("ipList");
		
		if( ipList != null && !ipList.trim().isEmpty()){
			try {
				//通过token查询user
				User user = getUserByToken(token);
				List<OrderAPI> userableList = getUserableList(user);
				List<OrderAPI> oAPIList = this.getOAPIList(user);		
				if(token!=null && token!="" && user!=null){
					
					if(havePermission(user, oAPIList)){
						if(!serviceExpired(user,oAPIList)){
							if(!usedUp(user, userableList)){
								String currentMethodName = getMethodName();
								JSONObject paramJson = new JSONObject();
								paramJson.put("ipList", ipList);
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,paramJson);
								southAPIWrapper2(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 6, 2);
								}
								
							}else
								Common.usedUpCodeCodeAndMessage(json);
						}else
							Common.expiredCodeAndMessage(json);
					}else
						Common.permissionDeniedCodeAndMessage(json);
				}else{
					Common.tokenInvalidCodeAndMessage(json);
				}
			}catch (Exception e) {
					e.printStackTrace();
					Common.failCodeAndMessage(json);
			}
		}else
			Common.paramErrCodeAndMessage(json);
		return json.toString();
	}
	
	//获取国内IP地址经纬度数据总数 
	@GET    //查询请求    相当于数据库的查询数据操作
    @Path("/getcncount/{token}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getIpLatlongCNCount(@PathParam("token") String token) {
		JSONObject json = new JSONObject();
		
		try {
			//通过token查询user
			System.out.println("bbbbbbbbbbbbbb  +  getIPLatLongCNCount()");
			User user = getUserByToken(token);
			List<OrderAPI> userableList = getUserableList(user);
			List<OrderAPI> oAPIList = this.getOAPIList(user);		
			if(token!=null && token!="" && user!=null){
				if(havePermission(user, oAPIList)){
					if(!serviceExpired(user,oAPIList)){
						if(!usedUp(user, userableList)){
							String currentMethodName = getMethodName();
							String southAPIReturn = getSouthAPIByMethod(currentMethodName,null);
							southAPIWrapper1(southAPIReturn,json);
							
							for (int i = 0; i < oAPIList.size(); i++) {
								countAPI(token, oAPIList.get(i).getId(), null, 6, 2);
							}
							
						}else
							Common.usedUpCodeCodeAndMessage(json);
					}else
						Common.expiredCodeAndMessage(json);
				}else
					Common.permissionDeniedCodeAndMessage(json);
			}else{
				Common.tokenInvalidCodeAndMessage(json);
			}
		}catch (Exception e) {
				e.printStackTrace();
				Common.failCodeAndMessage(json);
		} 	
		
		return json.toString();
	}
	
	//获取国内IP地址经纬度数据块    只有这个南向有问题  在调试 
	@POST
    @Path("/getcndatablock/{token}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getCNDataBlock(@PathParam("token") String token, String dataJson) {
		JSONObject json = new JSONObject();
		JSONObject jsonObj = new JSONObject().fromObject(dataJson);
		String begIndex = jsonObj.getString("begIndex");
		String endIndex = jsonObj.getString("endIndex");
		if( isValidIndex(begIndex,endIndex)){
			try {
				//通过token查询user
				User user = getUserByToken(token);
				List<OrderAPI> userableList = getUserableList(user);
				List<OrderAPI> oAPIList = this.getOAPIList(user);		
				if(token!=null && token!="" && user!=null){
					
					if(havePermission(user, oAPIList)){
						if(!serviceExpired(user,oAPIList)){
							if(!usedUp(user, userableList)){
								String currentMethodName = getMethodName();
								JSONObject paramJson = new JSONObject();
								paramJson.put("begIndex", begIndex);
								paramJson.put("endIndex", endIndex);
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,paramJson);
								southAPIWrapper2(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 6, 2);
								}
								
							}else
								Common.usedUpCodeCodeAndMessage(json);
						}else
							Common.expiredCodeAndMessage(json);
					}else
						Common.permissionDeniedCodeAndMessage(json);
				}else{
					Common.tokenInvalidCodeAndMessage(json);
				}
			}catch (Exception e) {
					e.printStackTrace();
					Common.failCodeAndMessage(json);
			} 	
		}else
			Common.paramErrCodeAndMessage(json);
		return json.toString();
	}
	
	//获取全球IP地址经纬度数据总数
	@GET
    @Path("/gettotalcount/{token}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getIPLatLongTotalCount(@PathParam("token") String token) {
		JSONObject json = new JSONObject();
		
		try {
			//通过token查询user
			System.out.println("bbbbbbbbbbbbbb  +  getIPLatLongTotalCount()");
			User user = getUserByToken(token);
			List<OrderAPI> userableList = getUserableList(user);
			List<OrderAPI> oAPIList = this.getOAPIList(user);		
			if(token!=null && token!="" && user!=null){
				if(havePermission(user, oAPIList)){
					if(!serviceExpired(user,oAPIList)){
						if(!usedUp(user, userableList)){
							String currentMethodName = getMethodName();
							String southAPIReturn = getSouthAPIByMethod(currentMethodName,null);
							southAPIWrapper3(southAPIReturn,json);
							
							for (int i = 0; i < oAPIList.size(); i++) {
								countAPI(token, oAPIList.get(i).getId(), null, 6, 2);
							}
							
						}else
							Common.usedUpCodeCodeAndMessage(json);
					}else
						Common.expiredCodeAndMessage(json);
				}else
					Common.permissionDeniedCodeAndMessage(json);
			}else{
				Common.tokenInvalidCodeAndMessage(json);
			}
		}catch (Exception e) {
				e.printStackTrace();
				Common.failCodeAndMessage(json);
		} 	
		
		return json.toString();
	}
	
	//获取全球IP地址经纬度数据块
	@POST
    @Path("/getdatablock/{token}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getIPLatLongDataBlock(@PathParam("token") String token, String dataJson) {
		JSONObject json = new JSONObject();
		JSONObject jsonObj = new JSONObject().fromObject(dataJson);
		String begIndex = jsonObj.getString("begIndex");
		String endIndex = jsonObj.getString("endIndex");
		if( isValidIndex(begIndex,endIndex)){
			try {
				//通过token查询user
				User user = getUserByToken(token);
				List<OrderAPI> userableList = getUserableList(user);
				List<OrderAPI> oAPIList = this.getOAPIList(user);		
				if(token!=null && token!="" && user!=null){
					
					if(havePermission(user, oAPIList)){
						if(!serviceExpired(user,oAPIList)){
							if(!usedUp(user, userableList)){
								String currentMethodName = getMethodName();
								JSONObject paramJson = new JSONObject();
								paramJson.put("begIndex", begIndex);
								paramJson.put("endIndex", endIndex);
								System.out.println("-----------------------------------");
								System.out.println("currentMethodName"+currentMethodName);
								System.out.println("-----------------------------------");
								
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,paramJson);
								southAPIWrapper3(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 6, 2);
								}
								
							}else
								Common.usedUpCodeCodeAndMessage(json);
						}else
							Common.expiredCodeAndMessage(json);
					}else
						Common.permissionDeniedCodeAndMessage(json);
				}else{
					Common.tokenInvalidCodeAndMessage(json);
				}
			}catch (Exception e) {
					e.printStackTrace();
					Common.failCodeAndMessage(json);
			} 	
		}else
			Common.paramErrCodeAndMessage(json);
		return json.toString();
	}
	
	/**
	 * 方法描述:根据令牌号，查询当前访问用户的信息
	 * @author man
	 * @date 2017年3月16日
	 * @param token
	 * @return
	 */
	private User getUserByToken(String token){
		return userService.findUserByToken(token);
	}
	
	/**
	 * 方法描述:根据当前访问用户的信息，查询其可用服务列表
	 * @author man
	 * @date 2017年3月16日
	 * @param user
	 * @return
	 */
	private  List<OrderAPI> getUserableList(User user){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		System.out.println("----------------"+user.getApikey()+"----------");
		paramMap.put("apiKey", user.getApikey());
        paramMap.put("apiId", 3);
		List<OrderAPI> userableList = orderAPIService.findUseableByParam(paramMap);
		return userableList ;
	}
	
	/**
	 * 方法描述:根据当前访问用户的信息，查询其订单列表
	 * @author man
	 * @date 2017年3月16日
	 * @param user
	 * @return
	 */
	private List<OrderAPI> getOAPIList(User user){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("apiKey", user.getApikey());
        paramMap.put("apiId", 3);
        //查找此项服务的API订单
		List<OrderAPI> oAPIList = orderAPIService.findByParam(paramMap);
		return oAPIList ;
	}
	
	/**
	 * 方法描述:根据用户，及其订单列表，判断其是否有访问权限
	 * @author man
	 * @date 2017年3月16日
	 * @param user
	 * @param oAPIList
	 * @return
	 */
	private boolean havePermission(User user, List<OrderAPI> oAPIList){
		boolean flag = false;
		//如果是企业用户，或者普通用户下有api订单
		if(user.getType()==3 || (user.getType()==2 && oAPIList.size()>0))
			flag = true ;
		return flag;
		
	}
	
	/**
	 * 方法描述:根据用户，查询其购买的服务是否过期
	 * @author man
	 * @date 2017年3月16日
	 * @param user
	 * @param oAPIList
	 * @return
	 */
	private boolean serviceExpired(User user,List<OrderAPI> oAPIList){
		
		boolean flag = false;
		if(user.getType()!=3 ){
			//服务结束时间
			Date end  = oAPIList.get(0).getEnd_date();
			//当前时间
			Date now = new Date();
			if(now.compareTo(end)>0)
				flag = true ;
		}		
		return flag;
	}
	/**
	 * 方法描述:根据用户及可用服务列表，查询其服务次数是否用完
	 * @author man
	 * @date 2017年3月16日
	 * @param user
	 * @param userableList
	 * @return
	 */
	
	private boolean usedUp(User user,List<OrderAPI> userableList){
		boolean flag = false;
		if(user.getType() != 3 && userableList.size() == 0)
			flag = true ;		
		return flag;
	}
	
	/**
	 * 方法描述:返回调用此方法的方法名
	 * @author man
	 * @date 2017年3月16日
	 * @return
	 */
	private String getMethodName() {  
       	return Thread.currentThread().getStackTrace()[2].getMethodName();
    }  
	
	/**
	 * 方法描述:根据当前方法名，获取南向api的访问url，并进行接口访问
	 * @author man
	 * @date 2017年3月16日
	 * @param methodName 实现服务能力api的方法名
	 * @param json 传给南向api的参数
	 * @return
	 */
	private String getSouthAPIByMethod(String methodName,JSONObject json){
		
		Response  response = null;
		String str = "";
        WebTarget target = ContextClient.southAPImainTarget.path(CspContextListener.allPropertisMap.get(methodName));
      
        
        try{
        	
            //获取响应结果
            if(json == null){
            	response = target.request(MediaType.APPLICATION_JSON).get() ;
            	str = response.readEntity(String.class);
            }
            	
            	
            else{
            	response =  target.request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));
                
            	str = (String)response.readEntity(String.class);
                
            }
            	
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	response.close();
        }
        return str ;
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
	 * 方法描述:根据南向api返回的json，重新包装，  返回结果是全是一对一的关系，不含有一个对一个集合的情况
	 * @param apiReturn
	 * @param json
	 */
	private void southAPIWrapper1(String apiReturn,JSONObject json){
		String status = JSONObject.fromObject(apiReturn).getString("status");
		if("success".equals(status)){
			Common.successCodeAndMessage(json);
			String key = "";
			String value = "";
			Iterator it = JSONObject.fromObject(apiReturn).keys();
			while(it.hasNext()){
				key = it.next().toString();
				if(!key.equals("status")){
					value = JSONObject.fromObject(apiReturn).getString(key);
					json.put(key, value);
				}
			}
			
		}else
			Common.failCodeAndMessage(json);
	}
	

	/**
	 * 方法描述:根据南向api返回的json，重新包装，  返回结果包含有一个对一个集合的情况
	 * @param apiReturn
	 * @param json
	 */
	private void southAPIWrapper2(String apiReturn,JSONObject json){
		String status = JSONObject.fromObject(apiReturn).getString("status");
		if("success".equals(status)){
			Common.successCodeAndMessage(json);
			String key = "";
			String value = "";
			Iterator it = JSONObject.fromObject(apiReturn).keys();
			while(it.hasNext()){
				key = it.next().toString();
				if(!key.equals("status"))
					value = JSONObject.fromObject(apiReturn).getString(key);
			}
			json.put(key, value);
		}else
			Common.failCodeAndMessage(json);
	}
	
	/**
	 * 获取国内IP地址经纬度数据块 和 获取全球IP地址经纬度数据块  以及全球IP地址总数 专用包装类
	 * @param apiReturn
	 * @param json
	 */
	private void southAPIWrapper3(String apiReturn,JSONObject json){
		//String status = JSONObject.fromObject(apiReturn).getString("status");
		//if("success".equals(status)){
		if(true){
			Common.successCodeAndMessage(json);
			String key = "";
			String value = "";
			Iterator it = JSONObject.fromObject(apiReturn).keys();
			while(it.hasNext()){
				key = it.next().toString();
				if(!key.equals("status"))
					value = JSONObject.fromObject(apiReturn).getString(key);
			}
			json.put(key, value);
		}else
			Common.failCodeAndMessage(json);
	}

	/**
	 * 用来判断传入的begIndex和endIndex是否合法
	 * @param str
	 * @return
	 */
	private boolean isValidIndex(String begIndex,String endIndex) {  
	        try{  
	        	int a = Integer.parseInt(begIndex);
	        	int b = Integer.parseInt(endIndex);
	        	boolean bol=false;
	        	if (a>0 && b>0 && a<b) {
	        		bol = true;
				}
	        	return bol;
	        	
	        }catch(Exception e){  
	            return false;  
	        }  
	 }    
	
	
	//2016-8-25 统计api
	public void countAPI(String token, String orderId, String taskId, int service_type, int api_type){
		User user = userService.findUserByToken(token);
		if(user.getType()!=3){
			Map<String, Object> param = new HashMap<String, Object>();
	        param.put("api_type", 1);
	        param.put("orderId", orderId);
	        API used = orderAPIService.findUsedByParam(param);
			
			//insert到统计表
			APINum num = new APINum();
			num.setApikey(user.getApikey());
			num.setService_type(service_type);
			num.setApi_type(api_type);//1表登录，2注销
			num.setStatus(1);
			num.setCreate_time(new Date());
			
			num.setApiId(used.getApiId());
			num.setToken(token);
			num.setOrderId(orderId);
			if(taskId!=null){
				num.setTaskId(Integer.parseInt(taskId));
			}
			userService.insertAPINum(num);
			ManagerWorker.createAPINum(user.getApikey(), service_type, api_type, 1);
		}
		
	}
	
}
