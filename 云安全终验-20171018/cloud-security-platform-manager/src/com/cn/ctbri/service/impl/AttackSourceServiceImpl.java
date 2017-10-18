package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.AttackSourceDao;
import com.cn.ctbri.entity.AttackSource;
import com.cn.ctbri.service.IAttackSourceService;

@Service
public class AttackSourceServiceImpl implements IAttackSourceService {
	
	@Autowired
	AttackSourceDao attackSourceDao;

	public List findCountByProvice() {
		return attackSourceDao.findCountByProvice();
	}

	public List<AttackSource> findByIp(String ip) {
		return attackSourceDao.findByIp(ip);
	}

	public void save(AttackSource attackSource) {
		attackSourceDao.save(attackSource);
		
	}

}
