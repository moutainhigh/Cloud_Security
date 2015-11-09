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
 * 创建日期：  2015-11-06
 * 描        述： 设备资源管理
 * 版        本：  1.0
 */
@Controller
public class EquResourceController {
	

	@Autowired
	IAssetService assetService;
	@Autowired
	IAssertAlarmService assetAlarmService;
	/**
	 * 功能描述：资产地理位置统计分析
	 *		 @time 2015-10-26
	 */
	@RequestMapping("/equResourceUI.html")
	public String dataAssertUI(HttpServletRequest request,HttpServletResponse response){
		return "/source/adminPage/userManage/equResource";
	}
	
}
