package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecDst;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecDstExample;
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

public interface TWafLogWebsecDstMapper {
    @SelectProvider(type=TWafLogWebsecDstSqlProvider.class, method="countByExample")
    int countByExample(TWafLogWebsecDstExample example);


    @SelectProvider(type=TWafLogWebsecDstSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="count", property="count", jdbcType=JdbcType.BIGINT),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR),
        @Result(column="dst_port", property="dstPort", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsecDst> selectByExample(TWafLogWebsecDstExample example);
}