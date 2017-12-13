package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4Location;
import com.cn.ctbri.southapi.adapter.batis.model.TViewIpv4LocationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TViewIpv4LocationMapper {
    long countByExample(TViewIpv4LocationExample example);

    int deleteByExample(TViewIpv4LocationExample example);

    int insert(TViewIpv4Location record);

    int insertSelective(TViewIpv4Location record);

    List<TViewIpv4Location> selectByExampleWithRowbounds(TViewIpv4LocationExample example, RowBounds rowBounds);

    List<TViewIpv4Location> selectByExample(TViewIpv4LocationExample example);

    int updateByExampleSelective(@Param("record") TViewIpv4Location record, @Param("example") TViewIpv4LocationExample example);

    int updateByExample(@Param("record") TViewIpv4Location record, @Param("example") TViewIpv4LocationExample example);
}