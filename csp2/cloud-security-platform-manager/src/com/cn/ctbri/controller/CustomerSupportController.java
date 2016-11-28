package com.cn.ctbri.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.cfg.dssWorker;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.ICustomerSupportSevice;
import com.cn.ctbri.util.CommonUtil;

@Controller
public class CustomerSupportController {

	@Autowired
	ICustomerSupportSevice customerSupportService;
	@Autowired
	IAssetService assetService;

	/**
	 * 功能描述：客服支持系统页面
	 * 参数描述：无
	 */
	@RequestMapping(value="/customerSupportUI.html")
	public String showHtml(){
		return "/source/adminPage/userManage/customerSupport";
	}
	
	/**
	 * 
	 * 用户信息查询Tab展示
	 * @param request
	 */
	@RequestMapping(value="/customerSupport.html")
	public void getUserInfoTab(HttpServletResponse response,HttpServletRequest request){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String orderno = request.getParameter("orderno");
		String assetname = request.getParameter("assetname");
		String assetaddr = request.getParameter("assetaddr");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("email", email);
		paramMap.put("mobile", mobile);
		paramMap.put("orderno", orderno);
		paramMap.put("assetname", assetname);
		paramMap.put("assetaddr", assetaddr);
		//查询用户信息
		List<Map<String, Object>> userInfoList = customerSupportService.queryUserInfo(paramMap);
		Map<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("userInfoList", userInfoList);
		
		//object转化为Json格式
		JSONObject JSON = CommonUtil.objectToJson(response, outputMap);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		ModelAndView mv = new ModelAndView("/source/adminPage/userManage/customerSupport");
//		mv.addObject("userInfoList", userInfoList);
//		mv.addObject("test", "11111");
//		return mv;
		
	}
	/**
	 * 订单查询tab显示
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="customerOrder.html")
	public void getOrderInfoTab(HttpServletResponse response,HttpServletRequest request){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String orderno = request.getParameter("orderno");
		String createDateS = request.getParameter("createDateS");
		String createDateE = request.getParameter("createDateE");
		String servDateS = request.getParameter("servDateS");
		String servDateE = request.getParameter("servDateE");
		String orderusername = request.getParameter("orderusername");
		int isapi = Integer.parseInt(request.getParameter("isapi"));
		String assetname = request.getParameter("assetname");
		String assetaddr = request.getParameter("assetaddr");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderno", orderno);
		paramMap.put("createDateS", createDateS);
		paramMap.put("createDateE", createDateE);
		paramMap.put("servDateS", servDateS);
		paramMap.put("servDateE", servDateE);
		paramMap.put("orderusername", orderusername);
		paramMap.put("isapi", isapi);
		paramMap.put("assetname", assetname);
		paramMap.put("assetaddr", assetaddr);
		
		List<Order> orderList = customerSupportService.queryOrderInfo(paramMap);
		
		Map<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("orderList", orderList);
		
		JSONObject json = CommonUtil.objectToJson(response, outputMap);
		
		try {
			CommonUtil.writeToJsp(response, json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 资源信息查询tab显示
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="customerResource.html")
	public void getResourceInfoTab(HttpServletResponse response,HttpServletRequest request){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");

		String resourcename = request.getParameter("resourcename");
		String resourceaddr = request.getParameter("resourceaddr");
		int isapi = Integer.parseInt(request.getParameter("isapi"));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("resourcename", resourcename);
		paramMap.put("resourceaddr", resourceaddr);
		paramMap.put("isapi", isapi);
		
		//存放解析数据，返回页面
		List resourceList = new ArrayList();
		
		String resultStr = dssWorker.getResource(resourcename, resourceaddr, isapi);
		JSONObject obj = JSONObject.fromObject(resultStr);
        String stateCode = obj.getString("code");
        if(stateCode.equals("201")){
        	String engineStr = obj.getString("engineObj");
        	if(engineStr!=null && !engineStr.equals("")){
				JSONArray engineArray = obj.getJSONArray("engineObj");
				for (Object enObj : engineArray) {
					JSONObject engineObj = (JSONObject) enObj;
					String engine_number = engineObj.getString("engine_number");
					String engine_addr = engineObj.getString("engine_addr");
		            String status = engineObj.getString("status");
		            String memoryUsage = engineObj.getString("memoryUsage");
		            String cpuUsage = engineObj.getString("cpuUsage");
		            String diskUsage = engineObj.getString("diskUsage");
		            
		            Map<String,Object> newMap = new HashMap<String,Object>();
			        newMap.put("engine_number", engine_number);
			        newMap.put("engine_addr", engine_addr);
			        newMap.put("status", status);
			        newMap.put("memoryUsage", memoryUsage);
			        newMap.put("cpuUsage", cpuUsage);
			        newMap.put("diskUsage", diskUsage);
			        
			        resourceList.add(newMap);
				}
        	}
				
        }
		
		Map<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("resourceList", resourceList);
		
		JSONObject json = CommonUtil.objectToJson(response, outputMap);
		
		try {
			CommonUtil.writeToJsp(response, json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="assetDetail.html")
    public void assetDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
        int assetId = Integer.parseInt(request.getParameter("assetId"));
        Asset asset = assetService.findById(assetId);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("assetName", asset.getName());
        map.put("assetAddr", asset.getAddr());
        map.put("create_date", asset.getCreate_date());
        map.put("purpose", asset.getPurpose());
    	
    	JSONObject JSON = CommonUtil.objectToJson(response, map);
	    // 把数据返回到页面
        CommonUtil.writeToJsp(response, JSON);
    }
}
