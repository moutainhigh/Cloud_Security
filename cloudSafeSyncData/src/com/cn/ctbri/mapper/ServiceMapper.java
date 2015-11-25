package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Service;

@Component("serviceMapper")
@Scope("prototype")
public class ServiceMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		Service service = new Service();
		service.setId(rs.getInt("id"));
		service.setName(rs.getString("name"));
		service.setFactory(rs.getString("factory"));
		service.setModule_name(rs.getString("module_name"));
		service.setType(rs.getInt("type"));
		service.setStatus(rs.getInt("status"));
		service.setRemarks(rs.getString("remarks"));
		service.setOrdertype(rs.getInt("ordertype"));
		service.setParentC(rs.getInt("parentC"));
		service.setWebsoc(rs.getInt("websoc"));
		return service;
	}

}
