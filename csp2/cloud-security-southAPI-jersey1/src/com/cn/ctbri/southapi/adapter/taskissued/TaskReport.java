package com.cn.ctbri.southapi.adapter.taskissued;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.cn.ctbri.southapi.adapter.batis.inter.AppReportMapper;
import com.cn.ctbri.southapi.adapter.batis.inter.AppTaskMapper;
import com.cn.ctbri.southapi.adapter.batis.model.AppReport;
import com.cn.ctbri.southapi.adapter.batis.model.AppReportExample;
import com.cn.ctbri.southapi.adapter.batis.model.AppTaskExample;
import com.cn.ctbri.southapi.adapter.common.CommonDatabaseController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TaskReport {
	private  SqlSession getPortscanSqlSession() throws IOException{
		return CommonDatabaseController.getPortscanSqlSession();
	}
	private static void closeSqlSession(SqlSession sqlSession){
		CommonDatabaseController.closeSqlSession(sqlSession);
	}
	public String getTaskReport(JSONObject jsonObject) {
		if (jsonObject.get("target")==null||jsonObject.getString("target").length()<=0) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "target error");
			return errorJsonObject.toString();
		}else if (jsonObject.get("task_type")==null&&(!jsonObject.getString("task_type").equals("portscan")||!jsonObject.getString("task_type").equals("app_detect")||!jsonObject.getString("task_type").equals("web_detect"))) {
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "task type error");
			return errorJsonObject.toString();			
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = getPortscanSqlSession();
			//String typeString = jsonObject.getString("type");
			String ttString = jsonObject.getString("task_type");
			String targetString = jsonObject.getString("target");
			AppTaskExample appTaskExample = new AppTaskExample();
			appTaskExample.createCriteria().andTargetLike("%"+targetString+"%").andTaskTypeEqualTo(ttString);
			AppTaskMapper appTaskMapper = sqlSession.getMapper(AppTaskMapper.class);
			int taskId = appTaskMapper.selectByExample(appTaskExample).get(0).getId();
			System.out.println(taskId);
			//获取report
			AppReportExample appReportExample = new AppReportExample();
			appReportExample.createCriteria().andTaskIdEqualTo(taskId);
			AppReportMapper appReportMapper = sqlSession.getMapper(AppReportMapper.class);
			List<AppReport> appReportList = appReportMapper.selectByExampleWithBLOBs(appReportExample);
			closeSqlSession(sqlSession);
			System.out.println(appReportList.get(0).getPortReport());
			String returnJsonArray = JSON.toJSONString(appReportList);
			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("status", "success");
			returnJsonObject.put("reportList", returnJsonArray);
			return returnJsonObject.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject errorJsonObject = new JSONObject();
			errorJsonObject.put("status", "failed");
			errorJsonObject.put("message", "database connection error");
			return errorJsonObject.toString();	
		}
	}
	public static void main(String[] args) {
		TaskReport taskReport = new TaskReport();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("target", "www.anquanbang.net");
		jsonObject.put("task_type", "portscan");
		System.out.println(jsonObject.get("task_type").equals("web_detect"));
		System.out.println(jsonObject);
		System.out.println(taskReport.getTaskReport(jsonObject));
	}
}
