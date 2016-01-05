package com.cn.ctbri.southapi.adpater.config;
public class EngineStatList {
	private String IP;
	private String MemoryTotal;
	private String MemoryFree;
	private String DiskTotal;
	private String DiskFree;
	private String CpuOccupancy;
	private Engine Engine;
	public void setIP(String IP) {
		this.IP = IP;
	}
	public String getIP() {
		return this.IP;
	}
	public void setMemoryTotal(String MemoryTotal) {
		this.MemoryTotal = MemoryTotal;
	}
	public String getMemoryTotal() {
		return this.MemoryTotal;
	}
	public void setMemoryFree(String MemoryFree) {
		this.MemoryFree = MemoryFree;
	}
	public String getMemoryFree() {
		return this.MemoryFree;
	}
	public void setDiskTotal(String DiskTotal) {
		this.DiskTotal = DiskTotal;
	}
	public String getDiskTotal() {
		return this.DiskTotal;
	}
	public void setDiskFree(String DiskFree) {
		this.DiskFree = DiskFree;
	}
	public String getDiskFree() {
		return this.DiskFree;
	}
	public void setCpuOccupancy(String CpuOccupancy) {
		this.CpuOccupancy = CpuOccupancy;
	}
	public String getCpuOccupancy() {
		return this.CpuOccupancy;
	}
	public void setEngine(Engine Engine) {
		this.Engine = Engine;
	}
	public Engine getEngine() {
		return this.Engine;
	}
}


class Engine {
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