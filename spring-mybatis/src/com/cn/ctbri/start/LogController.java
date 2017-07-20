package com.cn.ctbri.start;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cn.ctbri.dao.CityMapper;
import com.cn.ctbri.dao.IpMapper;
import com.cn.ctbri.dao.WebsecMapper;
import com.cn.ctbri.model.City;
import com.cn.ctbri.model.Ip;
import com.cn.ctbri.model.Websec;
import com.cn.ctbri.utils.IPUtility;

public class LogController {
	public static void main(String[] args) {
		
		File directory = new File("");//设定为当前文件夹 
		try{ 
		    System.out.println(directory.getCanonicalPath());//获取标准的路径 
		    System.out.println(directory.getAbsolutePath());//获取绝对路径 
		    ApplicationContext ctx=null;
			 
			// String path=System.getProperty("user.dir");
			// System.out.println(path);
		     ctx=new FileSystemXmlApplicationContext(directory.getAbsolutePath()+"/conf/applicationContext.xml");
		}catch(Exception e){} 
		 
	}
	
	
}
