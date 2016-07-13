package com.cn.ctbri.controller;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
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

import com.cn.ctbri.cfg.Configuration;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IDistrictDataService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.GetNetContent;
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
	@Autowired
	IOrderAssetService orderAssetService;
	@Autowired
	IDistrictDataService districtService;
	@Autowired
	IUserService userService;
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
	 * 异步进入我的资产页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/userAssets.html")
	public ModelAndView userAssets(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("source/page/userCenter/assetList");
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		List<Asset> list = assetService.findByUserId(globle_user.getId());
		if(list!=null && list.size()>0){
			mv.addObject("list", list);
		}
		return mv;
	}

	/**
	 * 功能描述： 检查资产地址是否已经存在
	 * 参数描述： Model model
	 *		 @time 2015-3-9
	 */
	@RequestMapping("/asset_addrIsExist.html")
	@ResponseBody
	public void addrIsExist(Model model,Asset asset,HttpServletResponse response,HttpServletRequest request) throws Exception{
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    String oldName = request.getParameter("oldName");
	    String oldAddr = request.getParameter("oldAddr");
	    String oldAddrVal="";
	    if(oldAddr!=null&&!"".equals(oldAddr)){
	    	oldAddrVal=oldAddr.toLowerCase();
	    }

	    String addr = asset.getAddr().toLowerCase();
	    //判断资产地址是否包含http
	    Pattern pattern2 = Pattern.compile("([hH][tT][tT][pP][sS]?):\\/\\/([\\w.]+\\/?)\\S*");
		Matcher matcher2 =	pattern2.matcher(addr);
		if(!matcher2.find()){
			addr="http://"+addr.trim();
		}
	    String assetName =URLDecoder.decode(asset.getName(),"utf-8"); ;
        paramMap.put("userId", globle_user.getId());
    	//String addrType = request.getParameter("addrType");
    	/*if(!(addr.startsWith(addrType))){
            addr = addrType + "://" + addr.trim();
        }*/
		Map<String, Object> m = new HashMap<String, Object>();
	    if(oldName!=null || oldAddrVal!=null){
	    	//修改时判断存在
	    	if(!assetName.trim().equals(oldName)){
	            paramMap.put("name", assetName.trim());
	    		List<Asset> listForName = assetService.findByAssetAddr(paramMap);

	    		if(listForName != null && listForName.size()>0){
	    			m.put("msg", '1');//1：表示资产名称已经存在
	    		}else{
	    			 if(!addr.trim().equals(oldAddrVal)){
	    		    		Map<String, Object> paramMap1 = new HashMap<String, Object>();
	    					paramMap1.put("userId", globle_user.getId());
	    					paramMap1.put("addr", addr.trim());
	    					List<Asset> listForAddr = assetService.findByAssetAddr(paramMap1);
	    					if(listForAddr != null && listForAddr.size()>0){
	    						m.put("msg", '2');//1：表示资产地址已经存在
	    					}
	    		    	}else{
	    		    		m.put("msg", false);
	    		    	}
	    		}
	    	}else if(!addr.trim().equals(oldAddrVal)){
	    		Map<String, Object> paramMap1 = new HashMap<String, Object>();
				paramMap1.put("userId", globle_user.getId());
				paramMap1.put("addr", addr.toLowerCase().trim());
				List<Asset> listForAddr = assetService.findByAssetAddr(paramMap1);
				if(listForAddr != null && listForAddr.size()>0){
					m.put("msg", '2');//1：表示资产地址已经存在
				}
	    	}else{
	    		m.put("msg", false);
	    	}
	    }else{
	        paramMap.put("name", assetName.trim());
			List<Asset> listForName = assetService.findByAssetAddr(paramMap);
			if(listForName != null && listForName.size()>0){
				m.put("msg", '1');//1：表示资产名称已经存在
			}else{
				Map<String, Object> paramMap1 = new HashMap<String, Object>();
				paramMap1.put("userId", globle_user.getId());
				paramMap1.put("addr", addr.trim());
				List<Asset> listForAddr = assetService.findByAssetAddr(paramMap1);
				if(listForAddr != null && listForAddr.size()>0){
					m.put("msg", '2');//1：表示资产地址已经存在
				}else{
					m.put("msg", false);
				}
			}
	    }
	    
		//object转化为Json格式
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述： 添加资产
	 * 参数描述： Model model
	 * @throws Exception 
	 *		 @time 2015-1-16
	 */
	@RequestMapping(value="/addAsset.html",method=RequestMethod.POST)
	public String addAsset(Model model,Asset asset,HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		asset.setUserid(globle_user.getId());//用户ID
		asset.setCreate_date(new Date());//创建日期
		//modify by zsh 2016-06-28 
		asset.setStatus(1);//资产状态 1：已验证
//		if(globle_user.getType()==2){
//			asset.setStatus(0);//资产状态(1：已验证，0：未验证)
//		}else if(globle_user.getType()==3){
//			asset.setStatus(1);//资产状态(1：已验证，0：未验证)
//		}

		String name = "";//资产名称
		String addr = "";//资产地址
		//String addrType = asset.getAddrType();
		String purpose = "";//用途
		//处理页面输入中文乱码的问题
		try {
			name=asset.getName();
			addr=asset.getAddr().toLowerCase();
			purpose=asset.getPurpose();
			//判断资产地址是否包含http
		    Pattern pattern2 = Pattern.compile("([hH][tT][tT][pP][sS]?):\\/\\/([\\w.]+\\/?)\\S*");
			Matcher matcher2 =	pattern2.matcher(addr);
			if(!matcher2.find()){
				addr="http://"+addr.trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		asset.setName(name);
		asset.setAddr(addr);
		asset.setPurpose(purpose);
		assetService.saveAsset(asset);
		return "redirect:/userAssetsUI.html";
	}

	/**
	 * 功能描述：检查资产是否可以被删除
	 * 参数描述：Asset asset ,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping("/checkdelete.html")
	public void checkdelete(Asset asset,HttpServletResponse response){
		//检查订单资产表里面是否含有此资产
		List<OrderAsset> list = orderAssetService.findAssetById(asset.getId());
		int count = 0;
		if(list.size()>0){
			count = list.size();
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", count);
		//object转化为Json格式
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述：检查资产是否可以被修改
	 * 参数描述：Asset asset ,HttpServletResponse response
	 *		 @time 2016-7-1
	 */
	@RequestMapping("/checkedit.html")
	@ResponseBody
	public void checkedit(Asset asset,HttpServletResponse response){
		//检查订单资产表里面是否含有此资产
		List<OrderAsset> list = orderAssetService.findRunAssetById(asset.getId());
		int count = 0;
		if(list.size()>0){
			count = list.size();
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", count);
		//object转化为Json格式
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述：修改资产
	 * 参数描述： Model model
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/editAsset.html",method=RequestMethod.POST)
	public void editAsset(HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
        try {
			int id = Integer.parseInt(request.getParameter("id"));
			Asset oldAsset = assetService.findById(id);
			//String addrType = request.getParameter("addrType");
			String districtId = request.getParameter("prov");
			String assetAddr = request.getParameter("assetAddr").toLowerCase();
			Pattern pattern2 = Pattern.compile("([hH][tT][tT][pP][sS]?):\\/\\/([\\w.]+\\/?)\\S*");
			Matcher matcher2 =	pattern2.matcher(assetAddr);
			if(!matcher2.find()){
				assetAddr="http://"+assetAddr.trim();
			}
			/*//判断资产地址是否包含http
		    if((assetAddr.trim().toLowerCase().indexOf("http://")==-1||assetAddr.trim().toLowerCase().indexOf("https://")==-1)){
		    
		    }*/
			String name = request.getParameter("assetName");
			String purpose = request.getParameter("purpose");
			String city = "";
			String oldCity = "";
			if(oldAsset.getCity()!=null){
				oldCity = oldAsset.getCity();
			}
			if(city!=null){
				city = request.getParameter("city");
			}
			if(!(name.equals(oldAsset.getName())&&assetAddr.equals(oldAsset.getAddr())&&purpose.equals(oldAsset.getPurpose())&&
				districtId==oldAsset.getDistrictId()&&
				oldCity.equals(city))){
				Asset asset = new Asset();
				asset.setName(name);
				asset.setId(id);
				//String addr = request.getParameter("assetAddr");
				
				asset.setAddr(assetAddr);
				//modify by zsh 2016-06-28 
				asset.setStatus(1);  //资产状态 1：已验证
//				asset.setStatus(0);
				asset.setDistrictId(districtId);
				asset.setCity(city);
				asset.setPurpose(purpose);
				//asset.setAddrType(addrType);
				assetService.updateAsset(asset);

				m.put("successFlag", true);
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (NumberFormatException e) {
			m.put("successFlag", false);
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	/**
	 * 功能描述：删除资产
	 * 参数描述： Model model
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/deleteAsset.html",method=RequestMethod.POST)
	public void delete(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		int id = Integer.parseInt(request.getParameter("id"));
	    try {
			if(validate(request)){
			    assetService.delete(id);
			    m.put("successFlag", true);
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			m.put("successFlag", false);
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	/**
	 * 功能描述：联合搜索
	 * 参数描述： Model model,Asset asset,HttpServletRequest request
	 *		 @time 2015-1-19
	 */
	@RequestMapping("/searchAssetCombine.html")
	public ModelAndView searchAssetsCombine(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("source/page/userCenter/assetList");
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		Map<String, Object> map = new HashMap<String, Object>();

		String name = request.getParameter("searchAssetName");

		if(name!=null && name.equals("输入资产名称或地址")){
			name="";
		}
		map.put("userid", globle_user.getId());
		map.put("keyword", name);
		List<Asset> list = assetService.searchAssetsCombine(map);//根据userid 资产状态 和资产名称联合查询
		
		mv.addObject("list", list);

				
		return mv;
	}
	/**
	 * 功能描述：检查资产是否通过验证
	 * 参数描述： Asset asset
	 *		 @time 2015-1-19
	 *	返回值：无
	 */
	@RequestMapping(value="/asset_verification.html",method=RequestMethod.POST)
	public void asset_verification(HttpServletRequest request,HttpServletResponse response){
		int id = Integer.valueOf(request.getParameter("id"));
		Asset _asset = assetService.findById(id);
		String path = _asset.getAddr().toLowerCase();
		int status = _asset.getStatus();
		//获取验证方式:代码验证 ;上传文件验证
		String verification_msg;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("status", status);//返回验证状态
		try {
//			URL url =new URL(path);
//			HttpClient httpClient = HttpClient.New(url);
//			OutputStream outputStream = httpClient.getOutputStream();
//			verification_msg = new String(request.getParameter("codeStyle").getBytes("ISO-8859-1"), "UTF-8");
			verification_msg = request.getParameter("codeStyle");
			//代码验证
			if(verification_msg.equals("codeVerification")){
				 //获取已知代码
				 String code1 = String.valueOf(request.getParameter("code1"));
//				 String code1 = new String(String.valueOf(request.getParameter("code1")).getBytes("ISO-8859-1"),"UTF-8");
				 
				 String str = GetNetContent.getNodeList(path);
//				 String str= rt.toString();
//				 String str= rt.toHtml();
				 if(str.contains(code1)){
					 m.put("msg", 1);//验证成功
				 }else{
					 m.put("msg", 0);//验证失败
				 }
			}
			//上传文件验证
			if(verification_msg.equals("fileVerification")){
				try{
					String newPath = "";
					if(path.endsWith("/")){
						newPath = path+"key.html";
					}else{
						newPath =  path+"/key.html";
					}
					URL fileUrl = new URL(newPath);//http://localhost:8080/cloud-security-platform/key.txt
					//sun.net.www.protocol.http.HttpURLConnection:http://localhost:8080/cloud-security-platform/key.txt
					HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
					DataInputStream input = new DataInputStream(conn.getInputStream());
					StringBuffer sb = new StringBuffer();
					int len = 0;
					while ((len = input.read()) != -1) {
	
						sb.append((char) len);
					}
					String toStringSb = sb.toString();
					input.close();
					if(toStringSb!=null&&toStringSb.contains(Configuration.getFileContent())){
						m.put("msg", 1);//验证成功
					}else{
						m.put("msg", 0);//验证失败
					}
				
				}catch (Exception e) {
					m.put("msg",0);//验证失败
					e.printStackTrace();
				}
			}
			 JSONObject JSON = CommonUtil.objectToJson(response, m);
			// 把数据返回到页面
			 CommonUtil.writeToJsp(response, JSON);
		} catch (Exception e) {
			m.put("msg",0);//验证失败
			JSONObject JSON = CommonUtil.objectToJson(response, m);
				// 把数据返回到页面
			try {
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述：资产验证
	 * 参数描述： Asset asset
	 *		 @time 2015-1-19
	 *	返回值：redirect:/userAssetsUI.html
	 */
	@RequestMapping(value="/verificationAsset.html",method=RequestMethod.POST)
	public String verificationAsset(Asset asset){
		asset.setStatus(1);
		assetService.updateAsset(asset);
		return "redirect:/userAssetsUI.html";
	}
	//================下载=================
    private String type;// 文件的MIME类型
    
	public String getType() {
		return type;
	}
	
	/**
	 * 功能描述：下载导入模板
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-21
	 */
	@RequestMapping("/download.html")
	public void download(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String fileName = request.getParameter("fileName");
		//fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");//反编译解决路径乱码
		//获取文件路径
		String path = request.getSession().getServletContext().getRealPath("/source/download");
		File file = new File(path+"/"+fileName);
		//response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1") + ";");//文件的下载方式：attachment：附件
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");//文件的下载方式：attachment：附件
		// 通常文件名称得到mime类型
		type = request.getSession().getServletContext().getMimeType(fileName);
		//将路径path转化成输入流
		InputStream in = new FileInputStream(file);
		//将输入流的数据放置到模型驱动对象的InputStream的属性中
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		in.close();
		ServletOutputStream os = response.getOutputStream();
		os.write(out.toByteArray());
		os.flush();
		os.close();
		return;
	}
	
	/**
     * 功能描述：防止跨域操作
     * 参数描述：HttpServletRequest request
     *       @time 2015-8-6
     */
	public static boolean validate(HttpServletRequest request){
        String Referer = "";
        boolean referer_sign = true; // true 站内提交，验证通过 //false 站外提交，验证失败
        Enumeration headerValues = request.getHeaders("Referer");
        while (headerValues.hasMoreElements())
            Referer = (String) headerValues.nextElement();
        // 判断是否存在请求页面
        if (Referer == null || Referer.length() < 1)
            referer_sign = false;
        else {
            // 判断请求页面和getRequestURI是否相同
            String servername_str = request.getServerName();
            if (servername_str != null || servername_str.length() > 0) {
                int index = 0;
                if (StringUtils.indexOf(Referer, "https://") == 0) {
                    index = 8;
                } else if (StringUtils.indexOf(Referer, "http://") == 0) {
                    index = 7;
                }
                if (Referer.length() - index < servername_str.length()) // 长度不够
                    referer_sign = false;
                else { // 比较字符串（主机名称）是否相同
                    String referer_str = Referer.substring(index, index
                            + servername_str.length());
                    if (!servername_str.equalsIgnoreCase(referer_str))
                        referer_sign = false;
                }
            } else
                referer_sign = false;
        }
        return referer_sign;
	    }
	
	@RequestMapping("/asset_CountOver.html")
	@ResponseBody
	public void isAssetCountOver(HttpServletResponse response,HttpServletRequest request) throws Exception{
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    
	    //根据用户id查询
	    List<User> userList = userService.findUserById(globle_user.getId());
	    User _user = userList.get(0);
	    
        //根据用户判断资产数是否超过预定
		List<Asset> list = assetService.findByUserId(globle_user.getId());
		Map<String, Object> m = new HashMap<String, Object>();
		
		//个人用户
		if(_user.getType()==2){
			if(list != null && list.size() >= 5){
				m.put("msg", true);//表示添加的资产数已经为5，不能再进行添加
				m.put("allowCount", "5");
			}else{
				m.put("msg", false);
			}
		}else if(_user.getType()==3){
			//企业用户
			if(list != null && list.size() >= 50){
				m.put("msg", true);//表示添加的资产数已经为10，不能再进行添加
				m.put("allowCount", "50");
			}else{
				m.put("msg", false);
			}
		}

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
