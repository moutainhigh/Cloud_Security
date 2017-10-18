package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.OrderIP;

@Component("orderIPMapper")
@Scope("prototype")
public class OrderIPMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		OrderIP orderIP = new OrderIP();
		orderIP.setId(rs.getInt("id"));
		orderIP.setOrderId(rs.getString("orderId"));
		orderIP.setIp(rs.getString("ip"));
		orderIP.setBandwidth(rs.getInt("bandwidth"));
		orderIP.setServiceId(rs.getInt("serviceId"));
		return orderIP;
	}

}
