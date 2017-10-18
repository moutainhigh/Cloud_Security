package com.cn.ctbri.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.FinancialDao;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.FinancialService;

/**
 * 创 建 人  ：  houtp
 * 创建日期：  2016-09-02
 * 描        述：  后台财务统计分析
 * 版        本：  1.0
 */
@Service
public class FinancialServiceImpl implements FinancialService{

	@Autowired
	FinancialDao financialDao;
	

    /**
     * 功能描述:当天订单交易额总数
     * 参数描述：userId
     * @time 2016-09-06
     * 
     */
	public List<Map> countOrderAmount() {
		return financialDao.countOrderAmount();
	}


    /**
     * 功能描述:各类服务订单交易额总数
     * 参数描述：userId
     * @time 2016-09-06
     */
	public List<Order> findServiceAmount() {
		return financialDao.findServiceAmount();
	}


    /**
     * 功能描述:最近6小时交易额变化曲线
     * 参数描述：userId
     * @time 2016-09-06
     */
	public List<Order> findTradingCurve() {
		return financialDao.findTradingCurve();
	}


	 /**
     * 功能描述:当日内各类服务订单交易额占比情况
     * @time 2016-09-07
     */
	public List<Order> findOrderAmountDayPie(String createDate) {
		return financialDao.findOrderAmountDayPie(createDate);
	}

	 /**
     * 功能描述:当月内各类服务订单交易额占比情况
     * @time 2016-09-07
     */
	public List<Order> findOrderAmountMonthPie(String createDate) {
		return financialDao.findOrderAmountMonthPie(createDate);
	}


	 /**
     * 功能描述：当日交易额总数
     * @time 2016-09-07
     */
	public List<Order> findOrderAmounDayLine(Map<String, Object> paramMap) {
		return financialDao.findOrderAmounDayLine(paramMap);
	}


	 /**
     * 功能描述：当月交易额总数
     * @time 2016-09-07
     */
	public List<Order> findOrderAmountMonthLine(Map<String, Object> paramMap) {
		return financialDao.findOrderAmountMonthLine(paramMap);
	}

    
}
