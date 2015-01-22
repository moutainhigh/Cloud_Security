package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceType;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.util.Random;

/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-1-12
 * 描        述：  订单管理控制层
 * 版        本：  1.0
 */
@Controller
public class OrderMgrController {
	
    @Autowired
    ISelfHelpOrderService selfHelpOrderService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderAssetService orderAssetService;
    
	 /**
	 * 功能描述： 用户中心-自助下单
	 * 参数描述：  无
	 *     @time 2015-1-12
	 */
	@RequestMapping(value="selfHelpOrderInit.html")
	public String selfHelpOrderInit(HttpServletRequest request){
//	    String orderId = request.getParameter("orderId");
	    String type = request.getParameter("type");
	    String serviceId = request.getParameter("serviceId");
	    //获取服务类型
        List<Serv> servList = selfHelpOrderService.findService();
	    //获取服务类型
	    List<ServiceType> typeList = selfHelpOrderService.findServiceType();
	    //获取厂商
	    List<Factory> factoryList = selfHelpOrderService.findListFactory();
	    //获取服务对象资产
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset();
//	    Order order = new Order();
//	    if(orderId!=null && orderId!=""){
//	        order = orderService.findOrderById(orderId);
//	    }
	    request.setAttribute("servList", servList);
	    request.setAttribute("typeList", typeList);
        request.setAttribute("factoryList", factoryList);
        request.setAttribute("serviceAssetList", serviceAssetList);
        request.setAttribute("type", type);
        request.setAttribute("serviceId", serviceId);
//        request.setAttribute("orderId", orderId);
//        request.setAttribute("order", order);
        String result = "/source/page/order/order";
        return result;
	}
	
	/**
     * 功能描述： 保存订单
     * 参数描述：  
	 * @throws Exception 
     *       @time 2015-01-16
     */
    @RequestMapping(value="saveOrder.html")
    @ResponseBody
    public String saveOrder(HttpServletRequest request) throws Exception{
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        String assets = request.getParameter("assets");
        String orderId = request.getParameter("orderId");
        String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String createDate = request.getParameter("createDate");
        String scanType = request.getParameter("scanType");
        String serviceId = request.getParameter("serviceId");
        String linkname = request.getParameter("linkname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String company = request.getParameter("company");
        String address = request.getParameter("address");
        //新增联系人
        Linkman linkObj = new Linkman();
        int linkmanId = Random.eightcode();
        linkObj.setId(linkmanId);
        linkObj.setName(linkname);
        linkObj.setMobile(phone);
        linkObj.setEmail(email);
        linkObj.setAddress(address);
        linkObj.setCompany(company);
        linkObj.setUserId(globle_user.getId());
        selfHelpOrderService.insertLinkman(linkObj);
        //新增订单
        Order order = new Order();
        order.setId(orderId);
        order.setType(Integer.parseInt(orderType));
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
        }
        order.setServiceId(Integer.parseInt(serviceId));
        order.setTask_date(begin_date);
        order.setUserId(globle_user.getId());
        order.setContactId(linkmanId);
        selfHelpOrderService.insertOrder(order);
        
        //新增服务资产
        String[] assetArray = null;   
        assetArray = assets.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
        for(int i=0;i<assetArray.length;i++){
            OrderAsset orderAsset = new OrderAsset();
            orderAsset.setOrderId(orderId);
            orderAsset.setAssetId(Integer.parseInt(assetArray[i]));
            orderAssetService.insertOrderAsset(orderAsset);
        }
        request.setAttribute("isSuccess", true);
        return "/source/page/order/order";
    }
	
	/**
     * 功能描述： 用户中心-订单跟踪
     * 参数描述：  无
     *     @time 2015-1-12
     */
    @RequestMapping(value="orderTrackInit.html")
    public String orderTrackInit(HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        //获取订单信息
        List orderList = orderService.findByUserId(globle_user.getId());
        request.setAttribute("orderList", orderList);
        return "/source/page/order/orderTrack";
    }
	
	

	

	/**
	 * 功能描述： 把数据返回到页面
	 * 参数描述： HttpServletResponse response, JSONObject JSON
	 * @throws Exception 
	 *		 @time 2015-1-12
	 */
	private void writeToJsp(HttpServletResponse response, JSONObject JSON)
			throws IOException {
		response.getWriter().write(JSON.toString());
		response.getWriter().flush();
	}
	
	/**
	 * 功能描述：  object转化为Json格式
	 * 参数描述： HttpServletResponse response,Map<String, Object> m
	 * @throws Exception 
	 *		 @time 2015-1-12
	 */
	private JSONObject objectToJson(HttpServletResponse response,
			Map<String, Object> m) {
		JSONObject JSON = JSONObject.fromObject(m);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		return JSON;
	}
	
	
	
	
	
	
}
