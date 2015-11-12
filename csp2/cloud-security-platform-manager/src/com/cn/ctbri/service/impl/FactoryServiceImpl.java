package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.FactoryDao;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.service.IFactoryService;
@Service
public class FactoryServiceImpl implements IFactoryService {
	
	@Autowired
	FactoryDao factoryDao;

	public int deleteByPrimaryKey(Integer id) {
		return factoryDao.deleteByPrimaryKey(id);
	}

	public int insert(Factory record) {
		return factoryDao.insert(record);
	}

	public int insertSelective(Factory record) {
		return factoryDao.insertSelective(record);
	}

	public Factory selectByPrimaryKey(Integer id) {
		return factoryDao.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(Factory record) {
		return factoryDao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Factory record) {
		return factoryDao.updateByPrimaryKey(record);
	}

	public List<Factory> findAll() {
		return factoryDao.findAll();
	}

}
