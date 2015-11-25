package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.TaskHW;

@Component("taskHWMapper")
@Scope("prototype")
public class TaskHWMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		TaskHW taskHW = new TaskHW();
		taskHW.setTaskId(rs.getInt("taskId"));
		taskHW.setOrder_ip_Id(rs.getInt("order_ip_Id"));
		taskHW.setExecute_time(rs.getTimestamp("execute_time"));
		taskHW.setStatus(rs.getInt("status"));
		taskHW.setRemarks(rs.getString("remarks"));
		taskHW.setEnd_time(rs.getTimestamp("end_time"));
		taskHW.setDrainage(rs.getInt("drainage"));
		return taskHW;
	}

}
