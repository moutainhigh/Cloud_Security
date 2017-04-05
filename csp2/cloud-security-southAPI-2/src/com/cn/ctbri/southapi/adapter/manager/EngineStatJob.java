package com.cn.ctbri.southapi.adapter.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cn.ctbri.southapi.adapter.config.Engine;
import com.cn.ctbri.southapi.adapter.config.EngineStatList;
import com.cn.ctbri.southapi.adapter.manager.DeviceAdapterConstant;

public class EngineStatJob implements Job {
	private HashMap<String, EngineStatList> mapDeviceStat = new HashMap<String, EngineStatList>();
	
	/**
	 * 获取引擎状态
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
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
		        	Document engineStatDocument = DocumentHelper.parseText(deviceAdpaterManager.getEngineStat(deviceId));
		        	Element engineStatRoot= engineStatDocument.getRootElement();
		        	List<?> nodes = engineStatRoot.elements("EngineList");
		            for(Iterator<?> it=nodes.iterator();it.hasNext();){
		            	Engine engine = new Engine();
		                Element engineStatElement = (Element) it.next();
		                engineStatList.setIP(engineStatElement.elementTextTrim("IP"));
		                engineStatList.setMemoryTotal(engineStatElement.elementTextTrim("MemoryTotal"));
		                engineStatList.setMemoryFree(engineStatElement.elementTextTrim("MemoryFree"));
		                engineStatList.setDiskTotal(engineStatElement.elementTextTrim("DiskTotal"));
		                engineStatList.setDiskFree(engineStatElement.elementTextTrim("DiskFree"));
		                engineStatList.setCpuOccupancy(engineStatElement.elementTextTrim("CpuOccupancy"));
		                
		                Element engineElement = engineStatElement.element("Engine");
		                engine.setEngineName(engineElement.elementTextTrim("EngineName"));
		                engine.setEngineState(engineElement.elementTextTrim("EngineStat"));
		                engine.setEUpTime(engineElement.elementTextTrim("EUpTime"));
		                engine.setEversion(engineElement.elementTextTrim("Eversion"));
		                engine.setPUpTime(engineElement.elementTextTrim("PUpTime"));
		                engine.setPversion(engineElement.elementTextTrim("Pversion"));
		                engineStatList.setEngine(engine);
		                // do something
		            }
		        	mapDeviceStat.put(deviceId,engineStatList);
				}
	        }
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
