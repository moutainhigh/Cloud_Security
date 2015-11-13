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
 * 描        述：  接收内部WebService请求接口
 * 版        本：  1.0
 */
@Component
@Path("internalapi")
public class InternalWebService {
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
	
	
	//主动告警
	@POST
    @Path("/vulnscan/orderTask")
	@Produces(MediaType.APPLICATION_JSON) 
    public String vulnScanCreate(JSONObject dataJson) throws JSONException {
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
		//任务执行时间
		String taskTime = dataJson.getString("taskTime");
		//目标地址，只有一个
		String url = dataJson.getString("targetURL");
        int scan_type = 0;
        if(scanPeriod!=null && !scanPeriod.equals("")){
        	scan_type = Integer.parseInt(scanPeriod);
        }
        Date begin_date = DateUtils.stringToDateNYRSFM(startTime);
    	Date end_date = DateUtils.stringToDateNYRSFM(endTime);
    	Date task_date = DateUtils.stringToDateNYRSFM(taskTime);
    	
//        OrderTask orderTask = new OrderTask();
//        orderTask.setOrderId(orderId);
//        orderTask.setServiceId(Integer.parseInt(serviceId));
//        orderTask.setType(Integer.parseInt(scanMode));       
//        orderTask.setBegin_date(begin_date);
//		orderTask.setEnd_date(end_date);
//		orderTask.setScan_type(scan_type);
//		orderTask.setStatus(0);
//		orderTask.setUrl(url);
//		orderTask.setTask_status(1);
//		orderTask.setTask_date(task_date);
//		orderTaskService.insertOrderTask(orderTask);
    	
		
		Respones r = new Respones();
		r.setState("201");
		net.sf.json.JSONArray json = new net.sf.json.JSONArray().fromObject(r);
        return json.toString();
    }
	
	 
	public static void main(String[] args) throws JSONException {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
//		Order order = new Order();
//		order.setId("66");
//		
//		net.sf.json.JSONObject orderObj = net.sf.json.JSONObject.fromObject(order);
//		json.put("orderObj", orderObj);
		
		json.put("ScanMode", 2);
		json.put("targetURL", "");
		json.put("ScanType", "");
		json.put("StartTime", "2015-11-12 09:45:55");
		json.put("EndTime", "");
		json.put("ScanPeriod", 1);
		json.put("ScanDepth", "");
		json.put("MaxPages", "");
		json.put("Stategy", "");
		json.put("CustomManu", "");
		json.put("orderId", "999999");
		json.put("serviceId", 1);
		json.put("websoc", 1);
		json.put("taskTime", "");

	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
        //检查安全传输协议设置
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource("http://localhost:8080/dss/rest/internalapi/vulnscan/orderTask");
        
//        WebResource service = client.resource("http://localhost:8080/cspi/rest/openapi/createOrder");
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json);        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses); 

	}
}
