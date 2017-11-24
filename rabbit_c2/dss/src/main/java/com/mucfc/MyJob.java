package com.mucfc;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cn.ctbri.dao.TaskMapper;
import com.cn.ctbri.model.Task;
import com.entity.CrtOrder;
@DisallowConcurrentExecution
public class MyJob implements Job{
	private static final Logger logger = Logger.getLogger(MyJob.class); 
	@Resource(name = "taskDao")
	private  TaskMapper taskDao;
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String s = (String)context.getJobDetail().getJobDataMap().get("crtOrder");
		System.out.println("Hello quzrtz  "+
		    s +
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()));
		net.sf.json.JSONObject o = JSONObject.fromObject(s);
		CrtOrder crtO =(CrtOrder) JSONObject.toBean(o,CrtOrder.class);
		String orderId = crtO.getOrderId();
		String assetId = crtO.getAssetId();
		//取出漏扫类型(serviceType=1)的未开始运行(status=1)的任务
		Task task_para = new Task();
		task_para.setAssetid(assetId);
		task_para.setOrderid(orderId);
		task_para.setStatus(1);
		task_para.setServicetype(1);
		List<Task> taskList = taskDao.selectByTask(task_para);
		//为每一个未开始运行的漏扫任务，分配引擎
		for(Task task_no_start : taskList){
			int run_times = task_no_start.getTotaltimes();//记录当前任务运行次数
			
		}
		
		
	}

}
