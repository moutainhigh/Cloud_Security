package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Alarm;
import com.sun.rowset.internal.Row;

@Component("alarmMapper")
@Scope("prototype")
public class AlarmMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Alarm alarm = new Alarm();
		alarm.setId(rs.getInt("id"));
		alarm.setName(rs.getString("name"));
		alarm.setAlarm_time(rs.getTimestamp("alarm_time"));
		alarm.setScore(rs.getString("score"));
		alarm.setLevel(rs.getInt("level"));
		alarm.setAdvice(rs.getString("advice"));
		alarm.setAlarm_content(rs.getString("alarm_content"));
		alarm.setUrl(rs.getString("url"));
		alarm.setTaskId(rs.getLong("taskId"));
		alarm.setUserId(rs.getInt("userId"));
		alarm.setKeyword(rs.getString("keyword"));
		alarm.setAlarm_type(rs.getString("alarm_type"));
		alarm.setGroup_id(rs.getString("group_id"));
		alarm.setServiceId(rs.getInt("serviceId"));
		alarm.setDistrictId(rs.getInt("districtId"));
		return alarm;
	} 
}
