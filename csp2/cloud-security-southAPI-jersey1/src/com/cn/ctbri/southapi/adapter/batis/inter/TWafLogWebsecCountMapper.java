package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecCount;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecCountExample;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface TWafLogWebsecCountMapper {
    @SelectProvider(type=TWafLogWebsecCountSqlProvider.class, method="countByExample")
    int countByExample(TWafLogWebsecCountExample example);


    @SelectProvider(type=TWafLogWebsecCountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="count", property="count", jdbcType=JdbcType.BIGINT),
        @Result(column="event_type", property="eventType", jdbcType=JdbcType.VARCHAR),
        @Result(column="stat_time", property="statTime", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsecCount> selectByExample(TWafLogWebsecCountExample example);

}