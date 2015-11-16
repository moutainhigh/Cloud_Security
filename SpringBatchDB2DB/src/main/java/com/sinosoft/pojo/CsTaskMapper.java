package com.sinosoft.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component("taskMapper")
@Scope("prototype")
public class CsTaskMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CsTask csTask = new CsTask();
		csTask.setTaskid(rs.getInt("taskId"));
		csTask.setOrderAssetId(rs.getString("order_asset_Id"));
		csTask.setExecuteTime(rs.getDate("execute_time"));
		csTask.setStatus(rs.getBoolean("status"));
		csTask.setGroupFlag(rs.getDate("group_flag"));
		csTask.setRemarks(rs.getString("remarks"));
		csTask.setEngineip(rs.getString("engineIP"));
		csTask.setTaskprogress(rs.getString("taskProgress"));
		csTask.setCurrenturl(rs.getString("currentUrl"));
		csTask.setBeginTime(rs.getDate("begin_time"));
		csTask.setEndTime(rs.getDate("end_time"));
		csTask.setScantime(rs.getString("scanTime"));
		csTask.setIssuecount(rs.getString("issueCount"));
		csTask.setRequestcount(rs.getString("requestCount"));
		csTask.setUrlcount(rs.getString("urlCount"));
		csTask.setAverresponse(rs.getString("averResponse"));
		csTask.setAversendcount(rs.getString("averSendCount"));
		csTask.setSendbytes(rs.getString("sendBytes"));
		csTask.setReceivebytes(rs.getString("receiveBytes"));
		csTask.setWebsoc(rs.getInt("websoc"));
		csTask.setGroupId(rs.getString("group_id"));
		csTask.setEngine(rs.getInt("engine"));
		return csTask;
	}

}
