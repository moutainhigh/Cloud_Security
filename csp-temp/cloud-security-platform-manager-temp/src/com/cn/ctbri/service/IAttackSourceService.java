package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.AttackSource;

/**
 * 创 建 人  ：  张少华
 * 创建日期：  2016-8-18
 * 描        述：  攻击源业务层接口
 * 版        本：  1.0
 */
public interface IAttackSourceService {
	/**
	 * 功能描述：根据攻击源IP名查询攻击源
	 * 参数描述：String ip
	 * 返回值    ：  AttackSource
	 */
	AttackSource findByIp(String ip);
	
	/**
	 * 功能描述：查询每个省份的攻击源数量
	 * 参数描述：
	 * 返回值    ：  List
	 */
	List findCountByProvice();
	
	void save(AttackSource attackSource);
}
