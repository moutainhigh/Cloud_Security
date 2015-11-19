package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.TaskWarn;

@Component("taskWarnMapper")
@Scope("prototype")
public class TaskWarnMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		TaskWarn taskWarn = new TaskWarn();
		taskWarn.setId(rs.getInt("id"));
		taskWarn.setCat1(rs.getString("cat1"));
		taskWarn.setCat2(rs.getString("cat2"));
		taskWarn.setName(rs.getString("name"));
		taskWarn.setSeverity(rs.getInt("severity"));
		taskWarn.setRule(rs.getString("rule"));
		taskWarn.setCt(rs.getInt("ct"));
		taskWarn.setApp_p(rs.getString("app_p"));
		taskWarn.setTran_p(rs.getString("tran_p"));
		taskWarn.setUrl(rs.getString("url"));
		taskWarn.setMsg(rs.getString("msg"));
		taskWarn.setTask_id(rs.getString("task_id"));
		taskWarn.setWarn_time(rs.getTimestamp("warn_time"));
		taskWarn.setServiceId(rs.getInt("serviceId"));
		taskWarn.setGroup_id(rs.getString("group_id"));
		taskWarn.setDistrictId(rs.getInt("districtId"));
		return taskWarn;
	}
}
