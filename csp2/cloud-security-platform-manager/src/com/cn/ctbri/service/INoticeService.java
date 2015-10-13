package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.Notice;
import com.cn.ctbri.entity.User;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-3-10
 * 描        述：  公告业务层接口
 * 版        本：  1.0
 */
public interface INoticeService {

	/**
	 * 功能描述：查询所有公告
	 *		 @time 2015-3-10
	 */
	List<Notice> findAllNotice();
	/**
     * 功能描述：添加公告
     *       @time 2015-3-10
     */
    void insert(Notice notice);
    /**
     * 功能描述：删除公告
     *       @time 2015-3-10
     */
    void delete(int id);
    /**
     * 功能描述：修改公告
     *       @time 2015-3-10
     */
    void update(Notice notice);
    /**
     * 功能描述：查询公告
     *       @time 2015-3-11
     */
    Notice findNoticeById(Notice notice);
	
}
