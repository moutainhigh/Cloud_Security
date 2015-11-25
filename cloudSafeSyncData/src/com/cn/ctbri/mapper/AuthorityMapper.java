package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Authority;

@Component("authorityMapper")
@Scope("prototype")
public class AuthorityMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		Authority authority = new Authority();
		authority.setId(rs.getInt("id"));
		authority.setAuthorityName(rs.getString("authorityName"));
		authority.setUrl(rs.getString("url"));
		authority.setState(rs.getInt("state"));
		authority.setRemark(rs.getString("remark"));
		return authority;
	}

}
