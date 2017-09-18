package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.pager.PageBean;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderListService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.DateUtils;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-4-21
 * 描        述：  个人中心——我的订单管理控制层
 * 版        本：  1.0
 */
@Controller
public class MyOrderController {
	
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
    @Autowired
    IOrderListService orderListService;
	
	/**
     * 功能描述： 个人中心——我的订单
     * 参数描述：  无
     *     @time 2016-4-21
     */
    @RequestMapping(value="orderTrackInit.html")
    public String orderTrackInit(HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        String state=request.getParameter("state");
        String list_group=request.getParameter("list_group");
        //获取订单信息
        //List orderList = orderService.findByUserId(globle_user.getId());
        //获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        request.setAttribute("nowDate",temp); 
        //request.setAttribute("orderList", orderList);
        request.setAttribute("mark","1");
        request.setAttribute("state", state);
        request.setAttribute("list_group", list_group);
        return "/source/page/personalCenter/my_order";
    }
    
    /**
     * 功能描述： 用户中心-订单跟踪-滚动加载
     * 参数描述：  无
     *     @time 2015-3-4
     */
    @RequestMapping(value="getOrderList.html")
    public ModelAndView getOrderList(HttpServletRequest request){
        //获取pageIndex
    	 ModelAndView mv = new ModelAndView("/source/page/personalCenter/orderList");
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String state=request.getParameter("state");
//        int list_group=Integer.parseInt(request.getParameter("list_group"));
        //根据pageIndex获取每页order条数,获取订单信息（逻辑删除订单不显示）
        List orderList = orderService.findByUserIdAndPage(globle_user.getId(),pageIndex,state,null,1);
        
        //根据orderId查询task表判断告警是否查看过
        if(orderList != null && orderList.size() > 0){
	        for(int i = 0; i < orderList.size(); i++){
	        	
	        	HashMap<String,Object>  mapOrder = (HashMap<String,Object>)orderList.get(i);
	        	String orderListId = (String)mapOrder.get("orderListId");
	        	//根据orderListId查询订单
	        	List ol = orderService.findByOrderListId(orderListId,state);
	        	for(int j = 0; j < ol.size(); j++){
	        		int alarmViewedFlag = 1;
		        	HashMap<String,Object>  map = (HashMap<String,Object>)ol.get(j);
		        	int serviceId =(Integer)map.get("serviceId");
		        	int status=(Integer)map.get("status");
		        	Date endDate =(Date) map.get("end_date");
		        	Map<String,Object> paramMap = new HashMap<String,Object>();
		        	String orderId = (String)map.get("id");
		        	String type = map.get("type").toString();
		        	paramMap.put("orderId", orderId);
		        	paramMap.put("type", type);
		        	//判断waf是否需要延期
		        	if(serviceId==6&&status==4){
			        	Date hourDate = DateUtils.getDateAfterHour(endDate);
			        	String h = formatter.format(hourDate);
			        	Date afterDate = new Date();
			        	if(hourDate.getTime()==afterDate.getTime()){
			        		 mv.addObject("Renew", true);
			        	}else{
			        		 mv.addObject("Renew", false);
			        	}
		        	}

//		        	List<Task> taskList = taskService.findAllByOrderId(paramMap);
//					if(taskList != null && taskList.size() > 0){
//						for (Task task : taskList) {
////							if(task.getAlarm_view_flag() != 1 && !task.getIssueCount().equals("0")){
//							if(task.getAlarm_view_flag() != 1 ){
//								alarmViewedFlag = 0;
//							}
//						}
//					}
//					map.put("alarmViewedFlag", alarmViewedFlag);
					
					
					//获取对应资产 add by tangxr 2016-4-25
//			        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
			        List<Asset> assetList = orderAssetService.findAssetsByOrderId(orderId);
			        map.put("assetList", assetList);
			        
			        //多资产情况下，判断已完成的 add by tangxr 2016-7-7 
//			        List<Task> tlist = taskService.findAllByOrderId(paramMap);
//					List<Task> finistlist = taskService.findFinishAlarmByOrderId(paramMap);
//					map.put("finistTask", finistlist.size());
	        	}
	        	mapOrder.put("order", ol);
	        }
        }
        
        //查询orderList表
        /*Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", globle_user.getId());
        //查订单消费记录
    	List ol = orderListService.getPayRecord(param);
    	if(ol != null && ol.size() > 0){
    		for(int i = 0; i < ol.size(); i++){
    			HashMap<String,Object>  map = (HashMap<String,Object>)ol.get(i);
    			//获取ids
    			String orderIds = (String) map.get("orderId");
    			if(orderIds!=null&&!"".equals(orderIds)){
    				String strArray[] = orderIds.split(",");
    				for (int m=0;m<strArray.length;m++){
    					Order o = orderService.findOrderById(strArray[m]);
    					map.put("o", o);
    				}
    			}
    		}
    	}*/
        
     
        Date currentTime = new Date();//得到当前系统时间 
        String temp = formatter.format(currentTime); //将日期时间格式化
        
      
        mv.addObject("orderList", orderList);
        mv.addObject("state", state);
        mv.addObject("nowDate", temp);
        return mv;
    }
    
