package com.cn.ctbri.southapi.adapter.manager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cn.ctbri.southapi.adapter.manager.DeviceAdapterConstant;
import com.cn.ctbri.southapi.adapter.config.DeviceConfigInfo;
import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;
import com.cn.ctbri.southapi.adapter.scanner.ArnhemDeviceAdpater;
import com.cn.ctbri.southapi.adapter.scanner.WebsocDeviceAdapter;
import com.cn.ctbri.southapi.adapter.waf.NsfocusWAFAdapter;
import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigManager;



/**
 * 设备适配管理
 * @version 
 * @author shao
 * @time 2015-11-04
 */

public class DeviceAdpaterManager {

	private static String wafRootString; 
	private static String wafRootStat;
	
	public static HashMap<String, DeviceConfigInfo> mapDeviceConfigInfoHashMap = new HashMap<String, DeviceConfigInfo>();
	public static  WAFConfigManager wafConfigManager = new WAFConfigManager();

	public static ArnhemDeviceAdpater arnhemDeviceAdpater = new ArnhemDeviceAdpater();
	public static WebsocDeviceAdapter websocDeviceAdapter = new WebsocDeviceAdapter();
	public static NsfocusWAFAdapter nsfocusWAFAdapter = new NsfocusWAFAdapter();
	
	//加载设备错误信息
	private static final String LOAD_DEVICE_ERROR = "{\"status\":\"fail\",\"message\":\"Load DeviceConfig failed!!\"}";
	//初始胡错误信息
	private static final String INIT_DEVICE_ERROR = "{\"status\":\"fail\",\"message\":\"Init DeviceAdapter failed!!\"}";
	//设备操作失败错误信息
	private static final String DEVICE_OPERATION_ERROR = "{\"status\":\"fail\",\"message\":\"This device does not support the operation\"}";

	/**
	 * 获取设备配置信息
	 * @param deviceId
	 * @return DeviceConfigInfo
	 */
	public static DeviceConfigInfo getDeviceAdapterAttrInfo(String deviceId)
	{
		return mapDeviceConfigInfoHashMap.get(deviceId);
	}
	
	private String errorDevieInfo(String deviceId) {
		return "{\"status\":\"fail\",\"message\":\"Can not find device: "+deviceId+"\"}";
	}
	
	/**
	 * 功能描述：统一状态参数的方法，替换掉原本转换后的默认参数
	 * @param xml
	 * @return
	 */
	private JSONObject responseToJSON(String xml){
		JSON json = new XMLSerializer().read(xml);
        JSONObject responseObject = JSONObject.fromObject(json.toString());
        String status = responseObject.getString("@value");
        responseObject.remove("@value");
        responseObject.put("status", status);
		return responseObject;
	}
	
	
	/*
	 * 初始化
	 */
	public String loadDeviceAdpater()
	{
		if (!loadDeviceConfig(DeviceAdapterConstant.FILE_DEVICE_CONFIG)){
			
			return LOAD_DEVICE_ERROR;
		}
		if (!initAllDeviceAdapter()){
			return INIT_DEVICE_ERROR;
		}
		//加载各个适配器
		//安恒适配器
		return "{\"status\":\"success\"}";
	}	
	public String loadDeviceAdapter(String deviceId){
		if (!loadDeviceConfig(DeviceAdapterConstant.FILE_DEVICE_CONFIG)){
			return LOAD_DEVICE_ERROR;
		}
		if (!initDeviceAdapter(deviceId)){
			return INIT_DEVICE_ERROR;
		}
		return "{\"status\":\"success\"}";
		
	}
	
