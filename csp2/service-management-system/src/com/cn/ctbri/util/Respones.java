package com.cn.ctbri.util;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Respones {
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
