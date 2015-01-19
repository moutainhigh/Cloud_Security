package com.cn.ctbri.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.util.DateUtils;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-14
 * 描        述：  我的账单
 * 版        本：  1.0
 */
@Controller
public class MyBillController {
	
	@Autowired
	IOrderService orderService;
	@Autowired
	IServService servService;
	/**
	 * 功能描述： 显示登录用户的订单信息
	 * 参数描述： Model model
	 *		 @time 2015-1-15
	 */
	@RequestMapping("/userBillUI.html")
	public String userBillUI(Model model,HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		List list = orderService.findByUserId(globle_user.getId());
		model.addAttribute("list",list);		//传对象到页面
		return "/source/page/userCenter/userBill";
	}
	
	/**
	 * 功能描述： 按条件查询订单
	 * 参数描述： Model model
	 *		 @time 2015-1-15
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/searchCombine.html")
	public String searchCombine(Model model,Integer type,String servName,String begin_datevo,String end_datevo,HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
//		Integer type = order.getType();//订单类型(1：长期，2：单次)
//		String servName = order.getServName();//服务名称
//		String begin_datevo = order.getBegin_datevo();//服务开始时间
//		String end_datevo = order.getEnd_datevo();//服务结束时间
		
		//组织条件查询
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userId", globle_user.getId());
		paramMap.put("type", type);
		paramMap.put("servName", servName);
		if(StringUtils.isNotEmpty(begin_datevo)){
			paramMap.put("begin_date", DateUtils.stringToDate(begin_datevo));
		}else{
			paramMap.put("begin_date", null);
		}
		if(StringUtils.isNotEmpty(end_datevo)){
			paramMap.put("end_date", DateUtils.stringToDate(end_datevo));
		}else{
			paramMap.put("end_date", null);
		}
		List result = orderService.findByCombine(paramMap);
		model.addAttribute("list",result);		//传对象到页面
		
		model.addAttribute("type",type);//回显类型	
		model.addAttribute("servName",servName);//回显服务名称
		model.addAttribute("begin_date",begin_datevo);//回显服务开始时间	
		model.addAttribute("end_date",end_datevo);	//回显结束时间
		return "/source/page/userCenter/userBill";
	}
	
	
	
	
	
	
	
	
}
