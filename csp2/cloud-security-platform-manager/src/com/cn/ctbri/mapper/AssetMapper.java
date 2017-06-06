package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Asset;

@Component("assetMapper")
@Scope("prototype")
public class AssetMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Asset asset = new Asset();
		asset.setId(rs.getInt("id"));
		asset.setName(rs.getString("name"));
		asset.setType(rs.getInt("type"));
		asset.setAddr(rs.getString("addr"));
		asset.setStatus(rs.getInt("status"));
		asset.setUserid(rs.getInt("userid"));
		asset.setCreate_date(rs.getTimestamp("create_date"));
		asset.setRemarks(rs.getString("remarks"));
		asset.setIp(rs.getString("ip"));
		asset.setDistrictId(rs.getInt("districtId"));
		asset.setCity(rs.getString("city"));
		asset.setPurpose(rs.getString("purpose"));
		asset.setAssetProvince(rs.getInt("assetProvince"));
		return asset;
	}
}
