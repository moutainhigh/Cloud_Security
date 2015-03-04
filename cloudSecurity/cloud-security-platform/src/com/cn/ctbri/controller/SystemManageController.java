package com.cn.ctbri.controller;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	 * 功能描述： 系统管理页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/SystemManageUI.html")
	public String systemManage(Model model){
		//获取硬盘的使用情况
		Sigar sigar = new Sigar();
		FileSystem fslist[];
		try {
			fslist = sigar.getFileSystemList();
			if(fslist!=null && fslist.length>0){
				FileSystem fs = fslist[0];
				FileSystemUsage usage = null;
				usage = sigar.getFileSystemUsage(fs.getDirName());
				model.addAttribute("totalSpace", usage.getTotal()/1024/1024);//磁盘总空间空间
			}
		} catch (SigarException e1) {
			e1.printStackTrace();
		}
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
		Sigar sigar = new Sigar();
		FileSystem fslist[];
		JSONArray json = new JSONArray();
		try {
			fslist = sigar.getFileSystemList();
			if(fslist!=null && fslist.length>0){
				JSONObject jo1 = new JSONObject();
				JSONObject jo2 = new JSONObject();
				FileSystem fs = fslist[0];
				FileSystemUsage usage = null;
				try {
					usage = sigar.getFileSystemUsage(fs.getDirName());
				} catch (SigarException e) {
				}
				jo1.put("label", "0");//未使用
				jo1.put("value", usage.getFree()/1024/1024);
				json.add(jo1);
				jo2.put("label", "1");//已使用
				jo2.put("value",  usage.getUsed()/1024/1024);
				json.add(jo2);
			}
			
		} catch (SigarException e1) {
			e1.printStackTrace();
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
		return json.toString();
	
	}
	private String printCpuPerc(CpuPerc cpu) {
		String format = CpuPerc.format(cpu.getCombined());// 总的使用率
		return format;
	} 

}
