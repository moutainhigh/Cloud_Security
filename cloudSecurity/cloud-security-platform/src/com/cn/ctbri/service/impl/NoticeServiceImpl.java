package com.cn.ctbri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.NoticeDao;
import com.cn.ctbri.dao.UserDao;
import com.cn.ctbri.entity.Notice;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.INoticeService;
import com.cn.ctbri.service.IUserService;
/**
 * 创 建 人  ： txr
 * 创建日期：  2015-3-10
 * 描        述：  公告业务层实现类
 * 版        本：  1.0
 */
@Service
public class NoticeServiceImpl implements INoticeService{

    @Autowired
    NoticeDao noticeDao;
    /**
     * 功能描述：查询所有公告
     *       @time 2015-3-10
     */
    public List<Notice> findAllNotice() {
        return noticeDao.findAllNotice();
    }
    /**
     * 功能描述：添加公告
     *       @time 2015-3-10
     */
    public void insert(Notice notice) {
        noticeDao.addNotice(notice);
    }
    /**
     * 功能描述：删除公告
     *       @time 2015-3-10
     */
    public void delete(int id) {
        noticeDao.deleteNotice(id);
    }
    /**
     * 功能描述：修改公告
     *       @time 2015-3-10
     */
    public void update(Notice notice) {
        noticeDao.updateNotice(notice);
    }
    /**
     * 功能描述：查询公告
     *       @time 2015-3-11
     */
    public Notice findNoticeById(Notice notice) {
        return noticeDao.findNoticeById(notice);
    }

}
