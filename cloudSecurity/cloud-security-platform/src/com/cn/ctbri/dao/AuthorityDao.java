package com.cn.ctbri.dao;

import java.util.List;

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
	List findUrlByUserType(int type);

	
}
