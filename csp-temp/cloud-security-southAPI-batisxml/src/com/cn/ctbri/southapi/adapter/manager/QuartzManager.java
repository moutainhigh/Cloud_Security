package com.cn.ctbri.southapi.adapter.manager;


import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzManager{
	public static void runJob() throws SchedulerException{
		/*
		JobDetail jobDetail = JobBuilder.newJob(EngineStatJob.class).withIdentity("myjob","group").build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myjob", "group").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).repeatForever())
				.build();
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		scheduler.scheduleJob(jobDetail,trigger);
		scheduler.start();
		*/
	}
} 
