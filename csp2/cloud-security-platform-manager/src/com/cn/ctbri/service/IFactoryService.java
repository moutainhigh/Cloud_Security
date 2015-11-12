package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.Factory;

public interface IFactoryService {
	int deleteByPrimaryKey(Integer id);

    int insert(Factory record);

    int insertSelective(Factory record);

    Factory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Factory record);

    int updateByPrimaryKey(Factory record);
    
    List<Factory> findAll();
}
