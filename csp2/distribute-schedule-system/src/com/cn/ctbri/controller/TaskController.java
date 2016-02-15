package com.cn.ctbri.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;

import org.dom4j.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IProducerService;
import com.cn.ctbri.service.ITaskService;

@Controller
public class TaskController {
	@Autowired
	private ITaskService taskService;
	@Autowired  
    private IProducerService producerService;   
    @Autowired  
    @Qualifier("taskQueueDestination")   
    private Destination taskDestination; 
    
    /**
     * 接收到webservice任务
     */
	@RequestMapping(value="test.html",method=RequestMethod.GET)
	public void receiveTask(Task t){
		//通过webservice获取返回结果，获取任务
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("page", 1);
		paramMap.put("status", 1);
		List<Task> list = taskService.findTask(paramMap);
		Task task = list.get(0);
		
		//将任务插入当前系统数据库
		taskService.insert(task);
		//将任务放到消息队列里		
		producerService.sendMessage(taskDestination, task);
	}
	
	/**
	 * 任务结果请求
	 */
	public void requestResult(int taskId){
		
	}
}
