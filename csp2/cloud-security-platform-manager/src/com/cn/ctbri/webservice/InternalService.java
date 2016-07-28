package com.cn.ctbri.webservice;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.ApiPrice;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.service.IServService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
@Component
@Path("servermanager")
public class InternalService {
	@Autowired
	IServService servService;
	
	//获取服务价格
	@POST
	@Path("/price/vindicatePrice/{serverid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_servicePrice(@PathParam("serverid") int serverid) {
		JSONObject json = new JSONObject();
		//JSONArray jsonArray = new JSONArray();
		try {
			//根据serviceid查询价格列表
			List<Price> priceList = servService.findPriceByServiceId(serverid);
/*			if(priceList!=null&& priceList.size()>0){
				//for(int i = 0; i < priceList.size(); i++){
					JSONObject newJson = new JSONObject();
					newJson.put("GTR", priceList.get(i).getTimesG());
					newJson.put("ITE", priceList.get(i).getTimesLE());
					newJson.put("Price", priceList.get(i).getPrice());
					//jsonArray.add(priceList);
				//}
			}*/
			JSONArray jsonArray = new JSONArray().fromObject(priceList);
			json.put("PriceStr", jsonArray);
			/*
			}
			json.put("code", 201);//返回201表示成功
*/		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
			json.put("message", "创建订单任务失败");
		}
    	    	
        return json.toString();
    }
	
	//获取API服务价格
	@POST
	@Path("/APIPrice/comboPrice/{serverid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScanServiceApiPrice(@PathParam("serverid") int serverid) {
		JSONObject json = new JSONObject();
		//JSONArray jsonArray = new JSONArray();
		try {
			//根据serviceid查询价格列表
			List<ApiPrice> priceList = servService.findApiPriceByServiceId(serverid);
/*			if(priceList!=null&& priceList.size()>0){
				//for(int i = 0; i < priceList.size(); i++){
					JSONObject newJson = new JSONObject();
					newJson.put("GTR", priceList.get(i).getTimesG());
					newJson.put("ITE", priceList.get(i).getTimesLE());
					newJson.put("Price", priceList.get(i).getPrice());
					//jsonArray.add(priceList);
				//}
			}*/
			JSONArray jsonArray = new JSONArray().fromObject(priceList);
			json.put("PriceStr", jsonArray);
			/*
			}
			json.put("code", 201);//返回201表示成功
*/		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
			json.put("message", "API套餐价格维护失败");
		}
    	    	
        return json.toString();
    }
	
	public static void main(String[] args) {
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
        WebResource service = client.resource("http://localhost:18080/cspm/rest/servermanager/price/vindicatePrice/"+2);
//        WebResource service = client.resource("http://localhost:8080/dss/rest/internalapi/vulnscan/orderTask/orderTaskid/6/9");
        
//        WebResource service = client.resource("http://localhost:8080/cspi/rest/openapi/createOrder");
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,json.toString());        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses); 

	}
}
