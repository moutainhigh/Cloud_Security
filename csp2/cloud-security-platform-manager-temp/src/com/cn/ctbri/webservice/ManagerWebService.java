package com.cn.ctbri.webservice;

import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.APINum;
import com.cn.ctbri.service.IUserService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-7-12
 * 描        述：  接收北向WebService请求接口
 * 版        本：  1.0
 */
@Component
@Path("manager")
public class ManagerWebService {
	@Autowired
    private IUserService userService;
	
	//创建api统计
	@POST
    @Path("/createAPINum")
	@Produces(MediaType.APPLICATION_JSON) 
    public String vulnScanCreate(String dataJson) {
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			String apiKey = jsonObj.getString("apiKey");
			int service_type = jsonObj.getInt("service_type");
			int api_type = jsonObj.getInt("api_type");
			int status = jsonObj.getInt("status");
		    
			//insert到统计表
			APINum num = new APINum();
			num.setApikey(apiKey);
			num.setService_type(service_type);
			num.setApi_type(api_type);
			num.setStatus(status);
			num.setCreate_time(new Date());
			userService.insertAPINum(num);
			
	        json.put("code", 201);//返回201表示成功
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//返回404表示失败
			json.put("message", "统计失败");
		} 
		return json.toString();
	}
	
	
	public static void main(String[] args) {
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		
		json.put("apiKey", "31f790d52aab4a4ca7b033e1f267eeaf");
		json.put("service_type", 100);
		json.put("api_type", 1);
		json.put("status", 1);

	    ClientConfig config = new DefaultClientConfig();
	    //检查安全传输协议设置
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource("http://localhost:8080/cloud-security-platform-manager/rest/manager/createAPINum");
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,json.toString());        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses); 

	}
		
	
}
