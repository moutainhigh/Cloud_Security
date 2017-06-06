package com.cn.ctbri.scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StopWatch;

public class SynData {
	@Autowired
	GetJobBean getJobBean;
	
	public boolean getParams(){
		InputStream in = SynData.class.getClassLoader().getResourceAsStream("sqlnew.properties");
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
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}
	public void startRun(String selectSql,String insertSql,String mapper) throws Exception{
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml","db-db-job.xml");
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("execJob");
		
		InputStream in = SynData.class.getClassLoader().getResourceAsStream("sqlnew.properties");
		Properties p = new Properties();
		p.load(in);
		String find = p.getProperty(selectSql);
		String add = p.getProperty(insertSql);
		
		deleteOldData(find); //先删除原始数据
		
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
	
	//删除原始数据
	private void deleteOldData(String selectSql){
		BasicDataSource ds = null;
		Connection conn = null;
		Statement sm = null;
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");  
			ds = (BasicDataSource)ctx.getBean("dataSource"); 
			conn = ds.getConnection();
			sm = conn.createStatement();
			
			String tableName = selectSql.substring(selectSql.indexOf("from")+4);
			String deleteSql = "delete from"+tableName;
			
			System.out.println(deleteSql);
			sm.execute(deleteSql); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(sm != null) {
					sm.close();
				}
				if(conn != null){
					conn.close();
				}
				if(ds != null){
					ds.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
