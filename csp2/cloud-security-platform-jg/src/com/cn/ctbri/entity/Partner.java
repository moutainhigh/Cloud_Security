package com.cn.ctbri.entity;

import java.util.Date;


/**
 * 创 建 人  ：  txr
 * 创建日期：   2016-11-30
 * 描        述：  合作方实体类
 * 版        本：  1.0
 */
public class Partner {
	private int id;
	//合作方名称
	private String partnerName;
	//创建时间
	private Date createDate;
	//begin_ip
	private String begin_ip;
	//end_ip
	private String end_ip;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getBegin_ip() {
		return begin_ip;
	}
	public void setBegin_ip(String begin_ip) {
		this.begin_ip = begin_ip;
	}
	public String getEnd_ip() {
		return end_ip;
	}
	public void setEnd_ip(String end_ip) {
		this.end_ip = end_ip;
	}
}
