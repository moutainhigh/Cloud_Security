package com.cn.ctbri.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.common.APIWorker;
import com.cn.ctbri.common.NorthAPIWorker;
import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.entity.ScanType;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceAPI;
import com.cn.ctbri.entity.ServiceDetail;
import com.cn.ctbri.entity.ShopCar;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAPIService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderListService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IPriceService;
import com.cn.ctbri.service.IScanTypeService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServDetailService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.IServiceAPIService;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Random;
import com.cn.ctbri.util.SMSUtils;

/**
 * 
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
    IServDetailService servDetailService;
    @Autowired
    IScanTypeService scanTypeService;
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
			p.load(APIWorker.class.getClassLoader().getResourceAsStream("InternalAPI.properties"));
			
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
	    //判断serviceId是否存在
	    List<Serv> serList = servService.findAllService();
	    boolean flag = false;
	    if(serList!=null && serList.size()>0){
	    	for(int i = 0; i < serList.size(); i++){
	    		if(serviceId==serList.get(i).getId()){
	    			flag = true;
	    		}
	    	}
	    }
	    if(!flag){
	    	return "redirect:/index.html";
	    }
	    //是否从首页进入
	    String indexPage = request.getParameter("indexPage");
	    //根据id查询service add by tangxr 2016-3-14
	    Serv service = servService.findById(serviceId);
	    //根据service Id查询服务详细信息
	    ServiceDetail servDetail = servDetailService.findByServId(serviceId);
	    if(servDetail == null){
	    	return "redirect:/index.html";
	    }
	    List pricelist = priceService.findPriceByServiceId(serviceId,0);
	    if (pricelist== null || pricelist.size() == 0){
	    	return "redirect:/index.html";
	    }
	    //
	    String[] detailImages = null;
	    if (servDetail != null) {
	    	String imageStr = servDetail.getDetailIcon();
	    	if(imageStr != null && !imageStr.equals("") && !imageStr.equals(";")){
	    		detailImages = imageStr.split(";");
	    	}
	    }
	    //查找服务频率
	    List<ScanType> scanTypeList = scanTypeService.findScanTypeById(serviceId);
	    //获取服务对象资产
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
	    //网站安全帮列表
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //查询安全能力API
		   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		// 查询系统安全帮
		   
		int carnum = shopCarList.size() + apiList.size();
		request.setAttribute("carnum", carnum);
        request.setAttribute("serviceAssetList", serviceAssetList);
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("indexPage", indexPage);
        request.setAttribute("service", service);
        request.setAttribute("servDetail", servDetail);
        request.setAttribute("detailImages", detailImages);
        request.setAttribute("scanTypeList", scanTypeList);
        if(serviceAssetList!=null && serviceAssetList.size()>0){
            request.setAttribute("AssetCount", serviceAssetList.size());
        }else{
        	request.setAttribute("AssetCount",0);
        }
        String result = "/source/page/details/vulnScanDetails";
        return result;
	}
	
	 /**
	 * 功能描述： 结算
	 * 参数描述：  无
	 *     @time 2016-3-10
	 */
	@RequestMapping(value="settlement.html",method=RequestMethod.POST)
	public String settlement(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		if(request.getParameterMap().size()==0){
			return "redirect:/index.html";
		}
		//资产ids
        String assetIds = request.getParameter("assetIds");
		String type = request.getParameter("type");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String scanPeriod = request.getParameter("scanType");
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        String price = request.getParameter("price");
        String[]  assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
       
        //根据id查询service
	    Serv service = servService.findById(serviceId);
	    if (service == null) {
	    	return "redirect:/index.html";
	    }
	    String assetAddr = "";
	    String assetNames = "";
        for(int i=0;i<assetArray.length;i++){
        	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]),globle_user.getId());
        	if(asset == null || asset.getUserid() != globle_user.getId()) {
        		return "redirect:/index.html";
        	}
        	assetAddr = assetAddr + asset.getAddr()+",";
        	assetNames = assetNames + asset.getName() + ",";
        }
        assetNames = assetNames.substring(0, assetNames.length()-1);
	    
        request.setAttribute("user", globle_user);
	    request.setAttribute("assetAddr", assetAddr);
	    request.setAttribute("assetIds", assetIds);
	    request.setAttribute("assetNames", assetNames);
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
	@RequestMapping(value="shoppingCar.html", method=RequestMethod.POST)
	public void shoppingCar(HttpServletRequest request,HttpServletResponse response){
		  Map<String, Object> m = new HashMap<String, Object>();
		  String assetArray[] = null;
		 List<Price> priceList = new ArrayList();
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
        
        /****判断参数是否有效开始*****/
        //判断参数值是否为空
        if((assetIds==null||"".equals(assetIds))||(orderType==null||"".equals(orderType))||(beginDate==null||"".equals(beginDate))||(serviceId==null||"".equals(serviceId))){
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
        
        //根据判断服务id是否存在
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
        
        //判断服务类型是否存在
        if(!checkOrderType(serviceId,orderType)){
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
        
        //单次
        if(orderType.equals("2")){
        	if(endDate!=null&&!"".equals(endDate)){
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
        	
        	//服务频率
        	if(scanPeriod!=null&&!"".equals(scanPeriod)){
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
        }
   	 //长期
       if(orderType.equals("1")){
    	   if(beginDate==null||"".equals(beginDate)||endDate==null || "".equals(endDate)){
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
	       //服务频率
	  	   if(scanPeriod==null || "".equals(scanPeriod)){
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
       }
  	//开始时间和结束时间参数比较
       if(beginDate!=null&&!"".equals(beginDate)&&endDate!=null&&!"".equals(endDate)){
    	   Date begin = DateUtils.stringToDateNYRSFM(beginDate);
           Date end = DateUtils.stringToDateNYRSFM(endDate);
           
           if(begin.getTime()>end.getTime()){
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
       }
       //长期
       if(orderType.equals("1")){
    	   //判断服务频率是否存在
    	   List<ScanType> scanTypeList = scanTypeService.findScanType(Integer.parseInt(serviceId), Integer.parseInt(scanPeriod));
    	   if(scanTypeList.size() <= 0){
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
       }
        /****判断参数是否有效结束*****/
        /****计算价格开始*****/ 
        double calPrice = 0;
        //计算出的次数
        long stimes = 0;
          //获得订单id
			int serviceIdV = Integer.parseInt(serviceId);
			if(assetIds!=null&&!"".equals(assetIds)){
				assetArray=assetIds.split(",");
			}
			int assetCount = assetArray.length;
			if(assetCount<=0){
				assetCount=1;
			}
			//进行价格分析
			if (orderType!= null && orderType.equals("1")){  //长期
				
				int scanType = Integer.valueOf(scanPeriod); 			//服务频率
				stimes = calTimes(scanType, beginDate, endDate);	//计算服务需要执行的总次数
				calPrice = calPrice(serviceIdV,scanType,stimes,assetCount);//计算价格
				
			} else {	//单次
				priceList = priceService.findPriceByServiceId(serviceIdV,0);
				if (priceList != null && priceList.size() != 0){
					//priceList按序排列，取第一个元素判断是不是单次价格
					Price price = priceList.get(0);  
					if (price.getType() == 0){
						calPrice = price.getPrice() * assetCount;
					}
				}
			}

			DecimalFormat df = new DecimalFormat("0.00"); 
          String  price = df.format(calPrice);
        /****计算价格结束*****/ 
        
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
        

        order.setUserId(globle_user.getId());
       
        /*if(Integer.parseInt(times)!=0){
        	if(Integer.parseInt(serviceId)!=5){
        		priceD = Integer.parseInt(times)*assetArray.length*Double.parseDouble(priceVal);
        	}else{
            	priceD = assetArray.length*Double.parseDouble(priceVal);
        	}
        }else{
        	priceD = assetArray.length*Double.parseDouble(priceVal);
        }*/
      
        order.setPrice(Double.parseDouble(price));
        order.setContactId(linkmanId);
        order.setPayFlag(0);//未支付
        selfHelpOrderService.insertOrder(order);

        for(int i=0;i<assetArray.length;i++){
        	//插入订单资产表
        	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]),globle_user.getId());
            OrderAsset orderAsset = new OrderAsset();
            orderAsset.setOrderId(orderId);
            orderAsset.setAssetId(Integer.parseInt(assetArray[i]));
            orderAsset.setServiceId(Integer.parseInt(serviceId));
            if(scanPeriod!=null && !scanPeriod.equals("")){
                orderAsset.setScan_type(Integer.parseInt(scanPeriod));
            }
            orderAsset.setAssetAddr(asset.getAddr());
            orderAsset.setAssetName(asset.getName());
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
    @RequestMapping(value="delShopCar.html",method=RequestMethod.POST)
    @ResponseBody
    public void delShopCar(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	Map map = new HashMap();
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	  Map<String, Object> m = new HashMap<String, Object>();
        //获得订单id
        String orderId = request.getParameter("orderId");
        if(orderId==null&&"".equals(orderId)){
        	map.put("error", true);
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
        //删除联系人
        orderService.delLinkmanByOrderId(orderId,globle_user.getId());
        //删除订单资产
        orderAssetService.deleteOaByOrderId(orderId,globle_user.getId());
        //删除订单api
        orderAPIService.deleteOrderAPI(orderId,globle_user.getId());
         //删除订单
        orderService.deleteOrderById(orderId,globle_user.getId());
     
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
	@RequestMapping(value = "shopBuy.html", method = RequestMethod.POST)
	public String shopBuy(HttpServletRequest request) {
		User globle_user = (User) request.getSession().getAttribute("globle_user");

		String str = request.getParameter("str");
		if (str == null || "".equals(str)) {
			return "redirect:/index.html";
		}
		List list = new ArrayList();
		List shopAPIList = new ArrayList();
		List<ShopCar> shopSysList = new ArrayList();
		List<ShopCar> shopList = new ArrayList();
		int orderNum = 0;
		List<String> orderIdList = new ArrayList();
		Linkman linkman = new Linkman();
		if (str != null && !"".equals(str)) {
			String strArray[] = str.substring(0, str.length() - 1).split(",");
			orderNum = strArray.length;
			for (int m = 0; m < strArray.length; m++) {
				orderIdList.add(strArray[m]);
			}
			int count = selfHelpOrderService.findOrderCountByUserId(orderIdList, globle_user.getId());
			if (orderIdList.size() == 0 || count != orderIdList.size()) {
				// 有些订单号没有或有些订单不属于该用户时，跳转到首页
				return "redirect:/index.html";
			}
			list = selfHelpOrderService.findBuyShopList(orderIdList, globle_user.getId());
			linkman = orderService.findLinkmanByOrderId(strArray[0]);
		} else {
			// 有些订单号没有或有些订单不属于该用户时，跳转到首页
			return "redirect:/index.html";
		}

		// 检查订单是否失效
		if (!checkOrderDate(list)) {
			// 有些订单号失效时，跳转到首页
			return "redirect:/index.html";
		}
		
		DecimalFormat df = new DecimalFormat("0.00");
		double shopCount = 0.0;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ShopCar shopCar = (ShopCar) list.get(i);
				if (shopCar.getIsAPI() == 3) {
				
					shopSysList.add(shopCar);

				} else {
					if (shopCar.getIsAPI() == 0 || shopCar.getIsAPI() == 2) {
						shopList.add(shopCar);
					} else {

						shopAPIList.add(shopCar);
					}
				}
				shopCount = shopCount + shopCar.getPrice();
			}
		}
		// System.out.println(shopSysList);

		request.setAttribute("linkman", linkman);
		request.setAttribute("orderNum", orderNum);
		request.setAttribute("shopCount", df.format(shopCount));
		request.setAttribute("shopList", shopList);
		request.setAttribute("shopAPIList", shopAPIList);
		request.setAttribute("shopSysList", shopSysList);
		request.setAttribute("user", globle_user);
		// 判断显示结算页的按钮
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
	@RequestMapping(value="shopSettlement.html",method=RequestMethod.POST)
	public void shopSettlement(HttpServletResponse response,HttpServletRequest request){
		 Map<String, Object> map = new HashMap<String, Object>();  
		 String id ="";
		 String orderVal="";
		 String serverNames="";
	try{
		 User globle_user = (User) request.getSession().getAttribute("globle_user");
		 Date date = new Date();
		 boolean flag=true;
		 boolean bflag=true;
		 String status="";
		String str = request.getParameter("orderIds");
		   DecimalFormat df = new DecimalFormat("0.00");
		   //联系人名称
		   String linkName = request.getParameter("linkName");
		   //联系人电话
		   String linkMoblie = request.getParameter("linkMobile");
		   //联系人邮箱
		   String linkEmail = request.getParameter("linkEmail");
		   if(str==null||"".equals(str)||linkName==null||"".equals(linkName)){
			   map.put("errorStatus", true);
			   map.put("errorMsg", "");
			   JSONObject JSON = CommonUtil.objectToJson(response, map);
		        // 把数据返回到页面
		           try {
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
		   }
		   //验证邮箱
		   if(linkEmail!=null&&!"".equals(linkEmail)){
			   String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	           Pattern regex = Pattern.compile(check);
	           Matcher matcher = regex.matcher(linkEmail);
	           bflag = matcher.matches();
	           if(!bflag){
	        	   map.put("errorStatus", true);
	        	   map.put("errorMsg", "邮箱格式不正确!");
	        	   JSONObject JSON = CommonUtil.objectToJson(response, map);
			        // 把数据返回到页面
			           try {
						CommonUtil.writeToJsp(response, JSON);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
	           }  
	          
		   }
		   //验证手机号
		   if(linkMoblie==null||"".equals(linkMoblie)){
			   map.put("errorStatus", true);
			   map.put("errorMsg", "手机号码不能为空!");
			   JSONObject JSON = CommonUtil.objectToJson(response, map);
		        // 把数据返回到页面
		           try {
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
		   }
		   
		   if(linkMoblie!=null&&!"".equals(linkMoblie)){
			    Pattern regex = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");;
               Matcher matcher = regex.matcher(linkMoblie);
               bflag = matcher.matches();
             if(!bflag){
           	  map.put("errorStatus", true);
           	  map.put("errorMsg", "手机号码格式不正确!");
           	  JSONObject JSON = CommonUtil.objectToJson(response, map);
 		        // 把数据返回到页面
 		           try {
 					CommonUtil.writeToJsp(response, JSON);
 				} catch (IOException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
 				return;
             }
            
		   }
          
		   Linkman linkman = new Linkman();
		   linkman.setName(linkName);
		   linkman.setMobile(linkMoblie);
		   linkman.setEmail(linkEmail);
		//总价格
		//String price = request.getParameter("countPrice");
	  
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
	    	  list = selfHelpOrderService.findBuyShopList(orderIdList,globle_user.getId());
			}
	      if(list.size()<=0){
	    	  map.put("errorStatus", true);  
	    	  map.put("errorMsg", "");
	    	  JSONObject JSON = CommonUtil.objectToJson(response, map);
		        // 把数据返回到页面
		           try {
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
	      }
	      
	      //判断订单是否失效
	      if(!checkOrderDate(list)) {
	    	  map.put("errorStatus", true); 
	    	  map.put("errorMsg", "");
	    	  JSONObject JSON = CommonUtil.objectToJson(response, map);
		        // 把数据返回到页面
		           try {
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
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
	    	     orderVal = orderVal+ shopCar.getOrderId()+",";
	    	     serverNames = serverNames+shopCar.getServerName()+",";
	    	    orderService.updateLinkManByOrderId(linkman, shopCar.getOrderId());
	    	     map.put("flag", flag);
	    		 map.put("price", shopCount);
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
			           orderService.updateLinkManByAPIId(linkman, shopCar.getOrderId());
						map.put("orderStatus", true);
						map.put("sucess", true);
						 map.put("flag", flag);
			    		 map.put("price", df.format(shopCount));
				}

			}
			
			  //插入数据到order_list
		    OrderList ol = new OrderList();
		    //生成订单id
//		    id = String.valueOf(Random.eightcode());
			SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
			String orderDate = odf.format(new Date());
			id = orderDate+String.valueOf(Random.fivecode());
		    ol.setId(id);
		    ol.setCreate_date(new Date());
		    ol.setUserId(globle_user.getId());
		    ol.setOrderId(orderVal.substring(0,orderVal.length()-1));
		    ol.setPrice(shopCount);
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
	@RequestMapping(value="orderBack.html", method=RequestMethod.POST)
	public String  orderBack(HttpServletResponse response,HttpServletRequest request) throws Exception{
		 Map<String, Object> map = new HashMap<String, Object>();
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		String result="";
		String[] assetArray = null;
		List assetIdsList = new ArrayList();
		String assetIds = request.getParameter("assetIds");
        String orderDetailId = request.getParameter("orderDetailId");
        String apiVal=request.getParameter("apiId");
        if(assetIds==null||"".equals(assetIds)||orderDetailId==null||"".equals(orderDetailId)){
        	return "redirect:/index.html";	
        }
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
			} else if (apiId == 6) {
				result = "/source/page/details/apiDetails6";
			} else if (apiId == 7) {
				result = "/source/page/details/apiDetails7";
			} else if (apiId == 8) {
				result = "/source/page/details/apiDetails8";
			}
		}
        if(assetIds!=null&&!"".equals(assetIds)){
	    	   assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
	    	   for(int i=0;i<assetArray.length;i++){
	    		   assetIdsList.add(assetArray[i]);
	    	   }
		}
        OrderDetail orderDetail = selfHelpOrderService.getOrderDetailById(orderDetailId, globle_user.getId(),assetIdsList);
       if(orderDetail==null){
    	   return "redirect:/index.html";	
       }
	    String assetAddr = "";
	    if(assetIds.length()>0){
	          assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
            	for(int i=0;i<assetArray.length;i++){
                	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]),globle_user.getId());
                	assetAddr = assetAddr + asset.getAddr()+",";
                }
	        	//根据id查询service add by tangxr 2016-3-14
	    	    Serv service = servService.findById(orderDetail.getServiceId());
	    	    //根据service Id查询服务详细信息
	    	    ServiceDetail servDetail = servDetailService.findByServId(orderDetail.getServiceId());
	    	    request.setAttribute("service", service);
	    	    request.setAttribute("servDetail", servDetail);
	    	    //商品详细信息的图片
	    	    String[] detailImages = null;
	    	    if (servDetail != null) {
	    	    	String imageStr = servDetail.getDetailIcon();
	    	    	if(imageStr != null && !imageStr.equals("") && !imageStr.equals(";")){
	    	    		detailImages = imageStr.split(";");
	    	    	}
	    	    }
	    	    request.setAttribute("detailImages", detailImages);
	        	result = "/source/page/details/vulnScanDetails";	
	     
	    }
	    //获取服务对象资产
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
	    //网站安全帮列表
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //查询安全能力API
		List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		//查找服务频率
	    List<ScanType> scanTypeList = scanTypeService.findScanTypeById(orderDetail.getServiceId());
	    int carnum=shopCarList.size()+apiList.size();
		request.setAttribute("carnum", carnum);
		request.setAttribute("assetIds", assetIds);  
		request.setAttribute("orderDetail", orderDetail);
		request.setAttribute("scanTypeList", scanTypeList);  
		request.setAttribute("serviceAssetList", serviceAssetList);
		return result;
	}
	
    /**
     * 功能描述： 计算商品价格
     * 参数描述： 
	 * @throws Exception 
	 *  add  zhang_shaohua
     *       @time 2016-11-3
     */
    @RequestMapping(value="calPrice.html", method=RequestMethod.POST)
    @ResponseBody
	public void calPrice(HttpServletResponse response,HttpServletRequest request){
    	Map<String, Object> m = new HashMap<String, Object>();
    	//价格
		double calPrice = 0;
        //计算出的次数
        long times = 0;
        
        try{
        	//获得订单id
			int serviceId = Integer.parseInt(request.getParameter("serviceId"));
			String type = request.getParameter("type");				//服务频率
			String beginDate = request.getParameter("beginDate");
			String endDate = request.getParameter("endDate");
			int assetCount = Integer.parseInt(request.getParameter("assetCount"));
			String orderType = request.getParameter("orderType");   //选类型  单次:2/长期:1
			
			//和运营管理数据同步
//			synPriceData(serviceId);
			
			
			//进行价格分析
			if (orderType!= null && orderType.equals("1")){  //长期
				
	        	int scanType = Integer.valueOf(type); 			//服务频率
				times = calTimes(scanType, beginDate, endDate);	//计算服务需要执行的总次数
				calPrice = calPrice(serviceId,scanType,times,assetCount);//计算价格
				
			} else {	//单次
				  List<Price> priceList = priceService.findPriceByServiceId(serviceId,0);
				  if (priceList != null && priceList.size() != 0){
					  //priceList按序排列，取第一个元素判断是不是单次价格
					  Price price = priceList.get(0);  
					  if (price.getType() == 0){
						  calPrice = price.getPrice() * assetCount;
					  }
				  }
			}
			
			
			DecimalFormat df = new DecimalFormat("0.00"); 

			m.put("success", true);
			m.put("price", df.format(calPrice));
			m.put("times", times);
      
			
        }catch(Exception e) {
        	e.printStackTrace();
        	m.put("success", false);
        } finally {
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
     * 运营管理同步当前服务的价格
     * */
    private void synPriceData(int serviceId) {
    	try {
    		String str = "";
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
			        int scanTypeJson = Integer.parseInt(jsonObject1.getString("scanType"));
			       
			        Price newprice = new Price();
			        newprice.setServiceId(serviceIdJson);
			        newprice.setType(typeJson);
			        if(typeJson != 0) {   //0:单次；1：长期；2：大于
			        	newprice.setTimesG(timesGJson);
			        	newprice.setTimesLE(timesLEJson);
			        }
			        newprice.setPrice(priceJson);
			        if (scanTypeJson !=0) {  //长期价格不根据服务频率计算时，服务频率设为null
			        	newprice.setScanType(scanTypeJson);
			        }
			        
			        priceService.insertPrice(newprice);
			    }
	        }
	        
		} catch (Exception e1) {
			System.out.println("链接服务器失败!");
		}
    }
	
	
    /**
     * 计算当前服务需要执行的总次数
     * */
    private long calTimes(int scanType,String beginDate, String endDate){
    	long times = 0; 
    	long ms = 0;//时间之间的毫秒数
    	Date bDate = null;
    	Date eDate = null;
    	if(beginDate!=null&&beginDate!=""&endDate!=null&&endDate!=""){
    		bDate = DateUtils.stringToDateNYRSFM(beginDate);
            eDate = DateUtils.stringToDateNYRSFM(endDate);  
            ms = DateUtils.getMsByDays(bDate, eDate);
    	}
    	
    	if (ms == 0){
    		return 1;
    	}
    	
    	switch(scanType){
    	case 1:		//10分钟
    		int min_10 = 1000*3600/6;
        	if(ms%min_10 > 0){
        		times = (long)(ms/min_10) + 1;
        	}else{
        		times = (long)(ms/min_10);
        	}
    		break;
    	case 2:		//30分钟
    		int min_30 = 1000*3600/2;
        	if(ms%min_30 > 0){
        		times = (long)(ms/min_30) + 1;
        	}else{
        		times = (long)(ms/min_30);
        	}
    		break;
    	case 3:		//1小时
    		int oneHour = 1000*3600;
        	if(ms%oneHour > 0){
        		times = (long)(ms/oneHour) + 1;
        	}else{
        		times = (long)(ms/oneHour);
        	}
    		break;
    	case 4:		//1天
    		int oneDay = 1000*3600*24;
        	if(ms%oneDay > 0){
        		times = (long)(ms/oneDay) + 1;
        	}else{
	        	times = (long)(ms/oneDay);
        	}
    		break;
    	case 5:		//每周
    		int perWeek = 1000*3600*24*7;
    		if(ms%perWeek>0){
    			times = (long)(ms/perWeek)+1;
    		}else{
    			times = (long)(ms/perWeek);
    		}
    		break;
    	case 6:		//每月
    		while(ms>0){
    			bDate = DateUtils.getDayAfterMonth(bDate);
    			ms = DateUtils.getMsByDays(bDate, eDate);
    			times++;
    		}
    		break;
    	default:
    		break;
    	}
    	
    	return times;
    }
    
    
    private double calPrice(int serviceId, int scanType, long times, int assetCount) {
    	double sumPrice = 0;
    	
    	//根据serviceid查询价格列表
    	List<Price> priceList = priceService.findPriceByServiceId(serviceId,scanType);
    	if (priceList == null|| priceList.size() == 0){		//按服务频率查询不到时，价格可能不按频率设置
    		priceList = priceService.findPriceByScanTypeNull(serviceId);
    	}
    	
    	//价格列表不存在时，
    	if (priceList == null|| priceList.size() == 0){
    		return sumPrice;
    	}
    	
		for (int i = 0; i < priceList.size(); i++) {
			Price price = priceList.get(i);
			if(price.getType()== 1 && times > price.getTimesG() && times <= price.getTimesLE()){  //区间
				sumPrice = price.getPrice()*times*assetCount;
				break;
			}else if (price.getType()== 2 && times>price.getTimesG()){  //大于
				sumPrice = price.getPrice()*times*assetCount;
				break;
			}
		}
		
		if (sumPrice == 0){   
			//例如：服务Id=1,times=1时,取单次的价格进行计算
			//     服务Id=4,times=1时,取第一个区间的价格进行计算
			sumPrice = priceList.get(0).getPrice()*times*assetCount;
		}
		
		
		return sumPrice;
    	
    }

	 /**
	 * 功能描述： 购物车去结算判断当前时间是否超过服务结束时间/加入购物车时间是否超过7天
	 * 参数描述：  无
	 *     @time 2016-05-23
	 *     add gxy
	 */
	@RequestMapping(value="checkShoppOrder.html", method=RequestMethod.POST)
	public void checkOrderInfo(HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
		
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

	try{
		 boolean flag = true;
		 int status=0;
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
	    	  list = selfHelpOrderService.findBuyShopList(orderIdList,globle_user.getId());
			}
	      
	      flag = checkOrderDate(list);
	     //检查购物车的系统安全帮订单下单时是否有正在运行的同类型系统安全帮订单
	      int countserid7=0;
		  int countserid8=0;
		  int countserid9=0;
			
			
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ShopCar shopCar = (ShopCar) list.get(i);
					
					if (shopCar.getIsAPI() == 3) {
						//去结算时，如果同时结算多个系统安全帮订单，某个类型相同的数量大于1，就提示错误
						
						if(shopCar.getServiceId()==7){
							countserid7++;
						}
						if(shopCar.getServiceId()==8){
							countserid8++;
						}
						if(shopCar.getServiceId()==9){
							countserid7++;
						}
						
					}
				}
			}
			if(countserid7>1 || countserid8>1 ||countserid9>1){
				status=-3;
				flag=false;
			}

	   //object转化为Json格式
	         m.put("flag", flag);
	         m.put("status", status);
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
	
	/**  功能描述：判断订单是否失效 
	 * true:所有订单都未失效 false：部分或所有订单失效
	 */
	private boolean checkOrderDate(List list){
		boolean flag = true;
		Date date = new Date();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				ShopCar shopCar = (ShopCar)list.get(i);
				Date endDate = shopCar.getEndDate();
				//判断当前时间已经超过服务结束时间
				if(endDate!=null && !"".equals(endDate) && date.getTime()>endDate.getTime()){
					
					flag=false;
					//修改订单状态已作废
					shopCar.setStatus(-1);
					selfHelpOrderService.updateShopOrder(shopCar);
				//加入购物车时间是否超过7天
				}else if (date.getTime() - shopCar.getCreateDate().getTime() > 1000*60*60*24*7) {
					flag=false;
					//修改订单状态已作废
					shopCar.setStatus(-1);
					selfHelpOrderService.updateShopOrder(shopCar);
				}
			}	 
		}
		
		return flag;
	}
    
    /**
     * 功能描述： 收银台页面
     * */
    @RequestMapping("cashierUI.html")
    public String cashier(Model model, HttpServletRequest request){
    	String orderListId = request.getParameter("orderListId");//订单编号(cs_order_list的id)
    	String renew = request.getParameter("renew");
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

    	//安全币余额
		List<User> userList = userService.findUserById(orderList.getUserId());
        String balance = df.format(userList.get(0).getBalance());
    	
        model.addAttribute("orderList", orderList);
        model.addAttribute("price", priceStr);
        model.addAttribute("serverName", serverNameMap);
        model.addAttribute("balance",balance);
        model.addAttribute("renew",renew);
    	return "source/page/details/shoppingcashier-desk2";
    }
    
    /**
     * 功能描述： 确认支付
     * */
    @RequestMapping(value="payConfirm.html", method=RequestMethod.POST)
    public void payConfirm(HttpServletRequest request, HttpServletResponse response){
    	Map<String, Object> m = new HashMap<String, Object>();
    	try {
    		String orderListId = request.getParameter("orderListId");//订单编号(cs_order_list的id)
    		//判断是否续费
    		String renew = request.getParameter("renew");
    		OrderList orderList = orderListService.findById(orderListId);
    		if (orderList == null) {
    			m.put("payFlag", 1);
    			return;
    		}
    		
    		//不是当前用户的订单,不能支付
        	User globle_user = (User) request.getSession().getAttribute("globle_user");
        	if (orderList.getUserId()!= globle_user.getId()) {
        		m.put("payFlag", 2);
    			return;
        	}
        	
    		Double price = orderList.getPrice();//支付金额
    		//收银台页面刷新，再次支付
    		if("".equals(renew)){
    			if (orderList.getPay_date() != null){
        			//不能重复支付
        			m.put("payFlag", 1);
        			return;
        		}
    		}

    		
    		List<User> userList = userService.findUserById(globle_user.getId());
    		Double balance = userList.get(0).getBalance();
    		//安全币余额不足
    		if (balance < price){
    			//安全币余额不足
    			m.put("orderListId",orderList.getId());
    			m.put("payFlag", 3);
    			return;
    		}
    		
    		//设置支付时间
    		orderList.setPay_date(new Date());
    		
    		//若支付时间>服务的开始时间，更新订单的开始时间，结束时间
    		List<String> orderIdOfModify = new ArrayList<String>();
    		try{
    			orderIdOfModify = modifyOrderBeginTime(orderList,request);
    		} catch(Exception e) {
    			e.printStackTrace();
    			//长期：订单结束时间不能早于当前订单提交时间
    			if (e.getMessage().equals("订单失效!")) {
    				m.put("payFlag", 5);//付款成功
    			}
    			return;
    		}
    			
    		// 南向API调用 任务执行
    		if(!orderTask(orderList, globle_user, orderIdOfModify,renew)) {
    			try{
    			m.put("payFlag", 4);
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    			return;
    		}
    		
    		//更新安全币余额（DB和session中都更新）
    		globle_user.setBalance(balance - price);//session更新
    		User user = new User();
    		user.setId(globle_user.getId());
    		user.setBalance(globle_user.getBalance());
    		userService.updateBalance(user);//DB更新
    		
    		//更新 支付时间，支付金额(cs_order_list表)
    		//orderList.setPay_date(new Date());
    		selfHelpOrderService.updatePayDate(orderList);
    		
    		//更新 支付Flag(cs_order表) 未支付-->已支付
    		String orderIds = orderList.getOrderId();//订单条目编号(cs_order的id)
    		selfHelpOrderService.updateOrderPayFlag(orderIds, 1 ,1);
    		
    		//取得服务时间更改的订单编号
    		String orderId = "";
    		for(String id: orderIdOfModify){
    			orderId = orderId+ id +",";
    		}
    		if (orderId!= null && !orderId.equals("")) {
    			orderId = orderId.substring(0,orderId.length()-1);
    		}
    		m.put("orderListId",orderList.getId());
    		m.put("modifyOrderId", orderId);//部分服务调整的 orderId
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
     * @throws Exception 
     * */
    public List<String> modifyOrderBeginTime(OrderList orderList,HttpServletRequest request) throws Exception {
    	List<String> orderIdOfModify = new ArrayList<String>();
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
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
			list = selfHelpOrderService.findBuyShopList(orderIdList,globle_user.getId());
		}
		Date payDate = orderList.getPay_date();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				ShopCar shopCar = (ShopCar)list.get(i);
				Date beginDate = shopCar.getBeginDate();
				Date endDate = shopCar.getEndDate();
				//判断订单是否失效
				//长期：订单结束时间不能早于当前订单提交时间
				if (endDate!= null && !DateUtils.dateToString(endDate).equals("")&& new Date().getTime()>endDate.getTime()) {
					Exception e = new Exception("订单失效!");
					throw e;
				}else if (new Date().getTime() - shopCar.getCreateDate().getTime() > 1000*60*60*24*7) {
					Exception e = new Exception("订单失效!");
					throw e;
				}
			
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
    
    public boolean orderTask(OrderList orderList, User globle_user, List<String> modifyOrderId,String renew){
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
    			 for (int m=0;m<strArray.length;m++){
    				orderIdList.add(strArray[m]);
    			}
    			
    			orderNum= strArray.length;
    			if (orderNum == 1) {
    				List orderHashList = orderService.findByOrderId(orderIdList.get(0));
    				if (orderHashList == null ||orderHashList.size() == 0) {
    		    		return false;
    		    	}
    				HashMap<String, Object> order=new HashMap<String, Object>();
    			    order=(HashMap) orderHashList.get(0);
    			    int serviceId=0;
    			    serviceId=(Integer) order.get("serviceId");
    			    if (serviceId == 7|| serviceId==8||serviceId==9) {//极光自助、金山、云眼  接口
    			    	User loginUser = userService.findUserById(globle_user.getId()).get(0);
    			    	Date createDate = (Date)order.get("create_date");
    			    	Date  beginDate =(Date)order.get("begin_date");
    			    	String orderId = (String)order.get("id");
    			    	status = status+order.get("status");
    			    	Integer userid = new Integer(globle_user.getId());
    			    	int scanTypeint = (Integer)order.get("scan_type");
    			    	Integer scanTypeInteger = new Integer(scanTypeint);
    				    if (orderId != null && !"".equals(orderId)) {
							selfHelpOrderService.updateOrder(orderId,orderId, "3",status,orderList.getId(),orderList.getPay_date());
    			    	} else {
        					result = false;
        				}
						return result;
					}
				}	
    			list = selfHelpOrderService.findBuyShopList(orderIdList,globle_user.getId());
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
    				String targetURL[]=shopCar.getAddr().split(",");
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
    					if(shopCar.getServiceId()!=6){
    						orderId = NorthAPIWorker.vulnScanCreate(String.valueOf(shopCar.getOrderType()), targetURL, scanType,begin_date,end_date, String.valueOf(shopCar.getScanPeriod()),
    								scanDepth, maxPages, stategy, customManu, String.valueOf(shopCar.getServiceId()), globle_user.getApikey());
    						orderVal = orderVal+ orderId+",";
    					}else{
                       //判断时间是否立即执行waf
    						String nowDay = DateUtils.dateToDate(new Date());
    						String begin = DateUtils.dateToDate(shopCar.getBeginDate());
    						if(begin.compareTo(nowDay)<=0){
    							//创建waf虚拟站点,modify by tangxr 2016-6-13
        						List assets = orderAssetService.findAssetsByOrderId(shopCar.getOrderId());
        						HashMap<String, Object> assetOrder = new HashMap<String, Object>();
    				        	assetOrder=(HashMap) assets.get(0);
    				        	int id = 0;
    				        	String addr = "";
    				        	String addrName = "";
    				        	String wafIp = "219.141.189.183";
    				        	String wafPort = "";
        						JSONArray ser = new JSONArray();
        						if(assets != null && assets.size() > 0){
        							String ipArray=(String) assetOrder.get("ipArray");
        				        	id = (Integer) assetOrder.get("orderAssetId");
        				        	addr=(String) assetOrder.get("addr");
        				        	addrName=(String) assetOrder.get("name");
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
        				            	String[] ip = ips[n].split(":");
        	    						jo.put("ip", ip[0]);
        	    						jo.put("port", ip[1]);
        	    						ser.add(jo);
        				            }
        				        }
        						//时间戳
        						String timestamp = String.valueOf(new Date().getTime());
        						addrName = addrName + timestamp;
        						if(addrName.length()>20){
        							addrName = addrName.substring(0, 20);
        						}
        						String wafcreate = WafAPIWorker.createVirtualSiteInResource("10001", addrName, wafIp, wafPort, "nsfocus.cer", "0", addr, "*", "", ser);
        						String targetKey = "";
        				    	try {
        				    		JSONObject obj = JSONObject.fromObject(wafcreate);
        				    		targetKey = obj.getString("targetKey"); 
        				    		String sta = obj.getString("status");
        				    		if(sta!=null&&!"".equals(sta)&&sta.equals("success")){
        				    			OrderAsset oa = new OrderAsset();
            				    		oa.setId(id);
            				    		oa.setTargetKey(targetKey);
            				    		orderAssetService.update(oa);
            				    		//5 下发到waf，但未解析域名
            				    		Order order = new Order();
            			    			order.setId(shopCar.getOrderId());
            			    			order.setStatus(5);
            			    			orderService.update(order);
            			    			
            				    		SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
            				    		if(renew!=null&&!"".equals(renew)){
            				    			orderId = shopCar.getOrderId();
            				    			orderVal=orderVal+ orderId+",";;
            				    		}else{
	                						String orderDate = odf.format(new Date());
	                						orderId = orderDate+String.valueOf(Random.fivecode());
	                						orderVal = orderVal+ orderId+",";
            				    		}
        				    		}else{
        				    			orderId = "";
        				    		}
        				        } catch (Exception e) {
        				        	orderId = "";
        				            e.printStackTrace();
        				        }
        						//end
    						}else{
    							if(renew!=null&&!"".equals(renew)){
    				    			orderVal = shopCar.getOrderId();
    				    			orderVal=orderVal+ orderId+",";
    				    		}else{
	    							SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
	        						String orderDate = odf.format(new Date());
	        						orderId = orderDate+String.valueOf(Random.fivecode());
	        						orderVal = orderVal+ orderId+",";
    				    		}
    						}
    						
    					}
    					
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				
    				//北向API返回orderId，创建用户订单
    				if(!orderId.equals("") && orderId != null){
    					//更新订单资产表
    					selfHelpOrderService.updateOrderAsset(shopCar.getOrderId(), orderId);
    					//更新订单表
    					selfHelpOrderService.updateOrder(shopCar.getOrderId(), orderId, String.valueOf(shopCar.getIsAPI()),status,orderList.getId(),orderList.getPay_date());
    					//更新 修改时间的订单Id
    					if (modifyOrderId.contains(shopCar.getOrderId())){
    						modifyOrderId.remove(shopCar.getOrderId());
    						modifyOrderId.add(orderId);
    					}
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
    				try {
    					if(shopCar.getServiceId()!=6){
    						orderId = NorthAPIWorker.vulnScanCreateAPI(
    								Integer.parseInt(shopCar.getAstName()),
    								shopCar.getBuynum(), shopCar.getServiceId(),
    								globle_user.getApikey(), globle_user.getId());
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
    							orderId, "1",status,orderList.getId(),orderList.getPay_date());
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
    		String newOrderIds = "";
    		if (orderVal != null&& !orderVal.equals("")) {
    			if(renew!=null&&!"".equals(renew)){
    				orderVal = orderVal.substring(0,orderVal.length()-1);
    				orderList.setOrderId(orderVal);
    				newOrderIds=orderVal;
    			}else{
    			   newOrderIds = orderVal.substring(0,orderVal.length()-1);
    			orderList.setOrderId(newOrderIds);
    			}
    			orderListService.update(orderList);
    		}
    		
    		//只有一个订单明细时，订单编号(orderList的Id)和订单明细的编号(order的Id)一致
    		if (orderIdList!= null && orderIdList.size() == 1) {
    			orderListService.updateOrderListId(orderList.getId(), newOrderIds);
    			orderList.setId(newOrderIds);
    			selfHelpOrderService.updateOrderListId(newOrderIds);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		result = false;
    	}
	     return result;
    }
    
    /**
     * 功能描述： 支付成功页面
     * @throws URISyntaxException 
     * @throws IOException 
     * */
    @RequestMapping(value="repayUI.html", method=RequestMethod.POST)
    public String toRepayUI(Model m,HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String orderListId = request.getParameter("orderListId");//订单编号(cs_order_list的id)
		OrderList orderList = orderListService.findById(orderListId);
		
		//不是当前用户的订单,不能支付
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	if (orderListId== null || orderList == null ||orderList.getUserId()!= globle_user.getId()) {
    		return "redirect:/index.html";
    	}
    	
    	
		//获取修改时间的订单编号
		String modifyOrderIdList = request.getParameter("modifyOrderId");
		if (modifyOrderIdList != null && !modifyOrderIdList.equals("") && !modifyOrderIdList.equals("undefined")){
			String modifyOrderId[] = modifyOrderIdList.split(",");
			for(String id:modifyOrderId){
				if(!orderList.getOrderId().contains(id.trim()+",")&&
						!orderList.getOrderId().endsWith(id.trim())){
					return "redirect:/index.html"; 
				}
			}
			/**waf 订单支付成功发送短信提醒开始**/
			List<String> orderIdList=new ArrayList();

			if(orderListId!=null&&!"".equals(orderListId)){
				String strArray[] = orderListId.split(",");
				for (int k=0;k<strArray.length;k++){
					orderIdList.add(strArray[k]);
				}
			}
			List	list = selfHelpOrderService.findBuyShopList(orderIdList,globle_user.getId());
			Linkman linkman=orderService.findLinkmanByOrderId(orderListId);
			for(int i=0;i<list.size();i++){
				ShopCar shopCar = (ShopCar)list.get(i);
				int serviceId = shopCar.getServiceId();
				if(serviceId==6){
				String mobile = 	linkman.getMobile();
				  SMSUtils smsUtils = new SMSUtils();
				     try {
						smsUtils.sendMessage(mobile, "恭喜您已成功付款"+shopCar.getPrice()+" &nbsp;部分服务时间已根据订单支付成功时间自动调整为："+orderList.getPay_date()+"&nbsp;当前订单需要设置域名解析；");
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			/**waf 订单支付成功发送短信提醒结束**/
			m.addAttribute("modifyOrderId", modifyOrderId);
			m.addAttribute("beginDate", orderList.getPay_date());  //部分服务调整的开始时间时间
		}
		
		if (orderList.getPay_date()== null){
			m.addAttribute("paySuccess", 1);
		} else {
			String[] api = orderList.getServerName().split(",");
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
	 * 功能描述： 保存详情页操作
	 * 参数描述：  无
	 *     @time 2016-07-18
	 */
	@RequestMapping(value="selfHelpOrderOpera.html",method=RequestMethod.POST)
	public String selfHelpOrderOpera(HttpServletRequest request){
		 String assetArray[] = null;
		  String detailId="";
		  List assetIdsList = new ArrayList();
		  List<Price> priceList = new ArrayList();
	try{
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		//资产ids
        String assetIds = request.getParameter("assetIds");
		String orderType = request.getParameter("type");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String scanPeriod = request.getParameter("scanType");
        String serviceId = request.getParameter("serviceId");
        String createDate = DateUtils.dateToString(new Date());

        //判断参数值是否为空
        if((assetIds==null||"".equals(assetIds))||(orderType==null||"".equals(orderType))||(beginDate==null||"".equals(beginDate))||(serviceId==null||"".equals(serviceId))){
        	return "redirect:/index.html";
        }
    
     //判断资产是否存在
       if(assetIds!=null&&!"".equals(assetIds)){
    	   assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
       	for(int i=0;i<assetArray.length;i++){
           	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]),globle_user.getId());
            if(asset==null){
            	return "redirect:/index.html";
            }
           }
       }
       
       //根据判断服务id是否存在
       Serv service = servService.findById(Integer.parseInt(serviceId));  
       if(service==null){
    	   return "redirect:/index.html";
       }
       
        //判断类型是否存在
       if(!checkOrderType(serviceId,orderType)){
    		return "redirect:/index.html";
       }
       //单次
       if(orderType.equals("2")){
    	   if(endDate!=null&&!"".equals(endDate)){
    		   return "redirect:/index.html";
    	   }
       }
       //长期
       if(orderType.equals("1")){
    	   if(beginDate==null || "".equals(beginDate)|| endDate==null || "".equals(endDate)){
    		   return "redirect:/index.html";
    	   }
    	   //服务频率
    	   if(scanPeriod==null || "".equals(scanPeriod)){
    		   return "redirect:/index.html";
    	   }
       }
       //开始时间和结束时间参数比较
       if(beginDate!=null&&!"".equals(beginDate)&&endDate!=null&&!"".equals(endDate)){
    	   Date begin = DateUtils.stringToDateNYRSFM(beginDate);
           Date end = DateUtils.stringToDateNYRSFM(endDate);
           
           if(begin.getTime()>end.getTime()){
           	return "redirect:/index.html";
           } 
       }
       //长期
       if(orderType.equals("1")){
    	   //判断服务频率是否存在
    	   List<ScanType> scanTypeList = scanTypeService.findScanType(Integer.parseInt(serviceId), Integer.parseInt(scanPeriod));
    	   if(scanTypeList.size() <= 0){
    		   return "redirect:/index.html";
    	   }
       }
      
		/***计算价格开始***/
      
		double calPrice = 0;
        //计算出的次数
        long stimes = 0;
    	try {
    		
			//获得订单id
			int serviceIdV = Integer.parseInt(serviceId);
			if(assetIds!=null&&!"".equals(assetIds)){
				assetArray=assetIds.split(",");
			}
			int assetCount = assetArray.length;
			if(assetCount<=0){
				assetCount=1;
			}
			//进行价格分析
			if (orderType!= null && orderType.equals("1")){  //长期
				
				int scanType = Integer.valueOf(scanPeriod); 			//服务频率
				stimes = calTimes(scanType, beginDate, endDate);	//计算服务需要执行的总次数
				calPrice = calPrice(serviceIdV,scanType,stimes,assetCount);//计算价格
				
			} else {	//单次
				priceList = priceService.findPriceByServiceId(serviceIdV,0);
				if (priceList != null && priceList.size() != 0){
					//priceList按序排列，取第一个元素判断是不是单次价格
					Price price = priceList.get(0);  
					if (price.getType() == 0){
						calPrice = price.getPrice() * assetCount;
					}
				}
			}
			
			DecimalFormat df = new DecimalFormat("0.00"); 
          String  price = df.format(calPrice);
          /***计算价格结束***/
          OrderDetail orderDetail = new OrderDetail();
      	  SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
   	      String orderDate = odf.format(new Date());
   	     detailId = orderDate+String.valueOf(Random.fivecode());
   	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
   	      orderDetail.setId(detailId);
          orderDetail.setBegin_date(DateUtils.stringToDateNYRSFM(beginDate));
          orderDetail.setEnd_date(DateUtils.stringToDateNYRSFM(endDate));
          orderDetail.setType(Integer.parseInt(orderType));
          orderDetail.setServiceId(serviceIdV);
          orderDetail.setUserId(globle_user.getId());
          orderDetail.setIsAPI(0);
          orderDetail.setAsstId(assetIds.substring(0,assetIds.length()-1));
          orderDetail.setPrice(Double.parseDouble(price));
          orderDetail.setCreate_date(sdf.parse(createDate));
          if(scanPeriod!=null&&!"".equals(scanPeriod)){
        	  orderDetail.setScan_type(Integer.parseInt(scanPeriod));
          }
        
          selfHelpOrderService.SaveOrderDetail(orderDetail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/index.html";
			//object转化为Json格式
			
		}
		if(assetIds!=null&&!"".equals(assetIds)){
	    	   assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
	    	   for(int i=0;i<assetArray.length;i++){
	    		   assetIdsList.add(assetArray[i]);
	    	   }
		}
		OrderDetail orderDetail = selfHelpOrderService.getOrderDetailById(detailId, globle_user.getId(),assetIdsList);
		
		request.setAttribute("orderDetail",orderDetail);
		request.setAttribute("service",service);
		request.setAttribute("user",globle_user);
	    String result = "/source/page/details/settlement";
        return result;
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
	/**
	 * 检查各服务的服务类型
	 */
	private boolean checkOrderType(String serviceId, String orderType) {
		boolean result = false;
		int servId = Integer.valueOf(serviceId);
		ServiceDetail servDetail = servDetailService.findByServId(servId);
		if (servDetail == null) {
			return result;
		}
		int servType = servDetail.getServType(); //0:单次和长期 1：长期 2：单次
		switch(servType) {
		case 0:
			if(orderType.equals("1") || orderType.equals("2")) {
				result = true;
			}
			break;
		case 1:
			if(orderType.equals("1")) {
				result = true;
			}
			break;
		case 2:
			if(orderType.equals("2")) {
				result = true;
			}
			break;
		default:
			break;
		}
		
		return result;
	}
	
	/**
     * 功能描述： 订单详情,支付成功之后的跳转
     * */
    @RequestMapping(value="orderDetailsUI.html")
    public String toOrderDetails(Model m,HttpServletRequest request, HttpServletResponse response){
    	String orderId = request.getParameter("orderId");//订单编号(cs_order_list的id)
		Order order = orderService.findOrderById(orderId);
		
		//验证orderId
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	if (orderId == null || order == null ||order.getUserId()!= globle_user.getId()) {
    		return "redirect:/index.html";
    	}
    	
    	if(order.getServiceId() != 6){
    		//API
    		if(order.getIsAPI() == 1) {
    			return "redirect:/apiDetails.html?orderId="+orderId;
    		}
    		
    		if(order.getIsAPI() != 1 && order.getBegin_date().getTime() > new Date().getTime()) {
    			return "redirect:/orderDetails.html?orderId=" + orderId;
    		}
    		
    		if (order.getStatus() == 2){  
    			return "redirect:/warningInit.html?orderId="+orderId+"&type="+order.getType()+"&websoc="+order.getWebsoc();
    		}
    		
    		if (order.getStatus() == 1 && order.getIsAPI() == 0) {  //status=1：完成无告警
    			return "redirect:/warningInit.html?orderId="+orderId+"&type="+order.getType()+"&websoc="+order.getWebsoc();
    		}
    		
    		if (order.getStatus() == 1 && order.getIsAPI() == 1) {   //status=1：完成无告警  API
    			return "redirect:/selfHelpOrderAPIInit.html?apiId="+order.getServiceId()+"&indexPage=2";
    		}
    		//安恒的服务
    		if (order.getIsAPI() == 0 && order.getBegin_date().getTime() <= new Date().getTime() && 
    				order.getStatus() != 3 && order.getStatus() != 2 && order.getStatus() != 1) {  //status=4：执行中 
    			return "redirect:/warningInit.html?orderId="+orderId+"&type="+order.getType()+"&websoc="+order.getWebsoc();
    		}
    		
    		if (order.getBegin_date().getTime() <= new Date().getTime() && order.getStatus() == 3) { //status=3：执行中有告警 
    			return "redirect:/warningInit.html?orderId="+orderId+"&type="+order.getType()+"&websoc="+order.getWebsoc();
    		}
    		
    	}else{
    		if (order.getIsAPI() == 2 && order.getStatus() == 4) {
    			return "redirect:/warningWaf.html?orderId="+orderId+"&type="+order.getType();
    		}
    	}
    	return "redirect:/orderDetails.html?orderId=" + orderId;
    }
    
  
}
