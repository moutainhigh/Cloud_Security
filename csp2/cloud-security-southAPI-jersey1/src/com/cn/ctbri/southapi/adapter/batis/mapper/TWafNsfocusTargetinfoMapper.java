package com.cn.ctbri.southapi.adapter.batis.mapper;

import com.cn.ctbri.southapi.adapter.batis.model.TWafNsfocusTargetinfo;
import com.cn.ctbri.southapi.adapter.batis.model.TWafNsfocusTargetinfoExample;
import com.cn.ctbri.southapi.adapter.batis.model.TWafNsfocusTargetinfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.session.RowBounds;

public interface TWafNsfocusTargetinfoMapper {
	@SelectProvider(type=TWafNsfocusTargetinfoSqlProvider.class, method="countByExample")
    int countByExample(TWafNsfocusTargetinfoExample example);

    @DeleteProvider(type=TWafNsfocusTargetinfoSqlProvider.class, method="deleteByExample")
    int deleteByExample(TWafNsfocusTargetinfoExample example);

    @Delete({
        "delete from t_waf_nsfocus_targetinfo",
        "where id = #{id,jdbcType=VARCHAR}",
          "and resourceId = #{resourceid,jdbcType=INTEGER}",
          "and deviceId = #{deviceid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(TWafNsfocusTargetinfoKey key);

    @Insert({
        "insert into t_waf_nsfocus_targetinfo (id, resourceId, ",
        "deviceId, name, ",
        "siteId, groupId, ",
        "virtSiteId)",
        "values (#{id,jdbcType=VARCHAR}, #{resourceid,jdbcType=INTEGER}, ",
        "#{deviceid,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{siteid,jdbcType=VARCHAR}, #{groupid,jdbcType=VARCHAR}, ",
        "#{virtsiteid,jdbcType=VARCHAR})"
    })
    int insert(TWafNsfocusTargetinfo record);

    @InsertProvider(type=TWafNsfocusTargetinfoSqlProvider.class, method="insertSelective")
    int insertSelective(TWafNsfocusTargetinfo record);

    @SelectProvider(type=TWafNsfocusTargetinfoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="resourceId", property="resourceid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="deviceId", property="deviceid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="siteId", property="siteid", jdbcType=JdbcType.VARCHAR),
        @Result(column="groupId", property="groupid", jdbcType=JdbcType.VARCHAR),
        @Result(column="virtSiteId", property="virtsiteid", jdbcType=JdbcType.VARCHAR)
    })
    List<TWafNsfocusTargetinfo> selectByExample(TWafNsfocusTargetinfoExample example);

    @Select({
        "select",
        "id, resourceId, deviceId, name, siteId, groupId, virtSiteId",
        "from t_waf_nsfocus_targetinfo",
        "where id = #{id,jdbcType=VARCHAR}",
          "and resourceId = #{resourceid,jdbcType=INTEGER}",
          "and deviceId = #{deviceid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="resourceId", property="resourceid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="deviceId", property="deviceid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="siteId", property="siteid", jdbcType=JdbcType.VARCHAR),
        @Result(column="groupId", property="groupid", jdbcType=JdbcType.VARCHAR),
        @Result(column="virtSiteId", property="virtsiteid", jdbcType=JdbcType.VARCHAR)
    })
    TWafNsfocusTargetinfo selectByPrimaryKey(TWafNsfocusTargetinfoKey key);

    @UpdateProvider(type=TWafNsfocusTargetinfoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TWafNsfocusTargetinfo record, @Param("example") TWafNsfocusTargetinfoExample example);

    @UpdateProvider(type=TWafNsfocusTargetinfoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TWafNsfocusTargetinfo record, @Param("example") TWafNsfocusTargetinfoExample example);

    @UpdateProvider(type=TWafNsfocusTargetinfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TWafNsfocusTargetinfo record);

    @Update({
        "update t_waf_nsfocus_targetinfo",
        "set name = #{name,jdbcType=VARCHAR},",
          "siteId = #{siteid,jdbcType=VARCHAR},",
          "groupId = #{groupid,jdbcType=VARCHAR},",
          "virtSiteId = #{virtsiteid,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=VARCHAR}",
          "and resourceId = #{resourceid,jdbcType=INTEGER}",
          "and deviceId = #{deviceid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TWafNsfocusTargetinfo record);
}