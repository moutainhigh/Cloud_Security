package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.dao.Authority_UserTypeDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Authority_UserType;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAuthority_UserTypeService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-5
 * 描        述：  权限和用户类型业务层实现类
 * 版        本：  1.0
 */
@Service
public class Authority_UserTypeServiceImpl implements IAuthority_UserTypeService{

	@Autowired
	Authority_UserTypeDao authority_UserTypeDao;

	public void insert(Authority_UserType au) {
		authority_UserTypeDao.insert(au);
	}

	public void delete(Authority_UserType au) {
		authority_UserTypeDao.delete(au);
		
	}

	public List<Authority_UserType> findAll() {
		return authority_UserTypeDao.findAll();
	}
	
}
