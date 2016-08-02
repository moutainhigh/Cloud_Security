package com.cn.ctbri.entity;

import java.util.Date;

public class API {
	private int id;//id
	private String apikey;//apikey
	private int service_type;//服务类型
	private int api_type;//操作类型
	private int status;//状态
	private Date create_time;//开始时间
	private String apiId;//订单编号
	private String orderId;//调用API生成ID
	private int taskId;//任务id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public int getService_type() {
		return service_type;
	}
	public void setService_type(int serviceType) {
		service_type = serviceType;
	}
	public int getApi_type() {
		return api_type;
	}
	public void setApi_type(int apiType) {
		api_type = apiType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
}
