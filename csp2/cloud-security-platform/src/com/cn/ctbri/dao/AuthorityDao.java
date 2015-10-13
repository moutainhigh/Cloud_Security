package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.Authority;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-5
 * 描        述：  权限数据访问层接口类
 * 版        本：  1.0
 */
public interface AuthorityDao {

	/**
	 * 根据用户类型，获取用户的所拥有的权限
	 * @param type
	 * @return
	 */
	List<String> findUrlByUserType(int type);
	/**
	 * 查询权限列表
	 * @param type
	 * @return
	 */
	List<Authority> findAllAuthority();
	/**
	 * 保存权限
	 */
	void saveAuthority(Authority authority);

	
}
