package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.entity.Task;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-11-10
 * 描        述：  订单任务业务层接口
 * 版        本：  1.0
 */
public interface IOrderTaskService {
	
    /**
     * 功能描述：保存订单
     *       @time 2015-11-10
     */
    void insertOrderTask(OrderTask orderTask);
}
