package com.cn.ctbri.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IUserService;

/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-1-12
 * 描        述：  用户中心控制层
 * 版        本：  1.0
 */
@Controller
public class UserCenterController {/*
	
	@Autowired
	IUserService userService;
	@Autowired
	IOrderService orderService;
	@Autowired
	IAlarmService alarmService;
	 *//**
	 * 功能描述： 用户中心页面
	 * 参数描述：  无
	 *     @time 2015-1-12
	 *//*
	@RequestMapping(value="userCenterUI.html")
	public String userCenterUI(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		//根据用户id查询订单表
		List<Order> orderList = orderService.findOrderByUserId(globle_user.getId());
		int orderNum = 0;
		//根据用户id查询服务中订单表在开始时间和结束时间中间
		int servNum = 0;
		if(orderList.size()>0&&orderList!=null){
			orderNum = orderList.size();
			for(Order order:orderList){
				Date begin_date = order.getBegin_date();
				Date end_date = order.getEnd_date();
				Long currentDate = new Date().getTime();
				if(begin_date.getTime()<currentDate && end_date.getTime()>currentDate){
					servNum +=1;
				}
			}
		}
		request.setAttribute("orderNum", orderNum);//订单总数
		request.setAttribute("servNum","1");
		//总告警数
		List<Alarm> alarmList = alarmService.findAlarmByUserId(globle_user.getId());
		int alarmSum = 0;
		if(alarmList.size()>0&&alarmList!=null){
			alarmSum = alarmList.size();
		}
		request.setAttribute("alarmSum",alarmSum);
		return "/source/page/userCenter/userCenter";
	}
	*//**
	 * 功能描述： 把数据返回到页面
	 * 参数描述： HttpServletResponse response, JSONObject JSON
	 * @throws Exception 
	 *		 @time 2015-1-12
	 *//*
	private void writeToJsp(HttpServletResponse response, JSONObject JSON)
			throws IOException {
		response.getWriter().write(JSON.toString());
		response.getWriter().flush();
	}
	
	*//**
	 * 功能描述：  object转化为Json格式
	 * 参数描述： HttpServletResponse response,Map<String, Object> m
	 * @throws Exception 
	 *		 @time 2015-1-12
	 *//*
	private JSONObject objectToJson(HttpServletResponse response,
			Map<String, Object> m) {
		JSONObject JSON = JSONObject.fromObject(m);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		return JSON;
	}
	
	
	
	
	
	
*/}
