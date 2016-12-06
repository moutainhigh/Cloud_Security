package com.cn.ctbri.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IDistrictDataService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.SMSUtils;

/**
 * 根据任务同步进度和结果的调度类
 * 
 * @author tangxr
 * 
 */
public class Scheduler4Result {

	static Logger logger = Logger.getLogger(Scheduler4Result.class.getName());
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITaskService taskService;
	@Autowired
	private IOrderAssetService orderAssetService;
	@Autowired
	private IAlarmService alarmService;
	@Autowired
	private IAssetService assetService;
	@Autowired
	private IDistrictDataService districtDataService;

	public void execute() throws Exception {
		logger.info("[获取结果调度]:任务表扫描开始....");
		/**
		 * 定时要job任务执行的逻辑
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		/*订单状态
		 * 1：完成无告警
		 * 2：完成有告警
		 * 3：扫描中有告警
		 * 4：开始扫描
		 * 5：暂停
		 */
		map.put("isAPI", 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();//获得系统时间.
        String nowTime = sdf.format(date);
		// 获取订单表前n条未开始执行的记录
		List<Order> orderList = orderService.findOrderByMap(map);
		for (Order order : orderList) {
			//订单服务类型
			int serviceId = order.getServiceId();
			//订单扫描类型
			String scanMode = String.valueOf(order.getType());
			String begin_date = DateUtils.dateToString(order.getBegin_date());
			String end_date = DateUtils.dateToString(order.getEnd_date());
//			List<OrderAsset> oaList = orderAssetService.findOrderAssetByOrderId(order.getId());
			//获取订单状态
			boolean session = false;
	    	try {
	    		session = NorthAPIWorker.getNorthSession();
			} catch (Exception e) {
//				e.printStackTrace();
			}
	    	//true成功连接服务管理系统
	    	try{
		    	if(session){
		    		if(order.getStatus()==4 || order.getStatus()==3){
		    			int s = 0;
	//	    			if(order.getType()==1){
		    				s = getTask(order);
	//	    			}
						Map<String,Object> paramMap = new HashMap<String,Object>();
						paramMap.put("orderId", order.getId());
			        	paramMap.put("type", String.valueOf(order.getType()));
			        	paramMap.put("isAlarm", 1);
						List<Task> tlist = taskService.findAllByOrderId(paramMap);
						
						paramMap.put("isAlarm", "");
						List<Task> tl = taskService.findAllByOrderId(paramMap);
						//add by tangxr 2016-2-25
						List<Task> finistlist = taskService.findFinishByOrderId(paramMap);
						if(tl.size() == finistlist.size()&&(s==1||s==2)&&serviceId!=5){
							int count = taskService.findissueCount(order.getId());
							if(count>0){
								order.setStatus(2);
								orderService.update(order);
							}else{
								order.setStatus(1);
								orderService.update(order);
							}
						//end 2016-2-25
						
						}else if((s==1||s==2)&&serviceId==5){
							order.setStatus(s);
							orderService.update(order);
						//end 2016-7-5
						}else{
							for (Task task : tlist) {
								//任务结束，告警未入库，并且非可用性
								if(task.getStatus()==3 && task.getIsAlarm()!=1 && serviceId!=5){
									String result = NorthAPIWorker.vulnScanGetResult(order.getId(),String.valueOf(task.getTaskId()));
									JSONObject jsonObj = new JSONObject().fromObject(result);
									JSONArray alarmArray = jsonObj.getJSONArray("alarmObj");
									if(alarmArray.size()>0){
										//查询资产，获取地理id add by 2016-8-16
										Asset a = assetService.findByOrderAssetId(Integer.parseInt(task.getOrder_asset_Id()));
										int districtId = 0;
										if(a!=null){
											districtId = a.getAssetProvince();
										}else{
											districtId = 35;
										}
										for (Object aObj : alarmArray) {
											JSONObject alarmObj = (JSONObject) aObj;
											String taskId = alarmObj.getString("taskId");
								            String alarm_time = alarmObj.getString("alarm_time");
								            String url = alarmObj.getString("url");
								            String alarm_type = alarmObj.getString("alarm_type");
								            String name = alarmObj.getString("name");
								            String score = alarmObj.getString("score");
								            String advice = alarmObj.getString("advice");
								            String alarm_content = alarmObj.getString("alarm_content");
								            String keyword = alarmObj.getString("keyword");
								            String serId = alarmObj.getString("serviceId");
								            String level = alarmObj.getString("level");
								            
								            Alarm alarm = new Alarm();
											alarm.setTaskId(Long.parseLong(taskId));
											alarm.setAlarm_time(DateUtils.stringToDateNYRSFM(alarm_time));
											alarm.setUrl(url);
											alarm.setAlarm_type(alarm_type);
											alarm.setName(name);
											alarm.setScore(score);
											alarm.setAdvice(advice);
											alarm.setLevel(Integer.parseInt(level));
											alarm.setAlarm_content(alarm_content);
											alarm.setKeyword(keyword);
											alarm.setServiceId(Integer.parseInt(serId));
											alarm.setDistrictId(districtId);
											alarmService.saveAlarm(alarm);
										}
										// 更新地域告警数  modify by 2016-8-23
						                Map<String, Object> disMap = new HashMap<String, Object>();
										disMap.put("id", districtId);
										disMap.put("count", alarmArray.size());
										disMap.put("serviceId", serviceId);
										districtDataService.updateDistrict(disMap);
										// end
										
										if(alarmArray.size()>0){
			                                List<Linkman> mlist= orderService.findLinkmanById(order.getContactId());
			                                if(mlist.size()>0){
			                                	Linkman linkman=mlist.get(0);
				                                String phoneNumber = linkman.getMobile();//联系方式
				                                String assetName = "";
				                                Asset asset = assetService.findByOrderAssetId(Integer.parseInt(task.getOrder_asset_Id()));
				                                if(asset!=null){
				                                	assetName = asset.getName();
				                                }else{
				                                	OrderAsset oa = orderAssetService.findOrderAssetById(Integer.parseInt(task.getOrder_asset_Id()));
					                                assetName = oa.getAssetName();
				                                }
				    //                          int sendFlag=order.getMessage();//是否下发短信
				                                if(!phoneNumber.equals("") && phoneNumber!=null){
				                                //发短信
				                                SMSUtils smsUtils = new SMSUtils();
				                                smsUtils.sendMessage_warn(phoneNumber,order,assetName,String.valueOf(alarmArray.size()));
				                                order.setMessage(1);
				                                order.setStatus(3);
				                                orderService.update(order);
			                                }
			                               }
				                        }
									}else{
	//									order.setStatus(1);
	//									orderService.update(order);
									}
									task.setIsAlarm(1);
									taskService.updateTask(task);
								}else{
									String result = NorthAPIWorker.vulnScanGetStatus(order.getId());
									JSONObject obj = new JSONObject().fromObject(result);
									if(!obj.getString("code").equals("421")){//订单不存在
										int status = obj.getInt("status");
										int websoc = obj.getInt("websoc");
										JSONArray taskArray = obj.getJSONArray("taskObj");
										if(taskArray.size()>0){
											for (Object tObj : taskArray) {
												JSONObject taskObj = (JSONObject) tObj;
												int sta = taskObj.getInt("status");
												int taskId = taskObj.getInt("taskId");
												String executeTime = null;
												if(tObj.toString().contains("executeTime")){
													executeTime = taskObj.getString("executeTime");
												}
												String beginTime = taskObj.getString("beginTime");
												String endTime = taskObj.getString("endTime");
												String groupFlag = taskObj.getString("groupFlag");
												String engineIP = taskObj.getString("engineIP");
									            String taskProgress = taskObj.getString("taskProgress");
									            String currentUrl = taskObj.getString("currentUrl");
									            String scanTime = taskObj.getString("scanTime");
									            String issueCount = taskObj.getString("issueCount");
									            String requestCount = taskObj.getString("requestCount");
									            String urlCount = taskObj.getString("urlCount");
									            String averResponse = taskObj.getString("averResponse");
									            String averSendCount = taskObj.getString("averSendCount");
									            String sendBytes = taskObj.getString("sendBytes");
									            String receiveBytes = taskObj.getString("receiveBytes");
										        Task t = new Task();
										        t.setTaskId(taskId);
	//									        t.setOrder_asset_Id(String.valueOf(oaList.get(0).getId()));
										        t.setExecute_time(DateUtils.stringToDateNYRSFM(executeTime));
												t.setBegin_time(DateUtils.stringToDateNYRSFM(beginTime));
												t.setEnd_time(DateUtils.stringToDateNYRSFM(endTime));
												t.setGroup_flag(DateUtils.stringToDateNYRSFM(groupFlag));
										        t.setEngineIP(engineIP);
										        t.setTaskProgress(taskProgress);
										        t.setCurrentUrl(currentUrl);
										        t.setScanTime(scanTime);
										        t.setIssueCount(issueCount);
										        t.setRequestCount(requestCount);
										        t.setUrlCount(urlCount);
										        t.setAverResponse(averResponse);
										        t.setAverSendCount(averSendCount);
										        t.setSendBytes(sendBytes);
								                t.setReceiveBytes(receiveBytes);
										        t.setStatus(sta);
										        taskService.insert(t);
											}
	//										order.setStatus(status);
	//										order.setWebsoc(websoc);
	//										orderService.update(order);
										}
									}
									if(serviceId==5){
										String result1 = NorthAPIWorker.vulnScanGetResult(order.getId(),String.valueOf(task.getTaskId()));
										JSONObject jsonObj = new JSONObject().fromObject(result1);
										JSONArray taskwarnArray = jsonObj.getJSONArray("taskwarnObj");
										if(taskwarnArray.size()>0){
											for (Object wObj : taskwarnArray) {
												JSONObject warnObj = (JSONObject) wObj;
												int warnid = warnObj.getInt("id");
												String cat1 = warnObj.getString("cat1");
												String cat2 = warnObj.getString("cat2");
									            String name = warnObj.getString("name");
									            String severity = warnObj.getString("severity");
									            String rule = warnObj.getString("rule");
									            String ct = warnObj.getString("ct");
									            String app_p = warnObj.getString("app_p");
									            String tran_p = warnObj.getString("tran_p");
									            String url = warnObj.getString("url");
									            String msg = warnObj.getString("msg");
									            String task_id = warnObj.getString("task_id");
									            
									            TaskWarn taskwarn = new TaskWarn();
									            taskwarn.setId(warnid);
								                taskwarn.setCat1(cat1);
								                taskwarn.setCat2(cat2);
								                taskwarn.setName(name);
								                taskwarn.setSeverity(Integer.parseInt(severity));
								                taskwarn.setRule(rule);
								                taskwarn.setCt(Integer.parseInt(ct));
								                taskwarn.setApp_p(app_p);
								                taskwarn.setTran_p(tran_p);
								                taskwarn.setUrl(url);
								                taskwarn.setMsg(msg);
								                taskwarn.setTask_id(task_id);
								                taskwarn.setWarn_time(new Date());
								                taskwarn.setServiceId(5);
								                taskService.insertTaskWarn(taskwarn);
											}
											order.setStatus(3);
			                                orderService.update(order);
										}
										
									}
									
								}
							}
						}
						
					}else if(order.getStatus()==0){
						getTask(order);
					}
	    		}else{
	    			
	    			logger.info("[获取结果调度]:服务管理系统连接异常....");
	    		}
			}catch (Exception e) {
				e.printStackTrace();
	//            logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
	            continue;
	        }
				
		}
		logger.info("[获取结果调度]:任务表扫描结束....");
	}
	
	
	int getTask(Order order){
		String result = NorthAPIWorker.vulnScanGetStatus(order.getId());
		JSONObject obj = new JSONObject().fromObject(result);
		if(!obj.getString("code").equals("421")){//订单不存在
			int status = obj.getInt("status");
			int websoc = obj.getInt("websoc");
			JSONArray taskArray = obj.getJSONArray("taskObj");
			if(taskArray.size()>0){
				for (Object tObj : taskArray) {
					JSONObject taskObj = (JSONObject) tObj;
					int sta = taskObj.getInt("status");
					int taskId = taskObj.getInt("taskId");
					String executeTime = null;
					if(tObj.toString().contains("executeTime")){
						executeTime = taskObj.getString("executeTime");
					}
					String beginTime = taskObj.getString("beginTime");
					String endTime = taskObj.getString("endTime");
					String groupFlag = taskObj.getString("groupFlag");
					String engineIP = taskObj.getString("engineIP");
		            String taskProgress = taskObj.getString("taskProgress");
		            String currentUrl = taskObj.getString("currentUrl");
		            String scanTime = taskObj.getString("scanTime");
		            String issueCount = taskObj.getString("issueCount");
		            String requestCount = taskObj.getString("requestCount");
		            String urlCount = taskObj.getString("urlCount");
		            String averResponse = taskObj.getString("averResponse");
		            String averSendCount = taskObj.getString("averSendCount");
		            String sendBytes = taskObj.getString("sendBytes");
		            String receiveBytes = taskObj.getString("receiveBytes");
		            String url = taskObj.getString("url");
			        
		            Map<String,Object> param = new HashMap<String,Object>();
					param.put("orderId", order.getId());
		        	param.put("url", url);
		        	List<OrderAsset> oaList = orderAssetService.findOrderAssetId(param);
		        	
			        Task t = new Task();
			        t.setTaskId(taskId);
			        t.setOrder_asset_Id(String.valueOf(oaList.get(0).getId()));
			        t.setExecute_time(DateUtils.stringToDateNYRSFM(executeTime));
					t.setBegin_time(DateUtils.stringToDateNYRSFM(beginTime));
					t.setEnd_time(DateUtils.stringToDateNYRSFM(endTime));
					t.setGroup_flag(DateUtils.stringToDateNYRSFM(groupFlag));
			        t.setEngineIP(engineIP);
			        t.setTaskProgress(taskProgress);
			        t.setCurrentUrl(currentUrl);
			        t.setScanTime(scanTime);
			        t.setIssueCount(issueCount);
			        t.setRequestCount(requestCount);
			        t.setUrlCount(urlCount);
			        t.setAverResponse(averResponse);
			        t.setAverSendCount(averSendCount);
			        t.setSendBytes(sendBytes);
	                t.setReceiveBytes(receiveBytes);
			        t.setStatus(sta);
			        taskService.insert(t);
				}
				//modify by 2016-7-8
				if(order.getStatus()!=3){
					order.setStatus(4);
				}
				order.setWebsoc(websoc);
				orderService.update(order);
			}
			return status;
		}else{
			return 0;
		}
		
	}
}
