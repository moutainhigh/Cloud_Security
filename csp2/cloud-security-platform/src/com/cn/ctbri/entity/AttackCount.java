package com.cn.ctbri.entity;

public class AttackCount {
	// 类型编码
	private Integer typeCode;
	
	private String type;
	// 数量
	private long currentNum;


	public AttackCount(String type, long currentNum) {
		this.type = type;
		this.currentNum = currentNum;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}

	public long getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(long currentNum) {
		this.currentNum = currentNum;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "[\""+type+"\","+
				+ currentNum + "]";
	}
	

}
