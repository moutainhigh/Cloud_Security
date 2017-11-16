package com.cn.ctbri.entity;

import java.util.Date;

public class MonitorTask {

	private int id;//主键Id
	private int userId;//用户ID
	private String taskName;//任务名称
	private String targetUrl;//任务目标的URL
	private int frequency;// 监测频率 1:15分钟  2:20分钟 3：30分钟 4:60分钟
	private int  monitor_Type;//监控类型 1:主机监控  2:服务监控
	private String port_lists; //端口列表 对应服务
	private String alarmemail; //告警email
	private String alarmphone; //告警手机
	private String lastStatus; //最后任务的返回状态 
	private String lastdetectTime;//最后一次检测时间
	private String createTime;//任务创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public int getMonitor_Type() {
		return monitor_Type;
	}
	public void setMonitor_Type(int monitor_Type) {
		this.monitor_Type = monitor_Type;
	}
	public String getPort_lists() {
		return port_lists;
	}
	public void setPort_lists(String port_lists) {
		this.port_lists = port_lists;
	}
	public String getAlarmemail() {
		return alarmemail;
	}
	public void setAlarmemail(String alarmemail) {
		this.alarmemail = alarmemail;
	}
	public String getAlarmphone() {
		return alarmphone;
	}
	public void setAlarmphone(String alarmphone) {
		this.alarmphone = alarmphone;
	}
	public String getLastStatus() {
		return lastStatus;
	}
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	public String getLastdetectTime() {
		return lastdetectTime;
	}
	public void setLastdetectTime(String lastdetectTime) {
		this.lastdetectTime = lastdetectTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
