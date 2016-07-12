package com.cn.ctbri.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.APIDao;
import com.cn.ctbri.dao.DaoCommon;

/**
 * 创 建 人  ：  zx
 * 创建日期：  2015-11-02
 * 描        述：  API统计实现类
 * 版        本：  1.0
 */
@Repository
public class APIDaoImpl extends DaoCommon implements APIDao {
	private String ns = "com.cn.ctbri.entity.APIMapper.";
	
	public List getAPICount(Map<String,Object> map) {
		return this.getSqlSession().selectList(ns+"getAPICount", map);
	}

}
