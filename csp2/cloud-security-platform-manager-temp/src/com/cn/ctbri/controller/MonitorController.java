package com.cn.ctbri.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.common.MonitorWorker;
import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.Advertisement;
import com.cn.ctbri.entity.MonitorTask;
import com.cn.ctbri.service.IMonitorTaskService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.util.CommonUtil;

@Controller
public class MonitorController {

	@Autowired
	IMonitorTaskService monitorTaskService;
	
	/**
     * 功能描述：可用性监控列表页面
     * 参数描述：无
     *       @time 2017-11-3
     */
    @RequestMapping("/monitor.html")
    public String monitor(HttpServletRequest request){
    	
    	List taskList = monitorTaskService.findAllTask();
    	int orderNum = 0;
		if(taskList!=null&&taskList.size()>0){		
			orderNum= taskList.size();
			
		}
    	for (int i = 0; i < orderNum; i++) {
    		HashMap<String,Object>  mapTask = (HashMap<String,Object>)taskList.get(i);
    		
    		
		}
    	
    	request.setAttribute("MonList", taskList);
        return "/source/adminPage/userManage/monitor";
    }
    /**
     * 功能描述：可用性监控列表页面
     * 参数描述：无
     *       @time 2017-11-3
     */
    @RequestMapping("/monitorLine.html")
    public void monitorLine(HttpServletRequest request, HttpServletResponse response){
    	
    	
    	List taskList = monitorTaskService.findAllTask();
    	int orderNum = 0;
		if(taskList!=null&&taskList.size()>0){		
			orderNum= taskList.size();
			
		}
    	for (int i = 0; i < orderNum; i++) {
    		HashMap<String,Object>  mapTask = (HashMap<String,Object>)taskList.get(i);
    		
    		
		}
    	Map<String,Object> m = new HashMap<String, Object>();
    	m.put("taskList", taskList);
    	
    	JSONObject obj = CommonUtil.objectToJson(response, m);
    	try {
			CommonUtil.writeToJsp(response, obj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	//request.setAttribute("obj", obj);
       // return "/source/adminPage/userManage/monitor";
    }
    
    
    /**
     * 功能描述：可用性监控列表页面
     * 参数描述：无
     *       @time 2017-11-3
     *       errorcode : 0 成功  1任务名称重复  2创建接口返错误
     */
    @RequestMapping("/addMonitorTask.html")
    public String addMonitorTask(HttpServletRequest request,HttpServletResponse response){
    	
    	 String taskName = request.getParameter("name");
         String targetURL = request.getParameter("addr");
         String frequencyStr = request.getParameter("frequency");
         String monitor_typestr = request.getParameter("monType");
         String server_Type = request.getParameter("serverType");
         String port_lists = request.getParameter("port_lists");
         //String alarmEmail  = request.getParameter("alarmEmail");
         //String alarmPhone = request.getParameter("alarmPhone");
         String alarmNews = request.getParameter("alarm");
         String[] alarm = alarmNews.split(";");
         String[] alarmEmailArray = alarm[0].split(",");
         String[] alarmMessageArray = alarm[1].split(",");
         String alarmEmail="";
         String alarmMessage="";
         if (alarmEmailArray.length>1) {
			alarmEmail = alarmEmailArray[1];
		}
         if (alarmMessageArray.length>1) {
			alarmMessage = alarmMessageArray[1];
		}
         
         int frequecy = Integer.parseInt(frequencyStr);
         int monitor_type = Integer.parseInt(monitor_typestr);
         
         
         List tempList = monitorTaskService.findTaskListByTaskNameAccurate(taskName);
         if (tempList!=null&&tempList.size()>0) {
			System.out.println("＊＊＊＊＊＊＊监控任务名称重复＊＊＊＊＊");
			request.setAttribute("errorcode", 1);
			
		}
         else{
        	 
        	 try {
        		//调用接口 创建task任务 
        		 String createtask = MonitorWorker.createTask(monitor_typestr, targetURL, port_lists, frequencyStr);
        		 Map map = this.getCreateTaskResult(createtask);
        		 if(map != null && map.size() > 0){
        			 String status = map.get("status").toString();
        			 int task_id = Integer.parseInt(map.get("id").toString());
        			 
        			 if (status.equals("failed")) {//
        				 request.setAttribute("errorcode", 2);
					}else {
						request.setAttribute("errorcode", 0);
						//插入任务数据到 库
						MonitorTask task = new MonitorTask();
						task.setId(0);
						task.setTask_id(task_id);
						task.setTaskName(taskName);
						task.setTargetUrl(targetURL);
						task.setFrequency(frequecy);
						task.setMonitor_Type(monitor_type);
						task.setPort_lists(port_lists);
						task.setAlarmemail(alarmEmail);
						task.setAlarmphone(alarmMessage);
						
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String createTime = sdf.format(date);
						task.setCreateTime(createTime);
						
						monitorTaskService.insertMonitorTask(task);
					}
        		 }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
         }
        return "/source/adminPage/userManage/monitor";
    }
    
    public Map getCreateTaskResult(String createTaskString){
    	Map<String,Object> reMap = new HashMap<String,Object>();
    	try {
    		JSONObject obj = JSONObject.fromObject(createTaskString);
    		String strStatus = obj.getString("status");
    		String strId = obj.getString("id");
    		reMap.put("status", strStatus);
    		reMap.put("id", strId);
    		
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return reMap;
    	
    }
    
}
