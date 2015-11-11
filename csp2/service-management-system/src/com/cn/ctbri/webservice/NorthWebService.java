package com.cn.ctbri.webservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.apache.log4j.Logger;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IOrderTaskService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Respones;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-10-19
 * 描        述：  接收北向WebService请求接口
 * 版        本：  1.0
 */
@Component
@Path("openapi")
public class NorthWebService {
	//日志对象
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
    private ITaskService taskService;
	@Autowired
    private IOrderService orderService;
	@Autowired
    private IAssetService assetService;
	@Autowired
    private IAlarmService alarmService;
	@Autowired
    private IOrderTaskService orderTaskService;
	
	//创建订单
	@POST
    @Path("/createOrder")
	@Produces(MediaType.APPLICATION_JSON) 
    public String createOrder(JSONObject dataJson) throws JSONException {
		//order 对象
		JSONObject orderObj = dataJson.getJSONObject("orderObj");
		//url 数组
		JSONArray targetArray = dataJson.getJSONArray("targetURLs");
		Order order = new Order();
		order.setId(orderObj.getString("id"));
		order.setServiceId(orderObj.getInt("serviceId"));
		order.setType(orderObj.getInt("type"));
		order.setBegin_date(DateUtils.stringToDateNYRSFM(orderObj.getString("begin_datevo")));
		String end_date = orderObj.getString("end_datevo");
		if(end_date!=null&&!end_date.equals("")){
			order.setEnd_date(DateUtils.stringToDateNYRSFM(end_date));
		}
		order.setScan_type(orderObj.getInt("scan_type"));
		order.setStatus(orderObj.getInt("status"));
		order.setWebsoc(orderObj.getInt("websoc"));
        orderService.insertOrder(order);
        
        for (int i = 0; i < targetArray.length(); i++) {
	        OrderTask orderTask = new OrderTask();
	        orderTask.setOrderId(orderObj.getString("id"));
	        orderTask.setServiceId(orderObj.getInt("serviceId"));
	        orderTask.setType(orderObj.getInt("type"));
	        orderTask.setBegin_date(DateUtils.stringToDateNYRSFM(orderObj.getString("begin_datevo")));
			String endDate = orderObj.getString("end_datevo");
			if(endDate!=null&&!endDate.equals("")){
				orderTask.setEnd_date(DateUtils.stringToDateNYRSFM(endDate));
			}
			orderTask.setScan_type(orderObj.getInt("scan_type"));
			orderTask.setStatus(orderObj.getInt("status"));
			orderTask.setWebsoc(orderObj.getInt("websoc"));
			orderTask.setUrl(targetArray.get(i).toString());
			orderTaskService.insertOrderTask(orderTask);
        }
		Respones r = new Respones();
		r.setState("201");
		net.sf.json.JSONArray json = new net.sf.json.JSONArray().fromObject(r);
        return json.toString();
    }
	
	
	//主动告警
	@POST
    @Path("/vulnscan/order")
	@Produces(MediaType.APPLICATION_JSON) 
    public String vulnScanCreate(JSONObject dataJson) throws JSONException, ParseException {
		//单次，长期
		String scanMode = dataJson.getString("ScanMode");
		//开始时间
		String startTime = dataJson.getString("StartTime");
		//结束时间
		String endTime = dataJson.getString("EndTime");
	    //周期
		String scanPeriod = dataJson.getString("ScanPeriod");
		//订单id
		String orderId = dataJson.getString("orderId");
		//服务id
		String serviceId = dataJson.getString("serviceId");
		//创宇标识
		String websoc = dataJson.getString("websoc");
		//目标地址，可以多个
		JSONArray targetArray = dataJson.getJSONArray("targetURLs");
		//新增订单
        Order order = new Order();
        order.setId(orderId);
		order.setServiceId(Integer.parseInt(serviceId));
        order.setType(Integer.parseInt(scanMode));
        Date begin_date = null;
        Date end_date = null;
        Date create_date = null;
        int scan_type = 0;
        if(startTime!=null && !startTime.equals("")){
            begin_date=DateUtils.stringToDateNYRSFM(startTime);
        }
        if(endTime!=null && !endTime.equals("")){
        	end_date=DateUtils.stringToDateNYRSFM(endTime);
        }

        order.setBegin_date(begin_date);
        order.setEnd_date(end_date);
        if(scanPeriod!=null && !scanPeriod.equals("")){
        	scan_type = Integer.parseInt(scanPeriod);
        }
        order.setScan_type(scan_type);
        order.setTask_date(begin_date);
        order.setStatus(0);
        orderService.insertOrder(order);
        
        for (int i = 0; i < targetArray.length(); i++) {
	        OrderTask orderTask = new OrderTask();
	        orderTask.setOrderId(orderId);
	        orderTask.setServiceId(Integer.parseInt(serviceId));
	        orderTask.setType(Integer.parseInt(scanMode));
	        orderTask.setBegin_date(begin_date);
			orderTask.setEnd_date(end_date);
			orderTask.setScan_type(scan_type);
			orderTask.setStatus(0);
			orderTask.setUrl(targetArray.get(i).toString());
			
			if (serviceId.equals("1") && scanMode.equals("1")) {
				Date executeTime = getOrderPeriods(startTime, endTime, scanPeriod);
				orderTask.setTask_date(executeTime);
			}else{
				orderTask.setTask_date(begin_date);
			}
			orderTaskService.insertOrderTask(orderTask);
        }
		Respones r = new Respones();
		r.setState("201");
		net.sf.json.JSONArray json = new net.sf.json.JSONArray().fromObject(r);
        return json.toString();
    }
	
	
	/**
	 * 获取周期时间
	 * @param beginDate 开始时间
	 * @param endDate	结束时间
	 * @param scanType	扫描类型
	 * @return
	 */
	Date getOrderPeriods(String beginDate, String endDate, String scanType){
		//初始化
		int count = 0;
		Date begin_date = null;
		Date end_date = null;
		Date executeTime = null;
		
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
		Date taskTime = DateUtils.stringToDateNYRSFM(beginDate.substring(0, 10).concat(" 00:10:00"));
		if(scanType.equals("1")){  
            if(taskTime.compareTo(DateUtils.stringToDateNYRSFM(endDate))>0){
            	executeTime = null;
            }else if(taskTime.compareTo(DateUtils.stringToDateNYRSFM(beginDate))==0){
            	executeTime = taskTime;
            }else if(beginHour.equals("00")&&beginMinute.compareTo("10")<0){
            	executeTime = taskTime;
            }else{
            	executeTime = getAfterDate(taskTime);
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
		return executeTime;
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
	 
	public static void main(String[] args) throws JSONException {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		Order order = new Order();
		order.setId("66");
		
		net.sf.json.JSONObject orderObj = net.sf.json.JSONObject.fromObject(order);
		json.put("orderObj", orderObj);

	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
        //检查安全传输协议设置
        Client client = Client.create(config);
        //连接服务器
//        WebResource service = client.resource("http://localhost:8080/cspi/rest/openapi/vulnscan/order");
        
        WebResource service = client.resource("http://localhost:8080/cspi/rest/openapi/createOrder");
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json);        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses); 

	}
}
