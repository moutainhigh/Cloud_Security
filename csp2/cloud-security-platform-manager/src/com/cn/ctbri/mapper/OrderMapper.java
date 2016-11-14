package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Order;

@Component("orderMapper")
@Scope("prototype")
public class OrderMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = new Order();
		
		order.setId(rs.getString("id"));
		order.setType(rs.getInt("type"));
		order.setBegin_date(rs.getTimestamp("begin_date"));
		order.setEnd_date(rs.getTimestamp("end_date"));
		order.setServiceId(rs.getInt("serviceId"));
		order.setCreate_date(rs.getTimestamp("create_date"));
		order.setTask_date(rs.getTimestamp("task_date"));
		order.setScan_type(rs.getInt("scan_type"));
		order.setUserId(rs.getInt("userId"));
		order.setContactId(rs.getInt("contactId"));
		order.setRemarks(rs.getString("remarks"));
		order.setStatus(rs.getInt("status"));
		order.setMessage(rs.getInt("message"));
		order.setWebsoc(rs.getInt("websoc"));
		order.setTasknum(rs.getInt("tasknum"));
		order.setDelFlag(rs.getInt("delFlag"));
		order.setPayFlag(rs.getInt("payFlag"));
		order.setPrice(rs.getBigDecimal("price"));
		order.setIsAPI(rs.getInt("isAPI"));
		return order;
	}

}
