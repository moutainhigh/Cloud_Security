package com.cn.ctbri.service;

import java.util.List;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-5
 * 描        述： 权限业务层接口
 * 版        本：  1.0
 */
public interface IAuthorityService {

	/**
	 * 根据用户类型，获取用户的所拥有的权限
	 * @param type
	 * @return
	 */
	List findUrlByUserType(int type);

}
