package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.APIKey;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  APIKey数据访问层接口类
 * 版        本：  1.0
 */
public interface APIKeyDao {

	APIKey findByKey(String apiKey);
	
}
