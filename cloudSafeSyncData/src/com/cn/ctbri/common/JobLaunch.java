package com.cn.ctbri.common;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

public class JobLaunch {
	public static JobExecution dataSync(String selectKey,String insertKey,String mapper) {
        try {
        	InputStream in = JobLaunch.class.getClassLoader().getResourceAsStream("sql.properties");
        	Properties p = new Properties();
        	p.load(in);
        	String find = p.getProperty(selectKey);
        	System.out.println(find);
        	String add = p.getProperty(insertKey);
        	System.out.println(add);
        	JobParametersBuilder job1 = new JobParametersBuilder();
        	job1.addString("readSql", find);
        	job1.addString("writeSql", add);
        	job1.addString("mapper", mapper);
        	job1.addDate("time", new Date());
        	JobExecution result = null;
        	JobLauncher launcher = GetJobBeans.getJobLauncher();
        	Job job = GetJobBeans.getExecJob();
        	long startTime = System.currentTimeMillis();
            result = launcher.run(job, job1.toJobParameters());
            long endTime = System.currentTimeMillis();
            System.out.println("运行时间:"+(endTime-startTime)+"ms");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
}
