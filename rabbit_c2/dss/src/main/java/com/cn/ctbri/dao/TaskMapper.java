package com.cn.ctbri.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.model.Task;

public interface TaskMapper {
    int deleteByPrimaryKey(Long taskid);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Long taskid);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
    
    List<Task> selectByTask(Task record);
}