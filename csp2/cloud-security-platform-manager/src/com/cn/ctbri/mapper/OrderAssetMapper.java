package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.OrderAsset;

@Component("orderAssetMapper")
@Scope("prototype")
public class OrderAssetMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		OrderAsset orderAsset = new OrderAsset();
		orderAsset.setId(rs.getInt("id"));
		orderAsset.setOrderId(rs.getString("orderId"));
		orderAsset.setAssetId(rs.getInt("assetId"));
		orderAsset.setServiceId(rs.getInt("serviceId"));
		orderAsset.setScan_type(rs.getInt("scan_type"));
		orderAsset.setScan_date(rs.getTimestamp("scan_date"));
		return orderAsset;
	}

}
