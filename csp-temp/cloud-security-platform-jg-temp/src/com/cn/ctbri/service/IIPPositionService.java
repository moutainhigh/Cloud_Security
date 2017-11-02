package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.IPPosition;

/**
 * 创 建 人 ： 邓元元 创建日期： 2015-4-16 描 述： 安恒告警业务层接口 版 本： 1.0
 */
public interface IIPPositionService {

	/**
	 * 
	 * 功能描述： 根据ip查询ip对应的地理位置 
	 * 参数描述：
	 * 
	 * @date:2016-8-17上午11:23:26 
	 * 返回值 ： 异 常：
	 */
	IPPosition findIPPositionByIP(String ip);

	/**
	 * 
	 * 功能描述：查询所有ip对应的地理位置信息 
	 * 参数描述：
	 * 
	 * @date:2016-8-17上午11:29:24 
	 * 返回值 ： 
	 * 异 常：
	 */
	Map<String, IPPosition> findIPPositions();
	
	/**
	 * 
	 * 功能描述：保存IPPosition信息
	 * 参数描述：   
	 *@date:2016-8-17上午11:30:55
	 * 返回值    ：  
	 * 异        常：
	 */
	void saveIPPosition(IPPosition ipPosition);
	/**
	 * 功能描述：删除ip
	 * @author hansheng
	 * @param ipPosition
	 * @return
	 */
	int delete(IPPosition ipPosition);

}
