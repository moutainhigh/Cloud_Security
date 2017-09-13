package com.cn.ctbri.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.ServiceSysDao;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceSys;

/**
 * 创 建 人  ：  cy
 * 创建日期：  2017-2-17
 * 描        述：  系统安全帮数据访问层实现类
 * 版        本：  1.0
 */
@Repository

public class ServiceSysDaoImpl extends DaoCommon implements ServiceSysDao{
	/**
	 * 功        能： ServiceAPIMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.ServiceSysMapper.";


	public ServiceSys findById(int SysId) {
		return this.getSqlSession().selectOne(ns + "findById",SysId);
	}


	public List<ServiceSys> findServiceSys() {
		return this.getSqlSession().selectList(ns + "list");
	}


	public List findSysPriceList() {
		return this.getSqlSession().selectList(ns + "findSysPriceList");
	}


	public int insert(ServiceSys serviceSys) {
		this.getSqlSession().insert(ns + "insertServSys", serviceSys);
        return serviceSys.getId();
	}


	public void updateById(ServiceSys serviceSys) {
		this.getSqlSession().update(ns+"updateById", serviceSys);
		
	}


	public void deleteById(int SysId) {
		this.getSqlSession().delete(ns+"deleteById", SysId);
		
	}

	

}
