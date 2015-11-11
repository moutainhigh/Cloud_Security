package com.cn.ctbri.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.OrderTaskDao;
import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.service.IOrderTaskService;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-11-10
 * 描        述：  订单任务业务层实现类
 * 版        本：  1.0
 */
@Service
public class OrderTaskServiceImpl implements IOrderTaskService{
	@Autowired
	OrderTaskDao orderTaskDao;
    /**
     * 功能描述：保存订单
     *       @time 2015-11-10
     */
	public void insertOrderTask(OrderTask orderTask) {
		orderTaskDao.save(orderTask);
	}
}
