package com.cn.ctbri.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 创 建 人  ：  tang
 * 创建日期：  2016-8-25
 * 描        述：  logs实体
 * 版        本：  1.0
 */
public class LogsInfo implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	//主键Id
	private int log_id;
	//ip地址
	private String ip_address;
	//日志内容
	private String log_content;
	//类型
    private String log_type;
	//日志日期
	private Date log_date;
	
	public int getLog_id() {
		return log_id;
	}
	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public String getLog_content() {
		return log_content;
	}
	public void setLog_content(String log_content) {
		this.log_content = log_content;
	}
	public String getLog_type() {
		return log_type;
	}
	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}
	public Date getLog_date() {
		return log_date;
	}
	public void setLog_date(Date log_date) {
		this.log_date = log_date;
	}
	
}
