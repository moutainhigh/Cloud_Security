package com.cn.ctbri.dao.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.EngineDao;
import com.cn.ctbri.dao.TaskWarnDao;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.TaskWarn;
/**
 * 创 建 人  ：tang
 * 创建日期：  2015-06-12
 * 描        述：  引擎数据访问层实现类
 * 版        本：  1.0
 */
@Repository
@Transactional
public class EngineDaoImpl extends DaoCommon implements EngineDao {
    @Resource
    public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
        this.setSqlSessionFactory(sessionFactory);
    }
    
	/**
	 * 功        能： EngineMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.EngineMapper.";
	
	public List<EngineCfg> getUsableEngine(Map<String, Object> engineMap) {
		return getSqlSession().selectList(ns+"getUsableEngine", engineMap);
	}

    public EngineCfg findEngineIdbyIP(String engine_addr) {
        return getSqlSession().selectOne(ns+"findEngineIdbyIP", engine_addr);
    }

    public EngineCfg findMinActivity(String ableIds) {
        return getSqlSession().selectOne(ns+"findMinActivity", ableIds);
    }

    public void update(EngineCfg engine) {
        getSqlSession().update(ns+"update", engine);
    }

    public List<EngineCfg> findAbleActivity(String ableIds) {
        return getSqlSession().selectList(ns+"findAbleActivity", ableIds);
    }

    public EngineCfg findEngineById(int id) {
        return getSqlSession().selectOne(ns+"findEngineById", id);
    }

	public List<EngineCfg> findEngineByParam(Map<String, Object> engineMap) {
		return getSqlSession().selectList(ns+"findEngineByParam", engineMap);
	}

	public EngineCfg getEngine() {
		return getSqlSession().selectOne(ns+"getEngine");
	}

	public void updatedown(EngineCfg en) {
		getSqlSession().update(ns+"updatedown", en);
	}

	public EngineCfg getEngineById(int id) {
		return getSqlSession().selectOne(ns+"getEngineById",id);
	}
		
	
}
