package com.cn.ctbri.southapi.adapter.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Element;

public class test {
	public void jsontest() {
		List<HashMap> engineList = new ArrayList<HashMap>();
        for(int i=1;i<=3;i++){
        	HashMap engineRateMap = new HashMap();
        	engineRateMap.put("ip", "IP"+i);
        	
        	engineRateMap.put("cpuUsage", "cpu"+i);
        	
        	engineRateMap.put("memoryUsage", "memoryUsage"+i);
        

        	engineRateMap.put("diskUsage", "diskUsage"+i);
        	
        	engineList.add(engineRateMap);
        	
        }
        String jsonStr = JSONArray.fromObject(engineList).toString();
        System.out.println(jsonStr);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<HashMap> engineList = new ArrayList<HashMap>();
        for(int i=1;i<=3;i++){
        	HashMap engineRateMap = new HashMap();
        	engineRateMap.put("ip", "IP"+i);
        	
        	engineRateMap.put("cpuUsage", "cpu"+i);
        	
        	engineRateMap.put("memoryUsage", "memoryUsage"+i);
        

        	engineRateMap.put("diskUsage", "diskUsage"+i);
        	
        	engineList.add(engineRateMap);
        	
        }
        JSONArray jsonStr = JSONArray.fromObject(engineList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaa", jsonStr);
        System.out.println(jsonObject);

	}

}
