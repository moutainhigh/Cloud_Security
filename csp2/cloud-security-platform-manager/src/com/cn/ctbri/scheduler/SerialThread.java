package com.cn.ctbri.scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

public class SerialThread {
	@Autowired
	GetJobBean getJobBean;
	
	public void getParams(){
		InputStream in = SerialThread.class.getClassLoader().getResourceAsStream("sql.properties");
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
					String str = list1.get(i).toString();
					int index =str.indexOf("Select");
					if(index!=-1){
					System.out.println(str.substring(0,index));
					this.startRun(str.substring(0,index)+"Select", str.substring(0,index)+"Insert", str.substring(0,index)+"Mapper");
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
		JobLauncher jobLauncher = getJobBean.getJobLauncher();
		Job job = getJobBean.getExecJob();
		InputStream in = SerialThread.class.getClassLoader().getResourceAsStream("sql.properties");
		Properties p = new Properties();
		p.load(in);
		String find = p.getProperty(selectSql);
		String add = p.getProperty(insertSql);
		System.out.println(find);
		System.out.println(add);
		System.out.println("开始同步数据");
		StopWatch stopWatch = new StopWatch(); 
		stopWatch.start();
		jobParametersBuilder.addString("readSql", find);
		jobParametersBuilder.addString("writeSql", add);
		jobParametersBuilder.addString("mapper", mapper);
		jobParametersBuilder.addDate("time", new Date());
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
	
	public String getNowDate(){
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String dateNowStr = sdf.format(d); 
        return dateNowStr;
	}
}
