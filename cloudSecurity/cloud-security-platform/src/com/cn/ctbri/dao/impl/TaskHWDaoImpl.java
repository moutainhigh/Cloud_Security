package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.dao.TaskHWDao;
import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskHW;
/**
 * 华为任务信息dao实现类
 * @author txr
 *
 */
@Repository
@Transactional
public class TaskHWDaoImpl extends DaoCommon implements TaskHWDao {
	
	/**
	 * 功        能： UserMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.TaskHWMapper.";		
	
	
	public int insert(TaskHW taskhw) {
		return getSqlSession().insert(ns+"insert", taskhw);
	}


    public List<TaskHW> findTask(Map<String, Object> map) {
        return getSqlSession().selectList(ns+"findTaskhw", map);
    }


    public void update(TaskHW t) {
        getSqlSession().update(ns+"update", t);
    }


    public OrderIP getIpByTaskId(int order_ip_id) {
        return getSqlSession().selectOne(ns+"getIpByTaskId", order_ip_id);
    }


	public List<TaskHW> findAlarmbyTaskhw(Map<String, Object> map) {
		return getSqlSession().selectList(ns+"findAlarmbyTaskhw", map);
	}


}
