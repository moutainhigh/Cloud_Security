package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  txr
 * 创建日期：   2015-4-14
 * 描        述：  华为任务实体类
 * 版        本：  1.0
 */
public class TaskHW{
	private int taskId;//任务Id(主键)
	private int order_ip_Id;//ip
	private Date execute_time;//执行时间
	private int status;//任务状态（1：未执行 ，2：进行中，3：已完成）
	private String remarks;//描述
	private Date end_time;//结束时间
	private int drainage;//是否创建引流
	private String zone_id;
    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public int getOrder_ip_Id() {
        return order_ip_Id;
    }
    public void setOrder_ip_Id(int order_ip_Id) {
        this.order_ip_Id = order_ip_Id;
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
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
    public int getDrainage() {
        return drainage;
    }
    public void setDrainage(int drainage) {
        this.drainage = drainage;
    }
    public String getZone_id() {
        return zone_id;
    }
    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }
	
	
}
