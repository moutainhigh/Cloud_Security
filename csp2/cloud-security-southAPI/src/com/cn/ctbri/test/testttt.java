package com.cn.ctbri.test;

import java.util.HashMap;

import net.sf.json.JSONObject;

import com.cn.ctbri.southapi.adpater.config.EngineStatInfo;
import com.cn.ctbri.southapi.adpater.manager.DeviceAdpaterManager;

public class testttt {
	private HashMap<String, EngineStatInfo> mapEngineStat = new HashMap<String, EngineStatInfo>();
	DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
	String s = deviceAdpaterManager.loadDeviceAdpater();
	JSONObject engineStatObject = JSONObject.fromObject(deviceAdpaterManager.getEngineStat("10001"));
}
