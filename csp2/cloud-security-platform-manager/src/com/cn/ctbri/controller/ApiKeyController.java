package com.cn.ctbri.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import se.akerfeldt.com.google.gson.Gson;

import com.cn.ctbri.service.IUserService;
/**
 * 创 建 人  ：  zsh
 * 创建日期：  2016-6-14
 * 描        述：  后台apikey查询
 * 版        本：  1.0
 */
@Controller
public class ApiKeyController {
	
	@Autowired
	IUserService userService;
	/**
     * 功能描述：后台apikey查询页面
     *       @time 2016-6-14
     */
	@RequestMapping("adminApikeyUI.html")
	public String apiKeyUI(Model model, HttpServletRequest request){
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("name", request.getParameter("userName"));
//		paramMap.put("apikey", request.getParameter("apikey"));
//		List apikeyList = userService.findUserApikey(paramMap);
//		model.addAttribute("apikey", apikeyList);
		
		return "/source/adminPage/userManage/apikeyAnalysis";
	}
	
	@RequestMapping("/apikeyAnalysis.html")
	public void apikeyAnalysis(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		try{
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("name", request.getParameter("userName"));
			paramMap.put("apikey", request.getParameter("apikey"));
			List apikeyList = userService.findUserApikey(paramMap);
			
			Gson gson= new Gson();
			String resultGson = gson.toJson(apikeyList);
			
			PrintWriter out;
			out = response.getWriter();
			out.write(resultGson); 
			out.flush(); 
			out.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
