package com.cn.ctbri.southapi.adapter.batis.inter;

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

    @DeleteProvider(type=TWafLogWebsecSrcSqlProvider.class, method="deleteByExample")
    int deleteByExample(TWafLogWebsecSrcExample example);

    @Insert({
        "insert into t_waf_log_websec_src (count, src_ip)",
        "values (#{count,jdbcType=BIGINT}, #{srcIp,jdbcType=VARCHAR})"
    })
    int insert(TWafLogWebsecSrc record);

    @InsertProvider(type=TWafLogWebsecSrcSqlProvider.class, method="insertSelective")
    int insertSelective(TWafLogWebsecSrc record);

    @SelectProvider(type=TWafLogWebsecSrcSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="count", property="count", jdbcType=JdbcType.BIGINT),
        @Result(column="src_ip", property="srcIp", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsecSrc> selectByExample(TWafLogWebsecSrcExample example);

    @UpdateProvider(type=TWafLogWebsecSrcSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TWafLogWebsecSrc record, @Param("example") TWafLogWebsecSrcExample example);

    @UpdateProvider(type=TWafLogWebsecSrcSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TWafLogWebsecSrc record, @Param("example") TWafLogWebsecSrcExample example);
}