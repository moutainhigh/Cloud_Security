package com.cn.ctbri.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.AuthorityDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.Authority;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-02-05
 * 描        述：  权限Dao接口实现类
 * 版        本：  1.0
 */
@Repository
@Transactional
public class AuthorityDaoImpl extends DaoCommon implements AuthorityDao {
	
	/**
	 * 功        能： AuthorityMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.AuthorityMapper.";
	/**
	 * 根据用户类型，获取用户的所拥有的权限
	 * @param type
	 * @return
	 */
	public List<String> findUrlByUserType(int type) {
		return getSqlSession().selectList(ns+"findUrlByUserType", type);
	}
	/**
	 * 查询权限列表
	 * @param type
	 * @return
	 */
	public List<Authority> findAllAuthority() {
		return getSqlSession().selectList(ns+"findAllAuthority");
	}
	/**
	 * 保存权限
	 */
	public void saveAuthority(Authority authority) {
		getSqlSession().insert(ns+"saveAuthority",authority);
	}		
}
