package com.cn.ctbri.southapi.adapter.scanner;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cn.ctbri.southapi.adapter.config.DeviceConfigInfo;
import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;


public class WebsocDeviceAdapter{
	public WebsocDeviceAdapter(){
		
	}
	
	protected static String WEBSOC_TEMPLATE = WebsocDeviceAdapter.class.getResource("/webSocTemplate.xml").toString();
	
	public WebsocDeviceOperation websocDeviceOperation = null;
	public String connectSessionID = null;
	public HashMap<String, WebsocDeviceOperation> mapWebsocDeviceOperation = new HashMap<String, WebsocDeviceOperation>();
	public HashMap<String, DeviceConfigInfo> mapDeviceConfigInfoHashMap = new HashMap<String, DeviceConfigInfo>();
	public HashMap<String, String> mapTemplate =  new HashMap<String, String>();
	public boolean initDeviceAdpater(HashMap<String, DeviceConfigInfo> mapDeviceConfigInfoHashMap){
		this.mapDeviceConfigInfoHashMap = mapDeviceConfigInfoHashMap;
		Iterator<Entry<String, DeviceConfigInfo>> deviceConfigInfoIterator = this.mapDeviceConfigInfoHashMap.entrySet().iterator();
		while (deviceConfigInfoIterator.hasNext()) {
			DeviceConfigInfo deviceConfigInfo = deviceConfigInfoIterator.next().getValue();
			if("Websoc".equalsIgnoreCase(deviceConfigInfo.getScannerFactory())){
			    websocDeviceOperation = new WebsocDeviceOperation();
			    if(!websocDeviceOperation.createSessionId(deviceConfigInfo.getScannerUserName(),deviceConfigInfo.getScannerPassword(),deviceConfigInfo.getScannerWebUrl())) return false;
			    mapWebsocDeviceOperation.put(deviceConfigInfo.getDeviceID(), websocDeviceOperation);
			}
		}
		loadWebsocTemplate();
		return true;
	}
	private boolean loadWebsocTemplate(){
		SAXReader reader = new SAXReader();
		Document doc;
		try {
			doc = reader.read(WebsocDeviceAdapter.class.getResource("/webSocTemplate.xml"));
			List<?> list = doc.selectNodes("/TemplateList/Template");
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				String name = null;
				String code = null;
				Element element = (Element) iterator.next();
				for (Iterator<?> it = element.elementIterator(); it.hasNext();) {
					Element ele = (Element) it.next();  
					if("Name".equalsIgnoreCase(ele.getName())) name = ele.getText();
					if("Code".equalsIgnoreCase(ele.getName())) code = ele.getText();
				}
				mapTemplate.put(name, code);
			}
			return true;
		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}
	}
	private WebsocDeviceOperation getDeviceById(String deviceId){
		return mapWebsocDeviceOperation.get(deviceId);
	}
	private DeviceConfigInfo getDeviceConfigById(String deviceId){
		return mapDeviceConfigInfoHashMap.get(deviceId);	
	}
	public String disposeScanTask(String deviceId, ScannerTaskUniParam scannerTaskUniParam) {
		return getDeviceById(deviceId).disposeScanTask(scannerTaskUniParam,mapTemplate);
	}
	/**
     * 获取临时组检测进度
     * @param ScannerTaskUniParam include virtual_group_id
     * @return 任务状态代码
     */
	public String getProgressByVirtualGroupId(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		return getDeviceById(deviceId).getProgressByVirtualGroupId(scannerTaskUniParam);
	}
	public String getTemplate(ScannerTaskUniParam scannerTaskUniParam){
		String s = scannerTaskUniParam.getTaskSLA();
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File("/webSocTemplate.xml"));
			Element element = document.getRootElement();
			Element element2 = element.element("Template");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
