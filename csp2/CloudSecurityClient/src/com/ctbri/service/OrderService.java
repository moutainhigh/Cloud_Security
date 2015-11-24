package com.ctbri.service;

import java.util.Date;
import java.util.Map;

import javax.ws.rs.FormParam;

import net.sf.json.JSONObject;

public interface OrderService {
	String findOrderByOrderId(String id);
	/**
	 * 
	* @Title: findOrderByUserIdAndState 
	* @Description: 根据用户id和状态查询订单
	* @param @param userId
	* @param @param state
	* @param @param currentDate
	* @param @param pageNow
	* @param @param pageSize
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	String findOrderByUserIdAndState(@FormParam("userId")Integer userId,
			@FormParam("state")String state,@FormParam("currentDate")Date currentDate,
			@FormParam("pageNow")Integer pageNow,@FormParam("pageSize")Integer pageSize);
	/**
	 * 
	* @Title: findOrderMessage 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	String findOrderMessage(Integer userId,String id);
	/**
	 * 
	* @Title: findOrderByUserId 
	* @Description: 根据用户名查询订单
	* @param userId
	* @return String    返回类型 
	* @throws
	 */
	String findOrderByUserId(Integer userId);
	/**
	 * 
	* @Title: findByCombineOrderTrack 
	* @Description: 组合查询订单追踪
	* @param userId
	* @param type
	* @param begin_date
	* @param end_date
	* @param currentDate
	* @param state
	* @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	String findByCombineOrderTrack(@FormParam("userId")Integer userId,
	@FormParam("state")String state,@FormParam("currentDate")Date currentDate,
	@FormParam("type")Integer type,@FormParam("sId")Integer sId,
	@FormParam("begin_date")Date begin_date,@FormParam("end_date")Date end_date);
	/**
	 * 
	* @Title: countOrder 
	* @Description: 统计全部订单
	* @param userId
	* @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	String countOrder(Integer userId);
	/**
	 * 
	* @Title: countServerOrder 
	* @Description: 统计服务中订单
	* @param userId
	* @param type
	* @param begin_date
	* @param end_date
	* @param currentDate
	* @param state
	* @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	String countServerOrder(@FormParam("userId")Integer userId,
			@FormParam("state")String state,@FormParam("currentDate")Date currentDate);
}
