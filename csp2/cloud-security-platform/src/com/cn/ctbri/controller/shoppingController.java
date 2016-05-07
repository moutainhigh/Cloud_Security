package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Serv;
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
		String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
//       String createDate = DateUtils.dateToString(new Date());
        String scanPeriod = request.getParameter("scanType");
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
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
	    String[] assetArray = null;
	    String assetAddr = "";
        assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
        for(int i=0;i<assetArray.length;i++){
        	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]));
        	assetAddr = assetAddr + asset.getAddr()+",";
        }
        assetAddr = assetAddr.substring(0, assetAddr.length()-1);
	    
        request.setAttribute("user", globle_user);
	    request.setAttribute("assetAddr", assetAddr);
	    request.setAttribute("assetIds", assetIds);
	    request.setAttribute("orderType", orderType);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("scanType", scanPeriod);
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("service", service);
        request.setAttribute("mark", "web");//web服务标记
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
	public void shoppingCar(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
        order.setUserId(globle_user.getId());
        order.setPrice(Double.parseDouble(priceVal));
        order.setPayFlag(0);//未支付
        selfHelpOrderService.insertOrder(order);
     
	    String[]  assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
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
		   Map<String, Object> m = new HashMap<String, Object>();
		   m.put("sucess", true);
		//object转化为Json格式
	        JSONObject JSON = CommonUtil.objectToJson(response, m);
	        // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
	}
	
	
	
	/**
	 * 功能描述： 查看购物车
	 * 参数描述：  无
	 * @throws Exception 
	 *      add by gxy 2016-5-05
	 */
	@RequestMapping(value="showShopCar.html")
	public String showShopCar(HttpServletRequest request) throws Exception{
		 //用户
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	//查询网站安全帮列表
		 List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
		 //查询安全能力API
		 List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
	        request.setAttribute("shopCarList", shopCarList);
	        request.setAttribute("apiList", apiList);
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
		//判断显示结算页的按钮
		request.setAttribute("shop", 0);
		 String result = "/source/page/details/newSettlement";
	        return result;
	}
  
}
