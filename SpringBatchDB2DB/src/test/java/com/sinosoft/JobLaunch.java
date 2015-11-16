package com.sinosoft;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sinosoft.scheduler.StartQuartz;

public class JobLaunch {
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("db-db-job.xml");
        InputStream in = StartQuartz.class.getClassLoader().getSystemResourceAsStream("sql.properties");
		Properties p = new Properties();   
		p.load(in);
        String find = p.getProperty("alarmSelect");
        System.out.println(find);
        String add = p.getProperty("alarmInsert");
        System.out.println(add);
        JobParametersBuilder job1 = new JobParametersBuilder();
        job1.addString("readSql", find);
        job1.addString("writeSql", add);
        job1.addString("mapper", "alarmMapper");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("userJob");
        try {
        	long startTime = System.currentTimeMillis();
            JobExecution result = launcher.run(job, job1.toJobParameters());
            System.out.println("result="+result.toString());
            System.out.println("异常原因:"+result.getAllFailureExceptions());
            long endTime = System.currentTimeMillis();
            System.out.println("运行时间:"+(endTime-startTime)+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
