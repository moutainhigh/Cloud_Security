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
	
	/**
     * 功能描述： 个人中心——我的订单
     * 参数描述：  无
     *     @time 2016-4-21
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
        return "/source/page/personalCenter/my_order";
    }
    
    /**
     * 功能描述： 用户中心-订单跟踪-滚动加载
     * 参数描述：  无
     *     @time 2015-3-4
     */
    @RequestMapping(value="getOrderList1.html")
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
//						if(task.getAlarm_view_flag() != 1 && !task.getIssueCount().equals("0")){
						if(task.getAlarm_view_flag() != 1 ){
							alarmViewedFlag = 0;
						}
					}
				}
				map.put("alarmViewedFlag", alarmViewedFlag);
				
				
				//获取对应资产 add by tangxr 2016-4-25
		        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
		        map.put("assetList", assetList);
	        }
        }
        
        //获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        ModelAndView mv = new ModelAndView("/source/page/personalCenter/orderList");
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
    @RequestMapping("/searchCombineByPage1.html")
    public ModelAndView searchCombineByPage(HttpServletRequest request){
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String type = request.getParameter("type");
        String servName = request.getParameter("servName");
        String state = request.getParameter("state");
        String begin_datevo = request.getParameter("begin_date");
        String end_datevo = request.getParameter("end_date");
        String search = request.getParameter("search");
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
        paramMap.put("search", search);
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
				//获取对应资产 add by tangxr 2016-4-25
		        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
		        map.put("assetList", assetList);
	        }
        }
        
        ModelAndView mv = new ModelAndView("/source/page/personalCenter/orderList");
        mv.addObject("nowDate",temp); 
        mv.addObject("orderList",result);      //传对象到页面
        mv.addObject("type",type);//回显类型  
        mv.addObject("servName",name);//回显服务名称
        mv.addObject("state",state);//回显服务状态
        mv.addObject("begin_date",begin_datevo);//回显服务开始时间    
        mv.addObject("end_date",end_datevo);  //回显结束时间
        mv.addObject("search",search);  //回显查询文本
        return mv;
    }
    
    
    /**
     * 功能描述： 按条件查询订单
     * 参数描述： Model model
     *       @time 2015-1-15
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/searchCombineOrderTrack1.html")
    public String searchCombine(Model model,Integer type,String servName,String state,String begin_datevo,String end_datevo,String search, HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        //组织条件查询
        String name=null;//服务名称
        String searchText = null;//输入的查询文本
        try {
            name=new String(servName.getBytes("ISO-8859-1"), "UTF-8");
            searchText = new String(search.getBytes("ISO-8859-1"), "UTF-8");
			if(searchText.equals("输入资产名称或者资产地址进行搜索")){
				searchText = "";
			}
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId", globle_user.getId());
        paramMap.put("type", type);
        paramMap.put("servName", name);
        paramMap.put("state", state);
        paramMap.put("search", searchText);
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
				//获取对应资产 add by tangxr 2016-4-25
		        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
		        map.put("assetList", assetList);
	        }
        }
        
        model.addAttribute("nowDate",temp); 
        model.addAttribute("orderList",result);      //传对象到页面
        model.addAttribute("type",type);//回显类型  
        model.addAttribute("servName",name);//回显服务名称
        model.addAttribute("state",state);//回显服务状态
        model.addAttribute("begin_date",begin_datevo);//回显服务开始时间    
        model.addAttribute("end_date",end_datevo);  //回显结束时间
        model.addAttribute("search",searchText);  //回显查询文本
        return "/source/page/personalCenter/my_order";
    }
    
}
