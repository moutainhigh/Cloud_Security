package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.cn.ctbri.common.NorthAPIWorker;
import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceType;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.User;
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
import com.cn.ctbri.util.Random;

/**
 * ??? ??? ???  ???  txr
 * ???????????????  2015-1-12
 * ???        ??????  ?????????????????????
 * ???        ??????  1.0
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
    @Autowired
    IOrderListService orderListService;
	
	
	/**
     * ??????????????? ????????????
     * ???????????????  ???
     *     @time 2015-2-2
     */
    @RequestMapping(value="getSession.html")
    @ResponseBody
    public void getSession(HttpServletResponse response,HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("globle_user", globle_user);
        
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
     * ??????????????? ????????????
     * ???????????????  ???
     *     @time 2015-2-2
     */
    @RequestMapping(value="filterPage.html")
    public String filterPage(HttpServletRequest request){
        String orderType = request.getParameter("type");
        String serviceId = request.getParameter("serviceId");
        //??????????????????
        List servList = selfHelpOrderService.findService();
        request.setAttribute("orderType",orderType); 
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("servList", servList);
        return "/source/page/order/filter";
    }
	
	/**
     * ???????????????  ???????????????????????????IP???????????????????????????????????????????????????
     * ???????????????  HttpServletResponse response,HttpServletRequest request
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
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//?????????mm??????????????????  
                scan_date=sdf.parse(scanDate);
                orderAsset.setScan_date(scan_date);
            }
            orderAsset.setUserId(userId);
            //?????????????????????????????????
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
            //??????assetArray???assetArray1?????????????????????
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
     * ???????????????  ??????
     * ???????????????  HttpServletResponse response,HttpServletRequest request
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
     * ??????????????? ????????????
     * ???????????????  
	 * @throws Exception 
     *       @time 2015-01-16
     */
    @RequestMapping(value="saveOrder.html",method=RequestMethod.POST)
    @ResponseBody
    public void saveOrder(HttpServletResponse response,HttpServletRequest request) throws Exception{
      
    	  Map<String, Object> m = new HashMap<String, Object>();
          String assetArray[] = null;
          //??????
      	User globle_user = (User) request.getSession().getAttribute("globle_user");
      	List assetIdsList = new ArrayList();
      	 String createDate = DateUtils.dateToString(new Date());
         String linkname =request.getParameter("linkname");
         String phone = request.getParameter("phone");
         String email = request.getParameter("email");
       
      	//???????????????
      
      	String orderDetailId = request.getParameter("orderDetailId");
      	OrderDetail orderDetailVo =selfHelpOrderService.findOrderDetailById(orderDetailId, globle_user.getId());
      	if(orderDetailId==null||"".equals(orderDetailId)||linkname==null||"".equals(linkname)){
      		m.put("error", true);
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
      	if(orderDetailVo==null){
      		m.put("error", true);
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
    	String assetIds = orderDetailVo.getAsstId();
    	  assetArray = assetIds.split(","); //???????????????"," ,???????????????????????????strArray 
    	  for(int i=0;i<assetArray.length;i++){
   		   assetIdsList.add(assetArray[i]);
   	   }
    	
    	OrderDetail orderDetail =selfHelpOrderService.getOrderDetailById(orderDetailId, globle_user.getId(), assetIdsList);
    	if(orderDetail==null){
    		m.put("error", true);
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
    
        String orderId = "";
       
        /***??????????????????**/
        if(phone!=null&&!"".equals(phone)){
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");  
         Matcher matcher = p.matcher(phone); 
	       if(!matcher.find()){
	    		m.put("error", true);
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
    	
        }
       if(email!=null&&!"".equals(email)){
    	   Pattern emailP = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");  
           Matcher ematcher = emailP.matcher(email); 
           if(!ematcher.find()){
        	   m.put("error", true);
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
       }
      
     /***??????????????????**/
      
        //???????????????????????????????????????????????????????????????,add by tangxr,2015-3-3
        if(String.valueOf(orderDetail.getType()).equals("1") && orderDetail.getEnd_date()!=null && DateUtils.dateToString(orderDetail.getEnd_date())!="" && createDate.compareTo( DateUtils.dateToString(orderDetail.getEnd_date()))>0){
    		m.put("timeCompare", false);
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
    	//????????????????????????????????????api???modify by tangxr 2015-12-21
    	try {
    		/*orderId = NorthAPIWorker.vulnScanCreate(orderType, targetURL, scanType, beginDate, endDate, scanPeriod,
        			scanDepth, maxPages, stategy, customManu, serviceId);*/
    	SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
    	 String orderDate = odf.format(new Date());
    	   orderId = orderDate+String.valueOf(Random.fivecode());
		} catch (Exception e) {
			// TODO: handle exception
		}
		 //??????API??????orderId?????????????????????
    	if(!orderId.equals("") && orderId != null){
			//???????????????
            Linkman linkObj = new Linkman();
            int linkmanId = Random.eightcode();
            linkObj.setId(linkmanId);
            linkObj.setName(linkname);
            linkObj.setMobile(phone);
            linkObj.setEmail(email);
            linkObj.setUserId(globle_user.getId());
            selfHelpOrderService.insertLinkman(linkObj);
            //????????????
            Order order = new Order();
            order.setId(orderId);
            order.setType(orderDetail.getType());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//?????????mm??????????????????  
            Date begin_date = null;
            Date end_date = null;
            Date create_date = null;
            
            if(createDate!=null && !createDate.equals("")){
                create_date=sdf.parse(createDate); 
            }
            order.setBegin_datevo(DateUtils.dateToString(orderDetail.getBegin_date()));
            order.setEnd_datevo(DateUtils.dateToString(orderDetail.getEnd_date()));
            order.setBegin_date(orderDetail.getBegin_date());
            order.setEnd_date(orderDetail.getEnd_date());
            order.setCreate_date(create_date);
            order.setPrice(orderDetail.getPrice());
            
                order.setScan_type(orderDetail.getScan_type());
           
            order.setServiceId(orderDetail.getServiceId());
            
            if (orderDetail.getServiceId()==1 && orderDetail.getType()==1) {//????????????
				Date executeTime = DateUtils.getOrderPeriods(DateUtils.dateToString(orderDetail.getBegin_date()),DateUtils.dateToString(orderDetail.getEnd_date()),String.valueOf(orderDetail.getScan_type()));
				order.setTask_date(executeTime);
			}else{
				order.setTask_date(begin_date);
			}
            
            order.setUserId(globle_user.getId());
            order.setContactId(linkmanId);
            order.setStatus(0);
            order.setPayFlag(0);
           
            selfHelpOrderService.insertOrder(order);
            
            
         
                //??????????????????
                for(int i=0;i<assetIdsList.size();i++){
                	Asset asset = assetService.findById(Integer.parseInt(assetIdsList.get(i).toString()),globle_user.getId());
                    OrderAsset orderAsset = new OrderAsset();
                    orderAsset.setOrderId(orderId);
                    orderAsset.setAssetId(Integer.parseInt(assetIdsList.get(i).toString()));
                    orderAsset.setServiceId(orderDetail.getServiceId());
                    orderAsset.setScan_type(orderDetail.getScan_type());
                    orderAsset.setAssetAddr(asset.getAddr());
                    orderAsset.setAssetName(asset.getName());
                    orderAssetService.insertOrderAsset(orderAsset);
                }
         
            m.put("orderStatus", true);
		}else{
			m.put("orderStatus", false);
		}
        m.put("timeCompare", true);
        Serv serv = servService.findById(orderDetail.getServiceId());
        //???????????????order_list
   	    OrderList ol = new OrderList();
   	    //????????????id
//   	    String id = String.valueOf(Random.eightcode());
   	    SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
   		String orderDate = odf.format(new Date());
   		String id = orderDate+String.valueOf(Random.fivecode());
   	    ol.setId(id);
   	    ol.setCreate_date(new Date());
   	    ol.setOrderId(orderId);
   	    ol.setUserId(globle_user.getId());
   	    ol.setPrice(orderDetail.getPrice());
   	    ol.setServerName(serv.getName());
   	    orderListService.insert(ol);
   	    
   	    m.put("orderListId", id);
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
     * ????????????????????????????????????
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
     * ??????????????? ????????????-????????????_????????????
     * ???????????????  ???
     *     @time 2015-7-7
     */
    @RequestMapping(value="deleteOrder.html",method=RequestMethod.POST)
    public String deleteOrder(HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        String orderId = request.getParameter("orderId");
        //????????????
        Order order = orderService.findOrderById(orderId);
        //??????????????????????????????
        if(order.getIsAPI()==0){
            if(order.getStatus()==1 || order.getStatus() == 2){
            	//??????????????????
            	order.setDelFlag(1);
            	orderService.update(order);
//            }else{
            }else if(order.getStatus()==0){
                //??????????????????
                List<OrderAsset> oaList = orderAssetService.findOrderAssetByOrderId(orderId);
                //????????????
                for (OrderAsset orderAsset : oaList) {
                    String order_asset_Id = String.valueOf(orderAsset.getId());
                    List<Task> tlist = taskService.findTaskByOrderAssetId(orderAsset.getId());
                    for (Task task : tlist) {
                        //????????????
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
//                            String sessionId = ArnhemWorker.getSessionId();
//                            ArnhemWorker.removeTask(sessionId, String.valueOf(task.getTaskId())+"_"+orderId);
                        }
                    }
                    taskService.deleteTaskByOaId(order_asset_Id);
                }
                //????????????
                orderService.deleteOrderById(orderId,globle_user.getId());
                //??????????????????
                orderAssetService.deleteOaByOrderId(orderId,globle_user.getId());
                //?????????????????????
                selfHelpOrderService.deleteLinkman(order.getContactId(),globle_user.getId());
                //????????????????????????
                NorthAPIWorker.deleteOrder(orderId);
            }
        }else if(order.getIsAPI()==2){//waf
        	//????????????
            orderService.deleteOrderById(orderId,globle_user.getId());
            //??????????????????
            orderAssetService.deleteOaByOrderId(orderId,globle_user.getId());
            //?????????????????????
            selfHelpOrderService.deleteLinkman(order.getContactId(),globle_user.getId());
            //????????????????????????
            List assets = orderAssetService.findAssetsByOrderId(order.getId());
			HashMap<String, Object> assetOrder = new HashMap<String, Object>();
        	assetOrder=(HashMap) assets.get(0);
        	String targetKey = (String) assetOrder.get("targetKey");
//            WafAPIWorker.deleteVirtualSite("10001", targetKey);
        }

        
//        return "/source/page/order/orderTrack";
        return "redirect:/orderTrackInit.html";
    }
    
    
    /**
     * ??????????????? ????????????-????????????_????????????
     * ???????????????  ???
     *     @time 2015-7-7
     */
    @RequestMapping(value="userdeleteOrder.html",method=RequestMethod.POST)
    public String userdeleteOrder(HttpServletRequest request){
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        String orderId = request.getParameter("orderId");
        //????????????
        Order order = orderService.findOrderById(orderId);
        //??????????????????????????????
        if(order.getStatus()==1 || order.getStatus() == 2){
        	//??????????????????
        	order.setDelFlag(1);
        	orderService.update(order);
//        }else{
        }else if(order.getStatus()==0){
            //??????????????????
            List<OrderAsset> oaList = orderAssetService.findOrderAssetByOrderId(orderId);
            //????????????
            for (OrderAsset orderAsset : oaList) {
                String order_asset_Id = String.valueOf(orderAsset.getId());
                List<Task> tlist = taskService.findTaskByOrderAssetId(orderAsset.getId());
                for (Task task : tlist) {
                    //????????????
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
            //????????????
            orderService.deleteOrderById(orderId,globle_user.getId());
            //??????????????????
            orderAssetService.deleteOaByOrderId(orderId,globle_user.getId());
            //?????????????????????
            selfHelpOrderService.deleteLinkman(order.getContactId(),globle_user.getId());
            //????????????????????????
            NorthAPIWorker.deleteOrder(orderId);
        }
        
//        return "/source/page/order/orderTrack";
        return "redirect:/userCenterUI.html";
    }
    
    /**
     * ??????????????? check????????????
     * ???????????????  ???
     *     @time 2015-8-6
     */
    @RequestMapping(value="checkOrderStatus.html")
    @ResponseBody
    public void checkOrderStatus(HttpServletResponse response,HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        //String beginDate = request.getParameter("begin_date");
//        Date begin_Date = DateUtils.stringToDateNYRSFM(beginDate);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//??????????????????
        String nowDate = df.format(new Date());
        //????????????
        Order order = orderService.findOrderById(orderId);
        //??????orderId???????????????????????????
//        List runList = orderService.findTaskRunning(orderId);
        //????????????
        String beginDate = df.format(order.getBegin_date());
        int status = order.getStatus();
        int isAPI = order.getIsAPI();
        Map<String, Object> m = new HashMap<String, Object>();
//        if((status==0&&beginDate.compareTo(nowDate)>0)||status!=0){
//        if(isAPI==0 && (status==1||status==2||status==0)){
        if(isAPI==0 && (status==1||status==2||beginDate.compareTo(nowDate)>0)){
            m.put("status", true);
        }else if (isAPI==2 && (status==0||status==1||status==2||beginDate.compareTo(nowDate)>0)) {
        	 m.put("status", true);
        }else{
            m.put("status", false);
        }
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
	 * ?????????????????????????????????
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/verificateTaskNum.html")
	@ResponseBody
	public void verificateTasknum(HttpServletResponse response,HttpServletRequest request) throws Exception{
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    
	    //????????????id??????
	    List<User> userList = userService.findUserById(globle_user.getId());
	    User _user = userList.get(0);

		Map<String, Object> m = new HashMap<String, Object>();
		
		//???????????????order???
		int oldCount = 0;
		Object object = orderService.findTaskNumsByUserId(globle_user.getId());
		if(object != null){
			oldCount = Integer.parseInt(String.valueOf(object));
		}
		//????????????
		if(_user.getType()==2){
			if(oldCount >= 20){
				m.put("msg", true);//?????????????????????????????????20
			}else{
				m.put("msg", false);
			}
		}else{
			m.put("msg", false);
		}

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
	 * ?????????????????????????????????
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/verificateSubmitTaskNum.html",method=RequestMethod.POST)
	@ResponseBody
	public void verificateSubmitTaskNum(HttpServletResponse response,HttpServletRequest request) throws Exception{
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    int nowCount = 0;//????????????????????????
	    //????????????id??????
	    List<User> userList = userService.findUserById(globle_user.getId());
	    User _user = userList.get(0);

		Map<String, Object> m = new HashMap<String, Object>();

		//???????????????order???
		int oldCount = 0;
		Object object = orderService.findTaskNumsByUserId(globle_user.getId());
		if(object != null){
			oldCount = Integer.parseInt(String.valueOf(object));
		}
		
		//????????????????????????
		int newCount = 0;

		int serviceIdInt = Integer.parseInt(request.getParameter("serviceId"));
		int assetCountInt = Integer.parseInt(request.getParameter("assetCount"));
		//???????????????1????????????2?????????
		String type = request.getParameter("type");
		//????????????
		String beginDate = request.getParameter("beginDate");
		//????????????
		String endDate = request.getParameter("endDate");
		//????????????
		String scanType = request.getParameter("scanType");
		//??????????????????????????????
		if(type.equals("2")){
			//??????:?????????????????????????????????
			newCount = assetCountInt;
			nowCount = oldCount + newCount;
			m.put("tasknum", newCount);
			
		}else{
			//??????????????????????????????????????????????????????????????????????????????
			Date begin_date = null;
			Date end_date = null;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//?????????mm?????????????????? 
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
					if(scanType.equals("3")){//???1??????
						long between_hour1 = (time2-time1)/(1000*3600);
						newCount = (int)between_hour1 * assetCountInt;
					}else{//???2??????
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
		//????????????
		if(_user.getType()==2){
			if(nowCount > 20){
				m.put("msg", true);
			}else{
				m.put("tasknum", newCount);
				m.put("msg", false);
			}
		}else{//????????????
			m.put("tasknum", newCount);
			m.put("msg", false);
		}

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
	 * ???????????????
	 * @param beginDate ????????????
	 * @param endDate	????????????
	 * @param scanType	????????????
	 * @return
	 */
	int getOrderPeriods(String beginDate, String endDate, String scanType){
		//?????????
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
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//?????????mm?????????????????? 
			begin_date = sdf.parse(beginDate);
			end_date = sdf.parse(endDate);
			
	        calBegin.setTime(begin_date);  
	        calEnd.setTime(end_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//??????????????????00:10:00???
		if(scanType.equals("1")){  
            long time1 = calBegin.getTimeInMillis();                     
            long time2 = calEnd.getTimeInMillis();         
            long between_days=(time2-time1)/(1000*3600*24);
            //??????????????????1
            if(between_days >= 1){
            	   if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
                       //??????????????????
                   	if(endHour.equals("00")&&endMinute.compareTo("10")<0){
                   		count = (int)between_days + 1;
                   	}else{
                   		count = (int)between_days + 1;
                   	}
                   }else{
                       //?????????????????????
                   	if(endHour.equals("00")&&endMinute.compareTo("10")<0){
                   		count = (int)between_days - 1;
                   	}else{
                   		count = (int)between_days;
                   	}
                   }
            }else{//????????????1
            	if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
            		count = 1;
            	}else{
            		count = 0;
            	}
            }
         
		}else if(scanType.equals("2")){ //???????????????????????????00:10:00???
			 int dayForWeekBegin = 0;//????????????????????? 
			 if(calBegin.get(Calendar.DAY_OF_WEEK) == 1){  
				 dayForWeekBegin = 7;  
			 }else{  
				 dayForWeekBegin = calBegin.get(Calendar.DAY_OF_WEEK) - 1;  
			 }  
			 int dayForWeekEnd = 0;//????????????????????? 
			 if(calEnd.get(Calendar.DAY_OF_WEEK) == 1){  
				 dayForWeekEnd = 7;  
			 }else{  
				 dayForWeekEnd = calEnd.get(Calendar.DAY_OF_WEEK) - 1;  
			 }
			 
			 long time1 = calBegin.getTimeInMillis();                   
	         long time2 = calEnd.getTimeInMillis();         
	         long between_weeks=(time2-time1)/(1000*3600*24*7);
	         //????????????????????????????????????????????????
	         if(dayForWeekBegin==1 && dayForWeekEnd==1){
	        	 if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
	                 //??????????????????
	             	if(endHour.equals("00")&&endMinute.compareTo("10")<0){
	             		count = (int)between_weeks - 1;
	             	}else{
	             		count = (int)between_weeks;
	             	}
	             }
	         }else if(dayForWeekBegin!=1 && dayForWeekEnd==1){//????????????????????????????????????????????????
	        	 if(endHour.equals("00")&&endMinute.compareTo("10")<0){
	        		 count = (int)between_weeks;
	        	 }else{
	        		 count = (int)between_weeks + 1;
	        	 }
	         }else if(dayForWeekBegin==1 && dayForWeekEnd!=1){//????????????????????????????????????????????????
	        	 if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
	        		 count = (int)between_weeks + 1;
	        	 }else{
	        		 count = (int)between_weeks;
	        	 }
	         }else{//??????????????????????????????????????????
	        	 if(dayForWeekBegin != dayForWeekEnd && dayForWeekEnd != 7){
	        		 count = (int)between_weeks+1;
	        	 }else{
		        	 count = (int)between_weeks;
	        	 }

	         }
		}else{//??????????????????1???00:10:00???
			int between_month = 0;     
			int flag = 0;  
			//???????????????
			int beginDay = calBegin.get(Calendar.DAY_OF_MONTH);
			int endDay = calEnd.get(Calendar.DAY_OF_MONTH);

			between_month = (calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR))*12 + 
			calEnd.get(Calendar.MONTH)  - calBegin.get(Calendar.MONTH);

			//?????????????????????????????????1???
			if(beginDay==1 && endDay==1){
	        	 if(beginHour.equals("00")&&beginMinute.compareTo("10")<=0){
	                 //??????????????????
	             	if(endHour.equals("00")&&endMinute.compareTo("10")<0){
	             		count = (int)between_month - 1;
	             	}else{
	             		count = (int)between_month;
	             	}
	             }else{
	                 //??????????????????
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
     * ??????????????? ????????????
     * ???????????????  ???
	 * @throws JSONException 
     *     @time 2015-12-10
     */
    @RequestMapping(value="optOrder.html",method=RequestMethod.POST)
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
        //????????????
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
