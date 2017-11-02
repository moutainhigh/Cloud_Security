package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecSrc;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecSrcExample;
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

public interface TWafLogWebsecSrcMapper {
    @SelectProvider(type=TWafLogWebsecSrcSqlProvider.class, method="countByExample")
    int countByExample(TWafLogWebsecSrcExample example);

    @SelectProvider(type=TWafLogWebsecSrcSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="count", property="count", jdbcType=JdbcType.BIGINT),
        @Result(column="src_ip", property="srcIp", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsecSrc> selectByExample(TWafLogWebsecSrcExample example);
}