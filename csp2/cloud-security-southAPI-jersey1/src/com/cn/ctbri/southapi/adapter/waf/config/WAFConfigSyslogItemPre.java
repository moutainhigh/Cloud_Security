package com.cn.ctbri.southapi.adapter.waf.config;

public class WAFConfigSyslogItemPre {
	private String id;
	private String objmethod;
	private String objClass;
	
	private Integer argCount = 0;
	
	private String[] arrayArg = new String[10];

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjmethod() {
		return objmethod;
	}

	public void setObjmethod(String objmethod) {
		this.objmethod = objmethod;
	}

	public String getObjClass() {
		return objClass;
	}

	public void setObjClass(String objClass) {
		this.objClass = objClass;
	}


	public Integer getArgCount() {
		return argCount;
	}

	public void setArgCount(Integer argCount) {
		this.argCount = argCount;
	}

	public String[] getArrayArg() {
		return arrayArg;
	}

	public void setArrayArg(String[] arrayArg) {
		this.arrayArg = arrayArg;
	}	
	
	
	
	
}
