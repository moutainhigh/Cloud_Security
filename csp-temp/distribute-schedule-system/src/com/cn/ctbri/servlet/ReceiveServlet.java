package com.cn.ctbri.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.ITaskService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class HomeServlet
 */
//@WebServlet("/ReceiveServlet")
public class ReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(ReceiveServlet.class.getName());
	/**
	 * Default constructor.
	 */
	public ReceiveServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	    WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());  
        IAlarmService alarmService  = (IAlarmService) context.getBean("alarmService"); 
        ITaskService taskService  = (ITaskService) context.getBean("taskService");
		String parameter = request.getParameter("parameter");
//		String parameter = "{\"virtual_group_id\": \"5564088443b9090ba07d6297\", \"total\": 26, \"start_at\": \"2015-05-26 13:45:49\", \"task_id\": 6961, \"values\": [{\"url\": \"http://www.sinosoft.com.cn/ywycp/index.html\", \"created_at\": \"2015-05-26 14:13:19\", \"type\": \"xss\", \"value\": {\"body\": \"\", \"place\": \"query\", \"method\": \"GET\", \"param\": \"page\", \"queryheaderspath\": null}}, {\"url\": \"http://www.sinosoft.com.cn/khyal/index.html\", \"created_at\": \"2015-05-26 14:13:19\", \"type\": \"xss\", \"value\": {\"body\": \"\", \"place\": \"query\", \"method\": \"GET\", \"param\": \"page\", \"queryheaderspath\": null}}, {\"url\": \"http://www.sinosoft.com.cn/test/\", \"created_at\": \"2015-05-26 14:13:19\", \"type\": \"info_leak\", \"value\": {\"type\": \"test_samples\", \"match_str\": []}}], \"group_id\": 392, \"site_id\": 46218, \"site\": \"http://www.sinosoft.com.cn/\", \"end_at\": \"2015-05-26 14:13:21\"}";
//		String parameter = "{\"virtual_group_id\": \"5564088443b9090ba07d6297\", \"total\": 1, \"start_at\": \"2015-05-26 13:45:49\", \"task_id\": 6959, \"values\": [{\"url\": \"http://www.sinosoft.com.cn/\", \"created_at\": \"2015-05-26 13:46:14\", \"type\": \"availability\", \"value\": {\"ping\": 15, \"dns_hijack\": {\"hijacked\": false}, \"dns\": {\"time\": 3, \"address\": [\"219.239.36.4\"]}, \"http_get\": {\"status\": 200, \"time\": 773}}}], \"group_id\": 390, \"site_id\": 46216, \"site\": \"http://www.sinosoft.com.cn/\", \"end_at\": \"2015-05-26 13:46:19\"}";
		System.out.println(parameter);  
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		JSONObject dataJson = JSONObject.fromObject(parameter);
		String group_id= dataJson.getString("virtual_group_id");
		String start_at= dataJson.getString("start_at");
		String site= dataJson.getString("site");
        JSONArray array = dataJson.getJSONArray("values");
        try {
            for (int i = 0; i < array.size(); i++) {
                System.out.println("array:" + array.get(i));
                String url = (String) array.getJSONObject(i).get("url");
                String type = (String) array.getJSONObject(i).get("type");
                Object value = array.getJSONObject(i).get("value");
                JSONObject data = JSONObject.fromObject(value);
                List<Task> taskList = taskService.findTaskByGroupId(group_id);
                //根据taskId查询地区
        		int districtId = taskService.findDistrictIdByTaskId(String.valueOf(taskList.get(0).getTaskId()));
                if(type.equals("siteinfo")){
                    String ip = data.getString("ip");
                    if(taskList.size()>0){
                        Task task = new Task();
//                        task.setDns(ip);
                        task.setTaskId(taskList.get(0).getTaskId());
                        taskService.updateTask(task);
                    }
                }else if(type.equals("availability")){
                    Object http_get = data.getJSONObject("http_get");
                    JSONObject http_data = JSONObject.fromObject(http_get);
                    if(http_data.containsKey("status")){
                        String status = http_data.getString("status");
                    }
                   
                    Object dns_hijack = data.getJSONObject("dns_hijack");
                    JSONObject dns_hijacked = JSONObject.fromObject(dns_hijack);
                    boolean hijacked = dns_hijacked.getBoolean("hijacked");
                    
                    Object dns = data.getJSONObject("dns");
                    JSONObject dnsObj = JSONObject.fromObject(dns);
                    String address = dnsObj.getString("address");
                    TaskWarn taskwarn = new TaskWarn();
                    taskwarn.setCat1("可用性");
                    if(hijacked==true){
                        taskwarn.setName("被劫持");
                    }else{
                        taskwarn.setName("网络正常");
                        taskwarn.setMsg("网络正常");
                    }
                    taskwarn.setUrl(site);
                    taskwarn.setGroup_id(group_id);
                    taskwarn.setWarn_time(sdf.parse(start_at));
                    taskwarn.setServiceId(5);
                    taskwarn.setDistrictId(districtId);
                    taskService.insertTaskWarn(taskwarn);
                }else{
                    String level = "";
                    if(data.containsKey("level")){
                        level = data.getString("level");
                    }
                    
                    Alarm alarm = new Alarm();
                    alarm.setGroup_id(group_id);
//                    alarm.setAlarm_time(sdf.parse(start_at));
                    alarm.setAlarm_time(start_at);
                    alarm.setAlarm_content(url);
                    alarm.setUrl(site);
                    alarm.setAlarm_type(type);
                    alarm.setKeyword(value.toString());
                    alarm.setDistrictId(districtId);
                    //等级判断
                    if(type.equals("black_links")){//暗链
                        alarm.setName("暗链");
                        alarm.setLevel(2);
                        alarm.setServiceId(3);
                    }else if(type.equals("malscan")){//挂马
                        alarm.setName("挂马");
                        if(level.equals("mal")){
                            alarm.setLevel(2);
                        }else if(level.equals("high")){
                            alarm.setLevel(1);
                        }else if(level.equals("anomalous")){
                            alarm.setLevel(0);
                        }
                        alarm.setServiceId(2);
                    }else if(type.equals("keyword")){
                        alarm.setName("关键词");
                        alarm.setLevel(0);
                        alarm.setServiceId(4);
                    }else if(type.equals("sql")){
                        alarm.setName("SQL注入漏洞");
                        alarm.setLevel(2);
                        alarm.setServiceId(1);
                    }else if(type.equals("xss")){
                        alarm.setName("XSS跨站脚本漏洞");
                        alarm.setLevel(2);
                        alarm.setServiceId(1);
                    }else if(type.equals("webvul")){
                        alarm.setName("应用漏洞");
                        if(level.equals("8")||level.equals("9")||level.equals("10")){
                            alarm.setLevel(2);
                        }else if(level.equals("5")||level.equals("6")||level.equals("7")){
                            alarm.setLevel(1);
                        }else if(level.equals("1")||level.equals("2")||level.equals("3")||level.equals("4")){
                            alarm.setLevel(0);
                        }
                        alarm.setServiceId(1);
                    }else if(type.equals("info_leak")){
                        alarm.setName("信息泄露");
                        alarm.setLevel(0);
                        alarm.setServiceId(1);
                    }else if(type.equals("cgi")){
                        alarm.setName("CGI漏洞");
                        if(level.equals("8")||level.equals("9")||level.equals("10")){
                            alarm.setLevel(2);
                        }else if(level.equals("5")||level.equals("6")||level.equals("7")){
                            alarm.setLevel(1);
                        }else if(level.equals("1")||level.equals("2")||level.equals("3")||level.equals("4")){
                            alarm.setLevel(0);
                        }
                        alarm.setServiceId(1);
                    }else if(type.equals("csrf")){
                        alarm.setName("CSRF跨站请求伪造漏洞");
                        if(data.containsKey("crossdomain")){
                            alarm.setLevel(1);
                        }else{
                            alarm.setLevel(0);
                        }
                        alarm.setServiceId(1);
                    }else if(type.equals("form_crack")){
                        alarm.setName("表单破解漏洞");
                        alarm.setLevel(2);
                        alarm.setServiceId(1);
                    }
                    if(!type.equals("siteinfo")){
                        alarmService.saveAlarm(alarm);
                    }
                }
            }
        } catch (ParseException e) {
            logger.info("[获取结果调度]:解析任务进度发生异常!");
            e.printStackTrace();
        }
        
		if (true) { 
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.println("{\"code\": 0, \"message\": \"success\", \"result\": {\"id\": 1}}");
		}
	}

}
