package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.LoginHistory;
import com.cn.ctbri.entity.OrderAPI;

@Component("loginHistoryMapper")
@Scope("prototype")
public class loginHistoryMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoginHistory loginHistory = new LoginHistory();
		
		loginHistory.setId(rs.getInt("id"));
		loginHistory.setUserId(rs.getInt("userId"));
		loginHistory.setLoginTime(rs.getTimestamp("loginTime"));
		loginHistory.setUserType(rs.getInt("userType"));
		loginHistory.setIPAddr(rs.getString("IPAddr"));
		
		return loginHistory;
	}

}
