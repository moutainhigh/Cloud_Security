package com.cn.ctbri.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.EngineCfg;

@Component("engineCfgMapper")
@Scope("prototype")
public class EngineCfgMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		EngineCfg engineCfg = new EngineCfg();
		engineCfg.setId(rs.getInt("id"));
		engineCfg.setEngine_number(rs.getString("engine_number"));
		engineCfg.setEngine_name(rs.getString("engine_name"));
		engineCfg.setEquipment_factory(rs.getString("equipment_factory"));
		engineCfg.setEngine_addr(rs.getString("engine_addr"));
		engineCfg.setEngine_api(rs.getString("engine_api"));
		engineCfg.setUsername(rs.getString("username"));
		engineCfg.setPassword(rs.getString("password"));
		engineCfg.setEngine_capacity(rs.getString("engine_capacity"));
		engineCfg.setEngine_capacity_model(rs.getString("engine_capacity_model"));
		engineCfg.setMaxTask(rs.getInt("maxTask"));
		engineCfg.setActivity(rs.getInt("activity"));
		engineCfg.setStatus(rs.getInt("status"));
		engineCfg.setEngine(rs.getInt("engine"));
		return engineCfg;
	}

}
