package com.cn.ctbri.util;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Respones {
	private String state;
	private String result;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
