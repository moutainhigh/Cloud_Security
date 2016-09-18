package com.cn.ctbri.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ctbri.entity.Order;
import com.cn.ctbri.service.ICustomerSupportSevice;
import com.cn.ctbri.util.CommonUtil;

@Controller
public class CustomerSupportController {

	@Autowired
	ICustomerSupportSevice customerSupportService;

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
}
