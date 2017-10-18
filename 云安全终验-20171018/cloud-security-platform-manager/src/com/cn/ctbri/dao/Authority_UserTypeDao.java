package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.Authority_UserType;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-5
 * 描        述：  权限和用户类型DAO接口
 * 版        本：  1.0
 */
public interface Authority_UserTypeDao {

	void insert(Authority_UserType au);

	void delete(Authority_UserType au);

	List<Authority_UserType> findAll();

}
