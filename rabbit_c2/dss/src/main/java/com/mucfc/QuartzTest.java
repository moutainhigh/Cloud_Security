package com.mucfc;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.dao.TaskMapper;
import com.cn.ctbri.model.Task;
import com.entity.CrtOrder;
@Component
public class QuartzTest {
	@Autowired
	private Scheduler scheduler;
	@Resource(name = "taskDao")
	private  TaskMapper taskDao;
	/**
	 * 开始一个simpleSchedule()调度
	 */
	public void startSchedule(CrtOrder crtOrder) {
		try {
			//先取出消息
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date startDate = sdf.parse(crtOrder.getStartDate()); 
			Date endDate = sdf.parse(crtOrder.getEndDate());
			JobDetail jobDetail = null ;
			Trigger trigger = null ;
			//单次任务
		    if(crtOrder.getIsCycle() == 0){
		    	// 1、创建一个JobDetail实例，指定Quartz
		    	jobDetail = JobBuilder.newJob(MyJob.class)
				// 任务执行类
						//.withIdentity("job1_1", "jGroup1")
						.withIdentity(crtOrder.getAssetId(), crtOrder.getOrderId())
						// 任务名，任务组
						.build();
		    	trigger = TriggerBuilder.newTrigger() 
		                .withIdentity(crtOrder.getAssetId(), crtOrder.getOrderId())
		                
		                .startAt(startDate)
		                .endAt(endDate)
		                .build();
		    	
		    }
		    //周期任务
		    else{
		    	// 2、创建Trigger
				/*SimpleScheduleBuilder builder = SimpleScheduleBuilder
						.simpleSchedule()
						// 设置执行次数
					    .repeatSecondlyForTotalCount(10);
			    Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger1_1", "tGroup1").startNow()
				.withSchedule(builder).build();*/
				
				 CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
				 //withMisfireHandlingInstructionDoNothing()若错过该周期，则等下个周期
				  trigger = (CronTrigger)( TriggerBuilder.newTrigger()
					    .withIdentity("trigger1", "group1")
					    
					    .withSchedule(scheduleBuilder.withMisfireHandlingInstructionDoNothing())
					    
					    .build());
				
				
		    }
		    Task task = new Task();
		    task.setAssetid(crtOrder.getAssetId());
		    task.setAsseturl(crtOrder.getAssetUrl());
		    task.setEnddate(sdf.parse(crtOrder.getEndDate()));
		    task.setIscycle(crtOrder.getIsCycle());
		    
		    task.setOrderid(crtOrder.getOrderId());
		    task.setOrigin(crtOrder.getOrigin());
		    task.setPeriodic(crtOrder.getPeriodic());
		    
		    task.setServicetype(crtOrder.getServiceType());
		    task.setStartdate(sdf.parse(crtOrder.getStartDate()));
		    task.setStatus(1);
		    
		    task.setTotaltimes(0);
		    
		    Date currentTime = new Date();
		    String insertTime_String = sdf.format(currentTime);
		    Date insertTime = sdf.parse(insertTime_String);
		    task.setUpdatetime(insertTime);
		    taskDao.insertSelective(task);
		    
		 
		    JSONObject o = JSONObject.fromObject(crtOrder);
			jobDetail.getJobDataMap().put("crtOrder", o.toString());
			
			// 3、创建Scheduler
			scheduler.start();
			// 4、调度执行
			scheduler.scheduleJob(jobDetail, (Trigger) trigger);
			
			taskDao.selectByPrimaryKey(1l);
			
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//若使用scheduler.shutdown()则任务不再被调度
			//scheduler.shutdown();

		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 从数据库中找到已经存在的job，并重新调度
	 */
	public  void resumeAllJob() {
		try {
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for(JobKey jobKey : jobKeys){
				scheduler.resumeJob(jobKey);
			}
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从数据库中找到已经存在的job，并删除
	 */
	public  void deleteAllJob() {
		try {
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for(JobKey jobKey : jobKeys){
				scheduler.deleteJob(jobKey);
			}
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从数据库中找到已经存在的job，并暂停
	 */
	public  void pauseAllJob() {
		try {
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for(JobKey jobKey : jobKeys){
				scheduler.pauseJob(jobKey);
			}
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
