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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	 * 功能描述：添加服务价格页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/addServicePriceUI.html")
	public String addServicePriceUI(HttpServletRequest request){
		//获取服务类型
		int serviceId = 1;
		List<Price> priceList = servService.findPriceByServiceId(serviceId);
        request.setAttribute("priceList", priceList);//订单总数
		return "/source/adminPage/userManage/priceManage";
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
			int timesBegin = Integer.parseInt(request.getParameter("timesBegin"));
			int timesEnd = Integer.parseInt(request.getParameter("timesEnd"));
			double price = Double.parseDouble(request.getParameter("price")); 
			
			Price newprice = new Price();
			newprice.setServiceId(serviceId);
			newprice.setTimesBegin(timesBegin);
			newprice.setTimesEnd(timesEnd);
			newprice.setPrice(price);
			
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
}
