package com.ctbri.util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtils {
	private static SqlSessionFactory factory;

	static {
		InputStream in = MyBatisUtils.class.getClassLoader()
				.getResourceAsStream("mybatis-config.xml");
		factory = new SqlSessionFactoryBuilder().build(in);
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return factory;
	}

	public static SqlSession openSession() {
		return factory.openSession();
	}

	public static void closeSession() {
		factory.openSession().close();
	}
}
