package com.mucfc;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class QuartzTest {
	@Autowired
	private Scheduler scheduler;
	private static String JOB_GROUP_NAME = "ddlib";
	private static String TRIGGER_GROUP_NAME = "ddlibTrigger";
	/**
	 * ��ʼһ��simpleSchedule()����
	 */
	public void startSchedule() {
		try {
			// 1������һ��JobDetailʵ����ָ��Quartz
			JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
			// ����ִ����
					.withIdentity("job1_1", "jGroup1")
					// ��������������
					.build();
			
			
			// 2������Trigger
			/*SimpleScheduleBuilder builder = SimpleScheduleBuilder
					.simpleSchedule()
					// ����ִ�д���
				    .repeatSecondlyForTotalCount(10);
		    Trigger trigger = TriggerBuilder.newTrigger()
			.withIdentity("trigger1_1", "tGroup1").startNow()
			.withSchedule(builder).build();*/
			
			 CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/20 * * * * ?");
			 CronTrigger  trigger = (CronTrigger)( TriggerBuilder.newTrigger()
				    .withIdentity("trigger1", "group1")
				    .withSchedule(scheduleBuilder)
				    .build());
			
			// 3������Scheduler
			scheduler.start();
			// 4������ִ��
			scheduler.scheduleJob(jobDetail, (Trigger) trigger);
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			scheduler.shutdown();

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����ݿ����ҵ��Ѿ����ڵ�job�������¿�������
	 */
	public  void resumeJob() {
		try {
			// �ٻ�ȡ�����������еĴ�������
			List<String> triggerGroups = scheduler.getTriggerGroupNames();
			// �����»ָ���tgroup1���У���Ϊtrigger1_1������������
			for (int i = 0; i < triggerGroups.size(); i++) {
				List<String> triggers = scheduler.getTriggerGroupNames();
				for (int j = 0; j < triggers.size(); j++) {
					Trigger tg = scheduler.getTrigger(new TriggerKey(triggers
							.get(j), triggerGroups.get(i)));
					// ��-1:���������ж�
					if (tg instanceof SimpleTrigger
							&& tg.getDescription().equals("tgroup1.trigger1_1")) {
						// ��-1:�ָ�����
						scheduler.resumeJob(new JobKey(triggers.get(j),
								triggerGroups.get(i)));
					}
				}

			}
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
