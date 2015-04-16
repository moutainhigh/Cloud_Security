package com.cn.ctbri.dao.impl;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.AlarmDDOSDao;
import com.cn.ctbri.dao.DaoCommon;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-4-16
 * 描        述：  安恒 报警信息Dao实现类
 * 版        本：  1.0
 */
@Repository
@Transactional
public class AlarmDDOSDaoImpl extends DaoCommon implements AlarmDDOSDao {
	
	/**
	 * 功        能： UserMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.AlarmDDOSMapper.";		
	
}
