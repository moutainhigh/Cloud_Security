package com.cn.ctbri.southapi.adapter.config;

public class Engine {
	private String EngineName;
	private String EngineState;
	private String Eversion;
	private String EUpTime;
	private String Pversion;
	private String PUpTime;
	public void setEngineName(String EngineName) {
		this.EngineName = EngineName;
	}
	public String getEngineName() {
		return this.EngineName;
	}
	public void setEngineState(String EngineState) {
		this.EngineState = EngineState;
	}
	public String getEngineState() {
		return this.EngineState;
	}
	public void setEversion(String Eversion) {
		this.Eversion = Eversion;
	}
	public String getEversion() {
		return this.Eversion;
	}
	public void setEUpTime(String EUpTime) {
		this.EUpTime = EUpTime;
	}
	public String getEUpTime() {
		return this.EUpTime;
	}
	public void setPversion(String Pversion) {
		this.Pversion = Pversion;
	}
	public String getPversion() {
		return this.Pversion;
	}
	public void setPUpTime(String PUpTime) {
		this.PUpTime = PUpTime;
	}
	public String getPUpTime() {
		return this.PUpTime;
	}
}