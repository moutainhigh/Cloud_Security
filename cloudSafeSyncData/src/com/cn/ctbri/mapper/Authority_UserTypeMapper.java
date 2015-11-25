package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Authority_UserType;

@Component("authority_UserTypeMapper")
@Scope("prototype")
public class Authority_UserTypeMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		Authority_UserType au = new Authority_UserType();
		au.setUserType(rs.getInt("userType"));
		au.setAuthorityId(rs.getInt("authorityId"));
		return au;
	}

}
