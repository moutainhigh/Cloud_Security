package com.cn.ctbri.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import se.akerfeldt.com.google.gson.Gson;

import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.City;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IDistrictDataService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.DateUtils;

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
    @Autowired
    IAssetService assetService;
	@Autowired
    ISelfHelpOrderService selfHelpOrderService;
	@Autowired
	IServService servService;
	 
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
        paramMap.put("serviceId", Integer.parseInt(serviceId));
        JSONObject jo = new JSONObject();
        List<District> districtList = districtDataService.getDistrictByAll(paramMap);
        int max = districtDataService.getMax(paramMap);
        jo.put("districtList", districtList);
        jo.put("max", max);
        String resultGson = jo.toString();//转成json数据
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
        paramMap.put("serviceId", Integer.parseInt(serviceId));
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
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("serviceId", serviceId);
        Gson gson= new Gson();
        List result = districtDataService.getServiceAlarmTop5(paramMap);
        String resultGson = gson.toJson(result);//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }
    
    /**
     * 最近一小时内漏洞跟踪
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getVulnscanAlarmOneHour.html")
    @ResponseBody
    public void getVulnscanAlarmOneHour(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        String endDate = DateUtils.dateToString(new Date());
        Date date = DateUtils.getDateBeforeOneHour(new Date());
        String beginDate = DateUtils.dateToString(date);
        
        Map<String,Object> dateMap = new HashMap<String,Object>();
        dateMap.put("beginDate", beginDate);
        dateMap.put("endDate", endDate);
        List result = districtDataService.getVulnscanAlarmOneHour(dateMap);
        JSONArray jsonArray = new JSONArray();

        if(result!=null && result.size()>0){
        	for(int i = 0; i<result.size(); i++){
            	JSONObject jsonObject = new JSONObject();
        		Map<String,Object> newMap = new HashMap<String,Object>();
        		newMap = (Map<String,Object>)result.get(i);
        		jsonObject.put("value", newMap.get("count"));
        		jsonObject.put("name", newMap.get("name"));
                jsonArray.add(jsonObject);
        	}
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("dataArray", jsonArray);
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
    
    /**
     * 安全帮使用情况
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getServiceUseInfo.html")
    @ResponseBody
    public void getServiceUseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        JSONArray jsonArray = new JSONArray();

        //发现漏洞数
        int leakNum = selfHelpOrderService.findLeakNum(1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","发现漏洞数");
        jsonObject.put("value",leakNum);
        jsonArray.add(jsonObject);
        
        //扫描页面数
        int webPageNum = selfHelpOrderService.findWebPageNum();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name","扫描页面数");
        jsonObject1.put("value",webPageNum);
        jsonArray.add(jsonObject1);
        
        //扫描网站
        int webSite = selfHelpOrderService.findWebSite();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name","扫描网站");
        jsonObject2.put("value",webSite);
        jsonArray.add(jsonObject2);
        
        //抵御攻击次数
        int wafNum = 200;
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("name","抵御攻击次数");
        jsonObject3.put("value",wafNum);
        jsonArray.add(jsonObject3);
        
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("dataArray", jsonArray);
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
    
    /**
     * 重大漏洞分布视图(TOP20)
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getVulnscanAlarmTOP20.html")
    @ResponseBody
    public void getVulnscanAlarmTOP20(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        List result = districtDataService.getVulnscanAlarmTOP20();
        JSONArray jsonArray = new JSONArray();

        if(result!=null && result.size()>0){
        	for(int i = 0; i<result.size(); i++){
            	JSONObject jsonObject = new JSONObject();
        		Map<String,Object> newMap = new HashMap<String,Object>();
        		newMap = (Map<String,Object>)result.get(i);
        		jsonObject.put("value", newMap.get("count"));
        		jsonObject.put("name", newMap.get("name"));
                jsonArray.add(jsonObject);
        	}
        }
        
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("dataArray", jsonArray);
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
    
    /**
     * 最近6个月各等级漏洞
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getVulnscanAlarmByLevelMonth6.html")
    @ResponseBody
    public void getVulnscanAlarmByLevelMonth6(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //List result = districtDataService.getVulnscanAlarmByLevelMonth6();
        JSONArray jsonArray = new JSONArray();

        //前6个月的月份
        String lastMonth6 = districtDataService.getMonth(5);
        //获取月份，近5个月
        List monthList = new ArrayList();
        for (int i = 5; i >= 0; i--) {
        	String month = districtDataService.getMonth(i);
        	monthList.add(month);
		}
        
        //定义漏洞等级
        int[] levels = {-1,0,1,2,3};
        String[] levelNames = {"信息","低","中","高","紧急"};
        List levelList = new ArrayList();
        
        //优化（查询六个月的数据）
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("month", lastMonth6);
        List dataList = districtDataService.getVulnscanAlarm(paramMap);
        
        for(int i = 0; i<levels.length; i++){
        	levelList.add(levelNames[i]);
        	//定义每个月的数值list
        	List<Integer> countList = new ArrayList<Integer>();
        	//按level分组
        	List tempList = new ArrayList();
        	if(dataList!=null && dataList.size()>0){
        		for(int j = 0; j < dataList.size(); j++){
        			Map newMap = (Map)dataList.get(j);
        			int newLevel = Integer.parseInt(newMap.get("level").toString());
        			if(newLevel==levels[i]){
        				tempList.add(dataList.get(j));
        			}
        		}
        	}
        	
        	//判断tempList
    		for(int j=0; j<monthList.size(); j++){//6个月
            	if(tempList!=null && tempList.size()>0){
            		boolean flag = false;
        			for(int k = 0; k < tempList.size(); k++){
        				Map<String,Object> newMap = (Map<String,Object>)tempList.get(k);
        				String month = newMap.get("month1").toString();
        				int count = Integer.parseInt(newMap.get("count").toString());
        				if(month.equals(monthList.get(j))){//有当月日期
        					countList.add(count);
        					flag = true;
        					break;
        				}else{
        					flag = false;
        				}
        			}
        			if(!flag){
        				countList.add(0);
        			}
        		}else{
        			countList.add(0);
        		}
        	}
        	
    		//生成前台展示json
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("name", levelNames[i]);
        	jsonObject.put("type", "bar");
        	jsonObject.put("data", countList);
        	
        	JSONObject jsonNormal = new JSONObject();
        	jsonNormal.put("show", true);
        	jsonNormal.put("position","top");
        	
        	JSONObject jsonLabel = new JSONObject();
        	jsonLabel.put("normal", jsonNormal);
        	
        	jsonObject.put("label", jsonLabel);
        	jsonArray.add(jsonObject);
        }
        
        
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("dataArray", jsonArray);
        m.put("monthList", monthList);
        m.put("levelList", levelList);
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
    
    /**
     * 查询用户最近6个月内不同类型服务订单数量变化
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getServiceUseInfoMonth6.html")
    @ResponseBody
    public void getServiceUseInfoMonth6(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User globle_user = (User) request.getSession().getAttribute("globle_user");
        
       //前6个月的月份
        String lastMonth6 = districtDataService.getMonth(23);
        //获取月份，近24个月
        List monthList = new ArrayList();
        for (int i = 23; i >= 0; i--) {
        	String month = districtDataService.getMonth(i);
        	monthList.add(month);
		}
        
        int maxCount = 0;//定义最大数值
        //根据用户和月份查询
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < monthList.size(); i++){
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("userId", globle_user.getId());
            paramMap.put("month", monthList.get(i));
            List dataList = districtDataService.getServiceUseInfoMonth6(paramMap);

            List<Integer> tempList = new ArrayList<Integer>();
            boolean flag = false;
            for(int j=1; j<=6; j++){
                if(dataList!=null && dataList.size()>0){
            		for(int k = 0; k < dataList.size(); k++){
                		String month = ((Map)dataList.get(k)).get("month1").toString();
                		int count = Integer.parseInt(((Map)dataList.get(k)).get("count").toString());
                		if(count > maxCount){
                			maxCount = count;
                		}
                		int serviceId = Integer.parseInt(((Map)dataList.get(k)).get("serviceId").toString());
                		if(serviceId==j){
                			tempList.add(count);
                			flag = true;
                			break;
                		}else{
                			flag = false;
                		}
                	}
            		if(!flag){
            			tempList.add(0);
            		}
                }else{
                	tempList.add(0);
                }
            }

        	
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("name", monthList.get(i));
        	jsonObject.put("value", tempList);
        	jsonArray.add(jsonObject);
        }
        
        //获取服务名称
        List<Serv> servList = servService.findAllService();
        List indicatorList = new ArrayList();
        if(servList!=null && servList.size()>0){
        	for(int i = 0; i < servList.size(); i++){
        		Map<String,Object> newMap = new HashMap<String,Object>();
        		newMap.put("text", servList.get(i).getName());
        		newMap.put("max", maxCount);
        		indicatorList.add(newMap);
        	}
        }
       
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("dataArray", jsonArray);
        m.put("indicatorList", indicatorList);
        m.put("monthList", monthList);
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
    
    /**
     * 用户行业分布：各行业注册用户数与已下订单数
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getIndustryStatistics.html")
    @ResponseBody
    public void getIndustryStatistics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取各行业注册用户数
        List userList = districtDataService.getIndustryUserCount();
        //行业list
        List industryList = new ArrayList();
        List jsonUserList = new ArrayList();
        if(userList!=null && userList.size()>0){
        	for(int i = 0; i<userList.size(); i++){
        		industryList.add(((Map)(userList.get(i))).get("industry"));
        		jsonUserList.add(((Map)(userList.get(i))).get("userCount"));
        	}
        }
        
        //获取各行业订单数
        List orderList = districtDataService.getIndustryOrderCount();
        List jsonOrderList = new ArrayList();
        if(orderList!=null && orderList.size()>0){
        	for(int i = 0; i < orderList.size(); i++){
        		jsonOrderList.add(((Map)(orderList.get(i))).get("orderCount"));
        	}
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("userList", jsonUserList);
        m.put("orderList", jsonOrderList);
        m.put("industryList", industryList);
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
    
    /**
     * 用户行业分布：各行业注册用户数与已下订单数
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getOrderServiceTimes.html")
    @ResponseBody
    public void getOrderServiceTimes(HttpServletRequest request, HttpServletResponse response) throws IOException {

    	JSONArray jsonArray = new JSONArray();
    	//获取服务
    	List<Serv> servList = servService.findAllService();
    	List servNameList = new ArrayList();
    	if(servList!=null && servList.size()>0){
    		for(int i = 0; i < servList.size(); i++){
    			servNameList.add(((Serv)servList.get(i)).getName());
    			List countList = new ArrayList();
    			List list = districtDataService.getOrderCountTimesAndServiceId(((Serv)servList.get(i)).getId());
    			if(list!=null && list.size()>0){
    				for(int j = 0; j<list.size();j++){
    					countList.add(Integer.parseInt(((Map)list.get(j)).get("count2").toString()));
    				}
    			}
    			
    			//生成前台json
    			JSONObject jsonObject = new JSONObject();
    			jsonObject.put("name", ((Serv)servList.get(i)).getName());
    			jsonObject.put("type", "line");
    			jsonObject.put("stack", "总量");
    			
    			JSONObject jsonObject2 = new JSONObject();
    			jsonObject2.put("normal", new JSONObject());

    			jsonObject.put("areaStyle", jsonObject2);
    			
    			jsonObject.put("data", countList);
    			
    			jsonArray.add(jsonObject);
    		}
    	}
    	
    	//获取一年内日期
    	List dayList = districtDataService.getDaysInYear();
    	List jsonDayList = new ArrayList();
    	if(dayList!=null && dayList.size()>0){
    		for(int i = 0; i < dayList.size(); i++){
    			Map map = (Map)dayList.get(i);
    			jsonDayList.add(map.get("dayName").toString());
    		}
    	}
    	
    	//获取一年内月份的最后一天
    	List lastdayList = districtDataService.getLastDayForMonthInYear();
    	List jsonLastdayList = new ArrayList();
    	if(lastdayList!=null && lastdayList.size()>0){
    		for(int i = 0; i < lastdayList.size(); i++){
    			Map map = (Map)lastdayList.get(i);
    			jsonLastdayList.add(map.get("lastday").toString());
    		}
    	}

    	Map<String, Object> m = new HashMap<String, Object>();
        m.put("seriesList", jsonArray);
        m.put("servNameList", servNameList);
        m.put("dayList", jsonDayList);
        m.put("lastdayList", jsonLastdayList);
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
        
        Gson gson= new Gson();
        //获取月份，近5个月
        List result = new ArrayList();
        for (int i = 4; i >= 0; i--) {
        	String month = districtDataService.getMonth(i);
        	Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("serviceId", serviceId);
            paramMap.put("month", month);
            Alarm alarm = districtDataService.getServiceAlarmByMonth(paramMap);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("count", alarm.getCount());
            param.put("months", month);
            result.add(param);
		}
        
//        List result = districtDataService.getServiceAlarmMonth5(paramMap);
        String resultGson = gson.toJson(result);//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }
    
    /**
     * 正在使用网站安全帮的资产
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="findUseAssetAddr.html")
    @ResponseBody
    public String findUseAssetAddr(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        Gson gson= new Gson();
        List<Asset> listAsset = assetService.findAllAssetAddr();
        String resultGson = gson.toJson(listAsset);//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }
    
    /**
     * 查询所有省份数据
     * 
     * @return 省份列表
     * @throws IOException
     */
    @RequestMapping(value="getDistrictListAll.html")
    @ResponseBody
    public String getDistrictListAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");

        Gson gson= new Gson();
        List<District> result = districtDataService.getDistrictList();
        String resultGson = gson.toJson(result);//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }
    
    /**
     * 根据省 查询省下所有市
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="getCityList.html")
    @ResponseBody
    public String getCityList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        String districtId = request.getParameter("districtId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("districtId", districtId);
        Gson gson= new Gson();
        List<City> result = districtDataService.getCityListByProv(paramMap);
        String resultGson = gson.toJson(result);//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }    

}
