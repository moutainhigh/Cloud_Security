package com.cn.ctbri.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.ServDao;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.service.IServService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-15
 * 描        述： 服务类业务层实现类
 * 版        本：  1.0
 */
@Service
public class ServServiceImpl implements IServService{
	@Autowired
	ServDao servDao;
	/**
	 * 功能描述： 根据id查询服务
	 * 参数描述： int serviceid
	 *		 @time 2015-1-15
	 *	返回值 ：Serv
	 */
	public Serv findById(int serviceid) {
		Serv serv = servDao.findById(serviceid);
		return serv;
	}
	
}
