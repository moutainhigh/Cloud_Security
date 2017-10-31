package com.cn.ctbri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/mvc")
public class MyController {

    @RequestMapping(value="/hello",method=RequestMethod.GET)
    public String hello(ModelMap model){        
    	model.addAttribute("message", "Hello Spring MVC Framework!");
    	return "hello2";
    }
}