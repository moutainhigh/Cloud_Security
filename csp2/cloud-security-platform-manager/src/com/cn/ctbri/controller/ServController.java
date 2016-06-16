package com.cn.ctbri.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.util.CommonUtil;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-3
 * 描        述：  后台服务管理
 * 版        本：  1.0
 */
@Controller
public class ServController {
	
	@Autowired
	IServService servService;
	@Autowired
    ISelfHelpOrderService selfHelpOrderService;
	/**
	 * 功能描述：服务管理页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/adminServUI.html")
	public String adminDeleteUser(User user,HttpServletRequest request){
		//获取服务类型
        List<Serv> servList = selfHelpOrderService.findService();
        request.setAttribute("servList", servList);//订单总数
		return "/source/adminPage/userManage/serv";
	}
	
	/**
	 * 功能描述：查询服务列表
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/getServiceList.html")
	public String getServiceList(HttpServletRequest request){
		//获取服务类型
        List servList = selfHelpOrderService.findSerList(null);
        request.setAttribute("servList", servList);//订单总数
		return "/source/serviceManage/serviceList";
	}
	
	/**
	 * 功能描述：添加服务页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/addServUI.html")
	public String addServUI(User user,HttpServletRequest request){
		return "/source/serviceManage/addSer";
	}
	
	/**
	 * 功能描述：修改服务页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/updateServUI.html")
	public String updateServUI(HttpServletRequest request){
		String servId = request.getParameter("servId");
		String remarks = request.getParameter("remarks");
		String parent = request.getParameter("parent");
		String icon = request.getParameter("icon");
		String servName = request.getParameter("servName");
		String type = request.getParameter("type");
		
		request.setAttribute("servId", servId);
		request.setAttribute("type", type);
		request.setAttribute("remarks", remarks);
		request.setAttribute("icon", icon);
		request.setAttribute("servName", servName);
		request.setAttribute("parent", parent);
		return "/source/serviceManage/updateSer";
	}

	
	/**
	 * 功能描述：上传服务图标
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/uploadIcon.html")
	public void uploadIcon(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			MultipartHttpServletRequest multipartHttpservletRequest=(MultipartHttpServletRequest) request;
	        MultipartFile multipartFile = multipartHttpservletRequest.getFile("file");
	        if(multipartFile==null){
	        	m.put("success", false);
	        	m.put("errorMsg", "请上传文件!");
	        	
				//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
			
	        String originalFileName=multipartFile.getOriginalFilename();
	        String fileType = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
	        //判断文件格式
	        if(!"png".equals(fileType.toLowerCase())){
	        	m.put("successFlag", false);
	        	m.put("errorMsg", "请导入png格式的文件!");
	        	
	        	//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		
	        //上传文件路径
	        String savePath =request.getSession().getServletContext().getRealPath("/uploadFile");
	        //上传文件名称；
	        String uploadFilePathName = String.valueOf(System.currentTimeMillis())+originalFileName.substring(originalFileName.lastIndexOf("."));
	        File file=new File(savePath);
	        if(!file.exists()){
	            file.mkdir();
	        }
	        
	        File fileToCreate = new File(savePath, uploadFilePathName);
	        FileOutputStream os = new FileOutputStream(fileToCreate);
   			os.write(multipartFile.getBytes());
   			os.flush();
   			os.close();

   			String filePath = savePath+"\\"+uploadFilePathName;
			m.put("success", true);
			m.put("filePath", filePath);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 功能描述：添加服务
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/addServ.html",method = RequestMethod.POST)
	@ResponseBody
	public void addServ(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			String name = request.getParameter("name");
			String parent = request.getParameter("parent");
			int type = Integer.parseInt(request.getParameter("type"));
			String remarks = request.getParameter("remarks");
			String icon = request.getParameter("icon");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("parent", parent);
			map.put("type", type);
			map.put("remarks", remarks);
			map.put("icon", icon);
			if(!parent.equals("API")){//添加到普通服务列表
				selfHelpOrderService.insertServ(map);
			}else{//添加到API服务列表
				selfHelpOrderService.insertAPI(map);
			}
			
		
			

			m.put("success", true);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能描述：添加服务
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/updateServ.html",method = RequestMethod.POST)
	@ResponseBody
	public void updateServ(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			String id = request.getParameter("servId");
			String name = request.getParameter("name");
			String parent = request.getParameter("parent");
			int type = Integer.parseInt(request.getParameter("type"));
			String remarks = request.getParameter("remarks");
			String icon = request.getParameter("icon");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("name", name);
			map.put("parent", parent);
			map.put("type", type);
			map.put("remarks", remarks);
			map.put("icon", icon);
			if(!parent.equals("API")){//添加到普通服务列表
				selfHelpOrderService.updateServ(map);
			}else{//添加到API服务列表
				selfHelpOrderService.updateAPI(map);
			}
			
		
			

			m.put("success", true);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能描述：按条件查询服务
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/searchServ.html")
	public void searchServ(HttpServletRequest request,HttpServletResponse response){	
		Map<String, Object> m = new HashMap<String, Object>();
		String name = "";
		String parent = "";
		try {
			name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
			parent = new String(request.getParameter("parent").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int type = 0;
		if(!request.getParameter("type").equals("null")){
			type = Integer.parseInt(request.getParameter("type"));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("parent", parent);
		map.put("type", type);
		
		List servList = selfHelpOrderService.findSerList(map);
		
		m.put("servList", servList);
		//object转化为Json格式
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	/**
	 * 功能描述：删除服务
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/delServ.html")
	public void delServ(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			String servId = request.getParameter("servId");
			String parent = new String(request.getParameter("parent").getBytes("ISO-8859-1"),"UTF-8");
					
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("servId", servId);
			map.put("parent", parent);
			
			selfHelpOrderService.delServ(map);
			
			m.put("success", true);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		

	}
	
	/**
	 * 功能描述：添加服务价格页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/addServicePriceUI.html")
	public String addServicePriceUI(HttpServletRequest request){
		//获取服务类型
		int serviceId = Integer.parseInt(request.getParameter("servId"));
		String parent = request.getParameter("parent");
		Map<String,Object> newMap = new HashMap<String,Object>();
		newMap.put("serviceId", serviceId);
		newMap.put("parent", parent);
		List<Price> priceList = servService.findPriceByParam(newMap);
		if(priceList!=null && priceList.size()>0){
			for (int i = 0; i < priceList.size(); i++) {
				if(priceList.get(i).getType()==0){//单次
					request.setAttribute("priceSingle", priceList.get(i).getPrice());
				}else if(priceList.get(i).getType()==2){//大于
					request.setAttribute("priceMaxTimesG", priceList.get(i).getTimesG());
					request.setAttribute("priceMax", priceList.get(i).getPrice());
				}
			}
		}
		request.setAttribute("serviceId", serviceId);
		request.setAttribute("priceList", priceList);//订单总数
		//查询区间list
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("serviceId", serviceId);
		map.put("parent", parent);
		map.put("type", 1);
		List<Price> priceLongList = servService.findPriceByParam(map);
        request.setAttribute("priceLongList", priceLongList);//订单总数
		return "/source/serviceManage/priceManage";
	}
	
	/**
	 * 功能描述：为服务添加价格
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/addServicePrice.html",method = RequestMethod.POST)
	@ResponseBody
	public void addServicePrice(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			int serviceId = Integer.parseInt(request.getParameter("serviceId"));
			int timesG = Integer.parseInt(request.getParameter("timesG"));
			int timesLE = Integer.parseInt(request.getParameter("timesLE"));
			int type = Integer.parseInt(request.getParameter("type"));
			double price = Double.parseDouble(request.getParameter("price")); 
			
			Price newprice = new Price();
			newprice.setServiceId(serviceId);
			newprice.setTimesG(timesG);
			newprice.setTimesLE(timesLE);
			newprice.setPrice(price);
			newprice.setType(type);
			
			servService.insertPrice(newprice);
			

			m.put("success", true);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能描述：删除服务价格
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/delServicePrice.html",method = RequestMethod.POST)
	@ResponseBody
	public void delServicePrice(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			int serviceId = Integer.parseInt(request.getParameter("serviceId"));		
			servService.delPrice(serviceId);
			
			m.put("success", true);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能描述：服务详情维护
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/serviceDetailsUI.html")
	public String serviceDetailsUI(HttpServletRequest request){
		//获取服务类型
		int serviceId = Integer.parseInt(request.getParameter("servId"));
		String parent = request.getParameter("parent");
		request.setAttribute("serviceId", serviceId);
		request.setAttribute("parent", parent);
		return "/source/serviceManage/servDetails";
	}
	
	/**
	 * 功能描述：保存服务详情
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/saveServDetails.html",method = RequestMethod.POST)
	@ResponseBody
	public void saveServDetails(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			int serviceId = Integer.parseInt(request.getParameter("serviceId"));
			String scanTypes = request.getParameter("scanTypes");
			String servRates = new String(request.getParameter("servRates").getBytes("ISO-8859-1"),"UTF-8");
			String parent = new String(request.getParameter("parent").getBytes("ISO-8859-1"),"UTF-8");
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("scanTypes", scanTypes);
			map.put("servRates", servRates);
			map.put("id", serviceId);
			
			if(!parent.equals("API")){//添加到普通服务列表
				selfHelpOrderService.updateServ(map);
			}else{//添加到API服务列表
				selfHelpOrderService.updateAPI(map);
			}
			

			m.put("success", true);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
