package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecCount;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TWafLogWebsecCountMapper {
    long countByExample(TWafLogWebsecCountExample example);

    int deleteByExample(TWafLogWebsecCountExample example);

    int insert(TWafLogWebsecCount record);

    int insertSelective(TWafLogWebsecCount record);

    List<TWafLogWebsecCount> selectByExampleWithRowbounds(TWafLogWebsecCountExample example, RowBounds rowBounds);

    List<TWafLogWebsecCount> selectByExample(TWafLogWebsecCountExample example);

    int updateByExampleSelective(@Param("record") TWafLogWebsecCount record, @Param("example") TWafLogWebsecCountExample example);

    int updateByExample(@Param("record") TWafLogWebsecCount record, @Param("example") TWafLogWebsecCountExample example);
}