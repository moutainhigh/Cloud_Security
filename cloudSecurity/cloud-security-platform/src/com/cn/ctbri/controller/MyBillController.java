package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
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
	@Autowired
	IOrderAssetService orderAssetService;
	@Autowired
	ITaskService taskService;
	/**
	 * 功能描述： 用户中心——我的账单页面
	 * 参数描述： Model model
	 *		 @time 2015-1-15
	 */
	@SuppressWarnings("rawtypes")
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
		//组织条件查询
		String name=null;
		try {
			name=new String(servName.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userId", globle_user.getId());
		paramMap.put("type", type);
		paramMap.put("servName", name);
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
		model.addAttribute("servName",name);//回显服务名称
		model.addAttribute("begin_date",begin_datevo);//回显服务开始时间	
		model.addAttribute("end_date",end_datevo);	//回显结束时间
		return "/source/page/userCenter/userBill";
	}
	
	/**
	 * 功能描述： 查看详情——资产个数
	 * 参数描述： Model model
	 *		 @time 2015-1-15
	 */
	@RequestMapping("/orderDetail.html")
	public void orderDetail(String orderId,HttpServletResponse response){
		List<OrderAsset> orderAsset = orderAssetService.findOrderAssetByOrderId(orderId);
		int count = 0;//资产个数
		if(orderAsset!=null&&orderAsset.size()>0){
			count=orderAsset.size();
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", count);
		int num=0;//扫描次数
		for(OrderAsset o : orderAsset){
			int orderAssetId = o.getId();
			List<Task> taskList= taskService.findTaskByOrderAssetId(orderAssetId);
			if(taskList!=null&&taskList.size()>0){
				num += taskList.size();
			}
		}
		m.put("num", num);
		//object转化为Json格式
		JSONObject JSON = objectToJson(response, m);
		try {
			// 把数据返回到页面
			writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 功能描述：  object转化为Json格式
	 * 参数描述： HttpServletResponse response,Map<String, Object> m
	 * @throws Exception 
	 *		 @time 2014-12-31
	 */
	private JSONObject objectToJson(HttpServletResponse response,
			Map<String, Object> m) {
		JSONObject JSON = JSONObject.fromObject(m);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		return JSON;
	}
	/**
	 * 功能描述： 把数据返回到页面
	 * 参数描述： HttpServletResponse response, JSONObject JSON
	 * @throws Exception 
	 *		 @time 2014-12-31
	 */
	private void writeToJsp(HttpServletResponse response, JSONObject JSON)
			throws IOException {
		response.getWriter().write(JSON.toString());
		response.getWriter().flush();
	}
}
