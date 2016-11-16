package com.cn.ctbri.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import se.akerfeldt.com.google.gson.Gson;

import com.cn.ctbri.common.ArnhemWorker;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.service.IAssertAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IEnginecfgService;
import com.cn.ctbri.service.IFactoryService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.util.CommonUtil;



/**
 * 创 建 人  ：  zx
 * 创建日期：  2015-11-06
 * 描        述： 设备资源管理
 * 版        本：  1.0
 */
@Controller
public class EquResourceController {
	

	@Autowired
	IAssetService assetService;
	@Autowired
	IAssertAlarmService assetAlarmService;
	@Autowired
	ITaskService taskService;
	@Autowired
	IEnginecfgService enginecfgService;
	@Autowired
	IFactoryService factoryService;
	
	/**
	 * 功能描述：资产地理位置统计分析
	 *		 @time 2015-10-26
	 */
	@RequestMapping("/equResourceUI.html")
	public String dataAssertUI(HttpServletRequest request,HttpServletResponse response){
		return "/source/adminPage/userManage/equResource";
	}
	
	@RequestMapping("/findAllFactory.html")
	public void findAllFactory(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<Factory> list = factoryService.findAll();
		Gson gson= new Gson(); 
		response.setContentType("text/html;charset=utf-8");
		String resultGson = gson.toJson(list);
		PrintWriter out;
		out = response.getWriter();
		out.write(resultGson); 
		out.flush(); 
		out.close();
	}
	/**
	 * 
	* @Title: findAll 
	* @Description: 分页方式获取全部数据
	* @param @param request
	* @param @param response
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/equFindAll.html")
	public void findAll(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map paramMap = new HashMap();
		String equName = new String(request.getParameter("engineName").getBytes("ISO-8859-1"), "UTF-8");
		paramMap.put("engineName", equName);
		paramMap.put("engineAddr", request.getParameter("engineAddr"));
		paramMap.put("factory", request.getParameter("factory"));
		List<Map> list = enginecfgService.findAllEnginecfg(paramMap);
		Gson gson= new Gson(); 
		response.setContentType("text/html;charset=utf-8");
		String resultGson = gson.toJson(list);
		PrintWriter out;
		out = response.getWriter();
		out.write(resultGson); 
		out.flush(); 
		out.close();
	}
	/**
	 * 
	* @Title: addEqu 
	* @Description: 添加设备引擎
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/addEqu.html")
	public String addEqu(HttpServletRequest request,HttpServletResponse response){
		EngineCfg enginecfg = new EngineCfg();
		try {
			String equName=new String(request.getParameter("equName").getBytes("ISO-8859-1"), "UTF-8");
			enginecfg.setEngine_name(equName);
			enginecfg.setEngine_addr(request.getParameter("equIP"));
			enginecfg.setEquipment_factory(request.getParameter("factoryName"));
			enginecfg.setStatus(1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		enginecfgService.insertSelective(enginecfg);
		return "redirect:/equResourceUI.html";
	}
	/**
	 * 
	* @Title: updEqu 
	* @Description: 修改设备信息
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/updEqu.html")
	public String updEqu(HttpServletRequest request,HttpServletResponse response){
		try {
			EngineCfg enginecfg = new EngineCfg();
			enginecfg.setId(Integer.parseInt(request.getParameter("id")));
			String equName;
			equName = new String(request.getParameter("equName").getBytes("ISO-8859-1"), "UTF-8");
			enginecfg.setEngine_name(equName);
			enginecfg.setEngine_addr(request.getParameter("equIP"));
			enginecfg.setEquipment_factory(request.getParameter("factoryName"));
			enginecfg.setStatus(1);
			enginecfgService.updateByPrimaryKeySelective(enginecfg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:/equResourceUI.html";
	}
	
	/**
	 * 
	* @Title: findEquById 
	* @Description: 通过ID查询设备信息
	* @param @param request
	* @param @param response
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/findEquById.html")
	public void findEquById(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		EngineCfg enginecfg = enginecfgService.findEngineById(id);
		Gson gson= new Gson(); 
		response.setContentType("text/html;charset=utf-8");
		String resultGson = gson.toJson(enginecfg);
		PrintWriter out;
		out = response.getWriter();
		out.write(resultGson); 
		out.flush(); 
		out.close();
	}
	
	/**
	 * 
	* @Title: delEquById 
	* @Description: 删除设备信息
	* @param @param request
	* @param @param response
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/delEquById.html")
	public void delEquById(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		enginecfgService.deleteByPrimaryKey(id);
	}
	
	/**
	 * 
	* @Title: getProperties 
	* @Description: 获取配置文件信息，取的CPU、内存、磁盘等信息并封装成list
	* @param @param engine 
	* @param @param ip
	* @param @return
	* @param @throws DocumentException
	* @param @throws IOException    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getProperties(int engine,String ip) throws DocumentException, IOException{
		if(engine>0&&engine<3){
			String sessionId = ArnhemWorker.getSessionId(engine);
			String s1 = ArnhemWorker.getEngineState(sessionId,engine);
			Document document = DocumentHelper.parseText(s1);
			Element task = document.getRootElement();
			List<Map> resultList = new ArrayList<Map>();
			
			List<Element> list = task.elements();
			for(Element e : list){
				if("EngineList".equals(e.getName())){
					Map map = new HashMap();
					List<Element> list1 = e.elements();
					for(Element e1 : list1){
						map.put(e1.getName(), e1.getText());
						if ("Engine".equals(e1.getName())) {
							Map map1 = new HashMap();
							List<Element> list2 = e1.elements();
							for (Element e2 : list2) {
								map1.put(e2.getName(), e2.getText());
							}
							map.put("Engine", map1);
						}
					}
					if(ip.equals(map.get("IP"))){
						resultList.add(map);
					}
				}
			}
			return resultList;
		}else{
			return null;
		}
	}
	
	/**
	 * 功能描述：获取磁盘空间使用情况数据
	 * @throws IOException 
	 * @throws DocumentException 
	 *		 @time 2015-2-10
	 */
	@RequestMapping("/getDiskUsage.html")
	@ResponseBody
	public String getDiskUsage(HttpServletRequest request,HttpServletResponse response) throws DocumentException, IOException{
		int engine = Integer.parseInt(request.getParameter("engine"));
		String ip = request.getParameter("ip");
		List<Map> list = this.getProperties(engine,ip);
		//获取硬盘的使用情况
		JSONArray json = new JSONArray();
		if (list != null && list.size() > 0) {
			int diskTotal = Integer.parseInt(list.get(0).get("DiskTotal").toString());//总的磁盘容量
			int diskFree = Integer.parseInt(list.get(0).get("DiskFree").toString());//可用磁盘容量
			int diskUse = diskTotal-diskFree;//已用磁盘容量
			JSONObject jo1 = new JSONObject();
			JSONObject jo2 = new JSONObject();
			jo1.put("label", "0");// 未使用
			jo1.put("value", diskFree/1024);
			json.add(jo1);
			jo2.put("label", "1");// 已使用
			jo2.put("value", diskUse/1024);
			jo2.put("total", diskTotal/1024);
			json.add(jo2);
		}
		return json.toString();
	}
	/**
	 * 功能描述：获取cpu使用率使用情况数据
	 * @throws IOException 
	 * @throws DocumentException 
	 *		 @time 2015-2-10
	 */
	@RequestMapping("/getSysCpuUsage.html")
	@ResponseBody
	public String getCpuUsage(HttpServletRequest request,HttpServletResponse response) throws DocumentException, IOException{
		int engine = Integer.parseInt(request.getParameter("engine"));
		String ip = request.getParameter("ip");
		List<Map> list = this.getProperties(engine,ip);
		float cpuOccupancy = Float.parseFloat(list.get(0).get("CpuOccupancy").toString());
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		// 方式一，主要是针对一块CPU的情况
		jo.put("printCpuPerc", cpuOccupancy);
		json.add(jo);
		return json.toString();
	}
	/**
	 * 功能描述：获取内存使用情况数据
	 * @throws IOException 
	 * @throws DocumentException 
	 *		 @time 2015-2-10
	 */
	@RequestMapping("/getSysMemoryUsage.html")
	@ResponseBody
	public String getMemoryUsage(HttpServletRequest request,HttpServletResponse response) throws DocumentException, IOException{
		int engine = Integer.parseInt(request.getParameter("engine"));
		String ip = request.getParameter("ip");
		List<Map> list = this.getProperties(engine,ip);
		//获取内存使用情况数据
		double free=0;//空闲内存
		double use=0;//已用内存
		double total=0;//总内存
		double kb=1024;
		total = Integer.parseInt(list.get(0).get("MemoryTotal").toString())/kb;
		free = Integer.parseInt(list.get(0).get("MemoryFree").toString())/kb;
		DecimalFormat df = new DecimalFormat("######0.00");   
		use=total-free; 
		BigDecimal total1 = new BigDecimal(total);
		double total2 = total1.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		//model.addAttribute("total", total2);
		BigDecimal use1 = new BigDecimal(use);
		double use2 = use1.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		JSONArray json = new JSONArray();
		JSONObject jo1 = new JSONObject();
		jo1.put("use", use2);
		jo1.put("total", total2);
		jo1.put("totals", df.format(total));
		json.add(jo1);
		return json.toString();
	}
	/**
	 * 
	* @Title: getCountRunning 
	* @Description: 查询正在运行程序数
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/getCountRuning.html")
	@ResponseBody
	public String getCountRunning(HttpServletRequest request,HttpServletResponse response){
		int engine = Integer.parseInt(request.getParameter("engine"));
		int count = taskService.getCount(engine);
		JSONObject json = new JSONObject();
		return json.element("count", count).toString();
	}
	
	/**
	 * 功能描述： 检验引擎名称或者IP是否已经存在
	 * @throws Exception 
	 *		 @time 2016-11-8
	 */
	@RequestMapping(value="/checkEngineNameAndAddr.html", method = RequestMethod.POST)
	@ResponseBody
	public void checkEngineNameAndAddr(String engineName,HttpServletResponse response,HttpServletRequest request){
		try {
			Map<String, Object> m = new HashMap<String, Object>();
			
			String equName = new String(engineName.getBytes("ISO-8859-1"), "UTF-8");
			String engineAddr = request.getParameter("engineAddr");;
			String equId = request.getParameter("equId");
			
			Map paramMap = new HashMap();
			Integer id = null;
			if (equId != null && !equId.equals("")){//修改
				id = Integer.valueOf(equId);
			}
			paramMap.put("id",id);
			paramMap.put("engineName", equName);
			//paramMap.put("check", 1);
			List list = enginecfgService.findEngineByParam(paramMap);
			m.put("message", "");
			if(list.size()>0){
				m.put("message", "设备引擎名称已存在！");
			}else {
				paramMap.remove("engineName");
				paramMap.put("engineAddr", engineAddr);
				List AddrList = enginecfgService.findEngineByParam(paramMap);
				if(AddrList.size()>0){
					m.put("message", "设备引擎IP地址已存在！");
				}
			}
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
		
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
