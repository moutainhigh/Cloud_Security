package com.cn.ctbri.southapi.adapter.jinshan;

import com.alibaba.fastjson.JSONObject;

public class JinshanDeviceAdapter {
	private JinshanDeviceOperation getJinshanDeviceOperation(){
		JinshanDeviceOperation jinshanDeviceOperation = new JinshanDeviceOperation();
		return jinshanDeviceOperation;
	}
	public String getTimeOnOrderIndex(JSONObject jsonObject) {
		return getJinshanDeviceOperation().getOrderIndex(jsonObject);
	}
	public String getUninstallInfo(JSONObject jsonObject) {
		return getJinshanDeviceOperation().getUninstallInfo(jsonObject);
	}
	public String getHostCount(JSONObject jsonObject){
		return getJinshanDeviceOperation().getHostCount(jsonObject);
	}
	public String getOauthUrl(JSONObject jsonObject) {
		return getJinshanDeviceOperation().getOauthUrl(jsonObject);
	}

}
