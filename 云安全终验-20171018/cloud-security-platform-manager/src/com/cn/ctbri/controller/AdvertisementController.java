package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.cn.ctbri.cfg.CspWorker;
import com.cn.ctbri.entity.Advertisement;
import com.cn.ctbri.service.IAdvertisementService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.SFTPUtil;

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
    public String advertisementManageUI(Model model,HttpServletRequest request){
        //User globle_user = (User) request.getSession().getAttribute("globle_user");
    	int type = 0;
    	String typeStr = request.getParameter("type");
    	if(typeStr != null) {
    		type = Integer.valueOf(typeStr);
    	}
        List<Advertisement> list = adService.findADbyType(type);
        model.addAttribute("adList",list);//广告列表
        model.addAttribute("adType",type);//广告分类
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
//    		String folderPath = request.getSession().getServletContext().getRealPath("");
//    		folderPath = folderPath.substring(0,folderPath.lastIndexOf("\\"));
//    		folderPath = folderPath + "/csp/source/images/ad/";
//    		folderPath = folderPath + "/cloud-security-platform/source/images/portal/ad/";
//    		String images = "";
//    		long  startTime=System.currentTimeMillis();
//    		//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
//    		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
//					request.getSession().getServletContext());
//			// 检查form中是否有enctype="multipart/form-data"
//    		if(multipartResolver.isMultipart(request)){
//    			//将request变成多部分request
//    			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
//    			//获取multiRequest 中所有的文件名
//    			Iterator iter=multiRequest.getFileNames();
//    			
//    			while(iter.hasNext()){
//    				//一次遍历所有文件
//    				MultipartFile file=multiRequest.getFile(iter.next().toString());
//    				if(file!=null){
//    					String path= folderPath+file.getOriginalFilename();
//    					//上传
//    					file.transferTo(new File(path));
//    					images = images + file.getOriginalFilename()+",";
//    				}
//    				
//    			}
//    			
//    			if (!images.equals("")) {
//    				images = images.substring(0,images.length() - 1);
//    			}
//    		}
    		MultipartHttpServletRequest multipartHttpservletRequest=(MultipartHttpServletRequest) request;
	        MultipartFile multipartFile = multipartHttpservletRequest.getFile("file");
	        if(multipartFile==null){
	        	m.put("success", false);
	        	return;
	        }
	        String originalFileName=multipartFile.getOriginalFilename();
	        //上传文件名称；
	        String uploadFileName = String.valueOf(System.currentTimeMillis())+ 
	        						originalFileName.substring(originalFileName.lastIndexOf("."));
	        boolean isSuccFlag = SFTPUtil.upload(multipartFile, uploadFileName, 3);

	        if(!isSuccFlag){
				m.put("success", false);
				return;
	        }
	        //解决中文乱码问题
    		//String name =  new String(request.getParameter("name").getBytes("ISO8859_1"), "UTF-8");
	        String name=request.getParameter("name");
    		String startDateStr = request.getParameter("startDate");
    		String endDateStr = request.getParameter("endDate");
    		int type = Integer.valueOf(request.getParameter("type"));
    		int orderIndex = adService.getMaxOrderIndex(type) + 1;
    		
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟  
    		Date createDate = new Date();
    		
    		//通过接口方式将数据存储于前端Portal数据库中
    		String adId = CspWorker.addAdvertisement(name, uploadFileName,type,
    				orderIndex, startDateStr, endDateStr, sdf.format(createDate));
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
    		advertisement.setImage(uploadFileName);
    		advertisement.setStartDate(startDate);
    		advertisement.setEndDate(endDate);
    		advertisement.setCreateDate(createDate);//创建时间
    		advertisement.setType(type);
    		advertisement.setOrderIndex(orderIndex);
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
		String code = CspWorker.deleteAdvertisement(adId);
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
    }
    
    @RequestMapping("/adminAdvertisementSort.html")
    public void sort(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> m = new HashMap<String,Object>();
    	
    	String adId1 = request.getParameter("adId1");
    	String adOrder1 = request.getParameter("adOrder1");
    	
    	String adId2 = request.getParameter("adId2");
    	String adOrder2 = request.getParameter("adOrder2");
    	
    	//通过接口方式将数据从前端Portal数据库中排序
		String code = CspWorker.sortAdvertisement(adId1, adOrder1, adId2, adOrder2);
		if(code == null || !code.equals("200")) {
			m.put("success", false);
		}else {
			//将数据从运营管理系统数据库中排序
			Advertisement ad = new Advertisement();
			ad.setId(Integer.valueOf(adId1));
			ad.setOrderIndex(Integer.valueOf(adOrder1));
			adService.update(ad);
			
			ad.setId(Integer.valueOf(adId2));
			ad.setOrderIndex(Integer.valueOf(adOrder2));
			adService.update(ad);
			
			m.put("success", true);
		}
		
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
