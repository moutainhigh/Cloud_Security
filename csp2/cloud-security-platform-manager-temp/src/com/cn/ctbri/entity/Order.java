package com.cn.ctbri.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-14
 * 描        述： 订单
 * 版        本：  1.0
 */
public class Order {
	
	private String id;//主键Id
	private int type;//订单类型(1：长期，2：单次)
	private Date begin_date;//开始时间
	private Date end_date;//结束日期
	private int serviceId;//服务ID
	private Date create_date;//下单日期
	private Date task_date;//任务起始日期
	private int scan_type;//扫描类型(1：每天，2：每周，3：每月)
	private int userId;//用户ID
	private int contactId;//联系人Id
	private String remarks;//备注
	private int status;//订单状态  1:服务中  2:已结束 3:已下单 
	private String servName;//服务名称vo
	private String begin_datevo;//begin_date vo
	private String end_datevo;//end_date vo备注
	private String task_datevo;//end_date vo备注
	private int message;//是否发送告警信息
	private int websoc;//创宇标志
	private int tasknum;//订单对应的任务数
	private int delFlag;//是否逻辑删除（1：逻辑删除）
	private BigDecimal price;
	private int count;
	private String time;
	//add by 2016-4-11
	private int payFlag;//是否结算
	private int isAPI;
	private String res1; //备用字段
	public String getRes1() {
		return res1;
	}
	public void setRes1(String res1) {
		this.res1 = res1;
	}
	public String getBegin_datevo() {
		return begin_datevo;
	}
	public void setBegin_datevo(String begin_datevo) {
		this.begin_datevo = begin_datevo;
	}
	public String getEnd_datevo() {
		return end_datevo;
	}
	public void setEnd_datevo(String end_datevo) {
		this.end_datevo = end_datevo;
	}
	
	public String getServName() {
		return servName;
	}
	public void setServName(String servName) {
		this.servName = servName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getTask_date() {
		return task_date;
	}
	public void setTask_date(Date task_date) {
		this.task_date = task_date;
	}
	public int getScan_type() {
		return scan_type;
	}
	public void setScan_type(int scan_type) {
		this.scan_type = scan_type;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
	public int getMessage() {
		return message;
	}
	public void setMessage(int message) {
		this.message = message;
	}
	public int getWebsoc() {
		return websoc;
	}
	public void setWebsoc(int websoc) {
		this.websoc = websoc;
	}
    public String getTask_datevo() {
        return task_datevo;
    }
    public void setTask_datevo(String task_datevo) {
        this.task_datevo = task_datevo;
    }
	public int getTasknum() {
		return tasknum;
	}
	public void setTasknum(int tasknum) {
		this.tasknum = tasknum;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public int getPayFlag() {
		return payFlag;
	}
	public void setPayFlag(int payFlag) {
		this.payFlag = payFlag;
	}
	public int getIsAPI() {
		return isAPI;
	}
	public void setIsAPI(int isAPI) {
		this.isAPI = isAPI;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	
	
}
