package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.dao.AuthorityDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Authority;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAuthorityService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-5
 * 描        述： 权限业务层实现类
 * 版        本：  1.0
 */
@Service
public class AuthorityServiceImpl implements IAuthorityService{

	@Autowired
	AuthorityDao authorityDao;

	/**
	 * 根据用户类型，获取用户的所拥有的权限
	 * @param type
	 * @return
	 */
	public List<String> findUrlByUserType(int type) {
		return authorityDao.findUrlByUserType(type);
	}
	/**
	 * 查询权限列表
	 * @param type
	 * @return
	 */
	public List<Authority> findAllAuthority() {
		return authorityDao.findAllAuthority();
	}
	/**
	 * 保存权限
	 */
	public void saveAuthority(Authority authority) {
		authorityDao.saveAuthority(authority);
	}
	
}
