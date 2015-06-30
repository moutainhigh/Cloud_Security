package com.cn.ctbri.entity;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-5
 * 描        述：  权限实体类
 * 版        本：  1.0
 */
public class Authority implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String authorityName;//权限名称
	private String url;//访问路径
	private int state;//状态:0:公共，1：前台权限页面，2：后台权限页面
	private String remark;//备注
	public Authority(){}
	public Authority(String authorityName, String url) {
		super();
		this.authorityName = authorityName;
		this.url = url;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
