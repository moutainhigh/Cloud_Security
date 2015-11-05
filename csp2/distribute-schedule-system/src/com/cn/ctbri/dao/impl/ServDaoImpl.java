package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.ServDao;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Task;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-15
 * 描        述：   服务数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class ServDaoImpl extends DaoCommon implements ServDao{


	
	/**
	 * 功        能： OrderMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.ServMapper.";


	public Serv findById(int serviceid) {
		return this.getSqlSession().selectOne(ns + "findById",serviceid);
	}

	/**
     * 功能描述： 根据条件查询服务
     * 参数描述： Serv service
     *       @time 2015-1-21
     *  返回值 ：Serv
     */
    public List<Serv> getServiceByParam(Serv service) {
        return this.getSqlSession().selectList(ns + "findServiceByParam",service);
    }
    
	/**
     * 功能描述： 根据任务查询服务
     * 参数描述： Serv service
     *       @time 2015-1-31
     *  返回值 ：Serv
     */
	public List<HashMap<String, Object>> getServByTask(Task task) {
		return this.getSqlSession().selectList(ns + "findServiceByTask",task);
	}
	
}
