package com.cn.ctbri.southapi.adapter.common;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cn.ctbri.southapi.adapter.manager.DeviceAdapterConstant;

public class CommonDatabaseController {
	private static SqlSessionFactory sqlSessionFactory;
	private static SqlSessionFactory sqlSessionFactoryForOpenphish;
	private static SqlSessionFactory sqlSessionFactoryForPortscan;
//	private static SqlSession sqlSession;
//	private static SqlSession sqlSessionForOpenphish;
//	private static SqlSession sqlSessionForPortscan;
	public static final String DatabaseConfigString = DeviceAdapterConstant.RESOURCE_DATABASE_CONFIG;
	//TODO SqlSession 数组，建立资源池
	
    private CommonDatabaseController(){  
    }  
	
	
	
	public static SqlSession getSqlSession() throws IOException{
		if (sqlSessionFactory==null) {
			Reader reader = Resources.getResourceAsReader(DatabaseConfigString);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		}



		SqlSession sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	public static SqlSession getOpenPhishSqlSession() throws IOException{
		if (sqlSessionFactoryForOpenphish==null) {
			Reader reader = Resources.getResourceAsReader(DatabaseConfigString);
			sqlSessionFactoryForOpenphish  = new SqlSessionFactoryBuilder().build(reader, "openphish");
		}
		SqlSession sqlSessionForOpenphish = sqlSessionFactoryForOpenphish.openSession();
		return sqlSessionForOpenphish;
	}
	
	public static SqlSession getPortscanSqlSession() throws IOException{
		if (sqlSessionFactoryForPortscan==null) {
			Reader reader = Resources.getResourceAsReader(DatabaseConfigString);
			sqlSessionFactoryForPortscan = new SqlSessionFactoryBuilder().build(reader, "portscan");
		}
		SqlSession sqlSessionForPortscan = sqlSessionFactoryForPortscan.openSession();
		return sqlSessionForPortscan;		
	}
	
	public static void closeSqlSession(SqlSession sqlSession){
		if(sqlSession!=null){
			sqlSession.close();
		}
	}
	
}
