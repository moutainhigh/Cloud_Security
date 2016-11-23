package com.cn.ctbri.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.ITaskService;

/**
 * 扫描订单任务表的调度类
 * 
 * @author tangxr 
 * 
 */

@SuppressWarnings("deprecation")
public class SchedulerEngine {

	static Logger logger = Logger.getLogger(SchedulerEngine.class.getName());

	@Autowired
    ITaskService taskService;
	@Autowired
    IEngineService engineService;

	public void execute() throws Exception {
		logger.info("[获取设备信息]:开始......");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		//取得设备ID
		String deviceIdStr = SouthAPIWorker.getDeviceId();
		JSONArray deviceIdArray = new JSONArray().fromObject(deviceIdStr);
		if(deviceIdArray.size()>0){
			for (Object dObj : deviceIdArray) {
				//获取对应设备信息，cpu，内存等
				String deviceId = (String) dObj;
				String resultStr = SouthAPIWorker.getEngineStatRate(deviceId);
				//解析引擎设备参数，返回负载值
	            getLoadForEngine(resultStr, deviceId);
			}
		}
		
		logger.info("[获取设备信息]:结束......");
	}
	
	/**
	 * 解析引擎，返回负载值
	 * @param resultStr 返回的json串
	 * @param activity 运行中的任务数
	 * @param maxTask 引擎最大承载任务数
	 * @return
	 */
	private Map<String,Double> getLoadForEngine(String resultStr,String deviceId){
		Map<String,Double> loadMap = new HashMap<String,Double>();
		double load = 0;
        try {
            String status = JSONObject.fromObject(resultStr).getString("status");
            if("success".equals(status)){
            	//解析引擎list
				JSONArray jsonList= JSONObject.fromObject(resultStr).getJSONArray("StatRateList"); 
				for (int i = 0; i < jsonList.size(); i++) {
					JSONObject jsonObject = jsonList.getJSONObject(i); 
					String ip = jsonObject.getString("ip");
					//根据ip查询当前任务数和最大任务数
					EngineCfg en = engineService.findEngineIdbyIP(ip);
					double memory_usage = 0d;
					double cpu_usage = 0d;
					double disk_usage = 0d;
					if(en!=null){
						if(!jsonObject.get("memoryUsage").equals("null") && jsonObject.get("cpuUsage") != null && jsonObject.get("diskUsage") != null){
							memory_usage = jsonObject.getDouble("memoryUsage");
							cpu_usage = jsonObject.getDouble("cpuUsage");
							disk_usage = jsonObject.getDouble("diskUsage");
							en.setStatus(1);
						}else{
							en.setStatus(0);
						}
					}else{
						en = new EngineCfg();
					}
					en.setEngine_number(deviceId);
					en.setEngine_name(ip.substring(ip.lastIndexOf(".")+1,ip.length()));
					en.setEngine_addr(ip);
					en.setMemoryUsage(memory_usage);
					en.setCpuUsage(cpu_usage);
					en.setDiskUsage(disk_usage);
					en.setUpdateDate(new Date());
					engineService.saveOrUpdate(en);
				}
			}else{
				logger.info("当前设备处于停止或者异常的状态!");
			}
        } catch (Exception e) {
            logger.info("解析引擎状态失败!");
        }
		return loadMap;
	}
	
}
