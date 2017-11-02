package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.District;

@Component("districtMapper")
@Scope("prototype")
public class DistrictMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		District district = new District();
		district.setId(rs.getInt("id"));
		district.setName(rs.getString("name"));
		district.setLongitude(rs.getString("longitude"));
		district.setLatitude(rs.getString("latitude"));
		district.setCount1(rs.getInt("count1"));
		district.setCount2(rs.getInt("count2"));
		district.setCount3(rs.getInt("count3"));
		district.setCount4(rs.getInt("count4"));
		district.setCount5(rs.getInt("count5"));
//		district.setCount(rs.getString("count"));
		district.setSiteCount(rs.getInt("siteCount"));
		district.setWafAlarmCount(rs.getInt("wafAlarmCount"));
		return district;
	}

}
