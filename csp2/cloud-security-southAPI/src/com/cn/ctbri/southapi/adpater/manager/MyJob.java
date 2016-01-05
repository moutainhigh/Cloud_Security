package com.cn.ctbri.southapi.adpater.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cn.ctbri.southapi.adpater.config.DeviceConfigInfo;
import com.cn.ctbri.southapi.adpater.config.EngineStatList;
import com.cn.ctbri.southapi.adpater.manager.DeviceAdapterConstant;

public class MyJob implements Job {
	private HashMap<String, EngineStatList> mapDeviceStat = new HashMap<String, EngineStatList>();
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
		deviceAdpaterManager.loadDeviceAdpater();	
		// TODO Auto-generated method stub
		SAXReader reader = new SAXReader();
        // 加载XML
        Document doc;
		try {
			mapDeviceStat.clear();
			doc = reader.read(DeviceAdapterConstant.FILE_DEVICE_CONFIG);
	        //加载SCANNER设备
	        List<?> list = doc.selectNodes(DeviceAdapterConstant.DEVICE_SCANNER_ROOT);	
	        Iterator<?> iter = list.iterator();
	        while (iter.hasNext()) {
		        EngineStatList engineStatList = new EngineStatList();
	        	Element elementDeviceScanner = (Element) iter.next();   
	        	String factoryString = elementDeviceScanner.element("ScannerFactory").getText();
	        	if (null==factoryString||"".equalsIgnoreCase(factoryString)||!"Arnhem".equalsIgnoreCase(factoryString)) {
					continue;
				}else if ("Arnhem".equalsIgnoreCase(factoryString)) {
		        	String deviceId = elementDeviceScanner.attributeValue("id");
		        	System.out.println("while"+deviceId);
		        	deviceAdpaterManager.getEngineStat(deviceId);
		        	
		        	mapDeviceStat.put(deviceId,engineStatList);
				}
	        }
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		JSONObject engineStatObject = JSONObject.fromObject(deviceAdpaterManager.getEngineStat("10001"));
		System.out.println(engineStatObject.get("EngineList").toString());
		JSONArray array = JSONArray.fromObject(deviceAdpaterManager.getEngineStat("10001"));
	}
}
