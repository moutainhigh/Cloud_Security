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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import se.akerfeldt.com.google.gson.Gson;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.constant.WarnType;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmDDOSService;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.DateUtils;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-2-2
 * 描        述：  告警管理控制层
 * 版        本：  1.0
 */
@Controller
public class WarningController {
	
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
    @RequestMapping(value="warningInit1.html")
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
        request.setAttribute("orderList", orderList);
        //根据orderId查询正在执行的任务
        List runList = orderService.findTaskRunning(orderId);
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
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
	        if(!orderList.get(0).get("status").equals(1)||!orderList.get(0).get("status").equals(2) ){
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
	                
	            }
	            //获取对应IP
	            List IPList = orderService.findIPByOrderId(orderId);
	            int serviceId=0 ;
	            
	            /** 获取告警次数  dyy*/
	            TaskWarn taskCount = taskWarnService.findTaskWarnCountByOrderId(orderId);
	            request.setAttribute("count", taskCount.getCount());
	            /** 基本信息   dyy*/
	            Task task = new Task();
	//            if(Integer.parseInt(type)==1){
	//                //长期查找最近的任务
	//                task = taskService.findNearlyTask(orderId);
	//            }else{
	                task = taskService.findBasicInfoByOrderId(paramMap);
	//            }
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
	    	            return "/source/page/order/warning";
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
	@RequestMapping(value="getData1.html" ,method = RequestMethod.POST)
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
    @RequestMapping(value="getDataUsable1.html" ,method = RequestMethod.POST)
    @ResponseBody
    public String getDataUsable(HttpServletRequest request,HttpServletResponse response,String orderId){
    	response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		//获取带有关键字告警的url
		//页面循环div的id 资产Url和曲线图匹配问题
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("orderId", orderId);
		m.put("url", null);
		List<TaskWarn> urlWarnList=taskWarnService.findWarnUrlByOrderId(m);
    	//根据url查询折线图和orderId
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("orderId", orderId);
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
    @RequestMapping(value="getExecuteTime1.html")
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
    @RequestMapping(value="getGaugeData1.html")
    @ResponseBody
    public String getGaugeData(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String group_flag = request.getParameter("group_flag");
        String websoc = request.getParameter("websoc");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("count", assetList.size());
        paramMap.put("group_flag", group_flag);
        paramMap.put("websoc", websoc);
        //低
        paramMap.put("level", WarnType.LOWLEVEL.ordinal());
        List<Alarm> lowList = alarmService.getAlarmByOrderId(paramMap);
        //中
        paramMap.put("level", WarnType.MIDDLELEVEL.ordinal());
        List<Alarm> middleList = alarmService.getAlarmByOrderId(paramMap);
        //高
        paramMap.put("level", WarnType.HIGHLEVEL.ordinal());
        List<Alarm> highList = alarmService.getAlarmByOrderId(paramMap);
        JSONArray json = new JSONArray();
        JSONObject jo = new JSONObject();
        if(highList.size()==0&&middleList.size()==0&&lowList.size()==0){
            jo.put("level", 0);
            json.add(jo);
        }else if(highList.size()>=2){//高风险
            jo.put("level", 90);
            json.add(jo);
        }else if(highList.size()<=0&&middleList.size()<=0){//低风险
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
    @RequestMapping(value="getPieData1.html")
    @ResponseBody
    public String getPieData(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String group_flag = request.getParameter("group_flag");
        String websoc = request.getParameter("websoc");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("count", assetList.size());
        paramMap.put("group_flag", group_flag);
        paramMap.put("websoc", websoc);
        //低
        paramMap.put("level", WarnType.LOWLEVEL.ordinal());
        List<Alarm> lowList = alarmService.getAlarmByOrderId(paramMap);
        //中
        paramMap.put("level", WarnType.MIDDLELEVEL.ordinal());
        List<Alarm> middleList = alarmService.getAlarmByOrderId(paramMap);
        //高
        paramMap.put("level", WarnType.HIGHLEVEL.ordinal());
        List<Alarm> highList = alarmService.getAlarmByOrderId(paramMap);
        //总数
        paramMap.put("level", null);
        List<Alarm> alarmList = alarmService.getAlarmByOrderId(paramMap);
        
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        JSONArray json = new JSONArray();
        if(lowList.size()>0&&lowList!=null){
            JSONObject jo = new JSONObject();
            jo.put("label", "0");
            jo.put("value", lowList.size());
            jo.put("color", "lightgreen");
            jo.put("ratio", df.format((float)lowList.size()/alarmList.size()*100)+"%");
            json.add(jo);
        }
        if(middleList.size()>0&&middleList!=null){
            JSONObject jo = new JSONObject();
            jo.put("label", "1");
            jo.put("value", middleList.size());
            jo.put("color", "orange");
            jo.put("ratio", df.format((float)middleList.size()/alarmList.size()*100)+"%");
            json.add(jo);
        }
        if(highList.size()>0&&highList!=null){
            JSONObject jo = new JSONObject();
            jo.put("label", "2");
            jo.put("value", highList.size());
            jo.put("color", "red");
            jo.put("ratio", df.format((float)highList.size()/alarmList.size()*100)+"%");
            json.add(jo);
        }
        return json.toString();
    }
    
    
    /**
     * 功能描述： 获取柱形数据
     * 参数描述：  无
     *     @time 2015-7-23
     */
    @RequestMapping(value="getBarData1.html",method = RequestMethod.POST)
    @ResponseBody
    public void getBarData(HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String group_flag = request.getParameter("group_flag");
        String websoc = request.getParameter("websoc");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("count", assetList.size());
        paramMap.put("group_flag", group_flag);
        paramMap.put("websoc", websoc);
        
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
    @RequestMapping(value="getLineData1.html")
    @ResponseBody
    public String getLineData(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        String group_flag = request.getParameter("group_flag");
        String websoc = request.getParameter("websoc");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("count", assetList.size());
        paramMap.put("group_flag", group_flag);
        paramMap.put("websoc", websoc);
        
        List<Task> taskList = alarmService.getTaskByOrderId(paramMap);
        JSONArray json = new JSONArray();
        for (int i=0; i<taskList.size(); i++) {
//            Map<String, Object> param = new HashMap<String, Object>();
//            param.put("taskId", taskList.get(i).getTaskId());
//            param.put("level", WarnType.LOWLEVEL.ordinal());
//            List<Alarm> lowList = alarmService.getAlarmByTaskId(param);
//            param.put("level", WarnType.MIDDLELEVEL.ordinal());
//            List<Alarm> middleList = alarmService.getAlarmByTaskId(param);
//            param.put("level", WarnType.HIGHLEVEL.ordinal());
//            List<Alarm> highList = alarmService.getAlarmByTaskId(param);
            //低
            paramMap.put("level", WarnType.LOWLEVEL.ordinal());
            paramMap.put("group_flag", taskList.get(i).getGroup_flag());
            List<Alarm> lowList = alarmService.getAlarmByOrderId(paramMap);
            //中
            paramMap.put("level", WarnType.MIDDLELEVEL.ordinal());
            List<Alarm> middleList = alarmService.getAlarmByOrderId(paramMap);
            //高
            paramMap.put("level", WarnType.HIGHLEVEL.ordinal());
            List<Alarm> highList = alarmService.getAlarmByOrderId(paramMap);
            
            SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = setDateFormat.format(taskList.get(i).getExecute_time());
            JSONObject jo = new JSONObject();
            jo.put("time", time);
            if(lowList.size()>0&&lowList!=null){
                jo.put("value", lowList.size());
            }else{
                jo.put("value", 0);
            }
            if(middleList.size()>0&&middleList!=null){
                jo.put("value2", middleList.size());
            }else{
                jo.put("value2", 0);
            }
            if(highList.size()>0&&highList!=null){
                jo.put("value3", highList.size());
            }else{
                jo.put("value3", 0);
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
    @RequestMapping(value="orderDetails1.html")
    public String orderDetails(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        //获取订单信息
        List orderList = orderService.findByOrderId(orderId);
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
     * 功能描述： 用户中心-订单跟踪-历史记录查询
     * 参数描述：  无
     *     @time 2015-3-12
     *     dyy
     */
    @RequestMapping(value="historyInit1.html")
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
    @RequestMapping(value="/scaning1.html")
    @ResponseBody
    public void scaning(HttpServletRequest request,HttpServletResponse response){
        String orderId = request.getParameter("orderId");
        String status = request.getParameter("status");
        String group_flag = request.getParameter("group_flag");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("status", status);
        paramMap.put("group_flag", group_flag);
        paramMap.put("count", assetList.size());
        Task task = taskService.findProgressByOrderId(paramMap);
        Map<String, Object> m = new HashMap<String, Object>();
        if(task!=null){
            m.put("progress", task.getProgress());
            if(task.getCurrentUrl()!=null && !task.getCurrentUrl().equals("")){
            	m.put("currentUrl", task.getCurrentUrl());
            }else{
            	m.put("currentUrl", "无");
            }
        }else{
        	Task  t = taskService.getNewStatus(paramMap);
        	if(t.getStatus()==3){
        		m.put("progress", 100);
        		if(t.getCurrentUrl()!=null && !t.getCurrentUrl().equals("")){
                    m.put("currentUrl", t.getCurrentUrl());
                }else{
                    m.put("currentUrl", "无");
                }
//        	if(Integer.parseInt(status)==3){
//        	    m.put("progress", 100);
//        	    m.put("currentUrl", "无");
        	}else{
        		m.put("progress", 0);
                m.put("currentUrl", "暂无");
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
     * 功能描述：扫描进度
     * 邓元元
     * 		@time 2015-4-8
     */
    @RequestMapping(value="/getAssetName1.html")
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
    @RequestMapping(value="warningTask1.html")
    public void warningTask(HttpServletRequest request,HttpServletResponse response){
        String orderId = request.getParameter("orderId");
        String group_flag = request.getParameter("group_flag");
        String type = request.getParameter("type");
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        paramMap.put("count", assetList.size());
        paramMap.put("groupId", group_flag);
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
    @RequestMapping(value="keyWord1.html" ,method = RequestMethod.POST)
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
    @RequestMapping(value="details1.html" ,method = RequestMethod.POST)
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
    @RequestMapping(value="warningTwoInit1.html")
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
    @RequestMapping(value="warningTwoAnHeng1.html")
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
    
}
