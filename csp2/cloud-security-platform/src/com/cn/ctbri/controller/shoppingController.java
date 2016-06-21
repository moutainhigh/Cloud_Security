package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.common.HuaweiWorker;
import com.cn.ctbri.common.NorthAPIWorker;
import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceAPI;
import com.cn.ctbri.entity.ShopCar;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAPIService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderListService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IPriceService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.IServiceAPIService;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Random;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 创 建 人  ：  tang
 * 创建日期：  2016-3-10
 * 描        述：  订单管理控制层
 * 版        本：  1.0
 */
@Controller
public class shoppingController {
	
    @Autowired
    ISelfHelpOrderService selfHelpOrderService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderAssetService orderAssetService;
    @Autowired
    IAssetService assetService;
    @Autowired
    IServService servService;
    @Autowired
    ITaskService taskService;
    @Autowired
    ITaskHWService taskhwService;
    @Autowired
    IAlarmService alarmService;
    @Autowired
    ITaskWarnService taskWarnService;
    @Autowired
    IServiceAPIService serviceAPIService;
    @Autowired
    IOrderAPIService orderAPIService;
    @Autowired
    IPriceService priceService;
    @Autowired
    IUserService userService;
    @Autowired
    IOrderListService orderListService;
    
    private static String SERVER_WEB_ROOT;
    private static String VulnScan_servicePrice;
    
	static{
		try {
			Properties p = new Properties();
			p.load(HuaweiWorker.class.getClassLoader().getResourceAsStream("InternalAPI.properties"));
			
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			VulnScan_servicePrice = p.getProperty("VulnScan_servicePrice");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	 /**
	 * 功能描述： 购买检测服务
	 * 参数描述：  无
	 *     @time 2016-3-12
	 */
	@RequestMapping(value="selfHelpOrderInit.html")
	public String selfHelpOrderInit(HttpServletRequest request){
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    int serviceId = Integer.parseInt(request.getParameter("serviceId"));
	    //是否从首页进入
	    String indexPage = request.getParameter("indexPage");
	    //根据id查询service add by tangxr 2016-3-14
	    Serv service = servService.findById(serviceId);
	    //获取服务对象资产
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
	    //网站安全帮列表
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //查询安全能力API
		   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		 int carnum=shopCarList.size()+apiList.size();
		 request.setAttribute("carnum", carnum);  
        request.setAttribute("serviceAssetList", serviceAssetList);
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("indexPage", indexPage);
        request.setAttribute("service", service);
        if(serviceAssetList!=null && serviceAssetList.size()>0){
            request.setAttribute("AssetCount", serviceAssetList.size());
        }else{
        	request.setAttribute("AssetCount",0);
        }
//        request.setAttribute("orderType", "");
        String result = "/source/page/details/vulnScanDetails";
        return result;
	}
	
	 /**
	 * 功能描述： 结算
	 * 参数描述：  无
	 *     @time 2016-3-10
	 */
	@RequestMapping(value="settlement.html")
	public String settlement(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		//资产ids
        String assetIds = request.getParameter("assetIds");
		String type = request.getParameter("type");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
//       String createDate = DateUtils.dateToString(new Date());
        String scanPeriod = request.getParameter("scanType");
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        String times = request.getParameter("buy_times");
        String price = request.getParameter("price");
        String priceVal="";
      // priceVal =  price.substring(price.indexOf("¥")+1,price.length()) ;
        String[]  assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
      //  double priceD = Double.parseDouble(priceVal);
       
        //根据id查询service
	    Serv service = servService.findById(serviceId);
	    String assetAddr = "";

        for(int i=0;i<assetArray.length;i++){
        	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]));
        	assetAddr = assetAddr + asset.getAddr()+",";
        }
//        assetAddr = assetAddr.substring(0, assetAddr.length()-1);
	    
        request.setAttribute("user", globle_user);
	    request.setAttribute("assetAddr", assetAddr);
	    request.setAttribute("assetIds", assetIds);
	    request.setAttribute("type", type);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("scanType", scanPeriod);
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("service", service);
        request.setAttribute("mark", "web");//web服务标记
        
        DecimalFormat df = new DecimalFormat("0.00");
        request.setAttribute("allPrice", df.format(Double.parseDouble(price)));
        
