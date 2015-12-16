package com.ctbri.service;

import java.util.Date;

import javax.ws.rs.FormParam;

public interface AlarmService {
	/**
	 * 
	 * @Title: findAlarmByUserId
	 * @Description: 通过用户ID查询
	 * @param userId
	 * @return String 返回类型
	 * @throws
	 */
	String findAlarmByUserId(@FormParam("userId")
	Integer userId);

	/**
	 * 
	 * @Title: countAlarm
	 * @Description: 统计告警订单总数
	 * @param
	 * @param userId
	 * @return JSONObject 返回类型
	 * @throws
	 */
	String countAlarm(Integer userId);

	/**
	 * 
	 * @Title: findAlarmByOrderId
	 * @Description: 根据orderid查询告警数,最近告警信息
	 * @param websoc
	 * @param orderId
	 * @param group_flag
	 * @param type
	 * @param count
	 * @param level
	 * @param name
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	String findAlarmByOrderId(@FormParam("orderId")
			String orderId, @FormParam("group_flag")
			Date group_flag, @FormParam("type")
			Integer type, @FormParam("count")
			Integer count, @FormParam("level")
			Integer level, @FormParam("name")
			String name, @FormParam("serviceId")
			Integer serviceId,@FormParam("pageNow")Integer pageNow,
			@FormParam("pageSize") Integer pageSize);
	
	/**
	 * 
	 * @Title: findAlarmByOrderId
	 * @Description: 根据orderid查询告警数,最近告警信息
	 * @param websoc
	 * @param orderId
	 * @param group_flag
	 * @param type
	 * @param count
	 * @param level
	 * @param name
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	String findAlarm(@FormParam("websoc")
			Integer websoc, @FormParam("alarmId")
			String alarmId, @FormParam("group_flag")
			Date group_flag, @FormParam("type")
			Integer type, @FormParam("count")
			Integer count, @FormParam("level")
			Integer level, @FormParam("name")
			String name);
	/**
	 * 
	* @Title: findAlarmByAlarmId 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param alarmId
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	String findAlarmByAlarmId(@FormParam("alarmId")Integer alarmId);
}
