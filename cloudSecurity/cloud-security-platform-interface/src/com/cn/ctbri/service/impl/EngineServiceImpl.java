package com.cn.ctbri.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.EngineDao;
import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.ITaskService;
@Service
public class EngineServiceImpl implements IEngineService{

	@Autowired
	EngineDao engineDao;

	public List<EngineCfg> getUsableEngine(Map<String, Object> engineMap) {
		return engineDao.getUsableEngine(engineMap);
	}

    public EngineCfg findEngineIdbyIP(String engine_addr) {
        return engineDao.findEngineIdbyIP(engine_addr);
    }

    public EngineCfg findMinActivity(String ableIds) {
        return engineDao.findMinActivity(ableIds);
    }

    public void update(EngineCfg engine) {
        engineDao.update(engine);
    }

    public List<EngineCfg> findAbleActivity(String ableIds) {
        return engineDao.findAbleActivity(ableIds);
    }

    public EngineCfg findEngineById(int id) {
        return engineDao.findEngineById(id);
    }

	public List<EngineCfg> findEngineByParam(Map<String, Object> engineMap) {
		return engineDao.findEngineByParam(engineMap);
	}

	public EngineCfg getEngine() {
		return engineDao.getEngine();
	}

	public void updatedown(EngineCfg en) {
		engineDao.updatedown(en);
	}

	public EngineCfg getEngineById(int id) {
		return engineDao.getEngineById(id);
	}


	
}
