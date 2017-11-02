package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import se.akerfeldt.com.google.gson.Gson;

import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.util.DateUtils;

@Controller
public class OrderAnalysisController {
	@Autowired
	IOrderService orderService;
	@Autowired
	IServService servService;
	
	@RequestMapping("/orderformanalyse.html")
	public String orderformanalyseUI(){
		return "/source/adminPage/orderManage/orderformanalyse";
	}
	@RequestMapping("/getAllService.html")
	public void getAllService(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<Serv> list = servService.findAllService();
		Gson gson= new Gson(); 
		response.setContentType("text/html;charset=utf-8");
		String resultGson = gson.toJson(list);
		PrintWriter out;
		out = response.getWriter();
		out.write(resultGson); 
		out.flush(); 
		out.close();
	}
	@RequestMapping("/getlinedate.html")
	public void orderLine(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Calendar nowcalendar = Calendar.getInstance();
		nowcalendar.set(Calendar.HOUR_OF_DAY, nowcalendar.get(Calendar.HOUR_OF_DAY)-5);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("begin_date", DateUtils.dateToString(nowcalendar.getTime()));
		paramMap.put("currentDate", new Date());
		List<Map> list = orderService.findOrderTimesLine(paramMap);
		List<Map> resultList = new ArrayList();
		if(list.size()!=6){
			for(int i=5;i>=0;i--){
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)-i);
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:00:00");
				Map map = new HashMap();
				int count=0;
				for(int j=0;j<list.size();j++){
					if(list.get(j).get("time").equals(dateFormat.format(calendar.getTime()))){
						System.out.println(list.get(j).get("value"));
						map.put("time", list.get(j).get("time"));
						map.put("time1", list.get(j).get("time1"));
						map.put("count", list.get(j).get("count"));
						resultList.add(map);
					}else{
						count++;
					}
				}
				if(count==list.size()){
					map.put("time",dateFormat.format(calendar.getTime()) );
					map.put("count", 0);
					resultList.add(map);
				}
			}
		}
		Gson gson= new Gson(); 
		response.setContentType("text/html;charset=utf-8");
		String resultGson = gson.toJson(resultList);
		PrintWriter out;
		out = response.getWriter();
		out.write(resultGson); 
		out.flush(); 
		out.close();
	}
	@RequestMapping("/getpiedate.html")
	public void orderPie(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)-5);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("begin_date", DateUtils.dateToString(calendar.getTime()));
		paramMap.put("currentDate", new Date());
		
		List<Map> list = orderService.findOrderTimesPie(paramMap);
		if(list.size()==0){
			Map map = new HashMap();
			map.put("count", -1);
			map.put("name", "无服务");
			map.put("serviceid", 0);
			list.add(map);
		}
		Gson gson= new Gson(); 
		response.setContentType("text/html;charset=utf-8");
		String resultGson = gson.toJson(list);
		PrintWriter out;
		out = response.getWriter();
		out.write(resultGson); 
		out.flush(); 
		out.close();
	}
	@RequestMapping("/getServiceDate.html")
	public void orderByServiceId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String beginDate = request.getParameter("begindate");
		String endDate = request.getParameter("enddate");
		String serviceId = request.getParameter("serviceid");
		String repeat = request.getParameter("repeat");
		paramMap.put("begin_date", beginDate);
		paramMap.put("end_date", endDate);
		paramMap.put("serviceId", serviceId);
		paramMap.put("repeat", repeat);
		List<Map> list = orderService.findOrderWithServiceId(paramMap);
		Gson gson= new Gson(); 
		response.setContentType("text/html;charset=utf-8");
//		if(list.size()<=0){
//			Map map = new HashMap();
//			map.put("name", "无服务");
//			map.put("count", -1);
//			list.add(map);
//		}
		String resultGson = gson.toJson(list);
		PrintWriter out;
		out = response.getWriter();
		out.write(resultGson); 
		out.flush(); 
		out.close();
	}
	
	/*
	 * 回购率
	 * add by tangxr
	 */
	@RequestMapping("/getServiceDateReBuy.html")
	public void orderByServiceIdReBuy(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String beginDate = request.getParameter("begindate");
		String endDate = request.getParameter("enddate");
		String serviceId = request.getParameter("serviceid");
		String repeat = request.getParameter("repeat");
		paramMap.put("begin_date", beginDate);
		paramMap.put("end_date", endDate);
		paramMap.put("serviceId", serviceId);
		paramMap.put("repeat", repeat);
		List<Map> list = orderService.findOrderWithServiceIdReBuy(paramMap);	
		List<Map> listAll = orderService.findOrderWithServiceIdBuy(paramMap);
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
		Gson gson= new Gson(); 
		response.setContentType("text/html;charset=utf-8");
		
		List<Map> listRebuy = new ArrayList();
		if (listAll.size()!=0) {
			Map map = new HashMap();
			map.put("name", "回购率");
			map.put("count", list.size());
			Map map1 = new HashMap();
			map1.put("name", "未回购率");
			map1.put("count", listAll.size()-list.size());
			listRebuy.add(map);
			listRebuy.add(map1);
		}
		
		String resultGson = gson.toJson(listRebuy);
		PrintWriter out;
		out = response.getWriter();
		out.write(resultGson); 
		out.flush(); 
		out.close();
	}
}
