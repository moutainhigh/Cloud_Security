package com.sinosoft.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component("userMapper")
@Scope("prototype")
public class CsUserMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CsUser csUser = new CsUser();
		csUser.setId(rs.getInt("id"));
		csUser.setName(rs.getString("name"));
		csUser.setPassword(rs.getString("password"));
		csUser.setMobile(rs.getString("mobile"));
		csUser.setEmail(rs.getString("email"));
		csUser.setStatus(rs.getBoolean("status"));
		csUser.setType(rs.getBoolean("type"));
		csUser.setRemarks(rs.getString("remarks"));
		csUser.setCreatetime(rs.getDate("createTime"));
		csUser.setRealname(rs.getString("realName"));
		csUser.setIp(rs.getString("ip"));
		return csUser;
	}

}
