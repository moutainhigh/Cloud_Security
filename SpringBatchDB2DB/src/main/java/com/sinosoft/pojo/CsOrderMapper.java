package com.sinosoft.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component("orderMapper")
@Scope("prototype")
public class CsOrderMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CsOrder csOrder = new CsOrder();
		csOrder.setId(rs.getString("id"));
		csOrder.setType(rs.getInt("type"));
		csOrder.setBeginDate(rs.getDate("begin_date"));
		csOrder.setEndDate(rs.getDate("end_date"));
		csOrder.setServiceid(rs.getInt("serviceId"));
		csOrder.setCreateDate(rs.getDate("create_date"));
		csOrder.setTaskDate(rs.getDate("task_date"));
		csOrder.setScanType(rs.getInt("scan_type"));
		csOrder.setUserid(rs.getInt("userId"));
		csOrder.setContactid(rs.getInt("contactId"));
		csOrder.setRemarks(rs.getString("remarks"));
		csOrder.setStatus(rs.getInt("status"));
		csOrder.setMessage(rs.getInt("message"));
		csOrder.setWebsoc(rs.getInt("websoc"));
		return csOrder;
	}
}
