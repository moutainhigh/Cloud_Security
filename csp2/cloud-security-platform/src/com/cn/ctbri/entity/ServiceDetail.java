package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 服务详情类
 * @author admin
 *
 */
public class ServiceDetail {

	private int id;  //主键id
	private int serviceId; //服务id
	private String priceTitle; //价格标题
	private String typeTitle; //选类型标题
	private int servType; //服务类型（0：单次和长期 1：单次 2：长期）
	private String ratesTitle; //服务频率标题
	private String detailIcon; //服务详细信息（图片）
	private Date createTime; //创建时间
	private int ParentC; //父级分类
	private String detailFlag; //服务相信信息配置的flag 0：图片 1：api

	public String getDetailFlag() {
		return detailFlag;
	}
	public void setDetailFlag(String detailFlag) {
		this.detailFlag = detailFlag;
	}
	public int getParentC() {
		return ParentC;
	}
	public void setParentC(int parentC) {
		ParentC = parentC;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getPriceTitle() {
		return priceTitle;
	}
	public void setPriceTitle(String priceTitle) {
		this.priceTitle = priceTitle;
	}
	public String getTypeTitle() {
		return typeTitle;
	}
	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}
	public int getServType() {
		return servType;
	}
	public void setServType(int servType) {
		this.servType = servType;
	}
	public String getRatesTitle() {
		return ratesTitle;
	}
	public void setRatesTitle(String ratesTitle) {
		this.ratesTitle = ratesTitle;
	}
	public String getDetailIcon() {
		return detailIcon;
	}
	public void setDetailIcon(String detailIcon) {
		this.detailIcon = detailIcon;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
