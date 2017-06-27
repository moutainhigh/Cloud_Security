package com.cn.ctbri.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.cfg.Configuration;
import com.cn.ctbri.entity.APICount;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAPI;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.entity.ScanType;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceAPI;
import com.cn.ctbri.entity.ServiceDetail;
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
import com.cn.ctbri.util.GetNetContent;
import com.cn.ctbri.util.Random;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;


/**
 * 创 建 人  ：  ltb
 * 创建日期：  2016-11-24
 * 描        述：  系统安全帮订单管理控制层
 * 版        本：  1.0
 */
@Controller
public class shoppingSysController {
	
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
			p.load(shoppingSysController.class.getClassLoader().getResourceAsStream("InternalAPI.properties"));
			
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			//VulnScan_servicePrice = p.getProperty("VulnScan_servicePrice");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 功能描述： 极光页面初始化
	 * 参数描述：  无
	 *     @time 2016-07-18
	 */
	@RequestMapping(value="systemOrderOperaInit.html")
	public String systemOrderOperaInit(HttpServletRequest request){
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
		    ServiceDetail servDetail = servDetailService.findByServId(serviceId);    //查找cs_service_detail表
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
		    List<ScanType> scanTypeList = scanTypeService.findScanTypeById(serviceId);  // 调用cs_scanType 中的 getScanTypeById
		    //获取服务对象资产
		    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
		    //网站安全帮列表
	        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
	        //查询安全能力API
			   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
			// 查询系统安全帮
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
//	        request.setAttribute("orderType", "");
	 
		String result = "/source/page/details/systemServDetails";
        return result;
	}

