package com.cn.ctbri.southapi.adapter.common;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cn.ctbri.southapi.adapter.manager.DeviceAdapterConstant;

public class WafDatabaseController {
	public static SqlSessionFactory sqlSessionFactory = null;
	public static SqlSession sqlSession = null;
	public static String DatabaseConfigString = DeviceAdapterConstant.RESOURCE_DATABASE_CONFIG;
	public static SqlSession getSqlSession() throws IOException{
		if (sqlSessionFactory==null) {
			Reader reader = Resources.getResourceAsReader(DatabaseConfigString);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,"wafForCtbri");
		}
		SqlSession sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	public static void closeSqlSession(SqlSession sqlSession){
		if (sqlSession!=null) {
			sqlSession.close();
		}
	}
	
}
