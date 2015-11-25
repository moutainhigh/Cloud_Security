package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.OperatingTableDao;

@Repository
@Transactional
public class OperatingTableDaoImpl extends writeDaoCommon implements OperatingTableDao {
	
	String sn = "com.cn.ctbri.dao.OperatingTableDao.";

	public void bakTable(List<Map> list) {
		for(int i=0;i<list.size();i++){
			Map map = list.get(i);
			String value = "create table "+map.get("bakTableName")+" as select * from "+map.get("tableName");
			this.getConn(value);
			this.closeConn();
//			this.getSqlSession().selectOne(sn+"bakTable",value);
		}
	}

	public void trunTable(String tableName) {
		String value = "truncate table "+tableName;
		this.getConn(value);
		this.closeConn();
//		this.getSqlSession().selectOne(sn+"trunTable",value);
	}

	public void dropTable(String tableName) {
		String value = "drop table "+tableName;
		this.getConn(value);
		this.closeConn();
//		this.getSqlSession().selectOne(sn+"dropTable",value);		
	}

}
