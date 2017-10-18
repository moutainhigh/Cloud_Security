package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.EnginecfgDao;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.service.IEnginecfgService;
@Service
public class EnginecfgServiceImpl implements IEnginecfgService {
	
	@Autowired
	EnginecfgDao enginecfgDao;
	
	public int deleteByPrimaryKey(Integer id) {
		return enginecfgDao.deleteByPrimaryKey(id);
	}

	public int insertSelective(EngineCfg record) {
		return enginecfgDao.insertSelective(record);
	}

	public EngineCfg findEngineById(Integer id) {
		return enginecfgDao.findEngineById(id);
	}

	public int updateByPrimaryKeySelective(EngineCfg record) {
		return enginecfgDao.updateByPrimaryKeySelective(record);
	}

	public List<Map> findAllEnginecfg(Map map) {
		return enginecfgDao.findAllEnginecfg(map);
	}

	public int countEnginecfig(Map map) {
		return enginecfgDao.countEnginecfig(map);
	}

	public List findEngineByParam(Map paramMap) {
		return enginecfgDao.findEngineByParam(paramMap);
	}

}
