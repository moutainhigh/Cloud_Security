package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.AppReport;
import com.cn.ctbri.southapi.adapter.batis.model.AppReportExample;
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
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

public interface AppReportMapper {
    @SelectProvider(type=AppReportSqlProvider.class, method="countByExample")
    int countByExample(AppReportExample example);

    @DeleteProvider(type=AppReportSqlProvider.class, method="deleteByExample")
    int deleteByExample(AppReportExample example);

    @Delete({
        "delete from app_report",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into app_report (id, task_id, ",
        "host_status, add_time, ",
        "response_time, port_report)",
        "values (#{id,jdbcType=INTEGER}, #{taskId,jdbcType=INTEGER}, ",
        "#{hostStatus,jdbcType=VARCHAR}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{responseTime,jdbcType=VARCHAR}, #{portReport,jdbcType=LONGVARCHAR})"
    })
    int insert(AppReport record);

    @InsertProvider(type=AppReportSqlProvider.class, method="insertSelective")
    int insertSelective(AppReport record);

    @SelectProvider(type=AppReportSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER),
        @Result(column="host_status", property="hostStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="add_time", property="addTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="response_time", property="responseTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="port_report", property="portReport", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<AppReport> selectByExampleWithBLOBsWithRowbounds(AppReportExample example, RowBounds rowBounds);

    @SelectProvider(type=AppReportSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER),
        @Result(column="host_status", property="hostStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="add_time", property="addTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="response_time", property="responseTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="port_report", property="portReport", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<AppReport> selectByExampleWithBLOBs(AppReportExample example);

    @SelectProvider(type=AppReportSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER),
        @Result(column="host_status", property="hostStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="add_time", property="addTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="response_time", property="responseTime", jdbcType=JdbcType.VARCHAR)
    })
    List<AppReport> selectByExampleWithRowbounds(AppReportExample example, RowBounds rowBounds);

    @SelectProvider(type=AppReportSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER),
        @Result(column="host_status", property="hostStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="add_time", property="addTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="response_time", property="responseTime", jdbcType=JdbcType.VARCHAR)
    })
    List<AppReport> selectByExample(AppReportExample example);

    @Select({
        "select",
        "id, task_id, host_status, add_time, response_time, port_report",
        "from app_report",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER),
        @Result(column="host_status", property="hostStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="add_time", property="addTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="response_time", property="responseTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="port_report", property="portReport", jdbcType=JdbcType.LONGVARCHAR)
    })
    AppReport selectByPrimaryKey(Integer id);

    @UpdateProvider(type=AppReportSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") AppReport record, @Param("example") AppReportExample example);

    @UpdateProvider(type=AppReportSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") AppReport record, @Param("example") AppReportExample example);

    @UpdateProvider(type=AppReportSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") AppReport record, @Param("example") AppReportExample example);

    @UpdateProvider(type=AppReportSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AppReport record);

    @Update({
        "update app_report",
        "set task_id = #{taskId,jdbcType=INTEGER},",
          "host_status = #{hostStatus,jdbcType=VARCHAR},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "response_time = #{responseTime,jdbcType=VARCHAR},",
          "port_report = #{portReport,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(AppReport record);

    @Update({
        "update app_report",
        "set task_id = #{taskId,jdbcType=INTEGER},",
          "host_status = #{hostStatus,jdbcType=VARCHAR},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "response_time = #{responseTime,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AppReport record);
}