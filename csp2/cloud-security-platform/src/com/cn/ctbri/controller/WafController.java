package com.cn.ctbri.controller;


import java.io.IOException;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderListService;
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
	    //是否从首页进入
	    String indexPage = request.getParameter("indexPage");
		//获取服务对象资产
	    List<Asset> list = selfHelpOrderService.findServiceAsset(globle_user.getId());
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
		Serv service = servService.findById(serviceId);
		  //网站安全帮列表
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //查询安全能力API
		   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		 int carnum=shopCarList.size()+apiList.size();
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
	   String errorIp ="";
		String addInfo = "";
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
        boolean flag=false;
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
      		      	errorIp=ipPortVal+",";
      		      	flag=false;
      		    }
      	  }
        }
       
        
        if(flag){
       
        //添加购物车时
		String serviceId = request.getParameter("serviceId");
		if(serviceId!=null){
			 SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss"); 
			 Date date= new Date();
			 String value1= sdf1.format(date);
			 //价格
			 String price = request.getParameter("price");
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
	         m.put("endDate", DateUtils.dateToString(afterEnd));
	         m.put("domainName", domainName);
	         m.put("ipStr", ipStr);
	         m.put("price", price);
	         m.put("month", month);
		}
	 	
        }   
        if(errorIp.length()>1){
        	  m.put("errorIp", errorIp.substring(0, errorIp.length()-1));
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
			  int linkmanId = Random.eightcode();
		//用户
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		  Date begin_date = null;
          Date end_date = null;
		//资产ids
       String serviceId = request.getParameter("serviceId");
		String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String createDate = DateUtils.dateToString(new Date());
        String price = request.getParameter("price");
        String domainName = request.getParameter("domainName");
        String ipStr = request.getParameter("ipStr");
        //根据ip地址加端口号
        String ipPortstr ="";
        if(ipStr!=null&&!"".equals(ipStr)){
        	if(ipStr.indexOf(",")!=-1){
        		String ipArray[] = ipStr.split(",");
        		for(int i =0;i<ipArray.length;i++){
        			String ipPort = ipArray[i];
        			if(ipPort.indexOf(":")==-1){
        				ipPort=ipPort+":80";
        				ipPortstr=ipPort+",";
        			}else{
        				ipPortstr = ipArray+",";
        			}
        		}
        	}else{
        		if(ipStr.indexOf(":")==-1){
        			ipPortstr = ipStr+":80"+",";
        		}else{
        			ipPortstr =ipStr+",";
        		}
        	}
        }
        String month = request.getParameter("month");
        String priceVal="";
        priceVal =  price.substring(price.indexOf("¥")+1,price.length()) ;
        Order order = new Order();
        
        SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
		String orderDate = odf.format(new Date());
        String orderId = String.valueOf(Random.fivecode())+orderDate;
        Date  create_date=sdf.parse(createDate); 
        order.setId(orderId);
       
        if(beginDate!=null && !beginDate.equals("")){
            begin_date=sdf.parse(beginDate); 
        }
        if(endDate!=null && !endDate.equals("")){
            end_date=sdf.parse(endDate); 
        }
       order.setBegin_date(begin_date);
        order.setEnd_date(end_date);
        order.setServiceId(Integer.parseInt(serviceId));
        order.setCreate_date(create_date);
        order.setTask_date(begin_date);
		order.setUserId(globle_user.getId());
		order.setPrice(Double.parseDouble(priceVal));
		order.setType(1);
		order.setScan_type(Integer.parseInt(orderType));
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
            orderAsset.setIpArray(ipPortstr.substring(0,ipPortstr.length()-1));
            orderAsset.setSermonth(Integer.parseInt(month));
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
		 int carnum=shopCarList.size()+apiList.size();
		 request.setAttribute("carnum", carnum);
		 
		   m.put("sucess", true);
		   m.put("serviceId", serviceId);
		   JSONObject JSON = CommonUtil.objectToJson(response, m);
	        // 把数据返回到页面
        CommonUtil.writeToJsp(response, JSON);
		 //object转化为Json格式
	       
		  }catch(Exception e){
			  m.put("sucess", false);
			  
		  }
	}

	 /**
	 * 功能描述： 跳立即支付頁
	 * 参数描述：  无
	 *     @time 2016-3-10
	 */
	@RequestMapping(value="buyNowWafUI.html")
	public String buyNowWafUI(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		//资产ids
        String domainName = request.getParameter("domainName");
        String domainId = request.getParameter("domainId");
		String type = request.getParameter("type");
        String beginDate = request.getParameter("beginDate");
        String scanType = request.getParameter("scanType");
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        double price = Double.parseDouble(request.getParameter("price"));
        int times = Integer.parseInt(request.getParameter("timeswaf"));
        String ipArray = request.getParameter("ipArray");
        //根据id查询service
	    Serv service = servService.findById(serviceId);
	    
	   
	    //日期格式 yyyy-MM
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date now = new Date();
	    String nowDate = dateFormat.format(now);
	    beginDate = beginDate + nowDate.substring(10);
	    Date bDate=DateUtils.stringToDateNYRSFM(beginDate);
	    //得到开始时间10分钟后
	    bDate = DateUtils.getDateAfter10Mins(bDate);
	    Date eDate = new Date();
	    //格式化价格
	    DecimalFormat df = new DecimalFormat("#.00");
	    
	    if(scanType.equals("9")){//包年
	    	eDate = DateUtils.getDateAfterOneYear(bDate);
	        request.setAttribute("allPrice", df.format(price));
	    }else{//包月
	    	eDate = DateUtils.getDateAfterMonths(bDate, times);
	        request.setAttribute("allPrice", df.format(price*times));
	    }
        
        request.setAttribute("user", globle_user);
	    request.setAttribute("assetAddr", domainName);
	    request.setAttribute("assetIds", domainId);
	    request.setAttribute("type", type);
        request.setAttribute("beginDate", dateFormat.format(bDate));
        request.setAttribute("endDate", dateFormat.format(eDate));
        request.setAttribute("scanType", scanType);
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("service", service);
        request.setAttribute("mark", "web");//web服务标记

        request.setAttribute("ipArray", ipArray);
        request.setAttribute("times", times);
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
        Map<String, Object> m = new HashMap<String, Object>();
        
        //用户
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	//前台传回的用户
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	//判断用户是否为当前用户
    	boolean userStatus = true;
    	if(userId != globle_user.getId()){
    		userStatus = false;
    	}
    	m.put("userStatus", userStatus);
    	if(!userStatus){
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

        String id ="";
        try {
			//后台判断资产是否可用，防止恶意篡改资产
			//资产id
			String domainId = request.getParameter("domainId");
			boolean assetsStatus = false;
			Asset _asset = assetService.findById(Integer.parseInt(domainId));
//			int status = _asset.getStatus();
//			if(status==0){//未验证
//			    assetsStatus = true;
//			}

			m.put("assetsStatus", assetsStatus);
//			if(assetsStatus){
//			    //object转化为Json格式
//			    JSONObject JSON = CommonUtil.objectToJson(response, m);
//			    try {
//			        // 把数据返回到页面
//			        CommonUtil.writeToJsp(response, JSON);
//			    } catch (IOException e) {
//			        e.printStackTrace();
//			    }
//			    return;
//			}
			
			//生成订单id，当前日期加5位随机数
			SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
			String orderDate = odf.format(new Date());
	        String orderId = orderDate+String.valueOf(Random.fivecode());

	        String scanType = request.getParameter("scanType");
			String beginDate = request.getParameter("beginDate");
			String endDate = request.getParameter("endDate");
			String createDate = DateUtils.dateToString(new Date());
			String serviceId = request.getParameter("serviceId");
			String ipArray = request.getParameter("ipArray");
			String times = request.getParameter("timeswaf");
			String price = request.getParameter("price");
			String serviceName = request.getParameter("serviceName");
     				
			//新增订单
			Order order = new Order();
			order.setId(orderId);
			order.setType(1);//长期
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
			Date begin_date = null;
			Date end_date = null;
			Date create_date = null;
			if(beginDate!=null && !beginDate.equals("")){
			    begin_date=sdf.parse(beginDate); 
			}
			if(endDate!=null && !endDate.equals("")){
			    end_date=sdf.parse(endDate); 
			}
			if(createDate!=null && !createDate.equals("")){
			    create_date=sdf.parse(createDate); 
			}
			order.setBegin_date(begin_date);
			order.setEnd_date(end_date);
			order.setCreate_date(create_date);
			if(scanType!=null && !scanType.equals("")){
			    order.setScan_type(Integer.parseInt(scanType));
			    order.setPayFlag(0);	
			   
			}
			order.setServiceId(Integer.parseInt(serviceId));
			order.setTask_date(begin_date);
			order.setIsAPI(2);
			order.setDelFlag(0);
			order.setPayFlag(0);
			order.setUserId(globle_user.getId());
			
			order.setStatus(0);
			order.setPrice(Double.parseDouble(price));
			selfHelpOrderService.insertOrder(order);
			
			
			//新增服务资产

			OrderAsset orderAsset = new OrderAsset();
			orderAsset.setOrderId(orderId);
			orderAsset.setAssetId(Integer.parseInt(domainId));
			orderAsset.setServiceId(Integer.parseInt(serviceId));
			if(scanType!=null && !scanType.equals("")){
			    orderAsset.setScan_type(Integer.parseInt(scanType));
			    if(scanType.equals("8")){//包月
			    	orderAsset.setSermonth(Integer.parseInt(times));
			    }else{
			    	orderAsset.setSermonth(12);
			    }
			}
			orderAsset.setIpArray(ipArray);
			orderAssetService.insertOrderAsset(orderAsset);
			
			  //插入数据到order_list
		    OrderList ol = new OrderList();
		    //生成订单id
//		     id = String.valueOf(Random.eightcode());
			id = orderDate+String.valueOf(Random.fivecode());
		    ol.setId(id);
		    ol.setCreate_date(new Date());
		    ol.setOrderId(orderId);
		    ol.setUserId(globle_user.getId());
		    ol.setPrice(Double.parseDouble(price));
		    ol.setServerName(serviceName);
		    orderListService.insert(ol);
		    
			m.put("orderStatus", true);
			m.put("orderListId", id);
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
		
		//查找当前用户的资产列表
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
		
        String domainId = request.getParameter("domainId");
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
}
