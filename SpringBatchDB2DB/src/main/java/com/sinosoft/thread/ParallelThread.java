package com.sinosoft.thread;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StopWatch;

import com.sinosoft.scheduler.QuartzLedgerJob;
import com.sinosoft.scheduler.StartQuartz;


public class ParallelThread {
	private static final Logger LOG = LoggerFactory.getLogger(QuartzLedgerJob.class);

//	@Autowired
//	private JobLauncher jobLauncher;
//
//	@Autowired
//	private Job userJob;
	
	private static long counter = 0l;
	
	private String selectSql;
	private String insertSql;
	private String mapper;
	
	public ParallelThread(){}
	public ParallelThread(String selectSql, String insertSql, String mapper) {
		this.selectSql = selectSql;
		this.insertSql = insertSql;
		this.mapper = mapper;
	}

	public boolean startRun() throws Exception{
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		InputStream in = StartQuartz.class.getClassLoader()
				.getSystemResourceAsStream("sql.properties");
		Properties p = new Properties();
		p.load(in);
		String find = p.getProperty(selectSql);
		String add = p.getProperty(insertSql);
		System.out.println(find);
		System.out.println(add);
		LOG.debug("start...");
		System.out.println("开始同步数据");
		StopWatch stopWatch = new StopWatch(); 
		stopWatch.start();
		jobParametersBuilder.addDate("date", new Date());
		ApplicationContext context = new ClassPathXmlApplicationContext("db-db-job.xml");
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("userJob");
		jobParametersBuilder.addString("readSql", find);
		jobParametersBuilder.addString("writeSql", add);
		jobParametersBuilder.addString("mapper", mapper);
		long startTime = System.currentTimeMillis();
		JobExecution result = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间:"+(endTime-startTime)+"ms");
		LOG.debug("运行时间:"+(endTime-startTime)+"ms");
		LOG.debug("状态:"+result.getStatus());
		System.out.println(result);
		System.out.println("状态:"+result.getStatus());
		System.out.println("异常:"+result.getAllFailureExceptions());
		System.out.println("结束存储");
		stopWatch.stop();
		LOG.debug("Time elapsed:{},Execute quartz ledgerJob:{}", stopWatch.prettyPrint(), ++counter);
		if(result.getStatus().equals("COMPLETED")){
			return true;
		}else{
			return false;
		}
	}
	
}
