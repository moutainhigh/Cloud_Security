package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cn.ctbri.cfg.CspWorker;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceDetail;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.SFTPUtil;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-3
 * 描        述：  后台服务管理
 * 版        本：  1.0
 */
@Controller
public class ServController {
	
	@Autowired
	IServService servService;
	@Autowired
    ISelfHelpOrderService selfHelpOrderService;
	
	private static Map<String,Integer> scanTypeMap = new HashMap<String, Integer>();
	static{
		
		scanTypeMap.put("10分钟", 1);
		scanTypeMap.put("30分钟", 2);
		scanTypeMap.put("1小时", 3);
		scanTypeMap.put("1天", 4);
		scanTypeMap.put("每周", 5);
		scanTypeMap.put("每月", 6);
	}
	
	/**
	 * 功能描述：服务管理页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/adminServUI.html")
	public String adminDeleteUser(User user,HttpServletRequest request){
		//获取服务类型
        List<Serv> servList = selfHelpOrderService.findService();
        request.setAttribute("servList", servList);//订单总数
		return "/source/adminPage/userManage/serv";
	}
	
	/**
	 * 功能描述：查询服务列表
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/getServiceList.html")
	public String getServiceList(HttpServletRequest request){
		//获取服务类型
        List servList = selfHelpOrderService.findSerList(null);
        request.setAttribute("servList", servList);//订单总数
		return "/source/adminPage/userManage/serviceList";
	}
	
	/**
	 * 功能描述：添加服务页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/addServUI.html")
	public String addServUI(User user,HttpServletRequest request){
		return "/source/serviceManage/addSer";
	}
	
	/**
	 * 功能描述：修改服务页面
	 * @throws UnsupportedEncodingException 
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/updateServUI.html")
	public String updateServUI(HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {

		String servId = request.getParameter("servId");
		String remarks = new String(request.getParameter("remarks").getBytes("ISO-8859-1"),"UTF-8");
		String parent = new String(request.getParameter("parent").getBytes("ISO-8859-1"),"UTF-8");
		String icon = request.getParameter("icon");
		String servName = new String(request.getParameter("servName").getBytes("ISO-8859-1"),"UTF-8");
		String type = request.getParameter("type");
		
		request.setAttribute("servId", servId);
		request.setAttribute("type", type);
		request.setAttribute("remarks", remarks);
		request.setAttribute("icon", icon);
		request.setAttribute("servName", servName);
		request.setAttribute("parent", parent);
		return "/source/serviceManage/updateSer";
	}

	
	/**
	 * 功能描述：上传服务图标
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/uploadIcon.html")
	public void uploadIcon(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			MultipartHttpServletRequest multipartHttpservletRequest=(MultipartHttpServletRequest) request;
	        MultipartFile multipartFile = multipartHttpservletRequest.getFile("file");
	        if(multipartFile==null){
	        	m.put("success", false);
	        	m.put("errorMsg", "请上传文件!");
	        	
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
			
	        String originalFileName=multipartFile.getOriginalFilename();
	        String fileType = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
	        //判断文件格式
	        if(!"png".equals(fileType.toLowerCase())){
	        	m.put("successFlag", false);
	        	m.put("errorMsg", "请导入png格式的文件!");
	        	
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
	        //上传文件名称；
	        String uploadFilePathName = String.valueOf(System.currentTimeMillis())+originalFileName.substring(originalFileName.lastIndexOf("."));
	        boolean isSuccFlag = SFTPUtil.upload(multipartFile, uploadFilePathName, 1);

	        if(isSuccFlag){
				m.put("success", true);
				m.put("filePath", uploadFilePathName);
	        } else {
	        	m.put("success", false);
	        }

			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m.put("success", false);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 功能描述：添加服务
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/addServ.html",method = RequestMethod.POST)
	@ResponseBody
	public void addServ(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			String name = request.getParameter("name");
			String parent = request.getParameter("parent");
			int type = Integer.parseInt(request.getParameter("type"));
			String remarks = request.getParameter("remarks");
			String homeIcon = request.getParameter("homeIcon");
			String categoryIcon = request.getParameter("categoryIcon");
			String detailIcon = request.getParameter("detailIcon");
			
			//通过接口方式将数据存储于前端Portal数据库中
    		String servId = CspWorker.addService(name, parent,type,
    				remarks, homeIcon, categoryIcon, detailIcon);
    		if(servId == null || servId.equals("")) {
    			m.put("success", false);
    			return;
    		}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", servId);
			map.put("name", name);
			map.put("parent", parent);
			map.put("type", type);
			map.put("remarks", remarks);
			map.put("homeIcon", homeIcon);
			map.put("categoryIcon", categoryIcon);
			map.put("detailIcon", detailIcon);
			if(!parent.equals("6")){//添加到普通服务列表
				selfHelpOrderService.insertServ(map);
			}else{//添加到API服务列表
				selfHelpOrderService.insertAPI(map);
			}
			
			m.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			m.put("success", false);
		}finally {
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能描述：添加服务
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/updateServ.html",method = RequestMethod.POST)
	@ResponseBody
	public void updateServ(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			String id = request.getParameter("servId");
			String name = request.getParameter("name");
			String parent = request.getParameter("parent");
			int type = Integer.parseInt(request.getParameter("type"));
			String remarks = request.getParameter("remarks");
			String homeIcon = request.getParameter("homeIcon");
			String categoryIcon = request.getParameter("categoryIcon");
			String detailIcon = request.getParameter("detailIcon");
			
			//通过接口方式将数据存储于前端Portal数据库中
    		String code = CspWorker.updateService(id, name, parent,type,
    				remarks, homeIcon, categoryIcon, detailIcon);
    		if(code == null || !code.equals("200")) {
    			m.put("success", false);
    			return;
    		}
    		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("name", name);
			map.put("parent", parent);
			map.put("type", type);
			map.put("remarks", remarks);
			map.put("homeIcon", homeIcon);
			map.put("categoryIcon", categoryIcon);
			map.put("detailIcon", detailIcon);
			if(!parent.equals("6")){//添加到普通服务列表
				selfHelpOrderService.updateServ(map);
			}else{//添加到API服务列表
				selfHelpOrderService.updateAPI(map);
			}
		
			m.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			m.put("success", false);
		} finally {
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能描述：按条件查询服务
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/searchService.html")
	public String searchService(Model model,HttpServletRequest request,HttpServletResponse response){	
		String name = null;
		try {
			String nameStr = request.getParameter("servName");
			if(!StringUtils.isEmpty(nameStr)){
				name = new String(nameStr.getBytes("ISO-8859-1"),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int type = -1;
		int parent = -1;
		
		String typeStr = request.getParameter("servType");
		if(!StringUtils.isEmpty(typeStr)){
			type = Integer.parseInt(typeStr);
		}
		String parentStr = request.getParameter("parentC");
		if(!StringUtils.isEmpty(parentStr)){
			parent = Integer.parseInt(parentStr);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("parent", parent);
		map.put("type", type);
		
		List servList = selfHelpOrderService.findSerList(map);
		
        model.addAttribute("servList",servList);      //传对象到页面
        model.addAttribute("name",name);  //回显结束时间
        model.addAttribute("parent",parent);  //回显查询文本
        model.addAttribute("type",type);  //回显查询文本
        return "/source/adminPage/userManage/serviceList";
	}
	
	/**
	 * 功能描述：删除服务
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/delServ.html")
	public void delServ(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			String servId = request.getParameter("servId");
			String parent = new String(request.getParameter("parent").getBytes("ISO-8859-1"),"UTF-8");
			
			//通过接口方式将数据存储于前端Portal数据库中
    		String code = CspWorker.deleteService(servId,parent);
    		if(code == null || !code.equals("200")) {
    			m.put("success", false);
    			return;
    		}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("servId", servId);
			map.put("parent", parent);
			//删除service表数据
			selfHelpOrderService.delServ(map);
			//删除service详情表数据
			selfHelpOrderService.delServDetail(map);
			//删除scanType表数据
			if(!StringUtils.isEmpty(parent) && !parent.equals("API")){
				selfHelpOrderService.delScanType(Integer.parseInt(servId));
			}
			//TODO 删除价格表
			
			m.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			m.put("success", false);
			
		} finally {
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		

	}
	
//	/**
//	 * 功能描述：添加服务价格页面
//	 *		 @time 2015-2-3
//	 */
//	@RequestMapping("/addServicePriceUI.html")
//	public String addServicePriceUI(HttpServletRequest request){
//		//获取服务类型
//		int serviceId = Integer.parseInt(request.getParameter("servId"));
//		String parent = request.getParameter("parent");
//		Map<String,Object> newMap = new HashMap<String,Object>();
//		newMap.put("serviceId", serviceId);
////		newMap.put("parent", parent);
//		List<Price> priceList = servService.findPriceByParam(newMap);
//		if(priceList!=null && priceList.size()>0){
//			for (int i = 0; i < priceList.size(); i++) {
//				if(priceList.get(i).getType()==0){//单次
//					request.setAttribute("priceSingle", priceList.get(i).getPrice());
//				}else if(priceList.get(i).getType()==2){//大于
//					request.setAttribute("priceMaxTimesG", priceList.get(i).getTimesG());
//					request.setAttribute("priceMax", priceList.get(i).getPrice());
//				}
//			}
//		}
//		request.setAttribute("serviceId", serviceId);
//		request.setAttribute("priceList", priceList);//订单总数
//		//查询区间list
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("serviceId", serviceId);
//		//map.put("parent", parent);
//		map.put("type", 1);
//		List<Price> priceLongList = servService.findPriceByParam(map);
//        request.setAttribute("priceLongList", priceLongList);//订单总数
//		return "/source/serviceManage/priceManage";
//	}
	
//	/**
//	 * 功能描述：为服务添加价格
//	 * 参数描述：HttpServletRequest request,HttpServletResponse response
//	 *		 @time 2015-1-19
//	 */
//	@RequestMapping(value="/addServicePrice.html",method = RequestMethod.POST)
//	@ResponseBody
//	public void addServicePrice(HttpServletRequest request,HttpServletResponse response){
//		Map<String, Object> m = new HashMap<String, Object>();
//		try {
//			int serviceId = Integer.parseInt(request.getParameter("serviceId"));
//			int timesG = Integer.parseInt(request.getParameter("timesG"));
//			int timesLE = Integer.parseInt(request.getParameter("timesLE"));
//			int type = Integer.parseInt(request.getParameter("type"));
//			double price = Double.parseDouble(request.getParameter("price")); 
//			
//			Price newprice = new Price();
//			newprice.setServiceId(serviceId);
//			newprice.setTimesG(timesG);
//			newprice.setTimesLE(timesLE);
//			newprice.setPrice(price);
//			newprice.setType(type);
//			
//			servService.insertPrice(newprice);
//			
//
//			m.put("success", true);
//			//object转化为Json格式
//			JSONObject JSON = CommonUtil.objectToJson(response, m);
//			try {
//				// 把数据返回到页面
//				CommonUtil.writeToJsp(response, JSON);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			m.put("success", false);
//			//object转化为Json格式
//			JSONObject JSON = CommonUtil.objectToJson(response, m);
//			try {
//				// 把数据返回到页面
//				CommonUtil.writeToJsp(response, JSON);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
//	}
	
//	/**
//	 * 功能描述：删除服务价格
//	 * 参数描述：HttpServletRequest request,HttpServletResponse response
//	 *		 @time 2015-1-19
//	 */
//	@RequestMapping(value="/delServicePrice.html",method = RequestMethod.POST)
//	@ResponseBody
//	public void delServicePrice(HttpServletRequest request,HttpServletResponse response){
//		Map<String, Object> m = new HashMap<String, Object>();
//		try {
//			int serviceId = Integer.parseInt(request.getParameter("serviceId"));		
//			servService.delPrice(serviceId);
//			
//			m.put("success", true);
//			//object转化为Json格式
//			JSONObject JSON = CommonUtil.objectToJson(response, m);
//			try {
//				// 把数据返回到页面
//				CommonUtil.writeToJsp(response, JSON);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			m.put("success", false);
//			//object转化为Json格式
//			JSONObject JSON = CommonUtil.objectToJson(response, m);
//			try {
//				// 把数据返回到页面
//				CommonUtil.writeToJsp(response, JSON);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * 功能描述：服务详情维护
	 * @throws UnsupportedEncodingException 
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/serviceDetailsUI.html")
	public String serviceDetailsUI(HttpServletRequest request) throws UnsupportedEncodingException{
		//获取服务类型
		int serviceId = Integer.parseInt(request.getParameter("servId"));
		int parent = Integer.parseInt(request.getParameter("parent"));
		ServiceDetail serviceDetail = new ServiceDetail();
		List<Map<String, Object>> scanList = null;
		if(parent != 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("serviceId", serviceId);
			map.put("parent", parent);
			serviceDetail = selfHelpOrderService.findServiceDetail(map);

			map.remove("parent");
			scanList = selfHelpOrderService.findScanTypeList(map);
		}
		if(serviceDetail != null){
			String detailIcon = serviceDetail.getDetailIcon();
			request.setAttribute("detailIcon", detailIcon);
		}
		request.setAttribute("scanTypeList", scanList);
		request.setAttribute("serviceDetail", serviceDetail);
		request.setAttribute("serviceId", serviceId);
		request.setAttribute("parent", parent);
		request.setAttribute("webRootPath",CspWorker.getServerWebRoot());
		return "/source/adminPage/userManage/servDetails";
		//return "/source/serviceManage/servDetails";
	}
	