	/**
	 * 功能描述：查看服务是否可以购买，是否有时间重叠
	 * 参数描述： Asset asset
	 *		 @time 2015-1-19
	 *	返回值：无
	 */
	@RequestMapping(value="checkifcanbuy.html",method=RequestMethod.POST)
	public void checkifcanbuy(HttpServletRequest request,HttpServletResponse response){
		 //用户
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
		//int id = Integer.valueOf(request.getParameter("id"));
    	String serviceId = request.getParameter("serviceId");
    	int intserviceId = Integer.parseInt(serviceId);
		
		//获取验证方式:代码验证 ;上传文件验证
		String check_msg;
		Map<String, Object> m = new HashMap<String, Object>();
		//m.put("status", status);//返回验证状态
		try {
//			
			// 判定是否可以 立即购买 ，如果购买时间 出现重叠则禁止购买
						List orderList = orderService.findOrderByUserIdAndServiceId(globle_user.getId(), Integer.parseInt(serviceId));		
						if(orderList.size()>0&&orderList!=null){
							//HashMap<String,Object>  map = (HashMap<String,Object>)ol.get(j);
							HashMap<String,Object>  orderMap = (HashMap<String,Object>)orderList.get(0);
							
							
							String strBeginDate = orderMap.get("begin_date").toString();
							String strEndDate =  orderMap.get("end_date").toString();
							String strNowDate = DateUtils.dateToString(new Date());
							
							String isAPI = orderMap.get("isAPI").toString();
							String strpayflag = orderMap.get("payFlag").toString();
							if(strNowDate.compareTo(strBeginDate)>0 && strNowDate.compareTo(strEndDate)<0 && strpayflag.equals("1")&& isAPI.equals("3")&& intserviceId!=10){
							//if (strNowDate.compareTo(strBeginDate)>0 && strNowDate.compareTo(strEndDate)<0 && strpayflag.equals("1")&&isAPI.equals("3")) {
								
	
									// 把数据返回到页面
									m.put("status", 0);
									m.put("message", "已有同类订单，不能重复购买");
									JSONObject JSON = CommonUtil.objectToJson(response, m);
									CommonUtil.writeToJsp(response, JSON);
									System.out.println("已有同类订单，不能重复购买");
									return ;

							}
							else {
								m.put("status", 1);
								m.put("message", "可以购买");
								JSONObject JSON = CommonUtil.objectToJson(response, m);
								CommonUtil.writeToJsp(response, JSON);
								System.out.println("可以购买");
								// 把数据返回到页面
								 //CommonUtil.writeToJsp(response, JSON);
								 return;
								
							}
						}
						else {
							m.put("status", 1);
							m.put("message", "可以购买");
							JSONObject JSON = CommonUtil.objectToJson(response, m);
							CommonUtil.writeToJsp(response, JSON);
							System.out.println("可以购买");
							// 把数据返回到页面
							// CommonUtil.writeToJsp(response, JSON);
							 return;
							
						}
			
		} catch (Exception e) {
			m.put("status", 2);
			m.put("message", "crush");
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("购买crush");
			e.printStackTrace();
			return;
		}
	}
	/**
	 * 功能描述： 保存{39608ce6-d506-4c6d-8166-864bbc2d7aba}极光详情页操作,立即购买
	 * 参数描述：  无
	 *     @time 2016-11-29
	 */
	@RequestMapping(value="jiGuangselfHelpOrderOpera.html",method=RequestMethod.POST)
	public String jiGuangselfHelpOrderOpera(HttpServletRequest request, HttpServletResponse response){
		String assetArray[] = null;
		String detailId = "";
		Map<String, Object> m = new HashMap<String, Object>();//返回是否可以下单的状态
		
		List assetIdsList = new ArrayList();
		List<Price> priceList = new ArrayList();
		System.out.println("12345678900000000");
		try {
			User globle_user = (User)request.getSession().getAttribute("globle_user");
			//String assetIds = request.getParameter("assetIds");
			String orderType = request.getParameter("type");  //orderType 从页面获取  长期单次
	        //String beginDate = request.getParameter("beginDate");
			String beginDate = DateUtils.dateToString(new Date());
			//Calendar cal = Calendar.getInstance();
			//cal.setTime(new Date());
			//cal.add(Calendar.YEAR, 1);
	        //String endDate = request.getParameter("endDate");
			
			
			
			String duration = request.getParameter("duration");
			System.out.println("duration是"+duration);
	        String scanPeriod = request.getParameter("scanType"); // 频率  绿盟64ip(scanType = 15)   128ip(16)   金山10对应 10ip
	        String serviceId = request.getParameter("serviceId");
	        String createDate = DateUtils.dateToString(new Date());
	        int durationint =Integer.parseInt(duration);
			//String assetIds;
			
	        
	        String endDate =DateUtils.dateToString(getAfterFewMonth(new Date(), durationint));
	        String portMessage =null;
	        if(Integer.parseInt(serviceId) ==10)//如果是服务监测，就获取检测的ip和端口信息,如果不是就为空
	        {
	        	portMessage = request.getParameter("portMessage");
	        	
//	        	if (!checkIP(portMessage)) {
//					return "redirect:/index.html";
//				}
	        }
	      //判断参数值是否为空
	      
	        if((orderType==null||"".equals(orderType))||(beginDate==null||"".equals(beginDate))||(serviceId==null||"".equals(serviceId))||(duration==null||"".equals(duration))){
	        	return "redirect:/index.html";
	        }
	        /*
	        if (scanPeriod.equals("6")) {
				assetIds="-1";   //  64ip
			}else {
				assetIds="-2";    //  128ip
			}*/
	       
	        
	      //根据判断服务id是否存在
	        Serv service = servService.findById(Integer.parseInt(serviceId));
			if (service==null) {
				return "redirect:/index.html";
			}
			
			// 判断类型是否存在  ordertype是页面点击的类型
			if (!checkOrderType(serviceId, orderType)) {
				return "redirect:/index.html";
			}
			//单次
			if (orderType.equals("2")) {
				if (endDate!=null && "".equals(endDate)) {
					return "redirect:/index.html";
				}
			}
		
			
			// 判定是否可以 立即购买
			try {
//				
				// 判定是否可以 立即购买 ，如果购买时间 出现重叠则禁止购买
							List orderList = orderService.findOrderByUserIdAndServiceId(globle_user.getId(), Integer.parseInt(serviceId));		
							if(orderList.size()>0&&orderList!=null){
								//HashMap<String,Object>  map = (HashMap<String,Object>)ol.get(j);
								HashMap<String,Object>  orderMap = (HashMap<String,Object>)orderList.get(0);								
								String strBeginDate = orderMap.get("begin_date").toString();
								String strEndDate =  orderMap.get("end_date").toString();			
								String strpayflag = orderMap.get("payFlag").toString();
								String strisAPI = orderMap.get("isAPI").toString();
								int intserviceId = Integer.parseInt(serviceId);
									
								String strNowDate = DateUtils.dateToString(new Date());							
								if (strNowDate.compareTo(strBeginDate)>0 && strNowDate.compareTo(strEndDate)<0 && strpayflag.equals("1")&& strisAPI.equals("3")&& intserviceId!=10) {
									//System.out.println(strNowDate.compareTo(strBeginDate)>0 && strNowDate.compareTo(strEndDate)<0 && strpayflag.equals("1")&&(strisAPI.equals("3")&&(intserviceId==7 || intserviceId==8 || intserviceId==9)));
										// 把数据返回到页面
									Serv serviceRe = servService.findById(Integer.parseInt(serviceId));
									ServiceDetail servDetailRe = servDetailService.findByServId(Integer.parseInt(serviceId));
									String[] detailImages = null;
								    if (servDetailRe != null) {
								    	String imageStr = servDetailRe.getDetailIcon();
								    	if(imageStr != null && !imageStr.equals("") && !imageStr.equals(";")){
								    		detailImages = imageStr.split(";");
								    	}
								    }
								  //网站安全帮列表
							        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
							        //查询安全能力API
									   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
									// 查询系统安全帮
										List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0, "");
										int carnum = shopCarList.size() + apiList.size() + sysList.size();
									request.setAttribute("globle_user",globle_user);
									request.setAttribute("serviceId",serviceId);
									request.setAttribute("indexPage", 4);
									request.setAttribute("service", serviceRe);
									request.setAttribute("carnum", carnum);
									request.setAttribute("detailImages", detailImages);
									request.setAttribute("message", "已有同类订单");
										System.out.println("已有同类订单，不能重复购买");
									
										return "/source/page/details/systemServDetails";

								}
							
							}
				
			} catch (Exception e) {
				
				System.out.println("购买crush");
				e.printStackTrace();
				return "redirect:/index.html";
			}
			
	        
			
			//计算价格
			double calPrice = 0;				
			try {
				int serviceIdInt = Integer.parseInt(serviceId);
				int scanTypeInt = Integer.parseInt(scanPeriod);
				if (orderType!=null && orderType.equals("1")) {  //长期
					priceList = priceService.findPriceByServiceId(serviceIdInt, scanTypeInt);
					if (priceList!=null && priceList.size()!=0) {
						//Price price = priceList.get(0);
						//calPrice = price.getPrice();
						
						 //scanPeriod 是web服务的服务频率  ，是系统安全帮的 扫描种类  有 6、7两种
				    	   if(scanPeriod==null || "".equals(scanPeriod)){
				    		   return "redirect:/index.html";
				    	   }
				    	/*   
						for (int i = 0; i < priceList.size(); i++) {
							
							if (priceList.get(i).getScanType()==scanTypeInt ) {
								calPrice = priceList.get(i).getPrice();
								break;
							}
						}*/
				    	 calPrice =calPrice(serviceIdInt, scanTypeInt, durationint);
					}
				}
				else {
					calPrice = 0;
				}
				DecimalFormat df = new DecimalFormat("0.00"); 
		        String  price = df.format(calPrice); //计算价格完成
		        
		        // 构造订单内容
		        OrderDetail orderDetail = new OrderDetail();
		        SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//
		        String orderDate = odf.format(new Date());
		        detailId = orderDate + String.valueOf(Random.fivecode());
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 
		        orderDetail.setId(detailId);
		        orderDetail.setBegin_date(DateUtils.stringToDateNYRSFM(beginDate));
		        //
		        orderDetail.setEnd_date(DateUtils.stringToDateNYRSFM(endDate));
		        orderDetail.setType(Integer.parseInt(orderType));
		        orderDetail.setServiceId(serviceIdInt);
		        orderDetail.setUserId(globle_user.getId());
		        orderDetail.setIsAPI(3); //
		        //orderDetail.setAsstId("0"); //
		        orderDetail.setPrice(Double.parseDouble(price));
		        orderDetail.setCreate_date(sdf.parse(createDate));
		        orderDetail.setWafTimes(durationint); //使用waftimes字段记录 购买时长
		        
		        if(serviceIdInt==10){
		        	orderDetail.setIpArray(portMessage);
		        }
		        
		        if (scanPeriod!=null && !"".equals(scanPeriod)) {
					orderDetail.setScan_type(Integer.parseInt(scanPeriod));
				}
		        
		        selfHelpOrderService.SaveOrderDetail(orderDetail); //insert into cs_settlemet
		        OrderDetail orderDetailVo = selfHelpOrderService.findOrderDetailById(detailId, globle_user.getId());
		        orderDetailVo.setServiceName(service.getName());
				request.setAttribute("orderDetail", orderDetailVo);
			    request.setAttribute("service", service);
			    request.setAttribute("user", globle_user);
			    request.setAttribute("portMessage", portMessage);
			        
			    String result = "/source/page/details/settlement";
			    return result;
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "redirect:/index.html";
			}	
			
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//return "redirect:/index.html";
		}
		return "redirect:/index.html";
		
		
	}
	
	/**
     * 功能描述： 提交订单
     * 参数描述：  
	 * @throws Exception 
     *       @time 2016-12-20
     */
    @RequestMapping(value="saveOrderSys.html",method=RequestMethod.POST)
    @ResponseBody
    public void saveOrderSys(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	Map<String, Object> m = new HashMap<String, Object>();
        
        User globle_user = (User) request.getSession().getAttribute("globle_user");
      String orderDetailId = request.getParameter("orderDetailId");
   
        String linkname =request.getParameter("linkname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
      
        String scanType = request.getParameter("scanType");
        String portMessage = request.getParameter("portMessage");//获取Serviceid为10的端口和ip信息

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
        if(phone!=null&&!"".equals(phone)) {
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
        OrderDetail orderDetailVo = selfHelpOrderService.findOrderDetailById(orderDetailId, globle_user.getId()); //SELECT * from cs_settlemet
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
        
      //长期：订单结束时间不能早于当前订单提交时间  2016-12-29
        String nowDate = DateUtils.dateToString(new Date());
        if(1 == orderDetailVo.getType() && orderDetailVo.getEnd_date()!=null && 
        		DateUtils.dateToString(orderDetailVo.getEnd_date())!="" && 
        		nowDate.compareTo( DateUtils.dateToString(orderDetailVo.getEnd_date()))>0){
    		m.put("timeCompare", false);
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
        
        String orderId = "";
        //创建订单（任务），调北向api，modify by tangxr 2015-12-21
    	try {
    		SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
       	    String orderDate = odf.format(new Date());
       	         orderId = orderDate+String.valueOf(Random.fivecode());
    		//orderId = NorthAPIWorker.vulnScanCreateAPI(type, time*num, apiId, globle_user.getApikey(), globle_user.getId());
		} catch (Exception e) {
			m.put("message", "系统异常，暂时不能购买api，请稍后购买~~");
		}
    	//北向API返回orderId，创建用户订单
    	if(!orderId.equals("") && orderId != null){
    		//新增联系人
            Linkman linkObj = new Linkman();
            int linkmanId = Random.eightcode();
            linkObj.setId(linkmanId);
            linkObj.setName(linkname);
            linkObj.setMobile(phone);
            linkObj.setEmail(email);
            linkObj.setUserId(globle_user.getId());
            selfHelpOrderService.insertLinkman(linkObj);    ////
            
            Order order = new Order();
            order.setId(orderId);
            order.setType(1);
            order.setBegin_date(new Date());
            order.setEnd_date(getAfterFewMonth(new Date(),orderDetailVo.getWafTimes()));  ///waftimes字段中保存月份时长 结束时间= 当前时间+购买月份时长
            order.setServiceId(orderDetailVo.getServiceId());
            order.setCreate_date(new Date());
            order.setUserId(globle_user.getId());
            order.setContactId(linkmanId);
            order.setStatus(1);//..1:服务中  2:已结束 3:已下单 
            order.setPayFlag(0);
            order.setPrice(orderDetailVo.getPrice());
            order.setIsAPI(3);// 0:web  1：API  2:waf 3：系统安全帮
            order.setScan_type(Integer.parseInt(scanType)); //设置节点个数 
            order.setWafTimes(orderDetailVo.getWafTimes()); // 设置服务时长
            
            order.setRemarks(orderDetailVo.getIpArray());//获取ipArray，将serviceid=10的端口和ip存入cs_order
         
            selfHelpOrderService.insertOrder(order);   //插入cs_order
            
            /*
            //新增API订单
            OrderAPI oAPI = new OrderAPI();
            oAPI.setId(orderId);
            oAPI.setBegin_date(new Date());
            oAPI.setEnd_date(getAfterYear(new Date()));
            oAPI.setApiId(orderDetailVo.getServiceId());
            oAPI.setCreate_date(new Date());
            oAPI.setPackage_type(orderDetailVo.getType());
            oAPI.setNum(orderDetailVo.getWafTimes());
            oAPI.setBuyNum(orderDetailVo.getWafTimes());
            oAPI.setUserId(globle_user.getId());
            oAPI.setContactId(linkmanId);
            oAPI.setPayFlag(1);
            orderAPIService.insert(oAPI);
            
            //记录用户购买服务接口次数
            APICount count = new APICount();
            count.setUserId(globle_user.getId());
            count.setCount(orderDetailVo.getScan_type()*orderDetailVo.getWafTimes());
            count.setApiId(orderDetailVo.getServiceId());
            orderAPIService.insertOrUpdateCount(count);
            */
            
            //获得服务名称
            Serv servSys =servService.findById(orderDetailVo.getServiceId());
            
            //插入数据到order_list
		    OrderList ol = new OrderList();
		    //生成订单id
		    SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
			String orderDate = odf.format(new Date());
			String id = orderDate+String.valueOf(Random.fivecode());
//		    String  id = String.valueOf(Random.eightcode());
		    ol.setId(id);
		    ol.setCreate_date(new Date());
		    ol.setOrderId(orderId);
		    ol.setUserId(globle_user.getId());
		    ol.setPrice(orderDetailVo.getPrice());
		    ol.setServerName(servSys.getName());
		    orderListService.insert(ol);  //  插入到cs_order_list
		    m.put("orderListId", id);
            m.put("message", true);
    	}else{
    		m.put("message", "系统异常，暂时不能购买sys，请稍后购买~~");
    	}
        
        
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
     * 得到某个日期的后一年日期
     * @param d
     * @return
     */
    public static Date getAfterYear(Date d){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.YEAR,1); 
         date = calendar.getTime();  
         return date;
    }
    /**
     * 得到某个日期的后几个月的日期
     * @param d
     * @return
     */
    public static Date getAfterFewMonth(Date d,int duration){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.MONTH,duration); 
         date = calendar.getTime();  
         return date;
    }
	
	 /**
     * 功能描述： 计算商品价格
     * 参数描述： 
	 *  add  ltb
     *       @time 2016-12-7
     *      传参数只有 serviceId   scanType  
     */
    @RequestMapping(value="syscalPrice.html", method=RequestMethod.POST)
    @ResponseBody
	public void syscalPrice(HttpServletResponse response,HttpServletRequest request){
    	Map<String, Object> m = new HashMap<String, Object>();
    	//价格
		double calPrice = 0;
        //计算出的次数
        long times = 1;
        
        try{
        	//获得订单id
			int serviceId = Integer.parseInt(request.getParameter("serviceId"));
//			String type = request.getParameter("type");				//服务频率
//			String beginDate = request.getParameter("beginDate");//
//			String endDate = request.getParameter("endDate");
//			int assetCount = Integer.parseInt(request.getParameter("assetCount"));
			String scanType = request.getParameter("scanType");   //选类型   64  128
			int duration =Integer.parseInt(request.getParameter("duration"));   //选择服务时长
			//和运营管理数据同步
//			synPriceData(serviceId);
			
			
			
			//进行价格分析
			if (scanType!= null && scanType!=""){
				//长期
	        	int scanTypeint = Integer.valueOf(scanType); 			//服务频率
	        	calPrice = calPrice(serviceId, scanTypeint, duration);
				//times = calTimes(scanType, beginDate, endDate);	//计算服务需要执行的总次数
	        	
				//calPrice = calPrice(serviceId,scanType,times,assetCount);//计算价格
	        	//cs_scanType  scanType=6 对应64IP   scanType=7 对应  128ip
	        	//select * from cs_price where serviceId=#{serviceId}  <if test="type!=0"> and scan_type=#{type}
	        	/*
	        	List<Price> priceList = priceService.findPriceByServiceId(serviceId,scanTypeint);
	        	if (priceList == null|| priceList.size() == 0){		//按服务频率查询不到时，价格可能不按频率设置
	        		priceList = priceService.findPriceByScanTypeNull(serviceId);
	        	}
	        	if (priceList != null|| priceList.size() > 0)
	        	{
	        		for (int i = 0; i < priceList.size(); i++) {
						if (priceList.get(i).getScanType() == scanTypeint) {
							//calPrice = calPrice(serviceId, scanTypeint, duration);
						calPrice = priceList.get(i).getPrice();
							 break;
						}
					}
	        	}
	        	else {
					calPrice = 0;
				}
	        	*/				
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
	 *计算价格
	 *ltb
	 * @time  2017-3-2
	 * serviceId 对应cs_service表中id
	 * scanType 对应 cs_service表中 scan_Type字段 代表端点个数
	 * timelength  代表1，2，3.....11，12，24 个月
	 */
    
    private double calPrice(int serviceId, int scanType, int timelength) {
    	double sumPrice = 0;
    	
    	//根据serviceid查询价格列表
    	List<Price> priceList = priceService.findPriceByServiceId(serviceId,scanType);
    	if (priceList == null|| priceList.size() == 0){		//按服务频率查询不到时，价格可能不按频率设置
    		priceList = priceService.findPriceByScanTypeNull(serviceId); //pricemapper.xml   查找cs_price
    	}
    	
    	//价格列表不存在时，
    	if (priceList == null|| priceList.size() == 0){
    		return sumPrice;
    	}
    	
    	//price.getType()==0,1,2     0:单次价格；1：区间价格；2：大于区间价格
		for (int i = 0; i < priceList.size(); i++) {
			Price price = priceList.get(i);
			if(price.getType()== 1 && timelength > price.getTimesG() && timelength <= price.getTimesLE()){  //区间
				sumPrice = price.getPrice()*timelength;
				break;
			}else if (price.getType()== 2 && timelength>=price.getTimesG()){  //大于
				sumPrice = price.getPrice()*timelength*scanType/price.getTimesG();
				break;
			}
		}
		
		if (sumPrice == 0){   
			//例如：服务Id=1,times=1时,取单次的价格进行计算
			//     服务Id=4,times=1时,取第一个区间的价格进行计算
			sumPrice = priceList.get(0).getPrice()*timelength*scanType/priceList.get(0).getTimesG();
		}
		
		
		return sumPrice;
    	
    }
 
    

	/**
	 * 功能描述：返回修改订单
	 * 参数描述：  无
	 * @throws Exception 
	 *      add by ltb   2016-12-13
	 */
	@RequestMapping(value="sysorderBack.html", method=RequestMethod.POST)
	public String  sysorderBack(HttpServletResponse response,HttpServletRequest request) throws Exception{
		 Map<String, Object> map = new HashMap<String, Object>();
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		String result="";
		String[] assetArray = null;
		List assetIdsList = new ArrayList();
		String assetIds = request.getParameter("assetIds");
        String orderDetailId = request.getParameter("orderDetailId");
        String apiVal=request.getParameter("apiId");
        if(orderDetailId==null||"".equals(orderDetailId)){
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
			}
		}
        if(assetIds!=null&&!"".equals(assetIds)){
	    	   assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
	    	   for(int i=0;i<assetArray.length;i++){
	    		   assetIdsList.add(assetArray[i]);
	    	   }
		}
        OrderDetail orderDetailVal = selfHelpOrderService.findOrderDetailById(orderDetailId, globle_user.getId());
     //   OrderDetail orderDetail = selfHelpOrderService.getOrderDetailById(orderDetailId, globle_user.getId(),assetIdsList);
       if(orderDetailVal==null){
    	   return "redirect:/index.html";	
       }
	 /*   String assetAddr = "";
	    if(assetIds.length()>0){
	          assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
            	for(int i=0;i<assetArray.length;i++){
                	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]),globle_user.getId());
                	assetAddr = assetAddr + asset.getAddr()+",";
                }
	        	//根据id查询service add by tangxr 2016-3-14
	    	    Serv service = servService.findById(orderDetailVal.getServiceId());
	    	    //根据service Id查询服务详细信息
	    	    ServiceDetail servDetail = servDetailService.findByServId(orderDetailVal.getServiceId());
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
	     
	    }*/
	    //获取服务对象资产
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
	    //网站安全帮列表
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //查询安全能力API
		List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		//查询服务频率
		//查找服务频率
	    List<ScanType> scanTypeList = scanTypeService.findScanTypeById(orderDetailVal.getServiceId());
	 // 查询系统安全帮
	 		List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0, "");
	 		int carnum = shopCarList.size() + apiList.size() + sysList.size();
		request.setAttribute("carnum", carnum);  
		request.setAttribute("assetIds", assetIds);  
		request.setAttribute("orderDetail", orderDetailVal);
		request.setAttribute("scanTypeList", scanTypeList);  
		request.setAttribute("serviceAssetList", serviceAssetList);
		return result;
	}
	
	
    /**
     * 功能描述： 收银台页面 -支付宝
     * */
    @RequestMapping("AliPaycashierUI.html")
    public String AliPaycashier(Model model, HttpServletRequest request){
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
     * 功能描述： 支付成功页面
     * */
    @RequestMapping(value="repayUISys.html", method=RequestMethod.POST)
    public String toRepayUIsys(Model m,HttpServletRequest request, HttpServletResponse response){
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
			m.addAttribute("modifyOrderId", modifyOrderId);
			m.addAttribute("beginDate", orderList.getPay_date());  //部分服务调整的开始时间时间
		}
		
		if (orderList.getPay_date()== null){
			m.addAttribute("paySuccess", 1);
		} else {
			String[] api = orderList.getServerName().split(",");
//			//判断所有订单都是API
//			boolean apiOrderFlg = true;//true：所有订单都是API
//			for(String name: api) {
//				if(!name.endsWith("API")){
//					apiOrderFlg = false;
//					break;
//				}
//			}
//			m.addAttribute("apiOrderFlg", apiOrderFlg);
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
     * 功能描述： 确认支付
     * */
    @RequestMapping(value="payConfirmAli.html", method=RequestMethod.POST)
    public void payConfirmAli(HttpServletRequest request, HttpServletResponse response){
    	Map<String, Object> m = new HashMap<String, Object>();
    	try {
    		String orderListId = request.getParameter("orderListId");//订单编号(cs_order_list的id)
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
    		if (orderList.getPay_date() != null){
    			//不能重复支付
    			m.put("payFlag", 1);
    			return;
    		}
    		
    		
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
	 * 检查各服务的服务类型
	 * servType  
	 * orderType  
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
	 * 功能描述： 添加购物车-系统安全帮 参数描述： 无
	 * 
	 * @throws Exception
	 *             add by gxy 2016-5-03
	 */
	@RequestMapping(value = "shoppingCarSys.html", method = RequestMethod.POST)
	public void shoppingCarSys(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();

		
		String detailId = "";
		
		List<Price> priceList = new ArrayList();

		User globle_user = (User) request.getSession().getAttribute("globle_user");

		String orderType = request.getParameter("orderType");
		String beginDate = DateUtils.dateToString(new Date());
		String duration = request.getParameter("duration");
		String scanPeriod = request.getParameter("scanType"); // 频率
																// 绿盟64ip(scanType
																// = 15)
																// 128ip(16)
																// 金山10对应 10ip
		String serviceId = request.getParameter("serviceId");
		int intserviceId = Integer.parseInt(serviceId);
		String createDate = DateUtils.dateToString(new Date());
		int durationint = Integer.parseInt(duration);
		String endDate = DateUtils.dateToString(getAfterFewMonth(new Date(), durationint));
        
		String portMessage =null;
	    if(Integer.parseInt(serviceId) ==10)//如果是服务监测，就获取检测的ip和端口信息,如果不是就为空
	    {
	       portMessage = request.getParameter("portMessage");
//	       if (!checkIP(portMessage)) {
//				m.put("error", true);
//				// object转化为Json格式
//				JSONObject JSON = CommonUtil.objectToJson(response, m);
//				try {
//					// 把数据返回到页面
//					CommonUtil.writeToJsp(response, JSON);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				return;
//			}
	    }
		// 判断参数值是否为空
		if ((orderType == null || "".equals(orderType)) || (beginDate == null || "".equals(beginDate))
				|| (serviceId == null || "".equals(serviceId)) || (duration == null || "".equals(duration))) {
			m.put("error", true);
			// object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		// 根据判断服务id是否存在
		Serv service = servService.findById(Integer.parseInt(serviceId));
		if (service == null) {
			m.put("error", true);
			// object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		// 判断类型是否存在 ordertype是页面点击的类型
		if (!checkOrderType(serviceId, orderType)) {
			m.put("error", true);
			// object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// 单次
		if (orderType.equals("2")) {
			if (endDate != null && "".equals(endDate)) {
				m.put("error", true);
				// object转化为Json格式
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
		//判断是否可以添加购物车，如果有正在运行的购买的订单就不允许添加
		List orderList = orderService.findOrderByUserIdAndServiceId(globle_user.getId(), Integer.parseInt(serviceId));		
		if(orderList.size()>0 && orderList!=null){
			//HashMap<String,Object>  map = (HashMap<String,Object>)ol.get(j);
			HashMap<String,Object>  orderMap = (HashMap<String,Object>)orderList.get(0);								
			String strBeginDate = orderMap.get("begin_date").toString();
			String strEndDate =  orderMap.get("end_date").toString();			
			String strpayflag = orderMap.get("payFlag").toString();	
			String strisAPI = orderMap.get("isAPI").toString();
			String strNowDate = DateUtils.dateToString(new Date());	
			
			
			if (strNowDate.compareTo(strBeginDate)>0 && strNowDate.compareTo(strEndDate)<0 && strpayflag.equals("1")&&strisAPI.equals("3") && intserviceId!=10) {
				m.put("status", 3);
				//m.put("error", true);
				//m.put("message", "购物车已有同类订单，不能重复购买");
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				//CommonUtil.writeToJsp(response, JSON);				
				// object转化为Json格式
				//JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;

			}

		}
			
		// 判定是否可以添加购物车 ，如果购物车里有相同serviceId的订单，就不允许添加购物车
		List orderList2 = orderService.findOrderByUserIdAndServiceIdCheckShopCar(globle_user.getId(), Integer.parseInt(serviceId));
		if (orderList2.size() > 0 && orderList2 != null) {			
			HashMap<String, Object> orderMap2 = (HashMap<String, Object>) orderList2.get(0);			
			String strpayflag2 = orderMap2.get("payFlag").toString();
			String strisAPI = orderMap2.get("isAPI").toString();
			if (strpayflag2.equals("0") && strisAPI.equals("3")&& intserviceId!=10) {	                
				m.put("status", 4);
				//m.put("error", true);
				//m.put("message", "购物车已有同类订单，不能重复购买");
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				//CommonUtil.writeToJsp(response, JSON);				
				// object转化为Json格式
				//JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;

			}

		}
		
		
		
		// 计算价格
		double calPrice = 0;

		int serviceIdInt = Integer.parseInt(serviceId);
		int scanTypeInt = Integer.parseInt(scanPeriod);
		if (orderType != null && orderType.equals("1")) { // 长期
			priceList = priceService.findPriceByServiceId(serviceIdInt, scanTypeInt);
			if (priceList != null && priceList.size() != 0) {
				// scanPeriod 是web服务的服务频率 ，是系统安全帮的 扫描种类 有 6、7两种
				if (scanPeriod == null || "".equals(scanPeriod)) {
					m.put("error", true);
					// object转化为Json格式
					JSONObject JSON = CommonUtil.objectToJson(response, m);
					try {
						// 把数据返回到页面
						CommonUtil.writeToJsp(response, JSON);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				calPrice = calPrice(serviceIdInt, scanTypeInt, durationint);
			}
		} else {
			calPrice = 0;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		String price = df.format(calPrice); // 计算价格完成

		
        Order order = new Order();
        SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//
		String orderDate = odf.format(new Date());
		detailId = orderDate + String.valueOf(Random.fivecode());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //
		int linkmanId = Random.eightcode();
		
		order.setId(detailId);
		order.setType(Integer.parseInt(orderType));
		order.setBegin_date(DateUtils.stringToDateNYRSFM(beginDate));
		order.setEnd_date(DateUtils.stringToDateNYRSFM(endDate));
		 order.setServiceId(serviceIdInt);
		 order.setUserId(globle_user.getId());
		 order.setIsAPI(3); //
		 order.setPrice(Double.parseDouble(price));
		 order.setCreate_date(sdf.parse(createDate));
		 order.setWafTimes(durationint); 
		 order.setPayFlag(0);//未支付
		 order.setNum(Integer.parseInt(duration));
		 order.setContactId(linkmanId);
		 if (scanPeriod != null && !"".equals(scanPeriod)) {
				order.setScan_type(Integer.parseInt(scanPeriod));
		 }
		 order.setRemarks(portMessage); 
		 selfHelpOrderService.insertOrder(order);
		///////////////////////////////////////////

			// 新增联系人
			Linkman linkObj = new Linkman();
			
			linkObj.setId(linkmanId);
			linkObj.setName(globle_user.getName());
			linkObj.setMobile(globle_user.getMobile());
			linkObj.setEmail(globle_user.getEmail());
			linkObj.setUserId(globle_user.getId());
			
			selfHelpOrderService.insertLinkman(linkObj);		
		

		

		// 网站安全帮列表
		List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0, "");
		// 查询安全能力API
		List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0, "");
		// 查询系统安全帮
		List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0, "");
		int carnum = shopCarList.size() + apiList.size() + sysList.size();
		request.setAttribute("carnum", carnum);
		request.setAttribute("portMessage", portMessage);
		 m.put("sucess", true);
		 JSONObject JSON = CommonUtil.objectToJson(response, m);
	        // 把数据返回到页面
         CommonUtil.writeToJsp(response, JSON);
		 //object转化为Json格式
	       
		  
	}
	 /**
		 *检查是不是IP或者域名
		 *ltb
		 * @time  2017-3-2
		 * serviceId 对应cs_service表中id
		 * scanType 对应 cs_service表中 scan_Type字段 代表端点个数
		 * timelength  代表1，2，3.....11，12，24 个月
		 */
	private boolean checkIP(String portMessage) {
		if(portMessage.length()>200){//传过来的字符串长度大于200，就不能存入数据库
    		return false;
    	}
    	String[] str=null;
    	String isUrlIP=null;
		String port="";
		String[] temp=null;
		str=portMessage.split(";");
		
		boolean flagIP=false;
		boolean flagURL=false;
		
		//检查是域名还是ip
	    isUrlIP=str[0];
	    if(isUrlIP.startsWith(" ")){  
	    	 isUrlIP=  isUrlIP.substring(1, isUrlIP.length()).trim();  
        }  
        if(isUrlIP.endsWith(" ")){  
        	 isUrlIP=  isUrlIP.substring(0, isUrlIP.length()-1).trim();  
        }
        if(isUrlIP.matches("^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$#\\=~_\\-@]*)*$")){//是url
        	flagURL=true;
        	System.out.println("是url");
        }
        if(isUrlIP.matches("^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$")){  
        	flagIP=true;
        	System.out.println("是ip");
        }
        if(flagIP || flagURL){
        	
        }else{
        	return false;
        }        
		
		int i=0;
		for(i=1;i<str.length;i++){//检查端口号信息
			temp=str[i].split(":");	
			if(!temp[1].matches("^[0-9]*$")){
				return false;
			}
			if (Integer.parseInt(temp[1]) < 0 || Integer.parseInt(temp[1]) > 65535) {// 传过来的端口号小于0或者大于65535，返回首页
				return false;
			}
		}
		return true;
	
	}
	
}



