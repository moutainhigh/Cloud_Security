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

    @DeleteProvider(type=TWafLogWebsecDstSqlProvider.class, method="deleteByExample")
    int deleteByExample(TWafLogWebsecDstExample example);

    @Insert({
        "insert into t_waf_log_websec_dst (count, dst_ip)",
        "values (#{count,jdbcType=BIGINT}, #{dstIp,jdbcType=VARCHAR})"
    })
    int insert(TWafLogWebsecDst record);

    @InsertProvider(type=TWafLogWebsecDstSqlProvider.class, method="insertSelective")
    int insertSelective(TWafLogWebsecDst record);

    @SelectProvider(type=TWafLogWebsecDstSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="count", property="count", jdbcType=JdbcType.BIGINT),
        @Result(column="dst_ip", property="dstIp", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafLogWebsecDst> selectByExample(TWafLogWebsecDstExample example);

    @UpdateProvider(type=TWafLogWebsecDstSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TWafLogWebsecDst record, @Param("example") TWafLogWebsecDstExample example);

    @UpdateProvider(type=TWafLogWebsecDstSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TWafLogWebsecDst record, @Param("example") TWafLogWebsecDstExample example);
}