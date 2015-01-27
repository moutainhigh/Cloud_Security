package com.cn.ctbri.entity;

import java.io.Serializable;
import java.sql.Date;

public class Task implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 任务id
	 */
	private Long teskId;
	
	/**
	 * 订单资产详情Id
	 */
	private Long order_asset_Id;
	
	/**
	 * 任务执行时间
	 */
	private Date execute_time;
	
	/**
	 * 任务状态
	 */
	private int status;
	
	/**
	 * 备注
	 */
	private String remarks;

	public Long getTeskId() {
		return teskId;
	}

	public void setTeskId(Long teskId) {
		this.teskId = teskId;
	}

	public Long getOrder_asset_Id() {
		return order_asset_Id;
	}

	public void setOrder_asset_Id(Long order_asset_Id) {
		this.order_asset_Id = order_asset_Id;
	}

	public Date getExecute_time() {
		return execute_time;
	}

	public void setExecute_time(Date execute_time) {
		this.execute_time = execute_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
