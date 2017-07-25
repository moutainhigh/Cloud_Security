package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecDstSrc;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecDstSrcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TWafLogWebsecDstSrcMapper {
    int countByExample(TWafLogWebsecDstSrcExample example);

    int deleteByExample(TWafLogWebsecDstSrcExample example);

    int insert(TWafLogWebsecDstSrc record);

    int insertSelective(TWafLogWebsecDstSrc record);

    List<TWafLogWebsecDstSrc> selectByExampleWithRowbounds(TWafLogWebsecDstSrcExample example, RowBounds rowBounds);

    List<TWafLogWebsecDstSrc> selectByExample(TWafLogWebsecDstSrcExample example);

    int updateByExampleSelective(@Param("record") TWafLogWebsecDstSrc record, @Param("example") TWafLogWebsecDstSrcExample example);

    int updateByExample(@Param("record") TWafLogWebsecDstSrc record, @Param("example") TWafLogWebsecDstSrcExample example);
}