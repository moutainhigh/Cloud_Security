package com.cn.ctbri.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 创 建 人  ：  张少华
 * 创建日期：  2016-06-12
 * 描        述：  广告实体
 * 版        本：  1.0
 */
public class Advertisement implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	//主键Id
	private int id;
	//广告名称
	private String name;
	//广告图片
	private String image;
	//广告分类(0：首页，1：网站安全帮，2：安全能力API)
	private int type;
	//前端展示顺序（数字越大，优先度越高）
	private int orderIndex;
	//广告有效期开始时间
	private Date startDate;
	//广告有效期结束时间
	private Date endDate;
	//广告创建时间
	private Date createDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	
}
