package com.cn.ctbri.constant;

import java.util.HashMap;
import java.util.Map;

public class EventTypeCode {
	/**
	 * 存储code到攻击类型之间的对应关系
	 *  HTTP协议违背--1
		Web服务器漏洞攻击--2
		Web插件漏洞攻击--3
		爬虫事件--4
		恶意扫描--5
		跨站请求伪造--6
		文件非法上传--7
		跨站脚本攻击--8
		SQL注入攻击--9
		LDAP注入攻击--10
		SSI指令--11
		XPath注入攻击--12
		命令注入攻击--13
		路径穿越攻击--14
		远程文件包含--15
		目录索引--16
		信息泄露--17
		内容过滤--18
		非法下载--19
		自定义攻击--20
		HTTP访问控制事件--21
		智能补丁--22
		资源盗链--23
		Cookie篡改--24
		违背白名单--25
		WebShell页面访问--26
		敏感信息过滤--27
		SSL加载--28
		暴力破解攻击--29
	 */
	public final static Map<String,Integer> typeToCodeMap=new HashMap<String,Integer>();
	public final static Map<Integer,String> codeToTypeMap=new HashMap<Integer,String>();
	static{
			typeToCodeMap.put("HTTP协议违背",0);
			typeToCodeMap.put("Web服务器漏洞攻击",1);
			typeToCodeMap.put("Web插件漏洞攻击",2);
			typeToCodeMap.put("爬虫事件",3);
			typeToCodeMap.put("恶意扫描",4);
			typeToCodeMap.put("跨站请求伪造",5);
			typeToCodeMap.put("文件非法上传",6);
			typeToCodeMap.put("跨站脚本攻击",7);
			typeToCodeMap.put("SQL注入攻击",8);
			typeToCodeMap.put("LDAP注入攻击",9);
			typeToCodeMap.put("SSI指令",10);
			typeToCodeMap.put("XPath注入攻击",11);
			typeToCodeMap.put("命令注入攻击",12);
			typeToCodeMap.put("路径穿越攻击",13);
			typeToCodeMap.put("远程文件包含",14);
			typeToCodeMap.put("目录索引",15);
			typeToCodeMap.put("信息泄露",16);
			typeToCodeMap.put("内容过滤",17);
			typeToCodeMap.put("非法下载",18);
			typeToCodeMap.put("自定义攻击",19);
			typeToCodeMap.put("HTTP访问控制事件",20);
			typeToCodeMap.put("智能补丁",21);
			typeToCodeMap.put("资源盗链",22);
			typeToCodeMap.put("Cookie篡改",23);
			typeToCodeMap.put("违背白名单",24);
			typeToCodeMap.put("WebShell页面访问",25);
			typeToCodeMap.put("敏感信息过滤",26);
			typeToCodeMap.put("SSL加载",27);
			typeToCodeMap.put("暴力破解攻击",28);
			
			codeToTypeMap.put(0,"HTTP协议违背");
			codeToTypeMap.put(1,"Web服务器漏洞攻击");
			codeToTypeMap.put(2,"Web插件漏洞攻击");
			codeToTypeMap.put(3,"爬虫事件");
			codeToTypeMap.put(4,"恶意扫描");
			codeToTypeMap.put(5,"跨站请求伪造");
			codeToTypeMap.put(6,"文件非法上传");
			codeToTypeMap.put(7,"跨站脚本攻击");
			codeToTypeMap.put(8,"SQL注入攻击");
			codeToTypeMap.put(9,"LDAP注入攻击");
			codeToTypeMap.put(10,"SSI指令");
			codeToTypeMap.put(11,"XPath注入攻击");
			codeToTypeMap.put(12,"命令注入攻击");
			codeToTypeMap.put(13,"路径穿越攻击");
			codeToTypeMap.put(14,"远程文件包含");
			codeToTypeMap.put(15,"目录索引");
			codeToTypeMap.put(16,"信息泄露");
			codeToTypeMap.put(17,"内容过滤");
			codeToTypeMap.put(18,"非法下载");
			codeToTypeMap.put(19,"自定义攻击");
			codeToTypeMap.put(20,"HTTP访问控制事件");
			codeToTypeMap.put(21,"智能补丁");
			codeToTypeMap.put(22,"资源盗链");
			codeToTypeMap.put(23,"Cookie篡改");
			codeToTypeMap.put(24,"违背白名单");
			codeToTypeMap.put(25,"WebShell页面访问");
			codeToTypeMap.put(26,"敏感信息过滤");
			codeToTypeMap.put(27,"SSL加载");
			codeToTypeMap.put(28,"暴力破解攻击");
			
	}
}
