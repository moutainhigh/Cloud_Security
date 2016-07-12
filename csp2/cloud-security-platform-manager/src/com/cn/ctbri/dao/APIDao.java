package com.cn.ctbri.dao;

import java.util.List;

/**
 * 创 建 人  ：  zx
 * 创建日期：  2015-11-02
 * 描        述：  API统计接口
 * 版        本：  1.0
 */
public interface APIDao {
	//查询服务类型操作类型个数
	List getAPICount(int serviceType);
}
