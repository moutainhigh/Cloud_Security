package com.cn.ctbri.southapi.adapter.bean;

public class URLInfo {
	private String urlId;
	/**
	 * 具体漏洞告警内容地址
	 * description:
	 * nsfocus:url_info
	 * arnhem:issuedata:url
	 */
	private String urlString;
	
	private String urlParam;
	/**
	 * 关键字
	 * arnhem:issuedata:value
	 * 
	 */
	private String verifyUrl;
	public String getUrlId() {
		return urlId;
	}
	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}
	public String getUrlString() {
		return urlString;
	}
	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
	public String getUrlParam() {
		return urlParam;
	}
	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}
	public String getVerifyUrl() {
		return verifyUrl;
	}
	public void setVerifyUrl(String verifyUrl) {
		this.verifyUrl = verifyUrl;
	}
	@Override
	public String toString(){
		return "urlId ="+this.getUrlId()+"; urlString="+this.getUrlString()+"; verifyUrl="+this.getVerifyUrl();
	}
}
