package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Factory;

public interface FactoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Factory record);

    int insertSelective(Factory record);

    Factory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Factory record);

    int updateByPrimaryKey(Factory record);
    
    List<Factory> findAll();
}