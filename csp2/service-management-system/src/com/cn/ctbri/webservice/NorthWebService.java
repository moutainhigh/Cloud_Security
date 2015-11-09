package com.cn.ctbri.webservice;

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

import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
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
	
	
	//主动告警
	@POST
    @Path("/vulnscan/order")
	@Produces(MediaType.APPLICATION_JSON) 
    public String vulnScanCreate(JSONObject dataJson) throws JSONException {
		String ScanMode = dataJson.getString("ScanMode");
		String StartTime = dataJson.getString("StartTime");
		String EndTime = dataJson.getString("EndTime");
		String ScanPeriod = dataJson.getString("ScanPeriod");
		JSONArray array = dataJson.getJSONArray("TargetURL");
		for (int i = 0; i < array.length(); i++) {
            System.out.println("array:" + array.get(i));
		}
		Respones r = new Respones();
		r.setState("201");
		net.sf.json.JSONArray json = new net.sf.json.JSONArray().fromObject(r);
        return json.toString();
    }
	 
	public static void main(String[] args) throws JSONException {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		json.put("ScanMode", 1);
		json.put("TargetURL", "http://localhost:80");

	    ClientConfig config = new DefaultClientConfig();
//	    config.getClasses().add(JacksonJsonProvider.class);
        //检查安全传输协议设置
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource("http://localhost:8080/cspi/rest/openapi/vulnscan/order");
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json);        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses); 

	}
}