    /**
     * 功能描述： 按条件分页查询订单
     * 参数描述： Model model
     *       @time 2015-3-6
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/searchCombineByPage.html")
    public ModelAndView searchCombineByPage(HttpServletRequest request){
    	ModelAndView mv = new ModelAndView("/source/page/personalCenter/orderList");
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String type = request.getParameter("type");
        String servName = request.getParameter("servName");
        String state = request.getParameter("state");
        String begin_datevo = request.getParameter("begin_date");
        String end_datevo = request.getParameter("end_date");
        String search = request.getParameter("search");
//        int list_group=Integer.parseInt(request.getParameter("list_group"));
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        //组织条件查询
        String name=servName;
        String searchText = null;//输入的查询文本
        try {
            //name=new String(servName.getBytes("ISO-8859-1"), "UTF-8");
            if(search!=null){
//            	searchText = new String(search.getBytes("ISO-8859-1"), "UTF-8");
            	searchText = search;
    			if(searchText.equals("输入资产名称或者资产地址进行搜索")){
    				searchText = "";
    			}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int isAPI = 0;
        if(servName!=""){
        	name = servName.substring(0,1);
            isAPI = Integer.parseInt(servName.substring(1));
        }
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId", globle_user.getId());
        paramMap.put("type", type);
        paramMap.put("servName", name);
        paramMap.put("state", state);
        paramMap.put("search", searchText);
        paramMap.put("isAPI", isAPI);
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
        List result = null;
       //根据资产查询
        result = orderService.findByCombineOrderTrackByPageAsset(paramMap);
//        if(result.size()<=0){
//        	//根据id查
//        	result = orderService.findByCombineOrderTrackByPage(paramMap);
//        }

        //根据orderId查询task表判断告警是否查看过
        if(result != null && result.size() > 0){
	        for(int i = 0; i < result.size(); i++){
	        	HashMap<String,Object>  mapOrder = (HashMap<String,Object>)result.get(i);
	        	String orderListId = (String)mapOrder.get("orderListId");
	        	//根据orderListId查询订单
	        	List ol = orderService.findByOrderListId(orderListId,state);
	        	for(int j = 0; j < ol.size(); j++){
	        		int alarmViewedFlag = 1;
		        	HashMap<String,Object>  map = (HashMap<String,Object>)ol.get(j);
		        	Map<String,Object> paramMap1 = new HashMap<String,Object>();
		        	String orderId = (String)map.get("id");
		        	int serviceId =(Integer)map.get("serviceId");
		        	int status=(Integer)map.get("status");
		        	Date endDate =(Date) map.get("end_date");
		        	String type1 = map.get("type").toString();
		        	paramMap.put("orderId", orderId);
		        	paramMap.put("type", type1);
		        	//判断waf是否需要延期
		        	if(serviceId==6&&status==4){
			        	Date hourDate = DateUtils.getDateAfterHour(endDate);
			        	String h = setDateFormat.format(hourDate);
			        	Date afterDate = new Date();
			        	if(hourDate.getTime()==afterDate.getTime()){
			        		   mv.addObject("Renew", true);//续费判断
			        	
			        	}else{
			        		  mv.addObject("Renew", false);//续费判断
			        	}
		        	}
		        	List<Task> taskList = taskService.findAllByOrderId(paramMap1);
					if(taskList != null && taskList.size() > 0){
						for (Task task : taskList) {
							if(task.getAlarm_view_flag() != 1 && !task.getIssueCount().equals("0")){
								alarmViewedFlag = 0;
							}
						}
					}
					map.put("alarmViewedFlag", alarmViewedFlag);
					//获取对应资产 add by tangxr 2016-4-25
//			        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
			        List<Asset> assetList = orderAssetService.findAssetsByOrderId(orderId);
			        map.put("assetList", assetList);
			        
			        //多资产情况下，判断已完成的 add by tangxr 2016-7-7 
//			        List<Task> tlist = taskService.findAllByOrderId(paramMap);
//					List<Task> finistlist = taskService.findFinishAlarmByOrderId(paramMap);
//					map.put("finistTask", finistlist.size());
	        	}
	        	mapOrder.put("order", ol);
	        }
        }
        
        
        mv.addObject("nowDate",temp); 
        mv.addObject("orderList",result);      //传对象到页面
        mv.addObject("type",type);//回显类型  
        mv.addObject("servName",name);//回显服务名称
        mv.addObject("state",state);//回显服务状态
        mv.addObject("begin_date",begin_datevo);//回显服务开始时间    
        mv.addObject("end_date",end_datevo);  //回显结束时间
        mv.addObject("search",searchText);  //回显查询文本
        mv.addObject("isAPI",isAPI);  //回显查询文本
        return mv;
    }
    
    
    /**
     * 功能描述： 按条件查询订单
     * 参数描述： Model model
     *       @time 2015-1-15
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/searchCombineOrderTrack.html")
    public String searchCombine(Model model,Integer type,String servName,String state,String begin_datevo,String end_datevo,String search, HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        //组织条件查询
        String name=null;//服务名称
        String searchText = null;//输入的查询文本
        try {
            //name=new String(servName.getBytes("ISO-8859-1"), "UTF-8");
        	name = servName;
            if(search!=null){
            	//searchText = new String(search.getBytes("ISO-8859-1"), "UTF-8");
            	searchText = search;
    			if(searchText.equals("输入资产名称或者资产地址进行搜索")){
    				searchText = "";
    			}
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        int isAPI = 0;
        if(servName!=""){
        	name = servName.substring(0,1);
            isAPI = Integer.parseInt(servName.substring(1));
        }
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId", globle_user.getId());
        paramMap.put("type", type);
        paramMap.put("servName", name);
        paramMap.put("state", state);
        paramMap.put("search", searchText);
        paramMap.put("isAPI", isAPI);
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
	        	HashMap<String,Object>  mapOrder = (HashMap<String,Object>)result.get(i);
	        	String orderListId = (String)mapOrder.get("orderListId");
	        	//根据orderListId查询订单
	        	List ol = orderService.findByOrderListId(orderListId,state);
	        	for(int j = 0; j < ol.size(); j++){
	        		int alarmViewedFlag = 1;
		        	HashMap<String,Object>  map = (HashMap<String,Object>)ol.get(j);
		        	Date endDate =(Date) map.get("end_date");
		        	int serviceId = Integer.parseInt(map.get("serviceId").toString());
		        	int status = Integer.parseInt(map.get("status").toString());
		        	//判断waf是否需要延期
		        	if(serviceId==6&&status==4){
			        	Date hourDate = DateUtils.getDateAfterHour(endDate);
			        	String h = setDateFormat.format(hourDate);
			        	Date afterDate = new Date();
			        	if(hourDate.getTime()==afterDate.getTime()){
			        		map.put("Renew", true);
			        	}
		        	}
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
					//获取对应资产 add by tangxr 2016-4-25
//			        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
			        List<Asset> assetList = orderAssetService.findAssetsByOrderId(orderId);
			        map.put("assetList", assetList);
			        
			        //多资产情况下，判断已完成的 add by tangxr 2016-7-7 
//			        List<Task> tlist = taskService.findAllByOrderId(paramMap);
//					List<Task> finistlist = taskService.findFinishAlarmByOrderId(paramMap);
//					map.put("finistTask", finistlist.size());
	        	}
	        	mapOrder.put("order", ol);
	        }
        }
        
        model.addAttribute("nowDate",temp); 
        model.addAttribute("orderList",result);      //传对象到页面
        model.addAttribute("type",type);//回显类型  
        model.addAttribute("servName",servName);//回显服务名称
        model.addAttribute("state",state);//回显服务状态
        model.addAttribute("begin_date",begin_datevo);//回显服务开始时间    
        model.addAttribute("end_date",end_datevo);  //回显结束时间
        model.addAttribute("search",searchText);  //回显查询文本
        model.addAttribute("isAPI",isAPI);  //回显查询文本
        return "/source/page/personalCenter/my_order";
    }
    
    /**
     * 功能描述： 个人中心-设置域名 页面的显示
     * 参数描述： Model model
     *       @time 2016-5-18
     */
    @RequestMapping(value="domainNameUI.html")
    public String domainNameUI(Model model, HttpServletRequest request){
    	//TODO
    	String orderId = request.getParameter("orderId");
    	//获取订单信息
        List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
        model.addAttribute("order", orderList.get(0));
        model.addAttribute("orderId", orderId);
        //获取服务ID
        int serviceId=0;
        HashMap<String, Object> order=new HashMap<String, Object>();
	    order=(HashMap) orderList.get(0);
	    serviceId=(Integer) order.get("serviceId");

	    List assets = orderAssetService.findAssetsByOrderId(orderId);
        if(assets != null && assets.size() > 0){
        	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	String addr=(String) assetOrder.get("addr");
        	String ipArray=(String) assetOrder.get("ipArray");
        	String domainIp=(String) assetOrder.get("domainIp");
        	String[] ips = null;   
            ips = ipArray.split(",");
            
            model.addAttribute("domainName", addr.substring(7)); //当前域名
            if(domainIp!=null && !domainIp.equals("")){
            	model.addAttribute("domainIp", domainIp); //当前A记录www值
            }else{
            	String newIpStr = "";
                for (int n = 0; n < ips.length; n++) {
                	String[] ip = ips[n].split(":");
                	newIpStr = newIpStr + ip[0] + ",";
                }
                domainIp = newIpStr.substring(0, newIpStr.length()-1);
            	model.addAttribute("domainIp", domainIp); //当前A记录www值
            }
        	
        }
    	return "/source/page/personalCenter/domainNameAnalysis";
    }
    
