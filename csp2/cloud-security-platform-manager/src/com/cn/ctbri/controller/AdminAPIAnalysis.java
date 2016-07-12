package com.cn.ctbri.controller;



import java.io.IOException;
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
import com.cn.ctbri.util.CommonUtil;


@Controller
public class AdminAPIAnalysis {
	
	@Autowired
	IAPIService apiService;
	/**
	 * 功能描述： API分析页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/adminAPIAnalysisUI.html")
	public String apiAnalysis(Model model,HttpServletRequest request){
		
		return "/source/adminPage/userManage/APIAnalysis";
	}
	
	/**
	 * 功能描述：获取磁盘空间使用情况数据
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="adminAPICount.html")
	@ResponseBody
	public void adminAPICount(HttpServletRequest request,HttpServletResponse response){
		int serviceType = Integer.parseInt(request.getParameter("serviceType"));
		Map<String, Object> m = new HashMap<String, Object>();
		List countList = apiService.getAPICount(serviceType);

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
