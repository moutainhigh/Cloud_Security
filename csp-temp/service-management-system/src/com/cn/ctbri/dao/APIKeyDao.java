package com.cn.ctbri.dao;

import com.cn.ctbri.entity.APIKey;


/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  APIKey数据访问层接口类
 * 版        本：  1.0
 */
public interface APIKeyDao {

	APIKey findByKey(String apiKey);
	
}
