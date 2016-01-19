package com.cn.ctbri.webservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.common.InternalWorker;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IOrderTaskService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Random;
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
	
	//创建漏洞扫描订单（任务）
	@POST
    @Path("/vulnscan/order")
	@Produces(MediaType.APPLICATION_JSON) 
    public String vulnScanCreate(String dataJson) {
		JSONObject jsonObj = new JSONObject().fromObject(dataJson);
		//单次，长期
		String scanMode = jsonObj.getString("ScanMode");
		//开始时间
		String startTime = jsonObj.getString("StartTime");
		//结束时间
		String endTime = jsonObj.getString("EndTime");
	    //周期
		String scanPeriod = jsonObj.getString("ScanPeriod");
		//订单id
		String orderId = jsonObj.getString("orderId");
		//服务id
		String serviceId = jsonObj.getString("serviceId");
		//创宇标识
		String websoc = jsonObj.getString("websoc");
		//userId
		int userId = jsonObj.getInt("userId");
		//目标地址，可以多个
		JSONArray targetArray = jsonObj.getJSONArray("targetURLs");
		//新增订单
        Order order = new Order();
        order.setId(orderId);
		order.setServiceId(Integer.parseInt(serviceId));
        order.setType(Integer.parseInt(scanMode));
        Date create_date = null;
        int scan_type = 0;
        if(scanPeriod!=null && !scanPeriod.equals("")){
        	scan_type = Integer.parseInt(scanPeriod);
        }
        Date begin_date = DateUtils.stringToDateNYRSFM(startTime);
        Date end_date = DateUtils.stringToDateNYRSFM(endTime);

        order.setBegin_date(begin_date);
        order.setEnd_date(end_date);
        
        order.setScan_type(scan_type);
        order.setTask_date(begin_date);
        order.setUserId(userId);
        order.setStatus(0);
        orderService.insertOrder(order);
        
        for (int i = 0; i < targetArray.size(); i++) {
	        OrderTask orderTask = new OrderTask();
	        orderTask.setOrderId(orderId);
	        orderTask.setServiceId(Integer.parseInt(serviceId));
	        orderTask.setType(Integer.parseInt(scanMode));
	        orderTask.setBegin_date(begin_date);
			orderTask.setEnd_date(end_date);
			orderTask.setScan_type(scan_type);
			orderTask.setStatus(0);
			orderTask.setWebsoc(Integer.parseInt(websoc));
			orderTask.setUrl(targetArray.get(i).toString());
			orderTask.setTask_status(1);
			orderTask.setUserId(userId);
			orderTask.setOrderTaskId(String.valueOf(Random.eightcode()));
			
			if (serviceId.equals("1") && scanMode.equals("1")) {//漏洞长期
				Date executeTime = DateUtils.getOrderPeriods(startTime,endTime,scanPeriod);
				orderTask.setTask_date(executeTime);
			}else{
				orderTask.setTask_date(begin_date);
			}
			orderTaskService.insertOrderTask(orderTask);
        }
		Respones r = new Respones();
		r.setState("201");
		net.sf.json.JSONObject json = new net.sf.json.JSONObject().fromObject(r);
        return json.toString();
    }
	
	
	//获取进度
	@GET
    @Path("/vulnscan/order/{orderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_Get_orderStatus(@PathParam("orderId") String orderId, @PathParam("taskId") String taskId) {
		JSONObject json = new JSONObject();
		try {
			//taskId 不空取任务信息，为空取订单状态
			if(taskId!=null && taskId!=""){
				Task task = new Task();
				task = taskService.findTaskByTaskId(taskId);
				if(task!=null){
					net.sf.json.JSONObject taskObject = new net.sf.json.JSONObject().fromObject(task);
					json.put("taskObj", taskObject);
					json.put("result", "success");
					Respones r = new Respones();
					r.setState("200");//成功获取
					net.sf.json.JSONArray state = new net.sf.json.JSONArray().fromObject(r);
					json.put("state", state);
			        return json.toString();
				}else{
					Respones r = new Respones();
					r.setState("421");//订单不存在
					net.sf.json.JSONArray state = new net.sf.json.JSONArray().fromObject(r);
					json.put("state", state);
			        return json.toString();
				}
			}else{
				Order o = orderService.findOrderByOrderId(orderId);
				Task t= taskService.findTaskByOrderId(orderId);
				JSONObject taskObject = new JSONObject().fromObject(t);
				json.put("taskObj", taskObject);
				json.put("status", o.getStatus());
				json.put("websoc", o.getWebsoc());
				return json.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Respones r = new Respones();
			r.setState("404");//获取状态失败
			net.sf.json.JSONArray state = new net.sf.json.JSONArray().fromObject(r);
			json.put("state", state);
	        return json.toString();
		}
    }
	
	
	//获取进度
	@GET
    @Path("/vulnscan/orderResult/{orderId}/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_Get_orderResult(@PathParam("orderId") String orderId, @PathParam("taskId") String taskId) {
		JSONObject json = new JSONObject();
		try {
			//taskId 不空取任务信息，为空取订单状态
			if(taskId!=null && taskId!=""){
				List<Alarm> alist = alarmService.findAlarmByTaskId(taskId);
				JSONArray alarmObject = new JSONArray().fromObject(alist);
				json.put("alarmObj", alarmObject);
				return json.toString();
			}else{
				return json.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Respones r = new Respones();
			r.setState("404");//获取状态失败
			net.sf.json.JSONArray state = new net.sf.json.JSONArray().fromObject(r);
			json.put("state", state);
	        return json.toString();
		}
    }
		
	
	//订单操作
	@PUT
    @Path("/vulnscan/order/{orderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject VulnScan_opt_orderTask(@PathParam("orderId") String orderId) {
		JSONObject json = new JSONObject();
		try {
			Order o = orderService.findOrderByOrderId(orderId);
			OrderTask ot = orderTaskService.findByOrderId(orderId);
			String result = InternalWorker.vulnScanOptOrderTask(orderId);
	        if(result.equals("200")){
	        	if(ot.getStatus()==4){
	        		ot.setStatus(5);//stop
	        	}else if(ot.getStatus()==5){
	        		ot.setStatus(4);//start
	        	}
	        	orderTaskService.update(ot);
	        	json.put("result", "success");
	        }else{
	        	json.put("result", "fail");
	        }			
	        return json;
		} catch (Exception e) {
			e.printStackTrace();
			Respones r = new Respones();
			r.setState("404");//获取状态失败
			net.sf.json.JSONArray state = new net.sf.json.JSONArray().fromObject(r);
			json.put("state", state);
	        return json;
		}
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
	 
	public static void main(String[] args) {
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
        WebResource service = client.resource("http://localhost:8080/cspi/rest/openapi/vulnscan/order/15123016431657620");
        
//        WebResource service = client.resource("http://localhost:8080/cspi/rest/openapi/createOrder");
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);        
//        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        JSONObject obj = new JSONObject().fromObject(addresses);
        
        net.sf.json.JSONObject taskObj = obj.getJSONObject("taskObj");
        String engineIP = taskObj.getString("engineIP");
        String taskProgress = taskObj.getString("taskProgress");
        String currentUrl = taskObj.getString("currentUrl");
        String issueCount = taskObj.getString("issueCount");
        String requestCount = taskObj.getString("requestCount");
        String urlCount = taskObj.getString("urlCount");
        String averResponse = taskObj.getString("averResponse");
        String averSendCount = taskObj.getString("averSendCount");
        String sendBytes = taskObj.getString("sendBytes");
        String receiveBytes = taskObj.getString("receiveBytes");
        System.out.println(obj);
        System.out.println(taskObj); 

	}
}
