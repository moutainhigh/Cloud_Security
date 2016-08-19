package com.cn.ctbri.entity;

public class AttackCount {
	// 类型编码
	private Integer typeCode;
	// 数量
	private long currentNum;


	public AttackCount(Integer typeCode, long currentNum) {
		this.typeCode = typeCode;
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

}
