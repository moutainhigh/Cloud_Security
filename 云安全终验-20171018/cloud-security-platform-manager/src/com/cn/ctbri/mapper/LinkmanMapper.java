package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Linkman;

@Component("linkmanMapper")
@Scope("prototype")
public class LinkmanMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		Linkman linkman = new Linkman();
		linkman.setId(rs.getInt("id"));
		linkman.setName(rs.getString("name"));
		linkman.setMobile(rs.getString("mobile"));
		linkman.setEmail(rs.getString("email"));
		linkman.setCompany(rs.getString("company"));
		linkman.setAddress(rs.getString("address"));
		linkman.setUserId(rs.getInt("userId"));
		return linkman;
	}
	
}
