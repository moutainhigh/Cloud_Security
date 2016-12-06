package com.cn.ctbri.entity;
/**
 * 数据统计 VO
 * @author 邓元元
 *
 */
public class DataAnalysis {

	private String name;//服务名称
	private Integer count;//服务统计个数
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
