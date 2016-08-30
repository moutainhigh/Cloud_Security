package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

/**
 * 创 建 人  ：  zx
 * 创建日期：  2015-11-02
 * 描        述：  API统计接口
 * 版        本：  1.0
 */
public interface APIDao {
	//查询服务类型操作类型个数
	List getAPICount(Map<String,Object> map);
	//查询所有服最近7日
	List getAPICountLast7Days();
	//查询开发者调用次数top5
	List getAPIUserCountTop5(Map<String,Object> map);
	//查询开发者调用次数列表
	List getAllAPIUserList(Map<String,Object> map);
	//根据开发这查询使用服务次数
	List getAPICountByUser(Map<String,Object> map);
	//查询API服务使用时段
	List getAPIUseTimes(Map<String,Object> map);
	//用户使用API时段统计
	List getAPITimesByUser(Map<String,Object> map);
	//API累计调用数
	int getAPIUseCount();
	//API接入总数
	int getAllAPICount();
}
