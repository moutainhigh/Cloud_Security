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
	static{
			typeToCodeMap.put("HTTP协议违背",1);
			typeToCodeMap.put("Web服务器漏洞攻击",2);
			typeToCodeMap.put("Web插件漏洞攻击",3);
			typeToCodeMap.put("爬虫事件",4);
			typeToCodeMap.put("恶意扫描",5);
			typeToCodeMap.put("跨站请求伪造",6);
			typeToCodeMap.put("文件非法上传",7);
			typeToCodeMap.put("跨站脚本攻击",8);
			typeToCodeMap.put("SQL注入攻击",9);
			typeToCodeMap.put("LDAP注入攻击",10);
			typeToCodeMap.put("SSI指令",11);
			typeToCodeMap.put("XPath注入攻击",12);
			typeToCodeMap.put("命令注入攻击",13);
			typeToCodeMap.put("路径穿越攻击",14);
			typeToCodeMap.put("远程文件包含",15);
			typeToCodeMap.put("目录索引",16);
			typeToCodeMap.put("信息泄露",17);
			typeToCodeMap.put("内容过滤",18);
			typeToCodeMap.put("非法下载",19);
			typeToCodeMap.put("自定义攻击",20);
			typeToCodeMap.put("HTTP访问控制事件",21);
			typeToCodeMap.put("智能补丁",22);
			typeToCodeMap.put("资源盗链",23);
			typeToCodeMap.put("Cookie篡改",24);
			typeToCodeMap.put("违背白名单",25);
			typeToCodeMap.put("WebShell页面访问",26);
			typeToCodeMap.put("敏感信息过滤",27);
			typeToCodeMap.put("SSL加载",28);
			typeToCodeMap.put("暴力破解攻击",29);
	}
}
