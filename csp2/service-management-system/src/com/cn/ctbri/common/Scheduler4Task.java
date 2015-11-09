package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;

/**
 * 扫描任务表的调度类
 * 
 * @author googe
 * 
 */

@SuppressWarnings("deprecation")
public class Scheduler4Task {

	static Logger logger = Logger.getLogger(Scheduler4Task.class.getName());

	private static String taskpage;

	@Autowired
	ITaskService taskService;
	
	@Autowired
	IAssetService assetService;
	
	@Autowired
	IServService servService;
	
	@Autowired
    IOrderService orderService;
	
	@Autowired
    ITaskWarnService taskWarnService;
	
	@Autowired
    IEngineService engineService;

	private String destURL = "";

	private String destIP = "";

	private String tplName ="";
	
	private int scantime = 0;

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
		map.put("status", Integer.parseInt(Constants.TASK_START));   //设置为 已开始？
		// 获取任务表前n条未下发的记录
		List<Task> taskList = taskService.findTask(map);
		logger.info("[下发任务调度]:当前等待下发的任务有 " + taskList.size() + " 个!");
		// 调用接口下发任务
		for (Task t : taskList) {
		    //获取订单类型
            OrderAsset orderAsset = taskService.getTypeByAssetId(Integer.parseInt(t.getOrder_asset_Id()));
            List<Order> orderList = orderService.findByOrderId(orderAsset.getOrderId());
            Map<String, Object> engineMap = new HashMap<String, Object>();
            EngineCfg engine = new EngineCfg();

            int serviceId = 0;
            if(orderList!=null&&orderList.size()>0){
				serviceId = orderList.get(0).getServiceId();
	            engineMap.put("serviceId", serviceId);
//	            engineMap.put("factory", t.getWebsoc());
	            engineMap.put("websoc", t.getWebsoc());
//	            engine = engineService.findEngineByParam(engineMap);
	            List<EngineCfg> engineList = engineService.findEngineByParam(engineMap);
	            for (EngineCfg engineCfg : engineList) {
					engine = engineCfg;
					String sessionid = "";
		            boolean engineStatus = false;
					if(engine.getEngine()==3){
						for(int i=0;i<3;i++){
			                sessionid = WebSocWorker.getSessionId();
			                if(sessionid!=null&&sessionid!=""){
			                    engineStatus = true;
			                    break;
			                }
			                if(!engineStatus){
			                	continue;
			                }
			            }
						if(!engineStatus){
							continue;
		                }else{
		                	break;
		                }
					}else{
						for(int i=0;i<3;i++){
				            sessionid = ArnhemWorker.getSessionId(engine.getEngine());
				            if(sessionid!=null&&sessionid!=""){
				                engineStatus = true;
				                break;
				            }
				            if(!engineStatus){
			                	continue;
			                }
				        }
						if(!engineStatus){
							continue;
		                }else{
		                	break;
		                }
					}
				}
			}
            
            //获取任务下发引擎 modify by tang 2015-10-29
//            List<EngineCfg> engines = getUsableEngine(engineMap);
            //获取可用设备
//            List<EngineCfg> usableEngine = engineService.getUsableEngine(engineMap);
            try{
    		    //创宇任务下发
    		    if(t.getWebsoc()==1){
    		        getWebsoc(t,orderAsset,orderList,serviceId);
    		    }else if(t.getWebsoc()==2){//引擎调度
    		    	double arn0 = 0;
    		    	double arn1 = 0;
    		    	double arn2 = 0;
    		    	
    		        List arn = taskService.getArnhemTask();
    		        List websoc = taskService.getWebsocTask();
//    		        if(arn.size()>10&&websoc.size()>30){
//    		            t.setEngine(-1);
//    		            taskService.update(t);
//    		        }else{
    		            //创建sessionId
//    		            String websessionid = "";
//    		            boolean engineStatus = false;
//    		            for(int i=0;i<3;i++){
//    		                websessionid = WebSocWorker.getSessionId();
//    		                if(websessionid!=null){
//    		                    engineStatus = true;
//    		                    break;
//    		                }
//    		            }
//    		            String arnsessionid = "";
//                        boolean arnengineStatus = false;
//                        for(int i=0;i<3;i++){
//                            arnsessionid = ArnhemWorker.getSessionId(engine.getEngine());
//                            if(arnsessionid!=null){
//                                arnengineStatus = true;
//                                break;
//                            }
//                        }
                        if(engine.getEngine()==3){//创宇
                        	getWebsoc(t,orderAsset,orderList,serviceId);
	                        t.setWebsoc(1);
	                        taskService.update(t); 
	                        Order o = orderList.get(0);
	                        o.setWebsoc(1);
	                        orderService.update(o);
                        }else{//安恒
                        	 getArnhem(t,orderAsset,orderList,serviceId,engine);
                             t.setWebsoc(0);
                             taskService.update(t);
                             Order o = orderList.get(0);
                             o.setWebsoc(0);
                             orderService.update(o);
                        }
//    		            if(arn.size()/10.0 <= websoc.size()/30.0){
//    		                if(arnengineStatus){
//    		                    getArnhem(t,orderAsset,orderList,serviceId);
//                                t.setWebsoc(0);
//                                taskService.update(t);
//                                Order o = orderList.get(0);
//                                o.setWebsoc(0);
//                                orderService.update(o);
//    		                }else{
//    		                    getWebsoc(t,orderAsset,orderList,serviceId);
//                                t.setWebsoc(1);
//                                taskService.update(t); 
//                                Order o = orderList.get(0);
//                                o.setWebsoc(1);
//                                orderService.update(o);
//    		                }
//    		                
//    		            }else if(arn.size()/10.0 > websoc.size()/30.0){
//    		                if(engineStatus){
//    		                    getWebsoc(t,orderAsset,orderList,serviceId);
//                                t.setWebsoc(1);
//                                taskService.update(t);
//                                Order o = orderList.get(0);
//                                o.setWebsoc(1);
//                                orderService.update(o);
//    		                }else{
//    		                    getArnhem(t,orderAsset,orderList,serviceId);
//                                t.setWebsoc(0);
//                                taskService.update(t);
//                                Order o = orderList.get(0);
//                                o.setWebsoc(0);
//                                orderService.update(o);
//    		                }   		                
//    		            }else{
//    		                t.setEngine(-1);
//                            taskService.update(t);
//    		            }
//    		        }
                }else{
        			getArnhem(t,orderAsset,orderList,serviceId,engine);
    		    }
            } catch (Exception e) {
                logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//              throw new RuntimeException("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
                continue;
            }
		}
		logger.info("[下发任务调度]:任务表扫描结束......");
		
		
		
