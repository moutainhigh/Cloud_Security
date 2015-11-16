package com.sinosoft.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component("serviceMapper")
@Scope("prototype")
public class CsServiceMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CsService csService = new CsService();
		csService.setId(rs.getInt("id"));
		csService.setName(rs.getString("name"));
		csService.setFactory(rs.getString("factory"));
		csService.setModuleName(rs.getString("module_name"));
		csService.setType(rs.getInt("type"));
		csService.setStatus(rs.getInt("status"));
		csService.setRemarks(rs.getString("remarks"));
		csService.setOrdertype(rs.getInt("ordertype"));
		csService.setParentc(rs.getInt("parentC"));
		csService.setWebsoc(rs.getInt("websoc"));
		return csService;
	}

}
