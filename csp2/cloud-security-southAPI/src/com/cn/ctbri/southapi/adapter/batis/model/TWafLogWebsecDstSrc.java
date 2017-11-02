package com.cn.ctbri.southapi.adapter.batis.model;

public class TWafLogWebsecDstSrc {
    private String srcIp;
	private String dstIp;
	private String srcCountry;
	private String srcSubdivision1;
	private String srcSubdivision2;
	private String srcCity; 
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

	public String getSrcCountry() {
		return srcCountry;
	}

	public void setSrcCountry(String srcCountry) {
		this.srcCountry = srcCountry;
	}

	public String getSrcSubdivision1() {
		return srcSubdivision1;
	}

	public void setSrcSubdivision1(String srcSubdivision1) {
		this.srcSubdivision1 = srcSubdivision1;
	}

	public String getSrcSubdivision2() {
		return srcSubdivision2;
	}

	public void setSrcSubdivision2(String srcSubdivision2) {
		this.srcSubdivision2 = srcSubdivision2;
	}

	public String getSrcCity() {
		return srcCity;
	}

	public void setSrcCity(String srcCity) {
		this.srcCity = srcCity;
	}

	public String getStatDate() {
		return statDate;
	}

	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
}