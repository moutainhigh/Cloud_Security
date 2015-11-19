package com.cn.ctbri.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Notice;

@Component("noticeMapper")
@Scope("prototype")
public class NoticeMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int num) throws SQLException {
		Notice notice = new Notice();
		notice.setId(rs.getInt("id"));
		notice.setNoticeName(rs.getString("noticeName"));
		notice.setNoticeDesc(rs.getString("noticeDesc"));
		notice.setCreateDate(rs.getTimestamp("createDate"));
		return notice;
	}

}
