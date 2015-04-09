package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.constant.WarnType;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
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
	/**
     * 功能描述： 用户中心-订单跟踪-告警提示
     * 参数描述：  无
     *     @time 2015-2-2
     */
    @RequestMapping(value="warningInit.html")
    public String warningInit(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        //获取订单信息
        List orderList = orderService.findByOrderId(orderId);
        //获取对应资产
        List assetList = orderAssetService.findAssetNameByOrderId(orderId);
        //获取对应IP
        List IPList = orderService.findIPByOrderId(orderId);
        //获取对应告警信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        List<Alarm> alarmList = alarmService.getAlarmByOrderId(paramMap);
        int serviceId=0 ;
        request.setAttribute("orderList", orderList);
        /** 基本信息   dyy*/
        Task task = taskService.findBasicInfoByOrderId(orderId);
        task.setBeginTime( DateUtils.dateToString(task.getBegin_time()));
        task.setEndTime(DateUtils.dateToString(task.getEnd_time()));
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
            return "/source/page/order/order_warning";
        	
        }else{
        	request.setAttribute("assetList", assetList);
            request.setAttribute("alarmList", alarmList);
            return "/source/page/order/warning";
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
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
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
        if(highList.size()>=2){//高风险
            
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
    @RequestMapping(value="getPieData.html")
    @ResponseBody
    public String getPieData(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String type = request.getParameter("type");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
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
        if(lowList.size()>0&&lowList!=null){
            JSONObject jo = new JSONObject();
            jo.put("label", "0");
            jo.put("value", lowList.size());
            json.add(jo);
        }
        if(middleList.size()>0&&middleList!=null){
            JSONObject jo = new JSONObject();
            jo.put("label", "1");
            jo.put("value", middleList.size());
            json.add(jo);
        }
        if(highList.size()>0&&highList!=null){
            JSONObject jo = new JSONObject();
            jo.put("label", "2");
            jo.put("value", highList.size());
            json.add(jo);
        }
        return json.toString();
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
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("type", type);
        
        List<Task> taskList = alarmService.getTaskByOrderId(paramMap);
        JSONArray json = new JSONArray();
        for (int i=0; i<taskList.size(); i++) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("taskId", taskList.get(i).getTaskId());
            param.put("level", WarnType.LOWLEVEL.ordinal());
            List<Alarm> lowList = alarmService.getAlarmByTaskId(param);
            param.put("level", WarnType.MIDDLELEVEL.ordinal());
            List<Alarm> middleList = alarmService.getAlarmByTaskId(param);
            param.put("level", WarnType.HIGHLEVEL.ordinal());
            List<Alarm> highList = alarmService.getAlarmByTaskId(param);
            
            
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
    @RequestMapping(value="orderDetails.html")
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
     */
    @RequestMapping(value="historyInit.html")
    public String historyInit(HttpServletRequest request){
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
        return "/source/page/order/historyDetail";
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
        int progress = taskService.findProgressByOrderId(orderId);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("progress", progress);
        //object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
