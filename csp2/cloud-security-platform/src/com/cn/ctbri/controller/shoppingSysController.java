package com.cn.ctbri.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.ctbri.common.HuaweiWorker;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.entity.Serv;
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
import com.cn.ctbri.util.DateUtils;
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
			p.load(HuaweiWorker.class.getClassLoader().getResourceAsStream("InternalAPI.properties"));
			
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
	@RequestMapping(value="systemOrderOpera.html",method=RequestMethod.POST)
	public String systemOrderOpera(HttpServletRequest request){
		User globle_user = (User)request.getSession().getAttribute("globle_user");
		int serviceId  = Integer.parseInt(request.getParameter("serviceId"));
		
		//判断 serviceId是否存在
		List<Serv> servList = servService.findAllService();
		boolean flag = false;
		if(servList!=null && servList.size()>0)
		{
			for (int i = 0; i < servList.size(); i++) {
				if (serviceId==servList.get(i).getId()) {
					flag = true;
				}
			}
		}
		if (!flag) {
			return "redirect:/index.html";
		}
		//是否从首页进入
		String indexPage = request.getParameter("indexPage");
		Serv service = servService.findById(serviceId);
		//根据service id 查询服务详细信息
		ServiceDetail servDetail = servDetailService.findByServId(serviceId);
		if (servDetail == null) {
			return "redirect:/index.html";
		}
		
		//List priceList = priceService.findPriceByServiceId(serviceId, 0);
		
		String result = "/source/page/details/systemServDetails";
        return result;
	}

	/**
	 * 功能描述： 保存极光详情页操作
	 * 参数描述：  无
	 *     @time 2016-11-29
	 */
	@RequestMapping(value="jiGuangselfHelpOrderOpera.html",method=RequestMethod.POST)
	public String jiGuangselfHelpOrderOpera(HttpServletRequest request){
		String assetArray[] = null;
		String detailId = "";
		List assetIdList = new ArrayList();
		List<Price> priceList = new ArrayList();
		
		try {
			User globle_user = (User)request.getSession().getAttribute("globle_user");
			//String assetIds = request.getParameter("assetIds");
			String orderType = request.getParameter("type");  //orderType 从页面获取 包1月  包1年
	        String beginDate = request.getParameter("beginDate");
	        String endDate = request.getParameter("endDate");
	        String scanPeriod = request.getParameter("scanType"); // 0 单次长期   1单次  2长期  
	        String serviceId = request.getParameter("serviceId");
	        String createDate = DateUtils.dateToString(new Date());
			
	      //判断参数值是否为空
	        if((orderType==null||"".equals(orderType))||(serviceId==null||"".equals(serviceId))){
	        	return "redirect:/index.html";
	        }
			
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
			
			//计算价格
			double calPrice = 0;				
			try {
				int serviceIdInt = Integer.parseInt(serviceId);
				if (orderType!=null && orderType.equals("2")) {  //单次
					priceList = priceService.findPriceByServiceId(serviceIdInt, 0);
					if (priceList!=null && priceList.size()!=0) {
						//Price price = priceList.get(0);
						//calPrice = price.getPrice();
						
						 //scanPeriod 是web服务的服务频率  ，是系统安全帮的 扫描种类  有 6、7两种
				    	   if(scanPeriod==null || "".equals(scanPeriod)){
				    		   return "redirect:/index.html";
				    	   }
				    	   
						int scanTypeInt = Integer.parseInt(scanPeriod);
						for (int i = 0; i < priceList.size(); i++) {
							
							if (priceList.get(i).getType()==scanTypeInt ) {
								calPrice = priceList.get(i).getPrice();
								break;
							}
						}
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
		        orderDetail.setType(Integer.parseInt(orderType));
		        orderDetail.setServiceId(serviceIdInt);
		        orderDetail.setUserId(globle_user.getId());
		        orderDetail.setIsAPI(0);
		        orderDetail.setAsstId("0"); //
		        orderDetail.setPrice(Double.parseDouble(price));
		        orderDetail.setCreate_date(sdf.parse(createDate));
		        
		        if (scanPeriod!=null && !"".equals(scanPeriod)) {
					orderDetail.setScan_type(Integer.parseInt(scanPeriod));
				}
		        
		        selfHelpOrderService.SaveOrderDetail(orderDetail);
		        
		        request.setAttribute("orderDetail", orderDetail);
		        request.setAttribute("service", service);
		        request.setAttribute("user", globle_user);
		        
		        String result = "/source/page/details/settlement";
			
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
		return "";
		
		
	}
	
	
	/**
	 * 检查各服务的服务类型
	 * servType  
	 * orderType  0：单次和长期    1：长期     2：单次
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
	
}
