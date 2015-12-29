package com.cn.ctbri.test;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cn.ctbri.southapi.adpater.config.EngineStatInfo;
import com.cn.ctbri.southapi.adpater.manager.DeviceAdpaterManager;

public class MyJob implements Job {
	private HashMap<String, EngineStatInfo> mapEngineStat = new HashMap<String, EngineStatInfo>();
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
		deviceAdpaterManager.loadDeviceAdpater();
		JSONObject engineStatObject = JSONObject.fromObject(deviceAdpaterManager.getEngineStat("10001"));
		System.out.println(engineStatObject.get("EngineList").toString());
	}
}
