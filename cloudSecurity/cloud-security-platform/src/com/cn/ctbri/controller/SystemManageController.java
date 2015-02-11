package com.cn.ctbri.controller;

import java.lang.management.ManagementFactory;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.disk.DiskUsage;
import com.cn.ctbri.disk.SysDisk;
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
		long free=0;//空闲内存
		long use=0;//已用内存
		long total=0;//总内存
		int kb=1024;
		Runtime rt=Runtime.getRuntime(); 
		total=rt.totalMemory();
		free=rt.freeMemory();
		use=total-free;
		model.addAttribute("total", total/kb/kb);
		model.addAttribute("use", use/kb/kb);
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
		long free=0;//空闲内存
		long use=0;//已用内存
		long total=0;//总内存
		int kb=1024; 
		Runtime rt=Runtime.getRuntime(); 
		total=rt.totalMemory();
		free=rt.freeMemory();
		use=total-free;
		JSONArray json = new JSONArray();
		JSONObject jo1 = new JSONObject();
		jo1.put("use", use/kb/kb);
		jo1.put("total", total/kb/kb);
		json.add(jo1);
		return json.toString();
	}
}
