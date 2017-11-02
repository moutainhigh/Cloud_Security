package com.cn.ctbri.southapi.adapter.waf.syslog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public class WAFSyslogDBHelper {
	private String DEFAULT_DB_DRIVER = "com.mysql.jdbc.Driver";	
	private String driver ;	
	private String url ;
	private String user;
	private String password;

	private Connection conn = null;

	public WAFSyslogDBHelper() {
	
	}
	
	public boolean isConnect() {
		if ( conn == null ) return false;
		try {
			if ( !conn.isClosed() ) return true;
		} catch(Exception e) {
			
		}
		
		return false;
	}
	public void connectDB(String url, String user,String password) {
		this.connectDB(DEFAULT_DB_DRIVER,url, user, password);
		
	}
	
	public void connectDB(String driver,String url, String user,String password) {
		if ( driver == null || "".equals(driver) ||
				url == null || "".equals(url) ||
			 user == null || "".equals(user) ||
			 password == null || "".equals(password) )
			return ;
		
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		
		try {
			Class.forName(driver);//ָ����������
			conn = DriverManager.getConnection(url, user, password);//��ȡ����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void closeDB() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public PreparedStatement getPreparedStatement(String sql) {
		try{	
			//For reconnection
			if ( conn==null || conn.isClosed() )
				this.connectDB(driver,url,user,password);
			
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;	
		
	}
	
	public boolean executePreparedStatement(PreparedStatement ps) {
		if ( null == ps ) return false;
		
		try{	
			//For reconnection
			if ( conn==null || conn.isClosed() )
				this.connectDB(driver,url,user,password);
			
            int nRetCode = ps.executeUpdate();
            ps.close();
  			if (nRetCode > 0)  return true;
  			
		}catch(Exception e){
			e.printStackTrace();
		} 
		
		return false;	
	}
	
	
	public boolean insertData(String sql) {
		
		if ( null == sql || "".equals(sql) )
			return false;
		
		try{	
			//For reconnection
			if ( conn==null || conn.isClosed() )
				this.connectDB(driver,url,user,password);
			
            PreparedStatement ps = conn.prepareStatement(sql);
            int nRetCode = ps.executeUpdate();
            ps.close();

			if (nRetCode > 0)
				return true;

		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;	
	}

	
}
