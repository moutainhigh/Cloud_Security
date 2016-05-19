package com.cn.ctbri.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.common.HuaweiWorker;
import com.cn.ctbri.common.NorthAPIWorker;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceAPI;
import com.cn.ctbri.entity.ShopCar;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAPIService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.IServiceAPIService;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
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
        String times = request.getParameter("times");
        String price = request.getParameter("price");
        String priceVal="";
        priceVal =  price.substring(price.indexOf("¥")+1,price.length()) ;
        String[]  assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
        double priceD = Integer.parseInt(times)*assetArray.length*Double.parseDouble(priceVal);
        DecimalFormat df = new DecimalFormat("#.00");

        //联系人信息
//       String linkname = new String(request.getParameter("linkname").getBytes("ISO-8859-1"),"UTF-8");
//       String phone = request.getParameter("phone");
//       String email = request.getParameter("email");
//       String company = new String(request.getParameter("company").getBytes("ISO-8859-1"),"UTF-8");
//       String address = new String(request.getParameter("address").getBytes("ISO-8859-1"),"UTF-8");
       //华为参数
//       String ip = request.getParameter("ip");
//       String bandwidth = request.getParameter("bandwidth");
       //厂商
//       String websoc = request.getParameter("websoc");
       //任务数
//       String tasknum = request.getParameter("tasknum");
       
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
        request.setAttribute("allPrice", df.format(priceD));
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
        String times = request.getParameter("times");
        String priceVal="";
        priceVal =  price.substring(price.indexOf("¥")+1,price.length()) ;
        Order order = new Order();
        
        SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
		String orderDate = odf.format(new Date());
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
        double priceD = Integer.parseInt(times)*assetArray.length*Double.parseDouble(priceVal);
        DecimalFormat df = new DecimalFormat("#.00");
        String temp = df.format(priceD);
        order.setPrice(Double.parseDouble(temp));
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
	    	   shopCount=shopCount+shopCar.getPrice();
	       }	 
	     }
	     
	     
	
		request.setAttribute("orderNum", orderNum);
		request.setAttribute("shopCount", shopCount);
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
	try{
		 User globle_user = (User) request.getSession().getAttribute("globle_user");
	
		String str = request.getParameter("orderIds");
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
	    	      if(beginDate!=null && !beginDate.equals("")){
	    	    	  begin_date = sdf.format(beginDate);
	    	      }
	    	      if(endDate!=null && !endDate.equals("")){
	    	    	  end_date = sdf.format(endDate);
	    	      }
	    	      String orderId = "";
	    	      try{
	    	    	  orderId = NorthAPIWorker.vulnScanCreate(String.valueOf(shopCar.getOrderType()), targetURL, scanType,begin_date,end_date, String.valueOf(shopCar.getScanPeriod()),
		            			scanDepth, maxPages, stategy, customManu, String.valueOf(shopCar.getServiceId()));
	    	      } catch (Exception e) {
	  				e.printStackTrace();
	  			}
	    	      
	    		
	    	   // String orderId="1";
	    		 //更新订单资产表
	    		 selfHelpOrderService.updateOrderAsset(shopCar.getOrderId(), orderId);
	    		 //更新订单表
	    		 selfHelpOrderService.updateOrder(shopCar.getOrderId(), orderId, "0");
	    	 }
	    	 map.put("orderStatus", true);
	    	 map.put("sucess", true);
	     }
	     //更新api订单表
	     if(shopAPIList!=null&&shopAPIList.size()>0){
	    	 for(int i=0;i<shopAPIList.size();i++){
	    		 ShopCar shopCar = (ShopCar)shopAPIList.get(i);
	    		 String targetURL[]=null;
	    		 String websoc = "0";
	    		 String[] customManu = null;//指定厂家
	    	        if(websoc != null && websoc != ""){
	    	        	customManu = websoc.split(","); //拆分字符为"," ,然后把结果交给数组customManu 
	    	        }
	    	      Date  beginDate = shopCar.getBeginDate();
	    	      Date endDate = shopCar.getEndDate();
	    		String orderId =NorthAPIWorker.vulnScanCreateAPI(Integer.parseInt(shopCar.getAstName()), shopCar.getNum(), shopCar.getServiceId(), globle_user.getApikey(), globle_user.getId());
	    	     // String orderId="2";
	    	      if(orderId!=null&&!"".equals(orderId)){
	    			  //更新订单资产表
	 	    	    
	 	    		 selfHelpOrderService.updateOrderAPI(shopCar.getOrderId(), orderId);
	 	    		 //更新订单表
	 	    		 selfHelpOrderService.updateOrder(shopCar.getOrderId(), orderId, "1");
	 	    		
	    		  }
	    		
	    	 }
	    	 map.put("orderStatus", true);
	    	 map.put("sucess", true);
	     }
	
	      
	}catch(Exception e){
		e.printStackTrace();
		 map.put("orderStatus", false);
    	 map.put("sucess", false);
	}

    //object转化为Json格式
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
        int times = 0;
    	try {
			//获得订单id
			int serviceId = Integer.parseInt(request.getParameter("serviceId"));
			String type = request.getParameter("type");
			String beginDate = request.getParameter("beginDate");
			String endDate = request.getParameter("endDate");
			
			//远程调用接口
			ClientConfig config = new DefaultClientConfig();
			//检查安全传输协议设置
			Client client = Client.create(config);
			//连接服务器
			String url = SERVER_WEB_ROOT + VulnScan_servicePrice;
			WebResource service = client.resource(url+serviceId);
			ClientResponse clientResponse = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);        
			System.out.println(clientResponse.toString());
			String str = clientResponse.getEntity(String.class);
			System.out.println(str);         
			//解析json
			JSONObject jsonObject = JSONObject.fromObject(str);			
			JSONArray jsonArray = jsonObject.getJSONArray("PriceStr");
			
		
			//进行价格分析
			
	        //长期
	        if(type!=null){	  
	            Date bDate=DateUtils.stringToDateNYRSFM(beginDate);
	            Date eDate=DateUtils.stringToDateNYRSFM(endDate);  
	            //时间之间的毫秒数
	            long ms = DateUtils.getMsByDays(bDate, eDate);
	            int typeInt = Integer.parseInt(type);
	            
		        switch(serviceId){
		        case 1:
		        	//每周
		        	if(typeInt==2){
		        		int perWeekHours = 1000*3600*24*7;
		        		times = (int)(ms/perWeekHours);
		        	}else{//每月
		        		int perMonthHours = 1000*3600*24*30;
		        		times = (int)(ms/perMonthHours);
		        	}
		        	break;
		        	
		        case 2://30分钟
		        	int min_30 = 1000*3600/2;
		        	times = (int)(ms/min_30);
		        	break;
		        	
		        case 3://一天
		        case 4:
		        	int oneDay = 1000*3600*24;
		        	times = (int)(ms/oneDay);
		        	break;
		        	
		        case 5:
		        	if(typeInt==3){//一小时
			        	int oneHour = 1000*3600;
			        	times = (int)(ms/oneHour);
		        	}else{//2小时
		        		int twoHour = 1000*3600*2;
			        	times = (int)(ms/twoHour);
		        	}
		        	break;
		        }
		        if(jsonArray!=null && jsonArray.size()>0){
				    for (int i = 0; i < jsonArray.size(); i++) {
				        String object = jsonArray.getString(i);
				        JSONObject jsonObject1 = JSONObject.fromObject(object);
				        double price = Double.parseDouble(jsonObject1.getString("Price"));
				        int timesG = 0;//大于
				        int timesLE = 0;//小于等于
				        if(!jsonObject1.getString("GTR").equals("0") && !jsonObject1.getString("ITE").equals("0")){
				        	timesG = Integer.parseInt(jsonObject1.getString("GTR"));
				        	timesLE = Integer.parseInt(jsonObject1.getString("ITE"));
				        	if((times>=timesG&&times<timesLE)||(times<1&&timesG==1)){
				    			calPrice = price;
				    			break;
				    		}
				        }else if(!jsonObject1.getString("GTR").equals("0") && jsonObject1.getString("ITE").equals("0")){
				        	timesG = Integer.parseInt(jsonObject1.getString("GTR"));
				        	if(times>timesG){
				        		calPrice = price;
				        		break;
				    		}
				        }

				    }
		        }	    		
	        }else{//单次
	        	times = 1;
	        	if(jsonArray!=null && jsonArray.size()>0){
				    for (int i = 0; i < jsonArray.size(); i++) {
				        String object = jsonArray.getString(i);
				        JSONObject jsonObject1 = JSONObject.fromObject(object);

				        double price = Double.parseDouble(jsonObject1.getString("Price"));
				        
				        if(jsonObject1.getString("GTR").equals("0") && jsonObject1.getString("ITE").equals("0")){
			        		//单次
				        	calPrice = price;
				        	break;
			        	}
				    }
	        	}
			}

			DecimalFormat df = new DecimalFormat("#.00");  
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
}
