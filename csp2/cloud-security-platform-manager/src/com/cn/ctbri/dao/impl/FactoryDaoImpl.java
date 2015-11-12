package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.FactoryDao;
import com.cn.ctbri.entity.Factory;
@Repository
public class FactoryDaoImpl extends DaoCommon implements FactoryDao {

	private String fd = "com.cn.ctbri.entity.FactoryMapper.";
	
	public int deleteByPrimaryKey(Integer id) {
		return this.getSqlSession().delete(fd+"deleteByPrimaryKey", id);
	}

	public int insert(Factory record) {
		return this.getSqlSession().insert(fd+"insert", record);
	}

	public int insertSelective(Factory record) {
		return this.getSqlSession().insert(fd+"insertSelective", record);
	}

	public Factory selectByPrimaryKey(Integer id) {
		return this.getSqlSession().selectOne(fd+"selectByPrimaryKey", id);
	}

	public int updateByPrimaryKeySelective(Factory record) {
		return this.getSqlSession().update(fd+"updateByPrimaryKeySelective", record);
	}

	public int updateByPrimaryKey(Factory record) {
		return this.getSqlSession().update(fd+"updateByPrimaryKey", record);
	}

	public List<Factory> findAll() {
		return this.getSqlSession().selectList(fd+"findAll");
	}

}
