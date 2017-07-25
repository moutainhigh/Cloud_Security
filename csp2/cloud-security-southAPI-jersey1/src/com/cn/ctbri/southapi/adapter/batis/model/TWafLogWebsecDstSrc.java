package com.cn.ctbri.southapi.adapter.batis.model;

public class TWafLogWebsecDstSrc {
    private String srcIp;


	private String dstIp;


	private String statDate;


	private Long count;

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getDstIp() {
		return dstIp;
	}

	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}

	public String getStatDate() {
		return statDate;
	}

	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
}