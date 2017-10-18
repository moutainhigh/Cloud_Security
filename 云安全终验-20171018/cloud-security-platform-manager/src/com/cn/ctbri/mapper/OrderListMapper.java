package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.OrderList;

@Component("orderListMapper")
@Scope("prototype")
public class OrderListMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderList orderList = new OrderList();
		
		orderList.setId(rs.getString("id"));
		orderList.setCreate_date(rs.getTimestamp("create_date"));
		orderList.setUserId(rs.getInt("userId"));
		orderList.setContactId(rs.getInt("contactId"));
		orderList.setRemarks(rs.getString("remarks"));
		orderList.setOrderId(rs.getString("orderId"));
		orderList.setPrice(rs.getInt("price"));
		orderList.setPay_date(rs.getTimestamp("pay_date"));
		orderList.setServerName(rs.getString("serverNames"));
		orderList.setBalanceFlag(rs.getInt("balanceFlag"));
		return orderList;
	}

}
