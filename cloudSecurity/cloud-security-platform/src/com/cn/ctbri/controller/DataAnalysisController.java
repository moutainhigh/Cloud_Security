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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import se.akerfeldt.com.google.gson.Gson;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
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
	@Autowired
	IAlarmService alarmService;
	/**
	 * 功能描述：数据分析页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/dataAnalysisUI.html")
	public String adminDeleteUser(Model model,User user,HttpServletRequest request){
		List<User> listRegist = userService.findUserByUserType(2);
		int registCount = 0;
		if(listRegist!=null && listRegist.size()>0){
			registCount = listRegist.size();
		}
		request.setAttribute("registCount", registCount);//注册用户数
		
		List<DataAnalysis> listHaveServ= userService.findHaveServSum();
		int haveServCount = 0;
		if(listHaveServ!=null && listHaveServ.size()>0){
			haveServCount = listHaveServ.size();
		}
		request.setAttribute("haveServCount", haveServCount);//活跃用户数
		
		List<Asset> listAsset = assetService.findAllAssetAddr();
		int asserAddrCount = 0;
		if(listAsset!=null && listAsset.size()>0){
			asserAddrCount = listAsset.size();
		}
		request.setAttribute("asserAddrCount", asserAddrCount);//检测网站
		
		List<Alarm> listAlarmCount = alarmService.findAll();
		int alarmCount = 0;
		if(listAlarmCount!=null && listAlarmCount.size()>0){
			alarmCount = listAlarmCount.size();
		}
		request.setAttribute("alarmCount", alarmCount);//扫描页面数
		return "/source/adminPage/userManage/dataAnalysis";
	}
	/**
	 * 功能描述：活跃用户列表
	 *		 @time 2015-3-12
	 */
	@RequestMapping("/haveServ.html")
	public String haveServCount(Model model){
		List<DataAnalysis> listHaveServ= userService.findHaveServSum();
		model.addAttribute("listHaveServ", listHaveServ);
		return "/source/adminPage/userManage/haveServ";
	}
	/**
	 * 功能描述：检测网站（资产地址）
	 *		 @time 2015-3-12
	 */
	@RequestMapping("/asserAddr.html")
	public String asserAddr(Model model){
		List<Asset> listAsset = assetService.findAllAssetAddr();
		model.addAttribute("listAsset", listAsset);
		return "/source/adminPage/userManage/asserAddr";
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
	}
	/**
	 * 功能描述：数据分析--告警统计分析
	 *		 @time 2015-3-11
	 */
	@RequestMapping(value="warningData.html" ,method = RequestMethod.POST)
	@ResponseBody
	public void warningDataAnalysis(HttpServletRequest request,HttpServletResponse response,Integer level,String alarm_type,String begin_datevo,String end_datevo){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		//组织条件查询
		String name=null;
		try {
			name=new String(alarm_type.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("alarm_type", name);//订单服务类型
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
		paramMap.put("level", level);//告警级别
		List<Alarm> result = alarmService.findAlarmByParam(paramMap);
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
	}
	
}
