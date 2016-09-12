package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.API;
import com.cn.ctbri.entity.APICount;
import com.cn.ctbri.entity.OrderAPI;


/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  用户数据访问层接口类
 * 版        本：  1.0
 */
public interface OrderAPIDao {

	void insert(OrderAPI oAPI);

	void insertOrUpdateCount(APICount count);

	List<OrderAPI> findByParam(Map<String, Object> paramMap);

	/**
	 * 根据订单编号查询调用接口次数
	 * @param paramMap
	 * @return
	 */
	List<API> findAPIByParam(Map<String, Object> paramMap);
	/**
	 * 根据订单编号查询调用所有接口次数
	 * @param paramMap
	 * @return
	 */
	List findAllAPIByParam(Map<String, Object> paramMap);
	/**
	 * 根据用户和订单号查询调用接口历史记录
	 * @param paramMap
	 * @return
	 */
	List findAPIHistoryInfoByParam(Map<String, Object> paramMap);
	
	List<OrderAPI> findUseableByParam(Map<String, Object> paramMap);

	void updateCount(Map<String, Object> param);

	API findUsedByParam(Map<String, Object> paramMap);
	
}
