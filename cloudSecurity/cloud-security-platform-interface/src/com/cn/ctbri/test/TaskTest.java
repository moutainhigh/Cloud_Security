package com.cn.ctbri.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.junit.Before;
import org.junit.Test;

import com.cn.ctbri.common.ArnhemWorker;

public class TaskTest {
	
	private String sessionId ;
	
	@Before
	public void preData(){
		sessionId = ArnhemWorker.getSessionId(1);
	}
	
	@Test
	public void testLssuedTask(){
		//String lssuedTask = ArnhemWorker.lssuedTask(sessionId, "test007", "", "65.61.137.117", "80", "可用性监测服务-周期10分钟");
		String lssuedTask = ArnhemWorker.lssuedTask(sessionId, "test008", "", "65.61.137.117", "80", "漏洞扫描服务",1);
		System.out.println(lssuedTask);
	}
	
	@Test
	public void testGetResultCount(){
		String  resultCountByTaskID= ArnhemWorker.getResultCountByTaskID(sessionId, "test",1);
		System.out.println(resultCountByTaskID);
	}
	
	@Test
	public void testGetReportPage(){
		String reportByTaskID = ArnhemWorker.getReportByTaskID(sessionId, "1", "1", 0, 500,1);
		try {
			System.out.println(URLDecoder.decode(reportByTaskID, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Test
	public void testGetStatus(){
		String result = ArnhemWorker.getStatusByTaskId(sessionId, "2",1);
		System.out.println(result);
	}
	
	@Test
	public void testRemoveTask(){
		String result = ArnhemWorker.removeTask(sessionId, "test007",1);
		System.out.println(result);
	}
	
}
