package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Task;

@Component("taskMapper")
@Scope("prototype")
public class TaskMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		Task task = new Task();
		task.setTaskId(rs.getInt("taskId"));
		task.setOrder_asset_Id(rs.getString("order_asset_Id"));
		task.setExecute_time(rs.getTimestamp("execute_time"));
		task.setStatus(rs.getInt("status"));
		task.setGroup_flag(rs.getTimestamp("group_flag"));
		task.setRemarks(rs.getString("remarks"));
		task.setEngineIP(rs.getString("engineIP"));
		task.setTaskProgress(rs.getString("taskProgress"));
		task.setCurrentUrl(rs.getString("currentUrl"));
		task.setBegin_time(rs.getTimestamp("begin_time"));
		task.setEnd_time(rs.getTimestamp("end_time"));
		task.setScanTime(rs.getString("scanTime"));
		task.setIssueCount(rs.getString("issueCount"));
		task.setRequestCount(rs.getString("requestCount"));
		task.setUrlCount(rs.getString("urlCount"));
		task.setAverResponse(rs.getString("averResponse"));
		task.setAverSendCount(rs.getString("averSendCount"));
		task.setSendBytes(rs.getString("sendBytes"));
		task.setReceiveBytes(rs.getString("receiveBytes"));
		task.setWebsoc(rs.getInt("websoc"));
		task.setGroup_id(rs.getString("group_id"));
		task.setEngine(rs.getInt("engine"));
		return task;
	}

}
