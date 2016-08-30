package com.cn.ctbri.controller;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.service.IAPIService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.util.CommonUtil;


@Controller
public class AdminAPIAnalysis {
	
	@Autowired
	IAPIService apiService;
	@Autowired
	IAssetService assetService;
	/**
	 * 功能描述： API分析页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/adminAPIAnalysisUI.html")
	public String apiAnalysis(Model model,HttpServletRequest request){
		//API累计调用数
		int apiUseCount = apiService.getAPIUseCount();
		//扫描网站数
		int assetCount = assetService.getAssetCount();
		//接入APIKEY数
		int apiCount = apiService.getAllAPICount();
		model.addAttribute("apiUseCount", apiUseCount);
		model.addAttribute("assetCount", assetCount);
		model.addAttribute("apiCount",apiCount);
		return "/source/adminPage/userManage/APIAnalysis";
	}
	
	/**
	 * 功能描述：API使用情况个数分析
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="adminAPICount.html")
	@ResponseBody
	public void adminAPICount(HttpServletRequest request,HttpServletResponse response){
		int serviceType = Integer.parseInt(request.getParameter("serviceType"));
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		Map<String, Object> m = new HashMap<String, Object>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("serviceType", serviceType);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List countList = apiService.getAPICount(map);

		m.put("countList", countList);
		
		//object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 功能描述：API最近7天使用趋势
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="adminAPICountLast7Days.html")
	@ResponseBody
	public void adminAPICountLast7Days(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		
		//Date now = new Date();
		List dateList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		for(int i = 0; i < 7; i++){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -i);
			Date tempDate = c.getTime();
	        String temp = sdf.format(tempDate);
	        dateList.add(temp);
		}
		
		
		Map<String,Object> map = new HashMap<String, Object>();
		List countList = apiService.getAPICountLast7Days();

		m.put("countList", countList);
		m.put("dateList", dateList);
		//object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 功能描述：开发者调用次数TOP5
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="adminAPIUserCountTop5.html")
	@ResponseBody
	public void adminAPIUserCountTop5(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List countList = apiService.getAPIUserCountTop5(map);

		m.put("countList", countList);
		//object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 功能描述：开发者调用次数列表
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="getAllAPIUserList.html")
	@ResponseBody
	public void getAllAPIUserList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List countList = apiService.getAllAPIUserList(map);

		m.put("countList", countList);
		//object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 功能描述：开发者使用服务统计
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="getAPICountByUser.html")
	@ResponseBody
	public void getAPICountByUser(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String userName = request.getParameter("userName");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("userName", userName);
		List countList = apiService.getAPICountByUser(map);

		m.put("countList", countList);
		//object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 功能描述：API服务使用时段统计
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="adminAPIUseTimes.html")
	@ResponseBody
	public void adminAPIUseTimes(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List countList = apiService.getAPIUseTimes(map);

		m.put("countList", countList);
		//object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 功能描述：用户使用时段统计
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="adminAPITimesByUser.html")
	@ResponseBody
	public void adminAPITimesByUser(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		String userName = request.getParameter("userName");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("userName", userName);
		List countList = apiService.getAPITimesByUser(map);

		m.put("countList", countList);
		//object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
