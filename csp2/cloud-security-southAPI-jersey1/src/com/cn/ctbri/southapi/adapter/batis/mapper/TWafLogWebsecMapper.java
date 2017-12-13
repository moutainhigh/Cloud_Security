package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsec;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecCount;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecDstSrc;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecEventTypeCount;
import com.cn.ctbri.southapi.adapter.batis.model.TWafLogWebsecExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TWafLogWebsecMapper {
	int countByExample(TWafLogWebsecExample example);
	
	List<TWafLogWebsecCount> countEventTypeByExample(TWafLogWebsecExample example);

    List<TWafLogWebsecCount> countEventTypeByDomain(@Param("domain") List<String> domain,@Param("rows") int limitNum);
    
    List<TWafLogWebsecCount> countEventTypeByDstIp(@Param("domain") List<String> domain,@Param("rows") int limitNum);
    
    List<TWafLogWebsecCount> countTimeByExample(TWafLogWebsecExample example);
    
    List<TWafLogWebsecCount> countAlertLevelByExample(TWafLogWebsecExample example);    
    
    List<TWafLogWebsecCount> countAlertLevelInTimeByExample(TWafLogWebsecExample example);
    
    List<TWafLogWebsecCount> countAlertLevelByDomain(@Param("domain") List<String> domain,@Param("limitNum") int limitNum);
    
    List<TWafLogWebsecDstSrc> selectSrcIpByDstIp(@Param("domain") List<String> domain,@Param("limitNum") int limitNum,@Param("startDate")Date startDate,@Param("endDate")Date endDate);

    List<TWafLogWebsecDstSrc> selectSrcIp(@Param("dstIp") List<String> dstIp,@Param("limitNum") int limitNum,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
    
    int selectMaxByExample(TWafLogWebsecExample example);
    
    List<TWafLogWebsec> selectByExampleWithBLOBsWithRowbounds(TWafLogWebsecExample example, RowBounds rowBounds);

    List<TWafLogWebsec> selectByExampleWithBLOBs(TWafLogWebsecExample example);

    List<TWafLogWebsec> selectByExampleWithRowbounds(TWafLogWebsecExample example, RowBounds rowBounds);

    List<TWafLogWebsec> selectByExample(TWafLogWebsecExample example);

    TWafLogWebsec selectByPrimaryKey(Long logId);
}