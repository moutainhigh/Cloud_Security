package com.cn.ctbri.entity;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-14
 * 描        述： 订单资产
 * 版        本：  1.0
 */
public class OrderAsset {
	private int id;//主键Id
	private String orderId;//订单Id
	private int assetId;//资产Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
}
