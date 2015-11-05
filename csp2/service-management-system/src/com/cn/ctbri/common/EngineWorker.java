package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.util.ArrayList;
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
	
	@Autowired
	private static IEngineService engineService;


	static {
		try {
			Properties p = new Properties();
			p.load(EngineWorker.class.getClassLoader().getResourceAsStream("engineConfig.properties"));
			engine_capacity = p.getProperty("engine_capacity");
			engine_capacity1 = p.getProperty("engine_capacity1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取服务支持的引擎
	public static List<EngineCfg> getUsableEngine(Map<String, Object> engineMap) {
	    String ableIds = "";
	    //获取可用设备
		List<EngineCfg> usableEngine = engineService.getUsableEngine(engineMap);
		for (EngineCfg engineCfg : usableEngine) {
		    //创宇
		    if((String)engineMap.get("factory")=="1"){
		        ableIds = ableIds + engineCfg.getId() + ",";
		    }else{
		    	String sessionId = null;
//		        String sessionId = ArnhemWorker.getSessionId(engineCfg.getEngine_api(),engineCfg.getUsername(),engineCfg.getPassword());
	            //获取引擎状态
	            String resultStr = ArnhemWorker.getEngineState(sessionId,engineCfg.getEngine_api());
	            //解析引擎状态,返回可用引擎ip
	            List ableList = getStatusByResult(resultStr);
	            for (int i = 0; i < ableList.size(); i++) {
	                String ip = (String) ableList.get(i);
	                EngineCfg engine = engineService.findEngineIdbyIP(ip);
	                ableIds = ableIds + engine.getId() + ",";
	            }
		    }
        }
		//获取任务下发的引擎
		ableIds = ableIds.substring(0,ableIds.length()-1);
//        EngineCfg minActivity = engineService.findMinActivity(ableIds);
		List<EngineCfg> ableActivity = engineService.findAbleActivity(ableIds);
        return ableActivity;
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
