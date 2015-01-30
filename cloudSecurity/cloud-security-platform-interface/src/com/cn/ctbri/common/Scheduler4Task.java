package com.cn.ctbri.common;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cn.ctbri.dao.TaskDao;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Task;
/**
 * 根据订单定制任务的调度类
 * @author googe
 *
 */
public class Scheduler4Task{
	
	static Logger logger = Logger.getLogger(Scheduler4Task.class.getName());
	
	static{
		try {
			Properties p = new Properties();
			p.load(Scheduler4Task.class.getClassLoader().getResourceAsStream("arnhem.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execute() throws JobExecutionException {
		System.out.println(new Date().toLocaleString() + " : 任务表扫描开始"); 
		//logger.info(new Date().toLocaleString() + " : 任务表扫描开始");
		
		/**
		 * 定时要job任务执行的逻辑
		 * 获取任务表前20条未开始的记录
		 */
		System.out.println("asdasd");
		
		System.out.println(new Date().toLocaleString() + " : 任务表扫描结束"); 
		//logger.info(new Date().toLocaleString() + " : 任务表扫描结束");
		
	}
	
	public static void main(String[] args) {
	}
}
