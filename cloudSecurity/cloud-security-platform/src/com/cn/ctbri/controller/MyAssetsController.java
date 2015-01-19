package com.cn.ctbri.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-16
 * 描        述：  我的资产
 * 版        本：  1.0
 */
@Controller
public class MyAssetsController {
	@Autowired
	IAssetService assetService;
	/**
	 * 功能描述： 我的资产页面
	 * 参数描述： Model model,HttpServletRequest request
	 *		 @time 2015-1-16
	 */
	@RequestMapping("/userAssetsUI.html")
	public String userBillUI(Model model,HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		List<Asset> list = assetService.findByUserId(globle_user.getId());
		model.addAttribute("list",list);
		return "/source/page/userCenter/userAssets";
	}
	/**
	 * 功能描述： 我的资产页面
	 * 参数描述： Model model
	 *		 @time 2015-1-16
	 */
	@RequestMapping("/addAsset.html")
	public String addAsset(Model model,Asset asset,HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		asset.setUserid(globle_user.getId());//用户ID
		asset.setCreate_date(new Date());//创建日期
		asset.setStatus(0);//资产状态(1：已验证，0：未验证)
		assetService.saveAsset(asset);
		return "redirect:/userAssetsUI.html";
	}
	
}
