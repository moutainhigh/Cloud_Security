package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.constant.EventTypeCode;
import com.cn.ctbri.entity.AlarmBug;
import com.cn.ctbri.entity.AttackCount;
import com.cn.ctbri.entity.OrderCount;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.util.DateUtils;

/**
 * 
 * @author ： LvCongLiang
 * @date： 2016-8-3下午2:47:25 修 改 人 ： 修改日期：
 * @description： 统计分析 版 本： 1.0
 */
@Controller
public class AnalyseController {
	@Autowired
	private IAlarmService alarmService;

	@Autowired
	private IOrderService orderService;
	String longTerm = "订购长期服务用户";
	String single = "订购单次服务用户";

	/**
	 * 
	 * 功能描述： 进入到漏洞类型分布及发展趋势页面 参数描述：
	 * 
	 * @date:2016-8-3下午3:01:45 返回值 ： 异 常：
	 */
	@RequestMapping(value = "bugUI.html")
	public String bugUI(HttpServletRequest request) {
		String result = "/source/page/analyse/bug";
		return result;
	}

	/**
	 * 
	 * 功能描述： 获取漏洞类型分布及发展趋势数据 参数描述：
	 * 
	 * @throws IOException
	 * @date:2016-8-3下午3:03:48 返回值 ： 异 常：
	 */
	@RequestMapping(value = "bug.html", method = RequestMethod.POST)
	@ResponseBody
	public String bugCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Date currentDate = new Date();
//		currentDate.setMonth(3);
//		currentDate.setDate(19);
		// 1.根据当前时间获取距离一个月前的日期
		Date lastMonthDate = DateUtils.getBeforeMonthDate(currentDate);
		Map<String, Integer> dateStrMap = new HashMap<String, Integer>();
		Date date = lastMonthDate;
		Date startDate = DateUtils.getAfterDate(date, 1);
		for (int i = 0; i < 31; i++) {
			if (date.before(currentDate)) {
				date = DateUtils.getAfterDate(date, 1);
				dateStrMap.put(DateUtils.dateToDate(date), i);
			}
		}
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", DateUtils.dateToDate(startDate));
		paramMap.put("endTime", DateUtils.dateToDate(currentDate));
		List<AlarmBug> bugMaxList = alarmService.findBugMaxCounts(paramMap);
		List<String> names = new ArrayList<String>();
		int bugMaxSize = bugMaxList.size();
		for (int i = 0; i < bugMaxSize; i++) {
			names.add(bugMaxList.get(i).getName());
		}
		paramMap.put("names", names);
		List<AlarmBug> bugList = alarmService.findAlarmBugCounts(paramMap);
		Map<String, List<Long>> map = new HashMap<String, List<Long>>();
		int dateSize = dateStrMap.size();

