package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.service.IEngineService;

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
	public static List<EngineCfg> getArnhemUsableEngine(Map<String, Object> engineMap) {
		//最优引擎top3
		List<EngineCfg> engineTop3 = new ArrayList<EngineCfg>();
		
	    Map<String, Double> arnhemEngineMap = new HashMap<String,Double>();
	    //获取可用设备
		List<EngineCfg> usableEngine = engineService.getUsableEngine(engineMap);
		
		for (int i = 0; i < usableEngine.size(); i++) {		    
	    	String sessionId = null;

    		//获取引擎状态
            String resultStr = ArnhemWorker.getEngineState(sessionId,usableEngine.get(i).getEngine_api());

            //解析引擎设备参数，返回负载值
            double load = getLoadForEngine(resultStr);
            if(load!=0){
	            arnhemEngineMap.put(usableEngine.get(i).getEngine_api(), load);
            }

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
			if(i < 3){//top3
				String engineAPI = loads.get(i).getKey();
				//根据api获取引擎
				EngineCfg engine = engineService.findEngineIdbyIP(engineAPI);
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
	private static float getLoadForEngine(String resultStr){
		float load = 0;
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
                    	//获取设备性能数据
                        //CpuOccupancy
                        String cpuOccupancy = engine.element("CpuOccupancy").getTextTrim();
                        //MemoryTotal
                        String memoryTotal = engine.element("MemoryTotal").getTextTrim();
                        //MemoryFree
                        String memoryFree = engine.element("MemoryFree").getTextTrim();
                        //DiskTotal
                        String diskTotal = engine.element("DiskTotal").getTextTrim();
                        //DiskFree
                        String diskFree = engine.element("DiskFree").getTextTrim();
                        
                        float cpu_usage = Float.parseFloat(cpuOccupancy);
                        float memory_usage = (Integer.parseInt(memoryTotal)-Integer.parseInt(memoryFree))/(Integer.parseInt(memoryTotal));
                        float disk_usage = (Integer.parseInt(diskTotal)-Integer.parseInt(diskFree))/(Integer.parseInt(diskTotal));
                        float cpu_usageWeightF = Float.parseFloat(cpu_usageWeight);
                        float memory_usageWeightF = Float.parseFloat(memory_usageWeight);
                        float disk_usageWeightF = Float.parseFloat(disk_usageWeight);
                        float get_speed_usageWeightF = Float.parseFloat(get_speed_usageWeight);
                        float send_speed_usageWeightF = Float.parseFloat(send_speed_usageWeight);
                        float get_speedF = Float.parseFloat(get_speed);
                        float get_speedmaxF = Float.parseFloat(get_speedmax);
                        float send_speedF = Float.parseFloat(send_speed);
                        float send_speedmaxF = Float.parseFloat(send_speedmax);
                        load = (cpu_usageWeightF*cpu_usage + memory_usageWeightF*memory_usage + disk_usageWeightF*disk_usage + get_speed_usageWeightF*(get_speedF/get_speedmaxF) +
                        		send_speed_usageWeightF*(send_speedF/send_speedmaxF))/(cpu_usageWeightF+memory_usageWeightF+disk_usageWeightF+get_speed_usageWeightF+send_speed_usageWeightF);
                    }else{
                    	logger.info("当前引擎处于停止或者异常的状态!");
                    }
                }
            }
            
        } catch (Exception e) {
            logger.info("解析引擎状态失败!");
            
        }
		return load;
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
