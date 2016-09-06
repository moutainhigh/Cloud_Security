package com.cn.ctbri.webservice;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Advertisement;
import com.cn.ctbri.service.IAdvertisementService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 创 建 人  ：  zhang_shaohua
 * 创建日期：  2016-6-20
 * 描        述：  接收运营管理系统请求接口
 * 版        本：  1.0
 */
@Component
@Path("servermanager")
public class ServerManagerService {
	@Autowired
	IAdvertisementService adService;
	
	/**
	 * 功能描述：广告添加
	 * */
	@POST
	@Path("/advertisement/vindicateAD")
	@Produces(MediaType.APPLICATION_JSON)
	public String addAdvertisement(String dataJson){
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			
//			String adId = jsonObj.getString("AdId");
			String adName = jsonObj.getString("AdName");
			String adImage = jsonObj.getString("AdImage");
			int type = jsonObj.getInt("AdType");
			int order = jsonObj.getInt("AdOrder");
			String adStartDate = jsonObj.getString("AdStartDate");
			String adEndDate = jsonObj.getString("AdEndDate");
			String adCreateDate = jsonObj.getString("AdCreateDate");
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
			Date startDate = sdf.parse(adStartDate);
			Date endDate = sdf.parse(adEndDate);
			Date createDate = sdf.parse(adCreateDate);
			
			Advertisement ad = new Advertisement();
//			ad.setId(Integer.valueOf(adId));
			ad.setName(adName);
			ad.setImage(adImage);
			ad.setType(type);
			ad.setOrderIndex(order);
			ad.setStartDate(startDate);
			ad.setEndDate(endDate);
			ad.setCreateDate(createDate);
			int adId = adService.insert(ad);
			
			json.put("code", 200);//返回200表示成功
			json.put("messaage", "广告图片上传成功");
			json.put("adId", adId);
		} catch (Exception e) {
			e.printStackTrace();
			
			json.put("code", 400);//返回400表示失败
			json.put("messaage", "广告图片上传失败");
		}
        
		return json.toString();
	}
	
	/**
	 * 功能描述：广告删除
	 * */
	@POST
	@Path("/advertisement/delAD/{ADId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteAdvertisement(@PathParam("ADId") int adId){
		JSONObject json = new JSONObject();
		
		try {
			Advertisement ad = adService.findById(adId);
			//图片删除
			String imageName = ad.getImage();
			if (imageName != null && !imageName.equals("")) {
				//获取根目录  E:/apache-tomcat-X.X.XX/webapps/csp/WEB-INF/classes/  
				String path = this.getClass().getClassLoader().getResource("/").getPath();
				path = URLDecoder.decode(path, "UTF-8");
				
				path = path.substring(0, path.lastIndexOf("/"));  //E:\apache-tomcat-X.X.XX\webapps\csp\WEB-INF/classes
				path = path.substring(0, path.lastIndexOf("/"));  //E:\apache-tomcat-X.X.XX\webapps\csp\WEB-INF
				path = path.substring(0, path.lastIndexOf("/"));  //E:\apache-tomcat-X.X.XX\webapps\csp\
				path = path +"/source/images/ad/" + imageName;
				File imageFile = new File(path);
				if (imageFile.exists()) {
					imageFile.delete();
				}
			}
			
			adService.delete(adId);
			
			
			json.put("code", 200);//返回200表示成功
			json.put("message", "删除广告成功");
		} catch(Exception e){
			e.printStackTrace();
			json.put("code", 400);//返回400表示失败
			json.put("message", "删除广告失败");
		}
		
		return json.toString();
	}
	
	/**
	 * 功能描述：广告排序
	 * */
	@POST
	@Path("/advertisement/sortAD")
	@Produces(MediaType.APPLICATION_JSON)
	public String sortAdvertisement(String dataJson){
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObj = new JSONObject().fromObject(dataJson);
			
			String adId1 = jsonObj.getString("AdId1");
			int adOrder1 = jsonObj.getInt("AdOrder1");
			
			String adId2 = jsonObj.getString("AdId2");
			int adOrder2 = jsonObj.getInt("AdOrder2");
			
			
			
			Advertisement ad = new Advertisement();
			ad.setId(Integer.valueOf(adId1));
			ad.setOrderIndex(adOrder1);
			adService.update(ad);
			
			ad.setId(Integer.valueOf(adId2));
			ad.setOrderIndex(adOrder2);
			adService.update(ad);
			
			json.put("code", 200);//返回200表示成功
			json.put("messaage", "广告排序成功");
		} catch (Exception e) {
			e.printStackTrace();
			
			json.put("code", 400);//返回400表示失败
			json.put("messaage", "广告排序失败");
		}
        
		return json.toString();
	}
	
	public static void main(String[] args){
		//组织发送内容JSON
		JSONObject json = new JSONObject();
		
		ClientConfig config = new DefaultClientConfig();
		//检查安全传输协议设置
        Client client = Client.create(config);
      //连接服务器
        WebResource service = client.resource("http://localhost:8080/csp/rest/servermanager/advertisement/delAD/"+8);
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,json.toString());        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses);
	}

}
