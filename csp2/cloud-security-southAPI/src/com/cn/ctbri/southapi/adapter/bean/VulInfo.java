package com.cn.ctbri.southapi.adapter.bean;

import java.io.Serializable;
import java.util.List;

public class VulInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	/**
	 * 漏洞信息id
	 */
	private String vulInfoId;
	/**
	 * 漏洞类型名称
	 */
	private String typeName;
	
	private String typeDescription;
	/**
	 * 告警等级
	 * description:
	 * 0 信息 0分
	 * 1 低危 1-2分
	 * 2 中危 3-5分
	 * 3 高危 6-8分
	 * 4 紧急 9-10分
	 */
	private String level;
	/**
	 * 漏洞告警建议
	 * description:
	 * nsfocus: i18n_solution
	 * arnhem: issuetype:advice
	 */
	private String advice;
	/**
	 * 漏洞地址列表
	 */
	private List<URLInfo> urlInfos;
	public String getVulInfoId() {
		return vulInfoId;
	}
	public void setVulInfoId(String vulInfoId) {
		this.vulInfoId = vulInfoId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeDescription() {
		return typeDescription;
	}
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public List<URLInfo> getUrlInfos() {
		return urlInfos;
	}
	public void setUrlInfos(List<URLInfo> urlInfos) {
		this.urlInfos = urlInfos;
	}
}
