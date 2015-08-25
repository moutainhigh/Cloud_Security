package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import se.akerfeldt.com.google.gson.Gson;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceType;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskHW;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IDistrictDataService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Random;

/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-8-20
 * 描        述：  数据展示控制层
 * 版        本：  1.0
 */
@Controller
public class DistrictDataController {
	
    @Autowired
    IDistrictDataService districtDataService;


	 
    /**
     * 查询所有省份下的漏洞
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="initDistrictList.html")
    @ResponseBody
    public String initDistrictList( HttpServletRequest request, HttpServletResponse response) throws IOException {
        String serviceId = request.getParameter("serviceId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("serviceId", serviceId);
        Gson gson= new Gson();
        List<District> districtList = districtDataService.getDistrictByAll(paramMap);
        String resultGson = gson.toJson(districtList);//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }
    
    /**
     * 根据省份id查询对应省份top5的数据
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getDistrictData.html")
    @ResponseBody
    public String getDistrictData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        String districtId = request.getParameter("id");
        String serviceId = request.getParameter("serviceId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("serviceId", serviceId);
        paramMap.put("districtId", districtId);
        Gson gson= new Gson();
        List result = districtDataService.getDistrictDataById(paramMap);
        String resultGson = gson.toJson(result);//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }
    
    /**
     * 地域告警top5
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getDistrictAlarmTop5.html")
    @ResponseBody
    public String getDistrictAlarmTop5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        String serviceId = request.getParameter("serviceId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("serviceId", serviceId);
        Gson gson= new Gson();
        List<District> result = districtDataService.getDistrictAlarmTop5(paramMap);
        String resultGson = gson.toJson(result);//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }
    
    /**
     * 服务能力告警top5
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getServiceAlarmTop5.html")
    @ResponseBody
    public String getServiceAlarmTop5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        String serviceId = request.getParameter("serviceId");
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("serviceId", serviceId);
        Gson gson= new Gson();
        List result = districtDataService.getServiceAlarmTop5(serviceId);
        String resultGson = gson.toJson(result);//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }
    
    
    /**
     * 服务能力告警近5个月数量统计
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getServiceAlarmMonth5.html")
    @ResponseBody
    public String getServiceAlarmMonth5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        String serviceId = request.getParameter("serviceId");
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("serviceId", serviceId);
        Gson gson= new Gson();
        List result = districtDataService.getServiceAlarmMonth5(serviceId);
        String resultGson = gson.toJson(result);//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }

}
