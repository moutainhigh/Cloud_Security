package com.cn.ctbri.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 根据任务获取结果的调度类
 * @author googe
 *
 */
public class Scheduler4Result implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//根据任务id获取任务状态
		
		//判断任务状态
			//完成的话 将异常结果插入到异常表中  结束调度
		
	}
	
	public void getResultByTask(){
		
		//获取任务id
		
		//获取轮训间隔
		
		
	}

}
