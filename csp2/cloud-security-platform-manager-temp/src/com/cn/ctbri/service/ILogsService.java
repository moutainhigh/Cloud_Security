package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

/**
 * 创 建 人  ： tang
 * 创建日期：  2016-8-25
 * 描        述：  logs统计业务层接口
 * 版        本：  1.0
 */
public interface ILogsService {

	List<Map> findLogsTimesLine(Map<String, Object> paramMap);
	
}