		for (AlarmBug alarmBug : bugList) {
			String name = alarmBug.getName();
			long countVals = alarmBug.getCountVals();
			String alarmTime = alarmBug.getAlarmTime();
			if (map.get(name) == null) {
				List<Long> list = new ArrayList<Long>(dateSize);
				for (int i = 0; i < dateSize; i++) {
					list.add((long) 0);
				}
				map.put(name, list);
			}
			map.get(name).set(dateStrMap.get(alarmTime), countVals);
		}
		List<String> resultList = new ArrayList<String>();
		// 组装数据
		StringBuilder results = new StringBuilder();
		results.append("\"[");
		int i = 0;
		for (Entry<String, List<Long>> entry : map.entrySet()) {
			String data = "\"" + entry.getKey() + "\"" + entry.getValue();
			data = data.replace("[", ",");
			if (i > 0) {
				data = ",[" + data;
			} else {
				data = "[" + data;
			}
			i++;
			results.append(data);
		}
		results.append("]\"");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", DateUtils.dateToDate(lastMonthDate));
		jsonObject.put("endTime", DateUtils.dateToDate(currentDate));
		jsonObject.put("names", names);
		// String result="\"[" +
		// "[\"框架注入\",0, 0, 10, 5, 0, 1, 0, 20, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",10, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",2, 0, 20, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",0, 0, 0, 5, 0, 1, 0, 20, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",3, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",0, 0,30, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",20, 0, 0, 5, 0, 1, 0, 10, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 10, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",40, 0, 0, 5, 0, 1, 0, 10, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",50, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 10, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",0, 0, 40, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 20, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",20, 0, 0, 5, 0, 1, 0, 0, 1, 0,20, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",0, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",20, 0, 10, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 20, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",20, 0, 10, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",0, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"框架注入\",0, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 0, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"可能的网站路径泄露\",0, 10, 0, 20, 0, 4, 0, 0, 4, 0, 0, 10, 0, 0, 0, 0, 0, 2, 1, 16, 16, 0, 16, 0, 51, 51, 18, 0, 0, 23184, 0],"+
		// "[\"可能的网站路径泄露\",10, 20, 0, 20, 0, 4, 0, 0, 4, 0, 0, 10, 0, 0, 0, 0, 0, 2, 1, 16, 16, 0, 16, 0, 51, 51, 18, 0, 0, 23184, 0],"+
		// "[\"可能的网站路径泄露\",0, 0, 0, 20, 0, 4, 0, 0, 4, 0, 0, 10, 0, 0, 0, 0, 0, 2, 1, 16, 16, 0, 16, 0, 51, 51, 18, 0, 0, 23184, 0],"+
		// "[\"可能的网站路径泄露\",30, 0, 0, 20, 0, 4, 0, 0, 4, 0, 0, 10, 0, 0, 0, 0, 0, 2, 1, 16, 16, 0, 16, 0, 51, 51, 18, 0, 0, 23184, 0],"+
		// "[\"可能的网站路径泄露\",0, 0, 40, 20, 0, 4, 0, 0, 4, 0, 0, 10, 0, 0, 0, 0, 0, 2, 1, 16, 16, 0, 16, 0, 51, 51, 18, 0, 0, 23184, 0],"+
		// "[\"可能的网站路径泄露\",0, 0, 0, 20, 0, 4, 0, 0, 4, 0, 20, 10, 0, 0, 0, 10, 0, 2, 1, 16, 16, 0, 16, 0, 51, 51, 18, 0, 0, 23184, 0],"+
		// "[\"可能的网站路径泄露\",0, 0, 50, 20, 0, 4, 0, 20, 4, 0, 0, 10, 0, 0, 0, 0, 0, 2, 1, 16, 16, 0, 16, 0, 51, 51, 18, 0, 0, 23184, 0],"+
		// "[\"可能的网站路径泄露\",0, 0, 0, 20, 0, 4, 0, 10, 4, 0, 0, 10, 0, 0, 0, 0, 0, 2, 1, 16, 16, 0, 16, 0, 51, 51, 18, 0, 0, 23184, 0],"+
		// "[\"可能的网站路径泄露\",0, 0, 20, 20, 0, 4, 0, 20, 4, 0, 0, 10, 0, 0, 0, 0, 0, 2, 1, 16, 16, 0, 16, 0, 51, 51, 18, 0, 0, 23184, 0],"+
		// "[\"可能的网站路径泄露\",0, 0, 0, 20, 0, 4, 0, 0, 4, 0, 0, 10, 0, 0, 0, 0, 0, 2, 1, 16, 16, 0, 16, 0, 51, 51, 18, 0, 0, 23184, 0],"+
		// "[\"测试文件\",10, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"测试文件\",20, 0, 10, 5, 0, 1, 40, 50, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"测试文件\",20, 10, 0, 5, 0, 1, 30, 20, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"测试文件\",0, 20, 40, 5, 0, 1, 0, 0, 1, 0, 0, 2, 10, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"测试文件\",30, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 10, 20, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"测试文件\",0, 0, 20, 5, 0, 1, 20, 10, 1, 0, 0, 2, 0, 30, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"测试文件\",0, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 20, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"测试文件\",0, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 20, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"错误页面Web应用服务器版本泄露\",0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 11, 5, 0, 0, 0, 12, 0, 0, 0, 0],"+
		// "[\"错误页面Web应用服务器版本泄露\",0, 20, 0, 10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 11, 5, 0, 0, 0, 12, 0, 0, 0, 0],"+
		// "[\"错误页面Web应用服务器版本泄露\",0, 30, 0, 20, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 11, 5, 0, 0, 0, 12, 0, 0, 0, 0],"+
		// "[\"错误页面Web应用服务器版本泄露\",0, 0, 30, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 11, 5, 0, 0, 0, 12, 0, 0, 0, 0],"+
		// "[\"错误页面Web应用服务器版本泄露\",0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 11, 5, 0, 0, 0, 12, 0, 0, 0, 0],"+
		// "[\"错误页面Web应用服务器版本泄露\",0, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 11, 5, 0, 0, 0, 12, 0, 0, 0, 0],"+
		// "[\"mhtml协议跨站脚本\",0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 2, 1, 0, 0, 7, 0, 0, 0, 0],"+
		// "[\"mhtml协议跨站脚本\",10, 20, 0, 0, 0, 40, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 2, 1, 0, 0, 7, 0, 0, 0, 0],"+
		// "[\"mhtml协议跨站脚本\",20, 0, 0, 0, 0, 30, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 2, 1, 0, 0, 7, 0, 0, 0, 0],"+
		// "[\"mhtml协议跨站脚本\",0, 0, 20, 0, 30, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 2, 1, 0, 0, 7, 0, 0, 0, 0],"+
		// "[\"mhtml协议跨站脚本\",0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 2, 1, 0, 0, 7, 0, 0, 0, 0],"+
		// "[\"电话号码\",10, 20, 20, 30, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 19, 26, 31, 3, 7, 20, 0, 0, 1, 0, 0, 0, 0],"+
		// "[\"电话号码\",0, 10, 20, 20, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 19, 26, 31, 3, 7, 20, 0, 0, 1, 0, 0, 0, 0],"+
		// "[\"电话号码\",0, 10, 0, 0, 0, 0, 40, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 19, 26, 31, 3, 7, 20, 0, 0, 1, 0, 0, 0, 0],"+
		// "[\"电话号码\",0, 10, 20, 0, 30, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 19, 26, 31, 3, 7, 20, 0, 0, 1, 0, 0, 0, 0],"+
		// "[\"电话号码\",0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 19, 26, 31, 3, 7, 20, 0, 0, 1, 0, 0, 0, 0],"+
		// "[\"电话号码\",0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 19, 26, 31, 3, 7, 20, 0, 0, 1, 0, 0, 0, 0],"+
		// "[\"跨站脚本\",0, 0, 0, 15, 0, 3, 0, 0, 3, 0, 0, 6, 0, 0, 0, 0, 0, 0, 6, 7, 7, 0, 8, 2, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"E-Mail地址\",0, 0, 0, 10, 0, 2, 0, 0, 2, 0, 0, 16, 0, 0, 0, 0, 0, 12, 5, 38, 29, 2, 43, 3, 13, 13, 11, 0, 0, 12144, 0],"+
		// "[\"网站管理后台\",0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 0, 19, 11, 0, 15, 0, 0, 0, 7, 0, 0, 0, 0],"+
		// "[\"允许Flash文件与任何域HTML页面通信\",0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"目录浏览\",0, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 56, 100, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"可能的数据库错误\",0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 15, 0, 0, 0, 1, 0, 0, 0, 0],"+
		// "[\"敏感目录\",0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 2, 4, 5, 4, 4, 2, 1, 2, 0, 0, 0, 0],"+
		// "[\"链接注入\",0, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 12, 1, 0, 0, 6, 0, 0, 0, 0],"+
		// "[\"内网IP\",0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23, 21, 0, 0, 0, 12, 12, 4, 0, 0, 2208, 0],"+
		// "[\"目录遍历\",0, 0, 0, 5, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"脚本木马\",0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 1, 1, 0, 0, 0, 1104, 0],"+
		// "[\"敏感的HTML信息\",0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"IIS短文件名泄露\",0, 0, 0, 20, 0, 4, 0, 0, 4, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0],"+
		// "[\"Web应用程序错误\",0, 0, 0, 12, 0, 2, 0, 0, 3, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 6, 3, 0, 12, 1, 0, 0, 7, 0, 0, 0, 0]"+
		// "]\"";
		jsonObject.put("listResult", results.toString());
		String resultGson = jsonObject.toString();// 转成json数据
		response.setContentType("textml;charset=UTF-8");
		response.getWriter().print(resultGson);
		return null;
	}

	/**
	 * 
	 * 功能描述： 进入订购单次服务的用户数与订购过长期服务的用户数分布情况页面 参数描述：
	 * 
	 * @date:2016-8-3下午3:01:45 返回值 ： 异 常：
	 */
	@RequestMapping(value = "serviceUserUI.html")
	public String serviceUserUI(HttpServletRequest request) {
		return "/source/page/analyse/serviceUser";
	}

	/**
	 * 
	 * 功能描述： 统计订购单次服务的用户数与订购过长期服务的用户数分布情况 参数描述：
	 * 
	 * @throws IOException
	 * @date:2016-8-3下午3:01:45 返回值 ： 异 常：
	 */
	@RequestMapping(value = "serviceUser.html", method = RequestMethod.POST)
	@ResponseBody
	public String serviceUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<Map<String, Object>> serviceUserList = orderService
				.findServiceUserCount();
		int serviceUserSize = serviceUserList.size();

		String title = "不同频次服务用户分布";
		// 左侧：组装数据格式['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		String sbType = "['" + longTerm + "','" + single + "']";
		// 组装数据
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (serviceUserSize == 0) {// 不存在数据
			sb.append("{value:0,name:'" + longTerm + "',value:0,name:'"
					+ single + "'}");
		} else {// 正常情况下（1长期或者2单次）备注：如果仅仅只出现一种类型的话，也将它归为正常情况，因为显示效果是相同的
			int i = 0;
			for (Map<String, Object> map : serviceUserList) {
				if (i > 0) {
					sb.append(",");
				}
				StringBuilder sbUnit = new StringBuilder();
				sbUnit.append("{");
				int type = (Integer) map.get("type");
				// String name=(String) map.get("name");
				long userNums = (Long) map.get("userNums");
				// 单元格式{value:135, name:'视频广告'},
				sbUnit.append("value:");
				sbUnit.append(userNums);
				sbUnit.append(",name:'");
				if (type == 1) {// 长期
					sbUnit.append(longTerm);
				} else {// 单次
					sbUnit.append(single);
				}
				sbUnit.append("'");
				sbUnit.append("}");
				sb.append(sbUnit);
				i++;
			}
		}
		sb.append("]");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("serviceUserList", sb.toString());
		jsonObject.put("legend", sbType);
		String resultGson = jsonObject.toString();// 转成json数据
		response.setContentType("textml;charset=UTF-8");
		response.getWriter().print(resultGson);
		return null;
	}

	/**
	 * 
	 * 功能描述： 参数描述：
	 * 
	 * @date:2016-8-8上午10:13:41 返回值 ： 异 常：
	 */
	@RequestMapping(value = "orderServiceUI.html")
	public String orderServiceUI(HttpServletRequest request) {
		return "/source/page/analyse/orderService";
	}

	/**
	 * 
	 * 功能描述： 统计长期和单次订单中各服务订单的对比 参数描述：
	 * 
	 * @date:2016-8-8下午1:24:00 返回值 ： 异 常：
	 */
	@RequestMapping(value = "orderServiceCount.html", method = RequestMethod.POST)
	@ResponseBody
	public String orderServiceCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String single="单次";
		String longTerm="长期";
		List<Map<String, Object>> orderServiceList = orderService
				.findServiceCount();
		int orderServiceSize = orderServiceList.size();
		String title = "服务类型关注分布";
		// 左侧标识：组装数据格式['直达','营销广告','搜索引擎','邮件营销','联盟广告','视频广告','百度','谷歌','必应','其他']
		StringBuilder sbType = new StringBuilder();
		/**
		 * 外圈：组装数据格式： [ {value:335, name:'直达'}, {value:310, name:'邮件营销'},
		 * {value:234, name:'联盟广告'}, {value:135, name:'视频广告'}, {value:1048,
		 * name:'百度'}, {value:251, name:'谷歌'}, {value:147, name:'必应'},
		 * {value:102, name:'其他'} ]
		 */
		StringBuilder sbDetailService = new StringBuilder();
		sbDetailService.append("[");
		sbType.append("['" + longTerm + "','" + single + "'");
		long longTermSum = 0;
		long singleSum = 0;
		int i = 0;
		LinkedHashMap<String,OrderCount> orderCountMap=new LinkedHashMap<String,OrderCount>();
		for (Map<String, Object> orderServiceMap : orderServiceList) {
			
			int type = (Integer) orderServiceMap.get("type");
			String name = (String) orderServiceMap.get("name");
			long countNums = (Long) orderServiceMap.get("countNums");
			OrderCount orderCount=new OrderCount(type,name,countNums);
			
			String key="";
			if(type==8){//包年包月的转为长期类型
				type=1;
			}
			key=type+name;
			OrderCount orderValue=orderCountMap.get(key);
			if(null==orderValue){
				orderCountMap.put(key,orderCount);
			}else{				
				orderValue.setCountNums(orderValue.getCountNums()+countNums);
			}
		}
		for(Entry<String,OrderCount> entry:orderCountMap.entrySet()){
			sbType.append(",");
			OrderCount orderCount=entry.getValue();
			int type = orderCount.getType();
			String name = orderCount.getName();
			long countNums = orderCount.getCountNums();
			sbType.append("'");
			if (i > 0) {
				sbDetailService.append(",");
			}
			sbDetailService.append("{value:" + countNums + ", name:'");
			if (type == 1) {// 长期
				sbType.append(longTerm);
				sbDetailService.append(longTerm);
				longTermSum += countNums;
				sbType.append(name);
				sbType.append("'");
				sbDetailService.append(name + "'}");
			} else if(type==2){// 短期
				sbType.append(single);
				sbDetailService.append(single);
				singleSum += countNums;
				sbType.append(name);
				sbType.append("'");
				sbDetailService.append(name + "'}");
			}

			i++;
		}
		sbType.append("]");
		sbDetailService.append("]");
		// 内圈：组装数据格式：[{value:335, name:'直达', selected:true},{value:679,
		// name:'营销广告'},{value:1548, name:'搜索引擎'}]
		StringBuilder sbInnerRing = new StringBuilder();
		sbInnerRing.append("[");
		sbInnerRing.append("{value:" + longTermSum + ",name:'" + longTerm
				+ "'},{value:" + singleSum + ",name:'" + single + "'}");
		sbInnerRing.append("]");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("type", sbType.toString());
		jsonObject.put("innerRing", sbInnerRing.toString());
		jsonObject.put("detailService", sbDetailService.toString());
		String resultGson = jsonObject.toString();// 转成json数据
		response.setContentType("textml;charset=UTF-8");
		response.getWriter().print(resultGson);
		return null;
	}

	/**
	 * 
	 * 功能描述： 攻击类型页面 参数描述：
	 * 
	 * @date:2016-8-9下午3:44:36 返回值 ： 异 常：
	 */
	@RequestMapping(value = "attackUI.html")
	public String attackUI(HttpServletRequest request) {
		String result = "/source/page/analyse/attack";
		return result;
	}

	@RequestMapping(value = "attackCount.html", method = RequestMethod.POST)
	@ResponseBody
	public String attackCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String title = "攻击类型分布及发展趋势";

		int showNums = 20;
		Date currentDate = new Date();
		// 1.根据当前时间获取距离一个月前的日期
		Date lastMonthDate = DateUtils.getBeforeMonthDate(currentDate);
		Map<String, Integer> dateStrMap = new HashMap<String, Integer>();
		Date date = lastMonthDate;
		Date startDate = DateUtils.getAfterDate(date, 1);
		List<String> dateList=new ArrayList<String>();
		Date tempDate=startDate;
		while(tempDate.getTime()<=currentDate.getTime()){
			dateList.add(DateUtils.dateToDate(tempDate));
			tempDate=DateUtils.getAfterDate(tempDate,1);
		}
		int dateSize=dateList.size();
		String starteDateStr = DateUtils.dateToDate(startDate);
		String endDateStr = DateUtils.dateToDate(currentDate);
		long start = System.currentTimeMillis();
		String wafcreate = null;
		wafcreate=WafAPIWorker.getEventTypeCountByDay("30");
		long end = System.currentTimeMillis();
		JSONArray jsonArray = JSONArray.fromObject(wafcreate);
		List<String> attackTypeList = new ArrayList<String>();
		List<Attack> attackList = new LinkedList<Attack>();
		int size = jsonArray.size();
		Map<String, List> map = new HashMap<String, List>();
		List resultList = new LinkedList();
		// 超过20个攻击类型后，需要通过排序查找出这段时间内攻击类型数量最多的20个
		for(int i=0;i<size;i++){
			JSONObject obj = (JSONObject) jsonArray.get(i);
			byte[] base64Bytes = Base64.decodeBase64(obj.get("eventTypeBase64").toString().getBytes());	
			String type = new String(base64Bytes,"UTF-8");
			JSONArray array=obj.getJSONArray("list");
			int listSize=array.size();
			int sum=0;
			//存储格式key=type,value={type,count1,count2,count3....},类型加日期最多不超过32个元素
			map.put(type,new ArrayList(32));
			map.get(type).add(type);;
			Map<String,Integer> mapCount=new HashMap<String,Integer>();
			for(int j=0;j<listSize;j++){
				JSONObject objData=array.getJSONObject(j);
				int count=objData.getInt("count");
				mapCount.put(objData.getString("statTime"),count);
				sum+=count;
			}
			for(int z=0;z<dateSize;z++){
				String dateStr=dateList.get(z);
				if(mapCount.get(dateStr)==null){
					mapCount.put(dateStr,0);
				}
				map.get(type).add(mapCount.get(dateStr));
			}
			attackList.add(new Attack(type,sum));
		}
		//排序
		Collections.sort(attackList, new Attack());
		//查找前20个攻击类型
		for (int x = 0; x < showNums; x++) {
			String attackType = attackList.get(x).getAttackType();
			attackTypeList.add(attackType);
			resultList.add(map.get(attackType));
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("startTime", DateUtils.dateToDate(lastMonthDate));
		jsonObject.put("endTime", endDateStr);
		jsonObject.put("listResult", resultList);
		jsonObject.put("names", attackTypeList);

		String resultGson = jsonObject.toString();// 转成json数据
		response.setContentType("textml;charset=UTF-8");
		response.getWriter().print(resultGson);
		return null;
	}

	class Attack implements Comparator {

		public Attack(String attackType, int attackValue) {
			super();
			this.attackType = attackType;
			this.attackValue = attackValue;
		}

		public Attack() {
			super();
		}

		private String attackType;
		private int attackValue;

		public String getAttackType() {
			return attackType;
		}

		public void setAttackType(String attackType) {
			this.attackType = attackType;
		}

		public int getAttackValue() {
			return attackValue;
		}

		public void setAttackValue(int attackValue) {
			this.attackValue = attackValue;
		}

		public int compare(Object o1, Object o2) {
			Attack obj1 = (Attack) o1;
			Attack obj2 = (Attack) o2;
			return (obj2.getAttackValue() - obj1.getAttackValue());
		}

	}

	@RequestMapping(value = "mapUI.html")
	public String mapUI(HttpServletRequest request) throws UnsupportedEncodingException {
		WafAPIWorker worker = new WafAPIWorker();
		String texts = worker.getWafEventTypeCount(null,"forever",0);
		JSONArray array = JSONArray.fromObject(texts);
		List<AttackCount> attackCountList = new ArrayList<AttackCount>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			byte[] base64Bytes = Base64.decodeBase64(obj.get("eventType")
					.toString().getBytes());
			String eventType = new String(base64Bytes, "UTF-8");
//			Integer typeCode = EventTypeCode.typeToCodeMap.get(eventType);
			Integer count = (Integer) obj.get("count");
			attackCountList.add(new AttackCount(eventType, count));
		}
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("wafEventTypeCount", attackCountList);
		request.setAttribute("wafEventTypeCount",attackCountList.toString());
//		String resultJson = jsonObject.toString();// 转成json数据
		String result = "/source/page/analyse/map";
		return result;
	}
	
}
