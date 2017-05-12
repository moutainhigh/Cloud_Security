package com.cn.ctbri.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IOrderTaskService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.DateUtils;

/**
 * 扫描订单任务表的调度类
 * 
 * @author tangxr 
 * 
 */

@SuppressWarnings("deprecation")
public class Scheduler4Task {

	static Logger logger = Logger.getLogger(Scheduler4Task.class.getName());

	@Autowired
    IOrderTaskService orderTaskService;
	@Autowired
    IUserService userService;

	public void execute() throws Exception {
		logger.info("[下发任务调度]:任务表扫描开始......");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("task_status", Integer.parseInt(Constants.TASK_START));   //设置为未开始
		// 获取订单任务表前n条未下发的记录
		List<OrderTask> oTaskList = orderTaskService.findOrderTask(map);
		logger.info("[下发任务调度]:当前等待下发的任务有 " + oTaskList.size() + " 个!");
		// 调用接口下发任务
		for (OrderTask o : oTaskList) {
			//爬取
//			GetPath.getUrl(o);
			
			//单次、长期
			String scanMode = String.valueOf(o.getType());
			//目标地址，只有一个
			String targetUrl = o.getUrl();
			//ScanType 扫描方式（正常、快速、全量）
			String scanType = "";
			//开始时间
//            String begin_date = DateUtils.dateToString(o.getBegin_date());
			String begin_date = DateUtils.dateToString(o.getTask_date());
            //结束时间
        	String end_date = DateUtils.dateToString(o.getEnd_date());
        	//周期
        	String scanPeriod = String.valueOf(o.getScan_type());
        	//检测深度
        	String scanDepth = "";
       	    //最大页面数
        	String maxPages = "";
    	    //策略
        	String stategy = "";
        	//服务类型
        	String serviceId = String.valueOf(o.getServiceId());
        	//任务执行时间
//        	String task_date = DateUtils.dateToString(o.getTask_date());
        	//订单任务Id
        	String orderTaskId = o.getId() + "_" + o.getOrderId();//o.getOrderTaskId();
        	
        	//厂商
        	String[] customManu = null;
            String websoc = String.valueOf(o.getWebsoc());
            if(websoc != null && websoc != ""){
            	customManu = websoc.split(","); //拆分字符为"," ,然后把结果交给数组customManu 
            }
            //查询user,新增合作方
            User user = userService.finUserByOrder(o.getOrderId());
            String partner = "";
            if(user!=null){
            	if(user.getPartner()!=null){
            		partner = user.getPartner();
            	}
            }
        	String result = "";
        	result = InternalWorker.vulnScanCreate(scanMode, targetUrl, scanType, begin_date, end_date, scanPeriod, scanDepth, maxPages, stategy, customManu, orderTaskId, serviceId, partner);
        	if(result.equals("success")){
        		o.setWarn(1);
        		orderTaskService.update(o);
        	}
            
		}
		logger.info("[下发任务调度]:任务表扫描结束......");
	}
	
}
