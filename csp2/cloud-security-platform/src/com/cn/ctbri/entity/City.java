package com.cn.ctbri.entity;

public class City {
	private int id;
	private String code; //城市编码
	private String name; //城市中文名称
	private int type;	 //城市类别：南方分为1、2、3、4类；北方分为1、2类
	private int provId;  //所属省ID
	private String provCode; //所属省份编码
	private String provName; //省份名称
	private String area;	 //城市所属区域：0南方、1北方
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public int getProvId() {
		return provId;
	}
	public void setProvId(int provId) {
		this.provId = provId;
	}
	public String getProvCode() {
		return provCode;
	}
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	public String getProvName() {
		return provName;
	}
	public void setProvName(String provName) {
		this.provName = provName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
}
