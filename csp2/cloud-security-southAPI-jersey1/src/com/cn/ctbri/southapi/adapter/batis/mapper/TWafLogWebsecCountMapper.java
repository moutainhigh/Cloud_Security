package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecCount;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecCountExample;
import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@CacheNamespace
public interface TWafLogWebsecCountMapper {
	@SelectProvider(type=TWafLogWebsecCountSqlProvider.class, method="countByExample")
    int countByExample(TWafLogWebsecCountExample example);


    @SelectProvider(type=TWafLogWebsecCountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="count", property="count"),
        @Result(column="event_type", property="eventType"),
        @Result(column="stat_time", property="statTime")
    })
    List<TWafLogWebsecCount> selectByExample(TWafLogWebsecCountExample example);

}