package com.cn.ctbri.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.OrderTaskDao;
import com.cn.ctbri.entity.OrderTask;
/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-11-10
 * 描        述：   订单任务数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class OrderTaskDaoImpl extends DaoCommon implements OrderTaskDao{
	
	@Resource
	public final void setSessionFactoryRegister(SqlSessionFactory sessionFactory) {
		this.setSqlSessionFactory(sessionFactory);
	}  

	/**
	 * 功        能： OrderTaskMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.OrderTaskMapper.";

    /**
     * 功能描述：保存订单
     *       @time 2015-11-10
     */
	public void save(OrderTask orderTask) {
		this.getSqlSession().insert(ns + "insert", orderTask);
	}  
	
	
}
