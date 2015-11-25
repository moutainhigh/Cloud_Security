package com.cn.ctbri.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.common.GetJobBeans;

public class writeDaoCommon {
	BasicDataSource wirteDataSource;
	
	Connection conn = null;
	PreparedStatement ps = null;
	
	public void getConn(String sql){
		try {
			wirteDataSource = GetJobBeans.getDataSource();
			conn = wirteDataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConn(){
		try {
			conn.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
