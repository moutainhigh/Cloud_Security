package com.cn.ctbri.southapi.adapter.common;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cn.ctbri.southapi.adapter.manager.DeviceAdapterConstant;

public class CommonDatabaseController {
	public static SqlSessionFactory sqlSessionFactory = null;
	public static SqlSessionFactory sqlSessionFactoryForOpenphish = null;
	public static SqlSession sqlSession = null;
	public static SqlSession sqlSessionForOpenphish = null;
	public static String DatabaseConfigString = DeviceAdapterConstant.RESOURCE_DATABASE_CONFIG;
	//TODO SqlSession 数组，建立资源池
	public static SqlSession getSqlSession() throws IOException{
		if ( sqlSessionFactory == null ) {
			Reader reader;
			reader = Resources.getResourceAsReader(DatabaseConfigString);
		    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		}
		
		if ( sqlSession == null ) {//sqlSession.isClose(); openSession 是否容错
			try {
				
				sqlSession = sqlSessionFactory.openSession();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Connection conn = sqlSession.getConnection();
		try {
			if( conn.isClosed() ){
				sqlSession = sqlSessionFactory.openSession();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return sqlSession;
	}
	
	public static SqlSession getOpenPhishSqlSession() throws IOException{
		if ( sqlSessionFactoryForOpenphish  == null ) {
			Reader reader;
			reader = Resources.getResourceAsReader(DeviceAdapterConstant.RESOURCE_DATABASE_CONFIG);
			sqlSessionFactoryForOpenphish  = new SqlSessionFactoryBuilder().build(reader, "openphish");
		}
		
		if ( sqlSessionForOpenphish == null ) {//sqlSession.isClose(); openSession 是否容错
			try {
				sqlSessionForOpenphish = sqlSessionFactoryForOpenphish.openSession();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Connection conn = sqlSessionForOpenphish.getConnection();
		try {
			if( conn.isClosed() ){
				sqlSessionForOpenphish = sqlSessionFactoryForOpenphish.openSession();
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return sqlSessionForOpenphish;
	}
	
	public static void closeSqlSession(SqlSession sqlSession){
		
		return;
		/*
		if(sqlSession==null){
			return;
		}
		sqlSession.close();
		
		sqlSession = null;
		*/
	}
	
}
