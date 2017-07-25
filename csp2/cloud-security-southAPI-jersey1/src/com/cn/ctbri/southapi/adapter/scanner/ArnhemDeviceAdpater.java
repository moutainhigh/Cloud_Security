package com.cn.ctbri.southapi.adapter.scanner;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import com.cn.ctbri.southapi.adapter.config.DeviceConfigInfo;
import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;


import net.sf.json.JSONArray;

public class ArnhemDeviceAdpater{
	
	protected static String ARNHEM_FACTORY = "Arnhem";
	
	public static HashMap<String, DeviceConfigInfo> mapDeviceConfigInfoHashMap = new HashMap<String, DeviceConfigInfo>();
	public static HashMap<String, ArnhemDeviceOperation> mapArnhemDeviceOperation = new HashMap<String, ArnhemDeviceOperation>();

	public ArnhemDeviceOperation arnhemDeviceOperation = null;
	
	
	public boolean initDeviceAdpater(HashMap<String, DeviceConfigInfo> mapDeviceConfigInfoHashMap)
	{
		//首次连接服务器获取Session	
		ArnhemDeviceAdpater.mapDeviceConfigInfoHashMap.putAll(mapDeviceConfigInfoHashMap);
		Iterator<Entry<String, DeviceConfigInfo>> deviceConfigInfoIterator = mapDeviceConfigInfoHashMap.entrySet().iterator();
		while (deviceConfigInfoIterator.hasNext()) {
			DeviceConfigInfo deviceConfigInfo = deviceConfigInfoIterator.next().getValue();
			if(ARNHEM_FACTORY.equalsIgnoreCase(deviceConfigInfo.getScannerFactory())){
			    arnhemDeviceOperation = new ArnhemDeviceOperation();
			    if(!arnhemDeviceOperation.createSessionId(deviceConfigInfo.getScannerUserName(),deviceConfigInfo.getScannerPassword(),deviceConfigInfo.getScannerWebUrl())){
			    	return false;
			    }
			    mapArnhemDeviceOperation.put(deviceConfigInfo.getDeviceID(), arnhemDeviceOperation);
			}
		}
		return true;
	}

	
	public String getDeviceId() {
		Set<?> deviceIdSet = mapArnhemDeviceOperation.keySet();
		JSONArray jsonArray = JSONArray.fromObject(deviceIdSet);
		return jsonArray.toString();
	}
	/**
	 * 根据deviceId在安恒设备map中获取指定设备
	 * @param deviceId
	 * @return
	 */
	private  ArnhemDeviceOperation getDeviceById(String deviceId)
	{
		ArnhemDeviceOperation operation = mapArnhemDeviceOperation.get(deviceId);
		return operation;
	}
	private DeviceConfigInfo getDeviceConfigById(String deviceId){
		return mapDeviceConfigInfoHashMap.get(deviceId);	
	}
	
	public String getState(String deviceId) {
		return getDeviceById(deviceId).getState(getDeviceConfigById(deviceId).getScannerEngineAPI());
	}
	
	public String getTemplate(String deviceId){
		return getDeviceById(deviceId).getTemplate(getDeviceConfigById(deviceId).getScannerEngineAPI());
	}
	
	public String disposeScanTask(String deviceId, ScannerTaskUniParam scannerTaskUniParam) {
		return getDeviceById(deviceId).disposeScanTask(scannerTaskUniParam);
	}
	
	public  String removeTask(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		//创建路径
		return getDeviceById(deviceId).removeTask(scannerTaskUniParam);
	}
	
	/**
	 * 序号:4.3.3
	 * 功能描述：启动下发的任务
	 * 参数描述:String sessionId, String taskId
	 *		 @time 2015-01-08
	 */
	public  String startTask(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		//创建路径
		return getDeviceById(deviceId).startTask(scannerTaskUniParam);
	}
	
	/**
	 * 序号:4.3.3
	 * 功能描述：暂停下发的任务
	 * 参数描述:String sessionId, String taskId
	 * @time 2015-10-19
	 */
	public  String pauseTask(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		//创建路径
		return getDeviceById(deviceId).pauseTask(scannerTaskUniParam);
	}
	/**
	 * 序号:4.3.3
	 * 功能描述：停止下发的任务
	 * 参数描述:String sessionId, String taskId
	 * @time 2015-10-19
	 */
	public  String stopTask(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
		//创建路径
		return getDeviceById(deviceId).stopTask(scannerTaskUniParam);
	}
	/**
	 * 4.3.4
	 * 根据任务id获取任务当前状态
	 * @param sessionId 会话id
	 * @param taskId 任务id
	 * @return 任务状态代码
	 */
	public  String getStatusByTaskId(String deviceId, ScannerTaskUniParam scannerTaskUniParam) {
		//创建路径
		return getDeviceById(deviceId).getStatusByTaskId(scannerTaskUniParam);
	}
	
	/**
	 * 序号:4.3.5
     * 根据任务id获取任务当前进度
     * @param sessionId 会话id
     * @param taskId 任务id
     * @param ProductId
     * @return 任务状态代码
     */
    public  String getProgressByTaskId(String deviceId, ScannerTaskUniParam scannerTaskUniParam) {
        return getDeviceById(deviceId).getProgressByTaskId(scannerTaskUniParam);
    }

