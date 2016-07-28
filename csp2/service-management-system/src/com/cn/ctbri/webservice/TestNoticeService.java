package com.cn.ctbri.webservice;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-4-9
 * 描        述：  testNotice
 * 版        本：  1.0
 */
@Component
@Path("open")
public class TestNoticeService {	
	
	//设置回调地址
	@POST
    @Path("/taskNotice")
	@Produces(MediaType.APPLICATION_JSON)
	public String getNotice(String dataJson) {
		JSONObject json = new JSONObject();
		try {
			if(dataJson!=null && dataJson!=""){
				JSONObject jsonObj = new JSONObject().fromObject(dataJson);
				String orderId = jsonObj.getString("orderId");
				System.out.println(orderId);
				//返回json
				json.put("code", 201);
				json.put("message", "成功");
			}else{
				json.put("code",422);
				json.put("message", "无效");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);
			json.put("message", "失败");
		}
        return json.toString();
    }

	 
	public static void main(String[] args) {

	}
}
