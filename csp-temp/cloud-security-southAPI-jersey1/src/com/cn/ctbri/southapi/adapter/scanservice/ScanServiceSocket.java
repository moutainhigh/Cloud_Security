package com.cn.ctbri.southapi.adapter.scanservice;

import java.io.IOException;
import java.util.Properties;

import net.sf.json.JSONObject;

public class ScanServiceSocket {
	private static String baseURL;
	private static int basePort;
	static{	
		try {
			Properties properties = new Properties();
			properties.load(ScanServiceSocket.class.getClassLoader().getResourceAsStream("SystemServiceConfig.properties"));
			baseURL = properties.getProperty("ScanServiceBaseURL");
			basePort = Integer.parseInt(properties.getProperty("ScanServiceBasePort"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String createScanServiceTask(JSONObject jsonObject){
	         NioClient client =new NioClient();
			try {
				JSONObject reJsonObject = client.init(baseURL, basePort).listen(jsonObject);
				 reJsonObject.put("code", "200");
				return reJsonObject.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSONObject errJsonObject = new JSONObject();
				errJsonObject.put("code", "500");
				errJsonObject.put("status", "failed");
				errJsonObject.put("message", "connection refused");
				return errJsonObject.toString();
			}
	         
	}
	
/*	public static void main(String[] args) {
		ScanServiceSocket socket = new ScanServiceSocket();
		JSONObject jo=new JSONObject();
	         jo.put("task_type","web_detect");
	         jo.put("target", "www.anquanbang.net");
	         jo.put("port", "80");
	         jo.put("interval_time", "2");
	         jo.put("start_time", "2017-09-21 16:00:00");
	         jo.put("end_time", "2017-09-21 16:30:00");
	         String str=jo.toString();
		System.out.println(socket.createScanServiceTask(jo));
	}
*/
}
