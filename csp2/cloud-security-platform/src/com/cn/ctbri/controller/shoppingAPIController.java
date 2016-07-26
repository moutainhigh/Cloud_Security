package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.entity.APICount;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAPI;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.ServiceAPI;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAPIService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderListService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.IServiceAPIService;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Random;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-28
 * 描        述：  订单管理控制层
 * 版        本：  1.0
 */
@Controller
public class shoppingAPIController {
	
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
    IServiceAPIService serviceAPIService;
    @Autowired
    IOrderAPIService orderAPIService;
    @Autowired
    IOrderListService orderListService;
	/**
	 * 功能描述： 购买API检测服务
	 * 参数描述：  无
	 *     @time 2016-3-28
	 */
	@RequestMapping(value="selfHelpOrderAPIInit.html")
	public String selfHelpOrderAPIInit(HttpServletRequest request){
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    int apiId = Integer.parseInt(request.getParameter("apiId"));
	    
	    //判断serviceId是否存在
	    List<ServiceAPI> servList = serviceAPIService.findServiceAPI();
        boolean flag = false;
        if(servList!=null && servList.size()>0){
            for(int i = 0; i < servList.size(); i++){
                if(apiId==servList.get(i).getId()){
                    flag = true;
                }
            }
        }
        if(!flag){
        	return "redirect:/index.html";
        }
        
	    //是否从首页进入
	    String indexPage = request.getParameter("indexPage");
	    //根据id查询serviceAPI, add by tangxr 2016-3-28
	    ServiceAPI serviceAPI = serviceAPIService.findById(apiId);
	    //网站安全帮列表
        List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
        //查询安全能力API
		   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		 int carnum=shopCarList.size()+apiList.size();
        request.setAttribute("apiId", apiId);
        request.setAttribute("indexPage", indexPage);
        request.setAttribute("serviceAPI", serviceAPI);
        request.setAttribute("carnum", carnum);
        String result = "";
        if(apiId==1){
        	result = "/source/page/details/apiDetails";
        }else if(apiId==2){
        	result = "/source/page/details/apiDetails2";
        }else if(apiId==3){
        	result = "/source/page/details/apiDetails3";
        }else if(apiId==4){
        	result = "/source/page/details/apiDetails4";
        }else if(apiId==5){
        	result = "/source/page/details/apiDetails5";
        }
        
        return result;
	}
	
	
	 /**
	 * 功能描述： 结算
	 * 参数描述：  无
	 *     @time 2016-3-10
	 */
	@RequestMapping(value="settlementAPI.html",method=RequestMethod.POST)
	public String settlementAPI(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
        String apiId = request.getParameter("apiId");
        //套餐
        String time =request.getParameter("time");
        //数量
        String num = request.getParameter("num");
        //套餐类型
        String type = request.getParameter("type");
        DecimalFormat df = new DecimalFormat("0.00");
       // String price = request.getParameter("price");
       String priceVal="";
       /**参数判断开始**/
       if(apiId==null||"".equals(apiId)||time==null||"".equals(time)||num==null||"".equals(num)||type==null||"".equals(type)){
    		return "redirect:/index.html";
       }
       /**参数判断结束**/
      // priceVal =  price.substring(price.indexOf("¥")+1,price.length()) ;
        //根据id查询serviceAPI, add by tangxr 2016-3-28
	    ServiceAPI serviceAPI = serviceAPIService.findById(Integer.parseInt(apiId));
	    if(serviceAPI==null){
	    	return "redirect:/index.html";
	    }
	   if(Integer.parseInt(num)<=0){
			return "redirect:/index.html";
	    }
	   if(!type.equals("1")&&!type.equals("2")&&!type.equals("3")){
		   return "redirect:/index.html";
	   }
	   if(time.equals("5")&&Integer.parseInt(num)>1){
		   return "redirect:/index.html";
	   }
	    OrderDetail orderDetail = new OrderDetail();
    	  SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
 	      String orderDate = odf.format(new Date());
 	     String  detailId = orderDate+String.valueOf(Random.fivecode());
 	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
 	      orderDetail.setId(detailId);
 	      orderDetail.setCreate_date(new Date());
 	      orderDetail.setBegin_date(new Date());
 	      orderDetail.setEnd_date(getAfterYear(new Date()));
 	      orderDetail.setType(Integer.parseInt(type));
 	      orderDetail.setWafTimes(Integer.parseInt(num));
 	      orderDetail.setServiceId(Integer.parseInt(apiId));
 	     orderDetail.setScan_type(Integer.parseInt(time));
 	      orderDetail.setPrice(0.0);
 	     orderDetail.setIsAPI(1);
 	     orderDetail.setUserId(globle_user.getId());
 	    selfHelpOrderService.SaveOrderDetail(orderDetail);
 	    
 	   OrderDetail orderDetailVo = selfHelpOrderService.getOrderAPIDetailById(detailId, globle_user.getId());
        request.setAttribute("user", globle_user);
	  /*  request.setAttribute("time", time);
	    request.setAttribute("num", num);
	    request.setAttribute("type", type);*/
	    //request.setAttribute("allPrice", df.format(Double.parseDouble(price)));
        //request.setAttribute("apiId", apiId);
        request.setAttribute("serviceAPI", serviceAPI);
        request.setAttribute("orderDetail", orderDetailVo);
  /*      request.setAttribute("mark", "api");//api标记
*/        String result = "/source/page/details/settlement";
        return result;
	}
	
