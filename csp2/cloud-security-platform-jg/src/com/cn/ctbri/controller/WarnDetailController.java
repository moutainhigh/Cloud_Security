package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.quartz.ee.jta.UserTransactionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import se.akerfeldt.com.google.gson.Gson;

import com.cn.ctbri.common.APIWorker;
import com.cn.ctbri.common.Constants;
import com.cn.ctbri.common.SysWorker;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmDDOSService;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IServiceAPIService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.DateUtils;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-5-5
 * 描        述：  订单告警管理控制层
 * 版        本：  1.0
 */
@Controller
public class WarnDetailController {
	
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderAssetService orderAssetService;
    @Autowired
    IAlarmService alarmService;
    @Autowired
    ITaskService taskService;
    @Autowired
    ITaskWarnService taskWarnService;
    @Autowired
    IAlarmDDOSService alarmDDOSService;
    @Autowired
    IAssetService assetService;
    @Autowired
    IServiceAPIService serviceAPIService;
    
    
	
	
    @RequestMapping(value="warningInit.html")
    public String warningInit(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String websoc = request.getParameter("websoc");
//        String taskId = request.getParameter("taskId");
        String groupId=request.getParameter("groupId");
        User user = (User)request.getSession().getAttribute("globle_user");
        //创宇标志
        request.setAttribute("websoc", websoc);
        //时间分组标志
        request.setAttribute("group_flag", groupId);
        //获取订单信息
        List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId); 
        /* SELECT a.id ,COUNT(a.id)	FROM (SELECT o.id ,t.taskId FROM cs_order o, cs_order_asset oa, cs_task t WHERE o.id={id} AND oa.id=t.order_asset_Id)a GROUP BY a.id HAVING a.id={id}*/
        request.setAttribute("order", orderList.get(0));
        //获取服务ID
        int serviceId=0;
        HashMap<String, Object> order=new HashMap<String, Object>();
	    order=(HashMap) orderList.get(0);
	    serviceId=(Integer) order.get("serviceId");
        
        //根据orderId查询正在执行的任务
        List runList = orderService.findTaskRunning(orderId); 
        //SELECT t.* FROM cs_task t,cs_order_asset oa  WHERE t.order_asset_Id = oa.id AND oa.orderId=#{orderId} and t.status = 2
        
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        
        //获取服务所对应的资产 ，并根据资产查询告警信息  modify by tangxr 2016-5-5
        Task task = null;
        List assets = orderAssetService.findAssetsByOrderId(orderId);
        if(assets != null && assets.size() > 0){
        	for(int i = 0; i < assets.size(); i++){
        		
        		HashMap<String,Object>  map = (HashMap<String,Object>)assets.get(i);
        		//获取对应告警信息
                Map<String, Object> paramAlarm = new HashMap<String, Object>();
                paramAlarm.put("orderId", orderId);
                paramAlarm.put("type", type);
                paramAlarm.put("group_flag", groupId);
                paramAlarm.put("count", assets.size());
                paramAlarm.put("websoc", websoc);
                paramAlarm.put("orderAssetId", map.get("orderAssetId"));
                
                if(serviceId==1 || serviceId==2){
                	List<Alarm> asset_alarmList = alarmService.getAlarmByAsset(paramAlarm);
                    
        	        //资产告警数量和告警信息 放入map
        	        map.put("aNum", asset_alarmList.size());
    		        map.put("asset_alarmList", asset_alarmList);
        		}
		        
		        //任务基本信息
		        task = new Task();
                task = taskService.findBasicInfoByOrderId(paramAlarm);
	            Map<String, Object> taskParam = this.getInfo(task);
	            map.put("scanTime", taskParam.get("scanTime"));
	            map.put("send", taskParam.get("send"));
	            map.put("receive", taskParam.get("receive"));
	            if(task!=null){
	                if(task.getBegin_time()!=null){
	                    task.setBeginTime( DateUtils.dateToString(task.getBegin_time()));
	                }
	                if(task.getEnd_time()!=null){
	                    task.setEndTime(DateUtils.dateToString(task.getEnd_time())); 
	                }
	                if(task.getExecute_time()!=null){
	                    task.setExecuteTime(DateUtils.dateToString(task.getExecute_time())); 
	                }
	            }
	            map.put("task", task);
	            
	            if(task!=null){
	                map.put("progress", 100);
	                if(task.getCurrentUrl()!=null && !task.getCurrentUrl().equals("")){
	                	map.put("currentUrl", task.getCurrentUrl());
	                }else{
	                	map.put("currentUrl", "无");
	                }
	            }else{
//	                if(task.getStatus()==3){
//	                	map.put("progress", 100);
//	                	map.put("currentUrl", "无");
//	                }else{
	                	map.put("progress", 0);
	                	map.put("currentUrl", "暂无");
//	                }
	            }
        	}
        }
        request.setAttribute("assets", assets);
        //end 
        
