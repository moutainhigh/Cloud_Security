package com.cn.ctbri.service;

public interface IDataSyncService {
	public String dataSync(String table);
	
	public void bakTable(String tableName);
	
	public void trunTable(String tableName);
	
	public void dropTable(String tableName);
	
	public void resetTable(String tableName);
}
