package com.cn.ctbri.southapi.adpater.manager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;







import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;










import com.cn.ctbri.southapi.adpater.config.DeviceConfigInfo;
import com.cn.ctbri.southapi.adpater.config.ScannerTaskUniParam;



/**
 * 设备适配管理
 * @version 
 * @author shao
 * @time 2015-11-04
 */

public class DeviceAdpaterManager {
	private static final String LOAD_DEVICE_ERROR = "{\"status\":\"fail\",\"message\":\"Load DeviceConfig failed!!\"}";
	private static final String INIT_DEVICE_ERROR = "{\"status\":\"fail\",\"message\":\"Init DeviceAdapter failed!!\"}";
	private static final String DEVICE_OPERATION_ERROR = "{\"status\":\"fail\",\"message\":\"This device does not support the operation\"}";
	
	public static HashMap<String, DeviceConfigInfo> mapDeviceConfigInfoHashMap = new HashMap<String, DeviceConfigInfo>();
	public static ArnhemDeviceAdpater arnhemDeviceAdpater = new ArnhemDeviceAdpater();
	public static WebsocDeviceAdapter websocDeviceAdapter = new WebsocDeviceAdapter();
	public static DeviceConfigInfo getDeviceAdapterAttrInfo(String deviceId)
	{
		return mapDeviceConfigInfoHashMap.get(deviceId);
	}
//	public DeviceAdpaterManager(){
//		loadDeviceAdpater();
//	}
	
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
        JSONObject responseObject = new JSONObject().fromObject(json.toString());
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
	        mapDeviceConfigInfoHashMap.clear();
	        while (iter.hasNext()) {
	        	  Element elementDeviceScanner = (Element) iter.next();   
	        	  DeviceConfigInfo daaInfo = new DeviceConfigInfo();
	        	  //daaInfo.scannerID = elementDeviceScanner.GET;;
	        	  for(Iterator<?> it = elementDeviceScanner.elementIterator();it.hasNext();){      
	                  Element element = (Element) it.next();   
	                 // do something    
	                  daaInfo.setDeviceType(DeviceAdapterConstant.DEFALUT_DEVICE_TYPE_SCANNER);
	                  if ( "DeviceID".equalsIgnoreCase(element.getName())) {
	                	  if ( null == element.getTextTrim() || "".equals(element.getTextTrim())){ 	
	                		  	System.out.println("Config Error: DeviceID is null!!!");
	                		  	continue;
	                	  }else{
	                		  daaInfo.setDeviceID(element.getTextTrim());  
	                	  }
	                  }
	                  if ( "ScannerFactory".equalsIgnoreCase(element.getName()) ){
	                	  if( null == element.getTextTrim() || "".equals(element.getTextTrim()) ){
	                		  System.out.println("Config Error: ScannerFactory is null!!!");
	                		  continue;
	                	  }else{
	                		  daaInfo.setScannerFactory(element.getText());
	                	  }
	                  }
	                  if ( "ScannerFactoryName".equalsIgnoreCase(element.getName()) ){
	                	  if(null == element.getTextTrim() || "".equals(element.getTextTrim())){
	                		  System.out.println("Config Error: ScannerFactoryName is null!!!");
	                		  continue;
	                	  }else{	                		  
	                		  daaInfo.setScannerFactoryName(element.getText());
	                	  }
	                  }
	                  if ( "ScannerWebUrl".equalsIgnoreCase(element.getName()) ){
	                	  if (null == element.getTextTrim() || "".equals(element.getTextTrim())) {
	                		  System.out.println("Config error: ScannerWebUrl is null!!!");
	                		  continue;
	                	  } else {
	                		  daaInfo.setScannerWebUrl(element.getText());
	                	  }
	                  }
	                  if ( "ScannerEngineAPI".equalsIgnoreCase(element.getName()) ){
	                	  if (null == element.getTextTrim() || "".equals(element.getTextTrim())) {							
	                		  System.out.println("Config error: ScannerEngineAPI is null!!!");
	                		  continue;
	                	  }else{	                		  
	                		  daaInfo.setScannerEngineAPI(element.getText());
	                	  }
	                  }
	                  if ( "ScannerUserName".equalsIgnoreCase(element.getName()) ){
	                	  if (null == element.getTextTrim() || "".equals(element.getTextTrim())) {
							System.out.println("Config error: ScannerUserName is null!!!");
							continue;
	                	  } else {
	                		  daaInfo.setScannerUserName(element.getText());	                		
	                	  }
	                  }
	                  if ( "ScannerPassword".equalsIgnoreCase(element.getName()) ){ 
	                	 if (null == element.getTextTrim() || "".equals(element.getTextTrim())) {
							System.out.println("Config error:ScannerPassword is null!!!");
							continue;
	                	 }else{
	                		 daaInfo.setScannerPassword(element.getText());							
	                	 }
	                  }
	              }
	        	  mapDeviceConfigInfoHashMap.put(daaInfo.getDeviceID(), daaInfo);
	        }
	        System.out.println(mapDeviceConfigInfoHashMap.entrySet());
	        return true;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return false;
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
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			String responseString = arnhemDeviceAdpater.getEngineState(deviceId);	
			System.out.println(responseString);
			try {
				SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(IOUtils.toInputStream(responseString));
				Element rootElement = document.getRootElement();
				List<?> nodes = rootElement.elements("EngineList");
				List<HashMap> engineList = new ArrayList<HashMap>();
	            for(Iterator it=nodes.iterator();it.hasNext();){
	            	Element engineStatElement = (Element) it.next();
	            	HashMap engineRateMap = new HashMap();
	            	
	            	if ("".equalsIgnoreCase(engineStatElement.elementTextTrim("IP"))||engineStatElement.elementTextTrim("IP")==null) {
						engineRateMap.put("ip", null);
					}else {
						String IP = engineStatElement.elementTextTrim("IP");
						engineRateMap.put("ip", IP);
					}
	            	
	            	if ("".equalsIgnoreCase(engineStatElement.elementTextTrim("CpuOccupancy"))
	            	||engineStatElement.elementTextTrim("CpuOccupancy")==null) {
	            		engineRateMap.put("cpuUsage", null);
					}else {
		            	String cpuUsage = engineStatElement.elementTextTrim("CpuOccupancy");
		            	engineRateMap.put("cpuUsage", cpuUsage);
		            	System.out.println("cpu"+cpuUsage);
					}
	            	
	            	if ("".equalsIgnoreCase(engineStatElement.elementTextTrim("MemoryFree"))
	            	||engineStatElement.elementTextTrim("MemoryFree")==null
	            	||"".equalsIgnoreCase(engineStatElement.elementTextTrim("MemoryTotal"))
	            	||engineStatElement.elementTextTrim("MemoryTotal")==null) {
	            		engineRateMap.put("memoryUsage", null);
					} else {
		            	float memoryFree = Float.parseFloat(engineStatElement.elementTextTrim("MemoryFree"));
		            	float memoryTotal = Float.parseFloat(engineStatElement.elementTextTrim("MemoryTotal"));
		            	float memoryUsage = memoryFree/memoryTotal;
		            	engineRateMap.put("memoryUsage", memoryUsage);
					}

	            	if ("".equalsIgnoreCase(engineStatElement.elementTextTrim("DiskFree"))
	    	        ||engineStatElement.elementTextTrim("DiskFree")==null
	    	        ||"".equalsIgnoreCase(engineStatElement.elementTextTrim("DiskTotal"))
	    	        ||engineStatElement.elementTextTrim("DiskTotal")==null) {
	            		engineRateMap.put("diskUsage", null);
					} else {
		            	float diskFree = Float.parseFloat(engineStatElement.elementTextTrim("DiskFree"));
		            	float diskTotal = Float.parseFloat(engineStatElement.elementTextTrim("DiskTotal"));
		            	float diskUsage = diskFree/diskTotal;
		            	engineRateMap.put("diskUsage", diskUsage);
					}	            	
	            	engineRateMap.put("getSpeed", 0);
	            	engineRateMap.put("sendSpeed", 0);
	            	
	            	engineList.add(engineRateMap);
	            	
	            }
	            JSONArray jsonArray = JSONArray.fromObject(engineList);
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put("status", "success");
	            jsonObject.put("StatRateList", jsonArray);
	            return jsonObject.toString();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	        JSONObject responseObject = new JSONObject().fromObject(response);
			if ("0".equalsIgnoreCase(responseObject.getString("code"))) {
				responseObject.put("status", "Success");
			}else {
				responseObject.put("status", "Fail");
			}
	        System.out.println(responseObject.toString());
	        return response.toString();
		}
		return DEVICE_OPERATION_ERROR;		
	}
	public String getTaskPercentById(String deviceId, ScannerTaskUniParam scannerTaskUniParam) {
		if ( null == deviceId || "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName() == null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) ){
			String percentProgress = null;
			String progressArnhem =  arnhemDeviceAdpater.getProgressByTaskId(deviceId,scannerTaskUniParam);
			System.out.println("progress"+progressArnhem);
			try {
				 SAXReader reader = new SAXReader();
				 Document document = reader.read(IOUtils.toInputStream(progressArnhem));
				 Element rootElement = document.getRootElement();
				 float floatProgress = Float.parseFloat(rootElement.element("TaskProgress").getTextTrim());
				 DecimalFormat decimalFormat = new DecimalFormat(".00");
				 percentProgress = decimalFormat.format(floatProgress);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("TaskProgress", percentProgress);
			return jsonObject.toString();
		} else if (DeviceAdapterConstant.DEVICE_SCANNER_WEBSOC.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim())) {
			String progressWebsoc = websocDeviceAdapter.getProgressByVirtualGroupId(deviceId, scannerTaskUniParam);
			JSONObject responseObject = new JSONObject().fromObject(progressWebsoc);
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
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
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
		if (null==deviceId|| "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.getIssueRepositoryList(deviceId);
		}
		return DEVICE_OPERATION_ERROR;	
	}
}
