package com.cn.ctbri.southapi.adpater.bean;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TaskReportMapper {
	
	@Insert("insert into")
	public int addTaskInfo(TaskInfo taskInfo);
	
	@Delete("")
	public int delTaskInfoById(int id);
	
	@Select("select * from southapi_taskinfo where id=#{id}")
	public TaskInfo getTaskInfoById(int id);
	
	@Update("update southapi_taskinfo")
	public int updateTaskInfo(TaskInfo taskInfo);
	

	
	
}
