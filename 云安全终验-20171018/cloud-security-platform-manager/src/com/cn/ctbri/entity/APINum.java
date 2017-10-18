package com.cn.ctbri.entity;

import java.util.Date;


/**
 * 创 建 人  ：  tangxr
 * 创建日期：   2016-7-12
 * 描        述：  API统计实体类
 * 版        本：  1.0
 */
public class APINum {
	
	//主键id
	private int id;
	//用户apikey
	private String apikey;
	//服务类型1-5
	private int service_type;
	//api接口类型1-5
	private int api_type;
	//状态
	private int status;
	//访问时间
	private Date create_time;
	
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
	public void setService_type(int service_type) {
		this.service_type = service_type;
	}
	public int getApi_type() {
		return api_type;
	}
	public void setApi_type(int api_type) {
		this.api_type = api_type;
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
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
}
