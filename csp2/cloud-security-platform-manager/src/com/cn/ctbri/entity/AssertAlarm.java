package com.cn.ctbri.entity;

import java.io.Serializable;

/**
 * 创 建 人  ：  zx
 * 创建日期：  2015-01-07
 * 描        述：  告警实体
 * 版        本：  1.0
 */
public class AssertAlarm implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	//资产名称
	private String name;
	//服务类型名称
	private String serName;
	//统计开始时间VO
	private String begin_date;
	//统计结束时间VO
	private String end_date;
	//告警时间VO
	private String alarmTime;
	//告警数量
	private int num;
	
	private String orderId;
	//服务ID
	private String serviceId;
	//告警等级
	private String level;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getSerName() {
		return serName;
	}
	public void setSerName(String serName) {
		this.serName = serName;
	}
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	
}
