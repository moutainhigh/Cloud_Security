package com.cn.ctbri.common;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;

public class GetJobBeans {

	private static JobLauncher jobLauncher;

	private static Job execJob;
	
	private static BasicDataSource dataSource;

	public static JobLauncher getJobLauncher() {
		return jobLauncher;
	}

	public static void setJobLauncher(JobLauncher jobLauncher) {
		GetJobBeans.jobLauncher = jobLauncher;
	}

	public static Job getExecJob() {
		return execJob;
	}

	public static void setExecJob(Job execJob) {
		GetJobBeans.execJob = execJob;
	}

	public static BasicDataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(BasicDataSource dataSource) {
		GetJobBeans.dataSource = dataSource;
	}

}
