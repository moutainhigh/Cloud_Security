package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.logger.CSPLoggerAdapter;
import com.cn.ctbri.logger.CSPLoggerConstant;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.util.DateUtils;

/**
 * 引擎调度类
 * 
 * @author tang
 * 
 */

public class EngineWorker {

	static Logger logger = Logger.getLogger(EngineWorker.class.getName());

	/**
	 * 引擎编号
	 */
	private static String engine_capacity;
	
	private static String engine_capacity1;
	
	private static String cpu_usageWeight;
	private static String memory_usageWeight;
	private static String disk_usageWeight;
	private static String get_speed_usageWeight;
	private static String send_speed_usageWeight;
	private static String get_speedmax;
	private static String get_speed;
	private static String send_speedmax;
	private static String send_speed;
	
	@Autowired
	private static IEngineService engineService;


	static {
		try {
			Properties p = new Properties();
			p.load(EngineWorker.class.getClassLoader().getResourceAsStream("engineConfig.properties"));
			engine_capacity = p.getProperty("engine_capacity");
			engine_capacity1 = p.getProperty("engine_capacity1");
			cpu_usageWeight = p.getProperty("cpu_usageWeight");
			memory_usageWeight = p.getProperty("memory_usageWeight");
			disk_usageWeight = p.getProperty("disk_usageWeight");
			get_speed_usageWeight = p.getProperty("get_speed_usageWeight");
			send_speed_usageWeight = p.getProperty("send_speed_usageWeight");
			get_speedmax = p.getProperty("get_speedmax");
			get_speed = p.getProperty("get_speed");
			send_speedmax = p.getProperty("send_speedmax");
			send_speed = p.getProperty("send_speed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取Arnhem支持的引擎top3
	public static List<EngineCfg> getArnhemUsableEngine(List<EngineCfg> engineList,int sessionId) {
		//最优引擎top3
		List<EngineCfg> engineTop3 = new ArrayList<EngineCfg>();
		
	    Map<String, Double> arnhemEngineMap = new HashMap<String,Double>();
	    
		for (int i = 0; i < engineList.size(); i++) {		    
    		//获取引擎状态json串
            String resultStr = SouthAPIWorker.getEngineStatRate(engineList.get(i).getEngine_number());

            //解析引擎设备参数，返回负载值
            arnhemEngineMap = getLoadForEngine(resultStr);
          
    	}

		
		//排序
		List<Map.Entry<String, Double>> loads =
		    new ArrayList<Map.Entry<String, Double>>(arnhemEngineMap.entrySet());
		
		Collections.sort(loads, new Comparator<Map.Entry<String, Double>>() {   
		    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
		    	if ((o1.getValue() - o2.getValue())>0)  
		            return 1;  
		          else if((o1.getValue() - o2.getValue())==0)  
		            return 0;  
		          else   
		            return -1;
		    }
		}); 
		
		
		//获取任务下发的引擎
		for(int i = 0; i < loads.size(); i++){
			String engineIP = loads.get(i).getKey();
			//根据ip获取引擎
			EngineCfg engine = engineService.findEngineIdbyIP(engineIP);
			if(engine.getEngine_capacity().contains(String.valueOf(sessionId)) && engineTop3.size()<3){
				engineTop3.add(engine);
			}
		}
		return engineTop3;
	}
	
	/**
	 * 解析引擎，返回负载值
	 * @param resultStr
	 * @return
	 */
	private static Map<String,Double> getLoadForEngine(String resultStr){
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

					if(jsonObject.get("memoryUsage")!=null && jsonObject.get("cpuUsage") != null && jsonObject.get("diskUsage") != null){
						double memory_usage = jsonObject.getDouble("memoryUsage");
						double cpu_usage = jsonObject.getDouble("cpuUsage");
						double disk_usage = jsonObject.getDouble("diskUsage");
						double get_speed = jsonObject.getDouble("getSpeed");
						double send_speed = jsonObject.getDouble("sendSpeed");
						
						double cpu_usageWeightD = Double.parseDouble(cpu_usageWeight);
						double memory_usageWeightD = Double.parseDouble(memory_usageWeight);
						double disk_usageWeightD = Double.parseDouble(disk_usageWeight);
						double get_speed_usageWeightD = Double.parseDouble(get_speed_usageWeight);
						double send_speed_usageWeightD = Double.parseDouble(send_speed_usageWeight);
						double get_speedmaxD = Double.parseDouble(get_speedmax);
	                    double send_speedmaxD = Double.parseDouble(send_speedmax);
	                    
	                    load = (cpu_usageWeightD*cpu_usage + memory_usageWeightD*memory_usage + disk_usageWeightD*disk_usage + get_speed_usageWeightD*(get_speed/get_speedmaxD) +
	                    		send_speed_usageWeightD*(send_speed/send_speedmaxD))/(cpu_usageWeightD+memory_usageWeightD+disk_usageWeightD+get_speed_usageWeightD+send_speed_usageWeightD);
	                    if(load!=0){
	                    	loadMap.put(ip, load);
	                    }
					}else{
						logger.info(ip+"引擎处于停止或者异常的状态!");
					}
				}
			}else{
				logger.info("当前设备处于停止或者异常的状态!");
			}
        } catch (Exception e) {
            logger.info("解析引擎状态失败!");
            
        }
		return loadMap;
	}
	
	private static List getStatusByResult(String resultStr) {
	    List ableList = new ArrayList();
	    List enableList = new ArrayList();
        String decode;
        try {
            decode = URLDecoder.decode(resultStr, "UTF-8");
            Document document = DocumentHelper.parseText(decode);
            Element result = document.getRootElement();
            Attribute attribute  = result.attribute("value");
            String resultValue = attribute.getStringValue();
            if("Success".equals(resultValue)){
                List<Element> EngineList = result.elements("EngineList");
                for (Element engine : EngineList) {
                    String ip = engine.element("IP").getTextTrim();
                    Element eng = engine.element("Engine");
                    String engineState = eng.element("EngineState").getTextTrim();
                    if (engineState!=null){
                        //可用ip
                        ableList.add(ip);
                    }else{
                        //不可用ip
                        enableList.add(ip);
                        //发短信
                    }
                }
            }
            return ableList;
        } catch (Exception e) {
            logger.info("解析引擎状态失败!");
            return null;
        }
        
    }
    
	
	
	
	public static void main(String[] args) {
		System.out.println(engine_capacity);
		System.out.println(engine_capacity1);
	}

	
	
    
}
