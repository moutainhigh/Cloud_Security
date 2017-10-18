package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.AlarmDDOS;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-4-16
 * 描        述：  安恒 报警信息Dao接口
 * 版        本：  1.0
 */
public interface AlarmDDOSDao {

	List<AlarmDDOS> findAlarmDDOSByOrderId(String orderId);

}
