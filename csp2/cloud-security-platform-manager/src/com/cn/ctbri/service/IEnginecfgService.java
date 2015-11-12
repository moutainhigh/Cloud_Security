package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.EngineCfg;

public interface IEnginecfgService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(EngineCfg record);

    EngineCfg findEngineById(Integer id);

    int updateByPrimaryKeySelective(EngineCfg record);

    List<Map> findAllEnginecfg(Map map);
    
    int countEnginecfig(Map map);
}
