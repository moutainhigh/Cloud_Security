package com.cn.ctbri.controller;


import java.io.IOException;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderListService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Random;


/**
 * 创 建 人  ：  tang
 * 创建日期：  2016-5-17
 * 描        述：  wafController
 * 版        本：  1.0
 */
@Controller
public class WafController {
	@Autowired
	IAssetService assetService;
    @Autowired
    IServService servService;
    @Autowired
	IOrderAssetService orderAssetService;
    @Autowired
	ISelfHelpOrderService selfHelpOrderService;
    @Autowired
    IOrderListService orderListService;
    @Autowired
    IOrderService orderService;
	 /**
	 * 功能描述：获取所有资产列表
	 * 参数描述：  无
	 * add gxy
	 *     @time 2016-5-18
	 */
	@RequestMapping(value="getAssetList.html")
	public void getAssetList(HttpServletRequest request,HttpServletResponse response){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		Map<String,Object> m = new HashMap<String,Object>();

		try {
			//获取服务对象资产
			List<Asset> list = selfHelpOrderService.findServiceAsset(globle_user.getId());
			String wafFlag = request.getParameter("wafFlag");
			if(wafFlag.equals("0")){//不是waf
				m.put("assList", list);
				m.put("success", true);
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			}else{
				String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
				String IpAddressRegex ="^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
				boolean flag=false;
				boolean ipflag=false;
				List<Asset> assList = new ArrayList();
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						Asset asset = (Asset)list.get(i);
//						if (asset.getStatus() == 0) {
//							continue;
//						}
						
						String addr = asset.getAddr();
						String addInfo = "";
						//判断http协议
						if(addr.indexOf("http://")!=-1){
						  	if(addr.substring(addr.length()-1).indexOf("/")!=-1){
						  		addInfo = addr.trim().substring(7,addr.length()-1);
						  	}else{
						  		addInfo = addr.trim().substring(7,addr.length());
						  	}
						}else if(addr.indexOf("https://")!=-1){
							if(addr.substring(addr.length()-1).indexOf("/")!=-1){
						  		addInfo = addr.trim().substring(8,addr.length()-1);
						  	}else{
						  		addInfo = addr.trim().substring(8,addr.length());
						  	}
						}
						//判断ip地址是否包含端口号
						if(addInfo.indexOf(":")!=-1){
							String addArr[] = addInfo.split(":");
							ipflag = addArr[0].matches(IpAddressRegex);
							if(ipflag==false){
								flag=addArr[0].matches(hostnameRegex);
							}
						}else{
							ipflag = addInfo.matches(IpAddressRegex);
						}
						if(ipflag==false){
                            //判断资产地址是否是域名
								flag=addInfo.matches(hostnameRegex);
								if(flag){
									Asset  assetInfo = new Asset();
									assetInfo.setAddr(asset.getAddr());
									assetInfo.setId(asset.getId());
									assetInfo.setName(asset.getName());
									assetInfo.setIp(asset.getIp());
									assetInfo.setStatus(asset.getStatus());
									assList.add(assetInfo);
								}
							}
					}
				}
				m.put("assList", assList);
				m.put("success", true);
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			// 把数据返回到页面
			try {
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
    
	 /**
	 * 功能描述：购买waf服务
	 * 参数描述：  无
	 * add gxy
	 *     @time 2016-5-18
	 */
	@RequestMapping(value="wafDetails.html")
	public String wafDetails(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		int serviceId = Integer.parseInt(request.getParameter("serviceId"));
		
	    //判断serviceId是否存在
	    List<Serv> serList = servService.findAllService();
	    boolean hasServFlag = false;
	    if(serList!=null && serList.size()>0){
	    	for(int i = 0; i < serList.size(); i++){
	    		if(serviceId==serList.get(i).getId()){
	    			hasServFlag = true;
	    		}
	    	}
	    }
	    if(!hasServFlag){
	    	return "redirect:/index.html";
	    }
	    
	    //是否从首页进入
	    String indexPage = request.getParameter("indexPage");
		//获取服务对象资产
	    List<Asset> list = selfHelpOrderService.findServiceAsset(globle_user.getId());
	    String IpAddressRegex ="^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
		String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
		boolean flag=false;
		boolean ipflag=false;
		List assList = new ArrayList();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Asset asset = (Asset)list.get(i);
//				if (asset.getStatus() == 0) {
//					continue;
//				}
				String addr = asset.getAddr();
				String addInfo = "";
				//判断http协议
				if(addr.indexOf("http://")!=-1){
				  	if(addr.substring(addr.length()-1).indexOf("/")!=-1){
				  		addInfo = addr.trim().substring(7,addr.length()-1);
				  	}else{
				  		addInfo = addr.trim().substring(7,addr.length());
				  	}
				}else if(addr.indexOf("https://")!=-1){
					if(addr.substring(addr.length()-1).indexOf("/")!=-1){
				  		addInfo = addr.trim().substring(8,addr.length()-1);
				  	}else{
				  		addInfo = addr.trim().substring(8,addr.length());
				  	}
				}
				//判断ip地址是否包含端口号
				if(addInfo.indexOf(":")!=-1){
					String addArr[] = addInfo.split(":");
					ipflag = addArr[0].matches(IpAddressRegex);
					if(ipflag==false){
						flag=addArr[0].matches(hostnameRegex);
					}
				}else{
					ipflag = addInfo.matches(IpAddressRegex);
				}
				if(ipflag==false){
                    //判断资产地址是否是域名
				flag=addInfo.matches(hostnameRegex);
				if(flag){
					Asset  assetInfo = new Asset();
					assetInfo.setAddr(asset.getAddr());
					assetInfo.setId(asset.getId());
					assetInfo.setName(asset.getName());
					assetInfo.setIp(asset.getIp());
					assetInfo.setStatus(asset.getStatus());
					assList.add(assetInfo);
				 }
				}
			}
		}
		Serv service = servService.findById(serviceId);
		  //网站安全帮列表
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //查询安全能力API
		   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		// 查询系统安全帮
			List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0, "");
			int carnum = shopCarList.size() + apiList.size() + sysList.size();
		 request.setAttribute("carnum", carnum);  
		request.setAttribute("assList", assList);
		request.setAttribute("service", service);
		request.setAttribute("indexPage", indexPage);
        request.setAttribute("service", service);
	    return  "/source/page/details/wafDetails";
	}
	
	 /**
	 * 功能描述：判断ip地址是否与域名绑定的一致
	 * 参数描述：  无
	 * add gxy
	 * @throws Exception 
	 *     @time 2016-5-19
	 */
	@RequestMapping(value="VerificationIP.html")
	public void VerificationIP(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> m = new HashMap<String, Object>();
		//ip地址
		String ipStr = request.getParameter("ipVal");
		
		//域名
		String domainName = request.getParameter("domainName");
		String domainId =  request.getParameter("domainId");
	   String errorIp ="";
		String addInfo = "";
		  boolean flag=false;
		try{
		//判断http协议
		if(domainName.indexOf("http://")!=-1){
		  	if(domainName.substring(domainName.length()-1).indexOf("/")!=-1){
		  		addInfo = domainName.trim().substring(7,domainName.length()-1);
		  	}else{
		  		addInfo = domainName.trim().substring(7,domainName.length());
		  	}
		}else if(domainName.indexOf("https://")!=-1){
			if(domainName.substring(domainName.length()-1).indexOf("/")!=-1){
		  		addInfo = domainName.trim().substring(8,domainName.length()-1);
		  	}else{
		  		addInfo = domainName.trim().substring(8,domainName.length());
		  	}
		}
	    List<String> IpInfo = new ArrayList();
	    //根据域名找出域名绑定下得所有ip地址
		InetAddress[] addresses=InetAddress.getAllByName(addInfo);
		for(InetAddress addr:addresses)
		{
			IpInfo.add(addr.getHostAddress());
      }
        String array[]=IpInfo.toArray(new String[]{});
      
        String ipPortVal="";
        //页面输入的ip地址
        String ipArr[]= ipStr.split(",");
        for(int i=0;i<ipArr.length;i++){
        	if(ipArr[i].indexOf(":")!=-1){
        		String ipPort[] = ipArr[i].split(":");
        		ipPortVal=ipPort[0];
        	}else{
        		ipPortVal=ipArr[i];
        	}
      	  for(int k=0;k<array.length;k++){
      		    if(ipPortVal.equals(array[k])){
      		    
      		    	flag=true;
      		    }else{
      		      	flag=false;
      		    }
      	  }
      	  if(!flag){
      		errorIp+=ipArr[i]+",";
      	  }
        }
       
        
        if(flag){
       
        //添加购物车时
		String serviceId = request.getParameter("serviceId");
		if(serviceId!=null){
			 SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss"); 
			 Date date= new Date();
			 String value1= sdf1.format(date);
			 //类型
			 String orderType = request.getParameter("orderType");
			 //开始时间
			 String beginDate = request.getParameter("beginDate");
			 //服务期限
			 String month ="";
			 Date cd= DateUtils.stringToDate(beginDate);
			 //包月
			 if(orderType.equals("8")){
				month =  request.getParameter("month");
			 }else{
				 month="12";
			 }
			Date endDate = DateUtils.getDateAfterMonths(cd,Integer.parseInt(month));
			String endVal =	DateUtils.dateToDate(endDate)+" "+value1;
			String beginVal = beginDate+" "+value1;   
			Date end = DateUtils.stringToDateNYRSFM(endVal);
			Date begin = DateUtils.stringToDateNYRSFM(beginVal);
			//获得当前日期后十分钟
			Date afterEnd = DateUtils.getDateAfter10Mins(end);
			Date afterBegin = DateUtils.getDateAfter10Mins(begin);
		   

	         m.put("serviceId", serviceId);
	         m.put("orderType", orderType);
	         m.put("beginDate", DateUtils.dateToString(afterBegin));
	         m.put("domainName", domainName);
	         m.put("ipStr", ipStr);
	         m.put("month", month);
	         m.put("domainId", domainId);
		}
	 	
        }   
        if(errorIp.length()>1){
        	  m.put("errorIp", errorIp.substring(0, errorIp.length()-1));
        }
     
	}catch(Exception e){
			e.printStackTrace();
			flag=false;
			 m.put("errorIp", "");
		}
		m.put("flag", flag);
		   JSONObject JSON = CommonUtil.objectToJson(response, m);
	       // 把数据返回到页面
           CommonUtil.writeToJsp(response, JSON);
		
	}
	
	 /**
	 * 功能描述：添加到购物车
	 * 参数描述：  无
	 * add gxy
	 *     @time 2016-5-19
	 */
	@RequestMapping(value="shoppingWaf.html")
	public void shoppingWaf(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> m = new HashMap<String, Object>();
		  try{
				boolean ipflag=false;
				  boolean flag=false;
				  String addInfo = "";
				  double countPrice =0.0;
				  Date eDate = new Date();
				  List<String> IpInfo = new ArrayList();
				  int linkmanId = Random.eightcode();
				  //根据ip地址加端口号
			        String ipPortstr ="";
				  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
			  User globle_user = (User) request.getSession().getAttribute("globle_user");
				//资产ids
		       String serviceId = request.getParameter("serviceId");
				String orderType = request.getParameter("orderType");
		        String beginDate = request.getParameter("beginDate");
		       // String endDate = request.getParameter("endDate");
		        String createDate = DateUtils.dateToString(new Date());
		        //String price = request.getParameter("price");
		        String domainName = request.getParameter("domainName");
		        String domainId = request.getParameter("domainId");
		        String ipStr = request.getParameter("ipStr");
		        String month = request.getParameter("month");
		        /*****判断参数开始***/
		        if(serviceId==null||"".equals(serviceId)||orderType==null||"".equals(orderType)||beginDate==null||"".equals(beginDate)||month==null||"".equals(month)
		        		||domainName==null||"".equals(domainName)||domainId==null||"".equals(domainId)||ipStr==null||"".equals(ipStr)){
		        	
		        	   m.put("error",true);
		          	  JSONObject JSON = CommonUtil.objectToJson(response, m);
		   				try {
		   					// 把数据返回到页面
		   					CommonUtil.writeToJsp(response, JSON);
		   				} catch (IOException e) {
		   					e.printStackTrace();
		   				}
		   				return;
		        }
		        if(!serviceId.equals("6")){
		        	  m.put("error",true);
		          	  JSONObject JSON = CommonUtil.objectToJson(response, m);
		   				try {
		   					// 把数据返回到页面
		   					CommonUtil.writeToJsp(response, JSON);
		   				} catch (IOException e) {
		   					e.printStackTrace();
		   				}
		   				return;
		        }
		        if(!orderType.equals("8")&&!orderType.equals("9")){
		        	  m.put("error",true);
		          	  JSONObject JSON = CommonUtil.objectToJson(response, m);
		   				try {
		   					// 把数据返回到页面
		   					CommonUtil.writeToJsp(response, JSON);
		   				} catch (IOException e) {
		   					e.printStackTrace();
		   				}
		   				return;
		        }
		        String IpAddressRegex ="^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
				String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
				//判断ip地址是否包含端口号
				if(domainName.indexOf(":")!=-1){
					String addArr[] = domainName.split(":");
					ipflag = addArr[0].matches(IpAddressRegex);
					if(ipflag==false){
						flag=addArr[0].matches(hostnameRegex);
					}
				}else{
					ipflag = domainName.matches(IpAddressRegex);
				}
				if(flag==false&&ipflag==false){
					 m.put("error",true);
		          	  JSONObject JSON = CommonUtil.objectToJson(response, m);
		   				try {
		   					// 把数据返回到页面
		   					CommonUtil.writeToJsp(response, JSON);
		   				} catch (IOException e) {
		   					e.printStackTrace();
		   				}
		   				return;
				}
				//判断http协议
				if(domainName.indexOf("http://")!=-1){
				  	if(domainName.substring(domainName.length()-1).indexOf("/")!=-1){
				  		addInfo = domainName.trim().substring(7,domainName.length()-1);
				  	}else{
				  		addInfo = domainName.trim().substring(7,domainName.length());
				  	}
				}else if(domainName.indexOf("https://")!=-1){
					if(domainName.substring(domainName.length()-1).indexOf("/")!=-1){
				  		addInfo = domainName.trim().substring(8,domainName.length()-1);
				  	}else{
				  		addInfo = domainName.trim().substring(8,domainName.length());
				  	}
				}
				    //根据域名找出域名绑定下得所有ip地址
					InetAddress[] addresses=InetAddress.getAllByName(addInfo);
					for(InetAddress addr:addresses)
					{
						IpInfo.add(addr.getHostAddress());
			      }
					String array[]=IpInfo.toArray(new String[]{});
			        boolean iflag=false;
			        String ipPortVal="";
			        //页面输入的ip地址
			        String ipArr[]= ipStr.split(",");
			        for(int i=0;i<ipArr.length;i++){
			        	if(ipArr[i].indexOf(":")!=-1){
			        		String ipPort[] = ipArr[i].split(":");
			        		ipPortVal=ipPort[0];
			        	}else{
			        		ipPortVal=ipArr[i];
			        	}
			      	  for(int k=0;k<array.length;k++){
			      		    if(ipPortVal.equals(array[k])){
			      		    
			      		    	iflag=true;
			      		    }else{
			      		    	iflag=false;
			      		    }
			      	  }
			       }
			        if(!iflag){
			        	 m.put("error",true);
			          	  JSONObject JSON = CommonUtil.objectToJson(response, m);
			   				try {
			   					// 把数据返回到页面
			   					CommonUtil.writeToJsp(response, JSON);
			   				} catch (IOException e) {
			   					e.printStackTrace();
			   				}
			   				return;
			        }
			        /*****判断参数结束***/
			        if(ipStr!=null&&!"".equals(ipStr)){
			        	if(ipStr.indexOf(",")!=-1){
			        		String ipArray[] = ipStr.split(",");
			        		for(int i =0;i<ipArray.length;i++){
			        			String ipPort = ipArray[i];
			        			if(ipPort.indexOf(":")==-1){
			        			   if(domainName.indexOf("http://")!=-1){
			        				ipPort=ipPort+":80";
			        				}else if(domainName.indexOf("https://")!=-1){
			        					ipPort=ipPort+":443";	
			        				}
			        				ipPortstr=ipPort+",";
			        			}else{
			        				ipPortstr = ipArray+",";
			        			}
			        		}
			        	}else{
			        		if(ipStr.indexOf(":")==-1){
			        			 if(domainName.indexOf("http://")!=-1){
			        			    ipPortstr = ipStr+":80"+",";
			        			 }else if(domainName.indexOf("https://")!=-1){
			        				 ipPortstr=ipStr+":443";	
			        				}
			        		}else{
			        			ipPortstr =ipStr+",";
			        		}
			        	}
			        }
			        Serv service = servService.findById(Integer.parseInt(serviceId));
				    if(service==null){
				    	 m.put("error",true);
			          	  JSONObject JSON = CommonUtil.objectToJson(response, m);
			   				try {
			   					// 把数据返回到页面
			   					CommonUtil.writeToJsp(response, JSON);
			   				} catch (IOException e) {
			   					e.printStackTrace();
			   				}
			   				return;
				    }
				    Date bDate=DateUtils.stringToDateNYRSFM(beginDate);
				    if(orderType.equals("9")){//包年
				    	countPrice =9000;
				    	eDate = DateUtils.getDateAfterOneYear(bDate);
				     
				    }else{//包月
				    	countPrice = 880*Integer.parseInt(month);
				    	eDate = DateUtils.getDateAfterMonths(bDate, Integer.parseInt(month));
				    
				    }
				    Order order = new Order();
			        
			        SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
					String orderDate = odf.format(new Date());
			        String orderId = String.valueOf(Random.fivecode())+orderDate;
			        Date  create_date=sdf.parse(createDate); 
			        order.setId(orderId);
			       
			      
			        order.setBegin_date(DateUtils.stringToDateNYRSFM(beginDate));
			        order.setEnd_date(eDate);
			        order.setServiceId(Integer.parseInt(serviceId));
			        order.setCreate_date(create_date);
			        order.setTask_date(DateUtils.stringToDateNYRSFM(beginDate));
					order.setUserId(globle_user.getId());
					order.setPrice(countPrice);
					order.setType(Integer.parseInt(orderType));
					
					order.setContactId(linkmanId);
					order.setIsAPI(2);
			        order.setPayFlag(0);//未支付
			        selfHelpOrderService.insertOrder(order);
				    
			    	//插入订单资产表
			        //根据资产名称查询资产信息
			        Map<String, Object> paramMap = new HashMap<String, Object>();
			        paramMap.put("userId",globle_user.getId());
			        paramMap.put("addr", domainName);
					List<Asset> listForName = assetService.findByAssetAddr(paramMap);
					Asset assetInfo = listForName.get(0);
			            OrderAsset orderAsset = new OrderAsset();
			            orderAsset.setOrderId(orderId);
			            orderAsset.setAssetId(assetInfo.getId());
			            orderAsset.setServiceId(Integer.parseInt(serviceId));
			            orderAsset.setIpArray(ipPortstr);
			            orderAsset.setSermonth(Integer.parseInt(month));
			            orderAsset.setAssetAddr(assetInfo.getAddr());
			            orderAsset.setAssetName(assetInfo.getName());
			            orderAssetService.insertOrderAsset(orderAsset);
			           
			            //插入联系人
			            Linkman linkObj = new Linkman();
			          
			            linkObj.setId(linkmanId);
			            linkObj.setName(globle_user.getName());
			            linkObj.setMobile(globle_user.getMobile());
			            linkObj.setEmail(globle_user.getEmail());
			            linkObj.setUserId(globle_user.getId());
			            selfHelpOrderService.insertLinkman(linkObj);
			            
			            //网站安全帮列表
			            List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
			         //查询安全能力API
			  		 List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
			  	// 查询系统安全帮
			 		List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0, "");
			 		int carnum = shopCarList.size() + apiList.size() + sysList.size();
			  		 request.setAttribute("carnum", carnum);
			  		 
			  		   m.put("sucess", true);
			  		   m.put("serviceId", serviceId);
			  		   JSONObject JSON = CommonUtil.objectToJson(response, m);
			  	        // 把数据返回到页面
			          CommonUtil.writeToJsp(response, JSON);
		  }catch(Exception e){
			  m.put("sucess", false);
			  
		  }
	}

	 /**
	 * 功能描述： 跳立即支付頁
	 * 参数描述：  无
	 * @throws ParseException 
	 *     @time 2016-3-10
	 */
	@RequestMapping(value="buyNowWafUI.html")
	public String buyNowWafUI(HttpServletRequest request) throws Exception{
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		List assetIdsList = new ArrayList();
		 String addInfo = "";
	       String price = "";
	       double countPrice =0.0;
	       List<String> IpInfo = new ArrayList();
	       boolean flag=false;
			boolean ipflag=false;
			String ipPortstr="";
		//资产ids
        String domainName = request.getParameter("domainName");
        String domainId = request.getParameter("domainId");
        String beginDate = request.getParameter("beginDate");
        String scanType = request.getParameter("scanType");
        String serviceId = request.getParameter("serviceId");

        String timeswaf = request.getParameter("timeswaf");
        String ipArray = request.getParameter("ipArray");
        String createDate = DateUtils.dateToString(new Date());
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
        /*****判断参数开始***/
        if(domainName==null||"".equals(domainName)||beginDate==null||"".equals(beginDate)||scanType==null||"".equals(scanType)
        		||serviceId==null||"".equals(serviceId)||timeswaf==null||"".equals(timeswaf)||ipArray==null||"".equals(ipArray)){
        	return "redirect:/index.html";
        }
        if(!scanType.equals("8")&&!scanType.equals("9")){
        	return "redirect:/index.html";
        }
        String IpAddressRegex ="^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
		String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
		//判断ip地址是否包含端口号
		if(domainName.indexOf(":")!=-1){
			String addArr[] = domainName.split(":");
			ipflag = addArr[0].matches(IpAddressRegex);
			if(ipflag==false){
				flag=addArr[0].matches(hostnameRegex);
			}
		}else{
			ipflag = domainName.matches(IpAddressRegex);
		}
		if(flag==false&&ipflag==false){
			return "redirect:/index.html";
		}
		
       
		//判断http协议
		if(domainName.indexOf("http://")!=-1){
		  	if(domainName.substring(domainName.length()-1).indexOf("/")!=-1){
		  		addInfo = domainName.trim().substring(7,domainName.length()-1);
		  	}else{
		  		addInfo = domainName.trim().substring(7,domainName.length());
		  	}
		}else if(domainName.indexOf("https://")!=-1){
			if(domainName.substring(domainName.length()-1).indexOf("/")!=-1){
		  		addInfo = domainName.trim().substring(8,domainName.length()-1);
		  	}else{
		  		addInfo = domainName.trim().substring(8,domainName.length());
		  	}
		}
		    //根据域名找出域名绑定下得所有ip地址
			InetAddress[] addresses=InetAddress.getAllByName(addInfo);
			for(InetAddress addr:addresses)
			{
				IpInfo.add(addr.getHostAddress());
	      }
		String array[]=IpInfo.toArray(new String[]{});
        boolean iflag=false;
        String ipPortVal="";
        //页面输入的ip地址
        String ipArr[]= ipArray.split(",");
        for(int i=0;i<ipArr.length;i++){
        	if(ipArr[i].indexOf(":")!=-1){
        		String ipPort[] = ipArr[i].split(":");
        		ipPortVal=ipPort[0];
        	}else{
        		ipPortVal=ipArr[i];
        	}
      	  for(int k=0;k<array.length;k++){
      		    if(ipPortVal.equals(array[k])){
      		    
      		    	iflag=true;
      		    }else{
      		    	iflag=false;
      		    }
      	  }
       }
        if(!iflag){
        	return "redirect:/index.html";
        }
	  
        if(!serviceId.equals("6")){
        	return "redirect:/index.html";
      }
        /*****判断参数结束***/
        //根据id查询service
	    Serv service = servService.findById(Integer.parseInt(serviceId));
	    if(service==null){
	    	return "redirect:/index.html";
	    }
	    if(ipArray!=null&&!"".equals(ipArray)){
        	if(ipArray.indexOf(",")!=-1){
        		String ipArrays[] = ipArray.split(",");
        		for(int i =0;i<ipArrays.length;i++){
        			String ipPort = ipArrays[i];
        			if(ipPort.indexOf(":")==-1){
        			   if(domainName.indexOf("http://")!=-1){
        				ipPort=ipPort+":80";
        				}else if(domainName.indexOf("https://")!=-1){
        					ipPort=ipPort+":443";	
        				}
        				ipPortstr=ipPort+",";
        			}else{
        				ipPortstr = ipArray+",";
        			}
        		}
        	}else{
        		if(ipArray.indexOf(":")==-1){
        			 if(domainName.indexOf("http://")!=-1){
        			    ipPortstr = ipArray+":80"+",";
        			 }else if(domainName.indexOf("https://")!=-1){
        				 ipPortstr=ipArray+":443";	
        				}
        		}else{
        			ipPortstr =ipArray+",";
        		}
        	}
        }
	    
	    //日期格式 yyyy-MM
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    
	    OrderDetail orderDetail = new OrderDetail();
    	  SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
 	      String orderDate = odf.format(new Date());
 	    String detailId = orderDate+String.valueOf(Random.fivecode());
 	   //格式化价格
	    DecimalFormat df = new DecimalFormat("#.00");
	    Date now = new Date();
	    String nowDate = dateFormat.format(now);
	    beginDate = beginDate + nowDate.substring(10);
	    Date bDate=DateUtils.stringToDateNYRSFM(beginDate);
	    //得到开始时间10分钟后
	    bDate = DateUtils.getDateAfter10Mins(bDate);
	    Date eDate = new Date();
	   
	    if(scanType.equals("9")){//包年
	    	countPrice =9000;
	    	eDate = DateUtils.getDateAfterOneYear(bDate);
	     
	    }else{//包月
	    	countPrice = 880*Integer.parseInt(timeswaf);
	    	eDate = DateUtils.getDateAfterMonths(bDate, Integer.parseInt(timeswaf));
	    
	    }
	    assetIdsList.add(domainId);
 	   orderDetail.setId(detailId);
       orderDetail.setBegin_date(DateUtils.stringToDateNYRSFM(beginDate));
       orderDetail.setEnd_date(eDate);
       orderDetail.setType(Integer.parseInt(scanType));
       orderDetail.setServiceId(Integer.parseInt(serviceId));
       orderDetail.setUserId(globle_user.getId());
       orderDetail.setIsAPI(0);
       orderDetail.setAsstId(domainId);
       orderDetail.setPrice(Double.parseDouble(df.format(countPrice)));
       orderDetail.setCreate_date(sdf.parse(createDate));
       orderDetail.setIpArray(ipPortstr);
       orderDetail.setWafTimes(Integer.parseInt(timeswaf));
     
       selfHelpOrderService.SaveOrderDetail(orderDetail);
   	  OrderDetail orderDetailVo = selfHelpOrderService.getOrderDetailById(detailId, globle_user.getId(),assetIdsList);
	 
        request.setAttribute("user", globle_user);
        request.setAttribute("service", service);
	    request.setAttribute("orderDetail", orderDetailVo);
        String result = "/source/page/details/settlement";
        return result;
	}
	
	/**
     * 功能描述： 保存Waf订单
     * 参数描述：  
	 * @throws Exception 
     *       @time 2015-01-16
     */
    @RequestMapping(value="saveWafOrder.html")
    @ResponseBody
    public void saveWafOrder(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    	String id ="";
       try{
    	  Map<String, Object> m = new HashMap<String, Object>();
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	List assetIdsList = new ArrayList();
    	String createDate = DateUtils.dateToString(new Date());
    	 String linkname =request.getParameter("linkname");
         String phone = request.getParameter("phone");
         String email = request.getParameter("email");
    	//判断参数值
     
      	String orderDetailId = request.getParameter("orderDetailId");
      	if(orderDetailId==null||"".equals(orderDetailId)||linkname==null||"".equals(linkname)){
      		m.put("error", true);
          		 //object转化为Json格式
          	       JSONObject JSON = CommonUtil.objectToJson(response, m);
          	       try {
          	           // 把数据返回到页面
          	           CommonUtil.writeToJsp(response, JSON);
          	       } catch (IOException e) {
          	           e.printStackTrace();
          	       }
          	       return;
          
      	}
      	 /***判断参数开始**/
        if(phone!=null&&!"".equals(phone)){
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");  
         Matcher matcher = p.matcher(phone); 
	       if(!matcher.find()){
	    		m.put("error", true);
	      		 //object转化为Json格式
	      	       JSONObject JSON = CommonUtil.objectToJson(response, m);
	      	       try {
	      	           // 把数据返回到页面
	      	           CommonUtil.writeToJsp(response, JSON);
	      	       } catch (IOException e) {
	      	           e.printStackTrace();
	      	       }
	      	       return;
	       }
    	
        }
       if(email!=null&&!"".equals(email)){
    	   Pattern emailP = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");  
           Matcher ematcher = emailP.matcher(email); 
           if(!ematcher.find()){
        	   m.put("error", true);
        		 //object转化为Json格式
        	       JSONObject JSON = CommonUtil.objectToJson(response, m);
        	       try {
        	           // 把数据返回到页面
        	           CommonUtil.writeToJsp(response, JSON);
        	       } catch (IOException e) {
        	           e.printStackTrace();
        	       }
        	       return;
           }
       }
      
     /***判断参数结束**/
    	OrderDetail orderDetailVo =selfHelpOrderService.findOrderDetailById(orderDetailId, globle_user.getId());
    	if(orderDetailVo==null){
      		m.put("error", true);
     		 //object转化为Json格式
     	       JSONObject JSON = CommonUtil.objectToJson(response, m);
     	       try {
     	           // 把数据返回到页面
     	           CommonUtil.writeToJsp(response, JSON);
     	       } catch (IOException e) {
     	           e.printStackTrace();
     	       }
     	       return;
      	}
      	boolean assetsStatus = false;
		Asset _asset = assetService.findById(Integer.parseInt(orderDetailVo.getAsstId()),globle_user.getId());
		if(_asset==null){
			m.put("error", true);
     		 //object转化为Json格式
     	       JSONObject JSON = CommonUtil.objectToJson(response, m);
     	       try {
     	           // 把数据返回到页面
     	           CommonUtil.writeToJsp(response, JSON);
     	       } catch (IOException e) {
     	           e.printStackTrace();
     	       }
     	       return;
		}
		assetIdsList.add(orderDetailVo.getAsstId());
		OrderDetail orderDetail =selfHelpOrderService.getOrderDetailById(orderDetailId, globle_user.getId(), assetIdsList);
    	if(orderDetail==null){
    		m.put("error", true);
   		 //object转化为Json格式
   	       JSONObject JSON = CommonUtil.objectToJson(response, m);
   	       try {
   	           // 把数据返回到页面
   	           CommonUtil.writeToJsp(response, JSON);
   	       } catch (IOException e) {
   	           e.printStackTrace();
   	       }
   	       return;
    	}
    	
		//新增订单

		//生成订单id，当前日期加5位随机数
		SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
		String orderDate = odf.format(new Date());
        String orderId = orderDate+String.valueOf(Random.fivecode());
      //新增联系人
        Linkman linkObj = new Linkman();
        int linkmanId = Random.eightcode();
        linkObj.setId(linkmanId);
        linkObj.setName(linkname);
        linkObj.setMobile(phone);
        linkObj.setEmail(email);
        linkObj.setUserId(globle_user.getId());
        selfHelpOrderService.insertLinkman(linkObj);
        
		Order order = new Order();
		order.setId(orderId);
		order.setType(1);//长期
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		
		order.setBegin_date(orderDetail.getBegin_date());
		order.setEnd_date(orderDetail.getEnd_date());
		order.setCreate_date(sdf.parse(createDate));
		order.setScan_type(orderDetail.getType());
		order.setPayFlag(0);	
		order.setServiceId(orderDetail.getServiceId());
		order.setTask_date(orderDetail.getBegin_date());
		order.setIsAPI(2);
		order.setDelFlag(0);
		order.setPayFlag(0);
		order.setUserId(globle_user.getId());
		order.setStatus(0);
		order.setPrice(orderDetail.getPrice());
		order.setContactId(linkmanId);
		selfHelpOrderService.insertOrder(order);
		
		//新增服务资产
		OrderAsset orderAsset = new OrderAsset();
		orderAsset.setOrderId(orderId);
		orderAsset.setAssetId(Integer.parseInt(orderDetail.getAsstId()));
		orderAsset.setServiceId(orderDetail.getServiceId());
		orderAsset.setScan_type(orderDetail.getType());
		    if(String.valueOf(orderDetail.getType()).equals("8")){//包月
		    	orderAsset.setSermonth(orderDetail.getWafTimes());
		    }else{
		    	orderAsset.setSermonth(12);
		    }
		orderAsset.setIpArray(orderDetail.getIpArray());
		orderAsset.setAssetAddr(_asset.getAddr());
        orderAsset.setAssetName(_asset.getName());
		orderAssetService.insertOrderAsset(orderAsset);
		  //插入数据到order_list
	    OrderList ol = new OrderList();
	    //生成订单id
//	     id = String.valueOf(Random.eightcode());
		id = orderDate+String.valueOf(Random.fivecode());
	    ol.setId(id);
	    ol.setCreate_date(new Date());
	    ol.setOrderId(orderId);
	    ol.setUserId(globle_user.getId());
	    ol.setPrice(orderDetail.getPrice());
	    ol.setServerName(orderDetail.getServiceName());
	    orderListService.insert(ol);
	    
		m.put("orderStatus", true);
		m.put("orderListId", id);
		//object转化为Json格式
		//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
			    // 把数据返回到页面
			    CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
     * 功能描述： 保存续费Waf订单
     * 参数描述：  
	 * @throws Exception 
     *       @time 2015-01-16
     */
    @RequestMapping(value="saveWafRenewOrder.html")
    @ResponseBody
    public void saveWafRenewOrder(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    	String id ="";
       try{
    	  Map<String, Object> m = new HashMap<String, Object>();
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	List assetIdsList = new ArrayList();
    	String newOrderId = request.getParameter("orderId");
    	String createDate = DateUtils.dateToString(new Date());
    	 String linkname =request.getParameter("linkname");
         String phone = request.getParameter("phone");
         String email = request.getParameter("email");
         String month = request.getParameter("month");
         String orderIdList = request.getParameter("orderIdList");
         List<HashMap<String, Object>> orderList = orderService.findByOrderId(newOrderId);
		   	HashMap<String, Object> orderInfo=new HashMap<String, Object>();
		   	orderInfo=(HashMap) orderList.get(0);
			Date newDate = DateUtils.getDateAfterMonths((Date)orderInfo.get("end_date"), Integer.parseInt(month));
			double orderPrice = (Double)orderInfo.get("price");
			double price = 880*Integer.parseInt(month);
			double sumprice = orderPrice+price;
    	//判断参数值
      	String orderDetailId = request.getParameter("orderDetailId");
      	if(orderDetailId==null||"".equals(orderDetailId)||linkname==null||"".equals(linkname)){
      		m.put("error", true);
          		 //object转化为Json格式
          	       JSONObject JSON = CommonUtil.objectToJson(response, m);
          	       try {
          	           // 把数据返回到页面
          	           CommonUtil.writeToJsp(response, JSON);
          	       } catch (IOException e) {
          	           e.printStackTrace();
          	       }
          	       return;
          
      	}
      	 /***判断参数开始**/
        if(phone!=null&&!"".equals(phone)){
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");  
         Matcher matcher = p.matcher(phone); 
	       if(!matcher.find()){
	    		m.put("error", true);
	      		 //object转化为Json格式
	      	       JSONObject JSON = CommonUtil.objectToJson(response, m);
	      	       try {
	      	           // 把数据返回到页面
	      	           CommonUtil.writeToJsp(response, JSON);
	      	       } catch (IOException e) {
	      	           e.printStackTrace();
	      	       }
	      	       return;
	       }
    	
        }
       if(email!=null&&!"".equals(email)){
    	   Pattern emailP = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");  
           Matcher ematcher = emailP.matcher(email); 
           if(!ematcher.find()){
        	   m.put("error", true);
        		 //object转化为Json格式
        	       JSONObject JSON = CommonUtil.objectToJson(response, m);
        	       try {
        	           // 把数据返回到页面
        	           CommonUtil.writeToJsp(response, JSON);
        	       } catch (IOException e) {
        	           e.printStackTrace();
        	       }
        	       return;
           }
       }
      
     /***判断参数结束**/
    	OrderDetail orderDetailVo =selfHelpOrderService.findOrderDetailById(orderDetailId, globle_user.getId());
    	if(orderDetailVo==null){
      		m.put("error", true);
     		 //object转化为Json格式
     	       JSONObject JSON = CommonUtil.objectToJson(response, m);
     	       try {
     	           // 把数据返回到页面
     	           CommonUtil.writeToJsp(response, JSON);
     	       } catch (IOException e) {
     	           e.printStackTrace();
     	       }
     	       return;
      	}
      	boolean assetsStatus = false;
		Asset _asset = assetService.findById(Integer.parseInt(orderDetailVo.getAsstId()),globle_user.getId());
		if(_asset==null){
			m.put("error", true);
     		 //object转化为Json格式
     	       JSONObject JSON = CommonUtil.objectToJson(response, m);
     	       try {
     	           // 把数据返回到页面
     	           CommonUtil.writeToJsp(response, JSON);
     	       } catch (IOException e) {
     	           e.printStackTrace();
     	       }
     	       return;
		}
		assetIdsList.add(orderDetailVo.getAsstId());
		OrderDetail orderDetail =selfHelpOrderService.getOrderDetailById(orderDetailId, globle_user.getId(), assetIdsList);
    	if(orderDetail==null){
    		m.put("error", true);
   		 //object转化为Json格式
   	       JSONObject JSON = CommonUtil.objectToJson(response, m);
   	       try {
   	           // 把数据返回到页面
   	           CommonUtil.writeToJsp(response, JSON);
   	       } catch (IOException e) {
   	           e.printStackTrace();
   	       }
   	       return;
    	}
    	
		//新增订单

		//生成订单id，当前日期加5位随机数
		//SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
		//String orderDate = odf.format(new Date());
        //String orderId = orderDate+String.valueOf(Random.fivecode());
    	Date date = new Date();
      //新增联系人
        Linkman linkObj = new Linkman();
        int linkmanId = Random.eightcode();
        linkObj.setId(linkmanId);
        linkObj.setName(linkname);
        linkObj.setMobile(phone);
        linkObj.setEmail(email);
        linkObj.setUserId(globle_user.getId());
        selfHelpOrderService.insertLinkman(linkObj);
        
        selfHelpOrderService.updateRenewOrder(2,0,(Date)orderInfo.get("begin_date"),newDate,(Date)orderInfo.get("end_date"),date,sumprice,(String)orderInfo.get("id"));
		  //插入数据到order_list
	    OrderList ol = orderListService.findById(orderIdList);

	   ol.setCreate_date(new Date());
	   // ol.setOrderId(orderId);
	    ol.setPrice(price);
	    orderListService.update(ol);
	    
		m.put("orderStatus", true);
		m.put("orderListId",orderIdList);
		m.put("renew", "true");
		//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
			    // 把数据返回到页面
			    CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
	 * 功能描述：返回修改订单
	 * 参数描述：  无
	 * @throws Exception 
	 *      add by gxy 2016-5-10
	 */
	@RequestMapping(value="wafOrderBack.html")
	public String  wafOrderBack(HttpServletResponse response,HttpServletRequest request) throws Exception{
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		List assetIdsList = new ArrayList();
		/*//查找当前用户的资产列表
		List<Asset> list = assetService.findByUserId(globle_user.getId());
		String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
		boolean flag=false;
		List assList = new ArrayList();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Asset asset = (Asset)list.get(i);
				String addr = asset.getAddr();
				String addInfo = "";
				//判断http协议
				if(addr.indexOf("http://")!=-1){
				  	if(addr.substring(addr.length()-1).indexOf("/")!=-1){
				  		addInfo = addr.trim().substring(7,addr.length()-1);
				  	}else{
				  		addInfo = addr.trim().substring(7,addr.length());
				  	}
				}else if(addr.indexOf("https://")!=-1){
					if(addr.substring(addr.length()-1).indexOf("/")!=-1){
				  		addInfo = addr.trim().substring(8,addr.length()-1);
				  	}else{
				  		addInfo = addr.trim().substring(8,addr.length());
				  	}
				}
				//判断资产地址是否是域名
				flag=addInfo.matches(hostnameRegex);
				if(flag){
					Asset  assetInfo = new Asset();
					assetInfo.setAddr(asset.getAddr());
					assetInfo.setId(asset.getId());
					assetInfo.setName(asset.getName());
					assetInfo.setIp(asset.getIp());
					assList.add(assetInfo);
				}
			}
		}
		
        String domainId = request.getParameter("assetIds");
        String domainName = request.getParameter("domainName");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String scanType = request.getParameter("scanType");
        String ipArray = request.getParameter("ipArray");
        String times=request.getParameter("times");     
        String serviceId = request.getParameter("serviceId");
        

	    request.setAttribute("scanType", scanType);
        request.setAttribute("user", globle_user);
	    request.setAttribute("domainId", domainId);
	    request.setAttribute("domainName", domainName);
	    request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("ipArray", ipArray);
        request.setAttribute("times", times);
        request.setAttribute("serviceId", serviceId);
		Serv service = servService.findById(Integer.parseInt(request.getParameter("serviceId")));
		
		request.setAttribute("assList", assList);
		request.setAttribute("service", service);
  	     //网站安全帮列表
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
     //查询安全能力API
		 List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		 int carnum=shopCarList.size()+apiList.size();
		 request.setAttribute("carnum", carnum);
		 */
		
		List<Asset> list = assetService.findByUserId(globle_user.getId());
		String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
		boolean flag=false;
		List assList = new ArrayList();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Asset asset = (Asset)list.get(i);
				String addr = asset.getAddr();
				String addInfo = "";
				//判断http协议
				if(addr.indexOf("http://")!=-1){
				  	if(addr.substring(addr.length()-1).indexOf("/")!=-1){
				  		addInfo = addr.trim().substring(7,addr.length()-1);
				  	}else{
				  		addInfo = addr.trim().substring(7,addr.length());
				  	}
				}else if(addr.indexOf("https://")!=-1){
					if(addr.substring(addr.length()-1).indexOf("/")!=-1){
				  		addInfo = addr.trim().substring(8,addr.length()-1);
				  	}else{
				  		addInfo = addr.trim().substring(8,addr.length());
				  	}
				}
				//判断资产地址是否是域名
				flag=addInfo.matches(hostnameRegex);
				if(flag){
					Asset  assetInfo = new Asset();
					assetInfo.setAddr(asset.getAddr());
					assetInfo.setId(asset.getId());
					assetInfo.setName(asset.getName());
					assetInfo.setIp(asset.getIp());
					assList.add(assetInfo);
				}
			}
		}
		String domainId = request.getParameter("assetIds");
		   String orderDetailId = request.getParameter("orderDetailId");
		   if(domainId==null||"".equals(domainId)||orderDetailId==null||"".equals(orderDetailId)){
	        	return "redirect:/index.html";	
	        }
		   assetIdsList.add(domainId);
		   OrderDetail orderDetail = selfHelpOrderService.getOrderDetailById(orderDetailId, globle_user.getId(),assetIdsList);
	       if(orderDetail==null){
	    	   return "redirect:/index.html";	
	       }
	       Serv service = servService.findById(orderDetail.getServiceId()); 
	       //网站安全帮列表
	        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
	     //查询安全能力API
			 List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
			// 查询系统安全帮
				List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0, "");
				int carnum = shopCarList.size() + apiList.size() + sysList.size();
			 request.setAttribute("carnum", carnum);
			 request.setAttribute("assList", assList);
			request.setAttribute("service", service); 
			request.setAttribute("orderDetail", orderDetail); 
	    return  "/source/page/details/wafDetails";
	}
	
	
	/**
     * 解析新建站点
     * @param siteStr
     * @return id
     */
    public String analysisGetcreateSite(String siteStr) {
    	String id = "";
    	try {
    		JSONObject siteObject = JSONObject.fromObject(siteStr);
    		String status = siteObject.getString("status");
            if("success".equals(status)){
            	JSONObject websiteObject = siteObject.getJSONObject("website");
            	JSONObject groupObject = websiteObject.getJSONObject("group");
            	id = groupObject.getString("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    
    /**
     * 解析添加ip到工作接口
     * @param ethStr
     * @return multi_result
     */
    public String analysisGetPostIpToEth(String ethStr){
    	String multiResult = "";
    	try {
    		JSONObject obj = JSONObject.fromObject(ethStr);
    		JSONArray jsonArray = obj.getJSONArray("ip_address");
    		if(jsonArray!=null && jsonArray.size()>0){
    			String object = jsonArray.getString(0);
		        JSONObject jsonObject = JSONObject.fromObject(object);
		        multiResult = jsonObject.getString("multi_result");
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    	System.out.println(multiResult);
        return multiResult;
    }
    
    /**
     * 解析根据ip查询websec日志信息
     * @param reStr
     * @return wafLogWebsecList
     */
    public List analysisGetWaflogWebsecByIp(String reStr){
		List reList = new ArrayList();
    	try {
    		JSONObject obj = JSONObject.fromObject(reStr);
    		JSONArray jsonArray = obj.getJSONArray("wafLogWebsecList");
    		if(jsonArray!=null && jsonArray.size()>0){
    			for (int i = 0; i < jsonArray.size(); i++) {
    				Map<String,Object> newMap = new HashMap<String,Object>();
    				String object = jsonArray.getString(i);
			        JSONObject jsonObject = JSONObject.fromObject(object);
			        int logId = jsonObject.getInt("logId");
			        int resourceId = jsonObject.getInt("resourceId");
			        String resourceUri = jsonObject.getString("resourceUri");
			        String resourceIp = jsonObject.getString("resourceIp");
			        long siteId = jsonObject.getLong("siteId");
			        int protectId = jsonObject.getInt("protectId");
			        String dstIp = jsonObject.getString("dstIp");
			        String dstPort = jsonObject.getString("dstPort");
			        String srcIp = jsonObject.getString("srcIp");
			        String srcPort = jsonObject.getString("srcPort");
			        String method = jsonObject.getString("method");
			        String domain = jsonObject.getString("domain");
			        String uri = jsonObject.getString("uri");
			        String alertlevel = jsonObject.getString("alertlevel");
			        String eventType = jsonObject.getString("eventType");
			        String statTime = jsonObject.getString("statTime");
			        String action = jsonObject.getString("action");
			        String block = jsonObject.getString("block");
			        String blockInfo = jsonObject.getString("blockInfo");
			        String alertinfo = jsonObject.getString("alertinfo");
			        String proxyInfo = jsonObject.getString("proxyInfo");
			        String characters = jsonObject.getString("characters");
			        int countNum = jsonObject.getInt("countNum");
			        String protocolType = jsonObject.getString("protocolType");
			        String wci = jsonObject.getString("wci");
			        String wsi = jsonObject.getString("wsi");
			        
			        newMap.put("logId", logId);
			        newMap.put("resourceId", resourceId);
			        newMap.put("resourceUri", resourceUri);
			        newMap.put("resourceIp", resourceIp);
			        newMap.put("siteId", siteId);
			        newMap.put("protectId", protectId);
			        newMap.put("dstIp", dstIp);
			        newMap.put("dstPort", dstPort);
			        newMap.put("srcIp", srcIp);
			        newMap.put("srcPort", srcPort);
			        newMap.put("method", method);
			        newMap.put("domain", domain);
			        newMap.put("uri", uri);
			        newMap.put("alertlevel", alertlevel);
			        newMap.put("eventType", eventType);
			        newMap.put("statTime", statTime);
			        newMap.put("action", action);
			        newMap.put("block", block);
			        newMap.put("blockInfo", blockInfo);
			        newMap.put("alertinfo", alertinfo);
			        newMap.put("proxyInfo", proxyInfo);
			        newMap.put("characters", characters);
			        newMap.put("countNum", countNum);
			        newMap.put("protocolType", protocolType);
			        newMap.put("wci", wci);
			        newMap.put("wsi", wsi);
			        
			        reList.add(newMap);
				}
    			
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reList;
    }
    
    /**
     * 解析新建虚拟站点
     * @param siteStr
     * @return
     */
    public String analysisGetCreateVirtualSite(String siteStr){
    	String status = "";
    	try {
    		JSONObject obj = JSONObject.fromObject(siteStr);
    		status = obj.getString("status");   		
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return status;
    }
    
    /**
     * 解析根据logId查询websec日志信息
     * @param reStr
     * @return wafLogWebsecList
     */
    public Map analysisGetWaflogWebsecById(String reStr){
		Map<String,Object> newMap = new HashMap<String,Object>();
    	try {
    		JSONObject obj = JSONObject.fromObject(reStr);
    		JSONObject jsonObject = obj.getJSONObject("wafLogWebsec");
    		
	        int logId = jsonObject.getInt("logId");
	        int resourceId = jsonObject.getInt("resourceId");
	        String resourceUri = jsonObject.getString("resourceUri");
	        String resourceIp = jsonObject.getString("resourceIp");
	        long siteId = jsonObject.getLong("siteId");
	        int protectId = jsonObject.getInt("protectId");
	        String dstIp = jsonObject.getString("dstIp");
	        String dstPort = jsonObject.getString("dstPort");
	        String srcIp = jsonObject.getString("srcIp");
	        String srcPort = jsonObject.getString("srcPort");
	        String method = jsonObject.getString("method");
	        String domain = jsonObject.getString("domain");
	        String uri = jsonObject.getString("uri");
	        String alertlevel = jsonObject.getString("alertlevel");
	        String eventType = jsonObject.getString("eventType");
	        String statTime = jsonObject.getString("statTime");
	        String action = jsonObject.getString("action");
	        String block = jsonObject.getString("block");
	        String blockInfo = jsonObject.getString("blockInfo");
	        String alertinfo = jsonObject.getString("alertinfo");
	        String proxyInfo = jsonObject.getString("proxyInfo");
	        String characters = jsonObject.getString("characters");
	        int countNum = jsonObject.getInt("countNum");
	        String protocolType = jsonObject.getString("protocolType");
	        String wci = jsonObject.getString("wci");
	        String wsi = jsonObject.getString("wsi");
	        
	        newMap.put("logId", logId);
	        newMap.put("resourceId", resourceId);
	        newMap.put("resourceUri", resourceUri);
	        newMap.put("resourceIp", resourceIp);
	        newMap.put("siteId", siteId);
	        newMap.put("protectId", protectId);
	        newMap.put("dstIp", dstIp);
	        newMap.put("dstPort", dstPort);
	        newMap.put("srcIp", srcIp);
	        newMap.put("srcPort", srcPort);
	        newMap.put("method", method);
	        newMap.put("domain", domain);
	        newMap.put("uri", uri);
	        newMap.put("alertlevel", alertlevel);
	        newMap.put("eventType", eventType);
	        newMap.put("statTime", statTime);
	        newMap.put("action", action);
	        newMap.put("block", block);
	        newMap.put("blockInfo", blockInfo);
	        newMap.put("alertinfo", alertinfo);
	        newMap.put("proxyInfo", proxyInfo);
	        newMap.put("characters", characters);
	        newMap.put("countNum", countNum);
	        newMap.put("protocolType", protocolType);
	        newMap.put("wci", wci);
	        newMap.put("wsi", wsi);
			 
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return newMap;
    }
    
    public List analysisGetWaflogWebsecInTime(String reStr){
		List reList = new ArrayList();
    	try {
    		JSONObject obj = JSONObject.fromObject(reStr);
    		JSONArray jsonArray = obj.getJSONArray("wafLogWebsecList");
    		if(jsonArray!=null && jsonArray.size()>0){
    			for (int i = 0; i < jsonArray.size(); i++) {
    				Map<String,Object> newMap = new HashMap<String,Object>();
    				String object = jsonArray.getString(i);
			        JSONObject jsonObject = JSONObject.fromObject(object);
			        int logId = jsonObject.getInt("logId");
			        int resourceId = jsonObject.getInt("resourceId");
			        String resourceUri = jsonObject.getString("resourceUri");
			        String resourceIp = jsonObject.getString("resourceIp");
			        long siteId = jsonObject.getLong("siteId");
			        int protectId = jsonObject.getInt("protectId");
			        String dstIp = jsonObject.getString("dstIp");
			        String dstPort = jsonObject.getString("dstPort");
			        String srcIp = jsonObject.getString("srcIp");
			        String srcPort = jsonObject.getString("srcPort");
			        String method = jsonObject.getString("method");
			        String domain = jsonObject.getString("domain");
			        String uri = jsonObject.getString("uri");
			        String alertlevel = jsonObject.getString("alertlevel");
			        String eventType = jsonObject.getString("eventType");
			        String statTime = jsonObject.getString("statTime");
			        String action = jsonObject.getString("action");
			        String block = jsonObject.getString("block");
			        String blockInfo = jsonObject.getString("blockInfo");
			        String alertinfo = jsonObject.getString("alertinfo");
			        String proxyInfo = jsonObject.getString("proxyInfo");
			        String characters = jsonObject.getString("characters");
			        int countNum = jsonObject.getInt("countNum");
			        String protocolType = jsonObject.getString("protocolType");
			        String wci = jsonObject.getString("wci");
			        String wsi = jsonObject.getString("wsi");
			        
			        newMap.put("logId", logId);
			        newMap.put("resourceId", resourceId);
			        newMap.put("resourceUri", resourceUri);
			        newMap.put("resourceIp", resourceIp);
			        newMap.put("siteId", siteId);
			        newMap.put("protectId", protectId);
			        newMap.put("dstIp", dstIp);
			        newMap.put("dstPort", dstPort);
			        newMap.put("srcIp", srcIp);
			        newMap.put("srcPort", srcPort);
			        newMap.put("method", method);
			        newMap.put("domain", domain);
			        newMap.put("uri", uri);
			        newMap.put("alertlevel", alertlevel);
			        newMap.put("eventType", eventType);
			        newMap.put("statTime", statTime);
			        newMap.put("action", action);
			        newMap.put("block", block);
			        newMap.put("blockInfo", blockInfo);
			        newMap.put("alertinfo", alertinfo);
			        newMap.put("proxyInfo", proxyInfo);
			        newMap.put("characters", characters);
			        newMap.put("countNum", countNum);
			        newMap.put("protocolType", protocolType);
			        newMap.put("wci", wci);
			        newMap.put("wsi", wsi);
			        
			        reList.add(newMap);
				}
    			
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reList;
    }
    
    /**
   	 * 功能描述：waf续费
   	 * 参数描述：  无
   	 * add gxy
   	 *     @time 2017-6-30
   	 */
   	@RequestMapping(value="reNewWafDetails.html")
   	public String reNewWafDetails(HttpServletRequest request){
   		User globle_user = (User) request.getSession().getAttribute("globle_user");
   		String orderId = request.getParameter("orderId");
   	//获取订单信息
        List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
        HashMap<String, Object> order=new HashMap<String, Object>();
        order=(HashMap) orderList.get(0);
        int  serviceId=(Integer) order.get("serviceId");
   		
   	    //判断serviceId是否存在
   	    List<Serv> serList = servService.findAllService();
   	    boolean hasServFlag = false;
   	    if(serList!=null && serList.size()>0){
   	    	for(int i = 0; i < serList.size(); i++){
   	    		if(serviceId==serList.get(i).getId()){
   	    			hasServFlag = true;
   	    		}
   	    	}
   	    }
   	    if(!hasServFlag){
   	    	return "redirect:/index.html";
   	    }
   	    
   	 
   		//获取服务对象资产
   	    List<Asset> list = selfHelpOrderService.findServiceAsset(globle_user.getId());
   	    String IpAddressRegex ="^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
   		String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
   		boolean flag=false;
   		boolean ipflag=false;
   		List assList = new ArrayList();
   		if(list!=null&&list.size()>0){
   			for(int i=0;i<list.size();i++){
   				Asset asset = (Asset)list.get(i);
//   				if (asset.getStatus() == 0) {
//   					continue;
//   				}
   				String addr = asset.getAddr();
   				String addInfo = "";
   				//判断http协议
   				if(addr.indexOf("http://")!=-1){
   				  	if(addr.substring(addr.length()-1).indexOf("/")!=-1){
   				  		addInfo = addr.trim().substring(7,addr.length()-1);
   				  	}else{
   				  		addInfo = addr.trim().substring(7,addr.length());
   				  	}
   				}else if(addr.indexOf("https://")!=-1){
   					if(addr.substring(addr.length()-1).indexOf("/")!=-1){
   				  		addInfo = addr.trim().substring(8,addr.length()-1);
   				  	}else{
   				  		addInfo = addr.trim().substring(8,addr.length());
   				  	}
   				}
   				//判断ip地址是否包含端口号
   				if(addInfo.indexOf(":")!=-1){
   					String addArr[] = addInfo.split(":");
   					ipflag = addArr[0].matches(IpAddressRegex);
   					if(ipflag==false){
   						flag=addArr[0].matches(hostnameRegex);
   					}
   				}else{
   					ipflag = addInfo.matches(IpAddressRegex);
   				}
   				if(ipflag==false){
                       //判断资产地址是否是域名
   				flag=addInfo.matches(hostnameRegex);
   				if(flag){
   					Asset  assetInfo = new Asset();
   					assetInfo.setAddr(asset.getAddr());
   					assetInfo.setId(asset.getId());
   					assetInfo.setName(asset.getName());
   					assetInfo.setIp(asset.getIp());
   					assetInfo.setStatus(asset.getStatus());
   					assList.add(assetInfo);
   				 }
   				}
   			}
   		}
   		Serv service = servService.findById(serviceId);
   		  //网站安全帮列表
           List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
           //查询安全能力API
   		   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
   		 int carnum=shopCarList.size()+apiList.size();
   		 request.setAttribute("carnum", carnum);  
   		request.setAttribute("assList", assList);
   		request.setAttribute("service", service);
   		request.setAttribute("serviceId", serviceId);
   		request.setAttribute("orderId", orderId);
   	    return  "/source/page/details/reNewWafDetails";
   	}
   	
    /**
   	 * 功能描述：续费判断ip地址是否与域名绑定的一致
   	 * 参数描述：  无
   	 * add gxy
   	 * @throws Exception 
   	 *     @time 2016-5-19
   	 */
   	@RequestMapping(value="VerificationRenewIP.html")
   	public void VerificationRenewIP(HttpServletRequest request,HttpServletResponse response) throws Exception{
   		Map<String, Object> m = new HashMap<String, Object>();
   		//ip地址
   		String ipStr = request.getParameter("ipVal");
   		
   		//域名
   		String domainName = request.getParameter("domainName");
   		String orderId = request.getParameter("orderId");
   	   List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
   	 HashMap<String, Object> order=new HashMap<String, Object>();
	    order=(HashMap) orderList.get(0);
   		String domainId =  request.getParameter("domainId");
   	   String errorIp ="";
   		String addInfo = "";
   		  boolean flag=false;
   		try{
   		//判断http协议
   		if(domainName.indexOf("http://")!=-1){
   		  	if(domainName.substring(domainName.length()-1).indexOf("/")!=-1){
   		  		addInfo = domainName.trim().substring(7,domainName.length()-1);
   		  	}else{
   		  		addInfo = domainName.trim().substring(7,domainName.length());
   		  	}
   		}else if(domainName.indexOf("https://")!=-1){
   			if(domainName.substring(domainName.length()-1).indexOf("/")!=-1){
   		  		addInfo = domainName.trim().substring(8,domainName.length()-1);
   		  	}else{
   		  		addInfo = domainName.trim().substring(8,domainName.length());
   		  	}
   		}
   	    List<String> IpInfo = new ArrayList();
   	    //根据域名找出域名绑定下得所有ip地址
   		InetAddress[] addresses=InetAddress.getAllByName(addInfo);
   		for(InetAddress addr:addresses)
   		{
   			IpInfo.add(addr.getHostAddress());
         }
           String array[]=IpInfo.toArray(new String[]{});
         
           String ipPortVal="";
           //页面输入的ip地址
           String ipArr[]= ipStr.split(",");
           for(int i=0;i<ipArr.length;i++){
           	if(ipArr[i].indexOf(":")!=-1){
           		String ipPort[] = ipArr[i].split(":");
           		ipPortVal=ipPort[0];
           	}else{
           		ipPortVal=ipArr[i];
           	}
         	  for(int k=0;k<array.length;k++){
         		    if(ipPortVal.equals(array[k])){
         		    
         		    	flag=true;
         		    }else{
         		      	flag=false;
         		    }
         	  }
         	  if(!flag){
         		errorIp+=ipArr[i]+",";
         	  }
           }
          
           
           if(flag){
          
           //添加购物车时
   		String serviceId = request.getParameter("serviceId");
   		if(serviceId!=null){
   			 SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss"); 
   			 Date date= new Date();
   			 String value1= sdf1.format(date);
   			 //类型
   			 String orderType = request.getParameter("orderType");
   			 //开始时间
   			 String beginDate =DateUtils.dateToString((Date)order.get("end_date"));
   			 //服务期限
   			 String month ="";
   			 Date cd= DateUtils.stringToDate(beginDate);
   			 //包月
   			 if(orderType.equals("8")){
   				month =  request.getParameter("month");
   			 }else{
   				 month="12";
   			 }
   			Date endDate = DateUtils.getDateAfterMonths(cd,Integer.parseInt(month));
   			String endVal =	DateUtils.dateToDate(endDate)+" "+value1;
   			String beginVal = beginDate+" "+value1;   
   			Date end = DateUtils.stringToDateNYRSFM(endVal);
   			Date begin = DateUtils.stringToDateNYRSFM(beginVal);
   			//获得当前日期后十分钟
   			Date afterEnd = DateUtils.getDateAfter10Mins(end);
   			Date afterBegin = DateUtils.getDateAfter10Mins(begin);
   		   

   	         m.put("serviceId", serviceId);
   	         m.put("orderType", orderType);
   	         m.put("domainName", domainName);
   	         m.put("ipStr", ipStr);
   	         m.put("month", month);
   	         m.put("domainId", domainId);
   	         m.put("orderId", orderId);
   		}
   	 	
           }   
           if(errorIp.length()>1){
           	  m.put("errorIp", errorIp.substring(0, errorIp.length()-1));
           }
        
   	}catch(Exception e){
   			e.printStackTrace();
   			flag=false;
   			 m.put("errorIp", "");
   		}
   		m.put("flag", flag);
   		   JSONObject JSON = CommonUtil.objectToJson(response, m);
   	       // 把数据返回到页面
              CommonUtil.writeToJsp(response, JSON);
   		
   	}
   	
   	
   	/**
	 * 功能描述：添加到购物车
	 * 参数描述：  无
	 * add gxy
	 *     @time 2016-5-19
	 */
	@RequestMapping(value="shoppingRenewWaf.html")
	public void shoppingRenewWaf(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> m = new HashMap<String, Object>();
		  try{
				boolean ipflag=false;
				  boolean flag=false;
				  String addInfo = "";
				  double countPrice =0.0;
					String orderId = request.getParameter("orderId");
				   	   List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
				   	 HashMap<String, Object> orderInfo=new HashMap<String, Object>();
				   	orderInfo=(HashMap) orderList.get(0);
				  List<String> IpInfo = new ArrayList();
				  int linkmanId = Random.eightcode();
				  //根据ip地址加端口号
			        String ipPortstr ="";
				  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
			  User globle_user = (User) request.getSession().getAttribute("globle_user");
				//资产ids
		       String serviceId = request.getParameter("serviceId");
				String orderType = request.getParameter("orderType");
		        String beginDate = DateUtils.dateToString((Date)orderInfo.get("end_date"));
		        String createDate = DateUtils.dateToString(new Date());
		        String month = request.getParameter("month");
		       
		        /*****判断参数开始***/
		        if(serviceId==null||"".equals(serviceId)||orderType==null||"".equals(orderType)||beginDate==null||"".equals(beginDate)||month==null||"".equals(month)){
		        	
		        	   m.put("error",true);
		          	  JSONObject JSON = CommonUtil.objectToJson(response, m);
		   				try {
		   					// 把数据返回到页面
		   					CommonUtil.writeToJsp(response, JSON);
		   				} catch (IOException e) {
		   					e.printStackTrace();
		   				}
		   				return;
		        }
		        if(!serviceId.equals("6")){
		        	  m.put("error",true);
		          	  JSONObject JSON = CommonUtil.objectToJson(response, m);
		   				try {
		   					// 把数据返回到页面
		   					CommonUtil.writeToJsp(response, JSON);
		   				} catch (IOException e) {
		   					e.printStackTrace();
		   				}
		   				return;
		        }
		        if(!orderType.equals("8")&&!orderType.equals("9")){
		        	  m.put("error",true);
		          	  JSONObject JSON = CommonUtil.objectToJson(response, m);
		   				try {
		   					// 把数据返回到页面
		   					CommonUtil.writeToJsp(response, JSON);
		   				} catch (IOException e) {
		   					e.printStackTrace();
		   				}
		   				return;
		        }
		       
			        Serv service = servService.findById(Integer.parseInt(serviceId));
				    if(service==null){
				    	 m.put("error",true);
			          	  JSONObject JSON = CommonUtil.objectToJson(response, m);
			   				try {
			   					// 把数据返回到页面
			   					CommonUtil.writeToJsp(response, JSON);
			   				} catch (IOException e) {
			   					e.printStackTrace();
			   				}
			   				return;
				    }
				    Date bDate=DateUtils.stringToDateNYRSFM(beginDate);
				    Date eDate=null;
				    if(orderType.equals("9")){//包年
				    	countPrice =9000;
				    	eDate = DateUtils.getDateAfterOneYear(bDate);
				     
				    }else{//包月
				    	countPrice = 880*Integer.parseInt(month);
				    	eDate = DateUtils.getDateAfterMonths(bDate, Integer.parseInt(month));
				    
				    }
				 selfHelpOrderService.updateRenewOrder(2,0,(Date)orderInfo.get("end_date"),eDate,DateUtils.stringToDateNYRSFM(beginDate),new Date(),countPrice,orderId);
				 //网站安全帮列表
			            List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
			         //查询安全能力API
			  		 List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
			  		 int carnum=shopCarList.size()+apiList.size();
			  		 request.setAttribute("carnum", carnum);
			  		 
			  		   m.put("sucess", true);
			  		   m.put("orderId", orderId);
			  		   m.put("serviceId", serviceId);
			  		   JSONObject JSON = CommonUtil.objectToJson(response, m);
			  	        // 把数据返回到页面
			          CommonUtil.writeToJsp(response, JSON);
		  }catch(Exception e){
			  e.printStackTrace();
			  m.put("sucess", false);
			  
		  }
	}

	 /**
		 * 功能描述： 续约跳立即支付頁
		 * 参数描述：  无
		 * @throws ParseException 
		 *     @time 2016-3-10
		 */
		@RequestMapping(value="buyRenewWafUI.html")
		public String buyRenewWafUI(HttpServletRequest request) throws Exception{
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			List assetIdsList = new ArrayList();
		      String orderId = request.getParameter("orderId");
		      String orderListId = request.getParameter("orderListId");
		      List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
			   	HashMap<String, Object> orderInfo=new HashMap<String, Object>();
			   	orderInfo=(HashMap) orderList.get(0);
			   	List<HashMap<String, Object>> orderAsset=orderAssetService.findAssetsByOrderId(orderId) ;
				 HashMap<String, Object> orderAssetInfo=new HashMap<String, Object>();
				 orderAssetInfo=(HashMap) orderAsset.get(0);
				 String createDate = DateUtils.dateToString(new Date());
				//根据id查询service
				    Serv service = servService.findById(Integer.parseInt(String.valueOf(orderInfo.get("serviceId"))));
				  //日期格式 yyyy-MM
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
		    OrderDetail orderDetail = new OrderDetail();
			String beginDate = dateFormat.format((Date)orderInfo.get("begin_date"));
			String  endDate =  dateFormat.format((Date)orderInfo.get("end_date"));
	 	      String orderDate = odf.format(new Date());
	 	    String detailId = orderDate+String.valueOf(Random.fivecode());
	 	    int timeswaf = DateUtils.getMonthSpace(DateUtils.stringToDateNYRSFM(beginDate), DateUtils.stringToDateNYRSFM(endDate));
	 	   //格式化价格
		    DecimalFormat df = new DecimalFormat("#.00");

		    assetIdsList.add(orderAssetInfo.get("assetId").toString());
	 	   orderDetail.setId(detailId);
	       orderDetail.setBegin_date(DateUtils.stringToDateNYRSFM(beginDate));
	       orderDetail.setEnd_date(DateUtils.stringToDateNYRSFM(endDate));
	       orderDetail.setType((Integer)orderInfo.get("scan_type"));
	       orderDetail.setServiceId((Integer)orderInfo.get("serviceId"));
	       orderDetail.setUserId(globle_user.getId());
	      // orderDetail.setIsAPI(0);
	       orderDetail.setAsstId(orderAssetInfo.get("assetId").toString());
	       orderDetail.setIpArray(orderAssetInfo.get("ipArray").toString());
	       orderDetail.setPrice((Double)orderInfo.get("price"));
	       orderDetail.setCreate_date(dateFormat.parse(createDate));
	       orderDetail.setWafTimes(timeswaf);
		  //  selfHelpOrderService.updateBuyRenewOrder(0,0,(Date)orderInfo.get("end_date"),eDate,DateUtils.stringToDateNYRSFM(beginDate),countPrice,orderId,scanType);
	       selfHelpOrderService.SaveOrderDetail(orderDetail);
	   	  OrderDetail orderDetailVo = selfHelpOrderService.getOrderDetailById(detailId, globle_user.getId(),assetIdsList);
		 
	        request.setAttribute("user", globle_user);
	        request.setAttribute("orderId", orderId);
	        request.setAttribute("service", service);
		    request.setAttribute("orderDetail", orderDetailVo);
		    request.setAttribute("orderInfo", orderInfo);
		    request.setAttribute("orderListId", orderListId);
	        String result = "/source/page/details/reNewsettlement";
	        return result;
		}
		 /**
		 * 功能描述： 续约跳立即支付頁
		 * 参数描述：  无
		 * @throws ParseException 
		 *     @time 2016-3-10
		 */
		@RequestMapping(value="changeOrderInfo.html")
		@ResponseBody
		public void changeOrderInfo(HttpServletResponse response,HttpServletRequest request) {
			  Map<String, Object> m = new HashMap<String, Object>();
			  try{
			String orderDate="";
			  String orderId = request.getParameter("orderId");
			  String month=request.getParameter("month");
		
		      List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
			   	HashMap<String, Object> orderInfo=new HashMap<String, Object>();
			   	orderInfo=(HashMap) orderList.get(0);
				Date newDate = DateUtils.getDateAfterMonths((Date)orderInfo.get("end_date"), Integer.parseInt(month));
				orderDate = DateUtils.dateToString((Date)orderInfo.get("end_date"))+"~"+ DateUtils.dateToString(newDate);
				  m.put("orderDate", orderDate);
				  m.put("beginDate", DateUtils.dateToString((Date)orderInfo.get("end_date")));
				  m.put("endDate",  DateUtils.dateToString(newDate));
				   JSONObject JSON = CommonUtil.objectToJson(response, m);
		  	        // 把数据返回到页面
		          CommonUtil.writeToJsp(response, JSON);
			  }catch(Exception e){
				  e.printStackTrace();
			  }
		}
		
		
		/**
	     * 功能描述： 保存续费Waf订单
	     * 参数描述：  
		 * @throws Exception 
	     *       @time 2015-01-16
	     */
	    @RequestMapping(value="saveRenewWafOrder.html")
	    @ResponseBody
	    public void saveRenewWafOrder(HttpServletResponse response,HttpServletRequest request) throws Exception{
	    	
	    	String id ="";
	       try{
	    	  Map<String, Object> m = new HashMap<String, Object>();
	    	User globle_user = (User) request.getSession().getAttribute("globle_user");
	    	List assetIdsList = new ArrayList();
	    	String orderId = request.getParameter("orderId");
	    	  List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
			   	 HashMap<String, Object> orderInfo=new HashMap<String, Object>();
			   	orderInfo=(HashMap) orderList.get(0);
	    	String createDate = DateUtils.dateToString(new Date());

	    	 String linkname =request.getParameter("linkname");
	         String phone = request.getParameter("phone");
	         String email = request.getParameter("email");
	    	//判断参数值
	     
	      	String orderDetailId = request.getParameter("orderDetailId");
	      	if(orderDetailId==null||"".equals(orderDetailId)||linkname==null||"".equals(linkname)){
	      		m.put("error", true);
	          		 //object转化为Json格式
	          	       JSONObject JSON = CommonUtil.objectToJson(response, m);
	          	       try {
	          	           // 把数据返回到页面
	          	           CommonUtil.writeToJsp(response, JSON);
	          	       } catch (IOException e) {
	          	           e.printStackTrace();
	          	       }
	          	       return;
	          
	      	}
	      	 /***判断参数开始**/
	        if(phone!=null&&!"".equals(phone)){
	        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");  
	         Matcher matcher = p.matcher(phone); 
		       if(!matcher.find()){
		    		m.put("error", true);
		      		 //object转化为Json格式
		      	       JSONObject JSON = CommonUtil.objectToJson(response, m);
		      	       try {
		      	           // 把数据返回到页面
		      	           CommonUtil.writeToJsp(response, JSON);
		      	       } catch (IOException e) {
		      	           e.printStackTrace();
		      	       }
		      	       return;
		       }
	    	
	        }
	       if(email!=null&&!"".equals(email)){
	    	   Pattern emailP = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");  
	           Matcher ematcher = emailP.matcher(email); 
	           if(!ematcher.find()){
	        	   m.put("error", true);
	        		 //object转化为Json格式
	        	       JSONObject JSON = CommonUtil.objectToJson(response, m);
	        	       try {
	        	           // 把数据返回到页面
	        	           CommonUtil.writeToJsp(response, JSON);
	        	       } catch (IOException e) {
	        	           e.printStackTrace();
	        	       }
	        	       return;
	           }
	       }
	      
	     /***判断参数结束**/
	    	OrderDetail orderDetailVo =selfHelpOrderService.findOrderDetailById(orderDetailId, globle_user.getId());
	    	if(orderDetailVo==null){
	      		m.put("error", true);
	     		 //object转化为Json格式
	     	       JSONObject JSON = CommonUtil.objectToJson(response, m);
	     	       try {
	     	           // 把数据返回到页面
	     	           CommonUtil.writeToJsp(response, JSON);
	     	       } catch (IOException e) {
	     	           e.printStackTrace();
	     	       }
	     	       return;
	      	}
	      	boolean assetsStatus = false;
			Asset _asset = assetService.findById(Integer.parseInt(orderDetailVo.getAsstId()),globle_user.getId());
			if(_asset==null){
				m.put("error", true);
	     		 //object转化为Json格式
	     	       JSONObject JSON = CommonUtil.objectToJson(response, m);
	     	       try {
	     	           // 把数据返回到页面
	     	           CommonUtil.writeToJsp(response, JSON);
	     	       } catch (IOException e) {
	     	           e.printStackTrace();
	     	       }
	     	       return;
			}
			assetIdsList.add(orderDetailVo.getAsstId());
			OrderDetail orderDetail =selfHelpOrderService.getOrderDetailById(orderDetailId, globle_user.getId(), assetIdsList);
	    	if(orderDetail==null){
	    		m.put("error", true);
	   		 //object转化为Json格式
	   	       JSONObject JSON = CommonUtil.objectToJson(response, m);
	   	       try {
	   	           // 把数据返回到页面
	   	           CommonUtil.writeToJsp(response, JSON);
	   	       } catch (IOException e) {
	   	           e.printStackTrace();
	   	       }
	   	       return;
	    	}
	    	 //日期格式 yyyy-MM
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	 String beginDate = DateUtils.dateToString((Date)orderInfo.get("end_date"));
	    	 String  scanType =String.valueOf(orderDetail.getScan_type());
	    	 String ipArray = orderDetail.getIpArray();
	    	 int timeswaf =orderDetail.getWafTimes();
	    	 double countPrice =orderDetail.getPrice();
	    	 Date now = new Date();
			    String nowDate = dateFormat.format(now);
	    	 beginDate = beginDate + nowDate.substring(10);
			    Date bDate=DateUtils.stringToDateNYRSFM(beginDate);
			    //得到开始时间10分钟后
			    bDate = DateUtils.getDateAfter10Mins(bDate);
			    Date eDate = new Date();
			   
			    if(scanType.equals("9")){//包年
			    	countPrice =9000;
			    	eDate = DateUtils.getDateAfterOneYear(bDate);
			     
			    }else{//包月
			     countPrice = 880*timeswaf;
			    	eDate = DateUtils.getDateAfterMonths(bDate, timeswaf);
			    
			    }
			//新增订单

			//生成订单id，当前日期加5位随机数
			SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
			String orderDate = odf.format(new Date());
	     //   String orderId = orderDate+String.valueOf(Random.fivecode());
	      //新增联系人
	        Linkman linkObj = new Linkman();
	        int linkmanId = Random.eightcode();
	        linkObj.setId(linkmanId);
	        linkObj.setName(linkname);
	        linkObj.setMobile(phone);
	        linkObj.setEmail(email);
	        linkObj.setUserId(globle_user.getId());
	        selfHelpOrderService.insertLinkman(linkObj);
	        
		
			selfHelpOrderService.updateBuyRenewOrder(2,0, (Date)orderInfo.get("end_date"), eDate, DateUtils.stringToDateNYRSFM(beginDate), countPrice, orderId, scanType);
			//新增服务资产
			OrderAsset orderAsset = new OrderAsset();
			orderAsset.setOrderId(orderId);
			orderAsset.setAssetId(Integer.parseInt(orderDetail.getAsstId()));
			orderAsset.setServiceId(orderDetail.getServiceId());
			orderAsset.setScan_type(orderDetail.getType());
			    if(String.valueOf(orderDetail.getType()).equals("8")){//包月
			    	orderAsset.setSermonth(orderDetail.getWafTimes());
			    }else{
			    	orderAsset.setSermonth(12);
			    }
			orderAsset.setIpArray(orderDetail.getIpArray());
			orderAsset.setAssetAddr(_asset.getAddr());
	        orderAsset.setAssetName(_asset.getName());
			orderAssetService.update(orderAsset);
			  //插入数据到order_list
		    OrderList ol = new OrderList();
		    //生成订单id
			id = orderDate+String.valueOf(Random.fivecode());
		    ol.setId(id);
		    ol.setCreate_date(new Date());
		    ol.setOrderId(orderId);
		    ol.setUserId(globle_user.getId());
		    ol.setPrice(orderDetail.getPrice());
		    ol.setServerName(orderDetail.getServiceName());
		    orderListService.update(ol);
		    
			m.put("orderStatus", true);
			m.put("orderListId", id);
			//object转化为Json格式
			//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
				    // 把数据返回到页面
				    CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
				    e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
}
