package com.cn.ctbri.webservice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cn.ctbri.common.SouthAPIWorker;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.logger.CSPLoggerAdapter;
import com.cn.ctbri.logger.CSPLoggerConstant;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IProducerService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Respones;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-10-12
 * 描        述：  接收内部WebService请求
 * 版        本：  1.0
 */
@Component
@Path("dssmanager")
public class ForManageService {
	@Autowired
    private ITaskService taskService;
	@Autowired
    private IEngineService engineService;	
	
	//获取资源信息
	@POST
    @Path("/getResource")
	@Produces(MediaType.APPLICATION_JSON) 
	public String VulnScan_Create_orderTask(String dataJson) {
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObj = JSONObject.fromObject(dataJson);
			//资源名称
			String resourcename = jsonObj.getString("resourcename");
			//资源地址
			String resourceaddr = jsonObj.getString("resourceaddr");
			//服务类别
			int isapi = jsonObj.getInt("isapi");
			
			Map<String, Object> resourcMap = new HashMap<String, Object>();
			resourcMap.put("resourcename", resourcename);
			resourcMap.put("resourceaddr", resourceaddr);
			resourcMap.put("isapi", isapi);
			List<EngineCfg> engineList = engineService.findResourceByParam(resourcMap);
			
			JSONArray engineObject = new JSONArray().fromObject(engineList);
			json.put("engineObj", engineObject);
			json.put("code", 201);//返回201表示成功
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
			json.put("message", "获取资源信息失败");
		}
        return json.toString();
    }
	 
/*	public static void main(String[] args) {
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
//        WebResource service = client.resource("http://localhost:8080/dss/rest/internalapi/vulnscan/orderTask/orderTaskid/6/9");
        
//        WebResource service = client.resource("http://localhost:8080/cspi/rest/openapi/createOrder");
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,json.toString());        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses); 

	}*/
}