		/**
         * 定时要job任务删除的逻辑
         */
        Map<String, Object> delmap = new HashMap<String, Object>();
        delmap.put("page", Integer.parseInt(taskpage));
        delmap.put("status", Integer.parseInt(Constants.TASK_RUNNING));
        // 获取任务表前n条未完成的记录
        List<Task> taskDelList = taskService.findDelTask(delmap);
        // 调用接口删除任务
        for (Task t : taskDelList) {
//            EngineCfg engine = engineService.findEngineIdbyIP(String.valueOf(t.getEngine()));
        	int engine = 0;
			EngineCfg en = engineService.getEngineById(t.getEngine());
			if(t.getEngine()!=0){
				engine = en.getEngine();
			}else{
				engine = 2;
			}
            logger.info("[删除任务调度]:任务-[" + t.getTaskId() + "]获取状态!");
            //获取订单类型
            OrderAsset orderAsset = taskService.getTypeByAssetId(Integer.parseInt(t.getOrder_asset_Id()));
            List<Order> orderList = orderService.findByOrderId(orderAsset.getOrderId());
            String sessionId = ArnhemWorker.getSessionId(engine);
            String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, String.valueOf(t.getTaskId())+"_"+orderList.get(0).getId(), engine);
            String status = this.getStatusByResult(resultStr);
            if("running".equals(status)){
                logger.info("[删除任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
                try {
                    Map<String, Object> hashmap = new HashMap<String, Object>();
                    hashmap.put("orderId", orderList.get(0).getId());
                    hashmap.put("websoc", orderList.get(0).getWebsoc());
//                    if(orderList.get(0).getServiceId()==5&&orderList.get(0).getWebsoc()!=1){
//                        ArnhemWorker.removeTask(sessionId, String.valueOf(t.getTaskId())+"_"+orderList.get(0).getId());
                        if(orderList.size() > 0){
                            Order o = orderList.get(0);
                            //获取告警信息
                            List<TaskWarn> taskWarnList=taskWarnService.findTaskWarnByOrderId(hashmap);
                            if(taskWarnList.size() > 0){
                                o.setStatus(Integer.parseInt(Constants.ORDERALARM_YES));
                            }else{
                                o.setStatus(Integer.parseInt(Constants.ORDERALARM_NO));
                            }
                            orderService.update(o);
                        }
//                    }else if(orderList.get(0).getServiceId()==5&&orderList.get(0).getWebsoc()==1){
//                        if(orderList.size() > 0){
//                            Order o = orderList.get(0);
//                            //获取告警信息
//                            List<TaskWarn> taskWarnList=taskWarnService.findTaskWarnByOrderId(hashmap);
//                            if(taskWarnList.size() > 0){
//                                o.setStatus(Integer.parseInt(Constants.ORDERALARM_YES));
//                            }else{
//                                o.setStatus(Integer.parseInt(Constants.ORDERALARM_NO));
//                            }
//                            orderService.update(o);
//                        }
//                    }
                    //任务完成后,引擎活跃数减1
                    en.setId(t.getEngine());
                    engineService.updatedown(en);
                    //更新任务状态为finish
                    t.setStatus(Integer.parseInt(Constants.TASK_FINISH));
                    taskService.update(t);
                } catch (Exception e) {
                    logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                    throw new RuntimeException("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
                    continue;
                }
                logger.info("[删除任务调度]:任务-[" + t.getTaskId() + "]完成删除!");
            }else{
                logger.info("[删除任务调度]: 任务-[" + t.getTaskId() + "]下发失败!，远程存在同名任务请先删除或重新下订单!");
            }

        }
        logger.info("[删除任务调度]:任务表扫描结束......");
		
	}