    /**
     * 4.3.6
     * 任务负载查询
     * @param sessionId 会话id
     * @param 引擎接口
     * @return 任务状态代码
     */  
    public  String getTaskLoadInfo(String deviceId){
    	return getDeviceById(deviceId).getTaskLoadInfo();
	}
    public  String distortChangeActive(String deviceId, ScannerTaskUniParam scannerTaskUniParam){
    	return getDeviceById(deviceId).distortChangeActive(scannerTaskUniParam);
    }    
	/**
	 * 4.4.3
	 * 功能描述：根据任务ID获取任务执行结果数
	 * 参数描述:String sessionId 回话ID, String taskId任务ID
	 *		 @time 2015-01-05
	 */
	public  String getResultCountByTaskID(String deviceId, ScannerTaskUniParam scannerTaskUniParam) {
		return getDeviceById(deviceId).getResultCountByTaskID(scannerTaskUniParam);
     }
	/**
	 * 4.4.4
	 * 功能描述：根据任务ID分页获取扫描结果
	 * 参数描述:String sessionId 回话ID, String taskId任务ID, String productId 引擎功能,
	 * 		 int startNum 起始数, int size 每页大小
	 * @time 2015-01-05
	 */
    public  String getReportByTaskID(String deviceId, ScannerTaskUniParam scannerTaskUniParam) {
    	return getDeviceById(deviceId).getReportByTaskID(scannerTaskUniParam);
    }
	/**
	 * 4.4.5
     * 获取监测网站总数
     * @param sessionId 会话id
     * @return 网站总数
     */
    public  String getWebsiteCount(String deviceId) {
		return getDeviceById(deviceId).getWebsiteCount();
	}

	/**
	 * 4.4.6
     * 获取监测网站列表
     * @param sessionId 会话id
     * @return 网站列表及问题分布
     */
    public  String getWebsiteList(String deviceId,ScannerTaskUniParam scannerTaskUniParam){
    	return getDeviceById(deviceId).getWebsiteList(scannerTaskUniParam);
    }
    /**
	 * 4.4.7
     * 根据网站id获取report总数
     * @param webId 网站id(和taskid不同)
     * @return 网站列表及问题分布
     */
    public  String  GetReportCountByWebID(String deviceId, ScannerTaskUniParam scannerTaskUniParam) {
    	return getDeviceById(deviceId).GetReportCountByWebID(scannerTaskUniParam);
	}
    /**
	 * 4.4.7
     * 根据网站id获取reportid列表
     * @param taskNum 网站id(和taskid不同)
     * @return 网站列表及问题分布
     */
    public  String GetReportIDListByWebId(String deviceId, ScannerTaskUniParam scannerTaskUniParam,int StartNum,int Size) {
		return getDeviceById(deviceId).GetReportIDListByWebId(scannerTaskUniParam, StartNum, Size);
    }
    /**
     * 4.4.9
     * 根据汇总结果id获取记录总数
     * @param reportId 汇总结果id(任务结束时，由监测平台告知外部系统)
     * @return 
     */
    public  String getResultCountByReportID(String deviceId, ScannerTaskUniParam scannerTaskUniParam) {
		return getDeviceById(deviceId).getResultCountByReportID(scannerTaskUniParam);
	}
    /**
     * 4.4.10
     * 根据汇总结果id直接分页获取信息
     * @param reportID
     * @param startNum
     * @param size
     * @return 
     */
    public  String getReportByReportIdInP(String deviceId, ScannerTaskUniParam scannerTaskUniParam,int startNum,int size) {
		return getDeviceById(deviceId).getReportByReportIdInP(scannerTaskUniParam, startNum, size);
    }
    /**
     * 获取report总数
     * id:4.4.11
     * @param sessionId
     * @return
     */
    public  String getReportCount(String deviceId){
    	return getDeviceById(deviceId).getReportCount();
    }
    /**
     * id:4.12
     * 获取report id列表
     * @param sessionId
     * @param ProductID
     * @param startNum
     * @param size
     * @return
     */
    public  String getReportIDList(String deviceId, ScannerTaskUniParam scannerTaskUniParam,int startNum,int size) {
    	return getDeviceById(deviceId).getReportIDList(scannerTaskUniParam, startNum, size);
	}
    
    /**
     * 序号：4.4.13
     * 获取引擎的存活状态(性能数据和版本号)
     * @param sessionId 会话id
     * @param taskId 任务id
     * @return 任务状态代码
     */
	public  String getEngineState(String deviceId) {
        //创建路径
    	String scannerEngineAPI = null;
		ArnhemDeviceOperation operation = null;
		try {
			scannerEngineAPI = getDeviceConfigById(deviceId).getScannerEngineAPI();
			operation = getDeviceById(deviceId);

		} catch (NullPointerException e) {
			System.err.println("scannerEngineAPI="+scannerEngineAPI);
			System.err.println("operation="+operation);
			e.printStackTrace();

		}
		return operation.getEngineState(scannerEngineAPI);
    	
    }
    /**
     * id:4.4.14
     * 获取漏洞库的信息
     * @param sessionId
     * @return
     */
    public  String getIssueRepositoryList(String deviceId) {
		return getDeviceById(deviceId).getIssueRepositoryList();
	}
    /**
     * id:4.4.15
     * 更新工具版本
     * @param sessionId
     * @return
     */
    public  String updateEngine(String deviceId){
    	return getDeviceById(deviceId).updateEngine();
    }
    /**
     * 4.4.16
     * 生成任务报表文件
     * @param sessionId
     * @param taskId
     * @param reportType = (A1,B3,B5)
     * @return
     */
    		
    public  String createReport(String deviceId, ScannerTaskUniParam scannerTaskUniParam,String reportType) {
		return getDeviceById(deviceId).createReport(scannerTaskUniParam, reportType);
	}
}