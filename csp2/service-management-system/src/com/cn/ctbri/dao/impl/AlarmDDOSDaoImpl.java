package com.cn.ctbri.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.AlarmDDOSDao;
import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.entity.AlarmDDOS;
/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-07
 * 描        述：  报警信息Dao接口实现类
 * 版        本：  1.0
 */

//原来华为的接口实现类   现在不用啦  2017-05-09

@Repository
@Transactional
public class AlarmDDOSDaoImpl extends DaoCommon implements AlarmDDOSDao {
	
	/**
	 * 功        能： UserMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.AlarmDDOSMapper.";		
	/**
	 * 功能描述：插入报警日志
	 * 参数描述：AlarmDDOS alarmDDOS 报警日志对象
	 *		 @time 2015-01-07
	 * 返回值    ：  long 若成功主键ID，若不成功返回-1
	 */
	public long insert(AlarmDDOS alarmDDOS) {
		return getSqlSession().insert(ns+"insert", alarmDDOS);
	}
    public void update(AlarmDDOS ddos) {
        getSqlSession().update(ns+"update", ddos);
    }
	
}
