package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：   2015-1-27
 * 描        述：  任务实体类
 * 版        本：  1.0
 */
public class Task{
	private int taskId;//任务Id(主键)
	private int order_asset_Id;//
	private Date execute_time;//执行时间
	private int status;//任务状态（1：已执行，未执行）
	private String remarks;//描述
	public int getTaskId() {
		return taskId;
	}
	public int getOrder_asset_Id() {
		return order_asset_Id;
	}
	public void setOrder_asset_Id(int order_asset_Id) {
		this.order_asset_Id = order_asset_Id;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
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
