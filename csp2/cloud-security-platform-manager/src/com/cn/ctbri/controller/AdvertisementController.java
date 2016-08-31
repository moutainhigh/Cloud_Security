package com.cn.ctbri.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cn.ctbri.cfg.CspAPIWorker;
import com.cn.ctbri.entity.Advertisement;
import com.cn.ctbri.service.IAdvertisementService;
import com.cn.ctbri.util.CommonUtil;

/**
 * 创 建 人  ：  zsh
 * 创建日期：  2016-6-12
 * 描        述：  后台广告管理
 * 版        本：  1.0
 */
@Controller
public class AdvertisementController {
	@Autowired
	IAdvertisementService adService;
	
	/**
     * 功能描述：后台广告管理页面
     * 参数描述：无
     *       @time 2016-6-12
     */
    @RequestMapping("/adminAdvertisementManageUI.html")
    public String noticeManageUI(Model model,HttpServletRequest request){
        //User globle_user = (User) request.getSession().getAttribute("globle_user");
        List<Advertisement> list = adService.findAllAdvertisement();
        model.addAttribute("adList",list);//广告列表
        return "/source/adminPage/userManage/advertisementManage";
    }
    
    /**
     * 功能描述：添加广告
     *       @time 2016-6-12
     */
    @RequestMapping("/adminAdvertisementAdd.html")
    public void addAdvertisement(HttpServletRequest request, HttpServletResponse response){
    	Map<String, Object> m = new HashMap<String, Object>();
    	try{
    		//获取根目录
    		String folderPath = request.getSession().getServletContext().getRealPath("");
    		folderPath = folderPath.substring(0,folderPath.lastIndexOf("\\"));
    		folderPath = folderPath + "/csp/source/images/ad/";
//    		folderPath = folderPath + "/cloud-security-platform/source/images/portal/ad/";
    		String images = "";
    		long  startTime=System.currentTimeMillis();
    		//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
    		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 检查form中是否有enctype="multipart/form-data"
    		if(multipartResolver.isMultipart(request)){
    			//将request变成多部分request
    			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
    			//获取multiRequest 中所有的文件名
    			Iterator iter=multiRequest.getFileNames();
    			
    			while(iter.hasNext()){
    				//一次遍历所有文件
    				MultipartFile file=multiRequest.getFile(iter.next().toString());
    				if(file!=null){
    					String path= folderPath+file.getOriginalFilename();
    					//上传
    					file.transferTo(new File(path));
    					images = images + file.getOriginalFilename()+",";
    				}
    				
    			}
    			
    			if (!images.equals("")) {
    				images = images.substring(0,images.length() - 1);
    			}
    		}
    		
//    		String name = "";
//    		String image = "";
//    		String name = new String(advertisement.getName().getBytes("ISO-8859-1"), "UTF-8");
//    		image = new String(advertisement.getImage().getBytes("ISO-8859-1"), "UTF-8");
    		String name =  new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
    		String startDateStr = request.getParameter("startDate");
    		String endDateStr = request.getParameter("endDate");
    		
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟  
    		Date createDate = new Date();
    		//TODO
    		//通过接口方式将数据存储于前端Portal数据库中
    		String adId = CspAPIWorker.addAdvertisement(name, images,
    				startDateStr, endDateStr, sdf.format(createDate));
    		System.out.println("adId:"+adId);
    		if(adId == null || adId.equals("")) {
    			m.put("success", false);
    			return;
    		}
    		
    		sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
    		Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);
            Advertisement advertisement = new Advertisement();
            advertisement.setId(Integer.valueOf(adId));
    		advertisement.setName(name);
    		advertisement.setImage(images);
    		advertisement.setStartDate(startDate);
    		advertisement.setEndDate(endDate);
    		advertisement.setCreateDate(createDate);//创建时间
    		adService.insert(advertisement);
    		m.put("success", true);
    		
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		m.put("success", false);
    	} finally {
    		//object转化为Json格式
    		JSONObject JSON = CommonUtil.objectToJson(response, m);
    		try {
    			// 把数据返回到页面
    			CommonUtil.writeToJsp(response, JSON);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
       
//        return "redirect:/adminAdvertisementManageUI.html";
    }
    
    /**
     * 功能描述：删除广告
     *       @time 2016-6-12
     */
    @RequestMapping("/adminAdvertisementDelete.html")
    public void delete(Advertisement advertisement,HttpServletRequest request, HttpServletResponse response){
    	Map<String, Object> m = new HashMap<String,Object>();
    	String adId = request.getParameter("adId");//广告Id
    	//通过接口方式将数据从前端Portal数据库中删除
		String code = CspAPIWorker.deleteAdvertisement(adId);
		if(code == null || !code.equals("200")) {
			m.put("success", false);
		}else {
			//将数据从运营管理系统数据库中删除
			adService.delete(Integer.valueOf(adId));
			m.put("success", true);
		}
		
		
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        //return "redirect:/adminAdvertisementManageUI.html";
    }
}
