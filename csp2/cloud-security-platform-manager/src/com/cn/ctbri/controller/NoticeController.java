package com.cn.ctbri.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Notice;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.INoticeService;


/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-3-10
 * 描        述：  后台公告管理
 * 版        本：  1.0
 */
@Controller
public class NoticeController {
    
    @Autowired
    INoticeService noticeService;
    
    /**
     * 功能描述：后台公告管理页面
     * 参数描述：无
     *       @time 2015-3-10
     */
    @RequestMapping("/adminNoticeManageUI.html")
    public String noticeManageUI(Model model,HttpServletRequest request,User user){
        //User globle_user = (User) request.getSession().getAttribute("globle_user");
        List<Notice> list = noticeService.findAllNotice();
        model.addAttribute("noticeList",list);//公告列表
        return "/source/adminPage/userManage/noticeManage";
    }
    
    
    /**
     * 功能描述：添加公告
     *       @time 2015-3-10
     */
    @RequestMapping("/adminNoticeAdd.html")
    public String noticeAdd(Notice notice){
        String noticeName = "";
        String noticeDesc = "";
        try {//中文乱码
            noticeName = new String(notice.getNoticeName().getBytes("ISO-8859-1"), "UTF-8");
            noticeDesc = new String(notice.getNoticeDesc().getBytes("ISO-8859-1"), "UTF-8");
            notice.setNoticeName(noticeName);
            notice.setNoticeDesc(noticeDesc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        notice.setCreateDate(new Date());//创建时间
        noticeService.insert(notice);
        return "redirect:/adminNoticeManageUI.html";
    }
    /**
     * 功能描述：修改公告
     *       @time 2015-3-10
     */
    @RequestMapping("/adminNoticeEdit.html")
    public String edit(Notice notice){
        String noticeName = "";
        String noticeDesc = "";
        try {//中文乱码
            noticeName = new String(notice.getNoticeName().getBytes("ISO-8859-1"), "UTF-8");
            noticeDesc = new String(notice.getNoticeDesc().getBytes("ISO-8859-1"), "UTF-8");
            notice.setNoticeName(noticeName);
            notice.setNoticeDesc(noticeDesc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        noticeService.update(notice);
        return "redirect:/adminNoticeManageUI.html";
    }
    
    
    
    /**
     * 功能描述：删除公告
     *       @time 2015-3-10
     */
    @RequestMapping("/adminNoticeDelete.html")
    public String delete(Notice notice){
        noticeService.delete(notice.getId());
        return "redirect:/adminNoticeManageUI.html";
    }
    
    /**
     * 功能描述：查看公告
     *       @time 2015-3-11
     */
    @RequestMapping("/noticeDescUI.html")
    public String noticeDesc(Model model,HttpServletRequest request,Notice notice){
        if(notice.getId()==0||"".equals(notice)){
            request.setAttribute("errorMsg", "页面不存在!");
            return "/source/error/errorMsg";
        }else{
            Notice no = noticeService.findNoticeById(notice);
            model.addAttribute("notice",no);
            return "/source/adminPage/userManage/noticeShow";
        }
    }
}
