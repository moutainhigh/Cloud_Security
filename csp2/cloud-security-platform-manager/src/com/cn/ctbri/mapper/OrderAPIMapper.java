package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.OrderAPI;

@Component("orderApiMapper")
@Scope("prototype")
public class OrderAPIMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderAPI orderApi = new OrderAPI();
		
		orderApi.setId(rs.getString("id"));
		orderApi.setBegin_date(rs.getTimestamp("begin_date"));
		orderApi.setEnd_date(rs.getTimestamp("end_date"));
		orderApi.setApiId(rs.getInt("apiId"));
		orderApi.setCreate_date(rs.getTimestamp("create_date"));
		orderApi.setPackage_type(rs.getInt("package_type"));
		orderApi.setNum(rs.getInt("num"));
		orderApi.setUserId(rs.getInt("userId"));
		orderApi.setContactId(rs.getInt("contactId"));
		orderApi.setRemarks(rs.getString("remarks"));
		orderApi.setMessage(rs.getInt("message"));
		orderApi.setPayFlag(rs.getInt("payFlag"));
		orderApi.setBuyNum(rs.getInt("buynum"));
		return orderApi;
	}

}
