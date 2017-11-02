package com.cn.ctbri.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-07
 * 描        述：  Dao实现类共有属性与方法
 * 版        本：  1.0
 */
public class DaoCommon extends SqlSessionDaoSupport{
	
	@Resource(name="sqlSession")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
}
