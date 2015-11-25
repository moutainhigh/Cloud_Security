package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.MobileInfo;

@Component("mobileInfoMapper")
@Scope("prototype")
public class MobileInfoMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		MobileInfo mobileInfo = new MobileInfo();
		mobileInfo.setMobileNumber(rs.getString("MobileNumber"));
		mobileInfo.setTimes(rs.getInt("Times"));
		mobileInfo.setSendDate(rs.getString("sendDate"));
		return mobileInfo;
	}
}
