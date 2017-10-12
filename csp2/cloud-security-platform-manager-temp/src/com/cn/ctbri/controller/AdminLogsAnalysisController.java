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

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.service.ILogsService;
import com.cn.ctbri.util.CommonUtil;

@Controller
public class AdminLogsAnalysisController {
	
	@Autowired
	ILogsService logsService;
	/**
	 * 功能描述： Logs分析页面
	 *		 @time 2016-8-25
	 */
	@RequestMapping("/adminLogsAnalysisUI.html")
	public String apiAnalysis(Model model,HttpServletRequest request){
		
		return "/source/adminPage/userManage/LogsAnalysis";
	}

	//日志折线图
	@RequestMapping("getlogslinedate.html")
	@ResponseBody
	public void orderLine(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> m = new HashMap<String, Object>();
		
		//Date now = new Date();
		List dateList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");

		for(int i = 0; i < 30; i++){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -i);
			Date tempDate = c.getTime();
	        String temp = sdf.format(tempDate);
	        dateList.add(temp);
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		List countList = logsService.findLogsTimesLine(null);

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
	
	
}
