package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.TaskWarn;

/**
 * 创 建 人  ：  tang
 * 创建日期：  2015-6-12
 * 描        述：  引擎业务层接口
 * 版        本：  1.0
 */
public interface IEngineService {

	//获取服务支持的引擎
	List<EngineCfg> getUsableEngine(Map<String, Object> engineMap);

	//根据ip查找引擎EngineCfg
    EngineCfg findEngineIdbyIP(String engine_addr);

    //获取任务下发的引擎
    EngineCfg findMinActivity(String ableIds);

    //更新活跃度+1
    void update(EngineCfg engine);
    //获取可用引擎
    List<EngineCfg> findAbleActivity(String ableIds);

    //根据id查询引擎
    EngineCfg findEngineById(int id);

    //查找最佳引擎
	List<EngineCfg> findEngineByParam(Map<String, Object> engineMap);

	EngineCfg getEngine();
	//更新活跃度-1
	void updatedown(EngineCfg en);

	EngineCfg getEngineById(int id);


}
