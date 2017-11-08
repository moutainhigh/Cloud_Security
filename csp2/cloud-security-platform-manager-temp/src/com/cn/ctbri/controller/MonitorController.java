package com.cn.ctbri.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Advertisement;

@Controller
public class MonitorController {

	/**
     * 功能描述：可用性监控页面
     * 参数描述：无
     *       @time 2017-11-3
     */
    @RequestMapping("/monitor.html")
    public String monitor(){
        return "/source/adminPage/userManage/monitor";
    }
    
}
