package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Service;
import com.cn.ctbri.entity.ServiceType;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.scheduler.Scheduler4Task;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.util.DateUtils;
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
    @Autowired
    IAssetService assetService;
    @Autowired
    IServService servService;
    
	 /**
	 * 功能描述： 用户中心-自助下单
	 * 参数描述：  无
	 *     @time 2015-1-12
	 */
	@RequestMapping(value="selfHelpOrderInit.html")
	public String selfHelpOrderInit(HttpServletRequest request){
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
//	    String orderId = request.getParameter("orderId");
	    //订单类型
	    String type = request.getParameter("type");
	    //服务ID
	    String serviceId = request.getParameter("serviceId");
	    //是否从首页进入
	    String indexPage = request.getParameter("indexPage");
	    //获取服务类型
        List<Serv> servList = selfHelpOrderService.findService();
	    //获取服务类型
	    List<ServiceType> typeList = selfHelpOrderService.findServiceType();
	    //获取厂商
	    List<Factory> factoryList = selfHelpOrderService.findListFactory();
	    //获取服务对象资产
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
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
        request.setAttribute("indexPage", indexPage);
//        request.setAttribute("orderId", orderId);
//        request.setAttribute("order", order);
        String result = "/source/page/order/order";
        return result;
	}
	
	/**
     * 功能描述： 筛选页面
     * 参数描述：  无
     *     @time 2015-2-2
     */
    @RequestMapping(value="filterPage.html")
    public String filterPage(HttpServletRequest request){
        return "/source/page/order/filter";
    }
	
	/**
     * 功能描述：  针对同一资产或同一IP是否存在时间重叠的同一类型服务订单
     * 参数描述：  HttpServletResponse response,HttpServletRequest request
	 * @throws Exception 
     *       @time 2015-1-21
     */
    @RequestMapping(value="checkOrderAsset.html", method = RequestMethod.POST)
    @ResponseBody
    public void checkName(HttpServletResponse response,HttpServletRequest request) throws Exception{
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        String assetIds = request.getParameter("assetIds");
        String scanType = request.getParameter("scanType");
        String scanDate = request.getParameter("scanDate");
        String ip = request.getParameter("ip");
        List<Asset> list = null;
        List<OrderIP> listIP = null;
        if(serviceId==6||serviceId==7||serviceId==8){
            OrderIP orderIP = new OrderIP();
            orderIP.setServiceId(serviceId);
            orderIP.setIp(ip);
            listIP = assetService.findorderIP(orderIP);
        }else{
            OrderAsset orderAsset = new OrderAsset();
            orderAsset.setServiceId(serviceId);
            if(scanType!=null && !scanType.equals("")){
                orderAsset.setScan_type(Integer.parseInt(scanType));
            }
            Date scan_date = null;
            if(scanDate!=null && !scanDate.equals("")){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
                scan_date=sdf.parse(scanDate);
                orderAsset.setScan_date(scan_date);
            }
            list = assetService.findorderAssetByServId(orderAsset);
        }
        Map<String, Object> m = new HashMap<String, Object>();
        int count = 0;
        if(list!=null&&list.size()>0){
            count = list.size();
            m.put("count", count);
            String[] assetArray = null;   
            assetArray = assetIds.split(",");
            System.out.println(assetArray.length);
            
            String assets = "";
            String assetNames = "";
            for (Asset asset : list) {
                assets = assets + asset.getId() + ",";
                assetNames = assetNames + asset.getName() + ",";
            }
            assetNames = assetNames.substring(0,assetNames.length()-1);
            
            String[] assetArray1 = null;   
            assetArray1 = assets.split(",");
            //判断assetArray和assetArray1是否包含相同值
            for(int i=0;i<assetArray.length;i++){
                for(int j=0;j<assetArray1.length;j++){
                    if(assetArray[i].equals(assetArray1[j])){
                        m.put("assetNames", assetNames);
                        break;
                    }
                }
            }
        }
        
        if(listIP!=null&&listIP.size()>0){
            m.put("ipText", true);
        }
        //object转化为Json格式
        JSONObject JSON = objectToJson(response, m);
        try {
            // 把数据返回到页面
            writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 功能描述：  筛选
     * 参数描述：  HttpServletResponse response,HttpServletRequest request
     * @throws Exception 
     *       @time 2015-1-21
     */
    @RequestMapping(value="select.html", method = RequestMethod.POST)
    @ResponseBody
    public void select(HttpServletResponse response,HttpServletRequest request) throws Exception{
        String type = request.getParameter("type");
        String factory = new String(request.getParameter("factory").getBytes("ISO-8859-1"),"UTF-8");
        String parentC = request.getParameter("parentC");
        String orderType = request.getParameter("orderType");
        Serv service = new Serv();
        service.setOrderType(Integer.parseInt(orderType));
        if(!type.equals("null")){
            service.setType(Integer.parseInt(type));
        }
        if(!factory.equals("null")){
            service.setFactory(factory);
        }
        if(!parentC.equals("null")){
            service.setParentC(Integer.parseInt(parentC));
        }
        List<Serv> list = servService.findServiceByParam(service);
        int count = 0;
        if(list.size()>0){
            count = list.size();
        }
        String servs = "";
        for (Serv serv : list) {
            servs = servs + serv.getId() + ",";
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("count", count);
        m.put("servs", servs);
        
        //object转化为Json格式
        JSONObject JSON = objectToJson(response, m);
        try {
            // 把数据返回到页面
            writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
     * 功能描述： 保存订单
     * 参数描述：  
	 * @throws Exception 
     *       @time 2015-01-16
     */
    @RequestMapping(value="saveOrder.html")
    @ResponseBody
    public void saveOrder(HttpServletResponse response,HttpServletRequest request) throws Exception{
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        String assetIds = request.getParameter("assetIds");
        //String orderId = request.getParameter("orderId");
        String orderId = String.valueOf(Random.tencode());
        String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String createDate = request.getParameter("createDate");
        String scanType = request.getParameter("scanType");
        String scanDate = request.getParameter("scanDate");
        String serviceId = request.getParameter("serviceId");
        String linkname = request.getParameter("linkname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String company = request.getParameter("company");
        String address = request.getParameter("address");
        String ip = request.getParameter("ip");
        String bandwidth = request.getParameter("bandwidth");
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
        order.setStatus(0);
        selfHelpOrderService.insertOrder(order);
        
        if(serviceId.equals("6")||serviceId.equals("7")||serviceId.equals("8")){
            //新增ip段
            OrderIP orderIP = new OrderIP();
            orderIP.setOrderId(orderId);
            orderIP.setIp(ip);
            orderIP.setBandwidth(Integer.parseInt(bandwidth));
            orderIP.setServiceId(Integer.parseInt(serviceId));
            orderAssetService.insertOrderIP(orderIP);
        }else{
            //新增服务资产
            String[] assetArray = null;   
            assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
            for(int i=0;i<assetArray.length;i++){
                OrderAsset orderAsset = new OrderAsset();
                orderAsset.setOrderId(orderId);
                orderAsset.setAssetId(Integer.parseInt(assetArray[i]));
                orderAsset.setServiceId(Integer.parseInt(serviceId));
                if(scanType!=null && !scanType.equals("")){
                    orderAsset.setScan_type(Integer.parseInt(scanType));
                }
                Date scan_date = null;
                if(scanDate!=null && !scanDate.equals("")){
                    scan_date=sdf.parse(scanDate);
                    orderAsset.setScan_date(scan_date);
                }
                orderAssetService.insertOrderAsset(orderAsset);
            }
        }
        
        request.setAttribute("isSuccess", true);
        
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("orderId", orderId);
        
        //object转化为Json格式
        JSONObject JSON = objectToJson(response, m);
        try {
            // 把数据返回到页面
            writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Scheduler4Task task = new Scheduler4Task();
//        task.setTaskByOrder(order);
        //return "/source/page/order/order";
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
        //获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        request.setAttribute("nowDate",temp); 
        request.setAttribute("orderList", orderList);
        return "/source/page/order/orderTrack";
    }
	
    /**
     * 功能描述： 按条件查询订单
     * 参数描述： Model model
     *       @time 2015-1-15
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/searchCombineOrderTrack.html")
    public String searchCombine(Model model,Integer type,String servName,String state,String begin_datevo,String end_datevo,HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        //组织条件查询
        String name=null;
        try {
            name=new String(servName.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId", globle_user.getId());
        paramMap.put("type", type);
        paramMap.put("servName", name);
        paramMap.put("state", state);
        if(StringUtils.isNotEmpty(begin_datevo)){
            paramMap.put("begin_date", DateUtils.stringToDate(begin_datevo));
        }else{
            paramMap.put("begin_date", null);
        }
        if(StringUtils.isNotEmpty(end_datevo)){
            paramMap.put("end_date", DateUtils.stringToDate(end_datevo));
        }else{
            paramMap.put("end_date", null);
        }
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /* 時：分：秒  HH:mm:ss  HH : 23小時制 (0-23)
                                 kk : 24小時制 (1-24)
                                 hh : 12小時制 (1-12)
                                 KK : 11小時制 (0-11)*/
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        paramMap.put("currentDate", temp);
        List result = orderService.findByCombineOrderTrack(paramMap);
        
        model.addAttribute("nowDate",temp); 
        model.addAttribute("orderList",result);      //传对象到页面
        model.addAttribute("type",type);//回显类型  
        model.addAttribute("servName",name);//回显服务名称
        model.addAttribute("state",state);//回显服务状态
        model.addAttribute("begin_date",begin_datevo);//回显服务开始时间    
        model.addAttribute("end_date",end_datevo);  //回显结束时间
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
