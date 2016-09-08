package com.cn.ctbri.dao;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Order;


/**
 * 创 建 人  ：  houtp
 * 创建日期：  2016-09-02
 * 描        述：  后台财务统计分析
 * 版        本：  1.0
 */
public interface FinancialDao {

    /**
     * 功能描述:当天订单交易额总数
     * 参数描述：userId
     * @time 2016-09-06
     * 
     */
	List<Map> countOrderAmount();

    /**
     * 功能描述:各类服务订单交易额总数
     * 参数描述：userId
     * @time 2016-09-06
     */
	List<Order> findServiceAmount();

    /**
     * 功能描述:最近6小时交易额变化曲线
     * 参数描述：userId
     * @time 2016-09-06
     */
	List<Order> findTradingCurve();

	 /**
     * 功能描述:当日内各类服务订单交易额占比情况
     * @time 2016-09-07
     */
	List<Order> findOrderAmountDayPie(String createDate);

	 /**
     * 功能描述:当月内各类服务订单交易额占比情况
     * @time 2016-09-07
     */
	List<Order> findOrderAmountMonthPie(String createDate);

	 /**
     * 功能描述：当日交易额总数
     * @time 2016-09-07
     */
	List<Order> findOrderAmounDayLine(Map<String, Object> paramMap);

	 /**
     * 功能描述：当月交易额总数
     * @time 2016-09-07
     */
	List<Order> findOrderAmountMonthLine(Map<String, Object> paramMap);

}