    /**
     * 功能描述： 检测域名 解析更改是否成功
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     * @time 2016-5-18
     */
    @RequestMapping(value="checkDomainName.html")
    public void checkDomainName(HttpServletRequest request, HttpServletResponse response){
    	Map<String,Object> m = new HashMap<String,Object>();
    	try {
    		String domainName = request.getParameter("domainName");
//    		String ipAddress = request.getParameter("ipAddress");
    		String orderId = request.getParameter("orderId");
    		
    		String ipAddress = "219.141.189.183";
    		//取得域名对应的IP地址
    		InetAddress inetAddress = InetAddress.getByName(domainName);
    		
    		System.out.println(inetAddress.getHostAddress());
    		//比较IP地址是否一致
    		if (!ipAddress.equals(inetAddress.getHostAddress())){
    			m.put("success", false);
    		}else{
    			Order order = new Order();
    			order.setId(orderId);
    			order.setStatus(4);
    			orderService.update(order);
    			//资产信息
    			List assets = orderAssetService.findAssetsByOrderId(orderId);
    			int id = 0;
    	    	if(assets != null && assets.size() > 0){
    	        	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
    	        	assetOrder=(HashMap) assets.get(0);
    	        	id= (Integer) assetOrder.get("orderAssetId");
    	        }
    			OrderAsset oa = new OrderAsset();
    			oa.setId(id);
    			oa.setDomainIp(ipAddress);
    			orderAssetService.update(oa);
    			m.put("success", true);
    			
//    			m.put("ipAddress", "219.141.189.183");
    		}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			m.put("success", false);
		}  
		
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
