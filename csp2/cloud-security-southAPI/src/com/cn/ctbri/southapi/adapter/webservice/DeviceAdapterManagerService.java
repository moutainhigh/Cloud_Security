package com.cn.ctbri.southapi.adapter.webservice;

import java.sql.Blob;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.codehaus.jettison.json.JSONException;

import com.cn.ctbri.southapi.adapter.config.ScannerTaskUniParam;
import com.cn.ctbri.southapi.adapter.manager.DeviceAdpaterManager;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Path("adapter")
public class DeviceAdapterManagerService {
	private static DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
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
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.disposeScanTask(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getProgressById")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProgressById(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getProgressById(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getTemplate")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTemplate(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getTemplate(deviceId);
	}
	@POST
	@Path("/getEngineStat")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEngineStat(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getEngineStat(deviceId);
	}
	@POST
	@Path("/getEngineStatRate")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEngineStatRate(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getEngineStatRate(deviceId);
	}
	@POST
	@Path("/removeTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeTask(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.removeTask(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/startTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String startTask(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.startTask(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/pauseTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String pauseTask(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.pauseTask(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/stopTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String stopTask(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
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
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getTaskPercentById(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getTaskLoadInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTaskLoadInfo(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getTaskLoadInfo(deviceId);
	}
	@POST
	@Path("/getResultCountByTaskID")
	@Produces(MediaType.APPLICATION_JSON)
	public String getResultCountByTaskID(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
		return deviceAdpaterManager.getResultCountByTaskID(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getReportByTaskID")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReportByTaskID(String dataJson) throws JSONException{
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		String deviceId = jsonObject.get("deviceId").toString();
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
		String deviceId = jsonObject.get("deviceId").toString();
		ScannerTaskUniParam scannerTaskUniParam = (ScannerTaskUniParam) JSONObject.toBean(jsonObject,ScannerTaskUniParam.class);
		return deviceAdpaterManager.getWebsiteList(deviceId, scannerTaskUniParam);
	}
	@POST
	@Path("/getWaflogWebsecByIp")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogWebsecByIp(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String dstIp = jsonObject.getString("dstIp");
		System.out.println(dstIp);
		return deviceAdpaterManager.getWafLogWebsec(dstIp);
	}
	@POST
	@Path("/getWaflogArpByIp")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogArpByIp(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String dstIp = jsonObject.getString("dstIp");
		return deviceAdpaterManager.getWafLogArp(dstIp);
	}
	@POST
	@Path("/getWaflogDdosByIp")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWafLogDdosByIp(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		String dstIp = jsonObject.getString("dstIp");
		return deviceAdpaterManager.getWafLogDDOS(dstIp);
	}
	@POST
	@Path("/getSites")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSites(String dataJson){
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		int resourceId = Integer.parseInt(jsonObject.get("resourceId").toString());
		int deviceId = Integer.parseInt(jsonObject.get("deviceId").toString());
		return deviceAdpaterManager.getSites(resourceId, deviceId);
	}
	@POST
	@Path("/createSite")
	@Produces(MediaType.APPLICATION_JSON)
	public String createSite(String dataJson) {
		JSONObject jsonObject = JSONObject.fromObject(dataJson);
		int resourceId = Integer.parseInt(jsonObject.getString("resourceId"));
		int deviceId = Integer.parseInt(jsonObject.getString("deviceId"));
		return deviceAdpaterManager.createSite(resourceId, deviceId, jsonObject);
	}

}