	private void getArnhem(Task t, OrderAsset orderAsset, List<Order> orderList, int serviceId, EngineCfg engine) {
	    logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]获取状态!");
        //创建sessionId
        String sessionId = "";
        boolean engineStatus = false;
//        EngineCfg engine = new EngineCfg();
        for(int i=0;i<3;i++){
            sessionId = ArnhemWorker.getSessionId(engine.getEngine());
            if(sessionId!=null){
                engineStatus = true;
                break;
            }
        }
        if(engineStatus){
            String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, String.valueOf(t.getTaskId())+"_"+orderList.get(0).getId(), engine.getEngine());
            String status = this.getStatusByResult(resultStr);
            if("".equals(status)){
                logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
                preTaskData(t,engine);
                try {
                    String lssued = ArnhemWorker.lssuedTask(sessionId, String.valueOf(t.getTaskId())+"_"+orderList.get(0).getId(), this.destURL, this.destIP, "80",
                            this.tplName, engine.getEngine());
                    boolean state = this.getStatusBylssued(lssued);
                    if(state){
                        //任务下发后,引擎活跃数加1
//                        engine.setActivity(engine.getActivity()+1);
                        engine.setId(engine.getId());
                        engineService.update(engine);
                        
                        //更新任务状态为running
                        t.setStatus(Integer.parseInt(Constants.TASK_RUNNING));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
                        TimeZone.setDefault(tz);
                        Date date = new Date();//获得系统时间.
                        String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
                        t.setExecute_time(sdf.parse(nowTime));
                        t.setEngine(engine.getId());
                        taskService.update(t);
                        logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
                    }
//                  if(!Constants.SERVICE_LDSMFW.equals(this.tplName)){
                    if(serviceId==2||serviceId==3||serviceId==4){
                        if(orderList.get(0).getType()==1){
                            //下一次扫描时间
                            Date endTime = orderList.get(0).getEnd_date();
                            Map<String, Object> paramMap = new HashMap<String, Object>();
//                          paramMap.put("executeTime", t.getExecute_time());
                            paramMap.put("executeTime", t.getGroup_flag());
                            paramMap.put("scantime", this.scantime);
                            Date nextTime = taskService.getNextScanTime(paramMap);
                            if(nextTime.compareTo(endTime)<=0){
                                //创建任务
                                Task task = new Task(); 
                                task.setExecute_time(nextTime);
                                task.setStatus(Integer.parseInt(Constants.TASK_START));
                                //设置订单详情id
                                task.setOrder_asset_Id(t.getOrder_asset_Id());
                                task.setGroup_flag(nextTime);
                                if(t.getWebsoc()==0){
                                	task.setWebsoc(0);
                                }else{
                                	task.setWebsoc(2);
                                }
                                //插入一条任务数据  获取任务id
                                int taskId = taskService.insert(task);
                            }
                        }
                    }
                    if(serviceId==1){
                        if(orderList.get(0).getType()==1){
                            //下一次扫描时间
                            Date endTime = orderList.get(0).getEnd_date();
                            Map<String, Object> paramMap = new HashMap<String, Object>();
//                            paramMap.put("executeTime", t.getExecute_time());
//                            paramMap.put("scantime", this.scantime);
//                            Date nextTime = taskService.getNextScanTime(paramMap);
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
//                            Date nextTime = getAfterDate(t.getExecute_time());
                            Date nextTime = getAfterDate(t.getGroup_flag());
                            if(nextTime.compareTo(endTime)<=0){
                                //创建任务
                                Task task = new Task(); 
                                task.setExecute_time(nextTime);
                                task.setStatus(Integer.parseInt(Constants.TASK_START));
                                //设置订单详情id
                                task.setOrder_asset_Id(t.getOrder_asset_Id());
                                task.setGroup_flag(nextTime);
                                if(t.getWebsoc()==0){
                                	task.setWebsoc(0);
                                }else{
                                	task.setWebsoc(2);
                                }
                                //插入一条任务数据  获取任务id
                                int taskId = taskService.insert(task);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                  throw new RuntimeException("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                    continue;
                }
            }else{
                logger.info("[下发任务调度]: 任务-[" + t.getTaskId() + "]下发失败!，远程存在同名任务请先删除或重新下订单!");
            }
        }else{
            t.setEngine(-1);
            taskService.update(t);
        }
    }

    private void getWebsoc(Task t, OrderAsset orderAsset, List<Order> orderList, int serviceId) {
	    String[] assetArray = null;   
        assetArray = t.getOrder_asset_Id().split(",");  
        String[] assets = new String[assetArray.length];
        for(int i=0;i<assetArray.length;i++){
            Asset asset = assetService.findAssetById(Integer.parseInt(assetArray[i]));
            assets[i] = asset.getAddr();
        }
        //websoc任务信息设置接口参数
        preTaskDatasoc(Integer.parseInt(assetArray[0]));
        try {
            //创建sessionId
            String sessionid = "";
            boolean engineStatus = false;
            //获取创宇引擎
            EngineCfg engine = engineService.getEngine();
            for(int i=0;i<3;i++){
                sessionid = WebSocWorker.getSessionId();
                if(sessionid!=null){
                    engineStatus = true;
                    break;
                }
            }
            //下发任务,返回任务组id
            String virtual_group_id = "";
            if(engineStatus){
                //下发任务
                virtual_group_id = WebSocWorker.lssuedTask(sessionid,String.valueOf(t.getTaskId())+"_"+orderList.get(0).getId(),assets,orderList.get(0).getServiceId());
                //任务下发后,引擎活跃数加1
//                engine.setActivity(engine.getActivity()+1);
                engine.setId(engine.getId());
                engineService.update(engine);
                //更新任务状态为running
                t.setStatus(Integer.parseInt(Constants.TASK_RUNNING));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
                TimeZone.setDefault(tz);
                Date date = new Date();//获得系统时间.
                String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
                t.setExecute_time(sdf.parse(nowTime));
                t.setGroup_id(virtual_group_id);
                //下发到创宇引擎，7
                t.setEngine(engine.getId());
                taskService.update(t);
                logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
            
//                  OrderAsset orderAsset = taskService.getTypeByAssetId(Integer.parseInt(assetArray[0]));
//                  List<Order> orderList = orderService.findByOrderId(orderAsset.getOrderId());
                if(orderList.get(0).getServiceId()==2||orderList.get(0).getServiceId()==3||orderList.get(0).getServiceId()==4||orderList.get(0).getServiceId()==5){
                    if(orderList.get(0).getType()==1){
                        //下一次扫描时间
                        Date endTime = orderList.get(0).getEnd_date();
                        Map<String, Object> paramMap = new HashMap<String, Object>();
    //                          paramMap.put("executeTime", t.getExecute_time());
                        paramMap.put("executeTime", t.getGroup_flag());
                        paramMap.put("scantime", this.scantime);
                        Date nextTime = taskService.getNextScanTime(paramMap);
                        if(nextTime.compareTo(endTime)<=0){
                            //创建任务
                            Task task = new Task(); 
                            task.setExecute_time(nextTime);
                            task.setStatus(Integer.parseInt(Constants.TASK_START));
                            //设置订单详情id
                            task.setOrder_asset_Id(t.getOrder_asset_Id());
                            task.setGroup_flag(nextTime);
                            if(t.getWebsoc()==1){
                            	task.setWebsoc(1);
                            }else{
                            	task.setWebsoc(2);
                            }
//                            task.setWebsoc(1);
                            //插入一条任务数据  获取任务id
                            int taskId = taskService.insert(task);
                        }
                    }
                }
                if(orderList.get(0).getServiceId()==1){
                    if(orderList.get(0).getType()==1){
                        //下一次扫描时间
                        Date endTime = orderList.get(0).getEnd_date();
                        Map<String, Object> paramMap = new HashMap<String, Object>();
    //                            paramMap.put("executeTime", t.getExecute_time());
    //                            paramMap.put("scantime", this.scantime);
    //                            Date nextTime = taskService.getNextScanTime(paramMap);
//                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
    //                            Date nextTime = getAfterDate(t.getExecute_time());
                        Date nextTime = getAfterDate(t.getGroup_flag());
                        if(nextTime.compareTo(endTime)<=0){
                            //创建任务
                            Task task = new Task(); 
                            task.setExecute_time(nextTime);
                            task.setStatus(Integer.parseInt(Constants.TASK_START));
                            //设置订单详情id
                            task.setOrder_asset_Id(t.getOrder_asset_Id());
                            task.setGroup_flag(nextTime);
                            if(t.getWebsoc()==1){
                            	task.setWebsoc(1);
                            }else{
                            	task.setWebsoc(2);
                            }
                            //插入一条任务数据  获取任务id
                            int taskId = taskService.insert(task);
                        }
                    }
                }
            }else{
                t.setEngine(-1);
                taskService.update(t);
            }
        } catch (Exception e) {
            logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//          throw new RuntimeException("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//            continue;
        }
    }

    /**
	 * 根据任务信息设置接口参数
	 * 
	 * @param task
     * @param engine 
	 */
	private void preTaskData(Task task, EngineCfg engine) {
		// 获取此任务的资产信息
		List<Asset> taskList = assetService.findByTask(task);
		if(taskList != null && taskList.size() > 0 ){
			if(!isboolIp(taskList.get(0).getAddr())){ //判断地址是否是ip
				this.destURL = taskList.get(0).getAddr();
			}else{
				this.destIP = taskList.get(0).getAddr();
			}
		}else{
			//从remarks获取地址
			if(!isboolIp(task.getRemarks())){ //判断地址是否是ip
				this.destURL = task.getRemarks();
			}else{
				this.destIP = task.getRemarks();
			}
		}
		// 获取此任务的服务模版名称及扫描频率集合
		List<HashMap<String, Object>> servList = servService.findByTask(task);
		if(servList != null && servList.size() > 0 ){
			this.tplName = (String) servList.get(0).get("module_name");
			/**
			 * 可用性与网页篡改处理
			 */
			//获取扫描频率
			int rate = (Integer) servList.get(0).get("scan_type");
			if(Constants.SERVICE_KYXJCFW.equals(this.tplName)){
				
				switch (rate) {
				case 1:
					this.tplName = Constants.TPL_KYXJCFU_10M +engine.getEngine_name();
					this.scantime = 10;
					break;
				case 2:
					this.tplName = Constants.TPL_KYXJCFU_30M +engine.getEngine_name();
					this.scantime = 30;
					break;
				case 3:
					this.tplName = Constants.TPL_KYXJCFU_1H +engine.getEngine_name();
					this.scantime = 60;
					break;
				case 4:
					this.tplName = Constants.TPL_KYXJCFU_2H +engine.getEngine_name();
					this.scantime = 120;
					break;
				}
				
			}else if(Constants.SERVICE_WYCGJCFW.equals(this.tplName)){
				switch (rate) {
				case 1:
					this.tplName = Constants.TPL_WYCGJCFW_30M2+engine.getEngine_name();
					this.scantime = 30;
					break;
				case 2:
					this.tplName = Constants.TPL_WYCGJCFW_1H2+engine.getEngine_name();
					this.scantime = 60;
					break;
				case 3:
					this.tplName = Constants.TPL_WYCGJCFW_2H2+engine.getEngine_name();
					this.scantime = 120;
					break;
				case 4:
					this.tplName = Constants.TPL_WYCGJCFW+engine.getEngine_name();
					this.scantime = 1440;
					break;
				}
			}else if(Constants.SERVICE_GJZJCFW.equals(this.tplName)){
                switch (rate) {
                case 4:
                	this.tplName = Constants.SERVICE_GJZJCFW+engine.getEngine_name();
                    this.scantime = 1440;
                    break;
                default:
                	this.tplName = Constants.SERVICE_GJZJCFW+engine.getEngine_name();
                	break;
                }
            }else if(Constants.SERVICE_EYDMJCFW.equals(this.tplName)){
                switch (rate) {
                case 1:
                	this.tplName = Constants.SERVICE_EYDMJCFW+engine.getEngine_name();
                    this.scantime = 30;
                    break;
                default:
                	this.tplName = Constants.SERVICE_EYDMJCFW+engine.getEngine_name();
                	break;
                }
            }else if(Constants.SERVICE_LDSMFW.equals(this.tplName)){
            	this.tplName = Constants.SERVICE_LDSMFW+engine.getEngine_name();
            }
			
		}
//		else{
//			this.tplName = Constants.TPL_SYKSSM;
//		}
		
	}
	
	/** 
	 * 判断是否为合法IP 
	 * @return the ip 
	 */  
	public boolean isboolIp(String ipAddress)  
	{  
	       String ip = "([1-9]|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])(//.(//d|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])){3}";   
	       Pattern pattern = Pattern.compile(ip);   
	       Matcher matcher = pattern.matcher(ipAddress);   
	       return matcher.matches();   
	}  
	
	private String getStatusByResult(String resultStr) {
		String decode;
		String state = "";
		try {
			decode = URLDecoder.decode(resultStr, "UTF-8");
			Document document = DocumentHelper.parseText(decode);
			Element result = document.getRootElement();
			Attribute attribute  = result.attribute("value");
			String resultValue = attribute.getStringValue();
			if("Success".equals(resultValue)){
				Element StateNode = result.element("State");
				state = StateNode.getTextTrim();
			}else{
				logger.info("[下发任务调度]:远程没有此任务，可以下发!");
			}
			return state;
		} catch (Exception e) {
			logger.info("[下发任务调度]:解析任务状态失败,远程没有此任务，可以下发!");
			return "";
		}
		
	}
	
	private boolean getStatusBylssued(String lssued) {
        String decode;
        boolean state = false;
        try {
            decode = URLDecoder.decode(lssued, "UTF-8");
            Document document = DocumentHelper.parseText(decode);
            Element result = document.getRootElement();
            Attribute attribute  = result.attribute("value");
            String resultValue = attribute.getStringValue();
            if("Success".equals(resultValue)){
                state = true;
            }
            return state;
        } catch (Exception e) {
            logger.info("[下发任务调度]:解析任务状态失败,远程没有此任务，可以下发!");
            return state;
        }
        
    }
	
	/**
	 * 
	 * @param task
	 * @throws SchedulerException 
	 */
	public void getResultByTask(Task task) throws SchedulerException{
		//获取引擎名称
//		servService = new ServServiceImpl();
//		List<Serv> sList = servService.findByTask(task);
//		String SName = sList.get(0).getName();
//		if("漏洞扫描服务".equals(SName)){
//			this.productId = Constants.PRODUCT_LD;
//		}else if("恶意代码监测服务".equals(SName)){
//			this.productId = Constants.PRODUCT_MM;
//		}else if("网页篡改监测服务".equals(SName)){
//			this.productId = Constants.PRODUCT_CG;
//		}else if("关键字监测服务".equals(SName)){
//			this.productId = Constants.PRODUCT_GJZ;
//		}else if("可用性监测服务".equals(SName)){
//			this.productId = Constants.PRODUCT_KYX;
//		}
		
		//获取任务id
		//this.taskId = String.valueOf(task.getTaskId());
		/**
		 * 为任务创建一个调度
		 */
//		SchedulerFactory schedFact = new StdSchedulerFactory();
//		Scheduler scheduler = schedFact.getScheduler();
//		// 创建一个JobDetail，指明name，groupname，以及具体的Job类名，
//		//该Job负责定义需要执行任务
//		JobDetail jobDetail = new JobDetail("job"+task.getTaskId(), Scheduler.DEFAULT_GROUP,Scheduler4Result.class);
//		jobDetail.getJobDataMap().put("task", task);
//		//jobDetail.getJobDataMap().put("task", task);
//		//根据任务信息创建触发器  设置调度策略
//		SimpleTrigger trigger = new SimpleTrigger("SimpleTrigger" , Scheduler.DEFAULT_GROUP);
//		trigger.setStartTime(new Date());  //立即执行
//		trigger.setEndTime(null);
//		trigger.setRepeatCount(0);
//		trigger.setRepeatInterval(60*1000);   //   每隔1分钟执行一次
//		scheduler.scheduleJob(jobDetail, trigger);
//		//启动调度
//		//scheduler.start();
	}
	
    /**
     * 得到某个日期的后一天日期
     * @param d
     * @return
     */
    public Date getAfterDate(Date d){
         Date date = d;
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.add(Calendar.DAY_OF_MONTH,1);  
         date = calendar.getTime();  
         return date;
    }
    
    /**
     * websoc任务信息设置接口参数
     * 
     * @param task
     */
    private void preTaskDatasoc(int asset) {
        OrderAsset orderAsset = taskService.getTypeByAssetId(asset);
        List<Order> orderList = orderService.findByOrderId(orderAsset.getOrderId());
        if(orderList != null && orderList.size() > 0 ){
            /**
             * 可用性与网页篡改处理
             */
            //获取扫描频率
            int rate = (Integer) orderList.get(0).getScan_type();
            if(orderList.get(0).getServiceId()==5){
                switch (rate) {
                case 1:
                    this.scantime = 10;
                    break;
                case 2:
                    this.scantime = 30;
                    break;
                case 3:
                    this.scantime = 60;
                    break;
                case 4:
                    this.scantime = 120;
                    break;
                }
            }else if(orderList.get(0).getServiceId()==3){
                switch (rate) {
                case 1:
                    this.scantime = 30;
                    break;
                case 2:
                    this.scantime = 60;
                    break;
                case 3:
                    this.scantime = 120;
                    break;
                case 4:
                    this.scantime = 1440;
                    break;
                }
            }else if(orderList.get(0).getServiceId()==4){
                switch (rate) {
                case 4:
                    this.scantime = 1440;
                    break;
                }
            }else if(orderList.get(0).getServiceId()==2){
                switch (rate) {
                case 1:
                    this.scantime = 30;
                    break;
                }
            }
            
        }
    }
    
    //获取服务支持的引擎
    public List<EngineCfg> getUsableEngine(Map<String, Object> engineMap) {
        String ableIds = "";
        //获取可用设备
        List<EngineCfg> usableEngine = engineService.getUsableEngine(engineMap);
        for (EngineCfg engineCfg : usableEngine) {
            //创宇
            if(String.valueOf(engineMap.get("factory"))=="1"){
                ableIds = ableIds + engineCfg.getId() + ",";
            }else{
            	String sessionId = null;
//                String sessionId = ArnhemWorker.getSessionId(engineCfg.getEngine_api(),engineCfg.getUsername(),engineCfg.getPassword());
                //获取引擎状态
                String resultStr = ArnhemWorker.getEngineState(sessionId,engineCfg.getEngine_api());
                //解析引擎状态,返回可用引擎ip
                List ableList = getStatusByResultEn(resultStr);
                for (int i = 0; i < ableList.size(); i++) {
                    String ip = (String) ableList.get(i);
                    EngineCfg engine = engineService.findEngineIdbyIP(ip);
                    ableIds = ableIds + engine.getId() + ",";
                }
            }
        }
        //获取任务下发的引擎
        ableIds = ableIds.substring(0,ableIds.length()-1);
//        EngineCfg minActivity = engineService.findMinActivity(ableIds);
        if(ableIds!=null){
            List<EngineCfg> ableActivity = engineService.findAbleActivity(ableIds);
            return ableActivity;
        }else{
            return null;
        }
        
        
    }
    
    private List getStatusByResultEn(String resultStr) {
        List ableList = new ArrayList();
        List enableList = new ArrayList();
        String decode;
        try {
            decode = URLDecoder.decode(resultStr, "UTF-8");
            Document document = DocumentHelper.parseText(decode);
            Element result = document.getRootElement();
            Attribute attribute  = result.attribute("value");
            String resultValue = attribute.getStringValue();
            if("Success".equals(resultValue)){
                List<Element> EngineList = result.elements("EngineList");
                for (Element engine : EngineList) {
                    String ip = engine.element("IP").getTextTrim();
                    String cpuOccupancy = engine.element("CpuOccupancy").getTextTrim();
                    Element eng = engine.element("Engine");
                    String engineState = eng.element("EngineState").getTextTrim();
                    if (!cpuOccupancy.equals("")){
                        //可用ip
                        ableList.add(ip);
                    }else{
                        //不可用ip
                        enableList.add(ip);
                        //发短信
                    }
                }
            }
            return ableList;
        } catch (Exception e) {
            logger.info("解析引擎状态失败!");
            return null;
        }
        
    }
}
