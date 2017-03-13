package com.cn.ctbri.entity;


/**
 * 创 建 人  ：  tangxr
 * 创建日期：   2016-4-9
 * 描        述：  第三方用户实体类
 * 版        本：  1.0
 */
public class User implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	//用户key 
	private String apikey;
	//推送url 
	private String urlAddr;
	//token
	private String token;
	//api类型
	private int api;
	//api类型
	private int api1;
	//api类型
	private int api2;
	//api类型
	private int api3;
	//api类型
	private int api4;
	//api类型
	private int api5;
	//计算数量
	private int count;
	//用户类型2：普通，3：企业
	private int type;
	//合作方
	private String partner;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public String getUrlAddr() {
		return urlAddr;
	}
	public void setUrlAddr(String urlAddr) {
		this.urlAddr = urlAddr;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getApi() {
		return api;
	}
	public void setApi(int api) {
		this.api = api;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getApi1() {
		return api1;
	}
	public void setApi1(int api1) {
		this.api1 = api1;
	}
	public int getApi2() {
		return api2;
	}
	public void setApi2(int api2) {
		this.api2 = api2;
	}
	public int getApi3() {
		return api3;
	}
	public void setApi3(int api3) {
		this.api3 = api3;
	}
	public int getApi4() {
		return api4;
	}
	public void setApi4(int api4) {
		this.api4 = api4;
	}
	public int getApi5() {
		return api5;
	}
	public void setApi5(int api5) {
		this.api5 = api5;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	
}
