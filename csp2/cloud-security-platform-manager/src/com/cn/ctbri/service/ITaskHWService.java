package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.TaskHW;


/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-4-14
 * 描        述：  华为任务业务层接口
 * 版        本：  1.0
 */
public interface ITaskHWService {

    int insert(TaskHW taskhw);

    List<TaskHW> findTaskhw(Map<String, Object> map);

    void update(TaskHW t);

    OrderIP getIpByTaskId(int order_ip_id);

	List<TaskHW> findAlarmbyTaskhw(Map<String, Object> map);




}
