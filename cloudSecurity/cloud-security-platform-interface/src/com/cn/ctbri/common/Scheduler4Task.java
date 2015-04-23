package com.cn.ctbri.common;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;

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
			logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]获取状态!");
			String sessionId = ArnhemWorker.getSessionId();
			String resultStr = ArnhemWorker.getStatusByTaskId(sessionId, String.valueOf(t.getTaskId()));
			String status = this.getStatusByResult(resultStr);
			if("".equals(status)){
				logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
				preTaskData(t);
				try {
					ArnhemWorker.lssuedTask(ArnhemWorker.getSessionId(), String.valueOf(t.getTaskId()), this.destURL, this.destIP, "80",
							this.tplName);
					//获取订单类型
					OrderAsset orderAsset = taskService.getTypeByAssetId(t.getOrder_asset_Id());
					List<Order> orderList = orderService.findByOrderId(orderAsset.getOrderId());
//					if(!Constants.SERVICE_LDSMFW.equals(this.tplName)){
					if(orderList.get(0).getServiceId()==3||orderList.get(0).getServiceId()==5||orderList.get(0).getServiceId()==4){
					    if(orderList.get(0).getType()==1){
					        //下一次扫描时间
					        Date endTime = orderList.get(0).getEnd_date();
					        Map<String, Object> paramMap = new HashMap<String, Object>();
					        paramMap.put("executeTime", t.getExecute_time());
					        paramMap.put("scantime", this.scantime);
					        Date nextTime = taskService.getNextScanTime(paramMap);
					        if(nextTime.compareTo(endTime)<=0){
					            //创建任务
	                            Task task = new Task(); 
	                            task.setExecute_time(nextTime);
	                            task.setStatus(Integer.parseInt(Constants.TASK_START));
	                            //设置订单详情id
	                            task.setOrder_asset_Id(t.getOrder_asset_Id());
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
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
                            Date nextTime = getAfterDate(t.getExecute_time());
                            if(nextTime.compareTo(endTime)<=0){
                                //创建任务
                                Task task = new Task(); 
                                task.setExecute_time(nextTime);
                                task.setStatus(Integer.parseInt(Constants.TASK_START));
                                //设置订单详情id
                                task.setOrder_asset_Id(t.getOrder_asset_Id());
                                //插入一条任务数据  获取任务id
                                int taskId = taskService.insert(task);
                            }
                        }
                    }
				} catch (Exception e) {
					logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
					throw new RuntimeException("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
				}
				//为此任务创建调度，定时获取任务结果
				//getResultByTask(t);
				//更新任务状态为running
				t.setStatus(Integer.parseInt(Constants.TASK_RUNNING));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
			    TimeZone.setDefault(tz);
			    Date date = new Date();//获得系统时间.
			    String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
			    t.setExecute_time(sdf.parse(nowTime));
			    
				taskService.update(t);
				logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
			}else{
				logger.info("[下发任务调度]: 任务-[" + t.getTaskId() + "]下发失败!，远程存在同名任务请先删除或重新下订单!");
			}

		}
		logger.info("[下发任务调度]:任务表扫描结束......");

	}

	/**
	 * 根据任务信息设置接口参数
	 * 
	 * @param task
	 */
	private void preTaskData(Task task) {
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
					this.tplName = Constants.TPL_KYXJCFU_10M ;
					this.scantime = 10;
					break;
				case 2:
					this.tplName = Constants.TPL_KYXJCFU_30M ;
					this.scantime = 30;
					break;
				case 3:
					this.tplName = Constants.TPL_KYXJCFU_1H ;
					this.scantime = 60;
					break;
				case 4:
					this.tplName = Constants.TPL_KYXJCFU_2H ;
					this.scantime = 120;
					break;
				}
				
			}else if(Constants.SERVICE_WYCGJCFW.equals(this.tplName)){
				switch (rate) {
				case 1:
					this.tplName = Constants.TPL_WYCGJCFW_30M2;
					this.scantime = 30;
					break;
				case 2:
					this.tplName = Constants.TPL_WYCGJCFW_1H2;
					this.scantime = 60;
					break;
				case 3:
					this.tplName = Constants.TPL_WYCGJCFW_2H2;
					this.scantime = 120;
					break;
				case 4:
					this.tplName = Constants.TPL_WYCGJCFW_1D2;
					this.scantime = 1440;
					break;
				}
			}else if(Constants.SERVICE_GJZJCFW.equals(this.tplName)){
                switch (rate) {
                case 1:
                    this.scantime = 30;
                    break;
                }
            }
			
		}else{
			this.tplName = Constants.TPL_SYKSSM;
		}
		
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
}
