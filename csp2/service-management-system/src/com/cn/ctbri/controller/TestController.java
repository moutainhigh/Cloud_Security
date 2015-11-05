package com.cn.ctbri.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ctbri.common.ArnhemWorker;
import com.cn.ctbri.dao.AlarmDao;
import com.cn.ctbri.entity.Alarm;

@Controller
public class TestController {
	@Autowired
	private AlarmDao alarmDao;
	@RequestMapping(value="test.html",method=RequestMethod.GET)
	public ModelAndView test() throws UnsupportedEncodingException, DocumentException, ParseException{
		String sessionId = ArnhemWorker.getSessionId(2);
		String reportByTaskID = ArnhemWorker.getReportByTaskID(sessionId, "test008", "1", 0, 200,2);
		String decode = URLDecoder.decode(reportByTaskID, "UTF-8");
//		decode = decode.replace("<bold>", "");
//		decode = decode.replace("</bold>", "");
//		decode = decode.replace("<>", "");
//		decode = decode.replace("<%", "");
//		decode = decode.replace("%>", "");
//		System.out.println(decode);
		//decode = tryXML(decode);
		Alarm alarm = new Alarm();
		Document document = DocumentHelper.parseText(decode);
		Element task = document.getRootElement();
		//任务ID节点
		Element taskIDNode = task.element("TaskID");
		//任务ID值
		String taskID = taskIDNode.getTextTrim();
		//任务ID表值
		String tid = taskID.substring(taskID.lastIndexOf("t")+1, taskID.length());
		alarm.setTaskId(Long.parseLong(tid));
		//获取报表节点
		Element reportNode = task.element("Report");
		//获取所有的Funcs
		Element funcs = reportNode.element("Funcs");
		//获取所有的Func集合
		List<Element> funcList = funcs.elements("Func");
		for (Element func : funcList) {
			//报警服务类型
			String alarm_type = func.element("name").getTextTrim();
			alarm.setAlarm_type(alarm_type);
			//站点
			Element siteNode = func.element("site");
			//资源地址
			Attribute urlAb = siteNode.attribute("name");
			String url = urlAb.getStringValue();
			alarm.setUrl(url);
			//issuetype
			Element issuetype = siteNode.element("issuetype");
			//时间
			String time = siteNode.attribute("time").getStringValue();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			alarm.setAlarm_time(sdf.parse(time));
			//名称
			String name = issuetype.attribute("name").getStringValue();
			alarm.setName(name);
			//等级
			String level = issuetype.attribute("level").getStringValue();
			if("信息".equals(level)){
				alarm.setLevel(3);
			}
			//建议
			String advice = issuetype.attribute("advice").getStringValue();
			alarm.setAdvice(advice);
			//issuedata
			List<Element> issuedatas = issuetype.elements("issuedata");
			for (Element issuedata : issuedatas) {
				String content = issuedata.attribute("url").getStringValue();
				alarm.setAlarm_content(content);
				alarmDao.insert(alarm);
			}
		}
		ModelAndView view = new ModelAndView("index");
		return view;
	}
	
	public static final String tryXML(String input) {
        if (input == null) {
             input = "";
            return input;
        }
        input = input.trim().replace("&", "&amp;");
        input = input.trim().replace("<", "&lt;");
        input = input.trim().replace(">", "&gt;");
        input = input.trim().replace("'", "&apos;");
        input = input.trim().replace("\\\"", "&quot;");
        return input;
}
}
