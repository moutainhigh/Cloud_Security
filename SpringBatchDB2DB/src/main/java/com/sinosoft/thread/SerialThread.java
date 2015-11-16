package com.sinosoft.thread;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StopWatch;

import com.sinosoft.scheduler.StartQuartz;

public class SerialThread {
	public static void main(String[] args){
		SerialThread s = new SerialThread();
		s.getParams();
	}
	public void getParams(){
		InputStream in = StartQuartz.class.getClassLoader()
				.getSystemResourceAsStream("sql.properties");
		Properties p = new Properties();
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		try {
			p.load(in);
			Enumeration enu2=p.propertyNames();
			while(enu2.hasMoreElements()){
			    String keys = (String)enu2.nextElement();
			    list1.add(keys);
			}
			for(int i=0;i<list1.size();i++){
				for(int j=0;j<list1.size();j++){
					if(list1.get(i).toString().substring(0, 4).equals(list1.get(j).toString().substring(0, 4))&&
							!list1.get(i).toString().substring(3).equals(list1.get(j).toString().substring(3))&&
							list1.get(i).toString().endsWith("Select")){
						String str = list1.get(i).toString();
						int index =str.indexOf("Select");
						System.out.println(str.substring(0,index));
						this.startRun(list1.get(i).toString(), list1.get(j).toString(), str.substring(0,index)+"Mapper");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void startRun(String selectSql,String insertSql,String mapper) throws Exception{
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		InputStream in = StartQuartz.class.getClassLoader()
				.getSystemResourceAsStream("sql.properties");
		Properties p = new Properties();
		p.load(in);
		String find = p.getProperty(selectSql);
		String add = p.getProperty(insertSql);
		System.out.println(find);
		System.out.println(add);
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
		System.out.println(result);
		System.out.println("状态:"+result.getStatus());
		System.out.println("异常:"+result.getAllFailureExceptions());
		System.out.println("结束存储");
		stopWatch.stop();
	}
}
