package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.Alarm;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-30
 * 描        述：  告警业务层接口
 * 版        本：  1.0
 */
public interface IAlarmService {

	/**
	 * 功能描述：根据用户id查询告警信息
	 * 参数描述：int id
	 *       @time 2015-1-30
	 * 返回值    ：List<Alarm>
	 */
	List<Alarm> findAlarmByUserId(int id);

}
