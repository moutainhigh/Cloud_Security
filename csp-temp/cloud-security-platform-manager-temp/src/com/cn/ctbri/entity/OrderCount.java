package com.cn.ctbri.entity;

public class OrderCount {
	private int type;
	private String name;
	private long countNums;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCountNums() {
		return countNums;
	}
	public void setCountNums(long countNums) {
		this.countNums = countNums;
	}
	public OrderCount(int type, String name, long countNums) {
		super();
		this.type = type;
		this.name = name;
		this.countNums = countNums;
	}
	
}
