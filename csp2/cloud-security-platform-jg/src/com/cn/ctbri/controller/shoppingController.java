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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.cn.ctbri.common.APIWorker;
import com.cn.ctbri.common.AlipayConfig;
import com.cn.ctbri.common.NorthAPIWorker;
import com.cn.ctbri.common.SysWorker;
import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.APICount;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAPI;
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
import com.cn.ctbri.listener.ContextClient;
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
 * ??? ??? ???  ???  tang
 * ???????????????  2016-3-10
 * ???        ??????  ?????????????????????
 * ???        ??????  1.0
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
			p.load(shoppingController.class.getClassLoader().getResourceAsStream("InternalAPI.properties"));
			
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			VulnScan_servicePrice = p.getProperty("VulnScan_servicePrice");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	 /**
	 * ??????????????? ??????????????????
	 * ???????????????  ???
	 *     @time 2016-3-12
	 */
	@RequestMapping(value="selfHelpOrderInit.html")
	public String selfHelpOrderInit(HttpServletRequest request){
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    int serviceId = Integer.parseInt(request.getParameter("serviceId"));
	    //??????serviceId????????????
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
	    //?????????????????????
	    String indexPage = request.getParameter("indexPage");
	    //??????id??????service add by tangxr 2016-3-14
	    Serv service = servService.findById(serviceId);
	    //??????service Id????????????????????????
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
	    //??????????????????
	    List<ScanType> scanTypeList = scanTypeService.findScanTypeById(serviceId);
	    //????????????????????????
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
	    //?????????????????????
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //??????????????????API
		   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		// ?????????????????????
			List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0, "");
			int carnum = shopCarList.size() + apiList.size() + sysList.size();
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
//        request.setAttribute("orderType", "");
        String result = "/source/page/details/vulnScanDetails";
        return result;
	}
	
	 /**
	 * ??????????????? ??????
	 * ???????????????  ???
	 *     @time 2016-3-10
	 */
	@RequestMapping(value="settlement.html",method=RequestMethod.POST)
	public String settlement(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		if(request.getParameterMap().size()==0){
			return "redirect:/index.html";
		}
		//??????ids
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
      // priceVal =  price.substring(price.indexOf("??")+1,price.length()) ;
        String[]  assetArray = assetIds.split(","); //???????????????"," ,???????????????????????????strArray 
      //  double priceD = Double.parseDouble(priceVal);
       
        //??????id??????service
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
//        assetAddr = assetAddr.substring(0, assetAddr.length()-1);
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
        request.setAttribute("mark", "web");//web????????????
        
        DecimalFormat df = new DecimalFormat("0.00");
        request.setAttribute("allPrice", df.format(Double.parseDouble(price)));
        
        String result = "/source/page/details/settlement";
        return result;
	}
	
	
	
	/**
	 * ??????????????? ???????????????
	 * ???????????????  ???
	 * @throws Exception 
	 *      add by gxy 2016-5-03
	 */
	@RequestMapping(value="shoppingCar.html", method=RequestMethod.POST)
	public void shoppingCar(HttpServletRequest request,HttpServletResponse response){
		  Map<String, Object> m = new HashMap<String, Object>();
		  String assetArray[] = null;
		 List<Price> priceList = new ArrayList();
		  try{
		 //??????
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//?????????mm??????????????????  
		  Date begin_date = null;
          Date end_date = null;
		//??????ids
        String assetIds = request.getParameter("assetIds");
		String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String scanPeriod = request.getParameter("scanType");
        String serviceId = request.getParameter("serviceId");
        String createDate = DateUtils.dateToString(new Date());
       
        //String times = request.getParameter("buy_times");
        
        /****??????????????????????????????*****/
        //???????????????????????????
        if((assetIds==null||"".equals(assetIds))||(orderType==null||"".equals(orderType))||(beginDate==null||"".equals(beginDate))||(serviceId==null||"".equals(serviceId))){
       	  m.put("error",true);
       	  JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// ????????????????????????
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
        }
        
        //??????????????????id????????????
        Serv service = servService.findById(Integer.parseInt(serviceId));  
        if(service==null){
        	m.put("error",true);
        	JSONObject JSON = CommonUtil.objectToJson(response, m);
        	try {
        		// ????????????????????????
        		CommonUtil.writeToJsp(response, JSON);
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        	return;
        }
        
        //??????????????????????????????
        if(!checkOrderType(serviceId,orderType)){
        	m.put("error",true);
        	JSONObject JSON = CommonUtil.objectToJson(response, m);
        	try {
        		// ????????????????????????
        		CommonUtil.writeToJsp(response, JSON);
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        	return;
        }
        
        //??????
        if(orderType.equals("2")){
        	if(endDate!=null&&!"".equals(endDate)){
        		m.put("error",true);
        		JSONObject JSON = CommonUtil.objectToJson(response, m);
        		try {
        			// ????????????????????????
        			CommonUtil.writeToJsp(response, JSON);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		return;
        	}
        	
        	//????????????
        	if(scanPeriod!=null&&!"".equals(scanPeriod)){
        		m.put("error",true);
        		JSONObject JSON = CommonUtil.objectToJson(response, m);
        		try {
        			// ????????????????????????
        			CommonUtil.writeToJsp(response, JSON);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		return;
        	}
        }
   	 //??????
       if(orderType.equals("1")){
    	   if(beginDate==null||"".equals(beginDate)||endDate==null || "".equals(endDate)){
    		 m.put("error",true);
     	     JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// ????????????????????????
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
    	   }
	       //????????????
	  	   if(scanPeriod==null || "".equals(scanPeriod)){
	  		 m.put("error",true);
	   	     JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// ????????????????????????
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
	  	   }
       }
  	//???????????????????????????????????????
       if(beginDate!=null&&!"".equals(beginDate)&&endDate!=null&&!"".equals(endDate)){
    	   Date begin = DateUtils.stringToDateNYRSFM(beginDate);
           Date end = DateUtils.stringToDateNYRSFM(endDate);
           
           if(begin.getTime()>end.getTime()){
          	 m.put("error",true);
         	     JSONObject JSON = CommonUtil.objectToJson(response, m);
  				try {
  					// ????????????????????????
  					CommonUtil.writeToJsp(response, JSON);
  				} catch (IOException e) {
  					e.printStackTrace();
  				}
  				return;
           } 
       }
       //??????
       if(orderType.equals("1")){
    	   //??????????????????????????????
    	   List<ScanType> scanTypeList = scanTypeService.findScanType(Integer.parseInt(serviceId), Integer.parseInt(scanPeriod));
    	   if(scanTypeList.size() <= 0){
    		   m.put("error",true);
    		   JSONObject JSON = CommonUtil.objectToJson(response, m);
    		   try {
    			   // ????????????????????????
    			   CommonUtil.writeToJsp(response, JSON);
    		   } catch (IOException e) {
    			   e.printStackTrace();
    		   }
    		   return;
    	   }
       }
        /****??????????????????????????????*****/
        /****??????????????????*****/ 
        double calPrice = 0;
        //??????????????????
        long stimes = 0;
          //????????????id
			int serviceIdV = Integer.parseInt(serviceId);
			if(assetIds!=null&&!"".equals(assetIds)){
				assetArray=assetIds.split(",");
			}
			int assetCount = assetArray.length;
			if(assetCount<=0){
				assetCount=1;
			}
			//??????????????????
			if (orderType!= null && orderType.equals("1")){  //??????
				
				int scanType = Integer.valueOf(scanPeriod); 			//????????????
				stimes = calTimes(scanType, beginDate, endDate);	//????????????????????????????????????
				calPrice = calPrice(serviceIdV,scanType,stimes,assetCount);//????????????
				
			} else {	//??????
				priceList = priceService.findPriceByServiceId(serviceIdV,0);
				if (priceList != null && priceList.size() != 0){
					//priceList????????????????????????????????????????????????????????????
					Price price = priceList.get(0);  
					if (price.getType() == 0){
						calPrice = price.getPrice() * assetCount;
					}
				}
			}
			   //??????serviceid??????????????????
//			if(serviceIdV==3||serviceIdV==4||serviceIdV==5){
//				 if(scanPeriod!=null&&!"".equals(scanPeriod)&&!"".equals(orderType)&&orderType.equals("1")){
//					 priceList = priceService.findPriceByServiceId(serviceIdV,Integer.parseInt(scanPeriod));	 
//				 }else{
//					 priceList = priceService.findPriceByServiceId(serviceIdV,0);
//				 }
//			
//			}else{
//				priceList = priceService.findPriceByServiceId(serviceIdV,0);
//			}
	        //??????
//	        if(orderType!=null&&!"".equals(orderType)&&orderType.equals("1")){	
//	        	long ms = 0;//????????????????????????
//	        	Date bDate = null;
//	        	Date eDate = null;
//	        	if(beginDate!=null&&beginDate!=""&endDate!=null&&endDate!=""){
//	        		bDate = DateUtils.stringToDateNYRSFM(beginDate);
//		            eDate = DateUtils.stringToDateNYRSFM(endDate);  
//		            ms = DateUtils.getMsByDays(bDate, eDate);
//	        	}
//	            
//	            int typeInt = Integer.parseInt(scanPeriod);
//	            
//		        switch(serviceIdV){
//		        case 1:
//		        case 2:
//		        	if(ms==0){
//		        		stimes = 1;//????????????????????????
//		        	}else{
//		        		//??????
//			        	if(typeInt==5){
//			        		int perWeek = 1000*3600*24*7;
//			        		if(ms%perWeek>0){
//			        			stimes = (long)(ms/perWeek)+1;
//			        		}else{
//			        			stimes = (long)(ms/perWeek);
//			        		}		        		
//			        	}else{//??????
//			        		while(ms>0){
//			        			bDate = DateUtils.getDayAfterMonth(bDate);
//			        			ms = DateUtils.getMsByDays(bDate, eDate);
//			        			stimes++;
//			        		}
//			        	}	
//		        	}
//		        	break;
//		        	
//		      
//		        case 3:
//		        case 4:
//		        	if(ms==0){
//		        		stimes = 1;//????????????????????????
//		        	}else if(typeInt==2){//30??????
//			        	int min_30 = 1000*3600/2;
//			        	if(ms%min_30 > 0){
//			        		stimes = (long)(ms/min_30) + 1;
//			        	}else{
//			        		stimes = (long)(ms/min_30);
//			        	}
//		        	}else if(typeInt==3){//1??????
//		        		int oneHour = 1000*3600;
//			        	if(ms%oneHour > 0){
//			        		stimes = (long)(ms/oneHour) + 1;
//			        	}else{
//			        		stimes = (long)(ms/oneHour);
//			        	}
//		        	}else if(typeInt==4){//1???
//		        		int oneDay = 1000*3600*24;
//			        	if(ms%oneDay > 0){
//			        		stimes = (long)(ms/oneDay) + 1;
//			        	}else{
//				        	stimes = (long)(ms/oneDay);
//			        	}
//		        	}
//		        	break;
//		        	
//		        case 5:
//		        	if(ms==0){
//		        		stimes = 1;//????????????????????????
//		        	}else if(typeInt==1){//10??????
//		        		int min_10 = 1000*3600/6;
//			        	if(ms%min_10 > 0){
//			        		stimes = (long)(ms/min_10) + 1;
//			        	}else{
//			        		stimes = (long)(ms/min_10);
//			        	}
//		        	}else if(typeInt==2){//30??????
//		        		int min_30 = 1000*3600/2;
//			        	if(ms%min_30 > 0){
//			        		stimes = (long)(ms/min_30) + 1;
//			        	}else{
//			        		stimes = (long)(ms/min_30);
//			        	}
//		        	}else if(typeInt==3){//1??????
//		        		int oneHour = 1000*3600;
//			        	if(ms%oneHour > 0){
//			        		stimes = (long)(ms/oneHour) + 1;
//			        	}else{
//			        		stimes = (long)(ms/oneHour);
//			        	}
//		        	}     	
//		        	break;
//		        }
//		        if(priceList!=null && priceList.size()>0){
//				    for (int i = 0; i < priceList.size(); i++) {
//				    	Price onePrice = priceList.get(i);
//				        if(onePrice.getTimesG()!=0 && onePrice.getTimesLE()!=0){//??????
//				        	if(stimes>onePrice.getTimesG()&&stimes<=onePrice.getTimesLE()){
//				    				calPrice = onePrice.getPrice()*stimes*assetCount;
//				    			break;
//				    		}
//				        	if(serviceIdV==4){
//				        		if(stimes>=onePrice.getTimesG()&&stimes<=onePrice.getTimesLE()){
//				    				calPrice = onePrice.getPrice()*stimes*assetCount;
//				    			break;
//				    		}
//				        	}
//				        	if((serviceIdV==5||serviceIdV==3) && stimes==1 && onePrice.getTimesG()==1){//??????5????????????times==1??????????????????
//				        		calPrice = onePrice.getPrice()*assetCount;
//				    			break;
//				        	}
//				        }else if(onePrice.getTimesG()!=0 && onePrice.getTimesLE()==0 && stimes>onePrice.getTimesG()){//??????
//			        			calPrice = onePrice.getPrice()*stimes*assetCount;			    			
//			        			break;
//				        }else if(onePrice.getTimesG()==0 && onePrice.getTimesLE()==0 && stimes <= 1){//?????????
//				        	calPrice = onePrice.getPrice()*assetCount;
//				        	break;
//				        }
//
//				    }
//		        }else{
//		        	calPrice = 0;
//		        }
//	        }else{//??????
//	        	stimes = 1;
//	        	if(priceList!=null && priceList.size()>0){
//				    for (int i = 0; i < priceList.size(); i++) {
//				    	Price onePrice = priceList.get(i);
//				    	if(serviceIdV!=5){
//				    		 if(onePrice.getTimesG()==0 && onePrice.getTimesLE()==0){
//					        		//??????
//						        	calPrice = onePrice.getPrice()*assetCount;
//						        	break;
//					         }
//				    	}else{//??????5??????????????????
//				    		if(onePrice.getTimesG()==1){
//				        		//??????
//					        	calPrice = onePrice.getPrice()*assetCount;
//					        	break;
//				         }
//				    	}
//				       
//				    }
//	        	}else{
//	        		calPrice = 0;
//	        	}
//			}

			DecimalFormat df = new DecimalFormat("0.00"); 
          String  price = df.format(calPrice);
        /****??????????????????*****/ 
        
        Order order = new Order();
        
        SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
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
        if (serviceId.equals("1") && orderType.equals("1")) {//????????????
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
        order.setPayFlag(0);//?????????
        selfHelpOrderService.insertOrder(order);

        for(int i=0;i<assetArray.length;i++){
        	//?????????????????????
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
        
		  //???????????????
          Linkman linkObj = new Linkman();
        
          linkObj.setId(linkmanId);
          linkObj.setName(globle_user.getName());
          linkObj.setMobile(globle_user.getMobile());
          linkObj.setEmail(globle_user.getEmail());
          linkObj.setUserId(globle_user.getId());
          selfHelpOrderService.insertLinkman(linkObj);
        
   	     //?????????????????????
          List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
       //??????????????????API
		 List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		 List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0, "");
		 int carnum=shopCarList.size()+apiList.size()+sysList.size();
		 request.setAttribute("carnum", carnum);
		 
		   m.put("sucess", true);
		   JSONObject JSON = CommonUtil.objectToJson(response, m);
	        // ????????????????????????
        CommonUtil.writeToJsp(response, JSON);
		 //object?????????Json??????
	       
		  }catch(Exception e){
			  e.printStackTrace();
			  m.put("sucess", false);
			  
		  }
		
	}
	
	
	
	/**
	 * ??????????????? ???????????????
	 * ???????????????  ???
	 * @throws Exception 
	 *      add by gxy 2016-5-05
	 */
	@RequestMapping(value="showShopCar.html")
	public String showShopCar(HttpServletRequest request) throws Exception{
		boolean flag=false;
		 //??????
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	//???????????????????????????
		 List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
		 //??????????????????API
		 List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		 List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0,"");
		 if((shopCarList!=null&&shopCarList.size()>0)||(apiList!=null&&apiList.size()>0)||(sysList!=null&&sysList.size()>0)){
			 flag=true;
		 }
        request.setAttribute("shopCarList", shopCarList);
        request.setAttribute("apiList", apiList);
        request.setAttribute("sysList", sysList);
        request.setAttribute("flag", flag);
        request.setAttribute("user", globle_user);
	     String result = "/source/page/details/shoppingCart-order";
		return result;
	}

	/**
     * ??????????????? ???????????????
     * ??????????????? 
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
        //????????????id
        String orderId = request.getParameter("orderId");
        if(orderId==null&&"".equals(orderId)){
        	map.put("error", true);
            //object?????????Json??????
      		JSONObject JSON = CommonUtil.objectToJson(response, m);
      		try {
      			// ????????????????????????
      			CommonUtil.writeToJsp(response, JSON);
      		} catch (IOException e) {
      			e.printStackTrace();
      		}
      		return;
        }
        //???????????????
        orderService.delLinkmanByOrderId(orderId,globle_user.getId());
        //??????????????????
        orderAssetService.deleteOaByOrderId(orderId,globle_user.getId());
        //????????????api
        orderAPIService.deleteOrderAPI(orderId,globle_user.getId());
         //????????????
        orderService.deleteOrderById(orderId,globle_user.getId());
     
      //object?????????Json??????
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// ????????????????????????
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	 /**
	 * ??????????????? ???????????????
	 * ???????????????  ???
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
				// ???????????????????????????????????????????????????????????????????????????
				return "redirect:/index.html";
			}
			list = selfHelpOrderService.findBuyShopList(orderIdList, globle_user.getId());
			linkman = orderService.findLinkmanByOrderId(strArray[0]);
		} else {
			// ???????????????????????????????????????????????????????????????????????????
			return "redirect:/index.html";
		}

		// ????????????????????????
		if (!checkOrderDate(list)) {
			// ??????????????????????????????????????????
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
		// ??????????????????????????????
		request.setAttribute("shop", 0);
		String result = "/source/page/details/newSettlement";
		return result;
	}
	 /**
	 * ??????????????? ?????????????????????
	 * ???????????????  ???
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
		   //???????????????
		   String linkName = request.getParameter("linkName");
		   //???????????????
		   String linkMoblie = request.getParameter("linkMobile");
		   //???????????????
		   String linkEmail = request.getParameter("linkEmail");
		   if(str==null||"".equals(str)||linkName==null||"".equals(linkName)){
			   map.put("errorStatus", true);
			   map.put("errorMsg", "");
			   JSONObject JSON = CommonUtil.objectToJson(response, map);
		        // ????????????????????????
		           try {
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
		   }
		   //????????????
		   if(linkEmail!=null&&!"".equals(linkEmail)){
			   String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	           Pattern regex = Pattern.compile(check);
	           Matcher matcher = regex.matcher(linkEmail);
	           bflag = matcher.matches();
	           if(!bflag){
	        	   map.put("errorStatus", true);
	        	   map.put("errorMsg", "?????????????????????!");
	        	   JSONObject JSON = CommonUtil.objectToJson(response, map);
			        // ????????????????????????
			           try {
						CommonUtil.writeToJsp(response, JSON);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
	           }  
	          
		   }
		   //???????????????
		   if(linkMoblie==null||"".equals(linkMoblie)){
			   map.put("errorStatus", true);
			   map.put("errorMsg", "????????????????????????!");
			   JSONObject JSON = CommonUtil.objectToJson(response, map);
		        // ????????????????????????
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
           	  map.put("errorMsg", "???????????????????????????!");
           	  JSONObject JSON = CommonUtil.objectToJson(response, map);
 		        // ????????????????????????
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
		//?????????
		//String price = request.getParameter("countPrice");
	  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//?????????mm??????????????????  
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
		        // ????????????????????????
		           try {
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
	      }
	      
	      //????????????????????????
	      if(!checkOrderDate(list)) {
	    	  map.put("errorStatus", true); 
	    	  map.put("errorMsg", "");
	    	  JSONObject JSON = CommonUtil.objectToJson(response, map);
		        // ????????????????????????
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
	    
	   //???????????????????????????id
	    
	     if(shopList!=null&&shopList.size()>0){
	    	 for(int i=0;i<shopList.size();i++){
	    		 ShopCar shopCar = (ShopCar)shopList.get(i);
	    		 String targetURL[]=shopCar.getAstName().split(",");
	    		 String websoc = "0";
	    		 String[] customManu = null;//????????????
   	         if(websoc != null && websoc != ""){
   	        	customManu = websoc.split(","); //???????????????"," ,???????????????????????????customManu 
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
			// ??????api?????????
			if (shopAPIList != null && shopAPIList.size() > 0) {
				for (int i = 0; i < shopAPIList.size(); i++) {
					ShopCar shopCar = (ShopCar) shopAPIList.get(i);
					String targetURL[] = null;
					String websoc = "0";
					String[] customManu = null;// ????????????
					if (websoc != null && websoc != "") {
						customManu = websoc.split(","); // ???????????????","
														// ,???????????????????????????customManu
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
			
			  //???????????????order_list
		    OrderList ol = new OrderList();
		    //????????????id
//		    id = String.valueOf(Random.eightcode());
			SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
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

   	//object?????????Json??????
	   map.put("orderListId", id);
      JSONObject JSON = CommonUtil.objectToJson(response, map);
       // ????????????????????????
          try {
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * ?????????????????????????????????
	 * ???????????????  ???
	 * @throws Exception 
	 *      add by gxy 2016-5-10
	 */
	@RequestMapping(value="orderBack.html", method=RequestMethod.POST)
	public String  orderBack(HttpServletResponse response,HttpServletRequest request) throws Exception{
		 Map<String, Object> map = new HashMap<String, Object>();
		User globle_user = (User) request.getSession().getAttribute("globle_user");
	   /* boolean apiFlag=false;
		String result="";
		
		//??????ids
        String assetIds = request.getParameter("assetIds");
        String orderDetailId = request.getParameter("orderDetailId");
		String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String scanType = request.getParameter("scanType");
        String type = request.getParameter("type");
        String apiVal=request.getParameter("apiId");
        String price = request.getParameter("price");
        String serviceId = request.getParameter("serviceId");
        String num=request.getParameter("num");
        String assetNames = request.getParameter("assetNames");
        
		// assetAddr = assetAddr.substring(0, assetAddr.length()-1);
		if (apiVal != null && !"".equals(apiVal)) {
			int apiId = Integer.parseInt(apiVal);
			// ??????id??????serviceAPI, add by tangxr 2016-3-28
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
	            assetArray = assetIds.split(","); //???????????????"," ,???????????????????????????strArray 
            	for(int i=0;i<assetArray.length;i++){
                	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]),globle_user.getId());
                	assetAddr = assetAddr + asset.getAddr()+",";
                }
	        	//??????id??????service add by tangxr 2016-3-14
	    	    Serv service = servService.findById(Integer.parseInt(serviceId));
	    	    request.setAttribute("service", service);
	        	result = "/source/page/details/vulnScanDetails";	
	        }
	    }
        //????????????????????????
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
	    //?????????????????????
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //??????????????????API
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
        request.setAttribute("assetNames", assetNames);
        return result;*/
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
			// ??????id??????serviceAPI, add by tangxr 2016-3-28
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
			} else if (apiId == 9) {
				result = "/source/page/details/apiDetails9";
			}
		}
        if(assetIds!=null&&!"".equals(assetIds)){
	    	   assetArray = assetIds.split(","); //???????????????"," ,???????????????????????????strArray 
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
	          assetArray = assetIds.split(","); //???????????????"," ,???????????????????????????strArray 
            	for(int i=0;i<assetArray.length;i++){
                	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]),globle_user.getId());
                	assetAddr = assetAddr + asset.getAddr()+",";
                }
	        	//??????id??????service add by tangxr 2016-3-14
	    	    Serv service = servService.findById(orderDetail.getServiceId());
	    	    //??????service Id????????????????????????
	    	    ServiceDetail servDetail = servDetailService.findByServId(orderDetail.getServiceId());
	    	    request.setAttribute("service", service);
	    	    request.setAttribute("servDetail", servDetail);
	    	    //???????????????????????????
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
	    //????????????????????????
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
	    //?????????????????????
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //??????????????????API
		List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		//??????????????????
		//??????????????????
	    List<ScanType> scanTypeList = scanTypeService.findScanTypeById(orderDetail.getServiceId());
	 // ?????????????????????
	 		List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0, "");
	 		int carnum = shopCarList.size() + apiList.size() + sysList.size();
		request.setAttribute("carnum", carnum);  
		request.setAttribute("assetIds", assetIds);  
		request.setAttribute("orderDetail", orderDetail);
		request.setAttribute("scanTypeList", scanTypeList);  
		request.setAttribute("serviceAssetList", serviceAssetList);
		return result;
	}
	
    /**
     * ??????????????? ??????????????????
     * ??????????????? 
	 * @throws Exception 
	 *  add  zhang_shaohua
     *       @time 2016-11-3
     */
    @RequestMapping(value="calPrice.html", method=RequestMethod.POST)
    @ResponseBody
	public void calPrice(HttpServletResponse response,HttpServletRequest request){
    	Map<String, Object> m = new HashMap<String, Object>();
    	//??????
		double calPrice = 0;
        //??????????????????
        long times = 0;
        
        try{
        	//????????????id
			int serviceId = Integer.parseInt(request.getParameter("serviceId"));
			String type = request.getParameter("type");				//????????????
			String beginDate = request.getParameter("beginDate");
			String endDate = request.getParameter("endDate");
			int assetCount = Integer.parseInt(request.getParameter("assetCount"));
			String orderType = request.getParameter("orderType");   //?????????  ??????:2/??????:1
			
			//???????????????????????????
//			synPriceData(serviceId);
			
			
			//??????????????????
			if (orderType!= null && orderType.equals("1")){  //??????
				
	        	int scanType = Integer.valueOf(type); 			//????????????
				times = calTimes(scanType, beginDate, endDate);	//????????????????????????????????????
				calPrice = calPrice(serviceId,scanType,times,assetCount);//????????????
				
			} else {	//??????
				  List<Price> priceList = priceService.findPriceByServiceId(serviceId,0);
				  if (priceList != null && priceList.size() != 0){
					  //priceList????????????????????????????????????????????????????????????
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
        	//object?????????Json??????
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// ????????????????????????
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
	}
    
    /**
     * ???????????????????????????????????????
     * */
    private void synPriceData(int serviceId) {
    	try {
	        //????????????????????????
			String url = SERVER_WEB_ROOT + VulnScan_servicePrice;
			System.out.println("****???????????????????????????????????????****");  
	        WebTarget target = ContextClient.mainClient.target(url);
	        Response response = target.request().post(Entity.entity(null, MediaType.APPLICATION_JSON));
	        String str = (String)response.readEntity(String.class);
	        response.close();
			
			//??????json,??????????????????
			JSONObject jsonObject = JSONObject.fromObject(str);			
			JSONArray jsonArray = jsonObject.getJSONArray("PriceStr");
	        if(jsonArray!=null && jsonArray.size()>0){
		        //????????????
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
			        if(typeJson != 0) {   //0:?????????1????????????2?????????
			        	newprice.setTimesG(timesGJson);
			        	newprice.setTimesLE(timesLEJson);
			        }
			        newprice.setPrice(priceJson);
			        if (scanTypeJson !=0) {  //???????????????????????????????????????????????????????????????null
			        	newprice.setScanType(scanTypeJson);
			        }
			        
			        priceService.insertPrice(newprice);
			    }
	        }
	        
		} catch (Exception e1) {
			System.out.println("?????????????????????!");
		}
    }
	
	
    /**
     * ??????????????????????????????????????????
     * */
    private long calTimes(int scanType,String beginDate, String endDate){
    	long times = 0; 
    	long ms = 0;//????????????????????????
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
    	case 1:		//10??????
    		int min_10 = 1000*3600/6;
        	if(ms%min_10 > 0){
        		times = (long)(ms/min_10) + 1;
        	}else{
        		times = (long)(ms/min_10);
        	}
    		break;
    	case 2:		//30??????
    		int min_30 = 1000*3600/2;
        	if(ms%min_30 > 0){
        		times = (long)(ms/min_30) + 1;
        	}else{
        		times = (long)(ms/min_30);
        	}
    		break;
    	case 3:		//1??????
    		int oneHour = 1000*3600;
        	if(ms%oneHour > 0){
        		times = (long)(ms/oneHour) + 1;
        	}else{
        		times = (long)(ms/oneHour);
        	}
    		break;
    	case 4:		//1???
    		int oneDay = 1000*3600*24;
        	if(ms%oneDay > 0){
        		times = (long)(ms/oneDay) + 1;
        	}else{
	        	times = (long)(ms/oneDay);
        	}
    		break;
    	case 5:		//??????
    		int perWeek = 1000*3600*24*7;
    		if(ms%perWeek>0){
    			times = (long)(ms/perWeek)+1;
    		}else{
    			times = (long)(ms/perWeek);
    		}
    		break;
    	case 6:		//??????
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
    	
    	//??????serviceid??????????????????
    	List<Price> priceList = priceService.findPriceByServiceId(serviceId,scanType);
    	if (priceList == null|| priceList.size() == 0){		//???????????????????????????????????????????????????????????????
    		priceList = priceService.findPriceByScanTypeNull(serviceId);
    	}
    	
    	//???????????????????????????
    	if (priceList == null|| priceList.size() == 0){
    		return sumPrice;
    	}
    	
		for (int i = 0; i < priceList.size(); i++) {
			Price price = priceList.get(i);
			if(price.getType()== 1 && times > price.getTimesG() && times <= price.getTimesLE()){  //??????
				sumPrice = price.getPrice()*times*assetCount;
				break;
			}else if (price.getType()== 2 && times>price.getTimesG()){  //??????
				sumPrice = price.getPrice()*times*assetCount;
				break;
			}
		}
		
		if (sumPrice == 0){   
			//???????????????Id=1,times=1???,??????????????????????????????
			//     ??????Id=4,times=1???,???????????????????????????????????????
			sumPrice = priceList.get(0).getPrice()*times*assetCount;
		}
		
		
		return sumPrice;
    	
    }
    
    //@RequestMapping(value="calPrice.html", method=RequestMethod.POST)
   // @ResponseBody
//    public void calPrice(HttpServletResponse response,HttpServletRequest request) throws Exception{
//		Map<String, Object> m = new HashMap<String, Object>();
//		//??????
//		double calPrice = 0;
//        //??????????????????
//        long times = 0;
//        List<Price> priceList = new ArrayList();
//    	try {
//			//????????????id
//			int serviceId = Integer.parseInt(request.getParameter("serviceId"));
//			String type = request.getParameter("type");				//????????????
//			String beginDate = request.getParameter("beginDate");
//			String endDate = request.getParameter("endDate");
//			int assetCount = Integer.parseInt(request.getParameter("assetCount"));
//			String orderType = request.getParameter("orderType");   //0:???????????????,1:??????,2:??????
//			String str;
//			try {
//				//??????????????????
//				ClientConfig config = new DefaultClientConfig();
//				//??????????????????????????????
//				Client client = Client.create(config);
//				//???????????????
//				String url = SERVER_WEB_ROOT + VulnScan_servicePrice;
//				WebResource service = client.resource(url+serviceId);
//				ClientResponse clientResponse = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);        
//				str = clientResponse.getEntity(String.class);
//				
//				//??????json,??????????????????
//				JSONObject jsonObject = JSONObject.fromObject(str);			
//				JSONArray jsonArray = jsonObject.getJSONArray("PriceStr");
//		        if(jsonArray!=null && jsonArray.size()>0){
//			        //????????????
//			        priceService.delPrice(serviceId);
//				    for (int i = 0; i < jsonArray.size(); i++) {
//				        String object = jsonArray.getString(i);
//				        JSONObject jsonObject1 = JSONObject.fromObject(object);
//				        int idJson = Integer.parseInt(jsonObject1.getString("id"));
//				        int serviceIdJson = Integer.parseInt(jsonObject1.getString("serviceId"));
//				        int typeJson = Integer.parseInt(jsonObject1.getString("type"));
//				        double priceJson = Double.parseDouble(jsonObject1.getString("price"));
//				        int timesGJson = Integer.parseInt(jsonObject1.getString("timesG"));
//				        int timesLEJson = Integer.parseInt(jsonObject1.getString("timesLE"));
//				        int scanTypeJson = Integer.parseInt(jsonObject1.getString("scanType"));
//				       
//				        Price newprice = new Price();
//				        newprice.setServiceId(serviceIdJson);
//				        newprice.setType(typeJson);
//				        if(typeJson != 0) {   //0:?????????1????????????2?????????
//				        	newprice.setTimesG(timesGJson);
//				        	newprice.setTimesLE(timesLEJson);
//				        }
//				        newprice.setPrice(priceJson);
//				        if (scanTypeJson !=0) {  //???????????????????????????????????????????????????????????????null
//				        	newprice.setScanType(scanTypeJson);
//				        }
//				        
//				        priceService.insertPrice(newprice);
//				    }
//		        }
//		        
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				//e1.printStackTrace();
//				System.out.println("?????????????????????!");
//			}     
//			
//
//	        
//		
//			//??????????????????
//	        //??????serviceid??????????????????
//			if(serviceId==3||serviceId==4||serviceId==5){
//				 if(type!=null&&!"".equals(type)&&orderType!=null&&!"".equals(orderType)&&orderType.equals("1")){
//					 priceList = priceService.findPriceByServiceId(serviceId,Integer.parseInt(type));	 
//				 }else{
//					 priceList = priceService.findPriceByServiceId(serviceId,0);
//				 }
//			
//			}else{
//				priceList = priceService.findPriceByServiceId(serviceId,0);
//			}
//			
//	        //??????
//	        if(type!=null){	
//	        	long ms = 0;//????????????????????????
//	        	Date bDate = null;
//	        	Date eDate = null;
//	        	if(beginDate!=null&&beginDate!=""&endDate!=null&&endDate!=""){
//	        		bDate = DateUtils.stringToDateNYRSFM(beginDate);
//		            eDate = DateUtils.stringToDateNYRSFM(endDate);  
//		            ms = DateUtils.getMsByDays(bDate, eDate);
//	        	}
//	            
//	            int typeInt = Integer.parseInt(type);
//	            
//		        switch(serviceId){
//		        case 1:
//		        case 2:
//		        	if(ms==0){
//		        		times = 1;//????????????????????????
//		        	}else{
//		        		//??????
//			        	if(typeInt==5){
//			        		int perWeek = 1000*3600*24*7;
//			        		if(ms%perWeek>0){
//			        			times = (long)(ms/perWeek)+1;
//			        		}else{
//			        			times = (long)(ms/perWeek);
//			        		}		        		
//			        	}else{//??????
//			        		while(ms>0){
//			        			bDate = DateUtils.getDayAfterMonth(bDate);
//			        			ms = DateUtils.getMsByDays(bDate, eDate);
//			        			times++;
//			        		}
//			        	}	
//		        	}
//		        	break;
//		        	
//		       
//		        case 3:
//		        case 4:
//		        	if(ms==0){
//		        		times = 1;//????????????????????????
//		        	}else if(typeInt==2){//30??????
//			        	int min_30 = 1000*3600/2;
//			        	if(ms%min_30 > 0){
//			        		times = (long)(ms/min_30) + 1;
//			        	}else{
//			        		times = (long)(ms/min_30);
//			        	}
//		        	}else if(typeInt==3){//1??????
//		        		int oneHour = 1000*3600;
//			        	if(ms%oneHour > 0){
//			        		times = (long)(ms/oneHour) + 1;
//			        	}else{
//			        		times = (long)(ms/oneHour);
//			        	}
//		        	}else if(typeInt==4){//1???
//		        		int oneDay = 1000*3600*24;
//			        	if(ms%oneDay > 0){
//			        		times = (long)(ms/oneDay) + 1;
//			        	}else{
//				        	times = (long)(ms/oneDay);
//			        	}
//		        	}
//		        	break;
//		        	
//		        case 5:
//		        	if(ms==0){
//		        		times = 1;//????????????????????????
//		        	}else if(typeInt==1){//10??????
//		        		int min_10 = 1000*3600/6;
//			        	if(ms%min_10 > 0){
//			        		times = (long)(ms/min_10) + 1;
//			        	}else{
//			        		times = (long)(ms/min_10);
//			        	}
//		        	}else if(typeInt==2){//30??????
//		        		int min_30 = 1000*3600/2;
//			        	if(ms%min_30 > 0){
//			        		times = (long)(ms/min_30) + 1;
//			        	}else{
//			        		times = (long)(ms/min_30);
//			        	}
//		        	}else if(typeInt==3){//1??????
//		        		int oneHour = 1000*3600;
//			        	if(ms%oneHour > 0){
//			        		times = (long)(ms/oneHour) + 1;
//			        	}else{
//			        		times = (long)(ms/oneHour);
//			        	}
//		        	}
//		        			        	
//		        	break;
//		        }
//		        if(priceList!=null && priceList.size()>0){
//				    for (int i = 0; i < priceList.size(); i++) {
//				    	Price onePrice = priceList.get(i);
//				        if(onePrice.getTimesG()!=0 && onePrice.getTimesLE()!=0){//??????
//				        	if(times>onePrice.getTimesG()&&times<=onePrice.getTimesLE()){
//				    				calPrice = onePrice.getPrice()*times*assetCount;
//				    			break;
//				    		}
//				        	if(serviceId==4){
//				        		if(times>=onePrice.getTimesG()&&times<=onePrice.getTimesLE()){
//				    				calPrice = onePrice.getPrice()*times*assetCount;
//				    			break;
//				    		}
//				        	}
//				        	if((serviceId==5||serviceId==3) && times==1 && onePrice.getTimesG()==1){//??????5????????????times==1??????????????????
//				        		calPrice = onePrice.getPrice()*assetCount;
//				    			break;
//				        	}
//				        }else if(onePrice.getTimesG()!= 0 && onePrice.getTimesLE()==0 && times>onePrice.getTimesG()){//??????
//			        			calPrice = onePrice.getPrice()*times*assetCount;			    			
//			        			break;
//				        }else if(onePrice.getTimesG()==0 && onePrice.getTimesLE()==0 && times <= 1){//?????????
//				        	calPrice = onePrice.getPrice()*assetCount;
//				        	break;
//				        }
//
//				    }
//		        }else{
//		        	calPrice = 0;
//		        }
//	        }else{//??????
//	        	times = 1;
//	        	if(priceList!=null && priceList.size()>0){
//				    for (int i = 0; i < priceList.size(); i++) {
//				    	Price onePrice = priceList.get(i);
//				    	if(serviceId!=5){
//				    		 if(onePrice.getTimesG()==0 && onePrice.getTimesLE()==0){
//					        		//??????
//						        	calPrice = onePrice.getPrice()*assetCount;
//						        	break;
//					         }
//				    	}else{//??????5??????????????????
//				    		if(onePrice.getTimesG()==1){
//				        		//??????
//					        	calPrice = onePrice.getPrice()*assetCount;
//					        	break;
//				         }
//				    	}
//				       
//				    }
//	        	}else{
//	        		calPrice = 0;
//	        	}
//			}
//
//			DecimalFormat df = new DecimalFormat("0.00"); 
//
//			m.put("success", true);
//			m.put("price", df.format(calPrice));
//			m.put("times", times);
//      
//			//object?????????Json??????
//			JSONObject JSON = CommonUtil.objectToJson(response, m);
//			try {
//				// ????????????????????????
//				CommonUtil.writeToJsp(response, JSON);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			m.put("success", false);
//			//object?????????Json??????
//			JSONObject JSON = CommonUtil.objectToJson(response, m);
//			try {
//				// ????????????????????????
//				CommonUtil.writeToJsp(response, JSON);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
//    }
	 /**
	 * ??????????????? ??????????????????????????????????????????????????????????????????/?????????????????????????????????7???
	 * ???????????????  ???
	 *     @time 2016-05-23
	 *     add gxy
	 */
	@RequestMapping(value="checkShoppOrder.html", method=RequestMethod.POST)
	public void checkOrderInfo(HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
		
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		//?????????????????????
		//?????????????????????
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	//?????????????????????????????????
    	boolean userStatus = true;
    	if(userId != globle_user.getId()){
    		userStatus = false;
    	}
    	m.put("userStatus", userStatus);
    	if(!userStatus){
    		 //object?????????Json??????
    	       JSONObject JSON = CommonUtil.objectToJson(response, m);
    	       try {
    	           // ????????????????????????
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
	     //??????????????????????????????????????????????????????????????????????????????????????????????????????
	      List orderList = orderService.findPaidSysOrderByUserId(globle_user.getId());
	      int countserid7=0;
		  int countserid8=0;
		  int countserid9=0;
		 
			
			
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ShopCar shopCar = (ShopCar) list.get(i);
					
					if (shopCar.getIsAPI() == 3) {
						if (orderList.size() > 0 && orderList != null) {
							for (int j = 0; j < orderList.size(); j++) {

								HashMap<String, Object> orderMap = (HashMap<String, Object>) orderList.get(j);
								String strBeginDate = orderMap.get("begin_date").toString();
								String strEndDate = orderMap.get("end_date").toString();
								String strNowDate = DateUtils.dateToString(new Date());
								String ServiceId = orderMap.get("serviceId").toString();
								int intserviceId = Integer.parseInt(ServiceId);
								String shopCarServiceId = String.valueOf(shopCar.getServiceId());
								System.out.println(strBeginDate + "  " + strEndDate + "  " + strNowDate + "  " + ServiceId
										+ "  " + shopCarServiceId);
								if (intserviceId != 10) {
									if (strNowDate.compareTo(strBeginDate) > 0 && strNowDate.compareTo(strEndDate) < 0
											&& ServiceId.equals(shopCarServiceId)) {
										System.out.println(strNowDate.compareTo(strBeginDate) > 0);
										System.out.println(strNowDate.compareTo(strEndDate) < 0);
										System.out.println(ServiceId.equals(shopCarServiceId));
										shopCar.setStatus(-1);
										selfHelpOrderService.updateShopOrder(shopCar);
										flag = false;
										status = -2;
									}
								}
								
								
									

								

							}
						} 
						//????????????????????????????????????????????????????????????????????????????????????????????????1??????????????????
						
						if(shopCar.getServiceId()==7){
							countserid7++;
						}
						if(shopCar.getServiceId()==8){
							countserid8++;
						}
						if(shopCar.getServiceId()==9){
							countserid9++;
						}
						
					}
				}
			}
			if(countserid7>1 || countserid8>1 ||countserid9>1){
				status=-3;
				flag=false;
			}

	   //object?????????Json??????
	         m.put("flag", flag);
	         m.put("status", status);
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// ????????????????????????
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object?????????Json??????
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// ????????????????????????
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	
		
	}
	
	/**  ??????????????????????????????????????? 
	 * true:???????????????????????? false??????????????????????????????
	 */
	private boolean checkOrderDate(List list){
		boolean flag = true;
		Date date = new Date();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				ShopCar shopCar = (ShopCar)list.get(i);
				Date endDate = shopCar.getEndDate();
				//????????????????????????????????????????????????
				if(endDate!=null && !"".equals(endDate) && date.getTime()>endDate.getTime()){
					
					flag=false;
					//???????????????????????????
					shopCar.setStatus(-1);
					selfHelpOrderService.updateShopOrder(shopCar);
				//?????????????????????????????????7???
				}else if (date.getTime() - shopCar.getCreateDate().getTime() > 1000*60*60*24*7) {
					flag=false;
					//???????????????????????????
					shopCar.setStatus(-1);
					selfHelpOrderService.updateShopOrder(shopCar);
				}
			}	 
		}
		
		return flag;
	}
    
    /**
     * ??????????????? ???????????????
     * */
    @RequestMapping("cashierUI.html")
    public String cashier(Model model, HttpServletRequest request){
    	String orderListId = request.getParameter("orderListId");//????????????(cs_order_list???id)
    	String renew = request.getParameter("renew");
    	String payflag = request.getParameter("payId"); // ????????????
    	//??????orderId,????????????,????????????
    	OrderList orderList = orderListService.findById(orderListId);
    	
    	//???????????????????????????,??????????????????????????????
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	if (orderList== null || orderList.getUserId()!= globle_user.getId()) {
    		return "redirect:/index.html";
    	}
    	
    	//??????????????????????????????
    	DecimalFormat df = new DecimalFormat("0.00");
    	String priceStr = df.format(orderList.getPrice());
    	
    	//????????????????????????????????????
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

    	//???????????????
		List<User> userList = userService.findUserById(orderList.getUserId());
        String balance = df.format(userList.get(0).getBalance());
    	
        model.addAttribute("orderList", orderList);
        model.addAttribute("price", priceStr);
        model.addAttribute("serverName", serverNameMap);
        model.addAttribute("balance",balance);
        model.addAttribute("renew",renew);
        model.addAttribute("pay", payflag);
    	return "source/page/details/shoppingcashier-desk2";
    }
    
    /**
     * ??????????????? ????????????
     * */
    @RequestMapping(value="payConfirm.html", method=RequestMethod.POST)
    public void payConfirm(HttpServletRequest request, HttpServletResponse response){
    	Map<String, Object> m = new HashMap<String, Object>();
    	
    	/*AlipayConfig aConfig = new AlipayConfig();
    	try {
			aConfig.doPost(request, response);
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	*/	
    	try {
    		String orderListId = request.getParameter("orderListId");//????????????(cs_order_list???id)
    		//??????????????????
    		String renew = request.getParameter("renew");
    		OrderList orderList = orderListService.findById(orderListId);
    		if (orderList == null) {
    			m.put("payFlag", 1);
    			return;
    		}
    		
    		//???????????????????????????,????????????
        	User globle_user = (User) request.getSession().getAttribute("globle_user");
        	if (orderList.getUserId()!= globle_user.getId()) {
        		m.put("payFlag", 2);
    			return;
        	}
        	
    		Double price = orderList.getPrice();//????????????
    		//????????????????????????????????????
    		if("".equals(renew)){
    			if (orderList.getPay_date() != null){
    				//??????????????????
    				m.put("payFlag", 1);
    				return;	
    			}
    		}
    		
//    		Date orderCreateDate = orderList.getCreate_date();//????????????
    		
    		//?????????????????????
//    		User globle_user = (User) request.getSession().getAttribute("globle_user");
    		List<User> userList = userService.findUserById(globle_user.getId());
    		Double balance = userList.get(0).getBalance();
    		//?????????????????????
    		if (balance < price){
    			//?????????????????????
    			m.put("orderListId",orderList.getId());
    			m.put("payFlag", 3);
    			return;
    		}
    		
    		//??????????????????
    		orderList.setPay_date(new Date());
    		
    		//???????????????>??????????????????????????????????????????????????????????????????
    		List<String> orderIdOfModify = new ArrayList<String>();
    		try{
    			orderIdOfModify = modifyOrderBeginTime(orderList,request);
    		} catch(Exception e) {
    			e.printStackTrace();
    			//???????????????????????????????????????????????????????????????
    			if (e.getMessage().equals("????????????!")) {
    				m.put("payFlag", 5);//????????????
    			}
    			return;
    		}
    			
    		// ??????API?????? ????????????
    		if(!orderTask(orderList, globle_user, orderIdOfModify,renew)) {
    			try{
    				m.put("payFlag", 4);
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    			return;
    		}
    		
    		//????????????????????????DB???session???????????????
    		globle_user.setBalance(balance - price);//session??????
    		User user = new User();
    		user.setId(globle_user.getId());
    		user.setBalance(globle_user.getBalance());
    		userService.updateBalance(user);//DB??????
    		
    		//?????? ???????????????????????????(cs_order_list???)
    		//orderList.setPay_date(new Date());
    		selfHelpOrderService.updatePayDate(orderList);
    		
    		//?????? ??????Flag(cs_order???) ?????????-->?????????
    		String orderIds = orderList.getOrderId();//??????????????????(cs_order???id)
    		String strArrayId[] = orderIds.split(",");
    		System.out.println("updateOrderPayFlag============"+orderIds);
    		int payflag = 1;
    		for (int i = 0; i < strArrayId.length; i++) {
				selfHelpOrderService.updateOrderPayFlag(strArrayId[i], payflag);
			}
    		
    		//???????????????????????????????????????
    		String orderId = "";
    		for(String id: orderIdOfModify){
    			orderId = orderId+ id +",";
    		}
    		if (orderId!= null && !orderId.equals("")) {
    			orderId = orderId.substring(0,orderId.length()-1);
    		}
    		m.put("orderListId",orderList.getId());
    		m.put("modifyOrderId", orderId);//????????????????????? orderId
    		m.put("payFlag", 0);//????????????
    	} catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		//object?????????Json??????
    		JSONObject JSON = CommonUtil.objectToJson(response, m);
    		try {
    			// ????????????????????????
    			CommonUtil.writeToJsp(response, JSON);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    }
    /**
     * ??????????????? ????????????
     * */
    @RequestMapping(value="aliPayConfirm.html", method=RequestMethod.POST)
    public void AlipayConfirm(HttpServletRequest request, HttpServletResponse response){
    	AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
    			AlipayConfig.app_id, 
    			AlipayConfig.merchant_private_key, 
    			"json", 
    			AlipayConfig.charset, 
    			AlipayConfig.alipay_public_key, 
    			"RSA2");
    	//???????????????API?????????request???,??????????????????????????????,??????}
    	
    	//alipayClient.
    	AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//??????API?????????request
    	//alipayRequest.setReturnUrl(returnUrl);
    	//String returnUrl = "http://localhost:8080/csp/cashierUI.html";
    	//String notifyUrl = "http://localhost:8080/csp/payConfirm.html";
    	
    	alipayRequest.setReturnUrl(AlipayConfig.return_url);
    	alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
    	 alipayRequest.setBizContent("{" +
    		        "    \"out_trade_no\":\"20150320010101001\"," +
    		        "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
    		        "    \"total_amount\":88.88," +
    		        "    \"subject\":\"Iphone6 16G\"," +
    		        "    \"body\":\"Iphone6 16G\"," +
    		        "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
    		        "    \"extend_params\":{" +
    		        "    \"sys_service_provider_id\":\"2088511833207846\"" +
    		        "    }"+
    		        "  }");//??????????????????
    	 
    	 String form="";
    	    try {
    	        form = alipayClient.pageExecute(alipayRequest).getBody(); //??????SDK????????????
    	    } catch (AlipayApiException e) {
    	        e.printStackTrace();
    	    }
    	    
    	    
    	    try {
    	    	response.setContentType("text/html;charset=" + AlipayConfig.charset);
				response.getWriter().write(form);
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//????????????????????????html???????????????
    	   
    	
    }
    
    
    
    /**
     * ??????????????????????????????>??????????????????????????????????????????????????????????????????
     * @return  ???????????????????????????(????????????)
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
//		Date payDate = new Date();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				ShopCar shopCar = (ShopCar)list.get(i);
				Date beginDate = shopCar.getBeginDate();
				Date endDate = shopCar.getEndDate();
				//????????????????????????
				//???????????????????????????????????????????????????????????????
				if (endDate!= null && !DateUtils.dateToString(endDate).equals("")&& new Date().getTime()>endDate.getTime()) {
					Exception e = new Exception("????????????!");
					throw e;
				}else if (new Date().getTime() - shopCar.getCreateDate().getTime() > 1000*60*60*24*7) {
					Exception e = new Exception("????????????!");
					throw e;
				}
				
				if (beginDate.getTime() < payDate.getTime()){
					shopCar.setBeginDate(payDate);
					if (endDate != null){
						long endDateLong = endDate.getTime() + 
							(payDate.getTime() - beginDate.getTime());
						shopCar.setEndDate(new Date(endDateLong));
					}
					
					//??????order??? ???????????????????????????
					selfHelpOrderService.updateOrderDate(shopCar);
					if (shopCar.getIsAPI()==1) {
						//??????order_api??? ???????????????????????????
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
    		String scanType = "";//??????????????????????????????????????????
    		String scanDepth = "";//????????????
    		String maxPages = "";//???????????????
    		String stategy = "";//??????
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//?????????mm??????????????????  
    		List list = new ArrayList();
    		List shopAPIList = new ArrayList();
    		List shopSysList = new ArrayList();
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
    			for (int i = 0; i < strArray.length; i++) {
    				
    				List orderHashList = orderService.findByOrderId(orderIdList.get(i));
    				 // <select id="findOrderByOrderId" resultType="hashmap" parameterType="String">
        				//	select o.id,o.serviceId,o.type,s.name,s.parentC,o.begin_date,o.end_date,o.create_date,o.scan_type,o.status,o.websoc,o.payFlag,o.isAPI,o.userId
        				//	from cs_order o,cs_service s
        					//where o.id = #{orderId} and o.serviceId = s.id
    					//</select>
    				
    				if (orderHashList == null ||orderHashList.size() == 0) {
    		    		return false;
    		    	}
    				HashMap<String, Object> order=new HashMap<String, Object>();
    			    order=(HashMap) orderHashList.get(0);
    			    int serviceId=0;
    			    serviceId=(Integer) order.get("serviceId");
    			    int isAPI=0;
    			    isAPI = (Integer)order.get("isAPI");
    			    if ((serviceId == 7|| serviceId==8||serviceId==9 ||serviceId==10)&&isAPI==3) {//??????????????????????????????  ??????
    			    	User loginUser = userService.findUserById(globle_user.getId()).get(0);
    			    	Date createDate = (Date)order.get("create_date");
    			    	Date  beginDate =(Date)order.get("begin_date");
    			    	String orderId = (String)order.get("id");
    			    	status = status+order.get("status");
    			    	Integer userid = new Integer(globle_user.getId());
    			    	int scanTypeint = (Integer)order.get("scan_type");
    			    	Integer scanTypeInteger = new Integer(scanTypeint);
    			    	Linkman linkman = orderService.findLinkmanByOrderId(orderId);  
    			    	String portMessage=(String)order.get("remarks");
    			    	
    			    	if (orderId != null && !"".equals(orderId)) {
        					// ?????????????????????
        					//selfHelpOrderService.updateOrderAPI(
        						//	shopCar.getOrderId(), orderId);
        					// ???????????????   UPDATE  cs_order o  
    			    		//void updateOrder(String orderId,String newOrderId,String isAPI,String status, String orderListId, Date creatDate);
    			    		// ??????????????? isAPI???3
    			    		if (serviceId == 8) {// ????????????
    			    			String strTeString = "test111996";
								String strResString = SysWorker.getJinshanCreateOrder(orderId+userid.toString(), loginUser.getCompany(), scanTypeInteger.toString());
								if (!strResString.equals("success")) {
									result = false; // ????????????????????????
									System.out.println("????????????????????????");
								} 
								else {
									System.out.println("????????????????????????" + orderId + " userid:"+userid.toString());
									String strUninstallPasswd = SysWorker.getJinshanUninstallInfo(orderId+userid.toString());
									selfHelpOrderService.updateSysOrder(orderId,orderId, "3",status,orderList.getId(),orderList.getPay_date(),strUninstallPasswd);
									 orderVal = orderVal+ orderId+",";
								}
								System.out.println("url:"+SysWorker.getJinshanoauthurl(orderId+userid.toString()));
								
							}else if (serviceId == 7) {//???????????? 
								System.out.println(""+orderId );
								String strInstanceid = SysWorker.getjiguanginstanceID(userid.toString(), orderId, linkman.getMobile(), linkman.getName());
								selfHelpOrderService.updateSysOrder(orderId,orderId, "3",status,orderList.getId(),orderList.getPay_date(),strInstanceid);
								 orderVal = orderVal+ orderId+",";
								
							}else if (serviceId == 10) {//???????????? 
								System.out.println(""+orderId );
								System.out.println(""+portMessage );
								//String strInstanceid = SysWorker.getjiguanginstanceID(userid.toString(), orderId, linkman.getMobile(), linkman.getName());
								selfHelpOrderService.updateSysOrder(orderId,orderId, "3",status,orderList.getId(),orderList.getPay_date(),portMessage);
								 orderVal = orderVal+ orderId+",";
								
							}
    			    	
    			    		else {
								selfHelpOrderService.updateOrder(orderId,orderId, "3",status,orderList.getId(),orderList.getPay_date());
								 orderVal = orderVal+ orderId+",";
							}
    			    		
        					
        					
        					//?????? ?????????????????????Id
        				//	if (modifyOrderId.contains(shopCar.getOrderId())){
        					//	modifyOrderId.remove(shopCar.getOrderId());
        					//	modifyOrderId.add(orderId);
        					//}
        				} else {
        					result = false;
        				}
						//return result;
					}
				}
    			
    			
    			list = selfHelpOrderService.findBuyShopList(orderIdList,globle_user.getId());
    		}
    		
    		if(list!=null&&list.size()>0){
    			for(int i=0;i<list.size();i++){
    				ShopCar shopCar = (ShopCar)list.get(i);
    				if(shopCar.getIsAPI()==0 || shopCar.getIsAPI()==2){
    					shopList.add(shopCar);
    				}else if (shopCar.getIsAPI()==1) {
    					shopAPIList.add(shopCar);
    				}else if (shopCar.getIsAPI()==3) {
    					shopSysList.add(shopCar);
					}
    			}	 
    		}
    		
    		//???????????????????????????id
    		
    		if(shopList!=null&&shopList.size()>0){
    			for(int i=0;i<shopList.size();i++){
    				ShopCar shopCar = (ShopCar)shopList.get(i);
    				String targetURL[]=shopCar.getAddr().split(",");
    				String websoc = "0";
    				String[] customManu = null;//????????????
    				if(websoc != null && websoc != ""){
    					customManu = websoc.split(","); //???????????????"," ,???????????????????????????customManu 
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
//    						SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
//    						String orderDate = odf.format(new Date());
//    						orderId = orderDate+String.valueOf(Random.fivecode());
    						orderVal = orderVal+ orderId+",";
    					}else{
//    						SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
//    						String orderDate = odf.format(new Date());
//    						orderId = orderDate+String.valueOf(Random.fivecode());
////    						orderId = shopCar.getOrderId();
//    						orderVal = orderVal+ orderId+",";
    						
    						//??????????????????????????????waf
    						String nowDay = DateUtils.dateToDate(new Date());
    						String begin = DateUtils.dateToDate(shopCar.getBeginDate());
    						if(begin.compareTo(nowDay)<=0){
    							//??????waf????????????,modify by tangxr 2016-6-13
        						List assets = orderAssetService.findAssetsByOrderId(shopCar.getOrderId());
        						HashMap<String, Object> assetOrder = new HashMap<String, Object>();
    				        	assetOrder=(HashMap) assets.get(0);
    				        	int id = 0;
    				        	String addr = "";
    				        	String addrName = "";
    				        	String wafIp = "1.1.1.1";
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
        						//?????????
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
//        				    		String sta = "success";
        				    		if(sta!=null&&!"".equals(sta)&&sta.equals("success")){
        				    			OrderAsset oa = new OrderAsset();
            				    		oa.setId(id);
            				    		oa.setTargetKey(targetKey);
            				    		orderAssetService.update(oa);
            				    		//5 ?????????waf?????????????????????
            				    		Order order = new Order();
            			    			order.setId(shopCar.getOrderId());
            			    			order.setStatus(5);
            			    			orderService.update(order);
            			    			
            				    		SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
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
    							SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
        						String orderDate = odf.format(new Date());
        						orderId = orderDate+String.valueOf(Random.fivecode());
        						orderVal = orderVal+ orderId+",";
    				    		}
    						}
    						
    					}
    					
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				
    				//??????API??????orderId?????????????????????
    				if(!orderId.equals("") && orderId != null){
    					// String orderId="1";
//    					status = String.valueOf(shopCar.getStatus());
    					//?????????????????????
    					selfHelpOrderService.updateOrderAsset(shopCar.getOrderId(), orderId);
    					//???????????????
//    					selfHelpOrderService.updateOrder(shopCar.getOrderId(), orderId, "0",status);
    					selfHelpOrderService.updateOrder(shopCar.getOrderId(), orderId, String.valueOf(shopCar.getIsAPI()),status,orderList.getId(),orderList.getPay_date());
    					//?????? ?????????????????????Id
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
    				String[] customManu = null;// ????????????
    				if (websoc != null && websoc != "") {
    					customManu = websoc.split(","); // ???????????????","
    					// ,???????????????????????????customManu
    				}
    				Date beginDate = shopCar.getBeginDate();
    				Date endDate = shopCar.getEndDate();

    				status = String.valueOf(shopCar.getStatus());
    				String orderId = "";
//	    		 orderVal = orderVal+ shopCar.getOrderId()+",";
    				try {
    					
    						orderId = NorthAPIWorker.vulnScanCreateAPI(
    								Integer.parseInt(shopCar.getAstName()),
    								shopCar.getBuynum(), shopCar.getServiceId(),
    								globle_user.getApikey(), globle_user.getId());
//    						SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
//    						String orderDate = odf.format(new Date());
//    						orderId = orderDate+String.valueOf(Random.fivecode());
    						orderVal = orderVal+ orderId+",";
    					
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				// String orderId="2";
    				if (orderId != null && !"".equals(orderId)) {
    					// ?????????????????????
    					selfHelpOrderService.updateOrderAPI(
    							shopCar.getOrderId(), orderId);
    					// ???????????????
    					selfHelpOrderService.updateOrder(shopCar.getOrderId(),
    							orderId, "1",status,orderList.getId(),orderList.getPay_date());
    					//?????? ?????????????????????Id
    					if (modifyOrderId.contains(shopCar.getOrderId())){
    						modifyOrderId.remove(shopCar.getOrderId());
    						modifyOrderId.add(orderId);
    					}
    				} else {
    					result = false;
    				}
    			}
    			
    		}
    		
    		/*
    		if (shopSysList != null && shopSysList.size() > 0) {
    			for (int i = 0; i < shopSysList.size(); i++) {
    				ShopCar shopCar = (ShopCar) shopSysList.get(i);
    				String orderId = "";
    				orderId = shopCar.getOrderId();
    			   
    			    
    			    if (orderId != null && !"".equals(orderId)) {
    					//?????? ?????????????????????Id
    					if (modifyOrderId.contains(shopCar.getOrderId())){
    						modifyOrderId.remove(shopCar.getOrderId());
    						modifyOrderId.add(orderId);
    					}
    				} else {
    					result = false;
    				}
    			   
    			}//for
    		}//shopSyslist
    		*/
    		
    		
    		
    		//??????orderList?????????orderId
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
    		
    		//??????????????????????????????????????????(orderList???Id)????????????????????????(order???Id)??????
    		if (orderIdList!= null && orderIdList.size() == 1) {
    			if(newOrderIds==null||newOrderIds.equals(""))
    				System.out.println("newOrderIds =====NULLLLLLLLLLLLLLLLLLL+ ??????waf??????????????????????????????orderval ??????");
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
     * ??????????????? ??????????????????
     * */
    @RequestMapping(value="repayUI.html", method=RequestMethod.POST)
    public String toRepayUI(Model m,HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String orderListId = request.getParameter("orderListId");//????????????(cs_order_list???id)
		OrderList orderList = orderListService.findById(orderListId);
		
		//???????????????????????????,????????????
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	if (orderListId== null || orderList == null ||orderList.getUserId()!= globle_user.getId()) {
    		return "redirect:/index.html";
    	}
    	/**waf ??????????????????????????????????????????**/
		List<String> orderIdList=new ArrayList();
		
		String orderIds = orderList.getOrderId();
		if(orderIds!=null&&!"".equals(orderIds)){
			String strArray[] = orderIds.split(",");
			for (int k=0;k<strArray.length;k++){
				orderIdList.add(strArray[k]);
			}
		}
		List	list = selfHelpOrderService.findBuyShopList(orderIdList,globle_user.getId());
		for(int i=0;i<list.size();i++){
			ShopCar shopCar = (ShopCar)list.get(i);
			int serviceId = shopCar.getServiceId();
			if(serviceId==6){
				Linkman linkman=orderService.findLinkmanByOrderId(shopCar.getOrderId());
				String mobile = linkman.getMobile();
				SMSUtils smsUtils = new SMSUtils();
			    try {
			    	smsUtils.sendMessage_WafOrderSuccess(mobile, shopCar.getOrderId(), shopCar.getBeginDate());
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
		/**waf ??????????????????????????????????????????**/
    	
		//?????????????????????????????????
		String modifyOrderIdList = request.getParameter("modifyOrderId");
		if (modifyOrderIdList != null && !modifyOrderIdList.equals("") && !modifyOrderIdList.equals("undefined")){
			String modifyOrderId[] = modifyOrderIdList.split(",");
			for(String id:modifyOrderId){
				if(!orderList.getOrderId().contains(id.trim()+",")&&
						!orderList.getOrderId().endsWith(id.trim())){
					return "redirect:/index.html"; 
				}
			}
			m.addAttribute("modifyOrderId", modifyOrderId);
			m.addAttribute("beginDate", orderList.getPay_date());  //???????????????????????????????????????
		}
		
		if (orderList.getPay_date()== null){
			m.addAttribute("paySuccess", 1);
		} else {
			String[] api = orderList.getServerName().split(",");
//			//????????????????????????API
//			boolean apiOrderFlg = true;//true?????????????????????API
//			for(String name: api) {
//				if(!name.endsWith("API")){
//					apiOrderFlg = false;
//					break;
//				}
//			}
//			m.addAttribute("apiOrderFlg", apiOrderFlg);
			m.addAttribute("paySuccess", 0);
			m.addAttribute("orderList", orderList);
			
			Double price = orderList.getPrice();//????????????
	    	DecimalFormat df = new DecimalFormat("0.00");
	    	String priceStr = df.format(price);
	    	m.addAttribute("price", priceStr);
			
		}
    	return "/source/page/details/repay";
    	
    }
    
    
//	/**
//	 * ??????????????? ????????????
//	 * ??????????????? Model model
//	 * @throws Exception 
//	 *		 @time 2015-1-16
//	 */
//	@RequestMapping(value="/addWebSiteWaf.html", method=RequestMethod.POST)
//	public void addWebSite(HttpServletRequest request,HttpServletResponse response){
//		Map<String, Object> m = new HashMap<String, Object>();
//		String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
//
//		try {
//			Asset asset = new Asset();
//			User globle_user = (User) request.getSession().getAttribute("globle_user");
//			asset.setUserid(globle_user.getId());//??????ID
//			asset.setCreate_date(new Date());//????????????
//			if(globle_user.getType()==2){
//				asset.setStatus(0);//????????????(1???????????????0????????????)
//			}else if(globle_user.getType()==3){
//				asset.setStatus(1);//????????????(1???????????????0????????????)
//			}
//
//			String name = request.getParameter("assetName");//????????????
//			String addr = request.getParameter("assetAddr").toLowerCase();//????????????
//			String addrType = request.getParameter("addrType");//????????????
//			String purpose = request.getParameter("purpose");//??????
//			String prov = request.getParameter("prov");
//			String city = request.getParameter("city");
//			String wafFlag = request.getParameter("wafFlag");
//						
////			if(!(addr.startsWith(addrType)) || addr.equals(addrType)){
////				addr = addrType + "://" + addr.trim();
////			}
//			//??????????????????????????????http
//			Pattern pattern2 = Pattern.compile("([hH][tT][tT][pP][sS]?):\\/\\/([\\w.]+\\/?)\\S*");
//			Matcher matcher2 =	pattern2.matcher(addr);
//			if(!matcher2.find()){
//				addr="http://"+addr.trim();
//			}
//			
//			//??????wafFlag!=null ?????????????????????
//			if(wafFlag!=null){
//				String addInfo = "";
//				//??????http??????
//				if(addr.indexOf("http://")!=-1){
//				  	if(addr.substring(addr.length()-1).indexOf("/")!=-1){
//				  		addInfo = addr.trim().substring(7,addr.length()-1);
//				  	}else{
//				  		addInfo = addr.trim().substring(7,addr.length());
//				  	}
//				}else if(addr.indexOf("https://")!=-1){
//					if(addr.substring(addr.length()-1).indexOf("/")!=-1){
//				  		addInfo = addr.trim().substring(8,addr.length()-1);
//				  	}else{
//				  		addInfo = addr.trim().substring(8,addr.length());
//				  	}
//				}
//				
//				//?????????????????????????????????
//				boolean flag=addInfo.matches(hostnameRegex);
//				if(!flag){
//					m.put("success", false);
//					m.put("wafFlag", false);
//					//object?????????Json??????
//					JSONObject JSON = CommonUtil.objectToJson(response, m);
//					try {
//						// ????????????????????????
//						CommonUtil.writeToJsp(response, JSON);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					return;
//				}
//				
//			}
//			
//			
//			asset.setName(name);
//			asset.setAddr(addr);
//			asset.setPurpose(purpose);
//			asset.setDistrictId(prov);
//			asset.setCity(city);
//			assetService.saveAsset(asset);
//
//			//????????????????????????
//		    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
//		    
//		    if(wafFlag==null){
//		    	m.put("serviceAssetList", serviceAssetList);
//		    }else{//?????????waf?????????????????????
//				boolean flag=false;				
//		    	List assList = new ArrayList();
//				if(serviceAssetList!=null&&serviceAssetList.size()>0){
//					for(int i=0;i<serviceAssetList.size();i++){
//						Asset newAsset = (Asset)serviceAssetList.get(i);
//						String newAddr = asset.getAddr();
//						String addInfo = "";
//						//??????http??????
//						if(addr.indexOf("http://")!=-1){
//						  	if(addr.substring(addr.length()-1).indexOf("/")!=-1){
//						  		addInfo = addr.trim().substring(7,addr.length()-1);
//						  	}else{
//						  		addInfo = addr.trim().substring(7,addr.length());
//						  	}
//						}else if(addr.indexOf("https://")!=-1){
//							if(addr.substring(addr.length()-1).indexOf("/")!=-1){
//						  		addInfo = addr.trim().substring(8,addr.length()-1);
//						  	}else{
//						  		addInfo = addr.trim().substring(8,addr.length());
//						  	}
//						}
//						//?????????????????????????????????
//						flag=addInfo.matches(hostnameRegex);
//						if(flag){
//							assList.add(newAsset);
//						}
//					}
//				}
//				m.put("serviceAssetList", assList);
//		    }
//			m.put("success", true);
//			
//			//object?????????Json??????
//			JSONObject JSON = CommonUtil.objectToJson(response, m);
//			try {
//				// ????????????????????????
//				CommonUtil.writeToJsp(response, JSON);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			m.put("success", false);
//			m.put("wafFlag", true);
//			//object?????????Json??????
//			JSONObject JSON = CommonUtil.objectToJson(response, m);
//			try {
//				// ????????????????????????
//				CommonUtil.writeToJsp(response, JSON);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * ??????????????? ?????????????????????
	 * ???????????????  ???
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
		//??????ids
        String assetIds = request.getParameter("assetIds");
		String orderType = request.getParameter("type");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String scanPeriod = request.getParameter("scanType");
        String serviceId = request.getParameter("serviceId");
        String createDate = DateUtils.dateToString(new Date());
        //String times = request.getParameter("buy_times");
       
       
        //???????????????????????????
        if((assetIds==null||"".equals(assetIds))||(orderType==null||"".equals(orderType))||(beginDate==null||"".equals(beginDate))||(serviceId==null||"".equals(serviceId))){
        	return "redirect:/index.html";
        }
    
     //????????????????????????
      
       if(assetIds!=null&&!"".equals(assetIds)){
    	   assetArray = assetIds.split(","); //???????????????"," ,???????????????????????????strArray 
       	for(int i=0;i<assetArray.length;i++){
           	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]),globle_user.getId());
            if(asset==null){
            	return "redirect:/index.html";
            }
           }
       }
       
       //??????????????????id????????????
       Serv service = servService.findById(Integer.parseInt(serviceId));  
       if(service==null){
    	   return "redirect:/index.html";
       }
       
        //????????????????????????
       if(!checkOrderType(serviceId,orderType)){
    		return "redirect:/index.html";
       }
       //??????
       if(orderType.equals("2")){
    	   if(endDate!=null&&!"".equals(endDate)){
    		   return "redirect:/index.html";
    	   }
    	 
       }
       //??????
       if(orderType.equals("1")){
    	   if(beginDate==null || "".equals(beginDate)|| endDate==null || "".equals(endDate)){
    		   return "redirect:/index.html";
    	   }
    	   //????????????
    	   if(scanPeriod==null || "".equals(scanPeriod)){
    		   return "redirect:/index.html";
    	   }
       }
       //???????????????????????????????????????
       if(beginDate!=null&&!"".equals(beginDate)&&endDate!=null&&!"".equals(endDate)){
    	   Date begin = DateUtils.stringToDateNYRSFM(beginDate);
           Date end = DateUtils.stringToDateNYRSFM(endDate);
           
           if(begin.getTime()>end.getTime()){
           	return "redirect:/index.html";
           } 
       }
       //??????
       if(orderType.equals("1")){
    	   //??????????????????????????????
    	   List<ScanType> scanTypeList = scanTypeService.findScanType(Integer.parseInt(serviceId), Integer.parseInt(scanPeriod));
    	   if(scanTypeList.size() <= 0){
    		   return "redirect:/index.html";
    	   }
       }
      
		/***??????????????????***/
      
		double calPrice = 0;
        //??????????????????
        long stimes = 0;
    	try {
    		
			//????????????id
			int serviceIdV = Integer.parseInt(serviceId);
			if(assetIds!=null&&!"".equals(assetIds)){
				assetArray=assetIds.split(",");
			}
			int assetCount = assetArray.length;
			if(assetCount<=0){
				assetCount=1;
			}
			//??????????????????
			if (orderType!= null && orderType.equals("1")){  //??????
				
				int scanType = Integer.valueOf(scanPeriod); 			//????????????
				stimes = calTimes(scanType, beginDate, endDate);	//????????????????????????????????????
				calPrice = calPrice(serviceIdV,scanType,stimes,assetCount);//????????????
				
			} else {	//??????
				priceList = priceService.findPriceByServiceId(serviceIdV,0);
				if (priceList != null && priceList.size() != 0){
					//priceList????????????????????????????????????????????????????????????
					Price price = priceList.get(0);  
					if (price.getType() == 0){
						calPrice = price.getPrice() * assetCount;
					}
				}
			}
			
			//??????serviceid??????????????????
//			if(serviceIdV==3||serviceIdV==4||serviceIdV==5){
//				 if(scanPeriod!=null&&!"".equals(scanPeriod)&&!"".equals(orderType)&&orderType.equals("1")){
//					 priceList = priceService.findPriceByServiceId(serviceIdV,Integer.parseInt(scanPeriod));	 
//				 }else{
//					 priceList = priceService.findPriceByServiceId(serviceIdV,0);
//				 }
//			
//			}else{
//				priceList = priceService.findPriceByServiceId(serviceIdV,0);
//			}
	        //??????
//	        if(orderType!=null&&!"".equals(orderType)&&orderType.equals("1")){	
//	        	long ms = 0;//????????????????????????
//	        	Date bDate = null;
//	        	Date eDate = null;
//	        	if(beginDate!=null&&beginDate!=""&endDate!=null&&endDate!=""){
//	        		bDate = DateUtils.stringToDateNYRSFM(beginDate);
//		            eDate = DateUtils.stringToDateNYRSFM(endDate);  
//		            ms = DateUtils.getMsByDays(bDate, eDate);
//	        	}
//	            
//	            int typeInt = Integer.parseInt(scanPeriod);
//	            
//		        switch(serviceIdV){
//		        case 1:
//		        case 2:
//		        	if(ms==0){
//		        		stimes = 1;//????????????????????????
//		        	}else{
//		        		//??????
//			        	if(typeInt==5){
//			        		int perWeek = 1000*3600*24*7;
//			        		if(ms%perWeek>0){
//			        			stimes = (long)(ms/perWeek)+1;
//			        		}else{
//			        			stimes = (long)(ms/perWeek);
//			        		}		        		
//			        	}else{//??????
//			        		while(ms>0){
//			        			bDate = DateUtils.getDayAfterMonth(bDate);
//			        			ms = DateUtils.getMsByDays(bDate, eDate);
//			        			stimes++;
//			        		}
//			        	}	
//		        	}
//		        	break;
//		        	
//		       
//		        case 3:
//		        case 4:
//		        	if(ms==0){
//		        		stimes = 1;//????????????????????????
//		        	}else if(typeInt==2){//30??????
//			        	int min_30 = 1000*3600/2;
//			        	if(ms%min_30 > 0){
//			        		stimes = (long)(ms/min_30) + 1;
//			        	}else{
//			        		stimes = (long)(ms/min_30);
//			        	}
//		        	}else if(typeInt==3){//1??????
//		        		int oneHour = 1000*3600;
//			        	if(ms%oneHour > 0){
//			        		stimes = (long)(ms/oneHour) + 1;
//			        	}else{
//			        		stimes = (long)(ms/oneHour);
//			        	}
//		        	}else if(typeInt==4){//1???
//		        		int oneDay = 1000*3600*24;
//			        	if(ms%oneDay > 0){
//			        		stimes = (long)(ms/oneDay) + 1;
//			        	}else{
//			        		stimes = (long)(ms/oneDay);
//			        	}
//		        	}
//		        	break;
//		        	
//		        case 5:
//		        	if(ms==0){
//		        		stimes = 1;//????????????????????????
//		        	}else if(typeInt==1){//10??????
//		        		int min_10 = 1000*3600/6;
//			        	if(ms%min_10 > 0){
//			        		stimes = (long)(ms/min_10) + 1;
//			        	}else{
//			        		stimes = (long)(ms/min_10);
//			        	}
//		        	}else if(typeInt==2){//30??????
//		        		int min_30 = 1000*3600/2;
//			        	if(ms%min_30 > 0){
//			        		stimes = (long)(ms/min_30) + 1;
//			        	}else{
//			        		stimes = (long)(ms/min_30);
//			        	}
//		        	}else if(typeInt==3){//1??????
//		        		int oneHour = 1000*3600;
//			        	if(ms%oneHour > 0){
//			        		stimes = (long)(ms/oneHour) + 1;
//			        	}else{
//			        		stimes = (long)(ms/oneHour);
//			        	}
//		        	}
//		        			        	
//		        	break;
//		        }
//		        if(priceList!=null && priceList.size()>0){
//				    for (int i = 0; i < priceList.size(); i++) {
//				    	Price onePrice = priceList.get(i);
//				        if(onePrice.getTimesG()!=0 && onePrice.getTimesLE()!=0){//??????
//				        	if(stimes>onePrice.getTimesG()&&stimes<=onePrice.getTimesLE()){
//				    				calPrice = onePrice.getPrice()*stimes*assetCount;
//				    			break;
//				    		}
//				        	if(serviceIdV==4){
//				        		if(stimes>=onePrice.getTimesG()&&stimes<=onePrice.getTimesLE()){
//				    				calPrice = onePrice.getPrice()*stimes*assetCount;
//				    			break;
//				    		}
//				        	}
//				        	if((serviceIdV==5||serviceIdV==3) && stimes==1 && onePrice.getTimesG()==1){//??????5????????????times==1??????????????????
//				        		calPrice = onePrice.getPrice()*assetCount;
//				    			break;
//				        	}
//				        }else if(onePrice.getTimesG()!=0 && onePrice.getTimesLE()==0 && stimes>onePrice.getTimesG()){//??????
//			        			calPrice = onePrice.getPrice()*stimes*assetCount;			    			
//			        			break;
//				        }else if(onePrice.getTimesG()==0 && onePrice.getTimesLE()==0 && stimes <= 1){//?????????
//				        	calPrice = onePrice.getPrice()*assetCount;
//				        	break;
//				        }
//
//				    }
//		        }else{
//		        	calPrice = 0;
//		        }
//	        }else{//??????
//	        	stimes = 1;
//	        	if(priceList!=null && priceList.size()>0){
//				    for (int i = 0; i < priceList.size(); i++) {
//				    	Price onePrice = priceList.get(i);
//				    	if(serviceIdV!=5){
//				    		 if(onePrice.getTimesG()==0 && onePrice.getTimesLE()==0){
//					        		//??????
//						        	calPrice = onePrice.getPrice()*assetCount;
//						        	break;
//					         }
//				    	}else{//??????5??????????????????
//				    		if(onePrice.getTimesG()==1){
//				        		//??????
//					        	calPrice = onePrice.getPrice()*assetCount;
//					        	break;
//				         }
//				    	}
//				       
//				    }
//	        	}else{
//	        		calPrice = 0;
//	        	}
//			}

			DecimalFormat df = new DecimalFormat("0.00"); 
          String  price = df.format(calPrice);
          /***??????????????????***/
          OrderDetail orderDetail = new OrderDetail();
      	  SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
   	      String orderDate = odf.format(new Date());
   	     detailId = orderDate+String.valueOf(Random.fivecode());
   	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//?????????mm??????????????????  
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
			//object?????????Json??????
			
		}
		if(assetIds!=null&&!"".equals(assetIds)){
	    	   assetArray = assetIds.split(","); //???????????????"," ,???????????????????????????strArray 
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
	 * ??????????????????????????????
	 */
	private boolean checkOrderType(String serviceId, String orderType) {
		boolean result = false;
		int servId = Integer.valueOf(serviceId);
		ServiceDetail servDetail = servDetailService.findByServId(servId);
		if (servDetail == null) {
			return result;
		}
		int servType = servDetail.getServType(); //0:??????????????? 1????????? 2?????????
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
//		if (serviceId.equals("1") || serviceId.equals("2") || serviceId.equals("4")) {
//			//?????????1 ?????????2
//			if(orderType.equals("1") || orderType.equals("2")) {
//				result = true;
//			}
//			
//		} else if (serviceId.equals("3") || serviceId.equals("5")) {
//			// ????????????????????????/???????????????????????????   
//			// ?????????1
//			if(orderType.equals("1")) {
//				result = true;
//			}
//			
//		}
//		else if (serviceId.equals("6")) {
//			//waf 8????????? 9?????????
//			if (orderType.equals("8") || orderType.equals("9")) {
//				result = true;
//			}
//		}
		
		return result;
	}
	
	
	/**
     * ??????????????? ????????????,???????????????????????????
     * */
    @RequestMapping(value="orderDetailsUI.html")
    public String toOrderDetails(Model m,HttpServletRequest request, HttpServletResponse response){
    	String orderId = request.getParameter("orderId");//????????????(cs_order_list???id)
		Order order = orderService.findOrderById(orderId);
		
		//??????orderId
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
    		
    		if (order.getStatus() == 1 && order.getIsAPI() == 0) {  //status=1??????????????????
    			return "redirect:/warningInit.html?orderId="+orderId+"&type="+order.getType()+"&websoc="+order.getWebsoc();
    		}
    		
    		if (order.getStatus() == 1 && order.getIsAPI() == 1) {   //status=1??????????????????  API
    			return "redirect:/selfHelpOrderAPIInit.html?apiId="+order.getServiceId()+"&indexPage=2";
    		}
    		//???????????????
    		if (order.getIsAPI() == 0 && order.getBegin_date().getTime() <= new Date().getTime() && 
    				order.getStatus() != 3 && order.getStatus() != 2 && order.getStatus() != 1) {  //status=4???????????? 
    			return "redirect:/warningInit.html?orderId="+orderId+"&type="+order.getType()+"&websoc="+order.getWebsoc();
    		}
    		
    		if (order.getBegin_date().getTime() <= new Date().getTime() && order.getStatus() == 3) { //status=3????????????????????? 
    			return "redirect:/warningInit.html?orderId="+orderId+"&type="+order.getType()+"&websoc="+order.getWebsoc();
    		}
    		if (order.getIsAPI()==3) {
    			return "redirect:/orderSysDetails.html?orderId="+orderId;
    		} 
		}
    	else{
    		if (order.getIsAPI() == 2 && order.getStatus() == 4) {
    			return "redirect:/warningWaf.html?orderId="+orderId+"&type="+order.getType();
    		}
    	}
    	
    	
    	return "redirect:/orderDetails.html?orderId=" + orderId;
    }
    
    /**
     * ??????????????? ????????????????????????
     * */
    @RequestMapping(value="alipaytradepaagepay.html")
	public String alipaytradepaagepay(Model m){
		return "/source/page/details/alipay.trade.page.pay";
	}
    /**
     * ??????????????? ????????????????????????
     * */
    @RequestMapping(value="alipayreturnURL.html")
	public String alipayreturnURL(Model m){
		return "/return_url";
	}
    /**
     * ??????????????? ????????????????????????
     * */
    @RequestMapping(value="alipaynotifyURL.html")
	public String alipaynotifyURL(Model m){
		return "/notify_url";
	}
    
}
