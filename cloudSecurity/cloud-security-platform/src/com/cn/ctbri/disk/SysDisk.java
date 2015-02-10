package com.cn.ctbri.disk;

public class SysDisk {

	private Long freeSpace;//未用
	private Long usableSpace;//已用
	private Long totalSpace;//总大小
	public Long getFreeSpace() {
		return freeSpace;
	}
	public void setFreeSpace(Long freeSpace) {
		this.freeSpace = freeSpace;
	}
	public Long getUsableSpace() {
		return usableSpace;
	}
	public void setUsableSpace(Long usableSpace) {
		this.usableSpace = usableSpace;
	}
	public Long getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(Long totalSpace) {
		this.totalSpace = totalSpace;
	}
	
}
