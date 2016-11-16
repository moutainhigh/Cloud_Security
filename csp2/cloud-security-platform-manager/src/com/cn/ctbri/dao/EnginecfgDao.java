package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.EngineCfg;


public interface EnginecfgDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EngineCfg record);

    EngineCfg findEngineById(Integer id);

    int updateByPrimaryKeySelective(EngineCfg record);

    List<Map> findAllEnginecfg(Map map);
    
    int countEnginecfig(Map map);

	List findEngineByParam(Map paramMap);
}