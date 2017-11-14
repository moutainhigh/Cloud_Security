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



import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
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
 * 创 建 人  ：  man
 * 创建日期：  2017-03-08
 * 描        述：  接收北向WebService请求接口
 * 版        本：  1.0
 */
/**
 * @author chengao
 *
 */
@Component
@Path("openapi/malurl")
public class N_MalUrlService {
	
		@Autowired
	    public IUserService userService;
	    @Autowired
		public IOrderAPIService orderAPIService;
		

		//1、获取当天国内活动恶意URL信息
		@GET
	    @Path("/getdatabycntoday/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String getMalurlDataByCNToday(@PathParam("token") String token) {
			JSONObject json = new JSONObject();
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
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,null);
								southAPIWrapper(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		//2、获取当天全球活动恶意URL信息
		@GET
	    @Path("/getdatabyyesterday/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String getMalurlDataByToday(@PathParam("token") String token) {
			JSONObject json = new JSONObject();
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
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,null);
								southAPIWrapper(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		//3、获取指定时间段内国内活动恶意URL信息
		@POST
	    @Path("/getdatabycnperiod/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String getMalurlDataByCNPeriod(@PathParam("token") String token, String dataJson) {
			JSONObject json = new JSONObject();
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			String begDate = jsonObj.getString("begDate");
			String endDate = jsonObj.getString("endDate");
			if( isValidDate(begDate) && isValidDate(endDate)){
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
									paramJson.put("begDate", begDate);
									paramJson.put("endDate", endDate);
									String southAPIReturn = getSouthAPIByMethod(currentMethodName,paramJson);
									southAPIWrapper(southAPIReturn,json);
									
									for (int i = 0; i < oAPIList.size(); i++) {
										countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		//4、获取指定时间段内全球活动恶意URL信息
		@POST
	    @Path("/getdatabyperiod/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String getMalurlDataByPeriod(@PathParam("token") String token, String dataJson) {
			JSONObject json = new JSONObject();
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			String begDate = jsonObj.getString("begDate");
			String endDate = jsonObj.getString("endDate");
			if( isValidDate(begDate) && isValidDate(endDate)){
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
									paramJson.put("begDate", begDate);
									paramJson.put("endDate", endDate);
									String southAPIReturn = getSouthAPIByMethod(currentMethodName,paramJson);
									southAPIWrapper(southAPIReturn,json);
									
									for (int i = 0; i < oAPIList.size(); i++) {
										countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		//5、获取国内所有活动恶意URL信息
		@GET
	    @Path("/getdatabycn/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String getMalurlDataByCN(@PathParam("token") String token) {
			JSONObject json = new JSONObject();
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
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,null);
								southAPIWrapper(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		//6、获取全球所有活动恶意URL信息
		@GET
	    @Path("/getdata/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String getMalurlData(@PathParam("token") String token) {
			JSONObject json = new JSONObject();
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
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,null);
								southAPIWrapper(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		
		//7、获取国内所有活动恶意URL针对的目标列表
		@GET
	    @Path("/gettargetlistbycn/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String getMalurlTargetListByCN(@PathParam("token") String token) {
			JSONObject json = new JSONObject();
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
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,null);
								southAPIWrapper(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		//8、获取国内针对特定目标所有活动恶意URL信息
		@POST
	    @Path("/getdatabycntarget/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String getMalurlDataByCNTarget(@PathParam("token") String token,String dataJson) {
			JSONObject json = new JSONObject();
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			String target = jsonObj.getString("target");
			
			if( target != null && !target.trim().isEmpty()){
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
									paramJson.put("target", target);
									String southAPIReturn = getSouthAPIByMethod(currentMethodName,paramJson);
									southAPIWrapper(southAPIReturn,json);
									
									for (int i = 0; i < oAPIList.size(); i++) {
										countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		
		
		//9、获取国内活动恶意URL行业分类列表
		@GET
	    @Path("/getfieldlistbycn/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String getMalurlFieldListByCN(@PathParam("token") String token) {
			JSONObject json = new JSONObject();
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
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,null);
								southAPIWrapper(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		//10、获取国内某行业所有活动恶意URL信息
		@POST
	    @Path("/getdatabycnfield/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String getMalurlDataByCNField(@PathParam("token") String token,String dataJson) {
			JSONObject json = new JSONObject();
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			String field = jsonObj.getString("field");
			
			if( field != null && !field.trim().isEmpty()){
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
									paramJson.put("field", field);
									String southAPIReturn = getSouthAPIByMethod(currentMethodName,paramJson);
									southAPIWrapper(southAPIReturn,json);
									
									for (int i = 0; i < oAPIList.size(); i++) {
										countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		//11、查询指定域名或 IP是否存在活动恶意URL信息
		@POST
	    @Path("/querydatabydomain/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
	    public String queryMalurlDataByDomain(@PathParam("token") String token,String dataJson) {
			JSONObject json = new JSONObject();
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			String condition = jsonObj.getString("condition");
			
			if( condition != null && !condition.trim().isEmpty()){
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
									paramJson.put("condition", condition);
									String southAPIReturn = getSouthAPIByMethod(currentMethodName,paramJson);
									southAPIWrapper(southAPIReturn,json);
									
									for (int i = 0; i < oAPIList.size(); i++) {
										countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		
		
		//12、查看全球有效的恶意URL总数    2017-11-6 add by liao
		@GET
	    @Path("/getvaliddatacount/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
		public String getValidDataCount(@PathParam("token") String token) {
			JSONObject json = new JSONObject();
			try {
				//通过token查询user
				System.out.println("===恶意URL=== +  getValidDataCount()");
				User user = getUserByToken(token);
				List<OrderAPI> userableList = getUserableList(user);
				List<OrderAPI> oAPIList = this.getOAPIList(user);		
				if(token!=null && token!="" && user!=null){
					if(havePermission(user, oAPIList)){
						if(!serviceExpired(user,oAPIList)){
							if(!usedUp(user, userableList)){
								String currentMethodName = getMethodName();
								String southAPIReturn = getSouthAPIByMethod(currentMethodName,null);
								southAPIWrapper(southAPIReturn,json);
								
								for (int i = 0; i < oAPIList.size(); i++) {
									countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
		
		
		
		
		//13、分段查看全球有效的恶意URL   2017-11-6 add by liao
		@POST
	    @Path("/getdatabysection/{token}")
		@Produces(MediaType.APPLICATION_JSON) 
		public String getMalurlDataBySection(@PathParam("token") String token, String dataJson) {
			JSONObject json = new JSONObject();
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			String offset = jsonObj.getString("offset");
			String rows = jsonObj.getString("rows");
			if( isValidIndex(offset,rows)){
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
									paramJson.put("offset", offset);
									paramJson.put("rows", rows);
									String southAPIReturn = getSouthAPIByMethod(currentMethodName,paramJson);
									southAPIWrapper(southAPIReturn,json);
									
									for (int i = 0; i < oAPIList.size(); i++) {
										countAPI(token, oAPIList.get(i).getId(), null, 8, 2);
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
	 * 方法描述:根据当前访问用户的信息，查询其可用服务列表
	 * @author man
	 * @date 2017年3月16日
	 * @param user
	 * @return
	 */
	private  List<OrderAPI> getUserableList(User user){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("apiKey", user.getApikey());
        paramMap.put("apiId", 3);
		List<OrderAPI> userableList = orderAPIService.findUseableByParam(paramMap);
		return userableList ;
	}
	
	 /**
	 * 方法描述:判断传入的参数是否符合格式yyyy-MM-dd
	 * @author man
	 * @date 2017年3月16日
	 * @param str 日期字符串
	 * @return
	 */
	private boolean isValidDate(String str) {  
	        //String str = "2007-01-02";  
	        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	        try{  
	            Date date = (Date)formatter.parse(str);  
	            return str.equals(formatter.format(date));  
	        }catch(Exception e){  
	            return false;  
	        }  
	 } 
	
	/**
	 * 用来判断传入的offset和rows是否合法
	 * @param str
	 * @return
	 * add by liao 2017-11-6
	 */
	private boolean isValidIndex(String offset,String rows) {  
	        try{  
	        	int a = Integer.parseInt(offset);
	        	int b = Integer.parseInt(rows);
	        	boolean bol=false;
	        	if (a>=1 && b>=0) {
	        		bol = true;
				}
	        	return bol;
	        	
	        }catch(Exception e){  
	            return false;  
	        }  
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
	 * 方法描述:根据南向api返回的json，重新包装，返回符合恶意url格式的json
	 * @author man
	 * @date 2017年3月16日
	 * @param apiReturn
	 * @param json
	 */
	private void southAPIWrapper(String apiReturn,JSONObject json){
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//N_MalUrlService   ns = new N_MalUrlService();
		//ns.malUrl_getDataByCnToday("d290553b9437df25d48a339322ff7d44");
		//System.out.print("   ".trim().isEmpty());
		N_MalUrlService s = new N_MalUrlService();
		s.test1();
		s.test2();
		
	}
	/*private static String test(){
		try{
			String nnn= null;
			nnn.isEmpty();
			return "try";
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("catch");
			return "catch" ;
		}finally{
			
			System.out.print("finally");
		}*/
		
		

	private  void test1(){
		System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
	}
	private  void test2(){
		System.out.println(getMethodName());
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
