package com.cn.ctbri.southapi.adapter.systemserv;

import com.alibaba.fastjson.JSONObject;

public class CloudInsightAdapter {
	private CloudInsightOperation getCloudInsightOperation(){
		CloudInsightOperation cloudInsightOperation = new CloudInsightOperation();
		return cloudInsightOperation;
	}
	public String getToken(JSONObject jsonObject) {
		return getCloudInsightOperation().getToken(jsonObject);
	}
	public String destroyToken(JSONObject jsonObject){
		return getCloudInsightOperation().destroyToken(jsonObject);
	}
	public String getLoginURL(JSONObject jsonObject) {
		return getCloudInsightOperation().getLoginURL(jsonObject);
	}
}
