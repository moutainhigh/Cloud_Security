package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import se.akerfeldt.com.google.gson.Gson;

import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.DateUtils;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-3
 * 描        述：  数据分析
 * 版        本：  1.0
 */
@Controller
public class DataAnalysisController {
	
	@Autowired
	IUserService userService;
	@Autowired
	IAssetService assetService;
	@Autowired
	IOrderService orderService;
	
	/**
	 * 功能描述：数据分析页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/dataAnalysisUI.html")
	public String adminDeleteUser(User user){
		return "/source/adminPage/userManage/dataAnalysis";
	}
	/**
	 * 功能描述：运营数据统计
	 *		 @time 2015-3-6
	 */
	@RequestMapping("/operationDataStatistics.html")
	public String operationDataStatistics(User user){
		//注册用户数
		List<User> list = userService.findUserByUserType(2);
		//活跃用户数：服务数大于0的注册用户数汇总，可点击查看所有活跃用户列表，包括：用户名、用户服务数，按用户服务数降序排列
		/**
		 * 	SELECT a.name,COUNT(a.name)
			FROM 
				(SELECT * 
				FROM   (
					SELECT  u.name , o.serviceId ,u.type	  
					FROM cs_order o LEFT JOIN cs_user u 
					ON o.userId=u.id ) uo 
				WHERE uo.type=2 AND  uo.serviceId IS NOT NULL) a  
			GROUP BY a.name ORDER BY COUNT(a.name) DESC;
		 */
		//监测网站数：所有已注册用户的资产数汇总，针对资产地址进行去重统计，只统计一级地址，可点击查看所有监测网站列表
		//比如www.baidu.com和www.baidu.com/index.html只统计一次， 在监测网站列表中显示为www.baidu.com
		/**
		 * SELECT DISTINCT a.addr 
		   FROM cs_asset a
		   (只做了去重)
		 */
		return "/source/adminPage/userManage/dataAnalysis";
	}
	/**
	 * 功能描述：订单统计分析
	 *		 @time 2015-3-9
	 */
	@RequestMapping(value="orderStatisticsAnalysis.html" ,method = RequestMethod.POST)
	@ResponseBody
	public void orderStatisticsAnalysis(HttpServletRequest request,HttpServletResponse response,Integer state,Integer type,String servName,String begin_datevo,String end_datevo){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		//组织条件查询
		String name=null;
		try {
			name=new String(servName.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("type", type);//订单类型
		paramMap.put("servName", name);//订单服务类型
		paramMap.put("state", state);//订单状态
		if(StringUtils.isNotEmpty(begin_datevo)){//开始时间
			paramMap.put("begin_date", DateUtils.stringToDate(begin_datevo));
		}else{
			paramMap.put("begin_date", null);
		}
		if(StringUtils.isNotEmpty(end_datevo)){//结束时间
			paramMap.put("end_date", DateUtils.stringToDate(end_datevo));
		}else{
			paramMap.put("end_date", null);
		}
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /* 時：分：秒  HH:mm:ss  HH : 23小時制 (0-23)
                                 kk : 24小時制 (1-24)
                                 hh : 12小時制 (1-12)
                                 KK : 11小時制 (0-11)*/
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        paramMap.put("currentDate", temp);
		List<DataAnalysis> result = orderService.findByCombineDataAnalysis(paramMap);//[{cou=2, name=关键字监测服务}, {cou=2, name=恶意代码监测服务}, {cou=23, name=漏洞扫描服务}]

		Gson gson= new Gson();          
		String resultGson = gson.toJson(result);//转成json数据
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(resultGson); 
			out.flush(); 
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
//		JSONArray json = new JSONArray();
//		JSONObject jo = new JSONObject();
//		if(result!=null && result.size()>0){
//			for(int i = 0; i< result.size();i++){
//				Map<String,Object> map = (Map<String, Object>) result.get(i);
//				Object name1 = map.get("name");
//				Object count = map.get("cou");
//				jo.put(name1, count);
//			}
//		}
//		json.add(jo);//[{"关键字监测服务":2,"恶意代码监测服务":2,"漏洞扫描服务":23}]
//		
//		return json.toString();
	}
	
}
