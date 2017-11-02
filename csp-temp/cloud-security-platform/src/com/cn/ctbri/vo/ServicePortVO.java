package com.cn.ctbri.vo;

import java.io.Serializable;

/**
 * 服务名称-服务端口VO类
 * @author dancy
 * @date 2017-07-02 21:02
 */
public class ServicePortVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String service;
	private String port;
	
	public ServicePortVO() {
		super();
	}
	
	

	public ServicePortVO(String service, String port) {
		super();
		this.service = service;
		this.port = port;
	}



	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "ServciePortVO [service=" + service + ", port=" + port + "]";
	}
}
