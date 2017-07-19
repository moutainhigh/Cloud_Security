package com.cn.ctbri.southapi.adapter.batis.inter;

import com.cn.ctbri.southapi.adapter.batis.model.AppTask;
import com.cn.ctbri.southapi.adapter.batis.model.AppTaskExample;
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

public interface AppTaskMapper {
    @SelectProvider(type=AppTaskSqlProvider.class, method="countByExample")
    int countByExample(AppTaskExample example);

    @DeleteProvider(type=AppTaskSqlProvider.class, method="deleteByExample")
    int deleteByExample(AppTaskExample example);

    @Delete({
        "delete from app_task",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into app_task (id, task_type, ",
        "agent_id, target, ",
        "port, options, interval_time, ",
        "status, run_time, ",
        "end_time, insert_time)",
        "values (#{id,jdbcType=INTEGER}, #{taskType,jdbcType=VARCHAR}, ",
        "#{agentId,jdbcType=VARCHAR}, #{target,jdbcType=VARCHAR}, ",
        "#{port,jdbcType=VARCHAR}, #{options,jdbcType=VARCHAR}, #{intervalTime,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=VARCHAR}, #{runTime,jdbcType=TIMESTAMP}, ",
        "#{endTime,jdbcType=VARCHAR}, #{insertTime,jdbcType=TIMESTAMP})"
    })
    int insert(AppTask record);

    @InsertProvider(type=AppTaskSqlProvider.class, method="insertSelective")
    int insertSelective(AppTask record);

    @SelectProvider(type=AppTaskSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="task_type", property="taskType", jdbcType=JdbcType.VARCHAR),
        @Result(column="agent_id", property="agentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="target", property="target", jdbcType=JdbcType.VARCHAR),
        @Result(column="port", property="port", jdbcType=JdbcType.VARCHAR),
        @Result(column="options", property="options", jdbcType=JdbcType.VARCHAR),
        @Result(column="interval_time", property="intervalTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="run_time", property="runTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="insert_time", property="insertTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<AppTask> selectByExampleWithRowbounds(AppTaskExample example, RowBounds rowBounds);

    @SelectProvider(type=AppTaskSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="task_type", property="taskType", jdbcType=JdbcType.VARCHAR),
        @Result(column="agent_id", property="agentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="target", property="target", jdbcType=JdbcType.VARCHAR),
        @Result(column="port", property="port", jdbcType=JdbcType.VARCHAR),
        @Result(column="options", property="options", jdbcType=JdbcType.VARCHAR),
        @Result(column="interval_time", property="intervalTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="run_time", property="runTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="insert_time", property="insertTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<AppTask> selectByExample(AppTaskExample example);

    @Select({
        "select",
        "id, task_type, agent_id, target, port, options, interval_time, status, run_time, ",
        "end_time, insert_time",
        "from app_task",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="task_type", property="taskType", jdbcType=JdbcType.VARCHAR),
        @Result(column="agent_id", property="agentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="target", property="target", jdbcType=JdbcType.VARCHAR),
        @Result(column="port", property="port", jdbcType=JdbcType.VARCHAR),
        @Result(column="options", property="options", jdbcType=JdbcType.VARCHAR),
        @Result(column="interval_time", property="intervalTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="run_time", property="runTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="insert_time", property="insertTime", jdbcType=JdbcType.TIMESTAMP)
    })
    AppTask selectByPrimaryKey(Integer id);

    @UpdateProvider(type=AppTaskSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") AppTask record, @Param("example") AppTaskExample example);

    @UpdateProvider(type=AppTaskSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") AppTask record, @Param("example") AppTaskExample example);

    @UpdateProvider(type=AppTaskSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AppTask record);

    @Update({
        "update app_task",
        "set task_type = #{taskType,jdbcType=VARCHAR},",
          "agent_id = #{agentId,jdbcType=VARCHAR},",
          "target = #{target,jdbcType=VARCHAR},",
          "port = #{port,jdbcType=VARCHAR},",
          "options = #{options,jdbcType=VARCHAR},",
          "interval_time = #{intervalTime,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=VARCHAR},",
          "run_time = #{runTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=VARCHAR},",
          "insert_time = #{insertTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AppTask record);
}