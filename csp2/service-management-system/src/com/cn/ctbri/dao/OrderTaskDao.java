package com.cn.ctbri.dao;

import com.cn.ctbri.entity.OrderTask;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-11-10
 * 描        述：  订单任务数据访问层接口类
 * 版        本：  1.0
 */
public interface OrderTaskDao {
	
    /**
     * 功能描述：保存订单
     *       @time 2015-11-10
     */
	void save(OrderTask orderTask);
	
}
