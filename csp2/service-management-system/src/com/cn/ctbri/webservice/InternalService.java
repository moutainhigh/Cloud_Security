package com.cn.ctbri.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.API;
import com.cn.ctbri.service.IOrderAPIService;
import com.cn.ctbri.service.IServService;

@Component
@Path("servermanager/api")
public class InternalService {
	@Autowired
	IOrderAPIService apiService;
	
	//获取某订单调用接口的次数
	@POST
	@Path("/createAPICount/{orderid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_serviceCreateAPICount(@PathParam("orderid") String orderid) {
		JSONObject json = new JSONObject();
		try {
			//根据orderId查询调用创建接口成功的次数
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("orderId", orderid);
			List apiList = apiService.findAPIByParam(paramMap);

			json.put("createAPICount", apiList.size());
			json.put("code", 201);//返回201表示成功
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
		}
    	    	
        return json.toString();
    }
	
	//分析某订单号调用所有接口的次数
	@POST
	@Path("/analysisAPICount/{orderid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_analysisAPICount(@PathParam("orderid") String orderid) {
		JSONObject json = new JSONObject();
		try {
			//根据orderId查询调用所有接口次数
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("orderId", orderid);
			List apiList = apiService.findAllAPIByParam(paramMap);
			JSONArray jsonArray = new JSONArray().fromObject(apiList);

			json.put("apiArray", jsonArray);
			json.put("code", 201);//返回201表示成功
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
		}
    	    	
        return json.toString();
    }
	
	//查询某订单号调用所有接口的历史记录
	@POST
	@Path("/getAPIHistory/{orderid}")
	@Consumes(MediaType.APPLICATION_JSON)  
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_GetAPIHistory(@PathParam("orderid") String orderid,String param) {
		JSONObject json = new JSONObject();
		try {

			Map<String,Object> paramMap = new HashMap<String,Object>();

			JSONObject myJsonObject = JSONObject.fromObject(param);
			paramMap.put("orderId", orderid);
			paramMap.put("scanUrl", myJsonObject.get("scanUrl"));
			paramMap.put("beginDate", myJsonObject.get("beginDate"));
			paramMap.put("endDate", myJsonObject.get("endDate"));
			List apiList = apiService.findAPIHistoryInfoByParam(paramMap);
			JSONArray jsonArray = new JSONArray().fromObject(apiList);

			json.put("apiArray", jsonArray);
			json.put("code", 201);//返回201表示成功
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
		}
    	    	
        return json.toString();
    }
}
