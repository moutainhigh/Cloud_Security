package com.cn.ctbri.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.service.IOrderTaskService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.GetPath;

/**
 * 扫描订单任务表的调度类
 * 
 * @author tangxr 
 * 
 */

@SuppressWarnings("deprecation")
public class Scheduler4Task {

	static Logger logger = Logger.getLogger(Scheduler4Task.class.getName());

	private static String taskpage;

	@Autowired
    IOrderTaskService orderTaskService;
	
	static {
		try {
			Properties p = new Properties();
			p.load(Scheduler4Task.class.getClassLoader().getResourceAsStream("arnhem.properties"));
			taskpage = p.getProperty("TASKPAGE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute() throws Exception {
		logger.info("[下发任务调度]:任务表扫描开始......");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", Integer.parseInt(taskpage));
		map.put("task_status", Integer.parseInt(Constants.TASK_START));   //设置为 已开始？
		// 获取订单任务表前n条未下发的记录
		List<OrderTask> oTaskList = orderTaskService.findOrderTask(map);
		logger.info("[下发任务调度]:当前等待下发的任务有 " + oTaskList.size() + " 个!");
		// 调用接口下发任务
		String[] CustomManu = new String[0];//指定厂家
		for (OrderTask o : oTaskList) {
			//爬取
//			GetPath.getUrl(o);
			
			//开始时间
            String begin_date=DateUtils.dateToString(o.getBegin_date());
            //结束时间
        	String end_date=DateUtils.dateToString(o.getEnd_date());
        	//任务执行时间
        	String task_date=DateUtils.dateToString(o.getTask_date());
        	
			String result = InternalWorker.vulnScanCreate(String.valueOf(o.getType()), o.getUrl(), "", begin_date, end_date,  String.valueOf(o.getScan_type()), "", "", "", CustomManu, o.getOrderId(), String.valueOf(o.getServiceId()),  String.valueOf(o.getWebsoc()), task_date, o.getOrderTaskId());
        	System.out.println(result);
            
		}
		logger.info("[下发任务调度]:任务表扫描结束......");
	}
	
}