	private boolean initAllDeviceAdapter()
	{
		//分别初始化各类设备
		/*
		 * Arnhem Device
		 */
		if (!arnhemDeviceAdpater.initDeviceAdpater(mapDeviceConfigInfoHashMap))
		{
			//log
			return false;
		}
		
		/*
		 * WebSoc Device
		 */		
		if (!websocDeviceAdapter.initDeviceAdpater(mapDeviceConfigInfoHashMap))
		{
			//log
			return false;
		}
		if (wafRootStat.equals(DeviceAdapterConstant.DEVICE_STATE_ON)&&!nsfocusWAFAdapter.initDeviceAdapter(wafConfigManager))
		{
			return false;
		}
		/*  Huawei DDOS
		if (!websocDeviceAdapter.initDeviceAdpater())
		{
			//log
			return false;
		}
		*/
	
		return true;
	}
	private boolean initDeviceAdapter(String deviceId) {
		HashMap<String, DeviceConfigInfo> map = new HashMap<String, DeviceConfigInfo>();
		map.put(deviceId, getDeviceAdapterAttrInfo(deviceId));
		if ("".equals(deviceId) ||getDeviceAdapterAttrInfo(deviceId)==null ){
			return false;
		}else if (DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim())){
			return arnhemDeviceAdpater.initDeviceAdpater(map);
		}else if (DeviceAdapterConstant.DEVICE_SCANNER_WEBSOC.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim())) {
			return websocDeviceAdapter.initDeviceAdpater(map);
		}
		return false;
	}
	private boolean loadDeviceConfig(String configFileName)
	{
		SAXReader reader = new SAXReader();
        // 加载XML
        Document doc;
        
		try {
			doc = reader.read(configFileName);
	        //加载SCANNER设备
	        List<?> list = doc.selectNodes(DeviceAdapterConstant.DEVICE_SCANNER_ROOT);	
	        Iterator<?> iter = list.iterator();
	        while (iter.hasNext()) {
	        	  Element elementDeviceScanner = (Element) iter.next();   
	        	  DeviceConfigInfo daaInfo = new DeviceConfigInfo();
	        	  
	        	  daaInfo.setDeviceType(DeviceAdapterConstant.DEFALUT_DEVICE_TYPE_SCANNER);
	        	  
	        	  //For DeviceID
	        	  String deviceIDString = elementDeviceScanner.elementTextTrim("DeviceID");
	        	  if(null==deviceIDString||"".equals(deviceIDString)){
	        		  System.err.println("Config error: DeviceID is null!!!");
	        		  continue;
	        	  }
	        	  daaInfo.setDeviceID(deviceIDString);
	        	  
	        	  //For ScannerFactory
	        	  String scannerFactoryString = elementDeviceScanner.elementTextTrim("ScannerFactory");
	        	  if(null==scannerFactoryString||"".equals(scannerFactoryString)){
	        		  System.err.println("Config error: ScannerFactory is null!!!");
	        		  continue;
	        	  }
	        	  daaInfo.setScannerFactory(scannerFactoryString);
	        	  
	        	  //For ScannerFactoryName
	        	  String scannerFactoryNameString = elementDeviceScanner.elementTextTrim("ScannerFactoryName");
	        	  if(null==scannerFactoryNameString||"".equals(scannerFactoryNameString)){
	        		  System.err.println("Config error: ScannerFactoryName is null!!!");
	        		  continue;
	        	  }
	        	  daaInfo.setScannerFactoryName(scannerFactoryNameString);
	        	  
	        	  //For ScannerWebUrl
	        	  String scannerWebUrlString = elementDeviceScanner.elementTextTrim("ScannerWebUrl");
	        	  if(null==scannerWebUrlString||"".equals(scannerWebUrlString)){
	        		  System.err.println("Config error: ScannerWebUrl is null!!!");
	        		  continue;
	        	  }
	        	  daaInfo.setScannerWebUrl(scannerWebUrlString);
	        	  
	        	  //For ScannerEngineAPI
	        	  String scannerEngineAPIString = elementDeviceScanner.elementTextTrim("ScannerEngineAPI");
	        	  if(null==scannerEngineAPIString||"".equals(scannerEngineAPIString)){
	        		  System.err.println("Config error: EngineAPI is null!!!");
	        		  continue;
	        	  }
	        	  daaInfo.setScannerEngineAPI(scannerEngineAPIString);
	        	  
	        	  
	        	  //For ScannerUsername
	        	  String scannerUsernameString =elementDeviceScanner.elementTextTrim("ScannerUserName");
	        	  if(null==scannerUsernameString||"".equals(scannerUsernameString)){
	        		  System.err.println("Config error: ScannerUsername is null!!!");
	        		  continue;
	        	  }
	        	  daaInfo.setScannerUserName(scannerUsernameString);
	        	  
	        	  //For ScannerPassword
	        	  String scannerPasswordString = elementDeviceScanner.elementTextTrim("ScannerPassword");
	        	  if(null==scannerPasswordString||"".equals(scannerPasswordString)){
	        		  System.err.println("Config error: ScannerPassword is null!!!");
	        		  continue;
	        	  }
	        	  daaInfo.setScannerPassword(scannerPasswordString);    	  
	        	  mapDeviceConfigInfoHashMap.put(daaInfo.getDeviceID(), daaInfo);
	        }
	        Element wafRoot = doc.getRootElement().element("DeviceList").element("DeviceWAFList");
	        if(wafRoot.attribute("state")!=null||wafRoot.attributeValue("state").equalsIgnoreCase(DeviceAdapterConstant.DEVICE_STATE_ON)){
		        wafRootStat = wafRoot.attributeValue("state");
	        	wafRootString = DeviceAdapterConstant.RootPath+doc.getRootElement().element("DeviceList").element("DeviceWAFList").attributeValue("configFile");
	        	wafConfigManager.loadWAFConfig(wafRootString);	    
	        }

	        return true;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getDeviceId(){
		return arnhemDeviceAdpater.getDeviceId();
	}
	
	public String getState(String deviceId) {
		if ("".equals(deviceId)||getDeviceAdapterAttrInfo(deviceId) == null){
			return errorDevieInfo(deviceId);
		}
		if (DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim())){
			return responseToJSON(arnhemDeviceAdpater.getState(deviceId)).toString();
		}
		return DEVICE_OPERATION_ERROR;
	}
	public String disposeScanTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam)
	{	
		if ("".equals(deviceId) ||getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if (DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()))
		{
			return arnhemDeviceAdpater.disposeScanTask(deviceId,scannerTaskUniParam);
		}else if (DeviceAdapterConstant.DEVICE_SCANNER_WEBSOC.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim())) {
			return websocDeviceAdapter.disposeScanTask(deviceId,scannerTaskUniParam);
		}
		return DEVICE_OPERATION_ERROR;
	}
	/**
	 * 获取任务配置模板
	 * @param deviceId
	 * @return
	 */
	public String  getTemplate(String deviceId) {
		if (null==deviceId||"".equals(deviceId)||getDeviceAdapterAttrInfo(deviceId) == null){
			return errorDevieInfo(deviceId);
		}
		if (DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return responseToJSON(arnhemDeviceAdpater.getTemplate(deviceId)).toString();
		}
		return DEVICE_OPERATION_ERROR;
	}

	/**
	 * 1.1.4	获取性能数据和版本号
	 * @param deviceId 设备号，由设备配置文件提供
	 * @return
	 */
	public String getEngineStat(String deviceId){
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.getEngineState(deviceId);		
		}
		return DEVICE_OPERATION_ERROR;
	}
	/**
	 * 获取性能数据（百分比）
	 * @param deviceId
	 * @return	IP
	 * 			cpuUsage
	 *         	memoryUsage
	 *         	diskUsage
	 *         
	 */
	public String getEngineStatRate(String deviceId) {
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equalsIgnoreCase(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			JSONObject jsonObject = new JSONObject();
			String responseString = arnhemDeviceAdpater.getEngineState(deviceId);	
			System.out.println(responseString);
			Document document = null;
			try {
				document = DocumentHelper.parseText(responseString);
			} catch (DocumentException e) {
				e.printStackTrace();
			} 
			Element rootElement = document.getRootElement();
			if ("Success".equalsIgnoreCase(rootElement.attributeValue("value"))) {		
				List<?> nodes = rootElement.elements("EngineList");
				List<HashMap> engineList = new ArrayList<HashMap>();
				for(Iterator<?> it=nodes.iterator();it.hasNext();){
					Element engineStatElement = (Element) it.next();
					HashMap<String, Comparable> engineRateMap = new HashMap<String, Comparable>();
					//For IP
					if ("".equalsIgnoreCase(engineStatElement.elementTextTrim("IP"))||engineStatElement.elementTextTrim("IP")==null) {
						engineRateMap.put("ip", null);
					}else {
						String IP = engineStatElement.elementTextTrim("IP");
						engineRateMap.put("ip", IP);
					}
					
					//For CPU Usage
					if ("".equalsIgnoreCase(engineStatElement.elementTextTrim("CpuOccupancy"))
							||engineStatElement.elementTextTrim("CpuOccupancy")==null) {
						engineRateMap.put("cpuUsage", null);
					}else {
						String CpuOccupancy = engineStatElement.elementTextTrim("CpuOccupancy");
						float cpuUsage = Float.parseFloat(CpuOccupancy);
						engineRateMap.put("cpuUsage", cpuUsage);
					}
					
					//For memoryFree
					if ("".equalsIgnoreCase(engineStatElement.elementTextTrim("MemoryFree"))
							||engineStatElement.elementTextTrim("MemoryFree")==null
							||"".equalsIgnoreCase(engineStatElement.elementTextTrim("MemoryTotal"))
							||engineStatElement.elementTextTrim("MemoryTotal")==null) {
						engineRateMap.put("memoryUsage", null);
					} else {
						float memoryFree = Float.parseFloat(engineStatElement.elementTextTrim("MemoryFree"));
						float memoryTotal = Float.parseFloat(engineStatElement.elementTextTrim("MemoryTotal"));
						float memoryUsage = 100.00f*(1f-memoryFree/memoryTotal);
						engineRateMap.put("memoryUsage", memoryUsage);
					}
					
					if (engineStatElement.elementTextTrim("DiskFree")==null
							||engineStatElement.elementTextTrim("DiskFree").length()<=0
							||"".equalsIgnoreCase(engineStatElement.elementTextTrim("DiskTotal"))
							||engineStatElement.elementTextTrim("DiskTotal")==null) {
						engineRateMap.put("diskUsage", null);
					} else {
						float diskFree = Float.parseFloat(engineStatElement.elementTextTrim("DiskFree"));
						float diskTotal = Float.parseFloat(engineStatElement.elementTextTrim("DiskTotal"));
						float diskUsage = 100.00f*(1f-diskFree/diskTotal);
						engineRateMap.put("diskUsage", diskUsage);
					}	            	
					engineRateMap.put("getSpeed", 0);
					engineRateMap.put("sendSpeed", 0);
					
					engineList.add(engineRateMap);
					
				}
				JSONArray jsonArray = JSONArray.fromObject(engineList);
				jsonObject.put("status", "success");
				jsonObject.put("StatRateList", jsonArray);
				return jsonObject.toString();
			} else {
				return responseToJSON(responseString).toString();
			}				
		}
		return DEVICE_OPERATION_ERROR;
	}
	
	public String removeTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
		if ( null==deviceId||"".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return responseToJSON(arnhemDeviceAdpater.removeTask(deviceId,scannerTaskUniParam)).toString();		
		}
		return DEVICE_OPERATION_ERROR;
	}
	public String startTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam) {
		if ( null==deviceId||"".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()))
		{
			return responseToJSON(arnhemDeviceAdpater.startTask(deviceId, scannerTaskUniParam)).toString();	
		}
		return DEVICE_OPERATION_ERROR;
	}
	public String pauseTask(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		if ( null == deviceId||"".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()))
		{
			return responseToJSON(arnhemDeviceAdpater.pauseTask(deviceId, scannerTaskUniParam)).toString();	
		}
		return DEVICE_OPERATION_ERROR;
	}
	public String stopTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
		if ( null == deviceId||"".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return responseToJSON(arnhemDeviceAdpater.stopTask(deviceId, scannerTaskUniParam)).toString();	
		}
		return DEVICE_OPERATION_ERROR;
	}
	/**
	 * 根据任务Id获取当前状态
	 * @param deviceId
	 * @param scannerTaskUniParam 填充taskId
	 * @return
	 */
	public String getStatusByTaskId(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
		if ( null==deviceId||"".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return responseToJSON(arnhemDeviceAdpater.getStatusByTaskId(deviceId, scannerTaskUniParam)).toString();
		}
		return DEVICE_OPERATION_ERROR;
	}
	/**
	 * 获取任务进度
	 * @param deviceId
	 * @param scannerTaskUniParam 安恒填充taskID，websoc填充virtualgroupID
	 * @return
	 */
	public String getProgressById(String deviceId,ScannerTaskUniParam scannerTaskUniParam) {
		if ( null == deviceId || "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName() == null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return responseToJSON(arnhemDeviceAdpater.getProgressByTaskId(deviceId,scannerTaskUniParam)).toString();
			
		} else if (DeviceAdapterConstant.DEVICE_SCANNER_WEBSOC.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim())) {
			String response = websocDeviceAdapter.getProgressByVirtualGroupId(deviceId, scannerTaskUniParam);
			JSONObject responseObject = JSONObject.fromObject(response);
			if ("0".equalsIgnoreCase(responseObject.getString("code"))) {
				responseObject.put("status", "Success");
			}else {
				responseObject.put("status", "fail");
			}
	        return response.toString();
		}
		return DEVICE_OPERATION_ERROR;		
	}
	public String getTaskPercentById(String deviceId, ScannerTaskUniParam scannerTaskUniParam) {
		if ( null == deviceId || "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName() == null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) ){
			float percentProgress = 0;
			JSONObject jsonObject = new JSONObject();
			String progressArnhem =  arnhemDeviceAdpater.getProgressByTaskId(deviceId,scannerTaskUniParam);
			System.out.println("progress"+progressArnhem);
			try {
				 SAXReader reader = new SAXReader();
				 Document document = reader.read(IOUtils.toInputStream(progressArnhem));
				 Element rootElement = document.getRootElement();
				 if("success".equalsIgnoreCase(rootElement.attributeValue("value").toString())){
					 
					 float floatProgress = Float.parseFloat(rootElement.element("TaskProgress").getTextTrim())/101;
					 percentProgress = (float)(Math.round(floatProgress*100))/100;
					 jsonObject.put("status", "success");
					 jsonObject.put("TaskPercent", percentProgress);
					 return jsonObject.toString();
				 }else{
					 return responseToJSON(progressArnhem).toString();
				 }
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else if (DeviceAdapterConstant.DEVICE_SCANNER_WEBSOC.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim())) {
			String progressWebsoc = websocDeviceAdapter.getProgressByVirtualGroupId(deviceId, scannerTaskUniParam);
			JSONObject responseObject = JSONObject.fromObject(progressWebsoc);
	        JSONObject resultObject = responseObject.getJSONObject("result");
			if ("0".equalsIgnoreCase(responseObject.getString("code"))) {
				float floatProgress = ((float)resultObject.getInt("sites_done_count"))/(float)resultObject.getInt("sites_count");
				DecimalFormat decimalFormat=new DecimalFormat(".00");
				String percentPrograss = decimalFormat.format(floatProgress);
				responseObject.clear();
				responseObject.put("status", "success");
				responseObject.put("percentPrograss", percentPrograss);
			}else {
				responseObject.clear();
				responseObject.put("status", "fail");
			}
			return responseObject.toString();
		}
		return DEVICE_OPERATION_ERROR;			
	}
	/**
	 * 任务负载查询
	 * @param deviceId
	 * @return 任务负载信息 xml
	 */
	public String getTaskLoadInfo(String deviceId){
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName() == null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return responseToJSON(arnhemDeviceAdpater.getTaskLoadInfo(deviceId)).toString();
		}
		return DEVICE_OPERATION_ERROR;		
	}
	/**
	 * 根据任务Id获取任务执行结果数 填充taskId
	 * @param deviceId
	 * @param scannerTaskUniParam
	 * @return
	 */
	public  String getResultCountByTaskID(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()))
		{
			return arnhemDeviceAdpater.getResultCountByTaskID(deviceId, scannerTaskUniParam);
		}
		return DEVICE_OPERATION_ERROR;
	}
	/**
	 * 根据任务Id分页获取扫描结果
	 * @param deviceId 
	 * @param scannerTaskUniParam 填充deviceId，productId，ProductID为引擎功能，对应关系为1=漏洞扫描，2=木马检测，3=篡改检测，4=关键字检测，5=可用性检测
	 * @param startNum 起始位置编码 建议为10
	 * @param size size<500
	 * @return
	 */
	public String getReportByTaskID(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.getReportByTaskID(deviceId,scannerTaskUniParam);
		}
		return DEVICE_OPERATION_ERROR;
	}
	/**
	 * 获取监测网站总数
	 * @param deviceId
	 * @return
	 */
	public String getWebsiteCount(String deviceId){
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) ){
			return responseToJSON(arnhemDeviceAdpater.getWebsiteCount(deviceId)).toString();
		}
		return DEVICE_OPERATION_ERROR;
	}
	/**
	 * 
	 * @param deviceId
	 * @param startNum
	 * @param size
	 * @return
	 */
	public  String getWebsiteList(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()))
		{
			return responseToJSON(arnhemDeviceAdpater.getWebsiteList(deviceId,scannerTaskUniParam)).toString();
		}
		return DEVICE_OPERATION_ERROR;
	}
	/**
	 * 根据网站id获取report总数
	 * @param deviceId
	 * @param scannerTaskUniParam 填充webid 即网站id
	 * @return
	 */
	public String GetReportCountByWebID(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.GetReportCountByWebID(deviceId, scannerTaskUniParam);
		}
		return DEVICE_OPERATION_ERROR;
	}
	public String GetReportIDListByWebId(String deviceId, ScannerTaskUniParam scannerTaskUniParam,int StartNum,int Size){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.GetReportIDListByWebId(deviceId, scannerTaskUniParam, StartNum, Size);
		}
		return DEVICE_OPERATION_ERROR;
	}
	/**
	 * 根据汇总结果id获取记录总数
	 * @param deviceId
	 * @param scannerTaskUniParam
	 */
	public String getResultCountByReportID(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.getResultCountByReportID(deviceId, scannerTaskUniParam);
		}
		return DEVICE_OPERATION_ERROR;		
	}
	/**
	 * 根据reportId分页获取信息
	 * @param deviceId
	 * @param scannerTaskUniParam 填充reportId
	 * @param startNum
	 * @param size
	 * @return
	 */
	public String getReportByReportIdInP(String deviceId, ScannerTaskUniParam scannerTaskUniParam,int startNum,int size){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.getReportByReportIdInP(deviceId, scannerTaskUniParam, startNum, size);
		}
		return DEVICE_OPERATION_ERROR;	
	}
	/**
	 * 获取report总数
	 * @param deviceId
	 * @return
	 */
	public String getReportCount(String deviceId){
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()))
		{
			return arnhemDeviceAdpater.getReportCount(deviceId);
		}
		return DEVICE_OPERATION_ERROR;	
	}
	/**
	 * 获取report id列表
	 * @param deviceId
	 * @param scannerTaskUniParam
	 * @param startNum
	 * @param size
	 * @return
	 */
	public String getReportIDList(String deviceId, ScannerTaskUniParam scannerTaskUniParam, int startNum, int size){
		if (null==deviceId||deviceId.length()<=0|| getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.getReportIDList(deviceId, scannerTaskUniParam, startNum, size);
		}
		return DEVICE_OPERATION_ERROR;			
	}
	/**
	 * 获取漏洞库信息
	 * @param deviceId
	 * @return
	 */
	public String  getIssueRepositoryList(String deviceId){
		if (null==deviceId|| deviceId.length()<=0 || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.getIssueRepositoryList(deviceId);
		}
		return DEVICE_OPERATION_ERROR;	
	}
	

	
	public String getSites(int resourceId, int deviceId) {
		return nsfocusWAFAdapter.getSites(resourceId, deviceId);
	}
	public String getSitesInResource(int resourceId) {
		return nsfocusWAFAdapter.getSitesInResource(resourceId);
	}
	
	public String createSiteInResource(int resourceId,JSONObject jsonObject) {
		return nsfocusWAFAdapter.createSite(resourceId, jsonObject);
	}
	public String createSite(int resourceId, int deviceId,JSONObject jsonObject) {
		return nsfocusWAFAdapter.createSite(resourceId, deviceId, jsonObject);
	}
	public String alterSite(int resourceId, int deviceId, JSONObject jsonObject) {
		return nsfocusWAFAdapter.alterSite(resourceId, deviceId, jsonObject);
	}
	
	public String createVirtSite(int resourceId,int deviceId,JSONObject jsonObject) {
		return nsfocusWAFAdapter.createVirtSite(resourceId, deviceId, jsonObject);
	}
	public String createVSiteInResource(int resourceId,JSONObject jsonObject) {
		return nsfocusWAFAdapter.createVirtSite(resourceId, jsonObject);
	}
	
	public String getVirtSite(int resourceId, int deviceId, JSONObject jsonObject) {
		return nsfocusWAFAdapter.getVirtSite(resourceId, deviceId, jsonObject);
	}
	
	public String alterVSite(int resourceId, int deviceId, JSONObject jsonObject) {
		return nsfocusWAFAdapter.alterVSite(resourceId, deviceId, jsonObject);
	}
	public String deleteVSite(int resourceId, int deviceId, JSONObject jsonObject) {
		return nsfocusWAFAdapter.deleteVSite(resourceId, deviceId, jsonObject);
	}
	public String deleteVSiteInResource(int resourceId, JSONObject jsonObject) {
		return nsfocusWAFAdapter.deleteVSite(resourceId, jsonObject);
	}	
	public String getWafLogWebsec(List<String> dstIpList) {
		return nsfocusWAFAdapter.getWafLogWebsec(dstIpList);
	}
	
	public String getWafLogWebsecInTime(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getWafLogWebsecInTime(jsonObject);
	}
	
	public String getAllWafLogWebsecInTime(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getAllWafLogWebsecInTime(jsonObject);
	}
	
	public String getAllWafLogWebsecThanCurrentId(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getAllWafLogWebsecThanCurrentId(jsonObject);
	}
	
	public String getWafLogWebsecCurrent(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getWafLogWebsecCurrent(jsonObject);
	}
	
	public String getWafLogWebsecById(String logId) {
		if (null==logId||logId.length()<=0){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "fail");
			jsonObject.put("message", "LogId is null!!!");
			return jsonObject.toString();
		}
		return nsfocusWAFAdapter.getWafLogWebSecById(logId);
	}

	
	public String getWafLogArp(List<String> dstIpList) {
		return nsfocusWAFAdapter.getWafLogArp(dstIpList);
	}
	
	public String getWafLogArpById(String logId) {
		if (null==logId || logId.length()<=0){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "fail");
			jsonObject.put("message", "LogId is null!!!");
			return jsonObject.toString();
		}
		return nsfocusWAFAdapter.getWafLogArpById(logId);
	}
	
	public String getWafLogArpInTime(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getWafLogArpInTime(jsonObject);
	}
	
	public String getWafLogDDOS(List<String> dstIpList) {
		return nsfocusWAFAdapter.getWafLogDDOS(dstIpList);
	}
	
	public String getWafLogDDOSById(String logId) {
		if (null==logId || "".equals(logId)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "fail");
			jsonObject.put("message", "LogId is null!!!");
			return jsonObject.toString();
		}
		return nsfocusWAFAdapter.getWafLogDDOSById(logId);
	}
	
	public String getWafLogDDOSInTime(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getWaflogDDOSInTime(jsonObject);
	}
	
	public String getWafLogDeface(List<String> dstIpList) {
		return nsfocusWAFAdapter.getWafLogDeface(dstIpList);
	}
	
	public String getWafLogDefaceById(String logId) {
		if (null==logId || "".equals(logId)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "fail");
			jsonObject.put("message", "LogId is null!!!");
			return jsonObject.toString();
		}
		return nsfocusWAFAdapter.getWafLogDefaceById(logId);
	}
	
	public String getWafLogDefaceInTime(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getWafLogDefaceInTime(jsonObject);
	}
	
	public String getWafEventTypeCount() {
		return nsfocusWAFAdapter.getEventTypeCount();
	}
	
	public String getWafEventTypeCountByDay(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getEventTypeCountByDay(jsonObject);
	}
	
	public String getWafEventTypeCountByMonth(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getEventTypeCountByMonth(jsonObject);
	}
	
	public String getWafEventTypeCountInTime(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getEventTypeCountInTime(jsonObject);
	}
	public String getEventTypeCountInTimeCurrent(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getEventTypeCountInTimeCurrent(jsonObject);
	}
	public String getWafLogWebSecDstIpList() {
		return nsfocusWAFAdapter.getWafLogWebSecDstIpList();
	}
	
	public String getWafLogWebSecSrcIpList() {
		return nsfocusWAFAdapter.getWafLogWebSecSrcIpList();
	}
	
	public String getAlertLevelCount() {
		return nsfocusWAFAdapter.getAlertLevelCount();
	}
	
	public String getAlertLevelCountByHour(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getAlertLevelCountByHour(jsonObject);
	}
	
	public String getAlertLevelCountByMonth(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getAlertLevelCountByMonth(jsonObject);
	}
	
	public String getLocationFromIp(JSONObject jsonObject) {
		return nsfocusWAFAdapter.getLocationFromIp(jsonObject);
	}
	
	public String getWafPublicIpListInResource(int resourceId) {
		return nsfocusWAFAdapter.getPublicIpListInResource(resourceId);
		
	}
}
