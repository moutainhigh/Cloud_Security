package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ctbri.common.Constants;
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
import com.cn.ctbri.scheduler.Scheduler4Task;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.ITaskService;
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
            if(scanType!=null && !scanType.equals("null")){
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
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        String assetIds = request.getParameter("assetIds");
        //String orderId = request.getParameter("orderId");
        String orderId = String.valueOf(Random.tencode());
        String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String createDate = df.format(new Date());
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        
//        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String temp = setDateFormat.format(Calendar.getInstance().getTime());
//        String createDate = request.getParameter("createDate");
        String scanType = request.getParameter("scanType");
        String scanDate = request.getParameter("scanDate");
        String serviceId = request.getParameter("serviceId");
        String linkname = new String(request.getParameter("linkname").getBytes("ISO-8859-1"),"UTF-8");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String company = new String(request.getParameter("company").getBytes("ISO-8859-1"),"UTF-8");
        String address = new String(request.getParameter("address").getBytes("ISO-8859-1"),"UTF-8");
        String ip = request.getParameter("ip");
        String bandwidth = request.getParameter("bandwidth");
        Map<String, Object> m = new HashMap<String, Object>();
        //订单开始时间不能早于当前订单提交时间,add by txr,2015-3-3
        if(beginDate.compareTo(createDate)>0){
            m.put("timeCompare", true);
            m.put("orderId", orderId);
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
            //modify by txr 2015-03-27
            if(serviceId.equals("1")&&orderType.equals("1")){
                Scheduler4Task task = new Scheduler4Task();
                WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
                task.setAc(ac);
                task.setTaskByOrder(order); 
            }else if(serviceId.equals("3")||serviceId.equals("5")||orderType.equals("2")){//add by txr 2015-03-26
              //根据orderid 获取要扫描的订单详情集合
                List<OrderAsset> oaList = orderAssetService.findOrderAssetByOrderId(orderId);
                //获取订单定制的服务信息
                //Service s = orderDao.getTPLByServiceId();
                //遍历订单详情  创建任务
                for(OrderAsset oa : oaList){
                    Task task = new Task(); 
                    task.setExecute_time(begin_date);
                    task.setStatus(Integer.parseInt(Constants.TASK_START));
                    //设置订单详情id
                    task.setOrder_asset_Id(oa.getId());
                    //插入一条任务数据  获取任务id
                    int taskId = taskService.insert(task);
                }
            }else if((serviceId.equals("2")||serviceId.equals("4"))&&orderType.equals("1")){
                List<OrderAsset> oaList = orderAssetService.findOrderAssetByOrderId(orderId);
                for(OrderAsset oa : oaList){
                    Task task = new Task(); 
                    task.setExecute_time(sdf.parse(scanDate));
                    task.setStatus(Integer.parseInt(Constants.TASK_START));
                    //设置订单详情id
                    task.setOrder_asset_Id(oa.getId());
                    //插入一条任务数据  获取任务id
                    int taskId = taskService.insert(task);
                }
            }else{
                List<OrderIP> ipList = orderAssetService.findIpByOrderId(orderId);
                for(OrderIP oip : ipList){
                    TaskHW taskhw = new TaskHW(); 
                    taskhw.setExecute_time(begin_date);
                    taskhw.setStatus(Integer.parseInt(Constants.TASK_START));
                    //设置订单详情id
                    taskhw.setOrder_ip_Id(oip.getId());
                    taskhw.setEnd_time(end_date);
                    //插入一条任务数据  获取任务id
                    int taskId = taskhwService.insert(taskhw);
                }
            }
            
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
        request.setAttribute("mark","1");
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
        //根据pageIndex获取每页order条数,获取订单信息
        List orderList = orderService.findByUserIdAndPage(globle_user.getId(),pageIndex);
        //获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        ModelAndView mv = new ModelAndView("/source/page/order/orderList");
        mv.addObject("orderList", orderList);
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
        //当前页
        int pageSize = 10;
        int pageNow = pageIndex*pageSize;
        paramMap.put("pageNow", pageNow);
        paramMap.put("pageSize", pageSize);
        List result = orderService.findByCombineOrderTrackByPage(paramMap);
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
	
}
