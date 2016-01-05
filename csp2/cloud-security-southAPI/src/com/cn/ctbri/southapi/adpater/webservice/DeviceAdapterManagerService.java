package com.cn.ctbri.southapi.adpater.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.codehaus.jettison.json.JSONException;

import com.cn.ctbri.southapi.adpater.config.ScannerTaskUniParam;
import com.cn.ctbri.southapi.adpater.manager.DeviceAdpaterManager;


@Path("adapter")
public class DeviceAdapterManagerService {
	private static DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
	@GET
	@Path("/loadDeviceAdapter")
	@Produces(MediaType.APPLICATION_JSON)
	public String loadDeviceAdapter(){
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.loadDeviceAdpater();
	}
	@POST
	@Path("/disposeScanTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String disposeScanTask(String dataJson)throws JSONException{
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.disposeScanTask(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getProgressById")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProgressById(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.getProgressById(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getTemplate")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTemplate(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.getTemplate(deviceId);
	}
	@POST
	@Path("/getEngineStat")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEngineStat(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.getEngineStat(deviceId);
	}
	@POST
	@Path("/removeTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeTask(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.removeTask(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/startTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String startTask(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.startTask(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/pauseTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String pauseTask(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.pauseTask(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/stopTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String stopTask(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.stopTask(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getStatusByTaskId")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatusByTaskId(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.getStatusByTaskId(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getTaskLoadInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTaskLoadInfo(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.getTaskLoadInfo(deviceId);
	}
	@POST
	@Path("/getResultCountByTaskID")
	@Produces(MediaType.APPLICATION_JSON)
	public String getResultCountByTaskID(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.getResultCountByTaskID(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getReportByTaskID")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReportByTaskID(String dataJson) throws JSONException{
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.getReportByTaskID(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getWebsiteCount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWebsiteCount(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.getWebsiteCount(deviceId); 	
	}
	@POST
	@Path("/getWebsiteList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWebsiteList(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		deviceAdpaterManager.loadDeviceAdpater();
		return deviceAdpaterManager.getWebsiteList(deviceId, scannerTaskUniParam);
	}

}
