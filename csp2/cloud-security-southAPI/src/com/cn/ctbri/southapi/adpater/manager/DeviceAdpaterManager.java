package com.cn.ctbri.southapi.adpater.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


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
			return arnhemDeviceAdpater.getState(deviceId);
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
			return arnhemDeviceAdpater.getTemplate(deviceId);
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
	public String removeTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
		if ( null==deviceId||"".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.removeTask(deviceId,scannerTaskUniParam);		
		}
		return DEVICE_OPERATION_ERROR;
	}
	public String startTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam) {
		if ( null==deviceId||"".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()))
		{
			return arnhemDeviceAdpater.startTask(deviceId, scannerTaskUniParam);	
		}
		return DEVICE_OPERATION_ERROR;
	}
	public String pauseTask(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		if ( null == deviceId||"".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()))
		{
			return arnhemDeviceAdpater.pauseTask(deviceId, scannerTaskUniParam);	
		}
		return DEVICE_OPERATION_ERROR;
	}
	public String stopTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
		if ( null == deviceId||"".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return errorDevieInfo(deviceId);
		}
		if ( DeviceAdapterConstant.DEVICE_SCANNER_ARNHEM.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim()) )
		{
			return arnhemDeviceAdpater.stopTask(deviceId, scannerTaskUniParam);	
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
			return arnhemDeviceAdpater.getStatusByTaskId(deviceId, scannerTaskUniParam);
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
			return arnhemDeviceAdpater.getProgressByTaskId(deviceId,scannerTaskUniParam);
			
		} else if (DeviceAdapterConstant.DEVICE_SCANNER_WEBSOC.equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactory().trim())) {
			return websocDeviceAdapter.getProgressByVirtualGroupId(deviceId, scannerTaskUniParam);
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
			return arnhemDeviceAdpater.getTaskLoadInfo(deviceId);
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
			return arnhemDeviceAdpater.getWebsiteCount(deviceId);
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
			return arnhemDeviceAdpater.getWebsiteList(deviceId,scannerTaskUniParam);
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
