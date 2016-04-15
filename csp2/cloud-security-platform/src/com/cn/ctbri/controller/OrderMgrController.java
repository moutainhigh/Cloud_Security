package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.common.NorthAPIWorker;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceType;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskHW;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.CommonUtil;
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
    @Autowired
    ITaskService taskService;
    @Autowired
    ITaskHWService taskhwService;
    @Autowired
    IAlarmService alarmService;
    @Autowired
    ITaskWarnService taskWarnService;
    @Autowired
    IUserService userService;
    
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
//        String result = "/source/page/details/vulnScanDetails";
        return result;
	}
	
	
	 /**
	 * 功能描述： 结算
	 * 参数描述：  无
	 *     @time 2016-3-10
	 */
	@RequestMapping(value="settlement.html")
	public String settlement(HttpServletRequest request){
		//资产ids
        String assetIds = request.getParameter("assetIds");
		String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
//        String createDate = DateUtils.dateToString(new Date());
        String scanPeriod = request.getParameter("scanType");
        String serviceId = request.getParameter("serviceId");
        //联系人信息
//        String linkname = new String(request.getParameter("linkname").getBytes("ISO-8859-1"),"UTF-8");
//        String phone = request.getParameter("phone");
//        String email = request.getParameter("email");
//        String company = new String(request.getParameter("company").getBytes("ISO-8859-1"),"UTF-8");
//        String address = new String(request.getParameter("address").getBytes("ISO-8859-1"),"UTF-8");
        //华为参数
//        String ip = request.getParameter("ip");
//        String bandwidth = request.getParameter("bandwidth");
        //厂商
//        String websoc = request.getParameter("websoc");
        //任务数
//        String tasknum = request.getParameter("tasknum");
        
	    request.setAttribute("assetIds", assetIds);
	    request.setAttribute("orderType", orderType);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("scanType", scanPeriod);
        request.setAttribute("serviceId", serviceId);
        String result = "/source/page/details/settlement";
        return result;
	}
	
	/**
     * 功能描述： 筛选页面
     * 参数描述：  无
     *     @time 2015-2-2
     */
    @RequestMapping(value="getSession.html")
    @ResponseBody
    public void getSession(HttpServletResponse response,HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("globle_user", globle_user);
        
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
     * 功能描述： 筛选页面
     * 参数描述：  无
     *     @time 2015-2-2
     */
    @RequestMapping(value="filterPage.html")
    public String filterPage(HttpServletRequest request){
        String orderType = request.getParameter("type");
        String serviceId = request.getParameter("serviceId");
        //获取服务类型
        List<Serv> servList = selfHelpOrderService.findService();
        request.setAttribute("orderType",orderType); 
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("servList", servList);
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
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        int userId = globle_user.getId();
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        String assetIds = request.getParameter("assetIds");
        String scanType = request.getParameter("scanType");
        String scanDate = request.getParameter("scanDate");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String ip = request.getParameter("ip");
        int parentC = Integer.parseInt(request.getParameter("parentC"));
        List<Asset> list = null;
        List listorder = null;
        if(serviceId==6||serviceId==7||serviceId==8){//DDOS
        //if(parentC==2){
            OrderIP orderIP = new OrderIP();
            orderIP.setServiceId(serviceId);
            orderIP.setIp(ip);
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("serviceId", serviceId);
            paramMap.put("ip", ip);
            paramMap.put("beginDate", beginDate);
            paramMap.put("endDate", endDate);
            
            listorder = assetService.findorderIP(paramMap);
        }else{//web
            OrderAsset orderAsset = new OrderAsset();
            orderAsset.setServiceId(serviceId);
            if(scanType!="" && !scanType.equals("null")){
                orderAsset.setScan_type(Integer.parseInt(scanType));
            }
            Date scan_date = null;
            if(scanDate!=null && !scanDate.equals("")){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
                scan_date=sdf.parse(scanDate);
                orderAsset.setScan_date(scan_date);
            }
            orderAsset.setUserId(userId);
            //获取有同类服务中的资产
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
        
        if(listorder!=null&&listorder.size()>0){
            m.put("ipText", true);
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
     * 功能描述：  筛选
     * 参数描述：  HttpServletResponse response,HttpServletRequest request
     * @throws Exception 
     *       @time 2015-1-21
     */
    @RequestMapping(value="select.html", method = RequestMethod.POST)
    @ResponseBody
    public void select(HttpServletResponse response,HttpServletRequest request) throws Exception{
        String type = request.getParameter("type");
//        String factory = new String(request.getParameter("factory").getBytes("ISO-8859-1"),"UTF-8");
        String factory = request.getParameter("factory");
        String parentC = request.getParameter("parentC");
        String orderType = request.getParameter("orderType");
        String websoc = request.getParameter("websoc");
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
        if(!websoc.equals("null")){
            service.setWebsoc(Integer.parseInt(websoc));
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
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
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
        //用户
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
        //资产ids
        String assetIds = request.getParameter("assetIds");
        Map<String, Object> m = new HashMap<String, Object>();
        /*定义变量*/
        String[] assetIdArrayAble = null;//资产id数组
        String[] targetURL = null;//资产url数组
        String[] customManu = null;//指定厂家
        String scanType = "";//扫描方式（正常、快速、全量）
        String scanDepth = "";//检测深度
        String maxPages = "";//最大页面数
        String stategy = "";//策略
        
        //后台判断资产是否可用，防止恶意篡改资产
        boolean assetsStatus = false;
        if(assetIds!=null&&assetIds!=""){
        	String urls = "";
	        assetIdArrayAble = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
	        for(int i=0;i<assetIdArrayAble.length;i++){
	            Asset _asset = assetService.findById(Integer.parseInt(assetIdArrayAble[i]));
	            urls = urls + _asset.getAddr() + ",";
	            int status = _asset.getStatus();
	            if(status==0){
	                assetsStatus = true;
	                break;
	            }
	        }
	        targetURL = urls.split(",");
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
        
        //modify by tangxr 2016-2-1 orderId通过北向api获取
        String orderId = "";
        String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String createDate = DateUtils.dateToString(new Date());
        String scanPeriod = request.getParameter("scanType");
        String scanDate = request.getParameter("scanDate");
        String serviceId = request.getParameter("serviceId");
        //联系人信息
        String linkname = new String(request.getParameter("linkname").getBytes("ISO-8859-1"),"UTF-8");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String company = new String(request.getParameter("company").getBytes("ISO-8859-1"),"UTF-8");
        String address = new String(request.getParameter("address").getBytes("ISO-8859-1"),"UTF-8");
        //华为参数
        String ip = request.getParameter("ip");
        String bandwidth = request.getParameter("bandwidth");
        //厂商
        String websoc = request.getParameter("websoc");
        if(websoc != null && websoc != ""){
        	customManu = websoc.split(","); //拆分字符为"," ,然后把结果交给数组customManu 
        }
        //任务数
        String tasknum = request.getParameter("tasknum");
        //订单开始时间不能早于当前订单提交时间,add by tangxr,2015-3-3
        if(beginDate.compareTo(createDate)>0){
         	//创建订单（任务），调北向api，modify by tangxr 2015-12-21
        	try {
        		orderId = NorthAPIWorker.vulnScanCreate(orderType, targetURL, scanType, beginDate, endDate, scanPeriod,
            			scanDepth, maxPages, stategy, customManu, serviceId);
			} catch (Exception e) {
				// TODO: handle exception
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
	            order.setBegin_datevo(beginDate);
	            order.setEnd_datevo(endDate);
	            order.setBegin_date(begin_date);
	            order.setEnd_date(end_date);
	            order.setCreate_date(create_date);
	            if(scanPeriod!=null && !scanPeriod.equals("")){
	                order.setScan_type(Integer.parseInt(scanPeriod));
	            }
	            order.setServiceId(Integer.parseInt(serviceId));
	            
	            if (serviceId.equals("1") && orderType.equals("1")) {//漏洞长期
					Date executeTime = DateUtils.getOrderPeriods(beginDate,endDate,scanPeriod);
					order.setTask_date(executeTime);
				}else{
					order.setTask_date(begin_date);
				}
	            
	            order.setUserId(globle_user.getId());
	            order.setContactId(linkmanId);
	            order.setStatus(0);
	            if(serviceId.equals("6")||serviceId.equals("7")||serviceId.equals("8")){
	            	order.setWebsoc(0);
	            }else{
	            	if(!websoc.equals("null")){
	                	order.setWebsoc(Integer.parseInt(websoc));
	                }
	            }
	            order.setTasknum(Integer.parseInt(tasknum));
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
	                for(int i=0;i<assetIdArrayAble.length;i++){
	                    OrderAsset orderAsset = new OrderAsset();
	                    orderAsset.setOrderId(orderId);
	                    orderAsset.setAssetId(Integer.parseInt(assetIdArrayAble[i]));
	                    orderAsset.setServiceId(Integer.parseInt(serviceId));
	                    if(scanPeriod!=null && !scanPeriod.equals("")){
	                        orderAsset.setScan_type(Integer.parseInt(scanPeriod));
	                    }
	                    Date scan_date = null;
	                    if(scanDate!=null && !scanDate.equals("")){
	                        scan_date=sdf.parse(scanDate);
	                        orderAsset.setScan_date(scan_date);
	                    }
	                    orderAssetService.insertOrderAsset(orderAsset);
	                }
	            }
	            m.put("orderStatus", true);
			}else{
				m.put("orderStatus", false);
			}
            m.put("timeCompare", true);
            m.put("orderId", orderId);
        }else{
            m.put("timeCompare", false);
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
     * 得到某个日期的后一天日期
     * @param d
     * @return
     */
    public Date getAfterDate(Date d){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.DAY_OF_MONTH,1);  
         date = calendar.getTime();  
         return date;
    }
	
	/**
     * 功能描述： 用户中心-订单跟踪
     * 参数描述：  无
     *     @time 2015-1-12
     */
    @RequestMapping(value="orderTrackInit.html")
    public String orderTrackInit(HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        String state=request.getParameter("state");
        //获取订单信息
        //List orderList = orderService.findByUserId(globle_user.getId());
        //获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        request.setAttribute("nowDate",temp); 
        //request.setAttribute("orderList", orderList);
        request.setAttribute("mark","1");
        request.setAttribute("state", state);
        return "/source/page/order/orderTrack";
    }
    
    /**
     * 功能描述： 用户中心-订单跟踪-滚动加载
     * 参数描述：  无
     *     @time 2015-3-4
     */
    @RequestMapping(value="getOrderList.html")
    public ModelAndView getOrderList(HttpServletRequest request){
        //获取pageIndex
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        String state=request.getParameter("state");
        //根据pageIndex获取每页order条数,获取订单信息（逻辑删除订单不显示）
        List orderList = orderService.findByUserIdAndPage(globle_user.getId(),pageIndex,state,null);
        
        //根据orderId查询task表判断告警是否查看过
        if(orderList != null && orderList.size() > 0){
	        for(int i = 0; i < orderList.size(); i++){
	        	int alarmViewedFlag = 1;
	        	HashMap<String,Object>  map = (HashMap<String,Object>)orderList.get(i);
	        	Map<String,Object> paramMap = new HashMap<String,Object>();
	        	String orderId = (String)map.get("id");
	        	String type = map.get("type").toString();
	        	paramMap.put("orderId", orderId);
	        	paramMap.put("type", type);
	        	List<Task> taskList = taskService.findAllByOrderId(paramMap);
				if(taskList != null && taskList.size() > 0){
					for (Task task : taskList) {
						if(task.getAlarm_view_flag() != 1 && !task.getIssueCount().equals("0")){
							alarmViewedFlag = 0;
						}
					}
				}
				map.put("alarmViewedFlag", alarmViewedFlag);
	        }
        }
        
        //获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        ModelAndView mv = new ModelAndView("/source/page/order/orderList");
        mv.addObject("orderList", orderList);
        mv.addObject("state", state);
        mv.addObject("nowDate", temp);
        return mv;
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
            paramMap.put("begin_date", DateUtils.stringToDateNYRSFM(begin_datevo));
        }else{
            paramMap.put("begin_date", null);
        }
        if(StringUtils.isNotEmpty(end_datevo)){
            paramMap.put("end_date", DateUtils.stringToDateNYRSFM(end_datevo));
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
       
        //根据orderId查询task表判断告警是否查看过
        if(result != null && result.size() > 0){
	        for(int i = 0; i < result.size(); i++){
	        	int alarmViewedFlag = 1;
	        	HashMap<String,Object>  map = (HashMap<String,Object>)result.get(i);
	        	Map<String,Object> paramMap1 = new HashMap<String,Object>();
	        	String orderId = (String)map.get("id");
	        	String type1 = map.get("type").toString();
	        	paramMap.put("orderId", orderId);
	        	paramMap.put("type", type1);
	        	List<Task> taskList = taskService.findAllByOrderId(paramMap1);
				if(taskList != null && taskList.size() > 0){
					for (Task task : taskList) {
						if(task.getAlarm_view_flag() != 1 && !task.getIssueCount().equals("0")){
							alarmViewedFlag = 0;
						}
					}
				}
				map.put("alarmViewedFlag", alarmViewedFlag);
	        }
        }
        
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
     * 功能描述： 按条件分页查询订单
     * 参数描述： Model model
     *       @time 2015-3-6
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/searchCombineByPage.html")
    public ModelAndView searchCombineByPage(HttpServletRequest request){
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String type = request.getParameter("type");
        String servName = request.getParameter("servName");
        String state = request.getParameter("state");
        String begin_datevo = request.getParameter("begin_date");
        String end_datevo = request.getParameter("end_date");
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
            paramMap.put("begin_date", DateUtils.stringToDateNYRSFM(begin_datevo));
        }else{
            paramMap.put("begin_date", null);
        }
        if(StringUtils.isNotEmpty(end_datevo)){
            paramMap.put("end_date", DateUtils.stringToDateNYRSFM(end_datevo));
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
        //当前页
        int pageSize = 10;
        int pageNow = pageIndex*pageSize;
        paramMap.put("pageNow", pageNow);
        paramMap.put("pageSize", pageSize);
        List result = orderService.findByCombineOrderTrackByPage(paramMap);

        //根据orderId查询task表判断告警是否查看过
        if(result != null && result.size() > 0){
	        for(int i = 0; i < result.size(); i++){
	        	int alarmViewedFlag = 1;
	        	HashMap<String,Object>  map = (HashMap<String,Object>)result.get(i);
	        	Map<String,Object> paramMap1 = new HashMap<String,Object>();
	        	String orderId = (String)map.get("id");
	        	String type1 = map.get("type").toString();
	        	paramMap.put("orderId", orderId);
	        	paramMap.put("type", type1);
	        	List<Task> taskList = taskService.findAllByOrderId(paramMap1);
				if(taskList != null && taskList.size() > 0){
					for (Task task : taskList) {
						if(task.getAlarm_view_flag() != 1 && !task.getIssueCount().equals("0")){
							alarmViewedFlag = 0;
						}
					}
				}
				map.put("alarmViewedFlag", alarmViewedFlag);
	        }
        }
        
        ModelAndView mv = new ModelAndView("/source/page/order/orderList");
        mv.addObject("nowDate",temp); 
        mv.addObject("orderList",result);      //传对象到页面
        mv.addObject("type",type);//回显类型  
        mv.addObject("servName",name);//回显服务名称
        mv.addObject("state",state);//回显服务状态
        mv.addObject("begin_date",begin_datevo);//回显服务开始时间    
        mv.addObject("end_date",end_datevo);  //回显结束时间
        return mv;
    }
    
    
    /**
     * 功能描述： 用户中心-订单跟踪_订单删除
     * 参数描述：  无
     *     @time 2015-7-7
     */
    @RequestMapping(value="deleteOrder.html")
    public String deleteOrder(HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        String orderId = request.getParameter("orderId");
        //查找订单
        Order order = orderService.findOrderById(orderId);
        //已完成订单则逻辑删除
        if(order.getStatus()==1 || order.getStatus() == 2){
        	//更新删除标记
        	order.setDelFlag(1);
        	orderService.update(order);
//        }else{
        }else if(order.getStatus()==0){
            //获取订单资产
            List<OrderAsset> oaList = orderAssetService.findOrderAssetByOrderId(orderId);
            //删除任务
            for (OrderAsset orderAsset : oaList) {
                String order_asset_Id = String.valueOf(orderAsset.getId());
                List<Task> tlist = taskService.findTaskByOrderAssetId(orderAsset.getId());
                for (Task task : tlist) {
                    //删除告警
                    Map<String,Object> paramMap = new HashMap<String,Object>();
                    paramMap.put("taskId", task.getTaskId());
                    paramMap.put("group_id",task.getGroup_id());
                    paramMap.put("websoc",order.getWebsoc());
                    if(order.getServiceId()==5){
                        taskWarnService.deleteTaskWarnByTaskId(paramMap);
                    }else{
                        alarmService.deleteAlarmByTaskId(paramMap);
                    }
                    if(order.getWebsoc()!=1&&order.getWebsoc()!=2){
//                        String sessionId = ArnhemWorker.getSessionId();
//                        ArnhemWorker.removeTask(sessionId, String.valueOf(task.getTaskId())+"_"+orderId);
                    }
                }
                taskService.deleteTaskByOaId(order_asset_Id);
            }
            //删除订单
            orderService.deleteOrderById(orderId);
            //删除订单资产
            orderAssetService.deleteOaByOrderId(orderId);
            //删除联系人信息
            selfHelpOrderService.deleteLinkman(order.getContactId());
            //删除服务系统相关
            NorthAPIWorker.deleteOrder(orderId);
        }
        
//        return "/source/page/order/orderTrack";
        return "redirect:/orderTrackInit.html";
    }
    
    /**
     * 功能描述： check订单状态
     * 参数描述：  无
     *     @time 2015-8-6
     */
    @RequestMapping(value="checkOrderStatus.html")
    @ResponseBody
    public void checkOrderStatus(HttpServletResponse response,HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String beginDate = request.getParameter("begin_date");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String nowDate = df.format(new Date());
        //查找订单
        Order order = orderService.findOrderById(orderId);
        //根据orderId查询正在执行的任务
        List runList = orderService.findTaskRunning(orderId);
        //订单状态
        int status = order.getStatus();
        Map<String, Object> m = new HashMap<String, Object>();
//        if((status==0&&beginDate.compareTo(nowDate)>0)||status!=0){
        if(status==1||status==2||status==0){
            m.put("status", true);
        }else{
            m.put("status", false);
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
	 * 自助下单时判断订单数量
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/verificateTaskNum.html")
	@ResponseBody
	public void verificateTasknum(HttpServletResponse response,HttpServletRequest request) throws Exception{
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    
	    //根据用户id查询
	    List<User> userList = userService.findUserById(globle_user.getId());
	    User _user = userList.get(0);

		Map<String, Object> m = new HashMap<String, Object>();
		
		//二期：查询order表
		int oldCount = 0;
		Object object = orderService.findTaskNumsByUserId(globle_user.getId());
		if(object != null){
			oldCount = Integer.parseInt(String.valueOf(object));
		}
		//个人用户
		if(_user.getType()==2){
			if(oldCount >= 20){
				m.put("msg", true);//表示添加的任务数已经为20
			}else{
				m.put("msg", false);
			}
		}else{
			m.put("msg", false);
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
	 * 提交订单时判断订单数量
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/verificateSubmitTaskNum.html")
	@ResponseBody
	public void verificateSubmitTaskNum(HttpServletResponse response,HttpServletRequest request) throws Exception{
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    int nowCount = 0;//定义所有任务的和
	    //根据用户id查询
	    List<User> userList = userService.findUserById(globle_user.getId());
	    User _user = userList.get(0);

		Map<String, Object> m = new HashMap<String, Object>();

		//二期：查询order表
		int oldCount = 0;
		Object object = orderService.findTaskNumsByUserId(globle_user.getId());
		if(object != null){
			oldCount = Integer.parseInt(String.valueOf(object));
		}
		
		//计算新增的任务数
		int newCount = 0;

		int serviceIdInt = Integer.parseInt(request.getParameter("serviceId"));
		int assetCountInt = Integer.parseInt(request.getParameter("assetCount"));
		//下单类型：1：长期；2：单次
		String type = request.getParameter("type");
		//开始时间
		String beginDate = request.getParameter("beginDate");
		//结束时间
		String endDate = request.getParameter("endDate");
		//扫描类型
		String scanType = request.getParameter("scanType");
		//判断此次下单的任务数
		if(type.equals("2")){
			//单次:新增的任务数等于资产数
			newCount = assetCountInt;
			nowCount = oldCount + newCount;
			m.put("tasknum", newCount);
			
		}else{
			//长期：根据服务类型、扫描类型、开始时间、结束时间判断
			Date begin_date = null;
			Date end_date = null;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟 
			begin_date = sdf.parse(beginDate);
			end_date = sdf.parse(endDate);
			
	        Calendar calBegin = Calendar.getInstance();
	        Calendar calEnd = Calendar.getInstance();
	        calBegin.setTime(begin_date);  
	        calEnd.setTime(end_date);

            long time1 = calBegin.getTimeInMillis();                     
            long time2 = calEnd.getTimeInMillis();  
			switch(serviceIdInt){
				case 1:
					newCount = getOrderPeriods(beginDate, endDate, scanType);
					newCount = newCount * assetCountInt;
					break;
				case 2:							       
		            long between_min30=(time2-time1)/(1000*60*30);
		            newCount = (int)between_min30 * assetCountInt;
					break;
				case 3:
					long between_day = (time2-time1)/(1000*3600*24);
					newCount = (int)between_day * assetCountInt;
					break;
				case 4:
		            long between_day1=(time2-time1)/(1000*3600*24);
		            newCount = (int)between_day1 * assetCountInt;
					break;
				case 5:
					if(scanType.equals("3")){//每1小时
						long between_hour1 = (time2-time1)/(1000*3600);
						newCount = (int)between_hour1 * assetCountInt;
					}else{//每2小时
						long between_hour2 = (time2-time1)/(1000*3600*2);
						newCount = (int)between_hour2 * assetCountInt;
					}
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				
			}
			
			nowCount = oldCount + newCount;
		}
		//个人用户
		if(_user.getType()==2){
			if(nowCount > 20){
				m.put("msg", true);
			}else{
				m.put("tasknum", newCount);
				m.put("msg", false);
			}
		}else{//企业用户
			m.put("tasknum", newCount);
			m.put("msg", false);
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
	 * 获取周期数
	 * @param beginDate 开始时间
	 * @param endDate	结束时间
	 * @param scanType	扫描类型
	 * @return
	 */
	int getOrderPeriods(String beginDate, String endDate, String scanType){
		//初始化
		int count = 0;
		Date begin_date = null;
		Date end_date = null;
		
        String beginHour = beginDate.substring(11, 13);
        String beginMinute = beginDate.substring(14, 16);

        String endHour = endDate.substring(11, 13);
        String endMinute = endDate.substring(14, 16);
        
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟 
			begin_date = sdf.parse(beginDate);
			end_date = sdf.parse(endDate);
			
	        calBegin.setTime(begin_date);  
	        calEnd.setTime(end_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//周期为每天（00:10:00）
		if(scanType.equals("1")){  
            long time1 = calBegin.getTimeInMillis();                     
            long time2 = calEnd.getTimeInMillis();         
            long between_days=(time2-time1)/(1000*3600*24);
            //天数大于等于1
            if(between_days >= 1){
            	   if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
                       //当天开始执行
                   	if(endHour.equals("00")&&endMinute.compareTo("10")<0){
                   		count = (int)between_days + 1;
                   	}else{
                   		count = (int)between_days + 1;
                   	}
                   }else{
                       //第二天开始执行
                   	if(endHour.equals("00")&&endMinute.compareTo("10")<0){
                   		count = (int)between_days - 1;
                   	}else{
                   		count = (int)between_days;
                   	}
                   }
            }else{//天数小于1
            	if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
            		count = 1;
            	}else{
            		count = 0;
            	}
            }
         
		}else if(scanType.equals("2")){ //周期为每周（每周一00:10:00）
			 int dayForWeekBegin = 0;//开始时间星期几 
			 if(calBegin.get(Calendar.DAY_OF_WEEK) == 1){  
				 dayForWeekBegin = 7;  
			 }else{  
				 dayForWeekBegin = calBegin.get(Calendar.DAY_OF_WEEK) - 1;  
			 }  
			 int dayForWeekEnd = 0;//结束时间星期几 
			 if(calEnd.get(Calendar.DAY_OF_WEEK) == 1){  
				 dayForWeekEnd = 7;  
			 }else{  
				 dayForWeekEnd = calEnd.get(Calendar.DAY_OF_WEEK) - 1;  
			 }
			 
			 long time1 = calBegin.getTimeInMillis();                   
	         long time2 = calEnd.getTimeInMillis();         
	         long between_weeks=(time2-time1)/(1000*3600*24*7);
	         //开始时间为周一；结束时间也是周一
	         if(dayForWeekBegin==1 && dayForWeekEnd==1){
	        	 if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
	                 //当天开始执行
	             	if(endHour.equals("00")&&endMinute.compareTo("10")<0){
	             		count = (int)between_weeks - 1;
	             	}else{
	             		count = (int)between_weeks;
	             	}
	             }
	         }else if(dayForWeekBegin!=1 && dayForWeekEnd==1){//开始时间大于周一，从下周开始执行
	        	 if(endHour.equals("00")&&endMinute.compareTo("10")<0){
	        		 count = (int)between_weeks;
	        	 }else{
	        		 count = (int)between_weeks + 1;
	        	 }
	         }else if(dayForWeekBegin==1 && dayForWeekEnd!=1){//开始时间为周一，结束时间不为周一
	        	 if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
	        		 count = (int)between_weeks + 1;
	        	 }else{
	        		 count = (int)between_weeks;
	        	 }
	         }else{//开始时间，结束时间都不为周一
	        	 if(dayForWeekBegin != dayForWeekEnd && dayForWeekEnd != 7){
	        		 count = (int)between_weeks+1;
	        	 }else{
		        	 count = (int)between_weeks;
	        	 }

	         }
		}else{//周期为每月（1日00:10:00）
			int between_month = 0;     
			int flag = 0;  
			//月份第几天
			int beginDay = calBegin.get(Calendar.DAY_OF_MONTH);
			int endDay = calEnd.get(Calendar.DAY_OF_MONTH);

			between_month = (calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR))*12 + 
			calEnd.get(Calendar.MONTH)  - calBegin.get(Calendar.MONTH);

			//开始日期、结束日期都是1日
			if(beginDay==1 && endDay==1){
	        	 if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
	                 //当天开始执行
	             	if(endHour.equals("00")&&endMinute.compareTo("10")<0){
	             		count = (int)between_month - 1;
	             	}else{
	             		count = (int)between_month;
	             	}
	             }else{
	                 //当天开始执行
		             	if(endHour.equals("00")&&endMinute.compareTo("10")<0){
		             		count = (int)between_month - 1-1;
		             	}else{
		             		count = (int)between_month-1;
		             	} 
	             }
			}else if(beginDay!=1 && endDay==1){
             	if(endHour.equals("00")&&endMinute.compareTo("10")<0){
             		count = (int)between_month - 1;
             	}else{
             		count = (int)between_month;
             	}
			}else if(beginDay==1 && endDay!=1){
				 if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
	        		 count = (int)between_month + 1;
	        	 }else{
	        		 count = (int)between_month;
	        	 }
			}else{
				count = (int)between_month;
			}
		}
		return count;
	}
	
	
	/**
     * 功能描述： 订单操作
     * 参数描述：  无
	 * @throws JSONException 
     *     @time 2015-12-10
     */
    @RequestMapping(value="optOrder.html")
    @ResponseBody
    public void optOrder(HttpServletResponse response,HttpServletRequest request) throws JSONException{
        String orderId = request.getParameter("orderId");
        int status = Integer.parseInt(request.getParameter("status"));
        String opt = "";
        if(status == 4){
        	opt = "stop";
        }else if(status == 5){
        	opt = "resume";
        }
        //查找订单
        Order order = orderService.findOrderById(orderId);
        String optCode = NorthAPIWorker.vulnScanOptOrder(orderId,opt);
        Map<String, Object> m = new HashMap<String, Object>();
        if(optCode.equals("200")){
        	if(status==4){
        		order.setStatus(5);
        	}else if(status==5){
        		order.setStatus(4);
        	}
        	orderService.update(order);
        	m.put("status", true);
        }else{
        	m.put("status", false);
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
}
