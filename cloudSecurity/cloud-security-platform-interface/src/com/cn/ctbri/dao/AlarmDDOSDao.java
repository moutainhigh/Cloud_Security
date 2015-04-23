package com.cn.ctbri.dao;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;

/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-07
 * 描        述：  报警信息Dao接口
 * 版        本：  1.0
 */
public interface AlarmDDOSDao {
	/**
	 * 功能描述：插入报警日志
	 * 参数描述：Alarm alarm 报警日志对象
	 *		 @time 2015-01-07
	 * 返回值    ：  long 若成功主键ID，若不成功返回-1
	 */
	public long insert(AlarmDDOS alarmDDOS);

    public void update(AlarmDDOS ddos);
	
}
