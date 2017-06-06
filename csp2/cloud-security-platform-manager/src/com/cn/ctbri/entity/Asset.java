package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：   2014-12-31
 * 描        述：  资产实体类
 * 版        本：  1.0
 */
public class Asset {
	
	private int id;
	private String name;//资产名称
	private int type;//资产类型：1为URL，2为IP
	private String addr;//资产地址
	private Integer status;//资产状态(1：已验证，0：未验证)
	private int userid;//用户ID
	private Date create_date;//创建日期
	private String remarks;//备注
	
	private String verification_msg;//VO :代码验证
	private String addrType;//VO :资产地址类型
	
	private String ip;//ip地址
	private int districtId;//地区id
	private String disName;//地区名称
	
	private String city;//城市id
	private String cityName;//城市名称
	
	
	private int num;

	private String purpose;
	private int assetProvince;//资产所在的省份
	
	public String getAddrType() {
		return addrType;
	}
	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVerification_msg() {
		return verification_msg;
	}
	public void setVerification_msg(String verification_msg) {
		this.verification_msg = verification_msg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getDistrictId() {
        return districtId;
    }
    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
	public String getDisName() {
		return disName;
	}
	public void setDisName(String disName) {
		this.disName = disName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public int getAssetProvince() {
		return assetProvince;
	}
	public void setAssetProvince(int assetProvince) {
		this.assetProvince = assetProvince;
	}
	
}
