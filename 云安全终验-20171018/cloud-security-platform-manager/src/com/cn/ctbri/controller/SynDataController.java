package com.cn.ctbri.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.entity.MobileInfo;
import com.cn.ctbri.entity.Notice;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.scheduler.SynData;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.INoticeService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.LogonUtils;
import com.cn.ctbri.util.Mail;
import com.cn.ctbri.util.MailUtils;
import com.cn.ctbri.util.Random;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-4-1
 * 描        述：  同步数据控制层
 * 版        本：  1.0
 */
@Controller
public class SynDataController {
	/**
	 * 功能描述： 同步数据
	 * 参数描述： Model model,HttpServletRequest request
	 *		 @time 2016-4-1
	 */
	@RequestMapping(value="synData.html")
    @ResponseBody
	public void synData(HttpServletResponse response,HttpServletRequest request){
		boolean d = new SynData().getParams();
		Map<String, Object> m = new HashMap<String, Object>();
        if(d){
        	m.put("message", true);
        }else{
        	m.put("message", false);
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
