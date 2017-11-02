package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.TaskWarn;

/**
 * 创 建 人  ：  tang
 * 创建日期：  2015-6-12
 * 描        述：  引擎数据访问层接口类
 * 版        本：  1.0
 */
public interface EngineDao {

	List<EngineCfg> getUsableEngine(Map<String, Object> engineMap);

    EngineCfg findEngineIdbyIP(String engine_addr);

    EngineCfg findMinActivity(String ableIds);

    void update(EngineCfg engine);

    List<EngineCfg> findAbleActivity(String ableIds);

    EngineCfg findEngineById(int id);

	List<EngineCfg> findEngineByParam(Map<String, Object> engineMap);

	EngineCfg getEngine();

	void updatedown(EngineCfg en);

	EngineCfg getEngineById(int id);


}
