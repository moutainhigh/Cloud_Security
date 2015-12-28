package com.cn.ctbri.test;

import net.sf.json.JSONObject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cn.ctbri.southapi.adpater.manager.DeviceAdpaterManager;

public class MyJob implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
		deviceAdpaterManager.loadDeviceAdpater();
		JSONObject engineStatObject = JSONObject.fromObject(deviceAdpaterManager.getEngineStat("10001"));
		System.out.println(engineStatObject.get("EngineList").toString());
	}
}
