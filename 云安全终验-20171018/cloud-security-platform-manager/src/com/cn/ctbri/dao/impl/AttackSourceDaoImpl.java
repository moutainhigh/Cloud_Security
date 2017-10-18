package com.cn.ctbri.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.AttackSourceDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.AttackSource;

@Repository
public class AttackSourceDaoImpl extends DaoCommon implements AttackSourceDao {
	
	/**
	 * 功        能： AssetMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.AttackSourceMapper.";
	
	/**
	 * 功能描述：查询每个省份的攻击源数量
	 * 参数描述：
	 * 返回值    ：  List
	 */
	public List findCountByProvice() {
		List list = this.getSqlSession().selectList(ns + "findCountByProvice");
		return list;
	}

	/**
	 * 功能描述：根据攻击源IP名查询攻击源
	 * 参数描述：String ip
	 * 返回值    ：  AttackSource
	 */
	public List<AttackSource> findByIp(String ip) {
		return this.getSqlSession().selectList(ns + "findByIp", ip);
		
		
	}

	public void save(AttackSource attackSource) {
		this.getSqlSession().selectOne(ns + "save", attackSource);
	}

}
