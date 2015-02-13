package com.cn.ctbri.controller;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.disk.DiskUsage;
import com.cn.ctbri.disk.SysDisk;
import com.cn.ctbri.util.SysInfo;
import com.sun.management.OperatingSystemMXBean;



/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-3
 * 描        述：  系统管理
 * 版        本：  1.0
 */
@Controller
public class SystemManageController {
	/**
	 * 功能描述：服务管理页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/SystemManageUI.html")
	public String systemManage(Model model){
		List<SysDisk> diskUsage = DiskUsage.getDiskUsage();
		SysDisk sys = null;
		if(diskUsage!=null && diskUsage.size()>0){
			sys = diskUsage.get(0);
			model.addAttribute("totalSpace", sys.getTotalSpace());//磁盘空间
		}
		double free=0;//空闲内存
		double use=0;//已用内存
		double total=0;//总内存
		double kb=1024;
//		Runtime rt=Runtime.getRuntime(); 
//		total=rt.totalMemory();
//		free=rt.freeMemory();
//		use=total-free;
		OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		total = osmb.getTotalPhysicalMemorySize()/kb/kb/kb;
		free = osmb.getFreePhysicalMemorySize()/kb/kb/kb;
		use=total-free;
		BigDecimal total1 = new BigDecimal(total);
		double total2 = total1.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal use1 = new BigDecimal(use);
		double use2 = use1.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		model.addAttribute("total", total2);
		model.addAttribute("use",use2);
		return "/source/adminPage/userManage/systemManage";
	}
	/**
	 * 功能描述：获取磁盘空间使用情况数据
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="sysDiskUsage.html")
	@ResponseBody
	public String sysDiskUsage(){
		//获取硬盘的使用情况
		List<SysDisk> diskUsage = DiskUsage.getDiskUsage();
		SysDisk sys = null;
		JSONArray json = new JSONArray();
		if(diskUsage!=null && diskUsage.size()>0){
			sys = diskUsage.get(0);
			JSONObject jo1 = new JSONObject();
			JSONObject jo2 = new JSONObject();
			//JSONObject jo3 = new JSONObject();
			jo1.put("label", "0");
			jo1.put("value", sys.getFreeSpace());
			json.add(jo1);
			jo2.put("label", "1");
			jo2.put("value", sys.getUsableSpace());
			json.add(jo2);
		}
		
		return json.toString();
	}
	
	
	
	/**
	 * 功能描述：获取内存使用情况数据
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="sysMemoryUsage.html")
	@ResponseBody
	public String sysMemoryUsage(){
		//获取内存使用情况数据
		double free=0;//空闲内存
		double use=0;//已用内存
		double total=0;//总内存
		double kb=1024;
		OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		total = osmb.getTotalPhysicalMemorySize()/kb/kb/kb;
		free = osmb.getFreePhysicalMemorySize()/kb/kb/kb;
		use=total-free; 
		BigDecimal total1 = new BigDecimal(total);
		double total2 = total1.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal use1 = new BigDecimal(use);
		double use2 = use1.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
//		Runtime rt=Runtime.getRuntime(); 
//		total=rt.totalMemory();
//		free=rt.freeMemory();
//		use=total-free;
		JSONArray json = new JSONArray();
		JSONObject jo1 = new JSONObject();
		jo1.put("use", use2);
		jo1.put("total", total2);
		json.add(jo1);
		return json.toString();
	}
	/**
	 * 功能描述：获取cpu使用率使用情况数据
	 *		 @time 2015-2-10
	 */
	@RequestMapping(value="sysCpuUsage.html")
	@ResponseBody
	public String sysCpuUsage(){

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		Sigar sigar = new Sigar();
		// 方式一，主要是针对一块CPU的情况
		CpuPerc cpu;
		try {
			cpu = sigar.getCpuPerc();
			String printCpuPerc = printCpuPerc(cpu);
			float result=new Float(printCpuPerc.substring(0,printCpuPerc.indexOf("%")));
			jo.put("printCpuPerc", result);
			json.add(jo);
		} catch (SigarException e) {
			e.printStackTrace();
		}
		// 方式二，不管是单块CPU还是多CPU都适用
//		CpuPerc cpuList[] = null;
//		try {
//			cpuList = sigar.getCpuPercList();
//		} catch (SigarException e) {
//			e.printStackTrace();
//			return;
//		}
//		for (int i = 0; i < cpuList.length; i++) {
//			printCpuPerc(cpuList[i]);
//		}
		return json.toString();
	
	}
	private String printCpuPerc(CpuPerc cpu) {
		String format = CpuPerc.format(cpu.getCombined());// 总的使用率
		return format;
	} 

}