        request.setAttribute("assetList", assetList);
        //获取对应告警信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String id = orderService.getOrderById(orderId, type, user.getId());
        if(id=="null"||"".equals(id)){
//        	request.setAttribute("errorMsg", "订单信息不存在!");
//        	return "/source/error/errorMsg";
        	return "redirect:/orderTrackInit.html";
        }else{
        	
        	//有告警：修改task表是否查看告警标识为1
            /*if(orderList.get(0).get("status").equals(2) || orderList.get(0).get("status").equals(3)){
            	Map<String, Object> paramMap1 = new HashMap<String, Object>();
            	if(type.equals("2")){
            		paramMap1.put("orderId", orderId);
            		taskService.update(paramMap1);
            	}else{
            		if(groupId != null){
                		paramMap1.put("orderId", orderId);
                		paramMap1.put("groupId", groupId);
                		taskService.update(paramMap1);
            		}
            	}

        	}*/
    		
	        paramMap.put("orderId", orderId);
	        paramMap.put("type", type);
	        paramMap.put("group_flag", groupId);
	        paramMap.put("count", assetList.size());
	        paramMap.put("websoc", websoc);
	        List<Alarm> alarmList = alarmService.getAlarmByOrderId(paramMap);
	        request.setAttribute("alist", alarmList.size());
	       
	        //获取历史时间
	        List taskTime = taskService.findScanTimeByOrderId(orderId);
	        request.setAttribute("taskTime", taskTime);
	        if(runList.size()>0){
	        	request.setAttribute("timeSize", 0);
	        }
	        
//	        if((orderList.get(0).get("status").equals(0)||runList.size()>0)&&groupId==null){
//	        if(!orderList.get(0).get("status").equals(1) && !orderList.get(0).get("status").equals(2) ){
	        if(false){
	        	int status=0;
	        	if(runList.size()>0){
	        		status = Integer.parseInt(Constants.TASK_RUNNING);
	        	}
	            return this.service(request,paramMap,status,orderList);
	        }else {
	            
	            /*
	            //当任务状态为3，完成状态
	        	int status = Integer.parseInt(Constants.TASK_FINISH);
	        	request.setAttribute("status", status);
	            //获取进度
	            Map<String, Object> pMap = new HashMap<String, Object>();
	            pMap.put("orderId", orderId);
	            pMap.put("status", status);
	            pMap.put("group_flag", groupId);
	            pMap.put("count", assetList.size());
	            Task taskpro = taskService.findProgressByOrderId(pMap);
	            if(taskpro!=null){
	                request.setAttribute("progress", 100);
	                if(taskpro.getCurrentUrl()!=null && !taskpro.getCurrentUrl().equals("")){
	                    request.setAttribute("currentUrl", taskpro.getCurrentUrl());
	                }else{
	                    request.setAttribute("currentUrl", "无");
	                }
	            }else{
	                if(status==3){
	                    request.setAttribute("progress", 100);
	                    request.setAttribute("currentUrl", "无");
	                }else{
	                    request.setAttribute("progress", 0);
	                    request.setAttribute("currentUrl", "暂无");
	                }
	                
	            }*/
	            
	            
	            
	            /** 基本信息   dyy*/
	            /*Task task = new Task();
	//            if(Integer.parseInt(type)==1){
	//                //长期查找最近的任务
	//                task = taskService.findNearlyTask(orderId);
	//            }else{
	                task = taskService.findBasicInfoByOrderId(paramMap);
	//            }
	            Map<String, Object> taskParam = this.getInfo(task);
	            //request.setAttribute("scanTime", taskParam.get("scanTime"));
	            //request.setAttribute("send", taskParam.get("send"));
	            //request.setAttribute("receive", taskParam.get("receive"));
	            if(task!=null){
	                if(task.getBegin_time()!=null){
	                    task.setBeginTime( DateUtils.dateToString(task.getBegin_time()));
	                }
	                if(task.getEnd_time()!=null){
	                    task.setEndTime(DateUtils.dateToString(task.getEnd_time())); 
	                }
	                if(task.getExecute_time()!=null){
	                    task.setExecuteTime(DateUtils.dateToString(task.getExecute_time())); 
	                }
	            }*/
	            //request.setAttribute("task", task);
	            
	            
	            if(serviceId==6||serviceId==7||serviceId==8){//DDOS
	            	//获取对应IP
		            List IPList = orderService.findIPByOrderId(orderId);
	            	List<AlarmDDOS> alarmDDosList = alarmService.getAlarmDdosByOrderId(paramMap);
	            	request.setAttribute("ipList", IPList);
	            	request.setAttribute("serviceId", serviceId);
	                request.setAttribute("alarmList", alarmDDosList);
	                return "/source/page/order/order_warning";
	            	
	            }else{//安恒的服务
	        		switch (serviceId) {
	    			case 3:/**篡改  dyy*/
	                	//获取：敏感词  折线图信息
	                	Map<String,Object> m = new HashMap<String, Object>();
	    				m.put("orderId", orderId);
	    				m.put("url", null);
	                	List<Alarm> alarm = alarmService.findSensitiveWordByOrderId(m);
	                	request.setAttribute("alarm", alarm);
	                	request.setAttribute("alist", alarm.size());
	                	
	                    request.setAttribute("alarmList", alarmList);
//	            		return "/source/page/order/warning_doctort"	;
	            		return "/source/page/personalCenter/warnDoctor";
	    			case 4:/**关键字监测服务 dyy*/    				
	    				//关键字告警信息
	    				List<Alarm> keywordList = alarmService.findKeywordWarningByOrderId(orderId);
	    				
	    				if(keywordList!=null){
	    					for(Alarm a :keywordList){
	    						a.setAlarmTime(DateUtils.dateToString(a.getAlarm_time()));
	    					}
	    				}
	    				request.setAttribute("keywordList", keywordList);
	    				request.setAttribute("keyList", keywordList.size());
	    				
	    				
	    				String flag=request.getParameter("flag");
	    				//关键字 折线图 左侧信息
	    				Map<String,Object> m1 = new HashMap<String, Object>();
	    				m1.put("orderId", orderId);
	    				m1.put("url", null);
	    				m1.put("flag",flag);
	    				List<Alarm> alarmKeyWordList = alarmService.findSensitiveWordByOrderId(m1);
	                	request.setAttribute("alarmKeyWordList", alarmKeyWordList);
	    				//敏感词排行榜(只适合一个资产)
	                	Map<String,Object> map = new HashMap<String, Object>();
	                	map.put("orderId", orderId);
	                	int flagalarm=0;
	                	if(alarmKeyWordList!=null){
	                		for(int a=0;a<alarmKeyWordList.size();a++){
	                			if(a==0){
	                				String url = alarmKeyWordList.get(0).getUrl();
	                    			map.put("url", url);
	                    			List<Alarm> list = alarmService.findKeywordByUrlAndOrderId(map);
	                    			List<Alarm> mapSortData = getSortData(list);
	                    			request.setAttribute("mapSortData", mapSortData);
	                    			
			                		Date tempTime=alarmKeyWordList.get(0).getAlarm_time();
			                		Date d2 = new Date(); //当前时间
			                		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd"); //格式化为 yyyymmdd
			                		int d1Number = Integer.parseInt(f.format(tempTime).toString()); //将取出数据格式化后转为int
			                		int d2Number = Integer.parseInt(f.format(d2).toString()); //将当前时间格式化后转为int
			                		int value=d2Number-d1Number;
			                		if(value==0){
			                			flagalarm=1;
			                		}else if(value==1){
			                			flagalarm=2;
			                		}else{
			                			flagalarm=3;
			                		}
			                		 request.setAttribute("value", flagalarm);
	                			}
	                		}
	                	}
//	                	return "/source/page/order/warning_keyword"	;
	                	return "/source/page/personalCenter/warnKeyword";
	    			case 5:/**可用性监测服务 dyy*/
	    				//获取推送告警信息
	    	        	String flag_able=request.getParameter("flag");
	    	        	paramMap.put("flag", flag_able);
	    	            List<TaskWarn> taskWarnList=taskWarnService.findTaskWarnByOrderId(paramMap);
	    	            //处理时间Thu Apr 16 09:47:38 CST 2015=》年月日时分秒
	    	        	if(taskWarnList!=null){
	    	        		for(TaskWarn t : taskWarnList){
	    	        			t.setWarnTime(DateUtils.dateToString(t.getWarn_time()));
	    	        		}
	    	        	}
	    	            request.setAttribute("taskWarnList", taskWarnList);
	    	            
	    	            /** 获取告警次数  dyy*/
	    	            TaskWarn taskCount = taskWarnService.findTaskWarnCountByOrderId(orderId);
	    	            request.setAttribute("count", taskCount.getCount());
	    				
	    				//可用性  折线图 左侧信息
	    				Map<String,Object> m2 = new HashMap<String, Object>();
	    				m2.put("orderId", orderId);
	    				m2.put("url", null);
	    				//可用率统计
	    		    	List<TaskWarn> listUseable = taskWarnService.findUseableByOrderId(paramMap);
	    		    	request.setAttribute("listUseable", listUseable);
	    			    request.setAttribute("wlist", taskWarnList.size());
	    			  
	    			    //根据url查询折线图和orderId
	    		    	Map<String, Object> m22 = new HashMap<String, Object>();
	    		    	m22.put("orderId", orderId);
	    		    	int flag1=0;
	    		    	if(listUseable!=null&&listUseable.size()>0){
	    		    		String url = listUseable.get(0).getUrl();
			    			m22.put("url", url); 
			    			List<TaskWarn> listRight = taskWarnService.findWarnByOrderIdAndUrl(m22);
			                for(int j =0 ;j< listRight.size();j++){
			                	if(j==listRight.size()-1){
			                		Date tempTime=listRight.get(j).getWarn_time();
			                		Date d2 = new Date(); //当前时间
			                		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd"); //格式化为 yyyymmdd
			                		int d1Number = Integer.parseInt(f.format(tempTime).toString()); //将取出数据格式化后转为int
			                		int d2Number = Integer.parseInt(f.format(d2).toString()); //将当前时间格式化后转为int
			                		int value=d2Number-d1Number;
			                		if(value==0){
			                			flag1=1;
			                		}else if(value==1){
			                			flag1=2;
			                		}else{
			                			flag1=3;
			                		}
			                	}
			                }
			    		}
			    		
	    		    		
	    			    //可用率计算
	    			    long usable = 0l;
	    			    long huifu = 0l;
	    			    for (int i = 0; i < taskWarnList.size(); i++) {
	    			    	if(taskWarnList.get(i).getName().equals("断网")){
	    			    		if(i==0){
	    			    			usable = usable + taskWarnList.get(i).getWarn_time().getTime()-task.getExecute_time().getTime();
	    			    		}else{
	    			    			usable = usable + (taskWarnList.get(i).getWarn_time().getTime()-huifu);
	    			    		}
	    			    		
	    			    	}else{
	    			    		huifu = taskWarnList.get(i).getWarn_time().getTime();
	    			    	}
						}
	    			    float usabling = 0f;
	    			    if(task!=null&&task.getExecute_time()!=null){
	    			    	usabling = Float.parseFloat(String.valueOf(usable))/Float.parseFloat(String.valueOf((task.getEnd_time().getTime()-task.getExecute_time().getTime())));
	    			    }
	    			    request.setAttribute("usabling", usabling*100+"%");
	    			    request.setAttribute("value", flag1);
//	    			    return "/source/page/order/order_usable";
	    			    return "/source/page/personalCenter/warnUsable";
	    			default: //漏洞和木马
	    				
//	    	            request.setAttribute("alarmList", alarmList);
	    	            return "/source/page/personalCenter/warnDetail";
	    			}
	            }
	        }
      
        }
    
    }
    
    @RequestMapping(value="taskFinish.html")
    public String taskFinish(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String websoc = request.getParameter("websoc");
        String groupId=request.getParameter("groupId");
        String orderAssetId=request.getParameter("orderAssetId");
        User user = (User)request.getSession().getAttribute("globle_user");
        
        //创宇标志
        request.setAttribute("websoc", websoc);
        //时间分组标志
        request.setAttribute("group_flag", groupId);
        //获取订单信息
        List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
        request.setAttribute("order", orderList.get(0));
        //根据orderId查询正在执行的任务
        List runList = orderService.findTaskRunning(orderId);
        
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        
        //获取服务所对应的资产 ，并根据资产查询告警信息  modify by tangxr 2016-5-5
        List assets = orderAssetService.findAssetsByOrderId(orderId);
		//获取对应告警信息
        Map<String, Object> paramAlarm = new HashMap<String, Object>();
        paramAlarm.put("orderId", orderId);
        paramAlarm.put("type", type);
        paramAlarm.put("group_flag", groupId);
        paramAlarm.put("count", assets.size());
        paramAlarm.put("websoc", websoc);
        paramAlarm.put("orderAssetId", orderAssetId);
        List<Alarm> asset_alarmList = alarmService.getAlarmByAsset(paramAlarm);
        
        //资产告警数量和告警信息 放入map
        request.setAttribute("aNum", asset_alarmList.size());
        request.setAttribute("asset_alarmList", asset_alarmList);
        
        //任务基本信息
        Task task = new Task();
        task = taskService.findBasicInfoByOrderId(paramAlarm);
        Map<String, Object> taskParam = this.getInfo(task);
        request.setAttribute("scanTime", taskParam.get("scanTime"));
        request.setAttribute("send", taskParam.get("send"));
        request.setAttribute("receive", taskParam.get("receive"));
        if(task!=null){
            if(task.getBegin_time()!=null){
                task.setBeginTime( DateUtils.dateToString(task.getBegin_time()));
            }
            if(task.getEnd_time()!=null){
                task.setEndTime(DateUtils.dateToString(task.getEnd_time())); 
            }
            if(task.getExecute_time()!=null){
                task.setExecuteTime(DateUtils.dateToString(task.getExecute_time())); 
            }
        }
        request.setAttribute("task", task);
        
        if(task!=null){
        	request.setAttribute("progress", 100);
            if(task.getCurrentUrl()!=null && !task.getCurrentUrl().equals("")){
            	request.setAttribute("currentUrl", task.getCurrentUrl());
            }else{
            	request.setAttribute("currentUrl", "无");
            }
        }else{
            if(task.getStatus()==3){
            	request.setAttribute("progress", 100);
            	request.setAttribute("currentUrl", "无");
            }else{
            	request.setAttribute("progress", 0);
            	request.setAttribute("currentUrl", "暂无");
            }
            
        }

        request.setAttribute("assets", assets);
        //end 
        
        request.setAttribute("assetList", assetList);
        //获取对应告警信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String id = orderService.getOrderById(orderId, type, user.getId());
        if(id=="null"||"".equals(id)){
//        	request.setAttribute("errorMsg", "订单信息不存在!");
//        	return "/source/error/errorMsg";
        	return "redirect:/orderTrackInit.html";
        }else{
        	
        	//有告警：修改task表是否查看告警标识为1
        	if(orderList.get(0).get("status").equals(2) || orderList.get(0).get("status").equals(3)){
            	Map<String, Object> paramMap1 = new HashMap<String, Object>();
            	if(type.equals("2")){
            		paramMap1.put("orderId", orderId);
            		taskService.update(paramMap1);
            	}else{
            		if(groupId != null){
                		paramMap1.put("orderId", orderId);
                		paramMap1.put("groupId", groupId);
                		taskService.update(paramMap1);
            		}
            	}

        	}

    		
	        paramMap.put("orderId", orderId);
	        paramMap.put("type", type);
	        paramMap.put("group_flag", groupId);
	        paramMap.put("count", assetList.size());
	        paramMap.put("websoc", websoc);
	        List<Alarm> alarmList = alarmService.getAlarmByOrderId(paramMap);
	        request.setAttribute("alist", alarmList.size());
	        //关键字告警信息
			List<Alarm> keywordList = alarmService.findKeywordWarningByOrderId(orderId);
			
			if(keywordList!=null){
				for(Alarm a :keywordList){
					a.setAlarmTime(DateUtils.dateToString(a.getAlarm_time()));
				}
			}
			request.setAttribute("keywordList", keywordList);
			request.setAttribute("keyList", keywordList.size());
	        //获取历史时间
	        List taskTime = taskService.findScanTimeByOrderId(orderId);
	        request.setAttribute("taskTime", taskTime);
	        if(runList.size()>0){
	        	request.setAttribute("timeSize", 0);
	        }
	        
//	        if((orderList.get(0).get("status").equals(0)||runList.size()>0)&&groupId==null){
	        if(!orderList.get(0).get("status").equals(1) && !orderList.get(0).get("status").equals(2) ){
	        	int status=0;
	        	if(runList.size()>0){
	        		status = Integer.parseInt(Constants.TASK_RUNNING);
	        	}
	            return this.service(request,paramMap,status,orderList);
	        }else {
	            //获取推送告警信息
	        	String flag_able=request.getParameter("flag");
	        	paramMap.put("flag", flag_able);
	            List<TaskWarn> taskWarnList=taskWarnService.findTaskWarnByOrderId(paramMap);
	            //处理时间Thu Apr 16 09:47:38 CST 2015=》年月日时分秒
	        	if(taskWarnList!=null){
	        		for(TaskWarn t : taskWarnList){
	        			t.setWarnTime(DateUtils.dateToString(t.getWarn_time()));
	        		}
	        	}
	            request.setAttribute("taskWarnList", taskWarnList);
	            //获取对应IP
	            List IPList = orderService.findIPByOrderId(orderId);
	            int serviceId=0 ;
	            
	            /** 获取告警次数  dyy*/
	            TaskWarn taskCount = taskWarnService.findTaskWarnCountByOrderId(orderId);
	            request.setAttribute("count", taskCount.getCount());
	            /** 基本信息   dyy*/
//	            Task task = new Task();
	//            if(Integer.parseInt(type)==1){
	//                //长期查找最近的任务
	//                task = taskService.findNearlyTask(orderId);
	//            }else{
	                task = taskService.findBasicInfoByOrderId(paramMap);
	//            }
	            Map<String, Object> taskParam1 = this.getInfo(task);
	            //request.setAttribute("scanTime", taskParam.get("scanTime"));
	            //request.setAttribute("send", taskParam.get("send"));
	            //request.setAttribute("receive", taskParam.get("receive"));
	            if(task!=null){
	                if(task.getBegin_time()!=null){
	                    task.setBeginTime( DateUtils.dateToString(task.getBegin_time()));
	                }
	                if(task.getEnd_time()!=null){
	                    task.setEndTime(DateUtils.dateToString(task.getEnd_time())); 
	                }
	                if(task.getExecute_time()!=null){
	                    task.setExecuteTime(DateUtils.dateToString(task.getExecute_time())); 
	                }
	            }
	            //request.setAttribute("task", task);
	            HashMap<String, Object> order=new HashMap<String, Object>();
	            for(int i=0;i<orderList.size();i++){
	            	 order=(HashMap) orderList.get(i);
	            	 serviceId=(Integer) order.get("serviceId");
	            }
	            //获取关键字信息
	            
	            if(serviceId==6||serviceId==7||serviceId==8){//DDOS
	            	 List<AlarmDDOS> alarmDDosList = alarmService.getAlarmDdosByOrderId(paramMap);
	            	request.setAttribute("ipList", IPList);
	            	request.setAttribute("serviceId", serviceId);
	                request.setAttribute("alarmList", alarmDDosList);
	                return "/source/page/order/order_warning";
	            	
	            }else{//安恒的服务
	        		switch (serviceId) {
	    			case 3:/**篡改  dyy*/
	                	//获取：敏感词  折线图信息
	                	Map<String,Object> m = new HashMap<String, Object>();
	    				m.put("orderId", orderId);
	    				m.put("url", null);
	                	List<Alarm> alarm = alarmService.findSensitiveWordByOrderId(m);
	                	request.setAttribute("alarm", alarm);
	                	request.setAttribute("alist", alarm.size());
	                	
	                    request.setAttribute("alarmList", alarmList);
	            		return "/source/page/order/warning_doctort"	;
	    			case 4:/**关键字监测服务 dyy*/    				
	    				String flag=request.getParameter("flag");
	    				//关键字 折线图 左侧信息
	    				Map<String,Object> m1 = new HashMap<String, Object>();
	    				m1.put("orderId", orderId);
	    				m1.put("url", null);
	    				m1.put("flag",flag);
	    				List<Alarm> alarmKeyWordList = alarmService.findSensitiveWordByOrderId(m1);
	                	request.setAttribute("alarmKeyWordList", alarmKeyWordList);
	    				//敏感词排行榜(只适合一个资产)
	                	Map<String,Object> map = new HashMap<String, Object>();
	                	map.put("orderId", orderId);
	                	int flagalarm=0;
	                	if(alarmKeyWordList!=null){
	                		for(int a=0;a<alarmKeyWordList.size();a++){
	                			if(a==0){
	                				String url = alarmKeyWordList.get(0).getUrl();
	                    			map.put("url", url);
	                    			List<Alarm> list = alarmService.findKeywordByUrlAndOrderId(map);
	                    			List<Alarm> mapSortData = getSortData(list);
	                    			request.setAttribute("mapSortData", mapSortData);
	                    			
			                		Date tempTime=alarmKeyWordList.get(0).getAlarm_time();
			                		Date d2 = new Date(); //当前时间
			                		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd"); //格式化为 yyyymmdd
			                		int d1Number = Integer.parseInt(f.format(tempTime).toString()); //将取出数据格式化后转为int
			                		int d2Number = Integer.parseInt(f.format(d2).toString()); //将当前时间格式化后转为int
			                		int value=d2Number-d1Number;
			                		if(value==0){
			                			flagalarm=1;
			                		}else if(value==1){
			                			flagalarm=2;
			                		}else{
			                			flagalarm=3;
			                		}
			                		 request.setAttribute("value", flagalarm);
	                			}
	                		}
	                	}
	                	return "/source/page/order/warning_keyword"	;
	    			case 5:/**可用性监测服务 dyy*/
	    				//可用性  折线图 左侧信息
	    				Map<String,Object> m2 = new HashMap<String, Object>();
	    				m2.put("orderId", orderId);
	    				m2.put("url", null);
	    				//可用率统计
	    		    	List<TaskWarn> listUseable = taskWarnService.findUseableByOrderId(paramMap);
	    		    	request.setAttribute("listUseable", listUseable);
	    			    request.setAttribute("wlist", taskWarnList.size());
	    			  
	    			    //根据url查询折线图和orderId
	    		    	Map<String, Object> m22 = new HashMap<String, Object>();
	    		    	m22.put("orderId", orderId);
	    		    	int flag1=0;
	    		    	if(listUseable!=null&&listUseable.size()>0){
	    		    		String url = listUseable.get(0).getUrl();
			    			m22.put("url", url); 
			    			List<TaskWarn> listRight = taskWarnService.findWarnByOrderIdAndUrl(m22);
			                for(int j =0 ;j< listRight.size();j++){
			                	if(j==listRight.size()-1){
			                		Date tempTime=listRight.get(j).getWarn_time();
			                		Date d2 = new Date(); //当前时间
			                		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd"); //格式化为 yyyymmdd
			                		int d1Number = Integer.parseInt(f.format(tempTime).toString()); //将取出数据格式化后转为int
			                		int d2Number = Integer.parseInt(f.format(d2).toString()); //将当前时间格式化后转为int
			                		int value=d2Number-d1Number;
			                		if(value==0){
			                			flag1=1;
			                		}else if(value==1){
			                			flag1=2;
			                		}else{
			                			flag1=3;
			                		}
			                	}
			                }
			    		}
			    		
	    		    		
	    			    //可用率计算
	    			    long usable = 0l;
	    			    long huifu = 0l;
	    			    for (int i = 0; i < taskWarnList.size(); i++) {
	    			    	if(taskWarnList.get(i).getName().equals("断网")){
	    			    		if(i==0){
	    			    			usable = usable + taskWarnList.get(i).getWarn_time().getTime()-task.getExecute_time().getTime();
	    			    		}else{
	    			    			usable = usable + (taskWarnList.get(i).getWarn_time().getTime()-huifu);
	    			    		}
	    			    		
	    			    	}else{
	    			    		huifu = taskWarnList.get(i).getWarn_time().getTime();
	    			    	}
						}
	    			    float usabling = Float.parseFloat(String.valueOf(usable))/Float.parseFloat(String.valueOf((task.getEnd_time().getTime()-task.getExecute_time().getTime())));
	    			    request.setAttribute("usabling", usabling*100+"%");
	    			    request.setAttribute("value", flag1);
	    			    return "/source/page/order/order_usable";
	    			default: //漏洞和木马
	    	            request.setAttribute("alarmList", alarmList);
	    	            return "/source/page/personalCenter/warnFinishDetail";
	    			}
	            }
	        }
      
        }
    
    }
    /**
     * 格式转换
     * @param list
     * @return 
     */
	private List<Alarm> getSortData(List<Alarm> list) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		if(list!=null){
			for(Alarm ala : list){
				//去掉[]
			    if(ala!=null){
    				String keyword = ala.getKeyword().substring(1, ala.getKeyword().length()-1);
    				if(keyword.contains(",")){
    					String str[] = keyword.split(",");
    					int i;
    					for(i=0;i<str.length;i++){
    						String str1 = str[i].trim();
    						if(map.containsKey(str1)){
    							 int count=map.get(str1);
    							 map.put(str1, count+1);
    						}else{
    							map.put(str1, 1);
    						}
    					}
    				}else{
    					if(map.containsKey(keyword)){
    						 int count=map.get(keyword);
    						 map.put(keyword, count+1);
    					}else{
    						map.put(keyword, 1);
    					}
    				}
			    }
			}
		}
		return sort(map);
	}
	public static List<Alarm> sort(Map<String,Integer> map){ 
		List<Alarm> result = new ArrayList<Alarm>();
	    List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());  
	    Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {    
	        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {    
	            return (o2.getValue() - o1.getValue());    
	        }    
	    }); //排序 
	    if(infoIds.size()<15){ //取前15个返回
		    for (int i = 0; i < infoIds.size(); i++) {  
		        Entry<String, Integer> entry = infoIds.get(i); 
		        Alarm alarm = new Alarm();
		        alarm.setKeyword(entry.getKey());
		        alarm.setCount(entry.getValue());
		        result.add(alarm);
		        System.out.println(entry.getKey()+":"+entry.getValue()); 
		        }
		    }else{
		    	for (int i = 0; i <15; i++) {   //输出 
			        Entry<String, Integer> entry = infoIds.get(i); 
			        Alarm alarm = new Alarm();
			        alarm.setKeyword(entry.getKey());
			        alarm.setCount(entry.getValue());
			        result.add(alarm);
			        System.out.println(entry.getKey()+":"+entry.getValue()); 
			        }
		    }
		return result; 
	} 
    /**
	 * 功能描述：获取折线图信息
	 *		 @time 2015-3-9
	 */
	@RequestMapping(value="getData.html" ,method = RequestMethod.POST)
	@ResponseBody
	public String getData(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
	    String orderId = request.getParameter("orderId");
		//获取带有关键字告警的url
		//页面循环div的id 资产Url和曲线图匹配问题
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("orderId", orderId);
		m.put("url", null);
		List<Alarm> alarmKeyWordList = alarmService.findSensitiveWordByOrderId(m);
    	//根据url查询折线图和orderId
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("orderId", orderId);
    	JSONArray json = new JSONArray();
    	List result1 = new ArrayList();
    	JSONObject jo1 = new JSONObject();
    	int total=0;
    	if(alarmKeyWordList!=null){
    		for(int i=0;i<alarmKeyWordList.size();i++){
    			String url = alarmKeyWordList.get(i).getUrl();
    			map.put("url", url); 
    			List<Alarm> listRight = alarmService.findRightByOrderIdAndUrl(map);
    			JSONObject jo = new JSONObject();
    			SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                List result = new ArrayList();
                jo.put("url", url);
                for(int j =0 ;j< listRight.size();j++){
                	String time1 = setDateFormat.format(listRight.get(j).getAlarm_time());
                	result1.add(time1);
                	if(i!=0&&j==0){
                		for(int a=0;a<total;a++){
                			result.add("");
                		}
                		result.add(listRight.get(j).getCount());
                	}else{
                		result.add(listRight.get(j).getCount());
                	}
                }
                jo.put("total", listRight.size());
                jo.put("count", result);
            	json.add(jo);
            	total+=listRight.size();//数据条数
    		}
    		
    	}
    	jo1.put("time", result1);
    	json.add(jo1);
        return json.toString();
	}
	/**
     * 功能描述：获取折线图信息
     *       @time 2015-3-9
     */
    @RequestMapping(value="getDataUsable.html" ,method = RequestMethod.POST)
    @ResponseBody
    public String getDataUsable(HttpServletRequest request,HttpServletResponse response,String orderId,String orderAssetId){
    	response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		//获取带有关键字告警的url
		//页面循环div的id 资产Url和曲线图匹配问题
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("orderId", orderId);
		m.put("orderAssetId", orderAssetId);
		m.put("url", null);
		List<TaskWarn> urlWarnList=taskWarnService.findWarnUrlByOrderId(m);
    	//根据url查询折线图和orderId
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("orderId", orderId);
    	map.put("orderAssetId", orderAssetId);
    	JSONArray json = new JSONArray();
    	List result1 = new ArrayList();
    	JSONObject jo1 = new JSONObject();
    	int flag=0;
    	if(urlWarnList!=null){
    		for(int i=0;i<urlWarnList.size();i++){
    			String url = urlWarnList.get(i).getUrl();
    			map.put("url", url); 
    			List<TaskWarn> listRight = taskWarnService.findWarnByOrderIdAndUrl(map);
    			JSONObject jo = new JSONObject();
    			SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                List result = new ArrayList();
                jo.put("url", url);
                Task task = taskService.findBasicInfoByOrderId(map);
                //任务开始断网率
                result1.add(setDateFormat.format(task.getExecute_time()));
                result.add(100);
                long usable = 0l;
			    long huifu = 0l;
                for(int j =0 ;j< listRight.size();j++){
                	String name = listRight.get(j).getName();
                	if(j==listRight.size()-1){
                		Date tempTime=listRight.get(j).getWarn_time();
                		Date d2 = new Date(); //当前时间
                		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd"); //格式化为 yyyymmdd
                		int d1Number = Integer.parseInt(f.format(tempTime).toString()); //将取出数据格式化后转为int
                		int d2Number = Integer.parseInt(f.format(d2).toString()); //将当前时间格式化后转为int
                		int value=d2Number-d1Number;
                		if(value==0){
                			System.out.println("时间为今天");
                			System.out.println(d1Number); 
                			flag=1;
                		}else if(value==1){
                			System.out.println("时间为昨天");
                			flag=2;
                			System.out.println(d2Number);
                		}else{
                			System.out.println("时间至少为两天前");
                			flag=3;
                			System.out.println(d1Number);
                		}
                	}
                	if(name.equals("断网")){
                		String time1 = setDateFormat.format(listRight.get(j).getWarn_time());
                    	result1.add(time1);
                		if(j==0){
			    			usable = usable + listRight.get(j).getWarn_time().getTime()-task.getExecute_time().getTime();
			    			result.add(100);
                		}else{
			    			usable = usable + (listRight.get(j).getWarn_time().getTime()-huifu);
			    			float usabling = Float.parseFloat(String.valueOf(usable))/Float.parseFloat(String.valueOf((listRight.get(j).getWarn_time().getTime())));
			    			result.add(usabling*100);
			    		}
			    		
                	}else{
                		huifu = listRight.get(j).getWarn_time().getTime();
                		String time1 = setDateFormat.format(listRight.get(j).getWarn_time());
                    	result1.add(time1);
                    	float usabling = Float.parseFloat(String.valueOf(usable))/Float.parseFloat(String.valueOf((huifu)));
                    	result.add(usabling*100);
                	}
                	
//                	if(i!=0&&j==0){
//                		for(int a=0;a<total;a++){
//                			result.add("");
//                		}
//                		result.add(listRight.get(j).getCount());
//                	}else{
//                		result.add(listRight.get(j).getCount());
//                	}
                }
                
                //根据orderId查询正在执行的任务
                List runList = orderService.findTaskRunning(orderId);
                //可用率计算
                if(runList.size()==0){
                    long usable1 = 0l;
                    long huifu1 = 0l;
                    for (int n = 0; n < listRight.size(); n++) {
                        if(listRight.get(n).getName().equals("断网")){
                            if(n==0){
                                usable1 = usable1 + listRight.get(n).getWarn_time().getTime()-task.getExecute_time().getTime();
                            }else{
                                usable1 = usable1 + (listRight.get(n).getWarn_time().getTime()-huifu1);
                            }
                        }else{
                            huifu1 = listRight.get(n).getWarn_time().getTime();
                        }
                    }
                    float usabling1 = Float.parseFloat(String.valueOf(usable1))/Float.parseFloat(String.valueOf((task.getEnd_time().getTime()-task.getExecute_time().getTime())));
                    result1.add(setDateFormat.format(task.getEnd_time()));
                    result.add(usabling1*100);
                }
                
                
                jo.put("total", listRight.size());
                jo.put("count", result);
            	json.add(jo);
    		}
    		
    	}
    	jo1.put("time", result1);
    	jo1.put("value", flag);
    	json.add(jo1);
    	
    	System.out.println(json);
        return json.toString();
    }
    
    /**
     * 功能描述： 历史记录  dyy查询所有时间
     * 参数描述：  无
     *     @time 2015-4-10
     */
    @RequestMapping(value="getExecuteTime.html")
    @ResponseBody
    public void getExecuteTime(HttpServletRequest request,HttpServletResponse response){
    	String orderId = request.getParameter("orderId");
    	String status = request.getParameter("status");
    	List taskTime = taskService.findScanTimeByOrderId(orderId);
        StringBuffer rsOption = new StringBuffer(); 
        int leng = 0;
        if(status.equals("2")){
            leng = taskTime.size();
        }else{
            leng = taskTime.size()-1;
        }
        for(int i=0;i<leng;i++){
        	HashMap map = (HashMap)taskTime.get(i);
//        	String str = DateUtils.dateToString(t.getExecute_time());
        	String str1=DateUtils.dateToString((Date)map.get("group_flag"));
        	rsOption.append("<option value='"+str1+"'>"+str1+"</option>"); 
        }
        PrintWriter pout;
		try {
			pout = response.getWriter();
			pout.write(rsOption.toString()); 
	        pout.flush(); 
	        pout.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    

    /**
     * 功能描述： 获取仪表图数据
     * 参数描述：  无
     *     @time 2015-2-3
     */
    @RequestMapping(value="getGaugeData.html")
    @ResponseBody
    public String getGaugeData(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String group_flag = request.getParameter("group_flag");
        String websoc = request.getParameter("websoc");
        String orderAssetId = request.getParameter("orderAssetId");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("count", assetList.size());
        paramMap.put("group_flag", group_flag);
        paramMap.put("websoc", websoc);
        paramMap.put("orderAssetId", orderAssetId);

        JSONArray json = new JSONArray();
        JSONObject jo = new JSONObject();
        //add by tangxr 2016-5-17
        int message = 0;
        int higher = 0;
        int high = 0;
        int middle = 0;
        int low = 0;
        List result = alarmService.getAlarmByParam(paramMap);
        if(result != null && result.size() > 0){
        	for(int i = 0; i < result.size(); i++){
        		HashMap<String,Object>  map = (HashMap<String,Object>)result.get(i);
        		if((Integer)map.get("level")==3){
        			higher = Integer.parseInt(map.get("count").toString());
        		}
        		if((Integer)map.get("level")==2){
        			high = Integer.parseInt(map.get("count").toString());
        		}
        		if((Integer)map.get("level")==1){
        			middle = Integer.parseInt(map.get("count").toString());
        		}
//        		if((Integer)map.get("level")==0){
//        			low = Integer.parseInt(map.get("count").toString());
//        		}
//        		if((Integer)map.get("level")==-1){
//    				message = Integer.parseInt(map.get("count").toString());
//    			}
        	}
        }
        if(result.size()==0){
            jo.put("level", 0);
            json.add(jo);
        }else if(higher+high>=2){//高风险
            jo.put("level", 90);
            json.add(jo);
        }else if(higher+high<=0&&middle<=0){//低风险
            jo.put("level", 20);
            json.add(jo);
        }else{//中风险
            jo.put("level", 60);
            json.add(jo);
        }
        return json.toString();
    }
    
    /**
     * 功能描述： 获取饼图数据
     * 参数描述：  无
     *     @time 2015-2-3
     */
    @RequestMapping(value="getPieData.html")
    @ResponseBody
    public String getPieData(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String group_flag = request.getParameter("group_flag");
        String websoc = request.getParameter("websoc");
        String orderAssetId = request.getParameter("orderAssetId");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("count", assetList.size());
        paramMap.put("group_flag", group_flag);
        paramMap.put("websoc", websoc);
        paramMap.put("orderAssetId", orderAssetId);
        //add by tangxr 2016-5-17
        int message = 0;
        int higher = 0;
        int high = 0;
        int middle = 0;
        int low = 0;
        int count = 0;
        List result = alarmService.getAlarmByParam(paramMap);
        if(result != null && result.size() > 0){
        	for(int i = 0; i < result.size(); i++){
        		HashMap<String,Object>  map = (HashMap<String,Object>)result.get(i);
        		if((Integer)map.get("level")==3){
        			higher = Integer.parseInt(map.get("count").toString());
        		}
        		if((Integer)map.get("level")==2){
        			high = Integer.parseInt(map.get("count").toString());
        		}
        		if((Integer)map.get("level")==1){
        			middle = Integer.parseInt(map.get("count").toString());
        		}
        		if((Integer)map.get("level")==0){
        			low = Integer.parseInt(map.get("count").toString());
        		}
        		if((Integer)map.get("level")==-1){
	    			message = Integer.parseInt(map.get("count").toString());
	    		}
        	}
        }
        count = higher + high + middle + low + message;
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        JSONArray json = new JSONArray();
        if(message>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "-1");
            jo.put("value", message);
            jo.put("color", "#40e1d1");
//            jo.put("ratio", df.format((float)message/count*100)+"%");
            json.add(jo);
        }
        if(low>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "0");
            jo.put("value", low);
            jo.put("color", "#1e91ff");
//            jo.put("ratio", df.format((float)low/count*100)+"%");
            json.add(jo);
        }
        if(middle>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "1");
            jo.put("value", middle);
            jo.put("color", "#ffa500");
//            jo.put("ratio", df.format((float)middle/count*100)+"%");
            json.add(jo);
        }
        if(high>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "2");
            jo.put("value", high);
            jo.put("color", "#ff7e50");
