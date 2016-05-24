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

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
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
	 /**
	 * 功能描述：购买waf服务
	 * 参数描述：  无
	 * add gxy
	 *     @time 2016-5-18
	 */
	@RequestMapping(value="wafDetails.html")
	public String wafDetails(HttpServletRequest request){
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
		int serviceId = Integer.parseInt(request.getParameter("serviceId"));
		Serv service = servService.findById(serviceId);
		  //网站安全帮列表
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //查询安全能力API
		   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		 int carnum=shopCarList.size()+apiList.size();
		 request.setAttribute("carnum", carnum);  
		request.setAttribute("assList", assList);
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
		 SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss"); 
		 Date date= new Date();
		String value1= sdf1.format(date);
		String serviceId = request.getParameter("serviceId");
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
		//ip地址
		String ipStr = request.getParameter("ipVal");
	   //资产名称
		String assetName = request.getParameter("assetName");
		//域名
		String domainName = request.getParameter("domainName");
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
        boolean flag=true;
        //页面输入的ip地址
        String ipArr[]= ipStr.split(",");
        for(int i=0;i<ipArr.length;i++){
      	  for(int k=0;k<array.length;k++){
      		    if(!ipArr[i].equals(array[k])){
      		    	flag=false;
      		    	break;
      		    }
      	  }
        }
	         m.put("flag", flag);
	         m.put("serviceId", serviceId);
	         m.put("orderType", orderType);
	         m.put("beginDate", beginVal);
	         m.put("endDate", endVal);
	         m.put("assetName", assetName);
	         m.put("ipStr", ipStr);
	         m.put("price", price);
	         m.put("month", month);
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
        String assetName = request.getParameter("assetName");
        String ipStr = request.getParameter("ipStr");
        String month = request.getParameter("month");
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
        order.setTask_date(begin_date);
		order.setUserId(globle_user.getId());
		order.setPrice(Double.parseDouble(priceVal));
        order.setPayFlag(0);//未支付
        selfHelpOrderService.insertOrder(order);

       
        	//插入订单资产表
        //根据资产名称查询资产信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId",globle_user.getId());
        paramMap.put("name", assetName.trim());
		List<Asset> listForName = assetService.findByAssetAddr(paramMap);
		Asset assetInfo = listForName.get(0);
            OrderAsset orderAsset = new OrderAsset();
            orderAsset.setOrderId(orderId);
            orderAsset.setAssetId(assetInfo.getId());
            orderAsset.setServiceId(Integer.parseInt(serviceId));
            orderAsset.setIpArray(ipStr);
            orderAsset.setSermonth(Integer.parseInt(month));
           orderAssetService.insertOrderAsset(orderAsset);
      
   	     //网站安全帮列表
          List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
       //查询安全能力API
		 List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		 int carnum=shopCarList.size()+apiList.hashCode();
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
        int times = Integer.parseInt(request.getParameter("times"));
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
	    
	    if(scanType.equals("1")){//包年
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
        //用户
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
        Map<String, Object> m = new HashMap<String, Object>();
        
        try {
			//后台判断资产是否可用，防止恶意篡改资产
			//资产id
			String domainId = request.getParameter("domainId");
			boolean assetsStatus = false;
			Asset _asset = assetService.findById(Integer.parseInt(domainId));
			int status = _asset.getStatus();
			if(status==0){//未验证
			    assetsStatus = true;
			}

			m.put("assetsStatus", assetsStatus);
			if(assetsStatus){
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
			
			//随机生成订单编号
			String orderId = String.valueOf(Random.code());
			String scanType = request.getParameter("scanType");
			String beginDate = request.getParameter("beginDate");
			String endDate = request.getParameter("endDate");
			String createDate = DateUtils.dateToString(new Date());
			String serviceId = request.getParameter("serviceId");
			String ipArray = request.getParameter("ipArray");
			String times = request.getParameter("times");
			String price = request.getParameter("price");
     				
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
			order.setUserId(globle_user.getId());

			//订单开始时间不能早于当前订单提交时间,add by tangxr,2015-3-3
			if(beginDate.compareTo(createDate)>0){
				m.put("timeCompare", true);
			    order.setStatus(0);
			}else{
				m.put("timeCompare", false);
			    order.setStatus(-1);
			}
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
			    }
			}
			orderAsset.setIpArray(ipArray);
			orderAssetService.insertOrderAsset(orderAsset);
			m.put("orderStatus", true);
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
        request.setAttribute("editFlag", "1");
		Serv service = servService.findById(Integer.parseInt(request.getParameter("serviceId")));
		
		request.setAttribute("assList", assList);
		request.setAttribute("service", service);
	    return  "/source/page/details/wafDetails";
	}
}
