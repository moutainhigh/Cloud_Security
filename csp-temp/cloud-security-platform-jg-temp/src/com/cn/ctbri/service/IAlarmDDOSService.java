package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.AlarmDDOS;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-4-16
 * 描        述：  安恒告警业务层接口
 * 版        本：  1.0
 */
public interface IAlarmDDOSService {

	List<AlarmDDOS> findAlarmDDOSByOrderId(String orderId);

}
