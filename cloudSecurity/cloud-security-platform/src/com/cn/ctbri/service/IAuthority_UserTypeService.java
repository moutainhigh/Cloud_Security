package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.Authority_UserType;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-5
 * 描        述：  权限用户类型业务层接口
 * 版        本：  1.0
 */
public interface IAuthority_UserTypeService {

	//插入新的记录
	void insert(Authority_UserType au);
	//删除记录
	void delete(Authority_UserType au);
	//查询所有
	List<Authority_UserType> findAll();

}
