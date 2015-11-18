package com.cn.ctbri.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

public class GetJobBean {

	private static JobLauncher jobLauncher;

	private static Job execJob;
	
	public static JobLauncher getJobLauncher() {
		return jobLauncher;
	}

	public static void setJobLauncher(JobLauncher jobLauncher) {
		GetJobBean.jobLauncher = jobLauncher;
	}

	public static Job getExecJob() {
		return execJob;
	}

	public static void setExecJob(Job execJob) {
		GetJobBean.execJob = execJob;
	}

}
