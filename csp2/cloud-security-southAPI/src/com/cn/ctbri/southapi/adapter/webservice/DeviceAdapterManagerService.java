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

import org.apache.cxf.ws.rm.RetransmissionCallback;
import org.codehaus.jettison.json.JSONException;
import org.w3c.dom.ls.LSInput;

import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;
import com.cn.ctbri.southapi.adapter.manager.DeviceAdpaterManager;


@Path("adapter")
public class DeviceAdapterManagerService {
	private static DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
	
	private String errNullScannerDevice() {
		JSONObject errJsonObject = new JSONObject();
		errJsonObject.put("status", "failed");
		errJsonObject.put("message", "The deviceId is null.");
		return errJsonObject.toString();
	}
	
	
	
	private String errNullWafDevice() {
		JSONObject errJsonObject = new JSONObject();
		errJsonObject.put("status", "failed");
		errJsonObject.put("message", "The resoureId or deviceId is null.");
		return errJsonObject.toString();
	}
	@GET
	@Path("/loadDeviceAdapter")
	@Produces(MediaType.APPLICATION_JSON)
	public String loadDeviceAdapter(){
		return deviceAdpaterManager.loadDeviceAdpater();
	}
	@POST
	@Path("/loadDeviceAdapter")
	@Produces(MediaType.APPLICATION_JSON)
	public String loadDeviceAdapter(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.loadDeviceAdapter(deviceId);
	}
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
	
	@POST
	@Path("/getSites")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSites(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").equals("")
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		int deviceId = jsonObject.getInt("deviceId");
		return deviceAdpaterManager.getSites(resourceId, deviceId);
	}
	@POST
	@Path("/postIpToEth")
	public String postIpToEth(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").equals("")
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		int deviceId = jsonObject.getInt("deviceId");
		return deviceAdpaterManager.postIpToEth(resourceId, deviceId, jsonObject);
	}
	@POST
	@Path("/getSitesInResource")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSitesInResource(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").equals(""))
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		return deviceAdpaterManager.getSitesInResource(resourceId);
	}
	@POST
	@Path("/createSite")
	@Produces(MediaType.APPLICATION_JSON)
	public String createSite(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").equals("")
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
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
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").equals("")
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		int deviceId = jsonObject.getInt("deviceId");
		return deviceAdpaterManager.createVSite(resourceId, deviceId, jsonObject);
	}
	
	@POST
	@Path("/getVirtualSite")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVirtualSite(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").equals("")
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
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
		if (jsonObject.get("resourceId")==null||jsonObject.getString("resourceId").equals("")
		||jsonObject.get("deviceId")==null||jsonObject.getString("deviceId").equals(""))
			return errNullWafDevice();
		int resourceId = jsonObject.getInt("resourceId");
		int deviceId = jsonObject.getInt("deviceId");
		return deviceAdpaterManager.alterVSite(resourceId, deviceId, jsonObject);
	}
	@DELETE
	@Path("/deleteVirtualSite")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteVirtualSite(String dataJson) {
		return null;
	}

}