        String result = "/source/page/details/settlement";
        return result;
	}
	
	
	/**
	 * 功能描述： 添加购物车
	 * 参数描述：  无
	 * @throws Exception 
	 *      add by gxy 2016-5-03
	 */
	@RequestMapping(value="shoppingCar.html")
	public void shoppingCar(HttpServletRequest request,HttpServletResponse response){
		  Map<String, Object> m = new HashMap<String, Object>();
		  try{
		 //用户
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		  Date begin_date = null;
          Date end_date = null;
		//资产ids
        String assetIds = request.getParameter("assetIds");
		String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String scanPeriod = request.getParameter("scanType");
        String serviceId = request.getParameter("serviceId");
        String createDate = DateUtils.dateToString(new Date());
        String price = request.getParameter("price");
        String times = request.getParameter("buy_times");
        String priceVal="";
        priceVal =  price.substring(price.indexOf("¥")+1,price.length()) ;
        Order order = new Order();
        
        SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
		String orderDate = odf.format(new Date());
		  int linkmanId = Random.eightcode();
        String orderId = String.valueOf(Random.fivecode())+orderDate;
        Date  create_date=sdf.parse(createDate); 
        order.setId(orderId);
        order.setType(Integer.parseInt(orderType));
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
        if (serviceId.equals("1") && orderType.equals("1")) {//漏洞长期
			Date executeTime = DateUtils.getOrderPeriods(beginDate,endDate,scanPeriod);
			order.setTask_date(executeTime);
		}else{
			order.setTask_date(begin_date);
		}
        if(scanPeriod!=null && !scanPeriod.equals("")){
            order.setScan_type(Integer.parseInt(scanPeriod));
        }
        
	    String[]  assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
        order.setUserId(globle_user.getId());
        double priceD = Double.parseDouble(priceVal);
        /*if(Integer.parseInt(times)!=0){
        	if(Integer.parseInt(serviceId)!=5){
        		priceD = Integer.parseInt(times)*assetArray.length*Double.parseDouble(priceVal);
        	}else{
            	priceD = assetArray.length*Double.parseDouble(priceVal);
        	}
        }else{
        	priceD = assetArray.length*Double.parseDouble(priceVal);
        }*/
        DecimalFormat df = new DecimalFormat("0.00");
        String temp = df.format(priceD);
        order.setPrice(Double.parseDouble(temp));
        order.setContactId(linkmanId);
        order.setPayFlag(0);//未支付
        selfHelpOrderService.insertOrder(order);

        for(int i=0;i<assetArray.length;i++){
        	//插入订单资产表
            OrderAsset orderAsset = new OrderAsset();
            orderAsset.setOrderId(orderId);
            orderAsset.setAssetId(Integer.parseInt(assetArray[i]));
            orderAsset.setServiceId(Integer.parseInt(serviceId));
            if(scanPeriod!=null && !scanPeriod.equals("")){
                orderAsset.setScan_type(Integer.parseInt(scanPeriod));
            }
           
            orderAssetService.insertOrderAsset(orderAsset);
        }
        
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
		 int carnum=shopCarList.size()+apiList.hashCode();
		 request.setAttribute("carnum", carnum);
		 
		   m.put("sucess", true);
		   JSONObject JSON = CommonUtil.objectToJson(response, m);
	        // 把数据返回到页面
        CommonUtil.writeToJsp(response, JSON);
		 //object转化为Json格式
	       
		  }catch(Exception e){
			  e.printStackTrace();
			  m.put("sucess", false);
			  
		  }
		
	}
	
	
	
	/**
	 * 功能描述： 查看购物车
	 * 参数描述：  无
	 * @throws Exception 
	 *      add by gxy 2016-5-05
	 */
	@RequestMapping(value="showShopCar.html")
	public String showShopCar(HttpServletRequest request) throws Exception{
		boolean flag=false;
		 //用户
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	//查询网站安全帮列表
		 List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
		 //查询安全能力API
		 List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		 if((shopCarList!=null&&shopCarList.size()>0)||(apiList!=null&&apiList.size()>0)){
			 flag=true;
		 }
        request.setAttribute("shopCarList", shopCarList);
        request.setAttribute("apiList", apiList);
        request.setAttribute("flag", flag);
        request.setAttribute("user", globle_user);
	     String result = "/source/page/details/shoppingCart-order";
		return result;
	}

	/**
     * 功能描述： 删除购物车
     * 参数描述： 
	 * @throws Exception 
	 *  add  gxy
     *       @time 2016-05-5
     */
    @RequestMapping(value="delShopCar.html")
    @ResponseBody
    public void saveOrderAPI(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	  Map<String, Object> m = new HashMap<String, Object>();
        //获得订单id
        String orderId = request.getParameter("orderId");
        //删除联系人
        orderService.delLinkmanByOrderId(orderId);
        //删除订单资产
        orderAssetService.deleteOaByOrderId(orderId);
        //删除订单api
        orderAPIService.deleteOrderAPI(orderId);
         //删除订单
        orderService.deleteOrderById(orderId);
     
      //object转化为Json格式
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	 /**
	 * 功能描述： 购物车结算
	 * 参数描述：  无
	 *     @time 2016-05-07
	 *     add gxy
	 */
	@RequestMapping(value="shopBuy.html")
	public String shopBuy(HttpServletRequest request){
		 User globle_user = (User) request.getSession().getAttribute("globle_user");

		String str = request.getParameter("str");
		
		List list = new ArrayList();
		List shopAPIList = new ArrayList();
		List<ShopCar> shopList = new ArrayList();
		int orderNum=0;
		List<String> orderIdList=new ArrayList();
		Linkman linkman = new Linkman();
	      if(str!=null&&!"".equals(str)){
	    	  String strArray[] = str.substring(0, str.length()-1).split(",");
	    	  orderNum= strArray.length;
	    	  for (int m=0;m<strArray.length;m++){
	    		  orderIdList.add(strArray[m]);
	    	  }
	    	  list = selfHelpOrderService.findBuyShopList(orderIdList);
	    	 linkman = orderService.findLinkmanByOrderId(strArray[0]);
			}
	      DecimalFormat df = new DecimalFormat("0.00");
	      double shopCount=0.0;
	     if(list!=null&&list.size()>0){
	       for(int i=0;i<list.size();i++){
	    	   ShopCar shopCar = (ShopCar)list.get(i);
	    	   if(shopCar.getIsAPI()==0||shopCar.getIsAPI()==2){
	    		   shopList.add(shopCar);
	    	   }else{
	    		   
	    		   shopAPIList.add(shopCar);
	    	   }
	    	   shopCount=shopCount+shopCar.getPrice();
	       }	 
	     }
	     
	  
	    request.setAttribute("linkman", linkman);
		request.setAttribute("orderNum", orderNum);
		request.setAttribute("shopCount", df.format(shopCount));
		request.setAttribute("shopList", shopList);
		request.setAttribute("shopAPIList", shopAPIList);
		 request.setAttribute("user", globle_user);
		//判断显示结算页的按钮
		request.setAttribute("shop", 0);
		 String result = "/source/page/details/newSettlement";
	        return result;
	}
	 /**
	 * 功能描述： 购物车提交订单
	 * 参数描述：  无
	 *     @time 2016-05-07
	 *     add gxy
	 */
	@RequestMapping(value="shopSettlement.html")
	public void shopSettlement(HttpServletResponse response,HttpServletRequest request){
		 Map<String, Object> map = new HashMap<String, Object>();  
		 String id ="";
		 String orderVal="";
		 String serverNames="";
	try{
		 User globle_user = (User) request.getSession().getAttribute("globle_user");
		//前台传回的用户
	    	int userId = Integer.parseInt(request.getParameter("userId"));
	    	//判断用户是否为当前用户
	    	boolean userStatus = true;
	    	if(userId != globle_user.getId()){
	    		userStatus = false;
	    	}
	    	map.put("userStatus", userStatus);
	    	if(!userStatus){
	    		 //object转化为Json格式
	    	       JSONObject JSON = CommonUtil.objectToJson(response, map);
	    	       try {
	    	           // 把数据返回到页面
	    	           CommonUtil.writeToJsp(response, JSON);
	    	       } catch (IOException e) {
	    	           e.printStackTrace();
	    	       }
	    	       return;
	    	}
	    	
		 Date date = new Date();
		 boolean flag=true;
		 String status="";
		String str = request.getParameter("orderIds");
		   DecimalFormat df = new DecimalFormat("0.00");
		   //联系人名称
		   String linkName = request.getParameter("linkName");
		   //联系人电话
		   String linkMoblie = request.getParameter("linkMobile");
		   //联系人邮箱
		   String linkEmail = request.getParameter("linkEmail");
		   Linkman linkman = new Linkman();
		   linkman.setName(linkName);
		   linkman.setMobile(linkMoblie);
		   linkman.setEmail(linkEmail);
		//总价格
		String price = request.getParameter("countPrice");
	    String scanType = "";//扫描方式（正常、快速、全量）
        String scanDepth = "";//检测深度
        String maxPages = "";//最大页面数
        String stategy = "";//策略
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		List list = new ArrayList();
		List shopAPIList = new ArrayList();
		List<ShopCar> shopList = new ArrayList();
		int orderNum=0;
		List<String> orderIdList=new ArrayList();
	      if(str!=null&&!"".equals(str)){
	    	  String strArray[] = str.substring(0, str.length()-1).split(",");
	    	  orderNum= strArray.length;
	    	  for (int m=0;m<strArray.length;m++){
	    		  orderIdList.add(strArray[m]);
	    	  }
	    	  list = selfHelpOrderService.findBuyShopList(orderIdList);
			}
	      double shopCount=0.0;
	     if(list!=null&&list.size()>0){
	       for(int i=0;i<list.size();i++){
	    	   ShopCar shopCar = (ShopCar)list.get(i);
	    	   if(shopCar.getIsAPI()==0){
	    		   shopList.add(shopCar);
	    	   }else{
	    		   
	    		   shopAPIList.add(shopCar);
	    	   }
	    	 
	       }	 
	     }
	    
	   //更新网站安全帮订单id
	    
	     if(shopList!=null&&shopList.size()>0){
	    	 for(int i=0;i<shopList.size();i++){
	    		 ShopCar shopCar = (ShopCar)shopList.get(i);
	    		 String targetURL[]=shopCar.getAstName().split(",");
	    		 String websoc = "0";
	    		 String[] customManu = null;//指定厂家
    	         if(websoc != null && websoc != ""){
    	        	customManu = websoc.split(","); //拆分字符为"," ,然后把结果交给数组customManu 
    	         }
	    	     Date  beginDate = shopCar.getBeginDate();
	    	     Date endDate = shopCar.getEndDate();
	    	     String begin_date=null;
	    	     String end_date="";
	    	     if(endDate!=null && !endDate.equals("")){
	    	     if(date.getTime()>endDate.getTime()){
	    	    	 flag=false;
	    	    	 status="-1";
	    	     }else{
	    	    	 status = String.valueOf(shopCar.getStatus());
	    	     }
	    	     }
	    	     if(beginDate!=null && !beginDate.equals("")){
	    	    	 begin_date = sdf.format(beginDate);
	    	     }
	    	     if(endDate!=null && !endDate.equals("")){
	    	    	 end_date = sdf.format(endDate);
	    	     }
	    	     String orderId = "";
	    	     orderVal = orderVal+ shopCar.getOrderId()+",";
	    	     serverNames = serverNames+shopCar.getServerName()+",";
	    	    /* try{
	    	    	 if(shopCar.getServiceId()!=6){
		    	    
	    	    	orderId = NorthAPIWorker.vulnScanCreate(String.valueOf(shopCar.getOrderType()), targetURL, scanType,begin_date,end_date, String.valueOf(shopCar.getScanPeriod()),
		            			scanDepth, maxPages, stategy, customManu, String.valueOf(shopCar.getServiceId()));
	    	    	SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
	    	    	 String orderDate = odf.format(new Date());
	    	    	   orderId = orderDate+String.valueOf(Random.fivecode());
	    	    	 orderVal = orderVal+ orderId+",";
		    	     }else{
		    	    	 SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
		     			 String orderDate = odf.format(new Date());
		     	         orderId = orderDate+String.valueOf(Random.fivecode());
		    	       }
	    	    	 } catch (Exception e) {
	  				e.printStackTrace();
	  			 }
	    	   //北向API返回orderId，创建用户订单
	         	if(!orderId.equals("") && orderId != null){
		    	     // String orderId="1";
		    		 //更新订单资产表
		    		 selfHelpOrderService.updateOrderAsset(shopCar.getOrderId(), orderId);
		    		 //更新订单表
		    		 selfHelpOrderService.updateOrder(shopCar.getOrderId(), orderId, "0",status);
		    		 orderService.updateLinkManByOrderId(linkman, orderId);
		    		 map.put("flag", flag);
		    		 map.put("price", df.format(Double.parseDouble(price)));
		    		 map.put("orderStatus", true);
			    	 map.put("sucess", true);
	         	}else{
	         		 map.put("orderStatus", true);
			    	 map.put("sucess", false);
			    	 map.put("flag", flag);
	         	}*/
	    		
	    	     orderService.updateLinkManByOrderId(linkman, shopCar.getOrderId());
	    	     map.put("flag", flag);
	    		 map.put("price", df.format(Double.parseDouble(price)));
	    		 map.put("orderStatus", true);
		    	 map.put("sucess", true);
	    	 }
	    	 
	     }
			// 更新api订单表
			if (shopAPIList != null && shopAPIList.size() > 0) {
				for (int i = 0; i < shopAPIList.size(); i++) {
					ShopCar shopCar = (ShopCar) shopAPIList.get(i);
					String targetURL[] = null;
					String websoc = "0";
					String[] customManu = null;// 指定厂家
					if (websoc != null && websoc != "") {
						customManu = websoc.split(","); // 拆分字符为","
														// ,然后把结果交给数组customManu
					}
					Date beginDate = shopCar.getBeginDate();
					Date endDate = shopCar.getEndDate();
					if(date.getTime()>endDate.getTime()){
						 flag=false;
		    	    	 status="-1";
					}else{
						status = String.valueOf(shopCar.getStatus());
					} 
					  orderVal = orderVal+ shopCar.getOrderId()+",";
					  serverNames = serverNames+shopCar.getServerName()+",";
				/*	try {
					if(shopCar.getServiceId()!=6){
						orderId = NorthAPIWorker.vulnScanCreateAPI(
								Integer.parseInt(shopCar.getAstName()),
								shopCar.getNum(), shopCar.getServiceId(),
								globle_user.getApikey(), globle_user.getId());
						 
						  SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
			    	    	 String orderDate = odf.format(new Date());
			    	    	   orderId = orderDate+String.valueOf(Random.fivecode());
			    	    	   orderVal = orderVal+ orderId+",";
						 }
					} catch (Exception e) {
						e.printStackTrace();
					}*/
					// String orderId="2";
					/*if (orderId != null && !"".equals(orderId)) {
						// 更新订单资产表
						selfHelpOrderService.updateOrderAPI(
								shopCar.getOrderId(), orderId);
						// 更新订单表
						selfHelpOrderService.updateOrder(shopCar.getOrderId(),
								orderId, "1",status);
						 orderService.updateLinkManByAPIId(linkman, orderId);
						map.put("orderStatus", true);
						map.put("sucess", true);
						 map.put("flag", flag);
			    		 map.put("price", df.format(Double.parseDouble(price)));
					} else {
						map.put("orderStatus", true);
						map.put("sucess", true);
						map.put("flag", flag);
					}*/
					    orderService.updateLinkManByAPIId(linkman, shopCar.getOrderId());
						map.put("orderStatus", true);
						map.put("sucess", true);
						 map.put("flag", flag);
			    		 map.put("price", df.format(Double.parseDouble(price)));
				}

			}
			
			  //插入数据到order_list
		    OrderList ol = new OrderList();
		    //生成订单id
		    id = String.valueOf(Random.eightcode());
		    ol.setId(id);
		    ol.setCreate_date(new Date());
		    ol.setUserId(globle_user.getId());
		    ol.setOrderId(orderVal.substring(0,orderVal.length()-1));
		    ol.setPrice(Double.parseDouble(price));
		    ol.setServerName(serverNames.substring(0, serverNames.length()-1));
		    orderListService.insert(ol);
	   
	}catch(Exception e){
		e.printStackTrace();
		map.put("orderStatus", false);
    	map.put("sucess", false);
	}

    	//object转化为Json格式
	   map.put("orderListId", id);
       JSONObject JSON = CommonUtil.objectToJson(response, map);
        // 把数据返回到页面
           try {
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 功能描述：返回修改订单
	 * 参数描述：  无
	 * @throws Exception 
	 *      add by gxy 2016-5-10
	 */
	@RequestMapping(value="orderBack.html")
	public String  orderBack(HttpServletResponse response,HttpServletRequest request) throws Exception{
		 Map<String, Object> map = new HashMap<String, Object>();
		User globle_user = (User) request.getSession().getAttribute("globle_user");
	    boolean apiFlag=false;
		String result="";
		
		//资产ids
        String assetIds = request.getParameter("assetIds");
		String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String scanType = request.getParameter("scanType");
        String type = request.getParameter("type");
        String apiVal=request.getParameter("apiId");
        String price = request.getParameter("price");
        String serviceId = request.getParameter("serviceId");
        String num=request.getParameter("num");
        
		// assetAddr = assetAddr.substring(0, assetAddr.length()-1);
		if (apiVal != null && !"".equals(apiVal)) {
			int apiId = Integer.parseInt(apiVal);
			// 根据id查询serviceAPI, add by tangxr 2016-3-28
			ServiceAPI serviceAPI = serviceAPIService.findById(apiId);
			request.setAttribute("apiId", apiId);
			request.setAttribute("serviceAPI", serviceAPI);
			if (apiId == 1) {
				result = "/source/page/details/apiDetails";
			} else if (apiId == 2) {
				result = "/source/page/details/apiDetails2";
			} else if (apiId == 3) {
				result = "/source/page/details/apiDetails3";
			} else if (apiId == 4) {
				result = "/source/page/details/apiDetails4";
			} else if (apiId == 5) {
				result = "/source/page/details/apiDetails5";
			}
		}
        String[] assetArray = null;
	    String assetAddr = "";
	    if(assetIds.length()>0){
	        if(serviceId!=null&&!"".equals(serviceId)){
	            assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
            	for(int i=0;i<assetArray.length;i++){
                	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]));
                	assetAddr = assetAddr + asset.getAddr()+",";
                }
	        	//根据id查询service add by tangxr 2016-3-14
	    	    Serv service = servService.findById(Integer.parseInt(serviceId));
	    	    request.setAttribute("service", service);
	        	result = "/source/page/details/vulnScanDetails";	
	        }
	    }
        //获取服务对象资产
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
	    //网站安全帮列表
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //查询安全能力API
		List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		int carnum=shopCarList.size()+apiList.size();
		request.setAttribute("carnum", carnum);  
        request.setAttribute("assetAddr", assetAddr);
	    request.setAttribute("assetIds", assetIds);
	    request.setAttribute("orderType", orderType);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("scanType", scanType);
        request.setAttribute("type", type);
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("serviceAssetList", serviceAssetList);
        request.setAttribute("num", num);
        request.setAttribute("price", price);
        return result;
	}
	
	
	/**
     * 功能描述： 计算商品价格
     * 参数描述： 
	 * @throws Exception 
	 *  add  pengdm
     *       @time 2016-05-12
     */
    @RequestMapping(value="calPrice.html")
    @ResponseBody
    public void calPrice(HttpServletResponse response,HttpServletRequest request) throws Exception{
		Map<String, Object> m = new HashMap<String, Object>();
		//价格
		double calPrice = 0;
        //计算出的次数
        long times = 0;
    	try {
			//获得订单id
			int serviceId = Integer.parseInt(request.getParameter("serviceId"));
			String type = request.getParameter("type");
			String beginDate = request.getParameter("beginDate");
			String endDate = request.getParameter("endDate");
			int assetCount = Integer.parseInt(request.getParameter("assetCount"));
			String str;
			try {
				//远程调用接口
				ClientConfig config = new DefaultClientConfig();
				//检查安全传输协议设置
				Client client = Client.create(config);
				//连接服务器
				String url = SERVER_WEB_ROOT + VulnScan_servicePrice;
				WebResource service = client.resource(url+serviceId);
				ClientResponse clientResponse = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);        
				//System.out.println(clientResponse.toString());
				str = clientResponse.getEntity(String.class);
				//System.out.println(str);
				
				//解析json,进行数据同步
				JSONObject jsonObject = JSONObject.fromObject(str);			
				JSONArray jsonArray = jsonObject.getJSONArray("PriceStr");
		        if(jsonArray!=null && jsonArray.size()>0){
			        //删除数据
			        priceService.delPrice(serviceId);
				    for (int i = 0; i < jsonArray.size(); i++) {
				        String object = jsonArray.getString(i);
				        JSONObject jsonObject1 = JSONObject.fromObject(object);
				        int idJson = Integer.parseInt(jsonObject1.getString("id"));
				        int serviceIdJson = Integer.parseInt(jsonObject1.getString("serviceId"));
				        int typeJson = Integer.parseInt(jsonObject1.getString("type"));
				        double priceJson = Double.parseDouble(jsonObject1.getString("price"));
				        int timesGJson = Integer.parseInt(jsonObject1.getString("timesG"));
				        int timesLEJson = Integer.parseInt(jsonObject1.getString("timesLE"));
				       
				        Price newprice = new Price();
				        newprice.setServiceId(serviceIdJson);
				        newprice.setType(typeJson);
				        newprice.setTimesG(timesGJson);
				        newprice.setTimesLE(timesLEJson);
				        newprice.setPrice(priceJson);
				        
				        priceService.insertPrice(newprice);
				    }
		        }
		        
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.out.println("链接服务器失败!");
			}     
			

	        
		
			//进行价格分析
	        //根据serviceid查询价格列表
			List<Price> priceList = priceService.findPriceByServiceId(serviceId);
	        //长期
	        if(type!=null){	
	        	long ms = 0;//时间之间的毫秒数
	        	Date bDate = null;
	        	Date eDate = null;
	        	if(beginDate!=null&&beginDate!=""&endDate!=null&&endDate!=""){
	        		bDate = DateUtils.stringToDateNYRSFM(beginDate);
		            eDate = DateUtils.stringToDateNYRSFM(endDate);  
		            ms = DateUtils.getMsByDays(bDate, eDate);
	        	}
	            
	            int typeInt = Integer.parseInt(type);
	            
		        switch(serviceId){
		        case 1:
		        	if(ms==0){
		        		times = 1;//用于显示默认价格
		        	}else{
		        		//每周
			        	if(typeInt==2){
			        		int perWeek = 1000*3600*24*7;
			        		if(ms%perWeek>0){
			        			times = (long)(ms/perWeek)+1;
			        		}else{
			        			times = (long)(ms/perWeek);
			        		}		        		
			        	}else{//每月
			        		while(ms>0){
			        			bDate = DateUtils.getDayAfterMonth(bDate);
			        			ms = DateUtils.getMsByDays(bDate, eDate);
			        			times++;
			        		}
			        	}	
		        	}
		        	break;
		        	
		        case 2://30分钟
		        	if(ms==0){
		        		times = 1;//用于显示默认价格
		        	}else{
			        	int min_30 = 1000*3600/2;
			        	if(ms%min_30 > 0){
			        		times = (long)(ms/min_30) + 1;
			        	}else{
			        		times = (long)(ms/min_30);
			        	}
		        	}

		        	break;
		        	
		        case 3://一天
		        case 4:
		        	if(ms==0){
		        		times = 1;//用于显示默认价格
		        	}else{
			        	int oneDay = 1000*3600*24;
			        	if(ms%oneDay > 0){
			        		times = (long)(ms/oneDay) + 1;
			        	}else{
				        	times = (long)(ms/oneDay);
			        	}
		        	}
		        	break;
		        	
		        case 5:
		        	if(ms==0){
		        		times = 1;//用于显示默认价格
		        	}else{
		        		if(typeInt==3){//一小时
				        	int oneHour = 1000*3600;
				        	if(ms%oneHour > 0){
				        		times = (long)(ms/oneHour) + 1;
				        	}else{
				        		times = (long)(ms/oneHour);
				        	}
			        	}else{//2小时
			        		int twoHour = 1000*3600*2;
			        		if(ms%twoHour > 0){
			        			times = (long)(ms/twoHour) + 1;
			        		}else{
			        			times = (long)(ms/twoHour);
			        		}
			        	}
		        	}		        	
		        	break;
		        }
		        if(priceList!=null && priceList.size()>0){
				    for (int i = 0; i < priceList.size(); i++) {
				    	Price onePrice = priceList.get(i);
				        if(onePrice.getTimesG()!=0 && onePrice.getTimesLE()!=0){//区间
				        	if(times>onePrice.getTimesG()&&times<=onePrice.getTimesLE()){
				    			if(serviceId!=5){
				    				calPrice = onePrice.getPrice()*times*assetCount;
				    			}else{
				    				calPrice = onePrice.getPrice()*assetCount;
				    			}
				    			break;
				    		}
				        	if(serviceId==5 && times==1 && onePrice.getTimesG()==1){//服务5：特殊，times==1，取第二区间
				        		calPrice = onePrice.getPrice()*assetCount;
				    			break;
				        	}
				        }else if(onePrice.getTimesG()!=0 && onePrice.getTimesLE()==0 && times>onePrice.getTimesG()){//超过
			        		if(serviceId!=5){
			        			calPrice = onePrice.getPrice()*times*assetCount;
			    			}else{
			    				calPrice = onePrice.getPrice()*assetCount;
			    			}
			        		break;
				        }else if(onePrice.getTimesG()==0 && onePrice.getTimesLE()==0 && times <= 1){//单次类
				        	calPrice = onePrice.getPrice()*assetCount;
			        		break;
				        }

				    }
		        }else{
		        	calPrice = 0;
		        }
	        }else{//单次
	        	times = 1;
	        	if(priceList!=null && priceList.size()>0){
				    for (int i = 0; i < priceList.size(); i++) {
				    	Price onePrice = priceList.get(i);
				    	if(serviceId!=5){
				    		 if(onePrice.getTimesG()==0 && onePrice.getTimesLE()==0){
					        		//单次
						        	calPrice = onePrice.getPrice()*assetCount;
						        	break;
					         }
				    	}else{//服务5没有单次价格
				    		if(onePrice.getTimesG()==1){
				        		//单次
					        	calPrice = onePrice.getPrice()*assetCount;
					        	break;
				         }
				    	}
				       
				    }
	        	}else{
	        		calPrice = 0;
	        	}
			}

			DecimalFormat df = new DecimalFormat("0.00"); 

			m.put("success", true);
			m.put("price", df.format(calPrice));
			m.put("times", times);
      
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
	 /**
	 * 功能描述： 购物车去结算判断当前时间是否超过下单开始时间
	 * 参数描述：  无
	 *     @time 2016-05-23
	 *     add gxy
	 */
	@RequestMapping(value="checkShoppOrder.html")
	public void checkOrderInfo(HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
		
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		//前台传回的用户
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

	try{
		Date date = new Date();
		 boolean flag = true;
		String str = request.getParameter("str");
		
		List list = new ArrayList();
		int orderNum=0;
		List<String> orderIdList=new ArrayList();
	      if(str!=null&&!"".equals(str)){
	    	  String strArray[] = str.substring(0, str.length()-1).split(",");
	    	  orderNum= strArray.length;
	    	  for (int k=0;k<strArray.length;k++){
	    		  orderIdList.add(strArray[k]);
	    	  }
	    	  list = selfHelpOrderService.findBuyShopList(orderIdList);
			}
	     if(list!=null&&list.size()>0){
	       for(int i=0;i<list.size();i++){
	    	   ShopCar shopCar = (ShopCar)list.get(i);
	    	  Date endDate = shopCar.getEndDate();
	    	  if(endDate!=null&&!"".equals(endDate)){
		    		
		    	  //判断当前时间已经查过下单时间
		    	  if(date.getTime()>endDate.getTime()){
		    		  flag=false;
		    		  break;
		    	  }
	    	  
	    	  }
	       }	 
	     }
	     //修改订单状态已作废
	     if(!flag){
	    	  if(list!=null&&list.size()>0){
	   	       for(int i=0;i<list.size();i++){
	   	    	   ShopCar shopCar = (ShopCar)list.get(i);
	   	    	  shopCar.setStatus(-1);
	   	    	 selfHelpOrderService.updateShopOrder(shopCar);
	   	       }	 
	   	     } 
	     }
	   //object转化为Json格式
	         m.put("flag", flag);
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	
		
	}
    
    /**
     * 功能描述： 收银台页面
     * */
    @RequestMapping("cashierUI.html")
    public String cashier(Model model, HttpServletRequest request){
    	String orderListId = request.getParameter("orderListId");//订单编号(cs_order_list的id)
    	//获取orderId,购买时间,交易金额
    	OrderList orderList = orderListService.findById(orderListId);
    	
    	//不是当前用户的订单,收银台不显示订单信息
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	if (orderList== null || orderList.getUserId()!= globle_user.getId()) {
    		return "redirect:/index.html";
    	}
    	
    	//交易金额保留两位小数
    	DecimalFormat df = new DecimalFormat("0.00");
    	String priceStr = df.format(orderList.getPrice());
    	
    	//根据订单编号获取服务名称
    	String[] serviceName = orderList.getServerName().split(",");
    	Map<String,Integer> serverNameMap = new HashMap<String,Integer>();
    	for (String name: serviceName){
    		if(serverNameMap.containsKey(name)){
    			int count = serverNameMap.get(name) +1;
    			serverNameMap.put(name, count);
    		}else {
    			serverNameMap.put(name, 1);
    		}
    		
    	}
//		List<String> nameList = orderService.findServiceNameByOrderId(orderList.getOrderId());

    	//安全币余额
		List<User> userList = userService.findUserById(orderList.getUserId());
        String balance = df.format(userList.get(0).getBalance());
    	
        model.addAttribute("orderList", orderList);
        model.addAttribute("price", priceStr);
        model.addAttribute("serverName", serverNameMap);
        model.addAttribute("balance",balance);
    	return "source/page/details/shoppingcashier-desk2";
    }
    
    /**
     * 功能描述： 确认支付
     * */
    @RequestMapping("payConfirm.html")
    public void payConfirm(HttpServletRequest request, HttpServletResponse response){
    	Map<String, Object> m = new HashMap<String, Object>();
    	try {
    		String orderListId = request.getParameter("orderListId");//订单编号(cs_order_list的id)
    		OrderList orderList = orderListService.findById(orderListId);
    		
    		//不是当前用户的订单,不能支付
        	User globle_user = (User) request.getSession().getAttribute("globle_user");
        	if (orderList.getUserId()!= globle_user.getId()) {
        		m.put("payFlag", 2);
    			return;
        	}
        	
    		Double price = orderList.getPrice();//支付金额
    		//收银台页面刷新，再次支付
    		if (orderList.getPay_date() != null){
    			//不能重复支付
    			m.put("payFlag", 1);
    			return;
    		}
    		
//    		Date orderCreateDate = orderList.getCreate_date();//下单时间
    		
    		//取得安全币余额
//    		User globle_user = (User) request.getSession().getAttribute("globle_user");
    		List<User> userList = userService.findUserById(globle_user.getId());
    		Double balance = userList.get(0).getBalance();
    		//安全币余额不足
    		if (balance < price){
    			//安全币余额不足
    			m.put("payFlag", 3);
    			return;
    		}
    		
    		//南向API认证，连接失败时订单异常，不支付
//    		boolean session = false;
//	    	try {
//	    		session = NorthAPIWorker.getNorthSession();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if (!true){
//				//连接服务管理系统失败
//				m.put("payFlag", 4);
//    			return;
//			}
    		//若支付时间>服务的开始时间，更新订单的开始时间，结束时间
    		List<String> orderIdOfModify = modifyOrderBeginTime(orderList);
    		
    		// 南向API调用 任务执行
    		if(!orderTask(orderList, globle_user, orderIdOfModify)) {
    			m.put("payFlag", 4);
    			return;
    		}
    		
    		//更新安全币余额（DB和session中都更新）
    		globle_user.setBalance(balance - price);//session更新
    		User user = new User();
    		user.setId(globle_user.getId());
    		user.setBalance(globle_user.getBalance());
    		userService.updateBalance(user);//DB更新
    		
    		//更新 支付时间，支付金额(cs_order_list表)
    		orderList.setPay_date(new Date());
    		selfHelpOrderService.updatePayDate(orderList);
    		
    		//更新 支付Flag(cs_order表) 未支付-->已支付
    		String orderIds = orderList.getOrderId();//订单条目编号(cs_order的id)
    		selfHelpOrderService.updateOrderPayFlag(orderIds, 1, 1);
    		
//    		//若支付时间>服务的开始时间，更新订单的开始时间，结束时间
//    		List<String> orderIdOfModify = modifyOrderBeginTime(orderList);
//    		
//    		// 南向API调用 任务执行
//    		if(!orderTask(orderList, globle_user, orderIdOfModify)) {
//    			m.put("payFlag", 4);
//    			return;
//    		}
    		
    		String orderId = "";
    		if (orderIdOfModify!= null && orderIdOfModify.size()!=0){
    			orderId = orderIdOfModify.toString();
    			orderId = orderId.substring(1,orderId.length()-1);
    		}
    		m.put("modifyOrderId", orderId);
    		m.put("payFlag", 0);//付款成功
    	} catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		//object转化为Json格式
    		JSONObject JSON = CommonUtil.objectToJson(response, m);
    		try {
    			// 把数据返回到页面
    			CommonUtil.writeToJsp(response, JSON);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    /**
     * 功能描述：若支付时间>服务的开始时间，更新订单的开始时间，结束时间
     * @return  需要修改的订单编号(逗号分割)
     * */
    public List<String> modifyOrderBeginTime(OrderList orderList) {
    	List<String> orderIdOfModify = new ArrayList<String>();
    	
    	List list = new ArrayList();
		List shopAPIList = new ArrayList();
		List<ShopCar> shopList = new ArrayList();
    	int orderNum=0;
    	
    	List<String> orderIdList=new ArrayList();
		String orderIds = orderList.getOrderId();
		if(orderIds!=null&&!"".equals(orderIds)){
			String strArray[] = orderIds.split(",");
			orderNum= strArray.length;
			for (int m=0;m<strArray.length;m++){
				orderIdList.add(strArray[m]);
			}
			list = selfHelpOrderService.findBuyShopList(orderIdList);
		}
		
//		Date payDate = orderList.getPay_date();
		Date payDate = new Date();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				ShopCar shopCar = (ShopCar)list.get(i);
				Date beginDate = shopCar.getBeginDate();
				Date endDate = shopCar.getEndDate();
				if (beginDate.getTime() < payDate.getTime()){
					shopCar.setBeginDate(payDate);
					if (endDate != null){
						long endDateLong = endDate.getTime() + 
							(payDate.getTime() - beginDate.getTime());
						shopCar.setEndDate(new Date(endDateLong));
					}
					
					//更新order表 开始时间，结束时间
					selfHelpOrderService.updateOrderDate(shopCar);
					if (shopCar.getIsAPI()==1) {
						//更新order_api表 开始时间，结束时间
						selfHelpOrderService.updateOrderAPIDate(shopCar);
					}
					
					orderIdOfModify.add(shopCar.getOrderId());
				}
			}
		}
    	 return orderIdOfModify;
    }
    
    public boolean orderTask(OrderList orderList, User globle_user, List<String> modifyOrderId){
    	boolean result = true;
    	try{
    		Date date = new Date();
    		String status="";
    		String scanType = "";//扫描方式（正常、快速、全量）
    		String scanDepth = "";//检测深度
    		String maxPages = "";//最大页面数
    		String stategy = "";//策略
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
    		List list = new ArrayList();
    		List shopAPIList = new ArrayList();
    		List<ShopCar> shopList = new ArrayList();
    		int orderNum=0;
    		String orderVal="";
    		
    		List<String> orderIdList=new ArrayList();
    		String orderIds = orderList.getOrderId();
    		if(orderIds!=null&&!"".equals(orderIds)){
    			String strArray[] = orderIds.split(",");
    			orderNum= strArray.length;
    			for (int m=0;m<strArray.length;m++){
    				orderIdList.add(strArray[m]);
    			}
    			list = selfHelpOrderService.findBuyShopList(orderIdList);
    		}
    		
    		if(list!=null&&list.size()>0){
    			for(int i=0;i<list.size();i++){
    				ShopCar shopCar = (ShopCar)list.get(i);
    				if(shopCar.getIsAPI()==0 || shopCar.getIsAPI()==2){
    					shopList.add(shopCar);
    				}else{
    					shopAPIList.add(shopCar);
    				}
    			}	 
    		}
    		
    		//更新网站安全帮订单id
    		
    		if(shopList!=null&&shopList.size()>0){
    			for(int i=0;i<shopList.size();i++){
    				ShopCar shopCar = (ShopCar)shopList.get(i);
    				String targetURL[]=shopCar.getAstName().split(",");
    				String websoc = "0";
    				String[] customManu = null;//指定厂家
    				if(websoc != null && websoc != ""){
    					customManu = websoc.split(","); //拆分字符为"," ,然后把结果交给数组customManu 
    				}
    				Date  beginDate = shopCar.getBeginDate();
    				Date endDate = shopCar.getEndDate();
    				String begin_date=null;
    				String end_date="";
    				
    				status = String.valueOf(shopCar.getStatus());
    				
    				if(beginDate!=null && !beginDate.equals("")){
    					begin_date = sdf.format(beginDate);
    				}
    				if(endDate!=null && !endDate.equals("")){
    					end_date = sdf.format(endDate);
    				}
    				String orderId = "";
    				try{
    					if(shopCar.getServiceId()!=6){
    						
    						orderId = NorthAPIWorker.vulnScanCreate(String.valueOf(shopCar.getOrderType()), targetURL, scanType,begin_date,end_date, String.valueOf(shopCar.getScanPeriod()),
    								scanDepth, maxPages, stategy, customManu, String.valueOf(shopCar.getServiceId()), globle_user.getApikey());
//    						SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
//    						String orderDate = odf.format(new Date());
//    						orderId = orderDate+String.valueOf(Random.fivecode());
    						orderVal = orderVal+ orderId+",";
    					}else{
//    						SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
//    						String orderDate = odf.format(new Date());
//    						orderId = orderDate+String.valueOf(Random.fivecode());
    						orderId = shopCar.getOrderId();
    						orderVal = orderVal+ orderId+",";
    						
    						//创建waf虚拟站点,modify by tangxr 2016-6-13
    						List assets = orderAssetService.findAssetsByOrderId(orderId);
    						HashMap<String, Object> assetOrder = new HashMap<String, Object>();
				        	assetOrder=(HashMap) assets.get(0);
				        	int id = 0;
				        	String addr = "";
				        	String wafIp = "219.141.189.183";
				        	String wafPort = "";
    						JSONArray ser = new JSONArray();
    						if(assets != null && assets.size() > 0){
    							String ipArray=(String) assetOrder.get("ipArray");
    				        	id = (Integer) assetOrder.get("orderAssetId");
    				        	addr=(String) assetOrder.get("addr");
    				        	String[] addrs = addr.split(":");
    				        	if(addrs[0].length()==5){
    				        		addr = addr.substring(8);
    				        		wafPort = "443";
    				        	}else if(addrs[0].length()==4){
    				        		addr = addr.substring(7);
    				        		wafPort = "80";
    				        	}
    				        	String[] ips = null;   
    				            ips = ipArray.split(",");
    				            for (int n = 0; n < ips.length; n++) {
    				            	JSONObject jo = new JSONObject();
    	    						jo.put("ip", ips[n]);
    	    						jo.put("port", "80");
    	    						ser.add(jo);
    				            }
    				        }
    						String wafcreate = WafAPIWorker.createVirtualSiteInResource("10001", shopCar.getOrderId(), wafIp, wafPort, "nsfocus.cer", "0", addr, "*", "", ser);
    						String targetKey = "";
    				    	try {
    				    		JSONObject obj = JSONObject.fromObject(wafcreate);
    				    		targetKey = obj.getString("targetKey"); 
    				    		String sta = obj.getString("status");
    				    		if(sta.equals("success")){
    				    			OrderAsset oa = new OrderAsset();
        				    		oa.setId(id);
        				    		oa.setTargetKey(targetKey);
        				    		orderAssetService.update(oa);
        				    		
            						orderId = shopCar.getOrderId();
    				    		}
    				        } catch (Exception e) {
    				            e.printStackTrace();
    				        }
    						//end
    					}
    					
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				
    				//北向API返回orderId，创建用户订单
    				if(!orderId.equals("") && orderId != null){
    					// String orderId="1";
    					//更新订单资产表
    					selfHelpOrderService.updateOrderAsset(shopCar.getOrderId(), orderId);
    					//更新订单表
    					selfHelpOrderService.updateOrder(shopCar.getOrderId(), orderId, "0",status);
    					//更新 修改时间的订单Id
    					if (modifyOrderId.contains(shopCar.getOrderId())){
    						modifyOrderId.remove(shopCar.getOrderId());
    						modifyOrderId.add(orderId);
    					}
//	    	    	 orderService.updateLinkManByOrderId(linkman, orderId);
    				}else{
    					result=false;
    				}
    			}
    		}
    		
    		if (shopAPIList != null && shopAPIList.size() > 0) {
    			for (int i = 0; i < shopAPIList.size(); i++) {
    				ShopCar shopCar = (ShopCar) shopAPIList.get(i);
    				String targetURL[] = null;
    				String websoc = "0";
    				String[] customManu = null;// 指定厂家
    				if (websoc != null && websoc != "") {
    					customManu = websoc.split(","); // 拆分字符为","
    					// ,然后把结果交给数组customManu
    				}
    				Date beginDate = shopCar.getBeginDate();
    				Date endDate = shopCar.getEndDate();

    				status = String.valueOf(shopCar.getStatus());
    				String orderId = "";
//	    		 orderVal = orderVal+ shopCar.getOrderId()+",";
    				try {
    					if(shopCar.getServiceId()!=6){
    						orderId = NorthAPIWorker.vulnScanCreateAPI(
    								Integer.parseInt(shopCar.getAstName()),
    								shopCar.getNum(), shopCar.getServiceId(),
    								globle_user.getApikey(), globle_user.getId());
//    						SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
//    						String orderDate = odf.format(new Date());
//    						orderId = orderDate+String.valueOf(Random.fivecode());
    						orderVal = orderVal+ orderId+",";
    					}
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				// String orderId="2";
    				if (orderId != null && !"".equals(orderId)) {
    					// 更新订单资产表
    					selfHelpOrderService.updateOrderAPI(
    							shopCar.getOrderId(), orderId);
    					// 更新订单表
    					selfHelpOrderService.updateOrder(shopCar.getOrderId(),
    							orderId, "1",status);
    					//更新 修改时间的订单Id
    					if (modifyOrderId.contains(shopCar.getOrderId())){
    						modifyOrderId.remove(shopCar.getOrderId());
    						modifyOrderId.add(orderId);
    					}
    				} else {
    					result = false;
    				}
    			}
    			
    		}
    		
    		//更新orderList表中的orderId
    		if (orderVal != null&& !orderVal.equals("")) {
    			orderList.setOrderId(orderVal.substring(0,orderVal.length()-1));
    			orderListService.update(orderList);
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		result = false;
    	}
	     
	     return result;
    }
    
    /**
     * 功能描述： 支付成功页面
     * */
    @RequestMapping("repayUI.html")
    public String toRepayUI(Model m,HttpServletRequest request, HttpServletResponse response){
    	String orderListId = request.getParameter("orderListId");//订单编号(cs_order_list的id)
		OrderList orderList = orderListService.findById(orderListId);
		
		//不是当前用户的订单,不能支付
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	if (orderListId== null || orderList.getUserId()!= globle_user.getId()) {
    		return "redirect:/index.html";
    	}
    	
		//获取修改时间的订单编号
		String modifyOrderIdList = request.getParameter("modifyOrderId");
		if (modifyOrderIdList != null && !modifyOrderIdList.equals("")){
			String modifyOrderId[] = modifyOrderIdList.split(",");
			m.addAttribute("modifyOrderId", modifyOrderId);
		}
		
		if (orderList.getPay_date()== null){
			m.addAttribute("paySuccess", 1);
		} else {
			m.addAttribute("paySuccess", 0);
			m.addAttribute("orderList", orderList);
			
			Double price = orderList.getPrice();//支付金额
	    	DecimalFormat df = new DecimalFormat("0.00");
	    	String priceStr = df.format(price);
	    	m.addAttribute("price", priceStr);
			
		}
    	return "/source/page/details/repay";
    	
    }
    
    
	/**
	 * 功能描述： 添加资产
	 * 参数描述： Model model
	 * @throws Exception 
	 *		 @time 2015-1-16
	 */
	@RequestMapping("/addWebSite.html")
	public void addWebSite(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";

		try {
			Asset asset = new Asset();
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			asset.setUserid(globle_user.getId());//用户ID
			asset.setCreate_date(new Date());//创建日期
			if(globle_user.getType()==2){
				asset.setStatus(0);//资产状态(1：已验证，0：未验证)
			}else if(globle_user.getType()==3){
				asset.setStatus(1);//资产状态(1：已验证，0：未验证)
			}

			String name = request.getParameter("assetName");//资产名称
			String addr = request.getParameter("assetAddr");//资产地址
			String addrType = request.getParameter("addrType");//资产类型
			String purpose = request.getParameter("purpose");//用途
			String prov = request.getParameter("prov");
			String city = request.getParameter("city");
			String wafFlag = request.getParameter("wafFlag");
						
			if(!(addr.startsWith(addrType))){
				addr = addrType + "://" + addr.trim();
			}
			
			//如果wafFlag!=null 只允许添加域名
			if(wafFlag!=null){
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
				boolean flag=addInfo.matches(hostnameRegex);
				if(!flag){
					m.put("success", false);
					m.put("wafFlag", false);
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
			
			
			asset.setName(name);
			asset.setAddr(addr);
			asset.setPurpose(purpose);
			asset.setDistrictId(prov);
			asset.setCity(city);
			assetService.saveAsset(asset);

			//获取服务对象资产
		    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
		    
		    if(wafFlag==null){
		    	m.put("serviceAssetList", serviceAssetList);
		    }else{//如果是waf，获取域名列表
				boolean flag=false;				
		    	List assList = new ArrayList();
				if(serviceAssetList!=null&&serviceAssetList.size()>0){
					for(int i=0;i<serviceAssetList.size();i++){
						Asset newAsset = (Asset)serviceAssetList.get(i);
						String newAddr = asset.getAddr();
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
							assList.add(newAsset);
						}
					}
				}
				m.put("serviceAssetList", assList);
		    }
			m.put("success", true);
			
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			m.put("success", false);
			m.put("wafFlag", true);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}

