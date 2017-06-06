package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.User;

@Component("userMapper")
@Scope("prototype")
public class UserMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setCreateTime(rs.getTimestamp("createTime"));
		user.setRealName(rs.getString("realName"));
		user.setPassword(rs.getString("password"));
		user.setMobile(rs.getString("mobile"));
		user.setEmail(rs.getString("email"));
		user.setStatus(rs.getInt("status"));
		user.setType(rs.getInt("type"));
		user.setRemarks(rs.getString("remarks"));
		user.setIndustry(rs.getString("industry"));
		user.setJob(rs.getString("job"));
		user.setCompany(rs.getString("company"));
		user.setIp(rs.getString("ip"));
		user.setApikey(rs.getString("apikey"));
		return user;
	}
}
