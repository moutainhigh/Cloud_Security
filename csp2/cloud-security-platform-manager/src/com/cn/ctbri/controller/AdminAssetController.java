package com.cn.ctbri.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.entity.AssertAlarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.District;
import com.cn.ctbri.service.IAssertAlarmService;
import com.cn.ctbri.service.IAssetService;



/**
 * 创 建 人  ：  zx
 * 创建日期：  2015-10-26
 * 描        述： 资产数据分析
 * 版        本：  1.0
 */
@Controller
public class AdminAssetController {
	

	@Autowired
	IAssetService assetService;
	@Autowired
	IAssertAlarmService assetAlarmService;
	/**
	 * 功能描述：资产地理位置统计分析
	 *		 @time 2015-10-26
	 */
	@RequestMapping("/adminDataAssetUI.html")
	public String dataAssertUI(HttpServletRequest request,HttpServletResponse response){
		
		String tablList = request.getParameter("tablList");
		String anList = request.getParameter("anList");
        
		String assetUserType = request.getParameter("assetUserType");
		String prov = request.getParameter("prov");
		String city = request.getParameter("city");
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("assetUserType", assetUserType);
        paramMap.put("province", prov);
        paramMap.put("city", city);
        if("0".equals(tablList)&&"0".equals(anList)){
           List<Asset> list=assetService.findByAssetProAndCity(paramMap);
           request.setAttribute("list", list);
        }
        if(tablList==null||"".equals(tablList)){
			tablList="0";
		}
        if(anList==null||"".equals(anList)){
			anList="0";
		}
        request.setAttribute("paramMap", paramMap);
        request.setAttribute("tablList", tablList);
        request.setAttribute("anList", anList);
		return "/source/adminPage/userManage/dataAsset";
	}
	/**
	 * 功能描述：资产用途统计分析/资产服务情况分析
	 * @throws UnsupportedEncodingException 
	 *		 @time 2015-10-29
	 */
	@RequestMapping("/adminPurposeAssetUI.html")
	public String dataPurposeUI(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		
		String tablList = request.getParameter("tablList");
		if(tablList==null||"".equals(tablList)){
			tablList="0";
		}
		String anList = request.getParameter("anList");
		if(anList==null||"".equals(anList)){
			anList="0";
		}
		String assetUserType = request.getParameter("assetUserType1");
		String purpose = new String(request.getParameter("purpose").getBytes("ISO-8859-1"), "UTF-8");
		String begin_date=request.getParameter("begin_datevo");
		String end_date = request.getParameter("end_datevo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("assetUserType", assetUserType);
        paramMap.put("purpose", purpose);
        paramMap.put("begin_date", begin_date);
        paramMap.put("end_date", end_date);
       	List<Asset> list=assetService.findByAssetPurposeList(paramMap);
       	if("0".equals(tablList)&&"1".equals(anList)){
       		request.setAttribute("porlist", list);
       		request.setAttribute("porMap", paramMap); 
       	 }else if("0".equals(tablList)&&"2".equals(anList)){
       		request.setAttribute("servlist", list); 
       		request.setAttribute("servMap", paramMap); 
       	 }   	 
        request.setAttribute("tablList", tablList);
        request.setAttribute("anList", anList);
		return "/source/adminPage/userManage/dataAsset";
	}
	
	/**
	 * 功能描述：资产历史警告分析
	 * @throws Exception 
	 *		 @time 2015-10-29
	 */
	@RequestMapping("/admineAssetAlarmUI.html")
	public String dataAssetAlarmUI(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tablList = request.getParameter("tablList");
		String anList = request.getParameter("anList");
		String assertName =request.getParameter("assertName");
		
		if(assertName!=null&&!"".equals(assertName)){
			assertName=new String(request.getParameter("assertName").getBytes("ISO8859_1"), "UTF-8");
		}
		String serverId = request.getParameter("serverId");
		String begin_date=request.getParameter("begin_datevo");
		String end_date = request.getParameter("end_datevo");
		String orderCode = request.getParameter("orderCode");
		String alarmRank = request.getParameter("alarmRank");
		String alarmName=request.getParameter("alarmName");
		if(alarmName!=null&&!"".equals(alarmName)){
			alarmName=new String(request.getParameter("alarmName").getBytes("ISO8859_1"), "UTF-8");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("assertName", assertName);
        paramMap.put("serverId", serverId);
        paramMap.put("begin_date", begin_date);
        paramMap.put("end_date", end_date);
        paramMap.put("orderCode", orderCode);
        paramMap.put("alarmRank", alarmRank); 
        paramMap.put("alarmName", alarmName);      
        if("1".equals(tablList)&&"0".equals(anList)){
       	     List<AssertAlarm> list=assetAlarmService.findAssertAlarmByMap(paramMap);
	       	 request.setAttribute("assertAlarmlist", list);  
        }else if("1".equals(tablList)&&"1".equals(anList)){
      	     List<AssertAlarm> list=assetAlarmService.findAssertAlarmTypeByMap(paramMap);
	       	 request.setAttribute("AlarmTypelist", list);  
        }
        request.setAttribute("assertAlarmMap", paramMap);
        request.setAttribute("tablList", tablList);
        request.setAttribute("anList", anList);
		return "/source/adminPage/userManage/dataAsset";
	}
	
	/**
     * 查询资产警告趋势数据
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value="admineAssetAlarmTrendUI.html")
    @ResponseBody
    public String initDistrictList( HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String assertName =request.getParameter("assertName");
		if(assertName!=null&&!"".equals(assertName)){
			assertName=new String(request.getParameter("assertName").getBytes("ISO8859_1"), "UTF-8");
		}
		String serverId = request.getParameter("serverId");
		String begin_date=request.getParameter("begin_date");
		String end_date = request.getParameter("end_date");
		String timeTtype = request.getParameter("timeTtype");
		if(timeTtype!=null&&"1".equals(timeTtype)){
			timeTtype="%H";
		}else if(timeTtype!=null&&"3".equals(timeTtype)){
			timeTtype="%m";
		}else{
			timeTtype="%d";
		}
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("assertName", assertName);
        paramMap.put("serverId", serverId);
        paramMap.put("begin_date", begin_date);
        paramMap.put("end_date", end_date);
        paramMap.put("timeTtype", timeTtype);
        JSONObject jo = new JSONObject();
    	List<AssertAlarm> list=assetAlarmService.findAssertAlarmTrendByMap(paramMap);
	    request.setAttribute("AlarmTrendlist", list);  
        jo.put("AlarmTrendlist", list);
        String resultGson = jo.toString();//转成json数据
        response.setContentType("textml;charset=UTF-8");
        response.getWriter().print(resultGson);
        return null;
    }
	
}
