package com.sinosoft.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component("alarmMapper")
@Scope("prototype")
public class CsAlarmMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CsAlarm csAlarm = new CsAlarm();
		csAlarm.setId(rs.getInt("id"));
		csAlarm.setName(rs.getString("name"));
		csAlarm.setAlarmTime(rs.getDate("alarm_time"));
		csAlarm.setScore(rs.getString("score"));
		csAlarm.setLevel(rs.getInt("level"));
		csAlarm.setAdvice(rs.getString("advice"));
		csAlarm.setAlarmContent(rs.getString("alarm_content"));
		csAlarm.setUrl(rs.getString("url"));
		csAlarm.setKeyword(rs.getString("keyword"));
		csAlarm.setTaskid(rs.getInt("taskid"));
		csAlarm.setAlarmType(rs.getString("alarm_type"));
		csAlarm.setUserid(rs.getInt("userid"));
		csAlarm.setGroupId(rs.getString("group_id"));
		csAlarm.setServiceid(rs.getInt("serviceid"));
		csAlarm.setDistrictid(rs.getInt("districtid"));
		return csAlarm;
	}

}
