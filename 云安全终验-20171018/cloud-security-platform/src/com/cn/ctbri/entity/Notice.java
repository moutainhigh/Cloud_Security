package com.cn.ctbri.entity;

import java.util.Date;

/**
 * 创 建 人  ：  txr
 * 创建日期：   2015-3-10
 * 描        述：  公告实体类
 * 版        本：  1.0
 */
public class Notice {
	private int id;
	//公告名称
	private String noticeName;
	//内容
	private String noticeDesc;
	//创建时间
	private Date createDate;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNoticeName() {
        return noticeName;
    }
    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }
    public String getNoticeDesc() {
        return noticeDesc;
    }
    public void setNoticeDesc(String noticeDesc) {
        this.noticeDesc = noticeDesc;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
	
}
