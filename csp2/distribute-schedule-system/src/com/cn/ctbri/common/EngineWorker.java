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