//            jo.put("ratio", df.format((float)high/count*100)+"%");
            json.add(jo);
        }
        if(higher>0){
            JSONObject jo = new JSONObject();
            jo.put("label", "3");
            jo.put("value", higher);
            jo.put("color", "#cd5c5c");
//            jo.put("ratio", df.format((float)higher/count*100)+"%");
            json.add(jo);
        }
        return json.toString();
    }
    
    
    /**
     * 功能描述： 获取柱形数据
     * 参数描述：  无
     *     @time 2015-7-23
     */
    @RequestMapping(value="getBarData.html",method = RequestMethod.POST)
    @ResponseBody
    public void getBarData(HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String group_flag = request.getParameter("group_flag");
        String websoc = request.getParameter("websoc");
        String orderAssetId = request.getParameter("orderAssetId");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("count", assetList.size());
        paramMap.put("group_flag", group_flag);
        paramMap.put("websoc", websoc);
        paramMap.put("orderAssetId", orderAssetId);
        
        List<Alarm> result = alarmService.findAlarmByOrderIdorGroupId(paramMap);
        JSONArray json = new JSONArray();
        if(result!=null){
            for(int i=0;i<result.size();i++){
                String name = result.get(i).getName();
                int num = result.get(i).getNum();
                JSONObject jo = new JSONObject();
                jo.put("name", name);
                jo.put("num", num);
                json.add(jo);
            }
        }
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(json.toString()); 
            out.flush(); 
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return json.toString();
    }
    
    /**
     * 功能描述： 获取趋势图数据
     * 参数描述：  无
     *     @time 2015-2-3
     */
    @RequestMapping(value="getLineData.html")
    @ResponseBody
    public String getLineData(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String group_flag = request.getParameter("group_flag");
        String websoc = request.getParameter("websoc");
        String orderAssetId = request.getParameter("orderAssetId");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("count", assetList.size());
        paramMap.put("group_flag", group_flag);
        paramMap.put("websoc", websoc);
        paramMap.put("orderAssetId", orderAssetId);
        
        List<Task> taskList = alarmService.getTaskByOrderId(paramMap);
        JSONArray json = new JSONArray();
        for (int i=0; i<taskList.size(); i++) {

//        	//信息
//            paramMap.put("level", -1);
//            paramMap.put("group_flag", taskList.get(i).getGroup_flag());
//            List<Alarm> messageList = alarmService.getAlarmByOrderId(paramMap);
//            //低
//            paramMap.put("level", WarnType.LOWLEVEL.ordinal());
//            List<Alarm> lowList = alarmService.getAlarmByOrderId(paramMap);
//            //中
//            paramMap.put("level", WarnType.MIDDLELEVEL.ordinal());
//            List<Alarm> middleList = alarmService.getAlarmByOrderId(paramMap);
//            //高
//            paramMap.put("level", WarnType.HIGHLEVEL.ordinal());
//            List<Alarm> highList = alarmService.getAlarmByOrderId(paramMap);
//            //紧急
//            paramMap.put("level", 3);
//            List<Alarm> higherList = alarmService.getAlarmByOrderId(paramMap);
            
        	//add by tangxr 2016-5-17
            int message = 0;
            int higher = 0;
            int high = 0;
            int middle = 0;
            int low = 0;
            int count = 0;
            paramMap.put("group_flag", taskList.get(i).getGroup_flag());
            List result = alarmService.getAlarmByParam(paramMap);
            if(result != null && result.size() > 0){
            	for(int n = 0; n < result.size(); n++){
            		HashMap<String,Object>  map = (HashMap<String,Object>)result.get(n);
            		if((Integer)map.get("level")==3){
            			higher = Integer.parseInt(map.get("count").toString());
            		}
            		if((Integer)map.get("level")==2){
            			high = Integer.parseInt(map.get("count").toString());
            		}
            		if((Integer)map.get("level")==1){
            			middle = Integer.parseInt(map.get("count").toString());
            		}
            		if((Integer)map.get("level")==0){
            			low = Integer.parseInt(map.get("count").toString());
            		}
            		if((Integer)map.get("level")==-1){
    	    			message = Integer.parseInt(map.get("count").toString());
    	    		}
            	}
            }
        	
            SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = setDateFormat.format(taskList.get(i).getExecute_time());
            JSONObject jo = new JSONObject();
            jo.put("time", time);
            if(message>0){
                jo.put("value0", message);
            }else{
                jo.put("value0", 0);
            }
            if(low>0){
                jo.put("value", low);
            }else{
                jo.put("value", 0);
            }
            if(middle>0){
                jo.put("value2", middle);
            }else{
                jo.put("value2", 0);
            }
            if(high>0){
                jo.put("value3", high);
            }else{
                jo.put("value3", 0);
            }
            if(higher>0){
                jo.put("value4", higher);
            }else{
                jo.put("value4", 0);
            }
            json.add(jo);
        }
        
        return json.toString();
    }
    
    /**
     * 功能描述： 用户中心-订单跟踪-订单详情
     * 参数描述：  无
     *     @time 2015-2-2
     */
    @RequestMapping(value="orderDetails.html")
    public String orderDetails(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        //获取订单信息
        List orderList = orderService.findByOrderId(orderId);

		//不是当前用户的订单,不能查看
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	if (orderId== null || orderList == null ||orderList.size() == 0) {
    		return "redirect:/index.html";
    	}
    	
    	 HashMap<String, Object> order=new HashMap<String, Object>();
 	    order=(HashMap) orderList.get(0);
 	    if (((Integer)order.get("userId"))!= globle_user.getId()) {
 	    	return "redirect:/index.html";
 	    }
    	
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        //获取对应资产
        List ipList = orderAssetService.findIpByOrderId(orderId);
        //获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        //获取最近检测时间
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("limitNum", 1);
        List lastTime = orderAssetService.findLastTimeByOrderId(paramMap);
        //获取检测次数
        Map<String, Object> paramMap1 = new HashMap<String, Object>();
        paramMap1.put("orderId", orderId);
        List checkTime = orderAssetService.findLastTimeByOrderId(paramMap1);
        request.setAttribute("orderList", orderList);
        request.setAttribute("assetList", assetList);
        request.setAttribute("ipList", ipList);
        request.setAttribute("nowDate",temp);
        if(lastTime.size()>0){
            request.setAttribute("lastTime",lastTime.get(0));
        }
        request.setAttribute("checkTime",checkTime.size());
        return "/source/page/order/orderDetail";
    }
	
    /**
     * 功能描述： 用户中心-订单跟踪-订单详情-系统安全帮类 订单详情
     * 参数描述：  无
     *     @time 2017-3-21
     */
    @RequestMapping(value="orderSysDetails.html")
    public String orderSysDetails(HttpServletRequest request){
    	
    	String strTeString = "test111996";
        String orderId = request.getParameter("orderId");
        //获取订单信息
        List orderList = orderService.findByOrderId(orderId);
        String status="";
        
		//不是当前用户的订单,不能查看
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
    	System.out.println("****>>>>>>>>>>>>>ordersysdetail.html");
    	if (orderId== null || orderList == null ||orderList.size() == 0) {
    		System.out.println("*****>>>>>>>>>>>>>>>>>>>>>orderId is null");
    		return "redirect:/index.html";
    		
    	}
  	
    	 HashMap<String, Object> order=new HashMap<String, Object>();
 	    order=(HashMap) orderList.get(0);
 	    if (((Integer)order.get("userId"))!= globle_user.getId()) {
 	    	System.out.println("*****>>>>>>>>>>>>>>>>>>>>>userid not equal");
 	    	return "redirect:/index.html";
 	    }

 	    String strBeginDate = order.get("begin_date").toString();
		String strEndDate =  order.get("end_date").toString();
		String strNowDate = DateUtils.dateToString(new Date());
		int serviceId = (Integer)order.get("serviceId");
    	status = status+order.get("status");
    	Integer userid = new Integer(globle_user.getId());
    	if (strNowDate.compareTo(strEndDate)>0 || strNowDate.compareTo(strBeginDate)<0) 
    	{
    		System.out.println("*****>>>>>>>>>>>>>>>>>>>>> time not in service");
    		return "";//时间不在服务范围内
    	}
    	else {
			if (serviceId == 8) { //调用金山接口
				String useridString = ((Integer)order.get("userId")).toString();
				String urlRes = SysWorker.getJinshanoauthurl(strTeString+useridString);
				if (!urlRes.equals("failed")) {
					request.setAttribute("returnURL", urlRes);
				}
			}
    		
			else if (serviceId == 9) { //云眼APM
				String useridString = ((Integer)order.get("userId")).toString();
				String userTokenString  = SysWorker.getYunyanToken(useridString);
				if (!userTokenString.equals("failed")) {
				//
					String urlyunyanString = SysWorker.getYunyanloginURL(userTokenString);
					if (!urlyunyanString.equals("failed")) {
						request.setAttribute("returnURL", urlyunyanString);	
					}
					
				}
			}
			
			else if (serviceId == 7) { // 绿盟极光
				//
				//SysWorker.destroyYunyanToken(userId);
			}
			
		}
    	
 	    
        return "/source/page/personalCenter/listExternal";
    }
    
    /**
     * 功能描述： 销毁token
     * 参数描述：  无
     *     @time 2017-4-14
     */
    @RequestMapping(value="destroyWindow.html")
    public void destroyWindow(HttpServletRequest request){
        try {
			String orderId = request.getParameter("orderId");
			//获取订单信息
		
			 List orderList = orderService.findByOrderId(orderId);
			 User globle_user = (User) request.getSession().getAttribute("globle_user");
		    	System.out.println("****>>>>>>>>>>>>>ordersysdetail.html");
		    	if (orderId== null || orderList == null ||orderList.size() == 0) {
		    		System.out.println("destory*****>>>>>>>>>>>>>>>>>>>>>orderId is null");
		    		return ;
		    		
		    	}
		  	
		    	 HashMap<String, Object> order=new HashMap<String, Object>();
		 	    order=(HashMap) orderList.get(0);
		 	    if (((Integer)order.get("userId"))!= globle_user.getId()) {
		 	    	System.out.println("destroy*****>>>>>>>>>>>>>>>>>>>>>userid not equal");
		 	    	return ;
		 	    }
		 	    
		 	   int serviceId = (Integer)order.get("serviceId");
		 	   if (serviceId == 9) {
		 		   //注销云眼token
		 		  String useridString = ((Integer)order.get("userId")).toString();
		 		   SysWorker.destroyYunyanToken(useridString);
			}

			 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 功能描述： 用户中心-订单跟踪-历史记录查询
     * 参数描述：  无
     *     @time 2015-3-12
     *     dyy
     */
    @RequestMapping(value="historyInit.html")
    public String historyInit(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String taskId = request.getParameter("taskId");
        String groupId=request.getParameter("groupId");
        //获取订单信息
        List orderList = orderService.findByOrderId(orderId);
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        //获取对应IP
        List IPList = orderService.findIPByOrderId(orderId);
        //获取对应告警信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("orderId", orderId);
//        paramMap.put("type", type);
        paramMap.put("taskId", taskId);
        int serviceId=0 ;
        request.setAttribute("orderList", orderList);
        /** 基本信息   dyy*/
        Map<String, Object> hisMap = new HashMap<String, Object>();
        hisMap.put("orderId", orderId);
        hisMap.put("group_flag", groupId);
        List<Alarm> alarmList = alarmService.getAlarmByOrderId(hisMap);
        request.setAttribute("alist", alarmList.size());
        Task task = taskService.findTaskList(hisMap);
        Map<String, Object> taskParam = this.getInfo(task);
        request.setAttribute("scanTime", taskParam.get("scanTime"));
        request.setAttribute("send", taskParam.get("send"));
        request.setAttribute("receive", taskParam.get("receive"));
        //获取进度
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("orderId", orderId);
        pMap.put("status", Integer.parseInt(Constants.TASK_FINISH));
        pMap.put("group_flag", null);
        pMap.put("count", assetList.size());
        Task taskpro = taskService.findProgressByOrderId(pMap);
        if(taskpro!=null){
            request.setAttribute("progress", taskpro.getProgress());
            if(taskpro.getCurrentUrl()!=null && !taskpro.getCurrentUrl().equals("")){
                request.setAttribute("currentUrl", taskpro.getCurrentUrl());
            }else{
                request.setAttribute("currentUrl", "无");
            }
        }else{
            request.setAttribute("progress", 0);
            request.setAttribute("currentUrl", "暂无");
        }
        if(task.getBegin_time()!=null){
            task.setBeginTime( DateUtils.dateToString(task.getBegin_time()));
        }
        if(task.getEnd_time()!=null){
            task.setEndTime(DateUtils.dateToString(task.getEnd_time())); 
        }
        if(task.getExecute_time()!=null){
            task.setExecuteTime(DateUtils.dateToString(task.getExecute_time())); 
        }
        request.setAttribute("task", task);
        HashMap<String, Object> order=new HashMap<String, Object>();
        for(int i=0;i<orderList.size();i++){
        	 order=(HashMap) orderList.get(i);
        	 serviceId=(Integer) order.get("serviceId");
        }
        if(serviceId==6||serviceId==7||serviceId==8){//DDOS
        	 List<AlarmDDOS> alarmDDosList = alarmService.getAlarmDdosByOrderId(paramMap);
        	request.setAttribute("ipList", IPList);
        	request.setAttribute("serviceId", serviceId);
            request.setAttribute("alarmList", alarmDDosList);
            return "/source/page/order/history_waring";
        	
        }else if(serviceId==3){
        	request.setAttribute("assetList", assetList);
            request.setAttribute("alarmList", alarmList);
            request.setAttribute("group_flag", groupId);
            request.setAttribute("status", Integer.parseInt(Constants.TASK_FINISH));
            return "/source/page/order/warning_doctort";
        }else{
        	request.setAttribute("assetList", assetList);
            request.setAttribute("alarmList", alarmList);
            request.setAttribute("group_flag", groupId);
            request.setAttribute("status", Integer.parseInt(Constants.TASK_FINISH));
            return "/source/page/order/history_waring";
        }
    
    	
    	
    	/**
    	 * 
	        String orderId = request.getParameter("orderId");
	        //获取订单信息
	        List orderList = orderService.findByOrderId(orderId);
	        //获取对应告警信息
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("orderId", orderId);
	        List<Alarm> alarmList = alarmService.getAlarmByOrderId(paramMap);
	        //获取检测次数
	        List checkTime = orderAssetService.findLastTimeByOrderId(paramMap);
	        request.setAttribute("orderList", orderList);
	        request.setAttribute("alarmList", alarmList);
	        request.setAttribute("checkTime", checkTime);
        */
    }
    /**
     * 功能描述：扫描进度
     * 邓元元
     * 		@time 2015-4-8
     */
    @RequestMapping(value="/scaning.html")
    @ResponseBody
    public void scaning(HttpServletRequest request,HttpServletResponse response){
    	String orderId = request.getParameter("orderId");
        String status = request.getParameter("status");
        String group_flag = request.getParameter("group_flag");
        String orderAssetId = request.getParameter("orderAssetId");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("status", status);
        paramMap.put("group_flag", group_flag);
        paramMap.put("count", assetList.size());
        paramMap.put("orderAssetId", orderAssetId);
        Task task = taskService.findBasicInfoByOrderId(paramMap);
        Map<String, Object> m = new HashMap<String, Object>();
        if(task!=null){
            m.put("progress", task.getProgress());
            if(task.getCurrentUrl()!=null && !task.getCurrentUrl().equals("")){
            	m.put("currentUrl", task.getCurrentUrl());
            }else{
            	m.put("currentUrl", "无");
            }
        }else{
//        	Task  t = taskService.getNewStatus(paramMap);
//        	if(t.getStatus()==3){
//        		m.put("progress", 100);
//        		if(t.getCurrentUrl()!=null && !t.getCurrentUrl().equals("")){
//                    m.put("currentUrl", t.getCurrentUrl());
//                }else{
//                    m.put("currentUrl", "无");
//                }
//        	}else{
        		m.put("progress", 0);
                m.put("currentUrl", "暂无");
//        	}
        }
        
        //object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 功能描述：扫描进度
     * 邓元元
     * 		@time 2015-4-8
     */
    @RequestMapping(value="/getAssetName.html")
    @ResponseBody
    public void getAssetName(HttpServletRequest request,HttpServletResponse response){
        String orderId = request.getParameter("orderId");
        //获取对应资产
        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> m = new HashMap<String, Object>();
        String assetName = "";
        if(assetList!=null&&assetList.size()!=0){
            for (Asset asset : assetList) {
            	assetName = assetName + asset.getName()+",";
			}
            assetName = assetName.substring(0, assetName.length()-1);
        }
        m.put("assetName", assetName);
        //object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取折线图数据
    
    /**
     * 功能描述：告警2扫描中状态
     */   
    @RequestMapping(value="warningTask.html")
    public void warningTask(HttpServletRequest request,HttpServletResponse response){
        String orderId = request.getParameter("orderId");
        String group_flag = request.getParameter("group_flag");
        String type = request.getParameter("type");
        String orderAssetId = request.getParameter("orderAssetId");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("count", assetList.size());
        paramMap.put("groupId", group_flag);
        paramMap.put("orderAssetId", orderAssetId);
        Task task = taskService.findBasicInfoByOrderId(paramMap);
        Map<String, Object> m = new HashMap<String, Object>();
        if(task!=null){
	        if(task.getExecute_time()!=null){
	    		task.setExecuteTime(DateUtils.dateToString(task.getExecute_time()));
	    	}
	        if(task.getEnd_time()!=null){
	            task.setEndTime(DateUtils.dateToString(task.getEnd_time()));
	        }
	        m.put("executeTime", task.getExecuteTime());
	        m.put("issueCount", task.getIssueCount());
	        m.put("requestCount", task.getRequestCount());
	        m.put("urlCount", task.getUrlCount());
	        m.put("averResponse", task.getAverResponse());
	        m.put("averSendCount", task.getAverSendCount());
	        m.put("endTime", task.getEndTime());
	        String send="";
	        String receive="";
	        if(task.getSendBytes()!=null&&!task.getSendBytes().equals("")){
	            //sendBytes
	        	long sendBytes= Long.parseLong(String.valueOf(Math.round(Float.parseFloat(task.getSendBytes()))));
	        	long MB=sendBytes/1024;
	            
	            if(MB!=0l){
	                send = send + MB +"MB";
	            }else{
	                send = task.getSendBytes()+"KB";
	            }
	        }
	        if(send==null){
	        	m.put("sendBytes", "0KB");
	        }else{
	        	m.put("sendBytes", send);
	        }
	        
	        if(task.getReceiveBytes()!=null&&!task.getReceiveBytes().equals("")){
	            //ReceiveBytes
	            long receiveBytes= Long.parseLong(String.valueOf(Math.round(Float.parseFloat(task.getReceiveBytes()))));
	            long RMB=receiveBytes/1024;
	            
	            if(RMB!=0l){
	                receive = receive + RMB +"MB";
	            }else{
	                receive = task.getReceiveBytes()+"KB";
	            }
	        }
	        if(receive==null){
	        	m.put("receiveBytes", "0KB");
	        }else{
	        	m.put("receiveBytes", receive);
	        }
        }
        //object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 功能描述：敏感词
     */   
    @RequestMapping(value="keyWord.html" ,method = RequestMethod.POST)
    public void keyWord(HttpServletRequest request,HttpServletResponse response,String orderId,String url){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
		map.put("url", url);
		List<Alarm> list = alarmService.findKeywordByUrlAndOrderId(map);
		List<Alarm> mapSortData = getSortData(list);
        
        Gson gson= new Gson(); 
        String resultGson = gson.toJson(mapSortData);//转成json数据
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(resultGson); 
			out.flush(); 
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
        
    }
    
    /**
     * 功能描述：详情
     */   
    @RequestMapping(value="details.html" ,method = RequestMethod.POST)
    public void details(HttpServletRequest request,HttpServletResponse response,String orderId,String url){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
		map.put("url", url);
		List<Alarm> alarm = alarmService.findSensitiveWordByOrderId(map);
        Gson gson= new Gson(); 
        String resultGson = gson.toJson(alarm);//转成json数据
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(resultGson); 
			out.flush(); 
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
        
    }
    
    /**
     * 功能描述：告警2扫描中状态
     * 邓元元
     * 		@time 2015-4-8
     */   
    @RequestMapping(value="warningTwoInit.html")
    public String warningTwoInit(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        //获取订单信息
        List orderList = orderService.findByOrderId(orderId);
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        //获取对应IP
       // List IPList = orderService.findIPByOrderId(orderId);
        request.setAttribute("assetList", assetList);
        request.setAttribute("orderList", orderList);
       
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
//        paramMap.put("type", type);
//        paramMap.put("count", assetList.size());
//        paramMap.put("groupId", groupId);
        Task task = taskService.findBasicInfoByOrderId(paramMap);
        if(task!=null){
        	if(task.getExecute_time()!=null){
        		task.setExecuteTime( DateUtils.dateToString(task.getExecute_time()));
        	}
        	if(task.getEnd_time()!=null){
        		task.setEndTime(DateUtils.dateToString(task.getEnd_time()));
        	}
        }
        
        request.setAttribute("task", task);
        /**可用性 begin dyy*/
        //获取告警信息
        String flag_able=request.getParameter("flag");
    	paramMap.put("flag", flag_able);
    	List<TaskWarn> taskWarnList=taskWarnService.findTaskWarnByOrderId(paramMap);
    	//处理时间Thu Apr 16 09:47:38 CST 2015=》年月日时分秒
    	if(taskWarnList!=null){
    		for(TaskWarn t : taskWarnList){
    			t.setWarnTime(DateUtils.dateToString(t.getWarn_time()));
    		}
    	}
    	request.setAttribute("taskWarnList", taskWarnList);
        //可用率统计
//    	List<TaskWarn> listUseable = taskWarnService.findUseableByOrderId(orderId);
//    	request.setAttribute("listUseable", listUseable);
        int serviceId=0 ;
        HashMap<String, Object> order=new HashMap<String, Object>();
        for(int i=0;i<orderList.size();i++){
        	 order=(HashMap) orderList.get(i);
        	 serviceId=(Integer) order.get("serviceId");
        }
        if(serviceId==5){
        	return "/source/page/order/order_usable";
        	/**end*/
        }else{
            //本次服务中
//            if(){
//                
//            }else{
//                //本次已完成
//            }
        	return "/source/page/order/warningtwo";
        }
    }
    /**
     * 功能描述：结果报表页--安恒 
     * 邓元元
     * 		@time 2015-4-14
     */
    @RequestMapping(value="warningTwoAnHeng.html")
    public String warningTwoAnHeng(HttpServletRequest request){
    	String orderId = request.getParameter("orderId");
    	String type = request.getParameter("type");
    	//获取订单信息
        List orderList = orderService.findByOrderId(orderId);
        //获取对应IP
        List IPList = orderService.findIPByOrderId(orderId);
        //获取安恒服务的告警信息
        List<AlarmDDOS> alarmDDOSsList = alarmDDOSService.findAlarmDDOSByOrderId(orderId);
        AlarmDDOS alarmDDOS = null;
        if(alarmDDOSsList!=null &&alarmDDOSsList.size()>0){
        	alarmDDOS = alarmDDOSsList.get(0);
        	if(alarmDDOS.getStart_time_alert()!=null){
        	    alarmDDOS.setAlarmTime(DateUtils.dateToString(alarmDDOS.getStart_time_alert()));
        	}
        	if(alarmDDOS.getStart_time_attack()!=null){
        	    alarmDDOS.setStartTime(DateUtils.dateToString(alarmDDOS.getStart_time_attack()));
        	}
        	
        }
        request.setAttribute("orderList", orderList);
        request.setAttribute("IPList", IPList);
        request.setAttribute("alarmDDOS", alarmDDOS);
        
    	return "/source/page/order/order_warning";
    }
    
    //服务ing
    public String service(HttpServletRequest request,Map<String, Object> paramMap,int status,List<HashMap<String, Object>> orderList) throws Exception{
        //获取对应IP
        // List IPList = orderService.findIPByOrderId(orderId);
        request.setAttribute("status", status);
       
        //获取进度
        paramMap.put("status", status);
        Task taskpro = taskService.findProgressByOrderId(paramMap);
        if(taskpro!=null){
            request.setAttribute("progress", taskpro.getProgress());
            if(taskpro.getCurrentUrl()!=null && !taskpro.getCurrentUrl().equals("")){
                request.setAttribute("currentUrl", taskpro.getCurrentUrl());
            }else{
                request.setAttribute("currentUrl", "无");
            }
        }else{
            request.setAttribute("progress", 0);
            request.setAttribute("currentUrl", "暂无");
        }
        /** 基本信息  */
        Task task = new Task();
        task = taskService.findBasicInfoByOrderId(paramMap);
        Map<String, Object> taskParam = this.getInfo(task);
        request.setAttribute("scanTime", taskParam.get("scanTime"));
        request.setAttribute("send", taskParam.get("send"));
        request.setAttribute("receive", taskParam.get("receive"));
        if(task!=null){
            if(task.getExecute_time()!=null){
                task.setExecuteTime( DateUtils.dateToString(task.getExecute_time()));
            }
            if(task.getEnd_time()!=null){
                task.setEndTime(DateUtils.dateToString(task.getEnd_time()));
            }
        }
        request.setAttribute("task", task);
        /**可用性 begin dyy*/
        //获取告警信息
        String flag_able=request.getParameter("flag");
    	paramMap.put("flag", flag_able);
        List<TaskWarn> taskWarnList=taskWarnService.findTaskWarnByOrderId(paramMap);
        //处理时间Thu Apr 16 09:47:38 CST 2015=》年月日时分秒
        if(taskWarnList!=null){
            for(TaskWarn t : taskWarnList){
                t.setWarnTime(DateUtils.dateToString(t.getWarn_time()));
            }
        }
        request.setAttribute("taskWarnList", taskWarnList);
        
        //可用率统计
        List<TaskWarn> listUseable = taskWarnService.findUseableByOrderId(paramMap);
        request.setAttribute("listUseable", listUseable);
        int serviceId=0 ;
        HashMap<String, Object> order=new HashMap<String, Object>();
        for(int i=0;i<orderList.size();i++){
             order=(HashMap) orderList.get(i);
             serviceId=(Integer) order.get("serviceId");
        }
        if(serviceId==5){
            request.setAttribute("wlist", taskWarnList.size());
            //可用率计算
            long usable = 0l;
            long huifu = 0l;
            float usabling = 0;
            if(task!=null){
	            for (int i = 0; i < taskWarnList.size(); i++) {
	                if(taskWarnList.get(i).getName().equals("断网")){
	                    if(i==0){
	                        usable = usable + taskWarnList.get(i).getWarn_time().getTime()-task.getExecute_time().getTime();
	                    }else{
	                        usable = usable + taskWarnList.get(i).getWarn_time().getTime()-huifu;
	                    }
	                    
	                }else{
	                    huifu = taskWarnList.get(i).getWarn_time().getTime();
	                }
	            }
	          //获取当前时间
	            SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            String temp = setDateFormat.format(Calendar.getInstance().getTime());
	            usabling = Float.parseFloat(String.valueOf(usable))/Float.parseFloat(String.valueOf((setDateFormat.parse(temp).getTime()-task.getExecute_time().getTime())));
	            request.setAttribute("usabling", usabling*100+"%");
            }
            return "/source/page/order/order_usable";
            /**end*/
        }else if(serviceId==3){
        	return "/source/page/order/warning_doctort";
        }else if(serviceId==4){
        	return "/source/page/order/warning_keyword";
        }else{
            return "/source/page/order/warningtwo";
        }
    }
    
    
    @RequestMapping("/taskRunning.html")
    public String taskRunning(HttpServletRequest request) throws Exception{
    	String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String groupId=request.getParameter("groupId");
        String orderAssetId=request.getParameter("orderAssetId");
        String index=request.getParameter("index");
    	//获取订单信息
        List<HashMap<String, Object>> orderList = orderService.findByOrderId(orderId);
        request.setAttribute("order", orderList.get(0));
        
        //获取服务所对应的资产 ，并根据资产查询告警信息  modify by tangxr 2016-5-5
        List assets = orderAssetService.findAssetsByOrderId(orderId);
		//获取对应告警信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("group_flag", groupId);
        paramMap.put("count", assets.size());
        paramMap.put("orderAssetId", orderAssetId);
                
        request.setAttribute("status", 2);
//        request.setAttribute("index", index);
        
        Asset asset = assetService.findByOrderAssetId(Integer.parseInt(orderAssetId));
        request.setAttribute("asset", asset);
        
        //获取进度
        paramMap.put("status", 2);
        /** 基本信息  */
        Task task = new Task();
        task = taskService.findBasicInfoByOrderId(paramMap);
        if(task!=null){
            request.setAttribute("progress", task.getProgress());
            if(task.getCurrentUrl()!=null && !task.getCurrentUrl().equals("")){
                request.setAttribute("currentUrl", task.getCurrentUrl());
            }else{
                request.setAttribute("currentUrl", "无");
            }
        }else{
            request.setAttribute("progress", 0);
            request.setAttribute("currentUrl", "暂无");
        }

        Map<String, Object> taskParam = this.getInfo(task);
        request.setAttribute("scanTime", taskParam.get("scanTime"));
        request.setAttribute("send", taskParam.get("send"));
        request.setAttribute("receive", taskParam.get("receive"));
        if(task!=null){
            if(task.getExecute_time()!=null){
                task.setExecuteTime( DateUtils.dateToString(task.getExecute_time()));
            }
            if(task.getEnd_time()!=null){
                task.setEndTime(DateUtils.dateToString(task.getEnd_time()));
            }
        }
        request.setAttribute("task", task);
        /**可用性 begin dyy*/
        //获取告警信息
        String flag_able=request.getParameter("flag");
    	paramMap.put("flag", flag_able);
        List<TaskWarn> taskWarnList=taskWarnService.findTaskWarnByOrderId(paramMap);
        //处理时间Thu Apr 16 09:47:38 CST 2015=》年月日时分秒
        if(taskWarnList!=null){
            for(TaskWarn t : taskWarnList){
                t.setWarnTime(DateUtils.dateToString(t.getWarn_time()));
            }
        }
        request.setAttribute("taskWarnList", taskWarnList);
        
        //可用率统计
        List<TaskWarn> listUseable = taskWarnService.findUseableByOrderId(paramMap);
        request.setAttribute("listUseable", listUseable);
        int serviceId=0 ;
        HashMap<String, Object> order=new HashMap<String, Object>();
        for(int i=0;i<orderList.size();i++){
             order=(HashMap) orderList.get(i);
             serviceId=(Integer) order.get("serviceId");
        }
        if(serviceId==5){
            request.setAttribute("wlist", taskWarnList.size());
            //可用率计算
            long usable = 0l;
            long huifu = 0l;
            float usabling = 0;
            if(task!=null){
	            for (int i = 0; i < taskWarnList.size(); i++) {
	                if(taskWarnList.get(i).getName().equals("断网")){
	                    if(i==0){
	                        usable = usable + taskWarnList.get(i).getWarn_time().getTime()-task.getExecute_time().getTime();
	                    }else{
	                        usable = usable + taskWarnList.get(i).getWarn_time().getTime()-huifu;
	                    }
	                    
	                }else{
	                    huifu = taskWarnList.get(i).getWarn_time().getTime();
	                }
	            }
	          //获取当前时间
	            SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            String temp = setDateFormat.format(Calendar.getInstance().getTime());
	            usabling = Float.parseFloat(String.valueOf(usable))/Float.parseFloat(String.valueOf((setDateFormat.parse(temp).getTime()-task.getExecute_time().getTime())));
	            request.setAttribute("usabling", usabling*100+"%");
            }
            return "/source/page/order/order_usable";
            /**end*/
        }else if(serviceId==3){
        	return "/source/page/order/warning_doctort";
        }else if(serviceId==4){
        	return "/source/page/order/warning_keyword";
        }else{
            return "/source/page/personalCenter/warnRunDetail";
        }
    }

    //扫描时常、发送字节sendBytes、接收字节ReceiveBytes 单位转换
    public Map<String, Object> getInfo(Task task){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(task!=null){
            //扫描时间
            if(task.getScanTime()!=null&&!task.getScanTime().equals("")){
                long diff= Long.parseLong(task.getScanTime());
                long day=diff/(24*60*60*1000);
                long hour=(diff/(60*60*1000)-day*24);
                long min=((diff/(60*1000))-day*24*60-hour*60);
                long s=(diff/1000-day*24*60*60-hour*60*60-min*60);
                System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
                String scanTime="";
                if(day!=0l){
                    scanTime = scanTime + day+"天";
                }
                if(hour!=0l){
                    scanTime = scanTime + hour+"小时";
                }
                if(min!=0l){
                    scanTime = scanTime + min+"分";
                }
                if(s!=0l){
                    scanTime = scanTime + s+"秒";
                }
                paramMap.put("scanTime", scanTime);
            }
            if(task.getSendBytes()!=null&&!task.getSendBytes().equals("")){
                //sendBytes
                long sendBytes= Long.parseLong(String.valueOf(Math.round(Float.parseFloat(task.getSendBytes()))));
                long MB=sendBytes/1024;
                String send="";
                if(MB!=0l){
                    send = send + MB +"MB";
                }else{
                    send = task.getSendBytes()+"KB";
                }
                paramMap.put("send", send);
            }
            
            if(task.getReceiveBytes()!=null&&!task.getReceiveBytes().equals("")){
                //ReceiveBytes
                long receiveBytes= Long.parseLong(String.valueOf(Math.round(Float.parseFloat(task.getReceiveBytes()))));
                long RMB=receiveBytes/1024;
                String receive="";
                if(RMB!=0l){
                    receive = receive + RMB +"MB";
                }else{
                    receive = task.getReceiveBytes()+"KB";
                }
                paramMap.put("receive", receive);
            }
        }
        return paramMap;
    }
    
    /**
     * 功能描述： 根据订单号查询调用接口的次数
     * 参数描述：  无
     *     @time 2015-2-2
     */
    @RequestMapping(value="apiDetails.html")
    public String apiDetails(HttpServletRequest request){
        try {
			String orderId = request.getParameter("orderId");
			//获取订单信息
			/*select o.id,o.serviceId,o.type,s.name,o.begin_date,o.end_date,o.create_date,o.scan_type,o.status,o.websoc,o.payFlag,o.isAPI,o.userId
        from cs_order o,cs_service_api s
        where o.id = #{orderId} and o.serviceId = s.id*/
			List orderList = orderService.findAPIInfoByOrderId(orderId);

			//不是当前用户的订单,不能查看
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			if (orderId== null || orderList == null ||orderList.size() == 0) {
				return "redirect:/index.html";
			}
			
			HashMap<String, Object> order=new HashMap<String, Object>();
			order=(HashMap) orderList.get(0);
			if (((Integer)order.get("userId"))!= globle_user.getId()) {
				return "redirect:/index.html";
			}
			
			//获取用户购买服务次数
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", globle_user.getId());
			paramMap.put("orderId", orderId);
			int apiCount = orderService.findAPICountByParam(paramMap);
			
			//远程调用接口
			String str = APIWorker.getUserCount(orderId);
			
			//解析json,进行数据同步
			JSONObject jsonObject = JSONObject.fromObject(str);	
			int createAPICount = 0;//订单调用创建接口的次数
			int code = jsonObject.getInt("code");
			if(code==201){
				createAPICount = jsonObject.getInt("createAPICount");
			}
			
			request.setAttribute("user",globle_user);
			request.setAttribute("order",order);
			request.setAttribute("apiCount", apiCount);
			request.setAttribute("createAPICount", createAPICount);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "/source/page/personalCenter/apiDetail";
    }
    
    /**
     * 功能描述： 获取某订单扫描所有api的次数
     * 参数描述：  无
     *     @time 2015-2-2
     */
    @RequestMapping(value="getAllApiCount.html")
    public void getAllApiCount(HttpServletRequest request,HttpServletResponse response){
    	Map<String, Object> m = new HashMap<String, Object>();
        try {
			String orderId = request.getParameter("orderId");
			//获取订单信息
			List orderList = orderService.findAPIInfoByOrderId(orderId);

			//不是当前用户的订单,不能查看
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			if (orderId== null || orderList == null ||orderList.size() == 0) {
				m.put("success", false);//订单号错误
				
				//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			
			HashMap<String, Object> order=new HashMap<String, Object>();
			order=(HashMap) orderList.get(0);
			if (((Integer)order.get("userId"))!= globle_user.getId()) {
				m.put("success", false);//订单号错误
				
				//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			
			
			
			//获取某订单扫描所有api的次数
			//远程调用接口
			String str = APIWorker.getUserAllCount(orderId);
			
			//解析json,进行数据同步
			JSONObject jsonObject = JSONObject.fromObject(str);	
			JSONArray jsonArray = jsonObject.getJSONArray("apiArray");
			int code = jsonObject.getInt("code");
			if(code==201){
				JSONArray jsonArray1 = new JSONArray();
				for (int i = 0; i < jsonArray.size(); i++) {
			        String object = jsonArray.getString(i);
			        JSONObject jsonObject1 = JSONObject.fromObject(object);
			        int apiType = jsonObject1.getInt("api_type");
			        int apiCount = jsonObject1.getInt("apiCount");
			        //查询服务名称
			        String apiName = "";
			        if(apiType==1){
			        	apiName = "创建订单";
			        }else if(apiType==2){
			        	apiName = "操作订单";
			        }else if(apiType==3){
			        	apiName = "获取订单(任务)状态";
			        }else if(apiType==4){
			        	apiName = "获取订单(任务)结果";
			        }else if(apiType==5){
			        	apiName = "获取订单(任务)报表";
			        }
			        
			        //传给前台的json串
			        JSONObject newJO = new JSONObject();
			        newJO.put("value", apiCount);
			        newJO.put("name", apiName);
			        jsonArray1.add(newJO);
				}
				
				m.put("success", true);//订单号错误
				m.put("dataArray", jsonArray1);
				//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("success", false);			
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			return;

		}
    }
    
    /**
     * 功能描述： 根据订单号获取调用接口历史记录
     * 参数描述：  无
     *     @time 2015-2-2
     */
    @RequestMapping(value="getAPIHistoryInfo.html")
    public void getAPIHistoryInfo(HttpServletRequest request,HttpServletResponse response){
    	Map<String, Object> m = new HashMap<String, Object>();
        try {
			String orderId = request.getParameter("orderId");
			String beginDate = request.getParameter("beginDate");
			String endDate = request.getParameter("endDate");
			String scanUrl = request.getParameter("url");
			//获取订单信息
			List orderList = orderService.findAPIInfoByOrderId(orderId);

			//不是当前用户的订单,不能查看
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			if (orderId== null || orderList == null ||orderList.size() == 0) {
				m.put("success", false);
				//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			
			HashMap<String, Object> order=new HashMap<String, Object>();
			order=(HashMap) orderList.get(0);
			if (((Integer)order.get("userId"))!= globle_user.getId()) {
				m.put("success", false);
				//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			

			//远程调用接口		
			String str = APIWorker.getAPIHistory(scanUrl, beginDate, endDate, orderId);
			
			//解析json,进行数据同步
			JSONObject jsonObject = JSONObject.fromObject(str);	
			JSONArray jsonArray = jsonObject.getJSONArray("apiArray");
			int code = jsonObject.getInt("code");
			List infoList = new ArrayList();
			if(code==201){
				for (int i = 0; i < jsonArray.size(); i++) {
			        String object = jsonArray.getString(i);
			        JSONObject jsonObject1 = JSONObject.fromObject(object);

			        Map<String,Object> newMap = new HashMap<String,Object>();
			        newMap.put("create_time", jsonObject1.getString("create_time1"));
			        newMap.put("api_type", jsonObject1.getInt("api_type"));
			        newMap.put("service_type", jsonObject1.getInt("service_type"));
			        newMap.put("status", jsonObject1.getInt("status"));
			        
			        //可能为空
			        String[] keys = {"token","taskId","orderId","type","scan_type","url","begin_date","end_date"};
			        for(int j = 0; j<keys.length;j++){
			        	if(jsonObject1.containsKey(keys[j])){
			        		newMap.put(keys[j], jsonObject1.getString(keys[j]));
			        	}else{
			        		newMap.put(keys[j], "");
			        	}
			        }
			       
			        //定义接口api
			    	switch(jsonObject1.getInt("service_type")){
			    	case 1:
			    		newMap.put("apiUrl", "rest/openapi/vulnscan/");
			    		newMap.put("apiName", "创建WEB漏洞扫描订单");
			    		break;
			    		
			    	case 2:
			    		newMap.put("apiUrl", "rest/openapi/trojandetect/");
			    		newMap.put("apiName", "创建木马检测订单（任务）");
			    		break;
			    		
			    	case 3:
			    		newMap.put("apiUrl", "rest/openapi/webpagetamper/");
			    		newMap.put("apiName", "创建网页篡改订单（任务）");
			    		break;
			    		
			    	case 4:
			    		newMap.put("apiUrl", "rest/openapi/sensitiveword/");
			    		newMap.put("apiName", "创建敏感词监测订单（任务）");
			    		break;
			    		
			    	case 5:
			    		newMap.put("apiUrl", "rest/openapi/availability/");
			    		newMap.put("apiName", "创建可用性监测订单（任务）");
			    		break;
			    	}
			    	
			        infoList.add(newMap);
				}
				
				m.put("success", true);
				m.put("infoList", infoList);
				//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		} catch (Exception e) {
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();
			return;
		} 
    }
}
