package com.cn.ctbri.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.batch.core.JobExecution;

import com.cn.ctbri.common.JobLaunch;
import com.cn.ctbri.dao.OperatingTableDao;
import com.cn.ctbri.dao.impl.OperatingTableDaoImpl;
import com.cn.ctbri.service.IDataSyncService;
public class DataSyncServiceImpl implements IDataSyncService {
	
	String[] tableName = null;
	
	public String dataSync(String table) {
		InputStream in = DataSyncServiceImpl.class.getClassLoader().getResourceAsStream("table.properties");
    	Properties p = new Properties();
    	String resultStatus = null;
    	try {
			p.load(in);
			tableName = p.getProperty(table).split(",");
			for(int i=0;i<tableName.length;i++){
				tableName[i] = tableName[i].trim();
				this.bakTable(tableName[i]);
				this.trunTable(tableName[i]);
				JobExecution result = JobLaunch.dataSync(tableName[i]+"Select", tableName[i]+"Insert", tableName[i]+"Mapper");
				System.out.println(result.getExitStatus());
				if("COMPLETED".equals(result.getExitStatus().getExitCode())){
					this.dropTable(tableName[i]);
					resultStatus = result.getExitStatus().getExitCode();
				}else{
					this.resetTable(tableName[i]);
					resultStatus = result.getExitStatus().getExitCode();
				}
			}
			return resultStatus;
    	} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void bakTable(String tableName){
		OperatingTableDao otDao = new OperatingTableDaoImpl();
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("bakTableName", "cs_"+tableName+"_bak");
		map.put("tableName", "cs_"+tableName);
		list.add(map);
		otDao.bakTable(list);
	}
	public void trunTable(String tableName) {
		OperatingTableDao otDao = new OperatingTableDaoImpl();
		otDao.trunTable("cs_"+tableName);
	}
	public void dropTable(String tableName) {
		OperatingTableDao otDao = new OperatingTableDaoImpl();
		otDao.dropTable("cs_"+tableName+"_bak");
	}
	public void resetTable(String tableName) {
		OperatingTableDao otDao = new OperatingTableDaoImpl();
		otDao.dropTable("cs_"+tableName);
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("bakTableName", "cs_"+tableName);
		map.put("tableName", "cs_"+tableName+"_bak");
		list.add(map);
		otDao.bakTable(list);
		otDao.dropTable("cs_"+tableName+"_bak");
	}

}
