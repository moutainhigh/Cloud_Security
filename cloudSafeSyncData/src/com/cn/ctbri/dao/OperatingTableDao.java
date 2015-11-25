package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

public interface OperatingTableDao {
	public void bakTable(List<Map> map);
	
	public void trunTable(String tableName);
	
	public void dropTable(String tableName);
}
