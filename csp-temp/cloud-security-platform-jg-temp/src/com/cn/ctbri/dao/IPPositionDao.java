package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.IPPosition;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;

/**
 * 
			 * @author ： LvCongLiang
			 * @date：  2016-8-17上午11:06:59
			 * 修 改 人  ：  
			 * 修改日期：  
			 * @description： 存储查询到的IP经纬度已经国家省信息
			 * 版        本： 1.0
 */
public interface IPPositionDao {
	/**
	 * 
	 * 功能描述： 保存ip对应的经纬度信息
	 * 参数描述：   
	 *@date:2016-8-17上午11:08:30
	 * 返回值    ：  
	 * 异        常：
	 */
	void saveAsset(IPPosition ipPosition);
	
	/**
	 * 
	 * 功能描述： 查询所有ip对应的位置信息
	 * 参数描述：   
	 *@date:2016-8-17下午1:52:11
	 * 返回值    ：  
	 * 异        常：
	 */
	List<IPPosition> getIPPositions();
	
	/**
	 * 
	 * 功能描述：根据IP查询对应的位置信息
	 * 参数描述：   
	 *@date:2016-8-17下午1:53:23
	 * 返回值    ：  
	 * 异        常：
	 */
	IPPosition findIPPositionByIP(String ip);
	/**
	 * 功能描述：删除废弃ip
	 * @param ipPosition
	 * @return
	 */
	int deleteIP(IPPosition ipPosition);
	
	
}