	/**
     * 功能描述： 判断是否有购买api的权限
     * 参数描述：  
	 * @throws Exception 
     *       @time 2016-03-29
     */
    @RequestMapping(value="checkAPI.html",method=RequestMethod.POST)
    @ResponseBody
    public void checkAPI(HttpServletResponse response,HttpServletRequest request) throws Exception{
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        int apiId = Integer.parseInt(request.getParameter("apiId"));
        int time = Integer.parseInt(request.getParameter("time"));
        int num = Integer.parseInt(request.getParameter("num"));
        int type = Integer.parseInt(request.getParameter("type"));
        Map<String, Object> m = new HashMap<String, Object>();
        if(globle_user.getApikey()!=null && !globle_user.getApikey().equals("")){
        	//查询用户是否买过服务
//        	List<Order> list = orderService.findByUserId(globle_user.getId());
//        	if(list.size()>0){
        		//add by 2016-4-13 免费购买超过一次，提示不能购买
        		if(type==1){
        			Map<String, Object> paramMap = new HashMap<String, Object>();
    				paramMap.put("userId", globle_user.getId());
    		        paramMap.put("type", type);
    		        paramMap.put("apiId", apiId);
            		List<OrderAPI> oAPIList = orderAPIService.findOrderAPIByType(paramMap);
            		if(oAPIList.size()>=1){
            			m.put("message", "此项服务不能免费购买第二次");
            		}else{
            			m.put("message", true);
            		}
        		}else{
        			m.put("message", true);
        		}
//        	}else{
//        		m.put("message", "用户尚未购买过服务");
//        	}
        }else{
        	m.put("message", "用户无key");
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
     * 功能描述： 保存订单
     * 参数描述：  
	 * @throws Exception 
     *       @time 2016-03-29
     */
    @RequestMapping(value="saveOrderAPI.html",method=RequestMethod.POST)
    @ResponseBody
    public void saveOrderAPI(HttpServletResponse response,HttpServletRequest request) throws Exception{
        Map<String, Object> m = new HashMap<String, Object>();
        
        User globle_user = (User) request.getSession().getAttribute("globle_user");
      String orderDetailId = request.getParameter("orderDetailId");
    	
       /* //apiId
        int apiId = Integer.parseInt(request.getParameter("apiId"));
        //套餐次数
        int time = Integer.parseInt(request.getParameter("time"));
        //数量
        int num = Integer.parseInt(request.getParameter("num"));
        //套餐类型
        int type = Integer.parseInt(request.getParameter("type"));*/
        String linkname =request.getParameter("linkname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        if(orderDetailId==null||"".equals(orderDetailId)||linkname==null||"".equals(linkname)||phone==null||"".equals(phone)){
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
        Pattern p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");  
        
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
        OrderDetail orderDetailVo = selfHelpOrderService.getOrderAPIDetailById(orderDetailId, globle_user.getId());
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
            selfHelpOrderService.insertLinkman(linkObj);
            
            Order order = new Order();
            order.setId(orderId);
            order.setType(1);
            order.setBegin_date(new Date());
            order.setEnd_date(getAfterYear(new Date()));
            order.setServiceId(orderDetailVo.getServiceId());
            order.setCreate_date(new Date());
            order.setUserId(globle_user.getId());
            order.setContactId(linkmanId);
            order.setStatus(1);//完成
            order.setPayFlag(0);
            order.setPrice(orderDetailVo.getPrice());
            order.setIsAPI(1);//api订单
            selfHelpOrderService.insertOrder(order);
            
            //新增API订单
            OrderAPI oAPI = new OrderAPI();
            oAPI.setId(orderId);
            oAPI.setBegin_date(new Date());
            oAPI.setEnd_date(getAfterYear(new Date()));
            oAPI.setApiId(orderDetailVo.getServiceId());
            oAPI.setCreate_date(new Date());
            oAPI.setPackage_type(orderDetailVo.getType());
            oAPI.setNum(orderDetailVo.getScan_type()*orderDetailVo.getWafTimes());
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
            //获得服务名称
            ServiceAPI api =serviceAPIService.findById(orderDetailVo.getServiceId());
            
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
		    ol.setServerName(api.getName());
		    orderListService.insert(ol);
		    m.put("orderListId", id);
            m.put("message", true);
    	}else{
    		m.put("message", "系统异常，暂时不能购买api，请稍后购买~~");
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
	 * 功能描述： 添加购物车API
	 * 参数描述：  无
	 * @throws Exception 
	 *      add by gxy 2016-5-03
	 */
	@RequestMapping(value="shoppingCarAPI.html",method=RequestMethod.POST)
	public void shoppingCarAPI(HttpServletRequest request,HttpServletResponse response) throws Exception{
		  Map<String, Object> m = new HashMap<String, Object>();
		 User globle_user = (User) request.getSession().getAttribute("globle_user");
	        //apiId
	        String apiId = request.getParameter("apiId");
	        //套餐次数
	        String time = request.getParameter("time");
	        //数量
	       String num =request.getParameter("num");
	        //套餐类型
	        String type = request.getParameter("type");
	        /*String price = request.getParameter("price");
	        String priceVal="";
	        priceVal =  price.substring(price.indexOf("¥")+1,price.length()) ;*/
	        if(apiId==null||"".equals(apiId)||time==null||"".equals(time)||num==null||"".equals(num)||type==null||"".equals(type)){
	        	 m.put("error", true);
	        	//object转化为Json格式
	             JSONObject JSON = CommonUtil.objectToJson(response, m);
	             // 把数据返回到页面
	             CommonUtil.writeToJsp(response, JSON);
	             return;
	        }
	        if(!type.equals("1")&&!type.equals("2")&&!type.equals("3")){
	        	 m.put("error", true);
		        	//object转化为Json格式
		             JSONObject JSON = CommonUtil.objectToJson(response, m);
		             // 把数据返回到页面
		             CommonUtil.writeToJsp(response, JSON);
		             return;
	        }
	        if(time.equals("5")&&Integer.parseInt(num)>1){
	        	 m.put("error", true);
		        	//object转化为Json格式
		             JSONObject JSON = CommonUtil.objectToJson(response, m);
		             // 把数据返回到页面
		             CommonUtil.writeToJsp(response, JSON);
		             return;
	        }
	        if(Integer.parseInt(num)<1){
	        	 m.put("error", true);
		        	//object转化为Json格式
		             JSONObject JSON = CommonUtil.objectToJson(response, m);
		             // 把数据返回到页面
		             CommonUtil.writeToJsp(response, JSON);
		             return;
	        }
	        //根据id查询serviceAPI, add by tangxr 2016-3-28
		    ServiceAPI serviceAPI = serviceAPIService.findById(Integer.parseInt(apiId));
		    if(serviceAPI==null){
		    	m.put("error", true);
	        	//object转化为Json格式
	             JSONObject JSON = CommonUtil.objectToJson(response, m);
	             // 把数据返回到页面
	             CommonUtil.writeToJsp(response, JSON);
	             return;
		    }
	        SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
			String orderDate = odf.format(new Date());
	        String orderId = String.valueOf(Random.fivecode())+orderDate;
	      
	        
	    	//新增联系人
            Linkman linkObj = new Linkman();
            int linkmanId = Random.eightcode();
            linkObj.setId(linkmanId);
            linkObj.setName(globle_user.getName());
            linkObj.setMobile(globle_user.getMobile());
            linkObj.setEmail(globle_user.getEmail());
            linkObj.setUserId(globle_user.getId());
            selfHelpOrderService.insertLinkman(linkObj);
	        
	        
	        
	        
	        Order order = new Order();
            order.setId(orderId);
            order.setType(1);
            order.setBegin_date(DateUtils.getDateAfter10Mins(new Date()));
            order.setEnd_date(getAfterYear(DateUtils.getDateAfter10Mins(new Date())));
            order.setServiceId(Integer.parseInt(apiId));
            order.setCreate_date(new Date());
            order.setUserId(globle_user.getId());
            order.setPrice(0.0);
            order.setContactId(linkmanId);
            order.setStatus(1);//完成
            order.setPayFlag(0);
            order.setIsAPI(1);//api订单
            selfHelpOrderService.insertOrder(order);
            
            //新增API订单
            OrderAPI oAPI = new OrderAPI();
            oAPI.setId(orderId);
            oAPI.setBegin_date(DateUtils.getDateAfter10Mins(new Date()));
            oAPI.setEnd_date(getAfterYear(DateUtils.getDateAfter10Mins(new Date())));
            oAPI.setApiId(Integer.parseInt(apiId));
            oAPI.setCreate_date(new Date());
            oAPI.setPackage_type(Integer.parseInt(type));
            oAPI.setNum(Integer.parseInt(time)*Integer.parseInt(num));
            oAPI.setBuyNum(Integer.parseInt(num));
            oAPI.setUserId(globle_user.getId());
            oAPI.setContactId(linkmanId);
            oAPI.setPayFlag(0);
            orderAPIService.insert(oAPI);
            //网站安全帮列表
            List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
            //查询安全能力API
   		   List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
   		 int carnum=shopCarList.size()+apiList.size();
   		 request.setAttribute("carnum", carnum);  
	   m.put("sucess", true);
	//object转化为Json格式
       JSONObject JSON = CommonUtil.objectToJson(response, m);
       // 把数据返回到页面
       CommonUtil.writeToJsp(response, JSON);
	}
}
