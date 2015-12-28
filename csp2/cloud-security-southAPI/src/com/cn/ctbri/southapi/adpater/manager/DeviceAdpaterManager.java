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
	protected static String FILE_DEVICE_CONFIG = DeviceAdpaterManager.class.getResource("/DeviceConfig.xml").toString();
	
	public static HashMap<String, DeviceConfigInfo> mapDeviceConfigInfoHashMap = new HashMap<String, DeviceConfigInfo>();
	public static ArnhemDeviceAdpater arnhemDeviceAdpater = new ArnhemDeviceAdpater();
	public static WebsocDeviceAdapter websocDeviceAdapter = new WebsocDeviceAdapter();
		
	public static DeviceConfigInfo getDeviceAdapterAttrInfo(String deviceId)
	{
		return mapDeviceConfigInfoHashMap.get(deviceId);
	}
	
	
	/*
	 * 初始化
	 */
	public String loadDeviceAdpater()
	{
		if (!loadDeviceConfig(FILE_DEVICE_CONFIG)){
			return "{\"status\":\"fail\",\"message\":\"Load DeviceConfig failed!!\"}";
		}
		if (!initAllDeviceAdapter()){
			return "{\"status\":\"fail\",\"message\":\"Init DeviceAdapter failed!!\"}";
		}
		//加载各个适配器
		//安恒适配器
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
	
	private boolean loadDeviceConfig(String configFileName)
	{
		SAXReader reader = new SAXReader();
        // 加载XML
        Document doc;
		try {
			doc = reader.read(configFileName);
	        //加载SCANNER设备
	        List<?> list = doc.selectNodes("/DeviceAdapterConfig/DeviceList/DeviceScannerList/DeviceScanner");	
	        Iterator<?> iter = list.iterator();
	        while (iter.hasNext()) {
	        	  Element elementDeviceScanner = (Element) iter.next();   
	        	  DeviceConfigInfo daaInfo = new DeviceConfigInfo();
	        	  //daaInfo.scannerID = elementDeviceScanner.GET;;
	        	  for(Iterator<?> it = elementDeviceScanner.elementIterator();it.hasNext();){      
	                  Element element = (Element) it.next();   
	                 // do something    
	                  daaInfo.setDeviceType(DeviceAdapterConstant.DEFALUT_DEVICE_TYPE_SCANNER);
	                  if ( "DeviceID".equalsIgnoreCase(element.getName()))  daaInfo.setDeviceID(element.getText());
	                  if ( "ScannerFactory".equalsIgnoreCase(element.getName()) ) daaInfo.setScannerFactory(element.getText());
	                  if ( "ScannerFactoryName".equalsIgnoreCase(element.getName()) ) daaInfo.setScannerFactoryName(element.getText());
	                  if ( "ScannerWebUrl".equalsIgnoreCase(element.getName()) ) daaInfo.setScannerWebUrl(element.getText());
	                  if ( "ScannerEngineAPI".equalsIgnoreCase(element.getName()) ) daaInfo.setScannerEngineAPI(element.getText());
	                  if ( "ScannerUserName".equalsIgnoreCase(element.getName()) ) daaInfo.setScannerUserName(element.getText());
	                  if ( "ScannerPassword".equalsIgnoreCase(element.getName()) ) daaInfo.setScannerPassword(element.getText());
	              }
	        	  mapDeviceConfigInfoHashMap.put(daaInfo.getDeviceID(), daaInfo);
	        }
	        return true;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return false;
	}	
	public String getState(String deviceId) {
		if ("".equals(deviceId)||getDeviceAdapterAttrInfo(deviceId) == null){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ("安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName())){
			return arnhemDeviceAdpater.getState(deviceId);
		}
		return "";
	}
	public String disposeScanTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam)
	{	
		if ("".equals(deviceId) ||getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"code\":\"404\",\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ("安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()))
		{
			String s = arnhemDeviceAdpater.disposeScanTask(deviceId,scannerTaskUniParam);
			System.out.println(s);
			return s;
		}else if ("知道创宇".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName())) {
			return websocDeviceAdapter.disposeScanTask(deviceId,scannerTaskUniParam);
		}
		return "{\"code\":\"404\",\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	/**
	 * 获取任务配置模板
	 * @param deviceId
	 * @return
	 */
	public String  getTemplate(String deviceId) {
		if ("".equals(deviceId)||getDeviceAdapterAttrInfo(deviceId) == null){
			return "{\"status\":\"fail\",\"message\":\"Can not find device: "+deviceId+"\"}";
		}
		if ("安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			String s = arnhemDeviceAdpater.getTemplate(deviceId);
			System.out.println(s);
			return s;
		} else if ("知道创宇".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName())) {
			return "{\"status\":\"fail\",\"message\":\"This device does not support the operation\"}";
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}

	/**
	 * 1.1.4	获取性能数据和版本号
	 * @param deviceId 设备号，由设备配置文件提供
	 * @return
	 */
	public String getEngineStat(String deviceId){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getEngineState(deviceId);		
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	public String removeTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.removeTask(deviceId,scannerTaskUniParam);		
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	public String startTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam) {
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.startTask(deviceId, scannerTaskUniParam);	
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	public String pauseTask(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.pauseTask(deviceId, scannerTaskUniParam);	
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	public String stopTask(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.stopTask(deviceId, scannerTaskUniParam);	
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	/**
	 * 根据任务Id获取当前状态
	 * @param deviceId
	 * @param scannerTaskUniParam 填充taskId
	 * @return
	 */
	public String getStatusByTaskId(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getStatusByTaskId(deviceId, scannerTaskUniParam);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	/**
	 * 获取任务进度
	 * @param deviceId
	 * @param scannerTaskUniParam 安恒填充taskID，websoc填充virtualgroupID
	 * @return
	 */
	public String getProgressById(String deviceId,ScannerTaskUniParam scannerTaskUniParam) {
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName() == null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getProgressByTaskId(deviceId,scannerTaskUniParam);
			
		} else if ("知道创宇".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName())) {
			return websocDeviceAdapter.getProgressByVirtualGroupId(deviceId, scannerTaskUniParam);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";		
	}
	/**
	 * 任务负载查询
	 * @param deviceId
	 * @return 任务负载信息 xml
	 */
	public String getTaskLoadInfo(String deviceId){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName() == null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getTaskLoadInfo(deviceId);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";		
	}
	/**
	 * 根据任务Id获取任务执行结果数 填充taskId
	 * @param deviceId
	 * @param scannerTaskUniParam
	 * @return
	 */
	public  String getResultCountByTaskID(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getResultCountByTaskID(deviceId, scannerTaskUniParam);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
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
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getReportByTaskID(deviceId,scannerTaskUniParam);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	/**
	 * 获取监测网站总数
	 * @param deviceId
	 * @return
	 */
	public String getWebsiteCount(String deviceId){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getWebsiteCount(deviceId);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	/**
	 * 
	 * @param deviceId
	 * @param startNum
	 * @param size
	 * @return
	 */
	public  String getWebsiteList(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getWebsiteList(deviceId,scannerTaskUniParam);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	/**
	 * 根据网站id获取report总数
	 * @param deviceId
	 * @param scannerTaskUniParam 填充webid 即网站id
	 * @return
	 */
	public String GetReportCountByWebID(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.GetReportCountByWebID(deviceId, scannerTaskUniParam);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	public String GetReportIDListByWebId(String deviceId, ScannerTaskUniParam scannerTaskUniParam,int StartNum,int Size){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.GetReportIDListByWebId(deviceId, scannerTaskUniParam, StartNum, Size);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";
	}
	/**
	 * 根据汇总结果id获取记录总数
	 * @param deviceId
	 * @param scannerTaskUniParam
	 */
	public String getResultCountByReportID(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getResultCountByReportID(deviceId, scannerTaskUniParam);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";		
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
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getReportByReportIdInP(deviceId, scannerTaskUniParam, startNum, size);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";	
	}
	/**
	 * 获取report总数
	 * @param deviceId
	 * @return
	 */
	public String getReportCount(String deviceId){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getReportCount(deviceId);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";	
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
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getReportIDList(deviceId, scannerTaskUniParam, startNum, size);
		}
		return "{\"status\":\"fail\",\"message\":\"Can not find device\"}";			
	}
	/**
	 * 获取漏洞库信息
	 * @param deviceId
	 * @return
	 */
	public String  getIssueRepositoryList(String deviceId){
		if ( "".equals(deviceId) || getDeviceAdapterAttrInfo(deviceId)==null ){
			return "{\"status\":\"fail\",\"message\":\"can not find device: "+deviceId+"\"}";
		}
		if ( "安恒".equals(getDeviceAdapterAttrInfo(deviceId).getScannerFactoryName()) )
		{
			return arnhemDeviceAdpater.getIssueRepositoryList(deviceId);
		}
		return "";	
	}
}
