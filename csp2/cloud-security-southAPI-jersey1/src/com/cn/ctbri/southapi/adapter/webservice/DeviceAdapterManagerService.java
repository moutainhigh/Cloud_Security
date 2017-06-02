package com.cn.ctbri.southapi.adapter.webservice;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.codehaus.jettison.json.JSONException;
import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;
import com.cn.ctbri.southapi.adapter.manager.DeviceAdpaterManager;


@Path("adapter")
public class DeviceAdapterManagerService {
	private static DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
	
	//设备为空时的错误提示函数
	private String errNullScannerDevice() {
		JSONObject errJsonObject = new JSONObject();
		errJsonObject.put("status", "failed");
		errJsonObject.put("message", "The deviceId is null.");
		return errJsonObject.toString();
	}
	
	
	//设备resourceId或deviceId为空或者不在列表内的报错
	private String errNullWafDevice() {
		JSONObject errJsonObject = new JSONObject();
		errJsonObject.put("status", "failed");
		errJsonObject.put("message", "The resourceId or deviceId is null.");
		return errJsonObject.toString();
	}
	
	//加载设备
	@GET
	@Path("/loadDeviceAdapter")
	@Produces(MediaType.APPLICATION_JSON)
	public String loadDeviceAdapter(){
		return deviceAdpaterManager.loadDeviceAdpater();
	}
	
//扫描器类
	//加载单个设备
	@POST
	@Path("/loadDeviceAdapter")
	@Produces(MediaType.APPLICATION_JSON)
	public String loadDeviceAdapter(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.loadDeviceAdapter(deviceId);
	}
	@GET
	@Path("/getDeviceId")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDeviceId() {
		return deviceAdpaterManager.getDeviceId();
	}
	//下发扫描任务
	@POST
	@Path("/disposeScanTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String disposeScanTask(String dataJson)throws JSONException{
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.disposeScanTask(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getProgressById")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProgressById(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.getProgressById(deviceId, scannerTaskUniParam);
	}
	
	//获取扫描模板
	@POST
	@Path("/getTemplate")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTemplate(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getTemplate(deviceId);
	}
	
	//获取引擎状态
	@POST
	@Path("/getEngineStat")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEngineStat(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getEngineStat(deviceId);
	}
	
	//获取引擎
	@POST
	@Path("/getEngineStatRate")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEngineStatRate(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getEngineStatRate(deviceId);
	}
	
	//移除任务
	@POST
	@Path("/removeTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeTask(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.removeTask(deviceId, scannerTaskUniParam);
	}
	
	//开始任务
	@POST
	@Path("/startTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String startTask(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.startTask(deviceId, scannerTaskUniParam);
	}
	
	//暂停任务
	@POST
	@Path("/pauseTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String pauseTask(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.pauseTask(deviceId, scannerTaskUniParam);
	}
	
	//停止任务
	@POST
	@Path("/stopTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String stopTask(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.stopTask(deviceId, scannerTaskUniParam);
	}
	
	
	@POST
	@Path("/getStatusByTaskId")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatusByTaskId(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getStatusByTaskId(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getTaskPercentById")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTaskPercentById(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.getTaskPercentById(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getTaskLoadInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTaskLoadInfo(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getTaskLoadInfo(deviceId);
	}
	@POST
	@Path("/getResultCountByTaskID")
	@Produces(MediaType.APPLICATION_JSON)
	public String getResultCountByTaskID(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.getResultCountByTaskID(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getReportByTaskID")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReportByTaskID(String dataJson) throws JSONException{
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.getReportByTaskID(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getWebsiteCount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWebsiteCount(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getWebsiteCount(deviceId); 	
	}
	@POST
	@Path("/getWebsiteList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWebsiteList(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if(jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullScannerDevice();
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.getWebsiteList(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getWaflogWebsecByIp")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogWebsecByIp(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
		return deviceAdpaterManager.getWafLogWebsec(dstIpList);
	}
	@POST
	@Path("/getWaflogWebsecById")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogWebsecById(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String logId = jsonObject.getString("logId");
		return deviceAdpaterManager.getWafLogWebsecById(logId);
	}
	@POST
	@Path("/getWaflogWebsecInTime")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogWebsecInTime(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getWafLogWebsecInTime(jsonObject);
	}
	
	@POST
	@Path("/getAllWafLogWebsecInTime")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllWafLogWebsecInTime(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getAllWafLogWebsecInTime(jsonObject);
	}
	
	@POST
	@Path("/getAllWafLogWebsecThanCurrentId")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllWafLogWebsecThanCurrentId(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getAllWafLogWebsecThanCurrentId(jsonObject);
	}
	
	@POST
	@Path("/getWafLogWebsecCurrent")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogWebsecCurrent(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getWafLogWebsecCurrent(jsonObject);
	}
	@POST
	@Path("/getWaflogArpByIp")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogArpByIp(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		List<String> dstIpList = (List<String>)jsonObject.get("dstIp");
		return deviceAdpaterManager.getWafLogArp(dstIpList);
	}
	@POST
	@Path("/getWaflogArpById")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogArpById(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String logId = jsonObject.getString("logId");
		return deviceAdpaterManager.getWafLogArpById(logId);
	}
	@POST
	@Path("/getWaflogArpInTime")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogArpInTime(String dataJson) {
		JSONObject jsonObject = new JSONObject();
		return deviceAdpaterManager.getWafLogArpInTime(jsonObject);
	}
	
	@POST
	@Path("/getWaflogDdosByIp")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogDdosByIp(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		List<String>  dstIpList= (List<String>) jsonObject.get("dstIp");
		return deviceAdpaterManager.getWafLogDDOS(dstIpList);
	}
	@POST
	@Path("/getWaflogDdosById")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogDdosById(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String logId = jsonObject.getString("logId");
		return deviceAdpaterManager.getWafLogDDOSById(logId);
	}
	@POST
	@Path("/getWaflogDdosInTime")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWaflogDdosInTime(String dataJson) {
		JSONObject jsonObject = new JSONObject();
		return deviceAdpaterManager.getWafLogDDOSInTime(jsonObject);
	}
	
	@POST
	@Path("/getWaflogDefaceByIp")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogDefaceByIp(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		List<String> dstIpList = (List<String>) jsonObject.get("dstIp");
		return deviceAdpaterManager.getWafLogDeface(dstIpList);
	}
	@POST
	@Path("/getWaflogDefaceById")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogDefaceById(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String logId = jsonObject.getString("logId");
		return deviceAdpaterManager.getWafLogDefaceById(logId);
	}
	@POST
	@Path("/getWaflogDefaceInTime")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogDefaceInTime(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getWafLogDefaceInTime(jsonObject);
	}
	@GET
	@Path("/getWafEventTypeCount")
	public String getWafEventType() {
		return deviceAdpaterManager.getWafEventTypeCount();
	}
	@POST
	@Path("/getWafEventTypeCount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafEventType(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getWafEventTypeCountInTime(jsonObject);
	}
	@POST
	@Path("/getEventTypeCountInTimeCurrent")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEventTypeCountInTimeCurrent(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getEventTypeCountInTimeCurrent(jsonObject);		
	}
	@POST
	@Path("/getEventTypeCountByDay")
	public String getEventTypeCountByDay(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getWafEventTypeCountByDay(jsonObject);
	}
	
	@POST
	@Path("/getEventTypeCountByMonth")
	public String getWafEventTypeCountByMonth(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getWafEventTypeCountByMonth(jsonObject);
	}
	
	@GET
	@Path("/getWafAlertLevelCount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafAlertLevelCount() {
		return deviceAdpaterManager.getAlertLevelCount();
	}
	@POST
	@Path("/getWafAlertLevelCount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafAlertLevelCountInTime(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getAlertLevelCountByHour(jsonObject);
	}
	@POST
	@Path("/getWafAlertLevelCountByMonth")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAlertLevelCountByMonth(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getAlertLevelCountByMonth(jsonObject);
	}
	
	
	@GET
	@Path("/getWafLogWebSecDstIpList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogWebSecDstIpList(){
		return deviceAdpaterManager.getWafLogWebSecDstIpList();
	}
	
	@GET
	@Path("/getWafLogWebSecSrcIpList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogWebSecSrcIpList(){
		return deviceAdpaterManager.getWafLogWebSecSrcIpList();
	}
	

	
	@POST
	@Path("/getSites")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSites(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").length()<=0
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").length()<-0)
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		int deviceId = jsonObject.getInt("deviceId");
		return deviceAdpaterManager.getSites(resourceId, deviceId);
	}
	@POST
	@Path("/getSitesInResource")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSitesInResource(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").length()<=0)
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		return deviceAdpaterManager.getSitesInResource(resourceId);
	}
	@POST
	@Path("/createSite")
	@Produces(MediaType.APPLICATION_JSON)
	public String createSite(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").length()<=0
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").length()<=0)
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		int deviceId = jsonObject.getInt("deviceId");
		return deviceAdpaterManager.createSite(resourceId, deviceId, jsonObject);
	}
	@POST
	@Path("/createSiteInResource")
	@Produces(MediaType.APPLICATION_JSON)
	public String createSiteInResource(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").equals(""))
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		jsonObject.remove("resourceId");
		return deviceAdpaterManager.createSiteInResource(resourceId, jsonObject);
	}
	
	@PUT
	@Path("/alterSite")
	@Produces(MediaType.APPLICATION_JSON)
	public String alterSite(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		int resourceId = Integer.parseInt(jsonObject.getString("resourceId"));
		int deviceId = Integer.parseInt(jsonObject.getString("deviceId"));
		return deviceAdpaterManager.alterSite(resourceId, deviceId, jsonObject);
	}
	
	
	@POST
	@Path("/createVirtualSite")
	@Produces(MediaType.APPLICATION_JSON)
	public String createVirtualSite(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").length()<=0
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").length()<=0)
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		int deviceId = jsonObject.getInt("deviceId");
		return deviceAdpaterManager.createVirtSite(resourceId, deviceId, jsonObject);
	}
	@POST
	@Path("/createVirtualSiteInResource")
	@Produces(MediaType.APPLICATION_JSON)
	public String createVirtualSiteInResouce(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").length()<=0)
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		jsonObject.remove("resourceId");
		return deviceAdpaterManager.createVirtSiteInResource(resourceId, jsonObject);
	}
	
	@POST
	@Path("/getVirtualSite")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVirtualSite(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").length()<=0
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").length()<=0)
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		int deviceId = jsonObject.getInt("deviceId");
		return deviceAdpaterManager.getVirtSite(resourceId, deviceId, jsonObject);
	}
	
	@PUT
	@Path("/alterVirtualSite")
	@Produces(MediaType.APPLICATION_JSON)
	public String alterVirtualSite(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").length()<=0
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").length()<=0)
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		int deviceId = jsonObject.getInt("deviceId");
		return deviceAdpaterManager.alterVSite(resourceId, deviceId, jsonObject);
	}
	@POST
	@Path("/deleteVirtualSiteInResource")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteVirtualSiteInResource(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").length()<=0)
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		jsonObject.remove("resourceId");
		return deviceAdpaterManager.deleteVSiteInResource(resourceId, jsonObject);
	}
	@POST
	@Path("/getWafPublicIP")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPublicIpListInResource(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").length()<=0)
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		return deviceAdpaterManager.getWafPublicIpListInResource(resourceId);
	}
	
	//安全基础数据
	//4IP与经纬度数据接口
	//4.1获取单IP经纬度信息
	@POST
	@Path("/secbasedata/iplatlong/getlatlongbyip")
	@Produces(MediaType.APPLICATION_JSON)
	public String getLatlongByIP(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getIpLatlongByIP(jsonObject);
	}
	
	//4.2获取多IP经纬度信息
	@POST
	@Path("/secbasedata/iplatlong/getlatlongbyiplist")
	@Produces(MediaType.APPLICATION_JSON)
	public String getIpLatlongByIPList(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getIpLatlongByIpList(jsonObject);
	}
	
	//4.3获取国内IP地址经纬度数据总数
	@GET
	@Path("/secbasedata/iplatlong/getcncount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getIpLatlongCNCount(){
		return deviceAdpaterManager.getIpLatlongCNCount();
	}
	
	//4.4获取国内IP地址经纬度数据块
	@POST
	@Path("/secbasedata/iplatlong/getcndatablock")
	@Produces(MediaType.APPLICATION_JSON)
	public String getIpLatlongCNDataBlock(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getIpLatlongCNDataBlock(jsonObject);
	}
	
	//4.5获取全球IP地址经纬度数据总数
	@GET
	@Path("/secbasedata/iplatlong/gettotalcount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getIpLatlongTotalCount() {
		return deviceAdpaterManager.getIpLatlongTotalCount();
	}
	
	//4.6获取全球IP地址经纬度数据块
	@POST
	@Path("/secbasedata/iplatlong/getdatablock")
	@Produces(MediaType.APPLICATION_JSON)
	public String getIpLatlongDataBlock(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getIpLatlongDataBlock(jsonObject);
	}
	
	//5.1 获取国内地理信息数据总数
	@GET
	@Path("/secbasedata/nationlocation/getcncount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getNationLocationCNCount() {
		return deviceAdpaterManager.getNationLocationCNCount();
	}
	
	//5.2 获取国内地理信息数据块
	@POST
	@Path("/secbasedata/nationlocation/getcndatablock")
	@Produces(MediaType.APPLICATION_JSON)
	public String getNationLocationCNDataBlock(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getNationLocationCNDataBlock(jsonObject);
	}
	
	//5.3 获取全球地理信息数据总数
	@GET
	@Path("/secbasedata/nationlocation/gettotalcount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getNationLocationTotalCount(){
		return deviceAdpaterManager.getNationLocationTotalCount();
	}
	
	//5.4 获取全球地理信息数据块
	@POST
	@Path("/secbasedata/nationlocation/getdatablock")
	@Produces(MediaType.APPLICATION_JSON)
	public String getNationLocationDataBlock(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getNationLocationDataBlock(jsonObject);
	}
	
	//6.1获取当天国内活动恶意url信息
	@GET
	@Path("/secbasedata/malurl/getdatabycntoday")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlDataByCNToday() {
		return deviceAdpaterManager.getMalurlDataByCNToday();
	}
	
	//6.2获取当天全球活动恶意URL信息
	@GET
	@Path("/secbasedata/malurl/getdatabytoday")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlDataByToday(){
		return deviceAdpaterManager.getMalurlDataByToday();
	}
	
	//6.3获取指定时间段内国内活动恶意url信息
	@POST
	@Path("/secbasedata/malurl/getdatabycnperiod")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlDataByCNPeriod(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getMalurlDataByCNPeriod(jsonObject);
	}
	//6.4
	@POST
	@Path("/secbasedata/malurl/getdatabyperiod")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlDataByPeriod(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.getMalurlDataByPeriod(jsonObject);
	}
	//6.5
	@GET
	@Path("/secbasedata/malurl/getdatabycn")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlDataByCN(){
		return deviceAdpaterManager.getMalurlDataByCN();
	}
	//6.6
	@GET
	@Path("/secbasedata/malurl/getdata")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlData(){
		return deviceAdpaterManager.getMalurlData();
	}
	//6.6b
	@POST
	@Path("/secbasedata/malurl/gettopdata")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlTopData(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return deviceAdpaterManager.getMalurlTopData(jsonObject);
	}
	//6.7
	@GET
	@Path("/secbasedata/malurl/gettargetlistbycn")
	public String getMalurlTargetListByCN() {
		return deviceAdpaterManager.getMalurlTargetListByCN();
	}
	//6.8
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/secbasedata/malurl/getdatabycntarget")
	public String getMalurlDataByCNTarget(String dataJson) {
		
		try {
			JSONObject jsonObject = JSONObject.fromObject(dataJson);
			return deviceAdpaterManager.getMalurlDataByCNTarget(jsonObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "json error");
			return errorJsonObject.toString();
		}
		
	}
	//6.9
	@GET
	@Path("/secbasedata/malurl/getfieldlistbycn")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlFieldListByCN() {
		return deviceAdpaterManager.getMalurlFieldListByCN();
	}
	//6.10
	@POST
	@Path("/secbasedata/malurl/getdatabycnfield")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlDataByCNField(String dataJson) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(dataJson);
			return deviceAdpaterManager.getMalurlDataByCNField(jsonObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "json error");
			return errorJsonObject.toString();
		}
	}
	//6.11
	@POST
	@Path("/secbasedata/malurl/getdatabydomain")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlDataByDomain(String dataJson) {
		
		try {
			JSONObject jsonObject = JSONObject.fromObject(dataJson);
			return deviceAdpaterManager.getMalurlDataByDomain(jsonObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "json error");
			return errorJsonObject.toString();
		}
		
		
	}
	
	
	
	//8
	//1.
	@GET
	@Path("/secbasedata/malurl/getcountbycountryvalid")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalUrlCountByCountryValid() {
		return deviceAdpaterManager.getMalUrlCountByCountryValid();
	}
	//2按国家类别分类获取全部的恶意url个数，带是否有效的状态
	@GET
	@Path("/secbasedata/malurl/getcountbycountrywithvalid")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalUrlCountByCountryWithValidState(){
		return deviceAdpaterManager.getMalUrlCountByCountryWithValidState();
	}
	
	@GET
	@Path("/secbasedata/malurl/getallcountbycountry")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalUrlAllCountByCountry() {
		return deviceAdpaterManager.getMalUrlAllCountByCountry();
	}
	//3.
	@GET
	@Path("/secbasedata/malurl/getcountvalid")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalUrlCountValid() {
		return deviceAdpaterManager.getMalUrlCountValid();
	}
	//4.获取所有恶意url个数
	@GET
	@Path("/secbasedata/malurl/getcount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalUrlCount() {
		return deviceAdpaterManager.getMalUrlCount();
	}
	
	@GET
	@Path("/secbasedata/malurl/getcountinchina")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalUrlCountInChina(){
		return deviceAdpaterManager.getMalUrlCountInChina();
	}
	
	@GET
	@Path("/secbasedata/malurl/getcountinchinavalid")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalUrlCountInChinaValid() {
		return deviceAdpaterManager.getMalUrlCountInChinaValid();
	}
	
	//5.
	@POST
	@Path("/secbasedata/malurl/getcountbymonth")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalUrlCountByMonth(String dataJson) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(dataJson);
			return deviceAdpaterManager.getMalUrlCountByMonth(jsonObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "json error");
			return errorJsonObject.toString();
		}
	}
	
	//6.
	@GET
	@Path("/secbasedata/malurl/getcountbycnprovince")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalUrlCountByCNProvince(){
		return deviceAdpaterManager.getMalUrlCountByCNProvince();
	}
	
	@GET
	@Path("/secbasedata/malurl/getallcountbycnprovince")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalUrlAllCountByCNProvince(){
		return deviceAdpaterManager.getMalUrlAllCountByCNProvince();
	}
	
	//7.
	@GET
	@Path("/secbasedata/malurl/getcountbyfieldtop5")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlCountByFieldTop5() {
		return deviceAdpaterManager.getMalurlCountByFieldTop5();
	}
	
	//8.
	@GET
	@Path("/secbasedata/malurl/getcountbytargettop10")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMalurlCountByTargetTop10() {
		return deviceAdpaterManager.getMalurlCountByTargetTop10();
	}
	
	@POST
	@Path("/systemservice/timeon/getorderindex")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTimeOnOrderIndex(String dataJson) {
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(dataJson);
		return deviceAdpaterManager.getTimeOnOrderIndex(jsonObject);
	}
	@POST
	@Path("/systemservice/timeon/getuninstallinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTimeOnUninstallInfo(String dataJson) {
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(dataJson);
		return deviceAdpaterManager.getTimeOnUninstallInfo(jsonObject);
	}
	@POST
	@Path("/systemservice/timeon/gethostcount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTimOnHostCount(String dataJson) {
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(dataJson);
		return deviceAdpaterManager.getTimeOnHostCount(jsonObject);
	}
	@POST
	@Path("/systemservice/timeon/getoauthurl")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTimOnOauthUrl(String dataJson) {
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(dataJson);
		return deviceAdpaterManager.getTimeOnOauthUrl(jsonObject);
	}
	
	@POST
	@Path("/systemservice/cloudinsight/gettoken")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCloudInsightToken(String dataJson) {
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(dataJson);
		return deviceAdpaterManager.getCloudInsightToken(jsonObject);
	}
	
	@POST
	@Path("/systemservice/cloudinsight/destroytoken")
	@Produces(MediaType.APPLICATION_JSON)
	public String destroyCloudInsightToken(String dataJson) {
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(dataJson);
		return deviceAdpaterManager.destroyCloudInsightToken(jsonObject);
	}
	
	@POST
	@Path("/systemservice/cloudinsight/getloginurl")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCloudInsightLoginURL(String dataJson){
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(dataJson);
		return deviceAdpaterManager.getCloudInsightLoginURL(jsonObject);
	}
	
	@POST
	@Path("/systemservice/nsfocus/createorder")
	@Produces(MediaType.APPLICATION_JSON)
	public String createNsfocusSysOrder(String dataJson) {
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(dataJson);
		return deviceAdpaterManager.createNsfocusSysOrder(jsonObject);
	}
	
	@POST
	@Path("/systemservice/nsfocus/reneworder")
	@Produces(MediaType.APPLICATION_JSON)
	public String renewNsfocusSysOrder(String dataJson){
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(dataJson);
		return deviceAdpaterManager.renewNsfocusSysOrder(jsonObject);
	}
	@POST
	@Path("/scanservice/createTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String createScanTask(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		return deviceAdpaterManager.createScanServiceTask(jsonObject);
	}

	
	

}