	/**
	 * 功能描述：保存服务详情
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	/**
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saveServDetails.html",method = RequestMethod.POST)
	@ResponseBody
	public void saveServDetails(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			int serviceId = Integer.parseInt(request.getParameter("serviceId"));
			int parentC = Integer.parseInt(request.getParameter("parent"));			//一级分类
			String priceTitle = request.getParameter("priceTitle"); //价格标题
			String typeTitle = request.getParameter("typeTitle");	//选类型标题
			int servType = -1;
			if(!StringUtils.isEmpty(request.getParameter("servType"))){    //选类型(0:单次和长期,1:长期,2:单次)
				servType = Integer.parseInt(request.getParameter("servType"));
			}		
			String servRatesTitle = request.getParameter("servRatesTitle");	//服务频率标题
			String scanTypeStr = null;
			scanTypeStr = request.getParameter("scanType");				//服务频率
			String servIconFlag = request.getParameter("servIconFlag");
			String servIcon = request.getParameter("servIcon");				//服务详情图片
			
			//通过接口方式将数据存储于前端Portal数据库中
    		String code = CspWorker.saveServDetails(serviceId, parentC, priceTitle, 
    				typeTitle, servType, servRatesTitle, scanTypeStr, servIcon);
			if(code == null || !code.equals("200")) {
				m.put("success", false);
				return;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("serviceId", serviceId);
			map.put("parent", parentC);
			ServiceDetail serviceDetail = selfHelpOrderService.findServiceDetail(map);
			boolean editFlag = false;
			if(serviceDetail != null){
				//删除scanType表serviceId的相关信息
				editFlag = true;
				selfHelpOrderService.delScanType(serviceId);
			}
			
			//int parentC = selfHelpOrderService.selectParentId(parent);
			
			String[] scanType = scanTypeStr.split(",");
			if(!StringUtils.isEmpty(scanType)){
				for(int i=0; i<scanType.length; i++){
					Map<String, Object> insertMap = new HashMap<String, Object>();
					insertMap.put("serviceId", serviceId);
					insertMap.put("scanName", scanType[i]);
					Integer scanTypeInt = scanTypeMap.get(scanType[i]);
					if (scanTypeInt != null) {
						insertMap.put("scanType", scanTypeInt);
						selfHelpOrderService.insertScanType(insertMap);
					}
				}
			}

			ServiceDetail sd = new ServiceDetail();
			sd.setServiceId(serviceId);
			sd.setPriceTitle(priceTitle);
			sd.setTypeTitle(typeTitle);
			sd.setServType(servType);
			sd.setRatesTitle(servRatesTitle);
			sd.setDetailIcon(servIcon);
			sd.setCreateTime(new Date());
			sd.setParentC(parentC);
			sd.setDetailFlag(servIconFlag);
			if(editFlag){
				selfHelpOrderService.updateServiceDetail(sd);
			} else {
				selfHelpOrderService.insertServiceDetail(sd);
			}

			m.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			m.put("success", false);
		}finally {
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * 功能描述：上传服务详情图片
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/uploadDetail.html")
	public void uploadDetailPhoto(HttpServletRequest request,HttpServletResponse response){

		Map<String, Object> m = new HashMap<String, Object>();
		try {
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
    				MultipartFile multipartFile = multiRequest.getFile(iter.next().toString());
    		        if(multipartFile==null){
    		        	m.put("success", false);
    		        	m.put("errorMsg", "请上传文件!");
    		        	return;
    		        }
    				
    		        String originalFileName=multipartFile.getOriginalFilename();
    		        String fileType = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
    		        //判断文件格式
    		        if(!"png".equals(fileType.toLowerCase())){
    		        	m.put("successFlag", false);
    		        	m.put("errorMsg", "请导入png格式的文件!");
    		        	
    		        	return;
    		        }
    		        //上传文件名称；
    		        String uploadFilePathName = String.valueOf(System.currentTimeMillis())+originalFileName.substring(originalFileName.lastIndexOf("."));
    		        boolean isSuccFlag = SFTPUtil.upload(multipartFile, uploadFilePathName, 2);

    		        if(isSuccFlag){
    					m.put("success", true);
    					m.put("filePath", uploadFilePathName);
    		        } else {
    		        	m.put("success", false);
    		        }

    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("success", false);
			
		}finally {
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
