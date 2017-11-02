package com.cn.ctbri.disk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * 创 建 人  ： 邓元元
 * 创建日期：  2015-2-6
 * 描        述：  系统磁盘使用情况
 * 版        本：  1.0
 */
public class DiskUsage {

	public static List<SysDisk> getDiskUsage(){
		List<SysDisk> list = new ArrayList<SysDisk>();
		File[] roots = File.listRoots();//获取磁盘分区列表   
        for (File file : roots) {   
        	SysDisk sd = new SysDisk();
        	//获取盘符
        	//sd.setPath(file.getPath());
        	//获取当前盘符下空闲未使用大小
        	sd.setFreeSpace(file.getFreeSpace()/1024/1024/1024);
        	//已经使用 
        	sd.setUsableSpace(file.getUsableSpace()/1024/1024/1024);
        	//总容量
        	sd.setTotalSpace(file.getTotalSpace()/1024/1024/1024);
        	list.add(sd);
            System.out.println(file.getPath()+"信息如下:");   
            System.out.println("空闲未使用 = " + file.getFreeSpace()/1024/1024/1024+"G");//空闲空间   
            System.out.println("已经使用 = " + file.getUsableSpace()/1024/1024/1024+"G");//可用空间   
            System.out.println("总容量 = " + file.getTotalSpace()/1024/1024/1024+"G");//总空间   
            System.out.println();   
        }   
		return list;
	}
}
