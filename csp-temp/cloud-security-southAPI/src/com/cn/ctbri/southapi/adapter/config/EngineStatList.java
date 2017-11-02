package com.cn.ctbri.southapi.adapter.config;

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


