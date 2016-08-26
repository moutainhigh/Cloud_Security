package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.APIDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.LogsDao;

/**
 * 创 建 人  ：  tang
 * 创建日期：  2016-8-25
 * 描        述：  logs统计实现类
 * 版        本：  1.0
 */
@Repository
public class LogsDaoImpl extends DaoCommon implements LogsDao {
	private String ns = "com.cn.ctbri.entity.LogsMapper.";

	public List<Map> findLogsTimesLine(Map<String, Object> paramMap) {
		return getSqlSession().selectList(ns + "findLogsTimesLine",paramMap);
	}
	
	
}
