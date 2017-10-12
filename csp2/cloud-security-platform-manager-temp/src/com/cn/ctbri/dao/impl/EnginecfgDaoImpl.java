package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.EnginecfgDao;
import com.cn.ctbri.entity.EngineCfg;
@Repository
public class EnginecfgDaoImpl extends DaoCommon implements EnginecfgDao {
	
	private String ed = "com.cn.ctbri.entity.EnginecfgMapper.";

	public int deleteByPrimaryKey(Integer id) {
		return this.getSqlSession().delete(ed+"deleteByPrimaryKey", id);
	}

	public int insertSelective(EngineCfg record) {
		return this.getSqlSession().insert(ed+"insertSelective", record);
	}

	public EngineCfg findEngineById(Integer id) {
		return this.getSqlSession().selectOne(ed+"findEngineById", id);
	}

	public int updateByPrimaryKeySelective(EngineCfg record) {
		return this.getSqlSession().update(ed+"updateByPrimaryKeySelective", record);
	}

	public List<Map> findAllEnginecfg(Map map) {
		return this.getSqlSession().selectList(ed+"findAllEnginecfg", map);
	}

	public int countEnginecfig(Map map) {
		return this.getSqlSession().selectOne(ed+"count",map);
	}

	public List findEngineByParam(Map map) {
		return this.getSqlSession().selectList(ed+"findEngine", map);
	}

}
