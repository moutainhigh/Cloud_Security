package com.cn.ctbri.dao.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.FinancialDao;
import com.cn.ctbri.entity.Order;

/**
 * 创 建 人  ：  houtp
 * 创建日期：  2016-09-02
 * 描        述：  后台财务统计分析
 * 版        本：  1.0
 */
@Repository
public class FinancialDaoImpl extends DaoCommon implements FinancialDao {
	
	/**
	 * 功        能： OrderMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.OrderMapper.";
	
    /**
     * 功能描述:当天订单交易额总数
     * 参数描述：globle_user
     * @time 2016-09-06
     * 
     */
	public List<Map> countOrderAmount() {
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH)+1; 
		int date = c.get(Calendar.DATE); 
		//int hour = c.get(Calendar.HOUR_OF_DAY); 
		//int minute = c.get(Calendar.MINUTE); 
		//int second = c.get(Calendar.SECOND); 
		
		Map<String, Object> hashmap = new HashMap<String, Object>();
		String beginDate = year +"-" + month + "-" + date + " 00:00:00";
		String endDate = year +"-" + month + "-" + date + " 23:59:59";
		
//		String beginDate ="2016-08-05 00:00:00";
//		String endDate ="2016-08-05 23:59:59";
		
		hashmap.put("beginDate", beginDate);
		hashmap.put("endDate", endDate);
		List<Map> orderAmount = this.getSqlSession().selectList(ns + "countOrderAmount",hashmap);
		return orderAmount;
	}

    /**
     * 功能描述:各类服务订单交易额总数
     * 参数描述：userId
     * @time 2016-09-06
     */
	public List<Order> findServiceAmount() {
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH)+1; 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
	    int second = c.get(Calendar.SECOND); 
		Map<String, Object> hashmap = new HashMap<String, Object>();
		String beginDate = year +"-" + month + "-" + date + " 00:00:00";
		String endDate = year +"-" + month + "-" + date + " 23:59:59";
//		String beginDate ="2016-08-05 00:00:00";//2015-08-06 15:03:38
//		String endDate ="2016-08-05 23:59:59";
		hashmap.put("beginDate", beginDate);
		hashmap.put("endDate", endDate);
		return this.getSqlSession().selectList(ns+"findServiceAmount", hashmap);
	}

    /**
     * 功能描述:最近6小时交易额变化曲线
     * 参数描述：userId
     * @time 2016-09-06
     */
	public List<Order> findTradingCurve() {
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH)+1; 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
	    int second = c.get(Calendar.SECOND); 
	    
		Map<String, Object> hashmap = new HashMap<String, Object>();
		String beginDate = year +"-" + month + "-" + date + " " + (hour-6) +":" + minute + ":" + second;
		String endDate = year +"-" + month + "-" + date + " " + hour +":" + minute + ":" + second;
//		String beginDate ="2016-08-06 09:03:38";
//		String endDate ="2016-08-06 15:03:38";
		hashmap.put("beginDate", beginDate);
		hashmap.put("endDate", endDate);
		return this.getSqlSession().selectList(ns+"findTradingCurve",hashmap);
	}

	 /**
     * 功能描述:当日内各类服务订单交易额占比情况
     * @time 2016-09-07
     */
	public List<Order> findOrderAmountDayPie(String createDate) {
		String beginDate = createDate + " 00:00:00";
		String endDate = createDate + " 23:59:59";
		Map<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("beginDate", beginDate);
		hashmap.put("endDate", endDate);
		return this.getSqlSession().selectList(ns+"findOrderAmountDayPie",hashmap);
	}

	 /**
     * 功能描述:当月内各类服务订单交易额占比情况
     * @time 2016-09-07
     */
	public List<Order> findOrderAmountMonthPie(String createDate) {
		return this.getSqlSession().selectList(ns+"findOrderAmountMonthPie",createDate);
	}

	 /**
     * 功能描述：当日交易额总数
     * @time 2016-09-07
     */
	public List<Order> findOrderAmounDayLine(Map<String, Object> hashmap) {
		 return this.getSqlSession().selectList(ns+"findOrderAmounDayLine",hashmap);
	}

	 /**
     * 功能描述：当月交易额总数
     * @time 2016-09-07
     */
	public List<Order> findOrderAmountMonthLine(Map<String, Object> hashmap) {
		 return this.getSqlSession().selectList(ns+"findOrderAmountMonthLine",hashmap);
	}

}
