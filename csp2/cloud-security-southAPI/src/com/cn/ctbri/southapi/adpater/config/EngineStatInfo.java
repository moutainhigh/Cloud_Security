package com.cn.ctbri.southapi.adpater.config;

public class EngineStatInfo {
	private String EngineIP;
	private String MemoryTotal;
	private String MemoryFree;
	private String DiskTotal;
	private String CPUOccupancy;
	private String EngineName;
	private String EngineStat;
	private String EVersion;
	private String PVersion;
	public String getEngineIP() {
		return EngineIP;
	}
	public void setEngineIP(String engineIP) {
		EngineIP = engineIP;
	}
	public String getMemoryTotal() {
		return MemoryTotal;
	}
	public void setMemoryTotal(String memoryTotal) {
		MemoryTotal = memoryTotal;
	}
	public String getMemoryFree() {
		return MemoryFree;
	}
	public void setMemoryFree(String memoryFree) {
		MemoryFree = memoryFree;
	}
	public String getDiskTotal() {
		return DiskTotal;
	}
	public void setDiskTotal(String diskTotal) {
		DiskTotal = diskTotal;
	}
	public String getCPUOccupancy() {
		return CPUOccupancy;
	}
	public void setCPUOccupancy(String cPUOccupancy) {
		CPUOccupancy = cPUOccupancy;
	}
	public String getEngineName() {
		return EngineName;
	}
	public void setEngineName(String engineName) {
		EngineName = engineName;
	}
	public String getEngineStat() {
		return EngineStat;
	}
	public void setEngineStat(String engineStat) {
		EngineStat = engineStat;
	}
	public String getEVersion() {
		return EVersion;
	}
	public void setEVersion(String eVersion) {
		EVersion = eVersion;
	}
	public String getPVersion() {
		return PVersion;
	}
	public void setPVersion(String pVersion) {
		PVersion = pVersion;
	}
}
